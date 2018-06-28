package ar.edu.itba.crypto.algorithms;

import ar.edu.itba.crypto.modes.EncryptionMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EncryptionAlgorithmsTest {

	private List<EncryptionAlgorithm> encryptionAlgorithms;

	@Before
	public void init() {
		encryptionAlgorithms = new ArrayList<>();
		encryptionAlgorithms.add(new AES128());
		encryptionAlgorithms.add(new AES192());
		encryptionAlgorithms.add(new AES256());
		encryptionAlgorithms.add(new DES());
	}

	@Test
	public void allModesEncryptionTest() throws Exception {
		EncryptionMode[] modes = EncryptionMode.values();
		byte[] input = "Supa secret message".getBytes();
		String password = "password";

		for (EncryptionMode mode : modes) {
			for (EncryptionAlgorithm ea : encryptionAlgorithms) {
				byte[] output = ea.decrypt(ea.encrypt(input, mode, password), mode, password);
				Assert.assertArrayEquals(input, output);
			}
		}
	}
}
