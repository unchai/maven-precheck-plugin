package org.openwebtop.maven.plugins.precheck.environment;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Environment {
	private String extractRegExp;
	private EnvironmentFiles checkTarget;
	private EnvironmentFiles checkSource;

	public String getExtractRegExp() {
		return extractRegExp;
	}

	public void setExtractRegExp(String extractRegExp) {
		this.extractRegExp = extractRegExp;
	}

	public EnvironmentFiles getCheckTarget() {
		return checkTarget;
	}

	public void setCheckTarget(EnvironmentFiles checkTarget) {
		this.checkTarget = checkTarget;
	}

	public EnvironmentFiles getCheckSource() {
		return checkSource;
	}

	public void setCheckSource(EnvironmentFiles checkSource) {
		this.checkSource = checkSource;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
