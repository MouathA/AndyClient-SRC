package org.apache.commons.compress.compressors.pack200;

import java.io.*;

public enum Pack200Strategy
{
    IN_MEMORY {
        @Override
        StreamBridge newStreamBridge() {
            return new InMemoryCachingStreamBridge();
        }
    }, 
    TEMP_FILE {
        @Override
        StreamBridge newStreamBridge() throws IOException {
            return new TempFileCachingStreamBridge();
        }
    };
    
    private static final Pack200Strategy[] $VALUES;
    
    private Pack200Strategy(final String s, final int n) {
    }
    
    abstract StreamBridge newStreamBridge() throws IOException;
    
    Pack200Strategy(final String s, final int n, final Pack200Strategy$1 pack200Strategy) {
        this(s, n);
    }
    
    static {
        $VALUES = new Pack200Strategy[] { Pack200Strategy.IN_MEMORY, Pack200Strategy.TEMP_FILE };
    }
}
