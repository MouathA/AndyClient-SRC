package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.*;
import com.viaversion.viaversion.api.minecraft.*;
import de.gerrygames.viarewind.*;
import java.util.*;

public class MetadataRewriter
{
    public static void transform(final Entity1_10Types.EntityType entityType, final List list) {
        for (final Metadata metadata : new ArrayList<Metadata>(list)) {
            final MetaIndex searchIndex = MetaIndex1_8to1_9.searchIndex(entityType, metadata.id());
            if (searchIndex == null) {
                throw new Exception("Could not find valid metadata");
            }
            if (searchIndex.getOldType() == MetaType1_8.NonExistent || searchIndex.getNewType() == null) {
                list.remove(metadata);
            }
            else {
                final Object value = metadata.getValue();
                metadata.setMetaTypeUnsafe(searchIndex.getOldType());
                metadata.setId(searchIndex.getIndex());
                switch (searchIndex.getNewType()) {
                    case Byte: {
                        if (searchIndex.getOldType() == MetaType1_8.Byte) {
                            metadata.setValue(value);
                        }
                        if (searchIndex.getOldType() == MetaType1_8.Int) {
                            metadata.setValue((int)value);
                            break;
                        }
                        break;
                    }
                    case OptUUID: {
                        if (searchIndex.getOldType() != MetaType1_8.String) {
                            list.remove(metadata);
                            break;
                        }
                        final UUID uuid = (UUID)value;
                        if (uuid == null) {
                            metadata.setValue("");
                            break;
                        }
                        metadata.setValue(uuid.toString());
                        break;
                    }
                    case BlockID: {
                        list.remove(metadata);
                        list.add(new Metadata(searchIndex.getIndex(), MetaType1_8.Short, ((Integer)value).shortValue()));
                        break;
                    }
                    case VarInt: {
                        if (searchIndex.getOldType() == MetaType1_8.Byte) {
                            metadata.setValue(((Integer)value).byteValue());
                        }
                        if (searchIndex.getOldType() == MetaType1_8.Short) {
                            metadata.setValue(((Integer)value).shortValue());
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
                            metadata.setValue((byte)(value ? -1 : 0));
                            break;
                        }
                        metadata.setValue((byte)(byte)(((boolean)value) ? 1 : 0));
                        break;
                    }
                    case Slot: {
                        metadata.setValue(ItemRewriter.toClient((Item)value));
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
                        metadata.setValue(value);
                        break;
                    }
                    default: {
                        ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + searchIndex.getNewType());
                        list.remove(metadata);
                        break;
                    }
                }
                if (searchIndex.getOldType().type().getOutputClass().isAssignableFrom(metadata.getValue().getClass())) {
                    continue;
                }
                list.remove(metadata);
            }
        }
    }
}
