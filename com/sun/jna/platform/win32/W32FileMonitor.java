package com.sun.jna.platform.win32;

import com.sun.jna.platform.*;
import java.util.*;
import com.sun.jna.ptr.*;
import java.io.*;
import com.sun.jna.*;

public class W32FileMonitor extends FileMonitor
{
    private static final int BUFFER_SIZE = 4096;
    private Thread watcher;
    private WinNT.HANDLE port;
    private final Map fileMap;
    private final Map handleMap;
    private boolean disposing;
    private static int watcherThreadID;
    
    public W32FileMonitor() {
        this.fileMap = new HashMap();
        this.handleMap = new HashMap();
        this.disposing = false;
    }
    
    private void handleChanges(final FileInfo fileInfo) throws IOException {
        final Kernel32 instance = Kernel32.INSTANCE;
        WinNT.FILE_NOTIFY_INFORMATION file_NOTIFY_INFORMATION = fileInfo.info;
        file_NOTIFY_INFORMATION.read();
        do {
            FileEvent fileEvent = null;
            final File file = new File(fileInfo.file, file_NOTIFY_INFORMATION.getFilename());
            switch (file_NOTIFY_INFORMATION.Action) {
                case 0: {
                    break;
                }
                case 3: {
                    fileEvent = new FileEvent(file, 4);
                    break;
                }
                case 1: {
                    fileEvent = new FileEvent(file, 1);
                    break;
                }
                case 2: {
                    fileEvent = new FileEvent(file, 2);
                    break;
                }
                case 4: {
                    fileEvent = new FileEvent(file, 16);
                    break;
                }
                case 5: {
                    fileEvent = new FileEvent(file, 32);
                    break;
                }
                default: {
                    System.err.println("Unrecognized file action '" + file_NOTIFY_INFORMATION.Action + "'");
                    break;
                }
            }
            if (fileEvent != null) {
                this.notify(fileEvent);
            }
            file_NOTIFY_INFORMATION = file_NOTIFY_INFORMATION.next();
        } while (file_NOTIFY_INFORMATION != null);
        if (!fileInfo.file.exists()) {
            this.unwatch(fileInfo.file);
            return;
        }
        if (!instance.ReadDirectoryChangesW(fileInfo.handle, fileInfo.info, fileInfo.info.size(), fileInfo.recursive, fileInfo.notifyMask, fileInfo.infoLength, fileInfo.overlapped, null) && !this.disposing) {
            final int getLastError = instance.GetLastError();
            throw new IOException("ReadDirectoryChangesW failed on " + fileInfo.file + ": '" + Kernel32Util.formatMessageFromLastErrorCode(getLastError) + "' (" + getLastError + ")");
        }
    }
    
    private FileInfo waitForChange() {
        final Kernel32 instance = Kernel32.INSTANCE;
        final IntByReference intByReference = new IntByReference();
        final BaseTSD.ULONG_PTRByReference ulong_PTRByReference = new BaseTSD.ULONG_PTRByReference();
        instance.GetQueuedCompletionStatus(this.port, intByReference, ulong_PTRByReference, new PointerByReference(), -1);
        // monitorenter(this)
        // monitorexit(this)
        return this.handleMap.get(ulong_PTRByReference.getValue());
    }
    
    private int convertMask(final int n) {
        if ((n & 0x1) != 0x0) {}
        if ((n & 0x2) != 0x0) {}
        if ((n & 0x4) != 0x0) {}
        if ((n & 0x30) != 0x0) {}
        if ((n & 0x40) != 0x0) {}
        if ((n & 0x8) != 0x0) {}
        if ((n & 0x80) != 0x0) {}
        if ((n & 0x100) != 0x0) {}
        return 0;
    }
    
    @Override
    protected synchronized void watch(final File file, final int n, final boolean b) throws IOException {
        File file2 = file;
        if (!file2.isDirectory()) {
            file2 = file.getParentFile();
        }
        while (file2 != null && !file2.exists()) {
            file2 = file2.getParentFile();
        }
        if (file2 == null) {
            throw new FileNotFoundException("No ancestor found for " + file);
        }
        final Kernel32 instance = Kernel32.INSTANCE;
        final WinNT.HANDLE createFile = instance.CreateFile(file.getAbsolutePath(), 1, 7, null, 3, 1107296256, null);
        if (WinBase.INVALID_HANDLE_VALUE.equals(createFile)) {
            throw new IOException("Unable to open " + file + " (" + instance.GetLastError() + ")");
        }
        final int convertMask = this.convertMask(n);
        final FileInfo fileInfo = new FileInfo(file, createFile, convertMask, true);
        this.fileMap.put(file, fileInfo);
        this.handleMap.put(createFile, fileInfo);
        this.port = instance.CreateIoCompletionPort(createFile, this.port, createFile.getPointer(), 0);
        if (WinBase.INVALID_HANDLE_VALUE.equals(this.port)) {
            throw new IOException("Unable to create/use I/O Completion port for " + file + " (" + instance.GetLastError() + ")");
        }
        if (!instance.ReadDirectoryChangesW(createFile, fileInfo.info, fileInfo.info.size(), true, convertMask, fileInfo.infoLength, fileInfo.overlapped, null)) {
            final int getLastError = instance.GetLastError();
            throw new IOException("ReadDirectoryChangesW failed on " + fileInfo.file + ", handle " + createFile + ": '" + Kernel32Util.formatMessageFromLastErrorCode(getLastError) + "' (" + getLastError + ")");
        }
        if (this.watcher == null) {
            (this.watcher = new Thread("W32 File Monitor-" + W32FileMonitor.watcherThreadID++) {
                final W32FileMonitor this$0;
                
                @Override
                public void run() {
                    while (true) {
                        final FileInfo access$000 = W32FileMonitor.access$000(this.this$0);
                        if (access$000 == null) {
                            // monitorenter(this$0 = this.this$0)
                            if (W32FileMonitor.access$100(this.this$0).isEmpty()) {
                                break;
                            }
                        }
                        // monitorexit(this$0)
                        else {
                            W32FileMonitor.access$300(this.this$0, access$000);
                        }
                    }
                    W32FileMonitor.access$202(this.this$0, null);
                }
                // monitorexit(this$0)
            }).setDaemon(true);
            this.watcher.start();
        }
    }
    
    @Override
    protected synchronized void unwatch(final File file) {
        final FileInfo fileInfo = this.fileMap.remove(file);
        if (fileInfo != null) {
            this.handleMap.remove(fileInfo.handle);
            Kernel32.INSTANCE.CloseHandle(fileInfo.handle);
        }
    }
    
    @Override
    public synchronized void dispose() {
        this.disposing = true;
        final Object[] array = this.fileMap.keySet().toArray();
        while (!this.fileMap.isEmpty()) {
            final Object[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            this.unwatch((File)array2[n]);
        }
        final Kernel32 instance = Kernel32.INSTANCE;
        instance.PostQueuedCompletionStatus(this.port, 0, null, null);
        instance.CloseHandle(this.port);
        this.port = null;
        this.watcher = null;
    }
    
    static FileInfo access$000(final W32FileMonitor w32FileMonitor) {
        return w32FileMonitor.waitForChange();
    }
    
    static Map access$100(final W32FileMonitor w32FileMonitor) {
        return w32FileMonitor.fileMap;
    }
    
    static Thread access$202(final W32FileMonitor w32FileMonitor, final Thread watcher) {
        return w32FileMonitor.watcher = watcher;
    }
    
    static void access$300(final W32FileMonitor w32FileMonitor, final FileInfo fileInfo) throws IOException {
        w32FileMonitor.handleChanges(fileInfo);
    }
    
    private class FileInfo
    {
        public final File file;
        public final WinNT.HANDLE handle;
        public final int notifyMask;
        public final boolean recursive;
        public final WinNT.FILE_NOTIFY_INFORMATION info;
        public final IntByReference infoLength;
        public final WinBase.OVERLAPPED overlapped;
        final W32FileMonitor this$0;
        
        public FileInfo(final W32FileMonitor this$0, final File file, final WinNT.HANDLE handle, final int notifyMask, final boolean recursive) {
            this.this$0 = this$0;
            this.info = new WinNT.FILE_NOTIFY_INFORMATION(4096);
            this.infoLength = new IntByReference();
            this.overlapped = new WinBase.OVERLAPPED();
            this.file = file;
            this.handle = handle;
            this.notifyMask = notifyMask;
            this.recursive = recursive;
        }
    }
}
