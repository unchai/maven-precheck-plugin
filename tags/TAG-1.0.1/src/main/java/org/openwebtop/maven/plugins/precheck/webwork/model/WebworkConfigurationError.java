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
package org.openwebtop.maven.plugins.precheck.webwork.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Webwork configuration error
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class WebworkConfigurationError {
	private WebworkConfigurationErrorType errorType;
	private String packageName;
	private String namespace;
	private String actionName;
	private String actionClassName;

	public WebworkConfigurationErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(WebworkConfigurationErrorType errorType) {
		this.errorType = errorType;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionClassName() {
		return actionClassName;
	}

	public void setActionClassName(String actionClassName) {
		this.actionClassName = actionClassName;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String message = null;

		if (errorType == WebworkConfigurationErrorType.DUPLICATE_ACTION_NAME) {
			message = String.format("Duplicate action has been founded! [package_name=%s, namespace=%s, action_name=%s, action_class_name=%s]", packageName, namespace, actionName, actionClassName);
		} else if (errorType == WebworkConfigurationErrorType.DUPLICATE_NAMESPACE) {
			message = String.format("Duplicate namespace has been founded! [package_name=%s, namespace=%s]", packageName, namespace);
		} else if (errorType == WebworkConfigurationErrorType.DUPLICATION_PACKAGE_NAME) {
			message = String.format("Duplicate package name has been founded! [package_name=%s, namespace=%s]", packageName, namespace);
		} else {
			message = ToStringBuilder.reflectionToString(this);
		}

		return message;
	}
}
