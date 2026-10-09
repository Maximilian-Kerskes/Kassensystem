package com.kassensystem.controller;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kassensystem.fachkonzept.Position;
import com.kassensystem.dto.kasse.BonPositionRequest;
import com.kassensystem.dto.kasse.BonRequest;
import com.kassensystem.dto.kasse.RueckgeldRequest;
import com.kassensystem.steuerung.Steuerung;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class KassenController {
	private final Steuerung dieSteuerung;

	public KassenController(Steuerung dieSteuerung) {
		this.dieSteuerung = dieSteuerung;
	}

	@PostMapping("/kasse/oeffnen")
	public ResponseEntity<Void> kasseOeffnen() {
		try {
			dieSteuerung.kasseOeffnen();
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/bon")
	public ResponseEntity<Void> bonDrucken(@Valid @RequestBody BonRequest request) {
		List<Position> positionen = request.positionen().stream()
				.map(this::toPosition)
				.toList();
		try {
			dieSteuerung.bonDrucken(positionen, request.gegebenesGeld().doubleValue());
		} catch (PrinterException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/rueckgeld")
	public ResponseEntity<BigDecimal> rueckgeldBerechnen(
			@Valid @RequestBody RueckgeldRequest request) {
		try {
			double rueckgeld = dieSteuerung.rueckgeldBerechnen(
					request.bezahlterBetrag().doubleValue(),
					request.gesamtbetrag().doubleValue());
			return ResponseEntity.ok(BigDecimal.valueOf(rueckgeld));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	private Position toPosition(BonPositionRequest request) {
		return new Position(
				request.einkaufsnummer(),
				request.produktnummer(),
				request.zeitpunkt(),
				request.menge());
	}
}
