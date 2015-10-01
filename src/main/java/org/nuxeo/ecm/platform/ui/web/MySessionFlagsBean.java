package org.nuxeo.ecm.platform.ui.web;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.webapp.helpers.EventNames;

@Name("mySessionFlags")
@Scope(ScopeType.SESSION)
public class MySessionFlagsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log LOGGER = LogFactory.getLog(MySessionFlagsBean.class);

    protected Boolean flag1 = false;

    public Boolean getFlag1() {
        LOGGER.debug("<getFlag1> " + this);
        return flag1;
    }

    public void setFlag1(Boolean flag) {
        LOGGER.debug("<setFlag1> " + this + " " + flag);
        this.flag1 = flag;
    }
    
    @Observer({EventNames.NAVIGATE_TO_DOCUMENT})
    public void resetFlags() {
        LOGGER.debug("<resetFlags> " + this);
        flag1 = false;
    }
}
