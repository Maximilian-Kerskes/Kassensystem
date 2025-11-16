package com.kassensystem.steuerung;

import java.sql.SQLException;
import java.util.List;

import com.kassensystem.datenspeicherung.db.Datenbank;
import com.kassensystem.fachkonzept.Produkt;

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

    public void bestandUpdateEvent(int produktNummer) {
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

    public double rueckGeldEvent(int produktNummer, double bezahlterBetrag) {
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

    public void scannedProdukt(Produkt pProdukt, int pAnzahl) {
        try {
            dieDatenbank.addProdukt(pProdukt, pAnzahl, einkaufsnummer);
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

    public void deleteProdukt(Produkt pProdukt) {
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

    public Produkt getProdukt(int produktnr) {
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

    public void deleteProdukt(int produktnr) {
        try {
            dieDatenbank.deleteProdukt(produktnr);
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen des Produkts: " + e.getMessage());
        }
    }
}
