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
package com.github.dozedoff.commonj.gui;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JTextArea;
/**
 * This class outputs Strings sent to it to a TextField.
 * The class also maintains the TextFied, limiting the amount of text
 * displayed on it.
 */
public class Log {
	static private JTextArea logArea;
	static private int logNr = 0;
	static private final Logger logger = LoggerFactory.getLogger(Log.class);

	public static void setLogArea(JTextArea logArea){
		Log.logArea = logArea;
	}

	public static void setLogNr(int nr){
		Log.logNr = nr;
	}
	
	public static void clear(){
		Log.logArea.setText("");
		Log.logNr = 0;
	}

	public static int getLogNr(){
		return logNr;
	}
	/**
	 * Will output a message to the GUI log. The message will have the current date
	 * and time as a prefix.
	 * @param add message to add to log
	 */
	public static void add(String add){
		if(logArea == null){
			logger.warn("No output textfield set for GUI log");
			return;
		}
		Calendar cal = new GregorianCalendar();
		DateFormat df;
		df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

		logArea.append(df.format(cal.getTime())+"  "+add+"\n");
		if (logNr > 50){
			logArea.replaceRange("", 0,1 + logArea.getText().indexOf("\n"));
		}else{
			logNr++; // to avoid overflow
		}
//		logArea.setCaretPosition(logArea.getText().length());
	}
}
