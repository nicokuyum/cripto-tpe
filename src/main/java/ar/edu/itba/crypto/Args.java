package ar.edu.itba.crypto;

import ar.edu.itba.crypto.converters.SteganographyConverter;
import ar.edu.itba.crypto.strategies.SteganographyStrategy;
import com.beust.jcommander.Parameter;

public class Args {

	@Parameter(names = "-embed", description = "Indica que se va a ocultar información")
	public boolean embed = false;

	@Parameter(names = "-extract", description = "Indica que se va a extraer información")
	public boolean extract = false;

	@Parameter(names = "-in", description = "Archivo que se va a ocultar")
	public String inFile;

	@Parameter(names = "-p", description = "Archivo bmp portador", required = true)
	public String image;

	@Parameter(names = "-out", description = "Archivo de salida obtenido")
	public String outFile;

	@Parameter(names = "-steg", description = "algoritmo de esteganografiado: LSB1, LSB4, LSBE", required = true, converter = SteganographyConverter.class)
	public SteganographyStrategy steg;

	@Parameter(names = "-a", description = "aes128 | aes192 | aes256 | des")
	public String a;

	@Parameter(names = "-m", description = "ecb | cfb | ofb | cbc")
	public String m;

	@Parameter(names = "-pass", description = "password de encripcion")
	public String password;
}
