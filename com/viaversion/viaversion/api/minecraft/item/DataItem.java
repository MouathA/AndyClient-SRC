package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.viaversion.libs.gson.annotations.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class DataItem implements Item
{
    @SerializedName(value = "identifier", alternate = { "id" })
    private int identifier;
    private byte amount;
    private short data;
    private CompoundTag tag;
    
    public DataItem() {
    }
    
    public DataItem(final int identifier, final byte amount, final short data, final CompoundTag tag) {
        this.identifier = identifier;
        this.amount = amount;
        this.data = data;
        this.tag = tag;
    }
    
    public DataItem(final Item item) {
        this(item.identifier(), (byte)item.amount(), item.data(), item.tag());
    }
    
    @Override
    public int identifier() {
        return this.identifier;
    }
    
    @Override
    public void setIdentifier(final int identifier) {
        this.identifier = identifier;
    }
    
    @Override
    public int amount() {
        return this.amount;
    }
    
    @Override
    public void setAmount(final int n) {
        if (n > 127 || n < -128) {
            throw new IllegalArgumentException("Invalid item amount: " + n);
        }
        this.amount = (byte)n;
    }
    
    @Override
    public short data() {
        return this.data;
    }
    
    @Override
    public void setData(final short data) {
        this.data = data;
    }
    
    @Override
    public CompoundTag tag() {
        return this.tag;
    }
    
    @Override
    public void setTag(final CompoundTag tag) {
        this.tag = tag;
    }
    
    @Override
    public Item copy() {
        return new DataItem(this.identifier, this.amount, this.data, this.tag);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DataItem dataItem = (DataItem)o;
        return this.identifier == dataItem.identifier && this.amount == dataItem.amount && this.data == dataItem.data && Objects.equals(this.tag, dataItem.tag);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * this.identifier + this.amount) + this.data) + ((this.tag != null) ? this.tag.hashCode() : 0);
    }
    
    @Override
    public String toString() {
        return "Item{identifier=" + this.identifier + ", amount=" + this.amount + ", data=" + this.data + ", tag=" + this.tag + '}';
    }
}
