package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;

import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;

public class EndermiteReplacement implements EntityReplacement
{
    private int entityId;
    private List datawatcher;
    private double locX;
    private double locY;
    private double locZ;
    private float yaw;
    private float pitch;
    private float headYaw;
    private UserConnection user;
    
    public EndermiteReplacement(final int entityId, final UserConnection user) {
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
    public void setYawPitch(final float yaw, final float pitch) {
        if (this.yaw != yaw || this.pitch != pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.updateLocation();
        }
    }
    
    @Override
    public void setHeadYaw(final float headYaw) {
        if (this.headYaw != headYaw) {
            this.headYaw = headYaw;
            this.updateLocation();
        }
    }
    
    @Override
    public void updateMetadata(final List list) {
        for (final Metadata metadata : list) {
            this.datawatcher.removeIf(EndermiteReplacement::lambda$updateMetadata$0);
            this.datawatcher.add(metadata);
        }
        this.updateMetadata();
    }
    
    public void updateLocation() {
        final PacketWrapper create = PacketWrapper.create(24, null, this.user);
        create.write(Type.INT, this.entityId);
        create.write(Type.INT, (int)(this.locX * 32.0));
        create.write(Type.INT, (int)(this.locY * 32.0));
        create.write(Type.INT, (int)(this.locZ * 32.0));
        create.write(Type.BYTE, (byte)(this.yaw / 360.0f * 256.0f));
        create.write(Type.BYTE, (byte)(this.pitch / 360.0f * 256.0f));
        final PacketWrapper create2 = PacketWrapper.create(25, null, this.user);
        create2.write(Type.INT, this.entityId);
        create2.write(Type.BYTE, (byte)(this.headYaw / 360.0f * 256.0f));
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
        PacketUtil.sendPacket(create2, Protocol1_7_6_10TO1_8.class, true, true);
    }
    
    public void updateMetadata() {
        final PacketWrapper create = PacketWrapper.create(28, null, this.user);
        create.write(Type.INT, this.entityId);
        final ArrayList<Metadata> list = new ArrayList<Metadata>();
        for (final Metadata metadata : this.datawatcher) {
            list.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
        }
        MetadataRewriter.transform(Entity1_10Types.EntityType.SQUID, list);
        create.write(Types1_7_6_10.METADATA_LIST, list);
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
    }
    
    @Override
    public void spawn() {
        final PacketWrapper create = PacketWrapper.create(15, null, this.user);
        create.write(Type.VAR_INT, this.entityId);
        create.write(Type.UNSIGNED_BYTE, 60);
        create.write(Type.INT, 0);
        create.write(Type.INT, 0);
        create.write(Type.INT, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.SHORT, 0);
        create.write(Type.SHORT, 0);
        create.write(Type.SHORT, 0);
        create.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
    }
    
    @Override
    public void despawn() {
        final PacketWrapper create = PacketWrapper.create(19, null, this.user);
        create.write(Types1_7_6_10.INT_ARRAY, new int[] { this.entityId });
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
    }
    
    @Override
    public int getEntityId() {
        return this.entityId;
    }
    
    private static boolean lambda$updateMetadata$0(final Metadata metadata, final Metadata metadata2) {
        return metadata2.id() == metadata.id();
    }
}
