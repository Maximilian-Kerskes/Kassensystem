package com.kassensystem.fachkonzept;

import com.google.protobuf.TextFormat.Printer;
import com.kassensystem.fachkonzept.Produkt;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOError;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.printing.PDFPageable;

public class Bon {
	private String printerName;
	private PDDocument document;
	private PDPage page;

	public Bon() {
		printerName = "Generic-CUPS-PDF-Printer";
		document = new PDDocument();
		page = new PDPage();
		document.addPage(page);
	}

	public void printBon(Produkt[] produkte) throws PrinterException {
		try {
			addText("hello world");
			printDocument();

		} catch (PrinterException e) {
			throw new PrinterException(e.getMessage());
		}
	}

	private void addText(String text) throws PrinterException {
		try (PDPageContentStream stream = new PDPageContentStream(document, page)) {
			stream.beginText();
			stream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 15);
			stream.showText(text);
			stream.endText();
		} catch (IOException e) {
			throw new PrinterException("Es kann nicht zur PDF geschrieben werden");
		}
	}

	private void printDocument() throws PrinterException {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
			for (PrintService printer : printServices) {
				if (printer.getName() == printerName) {
					System.out.println("hello");
					job.setPrintService(printer);
				}
			}
			job.setPageable(new PDFPageable(document));

			// Show the print dialog
			if (job.printDialog()) {
				job.print(); // Print the document
			}

		} catch (PrinterException e) {
			throw new PrinterException("");
		}
	}
}
