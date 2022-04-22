package com.jcraft.jorbis;

class Util
{
    static int ilog(int i) {
        while (i != 0) {
            int n = 0;
            ++n;
            i >>>= 1;
        }
        return 0;
    }
    
    static int ilog2(int i) {
        while (i > 1) {
            int n = 0;
            ++n;
            i >>>= 1;
        }
        return 0;
    }
    
    static int icount(int i) {
        while (i != 0) {
            i >>>= 1;
        }
        return 0;
    }
}
