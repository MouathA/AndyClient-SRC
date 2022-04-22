package net.minecraft.stats;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class StatFileWriter
{
    protected final Map field_150875_a;
    private static final String __OBFID;
    
    public StatFileWriter() {
        this.field_150875_a = Maps.newConcurrentMap();
    }
    
    public boolean hasAchievementUnlocked(final Achievement achievement) {
        return this.writeStat(achievement) > 0;
    }
    
    public boolean canUnlockAchievement(final Achievement achievement) {
        return achievement.parentAchievement == null || this.hasAchievementUnlocked(achievement.parentAchievement);
    }
    
    public int func_150874_c(final Achievement achievement) {
        if (this.hasAchievementUnlocked(achievement)) {
            return 0;
        }
        int n = 0;
        for (Achievement achievement2 = achievement.parentAchievement; achievement2 != null && !this.hasAchievementUnlocked(achievement2); achievement2 = achievement2.parentAchievement, ++n) {}
        return 0;
    }
    
    public void func_150871_b(final EntityPlayer entityPlayer, final StatBase statBase, final int n) {
        if (!statBase.isAchievement() || this.canUnlockAchievement((Achievement)statBase)) {
            this.func_150873_a(entityPlayer, statBase, this.writeStat(statBase) + n);
        }
    }
    
    public void func_150873_a(final EntityPlayer entityPlayer, final StatBase statBase, final int integerValue) {
        TupleIntJsonSerializable tupleIntJsonSerializable = this.field_150875_a.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new TupleIntJsonSerializable();
            this.field_150875_a.put(statBase, tupleIntJsonSerializable);
        }
        tupleIntJsonSerializable.setIntegerValue(integerValue);
    }
    
    public int writeStat(final StatBase statBase) {
        final TupleIntJsonSerializable tupleIntJsonSerializable = this.field_150875_a.get(statBase);
        return (tupleIntJsonSerializable == null) ? 0 : tupleIntJsonSerializable.getIntegerValue();
    }
    
    public IJsonSerializable func_150870_b(final StatBase statBase) {
        final TupleIntJsonSerializable tupleIntJsonSerializable = this.field_150875_a.get(statBase);
        return (tupleIntJsonSerializable != null) ? tupleIntJsonSerializable.getJsonSerializableValue() : null;
    }
    
    public IJsonSerializable func_150872_a(final StatBase statBase, final IJsonSerializable jsonSerializableValue) {
        TupleIntJsonSerializable tupleIntJsonSerializable = this.field_150875_a.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new TupleIntJsonSerializable();
            this.field_150875_a.put(statBase, tupleIntJsonSerializable);
        }
        tupleIntJsonSerializable.setJsonSerializableValue(jsonSerializableValue);
        return jsonSerializableValue;
    }
    
    static {
        __OBFID = "CL_00001481";
    }
}
