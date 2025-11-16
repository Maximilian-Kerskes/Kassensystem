const API_BASE_URL = "http://localhost:8080";

async function ladeTabelle(typ) {
    const response = await fetch(`${API_BASE_URL}/${typ}`);
    const daten = await response.json();

    const tbody = document.querySelector("#datenTable tbody");
    const thead = document.querySelector("#tableHeader");

    tbody.innerHTML = "";
    thead.innerHTML = "";

    if (typ === "produkte") {
        thead.innerHTML = `
            <th>Produktnr</th>
            <th>Bezeichnung</th>
            <th>Verkaufspreis</th>
            <th>Bestand</th>
            <th>Aktion</th>
        `;
        daten.forEach(p => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${p.produktnr}</td>
                <td contenteditable="true">${p.bezeichnung}</td>
                <td contenteditable="true">${p.verkaufspreis}</td>
                <td contenteditable="true">${p.bestand}</td>
                <td>
                    <button onclick="speichereProdukt(this)">Speichern</button>
                    <button onclick="loescheProdukt(${p.produktnr})">Löschen</button>
                </td>
            `;
            tbody.appendChild(tr);
        });

        const trNeu = document.createElement("tr");
        trNeu.innerHTML = `
            <td>neu</td>
            <td contenteditable="true"></td>
            <td contenteditable="true"></td>
            <td contenteditable="true"></td>
            <td><button onclick="erstelleProdukt(this)">Hinzufügen</button></td>
        `;
        tbody.appendChild(trNeu);

    } else if (typ === "einkaeufe") {
        thead.innerHTML = `
            <th>Einkaufnr</th>
            <th>Produktnr</th>
            <th>Menge</th>
            <th>Datum</th>
        `;
        daten.forEach(e => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${e.einkaufsnr}</td>
                <td>${e.produktnr}</td>
                <td>${e.anzahl}</td>
                <td>${e.timestamp}</td>
            `;
            tbody.appendChild(tr);
        });
    }
}

async function speichereProdukt(button) {
    const tr = button.closest("tr");
    const produktnr = tr.children[0].textContent;
    const bezeichnung = tr.children[1].textContent;
    const verkaufspreis = tr.children[2].textContent;
    const bestand = tr.children[3].textContent;

    try {
        const response = await fetch(`${API_BASE_URL}/produkte`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ produktnr, bezeichnung, verkaufspreis, bestand })
        });
        if (response.ok) {
            alert("Produkt gespeichert!");
        } else {
            alert("Fehler beim Speichern");
        }
    } catch (err) {
        console.error(err);
        alert("Fehler beim Speichern");
    }
}

async function erstelleProdukt(button) {
    const tr = button.closest("tr");
    const bezeichnung = tr.children[1].textContent;
    const verkaufspreis = tr.children[2].textContent;
    const bestand = tr.children[3].textContent;

    try {
        const response = await fetch(`${API_BASE_URL}/produkte`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ bezeichnung, verkaufspreis, bestand })
        });
        if (response.ok) {
            alert("Produkt hinzugefügt!");
            ladeTabelle("produkte");
        } else {
            alert("Fehler beim Hinzufügen");
        }
    } catch (err) {
        console.error(err);
        alert("Fehler beim Hinzufügen");
    }
}

async function loescheProdukt(produktnr) {
    if (!confirm("Produkt wirklich löschen?")) return;

    try {
        const response = await fetch(`${API_BASE_URL}/produkte/${produktnr}`, { method: "DELETE" });
        if (response.ok) {
            alert("Produkt gelöscht!");
            ladeTabelle("produkte");
        } else {
            alert("Fehler beim Löschen");
        }
    } catch (err) {
        console.error(err);
        alert("Fehler beim Löschen");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    ladeTabelle("produkte");
});
