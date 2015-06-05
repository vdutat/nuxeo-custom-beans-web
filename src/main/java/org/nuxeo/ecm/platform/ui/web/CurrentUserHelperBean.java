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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.usermanager.UserManager;

@Name("currentUserHelper")
@Scope(ScopeType.SESSION)
public class CurrentUserHelperBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log LOG = LogFactory.getLog(CurrentUserHelperBean.class);

//    @In(create = true, required = false)
//    protected transient CoreSession documentManager;
//
//    @In(create = true)
//    protected NavigationContext navigationContext;
//
//    @In(create = true, required = false)
//    protected transient FacesMessages facesMessages;
//    
//    @In(create = true)
//    protected DocumentsListsManager documentsListsManager;

    @In(create = true, required = false)
    protected NuxeoPrincipal currentNuxeoPrincipal;

    @In(create = true)
    protected transient UserManager userManager;

    private List<String> users = null;
    
    @Factory(value = "usersOfCurrentUserGroups", scope = ScopeType.SESSION)
    public List<String> getUsersOfCurrentUserGroups() {
        return getUsersOfCurrentUserGroupsExclude();
    }

    public List<String> getUsersOfCurrentUserGroupsExclude(String...groups) {
        if (users == null) {
            users = new ArrayList<String>();
            for (String groupId : currentNuxeoPrincipal.getAllGroups()) {
                if (Arrays.asList(groups).contains(groupId)) {
                    LOG.debug("group " + groupId + " excluded");
                    continue;
                }
                List<String> usersInGroups = userManager.getUsersInGroupAndSubGroups(groupId);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("\tAdding users of group " + groupId);
                    for (String user : usersInGroups) {
                        LOG.trace("\t\t" + user);
                    }
                }
                users.addAll(usersInGroups);
            }
            if (users.isEmpty()) {
                users.add(currentNuxeoPrincipal.getName());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("usersOfCurrentUserGroups contains " + users.size() + " users");
            for (String user : users) {
                LOG.debug("\t" + user);
            }
        }
        return users;
    }

}
