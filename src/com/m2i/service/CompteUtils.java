package com.m2i.service;

import com.m2i.compte.TypeCompte;
import com.m2i.transaction.TypeTransaction;

public class CompteUtils {
	private CompteUtils() { /* empêche l'instanciation */ }

    /**
     * Retourne un libellé humain pour chaque type de compte.
     * Utilise switch à flèches et yield pour plus de concision.
     */
    public static String labelTypeCompte(TypeCompte type) {
    	TypeTransaction t = TypeTransaction.CREDIT;
    	t.getLabel();
    	t.getCode();
        return switch (type) {
            case COURANT -> {
            	System.out.print(false);
            	yield "Compte courant";
        	}
            case EPARGNE -> "Compte épargne";
            case PEL     -> "Plan épargne logement";
            // si un nouveau TypeCompte est ajouté ailleurs, on le signale :
            default -> throw new IllegalStateException("Type de compte inconnu : " + type);
        };
    }

}
