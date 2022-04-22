package net.minecraft.client.renderer;

import java.util.concurrent.atomic.*;
import java.awt.image.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import javax.imageio.*;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import java.net.*;
import java.io.*;
import optifine.*;

public class ThreadDownloadImageData extends SimpleTexture
{
    private static final Logger logger;
    private static final AtomicInteger threadDownloadCounter;
    private final File field_152434_e;
    private final String imageUrl;
    private final IImageBuffer imageBuffer;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private boolean textureUploaded;
    private static final String __OBFID;
    public Boolean imageFound;
    public boolean pipeline;
    
    static {
        __OBFID = "CL_00001049";
        logger = LogManager.getLogger();
        threadDownloadCounter = new AtomicInteger(0);
    }
    
    public ThreadDownloadImageData(final File field_152434_e, final String imageUrl, final ResourceLocation resourceLocation, final IImageBuffer imageBuffer) {
        super(resourceLocation);
        this.imageFound = null;
        this.pipeline = false;
        this.field_152434_e = field_152434_e;
        this.imageUrl = imageUrl;
        this.imageBuffer = imageBuffer;
    }
    
    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            this.textureUploaded = true;
            if (this.textureLocation != null) {
                this.deleteGlTexture();
            }
            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
        }
    }
    
    @Override
    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }
    
    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        if (this.imageBuffer != null) {
            this.imageBuffer.func_152634_a();
        }
        this.imageFound = (this.bufferedImage != null);
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        if (this.bufferedImage == null && this.textureLocation != null) {
            super.loadTexture(resourceManager);
        }
        if (this.imageThread == null) {
            if (this.field_152434_e != null && this.field_152434_e.isFile()) {
                ThreadDownloadImageData.logger.debug("Loading http texture from local cache ({})", this.field_152434_e);
                this.bufferedImage = ImageIO.read(this.field_152434_e);
                if (this.imageBuffer != null) {
                    this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
                }
                this.imageFound = (this.bufferedImage != null);
            }
            else {
                this.func_152433_a();
            }
        }
    }
    
    protected void func_152433_a() {
        (this.imageThread = new Thread("Texture Downloader #" + ThreadDownloadImageData.threadDownloadCounter.incrementAndGet()) {
            final ThreadDownloadImageData this$0;
            
            @Override
            public void run() {
                ThreadDownloadImageData.access$0().debug("Downloading http texture from {} to {}", ThreadDownloadImageData.access$1(this.this$0), ThreadDownloadImageData.access$2(this.this$0));
                if (ThreadDownloadImageData.access$3(this.this$0)) {
                    ThreadDownloadImageData.access$4(this.this$0);
                }
                else {
                    final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(ThreadDownloadImageData.access$1(this.this$0)).openConnection(Minecraft.getMinecraft().getProxy());
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(false);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() / 100 != 2) {
                        if (httpURLConnection.getErrorStream() != null) {
                            Config.readAll(httpURLConnection.getErrorStream());
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        this.this$0.imageFound = (ThreadDownloadImageData.access$5(this.this$0) != null);
                        return;
                    }
                    BufferedImage bufferedImage;
                    if (ThreadDownloadImageData.access$2(this.this$0) != null) {
                        FileUtils.copyInputStreamToFile(httpURLConnection.getInputStream(), ThreadDownloadImageData.access$2(this.this$0));
                        bufferedImage = ImageIO.read(ThreadDownloadImageData.access$2(this.this$0));
                    }
                    else {
                        bufferedImage = TextureUtil.func_177053_a(httpURLConnection.getInputStream());
                    }
                    if (ThreadDownloadImageData.access$6(this.this$0) != null) {
                        bufferedImage = ThreadDownloadImageData.access$6(this.this$0).parseUserSkin(bufferedImage);
                    }
                    this.this$0.setBufferedImage(bufferedImage);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    this.this$0.imageFound = (ThreadDownloadImageData.access$5(this.this$0) != null);
                }
            }
        }).setDaemon(true);
        this.imageThread.start();
    }
    
    private boolean shouldPipeline() {
        if (!this.pipeline) {
            return false;
        }
        final Proxy proxy = Minecraft.getMinecraft().getProxy();
        return (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.SOCKS) && this.imageUrl.startsWith("http://");
    }
    
    private void loadPipelined() {
        final HttpResponse executeRequest = HttpPipeline.executeRequest(HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy()));
        if (executeRequest.getStatus() / 100 != 2) {
            this.imageFound = (this.bufferedImage != null);
            return;
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(executeRequest.getBody());
        BufferedImage bufferedImage;
        if (this.field_152434_e != null) {
            FileUtils.copyInputStreamToFile(byteArrayInputStream, this.field_152434_e);
            bufferedImage = ImageIO.read(this.field_152434_e);
        }
        else {
            bufferedImage = TextureUtil.func_177053_a(byteArrayInputStream);
        }
        if (this.imageBuffer != null) {
            bufferedImage = this.imageBuffer.parseUserSkin(bufferedImage);
        }
        this.setBufferedImage(bufferedImage);
        this.imageFound = (this.bufferedImage != null);
    }
    
    static Logger access$0() {
        return ThreadDownloadImageData.logger;
    }
    
    static String access$1(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageUrl;
    }
    
    static File access$2(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.field_152434_e;
    }
    
    static boolean access$3(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.shouldPipeline();
    }
    
    static void access$4(final ThreadDownloadImageData threadDownloadImageData) {
        threadDownloadImageData.loadPipelined();
    }
    
    static BufferedImage access$5(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.bufferedImage;
    }
    
    static IImageBuffer access$6(final ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageBuffer;
    }
}
