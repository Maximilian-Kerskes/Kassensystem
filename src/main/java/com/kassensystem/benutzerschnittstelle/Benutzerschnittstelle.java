package com.kassensystem.benutzerschnittstelle;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class Benutzerschnittstelle {

	private JFrame frmKassensystem;
	private JPanel mainPanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Benutzerschnittstelle window = new Benutzerschnittstelle();
				window.frmKassensystem.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public Benutzerschnittstelle() {
		initialize();
	}

	private void initialize() {
		frmKassensystem = new JFrame();
		frmKassensystem.setTitle("Kassensystem");
		frmKassensystem.setBounds(100, 100, 1000, 800);
		frmKassensystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKassensystem.getContentPane().setLayout(new BorderLayout());
		frmKassensystem.setSize(1000, 600);

		mainPanel = new JPanel(null);
		showMainMenu();

		frmKassensystem.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	private void showMainMenu() {
		mainPanel.removeAll();
		mainPanel.setLayout(null);

		JButton btnKassierer = new JButton("Kassierer");
		btnKassierer.setFont(new Font("Tahoma", Font.PLAIN, 38));
		btnKassierer.setBounds(400, 300, 200, 50);
		btnKassierer.addActionListener(this::onKassiererClicked);
		mainPanel.add(btnKassierer);

		JButton btnFilialleiter = new JButton("Filialleiter");
		btnFilialleiter.setFont(new Font("Tahoma", Font.PLAIN, 38));
		btnFilialleiter.setBounds(400, 400, 200, 50);
		btnFilialleiter.addActionListener(this::onFilialleiterClicked);
		mainPanel.add(btnFilialleiter);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private void onKassiererClicked(ActionEvent e) {
		showPanel(new KassiererUI(this));
	}

	private void onFilialleiterClicked(ActionEvent e) {
		showPanel(new FilialleiterUI(this));
	}

	public void showPanel(JPanel panel) {
		mainPanel.removeAll();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void showMainPage() {
		showMainMenu();
	}
}
