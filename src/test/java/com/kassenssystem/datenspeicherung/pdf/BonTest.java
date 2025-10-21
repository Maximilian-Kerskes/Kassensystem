package com.kassenssystem.datenspeicherung.pdf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.kassensystem.datenspeicherung.pdf.Bon;
import com.kassensystem.fachkonzept.Produkt;

public class BonTest {
	private Produkt[] getMockProdukte() {
		Produkt p1 = new Produkt(1, "Apfel", 0.99, 10);
		Produkt p2 = new Produkt(2, "Brot", 1.50, 5);
		Produkt p3 = new Produkt(3, "Milch", 0.80, 8);
		return new Produkt[] { p1, p2, p3 };
	}

	@Test
	public void printBonTest() {
		assertDoesNotThrow(() -> {
			Bon derBon = new Bon();
			Produkt[] produkte = getMockProdukte();
			PDDocument bon = derBon.createBon(produkte);
		});
	}

}

