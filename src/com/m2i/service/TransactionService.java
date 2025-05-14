package com.m2i.service;

import java.util.*;

import com.m2i.compte.Compte;
import com.m2i.transaction.Transaction;
import com.m2i.transaction.TypeTransaction;

public class TransactionService extends AbstractTransactionService {

    private final Map<String, List<Transaction>> transactions = new HashMap<>();

    @Override
    protected Transaction creerTransaction(Compte compte, double montant, TypeTransaction type) {
        Transaction t = new Transaction(montant, type, compte);
        transactions.computeIfAbsent(compte.getNumeroCompte(), k -> new ArrayList<>()).add(t);
        return t;
    }

    @Override
    public List<Transaction> listerTransactions(Compte compte) {
        return Collections.unmodifiableList(
                transactions.getOrDefault(compte.getNumeroCompte(), new ArrayList<>())
        );
    }
}

