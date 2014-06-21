package com.qganlan.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.appfuse.service.MailEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.qganlan.service.EmailManager;

@Service("emailManager")
public class EmailManagerImpl implements EmailManager {
    
	@Autowired
	private MailEngine mailEngine;
	
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}
 
    public void sentText(String subject, String message, String[] receivers) {
    	SimpleMailMessage msg = new SimpleMailMessage();
    	msg.setFrom("18072708209@163.com");
    	msg.setTo(receivers);
    	msg.setSubject(subject);
    	msg.setText(message);
    	mailEngine.send(msg);
    }

    public void sentHtml(String subject, String message, String[] receivers) {
    	MimeMessage mimeMessage =((JavaMailSender) mailEngine.getMailSender()).createMimeMessage();
    	MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
    	MimeMailMessage msg = new MimeMailMessage(messageHelper);
    	msg.setFrom("18072708209@163.com");
    	msg.setTo(receivers);
    	msg.setSubject(subject);
    	try {
			messageHelper.setText(message, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	((JavaMailSender) mailEngine.getMailSender()).send(mimeMessage);
    }

}
