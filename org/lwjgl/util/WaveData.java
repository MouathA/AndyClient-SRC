package org.lwjgl.util;

import java.net.*;
import com.sun.media.sound.*;
import java.io.*;
import javax.sound.sampled.*;
import java.nio.*;

public class WaveData
{
    public final ByteBuffer data;
    public final int format;
    public final int samplerate;
    static final boolean $assertionsDisabled;
    
    private WaveData(final ByteBuffer data, final int format, final int samplerate) {
        this.data = data;
        this.format = format;
        this.samplerate = samplerate;
    }
    
    public void dispose() {
        this.data.clear();
    }
    
    public static WaveData create(final URL url) {
        return create(new WaveFileReader().getAudioInputStream(new BufferedInputStream(url.openStream())));
    }
    
    public static WaveData create(final String s) {
        return create(Thread.currentThread().getContextClassLoader().getResource(s));
    }
    
    public static WaveData create(final InputStream inputStream) {
        return create(AudioSystem.getAudioInputStream(inputStream));
    }
    
    public static WaveData create(final byte[] array) {
        return create(AudioSystem.getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(array))));
    }
    
    public static WaveData create(final ByteBuffer byteBuffer) {
        byte[] array;
        if (byteBuffer.hasArray()) {
            array = byteBuffer.array();
        }
        else {
            array = new byte[byteBuffer.capacity()];
            byteBuffer.get(array);
        }
        return create(array);
    }
    
    public static WaveData create(final AudioInputStream audioInputStream) {
        final AudioFormat format = audioInputStream.getFormat();
        if (format.getChannels() == 1) {
            if (format.getSampleSizeInBits() != 8) {
                if (format.getSampleSizeInBits() != 16) {
                    assert false : "Illegal sample size";
                }
            }
        }
        else if (format.getChannels() == 2) {
            if (format.getSampleSizeInBits() != 8) {
                if (format.getSampleSizeInBits() != 16) {
                    assert false : "Illegal sample size";
                }
            }
        }
        else {
            assert false : "Only mono or stereo is supported";
        }
        if (audioInputStream.available() <= 0) {
            final int n = audioInputStream.getFormat().getChannels() * (int)audioInputStream.getFrameLength() * audioInputStream.getFormat().getSampleSizeInBits() / 8;
        }
        final byte[] array = new byte[audioInputStream.available()];
        while (audioInputStream.read(array, 0, array.length - 0) != -1 && 0 < array.length) {}
        final WaveData waveData = new WaveData(convertAudioBytes(array, format.getSampleSizeInBits() == 16, format.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN), 4355, (int)format.getSampleRate());
        audioInputStream.close();
        return waveData;
    }
    
    private static ByteBuffer convertAudioBytes(final byte[] array, final boolean b, final ByteOrder byteOrder) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(array.length);
        allocateDirect.order(ByteOrder.nativeOrder());
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        wrap.order(byteOrder);
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
        return allocateDirect;
    }
    
    static {
        $assertionsDisabled = !WaveData.class.desiredAssertionStatus();
    }
}
