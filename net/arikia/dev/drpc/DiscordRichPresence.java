package net.arikia.dev.drpc;

import com.sun.jna.*;
import java.util.*;

public class DiscordRichPresence extends Structure
{
    public String state;
    public String details;
    public long startTimestamp;
    public long endTimestamp;
    public String largeImageKey;
    public String largeImageText;
    public String smallImageKey;
    public String smallImageText;
    public String partyId;
    public int partySize;
    public int partyMax;
    @Deprecated
    public String matchSecret;
    public String spectateSecret;
    public String joinSecret;
    @Deprecated
    public int instance;
    
    public List getFieldOrder() {
        return Arrays.asList("state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance");
    }
    
    public static class Builder
    {
        private DiscordRichPresence p;
        
        public Builder(final String state) {
            this.p = new DiscordRichPresence();
            this.p.state = state;
        }
        
        public Builder setDetails(final String details) {
            this.p.details = details;
            return this;
        }
        
        public Builder setStartTimestamps(final long startTimestamp) {
            this.p.startTimestamp = startTimestamp;
            return this;
        }
        
        public Builder setEndTimestamp(final long endTimestamp) {
            this.p.endTimestamp = endTimestamp;
            return this;
        }
        
        public Builder setBigImage(final String largeImageKey, final String largeImageText) {
            if (largeImageText != null && !largeImageText.equalsIgnoreCase("") && largeImageKey == null) {
                throw new IllegalArgumentException("Image key must not be null when assigning a hover text.");
            }
            this.p.largeImageKey = largeImageKey;
            this.p.largeImageText = largeImageText;
            return this;
        }
        
        public Builder setSmallImage(final String smallImageKey, final String smallImageText) {
            if (smallImageText != null && !smallImageText.equalsIgnoreCase("") && smallImageKey == null) {
                throw new IllegalArgumentException("Image key must not be null when assigning a hover text.");
            }
            this.p.smallImageKey = smallImageKey;
            this.p.smallImageText = smallImageText;
            return this;
        }
        
        public Builder setParty(final String partyId, final int partySize, final int partyMax) {
            this.p.partyId = partyId;
            this.p.partySize = partySize;
            this.p.partyMax = partyMax;
            return this;
        }
        
        @Deprecated
        public Builder setSecrets(final String matchSecret, final String joinSecret, final String spectateSecret) {
            this.p.matchSecret = matchSecret;
            this.p.joinSecret = joinSecret;
            this.p.spectateSecret = spectateSecret;
            return this;
        }
        
        public Builder setSecrets(final String joinSecret, final String spectateSecret) {
            this.p.joinSecret = joinSecret;
            this.p.spectateSecret = spectateSecret;
            return this;
        }
        
        @Deprecated
        public Builder setInstance(final boolean instance) {
            this.p.instance = (instance ? 1 : 0);
            return this;
        }
        
        public DiscordRichPresence build() {
            return this.p;
        }
    }
}
