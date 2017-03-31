package org.nuxeo.ecm.platform.ui.web;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.automation.core.scripting.CoreFunctions;

/**
*
* Makes date manipulation methods of CoreFunctions available in the 'Seam' context.
* i.e.
* <pre>
* < h:outputText id="formatDate" value="#{Fn.date(currentDocument.dublincore.modified).days(15).format("dd/MM/yyyy hh:mm:ss")}">
* < /h:outputText>
* </pre>
*
* @see {@link https://jira.nuxeo.com/browse/SUPNXP-19647}.
*/
@Name("Fn")
@Scope(ScopeType.SESSION)
public class PlatformFunctionsBean extends CoreFunctions implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log LOGGER = LogFactory.getLog(PlatformFunctionsBean.class);
}
