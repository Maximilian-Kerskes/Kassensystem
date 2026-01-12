package com.kassensystem.datenspeicherung.pdf;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Position;
import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.fachkonzept.Steuersatz;

// god this sucks

public class Bon {
	private Datenbank db;

	private static final PDType1Font FONT = new PDType1Font(Standard14Fonts.FontName.COURIER);
	private static final int FONT_SIZE = 12;
	private static final float LEADING = 14f;

	private final PDDocument document;
	private final PDPage page;
	private String header;
	private String footer;

	public Bon(Datenbank db) throws PrinterException {
		this.db = db;

		document = new PDDocument();
		page = new PDPage();
		document.addPage(page);
		try {
			header = readFileLineByLine("src/main/resources/header.txt");
			footer = readFileLineByLine("src/main/resources/footer.txt");
		} catch (IOException e) {
			throw new PrinterException(
					"Fehler beim Lesen des Headers und Footers: " + e.getLocalizedMessage());
		}
	}

	private String readFileLineByLine(String path) throws IOException {
		List<String> lines = Files.readAllLines(Path.of(path));
		return String.join("\n", lines);
	}

	public PDDocument createBon(List<Position> positionen, double gegeben) throws PrinterException {
		try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

			float pageWidth = page.getMediaBox().getWidth();

			// Width of receipt block (~40 chars in Courier)
			String measure = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
			float blockWidth = FONT.getStringWidth(measure) / 1000 * FONT_SIZE;
			float startX = (pageWidth - blockWidth) / 2;
			float startY = page.getMediaBox().getHeight() - 40;

			cs.beginText();
			cs.setFont(FONT, FONT_SIZE);
			cs.setLeading(LEADING);
			cs.newLineAtOffset(startX, startY);

			addHeader(cs);
			cs.newLine();

			addProdukte(cs, positionen);
			cs.newLine();

			addTotals(cs, positionen, gegeben);
			cs.newLine();

			addVatBlock(cs, positionen);
			cs.newLine();

			addFooter(cs);

			cs.endText();
			return document;

		} catch (IOException e) {
			throw new PrinterException(e.getMessage());
		}
	}

	/* ---------------- HEADER ---------------- */

	private void addHeader(PDPageContentStream cs) throws IOException {
		for (String line : header.split("\n")) {
			cs.showText(line);
			cs.newLine();
		}

		String dateTime = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
		cs.showText(dateTime);
	}

	/* ---------------- PRODUKTE ---------------- */

	private void addProdukte(PDPageContentStream cs, List<Position> positionen)
			throws IOException {

		for (Position p : positionen) {
			Produkt produkt;
			// this is terrible
			try {
				produkt = db.fetchProdukt(p.getProduktnummer());
			} catch (SQLException e) {
				cs.newLine();
				cs.showText("FEHLER: Produkt nicht gefunden");
				continue;
			}

			double preisSumme = p.getMenge() * produkt.getVerkaufspreis();
			String bezeichnung = produkt.getBezeichnung();

			String line = String.format(
					"%-3d %-28s %7.2f",
					p.getMenge(),
					bezeichnung,
					preisSumme).replace('.', ',');
			cs.newLine();
			cs.showText(line);
		}
	}

	/* ---------------- TOTALS ---------------- */

	private void addTotals(PDPageContentStream cs, List<Position> positionen, double gegeben)
			throws IOException {

		double summe = 0;
		for (Position p : positionen) {
			Produkt produkt;
			// this is even worse because were doing the same shit again
			try {
				produkt = db.fetchProdukt(p.getProduktnummer());
			} catch (SQLException e) {
				cs.newLine();
				cs.showText("FEHLER: Produkt nicht gefunden");
				continue;
			}

			summe += p.getMenge() * produkt.getVerkaufspreis();
		}

		cs.newLine();
		cs.showText(String.format("zu zahlen:%22s", format(summe)));
		cs.newLine();
		cs.showText(String.format("gegeben:%24s", format(gegeben)));
		cs.newLine();
		cs.showText(String.format("zurück:%24s", format(Math.abs(gegeben - summe))));
	}

	/* ---------------- VAT BLOCK ---------------- */

	private void addVatBlock(PDPageContentStream cs, List<Position> positionen) throws IOException {
		cs.newLine();
		cs.newLine();
		cs.showText("Steuersatz    Netto    Steuer    Brutto");

		double summe7 = 0;
		double summe19 = 0;

		for (Position p : positionen) {
			Produkt produkt;
			try {
				produkt = db.fetchProdukt(p.getProduktnummer());
			} catch (SQLException e) {
				cs.newLine();
				cs.showText("FEHLER: Produkt nicht gefunden");
				continue;
			}

			double brutto = p.getMenge() * produkt.getVerkaufspreis();
			if (produkt.getSteuersatz() == Steuersatz.REDUZIERT_7) {
				summe7 += brutto;
			} else {
				summe19 += brutto;
			}
		}

		// Berechne Netto und Steuer
		double netto19 = summe19 / (1 + Steuersatz.REGULAER_19.getSteuersatz());
		double steuer19 = summe19 - netto19;

		double netto7 = summe7 / (1 + Steuersatz.REDUZIERT_7.getSteuersatz());
		double steuer7 = summe7 - netto7;

		cs.newLine();
		cs.showText(String.format("19,00%%   %7.2f   %7.2f   %7.2f", netto19, steuer19, summe19).replace('.',
				','));
		cs.newLine();
		cs.showText(String.format("7,00%%    %7.2f   %7.2f   %7.2f", netto7, steuer7, summe7).replace('.',
				','));
	}

	/* ---------------- FOOTER ---------------- */

	private void addFooter(PDPageContentStream cs) throws IOException {
		cs.newLine();
		cs.newLine();
		for (String line : footer.split("\n")) {
			cs.showText(line);
			cs.newLine();
		}
	}

	private String format(double value) {
		return String.format("%,.2f", value).replace('.', ',');
	}
}
