package com.github.brunothg.stream.autokey;

import java.util.Deque;
import java.util.LinkedList;

import com.github.brunothg.stream.manipulate.StreamManipulator;

/**
 * Realisierung der Autokey-Vigenère-Verschlüsselung<br>
 * 
 * @author Marvin Bruns
 * 
 */
public class AutoKey implements StreamManipulator {

	private static final int A_VALUE = 97;

	public static final int MODUS_ENCODE = 0x01;
	public static final int MODUS_DECODE = 0x02;
	protected static final int MODUS_NONE = 0x00;

	private String key;
	private int index;
	private Deque<Integer> lastKeyByte;
	private int modus;

	/**
	 * Erzeugt einen neuen AutoKey mit vorgegebenen Modus.
	 * 
	 * @param key
	 *            Der Schlüssel zum Ver/Ent schlüsseln
	 * @param modus
	 *            Der Modus in dem operiert wird. <b>MODUS_ENCODE</b> bzw.
	 *            <b>MODUS_DECODE</b>
	 */
	public AutoKey(String key, int modus) {
		this.key = key;
		this.modus = modus;
		this.index = 0;
		this.lastKeyByte = new LinkedList<Integer>();
	}

	/**
	 * Erzeugt einen neuen AutoKey mit nicht bekanntem Modus.<br>
	 * Wird zum ersten Mal eine Methode benutzt, die sich auf einen speziellen
	 * Modus bezieht wird dieser gesetzt.
	 * 
	 * @param key
	 *            Der Schlüssel zu Ver/Ent schlüsseln
	 */
	public AutoKey(String key) {
		this(key, MODUS_NONE);
	}

	/**
	 * Verschlüsselt das übergebene byte
	 * 
	 * @param b
	 *            zu verschlüsselndes byte
	 * @return verschlüsseltes byte
	 */
	public byte encode(byte b) {
		if (modus == MODUS_DECODE) {
			throw new ModeException(
					"Autokey is in decode modus. Encode not possible.");
		} else if (modus == MODUS_NONE) {
			modus = MODUS_ENCODE;
		}

		int ret = toInt(b);

		int keyByte = getKeyByte(b);
		lastKeyByte.add(new Integer(b));

		ret = (ret + keyByte) % 256;

		return (byte) ret;
	}

	/**
	 * Entschlüsselt das übergebene byte
	 * 
	 * @param b
	 *            zu entschlüsselndes byte
	 * @return entschlüsseltes byte
	 */
	public byte decode(byte b) {
		if (modus == MODUS_ENCODE) {
			throw new ModeException(
					"Autokey is in encode modus. Decode not possible.");
		} else if (modus == MODUS_NONE) {
			modus = MODUS_DECODE;
		}

		int ret = toInt(b);

		int keyByte = getAddInvers(getKeyByte(b));

		ret = (ret + keyByte) % 256;
		lastKeyByte.add(ret);

		return (byte) ret;
	}

	/**
	 * Gibt den benutzten Schlüssel
	 * 
	 * @return Den benutzten Schlüssel
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Berechnet das additive Inverse zu <b>i</b>
	 * 
	 * @param i
	 * @return
	 */
	private int getAddInvers(int i) {
		int ret = i;

		ret = ret % 256;
		ret = 256 - ret;

		return ret;
	}

	/**
	 * Ermittelt den Schlüsselwert zu dem byte <b>b</b>
	 * 
	 * @param b
	 *            zu verschlüsselndes byte
	 * @return Schlüsselwert
	 */
	private int getKeyByte(byte b) {
		int keyByte;

		if (index < key.length()) {
			keyByte = getDif(key.charAt(index));

			index++;
		} else {
			keyByte = getDif(lastKeyByte.poll());
		}

		return keyByte;
	}

	/**
	 * Differenzfunktion
	 * 
	 * @param c
	 * @return
	 */
	private int getDif(int c) {
		int ret = c - A_VALUE;

		return ret;
	}

	private static int toInt(byte signedbyte) {
		return signedbyte & 0xFF;
	}

	/**
	 * Gibt den Modus des AutoKeys
	 * 
	 * @return aktuellen Modus
	 */
	public int getModus() {
		return modus;
	}

	@Override
	public byte manipulate(byte b) {
		if (modus == MODUS_NONE) {
			throw new ModeException("No mode set.");
		}

		if (modus == MODUS_ENCODE) {
			return encode(b);
		} else {
			return decode(b);
		}
	}

	@Override
	public int manipulate(int b) {
		return manipulate((byte) b);
	}
}
