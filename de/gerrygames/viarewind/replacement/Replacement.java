package de.gerrygames.viarewind.replacement;

import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class Replacement
{
    private int id;
    private int data;
    private String name;
    private String resetName;
    private String bracketName;
    
    public Replacement(final int n) {
        this(n, -1);
    }
    
    public Replacement(final int n, final int n2) {
        this(n, n2, null);
    }
    
    public Replacement(final int n, final String s) {
        this(n, -1, s);
    }
    
    public Replacement(final int id, final int data, final String name) {
        this.id = id;
        this.data = data;
        this.name = name;
        if (name != null) {
            this.resetName = "§r" + name;
            this.bracketName = " §r§7(" + name + "§r§7)";
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getData() {
        return this.data;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Item replace(final Item item) {
        item.setIdentifier(this.id);
        if (this.data != -1) {
            item.setData((short)this.data);
        }
        if (this.name != null) {
            final CompoundTag tag = (item.tag() == null) ? new CompoundTag() : item.tag();
            if (!tag.contains("display")) {
                tag.put("display", new CompoundTag());
            }
            final CompoundTag compoundTag = (CompoundTag)tag.get("display");
            if (compoundTag.contains("Name")) {
                final StringTag stringTag = (StringTag)compoundTag.get("Name");
                if (!stringTag.getValue().equals(this.resetName) && !stringTag.getValue().endsWith(this.bracketName)) {
                    stringTag.setValue(stringTag.getValue() + this.bracketName);
                }
            }
            else {
                compoundTag.put("Name", new StringTag(this.resetName));
            }
            item.setTag(tag);
        }
        return item;
    }
    
    public int replaceData(final int n) {
        return (this.data == -1) ? n : this.data;
    }
}
