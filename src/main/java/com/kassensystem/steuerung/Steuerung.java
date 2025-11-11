package com.kassensystem.steuerung;

import java.sql.SQLException;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Produkt;

public class Steuerung {
	private Datenbank dieDatenbank;
	private int einkaufsnummer;

	public Steuerung() {
		try {
			dieDatenbank = new Datenbank();
		} catch (Exception e) {
			System.out.println("Fehler beim initialisieren der Datenbank: " + System.lineSeparator()
					+ e.getLocalizedMessage());
		}
	}

	public void bestandUpdateEvent(int produktNummer) {
		try {
			Produkt dasProdukt = dieDatenbank.fetchProdukt(produktNummer);
			double bestand = dasProdukt.getBestand();
			if (bestand <= 0) {
				dieDatenbank.setBestand(dasProdukt, 0);
				return;
			}
			dieDatenbank.setBestand(dasProdukt, bestand - 1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// TODO
	// migrate to actual GUI accurate tests
	public double rueckGeldEvent(int produktNummer, double bezahlterBetrag) {
		try {
			Produkt dasProdukt = dieDatenbank.fetchProdukt(produktNummer);
			return rueckgeldBerechnen(bezahlterBetrag, dasProdukt.getVerkaufspreis());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	private double rueckgeldBerechnen(double bezahlterBetrag, double verkaufspreis) {
		return Math.abs(verkaufspreis - bezahlterBetrag);
	}

	public void scannedProdukt(Produkt pProdukt, int pAnzahl) {
		try {
			dieDatenbank.addProdukt(pProdukt, pAnzahl, einkaufsnummer);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void changeMenge(Produkt pProdukt, int pMenge) {
		try {
			dieDatenbank.changePosition(pProdukt, pMenge, einkaufsnummer);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void deleteProdukt(Produkt pProdukt) {
		try {
			dieDatenbank.deletePosition(pProdukt, einkaufsnummer);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public int getEinkaufsnummer() {
		try {
			return dieDatenbank.fetchEinkaufsNummer();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return -1;
		}
	}

}
