package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;

public interface SteganographyStrategy {
	
	Image save(Image original, BinaryFile file);

	Image saveWithExtension(Image original, BinaryFile file);
	
	BinaryFile get(Image image);

	BinaryFile getWithExtension(Image image);
}
