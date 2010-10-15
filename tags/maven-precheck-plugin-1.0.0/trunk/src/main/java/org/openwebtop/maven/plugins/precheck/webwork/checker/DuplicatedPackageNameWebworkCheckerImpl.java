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
package org.openwebtop.maven.plugins.precheck.webwork.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationError;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationErrorType;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;

/**
 * Webwork duplicate package name checker
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class DuplicatedPackageNameWebworkCheckerImpl implements WebworkChecker {

	/**
	 * {@inheritDoc}
	 */
	public List<WebworkConfigurationError> check(List<WebworkPackage> webworkPackages) {
		final List<WebworkConfigurationError> webworkConfigurationErrors = new ArrayList<WebworkConfigurationError>();
		final Set<String> packageNameSet = new HashSet<String>();

		for (WebworkPackage webworkPackage : webworkPackages) {
			if (packageNameSet.contains(webworkPackage.getName())) {
				final WebworkConfigurationError error = new WebworkConfigurationError();
				error.setErrorType(WebworkConfigurationErrorType.DUPLICATION_PACKAGE_NAME);
				error.setPackageName(webworkPackage.getName());
				error.setNamespace(webworkPackage.getNamespace());

				webworkConfigurationErrors.add(error);
			} else {
				packageNameSet.add(webworkPackage.getName());
			}
		}

		return webworkConfigurationErrors;
	}

}
