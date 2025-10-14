package com.kassensystem.fachkonzept;

import com.fazecast.jSerialComm.*;
import java.io.IOException;

public class Kasse {
	private String kassenName;
	private SerialPort serialPort;

	public Kasse() throws IOException {
		kassenName = "USB-Serial Controller";
		serialPort = getSerialPort();
	}

	private SerialPort getSerialPort() throws IOException {
		SerialPort[] serialPorts = SerialPort.getCommPorts();
		for(SerialPort port : serialPorts) {
			if(port.getPortDescription().equals(kassenName)){
				return port;
			}
		}
		throw new IOException("Serial Port konnte nicht gefunden werden");
	}

	public void openKasse() throws IOException {
		if (serialPort == null) {
			throw new IOException("Serial port ist nicht Initialisiert");
		}
		if(!serialPort.isOpen()){
			if(!serialPort.openPort()) {
				throw new IOException("Der Serial Port konnte nicht geoffnet werden");
			}
		}
		byte[] message = {'M', 'E', 'L', 'L', 'O', 'N'};
		if(serialPort.writeBytes(message, 5) < 0) {
			throw new IOException("Das Schreiben der Nachricht zum Serial Port war nicht erfolgreich");
		}

		if(!serialPort.closePort()){
                        throw new IOException("Der Serial Port konnte nicht geschlossen werden");
		}
	}
}
