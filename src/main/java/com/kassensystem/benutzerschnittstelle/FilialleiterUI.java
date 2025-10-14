package com.kassensystem.benutzerschnittstelle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilialleiterUI extends JPanel {

	private JTable tblProdukte;
	private JTextField txtProduktnr;
	private JLabel lblProduktnr;
	private JTextField txtBezeichnung;
	private JTextField txtPreis;
	private JTextField txtBestand;
	private JLabel lblBezeichnung;
	private JLabel lblPreis;
	private JLabel lblBestand;
	private JButton btnHinzufuegen;
	private JButton btnAendern;
	private JButton btnLschen;

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public FilialleiterUI(Benutzerschnittstelle mainWindow) {
		setLayout(null);
		setSize(1000, 600);

		tblProdukte = new JTable();
		tblProdukte.setModel(new DefaultTableModel(
			new Object[][] {
				{ 1, "Beispielprodukt", 9.99, 42 },
			},
			new String[] {
				"Produktnr", "Bezeichnung", "Preis", "Bestand"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Double.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		JScrollPane scrProdukte = new JScrollPane(tblProdukte);
		scrProdukte.setBounds(10, 20, 970, 400);
		add(scrProdukte);
		
		txtProduktnr = new JTextField();
		txtProduktnr.setBounds(10, 460, 230, 20);
		add(txtProduktnr);
		txtProduktnr.setColumns(10);
		
		lblProduktnr = new JLabel("Produktnr");
		lblProduktnr.setBounds(10, 440, 70, 14);
		add(lblProduktnr);
		
		txtBezeichnung = new JTextField();
		txtBezeichnung.setColumns(10);
		txtBezeichnung.setBounds(260, 460, 230, 20);
		add(txtBezeichnung);
		
		txtPreis = new JTextField();
		txtPreis.setColumns(10);
		txtPreis.setBounds(500, 460, 230, 20);
		add(txtPreis);
		
		txtBestand = new JTextField();
		txtBestand.setColumns(10);
		txtBestand.setBounds(740, 460, 230, 20);
		add(txtBestand);
		
		lblBezeichnung = new JLabel("Bezeichnung");
		lblBezeichnung.setBounds(260, 440, 70, 14);
		add(lblBezeichnung);
		
		lblPreis = new JLabel("Preis");
		lblPreis.setBounds(500, 440, 70, 14);
		add(lblPreis);
		
		lblBestand = new JLabel("Bestand");
		lblBestand.setBounds(740, 440, 70, 14);
		add(lblBestand);
		
		btnHinzufuegen = new JButton("hinzufügen");
		btnHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHinzufuegen.setBounds(10, 530, 315, 23);
		add(btnHinzufuegen);
		
		btnAendern = new JButton("aendern");
		btnAendern.setBounds(335, 530, 315, 23);
		add(btnAendern);
		
		btnLschen = new JButton("löschen");
		btnLschen.setBounds(660, 530, 315, 23);
		add(btnLschen);
	}
}
