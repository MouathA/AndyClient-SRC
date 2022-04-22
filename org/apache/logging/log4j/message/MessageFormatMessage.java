package org.apache.logging.log4j.message;

import org.apache.logging.log4j.*;
import java.text.*;
import java.util.*;
import java.io.*;
import org.apache.logging.log4j.status.*;

public class MessageFormatMessage implements Message
{
    private static final Logger LOGGER;
    private static final long serialVersionUID = -665975803997290697L;
    private static final int HASHVAL = 31;
    private String messagePattern;
    private transient Object[] argArray;
    private String[] stringArgs;
    private transient String formattedMessage;
    private transient Throwable throwable;
    
    public MessageFormatMessage(final String messagePattern, final Object... argArray) {
        this.messagePattern = messagePattern;
        this.argArray = argArray;
        if (argArray != null && argArray.length > 0 && argArray[argArray.length - 1] instanceof Throwable) {
            this.throwable = (Throwable)argArray[argArray.length - 1];
        }
    }
    
    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            this.formattedMessage = this.formatMessage(this.messagePattern, this.argArray);
        }
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
    
    protected String formatMessage(final String s, final Object... array) {
        return MessageFormat.format(s, array);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MessageFormatMessage messageFormatMessage = (MessageFormatMessage)o;
        if (this.messagePattern != null) {
            if (this.messagePattern.equals(messageFormatMessage.messagePattern)) {
                return Arrays.equals(this.stringArgs, messageFormatMessage.stringArgs);
            }
        }
        else if (messageFormatMessage.messagePattern == null) {
            return Arrays.equals(this.stringArgs, messageFormatMessage.stringArgs);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.messagePattern != null) ? this.messagePattern.hashCode() : 0) + ((this.stringArgs != null) ? Arrays.hashCode(this.stringArgs) : 0);
    }
    
    @Override
    public String toString() {
        return "StringFormatMessage[messagePattern=" + this.messagePattern + ", args=" + Arrays.toString(this.argArray) + "]";
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        this.getFormattedMessage();
        objectOutputStream.writeUTF(this.formattedMessage);
        objectOutputStream.writeUTF(this.messagePattern);
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
        final int int1 = objectInputStream.readInt();
        this.stringArgs = new String[int1];
        while (0 < int1) {
            this.stringArgs[0] = objectInputStream.readUTF();
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
