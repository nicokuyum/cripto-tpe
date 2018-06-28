package ar.edu.itba.crypto;

public class BinaryFile {
	private byte[] data;
	private String extension = "";

	public BinaryFile(byte[] data) {
		this.data = data;
	}

	public BinaryFile(byte[] data, String extension) {
		this.data = data;
		this.extension = extension;
	}

	public byte[] getData() {
		return data;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setData(byte[] encrypted) {
		data = encrypted;
	}
}
