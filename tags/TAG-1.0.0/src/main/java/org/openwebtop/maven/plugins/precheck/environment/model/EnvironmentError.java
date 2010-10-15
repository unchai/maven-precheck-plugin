/**
 * Copyright (C) 2010  Jaehyeon Nam (dotoli21@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openwebtop.maven.plugins.precheck.environment.model;

import java.io.File;

/**
 * Environment Error
 * 
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 10. 14.
 */
public class EnvironmentError {
	private File file;
	private int lineNumber;
	private String line;
	private String detectedString;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getDetectedString() {
		return detectedString;
	}

	public void setDetectedString(String detectedString) {
		this.detectedString = detectedString;
	}

	@Override
	public String toString() {
		return String.format("File=%s, Line=%s, Lines=%d, DetectedString=%s", file.getPath(), line, lineNumber, detectedString);
	}

}
