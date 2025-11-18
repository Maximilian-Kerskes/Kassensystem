package com.kassensystem.fachkonzept;

import java.sql.Timestamp;

public class Position {
    private int einkaufsnummer;
    private String produktnummer;
    private Timestamp zeitpunkt;
    private int menge;

    public Position() {}

    public Position(int einkaufsnummer, String produktnummer, Timestamp zeitpunkt, int menge) {
        this.einkaufsnummer = einkaufsnummer;
        this.produktnummer = produktnummer;
        this.zeitpunkt = zeitpunkt;
        this.menge = menge;
    }

    public int getEinkaufsnummer() { return einkaufsnummer; }
    public void setEinkaufsnummer(int einkaufsnummer) { this.einkaufsnummer = einkaufsnummer; }

    public String getProduktnummer() { return produktnummer; }
	
    public void setProduktnummer(String produktnummer) { this.produktnummer = produktnummer; }

    public Timestamp getZeitpunkt() { return zeitpunkt; }
    public void setZeitpunkt(Timestamp zeitpunkt) { this.zeitpunkt = zeitpunkt; }

    public int getMenge() { return menge; }
    public void setMenge(int menge) { this.menge = menge; }
}
