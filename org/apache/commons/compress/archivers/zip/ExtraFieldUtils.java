package org.apache.commons.compress.archivers.zip;

import java.util.zip.*;
import java.util.*;
import java.util.concurrent.*;

public class ExtraFieldUtils
{
    private static final int WORD = 4;
    private static final Map implementations;
    
    public static void register(final Class clazz) {
        ExtraFieldUtils.implementations.put(clazz.newInstance().getHeaderId(), clazz);
    }
    
    public static ZipExtraField createExtraField(final ZipShort headerId) throws InstantiationException, IllegalAccessException {
        final Class<ZipExtraField> clazz = ExtraFieldUtils.implementations.get(headerId);
        if (clazz != null) {
            return clazz.newInstance();
        }
        final UnrecognizedExtraField unrecognizedExtraField = new UnrecognizedExtraField();
        unrecognizedExtraField.setHeaderId(headerId);
        return unrecognizedExtraField;
    }
    
    public static ZipExtraField[] parse(final byte[] array) throws ZipException {
        return parse(array, true, UnparseableExtraField.THROW);
    }
    
    public static ZipExtraField[] parse(final byte[] array, final boolean b) throws ZipException {
        return parse(array, b, UnparseableExtraField.THROW);
    }
    
    public static ZipExtraField[] parse(final byte[] array, final boolean b, final UnparseableExtraField unparseableExtraField) throws ZipException {
        final ArrayList<UnparseableExtraFieldData> list = new ArrayList<UnparseableExtraFieldData>();
    Label_0326:
        while (0 <= array.length - 4) {
            final ZipShort zipShort = new ZipShort(array, 0);
            final int value = new ZipShort(array, 2).getValue();
            if (4 + value > array.length) {
                switch (unparseableExtraField.getKey()) {
                    case 0: {
                        throw new ZipException("bad extra field starting at " + 0 + ".  Block length of " + value + " bytes exceeds remaining" + " data of " + (array.length - 0 - 4) + " bytes.");
                    }
                    case 2: {
                        final UnparseableExtraFieldData unparseableExtraFieldData = new UnparseableExtraFieldData();
                        if (b) {
                            unparseableExtraFieldData.parseFromLocalFileData(array, 0, array.length - 0);
                        }
                        else {
                            unparseableExtraFieldData.parseFromCentralDirectoryData(array, 0, array.length - 0);
                        }
                        list.add(unparseableExtraFieldData);
                    }
                    case 1: {
                        break Label_0326;
                    }
                    default: {
                        throw new ZipException("unknown UnparseableExtraField key: " + unparseableExtraField.getKey());
                    }
                }
            }
            else {
                final ZipExtraField extraField = createExtraField(zipShort);
                if (b) {
                    extraField.parseFromLocalFileData(array, 4, value);
                }
                else {
                    extraField.parseFromCentralDirectoryData(array, 4, value);
                }
                list.add((UnparseableExtraFieldData)extraField);
            }
        }
        return list.toArray(new ZipExtraField[list.size()]);
    }
    
    public static byte[] mergeLocalFileDataData(final ZipExtraField[] array) {
        final boolean b = array.length > 0 && array[array.length - 1] instanceof UnparseableExtraFieldData;
        final int n = b ? (array.length - 1) : array.length;
        int n2 = 4 * n;
        int length = array.length;
        int n3 = 0;
        while (0 < 0) {
            n2 += array[0].getLocalFileDataLength().getValue();
            ++n3;
        }
        final byte[] array2 = new byte[n2];
        while (0 < n) {
            System.arraycopy(array[0].getHeaderId().getBytes(), 0, array2, 0, 2);
            System.arraycopy(array[0].getLocalFileDataLength().getBytes(), 0, array2, 2, 2);
            length += 4;
            final byte[] localFileDataData = array[0].getLocalFileDataData();
            if (localFileDataData != null) {
                System.arraycopy(localFileDataData, 0, array2, 0, localFileDataData.length);
                length = 0 + localFileDataData.length;
            }
            ++n3;
        }
        if (b) {
            final byte[] localFileDataData2 = array[array.length - 1].getLocalFileDataData();
            if (localFileDataData2 != null) {
                System.arraycopy(localFileDataData2, 0, array2, 0, localFileDataData2.length);
            }
        }
        return array2;
    }
    
    public static byte[] mergeCentralDirectoryData(final ZipExtraField[] array) {
        final boolean b = array.length > 0 && array[array.length - 1] instanceof UnparseableExtraFieldData;
        final int n = b ? (array.length - 1) : array.length;
        int n2 = 4 * n;
        int length = array.length;
        int n3 = 0;
        while (0 < 0) {
            n2 += array[0].getCentralDirectoryLength().getValue();
            ++n3;
        }
        final byte[] array2 = new byte[n2];
        while (0 < n) {
            System.arraycopy(array[0].getHeaderId().getBytes(), 0, array2, 0, 2);
            System.arraycopy(array[0].getCentralDirectoryLength().getBytes(), 0, array2, 2, 2);
            length += 4;
            final byte[] centralDirectoryData = array[0].getCentralDirectoryData();
            if (centralDirectoryData != null) {
                System.arraycopy(centralDirectoryData, 0, array2, 0, centralDirectoryData.length);
                length = 0 + centralDirectoryData.length;
            }
            ++n3;
        }
        if (b) {
            final byte[] centralDirectoryData2 = array[array.length - 1].getCentralDirectoryData();
            if (centralDirectoryData2 != null) {
                System.arraycopy(centralDirectoryData2, 0, array2, 0, centralDirectoryData2.length);
            }
        }
        return array2;
    }
    
    static {
        implementations = new ConcurrentHashMap();
        register(AsiExtraField.class);
        register(X5455_ExtendedTimestamp.class);
        register(X7875_NewUnix.class);
        register(JarMarker.class);
        register(UnicodePathExtraField.class);
        register(UnicodeCommentExtraField.class);
        register(Zip64ExtendedInformationExtraField.class);
    }
    
    public static final class UnparseableExtraField
    {
        public static final int THROW_KEY = 0;
        public static final int SKIP_KEY = 1;
        public static final int READ_KEY = 2;
        public static final UnparseableExtraField THROW;
        public static final UnparseableExtraField SKIP;
        public static final UnparseableExtraField READ;
        private final int key;
        
        private UnparseableExtraField(final int key) {
            this.key = key;
        }
        
        public int getKey() {
            return this.key;
        }
        
        static {
            THROW = new UnparseableExtraField(0);
            SKIP = new UnparseableExtraField(1);
            READ = new UnparseableExtraField(2);
        }
    }
}
