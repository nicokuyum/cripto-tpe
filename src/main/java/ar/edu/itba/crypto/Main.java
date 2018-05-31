package ar.edu.itba.crypto;

import ar.edu.itba.crypto.strategies.LSB1WithExtension;
import ar.edu.itba.crypto.strategies.LSB1WithoutExtension;
import ar.edu.itba.crypto.strategies.SteganographyStrategy;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		File bmpFile = new File("image.bmp");
		BufferedImage image = ImageIO.read(bmpFile);
		Image i = new Image(image);
		SteganographyStrategy strategy = new LSB1WithoutExtension();
		byte[] data = "I will be hidden".getBytes();
		Image modified = strategy.save(i, new BinaryFile(data));
		BinaryFile retrieved = strategy.get(modified);

		System.out.println("Retrieved message is: " + new String(retrieved.getData()) + " with extension: " + retrieved.getExtension());
	}
}
