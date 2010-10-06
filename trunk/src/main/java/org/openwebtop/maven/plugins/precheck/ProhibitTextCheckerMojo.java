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
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.DirectoryScanner;
import org.openwebtop.maven.plugins.precheck.prohibittext.ProhibitText;
import org.openwebtop.maven.plugins.precheck.prohibittext.ProhibitTextChecker;
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
	private DirectoryScanner directoryScanner;

	public ProhibitTextCheckerMojo() {
		prohibitTextChecker = new ProhibitTextChecker();
		directoryScanner = new DirectoryScanner();
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
	public void execute() throws MojoExecutionException, MojoFailureException {
		printLog("----- Start to check prohibit text -----");

		if (skip) {
			printLog("----- Skip check prohibit text -----");
			return;
		}

		if (ArrayUtils.isEmpty(prohibitTexts)) {
			printLog("There is no configuration for checking prohibit text. skipping...");

			return;
		}

		final List<ProhibitTextError> prohibitTextErrors = new ArrayList<ProhibitTextError>();

		for (ProhibitText prohibitText : prohibitTexts) {
			if (StringUtils.isNotBlank(prohibitText.getDescription())) {
				printLog("Prohibit text check : " + prohibitText.getDescription());
			} else {
				printLog("Prohibit text check : noname");
			}

			directoryScanner.setBasedir(prohibitText.getBasedir());
			directoryScanner.setIncludes(prohibitText.getIncludes());
			directoryScanner.setExcludes(prohibitText.getExcludes());
			directoryScanner.scan();

			final String[] filenames = directoryScanner.getIncludedFiles();
			printLog(String.format("%d files has been founded.", ArrayUtils.getLength(filenames)));
			printLog("Start checking...");

			if (!ArrayUtils.isEmpty(filenames)) {
				for (String filename : filenames) {
					final File file = new File(prohibitText.getBasedir() + File.separator + filename);
					getLog().debug("Include " + filename);

					try {
						prohibitTextErrors.addAll(prohibitTextChecker.check(file, prohibitText.getProhibitTextPatterns()));
					} catch (IOException e) {
						getLog().error(String.format("Cannot read target file. [%s]", filename), e);
					}
				}
			}

			printLog("Done...");
		}

		if (CollectionUtils.isNotEmpty(prohibitTextErrors)) {
			for (ProhibitTextError textError : prohibitTextErrors) {
				printLog(textError.toString());
			}

			throw new MojoFailureException(String.format("%d files have prohibit text!", prohibitTextErrors.size()));
		}

		printLog("----- There is no prohibit text -----");
	}

	public void setProhibitTextChecker(ProhibitTextChecker prohibitTextChecker) {
		this.prohibitTextChecker = prohibitTextChecker;
	}

	public void setDirectoryScanner(DirectoryScanner directoryScanner) {
		this.directoryScanner = directoryScanner;
	}

	public ProhibitText[] getProhibitTexts() {
		return prohibitTexts;
	}

	public void setProhibitTexts(ProhibitText[] prohibitTexts) {
		this.prohibitTexts = prohibitTexts;
	}

}
