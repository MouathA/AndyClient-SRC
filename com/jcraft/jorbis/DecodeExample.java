package com.jcraft.jorbis;

import com.jcraft.jogg.*;
import java.io.*;

class DecodeExample
{
    static byte[] convbuffer;
    
    public static void main(final String[] array) {
        InputStream in = System.in;
        if (array.length > 0) {
            in = new FileInputStream(array[0]);
        }
        final SyncState syncState = new SyncState();
        final StreamState streamState = new StreamState();
        final Page page = new Page();
        final Packet packet = new Packet();
        final Info info = new Info();
        final Comment comment = new Comment();
        final Block block = new Block(new DspState());
        syncState.init();
        in.read(syncState.data, syncState.buffer(4096), 4096);
        syncState.wrote(0);
        if (syncState.pageout(page) != 1) {
            syncState.clear();
            System.err.println("Done.");
            return;
        }
        streamState.init(page.serialno());
        info.init();
        comment.init();
        if (streamState.pagein(page) < 0) {
            System.err.println("Error reading first page of Ogg bitstream data.");
            System.exit(1);
        }
        if (streamState.packetout(packet) != 1) {
            System.err.println("Error reading initial header packet.");
            System.exit(1);
        }
        if (info.synthesis_headerin(comment, packet) < 0) {
            System.err.println("This Ogg bitstream does not contain Vorbis audio data.");
            System.exit(1);
        }
        while (true) {
            final int pageout = syncState.pageout(page);
            if (pageout == 0) {
                in.read(syncState.data, syncState.buffer(4096), 4096);
                System.err.println("End of file before finding all Vorbis headers!");
                System.exit(1);
                syncState.wrote(0);
            }
            else {
                if (pageout != 1) {
                    continue;
                }
                streamState.pagein(page);
                while (true) {
                    final int packetout = streamState.packetout(packet);
                    if (packetout == 0) {
                        break;
                    }
                    if (packetout == -1) {
                        System.err.println("Corrupt secondary header.  Exiting.");
                        System.exit(1);
                    }
                    info.synthesis_headerin(comment, packet);
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    static {
        DecodeExample.convbuffer = new byte[8192];
    }
}
