package com.qganlan.webapp.services;

import org.appfuse.model.User;

public interface EmailService {
    public void send(User user, String subject, String message, String url, boolean hint);
}
