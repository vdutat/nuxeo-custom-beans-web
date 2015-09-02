package org.nuxeo.ecm.platform.ui.web;

import static org.jboss.seam.ScopeType.CONVERSATION;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.jboss.seam.core.Events;
import org.nuxeo.ecm.webapp.helpers.EventNames;

@Name("popupHelperExtra")
@Scope(CONVERSATION)
public class PopupHelperExtraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(PopupHelperExtraBean.class);

    @WebRemote
    public String refreshContentView(String popupDocId) {
        if (log.isDebugEnabled()) {
            log.debug("<refreshContentView> " + popupDocId);
        }
        Events.instance().raiseEvent(EventNames.DOCUMENT_CHILDREN_CHANGED);
        return "OK";
    }
    
}
