package com.kassensystem.benutzerschnittstelle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KassiererUI extends JPanel {

	private static final long serialVersionUID = 1L;

	public KassiererUI(Benutzerschnittstelle mainWindow) {
		setLayout(null);
		setSize(1000, 600);

		JLabel lbl = new JLabel("Kassierer-Oberfläche");
		lbl.setBounds(420, 200, 200, 30);
		add(lbl);

		JButton btnZurueck = new JButton("Zurück");
		btnZurueck.setBounds(420, 300, 150, 40);
		add(btnZurueck);

		btnZurueck.addActionListener(e -> mainWindow.showMainPage());
	}
}
