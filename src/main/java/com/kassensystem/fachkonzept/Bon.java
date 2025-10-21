package com.kassensystem.fachkonzept;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class Bon {
	private PDDocument document;
	private PDPage page;

	public Bon() {
		document = new PDDocument();
		page = new PDPage();
		document.addPage(page);
	}

	public PDDocument createBon(Produkt[] produkte) throws PrinterException {
		try {
			addProdukte(produkte);
			return document;
		} catch (PrinterException e) {
			throw new PrinterException(e.getMessage());
		}
	}

	private void addProdukte(Produkt[] produkte) throws PrinterException {
		try (PDPageContentStream stream = new PDPageContentStream(document, page)) {
			addDatum(stream);
			PDType1Font font = new PDType1Font(Standard14Fonts.FontName.COURIER);
			int fontSize = 12;
			float leading = 1.5f * fontSize;

			float pageWidth = page.getMediaBox().getWidth();
			float pageHeight = page.getMediaBox().getHeight();

			// Sample line representing the widest possible row (adjust to your expected max
			// length)
			String sampleLine = String.format("%-6s %-20s %10s", "999999", "XXXXXXXXXXXXXXXXXXXX",
					"99999.99€");

			// Calculate width of this sample line in points
			float textWidth = font.getStringWidth(sampleLine) / 1000 * fontSize;

			// Center horizontally
			float startX = (pageWidth - textWidth) / 2;
			float startY = pageHeight - 70; // Leave some margin from the top (also below date)

			stream.beginText();
			stream.setFont(font, fontSize);
			stream.setLeading(leading);
			stream.newLineAtOffset(startX, startY);

			// Table header
			stream.showText(String.format("%-6s %-20s %10s", "Menge", "Produkt", "Preis"));
			stream.newLine();
			stream.showText("-".repeat(sampleLine.length())); // horizontal line
			stream.newLine();

			// Table rows
			for (Produkt p : produkte) {
				String line = String.format(
						"%-6.0f %-20s %10.2f€",
						p.getBestand(),
						p.getBezeichnung(),
						p.getVerkaufspreis());
				stream.showText(line);
				stream.newLine();
			}

			stream.showText("-".repeat(sampleLine.length()));
			stream.newLine();

			stream.endText();
		} catch (IOException e) {
			throw new PrinterException("Fehler beim Schreiben des Bons");
		}
	}

	private void addDatum(PDPageContentStream stream) throws IOException {
		PDType1Font font = new PDType1Font(Standard14Fonts.FontName.COURIER);
		int fontSize = 12;

		float pageWidth = page.getMediaBox().getWidth();
		float topMargin = page.getMediaBox().getHeight() - 30;

		String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		float textWidth = font.getStringWidth(currentDate) / 1000 * fontSize;
		float startX = (pageWidth - textWidth) / 2;

		stream.beginText();
		stream.setFont(font, fontSize);
		stream.newLineAtOffset(startX, topMargin);
		stream.showText(currentDate);
		stream.endText();
	}
}
