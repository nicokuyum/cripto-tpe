package ar.edu.itba.crypto.modes;

public class ModeECB implements EncryptionMode {
	@Override
	public String getName() {
		return "ECB";
	}

	@Override
	public String getPadding() {
		return "PKCS5Padding";
	}

	@Override
	public boolean usesIV() {
		return false;
	}
}
