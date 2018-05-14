package ar.edu.itba.crypto;

import com.beust.jcommander.JCommander;

public class Main {

	public static void main(String[] argv) {
		Args args = new Args();
		JCommander.newBuilder()
				.addObject(args)
				.build()
				.parse(argv);

		System.out.println("Embed = " + args.embed);
	}
}
