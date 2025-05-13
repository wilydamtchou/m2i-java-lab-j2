package com.m2i.compte;


import java.time.LocalDate;

import com.m2i.client.Client;
import com.m2i.utils.IdUtil;

public class Compte {
    private String numeroCompte;
    private double solde;
    private LocalDate dateCreation;
    private TypeCompte typeCompte;
    private Client proprietaire;

    public Compte() {
        this.numeroCompte = IdUtil.generateId();
        this.dateCreation = LocalDate.now();
        this.solde = 0.0;
    }

    public Compte(Client proprietaire, TypeCompte typeCompte) {
        this();  // surcharge : initialise ID, date, solde
        this.proprietaire = proprietaire;
        this.typeCompte = typeCompte;
    }

    // Use-case: dépôt
    public void deposer(double montant) {
        if (montant > 0) {
            this.solde += montant;
        }
    }

    // Use-case: retrait
    public void retirer(double montant) {
        if (montant > 0 && this.solde >= montant) {
            this.solde -= montant;
        }
    }

    // Getters / Setters
    public String getNumeroCompte() { return numeroCompte; }
    public double getSolde() { return solde; }
    public LocalDate getDateCreation() { return dateCreation; }
    public TypeCompte getTypeCompte() { return typeCompte; }
    public void setTypeCompte(TypeCompte typeCompte) { this.typeCompte = typeCompte; }
    public Client getProprietaire() { return proprietaire; }
    public void setProprietaire(Client proprietaire) { this.proprietaire = proprietaire; }

    @Override
    public String toString() {
        return "Compte{" +
               "numeroCompte='" + numeroCompte + '\'' +
               ", solde=" + solde +
               ", type=" + typeCompte +
               '}';
    }
}
