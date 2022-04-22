package net.minecraft.client.resources;

import com.google.common.collect.*;
import org.apache.commons.io.filefilter.*;
import java.io.*;
import java.util.*;

public class FolderResourcePack extends AbstractResourcePack
{
    private static final String __OBFID;
    
    public FolderResourcePack(final File file) {
        super(file);
    }
    
    @Override
    protected InputStream getInputStreamByName(final String s) throws IOException {
        return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, s)));
    }
    
    @Override
    protected boolean hasResourceName(final String s) {
        return new File(this.resourcePackFile, s).isFile();
    }
    
    @Override
    public Set getResourceDomains() {
        final HashSet hashSet = Sets.newHashSet();
        final File file = new File(this.resourcePackFile, "assets/");
        if (file.isDirectory()) {
            final File[] listFiles = file.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
            while (0 < listFiles.length) {
                final String relativeName = AbstractResourcePack.getRelativeName(file, listFiles[0]);
                if (!relativeName.equals(relativeName.toLowerCase())) {
                    this.logNameNotLowercase(relativeName);
                }
                else {
                    hashSet.add(relativeName.substring(0, relativeName.length() - 1));
                }
                int n = 0;
                ++n;
            }
        }
        return hashSet;
    }
    
    static {
        __OBFID = "CL_00001076";
    }
}
