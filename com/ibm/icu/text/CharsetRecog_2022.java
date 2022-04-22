package com.ibm.icu.text;

abstract class CharsetRecog_2022 extends CharsetRecognizer
{
    int match(final byte[] array, final int n, final byte[][] array2) {
        while (0 < n) {
            int n4 = 0;
            Label_0105: {
                if (array[0] == 27) {
                    while (0 < array2.length) {
                        final byte[] array3 = array2[0];
                        Label_0077: {
                            if (n - 0 >= array3.length) {
                                while (1 < array3.length) {
                                    if (array3[1] != array[1]) {
                                        break Label_0077;
                                    }
                                    int n2 = 0;
                                    ++n2;
                                }
                                int n3 = 0;
                                ++n3;
                                n4 = 0 + (array3.length - 1);
                                break Label_0105;
                            }
                        }
                        int n5 = 0;
                        ++n5;
                    }
                    int n6 = 0;
                    ++n6;
                }
                if (array[0] == 14 || array[0] == 15) {
                    int n7 = 0;
                    ++n7;
                }
            }
            ++n4;
        }
        if (!false) {
            return 0;
        }
        if (0 < 5) {}
        if (0 < 0) {}
        return 0;
    }
    
    static class CharsetRecog_2022CN extends CharsetRecog_2022
    {
        private byte[][] escapeSequences;
        
        CharsetRecog_2022CN() {
            this.escapeSequences = new byte[][] { { 27, 36, 41, 65 }, { 27, 36, 41, 71 }, { 27, 36, 42, 72 }, { 27, 36, 41, 69 }, { 27, 36, 43, 73 }, { 27, 36, 43, 74 }, { 27, 36, 43, 75 }, { 27, 36, 43, 76 }, { 27, 36, 43, 77 }, { 27, 78 }, { 27, 79 } };
        }
        
        @Override
        String getName() {
            return "ISO-2022-CN";
        }
        
        @Override
        CharsetMatch match(final CharsetDetector charsetDetector) {
            final int match = this.match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            return (match == 0) ? null : new CharsetMatch(charsetDetector, this, match);
        }
    }
    
    static class CharsetRecog_2022KR extends CharsetRecog_2022
    {
        private byte[][] escapeSequences;
        
        CharsetRecog_2022KR() {
            this.escapeSequences = new byte[][] { { 27, 36, 41, 67 } };
        }
        
        @Override
        String getName() {
            return "ISO-2022-KR";
        }
        
        @Override
        CharsetMatch match(final CharsetDetector charsetDetector) {
            final int match = this.match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            return (match == 0) ? null : new CharsetMatch(charsetDetector, this, match);
        }
    }
    
    static class CharsetRecog_2022JP extends CharsetRecog_2022
    {
        private byte[][] escapeSequences;
        
        CharsetRecog_2022JP() {
            this.escapeSequences = new byte[][] { { 27, 36, 40, 67 }, { 27, 36, 40, 68 }, { 27, 36, 64 }, { 27, 36, 65 }, { 27, 36, 66 }, { 27, 38, 64 }, { 27, 40, 66 }, { 27, 40, 72 }, { 27, 40, 73 }, { 27, 40, 74 }, { 27, 46, 65 }, { 27, 46, 70 } };
        }
        
        @Override
        String getName() {
            return "ISO-2022-JP";
        }
        
        @Override
        CharsetMatch match(final CharsetDetector charsetDetector) {
            final int match = this.match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            return (match == 0) ? null : new CharsetMatch(charsetDetector, this, match);
        }
    }
}
