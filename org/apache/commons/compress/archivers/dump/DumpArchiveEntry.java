package org.apache.commons.compress.archivers.dump;

import org.apache.commons.compress.archivers.*;
import java.util.*;

public class DumpArchiveEntry implements ArchiveEntry
{
    private String name;
    private TYPE type;
    private int mode;
    private Set permissions;
    private long size;
    private long atime;
    private long mtime;
    private int uid;
    private int gid;
    private final DumpArchiveSummary summary;
    private final TapeSegmentHeader header;
    private String simpleName;
    private String originalName;
    private int volume;
    private long offset;
    private int ino;
    private int nlink;
    private long ctime;
    private int generation;
    private boolean isDeleted;
    
    public DumpArchiveEntry() {
        this.type = TYPE.UNKNOWN;
        this.permissions = Collections.emptySet();
        this.summary = null;
        this.header = new TapeSegmentHeader();
    }
    
    public DumpArchiveEntry(final String name, final String simpleName) {
        this.type = TYPE.UNKNOWN;
        this.permissions = Collections.emptySet();
        this.summary = null;
        this.header = new TapeSegmentHeader();
        this.setName(name);
        this.simpleName = simpleName;
    }
    
    protected DumpArchiveEntry(final String name, final String simpleName, final int ino, final TYPE type) {
        this.type = TYPE.UNKNOWN;
        this.permissions = Collections.emptySet();
        this.summary = null;
        this.header = new TapeSegmentHeader();
        this.setType(type);
        this.setName(name);
        this.simpleName = simpleName;
        this.ino = ino;
        this.offset = 0L;
    }
    
    public String getSimpleName() {
        return this.simpleName;
    }
    
    protected void setSimpleName(final String simpleName) {
        this.simpleName = simpleName;
    }
    
    public int getIno() {
        return this.header.getIno();
    }
    
    public int getNlink() {
        return this.nlink;
    }
    
    public void setNlink(final int nlink) {
        this.nlink = nlink;
    }
    
    public Date getCreationTime() {
        return new Date(this.ctime);
    }
    
    public void setCreationTime(final Date date) {
        this.ctime = date.getTime();
    }
    
    public int getGeneration() {
        return this.generation;
    }
    
    public void setGeneration(final int generation) {
        this.generation = generation;
    }
    
    public boolean isDeleted() {
        return this.isDeleted;
    }
    
    public void setDeleted(final boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public long getOffset() {
        return this.offset;
    }
    
    public void setOffset(final long offset) {
        this.offset = offset;
    }
    
    public int getVolume() {
        return this.volume;
    }
    
    public void setVolume(final int volume) {
        this.volume = volume;
    }
    
    public DumpArchiveConstants.SEGMENT_TYPE getHeaderType() {
        return this.header.getType();
    }
    
    public int getHeaderCount() {
        return this.header.getCount();
    }
    
    public int getHeaderHoles() {
        return this.header.getHoles();
    }
    
    public boolean isSparseRecord(final int n) {
        return (this.header.getCdata(n) & 0x1) == 0x0;
    }
    
    @Override
    public int hashCode() {
        return this.ino;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !o.getClass().equals(this.getClass())) {
            return false;
        }
        final DumpArchiveEntry dumpArchiveEntry = (DumpArchiveEntry)o;
        return this.header != null && dumpArchiveEntry.header != null && this.ino == dumpArchiveEntry.ino && (this.summary != null || dumpArchiveEntry.summary == null) && (this.summary == null || this.summary.equals(dumpArchiveEntry.summary));
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    static DumpArchiveEntry parse(final byte[] array) {
        final DumpArchiveEntry dumpArchiveEntry = new DumpArchiveEntry();
        final TapeSegmentHeader header = dumpArchiveEntry.header;
        TapeSegmentHeader.access$002(header, DumpArchiveConstants.SEGMENT_TYPE.find(DumpArchiveUtil.convert32(array, 0)));
        TapeSegmentHeader.access$102(header, DumpArchiveUtil.convert32(array, 12));
        dumpArchiveEntry.ino = TapeSegmentHeader.access$202(header, DumpArchiveUtil.convert32(array, 20));
        final int convert16 = DumpArchiveUtil.convert16(array, 32);
        dumpArchiveEntry.setType(TYPE.find(convert16 >> 12 & 0xF));
        dumpArchiveEntry.setMode(convert16);
        dumpArchiveEntry.nlink = DumpArchiveUtil.convert16(array, 34);
        dumpArchiveEntry.setSize(DumpArchiveUtil.convert64(array, 40));
        dumpArchiveEntry.setAccessTime(new Date(1000L * DumpArchiveUtil.convert32(array, 48) + DumpArchiveUtil.convert32(array, 52) / 1000));
        dumpArchiveEntry.setLastModifiedDate(new Date(1000L * DumpArchiveUtil.convert32(array, 56) + DumpArchiveUtil.convert32(array, 60) / 1000));
        dumpArchiveEntry.ctime = 1000L * DumpArchiveUtil.convert32(array, 64) + DumpArchiveUtil.convert32(array, 68) / 1000;
        dumpArchiveEntry.generation = DumpArchiveUtil.convert32(array, 140);
        dumpArchiveEntry.setUserId(DumpArchiveUtil.convert32(array, 144));
        dumpArchiveEntry.setGroupId(DumpArchiveUtil.convert32(array, 148));
        TapeSegmentHeader.access$302(header, DumpArchiveUtil.convert32(array, 160));
        TapeSegmentHeader.access$402(header, 0);
        while (0 < TapeSegmentHeader.access$300(header)) {
            if (array[164] == 0) {
                TapeSegmentHeader.access$408(header);
            }
            int n = 0;
            ++n;
        }
        System.arraycopy(array, 164, TapeSegmentHeader.access$500(header), 0, 512);
        dumpArchiveEntry.volume = header.getVolume();
        return dumpArchiveEntry;
    }
    
    void update(final byte[] array) {
        TapeSegmentHeader.access$102(this.header, DumpArchiveUtil.convert32(array, 16));
        TapeSegmentHeader.access$302(this.header, DumpArchiveUtil.convert32(array, 160));
        TapeSegmentHeader.access$402(this.header, 0);
        while (0 < TapeSegmentHeader.access$300(this.header)) {
            if (array[164] == 0) {
                TapeSegmentHeader.access$408(this.header);
            }
            int n = 0;
            ++n;
        }
        System.arraycopy(array, 164, TapeSegmentHeader.access$500(this.header), 0, 512);
    }
    
    public String getName() {
        return this.name;
    }
    
    String getOriginalName() {
        return this.originalName;
    }
    
    public final void setName(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: putfield        org/apache/commons/compress/archivers/dump/DumpArchiveEntry.originalName:Ljava/lang/String;
        //     5: aload_1        
        //     6: ifnull          58
        //     9: aload_0        
        //    10: if_acmpne       42
        //    13: aload_1        
        //    14: ldc             "/"
        //    16: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //    19: ifne            42
        //    22: new             Ljava/lang/StringBuilder;
        //    25: dup            
        //    26: invokespecial   java/lang/StringBuilder.<init>:()V
        //    29: aload_1        
        //    30: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    33: ldc             "/"
        //    35: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    38: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    41: astore_1       
        //    42: aload_1        
        //    43: ldc_w           "./"
        //    46: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //    49: ifeq            58
        //    52: aload_1        
        //    53: iconst_2       
        //    54: invokevirtual   java/lang/String.substring:(I)Ljava/lang/String;
        //    57: astore_1       
        //    58: aload_0        
        //    59: aload_1        
        //    60: putfield        org/apache/commons/compress/archivers/dump/DumpArchiveEntry.name:Ljava/lang/String;
        //    63: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Date getLastModifiedDate() {
        return new Date(this.mtime);
    }
    
    public boolean isFile() {
        return this.type == TYPE.FILE;
    }
    
    public boolean isSocket() {
        return this.type == TYPE.SOCKET;
    }
    
    public boolean isChrDev() {
        return this.type == TYPE.CHRDEV;
    }
    
    public boolean isBlkDev() {
        return this.type == TYPE.BLKDEV;
    }
    
    public boolean isFifo() {
        return this.type == TYPE.FIFO;
    }
    
    public TYPE getType() {
        return this.type;
    }
    
    public void setType(final TYPE type) {
        this.type = type;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public void setMode(final int n) {
        this.mode = (n & 0xFFF);
        this.permissions = PERMISSION.find(n);
    }
    
    public Set getPermissions() {
        return this.permissions;
    }
    
    public long getSize() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       10
        //     4: ldc2_w          -1
        //     7: goto            14
        //    10: aload_0        
        //    11: getfield        org/apache/commons/compress/archivers/dump/DumpArchiveEntry.size:J
        //    14: lreturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    long getEntrySize() {
        return this.size;
    }
    
    public void setSize(final long size) {
        this.size = size;
    }
    
    public void setLastModifiedDate(final Date date) {
        this.mtime = date.getTime();
    }
    
    public Date getAccessTime() {
        return new Date(this.atime);
    }
    
    public void setAccessTime(final Date date) {
        this.atime = date.getTime();
    }
    
    public int getUserId() {
        return this.uid;
    }
    
    public void setUserId(final int uid) {
        this.uid = uid;
    }
    
    public int getGroupId() {
        return this.gid;
    }
    
    public void setGroupId(final int gid) {
        this.gid = gid;
    }
    
    public enum PERMISSION
    {
        SETUID("SETUID", 0, 2048), 
        SETGUI("SETGUI", 1, 1024), 
        STICKY("STICKY", 2, 512), 
        USER_READ("USER_READ", 3, 256), 
        USER_WRITE("USER_WRITE", 4, 128), 
        USER_EXEC("USER_EXEC", 5, 64), 
        GROUP_READ("GROUP_READ", 6, 32), 
        GROUP_WRITE("GROUP_WRITE", 7, 16), 
        GROUP_EXEC("GROUP_EXEC", 8, 8), 
        WORLD_READ("WORLD_READ", 9, 4), 
        WORLD_WRITE("WORLD_WRITE", 10, 2), 
        WORLD_EXEC("WORLD_EXEC", 11, 1);
        
        private int code;
        private static final PERMISSION[] $VALUES;
        
        private PERMISSION(final String s, final int n, final int code) {
            this.code = code;
        }
        
        public static Set find(final int n) {
            final HashSet<Enum> set = new HashSet<Enum>();
            final PERMISSION[] values = values();
            while (0 < values.length) {
                final PERMISSION permission = values[0];
                if ((n & permission.code) == permission.code) {
                    set.add(permission);
                }
                int n2 = 0;
                ++n2;
            }
            if (set.isEmpty()) {
                return Collections.emptySet();
            }
            return EnumSet.copyOf(set);
        }
        
        static {
            $VALUES = new PERMISSION[] { PERMISSION.SETUID, PERMISSION.SETGUI, PERMISSION.STICKY, PERMISSION.USER_READ, PERMISSION.USER_WRITE, PERMISSION.USER_EXEC, PERMISSION.GROUP_READ, PERMISSION.GROUP_WRITE, PERMISSION.GROUP_EXEC, PERMISSION.WORLD_READ, PERMISSION.WORLD_WRITE, PERMISSION.WORLD_EXEC };
        }
    }
    
    public enum TYPE
    {
        WHITEOUT("WHITEOUT", 0, 14), 
        SOCKET("SOCKET", 1, 12), 
        LINK("LINK", 2, 10), 
        FILE("FILE", 3, 8), 
        BLKDEV("BLKDEV", 4, 6), 
        DIRECTORY("DIRECTORY", 5, 4), 
        CHRDEV("CHRDEV", 6, 2), 
        FIFO("FIFO", 7, 1), 
        UNKNOWN("UNKNOWN", 8, 15);
        
        private int code;
        private static final TYPE[] $VALUES;
        
        private TYPE(final String s, final int n, final int code) {
            this.code = code;
        }
        
        public static TYPE find(final int n) {
            TYPE unknown = TYPE.UNKNOWN;
            final TYPE[] values = values();
            while (0 < values.length) {
                final TYPE type = values[0];
                if (n == type.code) {
                    unknown = type;
                }
                int n2 = 0;
                ++n2;
            }
            return unknown;
        }
        
        static {
            $VALUES = new TYPE[] { TYPE.WHITEOUT, TYPE.SOCKET, TYPE.LINK, TYPE.FILE, TYPE.BLKDEV, TYPE.DIRECTORY, TYPE.CHRDEV, TYPE.FIFO, TYPE.UNKNOWN };
        }
    }
    
    static class TapeSegmentHeader
    {
        private DumpArchiveConstants.SEGMENT_TYPE type;
        private int volume;
        private int ino;
        private int count;
        private int holes;
        private final byte[] cdata;
        
        TapeSegmentHeader() {
            this.cdata = new byte[512];
        }
        
        public DumpArchiveConstants.SEGMENT_TYPE getType() {
            return this.type;
        }
        
        public int getVolume() {
            return this.volume;
        }
        
        public int getIno() {
            return this.ino;
        }
        
        void setIno(final int ino) {
            this.ino = ino;
        }
        
        public int getCount() {
            return this.count;
        }
        
        public int getHoles() {
            return this.holes;
        }
        
        public int getCdata(final int n) {
            return this.cdata[n];
        }
        
        static DumpArchiveConstants.SEGMENT_TYPE access$002(final TapeSegmentHeader tapeSegmentHeader, final DumpArchiveConstants.SEGMENT_TYPE type) {
            return tapeSegmentHeader.type = type;
        }
        
        static int access$102(final TapeSegmentHeader tapeSegmentHeader, final int volume) {
            return tapeSegmentHeader.volume = volume;
        }
        
        static int access$202(final TapeSegmentHeader tapeSegmentHeader, final int ino) {
            return tapeSegmentHeader.ino = ino;
        }
        
        static int access$302(final TapeSegmentHeader tapeSegmentHeader, final int count) {
            return tapeSegmentHeader.count = count;
        }
        
        static int access$402(final TapeSegmentHeader tapeSegmentHeader, final int holes) {
            return tapeSegmentHeader.holes = holes;
        }
        
        static int access$300(final TapeSegmentHeader tapeSegmentHeader) {
            return tapeSegmentHeader.count;
        }
        
        static int access$408(final TapeSegmentHeader tapeSegmentHeader) {
            return tapeSegmentHeader.holes++;
        }
        
        static byte[] access$500(final TapeSegmentHeader tapeSegmentHeader) {
            return tapeSegmentHeader.cdata;
        }
    }
}
