package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;

public class CommandBlockProvider implements Provider
{
    public void addOrUpdateBlock(final UserConnection userConnection, final Position position, final CompoundTag compoundTag) throws Exception {
        this.checkPermission(userConnection);
        if (this.isEnabled()) {
            this.getStorage(userConnection).addOrUpdateBlock(position, compoundTag);
        }
    }
    
    public Optional get(final UserConnection userConnection, final Position position) throws Exception {
        this.checkPermission(userConnection);
        if (this.isEnabled()) {
            return this.getStorage(userConnection).getCommandBlock(position);
        }
        return Optional.empty();
    }
    
    public void unloadChunk(final UserConnection userConnection, final int n, final int n2) throws Exception {
        this.checkPermission(userConnection);
        if (this.isEnabled()) {
            this.getStorage(userConnection).unloadChunk(n, n2);
        }
    }
    
    private CommandBlockStorage getStorage(final UserConnection userConnection) {
        return (CommandBlockStorage)userConnection.get(CommandBlockStorage.class);
    }
    
    public void sendPermission(final UserConnection userConnection) throws Exception {
        if (!this.isEnabled()) {
            return;
        }
        final PacketWrapper create = PacketWrapper.create(ClientboundPackets1_9.ENTITY_STATUS, null, userConnection);
        create.write(Type.INT, ((EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class)).getProvidedEntityId());
        create.write(Type.BYTE, 26);
        create.scheduleSend(Protocol1_9To1_8.class);
        ((CommandBlockStorage)userConnection.get(CommandBlockStorage.class)).setPermissions(true);
    }
    
    private void checkPermission(final UserConnection userConnection) throws Exception {
        if (!this.isEnabled()) {
            return;
        }
        if (!this.getStorage(userConnection).isPermissions()) {
            this.sendPermission(userConnection);
        }
    }
    
    public boolean isEnabled() {
        return true;
    }
    
    public void unloadChunks(final UserConnection userConnection) {
        if (this.isEnabled()) {
            this.getStorage(userConnection).unloadChunks();
        }
    }
}
