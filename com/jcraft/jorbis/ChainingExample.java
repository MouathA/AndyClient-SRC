package com.jcraft.jorbis;

class ChainingExample
{
    public static void main(final String[] array) {
        VorbisFile vorbisFile;
        if (array.length > 0) {
            vorbisFile = new VorbisFile(array[0]);
        }
        else {
            vorbisFile = new VorbisFile(System.in, null, -1);
        }
        if (vorbisFile.seekable()) {
            System.out.println("Input bitstream contained " + vorbisFile.streams() + " logical bitstream section(s).");
            System.out.println("Total bitstream playing time: " + vorbisFile.time_total(-1) + " seconds\n");
        }
        else {
            System.out.println("Standard input was not seekable.");
            System.out.println("First logical bitstream information:\n");
        }
        while (0 < vorbisFile.streams()) {
            final Info info = vorbisFile.getInfo(0);
            System.out.println("\tlogical bitstream section " + 1 + " information:");
            System.out.println("\t\t" + info.rate + "Hz " + info.channels + " channels bitrate " + vorbisFile.bitrate(0) / 1000 + "kbps serial number=" + vorbisFile.serialnumber(0));
            System.out.print("\t\tcompressed length: " + vorbisFile.raw_total(0) + " bytes ");
            System.out.println(" play time: " + vorbisFile.time_total(0) + "s");
            System.out.println(vorbisFile.getComment(0));
            int n = 0;
            ++n;
        }
    }
}
