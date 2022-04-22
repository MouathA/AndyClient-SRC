package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.*;

public class BlockPlaceDestroyTracker extends StoredObject
{
    private long blockPlaced;
    private long lastMining;
    private boolean mining;
    
    public BlockPlaceDestroyTracker(final UserConnection userConnection) {
        super(userConnection);
    }
    
    public long getBlockPlaced() {
        return this.blockPlaced;
    }
    
    public void place() {
        this.blockPlaced = System.currentTimeMillis();
    }
    
    public boolean isMining() {
        final long n = System.currentTimeMillis() - this.lastMining;
        return (this.mining && n < 75L) || n < 75L;
    }
    
    public void setMining(final boolean b) {
        this.mining = (b && ((EntityTracker)this.getUser().get(EntityTracker.class)).getPlayerGamemode() != 1);
        this.lastMining = System.currentTimeMillis();
    }
    
    public long getLastMining() {
        return this.lastMining;
    }
    
    public void updateMining() {
        if (this.isMining()) {
            this.lastMining = System.currentTimeMillis();
        }
    }
    
    public void setLastMining(final long lastMining) {
        this.lastMining = lastMining;
    }
}
