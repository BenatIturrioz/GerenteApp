package com.example.gerenteapp;

public class Erabiltzailea {


    private int id;
    private String erabiltzaile_izena;
    private String pasahitza;
    private int id_langilea;

    public Erabiltzailea(int id, String erabiltzaile_izena, String pasahitza, int id_langilea) {
        this.id = id;
        this.erabiltzaile_izena = erabiltzaile_izena;
        this.pasahitza = pasahitza;
        this.id_langilea = id_langilea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getErabiltzaile_izena() {
        return erabiltzaile_izena;
    }

    public void setErabiltzaile_izena(String erabiltzaile_izena) {
        this.erabiltzaile_izena = erabiltzaile_izena;
    }

    public String getPasahitza() {
        return pasahitza;
    }

    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public int getId_langilea() {
        return id_langilea;
    }

    public void setId_langilea(int id_langilea) {
        this.id_langilea = id_langilea;
    }
}
