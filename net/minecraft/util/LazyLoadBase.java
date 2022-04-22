package net.minecraft.util;

public abstract class LazyLoadBase
{
    private Object value;
    private boolean isLoaded;
    private static final String __OBFID;
    
    public LazyLoadBase() {
        this.isLoaded = false;
    }
    
    public Object getValue() {
        if (!this.isLoaded) {
            this.isLoaded = true;
            this.value = this.load();
        }
        return this.value;
    }
    
    protected abstract Object load();
    
    static {
        __OBFID = "CL_00002263";
    }
}
