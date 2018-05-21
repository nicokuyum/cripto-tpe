package ar.edu.itba.crypto.algorithms;

public interface EncryptionAlgorithm {

	byte[] encrypt(byte[] input, EncryptionMode mode, byte[] key);

	byte[] decrypt(byte[] input, EncryptionMode mode, byte[] key);
}
