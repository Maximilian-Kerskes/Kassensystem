package com.kassensystem.datenspeicherung.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kassensystem.fachkonzept.Produkt;

// TODO
// consider refactoring into try-with-resources? 
public class Datenbank implements AutoCloseable {
	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;

	public Datenbank() throws Exception {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kassensystem", "root", null);
		} catch (Exception e) {
			throw new SQLException("Fehler beim Oeffnen der Datenbank!" + System.lineSeparator()
					+ e.getLocalizedMessage());
		}
	}

	@Override
	public void close() throws Exception {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			throw new SQLException("Fehler beim Schliessen der Datenbank!" + System.lineSeparator()
					+ e.getLocalizedMessage());
		}
	}

	public Produkt fetchProdukt(int produktNummer) {
		String sqlStmt = "SELECT * ";
		sqlStmt += "FROM produkt ";
		sqlStmt += "WHERE produktnr = (?)";
		try {
			stmt = con.prepareStatement(sqlStmt);
			stmt.setInt(1, produktNummer);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new Produkt(rs.getInt(1), rs.getString(2), rs.getInt(3));
			} else {
				return null;
			}

		} catch (Exception e) {
			System.out.println("Fehler beim fetchen des Produkts: " + System.lineSeparator()
					+ e.getLocalizedMessage());
			return null;
		}
	}

}
