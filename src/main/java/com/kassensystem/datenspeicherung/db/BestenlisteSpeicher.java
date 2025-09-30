package main.java.com.kassensystem.datenspeicherung.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BestenlisteSpeicher implements AutoCloseable {
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;

	public BestenlisteSpeicher() throws Exception {
		try {
			con = DriverManager.getConnection("...", "root", null);
		} catch (Exception e) {
			throw new SQLException("Fehler beim Oeffnen der Datenbank!" + System.lineSeparator() + e.getLocalizedMessage());
		}
	}

	@Override
	public void close() throws Exception {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			throw new SQLException("Fehler beim Schliessen der Datenbank!" + System.lineSeparator() + e.getLocalizedMessage());
		}
	}
}
