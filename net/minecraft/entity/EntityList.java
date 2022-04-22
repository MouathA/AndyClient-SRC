package net.minecraft.entity;

import org.apache.logging.log4j.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.effect.*;
import net.minecraft.stats.*;

public class EntityList
{
    private static final Logger logger;
    private static final Map stringToClassMapping;
    private static final Map classToStringMapping;
    private static final Map idToClassMapping;
    private static final Map classToIDMapping;
    private static final Map field_180126_g;
    public static final Map entityEggs;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001538";
        logger = LogManager.getLogger();
        stringToClassMapping = Maps.newHashMap();
        classToStringMapping = Maps.newHashMap();
        idToClassMapping = Maps.newHashMap();
        classToIDMapping = Maps.newHashMap();
        field_180126_g = Maps.newHashMap();
        entityEggs = Maps.newLinkedHashMap();
        addMapping(EntityItem.class, "Item", 1);
        addMapping(EntityXPOrb.class, "XPOrb", 2);
        addMapping(EntityLeashKnot.class, "LeashKnot", 8);
        addMapping(EntityPainting.class, "Painting", 9);
        addMapping(EntityArrow.class, "Arrow", 10);
        addMapping(EntitySnowball.class, "Snowball", 11);
        addMapping(EntityLargeFireball.class, "Fireball", 12);
        addMapping(EntitySmallFireball.class, "SmallFireball", 13);
        addMapping(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        addMapping(EntityEnderEye.class, "EyeOfEnderSignal", 15);
        addMapping(EntityPotion.class, "ThrownPotion", 16);
        addMapping(EntityExpBottle.class, "ThrownExpBottle", 17);
        addMapping(EntityItemFrame.class, "ItemFrame", 18);
        addMapping(EntityWitherSkull.class, "WitherSkull", 19);
        addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
        addMapping(EntityFallingBlock.class, "FallingSand", 21);
        addMapping(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
        addMapping(EntityArmorStand.class, "ArmorStand", 30);
        addMapping(EntityBoat.class, "Boat", 41);
        addMapping(EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.RIDEABLE.func_180040_b(), 42);
        addMapping(EntityMinecartChest.class, EntityMinecart.EnumMinecartType.CHEST.func_180040_b(), 43);
        addMapping(EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.FURNACE.func_180040_b(), 44);
        addMapping(EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.TNT.func_180040_b(), 45);
        addMapping(EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.HOPPER.func_180040_b(), 46);
        addMapping(EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.SPAWNER.func_180040_b(), 47);
        addMapping(EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.COMMAND_BLOCK.func_180040_b(), 40);
        addMapping(EntityLiving.class, "Mob", 48);
        addMapping(EntityMob.class, "Monster", 49);
        addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
        addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        addMapping(EntityGiantZombie.class, "Giant", 53);
        addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        addMapping(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        addMapping(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        addMapping(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        addMapping(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        addMapping(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        addMapping(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        addMapping(EntityDragon.class, "EnderDragon", 63);
        addMapping(EntityWither.class, "WitherBoss", 64);
        addMapping(EntityBat.class, "Bat", 65, 4996656, 986895);
        addMapping(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        addMapping(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
        addMapping(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
        addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
        addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
        addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        addMapping(EntitySquid.class, "Squid", 94, 2243405, 7375001);
        addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        addMapping(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
        addMapping(EntitySnowman.class, "SnowMan", 97);
        addMapping(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        addMapping(EntityIronGolem.class, "VillagerGolem", 99);
        addMapping(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
        addMapping(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
        addMapping(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        addMapping(EntityEnderCrystal.class, "EnderCrystal", 200);
    }
    
    private static void addMapping(final Class clazz, final String s, final int n) {
        if (EntityList.stringToClassMapping.containsKey(s)) {
            throw new IllegalArgumentException("ID is already registered: " + s);
        }
        if (EntityList.idToClassMapping.containsKey(n)) {
            throw new IllegalArgumentException("ID is already registered: " + n);
        }
        if (n == 0) {
            throw new IllegalArgumentException("Cannot register to reserved id: " + n);
        }
        if (clazz == null) {
            throw new IllegalArgumentException("Cannot register null clazz for id: " + n);
        }
        EntityList.stringToClassMapping.put(s, clazz);
        EntityList.classToStringMapping.put(clazz, s);
        EntityList.idToClassMapping.put(n, clazz);
        EntityList.classToIDMapping.put(clazz, n);
        EntityList.field_180126_g.put(s, n);
    }
    
    private static void addMapping(final Class clazz, final String s, final int n, final int n2, final int n3) {
        addMapping(clazz, s, n);
        EntityList.entityEggs.put(n, new EntityEggInfo(n, n2, n3));
    }
    
    public static Entity createEntityByName(final String s, final World world) {
        Entity entity = null;
        final Class<Entity> clazz = EntityList.stringToClassMapping.get(s);
        if (clazz != null) {
            entity = clazz.getConstructor(World.class).newInstance(world);
        }
        return entity;
    }
    
    public static Entity createEntityFromNBT(final NBTTagCompound nbtTagCompound, final World world) {
        Entity entity = null;
        if ("Minecart".equals(nbtTagCompound.getString("id"))) {
            nbtTagCompound.setString("id", EntityMinecart.EnumMinecartType.func_180038_a(nbtTagCompound.getInteger("Type")).func_180040_b());
            nbtTagCompound.removeTag("Type");
        }
        final Class<Entity> clazz = EntityList.stringToClassMapping.get(nbtTagCompound.getString("id"));
        if (clazz != null) {
            entity = clazz.getConstructor(World.class).newInstance(world);
        }
        if (entity != null) {
            entity.readFromNBT(nbtTagCompound);
        }
        else {
            EntityList.logger.warn("Skipping Entity with id " + nbtTagCompound.getString("id"));
        }
        return entity;
    }
    
    public static Entity createEntityByID(final int n, final World world) {
        Entity entity = null;
        final Class classFromID = getClassFromID(n);
        if (classFromID != null) {
            entity = classFromID.getConstructor(World.class).newInstance(world);
        }
        if (entity == null) {
            EntityList.logger.warn("Skipping Entity with id " + n);
        }
        return entity;
    }
    
    public static int getEntityID(final Entity entity) {
        final Integer n = EntityList.classToIDMapping.get(entity.getClass());
        return (n == null) ? 0 : n;
    }
    
    public static Class getClassFromID(final int n) {
        return EntityList.idToClassMapping.get(n);
    }
    
    public static String getEntityString(final Entity entity) {
        return EntityList.classToStringMapping.get(entity.getClass());
    }
    
    public static int func_180122_a(final String s) {
        final Integer n = EntityList.field_180126_g.get(s);
        return (n == null) ? 90 : n;
    }
    
    public static String getStringFromID(final int n) {
        return EntityList.classToStringMapping.get(getClassFromID(n));
    }
    
    public static void func_151514_a() {
    }
    
    public static List func_180124_b() {
        final Set<String> keySet = EntityList.stringToClassMapping.keySet();
        final ArrayList arrayList = Lists.newArrayList();
        for (final String s : keySet) {
            if ((((Class)EntityList.stringToClassMapping.get(s)).getModifiers() & 0x400) != 0x400) {
                arrayList.add(s);
            }
        }
        arrayList.add("LightningBolt");
        return arrayList;
    }
    
    public static boolean func_180123_a(final Entity entity, final String s) {
        String entityString = getEntityString(entity);
        if (entityString == null && entity instanceof EntityPlayer) {
            entityString = "Player";
        }
        else if (entityString == null && entity instanceof EntityLightningBolt) {
            entityString = "LightningBolt";
        }
        return s.equals(entityString);
    }
    
    public static boolean func_180125_b(final String s) {
        return "Player".equals(s) || func_180124_b().contains(s);
    }
    
    public static class EntityEggInfo
    {
        public final int spawnedID;
        public final int primaryColor;
        public final int secondaryColor;
        public final StatBase field_151512_d;
        public final StatBase field_151513_e;
        private static final String __OBFID;
        
        public EntityEggInfo(final int spawnedID, final int primaryColor, final int secondaryColor) {
            this.spawnedID = spawnedID;
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
            this.field_151512_d = StatList.func_151182_a(this);
            this.field_151513_e = StatList.func_151176_b(this);
        }
        
        static {
            __OBFID = "CL_00001539";
        }
    }
}
