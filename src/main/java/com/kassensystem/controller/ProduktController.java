package com.kassensystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.dto.produkt.ProduktCreateRequest;
import com.kassensystem.dto.produkt.ProduktPatchRequest;
import com.kassensystem.dto.produkt.ProduktUpdateRequest;
import com.kassensystem.steuerung.Steuerung;

@RestController
@RequestMapping("/api")
public class ProduktController {
	private final Steuerung dieSteuerung;

	public ProduktController(Steuerung dieSteuerung) {
		this.dieSteuerung = dieSteuerung;
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
	public ResponseEntity<Void> createProdukt(@Valid @RequestBody ProduktCreateRequest request) {
		Produkt produkt = new Produkt(
				request.produktNummer(),
				request.bezeichnung(),
				request.verkaufspreis().doubleValue(),
				request.steuersatz(),
				request.bestand().doubleValue(),
				false);

		dieSteuerung.createProdukt(produkt);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/produkte/{id}")
	public ResponseEntity<Void> updateProdukt(
			@PathVariable String id,
			@Valid @RequestBody ProduktUpdateRequest request) {
		Produkt existierendesProdukt = dieSteuerung.getProdukt(id);
		if (existierendesProdukt == null) {
			return ResponseEntity.notFound().build();
		}

		Produkt produkt = new Produkt(
				id,
				request.bezeichnung(),
				request.verkaufspreis().doubleValue(),
				request.steuersatz(),
				request.bestand().doubleValue(),
				request.archiviert());

		dieSteuerung.logProdukt(existierendesProdukt, produkt);
		dieSteuerung.updateProdukt(produkt);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/produkte/{id}")
	public ResponseEntity<Void> patchProdukt(
			@PathVariable String id,
			@Valid @RequestBody ProduktPatchRequest request) {
		Produkt existierendesProdukt = dieSteuerung.getProdukt(id);
		if (existierendesProdukt == null) {
			return ResponseEntity.notFound().build();
		}

		Produkt produkt = new Produkt(
				id,
				request.bezeichnung() != null ? request.bezeichnung() : existierendesProdukt.getBezeichnung(),
				request.verkaufspreis() != null
						? request.verkaufspreis().doubleValue()
						: existierendesProdukt.getVerkaufspreis(),
				request.steuersatz() != null ? request.steuersatz() : existierendesProdukt.getSteuersatz(),
				request.bestand() != null ? request.bestand().doubleValue() : existierendesProdukt.getBestand(),
				request.archiviert() != null ? request.archiviert() : existierendesProdukt.getArchiviert());

		dieSteuerung.logProdukt(existierendesProdukt, produkt);
		dieSteuerung.updateProdukt(produkt);
		return ResponseEntity.noContent().build();
	}

}
