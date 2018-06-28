package ar.edu.itba.crypto;

import ar.edu.itba.crypto.algorithms.*;

import java.util.Base64;

public class TestMain {

	public static void main(String[] args) throws Exception {
		String message = "012jkfldhfdhksjhfkdjhfkjshdkfhksjhfkjshkfhskhfkjshkfjhskjfhksjhfksd345";
		String pass = "passw";
		System.out.println("Message: " + message);
		EncryptionAlgorithm encryptionAlgorithm = new AES256();
		EncryptionMode mode = EncryptionMode.OFB;
		byte[] cipheredText = encryptionAlgorithm.encrypt(message.getBytes(), mode, pass);
		System.out.println("Ciphered: " + new String(Base64.getEncoder().encode(cipheredText)));
		System.out.println("Ciphered text length: " + cipheredText.length);
		byte[] decryptedText = encryptionAlgorithm.decrypt(cipheredText, mode, pass);
		System.out.println("Deciphered: " + new String(decryptedText));
	}
}
