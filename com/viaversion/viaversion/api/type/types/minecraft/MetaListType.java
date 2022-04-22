package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;

public final class MetaListType extends MetaListTypeTemplate
{
    private final Type type;
    
    public MetaListType(final Type type) {
        Preconditions.checkNotNull(type);
        this.type = type;
    }
    
    @Override
    public List read(final ByteBuf byteBuf) throws Exception {
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        Metadata metadata;
        do {
            metadata = (Metadata)this.type.read(byteBuf);
            if (metadata != null) {
                list.add(metadata);
            }
        } while (metadata != null);
        return list;
    }
    
    public void write(final ByteBuf byteBuf, final List list) throws Exception {
        final Iterator<Metadata> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.type.write(byteBuf, iterator.next());
        }
        this.type.write(byteBuf, null);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (List)o);
    }
}
