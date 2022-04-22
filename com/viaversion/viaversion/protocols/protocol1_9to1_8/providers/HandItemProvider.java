package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class HandItemProvider implements Provider
{
    public Item getHandItem(final UserConnection userConnection) {
        return new DataItem(0, (byte)0, (short)0, null);
    }
}
