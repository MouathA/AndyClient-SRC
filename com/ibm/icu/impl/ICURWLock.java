package com.ibm.icu.impl;

public class ICURWLock
{
    private Object writeLock;
    private Object readLock;
    private int wwc;
    private int rc;
    private int wrc;
    private Stats stats;
    private static final int NOTIFY_NONE = 0;
    private static final int NOTIFY_WRITERS = 1;
    private static final int NOTIFY_READERS = 2;
    
    public ICURWLock() {
        this.writeLock = new Object();
        this.readLock = new Object();
        this.stats = new Stats((ICURWLock$1)null);
    }
    
    public synchronized Stats resetStats() {
        final Stats stats = this.stats;
        this.stats = new Stats((ICURWLock$1)null);
        return stats;
    }
    
    public synchronized Stats clearStats() {
        final Stats stats = this.stats;
        this.stats = null;
        return stats;
    }
    
    public synchronized Stats getStats() {
        return (this.stats == null) ? null : new Stats(this.stats, null);
    }
    
    private synchronized boolean gotRead() {
        ++this.rc;
        if (this.stats != null) {
            final Stats stats = this.stats;
            ++stats._rc;
            if (this.rc > 1) {
                final Stats stats2 = this.stats;
                ++stats2._mrc;
            }
        }
        return true;
    }
    
    private synchronized boolean getRead() {
        if (this.rc >= 0 && this.wwc == 0) {
            return this.gotRead();
        }
        ++this.wrc;
        return false;
    }
    
    private synchronized boolean retryRead() {
        if (this.stats != null) {
            final Stats stats = this.stats;
            ++stats._wrc;
        }
        if (this.rc >= 0 && this.wwc == 0) {
            --this.wrc;
            return this.gotRead();
        }
        return false;
    }
    
    private synchronized boolean finishRead() {
        if (this.rc > 0) {
            final boolean b = false;
            final int rc = this.rc - 1;
            this.rc = rc;
            return (b ? 1 : 0) == rc && this.wwc > 0;
        }
        throw new IllegalStateException("no current reader to release");
    }
    
    private synchronized boolean gotWrite() {
        this.rc = -1;
        if (this.stats != null) {
            final Stats stats = this.stats;
            ++stats._wc;
        }
        return true;
    }
    
    private synchronized boolean getWrite() {
        if (this.rc == 0) {
            return this.gotWrite();
        }
        ++this.wwc;
        return false;
    }
    
    private synchronized boolean retryWrite() {
        if (this.stats != null) {
            final Stats stats = this.stats;
            ++stats._wwc;
        }
        if (this.rc == 0) {
            --this.wwc;
            return this.gotWrite();
        }
        return false;
    }
    
    private synchronized int finishWrite() {
        if (this.rc >= 0) {
            throw new IllegalStateException("no current writer to release");
        }
        this.rc = 0;
        if (this.wwc > 0) {
            return 1;
        }
        if (this.wrc > 0) {
            return 2;
        }
        return 0;
    }
    
    public void acquireRead() {
        if (!this.getRead()) {
            do {
                // monitorenter(readLock = this.readLock)
                this.readLock.wait();
            }// monitorexit(readLock)
            while (!this.retryRead());
        }
    }
    
    public void releaseRead() {
        if (this.finishRead()) {
            // monitorenter(writeLock = this.writeLock)
            this.writeLock.notify();
        }
        // monitorexit(writeLock)
    }
    
    public void acquireWrite() {
        if (!this.getWrite()) {
            do {
                // monitorenter(writeLock = this.writeLock)
                this.writeLock.wait();
            }// monitorexit(writeLock)
            while (!this.retryWrite());
        }
    }
    
    public void releaseWrite() {
        switch (this.finishWrite()) {
            case 1: {
                // monitorenter(writeLock = this.writeLock)
                this.writeLock.notify();
                // monitorexit(writeLock)
                break;
            }
            case 2: {
                // monitorenter(readLock = this.readLock)
                this.readLock.notifyAll();
                // monitorexit(readLock)
                break;
            }
        }
    }
    
    public static final class Stats
    {
        public int _rc;
        public int _mrc;
        public int _wrc;
        public int _wc;
        public int _wwc;
        
        private Stats() {
        }
        
        private Stats(final int rc, final int mrc, final int wrc, final int wc, final int wwc) {
            this._rc = rc;
            this._mrc = mrc;
            this._wrc = wrc;
            this._wc = wc;
            this._wwc = wwc;
        }
        
        private Stats(final Stats stats) {
            this(stats._rc, stats._mrc, stats._wrc, stats._wc, stats._wwc);
        }
        
        @Override
        public String toString() {
            return " rc: " + this._rc + " mrc: " + this._mrc + " wrc: " + this._wrc + " wc: " + this._wc + " wwc: " + this._wwc;
        }
        
        Stats(final ICURWLock$1 object) {
            this();
        }
        
        Stats(final Stats stats, final ICURWLock$1 object) {
            this(stats);
        }
    }
}
