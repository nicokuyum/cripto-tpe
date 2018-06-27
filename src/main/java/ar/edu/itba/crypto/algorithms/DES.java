package ar.edu.itba.crypto.algorithms;

public class DES extends EncryptionAlgorithm {
	@Override
	String getName() {
		return "DES";
	}

	@Override
	int getKeyLength() {
		return 64;
	}

	/*@Override
	public byte[] encrypt(byte[] input, EncryptionMode mode, byte[] key) {
		return new byte[0];
	}

	@Override
	public byte[] decrypt(byte[] input, EncryptionMode mode, byte[] key) {
		return new byte[0];
	}*/
}
