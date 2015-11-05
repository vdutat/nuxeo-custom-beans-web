package org.nuxeo.ecm.platform.ui.web;

import static org.jboss.seam.ScopeType.CONVERSATION;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.impl.DocumentModelListImpl;
import org.nuxeo.ecm.platform.tag.web.TagActionsBean;

@Name("tagActions")
@Scope(CONVERSATION)
@Install(precedence=Install.DEPLOYMENT)
public class CustomTagActionsBean extends TagActionsBean {

    private static final long serialVersionUID = 1L;

    private static final Log LOGGER = LogFactory.getLog(CustomTagActionsBean.class);

    @Override
    public DocumentModelList getChildrenSelectModel() throws ClientException {
        LOGGER.debug("<getChildrenSelectModel> ");
        if (StringUtils.isBlank(listLabel)) {
            return new DocumentModelListImpl(0);
        } else {
            List<String> ids = getTagService().getTagDocumentIds(documentManager, listLabel, null);
            DocumentModelList docs = new DocumentModelListImpl(ids.size());
            DocumentModel doc = null;
            for (String id : ids) {
                try {
                    doc = documentManager.getDocument(new IdRef(id));
                } catch (ClientException e) {
                    LOGGER.error(e);
                }
                if (doc != null && !doc.isVersion()) {
                    docs.add(doc);
                    doc = null;
                }
            }
            return docs;
        }
    }

}
