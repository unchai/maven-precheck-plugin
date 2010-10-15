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
package org.openwebtop.maven.plugins.precheck.customtag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.openwebtop.maven.plugins.precheck.customtag.model.CustomTagError;

/**
 * Custom tag checker
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 11.
 */
public class CustomTagChecker {
	private final static Pattern CUSTOM_TAG_DEFINITION_REGEX = Pattern.compile("<%@[ ]?taglib[^>]*prefix=[\"']?([a-zA-Z0-9-_]+)[\"'][^>]*>");
	private final static Pattern CUSTOM_TAG_REGEX = Pattern.compile("<([a-zA-Z0-9-_]+):[^>/\"' ]+");

	@SuppressWarnings("unchecked")
	public List<CustomTagError> check(File file) throws IOException {
		final List<CustomTagError> customTagErrors = new ArrayList<CustomTagError>();

		final String text = FileUtils.readFileToString(file);

		final Set<String> customTagPrefixSet = parseJsp(text, CUSTOM_TAG_DEFINITION_REGEX);
		final Set<String> usingCustomTagSet = parseJsp(text, CUSTOM_TAG_REGEX);

		final List<String> resultList = (List<String>)CollectionUtils.subtract(usingCustomTagSet, customTagPrefixSet);
		resultList.remove("jsp");

		if (CollectionUtils.isNotEmpty(resultList)) {
			final CustomTagError customTagError = new CustomTagError();
			customTagError.setFile(file);
			customTagError.setUndefiendCustomTags(resultList);

			customTagErrors.add(customTagError);
		}

		return customTagErrors;
	}

	private Set<String> parseJsp(String text, Pattern pattern) {
		final Set<String> customTagPrefixSet = new HashSet<String>();
		final Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			customTagPrefixSet.add(matcher.group(1));
		}

		return customTagPrefixSet;
	}

}
