package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.configuration.*;

public class PacketTracker
{
    private final UserConnection connection;
    private long sentPackets;
    private long receivedPackets;
    private long startTime;
    private long intervalPackets;
    private long packetsPerSecond;
    private int secondsObserved;
    private int warnings;
    
    public PacketTracker(final UserConnection connection) {
        this.packetsPerSecond = -1L;
        this.connection = connection;
    }
    
    public void incrementSent() {
        ++this.sentPackets;
    }
    
    public boolean incrementReceived() {
        if (System.currentTimeMillis() - this.startTime >= 1000L) {
            this.packetsPerSecond = this.intervalPackets;
            this.startTime = System.currentTimeMillis();
            this.intervalPackets = 1L;
            return true;
        }
        ++this.intervalPackets;
        ++this.receivedPackets;
        return false;
    }
    
    public boolean exceedsMaxPPS() {
        if (this.connection.isClientSide()) {
            return false;
        }
        final ViaVersionConfig config = Via.getConfig();
        if (config.getMaxPPS() > 0 && this.packetsPerSecond >= config.getMaxPPS()) {
            this.connection.disconnect(config.getMaxPPSKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
            return true;
        }
        if (config.getMaxWarnings() > 0 && config.getTrackingPeriod() > 0) {
            if (this.secondsObserved > config.getTrackingPeriod()) {
                this.warnings = 0;
                this.secondsObserved = 1;
            }
            else {
                ++this.secondsObserved;
                if (this.packetsPerSecond >= config.getWarningPPS()) {
                    ++this.warnings;
                }
                if (this.warnings >= config.getMaxWarnings()) {
                    this.connection.disconnect(config.getMaxWarningsKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
                    return true;
                }
            }
        }
        return false;
    }
    
    public long getSentPackets() {
        return this.sentPackets;
    }
    
    public void setSentPackets(final long sentPackets) {
        this.sentPackets = sentPackets;
    }
    
    public long getReceivedPackets() {
        return this.receivedPackets;
    }
    
    public void setReceivedPackets(final long receivedPackets) {
        this.receivedPackets = receivedPackets;
    }
    
    public long getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final long startTime) {
        this.startTime = startTime;
    }
    
    public long getIntervalPackets() {
        return this.intervalPackets;
    }
    
    public void setIntervalPackets(final long intervalPackets) {
        this.intervalPackets = intervalPackets;
    }
    
    public long getPacketsPerSecond() {
        return this.packetsPerSecond;
    }
    
    public void setPacketsPerSecond(final long packetsPerSecond) {
        this.packetsPerSecond = packetsPerSecond;
    }
    
    public int getSecondsObserved() {
        return this.secondsObserved;
    }
    
    public void setSecondsObserved(final int secondsObserved) {
        this.secondsObserved = secondsObserved;
    }
    
    public int getWarnings() {
        return this.warnings;
    }
    
    public void setWarnings(final int warnings) {
        this.warnings = warnings;
    }
}
