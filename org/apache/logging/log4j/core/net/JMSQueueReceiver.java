package org.apache.logging.log4j.core.net;

import javax.naming.*;
import javax.jms.*;
import java.nio.charset.*;
import java.io.*;

public class JMSQueueReceiver extends AbstractJMSReceiver
{
    public JMSQueueReceiver(final String s, final String s2, final String s3, final String s4) {
        final InitialContext initialContext = new InitialContext();
        final QueueConnection queueConnection = ((QueueConnectionFactory)this.lookup(initialContext, s)).createQueueConnection(s3, s4);
        queueConnection.start();
        queueConnection.createQueueSession(false, 1).createReceiver((Queue)initialContext.lookup(s2)).setMessageListener((MessageListener)this);
    }
    
    public static void main(final String[] array) throws Exception {
        if (array.length != 4) {
            usage("Wrong number of arguments.");
        }
        new JMSQueueReceiver(array[0], array[1], array[2], array[3]);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
        System.out.println("Type \"exit\" to quit JMSQueueReceiver.");
        String line;
        do {
            line = bufferedReader.readLine();
        } while (line != null && !line.equalsIgnoreCase("exit"));
        System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
    }
    
    private static void usage(final String s) {
        System.err.println(s);
        System.err.println("Usage: java " + JMSQueueReceiver.class.getName() + " QueueConnectionFactoryBindingName QueueBindingName username password");
        System.exit(1);
    }
}
