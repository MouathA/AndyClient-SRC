package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.*;

interface BinaryTagScope extends AutoCloseable
{
    void close() throws IOException;
    
    public static class NoOp implements BinaryTagScope
    {
        static final NoOp INSTANCE;
        
        private NoOp() {
        }
        
        @Override
        public void close() {
        }
        
        static {
            INSTANCE = new NoOp();
        }
    }
}
