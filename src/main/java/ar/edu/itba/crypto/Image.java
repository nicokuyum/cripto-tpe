package ar.edu.itba.crypto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Image {

	private byte[][][] data;
	private int w;
	private int h;

	public Image(BufferedImage im) {
		this.w = im.getWidth();
		this.h = im.getHeight();
		this.data = new byte[w][h][3];
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++){
				int pixel = im.getRGB(i, j);
				data[i][j][0] = (byte) (pixel);
				data[i][j][1] = (byte) (pixel >>> 8);
				data[i][j][2] = (byte) (pixel >>> 16);
			}
		}
		System.out.println("fin");
	}

	public void put(int pos, byte b, int bitPos) {
		int pixelPos = pos / 3;
		int colorPos = pos % 3;
		int x = pixelPos % w;
		int y = pixelPos / w;
		byte bit = (byte)((b >>> bitPos) | 0xFE); // so 1111111(0|1)
		data[x][y][colorPos] = (byte)(data[x][y][colorPos] & bit);
	}

	public void put(int pos, byte b, int bitPos, int length) {

	}

}
