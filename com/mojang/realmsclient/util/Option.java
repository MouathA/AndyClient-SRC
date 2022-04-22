package com.mojang.realmsclient.util;

public abstract class Option
{
    public abstract Object get();
    
    public static Some some(final Object o) {
        return new Some(o);
    }
    
    public static None none() {
        return new None();
    }
    
    public static final class None extends Option
    {
        @Override
        public Object get() {
            throw new RuntimeException("None has no value");
        }
    }
    
    public static final class Some extends Option
    {
        private final Object a;
        
        public Some(final Object a) {
            this.a = a;
        }
        
        @Override
        public Object get() {
            return this.a;
        }
        
        public static Option of(final Object o) {
            return new Some(o);
        }
    }
}
