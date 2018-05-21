package ar.edu.itba.crypto.algorithms;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class AES implements EncryptionAlgorithm {

	@Override
	public byte[] encrypt(byte[] input, EncryptionMode mode, byte[] key) {
		try {
			//SecureRandom secureRandom = new SecureRandom();
			//byte[] IV = new byte[16];
			//secureRandom.nextBytes(IV);

			SecretKeySpec k = new SecretKeySpec(key, "AES");
			Cipher cipher = getCipher(mode);

			cipher.init(Cipher.ENCRYPT_MODE, k);
			return cipher.doFinal(input);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] decrypt(byte[] input, EncryptionMode mode, byte[] key) {
		try {
			SecretKeySpec k = new SecretKeySpec(key, "AES");
			Cipher cipher = getCipher(mode);

			cipher.init(Cipher.DECRYPT_MODE, k);
			return cipher.doFinal(input);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected abstract Cipher getCipher(EncryptionMode mode) throws NoSuchAlgorithmException, NoSuchPaddingException;
}
