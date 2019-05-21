package org.nuxeo.ecm.platform.ui.web;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("dateTimeHelper")
@Scope(ScopeType.EVENT)
public class DateTimeHelperBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log LOG = LogFactory.getLog(DateTimeHelperBean.class);

    public String getFutureDate() {
        Date currentDate = new Date();
        // convert date to localdatetime
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // add 7 days
        localDateTime = localDateTime.plusDays(7);
        // get formatter
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // format future date
        String format = pattern.format(localDateTime);
        if (LOG.isDebugEnabled()) {
            LOG.warn("<getFutureDate> " + format);
        }
        return format;
    }
}
