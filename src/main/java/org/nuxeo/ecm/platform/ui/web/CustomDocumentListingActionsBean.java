/*
 * (C) Copyright ${year} Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     vdutat
 */

package org.nuxeo.ecm.platform.ui.web;

import java.io.Serializable;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.webapp.documentsLists.DocumentsListsManager;

/**
 *
 * @see {@link https://jira.nuxeo.com/browse/SUPNXP-16300}
 *
 */
@Name("customDocumentListingActions")
@Scope(ScopeType.EVENT)
public class CustomDocumentListingActionsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @In(create = true)
    protected DocumentsListsManager documentsListsManager;

    /**
     * Checks if all documents in the provided document list are of the provided document type.
     * This can be used in the custom EL expression of a 'Worklist' user action.
     *
     * @param listName Worklist name
     * @param docType Document type
     * @return
     * @since TODO
     */
    public boolean areDocumentsOfType(String listName, String docType) {
        if (documentsListsManager.isWorkingListEmpty(listName)) {
            return false;
        }
        for (DocumentModel doc : documentsListsManager.getWorkingList(listName)) {
            if (!docType.equals(doc.getType())) {
                return false;
            }
        }
        return true;
    }

}
