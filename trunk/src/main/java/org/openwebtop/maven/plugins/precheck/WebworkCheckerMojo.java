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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.openwebtop.maven.plugins.precheck.webwork.WebworkConfiguration;
import org.openwebtop.maven.plugins.precheck.webwork.WebworkConfigurationParser;
import org.openwebtop.maven.plugins.precheck.webwork.checker.DuplicateActionNameWebworkCheckerImpl;
import org.openwebtop.maven.plugins.precheck.webwork.checker.DuplicateNamespaceWebworkCheckerImpl;
import org.openwebtop.maven.plugins.precheck.webwork.checker.DuplicatedPackageNameWebworkCheckerImpl;
import org.openwebtop.maven.plugins.precheck.webwork.checker.WebworkChecker;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationError;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;
import org.xml.sax.SAXException;

/**
 * The WebworkCheckerMojo is used to check webwork configuration.
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 *
 * @goal webwork
 * @phase process-resources
 */
public class WebworkCheckerMojo extends AbstractPrecheckMojo {
	private final static String GOAL_NAME = "webwork";

	/**
	 * Webwork configuration
	 *
	 * @parameter
	 */
	private WebworkConfiguration webworkConfiguration;

	private WebworkConfigurationParser webworkConfigurationParser;
	private Set<WebworkChecker> webworkCheckers;

	public WebworkCheckerMojo() {
		webworkConfigurationParser = new WebworkConfigurationParser();

		webworkCheckers = new HashSet<WebworkChecker>();
		webworkCheckers.add(new DuplicateActionNameWebworkCheckerImpl());
		webworkCheckers.add(new DuplicatedPackageNameWebworkCheckerImpl());
		webworkCheckers.add(new DuplicateNamespaceWebworkCheckerImpl());
	}

	@Override
	public String getGoalName() {
		return GOAL_NAME;
	}

	/**
	 * Mojo execute
	 *
	 * @throws MojoExecutionException MojoExecutionException
	 * @throws MojoFailureException MojoFailureException
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		printLog("----- Start to check webwork configuration -----");
		printLog("Target files : " + ArrayUtils.toString(webworkConfiguration.getWebworkConfigurationFiles()));

		if (skip) {
			printLog("----- Skip check webwork configuration -----");
			return;
		}

		final List<WebworkPackage> webworkPackages = new ArrayList<WebworkPackage>();

		for (File webworkConfigurationFile : webworkConfiguration.getWebworkConfigurationFiles()) {
			try {
				webworkPackages.addAll(webworkConfigurationParser.getWebworkPackages(webworkConfigurationFile));
			} catch (IOException e) {
				throw new MojoFailureException(String.format("Cannot read configuration file! [%s]", webworkConfigurationFile.getName()));
			} catch (SAXException e) {
				throw new MojoFailureException(String.format("Cannot parse configuration file! [%s]", webworkConfigurationFile.getName()));
			}
		}

		final List<WebworkConfigurationError> webworkConfigurationErrors = new ArrayList<WebworkConfigurationError>();

		for (WebworkChecker webworkChecker : webworkCheckers) {
			webworkConfigurationErrors.addAll(webworkChecker.check(webworkPackages));
		}

		if (CollectionUtils.isNotEmpty(webworkConfigurationErrors)) {
			for (WebworkConfigurationError error : webworkConfigurationErrors) {
				getLog().error(error.toString());
			}

			throw new MojoFailureException("Webwork configuration has problems.");
		}

		printLog("----- Webwork configuration check has been done -----");
	}

	public WebworkConfigurationParser getWebworkConfigurationParser() {
		return webworkConfigurationParser;
	}

	public void setWebworkConfigurationParser(WebworkConfigurationParser webworkConfigurationParser) {
		this.webworkConfigurationParser = webworkConfigurationParser;
	}

	public void setWebworkConfiguration(WebworkConfiguration webworkConfiguration) {
		this.webworkConfiguration = webworkConfiguration;
	}

}
