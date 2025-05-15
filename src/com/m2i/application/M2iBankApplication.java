package com.m2i.application;

import java.util.List;

import com.m2i.client.Client;
import com.m2i.compte.CompteCourant;
import com.m2i.compte.CompteUtils;
import com.m2i.compte.TypeCompte;
import com.m2i.dto.Report;
import com.m2i.dto.TransactionSummary;
import com.m2i.exception.TypeInvalideException;
import com.m2i.service.BankService;
import com.m2i.service.IBankService;
import com.m2i.transaction.Transaction;

class MonException extends Exception {
    public MonException(String message) {
        super(message);
    }
}

public class M2iBankApplication {
    public static void main(String[] args) {
        IBankService bankService = new BankService();

        // Listener anonyme pour alerter sur les gros montants
        bankService.addListener(new BankService.TransactionListener() {
            @Override
            public void onTransaction(Transaction tx) {
                if (tx.getMontant() > 1000) {
                    System.out.println("⚠️ Alerte gros montant : " + tx);
                }
            }
        });
        
        Client dupont = bankService.creerClient("Dupont", "Jean", "jean.dupont@example.com", "+33612345678");
        
        CompteCourant crDupont = null;
        
        try {
        	 crDupont = (CompteCourant) bankService.creerCompte(dupont.getId(), TypeCompte.PEL);
        } catch (NullPointerException ex) {
        	System.out.print("erreur de creation");
        } catch (TypeInvalideException ex) {
        	System.out.print("erreur de creation");
        }  finally {
        	if (crDupont == null) {
        		return;
        	}
        }
        

        // Dépôts/retraits déclenchent les notifications
        bankService.deposer(crDupont.getNumeroCompte(), 1500.0);
        
        
        List<Transaction> toutesTx = bankService.listerTransactions(crDupont.getNumeroCompte());

	     // 1) Filtrer et obtenir les résumés des transactions > 100
	     List<TransactionSummary> grandesTx = bankService.filtrerEtMapper(toutesTx, 100.0);
	     grandesTx.forEach(System.out::println);  // vérifiez que chaque montant > 100
	
	     // 2) Calculer le solde à partir de l’historique complet
	     double soldeCalcule = bankService.calculerSolde(toutesTx);
	     System.out.println("Solde calculé via stream : " + soldeCalcule);
	     
	     
	     for (TypeCompte t : TypeCompte.values()) {
	            System.out.printf("%s → %s%n", t, CompteUtils.labelTypeCompte(t));
	        }
	     
	     // Récupérer l’historique complet d’un compte
	        String numCompte = crDupont.getNumeroCompte();
	        List<Transaction> historique = bankService.listerTransactions(numCompte);

	        // Générer et afficher le rapport
	        Report rapport = ((BankService) bankService).syntheseTransactions(historique);
	        System.out.println("=== Rapport Transactions pour le compte " + numCompte + " ===");
	        System.out.println(rapport);
    }
}
