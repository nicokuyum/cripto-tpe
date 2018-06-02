package ar.edu.itba.crypto;

import ar.edu.itba.crypto.strategies.LSB1WithExtension;
import ar.edu.itba.crypto.strategies.LSB1WithoutExtension;
import ar.edu.itba.crypto.strategies.SteganographyStrategy;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
			BinaryFile result = args.steg.get(i);
			((LSB1WithExtension)args.steg).analize(i);
			 try (FileOutputStream fos = new FileOutputStream(args.outFile + result.getExtension())) {
				fos.write(result.getData());
				//fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
			}
		} else if (args.embed){
			Path inFile = Paths.get(args.inFile);
			String extension = com.google.common.io.Files.getFileExtension(args.inFile);
			BinaryFile bf = new BinaryFile(Files.readAllBytes(inFile), extension);
			args.steg.save(i, bf);
		} else {
			throw new ParameterException("You need to choose -embed or -extract");
		}
	}
}
