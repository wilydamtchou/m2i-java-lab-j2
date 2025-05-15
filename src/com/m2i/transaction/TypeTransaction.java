package com.m2i.transaction;

public enum TypeTransaction {
	CREDIT("CR", +1),
    DEBIT ("DB", -1),
    PEL ("DB", -1);

    private final String code;
    private final int sens;

    TypeTransaction(String code, int sens) {
        this.code  = code;
        this.sens  = sens;
    }

    public String getCode() {
        return code;
    }

    public int getSens() {
        return sens;
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

