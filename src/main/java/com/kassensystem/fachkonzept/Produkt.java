package com.kassensystem.fachkonzept;

public class Produkt {
	private int produktNummer;
	private String bezeichnung;
	private int verkaufspreis;
	private double bestand;

	public Produkt(int produktNummer, String bezeichnung, int verkaufspreis, double bestand) {
		this.produktNummer = produktNummer;
		this.bezeichnung = bezeichnung;
		this.verkaufspreis = verkaufspreis;
		this.bestand = bestand;
	}

	public int getProduktNummer() {
		return produktNummer;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public int getVerkaufspreis() {
		return verkaufspreis;
	}

	public double getBestand() {
		return bestand;
	}
}
