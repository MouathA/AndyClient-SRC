package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.*;
import com.viaversion.viaversion.api.data.*;
import com.viaversion.viaversion.libs.gson.*;
import java.lang.reflect.*;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class PistonHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler
{
    private final Map pistonIds;
    
    public PistonHandler() {
        this.pistonIds = new HashMap();
        if (Via.getConfig().isServersideBlockConnections()) {
            final Field declaredField = ConnectionData.class.getDeclaredField("keyToId");
            declaredField.setAccessible(true);
            for (final Map.Entry<String, V> entry : ((Map)declaredField.get(null)).entrySet()) {
                if (!entry.getKey().contains("piston")) {
                    continue;
                }
                this.addEntries(entry.getKey(), (int)entry.getValue());
            }
        }
        else {
            for (final Map.Entry<K, JsonElement> entry2 : MappingDataLoader.getMappingsCache().get("mapping-1.13.json").getAsJsonObject("blockstates").entrySet()) {
                final String asString = entry2.getValue().getAsString();
                if (!asString.contains("piston")) {
                    continue;
                }
                this.addEntries(asString, Integer.parseInt((String)entry2.getKey()));
            }
        }
    }
    
    private void addEntries(String string, int newBlockStateId) {
        newBlockStateId = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(newBlockStateId);
        this.pistonIds.put(string, newBlockStateId);
        final String substring = string.substring(10);
        if (!substring.startsWith("piston") && !substring.startsWith("sticky_piston")) {
            return;
        }
        final String[] split = string.substring(0, string.length() - 1).split("\\[");
        final String[] split2 = split[1].split(",");
        string = split[0] + "[" + split2[1] + "," + split2[0] + "]";
        this.pistonIds.put(string, newBlockStateId);
    }
    
    @Override
    public CompoundTag transform(final UserConnection userConnection, final int n, final CompoundTag compoundTag) {
        final CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("blockState");
        if (compoundTag2 == null) {
            return compoundTag;
        }
        final String dataFromTag = this.getDataFromTag(compoundTag2);
        if (dataFromTag == null) {
            return compoundTag;
        }
        final Integer n2 = this.pistonIds.get(dataFromTag);
        if (n2 == null) {
            return compoundTag;
        }
        compoundTag.put("blockId", new IntTag(n2 >> 4));
        compoundTag.put("blockData", new IntTag(n2 & 0xF));
        return compoundTag;
    }
    
    private String getDataFromTag(final CompoundTag compoundTag) {
        final StringTag stringTag = (StringTag)compoundTag.get("Name");
        if (stringTag == null) {
            return null;
        }
        final CompoundTag compoundTag2 = (CompoundTag)compoundTag.get("Properties");
        if (compoundTag2 == null) {
            return stringTag.getValue();
        }
        final StringJoiner stringJoiner = new StringJoiner(",", stringTag.getValue() + "[", "]");
        for (final Map.Entry<String, V> entry : compoundTag2) {
            if (!(entry.getValue() instanceof StringTag)) {
                continue;
            }
            stringJoiner.add(entry.getKey() + "=" + ((StringTag)entry.getValue()).getValue());
        }
        return stringJoiner.toString();
    }
}
