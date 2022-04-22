package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.*;

public class StatisticsRewriter
{
    private final Protocol protocol;
    private final int customStatsCategory = 8;
    
    public StatisticsRewriter(final Protocol protocol) {
        this.protocol = protocol;
    }
    
    public void register(final ClientboundPacketType clientboundPacketType) {
        this.protocol.registerClientbound(clientboundPacketType, new PacketRemapper() {
            final StatisticsRewriter this$0;
            
            @Override
            public void registerMap() {
                this.handler(this::lambda$registerMap$0);
            }
            
            private void lambda$registerMap$0(final PacketWrapper packetWrapper) throws Exception {
                int intValue;
                final int n = intValue = (int)packetWrapper.passthrough(Type.VAR_INT);
                while (0 < n) {
                    final int intValue2 = (int)packetWrapper.read(Type.VAR_INT);
                    int n2 = (int)packetWrapper.read(Type.VAR_INT);
                    final int intValue3 = (int)packetWrapper.read(Type.VAR_INT);
                    Label_0223: {
                        if (intValue2 == 8 && StatisticsRewriter.access$000(this.this$0).getMappingData().getStatisticsMappings() != null) {
                            n2 = StatisticsRewriter.access$000(this.this$0).getMappingData().getStatisticsMappings().getNewId(n2);
                            if (n2 == -1) {
                                --intValue;
                                break Label_0223;
                            }
                        }
                        else {
                            final RegistryType registryTypeForStatistic = this.this$0.getRegistryTypeForStatistic(intValue2);
                            final IdRewriteFunction rewriter;
                            if (registryTypeForStatistic != null && (rewriter = this.this$0.getRewriter(registryTypeForStatistic)) != null) {
                                n2 = rewriter.rewrite(n2);
                            }
                        }
                        packetWrapper.write(Type.VAR_INT, intValue2);
                        packetWrapper.write(Type.VAR_INT, n2);
                        packetWrapper.write(Type.VAR_INT, intValue3);
                    }
                    int n3 = 0;
                    ++n3;
                }
                if (intValue != n) {
                    packetWrapper.set(Type.VAR_INT, 0, intValue);
                }
            }
        });
    }
    
    protected IdRewriteFunction getRewriter(final RegistryType registryType) {
        switch (registryType) {
            case BLOCK: {
                return (this.protocol.getMappingData().getBlockMappings() != null) ? this::lambda$getRewriter$0 : null;
            }
            case ITEM: {
                return (this.protocol.getMappingData().getItemMappings() != null) ? this::lambda$getRewriter$1 : null;
            }
            case ENTITY: {
                return (this.protocol.getEntityRewriter() != null) ? this::lambda$getRewriter$2 : null;
            }
            default: {
                throw new IllegalArgumentException("Unknown registry type in statistics packet: " + registryType);
            }
        }
    }
    
    public RegistryType getRegistryTypeForStatistic(final int n) {
        switch (n) {
            case 0: {
                return RegistryType.BLOCK;
            }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                return RegistryType.ITEM;
            }
            case 6:
            case 7: {
                return RegistryType.ENTITY;
            }
            default: {
                return null;
            }
        }
    }
    
    private int lambda$getRewriter$2(final int n) {
        return this.protocol.getEntityRewriter().newEntityId(n);
    }
    
    private int lambda$getRewriter$1(final int n) {
        return this.protocol.getMappingData().getNewItemId(n);
    }
    
    private int lambda$getRewriter$0(final int n) {
        return this.protocol.getMappingData().getNewBlockId(n);
    }
    
    static Protocol access$000(final StatisticsRewriter statisticsRewriter) {
        return statisticsRewriter.protocol;
    }
}
