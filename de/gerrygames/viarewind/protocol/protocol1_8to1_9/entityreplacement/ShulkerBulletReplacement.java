package de.gerrygames.viarewind.protocol.protocol1_8to1_9.entityreplacement;

import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.*;
import de.gerrygames.viarewind.utils.*;

public class ShulkerBulletReplacement implements EntityReplacement
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
    
    public ShulkerBulletReplacement(final int entityId, final UserConnection user) {
        this.datawatcher = new ArrayList();
        this.entityId = entityId;
        this.user = user;
        this.spawn();
    }
    
    @Override
    public void setLocation(final double locX, final double locY, final double locZ) {
        if (locX != this.locX || locY != this.locY || locZ != this.locZ) {
            this.locX = locX;
            this.locY = locY;
            this.locZ = locZ;
            this.updateLocation();
        }
    }
    
    @Override
    public void relMove(final double n, final double n2, final double n3) {
        if (n == 0.0 && n2 == 0.0 && n3 == 0.0) {
            return;
        }
        this.locX += n;
        this.locY += n2;
        this.locZ += n3;
        this.updateLocation();
    }
    
    @Override
    public void setYawPitch(final float yaw, final float pitch) {
        if (this.yaw != yaw && this.pitch != pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.updateLocation();
        }
    }
    
    @Override
    public void setHeadYaw(final float headYaw) {
        this.headYaw = headYaw;
    }
    
    @Override
    public void updateMetadata(final List list) {
    }
    
    public void updateLocation() {
        final PacketWrapper create = PacketWrapper.create(24, null, this.user);
        create.write(Type.VAR_INT, this.entityId);
        create.write(Type.INT, (int)(this.locX * 32.0));
        create.write(Type.INT, (int)(this.locY * 32.0));
        create.write(Type.INT, (int)(this.locZ * 32.0));
        create.write(Type.BYTE, (byte)(this.yaw / 360.0f * 256.0f));
        create.write(Type.BYTE, (byte)(this.pitch / 360.0f * 256.0f));
        create.write(Type.BOOLEAN, true);
        final PacketWrapper create2 = PacketWrapper.create(25, null, this.user);
        create2.write(Type.VAR_INT, this.entityId);
        create2.write(Type.BYTE, (byte)(this.headYaw / 360.0f * 256.0f));
        PacketUtil.sendPacket(create, Protocol1_8TO1_9.class, true, true);
        PacketUtil.sendPacket(create2, Protocol1_8TO1_9.class, true, true);
    }
    
    @Override
    public void spawn() {
        final PacketWrapper create = PacketWrapper.create(14, null, this.user);
        create.write(Type.VAR_INT, this.entityId);
        create.write(Type.BYTE, 66);
        create.write(Type.INT, 0);
        create.write(Type.INT, 0);
        create.write(Type.INT, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.BYTE, 0);
        create.write(Type.INT, 0);
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
}
