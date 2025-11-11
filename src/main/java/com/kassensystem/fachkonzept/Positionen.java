package com.kassensystem.fachkonzept;

import java.sql.Timestamp;

public class Positionen
{
    private int einkaufsNummer;
    private int produktNummer;
    private Timestamp zeitpunkt;
    private int menge;
    private Boolean bezahlt;


    public Positionen(int pEinkaufsNummer, int pProduktNummer, Timestamp pZeitpunkt, int pMenge)
    {
        this.produktNummer = pProduktNummer;
		this.einkaufsNummer = pEinkaufsNummer;
		this.zeitpunkt = pZeitpunkt;
		this.menge = pMenge;
        this.bezahlt = false;
    }

    public int getEinkaufsnummer()
    {
        return einkaufsNummer;
    }
    public int getProduktnummer()
    {
        return produktNummer;
    }
    public Timestamp getZeitpunkt()
    {
        return zeitpunkt;
    }
    public int getMenge()
    {
        return menge;
    }

}