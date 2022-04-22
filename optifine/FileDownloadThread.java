package optifine;

import net.minecraft.client.*;

public class FileDownloadThread extends Thread
{
    private String urlString;
    private IFileDownloadListener listener;
    
    public FileDownloadThread(final String urlString, final IFileDownloadListener listener) {
        this.urlString = null;
        this.listener = null;
        this.urlString = urlString;
        this.listener = listener;
    }
    
    @Override
    public void run() {
        this.listener.fileDownloadFinished(this.urlString, HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy()), null);
    }
    
    public String getUrlString() {
        return this.urlString;
    }
    
    public IFileDownloadListener getListener() {
        return this.listener;
    }
}
