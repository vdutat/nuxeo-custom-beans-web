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

import static org.nuxeo.ecm.webapp.documentsLists.DocumentsListsManager.CURRENT_DOCUMENT_SELECTION;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.webapp.documentsLists.DocumentsListsManager;

/**
*
* Every time a document is selected in a content view, the HTML element with id 'currentSelectionCount' (which you need to add) will be re-rendered.
* i.e.
* <pre>
* < h:outputText id="currentSelectionCount" value="#{documentsListsManager.getWorkingList('CURRENT_SELECTION').size()}">
* < /h:outputText>
* </pre>
*
* @see {@link https://jira.nuxeo.com/browse/SUPNXP-16590}.
*/
@Name("currentSelectionChangeListener")
@Scope(ScopeType.SESSION)
public class CurrentSelectionCountBean implements Serializable {

    private static final String ELEMENT_TO_REFRESH_ID = "currentSelectionCount";

    private static final long serialVersionUID = 1L;

    private static final Log LOGGER = LogFactory.getLog(CurrentSelectionCountBean.class);

    @In(create = true)
    private DocumentsListsManager documentsListsManager;

    @Observer(value = {CURRENT_DOCUMENT_SELECTION + "Updated"}, create = true)
    public void updated() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("<updated> document list '" + CURRENT_DOCUMENT_SELECTION + "' updated. Number of selected documents: " + documentsListsManager.getWorkingList(CURRENT_DOCUMENT_SELECTION).size());
        }
        // Re-render ELEMENT_TO_REFRESH_ID element in the page
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(ELEMENT_TO_REFRESH_ID);
    }
}
