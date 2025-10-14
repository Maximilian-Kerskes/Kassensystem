package com.kassenssystem.steuerung;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kassensystem.steuerung.Steuerung;


public class SteuerungTest{
	// TODO
	// migrate to actual GUI accurate tests
	// use mockdb instead of real, this WILL break if the DB changes
	@Test
	public void rueckGeldEventTest(){
		Steuerung dieSteuerung = new Steuerung();
		int rueckGeld = dieSteuerung.rueckGeldEvent(1, 15);
		assertEquals(14, rueckGeld);

		rueckGeld = dieSteuerung.rueckGeldEvent(2, 5);
		assertEquals(10, rueckGeld);
	} 

}
