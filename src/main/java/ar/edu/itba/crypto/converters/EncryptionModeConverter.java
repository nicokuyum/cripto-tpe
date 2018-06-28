package ar.edu.itba.crypto.converters;

import ar.edu.itba.crypto.modes.*;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class EncryptionModeConverter implements IStringConverter<EncryptionMode> {

	@Override
	public EncryptionMode convert(String value) {
		switch (value.toUpperCase()) {
			case "CBC": return new ModeCBC();
			case "CFB": return new ModeCFB();
			case "ECB": return new ModeECB();
			case "OFB": return new ModeOFB();
			default: throw new ParameterException("Unknown encryption mode " + value);
		}
	}
}
