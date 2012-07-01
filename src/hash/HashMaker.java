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
package hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import string.Convert;

/**
 * Generates a SHA-2 Hash for binary data, and formats the value into
 * a Hex representation.
 */
public class HashMaker {
	private static Logger logger = Logger.getLogger(HashMaker.class.getName());

	MessageDigest md = null;
	String hash;
	byte[] rawHash;

	/**
	 * Generate a Hash value for binary data.
	 * @param data Binary data
	 * @return hash Hash as a hex value 
	 */
	public String hash(byte[] data){
		if(md == null){
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e1) {
				logger.severe(e1.getMessage());
				return null;
			}
		}
		
		if(data == null){
			logger.severe("No data");
			return null;
		}

		md.update(data);
		rawHash = md.digest();
		hash = Convert.byteToHex(rawHash);

		return hash;
	}
	
	public String hashFile(File file) {
		if (md == null) {
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e1) {
				logger.severe(e1.getMessage());
				return null;
			}
		}
		
		if(file == null){
			return null;
		}

		hash = null;
		DigestInputStream dis = null;
		final long filesize = file.length();
		
		try {
			dis = new DigestInputStream(new FileInputStream(file), md);
			
			for(long i = 0; i < filesize; i++){
				dis.read();
			}
			
			rawHash = md.digest();
			hash = Convert.byteToHex(rawHash);
		} catch (final IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		return hash;
	}
}