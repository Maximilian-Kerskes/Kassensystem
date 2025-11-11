package com.kassensystem.fachkonzept;

import java.sql.Timestamp;

public class Position {
	private int einkaufsNummer;
	private int produktNummer;
	private Timestamp zeitpunkt;
	private int menge;

	public Position(int pEinkaufsNummer, int pProduktNummer, Timestamp pZeitpunkt, int pMenge) {
		this.produktNummer = pProduktNummer;
		this.einkaufsNummer = pEinkaufsNummer;
		this.zeitpunkt = pZeitpunkt;
		this.menge = pMenge;
	}

	public int getEinkaufsnummer() {
		return einkaufsNummer;
	}

	public int getProduktnummer() {
		return produktNummer;
	}

	public Timestamp getZeitpunkt() {
		return zeitpunkt;
	}

	public int getMenge() {
		return menge;
	}

}
