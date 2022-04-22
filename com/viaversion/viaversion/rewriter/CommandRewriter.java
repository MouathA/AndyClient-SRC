package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import java.util.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;

public abstract class CommandRewriter
{
    protected final Protocol protocol;
    protected final Map parserHandlers;
    
    protected CommandRewriter(final Protocol protocol) {
        this.parserHandlers = new HashMap();
        this.protocol = protocol;
        this.parserHandlers.put("brigadier:double", CommandRewriter::lambda$new$0);
        this.parserHandlers.put("brigadier:float", CommandRewriter::lambda$new$1);
        this.parserHandlers.put("brigadier:integer", CommandRewriter::lambda$new$2);
        this.parserHandlers.put("brigadier:long", CommandRewriter::lambda$new$3);
        this.parserHandlers.put("brigadier:string", CommandRewriter::lambda$new$4);
        this.parserHandlers.put("minecraft:entity", CommandRewriter::lambda$new$5);
        this.parserHandlers.put("minecraft:score_holder", CommandRewriter::lambda$new$6);
    }
    
    public void handleArgument(final PacketWrapper packetWrapper, final String s) throws Exception {
        final CommandArgumentConsumer commandArgumentConsumer = this.parserHandlers.get(s);
        if (commandArgumentConsumer != null) {
            commandArgumentConsumer.accept(packetWrapper);
        }
    }
    
    public void registerDeclareCommands(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final CommandRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                while (0 < (int)packetWrapper.passthrough(Type.VAR_INT)) {
                    final byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
                    packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                    if ((byteValue & 0x8) != 0x0) {
                        packetWrapper.passthrough(Type.VAR_INT);
                    }
                    final byte b = (byte)(byteValue & 0x3);
                    if (b == 1 || b == 2) {
                        packetWrapper.passthrough(Type.STRING);
                    }
                    if (b == 2) {
                        final String s = (String)packetWrapper.read(Type.STRING);
                        final String handleArgumentType = this.this$0.handleArgumentType(s);
                        if (handleArgumentType != null) {
                            packetWrapper.write(Type.STRING, handleArgumentType);
                        }
                        this.this$0.handleArgument(packetWrapper, s);
                    }
                    if ((byteValue & 0x10) != 0x0) {
                        packetWrapper.passthrough(Type.STRING);
                    }
                    int n = 0;
                    ++n;
                }
                packetWrapper.passthrough(Type.VAR_INT);
            }
        });
    }
    
    protected String handleArgumentType(final String s) {
        return s;
    }
    
    private static void lambda$new$6(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BYTE);
    }
    
    private static void lambda$new$5(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BYTE);
    }
    
    private static void lambda$new$4(final PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
    }
    
    private static void lambda$new$3(final PacketWrapper packetWrapper) throws Exception {
        final byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
        if ((byteValue & 0x1) != 0x0) {
            packetWrapper.passthrough(Type.LONG);
        }
        if ((byteValue & 0x2) != 0x0) {
            packetWrapper.passthrough(Type.LONG);
        }
    }
    
    private static void lambda$new$2(final PacketWrapper packetWrapper) throws Exception {
        final byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
        if ((byteValue & 0x1) != 0x0) {
            packetWrapper.passthrough(Type.INT);
        }
        if ((byteValue & 0x2) != 0x0) {
            packetWrapper.passthrough(Type.INT);
        }
    }
    
    private static void lambda$new$1(final PacketWrapper packetWrapper) throws Exception {
        final byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
        if ((byteValue & 0x1) != 0x0) {
            packetWrapper.passthrough(Type.FLOAT);
        }
        if ((byteValue & 0x2) != 0x0) {
            packetWrapper.passthrough(Type.FLOAT);
        }
    }
    
    private static void lambda$new$0(final PacketWrapper packetWrapper) throws Exception {
        final byte byteValue = (byte)packetWrapper.passthrough(Type.BYTE);
        if ((byteValue & 0x1) != 0x0) {
            packetWrapper.passthrough(Type.DOUBLE);
        }
        if ((byteValue & 0x2) != 0x0) {
            packetWrapper.passthrough(Type.DOUBLE);
        }
    }
    
    @FunctionalInterface
    public interface CommandArgumentConsumer
    {
        void accept(final PacketWrapper p0) throws Exception;
    }
}
