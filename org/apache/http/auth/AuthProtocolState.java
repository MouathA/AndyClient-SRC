package org.apache.http.auth;

public enum AuthProtocolState
{
    UNCHALLENGED("UNCHALLENGED", 0), 
    CHALLENGED("CHALLENGED", 1), 
    HANDSHAKE("HANDSHAKE", 2), 
    FAILURE("FAILURE", 3), 
    SUCCESS("SUCCESS", 4);
    
    private static final AuthProtocolState[] $VALUES;
    
    private AuthProtocolState(final String s, final int n) {
    }
    
    static {
        $VALUES = new AuthProtocolState[] { AuthProtocolState.UNCHALLENGED, AuthProtocolState.CHALLENGED, AuthProtocolState.HANDSHAKE, AuthProtocolState.FAILURE, AuthProtocolState.SUCCESS };
    }
}
