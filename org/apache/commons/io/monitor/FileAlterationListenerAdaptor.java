package org.apache.commons.io.monitor;

import java.io.*;

public class FileAlterationListenerAdaptor implements FileAlterationListener
{
    @Override
    public void onStart(final FileAlterationObserver fileAlterationObserver) {
    }
    
    @Override
    public void onDirectoryCreate(final File file) {
    }
    
    @Override
    public void onDirectoryChange(final File file) {
    }
    
    @Override
    public void onDirectoryDelete(final File file) {
    }
    
    @Override
    public void onFileCreate(final File file) {
    }
    
    @Override
    public void onFileChange(final File file) {
    }
    
    @Override
    public void onFileDelete(final File file) {
    }
    
    @Override
    public void onStop(final FileAlterationObserver fileAlterationObserver) {
    }
}
