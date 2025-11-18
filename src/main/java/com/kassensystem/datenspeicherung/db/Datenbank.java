package com.kassensystem.datenspeicherung.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.kassensystem.fachkonzept.Position;
import com.kassensystem.fachkonzept.Produkt;

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

	public Produkt fetchProdukt(String produktNummer) throws SQLException {
		String sqlStmt = "SELECT * ";
		sqlStmt += "FROM produkt ";
		sqlStmt += "WHERE produktnr = (?)";
		try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) {
			stmt.setString(1, produktNummer);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Produkt(rs.getString(1), rs.getString(2), rs.getDouble(3),
							rs.getDouble(4));
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Laden des Produkts: " + e.getMessage(), e);
		}
	}
	
	public List<Produkt> fetchProdukte() throws SQLException {
	    String sqlStmt = "SELECT * FROM produkt";

	    try (PreparedStatement stmt = con.prepareStatement(sqlStmt);
	         ResultSet rs = stmt.executeQuery()) {

	        List<Produkt> produkte = new ArrayList<>();

	        while (rs.next()) {
	            produkte.add(new Produkt(
	                    rs.getString(1),
	                    rs.getString(2),
	                    rs.getDouble(3),
	                    rs.getDouble(4)
	            ));
	        }

	        return produkte;
	    } catch (SQLException e) {
	        throw new SQLException("Fehler beim Laden der Produkte: " + e.getMessage(), e);
	    }
	}
	
	
	public List<Position> fetchEinkaeufe() throws SQLException {
		String sqlStmt = "SELECT * FROM einkaeufe";

		try (PreparedStatement stmt = con.prepareStatement(sqlStmt);
			ResultSet rs = stmt.executeQuery()) {

			List<Position> einkaeufe = new ArrayList<>();

			while (rs.next()) {
				einkaeufe.add(new Position( 
					rs.getInt(1), 
					rs.getString(2),        
					rs.getTimestamp(3),  
					rs.getInt(4)         
				));
			}

			return einkaeufe;
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Laden der Einkäufe: " + e.getMessage(), e);
		}
	}

	
	public void deleteProdukt(String produktNummer) throws SQLException {
	    String sqlStmt = "DELETE FROM produkt WHERE produktnr = ?";
	    try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) {
	        stmt.setString(1, produktNummer);
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected == 0) {
	            throw new SQLException("Kein Produkt mit der Nummer " + produktNummer + " gefunden.");
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Fehler beim Löschen des Produkts: " + e.getMessage(), e);
	    }
	}
	
	public void updateProdukt(String produktNummer, String bezeichnung, double verkaufspreis, double bestand) throws SQLException {
	    String sqlStmt = "UPDATE produkt SET bezeichnung = ?, verkaufspreis = ?, bestand = ? WHERE produktnr = ?";
	    try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) {
	        stmt.setString(1, bezeichnung);
	        stmt.setDouble(2, verkaufspreis);
	        stmt.setDouble(3, bestand );
	        stmt.setString(4, produktNummer);

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected == 0) {
	            throw new SQLException("Kein Produkt mit der Nummer " + produktNummer + " gefunden.");
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Fehler beim Aktualisieren des Produkts: " + e.getMessage(), e);
	    }
	}
	
	public void createProdukt(String produktNummer, String bezeichnung, double verkaufspreis, double bestand) throws SQLException {
	    String sqlStmt = "INSERT INTO produkt (produktnr, bezeichnung, verkaufspreis, bestand) VALUES (?, ?, ?, ?)";
	    try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) {
	        stmt.setString(1, produktNummer);
	        stmt.setString(2, bezeichnung);
	        stmt.setDouble(3, verkaufspreis);
	        stmt.setDouble(4, bestand);

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new SQLException("Fehler beim Erstellen des Produkts: " + e.getMessage(), e);
	    }
	}


	public void setBestand(Produkt produkt, double neuerBestand) throws SQLException {
		String sqlStmt = "UPDATE produkt ";
		sqlStmt += "SET bestand = ? ";
		sqlStmt += "WHERE produktnr = ? ";

		try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) {
			stmt.setDouble(1, neuerBestand);
			stmt.setString(2, produkt.getProduktNummer());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Fehler beim setzen des Bestands: " + e.getMessage(), e);
		}

	}

	public void changePosition(Produkt pProdukt, int pMenge, int pEinkaufsnummer) throws SQLException {

		Produkt dasProdukt = pProdukt;
		int einkaufsnummer = pEinkaufsnummer;
		String produktID = dasProdukt.getProduktNummer();
		int menge = pMenge;

		String update = "UPDATE einkaeufe ";
		update += "SET menge = ? ";
		update += "WHERE produktnr = ?";
		update += "AND einkaufsnr = ?";

		try (PreparedStatement stmt = con.prepareStatement(update)) {
			stmt.setInt(1, menge);
			stmt.setString(2, produktID);
			stmt.setInt(3, einkaufsnummer);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Aendern der Position: " + e.getMessage(), e);
		}
	}

	public void deletePosition(Produkt pProdukt, int pEinkaufsnummer) throws SQLException {
		Produkt dasProdukt = pProdukt;
		String produktID = dasProdukt.getProduktNummer();

		String delete = "DELETE FROM";
		delete += "einkaeufe";
		delete += "WHERE";
		delete += "produktnr = ?";

		try (PreparedStatement stmt = con.prepareStatement(delete)) {
			stmt.setString(1, produktID);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Loeschen der Position: " + e.getMessage(), e);
		}
	}

	public void createPosition(int einkaufsnummer, String produktnr, int anzahl, Timestamp zeitpunkt) throws SQLException {
		String insert = "INSERT INTO einkaeufe (einkaufsnr, produktnr, timestamp, anzahl) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(insert)) {
			stmt.setInt(1, einkaufsnummer);
			stmt.setString(2, produktnr);
			stmt.setTimestamp(3, zeitpunkt);
			stmt.setInt(4, anzahl);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Erstellen der Position: " + e.getMessage(), e);
		}
	}

	public void payEinkauf(int pEinkaufsNummer) throws SQLException {
		int einkaufsnummer = pEinkaufsNummer;

		String update = "UPDATE einkaeufe ";
		update += "SET bezahlt = 1 ";
		update += "WHERE einkaufsnummer = ?";

		try (PreparedStatement stmt = con.prepareStatement(update)) {
			stmt.setInt(1, einkaufsnummer);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Bezahlen des Einkaufs: " + e.getMessage(), e);
		}
	}

	public int fetchEinkaufsNummer() throws SQLException {
		String select = "SELECT MAX(einkaufsnr) FROM einkaeufe";
		try (PreparedStatement stmt = con.prepareStatement(select);
			ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				int max = rs.getInt(1);
				if (rs.wasNull()) {
					return 1;
				}
				return max;
			}

		} catch (SQLException e) {
			throw new SQLException("Fehler beim Laden der Einkaufsnummer: " + e.getMessage(), e);
		}

		return 1;
	}
}
