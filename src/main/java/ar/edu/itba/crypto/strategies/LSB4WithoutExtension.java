package ar.edu.itba.crypto.strategies;

import ar.edu.itba.crypto.BinaryFile;
import ar.edu.itba.crypto.Image;
import com.google.common.base.Charsets;
import com.google.common.primitives.Bytes;

import java.util.ArrayList;
import java.util.List;

public class LSB4WithoutExtension extends LSB {
	@Override
	protected int step() {
		return 4;
	}
}
