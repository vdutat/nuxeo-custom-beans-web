/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     vdutat
 *
 */
package org.nuxeo.ecm.platform.ui.web;

import static org.jboss.seam.annotations.Install.APPLICATION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.core.ConversationEntry;
import org.jboss.seam.jsf.concurrency.ConcurrentRequestResolver;
import org.nuxeo.ecm.platform.ui.web.rest.api.URLPolicyService;
import org.nuxeo.ecm.platform.ui.web.seam.NuxeoConcurrentRequestResolver;

@Scope(ScopeType.STATELESS)
@Name(ConcurrentRequestResolver.NAME)
@Install(precedence = APPLICATION)
@BypassInterceptors
public class CustomConcurrentRequestResolver extends NuxeoConcurrentRequestResolver {

	private static final Log LOGGER = LogFactory.getLog(CustomConcurrentRequestResolver.class);

	@Override
	public boolean handleConcurrentRequest(ConversationEntry ce, HttpServletRequest request,
			HttpServletResponse response) {
    	LOGGER.error("handleConcurrentRequest");
        if (request.getMethod().equalsIgnoreCase("get")) {
            request.setAttribute(URLPolicyService.DISABLE_ACTION_BINDING_KEY, Boolean.TRUE);
            // log an error instead of displaying a warning in the JSF UI
            LOGGER.warn("This page may be not up to date, an other concurrent requests is still running");
            return true;
        } else {
        	return super.handleConcurrentRequest(ce, request, response);
        }
	}

}
