package com.github.brunothg.stream.manipulate;

/**
 * Inteface für StreamManipulatoren<br>
 * 
 * @see ManipulatorInputStream
 * @see ManipulatorOutputStream
 * 
 * @author Marvin Bruns
 * 
 */
public interface StreamManipulator {

	/**
	 * Manipuliert das byte <b>b</b>.
	 * 
	 * @param b
	 *            zu manipulierendes byte
	 * @return manipuliertes byte
	 */
	public byte manipulate(byte b);

	/**
	 * Manipuliert den Integer <b>i</b>.
	 * 
	 * @param b
	 *            zu manipulierender Integer
	 * @return manipulierter Integer
	 */
	public int manipulate(int b);
}
