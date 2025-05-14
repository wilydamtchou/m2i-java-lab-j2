package com.m2i.service;

import java.util.*;

import com.m2i.client.Client;
import com.m2i.compte.Compte;
import com.m2i.compte.TypeCompte;
import com.m2i.dto.CompteDTO;
import com.m2i.transaction.Transaction;
import com.m2i.transaction.TypeTransaction;

public interface IBankService {
    /** UC1 : Créer un client */
    public abstract Client creerClient(String nom, String prenom, String email, String telephone);

    /** UC2 : Créer un compte pour un client */
    public abstract Compte creerCompte(String clientId, TypeCompte type);

    /** UC3 : Consulter la liste des comptes (retourne des DTO) */
    public abstract List<CompteDTO> listerComptes(String clientId);

    /** UC4 : Effectuer une transaction (dépôt ou retrait) */
    public abstract Transaction effectuerTransaction(
            String numeroCompte,
            TypeTransaction type,
            double montant);

    /** UC5 : Afficher les transactions d’un compte */
    public List<Transaction> listerTransactions(String numeroCompte);
    
    /** UC4-bis : Déposer de l'argent */
    public Transaction deposer(String numeroCompte, double montant);

    /** UC4-ter : Retirer de l'argent */
    public Transaction retirer(String numeroCompte, double montant);
}

