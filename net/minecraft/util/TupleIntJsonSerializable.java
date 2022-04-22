package net.minecraft.util;

public class TupleIntJsonSerializable
{
    private int integerValue;
    private IJsonSerializable jsonSerializableValue;
    private static final String __OBFID;
    
    public int getIntegerValue() {
        return this.integerValue;
    }
    
    public void setIntegerValue(final int integerValue) {
        this.integerValue = integerValue;
    }
    
    public IJsonSerializable getJsonSerializableValue() {
        return this.jsonSerializableValue;
    }
    
    public void setJsonSerializableValue(final IJsonSerializable jsonSerializableValue) {
        this.jsonSerializableValue = jsonSerializableValue;
    }
    
    static {
        __OBFID = "CL_00001478";
    }
}
