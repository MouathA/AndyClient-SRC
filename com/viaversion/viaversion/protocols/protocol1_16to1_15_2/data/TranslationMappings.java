package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import java.util.*;
import com.viaversion.viaversion.libs.gson.*;

public class TranslationMappings extends ComponentRewriter
{
    private final Map mappings;
    
    public TranslationMappings(final Protocol protocol) {
        super(protocol);
        (this.mappings = new HashMap()).put("attribute.name.generic.armorToughness", "attribute.name.generic.armor_toughness");
        this.mappings.put("attribute.name.generic.attackDamage", "attribute.name.generic.attack_damage");
        this.mappings.put("attribute.name.generic.attackSpeed", "attribute.name.generic.attack_speed");
        this.mappings.put("attribute.name.generic.followRange", "attribute.name.generic.follow_range");
        this.mappings.put("attribute.name.generic.knockbackResistance", "attribute.name.generic.knockback_resistance");
        this.mappings.put("attribute.name.generic.maxHealth", "attribute.name.generic.max_health");
        this.mappings.put("attribute.name.generic.movementSpeed", "attribute.name.generic.movement_speed");
        this.mappings.put("attribute.name.horse.jumpStrength", "attribute.name.horse.jump_strength");
        this.mappings.put("attribute.name.zombie.spawnReinforcements", "attribute.name.zombie.spawn_reinforcements");
        this.mappings.put("block.minecraft.banner", "Banner");
        this.mappings.put("block.minecraft.wall_banner", "Wall Banner");
        this.mappings.put("block.minecraft.bed", "Bed");
        this.mappings.put("block.minecraft.bed.not_valid", "block.minecraft.spawn.not_valid");
        this.mappings.put("block.minecraft.bed.set_spawn", "block.minecraft.set_spawn");
        this.mappings.put("block.minecraft.flowing_water", "Flowing Water");
        this.mappings.put("block.minecraft.flowing_lava", "Flowing Lava");
        this.mappings.put("block.minecraft.two_turtle_eggs", "Two Turtle Eggs");
        this.mappings.put("block.minecraft.three_turtle_eggs", "Three Turtle Eggs");
        this.mappings.put("block.minecraft.four_turtle_eggs", "Four Turtle Eggs");
        this.mappings.put("item.minecraft.skeleton_skull", "block.minecraft.skeleton_skull");
        this.mappings.put("item.minecraft.wither_skeleton_skull", "block.minecraft.skeleton_wall_skull");
        this.mappings.put("item.minecraft.zombie_head", "block.minecraft.zombie_head");
        this.mappings.put("item.minecraft.creeper_head", "block.minecraft.creeper_head");
        this.mappings.put("item.minecraft.dragon_head", "block.minecraft.dragon_head");
        this.mappings.put("entity.minecraft.zombie_pigman", "Zombie Pigman");
        this.mappings.put("item.minecraft.zombie_pigman_spawn_egg", "Zombie Pigman Spawn Egg");
        this.mappings.put("death.fell.accident.water", "%1$s fell out of the water");
        this.mappings.put("death.attack.netherBed.message", "death.attack.badRespawnPoint.message");
        this.mappings.put("death.attack.netherBed.link", "death.attack.badRespawnPoint.link");
        this.mappings.put("advancements.husbandry.break_diamond_hoe.title", "Serious Dedication");
        this.mappings.put("advancements.husbandry.break_diamond_hoe.description", "Completely use up a diamond hoe, and then reevaluate your life choices");
        this.mappings.put("biome.minecraft.nether", "Nether");
        this.mappings.put("key.swapHands", "key.swapOffhand");
    }
    
    @Override
    public void processText(final JsonElement jsonElement) {
        super.processText(jsonElement);
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            return;
        }
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final JsonObject asJsonObject2 = asJsonObject.getAsJsonObject("score");
        if (asJsonObject2 == null || asJsonObject.has("text")) {
            return;
        }
        final JsonPrimitive asJsonPrimitive = asJsonObject2.getAsJsonPrimitive("value");
        if (asJsonPrimitive != null) {
            asJsonObject.remove("score");
            asJsonObject.add("text", asJsonPrimitive);
        }
    }
    
    @Override
    protected void handleTranslate(final JsonObject jsonObject, final String s) {
        final String s2 = this.mappings.get(s);
        if (s2 != null) {
            jsonObject.addProperty("translate", s2);
        }
    }
}
