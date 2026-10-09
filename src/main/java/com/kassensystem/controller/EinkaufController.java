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
public class EinkaufController {
	private final Steuerung dieSteuerung;

	public EinkaufController() {
		dieSteuerung = new Steuerung();
	}

	@GetMapping("/einkaeufe")
	public ResponseEntity<List<Position>> getAllEinkauefe() {
		List<Position> einkaeufe = dieSteuerung.getEinkaeufe();
		return ResponseEntity.ok(einkaeufe);
	}

	@GetMapping("/einkaeufe/naechste-einkaufsnr")
	public ResponseEntity<Integer> getNaechsteEinkaufsnummer() {
		int naechsteNr = dieSteuerung.naechsteEinkaufsnummer();
		return ResponseEntity.ok(naechsteNr);
	}

}
