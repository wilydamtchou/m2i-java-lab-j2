package com.m2i.service;

import java.util.*;
import java.util.stream.Collectors;

import com.m2i.client.Client;
import com.m2i.compte.Compte;
import com.m2i.compte.CompteCourant;
import com.m2i.compte.CompteEpargne;
import com.m2i.compte.TypeCompte;
import com.m2i.dto.CompteDTO;
import com.m2i.transaction.Transaction;
import com.m2i.transaction.TypeTransaction;

public class BankService implements IBankService {

    private final Map<String, Client> clients = new HashMap<>();
    private final Map<String, List<Compte>> comptesParClient = new HashMap<>();
    private final Map<String, List<Transaction>> txParCompte = new HashMap<>();
    private final AbstractTransactionService transactionService = new TransactionService();

    /** UC1 : Créer un client */
    @Override
    public Client creerClient(String nom, String prenom, String email, String telephone) {
        Client c = new Client(nom, prenom, email, telephone);
        clients.put(c.getId(), c);
        comptesParClient.put(c.getId(), new ArrayList<>());
        return c;
    }

    /** UC2 : Créer un compte pour un client */
    @Override
    public Compte creerCompte(String clientId, TypeCompte type) {
        Client c = clients.get(clientId);
        if (c == null) throw new NoSuchElementException("Client inconnu");
        
        Compte compte;

        if (type.equals(TypeCompte.COURANT))
        	compte = new CompteCourant(c, 0);
        else
        	compte = new CompteEpargne(c, 0.02);

        comptesParClient.get(clientId).add(compte);
        txParCompte.put(compte.getNumeroCompte(), new ArrayList<>());
        return compte;
    }

    /** UC3 : Consulter la liste des comptes (retourne des DTO) */
    @Override
    public List<CompteDTO> listerComptes(String clientId) {
        return comptesParClient.getOrDefault(clientId, List.of()).stream()
            .map(c -> new CompteDTO(
                c.getNumeroCompte(),
                c.getSolde(),
                c.getDateCreation(),
                c.getProprietaire().getNom() + " " + c.getProprietaire().getPrenom()
            ))
            .collect(Collectors.toList());
    }

    /** UC4 : Effectuer une transaction (dépôt ou retrait) */
    @Override
    public Transaction effectuerTransaction(
            String numeroCompte,
            TypeTransaction type,
            double montant) 
    {
        List<Transaction> listTx = txParCompte.get(numeroCompte);
        if (listTx == null) throw new NoSuchElementException("Compte inconnu");
        Compte compte = comptesParClient.values().stream()
                          .flatMap(List::stream)
                          .filter(c -> c.getNumeroCompte().equals(numeroCompte))
                          .findFirst()
                          .orElseThrow(() -> new NoSuchElementException("Compte introuvable"));
        
        Transaction tx = transactionService.creerTransaction(compte, montant, type);
        
        listTx.add(tx);

        return tx;
    }

    /** UC5 : Afficher les transactions d’un compte */
    @Override
    public List<Transaction> listerTransactions(String numeroCompte) {
        return Collections.unmodifiableList(
            txParCompte.getOrDefault(numeroCompte, List.of())
        );
    }
    
    /** UC4-bis : Déposer de l'argent */
    @Override
    public Transaction deposer(String numeroCompte, double montant) {
        return effectuerTransaction(numeroCompte, TypeTransaction.CREDIT, montant);
    }

    /** UC4-ter : Retirer de l'argent */
    @Override
    public Transaction retirer(String numeroCompte, double montant) {
        return effectuerTransaction(numeroCompte, TypeTransaction.DEBIT, montant);
    }
}

