/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas.adaptors.ldap.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.ldap.support.LdapContextSource;

public class AuthenticatedLdapContextSource extends LdapContextSource {
    
    public DirContext getDirContext(final String principal,
        final String credentials) {
    final Hashtable<String, String> environment = (Hashtable) getAnonymousEnv().clone();

    environment.put(Context.SECURITY_PRINCIPAL, principal);
    environment.put(Context.SECURITY_CREDENTIALS, credentials);
    
    environment.remove("com.sun.jndi.ldap.connect.pool"); // remove this since we're modifying principal

    try {
        return getDirContextInstance(environment);
    } catch (final NamingException e) {
        throw new DataAccessResourceFailureException("Unable to create DirContext");
    }
}
}