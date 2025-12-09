const API_BASE_URL = "http://localhost:8080/api";
const tbody = document.querySelector("#buchungenTabelle tbody");
const buchungen = [];

function berechneGesamtbetrag() {
    return buchungen.reduce(
        (summe, pos) => summe + pos.menge * pos.produkt.verkaufspreis,
        0
    );
}

async function oeffneKasse() {
    const res = await fetch(`${API_BASE_URL}/kasse`);
    if (!res.ok) {
        alert("Fehler beim Öffnen der Kasse");
    }
}

function renderBuchungen() {
    tbody.innerHTML = "";
    buchungen.forEach((pos, index) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${pos.produkt.produktNummer}</td>
            <td>${pos.produkt.bezeichnung}</td>
            <td>${pos.menge}</td>
            <td>${new Date(pos.zeitpunkt).toLocaleString()}</td>
            <td><button data-index="${index}">Löschen</button></td>
        `;
        tbody.appendChild(tr);
    });

    tbody.querySelectorAll("button").forEach((btn) => {
        btn.addEventListener("click", (e) => {
            const index = e.target.dataset.index;
            buchungen.splice(index, 1);
            renderBuchungen();
        });
    });

    document.getElementById("gesamtbetrag").textContent =
        berechneGesamtbetrag().toFixed(2);
}

async function bucheProdukt() {
    const produktNr = document.getElementById("produkt").value.trim();
    const menge = parseInt(document.getElementById("menge").value);

    if (!produktNr || !menge) {
        alert("Bitte Produktnummer und Menge eingeben");
        return;
    }

    const res = await fetch(`${API_BASE_URL}/produkte/${produktNr}`);
    if (!res.ok) {
        alert("Produkt nicht gefunden!");
        return;
    }

    const produkt = await res.json();

    buchungen.push({
        produkt,
        menge,
        zeitpunkt: new Date().toISOString(),
    });

    renderBuchungen();

    document.getElementById("produkt").value = "";
    document.getElementById("menge").value = 1;
    document.getElementById("produkt").focus();
}

document.getElementById("produkt").addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
        e.preventDefault();
        document.getElementById("menge").focus();
        document.getElementById("menge").select();
    }
});

document.getElementById("menge").addEventListener("keydown", async (e) => {
    if (e.key === "Enter") {
        e.preventDefault();
        await bucheProdukt();
    }
});

document.getElementById("buchen").addEventListener("click", async () => {
    await bucheProdukt();
});

document.getElementById("abschicken").addEventListener("click", async () => {
    if (buchungen.length === 0) {
        alert("Keine Buchungen zum Abschicken!");
        return;
    }

    const einkaufsNrRes = await fetch(`${API_BASE_URL}/einkaeufe/naechste-einkaufsnr`);
    if (!einkaufsNrRes.ok) {
        alert("Fehler beim Abrufen der Einkaufsnummer!");
        return;
    }

    const einkaufsnummer = await einkaufsNrRes.json();

    for (const pos of buchungen) {
        const response = await fetch(`${API_BASE_URL}/positionen`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                einkaufsnummer: einkaufsnummer,
                produktnummer: pos.produkt.produktNummer,
                menge: pos.menge,
                zeitpunkt: pos.zeitpunkt,
            }),
        });

        if (!response.ok) {
            alert("Fehler beim Erstellen einer Position!");
            return;
        }
    }

    buchungen.length = 0;
    renderBuchungen();
    await oeffneKasse();

    alert(`Alle Buchungen für Einkaufsnummer ${einkaufsnummer} abgeschickt!`);
});
