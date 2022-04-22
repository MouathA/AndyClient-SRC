package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage;

import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.connection.*;

public class ClientWorld extends StoredObject
{
    private Environment environment;
    
    public ClientWorld(final UserConnection userConnection) {
        super(userConnection);
    }
    
    public Environment getEnvironment() {
        return this.environment;
    }
    
    public void setEnvironment(final int n) {
        this.environment = Environment.getEnvironmentById(n);
    }
}
