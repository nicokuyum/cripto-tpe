package ar.edu.itba.crypto.strategies;

import java.awt.image.BufferedImage;

public class LSB1 implements SteganographyStrategy{
	@Override
	public BufferedImage save(BufferedImage original, byte[] data) {
		return null;
	}

	@Override
	public byte[] get(BufferedImage image) {
		return new byte[0];
	}
}
