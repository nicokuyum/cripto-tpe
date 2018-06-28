package ar.edu.itba.crypto.modes;

public interface EncryptionMode {
	String getName();
	String getPadding();
	boolean usesIV();
}
