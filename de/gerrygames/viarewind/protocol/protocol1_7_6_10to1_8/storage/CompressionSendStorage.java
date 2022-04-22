package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.*;

public class CompressionSendStorage extends StoredObject
{
    private boolean removeCompression;
    
    public CompressionSendStorage(final UserConnection userConnection) {
        super(userConnection);
        this.removeCompression = false;
    }
    
    public boolean isRemoveCompression() {
        return this.removeCompression;
    }
    
    public void setRemoveCompression(final boolean removeCompression) {
        this.removeCompression = removeCompression;
    }
}
