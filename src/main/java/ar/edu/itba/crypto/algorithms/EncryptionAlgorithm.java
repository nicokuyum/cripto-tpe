package ar.edu.itba.crypto.algorithms;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public abstract class EncryptionAlgorithm {

	public byte[] encrypt(byte[] input, EncryptionMode mode, String password) throws Exception {
		return transform(input, mode, password, Cipher.ENCRYPT_MODE);
	}

	public byte[] decrypt(byte[] input, EncryptionMode mode, String password) throws Exception {
		return transform(input, mode, password, Cipher.DECRYPT_MODE);
	}

	private byte[] transform(byte[] input, EncryptionMode mode, String password, int cipherMode) throws Exception{
		Cipher cipher = Cipher.getInstance(getName() + "/" + mode.toString() + "/PKCS5Padding");
		MessageDigest md5 = MessageDigest.getInstance("MD5");

		final byte[][] keyAndIV = EVP_BytesToKey(getKeyLength() / Byte.SIZE, cipher.getBlockSize(), md5, password.getBytes(), 1);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], getName());

		if (usesIV(mode)) {
			IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
			cipher.init(cipherMode, key, iv);
		} else {
			cipher.init(cipherMode, key);
		}
		return cipher.doFinal(input);
	}

	abstract String getName();

	abstract int getKeyLength();

	private boolean usesIV(EncryptionMode mode) {
		return !mode.equals(EncryptionMode.ECB);
	}

	// from https://gist.github.com/luosong/5523434
	public static byte[][] EVP_BytesToKey(int key_len, int iv_len, MessageDigest md, byte[] data, int count) {
		byte[][] both = new byte[2][];
		byte[] key = new byte[key_len];
		int key_ix = 0;
		byte[] iv = new byte[iv_len];
		int iv_ix = 0;
		both[0] = key;
		both[1] = iv;
		byte[] md_buf = null;
		int nkey = key_len;
		int niv = iv_len;
		int i = 0;
		if (data == null) {
			return both;
		}
		int addmd = 0;
		for (;;) {
			md.reset();
			if (addmd++ > 0) {
				md.update(md_buf);
			}
			md.update(data);
			md_buf = md.digest();
			for (i = 1; i < count; i++) {
				md.reset();
				md.update(md_buf);
				md_buf = md.digest();
			}
			i = 0;
			if (nkey > 0) {
				for (;;) {
					if (nkey == 0)
						break;
					if (i == md_buf.length)
						break;
					key[key_ix++] = md_buf[i];
					nkey--;
					i++;
				}
			}
			if (niv > 0 && i != md_buf.length) {
				for (;;) {
					if (niv == 0)
						break;
					if (i == md_buf.length)
						break;
					iv[iv_ix++] = md_buf[i];
					niv--;
					i++;
				}
			}
			if (nkey == 0 && niv == 0) {
				break;
			}
		}
		for (i = 0; i < md_buf.length; i++) {
			md_buf[i] = 0;
		}
		return both;
	}
}
