package com.kassensystem.fachkonzept;

import java.sql.Timestamp;

public class Bestandsveraenderung {
	private int lognr;
	private String produktnr;
	private Timestamp veraendert;
	private String feldname;
	private String alterwert;
	private String neuerwert;

	public Bestandsveraenderung(int lognr, String produktnr, Timestamp veraendert, String feldname,
			String alterwert, String neuerwert) {
		this.lognr = lognr;
		this.produktnr = produktnr;
		this.veraendert = veraendert;
		this.feldname = feldname;
		this.alterwert = alterwert;
		this.neuerwert = neuerwert;
	}

	public int getLognr() {
		return lognr;
	}

	public String getProduktnr() {
		return produktnr;
	}

	public Timestamp getVeraendert() {
                return veraendert;
        }

	public String getFeldname() {
                return feldname;
        }

        public String getAlterwert() {
                return alterwert;
        }

        public String getNeuerwert() {
                return neuerwert;
        }
}
