package org.nuxeo.ecm.platform.ui.web;

import static org.jboss.seam.ScopeType.CONVERSATION;

import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
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
    public DocumentModelList getChildrenSelectModel() {
        LOGGER.debug("<getChildrenSelectModel> ");
        return new DocumentModelListImpl(getTagService().getTagDocumentIds(documentManager, listLabel, null)
                .stream()
                .map(IdRef::new)
                .map(id -> documentManager.getDocument(id))
                .filter(doc -> !doc.isVersion())
                .collect(Collectors.toList()));
    }

}
