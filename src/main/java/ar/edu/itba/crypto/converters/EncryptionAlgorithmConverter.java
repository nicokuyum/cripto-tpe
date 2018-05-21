package ar.edu.itba.crypto.converters;

import ar.edu.itba.crypto.algorithms.*;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class EncryptionAlgorithmConverter implements IStringConverter<EncryptionAlgorithm> {

	@Override
	public EncryptionAlgorithm convert(String value) {
		switch (value.toUpperCase()) {
			case "AES128": return new AES128();
			case "AES192": return new AES192();
			case "AES256": return new AES256();
			case "DES": return new DES();
			default: throw new ParameterException("Invalid encryption algorithm " + value);
		}
	}
}
