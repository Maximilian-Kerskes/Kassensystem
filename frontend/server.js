import express from "express";
import path from "path";
import { fileURLToPath } from "url";
import fs from "fs/promises";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
const port = process.env.PORT || 3000;

app.use(express.static(path.join(__dirname, "public")));
app.use(express.json());

const mockDbPath = path.join(__dirname, "mockdb.json");

async function readMockDb() {
    const data = await fs.readFile(mockDbPath, "utf-8");
    return JSON.parse(data);
}

// API-Routen
app.get("/produkte", async (req, res) => {
    try {
        const db = await readMockDb();
        res.json(db.produkte);
    } catch (err) {
        console.error(err);
        res.status(500).send("Fehler beim Abrufen der Produkte");
    }
});

app.get("/einkaeufe", async (req, res) => {
    try {
        const db = await readMockDb();
        res.json(db.einkaeufe);
    } catch (err) {
        console.error(err);
        res.status(500).send("Fehler beim Abrufen der Einkäufe");
    }
});

app.put("/produkte", async (req, res) => {
    try {
        const db = await readMockDb();
        const { produktnr, bezeichnung, verkaufspreis, bestand } = req.body;

        const produkt = db.produkte.find(p => p.produktnr === Number(produktnr));
        if (produkt) {
            produkt.bezeichnung = bezeichnung;
            produkt.verkaufspreis = Number(verkaufspreis);
            produkt.bestand = Number(bestand);

            await fs.writeFile(mockDbPath, JSON.stringify(db, null, 2), "utf-8");
            res.sendStatus(200);
        } else {
            res.status(404).send("Produkt nicht gefunden");
        }
    } catch (err) {
        console.error(err);
        res.status(500).send("Fehler beim Speichern des Produkts");
    }
});

app.post("/produkte", async (req, res) => {
    try {
        const db = await readMockDb();
        const { bezeichnung, verkaufspreis, bestand } = req.body;

        // Neue Produktnummer automatisch vergeben, muss entfernt werden nur notwendig für aktuellen Mock
        const neueNr = db.produkte.length > 0 ? Math.max(...db.produkte.map(p => p.produktnr)) + 1 : 1;

        const neuesProdukt = {
            produktnr: neueNr,
            bezeichnung,
            verkaufspreis: Number(verkaufspreis),
            bestand: Number(bestand)
        };

        db.produkte.push(neuesProdukt);

        await fs.writeFile(mockDbPath, JSON.stringify(db, null, 2), "utf-8");
        res.sendStatus(200);
    } catch (err) {
        console.error(err);
        res.status(500).send("Fehler beim Hinzufügen des Produkts");
    }
});

app.delete("/produkte/:produktnr", async (req, res) => {
    try {
        const db = await readMockDb();
        const produktnr = Number(req.params.produktnr);

        const index = db.produkte.findIndex(p => p.produktnr === produktnr);
        if (index !== -1) {
            db.produkte.splice(index, 1);
            await fs.writeFile(mockDbPath, JSON.stringify(db, null, 2), "utf-8");
            res.sendStatus(200);
        } else {
            res.status(404).send("Produkt nicht gefunden");
        }
    } catch (err) {
        console.error(err);
        res.status(500).send("Fehler beim Löschen des Produkts");
    }
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
