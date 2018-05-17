package ar.edu.itba.crypto;

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
		File bmpFile = new File(args.image);
		BufferedImage image = ImageIO.read(bmpFile);
		Image i = new Image(image);
		if (args.embed) {

		}
		if (args.extract) {

		}
	}
}
