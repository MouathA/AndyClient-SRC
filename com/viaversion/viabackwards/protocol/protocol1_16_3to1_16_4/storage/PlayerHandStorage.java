package com.viaversion.viabackwards.protocol.protocol1_16_3to1_16_4.storage;

import com.viaversion.viaversion.api.connection.*;

public class PlayerHandStorage implements StorableObject
{
    private int currentHand;
    
    public int getCurrentHand() {
        return this.currentHand;
    }
    
    public void setCurrentHand(final int currentHand) {
        this.currentHand = currentHand;
    }
}
