package com.m2i.client;


import com.m2i.utils.IdUtil;

public class Client {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String username;
    private String password;

    public Client() {
        this.id = IdUtil.generateId();
    }

    public Client(String nom, String prenom, String email, String telephone) {
        this();  // génère l’ID via this()
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // Getters / Setters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    @Override
    public String toString() {
        return "Client{" +
               "id='" + id + '\'' +
               ", nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               '}';
    }
}

