package paulscode.sound.codecs;

import java.net.*;
import javax.sound.sampled.*;
import com.jcraft.jogg.*;
import com.jcraft.jorbis.*;
import paulscode.sound.*;
import java.io.*;

public class CodecJOrbis implements ICodec
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private URL url;
    private URLConnection urlConnection;
    private InputStream inputStream;
    private AudioFormat audioFormat;
    private boolean endOfStream;
    private boolean initialized;
    private byte[] buffer;
    private int bufferSize;
    private int count;
    private int index;
    private int convertedBufferSize;
    private byte[] convertedBuffer;
    private float[][][] pcmInfo;
    private int[] pcmIndex;
    private Packet joggPacket;
    private Page joggPage;
    private StreamState joggStreamState;
    private SyncState joggSyncState;
    private DspState jorbisDspState;
    private Block jorbisBlock;
    private Comment jorbisComment;
    private Info jorbisInfo;
    private SoundSystemLogger logger;
    
    public CodecJOrbis() {
        this.urlConnection = null;
        this.endOfStream = false;
        this.initialized = false;
        this.buffer = null;
        this.count = 0;
        this.index = 0;
        this.convertedBuffer = null;
        this.joggPacket = new Packet();
        this.joggPage = new Page();
        this.joggStreamState = new StreamState();
        this.joggSyncState = new SyncState();
        this.jorbisDspState = new DspState();
        this.jorbisBlock = new Block(this.jorbisDspState);
        this.jorbisComment = new Comment();
        this.jorbisInfo = new Info();
        this.logger = SoundSystemConfig.getLogger();
    }
    
    public void reverseByteOrder(final boolean b) {
    }
    
    public boolean initialize(final URL url) {
        this.initialized(true, false);
        if (this.joggStreamState != null) {
            this.joggStreamState.clear();
        }
        if (this.jorbisBlock != null) {
            this.jorbisBlock.clear();
        }
        if (this.jorbisDspState != null) {
            this.jorbisDspState.clear();
        }
        if (this.jorbisInfo != null) {
            this.jorbisInfo.clear();
        }
        if (this.joggSyncState != null) {
            this.joggSyncState.clear();
        }
        if (this.inputStream != null) {
            this.inputStream.close();
        }
        this.url = url;
        this.bufferSize = 8192;
        this.buffer = null;
        this.count = 0;
        this.index = 0;
        this.joggStreamState = new StreamState();
        this.jorbisBlock = new Block(this.jorbisDspState);
        this.jorbisDspState = new DspState();
        this.jorbisInfo = new Info();
        this.joggSyncState = new SyncState();
        this.urlConnection = url.openConnection();
        if (this.urlConnection != null) {
            this.inputStream = this.urlConnection.getInputStream();
        }
        this.endOfStream(true, false);
        this.joggSyncState.init();
        this.joggSyncState.buffer(this.bufferSize);
        this.buffer = this.joggSyncState.data;
        if (!this.readHeader()) {
            this.errorMessage("Error reading the header");
            return false;
        }
        this.convertedBufferSize = this.bufferSize * 2;
        this.jorbisDspState.synthesis_init(this.jorbisInfo);
        this.jorbisBlock.init(this.jorbisDspState);
        this.audioFormat = new AudioFormat((float)this.jorbisInfo.rate, 16, this.jorbisInfo.channels, true, false);
        this.pcmInfo = new float[1][][];
        this.pcmIndex = new int[this.jorbisInfo.channels];
        this.initialized(true, true);
        return true;
    }
    
    public boolean initialized() {
        return this.initialized(false, false);
    }
    
    public SoundBuffer read() {
        byte[] array = null;
        while (!this.endOfStream(false, false) && (array == null || array.length < SoundSystemConfig.getStreamingBufferSize())) {
            if (array == null) {
                array = this.readBytes();
            }
            else {
                array = appendByteArrays(array, this.readBytes());
            }
        }
        if (array == null) {
            return null;
        }
        return new SoundBuffer(array, this.audioFormat);
    }
    
    public SoundBuffer readAll() {
        byte[] array = null;
        while (!this.endOfStream(false, false)) {
            if (array == null) {
                array = this.readBytes();
            }
            else {
                array = appendByteArrays(array, this.readBytes());
            }
        }
        if (array == null) {
            return null;
        }
        return new SoundBuffer(array, this.audioFormat);
    }
    
    public boolean endOfStream() {
        return this.endOfStream(false, false);
    }
    
    public void cleanup() {
        this.joggStreamState.clear();
        this.jorbisBlock.clear();
        this.jorbisDspState.clear();
        this.jorbisInfo.clear();
        this.joggSyncState.clear();
        if (this.inputStream != null) {
            this.inputStream.close();
        }
        this.joggStreamState = null;
        this.jorbisBlock = null;
        this.jorbisDspState = null;
        this.jorbisInfo = null;
        this.joggSyncState = null;
        this.inputStream = null;
    }
    
    public AudioFormat getAudioFormat() {
        return this.audioFormat;
    }
    
    private boolean readHeader() throws IOException {
        this.index = this.joggSyncState.buffer(this.bufferSize);
        this.inputStream.read(this.joggSyncState.data, this.index, this.bufferSize);
        if (0 < 0) {}
        this.joggSyncState.wrote(0);
        if (this.joggSyncState.pageout(this.joggPage) != 1) {
            if (0 < this.bufferSize) {
                return true;
            }
            this.errorMessage("Ogg header not recognized in method 'readHeader'.");
            return false;
        }
        else {
            this.joggStreamState.init(this.joggPage.serialno());
            this.jorbisInfo.init();
            this.jorbisComment.init();
            if (this.joggStreamState.pagein(this.joggPage) < 0) {
                this.errorMessage("Problem with first Ogg header page in method 'readHeader'.");
                return false;
            }
            if (this.joggStreamState.packetout(this.joggPacket) != 1) {
                this.errorMessage("Problem with first Ogg header packet in method 'readHeader'.");
                return false;
            }
            if (this.jorbisInfo.synthesis_headerin(this.jorbisComment, this.joggPacket) < 0) {
                this.errorMessage("File does not contain Vorbis header in method 'readHeader'.");
                return false;
            }
            while (0 < 2) {
                while (0 < 2) {
                    final int pageout = this.joggSyncState.pageout(this.joggPage);
                    if (pageout == 0) {
                        break;
                    }
                    if (pageout != 1) {
                        continue;
                    }
                    this.joggStreamState.pagein(this.joggPage);
                    while (0 < 2) {
                        final int packetout = this.joggStreamState.packetout(this.joggPacket);
                        if (packetout == 0) {
                            break;
                        }
                        if (packetout == -1) {
                            this.errorMessage("Secondary Ogg header corrupt in method 'readHeader'.");
                            return false;
                        }
                        this.jorbisInfo.synthesis_headerin(this.jorbisComment, this.joggPacket);
                        int n = 0;
                        ++n;
                    }
                }
                this.index = this.joggSyncState.buffer(this.bufferSize);
                this.inputStream.read(this.joggSyncState.data, this.index, this.bufferSize);
                if (0 < 0) {}
                if (!false && 0 < 2) {
                    this.errorMessage("End of file reached before finished readingOgg header in method 'readHeader'");
                    return false;
                }
                this.joggSyncState.wrote(0);
            }
            this.index = this.joggSyncState.buffer(this.bufferSize);
            this.buffer = this.joggSyncState.data;
            return true;
        }
    }
    
    private byte[] readBytes() {
        if (!this.initialized(false, false)) {
            return null;
        }
        if (this.endOfStream(false, false)) {
            return null;
        }
        if (this.convertedBuffer == null) {
            this.convertedBuffer = new byte[this.convertedBufferSize];
        }
        byte[] appendByteArrays = null;
        switch (this.joggSyncState.pageout(this.joggPage)) {
            case -1:
            case 0: {
                break;
            }
            default: {
                this.joggStreamState.pagein(this.joggPage);
                if (this.joggPage.granulepos() == 0L) {
                    this.endOfStream(true, true);
                    return null;
                }
            Label_0152:
                while (true) {
                    switch (this.joggStreamState.packetout(this.joggPacket)) {
                        case 0: {
                            break Label_0152;
                        }
                        case -1: {
                            continue;
                        }
                        default: {
                            if (this.jorbisBlock.synthesis(this.joggPacket) == 0) {
                                this.jorbisDspState.synthesis_blockin(this.jorbisBlock);
                            }
                            int synthesis_pcmout;
                            while ((synthesis_pcmout = this.jorbisDspState.synthesis_pcmout(this.pcmInfo, this.pcmIndex)) > 0) {
                                final float[][] array = this.pcmInfo[0];
                                final int n = (synthesis_pcmout < this.convertedBufferSize) ? synthesis_pcmout : this.convertedBufferSize;
                                while (0 < this.jorbisInfo.channels) {
                                    final int n2 = this.pcmIndex[0];
                                    while (0 < n) {
                                        final int n3 = (int)(array[0][n2 + 0] * 32767.0);
                                        if (-32768 > 32767) {}
                                        if (-32768 < -32768) {}
                                        if (-32768 < 0) {}
                                        this.convertedBuffer[0] = (byte)(-32768);
                                        this.convertedBuffer[1] = (byte)16777088;
                                        final int n4 = 0 + 2 * this.jorbisInfo.channels;
                                        int n5 = 0;
                                        ++n5;
                                    }
                                    int n6 = 0;
                                    ++n6;
                                }
                                this.jorbisDspState.synthesis_read(n);
                                appendByteArrays = appendByteArrays(appendByteArrays, this.convertedBuffer, 2 * this.jorbisInfo.channels * n);
                            }
                            continue;
                        }
                    }
                }
                if (this.joggPage.eos() != 0) {
                    this.endOfStream(true, true);
                    break;
                }
                break;
            }
        }
        if (!this.endOfStream(false, false)) {
            this.index = this.joggSyncState.buffer(this.bufferSize);
            this.buffer = this.joggSyncState.data;
            this.count = this.inputStream.read(this.buffer, this.index, this.bufferSize);
            if (this.count == -1) {
                return appendByteArrays;
            }
            this.joggSyncState.wrote(this.count);
            if (this.count == 0) {
                this.endOfStream(true, true);
            }
        }
        return appendByteArrays;
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
    
    private static byte[] appendByteArrays(final byte[] array, final byte[] array2, final int n) {
        if (array2 != null && array2.length != 0) {
            if (array2.length < n) {
                final int length = array2.length;
            }
        }
        if (array == null && (array2 == null || 0 <= 0)) {
            return null;
        }
        byte[] array3;
        if (array == null) {
            array3 = new byte[0];
            System.arraycopy(array2, 0, array3, 0, 0);
        }
        else if (array2 == null || 0 <= 0) {
            array3 = new byte[array.length];
            System.arraycopy(array, 0, array3, 0, array.length);
        }
        else {
            array3 = new byte[array.length + 0];
            System.arraycopy(array, 0, array3, 0, array.length);
            System.arraycopy(array2, 0, array3, array.length, 0);
        }
        return array3;
    }
    
    private static byte[] appendByteArrays(final byte[] array, final byte[] array2) {
        if (array == null && array2 == null) {
            return null;
        }
        byte[] array3;
        if (array == null) {
            array3 = new byte[array2.length];
            System.arraycopy(array2, 0, array3, 0, array2.length);
        }
        else if (array2 == null) {
            array3 = new byte[array.length];
            System.arraycopy(array, 0, array3, 0, array.length);
        }
        else {
            array3 = new byte[array.length + array2.length];
            System.arraycopy(array, 0, array3, 0, array.length);
            System.arraycopy(array2, 0, array3, array.length, array2.length);
        }
        return array3;
    }
    
    private void errorMessage(final String s) {
        this.logger.errorMessage("CodecJOrbis", s, 0);
    }
    
    private void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
