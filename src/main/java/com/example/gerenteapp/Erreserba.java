package com.example.gerenteapp;

import javafx.beans.property.*;

import java.sql.*;
import java.time.LocalDate;

public class Erreserba {

    private final IntegerProperty id;
    private final StringProperty mahaia_id;
    private final StringProperty bezeroIzena;
    private final StringProperty telf;
    private final ObjectProperty<LocalDate> data;
    private final StringProperty bezroKop;

    public Erreserba(int id, String mahaia_id, String bezeroIzena, String telf, LocalDate data, String bezroKop) {
        this.id = new SimpleIntegerProperty(id);
        this.mahaia_id = new SimpleStringProperty(mahaia_id);
        this.bezeroIzena = new SimpleStringProperty(bezeroIzena);
        this.telf = new SimpleStringProperty(telf);
        this.data = new SimpleObjectProperty<>(data);
        this.bezroKop = new SimpleStringProperty(bezroKop);
    }

    // id
    public int getId() {return id.get();}

    public void setId(int id) {this.id.set(id);}

    public IntegerProperty idProperty() {return id;}


    // mahaia_id
    public String getMahaia_id() {return mahaia_id.get();}

    public void setMahaia_id(String mahaia_id) {this.mahaia_id.set(mahaia_id);}

    public StringProperty mahaia_idProperty() {return mahaia_id;}


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






















