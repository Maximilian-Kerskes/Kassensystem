import express from "express";
import path from "path";
import { fileURLToPath } from "url";
import mysql from "mysql2/promise";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
const port = process.env.PORT || 3000;

app.use(express.static(path.join(__dirname, "public")));

const dbConfig = {
    host: "localhost",
    user: "root",
    password: "",
    database: "kassensystem"
};

let connection;
async function initDb() {
    connection = await mysql.createConnection(dbConfig);
    console.log("MySQL verbunden!");
}

initDb().catch(err => console.error(err));

app.get("/produkte", async (req, res) => {
    try {
        const [rows] = await connection.execute("SELECT produktnr, bezeichnung, verkaufspreis, bestand FROM produkt");
        res.json(rows);
    } catch (err) {
        console.error(err);
        res.status(500).send("Fehler beim Abrufen der Produkte");
    }
});

app.get("/einkaeufe", async (req, res) => {
    try {
        const [rows] = await connection.execute(
            "SELECT einkaufsnr, produktnr, timestamp, anzahl FROM einkaeufe"
        );
        res.json(rows);
    } catch (err) {
        console.error(err);
        res.status(500).send("Fehler beim Abrufen der Einkäufe");
    }
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
