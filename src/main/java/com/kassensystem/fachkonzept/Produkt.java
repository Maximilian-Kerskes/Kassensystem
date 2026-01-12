package com.kassensystem.fachkonzept;

public class Produkt {
	private String produktNummer;
	private String bezeichnung;
	private double verkaufspreis;
	private Steuersatz steuersatz;
	private double bestand;
	private boolean archiviert;

	public Produkt(String produktNummer, String bezeichnung, double verkaufspreis, Steuersatz steuersatz,
			double bestand, boolean archiviert) {
		this.produktNummer = produktNummer;
		this.bezeichnung = bezeichnung;
		this.verkaufspreis = verkaufspreis;
		this.steuersatz = steuersatz;
		this.bestand = bestand;
		this.archiviert = archiviert;
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

	public Steuersatz getSteuersatz() {
		return steuersatz;
	}

	public double getBestand() {
		return bestand;
	}

	public boolean getArchiviert() {
		return archiviert;
	}

	public String getProduktAsString() {
		String produkt = getBezeichnung();
		produkt += " - " + getVerkaufspreis();
		produkt += " - " + getBestand();
		return produkt;
	}
}
