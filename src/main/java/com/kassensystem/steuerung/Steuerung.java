package com.kassensystem.steuerung;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.datenspeicherung.pdf.Bon;
import com.kassensystem.datenspeicherung.print.Printer;
import com.kassensystem.fachkonzept.Bestandsveraenderung;
import com.kassensystem.fachkonzept.Kasse;
import com.kassensystem.fachkonzept.Position;
import com.kassensystem.fachkonzept.Produkt;

public class Steuerung {
	private Datenbank dieDatenbank;
	private int einkaufsnummer;

	public Steuerung() {
		try {
			dieDatenbank = new Datenbank();
		} catch (Exception e) {
			System.out.println("Fehler beim Initialisieren der Datenbank: "
					+ System.lineSeparator() + e.getLocalizedMessage());
		}
	}

	public void bestandUpdateEvent(String produktNummer) {
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


	public void kasseOeffnen() throws IOException {
		Kasse dieKasse = new Kasse();
		dieKasse.openKasse();
	}

	public void bonDrucken(List<Position> positionen, double gegebenesGeld) throws PrinterException {
			Bon derBon = new Bon(dieDatenbank);
			PDDocument bon = derBon.createBon(positionen, gegebenesGeld);
			Printer derPrinter = new Printer();
			derPrinter.printDocument(bon);
	}

	public double rueckgeldBerechnen(double bezahlterBetrag, double verkaufspreis) {
		return Math.abs(verkaufspreis - bezahlterBetrag);
	}

	public void scannedProdukt(Produkt pProdukt) {
		try {
			dieDatenbank.createProdukt(
					pProdukt.getProduktNummer(),
					pProdukt.getBezeichnung(),
					pProdukt.getVerkaufspreis(),
					pProdukt.getBestand());
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

	public void deletePosition(Produkt pProdukt, int produkt) {
		try {
			dieDatenbank.deletePosition(pProdukt, einkaufsnummer);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void logProdukt(Produkt existierendesProdukt, Produkt neuesProdukt) {
		try {
			if (!existierendesProdukt.getBezeichnung().equals(neuesProdukt.getBezeichnung())) {
				dieDatenbank.logProduktVeraenderung(existierendesProdukt.getProduktNummer(),
						"Bezeichnung",
						existierendesProdukt.getBezeichnung(), neuesProdukt.getBezeichnung());
			}
			if (existierendesProdukt.getVerkaufspreis() != neuesProdukt.getVerkaufspreis()) {
				dieDatenbank.logProduktVeraenderung(existierendesProdukt.getProduktNummer(),
						"Verkaufspreis",
						String.valueOf(existierendesProdukt.getVerkaufspreis()),
						String.valueOf(neuesProdukt.getVerkaufspreis()));
			}
			if (existierendesProdukt.getBestand() != neuesProdukt.getBestand()) {
				dieDatenbank.logProduktVeraenderung(existierendesProdukt.getProduktNummer(),
						"Bestand",
						String.valueOf(existierendesProdukt.getBestand()),
						String.valueOf(neuesProdukt.getBestand()));
			}
			if (existierendesProdukt.getArchiviert() != neuesProdukt.getArchiviert()) {
				dieDatenbank.logProduktVeraenderung(existierendesProdukt.getProduktNummer(),
						"Archiviert",
						String.valueOf(existierendesProdukt.getArchiviert()),
						String.valueOf(neuesProdukt.getArchiviert()));
			}

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

	public List<Produkt> getProdukte() {
		try {
			return dieDatenbank.fetchProdukte();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<Position> getEinkaeufe() {
		try {
			return dieDatenbank.fetchEinkaeufe();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Produkt getProdukt(String produktnr) {
		try {
			return dieDatenbank.fetchProdukt(produktnr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createProdukt(Produkt produkt) {
		try {
			dieDatenbank.createProdukt(
					produkt.getProduktNummer(),
					produkt.getBezeichnung(),
					produkt.getVerkaufspreis(),
					produkt.getBestand());
		} catch (SQLException e) {
			System.out.println("Fehler beim Erstellen des Produkts: " + e.getMessage());
		}
	}

	public void updateProdukt(Produkt produkt) {
		try {
			dieDatenbank.updateProdukt(
					produkt.getProduktNummer(),
					produkt.getBezeichnung(),
					produkt.getVerkaufspreis(),
					produkt.getBestand(),
					produkt.getArchiviert());
		} catch (SQLException e) {
			System.out.println("Fehler beim Aktualisieren des Produkts: " + e.getMessage());
		}
	}

	public void archiveProduktById(String produktnr, boolean archiviert) {
		try {
			dieDatenbank.archiveProduktById(produktnr, archiviert);
		} catch (SQLException e) {
			System.out.println("Fehler beim Löschen des Produkts: " + e.getMessage());
		}
	}

	public void createPosition(Position position) {
		try {
			dieDatenbank.createPosition(
					position.getEinkaufsnummer(),
					position.getProduktnummer(),
					position.getMenge(),
					position.getZeitpunkt());
		} catch (SQLException e) {
			System.out.println("Fehler beim Erstellen der Position: " + e.getMessage());
		}
	}

	public int naechsteEinkaufsnummer() {
		int naechsteEinkaufsnummer;
		try {
			naechsteEinkaufsnummer = dieDatenbank.fetchEinkaufsNummer() + 1;
			return naechsteEinkaufsnummer;
		} catch (SQLException e) {
			System.out.println("Fehler beim erstellen der nächsten Einkaufsnummer: " + e.getMessage());
		}
		return -1;
	}

	public String generateBestandslisteCSV() {
		try {
			List<Produkt> produkte = dieDatenbank.fetchProdukte();
			StringBuilder csv = new StringBuilder();
			csv.append("Produktnummer;Bezeichnung;Verkaufspreis;Bestand\n");
			for (Produkt p : produkte) {
				csv.append(p.getProduktNummer())
						.append(";")
						.append(p.getBezeichnung())
						.append(";")
						.append(String.format("%.2f", p.getVerkaufspreis()).replace(".", ","))
						.append(";")
						.append(String.format("%.0f", p.getBestand()))
						.append("\n");
			}
			return csv.toString();
		} catch (SQLException e) {
			System.out.println("Fehler beim Generieren der Bestandsliste: " + e.getMessage());
			return "";
		}
	}

	public String generateProduktLogCSV() {
		try {
			List<Bestandsveraenderung> bestandsveraenderungen = dieDatenbank.fetchBestandsveraenderungen();
			StringBuilder csv = new StringBuilder();
			csv.append("LogNummer;ProduktNummer;Veraendert;Feldname;Alterwert;Neuerwert\n");
			for (Bestandsveraenderung b : bestandsveraenderungen) {
				csv.append(b.getLognr())
						.append(";")
						.append(b.getProduktnr())
						.append(";")
						.append(b.getVeraendert())
						.append(";")
						.append(b.getFeldname())
						.append(";")
						.append(b.getAlterwert())
						.append(";")
						.append(b.getNeuerwert())
						.append("\n");
			}
			return csv.toString();
		} catch (SQLException e) {
			System.out.println("Fehler beim Generieren des Bestandsveraenderungslogs: " + e.getMessage());
			return "";
		}
	}

	public String generateUmsatzCSV(String startDate, String endDate) {
		try {
			System.out.println("Generiere Umsatz CSV für " + startDate + " bis " + endDate);
			List<Object[]> data = dieDatenbank.fetchUmsatzData(startDate, endDate);
			System.out.println("Anzahl Datensätze: " + data.size());
			StringBuilder csv = new StringBuilder();
			csv.append("Produktnummer;Bezeichnung;Absatz;Umsatz\n");
			double totalUmsatz = 0;
			for (Object[] row : data) {
				String produktnr = (String) row[0];
				String bezeichnung = (String) row[1];
				int absatz = (Integer) row[2];
				double umsatz = (Double) row[3];
				totalUmsatz += umsatz;
				csv.append(produktnr)
						.append(";")
						.append(bezeichnung)
						.append(";")
						.append(absatz)
						.append(";")
						.append(String.format("%.2f", umsatz).replace(".", ","))
						.append("\n");
			}
			csv.append("GESAMT;;;" + String.format("%.2f", totalUmsatz).replace(".", ",") + "\n");
			return csv.toString();
		} catch (SQLException e) {
			System.out.println("Fehler beim Generieren der Umsatzliste: " + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
}
