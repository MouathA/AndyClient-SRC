package net.minecraft.world;

import net.minecraft.nbt.*;
import java.util.*;

public class GameRules
{
    private TreeMap theGameRules;
    
    public GameRules() {
        this.theGameRules = new TreeMap();
        this.addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
        this.addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
    }
    
    public void addGameRule(final String s, final String s2, final ValueType valueType) {
        this.theGameRules.put(s, new Value(s2, valueType));
    }
    
    public void setOrCreateGameRule(final String s, final String value) {
        final Value value2 = this.theGameRules.get(s);
        if (value2 != null) {
            value2.setValue(value);
        }
        else {
            this.addGameRule(s, value, ValueType.ANY_VALUE);
        }
    }
    
    public String getGameRuleStringValue(final String s) {
        final Value value = this.theGameRules.get(s);
        return (value != null) ? value.getGameRuleStringValue() : "";
    }
    
    public boolean getGameRuleBooleanValue(final String s) {
        final Value value = this.theGameRules.get(s);
        return value != null && value.getGameRuleBooleanValue();
    }
    
    public int getInt(final String s) {
        final Value value = this.theGameRules.get(s);
        return (value != null) ? value.getInt() : 0;
    }
    
    public NBTTagCompound writeGameRulesToNBT() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        for (final String s : this.theGameRules.keySet()) {
            nbtTagCompound.setString(s, ((Value)this.theGameRules.get(s)).getGameRuleStringValue());
        }
        return nbtTagCompound;
    }
    
    public void readGameRulesFromNBT(final NBTTagCompound nbtTagCompound) {
        for (final String s : nbtTagCompound.getKeySet()) {
            this.setOrCreateGameRule(s, nbtTagCompound.getString(s));
        }
    }
    
    public String[] getRules() {
        return (String[])this.theGameRules.keySet().toArray(new String[0]);
    }
    
    public boolean hasRule(final String s) {
        return this.theGameRules.containsKey(s);
    }
    
    public boolean areSameType(final String s, final ValueType valueType) {
        final Value value = this.theGameRules.get(s);
        return value != null && (value.getType() == valueType || valueType == ValueType.ANY_VALUE);
    }
    
    static class Value
    {
        private String valueString;
        private boolean valueBoolean;
        private int valueInteger;
        private double valueDouble;
        private final ValueType type;
        private static final String __OBFID;
        
        public Value(final String value, final ValueType type) {
            this.type = type;
            this.setValue(value);
        }
        
        public void setValue(final String valueString) {
            this.valueString = valueString;
            if (valueString != null) {
                if (valueString.equals("false")) {
                    this.valueBoolean = false;
                    return;
                }
                if (valueString.equals("true")) {
                    this.valueBoolean = true;
                    return;
                }
            }
            this.valueBoolean = Boolean.parseBoolean(valueString);
            this.valueInteger = (this.valueBoolean ? 1 : 0);
            this.valueInteger = Integer.parseInt(valueString);
            this.valueDouble = Double.parseDouble(valueString);
        }
        
        public String getGameRuleStringValue() {
            return this.valueString;
        }
        
        public boolean getGameRuleBooleanValue() {
            return this.valueBoolean;
        }
        
        public int getInt() {
            return this.valueInteger;
        }
        
        public ValueType getType() {
            return this.type;
        }
        
        static {
            __OBFID = "CL_00000137";
        }
    }
    
    public enum ValueType
    {
        ANY_VALUE("ANY_VALUE", 0, "ANY_VALUE", 0, "ANY_VALUE", 0), 
        BOOLEAN_VALUE("BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1), 
        NUMERICAL_VALUE("NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2);
        
        private static final ValueType[] $VALUES;
        private static final ValueType[] ENUM$VALUES;
        
        static {
            ENUM$VALUES = new ValueType[] { ValueType.ANY_VALUE, ValueType.BOOLEAN_VALUE, ValueType.NUMERICAL_VALUE };
            $VALUES = new ValueType[] { ValueType.ANY_VALUE, ValueType.BOOLEAN_VALUE, ValueType.NUMERICAL_VALUE };
        }
        
        private ValueType(final String s, final int n, final String s2, final int n2, final String s3, final int n3) {
        }
    }
}
