package com.m2i.transaction;


import java.time.LocalDateTime;

import com.m2i.compte.Compte;
import com.m2i.utils.IdUtil;

public class Transaction {
    private String id;
    private LocalDateTime date;
    private double montant;
    private TypeTransaction type;
    private Compte compte;

    public Transaction() {
        this.id = IdUtil.generateId();
        this.date = LocalDateTime.now();
    }

    public Transaction(double montant, TypeTransaction type, Compte compte) {
        this();  // surcharge
        this.montant = montant;
        this.type = type;
        this.compte = compte;
        // Exécute immédiatement la transaction
        if (type == TypeTransaction.CREDIT) {
            compte.deposer(montant);
        } else {
            compte.retirer(montant);
        }
    }

    // Getters / Setters
    public String getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public TypeTransaction getType() { return type; }
    public void setType(TypeTransaction type) { this.type = type; }
    public Compte getCompte() { return compte; }
    public void setCompte(Compte compte) { this.compte = compte; }

    @Override
    public String toString() {
        return "Transaction{" +
               "id='" + id + '\'' +
               ", date=" + date +
               ", montant=" + montant +
               ", type=" + type +
               '}';
    }
}

