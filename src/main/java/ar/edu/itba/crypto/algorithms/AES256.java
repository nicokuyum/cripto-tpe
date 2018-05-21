package ar.edu.itba.crypto.algorithms;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class AES256 extends AES {
	@Override
	protected Cipher getCipher(EncryptionMode mode) throws NoSuchAlgorithmException, NoSuchPaddingException {
		return null;
	}
}
