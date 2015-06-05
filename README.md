# nuxeo-custom-beans-web
===

## About this module

This module provides a Seam context variable `usersOfCurrentUserGroups` which is a list of users who are members of the current user's groups.

If some groups needs to be excluded, you can call `#{currentUserHelper.getUsersOfCurrentUserGroupsExclude()}` and pass as parameter(s) the group(s) you need to exclude.

The list of users is initialized once during the user session.

You can empty the list of users by call `#{currentUserHelper.clearUsersOfCurrentUserGroups()}`.

## Why this module

These can be used as a `query parameters` to the NXQL query of a content view. Here is an example:
- NXQL: `ecm:mixinType != 'HiddenInNavigation' AND ecm:isCheckedInVersion = 0 AND ecm:currentLifeCycleState != 'deleted' AND dc:creator IN (?) AND ecm:parentId = ?`
- query parameters:
  - `#{usersOfCurrentUserGroups}`
  - `#{currentDocument.id}`

## Building

        mvn clean install

## Using

All you have to do is:

 - to copy the bundle in `nxserver/plugins` or `nxserver/bundles`
 - restart the server
