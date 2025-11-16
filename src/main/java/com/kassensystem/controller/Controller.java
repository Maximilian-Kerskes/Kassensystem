package com.kassensystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.steuerung.Steuerung;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class Controller {
	Steuerung dieSteuerung;

	public Controller() {
		dieSteuerung = new Steuerung();
	}

	@GetMapping("/produkte")
	public ResponseEntity<List<Produkt>> getAllProdukte() {
	    List<Produkt> produkte = dieSteuerung.getProdukte();
	    return ResponseEntity.ok(produkte);
	}
	
	@GetMapping("/produkte/{id}")
	public ResponseEntity<Produkt> getProduktById(@PathVariable int id) {
		Produkt produkt = dieSteuerung.getProdukt(id);
		return ResponseEntity.ok(produkt);
	}

	@GetMapping("/einkaeufe")
	public ResponseEntity<Integer> getAllEinkauefe() {
		return ResponseEntity.ok(2);
	}

	@PostMapping("/produkte")
	public ResponseEntity<String> putProdukt(@RequestBody Produkt produkt) {
		
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/produkte/{id}")
	public ResponseEntity<String> deleteProduktById(@PathVariable int id) {
		return ResponseEntity.ok().build();
	}
}
