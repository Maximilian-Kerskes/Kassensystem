package test.java.com.kassenssystem.datenspeicherung.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.com.kassensystem.datenspeicherung.db.Datenbank;

public class DatenbankTest {
	private Datenbank dieDatenbank;

	@BeforeEach
	public void setUp() throws Exception {
		dieDatenbank = new Datenbank();
	}

	@Test
	public void fetchProduktTest() {
		assertDoesNotThrow(() -> {
			dieDatenbank.fetchProdukt(1);
		});
	}
}
