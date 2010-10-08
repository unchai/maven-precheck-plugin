package org.openwebtop.maven.plugins.precheck.environment;

import org.apache.commons.lang.builder.ToStringBuilder;

public class EnvironmentFiles {
	private String basedir;
	private String[] includes;
	private String[] excludes;

	public String getBasedir() {
		return basedir;
	}

	public void setBasedir(String basedir) {
		this.basedir = basedir;
	}

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
