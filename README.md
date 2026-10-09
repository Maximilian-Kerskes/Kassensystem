# Kassensystem 

## API Dokumentation

### Produkte

- GET:   `/api/produkte`
- GET:   `/api/produkte/{id}`
- POST:  `/api/produkte`
- PUT:   `/api/produkte/{id}`
- PATCH: `/api/produkte/{id}`

### Einkaeufe

- GET:   `/api/einkaeufe`
- POST:   `/api/einkaeufe`

### Bericht

- GET: `/api/bestandsliste/csv`
- GET: `/api/produktlog/csv`
- GET: `/api/umsatz/csv`

### Kasse

- POST:  `/api/kasse/oeffnen`
- POST:  `/api/bon`
- POST:  `/api/rueckgeld`


## Quickstart
```bash
./dependencies-start.sh
mvn clean package
mvn spring-boot:run
```

