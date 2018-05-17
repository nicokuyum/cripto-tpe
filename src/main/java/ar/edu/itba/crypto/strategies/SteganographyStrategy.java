package ar.edu.itba.crypto.strategies;

import java.awt.image.BufferedImage;

public interface SteganographyStrategy {
	
	public BufferedImage save(BufferedImage original, byte[] data);
	
	public byte[] get(BufferedImage image);
}
