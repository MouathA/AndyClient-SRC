package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.api.data.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.libs.gson.*;

public class MappingData extends MappingDataBase
{
    private final BiMap attributeMappings;
    
    public MappingData() {
        super("1.15", "1.16", true);
        this.attributeMappings = HashBiMap.create();
    }
    
    @Override
    protected void loadExtras(final JsonObject jsonObject, final JsonObject jsonObject2, final JsonObject jsonObject3) {
        this.attributeMappings.put("generic.maxHealth", "minecraft:generic.max_health");
        this.attributeMappings.put("zombie.spawnReinforcements", "minecraft:zombie.spawn_reinforcements");
        this.attributeMappings.put("horse.jumpStrength", "minecraft:horse.jump_strength");
        this.attributeMappings.put("generic.followRange", "minecraft:generic.follow_range");
        this.attributeMappings.put("generic.knockbackResistance", "minecraft:generic.knockback_resistance");
        this.attributeMappings.put("generic.movementSpeed", "minecraft:generic.movement_speed");
        this.attributeMappings.put("generic.flyingSpeed", "minecraft:generic.flying_speed");
        this.attributeMappings.put("generic.attackDamage", "minecraft:generic.attack_damage");
        this.attributeMappings.put("generic.attackKnockback", "minecraft:generic.attack_knockback");
        this.attributeMappings.put("generic.attackSpeed", "minecraft:generic.attack_speed");
        this.attributeMappings.put("generic.armorToughness", "minecraft:generic.armor_toughness");
    }
    
    public BiMap getAttributeMappings() {
        return this.attributeMappings;
    }
}
