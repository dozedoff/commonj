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
package filefilter;

import java.io.File;
import java.io.FileFilter;

public class SimpleImageFilter implements FileFilter {
	final String[] imageExtensions = {"jpg", "png", "gif"};
	
	@Override
	public boolean accept(File pathname) {
		if(! pathname.isFile()){
			return false;
		}
		
		String filename = pathname.getName();
		
		int extensionIndex = filename.lastIndexOf(".") + 1;
		String fileExtension = filename.substring(extensionIndex).toLowerCase();
		
		for(String extension : imageExtensions){
			if(fileExtension.equals(extension)){
				return true;
			}
		}
		
		return false;
	}

}
