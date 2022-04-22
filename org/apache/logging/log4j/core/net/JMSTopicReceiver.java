package org.apache.logging.log4j.core.net;

import javax.naming.*;
import javax.jms.*;
import java.nio.charset.*;
import java.io.*;

public class JMSTopicReceiver extends AbstractJMSReceiver
{
    public JMSTopicReceiver(final String s, final String s2, final String s3, final String s4) {
        final InitialContext initialContext = new InitialContext();
        final TopicConnection topicConnection = ((TopicConnectionFactory)this.lookup(initialContext, s)).createTopicConnection(s3, s4);
        topicConnection.start();
        topicConnection.createTopicSession(false, 1).createSubscriber((Topic)initialContext.lookup(s2)).setMessageListener((MessageListener)this);
    }
    
    public static void main(final String[] array) throws Exception {
        if (array.length != 4) {
            usage("Wrong number of arguments.");
        }
        new JMSTopicReceiver(array[0], array[1], array[2], array[3]);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
        System.out.println("Type \"exit\" to quit JMSTopicReceiver.");
        String line;
        do {
            line = bufferedReader.readLine();
        } while (line != null && !line.equalsIgnoreCase("exit"));
        System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
    }
    
    private static void usage(final String s) {
        System.err.println(s);
        System.err.println("Usage: java " + JMSTopicReceiver.class.getName() + " TopicConnectionFactoryBindingName TopicBindingName username password");
        System.exit(1);
    }
}
