package com.kassenssystem.datenspeicherung.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Produkt;

public class DatenbankTest {
	private Datenbank dieDatenbank;

	@BeforeEach
	public void setUpDB() throws Exception {
		dieDatenbank = new Datenbank();
	}

	public void setUpBestandsTest() {
		try {
			Produkt produkt = dieDatenbank.fetchProdukt("1");
			dieDatenbank.setBestand(produkt, 10);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void fetchProduktTest() {
		assertDoesNotThrow(() -> {
			dieDatenbank.fetchProdukt("1");
		});
	}

	@Test
	public void setBestandTest() {
		assertDoesNotThrow(() -> {
			setUpBestandsTest();

			Produkt produkt = dieDatenbank.fetchProdukt("1");
			double bestand = 5;

			assertEquals(10, produkt.getBestand());

			dieDatenbank.setBestand(produkt, bestand);
			produkt = dieDatenbank.fetchProdukt("1");
			assertEquals(5, produkt.getBestand());
		});
	}
}
