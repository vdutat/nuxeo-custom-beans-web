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
 */
package org.nuxeo.ecm.platform.ui.web;

import org.jboss.seam.ScopeType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Validator ensuring <code>String</code> items of the list are unique.
 *
 * @see https://jira.nuxeo.com/browse/SUPNXP-16897
 */
@Name("listUniqueItemsValidator")
@Scope(ScopeType.CONVERSATION)
public class ListWidgetUniqueItemsValidator implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log LOGGER = LogFactory.getLog(ListWidgetUniqueItemsValidator.class);

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        LOGGER.warn("<validate> ");
        List<String> list = (List<String>) value;
        Set<String> set = new HashSet<String>(list);
        if (set.size() < list.size()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "List validation failed", "List items are not unique.");
            context.addMessage(null, message);
            throw new ValidatorException(message);
        }
    }

}
