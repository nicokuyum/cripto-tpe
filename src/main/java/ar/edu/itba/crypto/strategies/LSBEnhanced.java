package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.Image;

public class LSBEnhanced implements SteganographyStrategy {

	@Override
	public Image save(Image original, byte[] data) {
		return null;
	}

	@Override
	public byte[] get(Image image) {
		return new byte[0];
	}
}
