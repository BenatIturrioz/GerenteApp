package com.example.gerenteapp;

import java.time.LocalDate;

public class Langilea {

    private int id;
    private String dni;
    private String izena;
    private String abizena;
    private String probintzia;
    private String pk;
    private String herria;
    private String helbidea;
    private String emaila;
    private String telf;
    private String kk;
    private LocalDate jaiotzeData;

    // Constructor
    public Langilea(int id, String dni, String izena, String abizena, String probintzia, String pk, String herria,
                    String helbidea, String emaila, String telf, String kk, LocalDate jaiotzeData) {
        this.id = id;
        this.dni = dni;
        this.izena = izena;
        this.abizena = abizena;
        this.probintzia = probintzia;
        this.pk = pk;
        this.herria = herria;
        this.helbidea = helbidea;
        this.emaila = emaila;
        this.telf = telf;
        this.kk = kk;
        this.jaiotzeData = jaiotzeData;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getIzena() { return izena; }
    public void setIzena(String izena) { this.izena = izena; }

    public String getAbizena() { return abizena; }
    public void setAbizena(String abizena) { this.abizena = abizena; }

    public String getProbintzia() { return probintzia; }
    public void setProbintzia(String probintzia) { this.probintzia = probintzia; }

    public String getPk() { return pk; }
    public void setPk(String pk) { this.pk = pk; }

    public String getHerria() { return herria; }
    public void setHerria(String herria) { this.herria = herria; }

    public String getHelbidea() { return helbidea; }
    public void setHelbidea(String helbidea) { this.helbidea = helbidea; }

    public String getEmaila() { return emaila; }
    public void setEmaila(String emaila) { this.emaila = emaila; }

    public String getTelf() { return telf; }
    public void setTelf(String telf) { this.telf = telf; }

    public String getKk() { return kk; }
    public void setKk(String kk) { this.kk = kk; }

    public LocalDate getJaiotzeData() { return jaiotzeData; }
    public void setJaiotzeData(LocalDate jaiotzeData) { this.jaiotzeData = jaiotzeData; }
}
