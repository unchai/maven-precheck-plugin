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
package org.openwebtop.maven.plugins.precheck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.openwebtop.maven.plugins.precheck.common.DefaultDirectoryScanner;
import org.openwebtop.maven.plugins.precheck.prohibittext.ProhibitTextChecker;
import org.openwebtop.maven.plugins.precheck.prohibittext.model.ProhibitText;
import org.openwebtop.maven.plugins.precheck.prohibittext.model.ProhibitTextError;

/**
 * The ProhibitTextCheckerMojo is used to check prohibit text in target files.
 * If prohibit text is exist in target files, maven build will be fail.
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 *
 * @goal prohibittext
 * @phase process-resources
 */
public class ProhibitTextCheckerMojo extends AbstractPrecheckMojo {
	private final static String GOAL_NAME = "prohibittext";

	/**
	 * Prohibit text
	 *
	 * @parameter
	 */
	private ProhibitText[] prohibitTexts;

	private ProhibitTextChecker prohibitTextChecker;
	private DefaultDirectoryScanner defaultDirectoryScanner;

	public ProhibitTextCheckerMojo() {
		prohibitTextChecker = new ProhibitTextChecker();
		defaultDirectoryScanner = new DefaultDirectoryScanner();
	}

	@Override
	public String getGoalName() {
		return GOAL_NAME;
	}

	/**
	 * Mojo execute
	 *
	 * @throws MojoExecutionException
	 * @throws MojoFailureException
	 */
	public void onExecute() throws MojoExecutionException, MojoFailureException {
		if (ArrayUtils.isEmpty(prohibitTexts)) {
			printInfoLog("There is no configuration for checking prohibit text. skipping...");
			return;
		}

		final List<ProhibitTextError> prohibitTextErrors = new ArrayList<ProhibitTextError>();

		for (ProhibitText prohibitText : prohibitTexts) {
			String[] filenames = null;

			try {
				filenames = defaultDirectoryScanner.getIncludedFiles(prohibitText);
			} catch (Exception e) {
				printErrorLog("error has been occured :" + e.getMessage());
				continue;
			}

			printInfoLog(String.format("%d files has been founded.", ArrayUtils.getLength(filenames)));
			printInfoLog("Start checking...");

			if (ArrayUtils.isNotEmpty(filenames)) {
				for (String filename : filenames) {
					final File file = new File(prohibitText.getBasedir() + File.separator + filename);
					printDebugLog("Include " + filename);

					try {
						prohibitTextErrors.addAll(prohibitTextChecker.check(file, prohibitText.getProhibitTextPatterns()));
					} catch (IOException e) {
						throw new MojoFailureException("Cannot read target file. [%s]");
					}
				}
			}

			printInfoLog("Done...");
		}

		if (CollectionUtils.isNotEmpty(prohibitTextErrors)) {
			for (ProhibitTextError textError : prohibitTextErrors) {
				printInfoLog(textError.toString());
			}

			throw new MojoFailureException(String.format("%d files have prohibit text!", prohibitTextErrors.size()));
		}
	}

	public void setProhibitTextChecker(ProhibitTextChecker prohibitTextChecker) {
		this.prohibitTextChecker = prohibitTextChecker;
	}

	public void setDefaultDirectoryScanner(DefaultDirectoryScanner defaultDirectoryScanner) {
		this.defaultDirectoryScanner = defaultDirectoryScanner;
	}

	public ProhibitText[] getProhibitTexts() {
		return prohibitTexts;
	}

	public void setProhibitTexts(ProhibitText[] prohibitTexts) {
		this.prohibitTexts = prohibitTexts;
	}

}
