package com.m2i.service;


import java.util.List;

import com.m2i.compte.Compte;
import com.m2i.transaction.Transaction;
import com.m2i.transaction.TypeTransaction;

public abstract class AbstractTransactionService {

    // Méthode commune à tous les services
    public Transaction executerTransaction(Compte compte, double montant, TypeTransaction type) {
        if (type == TypeTransaction.CREDIT) {
            compte.deposer(montant);
        } else {
            compte.retirer(montant);
        }
        return creerTransaction(compte, montant, type);
    }

    // Méthode à implémenter
    protected abstract Transaction creerTransaction(Compte compte, double montant, TypeTransaction type);

    // Facultatif : méthode utilitaire
    public abstract List<Transaction> listerTransactions(Compte compte);
}

