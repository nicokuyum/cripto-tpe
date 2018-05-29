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
	public void initializeImage() throws IOException {
		BufferedImage buf = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
		buf.setRGB(0,0,Integer.MAX_VALUE); 	// y means row, x means column, resulting img is
		buf.setRGB(1,0,0);				//
		buf.setRGB(0,1,Integer.MAX_VALUE);	// W B
		buf.setRGB(1,1,0);				// W B
		img = new Image(buf);
	}

	@Test
	public void correctPixelSelectionTest() {
		assert true;
	}

	@Test
	public void getTheCorrectPixelTest() {

	}

	@Test
	public void putInFirstPositionTest() {

	}

	@Test
	public void putInOtherPositionTest() {

	}

	@Test
	public void multiplePutInFirstPositionTest() {

	}

	@Test
	public void multiplePutInOtherPositionTest() {

	}
}
