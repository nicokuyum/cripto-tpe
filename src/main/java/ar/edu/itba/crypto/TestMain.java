package ar.edu.itba.crypto;

import ar.edu.itba.crypto.algorithms.*;
import ar.edu.itba.crypto.modes.EncryptionMode;
import ar.edu.itba.crypto.modes.ModeCFB;
import ar.edu.itba.crypto.modes.ModeOFB;
import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TestMain {

	public static void main(String[] args) throws Exception {
		BinaryFile bf = new BinaryFile(Files.readAllBytes(Paths.get("video.wmv")), ".wmv");
		byte[] r = new AES256().decrypt(bf.getData(), new ModeCFB(), "solucion");
		BinaryFile result = setBinaryFile(r);
		FileOutputStream fos = new FileOutputStream("pepito" + result.getExtension());
		fos.write(result.getData());

	}
	private static BinaryFile setBinaryFile(byte[] r) {
		int length = (r[0] & 0xFF) << 24 | (r[1] & 0xFF) << 16 | (r[2] & 0xFF) << 8 | (r[3] & 0xFF);
		int rIndex = 4;
		byte[] data = new byte[length];
		for(int i = 0; i< length;i++, rIndex++) {
			data[i] = r[rIndex];
		}
		List<Byte> extensionBytes = new ArrayList<>();

		while(r[rIndex] != 0) {
			extensionBytes.add(r[rIndex++]);
		}
		String extension = new String(Bytes.toArray(extensionBytes), Charsets.US_ASCII);
		return new BinaryFile(data, extension);
	}

}
