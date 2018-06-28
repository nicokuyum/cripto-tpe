package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LSB1WithoutExtension extends LSB {

	@Override
	protected int step() {
		return 1;
	}
}
