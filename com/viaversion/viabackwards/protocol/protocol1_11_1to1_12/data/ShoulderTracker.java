package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.*;

public class ShoulderTracker extends StoredObject
{
    private int entityId;
    private String leftShoulder;
    private String rightShoulder;
    
    public ShoulderTracker(final UserConnection userConnection) {
        super(userConnection);
    }
    
    public void update() {
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_12.CHAT_MESSAGE, null, this.getUser());
        create.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(this.generateString()));
        create.write(Type.BYTE, 2);
        create.scheduleSend(Protocol1_11_1To1_12.class);
    }
    
    private String generateString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("  ");
        if (this.leftShoulder == null) {
            sb.append("§4§lNothing");
        }
        else {
            sb.append("§2§l").append(this.getName(this.leftShoulder));
        }
        sb.append("§8§l <- §7§lShoulders§8§l -> ");
        if (this.rightShoulder == null) {
            sb.append("§4§lNothing");
        }
        else {
            sb.append("§2§l").append(this.getName(this.rightShoulder));
        }
        return sb.toString();
    }
    
    private String getName(String substring) {
        if (substring.startsWith("minecraft:")) {
            substring = substring.substring(10);
        }
        final String[] split = substring.split("_");
        final StringBuilder sb = new StringBuilder();
        final String[] array = split;
        while (0 < array.length) {
            final String s = array[0];
            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public void setEntityId(final int entityId) {
        this.entityId = entityId;
    }
    
    public String getLeftShoulder() {
        return this.leftShoulder;
    }
    
    public void setLeftShoulder(final String leftShoulder) {
        this.leftShoulder = leftShoulder;
    }
    
    public String getRightShoulder() {
        return this.rightShoulder;
    }
    
    public void setRightShoulder(final String rightShoulder) {
        this.rightShoulder = rightShoulder;
    }
    
    @Override
    public String toString() {
        return "ShoulderTracker{entityId=" + this.entityId + ", leftShoulder='" + this.leftShoulder + '\'' + ", rightShoulder='" + this.rightShoulder + '\'' + '}';
    }
}
