package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.packet.*;

public interface SimpleProtocol extends Protocol
{
    public enum DummyPacketTypes implements ClientboundPacketType, ServerboundPacketType
    {
        private static final DummyPacketTypes[] $VALUES;
        
        private DummyPacketTypes(final String s, final int n) {
        }
        
        @Override
        public int getId() {
            return this.ordinal();
        }
        
        @Override
        public String getName() {
            return this.name();
        }
        
        @Override
        public Direction direction() {
            throw new UnsupportedOperationException();
        }
        
        static {
            $VALUES = new DummyPacketTypes[0];
        }
    }
}
