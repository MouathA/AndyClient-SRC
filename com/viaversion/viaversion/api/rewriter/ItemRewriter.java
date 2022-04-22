package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.item.*;

public interface ItemRewriter extends Rewriter
{
    Item handleItemToClient(final Item p0);
    
    Item handleItemToServer(final Item p0);
}
