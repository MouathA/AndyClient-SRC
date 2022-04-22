package org.apache.commons.compress.archivers.dump;

public final class DumpArchiveConstants
{
    public static final int TP_SIZE = 1024;
    public static final int NTREC = 10;
    public static final int HIGH_DENSITY_NTREC = 32;
    public static final int OFS_MAGIC = 60011;
    public static final int NFS_MAGIC = 60012;
    public static final int FS_UFS2_MAGIC = 424935705;
    public static final int CHECKSUM = 84446;
    public static final int LBLSIZE = 16;
    public static final int NAMELEN = 64;
    
    private DumpArchiveConstants() {
    }
    
    public enum COMPRESSION_TYPE
    {
        ZLIB("ZLIB", 0, 0), 
        BZLIB("BZLIB", 1, 1), 
        LZO("LZO", 2, 2);
        
        int code;
        private static final COMPRESSION_TYPE[] $VALUES;
        
        private COMPRESSION_TYPE(final String s, final int n, final int code) {
            this.code = code;
        }
        
        public static COMPRESSION_TYPE find(final int n) {
            final COMPRESSION_TYPE[] values = values();
            while (0 < values.length) {
                final COMPRESSION_TYPE compression_TYPE = values[0];
                if (compression_TYPE.code == n) {
                    return compression_TYPE;
                }
                int n2 = 0;
                ++n2;
            }
            return null;
        }
        
        static {
            $VALUES = new COMPRESSION_TYPE[] { COMPRESSION_TYPE.ZLIB, COMPRESSION_TYPE.BZLIB, COMPRESSION_TYPE.LZO };
        }
    }
    
    public enum SEGMENT_TYPE
    {
        TAPE("TAPE", 0, 1), 
        INODE("INODE", 1, 2), 
        BITS("BITS", 2, 3), 
        ADDR("ADDR", 3, 4), 
        END("END", 4, 5), 
        CLRI("CLRI", 5, 6);
        
        int code;
        private static final SEGMENT_TYPE[] $VALUES;
        
        private SEGMENT_TYPE(final String s, final int n, final int code) {
            this.code = code;
        }
        
        public static SEGMENT_TYPE find(final int n) {
            final SEGMENT_TYPE[] values = values();
            while (0 < values.length) {
                final SEGMENT_TYPE segment_TYPE = values[0];
                if (segment_TYPE.code == n) {
                    return segment_TYPE;
                }
                int n2 = 0;
                ++n2;
            }
            return null;
        }
        
        static {
            $VALUES = new SEGMENT_TYPE[] { SEGMENT_TYPE.TAPE, SEGMENT_TYPE.INODE, SEGMENT_TYPE.BITS, SEGMENT_TYPE.ADDR, SEGMENT_TYPE.END, SEGMENT_TYPE.CLRI };
        }
    }
}
