package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.entityreplacements;

import de.gerrygames.viarewind.replacement.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.api.minecraft.metadata.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.metadata.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import de.gerrygames.viarewind.utils.math.*;

public class ArmorStandReplacement implements EntityReplacement
{
    private int entityId;
    private List datawatcher;
    private int[] entityIds;
    private double locX;
    private double locY;
    private double locZ;
    private State currentState;
    private boolean invisible;
    private boolean nameTagVisible;
    private String name;
    private UserConnection user;
    private float yaw;
    private float pitch;
    private float headYaw;
    private boolean small;
    private boolean marker;
    
    @Override
    public int getEntityId() {
        return this.entityId;
    }
    
    public ArmorStandReplacement(final int entityId, final UserConnection user) {
        this.datawatcher = new ArrayList();
        this.entityIds = null;
        this.currentState = null;
        this.invisible = false;
        this.nameTagVisible = false;
        this.name = null;
        this.small = false;
        this.marker = false;
        this.entityId = entityId;
        this.user = user;
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
    public void setYawPitch(final float n, final float pitch) {
        if ((this.yaw != n && this.pitch != pitch) || this.headYaw != n) {
            this.yaw = n;
            this.headYaw = n;
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
            this.datawatcher.removeIf(ArmorStandReplacement::lambda$updateMetadata$0);
            this.datawatcher.add(metadata);
        }
        this.updateState();
    }
    
    public void updateState() {
        for (final Metadata metadata : this.datawatcher) {
            if (metadata.id() == 0 && metadata.metaType() == MetaType1_8.Byte) {
                (byte)metadata.getValue();
            }
            else if (metadata.id() == 2 && metadata.metaType() == MetaType1_8.String) {
                this.name = (String)metadata.getValue();
                if (this.name == null || !this.name.equals("")) {
                    continue;
                }
                this.name = null;
            }
            else if (metadata.id() == 10 && metadata.metaType() == MetaType1_8.Byte) {
                (byte)metadata.getValue();
            }
            else {
                if (metadata.id() != 3 || metadata.metaType() != MetaType1_8.Byte) {
                    continue;
                }
                this.nameTagVisible = ((byte)metadata.id() != 0);
            }
        }
        this.invisible = false;
        this.small = false;
        this.marker = false;
        final State currentState = this.currentState;
        if (this.invisible && this.name != null) {
            this.currentState = State.HOLOGRAM;
        }
        else {
            this.currentState = State.ZOMBIE;
        }
        if (this.currentState != currentState) {
            this.despawn();
            this.spawn();
        }
        else {
            this.updateMetadata();
            this.updateLocation();
        }
    }
    
    public void updateLocation() {
        if (this.entityIds == null) {
            return;
        }
        if (this.currentState == State.ZOMBIE) {
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
        else if (this.currentState == State.HOLOGRAM) {
            final PacketWrapper create3 = PacketWrapper.create(27, null, this.user);
            create3.write(Type.INT, this.entityIds[1]);
            create3.write(Type.INT, -1);
            create3.write(Type.BOOLEAN, false);
            final PacketWrapper create4 = PacketWrapper.create(24, null, this.user);
            create4.write(Type.INT, this.entityIds[0]);
            create4.write(Type.INT, (int)(this.locX * 32.0));
            create4.write(Type.INT, (int)((this.locY + (this.marker ? 54.85 : (this.small ? 56.0 : 57.0))) * 32.0));
            create4.write(Type.INT, (int)(this.locZ * 32.0));
            create4.write(Type.BYTE, 0);
            create4.write(Type.BYTE, 0);
            final PacketWrapper create5 = PacketWrapper.create(24, null, this.user);
            create5.write(Type.INT, this.entityIds[1]);
            create5.write(Type.INT, (int)(this.locX * 32.0));
            create5.write(Type.INT, (int)((this.locY + 56.75) * 32.0));
            create5.write(Type.INT, (int)(this.locZ * 32.0));
            create5.write(Type.BYTE, 0);
            create5.write(Type.BYTE, 0);
            final PacketWrapper create6 = PacketWrapper.create(27, null, this.user);
            create6.write(Type.INT, this.entityIds[1]);
            create6.write(Type.INT, this.entityIds[0]);
            create6.write(Type.BOOLEAN, false);
            PacketUtil.sendPacket(create3, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(create4, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(create5, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(create6, Protocol1_7_6_10TO1_8.class, true, true);
        }
    }
    
    public void updateMetadata() {
        if (this.entityIds == null) {
            return;
        }
        final PacketWrapper create = PacketWrapper.create(28, null, this.user);
        if (this.currentState == State.ZOMBIE) {
            create.write(Type.INT, this.entityIds[0]);
            final ArrayList<Metadata> list = new ArrayList<Metadata>();
            for (final Metadata metadata : this.datawatcher) {
                if (metadata.id() >= 0) {
                    if (metadata.id() > 9) {
                        continue;
                    }
                    list.add(new Metadata(metadata.id(), metadata.metaType(), metadata.getValue()));
                }
            }
            if (this.small) {
                list.add(new Metadata(12, MetaType1_8.Byte, 1));
            }
            MetadataRewriter.transform(Entity1_10Types.EntityType.ZOMBIE, list);
            create.write(Types1_7_6_10.METADATA_LIST, list);
        }
        else {
            if (this.currentState != State.HOLOGRAM) {
                return;
            }
            create.write(Type.INT, this.entityIds[1]);
            final ArrayList<Metadata> list2 = new ArrayList<Metadata>();
            list2.add(new Metadata(12, MetaType1_7_6_10.Int, -1700000));
            list2.add(new Metadata(10, MetaType1_7_6_10.String, this.name));
            list2.add(new Metadata(11, MetaType1_7_6_10.Byte, 1));
            create.write(Types1_7_6_10.METADATA_LIST, list2);
        }
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class);
    }
    
    @Override
    public void spawn() {
        if (this.entityIds != null) {
            this.despawn();
        }
        if (this.currentState == State.ZOMBIE) {
            final PacketWrapper create = PacketWrapper.create(15, null, this.user);
            create.write(Type.VAR_INT, this.entityId);
            create.write(Type.UNSIGNED_BYTE, 54);
            create.write(Type.INT, (int)(this.locX * 32.0));
            create.write(Type.INT, (int)(this.locY * 32.0));
            create.write(Type.INT, (int)(this.locZ * 32.0));
            create.write(Type.BYTE, 0);
            create.write(Type.BYTE, 0);
            create.write(Type.BYTE, 0);
            create.write(Type.SHORT, 0);
            create.write(Type.SHORT, 0);
            create.write(Type.SHORT, 0);
            create.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
            PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
            this.entityIds = new int[] { this.entityId };
        }
        else if (this.currentState == State.HOLOGRAM) {
            final int[] array = { this.entityId, 0 };
            final int n = 1;
            final int n2 = 2147467647;
            ArmorStandReplacement.ENTITY_ID = 2147467646;
            array[n] = n2;
            final int[] entityIds = array;
            final PacketWrapper create2 = PacketWrapper.create(14, null, this.user);
            create2.write(Type.VAR_INT, entityIds[0]);
            create2.write(Type.BYTE, 66);
            create2.write(Type.INT, (int)(this.locX * 32.0));
            create2.write(Type.INT, (int)(this.locY * 32.0));
            create2.write(Type.INT, (int)(this.locZ * 32.0));
            create2.write(Type.BYTE, 0);
            create2.write(Type.BYTE, 0);
            create2.write(Type.INT, 0);
            final PacketWrapper create3 = PacketWrapper.create(15, null, this.user);
            create3.write(Type.VAR_INT, entityIds[1]);
            create3.write(Type.UNSIGNED_BYTE, 100);
            create3.write(Type.INT, (int)(this.locX * 32.0));
            create3.write(Type.INT, (int)(this.locY * 32.0));
            create3.write(Type.INT, (int)(this.locZ * 32.0));
            create3.write(Type.BYTE, 0);
            create3.write(Type.BYTE, 0);
            create3.write(Type.BYTE, 0);
            create3.write(Type.SHORT, 0);
            create3.write(Type.SHORT, 0);
            create3.write(Type.SHORT, 0);
            create3.write(Types1_7_6_10.METADATA_LIST, new ArrayList());
            PacketUtil.sendPacket(create2, Protocol1_7_6_10TO1_8.class, true, true);
            PacketUtil.sendPacket(create3, Protocol1_7_6_10TO1_8.class, true, true);
            this.entityIds = entityIds;
        }
        this.updateMetadata();
        this.updateLocation();
    }
    
    public AABB getBoundingBox() {
        final double n = this.small ? 0.25 : 0.5;
        return new AABB(new Vector3d(this.locX - n / 2.0, this.locY, this.locZ - n / 2.0), new Vector3d(this.locX + n / 2.0, this.locY + (this.small ? 0.9875 : 1.975), this.locZ + n / 2.0));
    }
    
    @Override
    public void despawn() {
        if (this.entityIds == null) {
            return;
        }
        final PacketWrapper create = PacketWrapper.create(19, null, this.user);
        create.write(Type.BYTE, (byte)this.entityIds.length);
        final int[] entityIds = this.entityIds;
        while (0 < entityIds.length) {
            create.write(Type.INT, entityIds[0]);
            int n = 0;
            ++n;
        }
        this.entityIds = null;
        PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
    }
    
    private static boolean lambda$updateMetadata$0(final Metadata metadata, final Metadata metadata2) {
        return metadata2.id() == metadata.id();
    }
    
    private enum State
    {
        HOLOGRAM("HOLOGRAM", 0), 
        ZOMBIE("ZOMBIE", 1);
        
        private static final State[] $VALUES;
        
        private State(final String s, final int n) {
        }
        
        static {
            $VALUES = new State[] { State.HOLOGRAM, State.ZOMBIE };
        }
    }
}
