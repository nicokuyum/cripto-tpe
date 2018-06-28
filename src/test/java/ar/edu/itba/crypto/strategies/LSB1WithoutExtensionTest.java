package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class LSB1WithoutExtensionTest {

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
	public void correctSteganographyTest() {
		byte[] data = {(byte)0xFF, (byte)0x00, (byte)0xFF};
		LSB1WithoutExtension strat = new LSB1WithoutExtension();
		strat.save(img, new BinaryFile(data));
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
		byte[] res = strat.get(img).getData();
		assert res[0] == (byte) 0xFF;
		assert res[1] == (byte) 0x00;
		assert res[2] == (byte) 0xFF;
	}

	@Test
	public void setByteTest() {
		LSB1WithoutExtension strat = new LSB1WithoutExtension();
		strat.putByte((byte) 0b11101010, img, 0);
		assert img.getLSB(0) == 1;
		assert img.getLSB(1) == 1;
		assert img.getLSB(2) == 1;
		assert img.getLSB(3) == 0;
		assert img.getLSB(4) == 1;
		assert img.getLSB(5) == 0;
		assert img.getLSB(6) == 1;
		assert img.getLSB(7) == 0;

		strat.putByte((byte) 0b10101010, img, 16);
		for(int i = 16; i< 8 + 16; i++) {
			assert img.getLSB(i) == ((i % 2 == 0)?1:0);
		}
	}

	@Test
	public void getByteTest() {
		LSB1WithoutExtension strat = new LSB1WithoutExtension();
		strat.putByte((byte) 0b10111000, img, 8);
		byte g = strat.getByte(img, 8);
		assert g == (byte) 0b10111000;
	}
}
