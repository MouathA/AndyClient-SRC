package com.viaversion.viaversion.api.connection;

public abstract class StoredObject implements StorableObject
{
    private final UserConnection user;
    
    protected StoredObject(final UserConnection user) {
        this.user = user;
    }
    
    public UserConnection getUser() {
        return this.user;
    }
}
