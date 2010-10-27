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
package org.openwebtop.maven.plugins.precheck.environment.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openwebtop.maven.plugins.precheck.common.model.DefaultDirectoryScannerConfiguration;

/**
 * Environment checker configuration
 * 
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 10. 14.
 */
public class Environment {
	private String description;
	private String extractRegExp;
	private DefaultDirectoryScannerConfiguration checkTarget;
	private DefaultDirectoryScannerConfiguration checkSource;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtractRegExp() {
		return extractRegExp;
	}

	public void setExtractRegExp(String extractRegExp) {
		this.extractRegExp = extractRegExp;
	}

	public DefaultDirectoryScannerConfiguration getCheckTarget() {
		return checkTarget;
	}

	public void setCheckTarget(DefaultDirectoryScannerConfiguration checkTarget) {
		this.checkTarget = checkTarget;
	}

	public DefaultDirectoryScannerConfiguration getCheckSource() {
		return checkSource;
	}

	public void setCheckSource(DefaultDirectoryScannerConfiguration checkSource) {
		this.checkSource = checkSource;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
