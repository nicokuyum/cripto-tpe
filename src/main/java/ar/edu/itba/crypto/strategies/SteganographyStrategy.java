package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.Image;

public interface SteganographyStrategy {
	
	Image save(Image original, byte[] data);
	
	byte[] get(Image image);
}
