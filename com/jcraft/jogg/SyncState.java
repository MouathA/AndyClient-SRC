package com.jcraft.jogg;

public class SyncState
{
    public byte[] data;
    int storage;
    int fill;
    int returned;
    int unsynced;
    int headerbytes;
    int bodybytes;
    private Page pageseek;
    private byte[] chksum;
    
    public SyncState() {
        this.pageseek = new Page();
        this.chksum = new byte[4];
    }
    
    public int clear() {
        this.data = null;
        return 0;
    }
    
    public int buffer(final int n) {
        if (this.returned != 0) {
            this.fill -= this.returned;
            if (this.fill > 0) {
                System.arraycopy(this.data, this.returned, this.data, 0, this.fill);
            }
            this.returned = 0;
        }
        if (n > this.storage - this.fill) {
            final int storage = n + this.fill + 4096;
            if (this.data != null) {
                final byte[] data = new byte[storage];
                System.arraycopy(this.data, 0, data, 0, this.data.length);
                this.data = data;
            }
            else {
                this.data = new byte[storage];
            }
            this.storage = storage;
        }
        return this.fill;
    }
    
    public int wrote(final int n) {
        if (this.fill + n > this.storage) {
            return -1;
        }
        this.fill += n;
        return 0;
    }
    
    public int pageseek(final Page page) {
        final int returned = this.returned;
        final int n = this.fill - this.returned;
        int n2 = 0;
        if (this.headerbytes == 0) {
            if (n < 27) {
                return 0;
            }
            if (this.data[returned] != 79 || this.data[returned + 1] != 103 || this.data[returned + 2] != 103 || this.data[returned + 3] != 83) {
                this.headerbytes = 0;
                this.bodybytes = 0;
                while (0 < n - 1 && this.data[returned + 1 + 0] != 79) {
                    ++n2;
                }
                final int fill = this.fill;
                return -((this.returned = 0) - returned);
            }
            final int headerbytes = (this.data[returned + 26] & 0xFF) + 27;
            if (n < headerbytes) {
                return 0;
            }
            while (0 < (this.data[returned + 26] & 0xFF)) {
                this.bodybytes += (this.data[returned + 27 + 0] & 0xFF);
                int n3 = 0;
                ++n3;
            }
            this.headerbytes = headerbytes;
        }
        if (this.bodybytes + this.headerbytes > n) {
            return 0;
        }
        // monitorenter(chksum = this.chksum)
        System.arraycopy(this.data, returned + 22, this.chksum, 0, 4);
        this.data[returned + 22] = 0;
        this.data[returned + 23] = 0;
        this.data[returned + 24] = 0;
        this.data[returned + 25] = 0;
        final Page pageseek = this.pageseek;
        pageseek.header_base = this.data;
        pageseek.header = returned;
        pageseek.header_len = this.headerbytes;
        pageseek.body_base = this.data;
        pageseek.body = returned + this.headerbytes;
        pageseek.body_len = this.bodybytes;
        pageseek.checksum();
        if (this.chksum[0] != this.data[returned + 22] || this.chksum[1] != this.data[returned + 23] || this.chksum[2] != this.data[returned + 24] || this.chksum[3] != this.data[returned + 25]) {
            System.arraycopy(this.chksum, 0, this.data, returned + 22, 4);
            this.headerbytes = 0;
            this.bodybytes = 0;
            while (0 < n - 1 && this.data[returned + 1 + 0] != 79) {
                ++n2;
            }
            final int fill2 = this.fill;
            this.returned = 0;
            // monitorexit(chksum)
            return -(0 - returned);
        }
        // monitorexit(chksum)
        final int returned2 = this.returned;
        if (page != null) {
            page.header_base = this.data;
            page.header = returned2;
            page.header_len = this.headerbytes;
            page.body_base = this.data;
            page.body = returned2 + this.headerbytes;
            page.body_len = this.bodybytes;
        }
        this.unsynced = 0;
        final int n5;
        this.returned += (n5 = this.headerbytes + this.bodybytes);
        this.headerbytes = 0;
        this.bodybytes = 0;
        return n5;
    }
    
    public int pageout(final Page page) {
        while (true) {
            final int pageseek = this.pageseek(page);
            if (pageseek > 0) {
                return 1;
            }
            if (pageseek == 0) {
                return 0;
            }
            if (this.unsynced == 0) {
                this.unsynced = 1;
                return -1;
            }
        }
    }
    
    public int reset() {
        this.fill = 0;
        this.returned = 0;
        this.unsynced = 0;
        this.headerbytes = 0;
        return this.bodybytes = 0;
    }
    
    public void init() {
    }
    
    public int getDataOffset() {
        return this.returned;
    }
    
    public int getBufferOffset() {
        return this.fill;
    }
}
