package Mood.Helpers;

import Mood.*;

public class ClientSettings
{
    private boolean directConnectAutoPing;
    private boolean disconnectAutoReconnect;
    
    public ClientSettings() {
        this.directConnectAutoPing = true;
        this.disconnectAutoReconnect = false;
    }
    
    public boolean isDirectConnectAutoPing() {
        return this.directConnectAutoPing;
    }
    
    public boolean isDisconnectAutoReconnect() {
        return this.disconnectAutoReconnect;
    }
    
    public void setDirectConnectAutoPing(final boolean directConnectAutoPing) {
        this.directConnectAutoPing = directConnectAutoPing;
        this.onSetValue();
    }
    
    public void setDisconnectAutoReconnect(final boolean disconnectAutoReconnect) {
        this.disconnectAutoReconnect = disconnectAutoReconnect;
        this.onSetValue();
    }
    
    private final void onSetValue() {
        Client.getInstance().getConfigRegistry().save();
    }
}
