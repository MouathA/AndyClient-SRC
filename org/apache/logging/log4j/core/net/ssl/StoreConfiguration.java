package org.apache.logging.log4j.core.net.ssl;

import org.apache.logging.log4j.status.*;

public class StoreConfiguration
{
    protected static final StatusLogger LOGGER;
    private String location;
    private String password;
    
    public StoreConfiguration(final String location, final String password) {
        this.location = location;
        this.password = password;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(final String location) {
        this.location = location;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public char[] getPasswordAsCharArray() {
        if (this.password == null) {
            return null;
        }
        return this.password.toCharArray();
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public boolean equals(final StoreConfiguration storeConfiguration) {
        if (storeConfiguration == null) {
            return false;
        }
        if (this.location != null) {
            this.location.equals(storeConfiguration.location);
        }
        else {
            final boolean b = this.location == storeConfiguration.location;
        }
        if (this.password != null) {
            this.password.equals(storeConfiguration.password);
        }
        else {
            final boolean b2 = this.password == storeConfiguration.password;
        }
        return false && false;
    }
    
    protected void load() throws StoreConfigurationException {
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
