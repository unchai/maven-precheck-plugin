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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import org.openwebtop.maven.plugins.precheck.webwork.WebworkConfiguration;
import org.openwebtop.maven.plugins.precheck.webwork.WebworkConfigurationParser;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;

/**
 * Webwork checker mojo test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class WebworkCheckerMojoTest {
	private WebworkConfigurationParser webworkConfigurationParser;
	private WebworkCheckerMojo webworkCheckerMojo;

	@Before
	public void setUp() throws Exception {
		webworkConfigurationParser = mock(WebworkConfigurationParser.class);

		webworkCheckerMojo = new WebworkCheckerMojo();
		webworkCheckerMojo.setWebworkConfigurationParser(webworkConfigurationParser);
	}

	@Test(expected = MojoFailureException.class)
	public void testExecute_DUPLICATE_ACTION_NAME() throws Exception {
		final File file = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork/xwork-action-error.xml"));

		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setWebworkConfigurationFiles(new File[] {file});

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	@Test(expected = MojoFailureException.class)
	public void testExecute_DUPLICATE_PACKAGE_NAME() throws Exception {
		final File file = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork/xwork-packagename-error.xml"));

		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setWebworkConfigurationFiles(new File[] {file});

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	@Test(expected = MojoFailureException.class)
	public void testExecute_DUPLICATE_NAMESPACE() throws Exception {
		final File file = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork/xwork-namespace-error.xml"));

		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setWebworkConfigurationFiles(new File[] {file});

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	@Test(expected = MojoFailureException.class)
	public void testExecute_FAILURE() throws Exception {
		final File file = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork/xwork.xml"));

		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setWebworkConfigurationFiles(new File[] {file, file});

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	@Test
	public void testExecute_SUCCESS() throws Exception {
		final File file = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork/xwork.xml"));

		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setWebworkConfigurationFiles(new File[] {file});

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	private List<WebworkPackage> getMock(File file) throws IOException, SAXException {
		final WebworkConfigurationParser parser = new WebworkConfigurationParser();

		return parser.getWebworkPackages(file);
	}

}
