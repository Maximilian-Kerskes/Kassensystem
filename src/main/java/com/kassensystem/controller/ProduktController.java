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
public class BerichtController {
	private final Steuerung dieSteuerung;

	public BerichtController() {
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
		dieSteuerung.logProdukt(existierendesProdukt, produkt);
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
}
