package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.function.*;
import com.viaversion.viaversion.api.data.*;
import java.util.*;

public class TagRewriter
{
    private static final int[] EMPTY_ARRAY;
    private final Protocol protocol;
    private final Map newTags;
    
    public TagRewriter(final Protocol protocol) {
        this.newTags = new EnumMap(RegistryType.class);
        this.protocol = protocol;
    }
    
    public void loadFromMappingData() {
        for (final RegistryType registryType : RegistryType.getValues()) {
            final List tags = this.protocol.getMappingData().getTags(registryType);
            if (tags != null) {
                this.getOrComputeNewTags(registryType).addAll(tags);
            }
        }
    }
    
    public void addEmptyTag(final RegistryType registryType, final String s) {
        this.getOrComputeNewTags(registryType).add(new TagData(s, TagRewriter.EMPTY_ARRAY));
    }
    
    public void addEmptyTags(final RegistryType registryType, final String... array) {
        final List orComputeNewTags = this.getOrComputeNewTags(registryType);
        for (int length = array.length, i = 0; i < length; ++i) {
            orComputeNewTags.add(new TagData(array[i], TagRewriter.EMPTY_ARRAY));
        }
    }
    
    public void addEntityTag(final String s, final EntityType... array) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = array[i].getId();
        }
        this.addTagRaw(RegistryType.ENTITY, s, array2);
    }
    
    public void addTag(final RegistryType registryType, final String s, final int... array) {
        final List orComputeNewTags = this.getOrComputeNewTags(registryType);
        final IdRewriteFunction rewriter = this.getRewriter(registryType);
        for (int i = 0; i < array.length; ++i) {
            array[i] = rewriter.rewrite(array[i]);
        }
        orComputeNewTags.add(new TagData(s, array));
    }
    
    public void addTagRaw(final RegistryType registryType, final String s, final int... array) {
        this.getOrComputeNewTags(registryType).add(new TagData(s, array));
    }
    
    public void register(final ClientboundPacketType clientboundPacketType, final RegistryType registryType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(registryType) {
            final RegistryType val$readUntilType;
            final TagRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this.this$0.getHandler(this.val$readUntilType));
            }
        });
    }
    
    public void registerGeneric(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final TagRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this.this$0.getGenericHandler());
            }
        });
    }
    
    public PacketHandler getHandler(final RegistryType registryType) {
        return this::lambda$getHandler$0;
    }
    
    public PacketHandler getGenericHandler() {
        return this::lambda$getGenericHandler$1;
    }
    
    public void handle(final PacketWrapper packetWrapper, final IdRewriteFunction idRewriteFunction, final List list) throws Exception {
        final int intValue = (int)packetWrapper.read(Type.VAR_INT);
        packetWrapper.write(Type.VAR_INT, (list != null) ? (intValue + list.size()) : intValue);
        for (int i = 0; i < intValue; ++i) {
            packetWrapper.passthrough(Type.STRING);
            final int[] array = (int[])packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
            if (idRewriteFunction != null) {
                final IntArrayList list2 = new IntArrayList(array.length);
                final int[] array2 = array;
                for (int length = array2.length, j = 0; j < length; ++j) {
                    final int rewrite = idRewriteFunction.rewrite(array2[j]);
                    if (rewrite != -1) {
                        list2.add(rewrite);
                    }
                }
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, list2.toArray(TagRewriter.EMPTY_ARRAY));
            }
            else {
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, array);
            }
        }
        if (list != null) {
            for (final TagData tagData : list) {
                packetWrapper.write(Type.STRING, tagData.identifier());
                packetWrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tagData.entries());
            }
        }
    }
    
    public List getNewTags(final RegistryType registryType) {
        return this.newTags.get(registryType);
    }
    
    public List getOrComputeNewTags(final RegistryType registryType) {
        return this.newTags.computeIfAbsent(registryType, TagRewriter::lambda$getOrComputeNewTags$2);
    }
    
    public IdRewriteFunction getRewriter(final RegistryType registryType) {
        final MappingData mappingData = this.protocol.getMappingData();
        switch (registryType) {
            case BLOCK: {
                return (mappingData != null && mappingData.getBlockMappings() != null) ? mappingData::getNewBlockId : null;
            }
            case ITEM: {
                return (mappingData != null && mappingData.getItemMappings() != null) ? mappingData::getNewItemId : null;
            }
            case ENTITY: {
                return (this.protocol.getEntityRewriter() != null) ? this::lambda$getRewriter$3 : null;
            }
            default: {
                return null;
            }
        }
    }
    
    private int lambda$getRewriter$3(final int n) {
        return this.protocol.getEntityRewriter().newEntityId(n);
    }
    
    private static List lambda$getOrComputeNewTags$2(final RegistryType registryType) {
        return new ArrayList();
    }
    
    private void lambda$getGenericHandler$1(final PacketWrapper packetWrapper) throws Exception {
        for (int intValue = (int)packetWrapper.passthrough(Type.VAR_INT), i = 0; i < intValue; ++i) {
            String substring = (String)packetWrapper.passthrough(Type.STRING);
            if (substring.startsWith("minecraft:")) {
                substring = substring.substring(10);
            }
            final RegistryType byKey = RegistryType.getByKey(substring);
            if (byKey != null) {
                this.handle(packetWrapper, this.getRewriter(byKey), this.getNewTags(byKey));
            }
            else {
                this.handle(packetWrapper, null, null);
            }
        }
    }
    
    private void lambda$getHandler$0(final RegistryType registryType, final PacketWrapper packetWrapper) throws Exception {
        for (final RegistryType registryType2 : RegistryType.getValues()) {
            this.handle(packetWrapper, this.getRewriter(registryType2), this.getNewTags(registryType2));
            if (registryType2 == registryType) {
                break;
            }
        }
    }
    
    static {
        EMPTY_ARRAY = new int[0];
    }
}
