package com.kassenssystem.fachkonzept;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.kassensystem.fachkonzept.Bon;

public class BonTest {
	@Test
	public void printBonTest(){
		assertDoesNotThrow(() -> {
			Bon derBon = new Bon();
			derBon.printBon(null);
		});
	}

}

