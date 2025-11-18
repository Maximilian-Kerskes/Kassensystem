package com.kassensystem.steuerung;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Produkt;
import com.kassensystem.fachkonzept.Position;

public class Steuerung {
    private Datenbank dieDatenbank;
    private int einkaufsnummer;

    public Steuerung() {
        try {
            dieDatenbank = new Datenbank();
        } catch (Exception e) {
            System.out.println("Fehler beim Initialisieren der Datenbank: "
                    + System.lineSeparator() + e.getLocalizedMessage());
        }
    }

    public void bestandUpdateEvent(String produktNummer) {
        try {
            Produkt dasProdukt = dieDatenbank.fetchProdukt(produktNummer);
            double bestand = dasProdukt.getBestand();
            if (bestand <= 0) {
                dieDatenbank.setBestand(dasProdukt, 0);
                return;
            }
            dieDatenbank.setBestand(dasProdukt, bestand - 1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public double rueckGeldEvent(String produktNummer, double bezahlterBetrag) {
        try {
            Produkt dasProdukt = dieDatenbank.fetchProdukt(produktNummer);
            return rueckgeldBerechnen(bezahlterBetrag, dasProdukt.getVerkaufspreis());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private double rueckgeldBerechnen(double bezahlterBetrag, double verkaufspreis) {
        return Math.abs(verkaufspreis - bezahlterBetrag);
    }

    public void scannedProdukt(Produkt pProdukt) {
        try {
            dieDatenbank.createProdukt(
                pProdukt.getProduktNummer(), 
                pProdukt.getBezeichnung(), 
                pProdukt.getVerkaufspreis(), 
                pProdukt.getBestand());
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void changeMenge(Produkt pProdukt, int pMenge) {
        try {
            dieDatenbank.changePosition(pProdukt, pMenge, einkaufsnummer);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void deletePosition(Produkt pProdukt, int produkt) {
        try {
            dieDatenbank.deletePosition(pProdukt, einkaufsnummer);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public int getEinkaufsnummer() {
        try {
            return dieDatenbank.fetchEinkaufsNummer();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
    }

    public List<Produkt> getProdukte() {
        try {
            return dieDatenbank.fetchProdukte();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Position> getEinkaeufe() {
        try {
            return dieDatenbank.fetchEinkaeufe();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Produkt getProdukt(String produktnr) {
        try {
            return dieDatenbank.fetchProdukt(produktnr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createProdukt(Produkt produkt) {
        try {
            dieDatenbank.createProdukt(
                produkt.getProduktNummer(),
                produkt.getBezeichnung(),
                produkt.getVerkaufspreis(),
                produkt.getBestand()
            );
        } catch (SQLException e) {
            System.out.println("Fehler beim Erstellen des Produkts: " + e.getMessage());
        }
    }

    public void updateProdukt(Produkt produkt) {
        try {
            dieDatenbank.updateProdukt(
                produkt.getProduktNummer(),
                produkt.getBezeichnung(),
                produkt.getVerkaufspreis(),
                produkt.getBestand()
            );
        } catch (SQLException e) {
            System.out.println("Fehler beim Aktualisieren des Produkts: " + e.getMessage());
        }
    }

    public void deleteProdukt(String produktnr) {
        try {
            dieDatenbank.deleteProdukt(produktnr);
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen des Produkts: " + e.getMessage());
        }
    }

    public void createPosition(Position position) {
        try {
            dieDatenbank.createPosition(
                position.getEinkaufsnummer(), 
                position.getProduktnummer(), 
                position.getMenge(), 
                position.getZeitpunkt()
            );
        } catch (SQLException e) {
            System.out.println("Fehler beim Erstellen der Position: " + e.getMessage());
        }
    }

    public int naechsteEinkaufsnummer() {
        int naechsteEinkaufsnummer;
        try {
            naechsteEinkaufsnummer = dieDatenbank.fetchEinkaufsNummer() + 1;
            return naechsteEinkaufsnummer;
        } catch (SQLException e) {
             System.out.println("Fehler beim erstellen der nächsten Einkaufsnummer: " + e.getMessage());
        }
        return -1;   
    }
}
