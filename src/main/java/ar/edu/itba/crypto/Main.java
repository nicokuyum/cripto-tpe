package ar.edu.itba.crypto;

import ar.edu.itba.crypto.strategies.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;
import sun.nio.cs.US_ASCII;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] argv) throws IOException {
		Args args = new Args();
		JCommander.newBuilder()
				.addObject(args)
				.build()
				.parse(argv);

		System.out.println("Embed = " + args.embed);

		if (args.embed && args.extract) {
			throw new ParameterException("Can't encrypt and decrypt as the same time");
		}
		File bmpFile = new File(args.image);
		BufferedImage image = ImageIO.read(bmpFile);
		Image i = new Image(image);
		if (args.extract) {
			//Validate out is present and not "";
			BinaryFile result;
			if(args.password != null) {
				result = args.steg.get(i);
				try {
					byte[] r = args.encryptionAlgorithm.decrypt(result.getData(), args.mode, args.password);
					result = setBinaryFile(r);
					System.out.println(new String(r, Charsets.UTF_8));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				result = args.steg.getWithExtension(i);
			}
			//((LSB)args.steg).analize(i);
			 try (FileOutputStream fos = new FileOutputStream(args.outFile + result.getExtension())) {
				fos.write(result.getData());
				//fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
			}
		} else if (args.embed){
			Path inFile = Paths.get(args.inFile);
			String extension = "." + com.google.common.io.Files.getFileExtension(args.inFile);
			BinaryFile bf = new BinaryFile(Files.readAllBytes(inFile), extension);
			Image f = null;
			if(args.password != null) {
				try {
					byte[] b = createByteArray(bf);
					byte[] encrypted = args.encryptionAlgorithm.encrypt(b,args.mode, args.password);
					bf.setData(encrypted);
					f = args.steg.save(i, bf);
					System.out.println("saved");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				f = args.steg.saveWithExtension(i, bf);
			}

			for(int x = 0; x < image.getWidth(); x++) {
				for(int y = 0 ; y < image.getHeight(); y++) {
					image.setRGB(x, y, f.get(x, y));
				}
			}
			File newF = new File(args.outFile);
			ImageIO.write(image, "bmp", newF);
		} else {
			throw new ParameterException("You need to choose -embed or -extract");
		}
	}

	private static BinaryFile setBinaryFile(byte[] r) {
		int length = (r[0] & 0xFF) << 24 | (r[1] & 0xFF) << 16 | (r[2] & 0xFF) << 8 | (r[3] & 0xFF);
		int rIndex = 4;
		byte[] data = new byte[length];
		for(int i = 0; i< length;i++, rIndex++) {
			data[i] = r[rIndex];
		}
		List<Byte> extensionBytes = new ArrayList<>();

		while(r[rIndex] != 0) {
			extensionBytes.add(r[rIndex++]);
		}
		String extension = new String(Bytes.toArray(extensionBytes), Charsets.US_ASCII);
		return new BinaryFile(data, extension);
	}

	private static byte[] createByteArray(BinaryFile bf) {
		int length = bf.getData().length;
		byte[] extension = (bf.getExtension() + "\0").getBytes(Charsets.US_ASCII);
		byte[] result = new byte[length + 4 + extension.length];
		result[0] = (byte)(length >> 24);
		result[1] = (byte)(length >> 16);
		result[2] = (byte)(length >> 8);
		result[3] = (byte) length;

		int resultIndex = 4;
		for(int i = 0; i< length;i++, resultIndex++){
			result[resultIndex] = bf.getData()[i];
		}
		for(int i = 0; i< extension.length;i++, resultIndex++) {
			result[resultIndex] = extension[i];
		}
		return result;
	}
}
