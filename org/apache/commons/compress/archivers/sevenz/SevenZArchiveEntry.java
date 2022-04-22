package org.apache.commons.compress.archivers.sevenz;

import org.apache.commons.compress.archivers.*;
import java.util.*;

public class SevenZArchiveEntry implements ArchiveEntry
{
    private String name;
    private boolean hasStream;
    private boolean isDirectory;
    private boolean isAntiItem;
    private boolean hasCreationDate;
    private boolean hasLastModifiedDate;
    private boolean hasAccessDate;
    private long creationDate;
    private long lastModifiedDate;
    private long accessDate;
    private boolean hasWindowsAttributes;
    private int windowsAttributes;
    private boolean hasCrc;
    private long crc;
    private long compressedCrc;
    private long size;
    private long compressedSize;
    private Iterable contentMethods;
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public boolean hasStream() {
        return this.hasStream;
    }
    
    public void setHasStream(final boolean hasStream) {
        this.hasStream = hasStream;
    }
    
    public boolean isDirectory() {
        return this.isDirectory;
    }
    
    public void setDirectory(final boolean isDirectory) {
        this.isDirectory = isDirectory;
    }
    
    public boolean isAntiItem() {
        return this.isAntiItem;
    }
    
    public void setAntiItem(final boolean isAntiItem) {
        this.isAntiItem = isAntiItem;
    }
    
    public boolean getHasCreationDate() {
        return this.hasCreationDate;
    }
    
    public void setHasCreationDate(final boolean hasCreationDate) {
        this.hasCreationDate = hasCreationDate;
    }
    
    public Date getCreationDate() {
        if (this.hasCreationDate) {
            return ntfsTimeToJavaTime(this.creationDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }
    
    public void setCreationDate(final long creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setCreationDate(final Date date) {
        this.hasCreationDate = (date != null);
        if (this.hasCreationDate) {
            this.creationDate = javaTimeToNtfsTime(date);
        }
    }
    
    public boolean getHasLastModifiedDate() {
        return this.hasLastModifiedDate;
    }
    
    public void setHasLastModifiedDate(final boolean hasLastModifiedDate) {
        this.hasLastModifiedDate = hasLastModifiedDate;
    }
    
    public Date getLastModifiedDate() {
        if (this.hasLastModifiedDate) {
            return ntfsTimeToJavaTime(this.lastModifiedDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }
    
    public void setLastModifiedDate(final long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public void setLastModifiedDate(final Date date) {
        this.hasLastModifiedDate = (date != null);
        if (this.hasLastModifiedDate) {
            this.lastModifiedDate = javaTimeToNtfsTime(date);
        }
    }
    
    public boolean getHasAccessDate() {
        return this.hasAccessDate;
    }
    
    public void setHasAccessDate(final boolean hasAccessDate) {
        this.hasAccessDate = hasAccessDate;
    }
    
    public Date getAccessDate() {
        if (this.hasAccessDate) {
            return ntfsTimeToJavaTime(this.accessDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }
    
    public void setAccessDate(final long accessDate) {
        this.accessDate = accessDate;
    }
    
    public void setAccessDate(final Date date) {
        this.hasAccessDate = (date != null);
        if (this.hasAccessDate) {
            this.accessDate = javaTimeToNtfsTime(date);
        }
    }
    
    public boolean getHasWindowsAttributes() {
        return this.hasWindowsAttributes;
    }
    
    public void setHasWindowsAttributes(final boolean hasWindowsAttributes) {
        this.hasWindowsAttributes = hasWindowsAttributes;
    }
    
    public int getWindowsAttributes() {
        return this.windowsAttributes;
    }
    
    public void setWindowsAttributes(final int windowsAttributes) {
        this.windowsAttributes = windowsAttributes;
    }
    
    public boolean getHasCrc() {
        return this.hasCrc;
    }
    
    public void setHasCrc(final boolean hasCrc) {
        this.hasCrc = hasCrc;
    }
    
    @Deprecated
    public int getCrc() {
        return (int)this.crc;
    }
    
    @Deprecated
    public void setCrc(final int n) {
        this.crc = n;
    }
    
    public long getCrcValue() {
        return this.crc;
    }
    
    public void setCrcValue(final long crc) {
        this.crc = crc;
    }
    
    @Deprecated
    int getCompressedCrc() {
        return (int)this.compressedCrc;
    }
    
    @Deprecated
    void setCompressedCrc(final int n) {
        this.compressedCrc = n;
    }
    
    long getCompressedCrcValue() {
        return this.compressedCrc;
    }
    
    void setCompressedCrcValue(final long compressedCrc) {
        this.compressedCrc = compressedCrc;
    }
    
    public long getSize() {
        return this.size;
    }
    
    public void setSize(final long size) {
        this.size = size;
    }
    
    long getCompressedSize() {
        return this.compressedSize;
    }
    
    void setCompressedSize(final long compressedSize) {
        this.compressedSize = compressedSize;
    }
    
    public void setContentMethods(final Iterable iterable) {
        if (iterable != null) {
            final LinkedList<SevenZMethodConfiguration> list = new LinkedList<SevenZMethodConfiguration>();
            final Iterator<SevenZMethodConfiguration> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                list.addLast(iterator.next());
            }
            this.contentMethods = Collections.unmodifiableList((List<?>)list);
        }
        else {
            this.contentMethods = null;
        }
    }
    
    public Iterable getContentMethods() {
        return this.contentMethods;
    }
    
    public static Date ntfsTimeToJavaTime(final long n) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        instance.set(1601, 0, 1, 0, 0, 0);
        instance.set(14, 0);
        return new Date(instance.getTimeInMillis() + n / 10000L);
    }
    
    public static long javaTimeToNtfsTime(final Date date) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        instance.set(1601, 0, 1, 0, 0, 0);
        instance.set(14, 0);
        return (date.getTime() - instance.getTimeInMillis()) * 1000L * 10L;
    }
}
