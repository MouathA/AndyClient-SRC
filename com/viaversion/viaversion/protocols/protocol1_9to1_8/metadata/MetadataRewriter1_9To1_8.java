package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.minecraft.*;

public class MetadataRewriter1_9To1_8 extends EntityRewriter
{
    public MetadataRewriter1_9To1_8(final Protocol1_9To1_8 protocol1_9To1_8) {
        super(protocol1_9To1_8);
    }
    
    @Override
    protected void handleMetadata(final int n, final EntityType entityType, final Metadata metadata, final List list, final UserConnection userConnection) throws Exception {
        final MetaIndex searchIndex = MetaIndex.searchIndex(entityType, metadata.id());
        if (searchIndex == null) {
            throw new Exception("Could not find valid metadata");
        }
        if (searchIndex.getNewType() == null) {
            list.remove(metadata);
            return;
        }
        metadata.setId(searchIndex.getNewIndex());
        metadata.setMetaTypeUnsafe(searchIndex.getNewType());
        final Object value = metadata.getValue();
        switch (searchIndex.getNewType()) {
            case Byte: {
                if (searchIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue(value);
                }
                if (searchIndex.getOldType() == MetaType1_8.Int) {
                    metadata.setValue(((Integer)value).byteValue());
                }
                if (searchIndex == MetaIndex.ENTITY_STATUS && entityType == Entity1_10Types.EntityType.PLAYER) {
                    Byte b = 0;
                    if (((byte)value & 0x10) == 0x10) {
                        b = 1;
                    }
                    list.add(new Metadata(MetaIndex.PLAYER_HAND.getNewIndex(), MetaIndex.PLAYER_HAND.getNewType(), b));
                    break;
                }
                break;
            }
            case OptUUID: {
                final String s = (String)value;
                Object fromString = null;
                if (!s.isEmpty()) {
                    fromString = UUID.fromString(s);
                }
                metadata.setValue(fromString);
                break;
            }
            case VarInt: {
                if (searchIndex.getOldType() == MetaType1_8.Byte) {
                    metadata.setValue((int)value);
                }
                if (searchIndex.getOldType() == MetaType1_8.Short) {
                    metadata.setValue((int)value);
                }
                if (searchIndex.getOldType() == MetaType1_8.Int) {
                    metadata.setValue(value);
                    break;
                }
                break;
            }
            case Float: {
                metadata.setValue(value);
                break;
            }
            case String: {
                metadata.setValue(value);
                break;
            }
            case Boolean: {
                if (searchIndex == MetaIndex.AGEABLE_AGE) {
                    metadata.setValue((byte)value < 0);
                    break;
                }
                metadata.setValue((byte)value != 0);
                break;
            }
            case Slot: {
                metadata.setValue(value);
                ItemRewriter.toClient((Item)metadata.getValue());
                break;
            }
            case Position: {
                metadata.setValue(value);
                break;
            }
            case Vector3F: {
                metadata.setValue(value);
                break;
            }
            case Chat: {
                metadata.setValue(Protocol1_9To1_8.fixJson(value.toString()));
                break;
            }
            case BlockID: {
                metadata.setValue(((Byte)value).intValue());
                break;
            }
            default: {
                list.remove(metadata);
                throw new Exception("Unhandled MetaDataType: " + searchIndex.getNewType());
            }
        }
    }
    
    @Override
    public EntityType typeFromId(final int n) {
        return Entity1_10Types.getTypeFromId(n, false);
    }
    
    @Override
    public EntityType objectTypeFromId(final int n) {
        return Entity1_10Types.getTypeFromId(n, true);
    }
}
