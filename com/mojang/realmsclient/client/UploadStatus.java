package com.mojang.realmsclient.client;

public class UploadStatus
{
    public Long bytesWritten;
    public Long totalBytes;
    
    public UploadStatus() {
        this.bytesWritten = 0L;
        this.totalBytes = 0L;
    }
}
