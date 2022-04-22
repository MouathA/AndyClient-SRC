package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.*;

public class RingBufferLogEventHandler implements SequenceReportingEventHandler
{
    private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
    private Sequence sequenceCallback;
    private int counter;
    
    public void setSequenceCallback(final Sequence sequenceCallback) {
        this.sequenceCallback = sequenceCallback;
    }
    
    public void onEvent(final RingBufferLogEvent ringBufferLogEvent, final long n, final boolean b) throws Exception {
        ringBufferLogEvent.execute(b);
        ringBufferLogEvent.clear();
        if (++this.counter > 50) {
            this.sequenceCallback.set(n);
            this.counter = 0;
        }
    }
    
    public void onEvent(final Object o, final long n, final boolean b) throws Exception {
        this.onEvent((RingBufferLogEvent)o, n, b);
    }
}
