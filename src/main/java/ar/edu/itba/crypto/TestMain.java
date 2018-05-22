package ar.edu.itba.crypto;

import ar.edu.itba.crypto.algorithms.AES128;
import ar.edu.itba.crypto.algorithms.AES256;
import ar.edu.itba.crypto.algorithms.EncryptionAlgorithm;
import ar.edu.itba.crypto.algorithms.EncryptionMode;

public class TestMain {

	public static void main(String[] args) {
		String message = "Super secret message";
		String pass = "Random Password!!";
		System.out.println("Message: " + message);
		EncryptionAlgorithm encryptionAlgorithm = new AES128();
		byte[] cipheredText = encryptionAlgorithm.encrypt(message.getBytes(), EncryptionMode.OFB, pass.getBytes());
		System.out.println("Ciphered: " + new String(cipheredText));
		byte[] decryptedText = encryptionAlgorithm.decrypt(cipheredText, EncryptionMode.OFB, pass.getBytes());
		System.out.println("Deciphered: " + new String(decryptedText));
	}
}
