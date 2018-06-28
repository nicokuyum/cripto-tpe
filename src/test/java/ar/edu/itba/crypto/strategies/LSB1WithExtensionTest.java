package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class LSB1WithExtensionTest {

	private Image img;

	@Before
	public void loadImage() {
		BufferedImage buf = new BufferedImage(20,20, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				buf.setRGB(0,0,0); 	// y means row, x means column
			}
		}
		img = new Image(buf);
	}

	@Test
	public void correctFileExtensionTest() {
		byte[] data = {(byte)0xFF, (byte)0x00, (byte)0xFF};
		LSB1WithoutExtension strat = new LSB1WithExtension();
		String extension = ".jpg";
		img = strat.save(img, new BinaryFile(data, extension));
		BinaryFile bf = strat.get(img);

		String saved = bf.getExtension();
		System.out.println(saved);
		assert saved.equals(extension);
		// Checking the correct saving of the binary data is done in the LSB1WithoutExtensionTest
		// So we just check the extension is correctly saved


	}
}
