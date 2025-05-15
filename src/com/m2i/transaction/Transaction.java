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
    public double solde;
    
    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public TypeTransaction getType() {
		return type;
	}

	public void setType(TypeTransaction type) {
		this.type = type;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public Transaction(double montant, TypeTransaction type, Compte compte) {
        this.id     = IdUtil.generateId();
        this.date   = LocalDateTime.now();
        this.montant = montant;
        this.type   = type;
        this.compte = compte;

        // Mise à jour du solde via apply()
        double nouveauSolde = type.apply(compte.getSolde(), montant);
        solde = nouveauSolde;
    }

    // … getters / setters restants …

    @Override
    public String toString() {
        return String.format(
            "Transaction{id=%s, date=%s, montant=%.2f, type=%s, soldeAprès=%.2f}",
            id, date, montant, type.getCode(), compte.getSolde()
        );
    }

}

