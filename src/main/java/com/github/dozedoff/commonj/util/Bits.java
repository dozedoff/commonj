package com.github.dozedoff.commonj.util;

public class Bits {
	public static int hammingDistance(String left, String right) {
		return hammingDistance(Long.parseUnsignedLong(left, 2), Long.parseUnsignedLong(right, 2));
	}

	public static int hammingDistance(long left, long right) {
		long xor = left ^ right;
		int distance = Long.bitCount(xor);
		return distance;
	}
}
