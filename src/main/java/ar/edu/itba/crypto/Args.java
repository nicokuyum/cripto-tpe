package ar.edu.itba.crypto;

import com.beust.jcommander.Parameter;

public class Args {

	@Parameter(names = "-embed", description = "Indica que se va a ocultar información")
	public boolean embed = false;
}
