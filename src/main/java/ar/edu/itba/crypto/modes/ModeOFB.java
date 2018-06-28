package ar.edu.itba.crypto.modes;

public class ModeOFB implements EncryptionMode {
	@Override
	public String getName() {
		return "OFB";
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
