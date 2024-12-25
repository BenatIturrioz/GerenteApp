package com.example.gerenteapp;

import java.sql.*;

public class Erreserba {

    private int id;
    private int erreserba_id;
    private int mahaia_id;
    private int langilea_id;
    private String bezeroIzena;
    private int telf;
    private java.sql.Date data;
    private int bezroKop;

    public Erreserba(int id, int erreserba_id, int mahaia_id, int langilea_id, String bezeroIzena, int telf, java.sql.Date, int bezroKop) {
        this.id = id;
        this.erreserba_id = erreserba_id;
        this.mahaia_id = mahaia_id;
        this.langilea_id = langilea_id;
        this.bezeroIzena = bezeroIzena;
        this.telf = telf;
        this.data = data;
        this.bezroKop = bezroKop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getErreserba_id() {
        return erreserba_id;
    }

    public void setErreserba_id(int erreserba_id) {
        this.erreserba_id = erreserba_id;
    }

    public int getMahaia_id() {
        return mahaia_id;
    }

    public void setMahaia_id(int mahaia_id) {
        this.mahaia_id = mahaia_id;
    }

    public int getLangilea_id() {
        return langilea_id;
    }

    public void setLangilea_id(int langilea_id) {
        this.langilea_id = langilea_id;
    }

    public String getBezeroIzena() {
        return bezeroIzena;
    }

    public void setBezeroIzena(String bezeroIzena) {
        this.bezeroIzena = bezeroIzena;
    }

    public int getTelf() {
        return telf;
    }

    public void setTelf(int telf) {
        this.telf = telf;
    }

    public String getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getBezroKop() {
        return bezroKop;
    }

    public void setBezroKop(int bezroKop) {
        this.bezroKop = bezroKop;
    }

}










