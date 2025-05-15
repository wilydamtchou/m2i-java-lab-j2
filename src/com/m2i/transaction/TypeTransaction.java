package com.m2i.transaction;

public enum TypeTransaction {
	CREDIT("CR", +1, "Compte Courant"),
    DEBIT ("DB", -1, "Compte Epargne"),
    PEL ("DB", -1, "Plan Epargne Logement");

    private final String code;
    private final int sens;
    private final String label;

    TypeTransaction(String code, int sens, String label) {
        this.code  = code;
        this.sens  = sens;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public int getSens() {
        return sens;
    }
    
    public String getLabel() {
        return label;
    }

    /**
     * Applique la transaction sur un solde donné.
     * @param solde   solde initial
     * @param montant montant de la transaction
     * @return nouveau solde
     */
    public double apply(double solde, double montant) {
        return solde + sens * montant;
    }

    @Override
    public String toString() {
        return name() + "(" + code + ", sens=" + sens + ")";
    }
}

