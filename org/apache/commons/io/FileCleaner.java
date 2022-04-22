package org.apache.commons.io;

import java.io.*;

@Deprecated
public class FileCleaner
{
    static final FileCleaningTracker theInstance;
    
    @Deprecated
    public static void track(final File file, final Object o) {
        FileCleaner.theInstance.track(file, o);
    }
    
    @Deprecated
    public static void track(final File file, final Object o, final FileDeleteStrategy fileDeleteStrategy) {
        FileCleaner.theInstance.track(file, o, fileDeleteStrategy);
    }
    
    @Deprecated
    public static void track(final String s, final Object o) {
        FileCleaner.theInstance.track(s, o);
    }
    
    @Deprecated
    public static void track(final String s, final Object o, final FileDeleteStrategy fileDeleteStrategy) {
        FileCleaner.theInstance.track(s, o, fileDeleteStrategy);
    }
    
    @Deprecated
    public static int getTrackCount() {
        return FileCleaner.theInstance.getTrackCount();
    }
    
    @Deprecated
    public static synchronized void exitWhenFinished() {
        FileCleaner.theInstance.exitWhenFinished();
    }
    
    public static FileCleaningTracker getInstance() {
        return FileCleaner.theInstance;
    }
    
    static {
        theInstance = new FileCleaningTracker();
    }
}
