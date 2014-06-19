package com.qganlan.webapp.components;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;

import java.util.Locale;

public class Header {

    @Inject
    private Locale locale;

    @Inject
    private PersistentLocale persistentLocale;

}
