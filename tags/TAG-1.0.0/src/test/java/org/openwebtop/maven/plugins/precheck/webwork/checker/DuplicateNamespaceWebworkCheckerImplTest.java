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

import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationError;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfigurationErrorType;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;

/**
 * Duplicate namespace webwork checker test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class DuplicateNamespaceWebworkCheckerImplTest {
	private WebworkChecker webworkChecker = new DuplicateNamespaceWebworkCheckerImpl();

	@Test
	public void testCheck() {
		final List<WebworkConfigurationError> webworkConfigurationErrors = webworkChecker.check(getMock());

		final WebworkConfigurationError webworkConfigurationError1 = new WebworkConfigurationError();
		webworkConfigurationError1.setErrorType(WebworkConfigurationErrorType.DUPLICATE_NAMESPACE);
		webworkConfigurationError1.setPackageName("org.openwebtop.maven.plugin.precheck.index");
		webworkConfigurationError1.setNamespace("/");

		final WebworkConfigurationError webworkConfigurationError2 = new WebworkConfigurationError();
		webworkConfigurationError2.setErrorType(WebworkConfigurationErrorType.DUPLICATE_NAMESPACE);
		webworkConfigurationError2.setPackageName("org.openwebtop.maven.plugin.precheck.test.posttest");
		webworkConfigurationError2.setNamespace("/");

		final WebworkConfigurationError webworkConfigurationError3 = new WebworkConfigurationError();
		webworkConfigurationError3.setErrorType(WebworkConfigurationErrorType.DUPLICATE_NAMESPACE);
		webworkConfigurationError3.setPackageName("org.openwebtop.maven.plugin.precheck.test.posttest");
		webworkConfigurationError3.setNamespace("/test");

		final WebworkConfigurationError webworkConfigurationError4 = new WebworkConfigurationError();
		webworkConfigurationError4.setErrorType(WebworkConfigurationErrorType.DUPLICATE_NAMESPACE);
		webworkConfigurationError4.setPackageName("org.openwebtop.maven.plugin.precheck.test.posttest");
		webworkConfigurationError4.setNamespace("/test");

		assertThat(webworkConfigurationErrors.size(), is(4));
		assertThat(webworkConfigurationErrors, hasItem(webworkConfigurationError1));
		assertThat(webworkConfigurationErrors, hasItem(webworkConfigurationError2));
		assertThat(webworkConfigurationErrors, hasItem(webworkConfigurationError3));
		assertThat(webworkConfigurationErrors, hasItem(webworkConfigurationError4));
	}

	private List<WebworkPackage> getMock() {
		final WebworkPackage webworkPackage1 = new WebworkPackage();
		webworkPackage1.setName("org.openwebtop.maven.plugin.precheck");
		webworkPackage1.setNamespace("/");

		final WebworkPackage webworkPackage2 = new WebworkPackage();
		webworkPackage2.setName("org.openwebtop.maven.plugin.precheck.index");
		webworkPackage2.setNamespace("/");

		final WebworkPackage webworkPackage3 = new WebworkPackage();
		webworkPackage3.setName("org.openwebtop.maven.plugin.precheck.test");
		webworkPackage3.setNamespace("/test");

		final WebworkPackage webworkPackage4 = new WebworkPackage();
		webworkPackage4.setName("org.openwebtop.maven.plugin.precheck.test.pretest");
		webworkPackage4.setNamespace("/test2");

		final WebworkPackage webworkPackage5 = new WebworkPackage();
		webworkPackage5.setName("org.openwebtop.maven.plugin.precheck.test.posttest");
		webworkPackage5.setNamespace("/");

		final WebworkPackage webworkPackage6 = new WebworkPackage();
		webworkPackage6.setName("org.openwebtop.maven.plugin.precheck.test.posttest");
		webworkPackage6.setNamespace("/test");

		final WebworkPackage webworkPackage7 = new WebworkPackage();
		webworkPackage7.setName("org.openwebtop.maven.plugin.precheck.test.posttest");
		webworkPackage7.setNamespace("/test");

		final List<WebworkPackage> webworkPackages = new ArrayList<WebworkPackage>();
		webworkPackages.add(webworkPackage1);
		webworkPackages.add(webworkPackage2);
		webworkPackages.add(webworkPackage3);
		webworkPackages.add(webworkPackage4);
		webworkPackages.add(webworkPackage5);
		webworkPackages.add(webworkPackage6);
		webworkPackages.add(webworkPackage7);

		return webworkPackages;
	}

}
