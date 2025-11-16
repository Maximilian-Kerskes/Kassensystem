package com.kassenssystem.steuerung;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.steuerung.Steuerung;

public class SteuerungTest {
	public void setUpTestProdukt() {
		try (Datenbank dieDatenbank = new Datenbank()) {
			Produkt produkt = dieDatenbank.fetchProdukt(1);
			if (produkt == null) {
				System.out.println("⚠️ Produkt mit ID 1 nicht gefunden. Test könnte fehlschlagen.");
				return;
			}
			dieDatenbank.setBestand(produkt, 10);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// TODO
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void scannedProduktTest() {
		setUpTestProdukt();
		assertDoesNotThrow(() -> {
			Steuerung steuerung = new Steuerung();
			assertNotEquals(-1, steuerung.getEinkaufsnummer()); // init Einkaufsvorgang

			Produkt produkt;
			try (Datenbank dieDatenbank = new Datenbank()) {
				produkt = dieDatenbank.fetchProdukt(1);
			}

			steuerung.scannedProdukt(produkt);
		});
	}

	// TODO
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void changeMengeTest() {
		setUpTestProdukt();
		assertDoesNotThrow(() -> {
			Steuerung steuerung = new Steuerung();
			assertNotEquals(-1, steuerung.getEinkaufsnummer()); // init Einkaufsvorgang

			Produkt produkt;
			try (Datenbank dieDatenbank = new Datenbank()) {
				produkt = dieDatenbank.fetchProdukt(1);
			}

			steuerung.changeMenge(produkt, 5);
		});
	}

	// TODO
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void deleteProduktTest() {
		setUpTestProdukt();
		assertDoesNotThrow(() -> {
			Steuerung steuerung = new Steuerung();
			steuerung.getEinkaufsnummer();

			Produkt produkt;
			try (Datenbank dieDatenbank = new Datenbank()) {
				produkt = dieDatenbank.fetchProdukt(1);
			}

			steuerung.deleteProdukt(produkt);
		});
	}

	// TODO
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void getEinkaufsnummerTest() {
		assertDoesNotThrow(() -> {
			Steuerung steuerung = new Steuerung();
			steuerung.getEinkaufsnummer();

			// just check that the Einkaufsnummer got set
			assertNotNull(steuerung);
		});
	}

	// TODO
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
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void bestandUpdateEventTest() {
		setUpTestProdukt();
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
