package com.kassenssystem.fachkonzept;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.kassensystem.fachkonzept.Kasse;

public class KasseTest {
	@Test
	public void openKasseTest(){
		assertDoesNotThrow(() -> {
			Kasse dieKasse = new Kasse();
			dieKasse.openKasse();
		});
	}

}
