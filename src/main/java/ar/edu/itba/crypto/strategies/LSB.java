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
		int maxSize = original.length() / step;
		if (maxSize < (data.length + 4) * (8 / step))
			throw new IllegalStateException("Message is larger than accepted by the image, current size: " + file.getData().length + " max accepted: " + maxSize);

		int length = data.length;
		int imagePos = 0;
		for (int i = 3; i >=0; i--) {
			byte s = (byte)(length >>> (8 * i));
			imagePos = putByte(s,original, imagePos);
			//original.put(imagePos, b,0, step);
		}
		for (int i = 0, dataLength = data.length; i < dataLength; i++) {
			byte b = data[i];
			imagePos = putByte(b, original, imagePos);
		}
		return original;
	}

	@Override
	public Image saveWithExtension(Image original, BinaryFile file) {
		Image img = save(original, file);
		int pos = (file.getData().length + 4) * (8 / step);
		byte[] extension = file.getExtension().getBytes(Charsets.US_ASCII);
		for (int i = 0; i< extension.length;i++) {
			byte b = extension[i];
			pos = putByte(b, img, pos);
		}
		putByte((byte) 0, img, pos);
		return img;
	}

	protected int putByte(byte b, Image original, int staringPosition) {
		for (int j = 8 - step; j>=0 ; j-= step) {
			original.put(staringPosition, b, j, step);
			staringPosition++;
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
		length = length * (8 / step) + (32 /step);
		while (pos < length) {
			data.add(getByte(image, pos, step));
			pos += (8 / step);
		}

		return new BinaryFile(Bytes.toArray(data));
	}

	@Override
	public BinaryFile getWithExtension(Image image) {
		BinaryFile fileWithoutExtension = get(image);
		int pos = (fileWithoutExtension.getData().length + 4) * (8 / step);
		StringBuilder extension = new StringBuilder();
		boolean endOfExtension = false;
		while(!endOfExtension) {
			byte b = getByte(image, pos, step);
			pos += (8 / step);
			if(b == 0){
				endOfExtension = true;
			} else {
				extension.append((char) b);
			}
		}
		fileWithoutExtension.setExtension(extension.toString());
		return fileWithoutExtension;
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
