package ar.edu.itba.crypto;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTest {

	private Image img;

	@Before
	public void initializeImage() {
		BufferedImage buf = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
		buf.setRGB(0,0,Integer.MAX_VALUE); 	// y means row, x means column, resulting img is
		buf.setRGB(1,0,0);				//
		buf.setRGB(0,1,0xFFDDEE);		// FF FF FF		00 00 00
		buf.setRGB(1,1,0x0000AA);		// FF DD EE 	00 00 AA
		img = new Image(buf);
	}

	@Test
	public void getTheCorrectPixelTest() {
		assert img.get(0) == (byte) 0xFF;
		assert img.get(1) == (byte) 0xFF;
		assert img.get(2) == (byte) 0xFF;

		assert img.get(3) == (byte) 0x00;
		assert img.get(4) == (byte) 0x00;
		assert img.get(5) == (byte) 0x00;

		assert img.get(6) == (byte) 0xFF;
		assert img.get(7) == (byte) 0xDD;
		assert img.get(8) == (byte) 0xEE;

		assert img.get(9) == (byte) 0x00;
		assert img.get(10) == (byte) 0x00;
		assert img.get(11) == (byte) 0xAA;
	}

	@Test
	public void putInFirstPositionTest() {
		img.put(0, (byte) 0x00,0);
		assert img.get(0) == (byte) 0xFE;
	}

	@Test
	public void putInOtherPositionTest() {
		img.put(1, (byte) 0x00,0);
		assert img.get(1) == (byte) 0xFE;
	}

	@Test
	public void putBitInNotFirstPositionTest() {
		img.put(0, (byte) 0b011111,5);
		assert img.get(0) == (byte) 0xFE;
		img.put(0, (byte) 0b001000,3);
		assert img.get(0) == (byte) 0xFF;
		img.put(0, (byte) 0b01111111,7);
		assert img.get(0) == (byte) 0xFE;
	}
	@Test
	public void multiplePutInFirstPositionTest() {
		//TODO
	}

	@Test
	public void multiplePutInOtherPositionTest() {
		//TODO
	}
}
