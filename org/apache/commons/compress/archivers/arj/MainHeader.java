package org.apache.commons.compress.archivers.arj;

import java.util.*;

class MainHeader
{
    int archiverVersionNumber;
    int minVersionToExtract;
    int hostOS;
    int arjFlags;
    int securityVersion;
    int fileType;
    int reserved;
    int dateTimeCreated;
    int dateTimeModified;
    long archiveSize;
    int securityEnvelopeFilePosition;
    int fileSpecPosition;
    int securityEnvelopeLength;
    int encryptionVersion;
    int lastChapter;
    int arjProtectionFactor;
    int arjFlags2;
    String name;
    String comment;
    byte[] extendedHeaderBytes;
    
    MainHeader() {
        this.extendedHeaderBytes = null;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MainHeader [archiverVersionNumber=");
        sb.append(this.archiverVersionNumber);
        sb.append(", minVersionToExtract=");
        sb.append(this.minVersionToExtract);
        sb.append(", hostOS=");
        sb.append(this.hostOS);
        sb.append(", arjFlags=");
        sb.append(this.arjFlags);
        sb.append(", securityVersion=");
        sb.append(this.securityVersion);
        sb.append(", fileType=");
        sb.append(this.fileType);
        sb.append(", reserved=");
        sb.append(this.reserved);
        sb.append(", dateTimeCreated=");
        sb.append(this.dateTimeCreated);
        sb.append(", dateTimeModified=");
        sb.append(this.dateTimeModified);
        sb.append(", archiveSize=");
        sb.append(this.archiveSize);
        sb.append(", securityEnvelopeFilePosition=");
        sb.append(this.securityEnvelopeFilePosition);
        sb.append(", fileSpecPosition=");
        sb.append(this.fileSpecPosition);
        sb.append(", securityEnvelopeLength=");
        sb.append(this.securityEnvelopeLength);
        sb.append(", encryptionVersion=");
        sb.append(this.encryptionVersion);
        sb.append(", lastChapter=");
        sb.append(this.lastChapter);
        sb.append(", arjProtectionFactor=");
        sb.append(this.arjProtectionFactor);
        sb.append(", arjFlags2=");
        sb.append(this.arjFlags2);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", comment=");
        sb.append(this.comment);
        sb.append(", extendedHeaderBytes=");
        sb.append(Arrays.toString(this.extendedHeaderBytes));
        sb.append("]");
        return sb.toString();
    }
    
    static class Flags
    {
        static final int GARBLED = 1;
        static final int OLD_SECURED_NEW_ANSI_PAGE = 2;
        static final int VOLUME = 4;
        static final int ARJPROT = 8;
        static final int PATHSYM = 16;
        static final int BACKUP = 32;
        static final int SECURED = 64;
        static final int ALTNAME = 128;
    }
}
