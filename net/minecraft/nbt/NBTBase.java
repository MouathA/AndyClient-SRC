package net.minecraft.nbt;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;

public abstract class NBTBase
{
    public static final String[] NBT_TYPES;
    private static final String __OBFID;
    private static final String[] lIIIIIIIIlIIlIII;
    private static String[] lIIIIIIIIlIIlIlI;
    
    static {
        lIllllIlIllIlIII();
        lIllllIlIllIIIIl();
        __OBFID = NBTBase.lIIIIIIIIlIIlIII[0];
        NBT_TYPES = new String[] { NBTBase.lIIIIIIIIlIIlIII[1], NBTBase.lIIIIIIIIlIIlIII[2], NBTBase.lIIIIIIIIlIIlIII[3], NBTBase.lIIIIIIIIlIIlIII[4], NBTBase.lIIIIIIIIlIIlIII[5], NBTBase.lIIIIIIIIlIIlIII[6], NBTBase.lIIIIIIIIlIIlIII[7], NBTBase.lIIIIIIIIlIIlIII[8], NBTBase.lIIIIIIIIlIIlIII[9], NBTBase.lIIIIIIIIlIIlIII[10], NBTBase.lIIIIIIIIlIIlIII[11], NBTBase.lIIIIIIIIlIIlIII[12] };
    }
    
    abstract void write(final DataOutput p0) throws IOException;
    
    abstract void read(final DataInput p0, final int p1, final NBTSizeTracker p2) throws IOException;
    
    @Override
    public abstract String toString();
    
    public abstract byte getId();
    
    protected static NBTBase createNewByType(final byte b) {
        switch (b) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte();
            }
            case 2: {
                return new NBTTagShort();
            }
            case 3: {
                return new NBTTagInt();
            }
            case 4: {
                return new NBTTagLong();
            }
            case 5: {
                return new NBTTagFloat();
            }
            case 6: {
                return new NBTTagDouble();
            }
            case 7: {
                return new NBTTagByteArray();
            }
            case 8: {
                return new NBTTagString();
            }
            case 9: {
                return new NBTTagList();
            }
            case 10: {
                return new NBTTagCompound();
            }
            case 11: {
                return new NBTTagIntArray();
            }
            default: {
                return null;
            }
        }
    }
    
    public abstract NBTBase copy();
    
    public boolean hasNoTags() {
        return false;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof NBTBase && this.getId() == ((NBTBase)o).getId();
    }
    
    @Override
    public int hashCode() {
        return this.getId();
    }
    
    protected String getString() {
        return this.toString();
    }
    
    private static void lIllllIlIllIIIIl() {
        (lIIIIIIIIlIIlIII = new String[13])[0] = lIllllIlIlIllIll(NBTBase.lIIIIIIIIlIIlIlI[0], NBTBase.lIIIIIIIIlIIlIlI[1]);
        NBTBase.lIIIIIIIIlIIlIII[1] = lIllllIlIlIllIll(NBTBase.lIIIIIIIIlIIlIlI[2], NBTBase.lIIIIIIIIlIIlIlI[3]);
        NBTBase.lIIIIIIIIlIIlIII[2] = lIllllIlIlIllllI(NBTBase.lIIIIIIIIlIIlIlI[4], NBTBase.lIIIIIIIIlIIlIlI[5]);
        NBTBase.lIIIIIIIIlIIlIII[3] = lIllllIlIlIlllll(NBTBase.lIIIIIIIIlIIlIlI[6], NBTBase.lIIIIIIIIlIIlIlI[7]);
        NBTBase.lIIIIIIIIlIIlIII[4] = lIllllIlIlIlllll(NBTBase.lIIIIIIIIlIIlIlI[8], NBTBase.lIIIIIIIIlIIlIlI[9]);
        NBTBase.lIIIIIIIIlIIlIII[5] = lIllllIlIlIllllI(NBTBase.lIIIIIIIIlIIlIlI[10], NBTBase.lIIIIIIIIlIIlIlI[11]);
        NBTBase.lIIIIIIIIlIIlIII[6] = lIllllIlIlIllIll(NBTBase.lIIIIIIIIlIIlIlI[12], NBTBase.lIIIIIIIIlIIlIlI[13]);
        NBTBase.lIIIIIIIIlIIlIII[7] = lIllllIlIlIlllll(NBTBase.lIIIIIIIIlIIlIlI[14], NBTBase.lIIIIIIIIlIIlIlI[15]);
        NBTBase.lIIIIIIIIlIIlIII[8] = lIllllIlIlIllIll(NBTBase.lIIIIIIIIlIIlIlI[16], NBTBase.lIIIIIIIIlIIlIlI[17]);
        NBTBase.lIIIIIIIIlIIlIII[9] = lIllllIlIlIlllll(NBTBase.lIIIIIIIIlIIlIlI[18], NBTBase.lIIIIIIIIlIIlIlI[19]);
        NBTBase.lIIIIIIIIlIIlIII[10] = lIllllIlIlIlllll(NBTBase.lIIIIIIIIlIIlIlI[20], NBTBase.lIIIIIIIIlIIlIlI[21]);
        NBTBase.lIIIIIIIIlIIlIII[11] = lIllllIlIlIllIll(NBTBase.lIIIIIIIIlIIlIlI[22], NBTBase.lIIIIIIIIlIIlIlI[23]);
        NBTBase.lIIIIIIIIlIIlIII[12] = lIllllIlIllIIIII(NBTBase.lIIIIIIIIlIIlIlI[24], NBTBase.lIIIIIIIIlIIlIlI[25]);
        NBTBase.lIIIIIIIIlIIlIlI = null;
    }
    
    private static void lIllllIlIllIlIII() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        NBTBase.lIIIIIIIIlIIlIlI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIllllIlIlIlllll(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lIllllIlIlIllllI(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIllllIlIlIllIll(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIllllIlIllIIIII(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public abstract static class NBTPrimitive extends NBTBase
    {
        private static final String __OBFID;
        
        public abstract long getLong();
        
        public abstract int getInt();
        
        public abstract short getShort();
        
        public abstract byte getByte();
        
        public abstract double getDouble();
        
        public abstract float getFloat();
        
        static {
            __OBFID = "CL_00001230";
        }
    }
}
