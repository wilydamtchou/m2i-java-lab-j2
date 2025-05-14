package com.m2i.application;

import com.m2i.client.Client;
import com.m2i.compte.Compte;
import com.m2i.compte.CompteCourant;
import com.m2i.compte.CompteEpargne;
import com.m2i.compte.TypeCompte;
import com.m2i.compte.ICompte;
import com.m2i.service.IBankService;
import com.m2i.service.BankService;

public class M2iBankApplication {
	public static void main (String[] args) {
		IBankService bankService = new BankService();
		
		System.out.println("Welcome to M2I Bank...");
		
		System.out.println("Chargement des clients...");
		
		System.out.println("Dupont Jean");
		Client dupont = bankService.creerClient ("Dupont", "Jean", "jean.dupont@example.com", "+33612345678");
		System.out.println("Chargement des comptes");
		CompteCourant crDupont = (CompteCourant) bankService.creerCompte(dupont.getId(), TypeCompte.COURANT);
		Compte epDupont =  bankService.creerCompte(dupont.getId(), TypeCompte.EPARGNE);
		
		System.out.println("John Doe");
		Client john = bankService.creerClient ("John", "Doe", "john.doe@example.com", "+33655345679");
		System.out.println("Chargement des comptes");
		CompteCourant crJohn = (CompteCourant) bankService.creerCompte(john.getId(), TypeCompte.COURANT);
		Compte epJohn = bankService.creerCompte(john.getId(), TypeCompte.EPARGNE);

        bankService.deposer(crJohn.getNumeroCompte(), 1000.0);
        bankService.deposer(crDupont.getNumeroCompte(), 1500.0);
        
        crJohn.afficherDetails();
        crDupont.afficherDetails();
        
	}

}
