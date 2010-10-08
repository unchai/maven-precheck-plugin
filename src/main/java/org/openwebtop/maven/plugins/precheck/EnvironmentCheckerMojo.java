package org.openwebtop.maven.plugins.precheck;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.DirectoryScanner;
import org.openwebtop.maven.plugins.precheck.environment.Environment;

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

	private DirectoryScanner directoryScanner;

	public EnvironmentCheckerMojo() {
		directoryScanner = new DirectoryScanner();
	}

	@Override
	public String getGoalName() {
		return GOAL_NAME;
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		directoryScanner.setBasedir(environments[0].getCheckSource().getBasedir());
		directoryScanner.setIncludes(environments[0].getCheckSource().getIncludes());
		directoryScanner.setExcludes(environments[0].getCheckSource().getExcludes());
		directoryScanner.scan();

		final String[] filenames = directoryScanner.getIncludedFiles();

		for (String filename : filenames) {
			System.out.println(filename);
		}
	}

	public void setEnvironments(Environment[] environments) {
		this.environments = environments;
	}

	public void setDirectoryScanner(DirectoryScanner directoryScanner) {
		this.directoryScanner = directoryScanner;
	}

}
