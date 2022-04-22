package Mood.Host;

import java.util.*;

public class DNSResult
{
    private final int ttl;
    private final Map result;
    
    public DNSResult(final int ttl, final Map result) {
        this.ttl = ttl;
        this.result = result;
    }
    
    public Map getResult() {
        return this.result;
    }
    
    public int getTTL() {
        return this.ttl;
    }
    
    public boolean isSuccessful() {
        return this.ttl >= 0;
    }
}
