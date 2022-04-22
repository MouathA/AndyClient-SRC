package org.apache.logging.log4j.message;

import java.util.regex.*;
import java.text.*;
import java.util.*;
import java.io.*;

public class FormattedMessage implements Message
{
    private static final long serialVersionUID = -665975803997290697L;
    private static final int HASHVAL = 31;
    private static final String FORMAT_SPECIFIER = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
    private static final Pattern MSG_PATTERN;
    private String messagePattern;
    private transient Object[] argArray;
    private String[] stringArgs;
    private transient String formattedMessage;
    private final Throwable throwable;
    private Message message;
    
    public FormattedMessage(final String messagePattern, final Object[] argArray, final Throwable throwable) {
        this.messagePattern = messagePattern;
        this.argArray = argArray;
        this.throwable = throwable;
    }
    
    public FormattedMessage(final String messagePattern, final Object[] argArray) {
        this.messagePattern = messagePattern;
        this.argArray = argArray;
        this.throwable = null;
    }
    
    public FormattedMessage(final String messagePattern, final Object o) {
        this.messagePattern = messagePattern;
        this.argArray = new Object[] { o };
        this.throwable = null;
    }
    
    public FormattedMessage(final String s, final Object o, final Object o2) {
        this(s, new Object[] { o, o2 });
    }
    
    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            if (this.message == null) {
                this.message = this.getMessage(this.messagePattern, this.argArray, this.throwable);
            }
            this.formattedMessage = this.message.getFormattedMessage();
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
    
    protected Message getMessage(final String s, final Object[] array, final Throwable t) {
        final Format[] formats = new MessageFormat(s).getFormats();
        if (formats != null && formats.length > 0) {
            return new MessageFormatMessage(s, array);
        }
        if (FormattedMessage.MSG_PATTERN.matcher(s).find()) {
            return new StringFormattedMessage(s, array);
        }
        return new ParameterizedMessage(s, array, t);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FormattedMessage formattedMessage = (FormattedMessage)o;
        if (this.messagePattern != null) {
            if (this.messagePattern.equals(formattedMessage.messagePattern)) {
                return Arrays.equals(this.stringArgs, formattedMessage.stringArgs);
            }
        }
        else if (formattedMessage.messagePattern == null) {
            return Arrays.equals(this.stringArgs, formattedMessage.stringArgs);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * ((this.messagePattern != null) ? this.messagePattern.hashCode() : 0) + ((this.stringArgs != null) ? Arrays.hashCode(this.stringArgs) : 0);
    }
    
    @Override
    public String toString() {
        return "FormattedMessage[messagePattern=" + this.messagePattern + ", args=" + Arrays.toString(this.argArray) + "]";
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
        if (this.throwable != null) {
            return this.throwable;
        }
        if (this.message == null) {
            this.message = this.getMessage(this.messagePattern, this.argArray, this.throwable);
        }
        return this.message.getThrowable();
    }
    
    static {
        MSG_PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");
    }
}
