package com.kassenssystem.fachkonzept;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kassensystem.fachkonzept.Produkt;

public class ProduktTest {
	@Test
	public void constructorTest(){
		Produkt produktTest = new Produkt(1, "Test", 5, 9);
		assertEquals(1, produktTest.getProduktNummer());
		assertEquals("Test", produktTest.getBezeichnung());
		assertEquals(5, produktTest.getVerkaufspreis());
		assertEquals(9, produktTest.getBestand());
	}

}
