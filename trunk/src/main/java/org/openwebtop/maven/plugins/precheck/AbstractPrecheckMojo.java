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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

/**
 * Abstract precheck mojo
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 3.
 */
public abstract class AbstractPrecheckMojo extends AbstractMojo {
	/**
	 * Maven project
	 *
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * Maven system settings for Maven.
	 *
	 * @parameter expression="${settings}"
	 * @required
	 * @readonly
	 */
	private Settings settings;

	/**
	 * Skip current goals
	 * 
	 * @parameter default-value="false"
	 */
	protected boolean skip;

	public MavenProject getProject() {
		return project;
	}

	public Settings getSettings() {
		return settings;
	}

	/**
	 * Print info log
	 * 
	 * @param message
	 */
	public void printInfoLog(String message) {
		getLog().info(getGoalName() + " : " + message);
	}

	/**
	 * Print error log
	 * 
	 * @param message
	 */
	public void printErrorLog(String message, Throwable t) {
		getLog().error(getGoalName() + " : " + message, t);
	}

	/**
	 * Print debug log
	 * 
	 * @param message
	 */
	public void printDebugLog(String message) {
		getLog().debug(getGoalName() + " : " + message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skip == false) {
			printInfoLog("----- Start to '" + getGoalName() + "' -----");
			onExecute();
			printInfoLog("----- Goal '" + getGoalName() + "' has been done -----");
		}
	}

	/**
	 * Goal name
	 * 
	 * @return Goal name
	 */
	public abstract String getGoalName();

	/**
	 * Maven plugin entry point
	 * 
	 * @throws MojoExecutionException MojoExecutionException
	 * @throws MojoFailureException MojoFailureException
	 */
	public abstract void onExecute() throws MojoExecutionException, MojoFailureException;

}
