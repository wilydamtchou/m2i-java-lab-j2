package com.m2i.service;

import java.util.*;
import java.util.stream.Collectors;

import com.m2i.client.Client;
import com.m2i.compte.Compte;
import com.m2i.compte.CompteCourant;
import com.m2i.compte.CompteEpargne;
import com.m2i.compte.TypeCompte;
import com.m2i.dto.CompteDTO;
import com.m2i.dto.Report;
import com.m2i.dto.TransactionSummary;
import com.m2i.exception.TypeInvalideException;
import com.m2i.transaction.Transaction;
import com.m2i.transaction.TypeTransaction;

public class BankService implements IBankService {

    private final Map<String, Client> clients = new HashMap<>();
    private final Map<String, List<Compte>> comptesParClient = new HashMap<>();
    private final Map<String, List<Transaction>> txParCompte = new HashMap<>();
    private final AbstractTransactionService transactionService = new TransactionService();

    /** Interface interne pour écouter les transactions */
    public interface TransactionListener {
        void onTransaction(Transaction tx);
    }

    /** Classe interne (non-static) pour logger les transactions */
    private class DefaultListener implements TransactionListener {
        @Override
        public void onTransaction(Transaction tx) {
            System.out.println("Nouvelle transaction enregistrée : " + tx);
        }
    }

    /** Liste des listeners */
    private final List<TransactionListener> listeners = new ArrayList<>();

    /** Méthode pour ajouter un listener (anonyme ou non) */
    public void addListener(TransactionListener l) {
        listeners.add(l);
    }

    /** Constructeur : on inscrit le listener par défaut */
    public BankService() {
        addListener(new DefaultListener());
    }

    @Override
    public Client creerClient(String nom, String prenom, String email, String telephone) {
        Client c = new Client(nom, prenom, email, telephone);
        clients.put(c.getId(), c);
        comptesParClient.put(c.getId(), new ArrayList<>());
        return c;
    }

    @Override
    public Compte creerCompte(String clientId, TypeCompte type) throws TypeInvalideException {
        Client c = clients.get(clientId);
        if (c == null) throw new NoSuchElementException("Client inconnu");

        Compte compte;
        if (type == TypeCompte.COURANT)
            compte = new CompteCourant(c, 0);
        else if(type == TypeCompte.EPARGNE)
            compte = new CompteEpargne(c, 0.02);
        else
        	throw new TypeInvalideException(type);

        comptesParClient.get(clientId).add(compte);
        txParCompte.put(compte.getNumeroCompte(), new ArrayList<>());
        return compte;
    }

    @Override
    public List<CompteDTO> listerComptes(String clientId) {
        return comptesParClient.getOrDefault(clientId, List.of()).stream()
            .map(c -> new CompteDTO(
                c.getNumeroCompte(),
                c.getSolde(),
                c.getDateCreation(),
                c.getProprietaire().getNom() + " " + c.getProprietaire().getPrenom()))
            .collect(Collectors.toList());
    }

    @Override
    public Transaction effectuerTransaction(String numeroCompte, TypeTransaction type, double montant) {
        List<Transaction> listTx = txParCompte.get(numeroCompte);
        if (listTx == null) throw new NoSuchElementException("Compte inconnu");

        Compte compte = comptesParClient.values().stream()
            .flatMap(List::stream)
            .filter(c -> c.getNumeroCompte().equals(numeroCompte))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Compte introuvable"));

        Transaction tx = transactionService.creerTransaction(compte, montant, type);
        listTx.add(tx);

        // Notifier tous les listeners (inner et anonymes)
        for (TransactionListener l : listeners) {
            l.onTransaction(tx);
        }

        return tx;
    }

    @Override
    public List<Transaction> listerTransactions(String numeroCompte) {
        return Collections.unmodifiableList(
            txParCompte.getOrDefault(numeroCompte, List.of())
        );
    }

    @Override
    public Transaction deposer(String numeroCompte, double montant) {
        return effectuerTransaction(numeroCompte, TypeTransaction.CREDIT, montant);
    }

    @Override
    public Transaction retirer(String numeroCompte, double montant) {
        return effectuerTransaction(numeroCompte, TypeTransaction.DEBIT, montant);
    }
    
    /**
     * Exercice 1 – Étape 1–3 :
     * Filtrer et mapper une liste de transactions en TransactionSummary.
     */
    public List<TransactionSummary> filtrerEtMapper(List<Transaction> txs, double seuil) {
        return txs.stream()
            // Étape 1 : filtrer
            .filter(t -> t.getMontant() > seuil)
            // Étape 2 : mapper
            .map(t -> new TransactionSummary(
                t.getId(),
                t.getDate(),
                t.getMontant(),
                t.getType()
            ))
            // Étape 3 : collecter
            .collect(Collectors.toList());
    }

    /**
     * Exercice 1 – calcul du solde :
     * Crédit positif, débit négatif.
     */
    public double calculerSolde(List<Transaction> txs) {
        return txs.stream()
            .mapToDouble(t ->
                t.getType() == TypeTransaction.CREDIT
                    ? t.getMontant()
                    : -t.getMontant()
            )
            .sum();
    }
    
    /**
     * Exercice 3 – Génération d’un rapport de synthèse des transactions.
     */
    public Report syntheseTransactions(List<Transaction> txs) {
        double totalCredit = txs.stream()
            .filter(t -> t.getType() == TypeTransaction.CREDIT)
            .mapToDouble(Transaction::getMontant)
            .sum();

        double totalDebit = txs.stream()
            .filter(t -> t.getType() == TypeTransaction.DEBIT)
            .mapToDouble(Transaction::getMontant)
            .sum();

        long count = txs.size();

        return new Report(count, totalCredit, totalDebit);
    }

}
