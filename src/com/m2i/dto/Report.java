package com.m2i.dto;

public class Report {
    private final long nombreTransactions;
    private final double totalCredit;
    private final double totalDebit;

    public Report(long nombreTransactions, double totalCredit, double totalDebit) {
        this.nombreTransactions = nombreTransactions;
        this.totalCredit      = totalCredit;
        this.totalDebit       = totalDebit;
    }

    public long getNombreTransactions() { return nombreTransactions; }
    public double getTotalCredit()      { return totalCredit; }
    public double getTotalDebit()       { return totalDebit; }

    @Override
    public String toString() {
        return String.format(
            "Nombre de transactions : %d%n" +
            "Total crédits        : %.2f%n" +
            "Total débits         : %.2f%n" +
            "Solde net            : %.2f",
            nombreTransactions,
            totalCredit,
            totalDebit,
            totalCredit - totalDebit
        );
    }
}
