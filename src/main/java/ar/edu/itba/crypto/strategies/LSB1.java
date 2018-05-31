package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.Image;
import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;

public class LSB1 implements SteganographyStrategy {

	@Override
	public Image save(Image original, byte[] data) {
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
			for (int j = 0; j < 8; j++) {
				original.put(imagePos, b, j);
				imagePos++;
			}
		}
		return original;
	}

	@Override
	public byte[] get(Image image) {
		List<Byte> data = new ArrayList<>();
		int bytePos = 0;
		byte b = 0;
		int length = 0;
		for(int i = 0; i< 32; i++) {
			length <<=1;
			length |= image.get(i);
		}
		for(int i = 0; i < length; i++) {
			b <<= 1;
			b |= image.get(i + 32);
			bytePos++;
			if(bytePos == 8) {
				data.add(b);
				b = 0x00;
				bytePos = 0;
			}
		}
		return Bytes.toArray(data);
	}
}
