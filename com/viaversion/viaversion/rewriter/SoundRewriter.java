package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class SoundRewriter
{
    protected final Protocol protocol;
    protected final IdRewriteFunction idRewriter;
    
    public SoundRewriter(final Protocol protocol) {
        this.protocol = protocol;
        this.idRewriter = SoundRewriter::lambda$new$0;
    }
    
    public SoundRewriter(final Protocol protocol, final IdRewriteFunction idRewriter) {
        this.protocol = protocol;
        this.idRewriter = idRewriter;
    }
    
    public void registerSound(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final SoundRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.handler(this.this$0.getSoundHandler());
            }
        });
    }
    
    public PacketHandler getSoundHandler() {
        return this::lambda$getSoundHandler$1;
    }
    
    private void lambda$getSoundHandler$1(final PacketWrapper packetWrapper) throws Exception {
        final int intValue = (int)packetWrapper.get(Type.VAR_INT, 0);
        final int rewrite = this.idRewriter.rewrite(intValue);
        if (rewrite == -1) {
            packetWrapper.cancel();
        }
        else if (intValue != rewrite) {
            packetWrapper.set(Type.VAR_INT, 0, rewrite);
        }
    }
    
    private static int lambda$new$0(final Protocol protocol, final int n) {
        return protocol.getMappingData().getSoundMappings().getNewId(n);
    }
}
