package com.qganlan.webapp.pages;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.appfuse.model.User;

import com.qganlan.service.StockManager;
import com.qganlan.webapp.services.SecurityContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
    
    @Inject
	private StockManager stockManager;
    
    public void onSynchronizeStock() {
    	stockManager.synchronizeStock();
    }

}
