package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class ParticleMapping
{
    private static final ParticleData[] particles;
    
    public static ParticleData getMapping(final int n) {
        return ParticleMapping.particles[n];
    }
    
    private static ParticleData rewrite(final int n) {
        return new ParticleData(n, null);
    }
    
    private static ParticleData rewrite(final int n, final ParticleHandler particleHandler) {
        return new ParticleData(n, particleHandler, null);
    }
    
    static {
        final ParticleHandler particleHandler = new ParticleHandler() {
            @Override
            public int[] rewrite(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final PacketWrapper packetWrapper) throws Exception {
                return this.rewrite((int)packetWrapper.read(Type.VAR_INT));
            }
            
            @Override
            public int[] rewrite(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final List list) {
                return this.rewrite((int)list.get(0).getValue());
            }
            
            private int[] rewrite(final int n) {
                final int newBlockStateId = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(n);
                return new int[] { (newBlockStateId >> 4) + ((newBlockStateId & 0xF) << 12) };
            }
            
            @Override
            public boolean isBlockHandler() {
                return true;
            }
        };
        particles = new ParticleData[] { rewrite(16), rewrite(20), rewrite(35), rewrite(37, particleHandler), rewrite(4), rewrite(29), rewrite(9), rewrite(44), rewrite(42), rewrite(19), rewrite(18), rewrite(30, new ParticleHandler() {
                @Override
                public int[] rewrite(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final PacketWrapper packetWrapper) throws Exception {
                    final float floatValue = (float)packetWrapper.read(Type.FLOAT);
                    final float floatValue2 = (float)packetWrapper.read(Type.FLOAT);
                    final float floatValue3 = (float)packetWrapper.read(Type.FLOAT);
                    final float floatValue4 = (float)packetWrapper.read(Type.FLOAT);
                    packetWrapper.set(Type.FLOAT, 3, floatValue);
                    packetWrapper.set(Type.FLOAT, 4, floatValue2);
                    packetWrapper.set(Type.FLOAT, 5, floatValue3);
                    packetWrapper.set(Type.FLOAT, 6, floatValue4);
                    packetWrapper.set(Type.INT, 1, 0);
                    return null;
                }
                
                @Override
                public int[] rewrite(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final List list) {
                    return null;
                }
            }), rewrite(13), rewrite(41), rewrite(10), rewrite(25), rewrite(43), rewrite(15), rewrite(2), rewrite(1), rewrite(46, particleHandler), rewrite(3), rewrite(6), rewrite(26), rewrite(21), rewrite(34), rewrite(14), rewrite(36, new ParticleHandler() {
                @Override
                public int[] rewrite(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final PacketWrapper packetWrapper) throws Exception {
                    return this.rewrite(protocol1_12_2To1_13, (Item)packetWrapper.read(Type.FLAT_ITEM));
                }
                
                @Override
                public int[] rewrite(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final List list) {
                    return this.rewrite(protocol1_12_2To1_13, (Item)list.get(0).getValue());
                }
                
                private int[] rewrite(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final Item item) {
                    final Item handleItemToClient = protocol1_12_2To1_13.getItemRewriter().handleItemToClient(item);
                    return new int[] { handleItemToClient.identifier(), handleItemToClient.data() };
                }
            }), rewrite(33), rewrite(31), rewrite(12), rewrite(27), rewrite(22), rewrite(23), rewrite(0), rewrite(24), rewrite(39), rewrite(11), rewrite(48), rewrite(12), rewrite(45), rewrite(47), rewrite(7), rewrite(5), rewrite(17), rewrite(4), rewrite(4), rewrite(4), rewrite(18), rewrite(18) };
    }
    
    public static final class ParticleData
    {
        private final int historyId;
        private final ParticleHandler handler;
        
        private ParticleData(final int historyId, final ParticleHandler handler) {
            this.historyId = historyId;
            this.handler = handler;
        }
        
        private ParticleData(final int n) {
            this(n, (ParticleHandler)null);
        }
        
        public int[] rewriteData(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final PacketWrapper packetWrapper) throws Exception {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol1_12_2To1_13, packetWrapper);
        }
        
        public int[] rewriteMeta(final Protocol1_12_2To1_13 protocol1_12_2To1_13, final List list) {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol1_12_2To1_13, list);
        }
        
        public int getHistoryId() {
            return this.historyId;
        }
        
        public ParticleHandler getHandler() {
            return this.handler;
        }
        
        ParticleData(final int n, final ParticleMapping$1 particleHandler) {
            this(n);
        }
        
        ParticleData(final int n, final ParticleHandler particleHandler, final ParticleMapping$1 particleHandler2) {
            this(n, particleHandler);
        }
    }
    
    public interface ParticleHandler
    {
        int[] rewrite(final Protocol1_12_2To1_13 p0, final PacketWrapper p1) throws Exception;
        
        int[] rewrite(final Protocol1_12_2To1_13 p0, final List p1);
        
        default boolean isBlockHandler() {
            return false;
        }
    }
}
