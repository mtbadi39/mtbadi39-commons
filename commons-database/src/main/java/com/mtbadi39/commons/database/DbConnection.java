package com.mtbadi39.commons.database;

public class DbConnection {

    private int id;
    private String nom;
    private String fichier;
    //private String kk;
    private boolean valide;

    public DbConnection() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    /*
     public String getKk() {
     return kk;
     }

     public void setKk(String kk) {
     this.kk = kk;
     }
     */
    public boolean getValide() {
        return valide;
    }

    public boolean estValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }
}
