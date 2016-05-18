/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.samples.portlet.filter;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Milen Dyankov
 */
@Component(immediate = true, property = {
		"javax.portlet.name=com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet",
		"javax.portlet.name=com_liferay_journal_content_web_portlet_JournalContentPortlet" }, service = PortletFilter.class)
public class ExampleContentFilter implements RenderFilter {

	@Override
	public void destroy() {
		// nothing to do
	}

	@Override
	public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain)
			throws IOException, PortletException {

		RenderResponseBuffer responseBuffer = new RenderResponseBuffer(response);
		chain.doFilter(request, responseBuffer);

		String content = responseBuffer.getResponseAsString();
		String replacedContent = content.replaceAll("Liferay", "<mark>Liferay</mark>");
		response.getWriter().write(replacedContent);
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
		// nothing to do
	}

}