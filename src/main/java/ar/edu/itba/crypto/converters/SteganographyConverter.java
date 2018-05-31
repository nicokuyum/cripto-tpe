package ar.edu.itba.crypto.converters;

import ar.edu.itba.crypto.strategies.LSB1WithoutExtension;
import ar.edu.itba.crypto.strategies.LSB4;
import ar.edu.itba.crypto.strategies.LSBEnhanced;
import ar.edu.itba.crypto.strategies.SteganographyStrategy;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class SteganographyConverter implements IStringConverter<SteganographyStrategy> {

	@Override
	public SteganographyStrategy convert(String value) {
		switch (value.toUpperCase()) {
			case "LSB1":
				return new LSB1WithoutExtension();
			case "LSB4":
				return new LSB4();
			case "LSBE":
				return new LSBEnhanced();
			default:
				throw new ParameterException("Invalid Steganography method");
		}
	}
}
