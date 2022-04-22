package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.rewriters.*;
import java.util.function.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.*;
import com.viaversion.viaversion.api.data.entity.*;
import com.viaversion.viaversion.api.connection.*;

public class EntityPositionHandler
{
    public static final double RELATIVE_MOVE_FACTOR = 4096.0;
    private final EntityRewriterBase entityRewriter;
    private final Class storageClass;
    private final Supplier storageSupplier;
    private boolean warnedForMissingEntity;
    
    public EntityPositionHandler(final EntityRewriterBase entityRewriter, final Class storageClass, final Supplier storageSupplier) {
        this.entityRewriter = entityRewriter;
        this.storageClass = storageClass;
        this.storageSupplier = storageSupplier;
    }
    
    public void cacheEntityPosition(final PacketWrapper packetWrapper, final boolean b, final boolean b2) throws Exception {
        this.cacheEntityPosition(packetWrapper, (double)packetWrapper.get(Type.DOUBLE, 0), (double)packetWrapper.get(Type.DOUBLE, 1), (double)packetWrapper.get(Type.DOUBLE, 2), b, b2);
    }
    
    public void cacheEntityPosition(final PacketWrapper packetWrapper, final double n, final double n2, final double n3, final boolean b, final boolean b2) throws Exception {
        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
        final StoredEntityData entityData = this.entityRewriter.tracker(packetWrapper.user()).entityData(intValue);
        if (entityData == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + intValue + " missing at position: " + n + " - " + n2 + " - " + n3 + " in " + this.storageClass.getSimpleName());
                if (intValue == -1 && n == 0.0 && n2 == 0.0 && n3 == 0.0) {
                    ViaBackwards.getPlatform().getLogger().warning("DO NOT REPORT THIS TO VIA, THIS IS A PLUGIN ISSUE");
                }
                else if (!this.warnedForMissingEntity) {
                    this.warnedForMissingEntity = true;
                    ViaBackwards.getPlatform().getLogger().warning("This is very likely caused by a plugin sending a teleport packet for an entity outside of the player's range.");
                }
            }
            return;
        }
        EntityPositionStorage entityPositionStorage;
        if (b) {
            entityPositionStorage = this.storageSupplier.get();
            entityData.put(entityPositionStorage);
        }
        else {
            entityPositionStorage = (EntityPositionStorage)entityData.get(this.storageClass);
            if (entityPositionStorage == null) {
                ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + intValue + " missing " + this.storageClass.getSimpleName());
                return;
            }
        }
        entityPositionStorage.setCoordinates(n, n2, n3, b2);
    }
    
    public EntityPositionStorage getStorage(final UserConnection userConnection, final int n) {
        final StoredEntityData entityData = this.entityRewriter.tracker(userConnection).entityData(n);
        final EntityPositionStorage entityPositionStorage;
        if (entityData == null || (entityPositionStorage = (EntityPositionStorage)entityData.get(EntityPositionStorage.class)) == null) {
            ViaBackwards.getPlatform().getLogger().warning("Untracked entity with id " + n + " in " + this.storageClass.getSimpleName());
            return null;
        }
        return entityPositionStorage;
    }
    
    public static void writeFacingAngles(final PacketWrapper packetWrapper, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n4 - n;
        final double n8 = n5 - n2;
        final double n9 = n6 - n3;
        final double sqrt = Math.sqrt(n7 * n7 + n8 * n8 + n9 * n9);
        double n10 = -Math.atan2(n7, n9) / 3.141592653589793 * 180.0;
        if (n10 < 0.0) {
            n10 += 360.0;
        }
        final double n11 = -Math.asin(n8 / sqrt) / 3.141592653589793 * 180.0;
        packetWrapper.write(Type.BYTE, (byte)(n10 * 256.0 / 360.0));
        packetWrapper.write(Type.BYTE, (byte)(n11 * 256.0 / 360.0));
    }
    
    public static void writeFacingDegrees(final PacketWrapper packetWrapper, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n4 - n;
        final double n8 = n5 - n2;
        final double n9 = n6 - n3;
        final double sqrt = Math.sqrt(n7 * n7 + n8 * n8 + n9 * n9);
        double n10 = -Math.atan2(n7, n9) / 3.141592653589793 * 180.0;
        if (n10 < 0.0) {
            n10 += 360.0;
        }
        final double n11 = -Math.asin(n8 / sqrt) / 3.141592653589793 * 180.0;
        packetWrapper.write(Type.FLOAT, (float)n10);
        packetWrapper.write(Type.FLOAT, (float)n11);
    }
}
