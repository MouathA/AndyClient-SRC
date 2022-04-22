package io.netty.handler.codec.spdy;

import java.util.concurrent.atomic.*;
import io.netty.util.internal.*;
import io.netty.channel.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

final class SpdySession
{
    private final AtomicInteger activeLocalStreams;
    private final AtomicInteger activeRemoteStreams;
    private final Map activeStreams;
    private final StreamComparator streamComparator;
    private final AtomicInteger sendWindowSize;
    private final AtomicInteger receiveWindowSize;
    
    SpdySession(final int n, final int n2) {
        this.activeLocalStreams = new AtomicInteger();
        this.activeRemoteStreams = new AtomicInteger();
        this.activeStreams = PlatformDependent.newConcurrentHashMap();
        this.streamComparator = new StreamComparator();
        this.sendWindowSize = new AtomicInteger(n);
        this.receiveWindowSize = new AtomicInteger(n2);
    }
    
    int numActiveStreams(final boolean b) {
        if (b) {
            return this.activeRemoteStreams.get();
        }
        return this.activeLocalStreams.get();
    }
    
    boolean noActiveStreams() {
        return this.activeStreams.isEmpty();
    }
    
    boolean isActiveStream(final int n) {
        return this.activeStreams.containsKey(n);
    }
    
    Map activeStreams() {
        final TreeMap treeMap = new TreeMap(this.streamComparator);
        treeMap.putAll(this.activeStreams);
        return treeMap;
    }
    
    void acceptStream(final int n, final byte b, final boolean b2, final boolean b3, final int n2, final int n3, final boolean b4) {
        if ((!b2 || !b3) && this.activeStreams.put(n, new StreamState(b, b2, b3, n2, n3)) == null) {
            if (b4) {
                this.activeRemoteStreams.incrementAndGet();
            }
            else {
                this.activeLocalStreams.incrementAndGet();
            }
        }
    }
    
    private StreamState removeActiveStream(final int n, final boolean b) {
        final StreamState streamState = this.activeStreams.remove(n);
        if (streamState != null) {
            if (b) {
                this.activeRemoteStreams.decrementAndGet();
            }
            else {
                this.activeLocalStreams.decrementAndGet();
            }
        }
        return streamState;
    }
    
    void removeStream(final int n, final Throwable t, final boolean b) {
        final StreamState removeActiveStream = this.removeActiveStream(n, b);
        if (removeActiveStream != null) {
            removeActiveStream.clearPendingWrites(t);
        }
    }
    
    boolean isRemoteSideClosed(final int n) {
        final StreamState streamState = this.activeStreams.get(n);
        return streamState == null || streamState.isRemoteSideClosed();
    }
    
    void closeRemoteSide(final int n, final boolean b) {
        final StreamState streamState = this.activeStreams.get(n);
        if (streamState != null) {
            streamState.closeRemoteSide();
            if (streamState.isLocalSideClosed()) {
                this.removeActiveStream(n, b);
            }
        }
    }
    
    boolean isLocalSideClosed(final int n) {
        final StreamState streamState = this.activeStreams.get(n);
        return streamState == null || streamState.isLocalSideClosed();
    }
    
    void closeLocalSide(final int n, final boolean b) {
        final StreamState streamState = this.activeStreams.get(n);
        if (streamState != null) {
            streamState.closeLocalSide();
            if (streamState.isRemoteSideClosed()) {
                this.removeActiveStream(n, b);
            }
        }
    }
    
    boolean hasReceivedReply(final int n) {
        final StreamState streamState = this.activeStreams.get(n);
        return streamState != null && streamState.hasReceivedReply();
    }
    
    void receivedReply(final int n) {
        final StreamState streamState = this.activeStreams.get(n);
        if (streamState != null) {
            streamState.receivedReply();
        }
    }
    
    int getSendWindowSize(final int n) {
        if (n == 0) {
            return this.sendWindowSize.get();
        }
        final StreamState streamState = this.activeStreams.get(n);
        return (streamState != null) ? streamState.getSendWindowSize() : -1;
    }
    
    int updateSendWindowSize(final int n, final int n2) {
        if (n == 0) {
            return this.sendWindowSize.addAndGet(n2);
        }
        final StreamState streamState = this.activeStreams.get(n);
        return (streamState != null) ? streamState.updateSendWindowSize(n2) : -1;
    }
    
    int updateReceiveWindowSize(final int n, final int n2) {
        if (n == 0) {
            return this.receiveWindowSize.addAndGet(n2);
        }
        final StreamState streamState = this.activeStreams.get(n);
        if (streamState == null) {
            return -1;
        }
        if (n2 > 0) {
            streamState.setReceiveWindowSizeLowerBound(0);
        }
        return streamState.updateReceiveWindowSize(n2);
    }
    
    int getReceiveWindowSizeLowerBound(final int n) {
        if (n == 0) {
            return 0;
        }
        final StreamState streamState = this.activeStreams.get(n);
        return (streamState != null) ? streamState.getReceiveWindowSizeLowerBound() : 0;
    }
    
    void updateAllSendWindowSizes(final int n) {
        final Iterator<StreamState> iterator = this.activeStreams.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().updateSendWindowSize(n);
        }
    }
    
    void updateAllReceiveWindowSizes(final int receiveWindowSizeLowerBound) {
        for (final StreamState streamState : this.activeStreams.values()) {
            streamState.updateReceiveWindowSize(receiveWindowSizeLowerBound);
            if (receiveWindowSizeLowerBound < 0) {
                streamState.setReceiveWindowSizeLowerBound(receiveWindowSizeLowerBound);
            }
        }
    }
    
    boolean putPendingWrite(final int n, final PendingWrite pendingWrite) {
        final StreamState streamState = this.activeStreams.get(n);
        return streamState != null && streamState.putPendingWrite(pendingWrite);
    }
    
    PendingWrite getPendingWrite(final int n) {
        if (n == 0) {
            final Iterator<Map.Entry<K, StreamState>> iterator = this.activeStreams().entrySet().iterator();
            while (iterator.hasNext()) {
                final StreamState streamState = iterator.next().getValue();
                if (streamState.getSendWindowSize() > 0) {
                    final PendingWrite pendingWrite = streamState.getPendingWrite();
                    if (pendingWrite != null) {
                        return pendingWrite;
                    }
                    continue;
                }
            }
            return null;
        }
        final StreamState streamState2 = this.activeStreams.get(n);
        return (streamState2 != null) ? streamState2.getPendingWrite() : null;
    }
    
    PendingWrite removePendingWrite(final int n) {
        final StreamState streamState = this.activeStreams.get(n);
        return (streamState != null) ? streamState.removePendingWrite() : null;
    }
    
    static Map access$000(final SpdySession spdySession) {
        return spdySession.activeStreams;
    }
    
    public static final class PendingWrite
    {
        final SpdyDataFrame spdyDataFrame;
        final ChannelPromise promise;
        
        PendingWrite(final SpdyDataFrame spdyDataFrame, final ChannelPromise promise) {
            this.spdyDataFrame = spdyDataFrame;
            this.promise = promise;
        }
        
        void fail(final Throwable failure) {
            this.spdyDataFrame.release();
            this.promise.setFailure(failure);
        }
    }
    
    private final class StreamComparator implements Comparator, Serializable
    {
        private static final long serialVersionUID = 1161471649740544848L;
        final SpdySession this$0;
        
        StreamComparator(final SpdySession this$0) {
            this.this$0 = this$0;
        }
        
        public int compare(final Integer n, final Integer n2) {
            final int n3 = SpdySession.access$000(this.this$0).get(n).getPriority() - SpdySession.access$000(this.this$0).get(n2).getPriority();
            if (n3 != 0) {
                return n3;
            }
            return n - n2;
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((Integer)o, (Integer)o2);
        }
    }
    
    private static final class StreamState
    {
        private final byte priority;
        private boolean remoteSideClosed;
        private boolean localSideClosed;
        private boolean receivedReply;
        private final AtomicInteger sendWindowSize;
        private final AtomicInteger receiveWindowSize;
        private int receiveWindowSizeLowerBound;
        private final Queue pendingWriteQueue;
        
        StreamState(final byte priority, final boolean remoteSideClosed, final boolean localSideClosed, final int n, final int n2) {
            this.pendingWriteQueue = new ConcurrentLinkedQueue();
            this.priority = priority;
            this.remoteSideClosed = remoteSideClosed;
            this.localSideClosed = localSideClosed;
            this.sendWindowSize = new AtomicInteger(n);
            this.receiveWindowSize = new AtomicInteger(n2);
        }
        
        byte getPriority() {
            return this.priority;
        }
        
        boolean isRemoteSideClosed() {
            return this.remoteSideClosed;
        }
        
        void closeRemoteSide() {
            this.remoteSideClosed = true;
        }
        
        boolean isLocalSideClosed() {
            return this.localSideClosed;
        }
        
        void closeLocalSide() {
            this.localSideClosed = true;
        }
        
        boolean hasReceivedReply() {
            return this.receivedReply;
        }
        
        void receivedReply() {
            this.receivedReply = true;
        }
        
        int getSendWindowSize() {
            return this.sendWindowSize.get();
        }
        
        int updateSendWindowSize(final int n) {
            return this.sendWindowSize.addAndGet(n);
        }
        
        int updateReceiveWindowSize(final int n) {
            return this.receiveWindowSize.addAndGet(n);
        }
        
        int getReceiveWindowSizeLowerBound() {
            return this.receiveWindowSizeLowerBound;
        }
        
        void setReceiveWindowSizeLowerBound(final int receiveWindowSizeLowerBound) {
            this.receiveWindowSizeLowerBound = receiveWindowSizeLowerBound;
        }
        
        boolean putPendingWrite(final PendingWrite pendingWrite) {
            return this.pendingWriteQueue.offer(pendingWrite);
        }
        
        PendingWrite getPendingWrite() {
            return this.pendingWriteQueue.peek();
        }
        
        PendingWrite removePendingWrite() {
            return this.pendingWriteQueue.poll();
        }
        
        void clearPendingWrites(final Throwable t) {
            while (true) {
                final PendingWrite pendingWrite = this.pendingWriteQueue.poll();
                if (pendingWrite == null) {
                    break;
                }
                pendingWrite.fail(t);
            }
        }
    }
}
