package ar.edu.itba.crypto.modes;

public class ModeCBC implements EncryptionMode {
	@Override
	public String getName() {
		return "CBC";
	}

	@Override
	public String getPadding() {
		return "PKCS5Padding";
	}

	@Override
	public boolean usesIV() {
		return true;
	}
}
