package com.m2i.compte;

public interface InteretCalculable {

    double calculerInteret();

    default void afficherInteret() {
        System.out.println("Intérêt calculé : " + calculerInteret());
    }

    private static double tauxParDefaut() {
        return 0.02;
    }

    static double getTauxParDefaut() {
        return tauxParDefaut();
    }
}
