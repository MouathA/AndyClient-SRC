package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.*;
import com.viaversion.viaversion.api.minecraft.*;

public class BlockData
{
    private final Map connectData;
    
    public BlockData() {
        this.connectData = new HashMap();
    }
    
    public void put(final String s, final boolean[] array) {
        this.connectData.put(s, array);
    }
    
    public boolean connectsTo(final String s, final BlockFace blockFace, final boolean b) {
        boolean[] array = null;
        if (b) {
            array = this.connectData.get("allFalseIfStairPre1_12");
        }
        if (array == null) {
            array = this.connectData.get(s);
        }
        return array != null && array[blockFace.ordinal()];
    }
}
