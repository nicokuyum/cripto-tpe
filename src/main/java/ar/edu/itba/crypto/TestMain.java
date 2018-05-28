package ar.edu.itba.crypto;

import ar.edu.itba.crypto.algorithms.*;

import java.util.Base64;

public class TestMain {

	public static void main(String[] args) {
		String message = "e";
		String pass = "Random Password!!";
		System.out.println("Message: " + message);
		EncryptionAlgorithm encryptionAlgorithm = new AES192();
		EncryptionMode mode = EncryptionMode.CBC;
		byte[] cipheredText = encryptionAlgorithm.encrypt(message.getBytes(), mode, pass.getBytes());
		System.out.println("Ciphered: " + new String(Base64.getEncoder().encode(cipheredText)));
		byte[] decryptedText = encryptionAlgorithm.decrypt(cipheredText, mode, pass.getBytes());
		System.out.println("Deciphered: " + new String(decryptedText));
	}
}
