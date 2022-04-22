package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute
{
    private final IAttribute field_180373_a;
    private final String unlocalizedName;
    private final double defaultValue;
    private boolean shouldWatch;
    private static final String __OBFID;
    
    protected BaseAttribute(final IAttribute field_180373_a, final String unlocalizedName, final double defaultValue) {
        this.field_180373_a = field_180373_a;
        this.unlocalizedName = unlocalizedName;
        this.defaultValue = defaultValue;
        if (unlocalizedName == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
    }
    
    @Override
    public String getAttributeUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    @Override
    public double getDefaultValue() {
        return this.defaultValue;
    }
    
    @Override
    public boolean getShouldWatch() {
        return this.shouldWatch;
    }
    
    public BaseAttribute setShouldWatch(final boolean shouldWatch) {
        this.shouldWatch = shouldWatch;
        return this;
    }
    
    @Override
    public IAttribute func_180372_d() {
        return this.field_180373_a;
    }
    
    @Override
    public int hashCode() {
        return this.unlocalizedName.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof IAttribute && this.unlocalizedName.equals(((IAttribute)o).getAttributeUnlocalizedName());
    }
    
    static {
        __OBFID = "CL_00001565";
    }
}
