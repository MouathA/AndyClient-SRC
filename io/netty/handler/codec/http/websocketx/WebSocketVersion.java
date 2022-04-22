package io.netty.handler.codec.http.websocketx;

public enum WebSocketVersion
{
    UNKNOWN("UNKNOWN", 0), 
    V00("V00", 1), 
    V07("V07", 2), 
    V08("V08", 3), 
    V13("V13", 4);
    
    private static final WebSocketVersion[] $VALUES;
    
    private WebSocketVersion(final String s, final int n) {
    }
    
    public String toHttpHeaderValue() {
        if (this == WebSocketVersion.V00) {
            return "0";
        }
        if (this == WebSocketVersion.V07) {
            return "7";
        }
        if (this == WebSocketVersion.V08) {
            return "8";
        }
        if (this == WebSocketVersion.V13) {
            return "13";
        }
        throw new IllegalStateException("Unknown web socket version: " + this);
    }
    
    static {
        $VALUES = new WebSocketVersion[] { WebSocketVersion.UNKNOWN, WebSocketVersion.V00, WebSocketVersion.V07, WebSocketVersion.V08, WebSocketVersion.V13 };
    }
}
