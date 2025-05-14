package com.m2i.compte;

import com.m2i.client.Client;

public class CompteCourant extends Compte {

    private double decouvertAutorise;

    public CompteCourant(Client proprietaire, double decouvertAutorise) {
        super(proprietaire, TypeCompte.COURANT);
        this.decouvertAutorise = decouvertAutorise;
    }

    public double getDecouvertAutorise() {
        return decouvertAutorise;
    }

    public void setDecouvertAutorise(double decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public void retirer(double montant) {
        if (montant > 0 && this.getSolde() + decouvertAutorise >= montant) {
            super.retirer(montant);
        }
    }
    
  // Affichage personnalisé
    public void afficherDetails() {
        System.out.println("Compte courant #" + this.getNumeroCompte());
        System.out.println("Solde: " + this.getSolde());
    }
}

