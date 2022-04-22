package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.*;
import java.math.*;

public final class JsonPrimitive extends JsonElement
{
    private final Object value;
    
    public JsonPrimitive(final Boolean b) {
        this.value = $Gson$Preconditions.checkNotNull(b);
    }
    
    public JsonPrimitive(final Number n) {
        this.value = $Gson$Preconditions.checkNotNull(n);
    }
    
    public JsonPrimitive(final String s) {
        this.value = $Gson$Preconditions.checkNotNull(s);
    }
    
    public JsonPrimitive(final Character c) {
        this.value = ((Character)$Gson$Preconditions.checkNotNull(c)).toString();
    }
    
    @Override
    public JsonPrimitive deepCopy() {
        return this;
    }
    
    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }
    
    @Override
    public boolean getAsBoolean() {
        if (this.isBoolean()) {
            return (boolean)this.value;
        }
        return Boolean.parseBoolean(this.getAsString());
    }
    
    public boolean isNumber() {
        return this.value instanceof Number;
    }
    
    @Override
    public Number getAsNumber() {
        return (this.value instanceof String) ? new LazilyParsedNumber((String)this.value) : this.value;
    }
    
    public boolean isString() {
        return this.value instanceof String;
    }
    
    @Override
    public String getAsString() {
        if (this.isNumber()) {
            return this.getAsNumber().toString();
        }
        if (this.isBoolean()) {
            return ((Boolean)this.value).toString();
        }
        return (String)this.value;
    }
    
    @Override
    public double getAsDouble() {
        return this.isNumber() ? this.getAsNumber().doubleValue() : Double.parseDouble(this.getAsString());
    }
    
    @Override
    public BigDecimal getAsBigDecimal() {
        return (BigDecimal)((this.value instanceof BigDecimal) ? this.value : new BigDecimal(this.value.toString()));
    }
    
    @Override
    public BigInteger getAsBigInteger() {
        return (BigInteger)((this.value instanceof BigInteger) ? this.value : new BigInteger(this.value.toString()));
    }
    
    @Override
    public float getAsFloat() {
        return this.isNumber() ? this.getAsNumber().floatValue() : Float.parseFloat(this.getAsString());
    }
    
    @Override
    public long getAsLong() {
        return this.isNumber() ? this.getAsNumber().longValue() : Long.parseLong(this.getAsString());
    }
    
    @Override
    public short getAsShort() {
        return this.isNumber() ? this.getAsNumber().shortValue() : Short.parseShort(this.getAsString());
    }
    
    @Override
    public int getAsInt() {
        return this.isNumber() ? this.getAsNumber().intValue() : Integer.parseInt(this.getAsString());
    }
    
    @Override
    public byte getAsByte() {
        return this.isNumber() ? this.getAsNumber().byteValue() : Byte.parseByte(this.getAsString());
    }
    
    @Override
    public char getAsCharacter() {
        return this.getAsString().charAt(0);
    }
    
    @Override
    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        if (isIntegral(this)) {
            final long longValue = this.getAsNumber().longValue();
            return (int)(longValue ^ longValue >>> 32);
        }
        if (this.value instanceof Number) {
            final long doubleToLongBits = Double.doubleToLongBits(this.getAsNumber().doubleValue());
            return (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
        }
        return this.value.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final JsonPrimitive jsonPrimitive = (JsonPrimitive)o;
        if (this.value == null) {
            return jsonPrimitive.value == null;
        }
        if (isIntegral(this) && isIntegral(jsonPrimitive)) {
            return this.getAsNumber().longValue() == jsonPrimitive.getAsNumber().longValue();
        }
        if (this.value instanceof Number && jsonPrimitive.value instanceof Number) {
            final double doubleValue = this.getAsNumber().doubleValue();
            final double doubleValue2 = jsonPrimitive.getAsNumber().doubleValue();
            return doubleValue == doubleValue2 || (Double.isNaN(doubleValue) && Double.isNaN(doubleValue2));
        }
        return this.value.equals(jsonPrimitive.value);
    }
    
    private static boolean isIntegral(final JsonPrimitive jsonPrimitive) {
        if (jsonPrimitive.value instanceof Number) {
            final Number n = (Number)jsonPrimitive.value;
            return n instanceof BigInteger || n instanceof Long || n instanceof Integer || n instanceof Short || n instanceof Byte;
        }
        return false;
    }
    
    @Override
    public JsonElement deepCopy() {
        return this.deepCopy();
    }
}
