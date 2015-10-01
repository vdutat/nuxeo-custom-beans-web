nuxeo-custom-beans-web
===

## About this module


### `currentUserHelper` bean

This module provides a Seam context variable `usersOfCurrentUserGroups` which is a list of users who are members of the current user's groups.

If users from some group(s) need to be excluded, you can call `#{currentUserHelper.getUsersOfCurrentUserGroupsExclude()}` and pass as parameter(s) the group(s) you need to exclude.

The list of users is initialized once during the user session.

You can empty the list of users by call `#{currentUserHelper.clearUsersOfCurrentUserGroups()}`.

### `popupHelperExtra` bean

Workaround for [NXP-17782 Fix content view refresh when child document title is changed using 'rename' from contextual popup menu](https://jira.nuxeo.com/browse/NXP-17782)

### `mySessionFlags` bean

Seam bean `mySessionFlags` offers a getter and setter on a `Boolean` flag `flag1`. This way, it can be used in the `NXQL` query of a content view. Its value can be set by a custom widget template used in the `Search layout` of the content view.
A simple use case is a content view listing all the `File` documents in the hierarchy tree of a `Folderish` document and a radio button in the content view search layout would exclude the direct children documents of the `Folderish` document.

#### `radio button` custom widget template  

`boolean_flag_search_form_widget_template.xhtml`

		<f:subview xmlns:f="http://java.sun.com/jsf/core"
		  xmlns="http://www.w3.org/1999/xhtml"
		  xmlns:h="http://java.sun.com/jsf/html"
		  xmlns:c="http://java.sun.com/jstl/core"
		  xmlns:a4j="http://richfaces.org/a4j"
		  xmlns:nxu="http://nuxeo.org/nxweb/util"
		  xmlns:nxdir="http://nuxeo.org/nxdirectory"
		  xmlns:nxl="http://nuxeo.org/nxforms/layout" id="#{widget.id}">
		
		  <h:selectOneRadio value="#{mySessionFlags.flag1}" id="#{widget.id}_checkbox_flag1">
		    <f:selectItem itemValue="#{true}" itemLabel="#{messages['label.yes']}" />
		    <f:selectItem itemValue="#{false}" itemLabel="#{messages['label.no']}" />
		  </h:selectOneRadio>
		
		</f:subview>

#### content view configuration

- query filter: `ecm:mixinType != 'HiddenInNavigation' AND ecm:isCheckedInVersion = 0 AND ecm:currentLifeCycleState != 'deleted' AND ecm:primaryType = 'File' AND ecm:path STARTSWITH ? AND ((ecm:parentId != ? AND ? = 1) OR ? = 0)`

- query parameters:
  - `#{currentDocument.pathAsString}`
  - `#{currentDocument.id}`
  - `#{mySessionFlags.flag1}`
  - `#{mySessionFlags.flag1}`

## Why this module

### `currentUserHelper` bean

These can be used as a `query parameters` to the NXQL query of a content view. Here is an example:
- NXQL: `ecm:mixinType != 'HiddenInNavigation' AND ecm:isCheckedInVersion = 0 AND ecm:currentLifeCycleState != 'deleted' AND dc:creator IN ? AND ecm:parentId = ?`
- query parameters:
  - `#{usersOfCurrentUserGroups}`
  - `#{currentDocument.id}`

### `popupHelperExtra` bean

After renamimg a document using the contextual menu in a content view, it could happen that the document title was not properly refreshed (i.e. after refreshing the page with `F5` key)

### `mySessionFlags` bean

Solution for [SUPNXP-14654 Filter in studio](https://jira.nuxeo.com/browse/SUPNXP-14654)


## Building

        mvn clean install

## Using

All you have to do is:

 - copy the bundle in `nxserver/plugins` or `nxserver/bundles`
 - restart the server
