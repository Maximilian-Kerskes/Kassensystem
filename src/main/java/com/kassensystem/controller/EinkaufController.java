package com.kassensystem.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kassensystem.dto.einkauf.EinkaufCreateRequest;
import com.kassensystem.dto.einkauf.EinkaufResponse;
import com.kassensystem.fachkonzept.Position;
import com.kassensystem.steuerung.Steuerung;

@RestController
@RequestMapping("/api")
public class EinkaufController {
	private final Steuerung dieSteuerung;

	public EinkaufController(Steuerung dieSteuerung) {
		this.dieSteuerung = dieSteuerung;
	}

	@GetMapping("/einkaeufe")
	public ResponseEntity<List<Position>> getAllEinkauefe() {
		List<Position> einkaeufe = dieSteuerung.getEinkaeufe();
		return ResponseEntity.ok(einkaeufe);
	}

	@PostMapping("/einkaeufe")
	public ResponseEntity<EinkaufResponse> createEinkauf(
			@Valid @RequestBody EinkaufCreateRequest request) {
		int einkaufsnummer = dieSteuerung.createEinkauf(request);

		return ResponseEntity
		  .status(HttpStatus.CREATED)
		  .body(new EinkaufResponse(einkaufsnummer));
	}

}
