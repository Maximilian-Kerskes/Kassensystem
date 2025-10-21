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

	public Datenbank() throws SQLException {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kassensystem", "root", null);
		} catch (Exception e) {
			throw new SQLException("Fehler beim Oeffnen der Datenbank!" + System.lineSeparator()
					+ e.getLocalizedMessage());
		}
	}

	@Override
	public void close() throws SQLException {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (Exception e) {
			throw new SQLException("Fehler beim Schliessen der Datenbank!" + System.lineSeparator()
					+ e.getLocalizedMessage());
		}
	}

	public Produkt fetchProdukt(int produktNummer) throws SQLException {
		String sqlStmt = "SELECT * ";
		sqlStmt += "FROM produkt ";
		sqlStmt += "WHERE produktnr = (?)";
		try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) {
			stmt.setInt(1, produktNummer);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Produkt(rs.getInt(1), rs.getString(2), rs.getDouble(3),
							rs.getDouble(4));
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Laden des Produkts: " + e.getMessage(), e);
		}
	}

	public void setBestand(Produkt produkt, double neuerBestand) throws SQLException {
		String sqlStmt = "UPDATE produkt ";
		sqlStmt += "SET bestand = ? ";
		sqlStmt += "WHERE produktnr = ? ";

		try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) {
			stmt.setDouble(1, neuerBestand);
			stmt.setInt(2, produkt.getProduktNummer());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Fehler beim setzen des Bestands: " + e.getMessage(), e);
		}
	}

}
