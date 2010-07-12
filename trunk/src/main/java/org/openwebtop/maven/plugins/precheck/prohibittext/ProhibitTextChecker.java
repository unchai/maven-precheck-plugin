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
package org.openwebtop.maven.plugins.precheck.prohibittext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import org.openwebtop.maven.plugins.precheck.prohibittext.model.ProhibitTextError;

/**
 * Prohibit text checker
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class ProhibitTextChecker {
	/**
	 * Check text files
	 *
	 * @param file file
	 * @param prohibitTextPatterns Error strings
	 * @return TextErrors
	 * @throws IOException
	 */
	public List<ProhibitTextError> check(File file, String[] prohibitTextPatterns) throws IOException {
		final List<ProhibitTextError> prohibitTextErrors = new ArrayList<ProhibitTextError>();

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			int lineNumber = 0;
			String line = null;

			while ((line = reader.readLine()) != null) {
				lineNumber++;

				for (String prohibitTextPattern : prohibitTextPatterns) {
					if (line.matches("(.*)(" + prohibitTextPattern + ")(.*)")) {
						final ProhibitTextError textError = new ProhibitTextError();
						textError.setFile(file);
						textError.setLineNumber(lineNumber);
						textError.setProhibitTextPattern(prohibitTextPattern);
						textError.setText(StringUtils.trim(line));

						prohibitTextErrors.add(textError);
					}
				}
			}
		} finally {
			IOUtils.closeQuietly(reader);
		}

		return prohibitTextErrors;
	}

}
