package org.nuxeo.ecm.platform.ui.web;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Validator ensuring date in input is a date in the future.
 *
 * @see https://jira.nuxeo.com/browse/SUPNXP-17294
 */
@Name("dateInFutureValidator")
@Scope(ScopeType.CONVERSATION)
public class FutureDateValidator implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log LOGGER = LogFactory.getLog(FutureDateValidator.class);

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Date inputDate = (Date) value;
        LOGGER.warn("<validate> " + inputDate);
        Instant inputInstant = inputDate.toInstant();
        if (inputInstant.isBefore(ZonedDateTime.now().toInstant())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date validation failed", "Provided date is in the past, should be in the future.");
            context.addMessage(null, message);
            throw new ValidatorException(message);
        }
    }

}
