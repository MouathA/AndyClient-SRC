package org.apache.commons.compress.archivers.zip;

import java.util.*;
import java.math.*;
import java.util.zip.*;

public abstract class ZipUtil
{
    private static final byte[] DOS_TIME_MIN;
    
    public static ZipLong toDosTime(final Date date) {
        return new ZipLong(toDosTime(date.getTime()));
    }
    
    public static byte[] toDosTime(final long timeInMillis) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeInMillis);
        final int value = instance.get(1);
        if (value < 1980) {
            return copy(ZipUtil.DOS_TIME_MIN);
        }
        return ZipLong.getBytes(value - 1980 << 25 | instance.get(2) + 1 << 21 | instance.get(5) << 16 | instance.get(11) << 11 | instance.get(12) << 5 | instance.get(13) >> 1);
    }
    
    public static long adjustToLong(final int n) {
        if (n < 0) {
            return 4294967296L + n;
        }
        return n;
    }
    
    public static byte[] reverse(final byte[] array) {
        final int n = array.length - 1;
        while (0 < array.length / 2) {
            final byte b = array[0];
            array[0] = array[n - 0];
            array[n - 0] = b;
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    static long bigToLong(final BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 63) {
            return bigInteger.longValue();
        }
        throw new NumberFormatException("The BigInteger cannot fit inside a 64 bit java long: [" + bigInteger + "]");
    }
    
    static BigInteger longToBig(long adjustToLong) {
        if (adjustToLong < -2147483648L) {
            throw new IllegalArgumentException("Negative longs < -2^31 not permitted: [" + adjustToLong + "]");
        }
        if (adjustToLong < 0L && adjustToLong >= -2147483648L) {
            adjustToLong = adjustToLong((int)adjustToLong);
        }
        return BigInteger.valueOf(adjustToLong);
    }
    
    public static int signedByteToUnsignedInt(final byte b) {
        if (b >= 0) {
            return b;
        }
        return 256 + b;
    }
    
    public static byte unsignedIntToSignedByte(final int n) {
        if (n > 255 || n < 0) {
            throw new IllegalArgumentException("Can only convert non-negative integers between [0,255] to byte: [" + n + "]");
        }
        if (n < 128) {
            return (byte)n;
        }
        return (byte)(n - 256);
    }
    
    public static Date fromDosTime(final ZipLong zipLong) {
        return new Date(dosToJavaTime(zipLong.getValue()));
    }
    
    public static long dosToJavaTime(final long n) {
        final Calendar instance = Calendar.getInstance();
        instance.set(1, (int)(n >> 25 & 0x7FL) + 1980);
        instance.set(2, (int)(n >> 21 & 0xFL) - 1);
        instance.set(5, (int)(n >> 16) & 0x1F);
        instance.set(11, (int)(n >> 11) & 0x1F);
        instance.set(12, (int)(n >> 5) & 0x3F);
        instance.set(13, (int)(n << 1) & 0x3E);
        instance.set(14, 0);
        return instance.getTime().getTime();
    }
    
    static void setNameAndCommentFromExtraFields(final ZipArchiveEntry zipArchiveEntry, final byte[] array, final byte[] array2) {
        final UnicodePathExtraField unicodePathExtraField = (UnicodePathExtraField)zipArchiveEntry.getExtraField(UnicodePathExtraField.UPATH_ID);
        final String name = zipArchiveEntry.getName();
        final String unicodeStringIfOriginalMatches = getUnicodeStringIfOriginalMatches(unicodePathExtraField, array);
        if (unicodeStringIfOriginalMatches != null && !name.equals(unicodeStringIfOriginalMatches)) {
            zipArchiveEntry.setName(unicodeStringIfOriginalMatches);
        }
        if (array2 != null && array2.length > 0) {
            final String unicodeStringIfOriginalMatches2 = getUnicodeStringIfOriginalMatches((AbstractUnicodeExtraField)zipArchiveEntry.getExtraField(UnicodeCommentExtraField.UCOM_ID), array2);
            if (unicodeStringIfOriginalMatches2 != null) {
                zipArchiveEntry.setComment(unicodeStringIfOriginalMatches2);
            }
        }
    }
    
    private static String getUnicodeStringIfOriginalMatches(final AbstractUnicodeExtraField abstractUnicodeExtraField, final byte[] array) {
        if (abstractUnicodeExtraField != null) {
            final CRC32 crc32 = new CRC32();
            crc32.update(array);
            if (crc32.getValue() == abstractUnicodeExtraField.getNameCRC32()) {
                return ZipEncodingHelper.UTF8_ZIP_ENCODING.decode(abstractUnicodeExtraField.getUnicodeName());
            }
        }
        return null;
    }
    
    static byte[] copy(final byte[] array) {
        if (array != null) {
            final byte[] array2 = new byte[array.length];
            System.arraycopy(array, 0, array2, 0, array2.length);
            return array2;
        }
        return null;
    }
    
    static boolean canHandleEntryData(final ZipArchiveEntry zipArchiveEntry) {
        return supportsEncryptionOf(zipArchiveEntry) && supportsMethodOf(zipArchiveEntry);
    }
    
    private static boolean supportsEncryptionOf(final ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesEncryption();
    }
    
    private static boolean supportsMethodOf(final ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getMethod() == 0 || zipArchiveEntry.getMethod() == ZipMethod.UNSHRINKING.getCode() || zipArchiveEntry.getMethod() == ZipMethod.IMPLODING.getCode() || zipArchiveEntry.getMethod() == 8;
    }
    
    static void checkRequestedFeatures(final ZipArchiveEntry zipArchiveEntry) throws UnsupportedZipFeatureException {
        if (!supportsEncryptionOf(zipArchiveEntry)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.ENCRYPTION, zipArchiveEntry);
        }
        if (supportsMethodOf(zipArchiveEntry)) {
            return;
        }
        final ZipMethod methodByCode = ZipMethod.getMethodByCode(zipArchiveEntry.getMethod());
        if (methodByCode == null) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.METHOD, zipArchiveEntry);
        }
        throw new UnsupportedZipFeatureException(methodByCode, zipArchiveEntry);
    }
    
    static {
        DOS_TIME_MIN = ZipLong.getBytes(8448L);
    }
}
