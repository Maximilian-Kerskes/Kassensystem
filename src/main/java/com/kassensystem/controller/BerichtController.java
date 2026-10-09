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

	@GetMapping("/bestandsliste/csv")
	public ResponseEntity<String> getBestandslisteCSV() {
		String csv = dieSteuerung.generateBestandslisteCSV();
		return ResponseEntity.ok()
				.header("Content-Type", "text/csv; charset=UTF-8")
				.header("Content-Disposition", "attachment; filename=bestandsliste.csv")
				.body(csv);
	}

	@GetMapping("/produktlog/csv")
	public ResponseEntity<String> getProduktLogCSV() {
		String csv = dieSteuerung.generateProduktLogCSV();
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
				.header("Content-Disposition",
						"attachment; filename=umsatz_" + startDate + "_bis_" + endDate + ".csv")
				.body(csv);
	}
}
