package paulscode.sound.codecs;

import java.net.*;
import java.io.*;
import paulscode.sound.*;
import javax.sound.sampled.*;
import java.nio.*;

public class CodecWav implements ICodec
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private boolean endOfStream;
    private boolean initialized;
    private AudioInputStream myAudioInputStream;
    private SoundSystemLogger logger;
    
    public void reverseByteOrder(final boolean b) {
    }
    
    public CodecWav() {
        this.endOfStream = false;
        this.initialized = false;
        this.myAudioInputStream = null;
        this.logger = SoundSystemConfig.getLogger();
    }
    
    public boolean initialize(final URL url) {
        this.initialized(true, false);
        this.cleanup();
        if (url == null) {
            this.errorMessage("url null in method 'initialize'");
            this.cleanup();
            return false;
        }
        this.myAudioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(url.openStream()));
        this.endOfStream(true, false);
        this.initialized(true, true);
        return true;
    }
    
    public boolean initialized() {
        return this.initialized(false, false);
    }
    
    public SoundBuffer read() {
        if (this.myAudioInputStream == null) {
            return null;
        }
        final AudioFormat format = this.myAudioInputStream.getFormat();
        if (format == null) {
            this.errorMessage("Audio Format null in method 'read'");
            return null;
        }
        byte[] trimArray = new byte[SoundSystemConfig.getStreamingBufferSize()];
        while (!this.endOfStream(false, false) && 0 < trimArray.length) {
            if (this.myAudioInputStream.read(trimArray, 0, trimArray.length - 0) <= 0) {
                this.endOfStream(true, true);
                break;
            }
        }
        if (0 <= 0) {
            return null;
        }
        if (0 < trimArray.length) {
            trimArray = trimArray(trimArray, 0);
        }
        return new SoundBuffer(convertAudioBytes(trimArray, format.getSampleSizeInBits() == 16), format);
    }
    
    public SoundBuffer readAll() {
        if (this.myAudioInputStream == null) {
            this.errorMessage("Audio input stream null in method 'readAll'");
            return null;
        }
        final AudioFormat format = this.myAudioInputStream.getFormat();
        if (format == null) {
            this.errorMessage("Audio Format null in method 'readAll'");
            return null;
        }
        byte[] appendByteArrays = null;
        if (format.getChannels() * (int)this.myAudioInputStream.getFrameLength() * format.getSampleSizeInBits() / 8 > 0) {
            appendByteArrays = new byte[format.getChannels() * (int)this.myAudioInputStream.getFrameLength() * format.getSampleSizeInBits() / 8];
            while (this.myAudioInputStream.read(appendByteArrays, 0, appendByteArrays.length - 0) != -1 && 0 < appendByteArrays.length) {}
        }
        else {
            final byte[] array = new byte[SoundSystemConfig.getFileChunkSize()];
            while (!this.endOfStream(false, false) && 0 < SoundSystemConfig.getMaxFileSize()) {
                while (0 < array.length) {
                    if (this.myAudioInputStream.read(array, 0, array.length - 0) <= 0) {
                        this.endOfStream(true, true);
                        break;
                    }
                }
                appendByteArrays = appendByteArrays(appendByteArrays, array, 0);
            }
        }
        final SoundBuffer soundBuffer = new SoundBuffer(convertAudioBytes(appendByteArrays, format.getSampleSizeInBits() == 16), format);
        this.myAudioInputStream.close();
        return soundBuffer;
    }
    
    public boolean endOfStream() {
        return this.endOfStream(false, false);
    }
    
    public void cleanup() {
        if (this.myAudioInputStream != null) {
            this.myAudioInputStream.close();
        }
        this.myAudioInputStream = null;
    }
    
    public AudioFormat getAudioFormat() {
        if (this.myAudioInputStream == null) {
            return null;
        }
        return this.myAudioInputStream.getFormat();
    }
    
    private synchronized boolean initialized(final boolean b, final boolean initialized) {
        if (b) {
            this.initialized = initialized;
        }
        return this.initialized;
    }
    
    private synchronized boolean endOfStream(final boolean b, final boolean endOfStream) {
        if (b) {
            this.endOfStream = endOfStream;
        }
        return this.endOfStream;
    }
    
    private static byte[] trimArray(final byte[] array, final int n) {
        Object o = null;
        if (array != null && array.length > n) {
            o = new byte[n];
            System.arraycopy(array, 0, o, 0, n);
        }
        return (byte[])o;
    }
    
    private static byte[] convertAudioBytes(final byte[] array, final boolean b) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(array.length);
        allocateDirect.order(ByteOrder.nativeOrder());
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        if (b) {
            final ShortBuffer shortBuffer = allocateDirect.asShortBuffer();
            final ShortBuffer shortBuffer2 = wrap.asShortBuffer();
            while (shortBuffer2.hasRemaining()) {
                shortBuffer.put(shortBuffer2.get());
            }
        }
        else {
            while (wrap.hasRemaining()) {
                allocateDirect.put(wrap.get());
            }
        }
        allocateDirect.rewind();
        if (!allocateDirect.hasArray()) {
            final byte[] array2 = new byte[allocateDirect.capacity()];
            allocateDirect.get(array2);
            allocateDirect.clear();
            return array2;
        }
        return allocateDirect.array();
    }
    
    private static byte[] appendByteArrays(final byte[] array, final byte[] array2, final int n) {
        if (array == null && array2 == null) {
            return null;
        }
        byte[] array3;
        if (array == null) {
            array3 = new byte[n];
            System.arraycopy(array2, 0, array3, 0, n);
        }
        else if (array2 == null) {
            array3 = new byte[array.length];
            System.arraycopy(array, 0, array3, 0, array.length);
        }
        else {
            array3 = new byte[array.length + n];
            System.arraycopy(array, 0, array3, 0, array.length);
            System.arraycopy(array2, 0, array3, array.length, n);
        }
        return array3;
    }
    
    private void errorMessage(final String s) {
        this.logger.errorMessage("CodecWav", s, 0);
    }
    
    private void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
