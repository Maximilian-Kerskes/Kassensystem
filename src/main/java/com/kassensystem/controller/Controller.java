package com.kassensystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.fachkonzept.Position;
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
    public ResponseEntity<Produkt> getProduktById(@PathVariable int id) {
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
    public ResponseEntity<String> updateProdukt(@PathVariable int id, @RequestBody Produkt produkt) {
        Produkt existierendesProdukt = dieSteuerung.getProdukt(id);
        if (existierendesProdukt == null) {
            return ResponseEntity.notFound().build();
        }
        dieSteuerung.updateProdukt(produkt);
        return ResponseEntity.ok("Produkt aktualisiert");
    }

    @DeleteMapping("/produkte/{id}")
    public ResponseEntity<String> deleteProduktById(@PathVariable int id) {
        Produkt existierendesProdukt = dieSteuerung.getProdukt(id);
        if (existierendesProdukt == null) {
            return ResponseEntity.notFound().build();
        }
        dieSteuerung.deleteProdukt(id);
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
}
