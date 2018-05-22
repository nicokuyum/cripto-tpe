package ar.edu.itba.crypto.algorithms;

public class AES256 extends AES {

	@Override
	protected int getKeyLength() {
		return 256;
	}
}
