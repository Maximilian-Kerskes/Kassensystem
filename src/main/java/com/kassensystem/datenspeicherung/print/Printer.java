package com.kassensystem.datenspeicherung.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class Printer {
	String printerName;

	public Printer() {
		this.printerName = "Generic-CUPS-PDF-Printer";
	}

	public void printDocument(PDDocument document) throws PrinterException {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
			for (PrintService printer : printServices) {
				if (printer.getName() == printerName) {
					job.setPrintService(printer);
				}
			}
			job.setPageable(new PDFPageable(document));

			if (job.printDialog()) {
				job.print();
			}
		} catch (PrinterException e) {
			throw new PrinterException("");
		}
	}

}
