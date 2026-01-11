package com.kassensystem.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kassensystem.fachkonzept.Position;
import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.steuerung.Steuerung;

@RestController
@RequestMapping("/api")
public class Controller {
	private final Steuerung dieSteuerung;

	public Controller() {
		dieSteuerung = new Steuerung();
	}

	@GetMapping("/produkte")
	public ResponseEntity<List<Produkt>> getAllProdukte() {
		List<Produkt> produkte = dieSteuerung.getProdukte();
		return ResponseEntity.ok(produkte);
	}

	@GetMapping("/produkte/{id}")
	public ResponseEntity<Produkt> getProduktById(@PathVariable String id) {
		Produkt produkt = dieSteuerung.getProdukt(id);
		if (produkt == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produkt);
	}

	@PostMapping("/produkte")
	public ResponseEntity<String> createProdukt(@RequestBody Produkt produkt) {
		dieSteuerung.createProdukt(produkt);
		return ResponseEntity.ok("Produkt erstellt");
	}

	@PutMapping("/produkte/{id}")
	public ResponseEntity<String> updateProdukt(@PathVariable String id, @RequestBody Produkt produkt) {
		Produkt existierendesProdukt = dieSteuerung.getProdukt(id);
		if (existierendesProdukt == null) {
			return ResponseEntity.notFound().build();
		}
		dieSteuerung.updateProdukt(produkt);
		return ResponseEntity.ok("Produkt aktualisiert");
	}

	@PutMapping("/produkte/{id}/archivieren")
	public ResponseEntity<String> archiveProduktById(@PathVariable String id, @RequestBody boolean archiviert) {
		Produkt existierendesProdukt = dieSteuerung.getProdukt(id);
		if (existierendesProdukt == null) {
			return ResponseEntity.notFound().build();
		}
		dieSteuerung.archiveProduktById(id, archiviert);
		return ResponseEntity.ok("Produkt gelöscht");
	}

	@GetMapping("/einkaeufe")
	public ResponseEntity<List<Position>> getAllEinkauefe() {
		List<Position> einkaeufe = dieSteuerung.getEinkaeufe();
		return ResponseEntity.ok(einkaeufe);
	}

	@PostMapping("/positionen")
	public ResponseEntity<String> createPosition(@RequestBody Position position) {
		if (position.getMenge() <= 0 || position.getZeitpunkt() == null) {
			return ResponseEntity.badRequest().body("Ungültige Positionsdaten");
		}
		dieSteuerung.createPosition(position);
		return ResponseEntity.ok("Position erstellt");
	}

	@GetMapping("/einkaeufe/naechste-einkaufsnr")
	public ResponseEntity<Integer> getNaechsteEinkaufsnummer() {
		int naechsteNr = dieSteuerung.naechsteEinkaufsnummer();
		return ResponseEntity.ok(naechsteNr);
	}

	@GetMapping("/kasse")
	public ResponseEntity<String> kasseOeffnen() {
		try {
			dieSteuerung.kasseOeffnen();
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Kasse konnte nicht geoeffnet werden");
		}
		return ResponseEntity.ok("Kasse geoeffnet");
	}

	@GetMapping("/bestandsliste/csv")
	public ResponseEntity<String> getBestandslisteCSV() {
		String csv = dieSteuerung.generateBestandslisteCSV();
		return ResponseEntity.ok()
				.header("Content-Type", "text/csv; charset=UTF-8")
				.header("Content-Disposition", "attachment; filename=bestandsliste.csv")
				.body(csv);
	}

	@GetMapping("/umsatz/csv")
	public ResponseEntity<String> getUmsatzCSV(
			@RequestParam String startDate,
			@RequestParam String endDate) {
		String csv = dieSteuerung.generateUmsatzCSV(startDate, endDate);
		return ResponseEntity.ok()
				.header("Content-Type", "text/csv; charset=UTF-8")
				.header("Content-Disposition", "attachment; filename=umsatz_" + startDate + "_bis_" + endDate + ".csv")
				.body(csv);
	}
}
