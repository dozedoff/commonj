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
package file;

import java.io.File;

/**
 * A simple storage class for file information.
 */
public class FileInfo {
	File file;
	long size;
	String hash;
	
	public FileInfo(File file, String hash) {
		super();
		this.file = file;
		this.hash = hash;
	}

	public File getFile() {
		return file;
	}

	public long getSize() {
		return size;
	}

	public String getHash() {
		return hash;
	}
}