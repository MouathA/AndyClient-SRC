package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import com.viaversion.viaversion.api.connection.*;

public class DifficultyStorage extends StoredObject
{
    private byte difficulty;
    
    public DifficultyStorage(final UserConnection userConnection) {
        super(userConnection);
    }
    
    public byte getDifficulty() {
        return this.difficulty;
    }
    
    public void setDifficulty(final byte difficulty) {
        this.difficulty = difficulty;
    }
}
