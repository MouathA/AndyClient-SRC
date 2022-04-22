package org.apache.logging.log4j.core.net;

import javax.naming.*;
import org.apache.logging.log4j.core.appender.*;
import java.io.*;
import javax.jms.*;
import org.apache.logging.log4j.*;

public class JMSQueueManager extends AbstractJMSManager
{
    private static final JMSQueueManagerFactory FACTORY;
    private QueueInfo info;
    private final String factoryBindingName;
    private final String queueBindingName;
    private final String userName;
    private final String password;
    private final Context context;
    
    protected JMSQueueManager(final String s, final Context context, final String factoryBindingName, final String queueBindingName, final String userName, final String password, final QueueInfo info) {
        super(s);
        this.context = context;
        this.factoryBindingName = factoryBindingName;
        this.queueBindingName = queueBindingName;
        this.userName = userName;
        this.password = password;
        this.info = info;
    }
    
    public static JMSQueueManager getJMSQueueManager(final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final String s8, final String s9) {
        if (s6 == null) {
            JMSQueueManager.LOGGER.error("No factory name provided for JMSQueueManager");
            return null;
        }
        if (s7 == null) {
            JMSQueueManager.LOGGER.error("No topic name provided for JMSQueueManager");
            return null;
        }
        return (JMSQueueManager)AbstractManager.getManager("JMSQueue:" + s6 + '.' + s7, JMSQueueManager.FACTORY, new FactoryData(s, s2, s3, s4, s5, s6, s7, s8, s9));
    }
    
    @Override
    public synchronized void send(final Serializable s) throws Exception {
        if (this.info == null) {
            this.info = connect(this.context, this.factoryBindingName, this.queueBindingName, this.userName, this.password, false);
        }
        super.send(s, (Session)QueueInfo.access$100(this.info), (MessageProducer)QueueInfo.access$200(this.info));
    }
    
    public void releaseSub() {
        if (this.info != null) {
            this.cleanup(false);
        }
    }
    
    private void cleanup(final boolean b) {
        QueueInfo.access$100(this.info).close();
        QueueInfo.access$300(this.info).close();
        this.info = null;
    }
    
    private static QueueInfo connect(final Context context, final String s, final String s2, final String s3, final String s4, final boolean b) throws Exception {
        final QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory)AbstractJMSManager.lookup(context, s);
        QueueConnection queueConnection;
        if (s3 != null) {
            queueConnection = queueConnectionFactory.createQueueConnection(s3, s4);
        }
        else {
            queueConnection = queueConnectionFactory.createQueueConnection();
        }
        final QueueSession queueSession = queueConnection.createQueueSession(false, 1);
        final QueueSender sender = queueSession.createSender((Queue)AbstractJMSManager.lookup(context, s2));
        queueConnection.start();
        return new QueueInfo(queueConnection, queueSession, sender);
    }
    
    static QueueInfo access$1300(final Context context, final String s, final String s2, final String s3, final String s4, final boolean b) throws Exception {
        return connect(context, s, s2, s3, s4, b);
    }
    
    static Logger access$1400() {
        return JMSQueueManager.LOGGER;
    }
    
    static Logger access$1500() {
        return JMSQueueManager.LOGGER;
    }
    
    static {
        FACTORY = new JMSQueueManagerFactory(null);
    }
    
    private static class JMSQueueManagerFactory implements ManagerFactory
    {
        private JMSQueueManagerFactory() {
        }
        
        public JMSQueueManager createManager(final String s, final FactoryData factoryData) {
            final Context context = AbstractJMSManager.createContext(FactoryData.access$400(factoryData), FactoryData.access$500(factoryData), FactoryData.access$600(factoryData), FactoryData.access$700(factoryData), FactoryData.access$800(factoryData));
            return new JMSQueueManager(s, context, FactoryData.access$900(factoryData), FactoryData.access$1000(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), JMSQueueManager.access$1300(context, FactoryData.access$900(factoryData), FactoryData.access$1000(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), true));
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        JMSQueueManagerFactory(final JMSQueueManager$1 object) {
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
        private final String queueBindingName;
        private final String userName;
        private final String password;
        
        public FactoryData(final String factoryName, final String providerURL, final String urlPkgPrefixes, final String securityPrincipalName, final String securityCredentials, final String factoryBindingName, final String queueBindingName, final String userName, final String password) {
            this.factoryName = factoryName;
            this.providerURL = providerURL;
            this.urlPkgPrefixes = urlPkgPrefixes;
            this.securityPrincipalName = securityPrincipalName;
            this.securityCredentials = securityCredentials;
            this.factoryBindingName = factoryBindingName;
            this.queueBindingName = queueBindingName;
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
            return factoryData.queueBindingName;
        }
        
        static String access$1100(final FactoryData factoryData) {
            return factoryData.userName;
        }
        
        static String access$1200(final FactoryData factoryData) {
            return factoryData.password;
        }
    }
    
    private static class QueueInfo
    {
        private final QueueConnection conn;
        private final QueueSession session;
        private final QueueSender sender;
        
        public QueueInfo(final QueueConnection conn, final QueueSession session, final QueueSender sender) {
            this.conn = conn;
            this.session = session;
            this.sender = sender;
        }
        
        static QueueSession access$100(final QueueInfo queueInfo) {
            return queueInfo.session;
        }
        
        static QueueSender access$200(final QueueInfo queueInfo) {
            return queueInfo.sender;
        }
        
        static QueueConnection access$300(final QueueInfo queueInfo) {
            return queueInfo.conn;
        }
    }
}
