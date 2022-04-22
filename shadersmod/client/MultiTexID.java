package shadersmod.client;

public class MultiTexID
{
    public int base;
    public int norm;
    public int spec;
    
    public MultiTexID(final int base, final int norm, final int spec) {
        this.base = base;
        this.norm = norm;
        this.spec = spec;
    }
}
