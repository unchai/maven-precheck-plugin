package org.openwebtop.maven.plugins.precheck.environment.model;

import java.io.File;

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
