package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class SoundRewriter extends com.viaversion.viaversion.rewriter.SoundRewriter
{
    private final BackwardsProtocol protocol;
    
    public SoundRewriter(final BackwardsProtocol protocol) {
        super(protocol);
        this.protocol = protocol;
    }
    
    public void registerNamedSound(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final SoundRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(this.this$0.getNamedSoundHandler());
            }
        });
    }
    
    public void registerStopSound(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final SoundRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this.this$0.getStopSoundHandler());
            }
        });
    }
    
    public PacketHandler getNamedSoundHandler() {
        return this::lambda$getNamedSoundHandler$0;
    }
    
    public PacketHandler getStopSoundHandler() {
        return this::lambda$getStopSoundHandler$1;
    }
    
    private void lambda$getStopSoundHandler$1(final PacketWrapper packetWrapper) throws Exception {
        final byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
        if ((byteValue & 0x2) == 0x0) {
            return;
        }
        if ((byteValue & 0x1) != 0x0) {
            packetWrapper.passthrough(Type.VAR_INT);
        }
        final String s = (String)packetWrapper.read(Type.STRING);
        final String mappedNamedSound = this.protocol.getMappingData().getMappedNamedSound(s);
        if (mappedNamedSound == null) {
            packetWrapper.write(Type.STRING, s);
            return;
        }
        if (!mappedNamedSound.isEmpty()) {
            packetWrapper.write(Type.STRING, mappedNamedSound);
        }
        else {
            packetWrapper.cancel();
        }
    }
    
    private void lambda$getNamedSoundHandler$0(final PacketWrapper packetWrapper) throws Exception {
        final String mappedNamedSound = this.protocol.getMappingData().getMappedNamedSound((String)packetWrapper.get(Type.STRING, 0));
        if (mappedNamedSound == null) {
            return;
        }
        if (!mappedNamedSound.isEmpty()) {
            packetWrapper.set(Type.STRING, 0, mappedNamedSound);
        }
        else {
            packetWrapper.cancel();
        }
    }
}
