package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.Image;

public class LSB1 implements SteganographyStrategy {

	@Override
	public Image save(Image original, byte[] data) {
		for (byte b: data) {
		}
		return null;
	}

	@Override
	public byte[] get(Image image) {
		return new byte[0];
	}
}
