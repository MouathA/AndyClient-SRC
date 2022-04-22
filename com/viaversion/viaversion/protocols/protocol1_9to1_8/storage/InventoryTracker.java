package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class InventoryTracker implements StorableObject
{
    private String inventory;
    private final Map windowItemCache;
    private int itemIdInCursor;
    private boolean dragging;
    
    public InventoryTracker() {
        this.windowItemCache = new HashMap();
        this.itemIdInCursor = 0;
        this.dragging = false;
    }
    
    public String getInventory() {
        return this.inventory;
    }
    
    public void setInventory(final String inventory) {
        this.inventory = inventory;
    }
    
    public void resetInventory(final short n) {
        if (this.inventory == null) {
            this.itemIdInCursor = 0;
            this.dragging = false;
            if (n != 0) {
                this.windowItemCache.remove(n);
            }
        }
    }
    
    public int getItemId(final short n, final short n2) {
        final Map<Object, Integer> map = this.windowItemCache.get(n);
        if (map == null) {
            return 0;
        }
        return map.getOrDefault(n2, 0);
    }
    
    public void setItemId(final short n, final short n2, final int itemIdInCursor) {
        if (n == -1 && n2 == -1) {
            this.itemIdInCursor = itemIdInCursor;
        }
        else {
            this.windowItemCache.computeIfAbsent(n, InventoryTracker::lambda$setItemId$0).put(n2, itemIdInCursor);
        }
    }
    
    public void handleWindowClick(final UserConnection userConnection, final short n, final byte b, final short n2, final byte b2) {
        final EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
        if (n2 == -1) {
            return;
        }
        if (n2 == 45) {
            entityTracker1_9.setSecondHand(null);
            return;
        }
        final boolean b3 = (n2 >= 5 && n2 <= 8) || n2 == 0;
        Label_0361: {
            switch (b) {
                case 0: {
                    if (this.itemIdInCursor == 0) {
                        this.itemIdInCursor = this.getItemId(n, n2);
                        this.setItemId(n, n2, 0);
                        break;
                    }
                    if (n2 == -999) {
                        this.itemIdInCursor = 0;
                        break;
                    }
                    if (!b3) {
                        final int itemId = this.getItemId(n, n2);
                        this.setItemId(n, n2, this.itemIdInCursor);
                        this.itemIdInCursor = itemId;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (!b3) {
                        final short n3 = (short)(b2 + 36);
                        final int itemId2 = this.getItemId(n, n2);
                        final int itemId3 = this.getItemId(n, n3);
                        this.setItemId(n, n3, itemId2);
                        this.setItemId(n, n2, itemId3);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.getItemId(n, n2) != 0) {
                        this.setItemId(n, n2, 0);
                        break;
                    }
                    break;
                }
                case 5: {
                    switch (b2) {
                        case 0:
                        case 4: {
                            this.dragging = true;
                            break Label_0361;
                        }
                        case 1:
                        case 5: {
                            if (this.dragging && this.itemIdInCursor != 0 && !b3) {
                                final int itemId4 = this.getItemId(n, n2);
                                this.setItemId(n, n2, this.itemIdInCursor);
                                this.itemIdInCursor = itemId4;
                                break Label_0361;
                            }
                            break Label_0361;
                        }
                        case 2:
                        case 6: {
                            this.dragging = false;
                            break Label_0361;
                        }
                    }
                    break;
                }
            }
        }
        entityTracker1_9.syncShieldWithSword();
    }
    
    private static Map lambda$setItemId$0(final Short n) {
        return new HashMap();
    }
}
