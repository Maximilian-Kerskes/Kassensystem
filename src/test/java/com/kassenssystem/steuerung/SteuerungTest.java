package com.kassenssystem.steuerung;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.SQLException;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.steuerung.Steuerung;

public class SteuerungTest {
	public void setUpBestandUpdateEventTest() {
		try (Datenbank dieDatenbank = new Datenbank()) {
			Produkt produkt = dieDatenbank.fetchProdukt(1);
			dieDatenbank.setBestand(produkt, 10);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// TODO
	// migrate to actual GUI accurate tests
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void rueckGeldEventTest() {
		assertDoesNotThrow(() -> {
			Steuerung dieSteuerung = new Steuerung();
			double rueckGeld = dieSteuerung.rueckGeldEvent(1, 1);
			assertEquals(14, rueckGeld);

			rueckGeld = dieSteuerung.rueckGeldEvent(1, 5);
			assertEquals(10, rueckGeld);
		});
	}

	// TODO
	// migrate to actual GUI accurate tests
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void bestandUpdateEventTest() {
		setUpBestandUpdateEventTest();
		Steuerung dieSteuerung = new Steuerung();
		int produktNummer = 1;
		assertDoesNotThrow(() -> {
			Datenbank dieDatenbank = new Datenbank();
			Produkt dasProdukt = dieDatenbank.fetchProdukt(1);
			assertEquals(10, dasProdukt.getBestand());
			dieSteuerung.bestandUpdateEvent(produktNummer);

			dasProdukt = dieDatenbank.fetchProdukt(1);
			assertEquals(9, dasProdukt.getBestand());
		});
	}
}
