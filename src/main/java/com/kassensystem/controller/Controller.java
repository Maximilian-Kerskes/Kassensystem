package com.kassensystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.steuerung.Steuerung;

// TODO 
// add global error handling controller

@RestController
public class Controller {
	Steuerung dieSteuerung;

	public Controller() {
		dieSteuerung = new Steuerung();
	}

	@GetMapping("/produkte")
	// return the actual Produkte
	public ResponseEntity<Integer> getAllProdukte() {
		return ResponseEntity.ok(1);
	}
	
	@GetMapping("/produkte/{id}")
	// return the actual Produkte
	public ResponseEntity<Integer> getProduktById(@PathVariable int id) {
		return ResponseEntity.ok(id);
	}

	@GetMapping("/einkauefe")
	// return the actual Positionen
	public ResponseEntity<Integer> getAllEinkauefe() {
		return ResponseEntity.ok(2);
	}

	@PostMapping("/produkte")
	// not sure if this works...
	public ResponseEntity<String> putProdukt(@RequestBody Produkt produkt) {
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/produkte/{id}")
	public ResponseEntity<String> deleteProduktById(@PathVariable int id) {
		return ResponseEntity.ok().build();
	}
}
