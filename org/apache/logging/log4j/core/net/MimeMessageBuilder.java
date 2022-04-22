package org.apache.logging.log4j.core.net;

import javax.mail.*;
import org.apache.logging.log4j.core.helpers.*;
import javax.mail.internet.*;

public class MimeMessageBuilder
{
    private final MimeMessage message;
    
    public MimeMessageBuilder(final Session session) {
        this.message = new MimeMessage(session);
    }
    
    public MimeMessageBuilder setFrom(final String s) throws MessagingException {
        final InternetAddress address = parseAddress(s);
        if (null != address) {
            this.message.setFrom((Address)address);
        }
        else {
            this.message.setFrom();
        }
        return this;
    }
    
    public MimeMessageBuilder setReplyTo(final String s) throws MessagingException {
        final InternetAddress[] addresses = parseAddresses(s);
        if (null != addresses) {
            this.message.setReplyTo((Address[])addresses);
        }
        return this;
    }
    
    public MimeMessageBuilder setRecipients(final Message.RecipientType recipientType, final String s) throws MessagingException {
        final InternetAddress[] addresses = parseAddresses(s);
        if (null != addresses) {
            this.message.setRecipients(recipientType, (Address[])addresses);
        }
        return this;
    }
    
    public MimeMessageBuilder setSubject(final String s) throws MessagingException {
        if (s != null) {
            this.message.setSubject(s, Charsets.UTF_8.name());
        }
        return this;
    }
    
    public MimeMessage getMimeMessage() {
        return this.message;
    }
    
    private static InternetAddress parseAddress(final String s) throws AddressException {
        return (s == null) ? null : new InternetAddress(s);
    }
    
    private static InternetAddress[] parseAddresses(final String s) throws AddressException {
        return (InternetAddress[])((s == null) ? null : InternetAddress.parse(s, true));
    }
}
