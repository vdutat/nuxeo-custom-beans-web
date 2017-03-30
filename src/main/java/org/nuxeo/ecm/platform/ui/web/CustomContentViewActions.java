package org.nuxeo.ecm.platform.ui.web;

import static org.jboss.seam.ScopeType.CONVERSATION;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelFactory;
import org.nuxeo.ecm.platform.contentview.seam.ContentViewActions;

@Name("customContentViewActions")
@Scope(CONVERSATION)
public class CustomContentViewActions implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    private static final Log LOGGER = LogFactory.getLog(CustomContentViewActions.class);

    @In(create = true, required = false)
    private transient CoreSession documentManager;

	@In(create = true)
	private transient ContentViewActions contentViewActions;

	/**
	 * This method is called ONLY if <code>searchDocumentModel</code> is <code>null</code>
	 * @return
	 */
	public DocumentModel getSearchDocument() {
		LOGGER.warn("<getSearchDocument> " + contentViewActions.getCurrentContentView());
		DocumentModel bareDoc = DocumentModelFactory.createDocumentModel(contentViewActions.getCurrentContentView().getSearchDocumentModelType());
//		DocumentModel bareDoc = contentViewActions.getCurrentContentView().getSearchDocumentModel();
//		String fulltext = (String) bareDoc.getPropertyValue("SUPNXP-17164_cv:system_fulltext");
//		if (fulltext.isEmpty()) {
//			LOGGER.warn("<getSearchDocument> fulltext:" + fulltext);
//		}
		bareDoc.setPropertyValue("SUPNXP-17164_cv:system_fulltext", "blabla");
		return bareDoc;
	}
}
