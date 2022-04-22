package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.google.common.collect.*;

public class EntityIdRewriter
{
    private static final BiMap oldToNewNames;
    
    public static void toClient(final CompoundTag compoundTag) {
        toClient(compoundTag, false);
    }
    
    public static void toClient(final CompoundTag compoundTag, final boolean b) {
        final Tag value = compoundTag.get("id");
        if (value instanceof StringTag) {
            final StringTag stringTag = (StringTag)value;
            final String value2 = b ? EntityIdRewriter.oldToNewNames.inverse().get(stringTag.getValue()) : EntityIdRewriter.oldToNewNames.get(stringTag.getValue());
            if (value2 != null) {
                stringTag.setValue(value2);
            }
        }
    }
    
    public static void toClientSpawner(final CompoundTag compoundTag) {
        toClientSpawner(compoundTag, false);
    }
    
    public static void toClientSpawner(final CompoundTag compoundTag, final boolean b) {
        if (compoundTag == null) {
            return;
        }
        final Tag value = compoundTag.get("SpawnData");
        if (value != null) {
            toClient((CompoundTag)value, b);
        }
    }
    
    public static void toClientItem(final Item item) {
        toClientItem(item, false);
    }
    
    public static void toClientItem(final Item item, final boolean b) {
        if (hasEntityTag(item)) {
            toClient((CompoundTag)item.tag().get("EntityTag"), b);
        }
        if (item != null && item.amount() <= 0) {
            item.setAmount(1);
        }
    }
    
    public static void toServerItem(final Item item) {
        toServerItem(item, false);
    }
    
    public static void toServerItem(final Item item, final boolean b) {
        if (!hasEntityTag(item)) {
            return;
        }
        final Tag value = ((CompoundTag)item.tag().get("EntityTag")).get("id");
        if (value instanceof StringTag) {
            final StringTag stringTag = (StringTag)value;
            final String value2 = b ? EntityIdRewriter.oldToNewNames.get(stringTag.getValue()) : EntityIdRewriter.oldToNewNames.inverse().get(stringTag.getValue());
            if (value2 != null) {
                stringTag.setValue(value2);
            }
        }
    }
    
    private static boolean hasEntityTag(final Item item) {
        if (item == null || item.identifier() != 383) {
            return false;
        }
        final CompoundTag tag = item.tag();
        if (tag == null) {
            return false;
        }
        final Tag value = tag.get("EntityTag");
        return value instanceof CompoundTag && ((CompoundTag)value).get("id") instanceof StringTag;
    }
    
    static {
        (oldToNewNames = HashBiMap.create()).put("AreaEffectCloud", "minecraft:area_effect_cloud");
        EntityIdRewriter.oldToNewNames.put("ArmorStand", "minecraft:armor_stand");
        EntityIdRewriter.oldToNewNames.put("Arrow", "minecraft:arrow");
        EntityIdRewriter.oldToNewNames.put("Bat", "minecraft:bat");
        EntityIdRewriter.oldToNewNames.put("Blaze", "minecraft:blaze");
        EntityIdRewriter.oldToNewNames.put("Boat", "minecraft:boat");
        EntityIdRewriter.oldToNewNames.put("CaveSpider", "minecraft:cave_spider");
        EntityIdRewriter.oldToNewNames.put("Chicken", "minecraft:chicken");
        EntityIdRewriter.oldToNewNames.put("Cow", "minecraft:cow");
        EntityIdRewriter.oldToNewNames.put("Creeper", "minecraft:creeper");
        EntityIdRewriter.oldToNewNames.put("Donkey", "minecraft:donkey");
        EntityIdRewriter.oldToNewNames.put("DragonFireball", "minecraft:dragon_fireball");
        EntityIdRewriter.oldToNewNames.put("ElderGuardian", "minecraft:elder_guardian");
        EntityIdRewriter.oldToNewNames.put("EnderCrystal", "minecraft:ender_crystal");
        EntityIdRewriter.oldToNewNames.put("EnderDragon", "minecraft:ender_dragon");
        EntityIdRewriter.oldToNewNames.put("Enderman", "minecraft:enderman");
        EntityIdRewriter.oldToNewNames.put("Endermite", "minecraft:endermite");
        EntityIdRewriter.oldToNewNames.put("EntityHorse", "minecraft:horse");
        EntityIdRewriter.oldToNewNames.put("EyeOfEnderSignal", "minecraft:eye_of_ender_signal");
        EntityIdRewriter.oldToNewNames.put("FallingSand", "minecraft:falling_block");
        EntityIdRewriter.oldToNewNames.put("Fireball", "minecraft:fireball");
        EntityIdRewriter.oldToNewNames.put("FireworksRocketEntity", "minecraft:fireworks_rocket");
        EntityIdRewriter.oldToNewNames.put("Ghast", "minecraft:ghast");
        EntityIdRewriter.oldToNewNames.put("Giant", "minecraft:giant");
        EntityIdRewriter.oldToNewNames.put("Guardian", "minecraft:guardian");
        EntityIdRewriter.oldToNewNames.put("Husk", "minecraft:husk");
        EntityIdRewriter.oldToNewNames.put("Item", "minecraft:item");
        EntityIdRewriter.oldToNewNames.put("ItemFrame", "minecraft:item_frame");
        EntityIdRewriter.oldToNewNames.put("LavaSlime", "minecraft:magma_cube");
        EntityIdRewriter.oldToNewNames.put("LeashKnot", "minecraft:leash_knot");
        EntityIdRewriter.oldToNewNames.put("MinecartChest", "minecraft:chest_minecart");
        EntityIdRewriter.oldToNewNames.put("MinecartCommandBlock", "minecraft:commandblock_minecart");
        EntityIdRewriter.oldToNewNames.put("MinecartFurnace", "minecraft:furnace_minecart");
        EntityIdRewriter.oldToNewNames.put("MinecartHopper", "minecraft:hopper_minecart");
        EntityIdRewriter.oldToNewNames.put("MinecartRideable", "minecraft:minecart");
        EntityIdRewriter.oldToNewNames.put("MinecartSpawner", "minecraft:spawner_minecart");
        EntityIdRewriter.oldToNewNames.put("MinecartTNT", "minecraft:tnt_minecart");
        EntityIdRewriter.oldToNewNames.put("Mule", "minecraft:mule");
        EntityIdRewriter.oldToNewNames.put("MushroomCow", "minecraft:mooshroom");
        EntityIdRewriter.oldToNewNames.put("Ozelot", "minecraft:ocelot");
        EntityIdRewriter.oldToNewNames.put("Painting", "minecraft:painting");
        EntityIdRewriter.oldToNewNames.put("Pig", "minecraft:pig");
        EntityIdRewriter.oldToNewNames.put("PigZombie", "minecraft:zombie_pigman");
        EntityIdRewriter.oldToNewNames.put("PolarBear", "minecraft:polar_bear");
        EntityIdRewriter.oldToNewNames.put("PrimedTnt", "minecraft:tnt");
        EntityIdRewriter.oldToNewNames.put("Rabbit", "minecraft:rabbit");
        EntityIdRewriter.oldToNewNames.put("Sheep", "minecraft:sheep");
        EntityIdRewriter.oldToNewNames.put("Shulker", "minecraft:shulker");
        EntityIdRewriter.oldToNewNames.put("ShulkerBullet", "minecraft:shulker_bullet");
        EntityIdRewriter.oldToNewNames.put("Silverfish", "minecraft:silverfish");
        EntityIdRewriter.oldToNewNames.put("Skeleton", "minecraft:skeleton");
        EntityIdRewriter.oldToNewNames.put("SkeletonHorse", "minecraft:skeleton_horse");
        EntityIdRewriter.oldToNewNames.put("Slime", "minecraft:slime");
        EntityIdRewriter.oldToNewNames.put("SmallFireball", "minecraft:small_fireball");
        EntityIdRewriter.oldToNewNames.put("Snowball", "minecraft:snowball");
        EntityIdRewriter.oldToNewNames.put("SnowMan", "minecraft:snowman");
        EntityIdRewriter.oldToNewNames.put("SpectralArrow", "minecraft:spectral_arrow");
        EntityIdRewriter.oldToNewNames.put("Spider", "minecraft:spider");
        EntityIdRewriter.oldToNewNames.put("Squid", "minecraft:squid");
        EntityIdRewriter.oldToNewNames.put("Stray", "minecraft:stray");
        EntityIdRewriter.oldToNewNames.put("ThrownEgg", "minecraft:egg");
        EntityIdRewriter.oldToNewNames.put("ThrownEnderpearl", "minecraft:ender_pearl");
        EntityIdRewriter.oldToNewNames.put("ThrownExpBottle", "minecraft:xp_bottle");
        EntityIdRewriter.oldToNewNames.put("ThrownPotion", "minecraft:potion");
        EntityIdRewriter.oldToNewNames.put("Villager", "minecraft:villager");
        EntityIdRewriter.oldToNewNames.put("VillagerGolem", "minecraft:villager_golem");
        EntityIdRewriter.oldToNewNames.put("Witch", "minecraft:witch");
        EntityIdRewriter.oldToNewNames.put("WitherBoss", "minecraft:wither");
        EntityIdRewriter.oldToNewNames.put("WitherSkeleton", "minecraft:wither_skeleton");
        EntityIdRewriter.oldToNewNames.put("WitherSkull", "minecraft:wither_skull");
        EntityIdRewriter.oldToNewNames.put("Wolf", "minecraft:wolf");
        EntityIdRewriter.oldToNewNames.put("XPOrb", "minecraft:xp_orb");
        EntityIdRewriter.oldToNewNames.put("Zombie", "minecraft:zombie");
        EntityIdRewriter.oldToNewNames.put("ZombieHorse", "minecraft:zombie_horse");
        EntityIdRewriter.oldToNewNames.put("ZombieVillager", "minecraft:zombie_villager");
    }
}
