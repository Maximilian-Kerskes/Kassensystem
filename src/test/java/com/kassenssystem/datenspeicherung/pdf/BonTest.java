package com.kassenssystem.datenspeicherung.pdf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.datenspeicherung.pdf.Bon;
import com.kassensystem.fachkonzept.Position;
import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.fachkonzept.Steuersatz;

public class BonTest {
	private List<Position> getMockPositionen() {
		List<Position> positionen = new java.util.ArrayList<>();
		positionen.add(new Position(0, "0", new java.sql.Timestamp(System.currentTimeMillis()), 5));
		positionen.add(new Position(1, "1", new java.sql.Timestamp(System.currentTimeMillis()), 1));
		return positionen;
	}

	@Test
	public void printBonTest() {
		assertDoesNotThrow(() -> {
			Datenbank db = new Datenbank();
			Bon derBon = new Bon(db);
			List<Position> positionen = getMockPositionen();
			PDDocument bon = derBon.createBon(positionen, 10.50);
		});
	}

}
