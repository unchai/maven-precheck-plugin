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
package org.openwebtop.maven.plugins.precheck.prohibittext;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.openwebtop.maven.plugins.precheck.prohibittext.model.ProhibitTextError;

/**
 * Prohibit text checker test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class EnvironmentCheckerTest {
	private ProhibitTextChecker textChecker;

	@Before
	public void setUp() throws Exception {
		textChecker = new ProhibitTextChecker();
	}

	@Test
	public void testCheck() throws Exception {
		final File file = FileUtils.toFile(EnvironmentCheckerTest.class.getResource("test.html"));
		final String[] strings = {"(.*)(^ex)(.*)"};

		final List<ProhibitTextError> textErrors = textChecker.check(file, strings);

		assertThat(textErrors, is(not(nullValue())));
		assertThat(textErrors.size(), is(3));

		final ProhibitTextError textError1 = new ProhibitTextError();
		textError1.setFile(file);
		textError1.setProhibitTextPattern(strings[0]);
		textError1.setLineNumber(216);
		textError1.setText("exact copy.  The resulting work is called a &ldquo;modified version&rdquo; of the");

		final ProhibitTextError textError2 = new ProhibitTextError();
		textError2.setFile(file);
		textError2.setProhibitTextPattern(strings[0]);
		textError2.setLineNumber(237);
		textError2.setText("extent that warranties are provided), that licensees may convey the");

		final ProhibitTextError textError3 = new ProhibitTextError();
		textError3.setFile(file);
		textError3.setProhibitTextPattern(strings[0]);
		textError3.setLineNumber(680);
		textError3.setText("excuse you from the conditions of this License.  If you cannot convey a");

		assertThat(textErrors, hasItem(textError1));
		assertThat(textErrors, hasItem(textError2));
		assertThat(textErrors, hasItem(textError3));
	}

}
