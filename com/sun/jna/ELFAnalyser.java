package com.sun.jna;

import java.io.*;
import java.util.*;
import java.nio.*;

class ELFAnalyser
{
    private static final byte[] ELF_MAGIC;
    private static final int EF_ARM_ABI_FLOAT_HARD = 1024;
    private static final int EF_ARM_ABI_FLOAT_SOFT = 512;
    private static final int EI_DATA_BIG_ENDIAN = 2;
    private static final int E_MACHINE_ARM = 40;
    private static final int EI_CLASS_64BIT = 2;
    private final String filename;
    private boolean ELF;
    private boolean _64Bit;
    private boolean bigEndian;
    private boolean armHardFloat;
    private boolean armSoftFloat;
    private boolean arm;
    
    public static ELFAnalyser analyse(final String s) throws IOException {
        final ELFAnalyser elfAnalyser = new ELFAnalyser(s);
        elfAnalyser.runDetection();
        return elfAnalyser;
    }
    
    public boolean isELF() {
        return this.ELF;
    }
    
    public boolean is64Bit() {
        return this._64Bit;
    }
    
    public boolean isBigEndian() {
        return this.bigEndian;
    }
    
    public String getFilename() {
        return this.filename;
    }
    
    public boolean isArmHardFloat() {
        return this.armHardFloat;
    }
    
    public boolean isArmSoftFloat() {
        return this.armSoftFloat;
    }
    
    public boolean isArm() {
        return this.arm;
    }
    
    private ELFAnalyser(final String filename) {
        this.ELF = false;
        this._64Bit = false;
        this.bigEndian = false;
        this.armHardFloat = false;
        this.armSoftFloat = false;
        this.arm = false;
        this.filename = filename;
    }
    
    private void runDetection() throws IOException {
        final RandomAccessFile randomAccessFile = new RandomAccessFile(this.filename, "r");
        if (randomAccessFile.length() > 4L) {
            final byte[] array = new byte[4];
            randomAccessFile.seek(0L);
            randomAccessFile.read(array);
            if (Arrays.equals(array, ELFAnalyser.ELF_MAGIC)) {
                this.ELF = true;
            }
        }
        if (!this.ELF) {
            randomAccessFile.close();
            return;
        }
        randomAccessFile.seek(4L);
        this._64Bit = (randomAccessFile.readByte() == 2);
        randomAccessFile.seek(0L);
        final ByteBuffer allocate = ByteBuffer.allocate(this._64Bit ? 64 : 52);
        randomAccessFile.getChannel().read(allocate, 0L);
        this.bigEndian = (allocate.get(5) == 2);
        allocate.order(this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.arm = (allocate.get(18) == 40);
        if (this.arm) {
            this.armHardFloat = ((allocate.getInt(this._64Bit ? 48 : 36) & 0x400) == 0x400);
            this.armSoftFloat = !this.armHardFloat;
        }
        randomAccessFile.close();
    }
    
    static {
        ELF_MAGIC = new byte[] { 127, 69, 76, 70 };
    }
}
