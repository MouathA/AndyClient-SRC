package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.*;
import de.gerrygames.viarewind.utils.*;

public class WorldBorder extends StoredObject implements Tickable
{
    private double x;
    private double z;
    private double oldDiameter;
    private double newDiameter;
    private long lerpTime;
    private long lerpStartTime;
    private int portalTeleportBoundary;
    private int warningTime;
    private int warningBlocks;
    private boolean init;
    private final int VIEW_DISTANCE = 16;
    
    public WorldBorder(final UserConnection userConnection) {
        super(userConnection);
        this.init = false;
    }
    
    @Override
    public void tick() {
        if (!this.isInit()) {
            return;
        }
        this.sendPackets();
    }
    
    private void sendPackets() {
        final PlayerPosition playerPosition = (PlayerPosition)this.getUser().get(PlayerPosition.class);
        final double n = this.getSize() / 2.0;
        final Side[] values = Side.values();
        while (0 < values.length) {
            final Side side = values[0];
            double n2;
            double n3;
            double n4;
            if (Side.access$000(side) != 0) {
                n2 = playerPosition.getPosZ();
                n3 = this.z;
                n4 = Math.abs(this.x + n * Side.access$000(side) - playerPosition.getPosX());
            }
            else {
                n3 = this.x;
                n2 = playerPosition.getPosX();
                n4 = Math.abs(this.z + n * Side.access$100(side) - playerPosition.getPosZ());
            }
            if (n4 < 16.0) {
                final double sqrt = Math.sqrt(256.0 - n4 * n4);
                double n5 = Math.ceil(n2 - sqrt);
                double n6 = Math.floor(n2 + sqrt);
                double ceil = Math.ceil(playerPosition.getPosY() - sqrt);
                final double floor = Math.floor(playerPosition.getPosY() + sqrt);
                if (n5 < n3 - n) {
                    n5 = Math.ceil(n3 - n);
                }
                if (n6 > n3 + n) {
                    n6 = Math.floor(n3 + n);
                }
                if (ceil < 0.0) {
                    ceil = 0.0;
                }
                final double n7 = (n5 + n6) / 2.0;
                final double n8 = (ceil + floor) / 2.0;
                final int n9 = (int)Math.floor((n6 - n5) * (floor - ceil) * 0.5);
                final double n10 = 2.5;
                final PacketWrapper create = PacketWrapper.create(42, null, this.getUser());
                create.write(Type.STRING, "fireworksSpark");
                create.write(Type.FLOAT, (float)((Side.access$000(side) != 0) ? (this.x + n * Side.access$000(side)) : n7));
                create.write(Type.FLOAT, (float)n8);
                create.write(Type.FLOAT, (float)((Side.access$000(side) == 0) ? (this.z + n * Side.access$100(side)) : n7));
                create.write(Type.FLOAT, (float)((Side.access$000(side) != 0) ? 0.0 : ((n6 - n5) / n10)));
                create.write(Type.FLOAT, (float)((floor - ceil) / n10));
                create.write(Type.FLOAT, (float)((Side.access$000(side) == 0) ? 0.0 : ((n6 - n5) / n10)));
                create.write(Type.FLOAT, 0.0f);
                create.write(Type.INT, n9);
                PacketUtil.sendPacket(create, Protocol1_7_6_10TO1_8.class, true, true);
            }
            int n11 = 0;
            ++n11;
        }
    }
    
    private boolean isInit() {
        return this.init;
    }
    
    public void init(final double x, final double z, final double oldDiameter, final double newDiameter, final long lerpTime, final int portalTeleportBoundary, final int warningTime, final int warningBlocks) {
        this.x = x;
        this.z = z;
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.lerpTime = lerpTime;
        this.portalTeleportBoundary = portalTeleportBoundary;
        this.warningTime = warningTime;
        this.warningBlocks = warningBlocks;
        this.init = true;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setCenter(final double x, final double z) {
        this.x = x;
        this.z = z;
    }
    
    public double getOldDiameter() {
        return this.oldDiameter;
    }
    
    public double getNewDiameter() {
        return this.newDiameter;
    }
    
    public long getLerpTime() {
        return this.lerpTime;
    }
    
    public void lerpSize(final double oldDiameter, final double newDiameter, final long lerpTime) {
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.lerpTime = lerpTime;
        this.lerpStartTime = System.currentTimeMillis();
    }
    
    public void setSize(final double n) {
        this.oldDiameter = n;
        this.newDiameter = n;
        this.lerpTime = 0L;
    }
    
    public double getSize() {
        if (this.lerpTime == 0L) {
            return this.newDiameter;
        }
        double n = (System.currentTimeMillis() - this.lerpStartTime) / (double)this.lerpTime;
        if (n > 1.0) {
            n = 1.0;
        }
        else if (n < 0.0) {
            n = 0.0;
        }
        return this.oldDiameter + (this.newDiameter - this.oldDiameter) * n;
    }
    
    public int getPortalTeleportBoundary() {
        return this.portalTeleportBoundary;
    }
    
    public void setPortalTeleportBoundary(final int portalTeleportBoundary) {
        this.portalTeleportBoundary = portalTeleportBoundary;
    }
    
    public int getWarningTime() {
        return this.warningTime;
    }
    
    public void setWarningTime(final int warningTime) {
        this.warningTime = warningTime;
    }
    
    public int getWarningBlocks() {
        return this.warningBlocks;
    }
    
    public void setWarningBlocks(final int warningBlocks) {
        this.warningBlocks = warningBlocks;
    }
    
    private enum Side
    {
        NORTH("NORTH", 0, 0, -1), 
        EAST("EAST", 1, 1, 0), 
        SOUTH("SOUTH", 2, 0, 1), 
        WEST("WEST", 3, -1, 0);
        
        private int modX;
        private int modZ;
        private static final Side[] $VALUES;
        
        private Side(final String s, final int n, final int modX, final int modZ) {
            this.modX = modX;
            this.modZ = modZ;
        }
        
        static int access$000(final Side side) {
            return side.modX;
        }
        
        static int access$100(final Side side) {
            return side.modZ;
        }
        
        static {
            $VALUES = new Side[] { Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST };
        }
    }
}
