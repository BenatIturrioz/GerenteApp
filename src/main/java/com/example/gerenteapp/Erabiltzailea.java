package com.example.gerenteapp;

import javafx.beans.property.*;

public class Erabiltzailea {
    private final IntegerProperty id;
    private final StringProperty erabiltzaileIzena;
    private final StringProperty pasahitza;
    private final IntegerProperty langilea_id;
    private final IntegerProperty langilea_mota;
    private final BooleanProperty txatBaimena;

    // Constructor
    public Erabiltzailea(int id, String erabiltzaileIzena, String pasahitza, int langilea_id, int langilea_mota, boolean txatBaimena){
        this.id = new SimpleIntegerProperty(id);
        this.erabiltzaileIzena = new SimpleStringProperty(erabiltzaileIzena);
        this.pasahitza = new SimpleStringProperty(pasahitza);
        this.langilea_id = new SimpleIntegerProperty(langilea_id);
        this.langilea_mota = new SimpleIntegerProperty(langilea_mota);
        this.txatBaimena = new SimpleBooleanProperty(txatBaimena);
    }

    // Getters y setters observables
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getErabiltzaileIzena() { return erabiltzaileIzena.get(); }
    public void setErabiltzaileIzena(String erabiltzaileIzena) { this.erabiltzaileIzena.set(erabiltzaileIzena); }
    public StringProperty erabiltzaileIzenaProperty() { return erabiltzaileIzena; }

    public String getPasahitza() { return pasahitza.get(); }
    public void setPasahitza(String pasahitza) { this.pasahitza.set(pasahitza); }
    public StringProperty pasahitzaProperty() { return pasahitza; }

    public Integer getLangilea_id() { return langilea_id.get(); }
    public void setLangilea_id(int langilea_id) { this.langilea_id.set(langilea_id); }
    public IntegerProperty langileIdProperty() { return langilea_id; }

    public Integer getLangilea_mota() { return langilea_mota.get(); }
    public void setLangilea_mota(int langilea_mota) { this.langilea_mota.set(langilea_mota); }
    public IntegerProperty langileMotaProperty() { return langilea_mota; }

    public Boolean getTxatBaimena() { return txatBaimena.get(); }
    public void setTxatBaimena(boolean txatBaimena) { this.txatBaimena.set(txatBaimena); }
    public BooleanProperty txatBaimenaProperty() { return txatBaimena; }

}
