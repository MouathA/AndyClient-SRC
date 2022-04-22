package optifine;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;

public class RandomMobs
{
    private static Map locationProperties;
    private static RenderGlobal renderGlobal;
    private static boolean initialized;
    private static Random random;
    private static boolean working;
    public static final String SUFFIX_PNG;
    public static final String SUFFIX_PROPERTIES;
    public static final String PREFIX_TEXTURES_ENTITY;
    public static final String PREFIX_MCPATCHER_MOB;
    private static final String[] DEPENDANT_SUFFIXES;
    private static final String[] llIIllIIIIIIlIl;
    private static String[] llIIllIIIIIlllI;
    
    static {
        lIIlIllIIlIllIll();
        lIIlIllIIlIllIlI();
        SUFFIX_PNG = RandomMobs.llIIllIIIIIIlIl[0];
        PREFIX_TEXTURES_ENTITY = RandomMobs.llIIllIIIIIIlIl[1];
        SUFFIX_PROPERTIES = RandomMobs.llIIllIIIIIIlIl[2];
        PREFIX_MCPATCHER_MOB = RandomMobs.llIIllIIIIIIlIl[3];
        RandomMobs.locationProperties = new HashMap();
        RandomMobs.renderGlobal = null;
        RandomMobs.initialized = false;
        RandomMobs.random = new Random();
        RandomMobs.working = false;
        DEPENDANT_SUFFIXES = new String[] { RandomMobs.llIIllIIIIIIlIl[4], RandomMobs.llIIllIIIIIIlIl[5], RandomMobs.llIIllIIIIIIlIl[6], RandomMobs.llIIllIIIIIIlIl[7], RandomMobs.llIIllIIIIIIlIl[8], RandomMobs.llIIllIIIIIIlIl[9], RandomMobs.llIIllIIIIIIlIl[10], RandomMobs.llIIllIIIIIIlIl[11], RandomMobs.llIIllIIIIIIlIl[12], RandomMobs.llIIllIIIIIIlIl[13] };
    }
    
    public static void entityLoaded(final Entity entity, final World world) {
        if (entity instanceof EntityLiving && world != null) {
            final EntityLiving entityLiving = (EntityLiving)entity;
            entityLiving.spawnPosition = entityLiving.getPosition();
            entityLiving.spawnBiome = world.getBiomeGenForCoords(entityLiving.spawnPosition);
            final WorldServer worldServer = Config.getWorldServer();
            if (worldServer != null) {
                final Entity entityByID = worldServer.getEntityByID(entity.getEntityId());
                if (entityByID instanceof EntityLiving) {
                    entityLiving.randomMobsId = (int)(((EntityLiving)entityByID).getUniqueID().getLeastSignificantBits() & 0x7FFFFFFFL);
                }
            }
        }
    }
    
    public static void worldChanged(final World world, final World world2) {
        if (world2 != null) {
            final List loadedEntityList = world2.getLoadedEntityList();
            for (int i = 0; i < loadedEntityList.size(); ++i) {
                entityLoaded(loadedEntityList.get(i), world2);
            }
        }
    }
    
    public static ResourceLocation getTextureLocation(final ResourceLocation resourceLocation) {
        if (RandomMobs.working) {
            return resourceLocation;
        }
        ResourceLocation textureLocation;
        try {
            RandomMobs.working = true;
            if (!RandomMobs.initialized) {
                initialize();
            }
            if (RandomMobs.renderGlobal == null) {
                return resourceLocation;
            }
            final Entity renderedEntity = RandomMobs.renderGlobal.renderedEntity;
            if (!(renderedEntity instanceof EntityLiving)) {
                return resourceLocation;
            }
            final EntityLiving entityLiving = (EntityLiving)renderedEntity;
            if (!resourceLocation.getResourcePath().startsWith(RandomMobs.llIIllIIIIIIlIl[14])) {
                return resourceLocation;
            }
            final RandomMobsProperties properties = getProperties(resourceLocation);
            if (properties != null) {
                textureLocation = properties.getTextureLocation(resourceLocation, entityLiving);
                return textureLocation;
            }
            textureLocation = resourceLocation;
        }
        finally {
            RandomMobs.working = false;
        }
        RandomMobs.working = false;
        return textureLocation;
    }
    
    private static RandomMobsProperties getProperties(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        RandomMobsProperties properties = RandomMobs.locationProperties.get(resourcePath);
        if (properties == null) {
            properties = makeProperties(resourceLocation);
            RandomMobs.locationProperties.put(resourcePath, properties);
        }
        return properties;
    }
    
    private static RandomMobsProperties makeProperties(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        final ResourceLocation propertyLocation = getPropertyLocation(resourceLocation);
        if (propertyLocation != null) {
            final RandomMobsProperties properties = parseProperties(propertyLocation, resourceLocation);
            if (properties != null) {
                return properties;
            }
        }
        return new RandomMobsProperties(resourcePath, getTextureVariants(resourceLocation));
    }
    
    private static RandomMobsProperties parseProperties(final ResourceLocation resourceLocation, final ResourceLocation resourceLocation2) {
        try {
            final String resourcePath = resourceLocation.getResourcePath();
            Config.dbg(RandomMobs.llIIllIIIIIIlIl[15] + resourceLocation2.getResourcePath() + RandomMobs.llIIllIIIIIIlIl[16] + resourcePath);
            final InputStream resourceStream = Config.getResourceStream(resourceLocation);
            if (resourceStream == null) {
                Config.warn(RandomMobs.llIIllIIIIIIlIl[17] + resourcePath);
                return null;
            }
            final Properties properties = new Properties();
            properties.load(resourceStream);
            resourceStream.close();
            final RandomMobsProperties randomMobsProperties = new RandomMobsProperties(properties, resourcePath, resourceLocation2);
            return randomMobsProperties.isValid(resourcePath) ? randomMobsProperties : null;
        }
        catch (FileNotFoundException ex2) {
            Config.warn(RandomMobs.llIIllIIIIIIlIl[18] + resourceLocation2.getResourcePath());
            return null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static ResourceLocation getPropertyLocation(final ResourceLocation resourceLocation) {
        final ResourceLocation mcpatcherLocation = getMcpatcherLocation(resourceLocation);
        if (mcpatcherLocation == null) {
            return null;
        }
        final String resourceDomain = mcpatcherLocation.getResourceDomain();
        String s2;
        final String s = s2 = mcpatcherLocation.getResourcePath();
        if (s.endsWith(RandomMobs.llIIllIIIIIIlIl[19])) {
            s2 = s.substring(0, s.length() - RandomMobs.llIIllIIIIIIlIl[20].length());
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(resourceDomain, String.valueOf(s2) + RandomMobs.llIIllIIIIIIlIl[21]);
        if (Config.hasResource(resourceLocation2)) {
            return resourceLocation2;
        }
        final String parentPath = getParentPath(s2);
        if (parentPath == null) {
            return null;
        }
        final ResourceLocation resourceLocation3 = new ResourceLocation(resourceDomain, String.valueOf(parentPath) + RandomMobs.llIIllIIIIIIlIl[22]);
        return Config.hasResource(resourceLocation3) ? resourceLocation3 : null;
    }
    
    public static ResourceLocation getMcpatcherLocation(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        if (!resourcePath.startsWith(RandomMobs.llIIllIIIIIIlIl[23])) {
            return null;
        }
        return new ResourceLocation(resourceLocation.getResourceDomain(), RandomMobs.llIIllIIIIIIlIl[24] + resourcePath.substring(RandomMobs.llIIllIIIIIIlIl[25].length()));
    }
    
    public static ResourceLocation getLocationIndexed(final ResourceLocation resourceLocation, final int n) {
        if (resourceLocation == null) {
            return null;
        }
        final String resourcePath = resourceLocation.getResourcePath();
        final int lastIndex = resourcePath.lastIndexOf(46);
        if (lastIndex < 0) {
            return null;
        }
        return new ResourceLocation(resourceLocation.getResourceDomain(), String.valueOf(resourcePath.substring(0, lastIndex)) + n + resourcePath.substring(lastIndex));
    }
    
    private static String getParentPath(final String s) {
        for (int i = 0; i < RandomMobs.DEPENDANT_SUFFIXES.length; ++i) {
            final String s2 = RandomMobs.DEPENDANT_SUFFIXES[i];
            if (s.endsWith(s2)) {
                return s.substring(0, s.length() - s2.length());
            }
        }
        return null;
    }
    
    private static ResourceLocation[] getTextureVariants(final ResourceLocation resourceLocation) {
        final ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        list.add(resourceLocation);
        final ResourceLocation mcpatcherLocation = getMcpatcherLocation(resourceLocation);
        if (mcpatcherLocation == null) {
            return null;
        }
        for (int i = 1; i < list.size() + 10; ++i) {
            final ResourceLocation locationIndexed = getLocationIndexed(mcpatcherLocation, i + 1);
            if (Config.hasResource(locationIndexed)) {
                list.add(locationIndexed);
            }
        }
        if (list.size() <= 1) {
            return null;
        }
        final ResourceLocation[] array = list.toArray(new ResourceLocation[list.size()]);
        Config.dbg(RandomMobs.llIIllIIIIIIlIl[26] + resourceLocation.getResourcePath() + RandomMobs.llIIllIIIIIIlIl[27] + array.length);
        return array;
    }
    
    public static void resetTextures() {
        RandomMobs.locationProperties.clear();
        if (Config.isRandomMobs()) {
            initialize();
        }
    }
    
    private static void initialize() {
        RandomMobs.renderGlobal = Config.getRenderGlobal();
        if (RandomMobs.renderGlobal != null) {
            RandomMobs.initialized = true;
            final ArrayList<String> list = new ArrayList<String>();
            list.add(RandomMobs.llIIllIIIIIIlIl[28]);
            list.add(RandomMobs.llIIllIIIIIIlIl[29]);
            list.add(RandomMobs.llIIllIIIIIIlIl[30]);
            list.add(RandomMobs.llIIllIIIIIIlIl[31]);
            list.add(RandomMobs.llIIllIIIIIIlIl[32]);
            list.add(RandomMobs.llIIllIIIIIIlIl[33]);
            list.add(RandomMobs.llIIllIIIIIIlIl[34]);
            list.add(RandomMobs.llIIllIIIIIIlIl[35]);
            list.add(RandomMobs.llIIllIIIIIIlIl[36]);
            list.add(RandomMobs.llIIllIIIIIIlIl[37]);
            list.add(RandomMobs.llIIllIIIIIIlIl[38]);
            list.add(RandomMobs.llIIllIIIIIIlIl[39]);
            list.add(RandomMobs.llIIllIIIIIIlIl[40]);
            list.add(RandomMobs.llIIllIIIIIIlIl[41]);
            list.add(RandomMobs.llIIllIIIIIIlIl[42]);
            list.add(RandomMobs.llIIllIIIIIIlIl[43]);
            list.add(RandomMobs.llIIllIIIIIIlIl[44]);
            list.add(RandomMobs.llIIllIIIIIIlIl[45]);
            list.add(RandomMobs.llIIllIIIIIIlIl[46]);
            list.add(RandomMobs.llIIllIIIIIIlIl[47]);
            list.add(RandomMobs.llIIllIIIIIIlIl[48]);
            list.add(RandomMobs.llIIllIIIIIIlIl[49]);
            list.add(RandomMobs.llIIllIIIIIIlIl[50]);
            list.add(RandomMobs.llIIllIIIIIIlIl[51]);
            list.add(RandomMobs.llIIllIIIIIIlIl[52]);
            list.add(RandomMobs.llIIllIIIIIIlIl[53]);
            list.add(RandomMobs.llIIllIIIIIIlIl[54]);
            list.add(RandomMobs.llIIllIIIIIIlIl[55]);
            list.add(RandomMobs.llIIllIIIIIIlIl[56]);
            list.add(RandomMobs.llIIllIIIIIIlIl[57]);
            list.add(RandomMobs.llIIllIIIIIIlIl[58]);
            list.add(RandomMobs.llIIllIIIIIIlIl[59]);
            list.add(RandomMobs.llIIllIIIIIIlIl[60]);
            list.add(RandomMobs.llIIllIIIIIIlIl[61]);
            list.add(RandomMobs.llIIllIIIIIIlIl[62]);
            list.add(RandomMobs.llIIllIIIIIIlIl[63]);
            list.add(RandomMobs.llIIllIIIIIIlIl[64]);
            list.add(RandomMobs.llIIllIIIIIIlIl[65]);
            list.add(RandomMobs.llIIllIIIIIIlIl[66]);
            list.add(RandomMobs.llIIllIIIIIIlIl[67]);
            list.add(RandomMobs.llIIllIIIIIIlIl[68]);
            list.add(RandomMobs.llIIllIIIIIIlIl[69]);
            list.add(RandomMobs.llIIllIIIIIIlIl[70]);
            list.add(RandomMobs.llIIllIIIIIIlIl[71]);
            for (int i = 0; i < list.size(); ++i) {
                final ResourceLocation resourceLocation = new ResourceLocation(RandomMobs.llIIllIIIIIIlIl[72] + list.get(i) + RandomMobs.llIIllIIIIIIlIl[73]);
                if (!Config.hasResource(resourceLocation)) {
                    Config.warn(RandomMobs.llIIllIIIIIIlIl[74] + resourceLocation);
                }
                getProperties(resourceLocation);
            }
        }
    }
    
    private static void lIIlIllIIlIllIlI() {
        (llIIllIIIIIIlIl = new String[75])[0] = lIIlIllIIlIIIlIl(RandomMobs.llIIllIIIIIlllI[0], RandomMobs.llIIllIIIIIlllI[1]);
        RandomMobs.llIIllIIIIIIlIl[1] = lIIlIllIIlIIlIII(RandomMobs.llIIllIIIIIlllI[2], RandomMobs.llIIllIIIIIlllI[3]);
        RandomMobs.llIIllIIIIIIlIl[2] = lIIlIllIIlIIlIII(RandomMobs.llIIllIIIIIlllI[4], RandomMobs.llIIllIIIIIlllI[5]);
        RandomMobs.llIIllIIIIIIlIl[3] = lIIlIllIIlIIlIIl(RandomMobs.llIIllIIIIIlllI[6], RandomMobs.llIIllIIIIIlllI[7]);
        RandomMobs.llIIllIIIIIIlIl[4] = lIIlIllIIlIIlIIl(RandomMobs.llIIllIIIIIlllI[8], RandomMobs.llIIllIIIIIlllI[9]);
        RandomMobs.llIIllIIIIIIlIl[5] = lIIlIllIIlIIIlIl(RandomMobs.llIIllIIIIIlllI[10], RandomMobs.llIIllIIIIIlllI[11]);
        RandomMobs.llIIllIIIIIIlIl[6] = lIIlIllIIlIIlIIl(RandomMobs.llIIllIIIIIlllI[12], RandomMobs.llIIllIIIIIlllI[13]);
        RandomMobs.llIIllIIIIIIlIl[7] = lIIlIllIIlIIlIIl(RandomMobs.llIIllIIIIIlllI[14], RandomMobs.llIIllIIIIIlllI[15]);
        RandomMobs.llIIllIIIIIIlIl[8] = lIIlIllIIlIIIlIl(RandomMobs.llIIllIIIIIlllI[16], RandomMobs.llIIllIIIIIlllI[17]);
        RandomMobs.llIIllIIIIIIlIl[9] = lIIlIllIIlIIlIII(RandomMobs.llIIllIIIIIlllI[18], RandomMobs.llIIllIIIIIlllI[19]);
        RandomMobs.llIIllIIIIIIlIl[10] = lIIlIllIIlIIlIII(RandomMobs.llIIllIIIIIlllI[20], RandomMobs.llIIllIIIIIlllI[21]);
        RandomMobs.llIIllIIIIIIlIl[11] = lIIlIllIIlIIlIIl(RandomMobs.llIIllIIIIIlllI[22], RandomMobs.llIIllIIIIIlllI[23]);
        RandomMobs.llIIllIIIIIIlIl[12] = lIIlIllIIlIIlIIl(RandomMobs.llIIllIIIIIlllI[24], RandomMobs.llIIllIIIIIlllI[25]);
        RandomMobs.llIIllIIIIIIlIl[13] = lIIlIllIIlIIlIII(RandomMobs.llIIllIIIIIlllI[26], RandomMobs.llIIllIIIIIlllI[27]);
        RandomMobs.llIIllIIIIIIlIl[14] = lIIlIllIIlIIIlIl(RandomMobs.llIIllIIIIIlllI[28], RandomMobs.llIIllIIIIIlllI[29]);
        RandomMobs.llIIllIIIIIIlIl[15] = lIIlIllIIlIIlIII(RandomMobs.llIIllIIIIIlllI[30], RandomMobs.llIIllIIIIIlllI[31]);
        RandomMobs.llIIllIIIIIIlIl[16] = lIIlIllIIlIIllIl(RandomMobs.llIIllIIIIIlllI[32], RandomMobs.llIIllIIIIIlllI[33]);
        RandomMobs.llIIllIIIIIIlIl[17] = lIIlIllIIlIIllIl("r1D1t+BcUEFYjo3O03aZfdU2oomkOyS3dQNWO1GTwx0igi6fku16+w==", "XmtYZ");
        RandomMobs.llIIllIIIIIIlIl[18] = lIIlIllIIlIIIlIl("9OXv+lECxGnmRexr/OeRjFrtVRl7LUy9m91LfmmFXM0=", "aMrzh");
        RandomMobs.llIIllIIIIIIlIl[19] = lIIlIllIIlIIIlIl("psBF57c6zOX3Pk7sRbJp6w==", "TwwGp");
        RandomMobs.llIIllIIIIIIlIl[20] = lIIlIllIIlIIllIl("pGOBLi/b3Fc=", "QEIXt");
        RandomMobs.llIIllIIIIIIlIl[21] = lIIlIllIIlIIIlIl("YpmN60x2vK8x2B1LhGUP6Q==", "GDeIM");
        RandomMobs.llIIllIIIIIIlIl[22] = lIIlIllIIlIIIlIl("xiABMkLsm+sk+6Rdq8Dfyw==", "GCNqB");
        RandomMobs.llIIllIIIIIIlIl[23] = lIIlIllIIlIIlIIl("Ob9wO2RE4BECCI2ESBLplapGphMfASiR", "CWADt");
        RandomMobs.llIIllIIIIIIlIl[24] = lIIlIllIIlIIIlIl("PXreqCe1GCHwuMQ7gkm2kA==", "jkyck");
        RandomMobs.llIIllIIIIIIlIl[25] = lIIlIllIIlIIlIIl("xobu+9cjw5SkF3bcWmPOYPxEw9Sl7Gwy", "GCWsr");
        RandomMobs.llIIllIIIIIIlIl[26] = lIIlIllIIlIIIlIl("AIN2yLsf9HR93IFXxq3Phg==", "hBzzj");
        RandomMobs.llIIllIIIIIIlIl[27] = lIIlIllIIlIIlIII("ZEEaJQAhAAIwAXJB", "HalDr");
        RandomMobs.llIIllIIIIIIlIl[28] = lIIlIllIIlIIlIIl("mXhe0Qdug8M=", "nxNTy");
        RandomMobs.llIIllIIIIIIlIl[29] = lIIlIllIIlIIlIII("Fy84KAc=", "uCYRb");
        RandomMobs.llIIllIIIIIIlIl[30] = lIIlIllIIlIIlIIl("2pQ/2RrzO7p6kBBathWuTw==", "WsrwY");
        RandomMobs.llIIllIIIIIIlIl[31] = lIIlIllIIlIIllIl("dKeWjIx9z9rSXEYtQ8wGzA==", "kJqYO");
        RandomMobs.llIIllIIIIIIlIl[32] = lIIlIllIIlIIIlIl("UvrQ181kLZJ5lhl9puU/lg==", "NJxBI");
        RandomMobs.llIIllIIIIIIlIl[33] = lIIlIllIIlIIlIIl("uij0bcu0s1K4CGdiSfHCJA==", "oZvqD");
        RandomMobs.llIIllIIIIIIlIl[34] = lIIlIllIIlIIlIIl("ORk6QWu5crM=", "scaRU");
        RandomMobs.llIIllIIIIIIlIl[35] = lIIlIllIIlIIlIII("Gx4tVxsXBg==", "xqZxx");
        RandomMobs.llIIllIIIIIIlIl[36] = lIIlIllIIlIIIlIl("l8B0QHFAjaAFgrP7JbNcHA==", "Mhony");
        RandomMobs.llIIllIIIIIIlIl[37] = lIIlIllIIlIIllIl("njd2jm6GSW8+k/LzyuI7Rg==", "RNYjQ");
        RandomMobs.llIIllIIIIIIlIl[38] = lIIlIllIIlIIllIl("0p28su5ErBNnF3oUeYmMbVRtZHMqB0/A", "YkSrm");
        RandomMobs.llIIllIIIIIIlIl[39] = lIIlIllIIlIIlIIl("7K1evrQCEXJOXCvPq9HPEOsPTUj57oIW", "BbZlJ");
        RandomMobs.llIIllIIIIIIlIl[40] = lIIlIllIIlIIlIIl("0AN2/ekON1//5sXmfW4BqA==", "pgwWl");
        RandomMobs.llIIllIIIIIIlIl[41] = lIIlIllIIlIIlIII("LT0XEQ1lMh4DCj4KBQoWJSEfDB4=", "JUvby");
        RandomMobs.llIIllIIIIIIlIl[42] = lIIlIllIIlIIllIl("HpoU7Lwv3JSYtqksA6we9w==", "DhLaF");
        RandomMobs.llIIllIIIIIIlIl[43] = lIIlIllIIlIIllIl("6Yi2yljqL2A=", "ZvDXP");
        RandomMobs.llIIllIIIIIIlIl[44] = lIIlIllIIlIIIlIl("XMHgb3aU0jOznkr9u9Z1Dg==", "gGqDe");
        RandomMobs.llIIllIIIIIIlIl[45] = lIIlIllIIlIIllIl("vpc9ENHcLvdF7b4oB1zRNA==", "SlDsq");
        RandomMobs.llIIllIIIIIIlIl[46] = lIIlIllIIlIIlIII("HyE5DyAeLjwKLQ==", "lHUyE");
        RandomMobs.llIIllIIIIIIlIl[47] = lIIlIllIIlIIlIIl("FU+vHYAzj048fLZHoQ4HCfSmIPjr+QV3", "pojAw");
        RandomMobs.llIIllIIIIIIlIl[48] = lIIlIllIIlIIlIII("EjkSBRIVPRlGAAgmHwwFPiEcDBsEJhgH", "aRwiw");
        RandomMobs.llIIllIIIIIIlIl[49] = lIIlIllIIlIIlIII("HTY6ISxBKT8lJAs=", "nZSLI");
        RandomMobs.llIIllIIIIIIlIl[50] = lIIlIllIIlIIIlIl("1SEvzPxeTfS5M00Ub68SBA==", "TkvnN");
        RandomMobs.llIIllIIIIIIlIl[51] = lIIlIllIIlIIIlIl("Agj1ZIJ3qq9dASURcfh4pw==", "EIGdJ");
        RandomMobs.llIIllIIIIIIlIl[52] = lIIlIllIIlIIIlIl("0aPiCajXCt25mAUyig6ZqRNe5B6WMrYKsO8ZPUf/WLE=", "kBNWV");
        RandomMobs.llIIllIIIIIIlIl[53] = lIIlIllIIlIIllIl("8jpHbml4PRpBdCYOB8axYA==", "hvIWA");
        RandomMobs.llIIllIIIIIIlIl[54] = lIIlIllIIlIIllIl("dgg7Kvs24a6vxXhjeJxZiw==", "ruyMr");
        RandomMobs.llIIllIIIIIIlIl[55] = lIIlIllIIlIIlIIl("09proBWXjfA=", "RqesP");
        RandomMobs.llIIllIIIIIIlIl[56] = lIIlIllIIlIIlIII("AzMYIgoSPwZhHRw2GC8MECg=", "uZtNk");
        RandomMobs.llIIllIIIIIIlIl[57] = lIIlIllIIlIIllIl("FLffhse8yGHzS8DkZ4o3yx7bJC6UoE2J", "rRWJJ");
        RandomMobs.llIIllIIIIIIlIl[58] = lIIlIllIIlIIIlIl("EJXepmnVjUHV76ezoBieXg==", "zbhtI");
        RandomMobs.llIIllIIIIIIlIl[59] = lIIlIllIIlIIIlIl("mQxQ/7rSY4OpPBsLfrum9m1aUdZzpoqj3eqx7v+GAaw=", "HPEXB");
        RandomMobs.llIIllIIIIIIlIl[60] = lIIlIllIIlIIlIII("NDsmKyklNzhoODA7LzQ8", "BRJGH");
        RandomMobs.llIIllIIIIIIlIl[61] = lIIlIllIIlIIllIl("guI8ep15YZlY5RF7yQX0jA==", "gtDyd");
        RandomMobs.llIIllIIIIIIlIl[62] = lIIlIllIIlIIlIIl("M+cc7GFY2Xj8OHF4cDkJcQ==", "UgcIT");
        RandomMobs.llIIllIIIIIIlIl[63] = lIIlIllIIlIIlIII("FgcePxITQR0+AwkLGAgWEwMFJQ==", "anjWw");
        RandomMobs.llIIllIIIIIIlIl[64] = lIIlIllIIlIIIlIl("yiQoIDa37ID50UiFbn6s06rn5NLtpDbpZ3//UyKn4Ew=", "dtCgS");
        RandomMobs.llIIllIIIIIIlIl[65] = lIIlIllIIlIIIlIl("JaTKU4z6M7ZwezZwUNHMHQ==", "DzPgt");
        RandomMobs.llIIllIIIIIIlIl[66] = lIIlIllIIlIIIlIl("wbyiqIXfETAsPEaV8EaNXg==", "AuPgb");
        RandomMobs.llIIllIIIIIIlIl[67] = lIIlIllIIlIIIlIl("IukzbUVXKMj/EsBLcFqe7PesP+RPS7cOXdN8vpElGLk=", "rfjQF");
        RandomMobs.llIIllIIIIIIlIl[68] = lIIlIllIIlIIlIIl("LFmvjya36R9bbmyh1GIOng==", "BvWHQ");
        RandomMobs.llIIllIIIIIIlIl[69] = lIIlIllIIlIIlIII("EQE3BwUOMSoMCwYPNA==", "knZel");
        RandomMobs.llIIllIIIIIIlIl[70] = lIIlIllIIlIIIlIl("inMQzhZr6dgMxfypQAaInQ==", "qwqZb");
        RandomMobs.llIIllIIIIIIlIl[71] = lIIlIllIIlIIlIII("ESIGAwUOYhEOAQkkDj4aAiEHAAsOPw==", "kMkal");
        RandomMobs.llIIllIIIIIIlIl[72] = lIIlIllIIlIIIlIl("RsdAf05Ovmw2vRfaikDJY0nIWN3ZyK4ce8j9usGkhGk=", "Hqcjz");
        RandomMobs.llIIllIIIIIIlIl[73] = lIIlIllIIlIIlIIl("JQ63HeI77S0=", "SfkQz");
        RandomMobs.llIIllIIIIIIlIl[74] = lIIlIllIIlIIlIII("GzcGchU6LRw2SXU=", "UXrRs");
        RandomMobs.llIIllIIIIIlllI = null;
    }
    
    private static void lIIlIllIIlIllIll() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        RandomMobs.llIIllIIIIIlllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String lIIlIllIIlIIlIII(String s, final String s2) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int n = 0;
        final char[] charArray2 = s.toCharArray();
        for (int length = charArray2.length, i = 0; i < length; ++i) {
            sb.append((char)(charArray2[i] ^ charArray[n % charArray.length]));
            ++n;
        }
        return sb.toString();
    }
    
    private static String lIIlIllIIlIIlIIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            final Cipher instance = Cipher.getInstance("DES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIlIllIIlIIIlIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static String lIIlIllIIlIIllIl(final String s, final String s2) {
        try {
            final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(2, secretKeySpec);
            return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
