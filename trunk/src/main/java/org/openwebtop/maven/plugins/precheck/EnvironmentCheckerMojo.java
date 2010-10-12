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
import org.openwebtop.maven.plugins.precheck.environment.Environment;
import org.openwebtop.maven.plugins.precheck.environment.EnvironmentChecker;
import org.openwebtop.maven.plugins.precheck.environment.EnvironmentFiles;
import org.openwebtop.maven.plugins.precheck.environment.model.EnvironmentError;

/**
 * 
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

	@Override
	public String getGoalName() {
		return GOAL_NAME;
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		printLog("----- Start to check environment -----");

		if (skip) {
			printLog("----- Skip check environment -----");
			return;
		}

		if (ArrayUtils.isEmpty(environments)) {
			printLog("There is no configuration for checking environment. skipping...");

			return;
		}

		final List<EnvironmentError> environmentErrors = new ArrayList<EnvironmentError>();

		for (Environment environment : environments) {
			final EnvironmentChecker environmentChecker = new EnvironmentChecker();
			environmentChecker.setExtractRegExp(environment.getExtractRegExp());
			environmentChecker.setCheckTargetFiles(getFilelist(environment.getCheckTarget()));
			environmentChecker.setCheckSourceFiles(getFilelist(environment.getCheckSource()));

			try {
				environmentErrors.addAll(environmentChecker.check());
			} catch (IOException e) {
				throw new MojoFailureException("Cannot scan some files!");
			}
		}

		if (CollectionUtils.isNotEmpty(environmentErrors)) {
			for (EnvironmentError environmentError : environmentErrors) {
				printLog(environmentError.toString());
			}

			throw new MojoFailureException(String.format("%d files have been detected!", environmentErrors.size()));
		}

		printLog("----- There is no prohibit text -----");
	}

	private File[] getFilelist(EnvironmentFiles environmentFiles) {
		final DirectoryScanner directoryScanner = new DirectoryScanner();
		directoryScanner.setBasedir(environmentFiles.getBasedir());
		directoryScanner.setIncludes(environmentFiles.getIncludes());
		directoryScanner.setExcludes(environmentFiles.getExcludes());
		directoryScanner.scan();

		final String[] filelist = directoryScanner.getIncludedFiles();

		final List<File> files = new ArrayList<File>();

		for (String filename : filelist) {
			files.add(new File(environmentFiles.getBasedir() + File.separator + filename));
		}

		return files.toArray(new File[0]);
	}

	public void setEnvironments(Environment[] environments) {
		this.environments = environments;
	}

}
