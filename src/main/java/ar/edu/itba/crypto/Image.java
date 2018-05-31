package ar.edu.itba.crypto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Image Class, gets an ImageBuffer and store the image information in a byte matrix.
 * an image consist of a sequence of bytes, 3 bytes per pixel, one for red, one for green and one for blue
 * The size of the matrix is h x w.
 *
 */
public class Image {

	private byte[][][] data;
	private int w;
	private int h;
	private int length;

	public Image(BufferedImage im) {
		this.w = im.getWidth();
		this.h = im.getHeight();
		length = w * h * 3;
		this.data = new byte[h][w][3];
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++){
				int pixel = im.getRGB(i, j);
				data[j][i][0] = (byte) (pixel >> 16);
				data[j][i][1] = (byte) (pixel >> 8);
				data[j][i][2] = (byte) (pixel);
			}
		}
	}

	/**
	 * The position to add the byte is as follows:
	 * starting in the top left corner, advance pos / 3 pixels, and change the pos % 3 color of that pixel.
	 * the position advances to the right.
	 *
	 * @param pos the position of the byte in the image
	 * @param b byte containg the bit to add to the last position of the image byte
	 * @param bitPos the position of the bit inside the b to be added.
	 */
	public void put(int pos, byte b, int bitPos) {
		int pixelPos = pos / 3;
		int colorPos = pos % 3;
		int x = pixelPos % w;
		int y = pixelPos / w;
		byte bit = (byte)(b >>> bitPos & 0x01); // so 0000000(0|1)
		if (bit == 0) {
			data[y][x][colorPos] = (byte)(data[y][x][colorPos] & 0xFE);
		} else {
			data[y][x][colorPos] = (byte)(data[y][x][colorPos] | 0x01);
		}

	}

	public void put(int pos, byte b, int bitPos, int length) {

	}

	public int get(int x, int y) {
		return (data[y][x][0] << 16 | data[y][x][1] << 8 | data[y][x][0] << 16);
	}

	public byte get(int pos) {
		int pixelPos = pos / 3;
		int colorPos = pos % 3;
		int x = pixelPos % w;
		int y = pixelPos / w;
		return data[y][x][colorPos];
	}

	public byte getLSB(int pos) {
		int pixelPos = pos / 3;
		int colorPos = pos % 3;
		int x = pixelPos % w;
		int y = pixelPos / w;
		return (byte)(data[y][x][colorPos] & 0x01);
	}

	public int length() {
		return length;
	}
}
