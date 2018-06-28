package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import com.google.common.base.Charsets;

import java.nio.charset.Charset;

public class LSB1WithExtension extends LSB1WithoutExtension {

	@Override
	public BinaryFile get(Image image) {
		BinaryFile fileWithoutExtension = super.get(image);
		int pos = (fileWithoutExtension.getData().length + 4) * 8;
		StringBuilder extension = new StringBuilder();
		boolean endOfExtension = false;
		while(!endOfExtension) {
			byte b = getByte(image, pos, step);
			pos += 8;
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
	public Image save(Image original, BinaryFile file) {
		Image img = super.save(original, file);
		int pos = (file.getData().length + 4) * 8;
		byte[] extension = file.getExtension().getBytes(Charsets.US_ASCII);
		for (int i = 0; i< extension.length;i++) {
			byte b = extension[i];
			pos = putByte(b, img, pos);
		}
		putByte((byte) 0, img, pos);
		return img;
	}
}
