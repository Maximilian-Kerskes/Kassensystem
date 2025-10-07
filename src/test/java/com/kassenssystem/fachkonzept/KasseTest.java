package test.java.com.kassenssystem.fachkonzept;

import org.junit.jupiter.api.Test;

import main.java.com.kassensystem.fachkonzept.Kasse;

public class KasseTest {
	@Test
	public void openKasseTest(){
		assertDoesNotThrow(() -> {
			Kasse dieKasse = new Kasse();
			dieKasse.openKasse();
		});
	}

}
