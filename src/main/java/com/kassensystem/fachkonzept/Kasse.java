package com.kassensystem.fachkonzept;

import com.fazecast.jSerialComm.*;
import java.io.IOException;

public class Kasse {
	private static final String KASSEN_NAME = "USB-Serial Controller";
	private SerialPort serialPort;

	public Kasse() throws IOException {
		this(getSerialPort());
	}

	public Kasse(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	private static SerialPort getSerialPort() throws IOException {
		SerialPort[] serialPorts = SerialPort.getCommPorts();
		for(SerialPort port : serialPorts) {
			if(port.getPortDescription().equals(KASSEN_NAME)){
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
