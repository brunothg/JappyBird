package com.github.brunothg.stream.manipulate;

import java.io.IOException;
import java.io.InputStream;

/**
 * Liest aus dem übergebenen InputStream und manipuliert die gelesenen bytes
 * anhand des StreamManipulators
 * 
 * @author Marvin Bruns
 * 
 */
public class ManipulatorInputStream extends InputStream {

	private StreamManipulator manipulator;
	private InputStream in;

	public ManipulatorInputStream(InputStream in, StreamManipulator manipulator) {
		this.in = in;
		this.manipulator = manipulator;
	}

	@Override
	public int read() throws IOException {

		return manipulator.manipulate(in.read());
	}

	@Override
	public int read(byte[] b) throws IOException {

		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {

		int ret = in.read(b, off, len);

		for (int i = 0; i < len; i++) {
			b[i + off] = manipulator.manipulate(b[i + off]);
		}

		return ret;
	}

}
