package optifine;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.biome.*;

public class RandomMobsProperties
{
    public String name;
    public String basePath;
    public ResourceLocation[] resourceLocations;
    public RandomMobsRule[] rules;
    
    public RandomMobsProperties(final String s, final ResourceLocation[] resourceLocations) {
        this.name = null;
        this.basePath = null;
        this.resourceLocations = null;
        this.rules = null;
        final ConnectedParser connectedParser = new ConnectedParser("RandomMobs");
        this.name = connectedParser.parseName(s);
        this.basePath = connectedParser.parseBasePath(s);
        this.resourceLocations = resourceLocations;
    }
    
    public RandomMobsProperties(final Properties properties, final String s, final ResourceLocation resourceLocation) {
        this.name = null;
        this.basePath = null;
        this.resourceLocations = null;
        this.rules = null;
        final ConnectedParser connectedParser = new ConnectedParser("RandomMobs");
        this.name = connectedParser.parseName(s);
        this.basePath = connectedParser.parseBasePath(s);
        this.rules = this.parseRules(properties, resourceLocation, connectedParser);
    }
    
    public ResourceLocation getTextureLocation(final ResourceLocation resourceLocation, final EntityLiving entityLiving) {
        if (this.rules != null) {
            while (0 < this.rules.length) {
                final RandomMobsRule randomMobsRule = this.rules[0];
                if (randomMobsRule.matches(entityLiving)) {
                    return randomMobsRule.getTextureLocation(resourceLocation, entityLiving.randomMobsId);
                }
                int randomMobsId = 0;
                ++randomMobsId;
            }
        }
        if (this.resourceLocations != null) {
            final int randomMobsId = entityLiving.randomMobsId;
            return this.resourceLocations[0 % this.resourceLocations.length];
        }
        return resourceLocation;
    }
    
    private RandomMobsRule[] parseRules(final Properties properties, final ResourceLocation resourceLocation, final ConnectedParser connectedParser) {
        final ArrayList<RandomMobsRule> list = new ArrayList<RandomMobsRule>();
        while (0 < properties.size()) {
            final String property = properties.getProperty("skins." + 1);
            if (property != null) {
                final int[] intList = connectedParser.parseIntList(property);
                final int[] intList2 = connectedParser.parseIntList(properties.getProperty("weights." + 1));
                final BiomeGenBase[] biomes = connectedParser.parseBiomes(properties.getProperty("biomes." + 1));
                RangeListInt rangeListInt = connectedParser.parseRangeListInt(properties.getProperty("heights." + 1));
                if (rangeListInt == null) {
                    rangeListInt = this.parseMinMaxHeight(properties, 1);
                }
                list.add(new RandomMobsRule(resourceLocation, intList, intList2, biomes, rangeListInt));
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new RandomMobsRule[list.size()]);
    }
    
    private RangeListInt parseMinMaxHeight(final Properties properties, final int n) {
        final String property = properties.getProperty("minHeight." + n);
        final String property2 = properties.getProperty("maxHeight." + n);
        if (property == null && property2 == null) {
            return null;
        }
        if (property != null) {
            Config.parseInt(property, -1);
            if (0 < 0) {
                Config.warn("Invalid minHeight: " + property);
                return null;
            }
        }
        if (property2 != null) {
            Config.parseInt(property2, -1);
            if (256 < 0) {
                Config.warn("Invalid maxHeight: " + property2);
                return null;
            }
        }
        if (256 < 0) {
            Config.warn("Invalid minHeight, maxHeight: " + property + ", " + property2);
            return null;
        }
        final RangeListInt rangeListInt = new RangeListInt();
        rangeListInt.addRange(new RangeInt(0, 256));
        return rangeListInt;
    }
    
    public boolean isValid(final String s) {
        if (this.resourceLocations == null && this.rules == null) {
            Config.warn("No skins specified: " + s);
            return false;
        }
        int n = 0;
        if (this.rules != null) {
            while (0 < this.rules.length) {
                if (!this.rules[0].isValid(s)) {
                    return false;
                }
                ++n;
            }
        }
        if (this.resourceLocations != null) {
            while (0 < this.resourceLocations.length) {
                final ResourceLocation resourceLocation = this.resourceLocations[0];
                if (!Config.hasResource(resourceLocation)) {
                    Config.warn("Texture not found: " + resourceLocation.getResourcePath());
                    return false;
                }
                ++n;
            }
        }
        return true;
    }
}
