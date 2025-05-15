package com.m2i.compte;

public interface InteretCalculable {

    abstract double calculerInteret();

    default void afficherInteret() {
        System.out.println("Intérêt calculé : " + calculerInteret());
        tauxParDefaut();
    }

    private static double tauxParDefaut() {
        return 0.02;
    }

    static double getTauxParDefaut() {
    	  tauxParDefaut();
        return tauxParDefaut();
      
    }
}
