package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.Image;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class LSB1Test {

	private Image img;

	@Before
	public void loadImage() {
		BufferedImage buf = new BufferedImage(20,20, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				buf.setRGB(0,0,0); 	// y means row, x means column, resulting img is
			}
		}
		img = new Image(buf);
	}

	@Test
	public void CorrectSteganographyTest() {
		byte[] data = {(byte)0xFF, (byte)0x00, (byte)0xFF};
		LSB1 strat = new LSB1();
		strat.save(img, data);
		byte[] savedLength = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0}; //24
		System.out.println(savedLength.length);
		for(int i = 0; i< 32; i++) {
			byte im = img.get(i);
			byte exp = savedLength[i];
			assert im == exp;
		}
		for(int i = 0; i< 24; i++) {
			if (i < 8)
				assert img.get(i + 32) == (byte)0x01;
			else if (i < 16)
				assert img.get(i + 32) == (byte)0x00;
			else
				assert img.get(i + 32) == (byte)0x01;
		}
		//Now that we know the saving works fine, lets check the getting.
		byte[] res = strat.get(img);
		assert res[0] == (byte) 0xFF;
		assert res[1] == (byte) 0x00;
		assert res[2] == (byte) 0xFF;
	}

}
