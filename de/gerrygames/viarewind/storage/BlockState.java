package de.gerrygames.viarewind.storage;

public class BlockState
{
    public static int extractId(final int n) {
        return n >> 4;
    }
    
    public static int extractData(final int n) {
        return n & 0xF;
    }
    
    public static int stateToRaw(final int n, final int n2) {
        return n << 4 | (n2 & 0xF);
    }
}
