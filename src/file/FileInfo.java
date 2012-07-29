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
import java.nio.file.Path;

/**
 * A simple storage class for file information.
 */
public class FileInfo {
	Path file;
	long size;
	String hash;
	
	public FileInfo(File file, String hash) {
		super();
		this.file = file.toPath();
		this.hash = hash;
	}
	
	public FileInfo(Path file, String hash){
		super();
		this.file = file;
		this.hash = hash;
	}
	
	public FileInfo(File file){
		this.file = file.toPath();
	}
	
	public FileInfo(Path path){
		this.file = path;
	}

	public File getFile() {
		return file.toFile();
	}

	public long getSize() {
		return size;
	}

	public String getHash() {
		return hash;
	}

	public void setFile(File file) {
		this.file = file.toPath();
	}
	
	public void setFile(Path file){
		this.file = file;
	}
	
	public Path getFilePath(){
		return file;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}
