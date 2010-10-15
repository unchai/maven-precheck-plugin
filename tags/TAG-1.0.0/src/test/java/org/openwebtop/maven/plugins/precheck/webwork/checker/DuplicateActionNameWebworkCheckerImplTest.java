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

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkAction;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationError;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationErrorType;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;

/**
 * Duplicate action name webwork checker test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class DuplicateActionNameWebworkCheckerImplTest {
	private WebworkChecker webworkChecker = new DuplicateActionNameWebworkCheckerImpl();

	@Test
	public void testCheck() {
		final List<WebworkConfigurationError> webworkConfigurationErrors = webworkChecker.check(getMock());

		final WebworkConfigurationError webworkConfigurationError1 = new WebworkConfigurationError();
		webworkConfigurationError1.setErrorType(WebworkConfigurationErrorType.DUPLICATE_ACTION_NAME);
		webworkConfigurationError1.setPackageName("org.openwebtop.maven.plugin.precheck");
		webworkConfigurationError1.setNamespace("/");
		webworkConfigurationError1.setActionName("test");
		webworkConfigurationError1.setActionClassName("org.openwebtop.maven.plugin.TestTwoAction");

		final WebworkConfigurationError webworkConfigurationError2 = new WebworkConfigurationError();
		webworkConfigurationError2.setErrorType(WebworkConfigurationErrorType.DUPLICATE_ACTION_NAME);
		webworkConfigurationError2.setPackageName("org.openwebtop.maven.plugin.precheck");
		webworkConfigurationError2.setNamespace("/");
		webworkConfigurationError2.setActionName("test");
		webworkConfigurationError2.setActionClassName("org.openwebtop.maven.plugin.TestThreeAction");

		final WebworkConfigurationError webworkConfigurationError3 = new WebworkConfigurationError();
		webworkConfigurationError3.setErrorType(WebworkConfigurationErrorType.DUPLICATE_ACTION_NAME);
		webworkConfigurationError3.setPackageName("org.openwebtop.maven.plugin.precheck");
		webworkConfigurationError3.setNamespace("/");
		webworkConfigurationError3.setActionName("john");
		webworkConfigurationError3.setActionClassName("org.openwebtop.maven.plugin.JohnTwoAction");

		assertThat(webworkConfigurationErrors.size(), is(3));
		assertThat(webworkConfigurationErrors, hasItem(webworkConfigurationError1));
		assertThat(webworkConfigurationErrors, hasItem(webworkConfigurationError2));
		assertThat(webworkConfigurationErrors, hasItem(webworkConfigurationError3));
	}

	private List<WebworkPackage> getMock() {
		final List<WebworkAction> webworkActions = new ArrayList<WebworkAction>();

		final WebworkAction webworkAction1 = new WebworkAction();
		webworkAction1.setName("index");
		webworkAction1.setClassName("org.openwebtop.maven.plugin.IndexAction");

		final WebworkAction webworkAction2 = new WebworkAction();
		webworkAction2.setName("test");
		webworkAction2.setClassName("org.openwebtop.maven.plugin.TestOneAction");

		final WebworkAction webworkAction3 = new WebworkAction();
		webworkAction3.setName("test");
		webworkAction3.setClassName("org.openwebtop.maven.plugin.TestTwoAction");

		final WebworkAction webworkAction4 = new WebworkAction();
		webworkAction4.setName("test");
		webworkAction4.setClassName("org.openwebtop.maven.plugin.TestThreeAction");

		final WebworkAction webworkAction5 = new WebworkAction();
		webworkAction5.setName("john");
		webworkAction5.setClassName("org.openwebtop.maven.plugin.JohnOneAction");

		final WebworkAction webworkAction6 = new WebworkAction();
		webworkAction6.setName("john");
		webworkAction6.setClassName("org.openwebtop.maven.plugin.JohnTwoAction");

		webworkActions.add(webworkAction1);
		webworkActions.add(webworkAction2);
		webworkActions.add(webworkAction3);
		webworkActions.add(webworkAction4);
		webworkActions.add(webworkAction5);
		webworkActions.add(webworkAction6);

		final WebworkPackage webworkPackage = new WebworkPackage();
		webworkPackage.setName("org.openwebtop.maven.plugin.precheck");
		webworkPackage.setNamespace("/");
		webworkPackage.setActions(webworkActions);

		final List<WebworkPackage> webworkPackages = new ArrayList<WebworkPackage>();
		webworkPackages.add(webworkPackage);

		return webworkPackages;
	}

}
