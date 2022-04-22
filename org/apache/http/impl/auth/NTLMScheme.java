package org.apache.http.impl.auth;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.message.*;

@NotThreadSafe
public class NTLMScheme extends AuthSchemeBase
{
    private final NTLMEngine engine;
    private State state;
    private String challenge;
    
    public NTLMScheme(final NTLMEngine engine) {
        Args.notNull(engine, "NTLM engine");
        this.engine = engine;
        this.state = State.UNINITIATED;
        this.challenge = null;
    }
    
    public NTLMScheme() {
        this(new NTLMEngineImpl());
    }
    
    public String getSchemeName() {
        return "ntlm";
    }
    
    public String getParameter(final String s) {
        return null;
    }
    
    public String getRealm() {
        return null;
    }
    
    public boolean isConnectionBased() {
        return true;
    }
    
    @Override
    protected void parseChallenge(final CharArrayBuffer charArrayBuffer, final int n, final int n2) throws MalformedChallengeException {
        this.challenge = charArrayBuffer.substringTrimmed(n, n2);
        if (this.challenge.length() == 0) {
            if (this.state == State.UNINITIATED) {
                this.state = State.CHALLENGE_RECEIVED;
            }
            else {
                this.state = State.FAILED;
            }
        }
        else {
            if (this.state.compareTo(State.MSG_TYPE1_GENERATED) < 0) {
                this.state = State.FAILED;
                throw new MalformedChallengeException("Out of sequence NTLM response message");
            }
            if (this.state == State.MSG_TYPE1_GENERATED) {
                this.state = State.MSG_TYPE2_RECEVIED;
            }
        }
    }
    
    public Header authenticate(final Credentials credentials, final HttpRequest httpRequest) throws AuthenticationException {
        final NTCredentials ntCredentials = (NTCredentials)credentials;
        if (this.state == State.FAILED) {
            throw new AuthenticationException("NTLM authentication failed");
        }
        String s;
        if (this.state == State.CHALLENGE_RECEIVED) {
            s = this.engine.generateType1Msg(ntCredentials.getDomain(), ntCredentials.getWorkstation());
            this.state = State.MSG_TYPE1_GENERATED;
        }
        else {
            if (this.state != State.MSG_TYPE2_RECEVIED) {
                throw new AuthenticationException("Unexpected state: " + this.state);
            }
            s = this.engine.generateType3Msg(ntCredentials.getUserName(), ntCredentials.getPassword(), ntCredentials.getDomain(), ntCredentials.getWorkstation(), this.challenge);
            this.state = State.MSG_TYPE3_GENERATED;
        }
        final CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
        if (this.isProxy()) {
            charArrayBuffer.append("Proxy-Authorization");
        }
        else {
            charArrayBuffer.append("Authorization");
        }
        charArrayBuffer.append(": NTLM ");
        charArrayBuffer.append(s);
        return new BufferedHeader(charArrayBuffer);
    }
    
    public boolean isComplete() {
        return this.state == State.MSG_TYPE3_GENERATED || this.state == State.FAILED;
    }
    
    enum State
    {
        UNINITIATED("UNINITIATED", 0), 
        CHALLENGE_RECEIVED("CHALLENGE_RECEIVED", 1), 
        MSG_TYPE1_GENERATED("MSG_TYPE1_GENERATED", 2), 
        MSG_TYPE2_RECEVIED("MSG_TYPE2_RECEVIED", 3), 
        MSG_TYPE3_GENERATED("MSG_TYPE3_GENERATED", 4), 
        FAILED("FAILED", 5);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.UNINITIATED, State.CHALLENGE_RECEIVED, State.MSG_TYPE1_GENERATED, State.MSG_TYPE2_RECEVIED, State.MSG_TYPE3_GENERATED, State.FAILED };
        }
    }
}
