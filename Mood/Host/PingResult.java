package Mood.Host;

import java.util.*;

public class PingResult
{
    private final List pingEntries;
    
    public PingResult(final List pingEntries) {
        this.pingEntries = pingEntries;
    }
    
    public List getPingEntries() {
        return this.pingEntries;
    }
    
    public boolean isSuccessful() {
        return this.pingEntries != null && !this.pingEntries.isEmpty();
    }
    
    public static class PingEntry
    {
        private final String status;
        private final double ping;
        private final String address;
        
        public PingEntry(final String status, final double ping, final String address) {
            this.status = status;
            this.ping = ping;
            this.address = address;
        }
        
        public String getAddress() {
            return this.address;
        }
        
        public double getPing() {
            return this.ping;
        }
        
        public String getStatus() {
            return this.status;
        }
        
        public boolean isSuccessful() {
            return this.status != null && this.status.equalsIgnoreCase("OK") && this.ping >= 0.0;
        }
    }
}
