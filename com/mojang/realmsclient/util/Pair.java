package com.mojang.realmsclient.util;

public class Pair
{
    private final Object first;
    private final Object second;
    
    protected Pair(final Object first, final Object second) {
        this.first = first;
        this.second = second;
    }
    
    public static Pair of(final Object o, final Object o2) {
        return new Pair(o, o2);
    }
    
    public Object first() {
        return this.first;
    }
    
    public Object second() {
        return this.second;
    }
    
    public String mkString(final String s) {
        return String.format("%s%s%s", this.first, s, this.second);
    }
}
