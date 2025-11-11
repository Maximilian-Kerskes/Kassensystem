package com.kassensystem.datenspeicherung.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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


	public void addProdukt(Produkt pProdukt, int pAnzahl, int pEinkaufsnummer) throws SQLException
    {
		int einkaufsnummer = pEinkaufsnummer;
        Produkt dasProdukt = pProdukt;
        int produktID = dasProdukt.getProduktNummer();
        int anzahl = pAnzahl;
 		Timestamp zeitpunkt = new Timestamp(System.currentTimeMillis());


        String insert = "INSERT INTO einkaeufe" ; 
        insert += "(einkausnummer, produktid, timestamp, anzahl)" ;
        insert += "VALUES" ;
        insert += "(?, ?, ?, ?)" ;



        try (PreparedStatement stmt = con.prepareStatement(insert)) {
			stmt.setInt(1, einkaufsnummer);
	        stmt.setInt(2, produktID);
    	    stmt.setTimestamp(3, zeitpunkt);
        	stmt.setInt(4, anzahl);
		
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("Fehler beim Speichern der Position: " + e.getMessage(), e);
		}
	}
    

	public Produkt fetchProdukt(int produktNummer) throws SQLException
	{
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

		try (PreparedStatement stmt = con.prepareStatement(sqlStmt)) 
		{
			stmt.setDouble(1, neuerBestand);
			stmt.setInt(2, produkt.getProduktNummer());
			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new SQLException("Fehler beim setzen des Bestands: " + e.getMessage(), e);
		}

	}

	public void changePosition(Produkt pProdukt, int pMenge, int pEinkaufsnummer) throws SQLException
	{

		Produkt dasProdukt = pProdukt;
        int einkaufsnummer = pEinkaufsnummer;
        int produktID = dasProdukt.getProduktNummer();
        int menge = pMenge;
    
        String update = "UPDATE einkaeufe ";
        update += "SET menge = ? ";
        update += "WHERE produktID = ?";
        update += "AND einkaufsnummer = ?";

        try (PreparedStatement stmt = con.prepareStatement(update)) {
			stmt.setInt(1, menge);
	        stmt.setInt(2, produktID);
        	stmt.setInt(3, einkaufsnummer);
		
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("Fehler beim Aendern der Position: " + e.getMessage(), e);
		}
	}

	

	public void deletePosition(Produkt pProdukt, int pEinkaufsnummer) throws SQLException
	{
		Produkt dasProdukt = pProdukt;
        int produktID = dasProdukt.getProduktNummer();

        String delete = "DELETE FROM";
        delete += "einkaeufe";
        delete += "WHERE";
        delete += "produktid = ?" ;

        try (PreparedStatement stmt = con.prepareStatement(delete)) {
			stmt.setInt(1, produktID);

		
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("Fehler beim Loeschen der Position: " + e.getMessage(), e);
		}
	}

	public void payEinkauf(int pEinkaufsNummer) throws SQLException
	{
        int einkaufsnummer = pEinkaufsNummer;
        
        String update = "UPDATE einkaeufe ";
        update += "SET bezahlt = 1 ";
        update += "WHERE einkaufsnummer = ?";

        try (PreparedStatement stmt = con.prepareStatement(update)) 
		{
        	stmt.setInt(1, einkaufsnummer);
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLException("Fehler beim Bezahlen des Einkaufs: " + e.getMessage(), e);
		}
	}

	public int fetchEinkaufsNummer () throws SQLException
	{
		String select = "SELECT max(einkaufsnummer), bezahlt ";
		select += "FROM einkaeufe ";

		try (PreparedStatement stmt = con.prepareStatement(select)) 
		{
			try (ResultSet rs = stmt.executeQuery()) 
			{
				if (rs.next()) 
				{
					int nummer = rs.getInt(1);
					int bezahlt = rs.getInt(2);
					if (bezahlt == 1)
					{
						return nummer++;
					}
					else
					{
						return nummer;
					}
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Fehler beim Laden der Einkaufsnummer: " + e.getMessage(), e);
		}
            return 0;
	}

}
