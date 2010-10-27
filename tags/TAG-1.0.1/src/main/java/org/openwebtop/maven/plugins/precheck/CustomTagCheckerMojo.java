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
import org.openwebtop.maven.plugins.precheck.common.model.DefaultDirectoryScannerConfiguration;
import org.openwebtop.maven.plugins.precheck.customtag.CustomTagChecker;
import org.openwebtop.maven.plugins.precheck.customtag.model.CustomTagError;

/**
 * The CustomTagCheckerMojo is used to check undefined custom tag in markup. 
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 10. 6.
 *
 * @goal customtag
 * @phase process-resources
 */
public class CustomTagCheckerMojo extends AbstractPrecheckMojo {
	private final static String GOAL_NAME = "customtag";

	/**
	 * Custom tag configuration
	 * 
	 * @parameter
	 */
	private DefaultDirectoryScannerConfiguration customTag;

	private CustomTagChecker customTagChecker;
	private DefaultDirectoryScanner defaultDirectoryScanner;

	public CustomTagCheckerMojo() {
		customTagChecker = new CustomTagChecker();
		defaultDirectoryScanner = new DefaultDirectoryScanner();
	}

	@Override
	public String getGoalName() {
		return GOAL_NAME;
	}

	public void onExecute() throws MojoExecutionException, MojoFailureException {
		if (customTag == null) {
			printInfoLog("There is no configuration for checking custom tag. skipping...");
			return;
		}

		String[] filenames = null;

		try {
			filenames = defaultDirectoryScanner.getIncludedFiles(customTag);
		} catch (Exception e) {
			printErrorLog("error has been occured :" + e.getMessage());
			return;
		}

		printInfoLog(String.format("%d files has been founded.", ArrayUtils.getLength(filenames)));
		printInfoLog("Start searching...");

		if (ArrayUtils.isNotEmpty(filenames)) {
			final List<CustomTagError> customTagErrors = new ArrayList<CustomTagError>();

			for (String filename : filenames) {
				final File file = new File(customTag.getBasedir() + File.separator + filename);
				getLog().debug("Include " + filename);

				try {
					customTagErrors.addAll(customTagChecker.check(file));
				} catch (IOException e) {
					getLog().error(String.format("Cannot read target file. [%s]", filename), e);
				}
			}

			if (CollectionUtils.isNotEmpty(customTagErrors)) {
				for (CustomTagError customTagError : customTagErrors) {
					printInfoLog(customTagError.toString());
				}

				throw new MojoFailureException(String.format("%d files arg using undefiend custom tag!", customTagErrors.size()));
			}
		}
	}

	public void setCustomTag(DefaultDirectoryScannerConfiguration customTag) {
		this.customTag = customTag;
	}

	public void setDefaultDirectoryScanner(DefaultDirectoryScanner defaultDirectoryScanner) {
		this.defaultDirectoryScanner = defaultDirectoryScanner;
	}

	public void setCustomTagChecker(CustomTagChecker customTagChecker) {
		this.customTagChecker = customTagChecker;
	}

}
