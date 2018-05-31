package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;

public class LSB1WithoutExtension implements SteganographyStrategy {

	@Override
	public Image save(Image original, BinaryFile file) {
		byte[] data = file.getData();
		if (original.length() < (data.length + 4) * 8)
			throw new IllegalStateException("Message is larger than accepted by the image");

		int length = data.length * 8;
		int imagePos = 0;
		for (; imagePos < 32; imagePos++) {
			byte b = (byte)(length >>> (31 - imagePos));
			original.put(imagePos, b,0);
		}
		for (int i = 0, dataLength = data.length; i < dataLength; i++) {
			byte b = data[i];
			imagePos = putByte(b, original, imagePos);
		}
		return original;
	}

	protected int putByte(byte b, Image original, int staringPosition) {
		for (int j = 7; j >=0 ; j--) {
			original.put(staringPosition, b, j);
			staringPosition++;
		}
		return staringPosition;
	}
	@Override
	public BinaryFile get(Image image) {
		List<Byte> data = new ArrayList<>();
		int length = 0;
		int pos = 0;
		for(; pos < 32; pos++) {
			length <<=1;
			length |= image.getLSB(pos);
		}

		while (pos < length + 32) {
			data.add(getByte(image, pos));
			pos +=8;
		}

		return new BinaryFile(Bytes.toArray(data));
	}

	public byte getByte(Image image, int pos) {
		byte b = 0;
		for (int i = 0; i< 8; i++, pos++) {
			b <<= 1;
			b |= image.getLSB(pos);
		}
		return b;
	}
}
