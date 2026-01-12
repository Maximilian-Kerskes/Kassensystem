package com.kassensystem.fachkonzept;

public enum Steuersatz {
	REGULAER_19(0.19),
	REDUZIERT_7(0.07);

	Steuersatz(double steuersatz) {
		this.steuersatz = steuersatz;
	}

	private final double steuersatz;

	public double getSteuersatz() {
		return steuersatz;
	}

	public static Steuersatz fromString(String name) {
		// Safe mapping from DB string to enum
		return Steuersatz.valueOf(name.toUpperCase());
	}
}
