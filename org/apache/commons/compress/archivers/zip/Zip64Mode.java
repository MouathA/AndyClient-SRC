package org.apache.commons.compress.archivers.zip;

public enum Zip64Mode
{
    Always("Always", 0), 
    Never("Never", 1), 
    AsNeeded("AsNeeded", 2);
    
    private static final Zip64Mode[] $VALUES;
    
    private Zip64Mode(final String s, final int n) {
    }
    
    static {
        $VALUES = new Zip64Mode[] { Zip64Mode.Always, Zip64Mode.Never, Zip64Mode.AsNeeded };
    }
}
