package org.apache.commons.compress.archivers.zip;

import java.io.*;
import java.math.*;
import java.util.zip.*;

public class X7875_NewUnix implements ZipExtraField, Cloneable, Serializable
{
    private static final ZipShort HEADER_ID;
    private static final BigInteger ONE_THOUSAND;
    private static final long serialVersionUID = 1L;
    private int version;
    private BigInteger uid;
    private BigInteger gid;
    
    public X7875_NewUnix() {
        this.version = 1;
        this.reset();
    }
    
    public ZipShort getHeaderId() {
        return X7875_NewUnix.HEADER_ID;
    }
    
    public long getUID() {
        return ZipUtil.bigToLong(this.uid);
    }
    
    public long getGID() {
        return ZipUtil.bigToLong(this.gid);
    }
    
    public void setUID(final long n) {
        this.uid = ZipUtil.longToBig(n);
    }
    
    public void setGID(final long n) {
        this.gid = ZipUtil.longToBig(n);
    }
    
    public ZipShort getLocalFileDataLength() {
        return new ZipShort(3 + trimLeadingZeroesForceMinLength(this.uid.toByteArray()).length + trimLeadingZeroesForceMinLength(this.gid.toByteArray()).length);
    }
    
    public ZipShort getCentralDirectoryLength() {
        return this.getLocalFileDataLength();
    }
    
    public byte[] getLocalFileDataData() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        org/apache/commons/compress/archivers/zip/X7875_NewUnix.uid:Ljava/math/BigInteger;
        //     4: invokevirtual   java/math/BigInteger.toByteArray:()[B
        //     7: astore_1       
        //     8: aload_0        
        //     9: getfield        org/apache/commons/compress/archivers/zip/X7875_NewUnix.gid:Ljava/math/BigInteger;
        //    12: invokevirtual   java/math/BigInteger.toByteArray:()[B
        //    15: astore_2       
        //    16: aload_1        
        //    17: invokestatic    org/apache/commons/compress/archivers/zip/X7875_NewUnix.trimLeadingZeroesForceMinLength:([B)[B
        //    20: astore_1       
        //    21: aload_2        
        //    22: invokestatic    org/apache/commons/compress/archivers/zip/X7875_NewUnix.trimLeadingZeroesForceMinLength:([B)[B
        //    25: astore_2       
        //    26: iconst_3       
        //    27: aload_1        
        //    28: arraylength    
        //    29: iadd           
        //    30: aload_2        
        //    31: arraylength    
        //    32: iadd           
        //    33: newarray        B
        //    35: astore_3       
        //    36: aload_1        
        //    37: invokestatic    org/apache/commons/compress/archivers/zip/ZipUtil.reverse:([B)[B
        //    40: pop            
        //    41: aload_2        
        //    42: invokestatic    org/apache/commons/compress/archivers/zip/ZipUtil.reverse:([B)[B
        //    45: pop            
        //    46: aload_3        
        //    47: iconst_0       
        //    48: iinc            4, 1
        //    51: aload_0        
        //    52: getfield        org/apache/commons/compress/archivers/zip/X7875_NewUnix.version:I
        //    55: invokestatic    org/apache/commons/compress/archivers/zip/ZipUtil.unsignedIntToSignedByte:(I)B
        //    58: bastore        
        //    59: aload_3        
        //    60: iconst_0       
        //    61: iinc            4, 1
        //    64: aload_1        
        //    65: arraylength    
        //    66: invokestatic    org/apache/commons/compress/archivers/zip/ZipUtil.unsignedIntToSignedByte:(I)B
        //    69: bastore        
        //    70: aload_1        
        //    71: iconst_0       
        //    72: aload_3        
        //    73: iconst_0       
        //    74: aload_1        
        //    75: arraylength    
        //    76: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //    79: iconst_0       
        //    80: aload_1        
        //    81: arraylength    
        //    82: iadd           
        //    83: istore          4
        //    85: aload_3        
        //    86: iconst_0       
        //    87: iinc            4, 1
        //    90: aload_2        
        //    91: arraylength    
        //    92: invokestatic    org/apache/commons/compress/archivers/zip/ZipUtil.unsignedIntToSignedByte:(I)B
        //    95: bastore        
        //    96: aload_2        
        //    97: iconst_0       
        //    98: aload_3        
        //    99: iconst_0       
        //   100: aload_2        
        //   101: arraylength    
        //   102: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   105: aload_3        
        //   106: areturn        
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public byte[] getCentralDirectoryData() {
        return this.getLocalFileDataData();
    }
    
    public void parseFromLocalFileData(final byte[] array, int n, final int n2) throws ZipException {
        this.reset();
        this.version = ZipUtil.signedByteToUnsignedInt(array[n++]);
        final int signedByteToUnsignedInt = ZipUtil.signedByteToUnsignedInt(array[n++]);
        final byte[] array2 = new byte[signedByteToUnsignedInt];
        System.arraycopy(array, n, array2, 0, signedByteToUnsignedInt);
        n += signedByteToUnsignedInt;
        this.uid = new BigInteger(1, ZipUtil.reverse(array2));
        final int signedByteToUnsignedInt2 = ZipUtil.signedByteToUnsignedInt(array[n++]);
        final byte[] array3 = new byte[signedByteToUnsignedInt2];
        System.arraycopy(array, n, array3, 0, signedByteToUnsignedInt2);
        this.gid = new BigInteger(1, ZipUtil.reverse(array3));
    }
    
    public void parseFromCentralDirectoryData(final byte[] array, final int n, final int n2) throws ZipException {
        this.reset();
        this.parseFromLocalFileData(array, n, n2);
    }
    
    private void reset() {
        this.uid = X7875_NewUnix.ONE_THOUSAND;
        this.gid = X7875_NewUnix.ONE_THOUSAND;
    }
    
    @Override
    public String toString() {
        return "0x7875 Zip Extra Field: UID=" + this.uid + " GID=" + this.gid;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof X7875_NewUnix) {
            final X7875_NewUnix x7875_NewUnix = (X7875_NewUnix)o;
            return this.version == x7875_NewUnix.version && this.uid.equals(x7875_NewUnix.uid) && this.gid.equals(x7875_NewUnix.gid);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return -1234567 * this.version ^ Integer.rotateLeft(this.uid.hashCode(), 16) ^ this.gid.hashCode();
    }
    
    static byte[] trimLeadingZeroesForceMinLength(final byte[] array) {
        if (array == null) {
            return array;
        }
        while (0 < array.length && array[0] == 0) {
            int n = 0;
            ++n;
            int n2 = 0;
            ++n2;
        }
        final byte[] array2 = new byte[Math.max(1, array.length - 0)];
        int n2 = array2.length - (array.length - 0);
        System.arraycopy(array, 0, array2, 0, array2.length - 0);
        return array2;
    }
    
    static {
        HEADER_ID = new ZipShort(30837);
        ONE_THOUSAND = BigInteger.valueOf(1000L);
    }
}
