package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;


public class LSBEnhanced implements SteganographyStrategy {

	@Override
	public Image save(Image original, BinaryFile file) {
		byte[] data = file.getData();
		int[] candidates = getCandidates(original);
		if (candidates.length * 8 < (data.length + 4) * 8)
			throw new IllegalStateException("Message is larger than accepted by the image, current length: " + data.length + " max accepted " + candidates.length / 8);

		int length = data.length;
		int candidatesIndex = 0;
		for (; candidatesIndex < 32; candidatesIndex+=1) {
			byte b = (byte)(length >>> (31 - candidatesIndex));
			original.put(candidates[candidatesIndex], b,0, 1);
		}
		for (int i = 0, dataLength = data.length; i < dataLength; i++) {
			byte b = data[i];
			candidatesIndex = putByte(b, original, candidatesIndex, candidates);
		}
		return original;
	}

	protected int[] getCandidates(Image original) {
		List<Integer> candidates = new ArrayList<>();
		for(int i = 0; i< original.length(); i++) {
			if ((original.get(i) & 0xFF) >= 254) {
				candidates.add(i);
			}
		}
		return Ints.toArray(candidates);
	}

	protected int putByte(byte b, Image original, int staringPosition, int[] candidates) {
		for (int j = 7; j>=0 ; j-= 1) {
			original.put(candidates[staringPosition], b, j, 1);
			staringPosition++;
		}
		return staringPosition;
	}
	@Override
	public BinaryFile get(Image image) {
		List<Byte> data = new ArrayList<>();
		int length = 0;
		int pos = 0;
		int[] candidates = getCandidates(image);
		for(; pos < 32; pos++) {
			length <<= 1;
			length |= image.getLSB(candidates[pos], 1);
		}
		length *=8;
		while (pos < length + 32) {
			data.add(getByte(image, pos, candidates));
			pos += 8;
		}

		return new BinaryFile(Bytes.toArray(data));
	}

	public byte getByte(Image image, int pos, int[] candidates) {
		byte b = 0;
		for (int i = 0; i< 8; i++, pos++) {
			b <<= 1;
			b |= image.getLSB(candidates[pos], 1);
		}
		return b;
	}

	public void analize(Image i) {
		int pos = 0;
		byte[] b = new byte[i.length() / 8];
		int j = 0;
		int[] candidates = getCandidates(i);
		while(pos < candidates.length) {
			b[j] = getByte(i, pos, candidates);
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

	@Override
	public BinaryFile getWithExtension(Image image) {
		BinaryFile fileWithoutExtension = get(image);
		int pos = (fileWithoutExtension.getData().length + 4) * 8;
		int[] candidates = getCandidates(image);
		StringBuilder extension = new StringBuilder();
		boolean endOfExtension = false;
		while(!endOfExtension) {
			byte b = getByte(image, pos, candidates);
			pos +=8;
			if(b == 0){
				endOfExtension = true;
			} else {
				extension.append((char) b);
			}
		}
		fileWithoutExtension.setExtension(extension.toString());
		return fileWithoutExtension;
	}

	@Override
	public Image saveWithExtension(Image original, BinaryFile file) {
		Image img = save(original, file);
		int pos = (file.getData().length + 4) * 8;
		int[] candidates = getCandidates(original);
		byte[] extension = file.getExtension().getBytes(Charsets.US_ASCII);
		for (int i = 0; i< extension.length;i++) {
			byte b = extension[i];
			pos = putByte(b, img, pos, candidates);
		}
		putByte((byte) 0, img, pos, candidates);
		return img;
	}
}
