package org.apache.commons.compress.archivers.zip;

public final class GeneralPurposeBit
{
    private static final int ENCRYPTION_FLAG = 1;
    private static final int SLIDING_DICTIONARY_SIZE_FLAG = 2;
    private static final int NUMBER_OF_SHANNON_FANO_TREES_FLAG = 4;
    private static final int DATA_DESCRIPTOR_FLAG = 8;
    private static final int STRONG_ENCRYPTION_FLAG = 64;
    public static final int UFT8_NAMES_FLAG = 2048;
    private boolean languageEncodingFlag;
    private boolean dataDescriptorFlag;
    private boolean encryptionFlag;
    private boolean strongEncryptionFlag;
    private int slidingDictionarySize;
    private int numberOfShannonFanoTrees;
    
    public GeneralPurposeBit() {
        this.languageEncodingFlag = false;
        this.dataDescriptorFlag = false;
        this.encryptionFlag = false;
        this.strongEncryptionFlag = false;
    }
    
    public boolean usesUTF8ForNames() {
        return this.languageEncodingFlag;
    }
    
    public void useUTF8ForNames(final boolean languageEncodingFlag) {
        this.languageEncodingFlag = languageEncodingFlag;
    }
    
    public boolean usesDataDescriptor() {
        return this.dataDescriptorFlag;
    }
    
    public void useDataDescriptor(final boolean dataDescriptorFlag) {
        this.dataDescriptorFlag = dataDescriptorFlag;
    }
    
    public boolean usesEncryption() {
        return this.encryptionFlag;
    }
    
    public void useEncryption(final boolean encryptionFlag) {
        this.encryptionFlag = encryptionFlag;
    }
    
    public boolean usesStrongEncryption() {
        return this.encryptionFlag && this.strongEncryptionFlag;
    }
    
    public void useStrongEncryption(final boolean strongEncryptionFlag) {
        this.strongEncryptionFlag = strongEncryptionFlag;
        if (strongEncryptionFlag) {
            this.useEncryption(true);
        }
    }
    
    int getSlidingDictionarySize() {
        return this.slidingDictionarySize;
    }
    
    int getNumberOfShannonFanoTrees() {
        return this.numberOfShannonFanoTrees;
    }
    
    public byte[] encode() {
        return ZipShort.getBytes((this.dataDescriptorFlag ? 8 : 0) | (this.languageEncodingFlag ? 2048 : 0) | (this.encryptionFlag ? 1 : 0) | (this.strongEncryptionFlag ? 64 : 0));
    }
    
    public static GeneralPurposeBit parse(final byte[] array, final int n) {
        final int value = ZipShort.getValue(array, n);
        final GeneralPurposeBit generalPurposeBit = new GeneralPurposeBit();
        generalPurposeBit.useDataDescriptor((value & 0x8) != 0x0);
        generalPurposeBit.useUTF8ForNames((value & 0x800) != 0x0);
        generalPurposeBit.useStrongEncryption((value & 0x40) != 0x0);
        generalPurposeBit.useEncryption((value & 0x1) != 0x0);
        generalPurposeBit.slidingDictionarySize = (((value & 0x2) != 0x0) ? 8192 : 4096);
        generalPurposeBit.numberOfShannonFanoTrees = (((value & 0x4) != 0x0) ? 3 : 2);
        return generalPurposeBit;
    }
    
    @Override
    public int hashCode() {
        return 3 * (7 * (13 * (17 * (this.encryptionFlag ? 1 : 0) + (this.strongEncryptionFlag ? 1 : 0)) + (this.languageEncodingFlag ? 1 : 0)) + (this.dataDescriptorFlag ? 1 : 0));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof GeneralPurposeBit)) {
            return false;
        }
        final GeneralPurposeBit generalPurposeBit = (GeneralPurposeBit)o;
        return generalPurposeBit.encryptionFlag == this.encryptionFlag && generalPurposeBit.strongEncryptionFlag == this.strongEncryptionFlag && generalPurposeBit.languageEncodingFlag == this.languageEncodingFlag && generalPurposeBit.dataDescriptorFlag == this.dataDescriptorFlag;
    }
}
