package main.java.com.kassensystem.fachkonzept;

public class Produkt {
	private int produktNummer;
	private String bezeichnung;
	private int verkaufspreis;

	public Produkt(int produktNummer, String bezeichnung, int verkaufspreis) {
		this.produktNummer = produktNummer;
		this.bezeichnung = bezeichnung;
		this.verkaufspreis = verkaufspreis;
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
}
