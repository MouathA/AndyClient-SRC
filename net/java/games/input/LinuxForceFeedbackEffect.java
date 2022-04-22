package net.java.games.input;

import java.io.*;

abstract class LinuxForceFeedbackEffect implements Rumbler
{
    private final LinuxEventDevice device;
    private final int ff_id;
    private final WriteTask write_task;
    private final UploadTask upload_task;
    
    public LinuxForceFeedbackEffect(final LinuxEventDevice device) throws IOException {
        this.write_task = new WriteTask(null);
        this.upload_task = new UploadTask(null);
        this.device = device;
        this.ff_id = this.upload_task.doUpload(-1, 0.0f);
    }
    
    protected abstract int upload(final int p0, final float p1) throws IOException;
    
    protected final LinuxEventDevice getDevice() {
        return this.device;
    }
    
    public final synchronized void rumble(final float n) {
        if (n > 0.0f) {
            this.upload_task.doUpload(this.ff_id, n);
            this.write_task.write(1);
        }
        else {
            this.write_task.write(0);
        }
    }
    
    public final String getAxisName() {
        return null;
    }
    
    public final Component.Identifier getAxisIdentifier() {
        return null;
    }
    
    static int access$200(final LinuxForceFeedbackEffect linuxForceFeedbackEffect) {
        return linuxForceFeedbackEffect.ff_id;
    }
    
    static LinuxEventDevice access$300(final LinuxForceFeedbackEffect linuxForceFeedbackEffect) {
        return linuxForceFeedbackEffect.device;
    }
    
    private final class WriteTask extends LinuxDeviceTask
    {
        private int value;
        private final LinuxForceFeedbackEffect this$0;
        
        private WriteTask(final LinuxForceFeedbackEffect this$0) {
            this.this$0 = this$0;
        }
        
        public final void write(final int value) throws IOException {
            this.value = value;
            LinuxEnvironmentPlugin.execute(this);
        }
        
        protected final Object execute() throws IOException {
            LinuxForceFeedbackEffect.access$300(this.this$0).writeEvent(21, LinuxForceFeedbackEffect.access$200(this.this$0), this.value);
            return null;
        }
        
        WriteTask(final LinuxForceFeedbackEffect linuxForceFeedbackEffect, final LinuxForceFeedbackEffect$1 object) {
            this(linuxForceFeedbackEffect);
        }
    }
    
    private final class UploadTask extends LinuxDeviceTask
    {
        private int id;
        private float intensity;
        private final LinuxForceFeedbackEffect this$0;
        
        private UploadTask(final LinuxForceFeedbackEffect this$0) {
            this.this$0 = this$0;
        }
        
        public final int doUpload(final int id, final float intensity) throws IOException {
            this.id = id;
            this.intensity = intensity;
            LinuxEnvironmentPlugin.execute(this);
            return this.id;
        }
        
        protected final Object execute() throws IOException {
            this.id = this.this$0.upload(this.id, this.intensity);
            return null;
        }
        
        UploadTask(final LinuxForceFeedbackEffect linuxForceFeedbackEffect, final LinuxForceFeedbackEffect$1 object) {
            this(linuxForceFeedbackEffect);
        }
    }
}
