package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;

public final class MapColorRewriter
{
    public static PacketHandler getRewriteHandler(final IdRewriteFunction idRewriteFunction) {
        return MapColorRewriter::lambda$getRewriteHandler$0;
    }
    
    private static void lambda$getRewriteHandler$0(final IdRewriteFunction idRewriteFunction, final PacketWrapper packetWrapper) throws Exception {
        while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
            packetWrapper.passthrough(Type.VAR_INT);
            packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.BYTE);
            if (packetWrapper.passthrough(Type.BOOLEAN)) {
                packetWrapper.passthrough(Type.COMPONENT);
            }
            int shortValue = 0;
            ++shortValue;
        }
        int shortValue = (short)packetWrapper.passthrough(Type.UNSIGNED_BYTE);
    }
}
