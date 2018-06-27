package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;

public abstract class LSB implements SteganographyStrategy{
	protected abstract int step();

	int step = step();

	@Override
	public Image save(Image original, BinaryFile file) {
		byte[] data = file.getData();
		if (original.length() < (data.length + 4) * (8 / step))
			throw new IllegalStateException("Message is larger than accepted by the image");

		int length = data.length;
		int imagePos = step;
		for (; imagePos < 32; imagePos+= step) {
			byte b = (byte)(length >>> (32 - imagePos));
			original.put(imagePos, b,0, step);
		}
		for (int i = 0, dataLength = data.length; i < dataLength; i++) {
			byte b = data[i];
			imagePos = putByte(b, original, imagePos);
		}
		return original;
	}

	protected int putByte(byte b, Image original, int staringPosition) {
		for (int j = 7; j>=0 ; j-= step) {
			original.put(staringPosition, b, j, step);
			staringPosition+=step;
		}
		return staringPosition;
	}
	@Override
	public BinaryFile get(Image image) {
		List<Byte> data = new ArrayList<>();
		int length = 0;
		int pos = 0;
		for(; pos < (32 / step); pos++) {
			length <<= step;
			length |= image.getLSB(pos, step);
		}
		length *= (8 / step);
		while (pos < length + (32 / step)) {
			data.add(getByte(image, pos, step));
			pos += (8 / step);
		}

		return new BinaryFile(Bytes.toArray(data));
	}

	public byte getByte(Image image, int pos, int step) {
		byte b = 0;
		for (int i = 0; i< 8; i+= step, pos++) {
			b <<= step;
			b |= image.getLSB(pos, step);
		}
		return b;
	}

	public void analize(Image i) {
		int pos = 0;
		byte[] b = new byte[i.length() / 8];
		int j = 0;
		while(pos < i.length()) {
			b[j] = getByte(i, pos, step);
			j++;
			pos+= 8;
		}
		for( j = 0; j< 10;j++){
			System.out.println(Integer.toBinaryString(b[j] & 0xFF));
		}
		String s = new String(b, Charsets.US_ASCII);
		//String end = s.split("%%EOF")[1];
		//System.out.println(s.indexOf("%%EOF"));
		//for(char c : end.toCharArray()) {
			//System.out.println((byte) c);
		//}

		System.out.println(s);
	}
}
