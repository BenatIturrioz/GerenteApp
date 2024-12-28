package com.example.gerenteapp;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Langilea {

    private final IntegerProperty id;
    private final StringProperty dni;
    private final StringProperty izena;
    private final StringProperty abizena;
    private final StringProperty probintzia;
    private final StringProperty pk;
    private final StringProperty herria;
    private final StringProperty helbidea;
    private final StringProperty emaila;
    private final StringProperty telf;
    private final StringProperty kontuKorrontea;
    private final ObjectProperty<LocalDate> jaiotzeData;
    private final IntegerProperty mota;

    // Constructor
    public Langilea(int id, String dni, String izena, String abizena, String probintzia, String pk, String herria,
                    String helbidea, String emaila, String telf, String kontuKorrontea, LocalDate jaiotzeData, int mota) {
        this.id = new SimpleIntegerProperty(id);
        this.dni = new SimpleStringProperty(dni);
        this.izena = new SimpleStringProperty(izena);
        this.abizena = new SimpleStringProperty(abizena);
        this.probintzia = new SimpleStringProperty(probintzia);
        this.pk = new SimpleStringProperty(pk);
        this.herria = new SimpleStringProperty(herria);
        this.helbidea = new SimpleStringProperty(helbidea);
        this.emaila = new SimpleStringProperty(emaila);
        this.telf = new SimpleStringProperty(telf);
        this.kontuKorrontea = new SimpleStringProperty(kontuKorrontea);
        this.jaiotzeData = new SimpleObjectProperty<>(jaiotzeData);
        this.mota = new SimpleIntegerProperty(mota);
    }

    // Getters y setters observables
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getDni() { return dni.get(); }
    public void setDni(String dni) { this.dni.set(dni); }
    public StringProperty dniProperty() { return dni; }

    public String getIzena() { return izena.get(); }
    public void setIzena(String izena) { this.izena.set(izena); }
    public StringProperty izenaProperty() { return izena; }

    public String getAbizena() { return abizena.get(); }
    public void setAbizena(String abizena) { this.abizena.set(abizena); }
    public StringProperty abizenaProperty() { return abizena; }

    public String getProbintzia() { return probintzia.get(); }
    public void setProbintzia(String probintzia) { this.probintzia.set(probintzia); }
    public StringProperty probintziaProperty() { return probintzia; }

    public String getPk() { return pk.get(); }
    public void setPk(String pk) { this.pk.set(pk); }
    public StringProperty pkProperty() { return pk; }

    public String getHerria() { return herria.get(); }
    public void setHerria(String herria) { this.herria.set(herria); }
    public StringProperty herriaProperty() { return herria; }

    public String getHelbidea() { return helbidea.get(); }
    public void setHelbidea(String helbidea) { this.helbidea.set(helbidea); }
    public StringProperty helbideaProperty() { return helbidea; }

    public String getEmaila() { return emaila.get(); }
    public void setEmaila(String emaila) { this.emaila.set(emaila); }
    public StringProperty emailaProperty() { return emaila; }

    public String getTelf() { return telf.get(); }
    public void setTelf(String telf) { this.telf.set(telf); }
    public StringProperty telfProperty() { return telf; }

    public String getKontuKorrontea() { return kontuKorrontea.get(); }
    public void setKontuKorrontea(String kontuKorrontea) { this.kontuKorrontea.set(kontuKorrontea); }
    public StringProperty kontuKorronteaProperty() { return kontuKorrontea; }

    public LocalDate getJaiotzeData() { return jaiotzeData.get(); }
    public void setJaiotzeData(LocalDate jaiotzeData) { this.jaiotzeData.set(jaiotzeData); }
    public ObjectProperty<LocalDate> jaiotzeDataProperty() { return jaiotzeData; }

    public int getMota() { return mota.get(); }
    public void setMota(int mota) { this.mota.set(mota); }
    public IntegerProperty motaProperty() { return mota; }
}


