package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata.*;
import com.viaversion.viaversion.api.type.types.version.*;

public class ShulkerReplacement implements EntityReplacement
{
    private int entityId;
    private List datawatcher;
    private double locX;
    private double locY;
    private double locZ;
    private UserConnection user;
    
    public ShulkerReplacement(final int entityId, final UserConnection user) {
        this.datawatcher = new ArrayList();
        this.entityId = entityId;
        this.user = user;
        this.spawn();
    }
    
    @Override
    public void setLocation(final double locX, final double locY, final double locZ) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        this.updateLocation();
    }
    
    @Override
    public void relMove(final double n, final double n2, final double n3) {
        this.locX += n;
        this.locY += n2;
        this.locZ += n3;
        this.updateLocation();
    }
    
    @Override
    public void setYawPitch(final float n, final float n2) {
    }
    
    @Override
    public void setHeadYaw(final float n) {
    }
    
    @Override
    public void updateMetadata(final List list) {
        for (final Metadata metadata : list) {
            this.datawatcher.removeIf(ShulkerReplacement::lambda$updateMetadata$0);
            this.datawatcher.add(metadata);
        }
        this.updateMetadata();
    }
    
    public void updateLocation() {
        final PacketWrapper create = PacketWrapper.create(24, null, this.user);
        create.write(Type.VAR_INT, this.entityId);
        create.write(Type.INT, (int)(this.locX * 32.0));
        create.write(Type.INT, (int)(this.locY * 32.0));
        create.write(Type.INT, (int)(this.locZ * 32.0));
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BOOLEAN, true);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, true);
    }
    
    public void updateMetadata() {
        final PacketWrapper create = PacketWrapper.create(28, null, this.user);
        create.write(Type.VAR_INT, this.entityId);
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        for (final Metadata metadata : this.datawatcher) {
            if (metadata.id() != 11 && metadata.id() != 12) {
                if (metadata.id() == 13) {
                    continue;
                }
                list.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
            }
        }
        list.add(new Metadata(11, MetaType1_9.VarInt, 2));
        MetadataRewriter.transform(Entity1_10Types.EntityType.MAGMA_CUBE, list);
        create.write(Types1_8.METADATA_LIST, list);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class);
    }
    
    @Override
    public void spawn() {
        final PacketWrapper create = PacketWrapper.create(15, null, this.user);
        create.write(Type.VAR_INT, this.entityId);
        create.write(Type.UNSIGNED_BYTE, 62);
        create.write(Type.INT, 0);
        create.write(Type.INT, 0);
        create.write(Type.INT, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.SHORT, 0);
        create.write(Type.SHORT, 0);
        create.write(Type.SHORT, 0);
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        list.add(new Metadata(0, MetaType1_9.Byte, 0));
        create.write(Types1_8.METADATA_LIST, list);
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, true);
    }
    
    @Override
    public void despawn() {
        final PacketWrapper create = PacketWrapper.create(19, null, this.user);
        create.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[] { this.entityId });
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, true);
    }
    
    @Override
    public int getEntityId() {
        return this.entityId;
    }
    
    private static boolean lambda$updateMetadata$0(final Metadata metadata, final Metadata metadata2) {
        return metadata2.id() == metadata.id();
    }
}
