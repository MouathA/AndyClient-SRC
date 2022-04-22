package com.viaversion.viaversion;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.*;

public abstract class ViaListener
{
    private final Class requiredPipeline;
    private boolean registered;
    
    protected ViaListener(final Class requiredPipeline) {
        this.requiredPipeline = requiredPipeline;
    }
    
    protected UserConnection getUserConnection(final UUID uuid) {
        return Via.getManager().getConnectionManager().getConnectedClient(uuid);
    }
    
    protected boolean isOnPipe(final UUID uuid) {
        final UserConnection userConnection = this.getUserConnection(uuid);
        return userConnection != null && (this.requiredPipeline == null || userConnection.getProtocolInfo().getPipeline().contains(this.requiredPipeline));
    }
    
    public abstract void register();
    
    protected Class getRequiredPipeline() {
        return this.requiredPipeline;
    }
    
    protected boolean isRegistered() {
        return this.registered;
    }
    
    protected void setRegistered(final boolean registered) {
        this.registered = registered;
    }
}
