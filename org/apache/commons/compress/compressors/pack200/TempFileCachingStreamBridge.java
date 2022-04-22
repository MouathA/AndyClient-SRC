package org.apache.commons.compress.compressors.pack200;

import java.io.*;

class TempFileCachingStreamBridge extends StreamBridge
{
    private final File f;
    
    TempFileCachingStreamBridge() throws IOException {
        (this.f = File.createTempFile("commons-compress", "packtemp")).deleteOnExit();
        this.out = new FileOutputStream(this.f);
    }
    
    @Override
    InputStream getInputView() throws IOException {
        this.out.close();
        return new FileInputStream(this.f) {
            final TempFileCachingStreamBridge this$0;
            
            @Override
            public void close() throws IOException {
                super.close();
                TempFileCachingStreamBridge.access$000(this.this$0).delete();
            }
        };
    }
    
    static File access$000(final TempFileCachingStreamBridge tempFileCachingStreamBridge) {
        return tempFileCachingStreamBridge.f;
    }
}
