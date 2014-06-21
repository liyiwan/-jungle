package com.qganlan.service;

public interface EmailManager {
    public void sentText(String subject, String message, String[] receivers);
    public void sentHtml(String subject, String message, String[] receivers);
}
