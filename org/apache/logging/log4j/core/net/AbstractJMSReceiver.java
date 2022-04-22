package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.*;
import javax.jms.*;
import org.apache.logging.log4j.core.*;
import javax.naming.*;

public abstract class AbstractJMSReceiver extends AbstractServer implements MessageListener
{
    protected Logger logger;
    
    public AbstractJMSReceiver() {
        this.logger = LogManager.getLogger(this.getClass().getName());
    }
    
    public void onMessage(final Message message) {
        if (message instanceof ObjectMessage) {
            this.log((LogEvent)((ObjectMessage)message).getObject());
        }
        else {
            this.logger.warn("Received message is of type " + message.getJMSType() + ", was expecting ObjectMessage.");
        }
    }
    
    protected Object lookup(final Context context, final String s) throws NamingException {
        return context.lookup(s);
    }
}
