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

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.openwebtop.maven.plugins.precheck.common.DefaultDirectoryScanner;
import org.openwebtop.maven.plugins.precheck.common.model.DefaultDirectoryScannerConfiguration;
import org.openwebtop.maven.plugins.precheck.prohibittext.ProhibitTextChecker;
import org.openwebtop.maven.plugins.precheck.prohibittext.model.ProhibitText;

/**
 * Prohibit text checker mojo test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class ProhibitTextCheckerMojoTest {
	private DefaultDirectoryScanner defaultDirectoryScanner;
	private ProhibitTextChecker prohibitTextChecker;
	private ProhibitTextCheckerMojo prohibitTextCheckerMojo;

	@Before
	public void setUp() throws Exception {
		defaultDirectoryScanner = mock(DefaultDirectoryScanner.class);
		prohibitTextChecker = mock(ProhibitTextChecker.class);

		prohibitTextCheckerMojo = new ProhibitTextCheckerMojo();
		prohibitTextCheckerMojo.setDefaultDirectoryScanner(defaultDirectoryScanner);
		prohibitTextCheckerMojo.setProhibitTextChecker(prohibitTextChecker);
	}

	/**
	 * Test when prohibit text found.
	 */
	@Test
	public void testExecute_CASE1() throws Exception {
		when(defaultDirectoryScanner.getIncludedFiles(any(DefaultDirectoryScannerConfiguration.class))).thenReturn(new String[] {"test.html"});

		final File baseDirectory = FileUtils.toFile(ProhibitTextCheckerMojoTest.class.getResource("prohibittext"));
		final String[] errorString = new String[] {"error_string"};

		final ProhibitText prohibitText = new ProhibitText();
		prohibitText.setBasedir(baseDirectory);
		prohibitText.setProhibitTextPatterns(new String[] {"error_string"});

		prohibitTextCheckerMojo.setProhibitTexts(new ProhibitText[] {prohibitText});
		prohibitTextCheckerMojo.execute();

		final File file = FileUtils.toFile(ProhibitTextCheckerMojoTest.class.getResource("prohibittext/test.html"));
		verify(prohibitTextChecker, times(1)).check(file, errorString);
	}

	@Test
	public void textExecute_CASE2() throws Exception {
		prohibitTextCheckerMojo.setProhibitTexts(null);
		prohibitTextCheckerMojo.execute();

		verify(prohibitTextChecker, never()).check(any(File.class), any(String[].class));
	}

}
