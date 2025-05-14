package com.m2i.compte;

import com.m2i.client.Client;

public class CompteEpargne extends Compte implements InteretCalculable {

    private double tauxInteret;

    public CompteEpargne(Client proprietaire, double tauxInteret) {
        super(proprietaire, TypeCompte.EPARGNE);
        this.tauxInteret = tauxInteret;
    }

    @Override
    public double calculerInteret() {
        return this.getSolde() * tauxInteret;
    }

    // Getters et Setters
    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    // Affichage personnalisé
    public void afficherDetails() {
        System.out.println("Compte épargne #" + this.getNumeroCompte());
        System.out.println("Solde: " + this.getSolde());
        System.out.println("Intérêt attendu: " + calculerInteret());
    }
}
