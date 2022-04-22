package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.types.minecraft.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.*;

public class MetadataListType extends MetaListTypeTemplate
{
    private MetadataType metadataType;
    
    public MetadataListType() {
        this.metadataType = new MetadataType();
    }
    
    @Override
    public List read(final ByteBuf byteBuf) throws Exception {
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        Metadata metadata;
        do {
            metadata = (Metadata)Types1_7_6_10.METADATA.read(byteBuf);
            if (metadata != null) {
                list.add(metadata);
            }
        } while (metadata != null);
        return list;
    }
    
    public void write(final ByteBuf byteBuf, final List list) throws Exception {
        final Iterator<Metadata> iterator = list.iterator();
        while (iterator.hasNext()) {
            Types1_7_6_10.METADATA.write(byteBuf, iterator.next());
        }
        if (list.isEmpty()) {
            Types1_7_6_10.METADATA.write(byteBuf, new Metadata(0, MetaType1_7_6_10.Byte, 0));
        }
        byteBuf.writeByte(127);
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
