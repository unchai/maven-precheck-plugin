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

import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkAction;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationError;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationErrorType;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;

/**
 * Webwork duplicate action name checker
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class DuplicateActionNameWebworkCheckerImpl implements WebworkChecker {

	/**
	 * {@inheritDoc}
	 */
	public List<WebworkConfigurationError> check(List<WebworkPackage> webworkPackages) {
		final List<WebworkConfigurationError> webworkConfigurationErrors = new ArrayList<WebworkConfigurationError>();
		for (WebworkPackage webworkPackage : webworkPackages) {
			final Set<String> actionNameSet = new HashSet<String>();

			for (WebworkAction webworkAction : webworkPackage.getActions()) {
				if (actionNameSet.contains(webworkAction.getName())) {
					final WebworkConfigurationError error = new WebworkConfigurationError();
					error.setErrorType(WebworkConfigurationErrorType.DUPLICATE_ACTION_NAME);
					error.setPackageName(webworkPackage.getName());
					error.setNamespace(webworkPackage.getNamespace());
					error.setActionName(webworkAction.getName());
					error.setActionClassName(webworkAction.getClassName());

					webworkConfigurationErrors.add(error);
				} else {
					actionNameSet.add(webworkAction.getName());
				}
			}
		}

		return webworkConfigurationErrors;
	}

}
