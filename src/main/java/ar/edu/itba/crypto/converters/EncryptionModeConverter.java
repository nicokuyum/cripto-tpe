package ar.edu.itba.crypto.converters;

import ar.edu.itba.crypto.algorithms.EncryptionMode;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class EncryptionModeConverter implements IStringConverter<EncryptionMode> {

	@Override
	public EncryptionMode convert(String value) {
		try {
			return EncryptionMode.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ParameterException("Unknown encryption mode " + value);
		}
	}
}
