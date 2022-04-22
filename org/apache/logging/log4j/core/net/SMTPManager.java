package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import java.io.*;
import javax.mail.util.*;
import javax.activation.*;
import javax.mail.internet.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.util.*;
import org.apache.logging.log4j.core.helpers.*;
import javax.mail.*;
import java.util.*;

public class SMTPManager extends AbstractManager
{
    private static final SMTPManagerFactory FACTORY;
    private final Session session;
    private final CyclicBuffer buffer;
    private MimeMessage message;
    private final FactoryData data;
    
    protected SMTPManager(final String s, final Session session, final MimeMessage message, final FactoryData data) {
        super(s);
        this.session = session;
        this.message = message;
        this.data = data;
        this.buffer = new CyclicBuffer(LogEvent.class, FactoryData.access$100(data));
    }
    
    public void add(final LogEvent logEvent) {
        this.buffer.add(logEvent);
    }
    
    public static SMTPManager getSMTPManager(final String s, final String s2, final String s3, final String s4, final String s5, final String s6, String s7, final String s8, final int n, final String s9, final String s10, final boolean b, final String s11, final int n2) {
        if (Strings.isEmpty(s7)) {
            s7 = "smtp";
        }
        final StringBuilder sb = new StringBuilder();
        if (s != null) {
            sb.append(s);
        }
        sb.append(":");
        if (s2 != null) {
            sb.append(s2);
        }
        sb.append(":");
        if (s3 != null) {
            sb.append(s3);
        }
        sb.append(":");
        if (s4 != null) {
            sb.append(s4);
        }
        sb.append(":");
        if (s5 != null) {
            sb.append(s5);
        }
        sb.append(":");
        if (s6 != null) {
            sb.append(s6);
        }
        sb.append(":");
        sb.append(s7).append(":").append(s8).append(":").append("port").append(":");
        if (s9 != null) {
            sb.append(s9);
        }
        sb.append(":");
        if (s10 != null) {
            sb.append(s10);
        }
        sb.append(b ? ":debug:" : "::");
        sb.append(s11);
        return (SMTPManager)AbstractManager.getManager("SMTP:" + NameUtil.md5(sb.toString()), SMTPManager.FACTORY, new FactoryData(s, s2, s3, s4, s5, s6, s7, s8, n, s9, s10, b, n2));
    }
    
    public void sendEvents(final Layout layout, final LogEvent logEvent) {
        if (this.message == null) {
            this.connect();
        }
        final byte[] formatContentToBytes = this.formatContentToBytes((LogEvent[])this.buffer.removeAll(), logEvent, layout);
        final String contentType = layout.getContentType();
        final String encoding = this.getEncoding(formatContentToBytes, contentType);
        this.sendMultipartMessage(this.message, this.getMimeMultipart(this.encodeContentToBytes(formatContentToBytes, encoding), this.getHeaders(contentType, encoding)));
    }
    
    protected byte[] formatContentToBytes(final LogEvent[] array, final LogEvent logEvent, final Layout layout) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.writeContent(array, logEvent, layout, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    private void writeContent(final LogEvent[] array, final LogEvent logEvent, final Layout layout, final ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        this.writeHeader(layout, byteArrayOutputStream);
        this.writeBuffer(array, logEvent, layout, byteArrayOutputStream);
        this.writeFooter(layout, byteArrayOutputStream);
    }
    
    protected void writeHeader(final Layout layout, final OutputStream outputStream) throws IOException {
        final byte[] header = layout.getHeader();
        if (header != null) {
            outputStream.write(header);
        }
    }
    
    protected void writeBuffer(final LogEvent[] array, final LogEvent logEvent, final Layout layout, final OutputStream outputStream) throws IOException {
        while (0 < array.length) {
            outputStream.write(layout.toByteArray(array[0]));
            int n = 0;
            ++n;
        }
        outputStream.write(layout.toByteArray(logEvent));
    }
    
    protected void writeFooter(final Layout layout, final OutputStream outputStream) throws IOException {
        final byte[] footer = layout.getFooter();
        if (footer != null) {
            outputStream.write(footer);
        }
    }
    
    protected String getEncoding(final byte[] array, final String s) {
        return MimeUtility.getEncoding((DataSource)new ByteArrayDataSource(array, s));
    }
    
    protected byte[] encodeContentToBytes(final byte[] array, final String s) throws MessagingException, IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.encodeContent(array, s, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    protected void encodeContent(final byte[] array, final String s, final ByteArrayOutputStream byteArrayOutputStream) throws MessagingException, IOException {
        final OutputStream encode = MimeUtility.encode((OutputStream)byteArrayOutputStream, s);
        encode.write(array);
        encode.close();
    }
    
    protected InternetHeaders getHeaders(final String s, final String s2) {
        final InternetHeaders internetHeaders = new InternetHeaders();
        internetHeaders.setHeader("Content-Type", s + "; charset=UTF-8");
        internetHeaders.setHeader("Content-Transfer-Encoding", s2);
        return internetHeaders;
    }
    
    protected MimeMultipart getMimeMultipart(final byte[] array, final InternetHeaders internetHeaders) throws MessagingException {
        final MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart((BodyPart)new MimeBodyPart(internetHeaders, array));
        return mimeMultipart;
    }
    
    protected void sendMultipartMessage(final MimeMessage mimeMessage, final MimeMultipart content) throws MessagingException {
        // monitorenter(mimeMessage)
        mimeMessage.setContent((Multipart)content);
        mimeMessage.setSentDate(new Date());
        Transport.send((Message)mimeMessage);
    }
    // monitorexit(mimeMessage)
    
    private synchronized void connect() {
        if (this.message != null) {
            return;
        }
        this.message = new MimeMessageBuilder(this.session).setFrom(FactoryData.access$700(this.data)).setReplyTo(FactoryData.access$600(this.data)).setRecipients(Message.RecipientType.TO, FactoryData.access$500(this.data)).setRecipients(Message.RecipientType.CC, FactoryData.access$400(this.data)).setRecipients(Message.RecipientType.BCC, FactoryData.access$300(this.data)).setSubject(FactoryData.access$200(this.data)).getMimeMessage();
    }
    
    static Logger access$1400() {
        return SMTPManager.LOGGER;
    }
    
    static {
        FACTORY = new SMTPManagerFactory(null);
    }
    
    private static class SMTPManagerFactory implements ManagerFactory
    {
        private SMTPManagerFactory() {
        }
        
        public SMTPManager createManager(final String s, final FactoryData factoryData) {
            final String string = "mail." + FactoryData.access$800(factoryData);
            final Properties systemProperties = PropertiesUtil.getSystemProperties();
            ((Hashtable<String, String>)systemProperties).put("mail.transport.protocol", FactoryData.access$800(factoryData));
            if (systemProperties.getProperty("mail.host") == null) {
                ((Hashtable<String, String>)systemProperties).put("mail.host", NetUtils.getLocalHostname());
            }
            if (null != FactoryData.access$900(factoryData)) {
                ((Hashtable<String, String>)systemProperties).put(string + ".host", FactoryData.access$900(factoryData));
            }
            if (FactoryData.access$1000(factoryData) > 0) {
                ((Hashtable<String, String>)systemProperties).put(string + ".port", String.valueOf(FactoryData.access$1000(factoryData)));
            }
            final Authenticator buildAuthenticator = this.buildAuthenticator(FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData));
            if (null != buildAuthenticator) {
                ((Hashtable<String, String>)systemProperties).put(string + ".auth", "true");
            }
            final Session instance = Session.getInstance(systemProperties, buildAuthenticator);
            instance.setProtocolForAddress("rfc822", FactoryData.access$800(factoryData));
            instance.setDebug(FactoryData.access$1300(factoryData));
            return new SMTPManager(s, instance, new MimeMessageBuilder(instance).setFrom(FactoryData.access$700(factoryData)).setReplyTo(FactoryData.access$600(factoryData)).setRecipients(Message.RecipientType.TO, FactoryData.access$500(factoryData)).setRecipients(Message.RecipientType.CC, FactoryData.access$400(factoryData)).setRecipients(Message.RecipientType.BCC, FactoryData.access$300(factoryData)).setSubject(FactoryData.access$200(factoryData)).getMimeMessage(), factoryData);
        }
        
        private Authenticator buildAuthenticator(final String s, final String s2) {
            if (null != s2 && null != s) {
                return new Authenticator(s, s2) {
                    private final PasswordAuthentication passwordAuthentication = new PasswordAuthentication(this.val$username, this.val$password);
                    final String val$username;
                    final String val$password;
                    final SMTPManagerFactory this$0;
                    
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return this.passwordAuthentication;
                    }
                };
            }
            return null;
        }
        
        @Override
        public Object createManager(final String s, final Object o) {
            return this.createManager(s, (FactoryData)o);
        }
        
        SMTPManagerFactory(final SMTPManager$1 object) {
            this();
        }
    }
    
    private static class FactoryData
    {
        private final String to;
        private final String cc;
        private final String bcc;
        private final String from;
        private final String replyto;
        private final String subject;
        private final String protocol;
        private final String host;
        private final int port;
        private final String username;
        private final String password;
        private final boolean isDebug;
        private final int numElements;
        
        public FactoryData(final String to, final String cc, final String bcc, final String from, final String replyto, final String subject, final String protocol, final String host, final int port, final String username, final String password, final boolean isDebug, final int numElements) {
            this.to = to;
            this.cc = cc;
            this.bcc = bcc;
            this.from = from;
            this.replyto = replyto;
            this.subject = subject;
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
            this.isDebug = isDebug;
            this.numElements = numElements;
        }
        
        static int access$100(final FactoryData factoryData) {
            return factoryData.numElements;
        }
        
        static String access$200(final FactoryData factoryData) {
            return factoryData.subject;
        }
        
        static String access$300(final FactoryData factoryData) {
            return factoryData.bcc;
        }
        
        static String access$400(final FactoryData factoryData) {
            return factoryData.cc;
        }
        
        static String access$500(final FactoryData factoryData) {
            return factoryData.to;
        }
        
        static String access$600(final FactoryData factoryData) {
            return factoryData.replyto;
        }
        
        static String access$700(final FactoryData factoryData) {
            return factoryData.from;
        }
        
        static String access$800(final FactoryData factoryData) {
            return factoryData.protocol;
        }
        
        static String access$900(final FactoryData factoryData) {
            return factoryData.host;
        }
        
        static int access$1000(final FactoryData factoryData) {
            return factoryData.port;
        }
        
        static String access$1100(final FactoryData factoryData) {
            return factoryData.username;
        }
        
        static String access$1200(final FactoryData factoryData) {
            return factoryData.password;
        }
        
        static boolean access$1300(final FactoryData factoryData) {
            return factoryData.isDebug;
        }
    }
}
