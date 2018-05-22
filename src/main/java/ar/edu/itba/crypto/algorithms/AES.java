package ar.edu.itba.crypto.algorithms;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class AES implements EncryptionAlgorithm {

	private static final int ivSize = 16;

	@Override
	public byte[] encrypt(byte[] input, EncryptionMode mode, byte[] key) {
		try {

			SecureRandom secureRandom = new SecureRandom();
			byte[] IV = new byte[ivSize];
			secureRandom.nextBytes(IV);
			IvParameterSpec ivSpec = new IvParameterSpec(IV);

			SecretKeySpec k = getKey(key, getKeyLength());
			System.out.println("Using key length: " + k.getEncoded().length*8);
			Cipher cipher = getCipher(mode);

			cipher.init(Cipher.ENCRYPT_MODE, k, ivSpec);
			byte[] encrypted = cipher.doFinal(input);

			byte[] encryptedWithIV = new byte[IV.length + encrypted.length];
			System.arraycopy(IV, 0, encryptedWithIV, 0, IV.length);
			System.arraycopy(encrypted, 0, encryptedWithIV, IV.length, encrypted.length);

			return encryptedWithIV;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public byte[] decrypt(byte[] input, EncryptionMode mode, byte[] key) {
		try {

			SecretKeySpec k = getKey(key, getKeyLength());
			Cipher cipher = getCipher(mode);

			byte[] IV = new byte[ivSize];
			System.arraycopy(input, 0, IV, 0, IV.length);
			IvParameterSpec ivSpec = new IvParameterSpec(IV);

			byte[] encrypted = new byte[input.length - IV.length];
			System.arraycopy(input, IV.length, encrypted, 0, encrypted.length);

			cipher.init(Cipher.DECRYPT_MODE, k, ivSpec);
			return cipher.doFinal(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Cipher getCipher(EncryptionMode mode) throws NoSuchAlgorithmException, NoSuchPaddingException {
		return Cipher.getInstance("AES/" + mode.toString() + "/PKCS5Padding");
	}

	private SecretKeySpec getKey(byte[] key, int keyLength) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(key);
		byte[] fullHash = digest.digest();
		byte[] trimmed = new byte[keyLength/8];
		System.arraycopy(fullHash, 0, trimmed, 0, keyLength/8);
		return new SecretKeySpec(trimmed, "AES");
	}

	protected abstract int getKeyLength();
}
