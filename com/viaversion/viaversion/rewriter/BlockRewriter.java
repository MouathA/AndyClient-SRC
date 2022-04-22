package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.*;

public class BlockRewriter
{
    private final Protocol protocol;
    private final Type positionType;
    
    public BlockRewriter(final Protocol protocol, final Type positionType) {
        this.protocol = protocol;
        this.positionType = positionType;
    }
    
    public void registerBlockAction(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final BlockRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(BlockRewriter.access$000(this.this$0));
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final int newBlockId = BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockId((int)packetWrapper.get(Type.VAR_INT, 0));
                if (newBlockId == -1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.VAR_INT, 0, newBlockId);
            }
        });
    }
    
    public void registerBlockChange(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final BlockRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(BlockRewriter.access$000(this.this$0));
                this.map(Type.VAR_INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.VAR_INT, 0, BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId((int)packetWrapper.get(Type.VAR_INT, 0)));
            }
        });
    }
    
    public void registerMultiBlockChange(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final BlockRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.passthrough(Type.BLOCK_CHANGE_RECORD_ARRAY);
                while (0 < array.length) {
                    final BlockChangeRecord blockChangeRecord = array[0];
                    blockChangeRecord.setBlockId(BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                    int n = 0;
                    ++n;
                }
            }
        });
    }
    
    public void registerVarLongMultiBlockChange(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final BlockRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.LONG);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                final BlockChangeRecord[] array = (BlockChangeRecord[])packetWrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                while (0 < array.length) {
                    final BlockChangeRecord blockChangeRecord = array[0];
                    blockChangeRecord.setBlockId(BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                    int n = 0;
                    ++n;
                }
            }
        });
    }
    
    public void registerAcknowledgePlayerDigging(final ClientboundPacketType clientboundPacketType) {
        this.registerBlockChange(clientboundPacketType);
    }
    
    public void registerEffect(final ClientboundPacketType clientboundPacketType, final int n, final int n2) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper(n, n2) {
            final int val$playRecordId;
            final int val$blockBreakId;
            final BlockRewriter this$0;
            
            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(BlockRewriter.access$000(this.this$0));
                this.map(Type.INT);
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final int n, final int n2, final PacketWrapper packetWrapper) throws Exception {
                final int intValue = (int)packetWrapper.get(Type.INT, 0);
                final int intValue2 = (int)packetWrapper.get(Type.INT, 1);
                if (intValue == n) {
                    packetWrapper.set(Type.INT, 1, BlockRewriter.access$100(this.this$0).getMappingData().getNewItemId(intValue2));
                }
                else if (intValue == n2) {
                    packetWrapper.set(Type.INT, 1, BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(intValue2));
                }
            }
        });
    }
    
    static Type access$000(final BlockRewriter blockRewriter) {
        return blockRewriter.positionType;
    }
    
    static Protocol access$100(final BlockRewriter blockRewriter) {
        return blockRewriter.protocol;
    }
}
