package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.rewriter.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.*;

public class SoundPackets1_13 extends RewriterBase
{
    public SoundPackets1_13(final Protocol1_12_2To1_13 protocol1_12_2To1_13) {
        super(protocol1_12_2To1_13);
    }
    
    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.NAMED_SOUND, new PacketRemapper() {
            final SoundPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final String s = (String)packetWrapper.read(Type.STRING);
                String s2 = NamedSoundMapping.getOldId(s);
                if (s2 != null || (s2 = ((Protocol1_12_2To1_13)SoundPackets1_13.access$000(this.this$0)).getMappingData().getMappedNamedSound(s)) != null) {
                    packetWrapper.write(Type.STRING, s2);
                }
                else {
                    packetWrapper.write(Type.STRING, s);
                }
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.STOP_SOUND, ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() {
            final SoundPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.STRING, "MC|StopSound");
                final byte byteValue = (byte)packetWrapper.read(Type.BYTE);
                String s;
                if ((byteValue & 0x1) != 0x0) {
                    s = SoundPackets1_13.access$100()[(int)packetWrapper.read(Type.VAR_INT)];
                }
                else {
                    s = "";
                }
                String mappedNamedSound;
                if ((byteValue & 0x2) != 0x0) {
                    mappedNamedSound = ((Protocol1_12_2To1_13)SoundPackets1_13.access$200(this.this$0)).getMappingData().getMappedNamedSound((String)packetWrapper.read(Type.STRING));
                    if (mappedNamedSound == null) {
                        mappedNamedSound = "";
                    }
                }
                else {
                    mappedNamedSound = "";
                }
                packetWrapper.write(Type.STRING, s);
                packetWrapper.write(Type.STRING, mappedNamedSound);
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.SOUND, new PacketRemapper() {
            final SoundPackets1_13 this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int newId = ((Protocol1_12_2To1_13)SoundPackets1_13.access$300(this.this$0)).getMappingData().getSoundMappings().getNewId((int)packetWrapper.get(Type.VAR_INT, 0));
                if (newId == -1) {
                    packetWrapper.cancel();
                }
                else {
                    packetWrapper.set(Type.VAR_INT, 0, newId);
                }
            }
        });
    }
    
    static Protocol access$000(final SoundPackets1_13 soundPackets1_13) {
        return soundPackets1_13.protocol;
    }
    
    static String[] access$100() {
        return SoundPackets1_13.SOUND_SOURCES;
    }
    
    static Protocol access$200(final SoundPackets1_13 soundPackets1_13) {
        return soundPackets1_13.protocol;
    }
    
    static Protocol access$300(final SoundPackets1_13 soundPackets1_13) {
        return soundPackets1_13.protocol;
    }
    
    static {
        SoundPackets1_13.SOUND_SOURCES = new String[] { "master", "music", "record", "weather", "block", "hostile", "neutral", "player", "ambient", "voice" };
    }
}
