package paulscode.sound;

import java.net.*;
import java.util.*;
import javax.sound.midi.*;

public class MidiChannel implements MetaEventListener
{
    private SoundSystemLogger logger;
    private FilenameURL filenameURL;
    private String sourcename;
    private static final int CHANGE_VOLUME = 7;
    private static final int END_OF_TRACK = 47;
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    private MidiDevice synthDevice;
    private Sequence sequence;
    private boolean toLoop;
    private float gain;
    private boolean loading;
    private LinkedList sequenceQueue;
    private final Object sequenceQueueLock;
    protected float fadeOutGain;
    protected float fadeInGain;
    protected long fadeOutMilis;
    protected long fadeInMilis;
    protected long lastFadeCheck;
    private FadeThread fadeThread;
    
    public MidiChannel(final boolean looping, final String s, final String s2) {
        this.sequencer = null;
        this.synthesizer = null;
        this.synthDevice = null;
        this.sequence = null;
        this.toLoop = true;
        this.gain = 1.0f;
        this.loading = true;
        this.sequenceQueue = null;
        this.sequenceQueueLock = new Object();
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.fadeThread = null;
        this.loading(true, true);
        this.logger = SoundSystemConfig.getLogger();
        this.filenameURL(true, new FilenameURL(s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.init();
        this.loading(true, false);
    }
    
    public MidiChannel(final boolean looping, final String s, final URL url, final String s2) {
        this.sequencer = null;
        this.synthesizer = null;
        this.synthDevice = null;
        this.sequence = null;
        this.toLoop = true;
        this.gain = 1.0f;
        this.loading = true;
        this.sequenceQueue = null;
        this.sequenceQueueLock = new Object();
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.fadeThread = null;
        this.loading(true, true);
        this.logger = SoundSystemConfig.getLogger();
        this.filenameURL(true, new FilenameURL(url, s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.init();
        this.loading(true, false);
    }
    
    public MidiChannel(final boolean looping, final String s, final FilenameURL filenameURL) {
        this.sequencer = null;
        this.synthesizer = null;
        this.synthDevice = null;
        this.sequence = null;
        this.toLoop = true;
        this.gain = 1.0f;
        this.loading = true;
        this.sequenceQueue = null;
        this.sequenceQueueLock = new Object();
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.fadeThread = null;
        this.loading(true, true);
        this.logger = SoundSystemConfig.getLogger();
        this.filenameURL(true, filenameURL);
        this.sourcename(true, s);
        this.setLooping(looping);
        this.init();
        this.loading(true, false);
    }
    
    private void init() {
        this.getSequencer();
        this.setSequence(this.filenameURL(false, null).getURL());
        this.getSynthesizer();
        this.resetGain();
    }
    
    public void cleanup() {
        this.loading(true, true);
        this.setLooping(true);
        if (this.sequencer != null) {
            this.sequencer.stop();
            this.sequencer.close();
            this.sequencer.removeMetaEventListener(this);
        }
        this.logger = null;
        this.sequencer = null;
        this.synthesizer = null;
        this.sequence = null;
        // monitorenter(sequenceQueueLock = this.sequenceQueueLock)
        if (this.sequenceQueue != null) {
            this.sequenceQueue.clear();
        }
        this.sequenceQueue = null;
        // monitorexit(sequenceQueueLock)
        if (this.fadeThread != null) {
            this.fadeThread.kill();
            this.fadeThread.interrupt();
            if (!true) {
                while (0 < 50) {
                    if (!this.fadeThread.alive()) {
                        break;
                    }
                    Thread.sleep(100L);
                    int n = 0;
                    ++n;
                }
            }
            if (true || this.fadeThread.alive()) {
                this.errorMessage("MIDI fade effects thread did not die!");
                this.message("Ignoring errors... continuing clean-up.");
            }
        }
        this.fadeThread = null;
        this.loading(true, false);
    }
    
    public void queueSound(final FilenameURL filenameURL) {
        if (filenameURL == null) {
            this.errorMessage("Filename/URL not specified in method 'queueSound'");
            return;
        }
        // monitorenter(sequenceQueueLock = this.sequenceQueueLock)
        if (this.sequenceQueue == null) {
            this.sequenceQueue = new LinkedList();
        }
        this.sequenceQueue.add(filenameURL);
    }
    // monitorexit(sequenceQueueLock)
    
    public void dequeueSound(final String s) {
        if (s == null || s.equals("")) {
            this.errorMessage("Filename not specified in method 'dequeueSound'");
            return;
        }
        // monitorenter(sequenceQueueLock = this.sequenceQueueLock)
        if (this.sequenceQueue != null) {
            final ListIterator listIterator = this.sequenceQueue.listIterator();
            while (listIterator.hasNext()) {
                if (listIterator.next().getFilename().equals(s)) {
                    listIterator.remove();
                    break;
                }
            }
        }
    }
    // monitorexit(sequenceQueueLock)
    
    public void fadeOut(final FilenameURL filenameURL, final long fadeOutMilis) {
        if (fadeOutMilis < 0L) {
            this.errorMessage("Miliseconds may not be negative in method 'fadeOut'.");
            return;
        }
        this.fadeOutMilis = fadeOutMilis;
        this.fadeInMilis = 0L;
        this.fadeOutGain = 1.0f;
        this.lastFadeCheck = System.currentTimeMillis();
        // monitorenter(sequenceQueueLock = this.sequenceQueueLock)
        if (this.sequenceQueue != null) {
            this.sequenceQueue.clear();
        }
        if (filenameURL != null) {
            if (this.sequenceQueue == null) {
                this.sequenceQueue = new LinkedList();
            }
            this.sequenceQueue.add(filenameURL);
        }
        // monitorexit(sequenceQueueLock)
        if (this.fadeThread == null) {
            (this.fadeThread = new FadeThread(null)).start();
        }
        this.fadeThread.interrupt();
    }
    
    public void fadeOutIn(final FilenameURL filenameURL, final long fadeOutMilis, final long fadeInMilis) {
        if (filenameURL == null) {
            this.errorMessage("Filename/URL not specified in method 'fadeOutIn'.");
            return;
        }
        if (fadeOutMilis < 0L || fadeInMilis < 0L) {
            this.errorMessage("Miliseconds may not be negative in method 'fadeOutIn'.");
            return;
        }
        this.fadeOutMilis = fadeOutMilis;
        this.fadeInMilis = fadeInMilis;
        this.fadeOutGain = 1.0f;
        this.lastFadeCheck = System.currentTimeMillis();
        // monitorenter(sequenceQueueLock = this.sequenceQueueLock)
        if (this.sequenceQueue == null) {
            this.sequenceQueue = new LinkedList();
        }
        this.sequenceQueue.clear();
        this.sequenceQueue.add(filenameURL);
        // monitorexit(sequenceQueueLock)
        if (this.fadeThread == null) {
            (this.fadeThread = new FadeThread(null)).start();
        }
        this.fadeThread.interrupt();
    }
    
    private synchronized boolean checkFadeOut() {
        if (this.fadeOutGain == -1.0f && this.fadeInGain == 1.0f) {
            return false;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - this.lastFadeCheck;
        this.lastFadeCheck = currentTimeMillis;
        if (this.fadeOutGain < 0.0f) {
            if (this.fadeInGain < 1.0f) {
                this.fadeOutGain = -1.0f;
                if (this.fadeInMilis == 0L) {
                    this.fadeOutGain = -1.0f;
                    this.fadeInGain = 1.0f;
                }
                else {
                    this.fadeInGain += n / (float)this.fadeInMilis;
                    if (this.fadeInGain >= 1.0f) {
                        this.fadeOutGain = -1.0f;
                        this.fadeInGain = 1.0f;
                    }
                }
                this.resetGain();
            }
            return false;
        }
        if (this.fadeOutMilis == 0L) {
            this.fadeOutGain = 0.0f;
            this.fadeInGain = 0.0f;
            if (!this.incrementSequence()) {
                this.stop();
            }
            this.rewind();
            this.resetGain();
            return false;
        }
        this.fadeOutGain -= n / (float)this.fadeOutMilis;
        if (this.fadeOutGain <= 0.0f) {
            this.fadeOutGain = -1.0f;
            this.fadeInGain = 0.0f;
            if (!this.incrementSequence()) {
                this.stop();
            }
            this.rewind();
            this.resetGain();
            return false;
        }
        this.resetGain();
        return true;
    }
    
    private boolean incrementSequence() {
        // monitorenter(sequenceQueueLock = this.sequenceQueueLock)
        if (this.sequenceQueue == null || this.sequenceQueue.size() <= 0) {
            // monitorexit(sequenceQueueLock)
            return false;
        }
        this.filenameURL(true, this.sequenceQueue.remove(0));
        this.loading(true, true);
        if (this.sequencer == null) {
            this.getSequencer();
        }
        else {
            this.sequencer.stop();
            this.sequencer.setMicrosecondPosition(0L);
            this.sequencer.removeMetaEventListener(this);
            Thread.sleep(100L);
        }
        if (this.sequencer == null) {
            this.errorMessage("Unable to set the sequence in method 'incrementSequence', because there wasn't a sequencer to use.");
            this.loading(true, false);
            // monitorexit(sequenceQueueLock)
            return false;
        }
        this.setSequence(this.filenameURL(false, null).getURL());
        this.sequencer.start();
        this.resetGain();
        this.sequencer.addMetaEventListener(this);
        this.loading(true, false);
        // monitorexit(sequenceQueueLock)
        return true;
    }
    
    public void play() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            this.sequencer.start();
            this.sequencer.addMetaEventListener(this);
        }
    }
    
    public void stop() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            this.sequencer.stop();
            this.sequencer.setMicrosecondPosition(0L);
            this.sequencer.removeMetaEventListener(this);
        }
    }
    
    public void pause() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            this.sequencer.stop();
        }
    }
    
    public void rewind() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            this.sequencer.setMicrosecondPosition(0L);
        }
    }
    
    public void setVolume(final float gain) {
        this.gain = gain;
        this.resetGain();
    }
    
    public float getVolume() {
        return this.gain;
    }
    
    public void switchSource(final boolean looping, final String s, final String s2) {
        this.loading(true, true);
        this.filenameURL(true, new FilenameURL(s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.reset();
        this.loading(true, false);
    }
    
    public void switchSource(final boolean looping, final String s, final URL url, final String s2) {
        this.loading(true, true);
        this.filenameURL(true, new FilenameURL(url, s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.reset();
        this.loading(true, false);
    }
    
    public void switchSource(final boolean looping, final String s, final FilenameURL filenameURL) {
        this.loading(true, true);
        this.filenameURL(true, filenameURL);
        this.sourcename(true, s);
        this.setLooping(looping);
        this.reset();
        this.loading(true, false);
    }
    
    private void reset() {
        // monitorenter(sequenceQueueLock = this.sequenceQueueLock)
        if (this.sequenceQueue != null) {
            this.sequenceQueue.clear();
        }
        // monitorexit(sequenceQueueLock)
        if (this.sequencer == null) {
            this.getSequencer();
        }
        else {
            this.sequencer.stop();
            this.sequencer.setMicrosecondPosition(0L);
            this.sequencer.removeMetaEventListener(this);
            Thread.sleep(100L);
        }
        if (this.sequencer == null) {
            this.errorMessage("Unable to set the sequence in method 'reset', because there wasn't a sequencer to use.");
            return;
        }
        this.setSequence(this.filenameURL(false, null).getURL());
        this.sequencer.start();
        this.resetGain();
        this.sequencer.addMetaEventListener(this);
    }
    
    public void setLooping(final boolean b) {
        this.toLoop(true, b);
    }
    
    public boolean getLooping() {
        return this.toLoop(false, false);
    }
    
    private synchronized boolean toLoop(final boolean b, final boolean toLoop) {
        if (b) {
            this.toLoop = toLoop;
        }
        return this.toLoop;
    }
    
    public boolean loading() {
        return this.loading(false, false);
    }
    
    private synchronized boolean loading(final boolean b, final boolean loading) {
        if (b) {
            this.loading = loading;
        }
        return this.loading;
    }
    
    public void setSourcename(final String s) {
        this.sourcename(true, s);
    }
    
    public String getSourcename() {
        return this.sourcename(false, null);
    }
    
    private synchronized String sourcename(final boolean b, final String sourcename) {
        if (b) {
            this.sourcename = sourcename;
        }
        return this.sourcename;
    }
    
    public void setFilenameURL(final FilenameURL filenameURL) {
        this.filenameURL(true, filenameURL);
    }
    
    public String getFilename() {
        return this.filenameURL(false, null).getFilename();
    }
    
    public FilenameURL getFilenameURL() {
        return this.filenameURL(false, null);
    }
    
    private synchronized FilenameURL filenameURL(final boolean b, final FilenameURL filenameURL) {
        if (b) {
            this.filenameURL = filenameURL;
        }
        return this.filenameURL;
    }
    
    public void meta(final MetaMessage metaMessage) {
        if (metaMessage.getType() == 47) {
            SoundSystemConfig.notifyEOS(this.sourcename, this.sequenceQueue.size());
            if (this.toLoop) {
                if (!this.checkFadeOut()) {
                    if (!this.incrementSequence()) {
                        this.sequencer.setMicrosecondPosition(0L);
                        this.sequencer.start();
                        this.resetGain();
                    }
                }
                else if (this.sequencer != null) {
                    this.sequencer.setMicrosecondPosition(0L);
                    this.sequencer.start();
                    this.resetGain();
                }
            }
            else if (!this.checkFadeOut()) {
                if (!this.incrementSequence()) {
                    this.sequencer.stop();
                    this.sequencer.setMicrosecondPosition(0L);
                    this.sequencer.removeMetaEventListener(this);
                }
            }
            else {
                this.sequencer.stop();
                this.sequencer.setMicrosecondPosition(0L);
                this.sequencer.removeMetaEventListener(this);
            }
        }
    }
    
    public void resetGain() {
        if (this.gain < 0.0f) {
            this.gain = 0.0f;
        }
        if (this.gain > 1.0f) {
            this.gain = 1.0f;
        }
        final int n = (int)(this.gain * SoundSystemConfig.getMasterGain() * Math.abs(this.fadeOutGain) * this.fadeInGain * 127.0f);
        if (this.synthesizer != null) {
            final javax.sound.midi.MidiChannel[] channels = this.synthesizer.getChannels();
            while (channels != null && 0 < channels.length) {
                channels[0].controlChange(7, n);
                int n2 = 0;
                ++n2;
            }
        }
        else if (this.synthDevice != null) {
            final ShortMessage shortMessage = new ShortMessage();
            while (0 < 16) {
                shortMessage.setMessage(176, 0, 7, n);
                this.synthDevice.getReceiver().send(shortMessage, -1L);
                int n2 = 0;
                ++n2;
            }
        }
        else if (this.sequencer != null && this.sequencer instanceof Synthesizer) {
            this.synthesizer = (Synthesizer)this.sequencer;
            final javax.sound.midi.MidiChannel[] channels2 = this.synthesizer.getChannels();
            while (channels2 != null && 0 < channels2.length) {
                channels2[0].controlChange(7, n);
                int n2 = 0;
                ++n2;
            }
        }
        else {
            final Receiver receiver = MidiSystem.getReceiver();
            final ShortMessage shortMessage2 = new ShortMessage();
            while (0 < 16) {
                shortMessage2.setMessage(176, 0, 7, n);
                receiver.send(shortMessage2, -1L);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    private void getSequencer() {
        this.sequencer = MidiSystem.getSequencer();
        if (this.sequencer != null) {
            this.sequencer.getTransmitter();
            this.sequencer.open();
        }
        if (this.sequencer == null) {
            this.sequencer = this.openSequencer("Real Time Sequencer");
        }
        if (this.sequencer == null) {
            this.sequencer = this.openSequencer("Java Sound Sequencer");
        }
        if (this.sequencer == null) {
            this.errorMessage("Failed to find an available MIDI sequencer");
        }
    }
    
    private void setSequence(final URL url) {
        if (this.sequencer == null) {
            this.errorMessage("Unable to update the sequence in method 'setSequence', because variable 'sequencer' is null");
            return;
        }
        if (url == null) {
            this.errorMessage("Unable to load Midi file in method 'setSequence'.");
            return;
        }
        this.sequence = MidiSystem.getSequence(url);
        if (this.sequence == null) {
            this.errorMessage("MidiSystem 'getSequence' method returned null in method 'setSequence'.");
        }
        else {
            this.sequencer.setSequence(this.sequence);
        }
    }
    
    private void getSynthesizer() {
        if (this.sequencer == null) {
            this.errorMessage("Unable to load a Synthesizer in method 'getSynthesizer', because variable 'sequencer' is null");
            return;
        }
        final String overrideMIDISynthesizer = SoundSystemConfig.getOverrideMIDISynthesizer();
        if (overrideMIDISynthesizer != null && !overrideMIDISynthesizer.equals("")) {
            this.synthDevice = this.openMidiDevice(overrideMIDISynthesizer);
            if (this.synthDevice != null) {
                this.sequencer.getTransmitter().setReceiver(this.synthDevice.getReceiver());
                return;
            }
        }
        if (this.sequencer instanceof Synthesizer) {
            this.synthesizer = (Synthesizer)this.sequencer;
        }
        else {
            (this.synthesizer = MidiSystem.getSynthesizer()).open();
            if (this.synthesizer == null) {
                this.synthDevice = this.openMidiDevice("Java Sound Synthesizer");
                if (this.synthDevice == null) {
                    this.synthDevice = this.openMidiDevice("Microsoft GS Wavetable");
                }
                if (this.synthDevice == null) {
                    this.synthDevice = this.openMidiDevice("Gervill");
                }
                if (this.synthDevice == null) {
                    this.errorMessage("Failed to find an available MIDI synthesizer");
                    return;
                }
            }
            if (this.synthesizer == null) {
                this.sequencer.getTransmitter().setReceiver(this.synthDevice.getReceiver());
            }
            else if (this.synthesizer.getDefaultSoundbank() == null) {
                this.sequencer.getTransmitter().setReceiver(MidiSystem.getReceiver());
            }
            else {
                this.sequencer.getTransmitter().setReceiver(this.synthesizer.getReceiver());
            }
        }
    }
    
    private Sequencer openSequencer(final String s) {
        final Sequencer sequencer = (Sequencer)this.openMidiDevice(s);
        if (sequencer == null) {
            return null;
        }
        sequencer.getTransmitter();
        return sequencer;
    }
    
    private MidiDevice openMidiDevice(final String s) {
        this.message("Searching for MIDI device with name containing '" + s + "'");
        final MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
        while (0 < midiDeviceInfo.length) {
            final MidiDevice midiDevice = MidiSystem.getMidiDevice(midiDeviceInfo[0]);
            if (midiDevice != null && midiDeviceInfo[0].getName().contains(s)) {
                this.message("    Found MIDI device named '" + midiDeviceInfo[0].getName() + "'");
                if (midiDevice instanceof Synthesizer) {
                    this.message("        *this is a Synthesizer instance");
                }
                if (midiDevice instanceof Sequencer) {
                    this.message("        *this is a Sequencer instance");
                }
                midiDevice.open();
                return midiDevice;
            }
            int n = 0;
            ++n;
        }
        this.message("    MIDI device not found");
        return null;
    }
    
    protected void message(final String s) {
        this.logger.message(s, 0);
    }
    
    protected void importantMessage(final String s) {
        this.logger.importantMessage(s, 0);
    }
    
    protected boolean errorCheck(final boolean b, final String s) {
        return this.logger.errorCheck(b, "MidiChannel", s, 0);
    }
    
    protected void errorMessage(final String s) {
        this.logger.errorMessage("MidiChannel", s, 0);
    }
    
    protected void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
    
    static boolean access$100(final MidiChannel midiChannel) {
        return midiChannel.checkFadeOut();
    }
    
    private class FadeThread extends SimpleThread
    {
        final MidiChannel this$0;
        
        private FadeThread(final MidiChannel this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void run() {
            while (!this.dying()) {
                if (this.this$0.fadeOutGain == -1.0f && this.this$0.fadeInGain == 1.0f) {
                    this.snooze(3600000L);
                }
                MidiChannel.access$100(this.this$0);
                this.snooze(50L);
            }
            this.cleanup();
        }
        
        FadeThread(final MidiChannel midiChannel, final MidiChannel$1 object) {
            this(midiChannel);
        }
    }
}
