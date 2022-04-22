package org.apache.logging.log4j.core.appender;

public class TLSSyslogFrame
{
    public static final char SPACE = ' ';
    private String message;
    private int messageLengthInBytes;
    
    public TLSSyslogFrame(final String message) {
        this.setMessage(message);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
        this.setLengthInBytes();
    }
    
    private void setLengthInBytes() {
        this.messageLengthInBytes = this.message.length();
    }
    
    public byte[] getBytes() {
        return this.toString().getBytes();
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.messageLengthInBytes) + ' ' + this.message;
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    public boolean equals(final TLSSyslogFrame tlsSyslogFrame) {
        return this.isLengthEquals(tlsSyslogFrame) && this.isMessageEquals(tlsSyslogFrame);
    }
    
    private boolean isLengthEquals(final TLSSyslogFrame tlsSyslogFrame) {
        return this.messageLengthInBytes == tlsSyslogFrame.messageLengthInBytes;
    }
    
    private boolean isMessageEquals(final TLSSyslogFrame tlsSyslogFrame) {
        return this.message.equals(tlsSyslogFrame.message);
    }
}
