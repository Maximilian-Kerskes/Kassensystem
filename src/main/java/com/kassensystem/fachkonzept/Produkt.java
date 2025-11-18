package com.kassensystem.fachkonzept;

public class Produkt {
	private String produktNummer;
	private String bezeichnung;
	private double verkaufspreis;
	private double bestand;

	public Produkt(String produktNummer, String bezeichnung, double verkaufspreis, double bestand) {
		this.produktNummer = produktNummer;
		this.bezeichnung = bezeichnung;
		this.verkaufspreis = verkaufspreis;
		this.bestand = bestand;
	}

	public String getProduktNummer() {
		return produktNummer;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public double getVerkaufspreis() {
		return verkaufspreis;
	}

	public double getBestand() {
		return bestand;
	}

	public String getProduktAsString() {
		String produkt = getBezeichnung();
		produkt += " - " + getVerkaufspreis();
		produkt += " - " + getBestand();
		return produkt;
	}
}
