package com.kassensystem.steuerung;

import java.sql.SQLException;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Produkt;

public class Steuerung {
	private Datenbank dieDatenbank;

	public Steuerung() {
		try {
			dieDatenbank = new Datenbank();
		} catch (Exception e) {
			System.out.println("Fehler beim initialisieren der Datenbank: " + System.lineSeparator()
					+ e.getLocalizedMessage());
		}
	}

	// TODO
	// migrate to actual GUI accurate tests
	public int rueckGeldEvent(int produktNummer, int bezahlterBetrag) {
		try {
			Produkt dasProdukt = dieDatenbank.fetchProdukt(produktNummer);
			return rueckgeldBerechnen(bezahlterBetrag, dasProdukt.getVerkaufspreis());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	private int rueckgeldBerechnen(int bezahlterBetrag, int verkaufspreis) {
		return Math.abs(verkaufspreis - bezahlterBetrag);
	}
}
