package org.apache.logging.log4j.core.net;

import javax.naming.*;
import org.apache.logging.log4j.core.appender.*;
import java.io.*;
import javax.jms.*;
import org.apache.logging.log4j.*;

public class JMSTopicManager extends AbstractJMSManager
{
    private static final JMSTopicManagerFactory FACTORY;
    private TopicInfo info;
    private final String factoryBindingName;
    private final String topicBindingName;
    private final String userName;
    private final String password;
    private final Context context;
    
    protected JMSTopicManager(final String s, final Context context, final String factoryBindingName, final String topicBindingName, final String userName, final String password, final TopicInfo info) {
        super(s);
        this.context = context;
        this.factoryBindingName = factoryBindingName;
        this.topicBindingName = topicBindingName;
        this.userName = userName;
        this.password = password;
        this.info = info;
    }
    
    public static JMSTopicManager getJMSTopicManager(final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final String s8, final String s9) {
        if (s6 == null) {
            JMSTopicManager.LOGGER.error("No factory name provided for JMSTopicManager");
            return null;
        }
        if (s7 == null) {
            JMSTopicManager.LOGGER.error("No topic name provided for JMSTopicManager");
            return null;
        }
        return (JMSTopicManager)AbstractManager.getManager("JMSTopic:" + s6 + '.' + s7, JMSTopicManager.FACTORY, new FactoryData(s, s2, s3, s4, s5, s6, s7, s8, s9));
    }
    
    @Override
    public void send(final Serializable s) throws Exception {
        if (this.info == null) {
            this.info = connect(this.context, this.factoryBindingName, this.topicBindingName, this.userName, this.password, false);
        }
        super.send(s, (Session)TopicInfo.access$100(this.info), (MessageProducer)TopicInfo.access$200(this.info));
    }
    
    public void releaseSub() {
        if (this.info != null) {
            this.cleanup(false);
        }
    }
    
    private void cleanup(final boolean b) {
        TopicInfo.access$100(this.info).close();
        TopicInfo.access$300(this.info).close();
        this.info = null;
    }
    
    private static TopicInfo connect(final Context context, final String s, final String s2, final String s3, final String s4, final boolean b) throws Exception {
        final TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)AbstractJMSManager.lookup(context, s);
        TopicConnection topicConnection;
        if (s3 != null) {
            topicConnection = topicConnectionFactory.createTopicConnection(s3, s4);
        }
        else {
            topicConnection = topicConnectionFactory.createTopicConnection();
        }
        final TopicSession topicSession = topicConnection.createTopicSession(false, 1);
        final TopicPublisher publisher = topicSession.createPublisher((Topic)AbstractJMSManager.lookup(context, s2));
        topicConnection.start();
        return new TopicInfo(topicConnection, topicSession, publisher);
    }
    
    static TopicInfo access$1300(final Context context, final String s, final String s2, final String s3, final String s4, final boolean b) throws Exception {
        return connect(context, s, s2, s3, s4, b);
    }
    
    static Logger access$1400() {
        return JMSTopicManager.LOGGER;
    }
    
    static Logger access$1500() {
        return JMSTopicManager.LOGGER;
    }
    
    static {
        FACTORY = new JMSTopicManagerFactory(null);
    }
    
    private static class JMSTopicManagerFactory implements ManagerFactory
    {
        private JMSTopicManagerFactory() {
        }
        
        public JMSTopicManager createManager(final String s, final FactoryData factoryData) {
            final Context context = AbstractJMSManager.createContext(FactoryData.access$400(factoryData), FactoryData.access$500(factoryData), FactoryData.access$600(factoryData), FactoryData.access$700(factoryData), FactoryData.access$800(factoryData));
            return new JMSTopicManager(s, context, FactoryData.access$900(factoryData), FactoryData.access$1000(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), JMSTopicManager.access$1300(context, FactoryData.access$900(factoryData), FactoryData.access$1000(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), true));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        JMSTopicManagerFactory(final JMSTopicManager$1 object) {
            this();
        }
    }
    
    private static class FactoryData
    {
        private final String factoryName;
        private final String providerURL;
        private final String urlPkgPrefixes;
        private final String securityPrincipalName;
        private final String securityCredentials;
        private final String factoryBindingName;
        private final String topicBindingName;
        private final String userName;
        private final String password;
        
        public FactoryData(final String factoryName, final String providerURL, final String urlPkgPrefixes, final String securityPrincipalName, final String securityCredentials, final String factoryBindingName, final String topicBindingName, final String userName, final String password) {
            this.factoryName = factoryName;
            this.providerURL = providerURL;
            this.urlPkgPrefixes = urlPkgPrefixes;
            this.securityPrincipalName = securityPrincipalName;
            this.securityCredentials = securityCredentials;
            this.factoryBindingName = factoryBindingName;
            this.topicBindingName = topicBindingName;
            this.userName = userName;
            this.password = password;
        }
        
        static String access$400(final FactoryData factoryData) {
            return factoryData.factoryName;
        }
        
        static String access$500(final FactoryData factoryData) {
            return factoryData.providerURL;
        }
        
        static String access$600(final FactoryData factoryData) {
            return factoryData.urlPkgPrefixes;
        }
        
        static String access$700(final FactoryData factoryData) {
            return factoryData.securityPrincipalName;
        }
        
        static String access$800(final FactoryData factoryData) {
            return factoryData.securityCredentials;
        }
        
        static String access$900(final FactoryData factoryData) {
            return factoryData.factoryBindingName;
        }
        
        static String access$1000(final FactoryData factoryData) {
            return factoryData.topicBindingName;
        }
        
        static String access$1100(final FactoryData factoryData) {
            return factoryData.userName;
        }
        
        static String access$1200(final FactoryData factoryData) {
            return factoryData.password;
        }
    }
    
    private static class TopicInfo
    {
        private final TopicConnection conn;
        private final TopicSession session;
        private final TopicPublisher publisher;
        
        public TopicInfo(final TopicConnection conn, final TopicSession session, final TopicPublisher publisher) {
            this.conn = conn;
            this.session = session;
            this.publisher = publisher;
        }
        
        static TopicSession access$100(final TopicInfo topicInfo) {
            return topicInfo.session;
        }
        
        static TopicPublisher access$200(final TopicInfo topicInfo) {
            return topicInfo.publisher;
        }
        
        static TopicConnection access$300(final TopicInfo topicInfo) {
            return topicInfo.conn;
        }
    }
}
