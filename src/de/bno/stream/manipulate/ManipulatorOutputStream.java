package de.bno.stream.manipulate;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Schreibt in den übergebenen OutputStream und manipuliert die zu schreibenden
 * bytes anhand des StreamManipulators
 * 
 * @author Marvin Bruns
 * 
 */
public class ManipulatorOutputStream extends OutputStream {

	private StreamManipulator manipulator;
	private OutputStream out;

	public ManipulatorOutputStream(OutputStream out,
			StreamManipulator manipulator) {
		this.out = out;
		this.manipulator = manipulator;
	}

	@Override
	public void write(int i) throws IOException {

		out.write(manipulator.manipulate(i));
	}

	@Override
	public void write(byte[] b) throws IOException {

		write(b, 0, b.length);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {

		byte[] write = new byte[len - off];

		for (int i = 0; i < len; i++) {
			write[i] = manipulator.manipulate(b[i + off]);
		}

		out.write(write, 0, write.length);
	}

}
