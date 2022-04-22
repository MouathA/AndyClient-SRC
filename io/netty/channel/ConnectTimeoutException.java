package io.netty.channel;

import java.net.*;

public class ConnectTimeoutException extends ConnectException
{
    private static final long serialVersionUID = 2317065249988317463L;
    
    public ConnectTimeoutException(final String s) {
        super(s);
    }
    
    public ConnectTimeoutException() {
    }
}
