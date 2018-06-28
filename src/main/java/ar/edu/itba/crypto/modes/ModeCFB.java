package ar.edu.itba.crypto.modes;

public class ModeCFB implements EncryptionMode {
	@Override
	public String getName() {
		return "CFB8";
	}

	@Override
	public String getPadding() {
		return "NoPadding";
	}

	@Override
	public boolean usesIV() {
		return true;
	}
}
