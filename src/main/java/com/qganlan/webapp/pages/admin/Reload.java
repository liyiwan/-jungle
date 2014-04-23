package com.qganlan.webapp.pages.admin;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import com.qganlan.webapp.listener.StartupListener;
import com.qganlan.webapp.pages.Home;

import java.io.IOException;

/**
 * @author Serge Eby
 * @version $Id: Reload.java 5 2008-08-30 09:59:21Z serge.eby $
 */
public class Reload {
    @Inject
    private Messages messages;

    @Inject
    private ApplicationGlobals globals;

    @Inject
    private AlertManager alertManager;


    Object onActivate() throws IOException {
        StartupListener.setupContext(globals.getServletContext());
        alertManager.alert(Duration.TRANSIENT,
                Severity.INFO,
                messages.get("reload.succeeded"));
        return Home.class;
    }

}
