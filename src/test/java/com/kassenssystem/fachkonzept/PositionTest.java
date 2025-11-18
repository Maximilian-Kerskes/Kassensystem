package com.kassenssystem.fachkonzept;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.kassensystem.fachkonzept.Position;

public class PositionTest {
	@Test
	public void constructorTest() {
		Timestamp zeitpunkt = Timestamp.valueOf(LocalDateTime.now());
		Position positionTest = new Position(1, "1", zeitpunkt, 9);
		assertEquals(1, positionTest.getEinkaufsnummer());
		assertEquals(1, positionTest.getProduktnummer());
		assertEquals(zeitpunkt, positionTest.getZeitpunkt());
		assertEquals(9, positionTest.getMenge());
	}

}
