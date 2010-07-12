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
	/**
	 * Prohibit text
	 *
	 * @parameter
	 */
	private ProhibitText prohibitText;

	private ProhibitTextChecker prohibitTextChecker;
	private DirectoryScanner directoryScanner;

	public ProhibitTextCheckerMojo() {
		prohibitTextChecker = new ProhibitTextChecker();
		directoryScanner = new DirectoryScanner();
	}

	/**
	 * Mojo execute
	 *
	 * @throws MojoExecutionException
	 * @throws MojoFailureException
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		directoryScanner.setBasedir(prohibitText.getBasedir());
		directoryScanner.setIncludes(prohibitText.getIncludes());
		directoryScanner.setExcludes(prohibitText.getExcludes());
		directoryScanner.scan();

		final String[] filenames = directoryScanner.getIncludedFiles();
		getLog().info(String.format("%d files has been founded.", ArrayUtils.getLength(filenames)));
		getLog().info("Start searching...");

		if (!ArrayUtils.isEmpty(filenames)) {
			final List<ProhibitTextError> prohibitTextErrors = new ArrayList<ProhibitTextError>();

			for (String filename : filenames) {
				final File file = new File(prohibitText.getBasedir() + File.separator + filename);
				getLog().debug("Include " + filename);

				try {
					prohibitTextErrors.addAll(prohibitTextChecker.check(file, prohibitText.getProhibitTextPatterns()));
				} catch (IOException e) {
					getLog().error(String.format("Cannot read target file. [%s]", filename), e);
				}
			}

			if (CollectionUtils.isNotEmpty(prohibitTextErrors)) {
				for (ProhibitTextError textError : prohibitTextErrors) {
					getLog().info(textError.toString());
				}

				throw new MojoFailureException(String.format("%d files have prohibit text!", prohibitTextErrors.size()));
			}
		}

		getLog().info("There is no prohibit text.");
	}

	public void setProhibitTextChecker(ProhibitTextChecker prohibitTextChecker) {
		this.prohibitTextChecker = prohibitTextChecker;
	}

	public void setDirectoryScanner(DirectoryScanner directoryScanner) {
		this.directoryScanner = directoryScanner;
	}

	public void setProhibitText(ProhibitText prohibitText) {
		this.prohibitText = prohibitText;
	}

}
