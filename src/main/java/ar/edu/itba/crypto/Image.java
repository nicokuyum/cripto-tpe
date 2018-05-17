package ar.edu.itba.crypto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Image {

	private byte[][][] data;

	public Image(BufferedImage im) {
		this.data = new byte[im.getWidth()][im.getHeight()][3];
		for(int i = 0; i < im.getWidth(); i++) {
			for(int j = 0; j < im.getHeight(); j++){
				int pixel = im.getRGB(i, j);
				data[i][j][0] = (byte) (pixel);
				data[i][j][1] = (byte) (pixel >> 8);
				data[i][j][2] = (byte) (pixel >> 16);
			}
		}
		System.out.println("fin");
	}

}
