package org.apache.commons.compress.archivers.arj;

import java.util.*;

class LocalFileHeader
{
    int archiverVersionNumber;
    int minVersionToExtract;
    int hostOS;
    int arjFlags;
    int method;
    int fileType;
    int reserved;
    int dateTimeModified;
    long compressedSize;
    long originalSize;
    long originalCrc32;
    int fileSpecPosition;
    int fileAccessMode;
    int firstChapter;
    int lastChapter;
    int extendedFilePosition;
    int dateTimeAccessed;
    int dateTimeCreated;
    int originalSizeEvenForVolumes;
    String name;
    String comment;
    byte[][] extendedHeaders;
    
    LocalFileHeader() {
        this.extendedHeaders = null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LocalFileHeader [archiverVersionNumber=");
        sb.append(this.archiverVersionNumber);
        sb.append(", minVersionToExtract=");
        sb.append(this.minVersionToExtract);
        sb.append(", hostOS=");
        sb.append(this.hostOS);
        sb.append(", arjFlags=");
        sb.append(this.arjFlags);
        sb.append(", method=");
        sb.append(this.method);
        sb.append(", fileType=");
        sb.append(this.fileType);
        sb.append(", reserved=");
        sb.append(this.reserved);
        sb.append(", dateTimeModified=");
        sb.append(this.dateTimeModified);
        sb.append(", compressedSize=");
        sb.append(this.compressedSize);
        sb.append(", originalSize=");
        sb.append(this.originalSize);
        sb.append(", originalCrc32=");
        sb.append(this.originalCrc32);
        sb.append(", fileSpecPosition=");
        sb.append(this.fileSpecPosition);
        sb.append(", fileAccessMode=");
        sb.append(this.fileAccessMode);
        sb.append(", firstChapter=");
        sb.append(this.firstChapter);
        sb.append(", lastChapter=");
        sb.append(this.lastChapter);
        sb.append(", extendedFilePosition=");
        sb.append(this.extendedFilePosition);
        sb.append(", dateTimeAccessed=");
        sb.append(this.dateTimeAccessed);
        sb.append(", dateTimeCreated=");
        sb.append(this.dateTimeCreated);
        sb.append(", originalSizeEvenForVolumes=");
        sb.append(this.originalSizeEvenForVolumes);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", comment=");
        sb.append(this.comment);
        sb.append(", extendedHeaders=");
        sb.append(Arrays.toString(this.extendedHeaders));
        sb.append("]");
        return sb.toString();
    }
    
    static class Methods
    {
        static final int STORED = 0;
        static final int COMPRESSED_MOST = 1;
        static final int COMPRESSED_FASTEST = 4;
        static final int NO_DATA_NO_CRC = 8;
        static final int NO_DATA = 9;
    }
    
    static class FileTypes
    {
        static final int BINARY = 0;
        static final int SEVEN_BIT_TEXT = 1;
        static final int DIRECTORY = 3;
        static final int VOLUME_LABEL = 4;
        static final int CHAPTER_LABEL = 5;
    }
    
    static class Flags
    {
        static final int GARBLED = 1;
        static final int VOLUME = 4;
        static final int EXTFILE = 8;
        static final int PATHSYM = 16;
        static final int BACKUP = 32;
    }
}
