package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import java.util.*;

public class PaintingProvider implements Provider
{
    private final Map paintings;
    
    public PaintingProvider() {
        this.paintings = new HashMap();
        this.add("kebab");
        this.add("aztec");
        this.add("alban");
        this.add("aztec2");
        this.add("bomb");
        this.add("plant");
        this.add("wasteland");
        this.add("pool");
        this.add("courbet");
        this.add("sea");
        this.add("sunset");
        this.add("creebet");
        this.add("wanderer");
        this.add("graham");
        this.add("match");
        this.add("bust");
        this.add("stage");
        this.add("void");
        this.add("skullandroses");
        this.add("wither");
        this.add("fighters");
        this.add("pointer");
        this.add("pigscene");
        this.add("burningskull");
        this.add("skeleton");
        this.add("donkeykong");
    }
    
    private void add(final String s) {
        this.paintings.put("minecraft:" + s, this.paintings.size());
    }
    
    public Optional getIntByIdentifier(String string) {
        if (!string.startsWith("minecraft:")) {
            string = "minecraft:" + string.toLowerCase(Locale.ROOT);
        }
        return Optional.ofNullable(this.paintings.get(string));
    }
}
