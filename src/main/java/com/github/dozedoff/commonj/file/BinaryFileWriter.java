/*  Copyright (C) 2013  Nicholas Wright

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
package com.github.dozedoff.commonj.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryFileWriter {
	public void write(byte[] byteData, String savePath) throws IllegalArgumentException, IOException {

		if (savePath == null || savePath.equals("")) {
			throw new IllegalArgumentException("Filepath is invalid");
		}

		if (byteData == null) {
			throw new IllegalArgumentException("Data cannot be null");
		}

		File myFile = new File(savePath);

		new File(myFile.getParent()).mkdirs(); // create all directories
		myFile.createNewFile();

		FileOutputStream writeMe = new FileOutputStream(myFile);
		BufferedOutputStream buffOut = new BufferedOutputStream(writeMe, 1024);
		buffOut.write(byteData);

		buffOut.close();
	}
}
