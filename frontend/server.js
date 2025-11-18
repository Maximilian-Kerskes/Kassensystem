import express from "express";
import path from "path";
import { fileURLToPath } from "url";
import cors from "cors";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
const port = process.env.PORT || 3000;

app.use(cors({
    origin: "http://localhost:3000"
}));

app.use(express.static(path.join(__dirname, "public")));

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
