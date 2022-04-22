package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata;

import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_7_6_10.metadata.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.*;
import de.gerrygames.viarewind.*;
import java.util.*;

public class MetadataRewriter
{
    public static void transform(final Entity1_10Types.EntityType entityType, final List list) {
        for (final Metadata metadata : new ArrayList<Metadata>(list)) {
            final MetaIndex1_8to1_7_6_10 searchIndex = MetaIndex1_7_6_10to1_8.searchIndex(entityType, metadata.id());
            if (searchIndex == null) {
                throw new Exception("Could not find valid metadata");
            }
            if (searchIndex.getOldType() == MetaType1_7_6_10.NonExistent) {
                list.remove(metadata);
            }
            else {
                Object o = metadata.getValue();
                if (!searchIndex.getNewType().type().getOutputClass().isAssignableFrom(((Byte)o).getClass())) {
                    list.remove(metadata);
                }
                else {
                    metadata.setMetaTypeUnsafe(searchIndex.getOldType());
                    metadata.setId(searchIndex.getIndex());
                    switch (searchIndex.getOldType()) {
                        case Int: {
                            if (searchIndex.getNewType() == MetaType1_8.Byte) {
                                metadata.setValue((int)o);
                                if (searchIndex == MetaIndex1_8to1_7_6_10.ENTITY_AGEABLE_AGE && (int)metadata.getValue() < 0) {
                                    metadata.setValue(-25000);
                                }
                            }
                            if (searchIndex.getNewType() == MetaType1_8.Short) {
                                metadata.setValue((int)o);
                            }
                            if (searchIndex.getNewType() == MetaType1_8.Int) {
                                metadata.setValue(o);
                                continue;
                            }
                            continue;
                        }
                        case Byte: {
                            if (searchIndex.getNewType() == MetaType1_8.Int) {
                                metadata.setValue(((Integer)o).byteValue());
                            }
                            if (searchIndex.getNewType() == MetaType1_8.Byte) {
                                if (searchIndex == MetaIndex1_8to1_7_6_10.ITEM_FRAME_ROTATION) {
                                    o = ((byte)o / 2).byteValue();
                                }
                                metadata.setValue(o);
                            }
                            if (searchIndex == MetaIndex1_8to1_7_6_10.HUMAN_SKIN_FLAGS) {
                                metadata.setValue((byte)((((byte)o & 0x1) != 0x0) ? 0 : 2));
                                continue;
                            }
                            continue;
                        }
                        case Slot: {
                            metadata.setValue(ItemRewriter.toClient((Item)o));
                            continue;
                        }
                        case Float: {
                            metadata.setValue(o);
                            continue;
                        }
                        case Short: {
                            metadata.setValue(o);
                            continue;
                        }
                        case String: {
                            metadata.setValue(o);
                            continue;
                        }
                        case Position: {
                            metadata.setValue(o);
                            continue;
                        }
                        default: {
                            ViaRewind.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + searchIndex.getNewType());
                            list.remove(metadata);
                            continue;
                        }
                    }
                }
            }
        }
    }
}
