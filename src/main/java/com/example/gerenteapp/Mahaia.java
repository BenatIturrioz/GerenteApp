package com.example.gerenteapp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class Mahaia {


    private final IntegerProperty mahaia_id;
    private final IntegerProperty zenbakia;
    private final IntegerProperty bezeroKopMax;


    public Mahaia(int mahaiaId, int zenbakia, int bezeroKopMax) {
        this.mahaia_id = new SimpleIntegerProperty(mahaiaId);
        this.zenbakia = new SimpleIntegerProperty(zenbakia);
        this.bezeroKopMax = new SimpleIntegerProperty(bezeroKopMax);
    }


    public int getMahaia_id() {return mahaia_id.get();}
    public void setMahaia_id(int mahaia_id) {this.mahaia_id.set(mahaia_id);}
    public IntegerProperty mahaia_idProperty() {return mahaia_id;}

    public int getZenbakia() {return zenbakia.get();}
    public void setZenbakia(int zenbakia) {this.zenbakia.set(zenbakia);}
    public IntegerProperty zenbakiaProperty() {return zenbakia;}

    public int getBezeroKopMax() {return bezeroKopMax.get();}
    public void setBezeroKopMax(int bezeroKopMax) {this.bezeroKopMax.set(bezeroKopMax);}
    public IntegerProperty bezeroKopMaxProperty() {return bezeroKopMax;}
}
