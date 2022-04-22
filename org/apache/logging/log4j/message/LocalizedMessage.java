package org.apache.logging.log4j.message;

import java.util.*;
import org.apache.logging.log4j.status.*;
import java.io.*;

public class LocalizedMessage implements Message, LoggerNameAwareMessage
{
    private static final long serialVersionUID = 3893703791567290742L;
    private String bundleId;
    private transient ResourceBundle bundle;
    private Locale locale;
    private transient StatusLogger logger;
    private String loggerName;
    private String messagePattern;
    private String[] stringArgs;
    private transient Object[] argArray;
    private String formattedMessage;
    private transient Throwable throwable;
    
    public LocalizedMessage(final String s, final Object[] array) {
        this((ResourceBundle)null, null, s, array);
    }
    
    public LocalizedMessage(final String s, final String s2, final Object[] array) {
        this(s, null, s2, array);
    }
    
    public LocalizedMessage(final ResourceBundle resourceBundle, final String s, final Object[] array) {
        this(resourceBundle, null, s, array);
    }
    
    public LocalizedMessage(final String s, final Locale locale, final String messagePattern, final Object[] argArray) {
        this.logger = StatusLogger.getLogger();
        this.messagePattern = messagePattern;
        this.argArray = argArray;
        this.throwable = null;
        this.setup(s, null, locale);
    }
    
    public LocalizedMessage(final ResourceBundle resourceBundle, final Locale locale, final String messagePattern, final Object[] argArray) {
        this.logger = StatusLogger.getLogger();
        this.messagePattern = messagePattern;
        this.argArray = argArray;
        this.throwable = null;
        this.setup(null, resourceBundle, locale);
    }
    
    public LocalizedMessage(final Locale locale, final String s, final Object[] array) {
        this((ResourceBundle)null, locale, s, array);
    }
    
    public LocalizedMessage(final String s, final Object o) {
        this((ResourceBundle)null, null, s, new Object[] { o });
    }
    
    public LocalizedMessage(final String s, final String s2, final Object o) {
        this(s, null, s2, new Object[] { o });
    }
    
    public LocalizedMessage(final ResourceBundle resourceBundle, final String s, final Object o) {
        this(resourceBundle, null, s, new Object[] { o });
    }
    
    public LocalizedMessage(final String s, final Locale locale, final String s2, final Object o) {
        this(s, locale, s2, new Object[] { o });
    }
    
    public LocalizedMessage(final ResourceBundle resourceBundle, final Locale locale, final String s, final Object o) {
        this(resourceBundle, locale, s, new Object[] { o });
    }
    
    public LocalizedMessage(final Locale locale, final String s, final Object o) {
        this((ResourceBundle)null, locale, s, new Object[] { o });
    }
    
    public LocalizedMessage(final String s, final Object o, final Object o2) {
        this((ResourceBundle)null, null, s, new Object[] { o, o2 });
    }
    
    public LocalizedMessage(final String s, final String s2, final Object o, final Object o2) {
        this(s, null, s2, new Object[] { o, o2 });
    }
    
    public LocalizedMessage(final ResourceBundle resourceBundle, final String s, final Object o, final Object o2) {
        this(resourceBundle, null, s, new Object[] { o, o2 });
    }
    
    public LocalizedMessage(final String s, final Locale locale, final String s2, final Object o, final Object o2) {
        this(s, locale, s2, new Object[] { o, o2 });
    }
    
    public LocalizedMessage(final ResourceBundle resourceBundle, final Locale locale, final String s, final Object o, final Object o2) {
        this(resourceBundle, locale, s, new Object[] { o, o2 });
    }
    
    public LocalizedMessage(final Locale locale, final String s, final Object o, final Object o2) {
        this((ResourceBundle)null, locale, s, new Object[] { o, o2 });
    }
    
    @Override
    public void setLoggerName(final String loggerName) {
        this.loggerName = loggerName;
    }
    
    @Override
    public String getLoggerName() {
        return this.loggerName;
    }
    
    private void setup(final String bundleId, final ResourceBundle bundle, final Locale locale) {
        this.bundleId = bundleId;
        this.bundle = bundle;
        this.locale = locale;
    }
    
    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage != null) {
            return this.formattedMessage;
        }
        ResourceBundle resourceBundle = this.bundle;
        if (resourceBundle == null) {
            if (this.bundleId != null) {
                resourceBundle = this.getBundle(this.bundleId, this.locale, false);
            }
            else {
                resourceBundle = this.getBundle(this.loggerName, this.locale, true);
            }
        }
        final String format = this.getFormat();
        final FormattedMessage formattedMessage = new FormattedMessage((resourceBundle == null || !resourceBundle.containsKey(format)) ? format : resourceBundle.getString(format), (this.argArray == null) ? this.stringArgs : this.argArray);
        this.formattedMessage = formattedMessage.getFormattedMessage();
        this.throwable = formattedMessage.getThrowable();
        return this.formattedMessage;
    }
    
    @Override
    public String getFormat() {
        return this.messagePattern;
    }
    
    @Override
    public Object[] getParameters() {
        if (this.argArray != null) {
            return this.argArray;
        }
        return this.stringArgs;
    }
    
    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    protected ResourceBundle getBundle(final String s, final Locale locale, final boolean b) {
        if (s == null) {
            return null;
        }
        ResourceBundle resourceBundle;
        if (locale != null) {
            resourceBundle = ResourceBundle.getBundle(s, locale);
        }
        else {
            resourceBundle = ResourceBundle.getBundle(s);
        }
        String substring = s;
        int lastIndex;
        while (resourceBundle == null && (lastIndex = substring.lastIndexOf(46)) > 0) {
            substring = substring.substring(0, lastIndex);
            if (locale != null) {
                resourceBundle = ResourceBundle.getBundle(substring, locale);
            }
            else {
                resourceBundle = ResourceBundle.getBundle(substring);
            }
        }
        return resourceBundle;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        this.getFormattedMessage();
        objectOutputStream.writeUTF(this.formattedMessage);
        objectOutputStream.writeUTF(this.messagePattern);
        objectOutputStream.writeUTF(this.bundleId);
        objectOutputStream.writeInt(this.argArray.length);
        this.stringArgs = new String[this.argArray.length];
        final Object[] argArray = this.argArray;
        while (0 < argArray.length) {
            this.stringArgs[0] = argArray[0].toString();
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.formattedMessage = objectInputStream.readUTF();
        this.messagePattern = objectInputStream.readUTF();
        this.bundleId = objectInputStream.readUTF();
        final int int1 = objectInputStream.readInt();
        this.stringArgs = new String[int1];
        while (0 < int1) {
            this.stringArgs[0] = objectInputStream.readUTF();
            int n = 0;
            ++n;
        }
        this.logger = StatusLogger.getLogger();
        this.bundle = null;
        this.argArray = null;
    }
}
