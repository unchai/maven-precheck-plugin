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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.DirectoryScanner;
import org.junit.Before;
import org.junit.Test;
import org.openwebtop.maven.plugins.precheck.webwork.WebworkConfigurationParser;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkConfiguration;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;
import org.xml.sax.SAXException;

/**
 * Webwork checker mojo test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class WebworkCheckerMojoTest {
	private WebworkConfigurationParser webworkConfigurationParser;
	private WebworkCheckerMojo webworkCheckerMojo;
	private DirectoryScanner directoryScanner;

	@Before
	public void setUp() throws Exception {
		webworkConfigurationParser = mock(WebworkConfigurationParser.class);
		directoryScanner = mock(DirectoryScanner.class);

		webworkCheckerMojo = new WebworkCheckerMojo();
		webworkCheckerMojo.setWebworkConfigurationParser(webworkConfigurationParser);
		webworkCheckerMojo.setDirectoryScanner(directoryScanner);
	}

	@Test(expected = MojoFailureException.class)
	public void testExecute_DUPLICATE_ACTION_NAME() throws Exception {
		final File basedir = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork"));
		final String filename = "xwork-action-error.xml";
		final File file = new File(basedir.getAbsolutePath() + File.separator + filename);

		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {filename});
		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setBasedir(basedir);

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	/**
	 * Test when webwork configuration have duplicate package name
	 */
	@Test(expected = MojoFailureException.class)
	public void testExecute_CASE1() throws Exception {
		final File basedir = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork"));
		final String filename = "xwork-packagename-error.xml";
		final File file = new File(basedir.getAbsolutePath() + File.separator + filename);

		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {filename});
		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setBasedir(basedir);

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	/**
	 * Test when webwork configuration have duplicate namespace
	 */
	@Test(expected = MojoFailureException.class)
	public void testExecute_CASE2() throws Exception {
		final File basedir = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork"));
		final String filename = "xwork-namespace-error.xml";
		final File file = new File(basedir.getAbsolutePath() + File.separator + filename);

		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {filename});
		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setBasedir(basedir);

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	/**
	 * Test when webwork configuration check fail
	 */
	@Test(expected = MojoFailureException.class)
	public void testExecute_CASE3() throws Exception {
		final File basedir = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork"));
		final String filename = "xwork-packagename-error.xml";
		final File file = new File(basedir.getAbsolutePath() + File.separator + filename);

		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {filename, filename});
		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setBasedir(basedir);

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	/**
	 * Test when webwork configuration check success
	 */
	@Test
	public void testExecute_CASE4() throws Exception {
		final File basedir = FileUtils.toFile(WebworkCheckerMojoTest.class.getResource("webwork"));
		final String filename = "xwork.xml";
		final File file = new File(basedir.getAbsolutePath() + File.separator + filename);

		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {filename});
		when(webworkConfigurationParser.getWebworkPackages(file)).thenReturn(getMock(file));

		final WebworkConfiguration webworkConfiguration = new WebworkConfiguration();
		webworkConfiguration.setBasedir(basedir);

		webworkCheckerMojo.setWebworkConfiguration(webworkConfiguration);
		webworkCheckerMojo.execute();
	}

	/**
	 * Test when webwork configuration file have I/O problem.
	 */
	@Test(expected = MojoFailureException.class)
	public void testExecute_CASE5() throws Exception {
		when(webworkConfigurationParser.getWebworkPackages(any(File.class))).thenThrow(new IOException());
		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {"test"});

		webworkCheckerMojo.setWebworkConfiguration(new WebworkConfiguration());
		webworkCheckerMojo.execute();
	}

	/**
	 * Test when webwork configuration file have XML parsing problem.
	 */
	@Test(expected = MojoFailureException.class)
	public void testExecute_CASE6() throws Exception {
		when(webworkConfigurationParser.getWebworkPackages(any(File.class))).thenThrow(new SAXException());
		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {"test"});

		webworkCheckerMojo.setWebworkConfiguration(new WebworkConfiguration());
		webworkCheckerMojo.execute();
	}

	private List<WebworkPackage> getMock(File file) throws IOException, SAXException {
		final WebworkConfigurationParser parser = new WebworkConfigurationParser();

		return parser.getWebworkPackages(file);
	}

	@Test
	public void testExecution_CASE7() throws Exception {
		webworkCheckerMojo.setWebworkConfiguration(null);
		webworkCheckerMojo.execute();

		verify(webworkConfigurationParser, never()).getWebworkPackages(any(File.class));
	}
}
