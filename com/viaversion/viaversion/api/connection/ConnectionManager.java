package com.viaversion.viaversion.api.connection;

import java.util.*;

public interface ConnectionManager
{
    boolean isClientConnected(final UUID p0);
    
    default boolean isFrontEnd(final UserConnection userConnection) {
        return !userConnection.isClientSide();
    }
    
    UserConnection getConnectedClient(final UUID p0);
    
    UUID getConnectedClientId(final UserConnection p0);
    
    Set getConnections();
    
    Map getConnectedClients();
    
    void onLoginSuccess(final UserConnection p0);
    
    void onDisconnect(final UserConnection p0);
}
