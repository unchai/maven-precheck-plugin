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
package org.openwebtop.maven.plugins.precheck.environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.openwebtop.maven.plugins.precheck.environment.model.EnvironmentError;

/**
 * Environment checker
 * 
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 10. 14.
 */
public class EnvironmentChecker {
	private String extractRegExp;
	private File[] checkSourceFiles;
	private File[] checkTargetFiles;

	public List<EnvironmentError> check() throws IOException {
		final Pattern pattern = Pattern.compile(extractRegExp);

		final List<EnvironmentError> environmentErrors = new ArrayList<EnvironmentError>();

		final Set<String> extractedStrings = extractStringFromSourceFiles();

		for (File file : checkTargetFiles) {
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

				int lineNumber = 0;
				String line = null;

				while ((line = reader.readLine()) != null) {
					lineNumber++;

					final Matcher matcher = pattern.matcher(line);

					while (matcher.find()) {
						final String extractedString = matcher.group();

						if (extractedStrings.contains(extractedString)) {
							final EnvironmentError environmentError = new EnvironmentError();
							environmentError.setFile(file);
							environmentError.setLine(line);
							environmentError.setLineNumber(lineNumber);
							environmentError.setDetectedString(matcher.group());

							environmentErrors.add(environmentError);
						}
					}
				}
			} finally {
				IOUtils.closeQuietly(reader);
			}
		}

		return environmentErrors;
	}

	private Set<String> extractStringFromSourceFiles() throws IOException {
		final Pattern pattern = Pattern.compile(extractRegExp);

		final Set<String> extractedStringSet = new HashSet<String>();

		BufferedReader reader = null;

		for (File file : checkSourceFiles) {
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

				String line = null;

				while ((line = reader.readLine()) != null) {
					final Matcher matcher = pattern.matcher(line);

					while (matcher.find()) {
						extractedStringSet.add(matcher.group());
					}
				}
			} finally {
				IOUtils.closeQuietly(reader);
			}
		}

		return extractedStringSet;
	}

	public String getExtractRegExp() {
		return extractRegExp;
	}

	public void setExtractRegExp(String extractRegExp) {
		this.extractRegExp = extractRegExp;
	}

	public File[] getCheckSourceFiles() {
		return checkSourceFiles;
	}

	public void setCheckSourceFiles(File[] checkSourceFiles) {
		this.checkSourceFiles = checkSourceFiles;
	}

	public File[] getCheckTargetFiles() {
		return checkTargetFiles;
	}

	public void setCheckTargetFiles(File[] checkTargetFiles) {
		this.checkTargetFiles = checkTargetFiles;
	}

}
