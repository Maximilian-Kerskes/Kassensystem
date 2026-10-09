package com.kassensystem.controller;

import java.awt.print.PrinterException;
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
public class KassenController {
	private final Steuerung dieSteuerung;

	public KassenController() {
		dieSteuerung = new Steuerung();
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

	@PostMapping("/bon")
	public ResponseEntity<String> bonDrucken(@RequestBody List<Position> positionen,
			@RequestParam double gegebenesGeld) {
		try {
			dieSteuerung.bonDrucken(positionen, gegebenesGeld);
		} catch (PrinterException e) {
			return ResponseEntity.badRequest().body("Bon konnte nicht gedruckt werden");
		}
		return ResponseEntity.ok("Kasse geoeffnet");
	}

	@PostMapping("/rueckgeld")
	public ResponseEntity<Double> rueckgeldBerechnen(
			@RequestParam double bezahlterBetrag,
			@RequestParam double verkaufspreis) {

		if (bezahlterBetrag < 0 || verkaufspreis < 0) {
			return ResponseEntity.badRequest().build();
		}

		double rueckgeld = dieSteuerung.rueckgeldBerechnen(bezahlterBetrag, verkaufspreis);
		return ResponseEntity.ok(rueckgeld);
	}
}
