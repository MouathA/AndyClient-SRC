package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;
import com.viaversion.viabackwards.api.*;
import com.google.common.base.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.data.*;
import java.util.logging.*;
import com.viaversion.viabackwards.*;

public class BackwardsMappings extends MappingDataBase
{
    private final Class vvProtocolClass;
    private Int2ObjectMap backwardsItemMappings;
    private Map backwardsSoundMappings;
    private Map entityNames;
    
    public BackwardsMappings(final String s, final String s2, final Class clazz) {
        this(s, s2, clazz, false);
    }
    
    public BackwardsMappings(final String s, final String s2, final Class vvProtocolClass, final boolean b) {
        super(s, s2, b);
        Preconditions.checkArgument(vvProtocolClass == null || !vvProtocolClass.isAssignableFrom(BackwardsProtocol.class));
        this.vvProtocolClass = vvProtocolClass;
        this.loadItems = false;
    }
    
    @Override
    protected void loadExtras(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        if (jsonObject3 != null) {
            final JsonObject asJsonObject = jsonObject3.getAsJsonObject("items");
            if (asJsonObject != null) {
                this.backwardsItemMappings = VBMappingDataLoader.loadItemMappings(jsonObject.getAsJsonObject("items"), jsonObject2.getAsJsonObject("items"), asJsonObject, this.shouldWarnOnMissing("items"));
            }
            final JsonObject asJsonObject2 = jsonObject3.getAsJsonObject("sounds");
            if (asJsonObject2 != null) {
                this.backwardsSoundMappings = VBMappingDataLoader.objectToNamespacedMap(asJsonObject2);
            }
            final JsonObject asJsonObject3 = jsonObject3.getAsJsonObject("entitynames");
            if (asJsonObject3 != null) {
                this.entityNames = VBMappingDataLoader.objectToMap(asJsonObject3);
            }
        }
        if (this.vvProtocolClass != null) {
            this.itemMappings = Via.getManager().getProtocolManager().getProtocol(this.vvProtocolClass).getMappingData().getItemMappings().inverse();
        }
        this.loadVBExtras(jsonObject, jsonObject2);
    }
    
    @Override
    protected Mappings loadFromArray(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final String s) {
        if (!jsonObject.has(s) || !jsonObject2.has(s)) {
            return null;
        }
        return VBMappings.vbBuilder().unmapped(jsonObject.getAsJsonArray(s)).mapped(jsonObject2.getAsJsonArray(s)).diffMappings((jsonObject3 != null) ? jsonObject3.getAsJsonObject(s) : null).warnOnMissing(this.shouldWarnOnMissing(s)).build();
    }
    
    @Override
    protected Mappings loadFromObject(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3, final String s) {
        if (!jsonObject.has(s) || !jsonObject2.has(s)) {
            return null;
        }
        return VBMappings.vbBuilder().unmapped(jsonObject.getAsJsonObject(s)).mapped(jsonObject2.getAsJsonObject(s)).diffMappings((jsonObject3 != null) ? jsonObject3.getAsJsonObject(s) : null).warnOnMissing(this.shouldWarnOnMissing(s)).build();
    }
    
    @Override
    protected JsonObject loadDiffFile() {
        return VBMappingDataLoader.loadFromDataDir("mapping-" + this.newVersion + "to" + this.oldVersion + ".json");
    }
    
    protected void loadVBExtras(final JsonObject jsonObject, final JsonObject jsonObject2) {
    }
    
    protected boolean shouldWarnOnMissing(final String s) {
        return !s.equals("blocks") && !s.equals("statistics");
    }
    
    @Override
    protected Logger getLogger() {
        return ViaBackwards.getPlatform().getLogger();
    }
    
    @Override
    public int getNewItemId(final int n) {
        return this.itemMappings.get(n);
    }
    
    @Override
    public int getNewBlockId(final int n) {
        return this.blockMappings.getNewId(n);
    }
    
    @Override
    public int getOldItemId(final int n) {
        return this.checkValidity(n, this.itemMappings.inverse().get(n), "item");
    }
    
    public MappedItem getMappedItem(final int n) {
        return (this.backwardsItemMappings != null) ? ((MappedItem)this.backwardsItemMappings.get(n)) : null;
    }
    
    public String getMappedNamedSound(String string) {
        if (this.backwardsSoundMappings == null) {
            return null;
        }
        if (string.indexOf(58) == -1) {
            string = "minecraft:" + string;
        }
        return this.backwardsSoundMappings.get(string);
    }
    
    public String mappedEntityName(final String s) {
        return this.entityNames.get(s);
    }
    
    public Int2ObjectMap getBackwardsItemMappings() {
        return this.backwardsItemMappings;
    }
    
    public Map getBackwardsSoundMappings() {
        return this.backwardsSoundMappings;
    }
}
