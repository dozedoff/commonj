/*  Copyright (C) 2012  Nicholas Wright

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dozedoff.commonj.hash;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.commonj.string.Convert;
import com.github.dozedoff.commonj.util.FileIO;

/**
 * Generates a SHA-2 Hash (default) for binary data, and formats the value into a Hex representation.
 */
public class HashMaker {
	private static Logger logger = LoggerFactory.getLogger(HashMaker.class);
	private final String DEFAULT_ALGORITHM = "SHA-256";
	MessageDigest md = null;

	public HashMaker() {
		createDigest(DEFAULT_ALGORITHM);
	}

	public HashMaker(String algorithm) {
		createDigest(algorithm);
	}

	private void createDigest(String algorithm) {
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e1) {
			logger.error("Unable to find Algorithm {}, falling back to {}", algorithm, DEFAULT_ALGORITHM);
			createDigest(DEFAULT_ALGORITHM);
		}
	}

	/**
	 * Generate a Hash value for binary data.
	 * 
	 * @param data
	 *            Binary data
	 * @return hash Hash as a hex value
	 */
	public String hash(byte[] data) {
		byte[] rawHash;

		if (data == null) {
			logger.error("No data");
			return null;
		}

		md.update(data);
		rawHash = md.digest();

		return Convert.byteToHex(rawHash);
	}

	public String hashFile(File file) {
		byte[] rawHash;

		if (file == null || !file.exists()) {
			return null;
		}

		FileInputStream stream;

		stream = null;

		final MappedByteBuffer buffer;
		final int fileSize;

		buffer = FileIO.openReadOnlyBuffer(file);

		if (buffer == null) {
			return null;
		}

		fileSize = (int) file.length();

		for (int i = 0; i < fileSize; i++) {
			md.update(buffer.get());
		}

		FileIO.closeFileInputStream(stream);

		rawHash = md.digest();

		return Convert.byteToHex(rawHash);
	}
}
