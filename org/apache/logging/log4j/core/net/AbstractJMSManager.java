package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.core.appender.*;
import javax.naming.*;
import java.util.*;
import java.io.*;
import javax.jms.*;

public abstract class AbstractJMSManager extends AbstractManager
{
    public AbstractJMSManager(final String s) {
        super(s);
    }
    
    protected static Context createContext(final String s, final String s2, final String s3, final String s4, final String s5) throws NamingException {
        return new InitialContext(getEnvironment(s, s2, s3, s4, s5));
    }
    
    protected static Object lookup(final Context context, final String s) throws NamingException {
        return context.lookup(s);
    }
    
    protected static Properties getEnvironment(final String s, final String s2, final String s3, final String s4, final String s5) {
        final Properties properties = new Properties();
        if (s != null) {
            ((Hashtable<String, String>)properties).put("java.naming.factory.initial", s);
            if (s2 != null) {
                ((Hashtable<String, String>)properties).put("java.naming.provider.url", s2);
            }
            else {
                AbstractJMSManager.LOGGER.warn("The InitialContext factory name has been provided without a ProviderURL. This is likely to cause problems");
            }
            if (s3 != null) {
                ((Hashtable<String, String>)properties).put("java.naming.factory.url.pkgs", s3);
            }
            if (s4 != null) {
                ((Hashtable<String, String>)properties).put("java.naming.security.principal", s4);
                if (s5 != null) {
                    ((Hashtable<String, String>)properties).put("java.naming.security.credentials", s5);
                }
                else {
                    AbstractJMSManager.LOGGER.warn("SecurityPrincipalName has been set without SecurityCredentials. This is likely to cause problems.");
                }
            }
            return properties;
        }
        return null;
    }
    
    public abstract void send(final Serializable p0) throws Exception;
    
    public synchronized void send(final Serializable object, final Session session, final MessageProducer messageProducer) throws Exception {
        Object o;
        if (object instanceof String) {
            o = session.createTextMessage();
            ((TextMessage)o).setText((String)object);
        }
        else {
            o = session.createObjectMessage();
            ((ObjectMessage)o).setObject(object);
        }
        messageProducer.send((Message)o);
    }
}
