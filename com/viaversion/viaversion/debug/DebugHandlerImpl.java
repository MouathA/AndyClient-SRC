package com.viaversion.viaversion.debug;

import com.viaversion.viaversion.api.debug.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public final class DebugHandlerImpl implements DebugHandler
{
    private final Set packetTypesToLog;
    private boolean logPostPacketTransform;
    private boolean enabled;
    
    public DebugHandlerImpl() {
        this.packetTypesToLog = new HashSet();
        this.logPostPacketTransform = true;
    }
    
    @Override
    public boolean enabled() {
        return this.enabled;
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public void addPacketTypeNameToLog(final String s) {
        this.packetTypesToLog.add(s);
    }
    
    @Override
    public boolean removePacketTypeNameToLog(final String s) {
        return this.packetTypesToLog.remove(s);
    }
    
    @Override
    public void clearPacketTypesToLog() {
        this.packetTypesToLog.clear();
    }
    
    @Override
    public boolean logPostPacketTransform() {
        return this.logPostPacketTransform;
    }
    
    @Override
    public void setLogPostPacketTransform(final boolean logPostPacketTransform) {
        this.logPostPacketTransform = logPostPacketTransform;
    }
    
    @Override
    public boolean shouldLog(final PacketWrapper packetWrapper) {
        return this.packetTypesToLog.isEmpty() || (packetWrapper.getPacketType() != null && this.packetTypesToLog.contains(packetWrapper.getPacketType().getName()));
    }
}
