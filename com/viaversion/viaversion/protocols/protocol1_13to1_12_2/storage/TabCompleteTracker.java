package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;

public class TabCompleteTracker implements StorableObject
{
    private int transactionId;
    private String input;
    private String lastTabComplete;
    private long timeToSend;
    
    public void sendPacketToServer(final UserConnection userConnection) {
        if (this.lastTabComplete == null || this.timeToSend > System.currentTimeMillis()) {
            return;
        }
        final PacketWrapper create = PacketWrapper.create(ServerboundPackets1_12_1.TAB_COMPLETE, null, userConnection);
        create.write(Type.STRING, this.lastTabComplete);
        create.write(Type.BOOLEAN, false);
        create.write(Type.OPTIONAL_POSITION, null);
        create.scheduleSendToServer(Protocol1_13To1_12_2.class);
        this.lastTabComplete = null;
    }
    
    public int getTransactionId() {
        return this.transactionId;
    }
    
    public void setTransactionId(final int transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getInput() {
        return this.input;
    }
    
    public void setInput(final String input) {
        this.input = input;
    }
    
    public String getLastTabComplete() {
        return this.lastTabComplete;
    }
    
    public void setLastTabComplete(final String lastTabComplete) {
        this.lastTabComplete = lastTabComplete;
    }
    
    public long getTimeToSend() {
        return this.timeToSend;
    }
    
    public void setTimeToSend(final long timeToSend) {
        this.timeToSend = timeToSend;
    }
}
