package net.minecraft.client.resources;

import com.google.common.base.*;
import java.io.*;
import java.util.zip.*;
import com.google.common.collect.*;
import java.util.*;

public class FileResourcePack extends AbstractResourcePack implements Closeable
{
    public static final Splitter entryNameSplitter;
    private ZipFile resourcePackZipFile;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001075";
        entryNameSplitter = Splitter.on('/').omitEmptyStrings().limit(3);
    }
    
    public FileResourcePack(final File file) {
        super(file);
    }
    
    private ZipFile getResourcePackZipFile() throws IOException {
        if (this.resourcePackZipFile == null) {
            this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
        }
        return this.resourcePackZipFile;
    }
    
    @Override
    protected InputStream getInputStreamByName(final String s) throws IOException {
        final ZipFile resourcePackZipFile = this.getResourcePackZipFile();
        final ZipEntry entry = resourcePackZipFile.getEntry(s);
        if (entry == null) {
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, s);
        }
        return resourcePackZipFile.getInputStream(entry);
    }
    
    public boolean hasResourceName(final String s) {
        return this.getResourcePackZipFile().getEntry(s) != null;
    }
    
    @Override
    public Set getResourceDomains() {
        final Enumeration<? extends ZipEntry> entries = this.getResourcePackZipFile().entries();
        final HashSet hashSet = Sets.newHashSet();
        while (entries.hasMoreElements()) {
            final String name = ((ZipEntry)entries.nextElement()).getName();
            if (name.startsWith("assets/")) {
                final ArrayList arrayList = Lists.newArrayList(FileResourcePack.entryNameSplitter.split(name));
                if (arrayList.size() <= 1) {
                    continue;
                }
                final String s = arrayList.get(1);
                if (!s.equals(s.toLowerCase())) {
                    this.logNameNotLowercase(s);
                }
                else {
                    hashSet.add(s);
                }
            }
        }
        return hashSet;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
    
    @Override
    public void close() throws IOException {
        if (this.resourcePackZipFile != null) {
            this.resourcePackZipFile.close();
            this.resourcePackZipFile = null;
        }
    }
}
