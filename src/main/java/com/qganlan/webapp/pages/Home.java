package com.qganlan.webapp.pages;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.appfuse.model.User;
import com.qganlan.webapp.services.SecurityContext;
import org.slf4j.Logger;

public class Home {

    @Inject
    private Logger logger;

    @Inject
    private Messages messages;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AlertManager alertManager;

    @InjectPage
    private UserEdit userEdit;

    @Persist
    @Property
    private User currentUser;

}
