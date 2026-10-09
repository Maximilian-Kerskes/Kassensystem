package com.kassenssystem.fachkonzept;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fazecast.jSerialComm.SerialPort;
import com.kassensystem.fachkonzept.Kasse;

public class KasseTest {
	@Test
	public void openKasseTest(){
		SerialPort serialPort = mock(SerialPort.class);
		when(serialPort.isOpen()).thenReturn(false);
		when(serialPort.openPort()).thenReturn(true);
		when(serialPort.writeBytes(any(byte[].class), eq(5))).thenReturn(6);
		when(serialPort.closePort()).thenReturn(true);

		Kasse dieKasse = new Kasse(serialPort);

		assertDoesNotThrow(dieKasse::openKasse);

		verify(serialPort).openPort();
		verify(serialPort).writeBytes(any(byte[].class), eq(5));
		verify(serialPort).closePort();
	}

}
