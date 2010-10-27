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
import org.openwebtop.maven.plugins.precheck.environment.EnvironmentChecker;
import org.openwebtop.maven.plugins.precheck.environment.model.Environment;
import org.openwebtop.maven.plugins.precheck.environment.model.EnvironmentError;

/**
 * Environment Checker
 * 
 * @author Jaehyeon Nam
 * @since 2010. 10. 8.
 * @goal environment
 * @phase process-resources 
 */
public class EnvironmentCheckerMojo extends AbstractPrecheckMojo {
	private final static String GOAL_NAME = "enviroment";

	/**
	 * Environment checker configuration
	 * 
	 * @parameter
	 */
	private Environment[] environments;

	private DefaultDirectoryScanner defaultDirectoryScanner;

	public EnvironmentCheckerMojo() {
		this.defaultDirectoryScanner = new DefaultDirectoryScanner();
	}

	@Override
	public String getGoalName() {
		return GOAL_NAME;
	}

	@Override
	public void onExecute() throws MojoExecutionException, MojoFailureException {
		if (ArrayUtils.isEmpty(environments)) {
			printInfoLog("There is no configuration for checking environment. skipping...");
			return;
		}

		final List<EnvironmentError> environmentErrors = new ArrayList<EnvironmentError>();

		for (Environment environment : environments) {
			final EnvironmentChecker environmentChecker = new EnvironmentChecker();
			environmentChecker.setExtractRegExp(environment.getExtractRegExp());

			try {
				environmentChecker.setCheckTargetFiles(getFilelist(environment.getCheckTarget()));
				environmentChecker.setCheckSourceFiles(getFilelist(environment.getCheckSource()));
			} catch (Exception e) {
				printErrorLog("error has been occured :" + e.getMessage());
				continue;
			}

			try {
				environmentErrors.addAll(environmentChecker.check());
			} catch (IOException e) {
				throw new MojoFailureException("Cannot scan some files!");
			}
		}

		if (CollectionUtils.isNotEmpty(environmentErrors)) {
			for (EnvironmentError environmentError : environmentErrors) {
				printInfoLog(environmentError.toString());
			}

			throw new MojoFailureException(String.format("%d files have been detected!", environmentErrors.size()));
		}
	}

	private File[] getFilelist(DefaultDirectoryScannerConfiguration environmentFiles) throws Exception {
		final String[] filelist = defaultDirectoryScanner.getIncludedFiles(environmentFiles);

		final List<File> files = new ArrayList<File>();

		for (String filename : filelist) {
			files.add(new File(environmentFiles.getBasedir() + File.separator + filename));
		}

		return files.toArray(new File[0]);
	}

	public void setEnvironments(Environment[] environments) {
		this.environments = environments;
	}

	public void setDefaultDirectoryScanner(DefaultDirectoryScanner defaultDirectoryScanner) {
		this.defaultDirectoryScanner = defaultDirectoryScanner;
	}

}
