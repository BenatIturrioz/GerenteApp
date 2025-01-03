package com.example.gerenteapp;

import javafx.beans.property.*;

import java.sql.*;
import java.time.LocalDate;

public class Erreserba {

    private final IntegerProperty id;
    private final IntegerProperty erreserba_id;
    private final StringProperty mahaia_id;
    private final IntegerProperty langilea_id;
    private final StringProperty bezeroIzena;
    private final StringProperty telf;
    private final ObjectProperty<LocalDate> data;
    private final StringProperty bezroKop;

    public Erreserba(int id, int erreserba_id, String mahaia_id, int langilea_id, String bezeroIzena, String telf, LocalDate data, String bezroKop) {
        this.id = new SimpleIntegerProperty(id);
        this.erreserba_id = new SimpleIntegerProperty(erreserba_id);
        this.mahaia_id = new SimpleStringProperty(mahaia_id);
        this.langilea_id = new SimpleIntegerProperty(langilea_id);
        this.bezeroIzena = new SimpleStringProperty(bezeroIzena);
        this.telf = new SimpleStringProperty(telf);
        this.data = new SimpleObjectProperty<>(data);
        this.bezroKop = new SimpleStringProperty(bezroKop);
    }

    // id
    public int getId() {return id.get();}

    public void setId(int id) {this.id.set(id);}

    public IntegerProperty idProperty() {return id;}

    // erreserba_id
    public int getErreserba_id() {return erreserba_id.get();}

    public void setErreserba_id(int erreserba_id) {this.erreserba_id.set(erreserba_id);}

    public IntegerProperty erreserba_idProperty() {return erreserba_id;}

    // mahaia_id
    public String getMahaia_id() {return mahaia_id.get();}

    public void setMahaia_id(String mahaia_id) {this.mahaia_id.set(mahaia_id);}

    public StringProperty mahaia_idProperty() {return mahaia_id;}

    // langilea_id
    public int getLangilea_id() {return langilea_id.get();}

    public void setLangilea_id(int langilea_id) {this.langilea_id.set(langilea_id);}

    public IntegerProperty langilea_idProperty() {return langilea_id;}

    // bezeroIzena
    public String getBezeroIzena() {return bezeroIzena.get();}

    public void setBezeroIzena(String bezeroIzena) {this.bezeroIzena.set(bezeroIzena);}

    public StringProperty bezeroIzenaProperty() {return bezeroIzena;}

    // telf
    public String getTelf() {return telf.get();}

    public void setTelf(String telf) {this.telf.set(telf);}

    public StringProperty telfProperty() {return telf;}

    // data
    public LocalDate getData() {return data.get();}

    public void setData(LocalDate data) {this.data.set(data);}

    public ObjectProperty<LocalDate> dataProperty() {return data;}

    // bezroKop
    public String getBezroKop() {return bezroKop.get();}

    public void setBezroKop(String bezroKop) {this.bezroKop.set(bezroKop);}

    public StringProperty bezroKopProperty() {return bezroKop;}

}






















