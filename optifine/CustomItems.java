package optifine;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.model.*;
import shadersmod.client.*;
import net.minecraft.entity.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class CustomItems
{
    private static CustomItemProperties[][] itemProperties;
    private static CustomItemProperties[][] enchantmentProperties;
    private static Map mapPotionIds;
    private static ItemModelGenerator itemModelGenerator;
    private static boolean useGlint;
    public static final int MASK_POTION_SPLASH;
    public static final int MASK_POTION_NAME;
    public static final String KEY_TEXTURE_OVERLAY;
    public static final String KEY_TEXTURE_SPLASH;
    public static final String KEY_TEXTURE_DRINKABLE;
    public static final String DEFAULT_TEXTURE_OVERLAY;
    public static final String DEFAULT_TEXTURE_SPLASH;
    public static final String DEFAULT_TEXTURE_DRINKABLE;
    private static final int[] EMPTY_INT_ARRAY;
    private static final int[][] EMPTY_INT2_ARRAY;
    private static final String[] lIlIIIllIlIlIIlI;
    private static String[] lIlIIIllIlIllllI;
    
    static {
        llllIIIIlIIIIIIl();
        llllIIIIlIIIIIII();
        DEFAULT_TEXTURE_SPLASH = CustomItems.lIlIIIllIlIlIIlI[0];
        MASK_POTION_SPLASH = 16384;
        DEFAULT_TEXTURE_OVERLAY = CustomItems.lIlIIIllIlIlIIlI[1];
        DEFAULT_TEXTURE_DRINKABLE = CustomItems.lIlIIIllIlIlIIlI[2];
        KEY_TEXTURE_OVERLAY = CustomItems.lIlIIIllIlIlIIlI[3];
        KEY_TEXTURE_DRINKABLE = CustomItems.lIlIIIllIlIlIIlI[4];
        MASK_POTION_NAME = 63;
        KEY_TEXTURE_SPLASH = CustomItems.lIlIIIllIlIlIIlI[5];
        CustomItems.itemProperties = null;
        CustomItems.enchantmentProperties = null;
        CustomItems.mapPotionIds = null;
        CustomItems.itemModelGenerator = new ItemModelGenerator();
        CustomItems.useGlint = true;
        EMPTY_INT_ARRAY = new int[0];
        EMPTY_INT2_ARRAY = new int[0][];
    }
    
    public static void updateIcons(final TextureMap textureMap) {
        CustomItems.itemProperties = null;
        CustomItems.enchantmentProperties = null;
        CustomItems.useGlint = true;
        if (Config.isCustomItems()) {
            readCitProperties(CustomItems.lIlIIIllIlIlIIlI[6]);
            final IResourcePack[] resourcePacks = Config.getResourcePacks();
            for (int i = resourcePacks.length - 1; i >= 0; --i) {
                updateIcons(textureMap, resourcePacks[i]);
            }
            updateIcons(textureMap, Config.getDefaultResourcePack());
            if (CustomItems.itemProperties.length <= 0) {
                CustomItems.itemProperties = null;
            }
            if (CustomItems.enchantmentProperties.length <= 0) {
                CustomItems.enchantmentProperties = null;
            }
        }
    }
    
    private static void readCitProperties(final String s) {
        try {
            final InputStream resourceStream = Config.getResourceStream(new ResourceLocation(s));
            if (resourceStream == null) {
                return;
            }
            Config.dbg(CustomItems.lIlIIIllIlIlIIlI[7] + s);
            final Properties properties = new Properties();
            properties.load(resourceStream);
            resourceStream.close();
            CustomItems.useGlint = Config.parseBoolean(properties.getProperty(CustomItems.lIlIIIllIlIlIIlI[8]), true);
        }
        catch (FileNotFoundException ex2) {}
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void updateIcons(final TextureMap textureMap, final IResourcePack resourcePack) {
        String[] collectFiles = ResUtils.collectFiles(resourcePack, CustomItems.lIlIIIllIlIlIIlI[9], CustomItems.lIlIIIllIlIlIIlI[10], null);
        final Map autoImageProperties = makeAutoImageProperties(resourcePack);
        if (autoImageProperties.size() > 0) {
            final Set keySet = autoImageProperties.keySet();
            collectFiles = (String[])Config.addObjectsToArray(collectFiles, keySet.toArray(new String[keySet.size()]));
        }
        Arrays.sort(collectFiles);
        final List propertyList = makePropertyList(CustomItems.itemProperties);
        final List propertyList2 = makePropertyList(CustomItems.enchantmentProperties);
        for (int i = 0; i < collectFiles.length; ++i) {
            final String s = collectFiles[i];
            Config.dbg(CustomItems.lIlIIIllIlIlIIlI[11] + s);
            try {
                CustomItemProperties customItemProperties = null;
                if (autoImageProperties.containsKey(s)) {
                    customItemProperties = autoImageProperties.get(s);
                }
                if (customItemProperties == null) {
                    final InputStream inputStream = resourcePack.getInputStream(new ResourceLocation(s));
                    if (inputStream == null) {
                        Config.warn(CustomItems.lIlIIIllIlIlIIlI[12] + s);
                        continue;
                    }
                    final Properties properties = new Properties();
                    properties.load(inputStream);
                    customItemProperties = new CustomItemProperties(properties, s);
                }
                if (customItemProperties.isValid(s)) {
                    customItemProperties.updateIcons(textureMap);
                    addToItemList(customItemProperties, propertyList);
                    addToEnchantmentList(customItemProperties, propertyList2);
                }
            }
            catch (FileNotFoundException ex2) {
                Config.warn(CustomItems.lIlIIIllIlIlIIlI[13] + s);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        CustomItems.itemProperties = propertyListToArray(propertyList);
        CustomItems.enchantmentProperties = propertyListToArray(propertyList2);
        final Comparator propertiesComparator = getPropertiesComparator();
        for (int j = 0; j < CustomItems.itemProperties.length; ++j) {
            final CustomItemProperties[] array = CustomItems.itemProperties[j];
            if (array != null) {
                Arrays.sort(array, propertiesComparator);
            }
        }
        for (int k = 0; k < CustomItems.enchantmentProperties.length; ++k) {
            final CustomItemProperties[] array2 = CustomItems.enchantmentProperties[k];
            if (array2 != null) {
                Arrays.sort(array2, propertiesComparator);
            }
        }
    }
    
    private static Comparator getPropertiesComparator() {
        return new Comparator() {
            @Override
            public int compare(final Object o, final Object o2) {
                final CustomItemProperties customItemProperties = (CustomItemProperties)o;
                final CustomItemProperties customItemProperties2 = (CustomItemProperties)o2;
                return (customItemProperties.layer != customItemProperties2.layer) ? (customItemProperties.layer - customItemProperties2.layer) : ((customItemProperties.weight != customItemProperties2.weight) ? (customItemProperties2.weight - customItemProperties.weight) : (customItemProperties.basePath.equals(customItemProperties2.basePath) ? customItemProperties.name.compareTo(customItemProperties2.name) : customItemProperties.basePath.compareTo(customItemProperties2.basePath)));
            }
        };
    }
    
    public static void updateModels() {
        if (CustomItems.itemProperties != null) {
            for (int i = 0; i < CustomItems.itemProperties.length; ++i) {
                final CustomItemProperties[] array = CustomItems.itemProperties[i];
                if (array != null) {
                    for (int j = 0; j < array.length; ++j) {
                        final CustomItemProperties customItemProperties = array[j];
                        if (customItemProperties != null && customItemProperties.type == 1) {
                            customItemProperties.updateModel(Minecraft.getMinecraft().getTextureMapBlocks(), CustomItems.itemModelGenerator);
                        }
                    }
                }
            }
        }
    }
    
    private static Map makeAutoImageProperties(final IResourcePack resourcePack) {
        final HashMap hashMap = new HashMap();
        hashMap.putAll(makePotionImageProperties(resourcePack, false));
        hashMap.putAll(makePotionImageProperties(resourcePack, true));
        return hashMap;
    }
    
    private static Map makePotionImageProperties(final IResourcePack resourcePack, final boolean b) {
        final HashMap<String, CustomItemProperties> hashMap = new HashMap<String, CustomItemProperties>();
        final String s = b ? CustomItems.lIlIIIllIlIlIIlI[14] : CustomItems.lIlIIIllIlIlIIlI[15];
        final String[] array = { CustomItems.lIlIIIllIlIlIIlI[16] + s, CustomItems.lIlIIIllIlIlIIlI[17] + s };
        final String[] array2 = { CustomItems.lIlIIIllIlIlIIlI[18] };
        final String[] collectFiles = ResUtils.collectFiles(resourcePack, array, array2);
        for (int i = 0; i < collectFiles.length; ++i) {
            final String s2 = collectFiles[i];
            final Properties potionProperties = makePotionProperties(StrUtils.removePrefixSuffix(s2, array, array2), b, s2);
            if (potionProperties != null) {
                final String string = String.valueOf(StrUtils.removeSuffix(s2, array2)) + CustomItems.lIlIIIllIlIlIIlI[19];
                hashMap.put(string, new CustomItemProperties(potionProperties, string));
            }
        }
        return hashMap;
    }
    
    private static Properties makePotionProperties(final String s, final boolean b, final String s2) {
        if (StrUtils.endsWith(s, new String[] { CustomItems.lIlIIIllIlIlIIlI[20], CustomItems.lIlIIIllIlIlIIlI[21] })) {
            return null;
        }
        if (s.equals(CustomItems.lIlIIIllIlIlIIlI[22]) && !b) {
            final int idFromItem = Item.getIdFromItem(Items.glass_bottle);
            final Properties properties = new Properties();
            ((Hashtable<String, String>)properties).put(CustomItems.lIlIIIllIlIlIIlI[23], CustomItems.lIlIIIllIlIlIIlI[24]);
            ((Hashtable<String, String>)properties).put(CustomItems.lIlIIIllIlIlIIlI[25], new StringBuilder().append(idFromItem).toString());
            return properties;
        }
        final int idFromItem2 = Item.getIdFromItem(Items.potionitem);
        final int[] array = getMapPotionIds().get(s);
        if (array == null) {
            Config.warn(CustomItems.lIlIIIllIlIlIIlI[26] + s2);
            return null;
        }
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            int n = array[i];
            if (b) {
                n |= 0x4000;
            }
            if (i > 0) {
                sb.append(CustomItems.lIlIIIllIlIlIIlI[27]);
            }
            sb.append(n);
        }
        final int n2 = 16447;
        final Properties properties2 = new Properties();
        ((Hashtable<String, String>)properties2).put(CustomItems.lIlIIIllIlIlIIlI[28], CustomItems.lIlIIIllIlIlIIlI[29]);
        ((Hashtable<String, String>)properties2).put(CustomItems.lIlIIIllIlIlIIlI[30], new StringBuilder().append(idFromItem2).toString());
        ((Hashtable<String, String>)properties2).put(CustomItems.lIlIIIllIlIlIIlI[31], new StringBuilder().append(sb.toString()).toString());
        ((Hashtable<String, String>)properties2).put(CustomItems.lIlIIIllIlIlIIlI[32], new StringBuilder().append(n2).toString());
        if (b) {
            ((Hashtable<String, String>)properties2).put(CustomItems.lIlIIIllIlIlIIlI[33], s);
        }
        else {
            ((Hashtable<String, String>)properties2).put(CustomItems.lIlIIIllIlIlIIlI[34], s);
        }
        return properties2;
    }
    
    private static Map getMapPotionIds() {
        if (CustomItems.mapPotionIds == null) {
            (CustomItems.mapPotionIds = new LinkedHashMap()).put(CustomItems.lIlIIIllIlIlIIlI[35], new int[1]);
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[36], new int[] { 16 });
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[37], new int[] { 32 });
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[38], new int[] { 48 });
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[39], getPotionIds(1));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[40], getPotionIds(2));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[41], getPotionIds(3));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[42], getPotionIds(4));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[43], getPotionIds(5));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[44], getPotionIds(6));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[45], getPotionIds(7));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[46], getPotionIds(23));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[47], getPotionIds(39));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[48], getPotionIds(55));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[49], getPotionIds(8));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[50], getPotionIds(9));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[51], getPotionIds(10));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[52], getPotionIds(11));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[53], getPotionIds(27));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[54], getPotionIds(43));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[55], getPotionIds(59));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[56], getPotionIds(12));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[57], getPotionIds(13));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[58], getPotionIds(14));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[59], getPotionIds(15));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[60], getPotionIds(31));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[61], getPotionIds(47));
            CustomItems.mapPotionIds.put(CustomItems.lIlIIIllIlIlIIlI[62], getPotionIds(63));
        }
        return CustomItems.mapPotionIds;
    }
    
    private static int[] getPotionIds(final int n) {
        return new int[] { n, n + 16, n + 32, n + 48 };
    }
    
    private static int getPotionNameDamage(final String s) {
        final String string = CustomItems.lIlIIIllIlIlIIlI[63] + s;
        final Potion[] potionTypes = Potion.potionTypes;
        for (int i = 0; i < potionTypes.length; ++i) {
            final Potion potion = potionTypes[i];
            if (potion != null && string.equals(potion.getName())) {
                return potion.getId();
            }
        }
        return -1;
    }
    
    private static List makePropertyList(final CustomItemProperties[][] array) {
        final ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                final CustomItemProperties[] array2 = array[i];
                ArrayList list2 = null;
                if (array2 != null) {
                    list2 = new ArrayList(Arrays.asList(array2));
                }
                list.add(list2);
            }
        }
        return list;
    }
    
    private static CustomItemProperties[][] propertyListToArray(final List list) {
        final CustomItemProperties[][] array = new CustomItemProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List list2 = list.get(i);
            if (list2 != null) {
                final CustomItemProperties[] array2 = (CustomItemProperties[])list2.toArray(new CustomItemProperties[list2.size()]);
                Arrays.sort(array2, new CustomItemsComparator());
                array[i] = array2;
            }
        }
        return array;
    }
    
    private static void addToItemList(final CustomItemProperties customItemProperties, final List list) {
        if (customItemProperties.items != null) {
            for (int i = 0; i < customItemProperties.items.length; ++i) {
                final int n = customItemProperties.items[i];
                if (n <= 0) {
                    Config.warn(CustomItems.lIlIIIllIlIlIIlI[64] + n);
                }
                else {
                    addToList(customItemProperties, list, n);
                }
            }
        }
    }
    
    private static void addToEnchantmentList(final CustomItemProperties customItemProperties, final List list) {
        if (customItemProperties.type == 2 && customItemProperties.enchantmentIds != null) {
            for (int i = 0; i < 256; ++i) {
                if (customItemProperties.enchantmentIds.isInRange(i)) {
                    addToList(customItemProperties, list, i);
                }
            }
        }
    }
    
    private static void addToList(final CustomItemProperties customItemProperties, final List list, final int i) {
        while (i >= list.size()) {
            list.add(null);
        }
        List<CustomItemProperties> list2 = list.get(i);
        if (list2 == null) {
            list2 = new ArrayList<CustomItemProperties>();
            list.set(i, list2);
        }
        list2.add(customItemProperties);
    }
    
    public static IBakedModel getCustomItemModel(final ItemStack itemStack, final IBakedModel bakedModel, final ModelResourceLocation modelResourceLocation) {
        if (bakedModel.isAmbientOcclusionEnabled()) {
            return bakedModel;
        }
        if (CustomItems.itemProperties == null) {
            return bakedModel;
        }
        final CustomItemProperties customItemProperties = getCustomItemProperties(itemStack, 1);
        return (customItemProperties == null) ? bakedModel : customItemProperties.getModel(modelResourceLocation);
    }
    
    public static boolean bindCustomArmorTexture(final ItemStack itemStack, final int n, final String s) {
        if (CustomItems.itemProperties == null) {
            return false;
        }
        final ResourceLocation customArmorLocation = getCustomArmorLocation(itemStack, n, s);
        if (customArmorLocation == null) {
            return false;
        }
        Config.getTextureManager().bindTexture(customArmorLocation);
        return true;
    }
    
    private static ResourceLocation getCustomArmorLocation(final ItemStack itemStack, final int n, final String s) {
        final CustomItemProperties customItemProperties = getCustomItemProperties(itemStack, 3);
        if (customItemProperties == null) {
            return null;
        }
        if (customItemProperties.mapTextureLocations == null) {
            return null;
        }
        final Item item = itemStack.getItem();
        if (!(item instanceof ItemArmor)) {
            return null;
        }
        final String func_179242_c = ((ItemArmor)item).getArmorMaterial().func_179242_c();
        final StringBuffer sb = new StringBuffer();
        sb.append(CustomItems.lIlIIIllIlIlIIlI[65]);
        sb.append(func_179242_c);
        sb.append(CustomItems.lIlIIIllIlIlIIlI[66]);
        sb.append(n);
        if (s != null) {
            sb.append(CustomItems.lIlIIIllIlIlIIlI[67]);
            sb.append(s);
        }
        return (ResourceLocation)customItemProperties.mapTextureLocations.get(sb.toString());
    }
    
    private static CustomItemProperties getCustomItemProperties(final ItemStack itemStack, final int n) {
        if (CustomItems.itemProperties == null) {
            return null;
        }
        if (itemStack == null) {
            return null;
        }
        final int idFromItem = Item.getIdFromItem(itemStack.getItem());
        if (idFromItem >= 0 && idFromItem < CustomItems.itemProperties.length) {
            final CustomItemProperties[] array = CustomItems.itemProperties[idFromItem];
            if (array != null) {
                for (int i = 0; i < array.length; ++i) {
                    final CustomItemProperties customItemProperties = array[i];
                    if (customItemProperties.type == n && matchesProperties(customItemProperties, itemStack, null)) {
                        return customItemProperties;
                    }
                }
            }
        }
        return null;
    }
    
    private static boolean matchesProperties(final CustomItemProperties customItemProperties, final ItemStack itemStack, final int[][] array) {
        final Item item = itemStack.getItem();
        if (customItemProperties.damage != null) {
            int itemDamage = itemStack.getItemDamage();
            if (customItemProperties.damageMask != 0) {
                itemDamage &= customItemProperties.damageMask;
            }
            if (customItemProperties.damagePercent) {
                itemDamage = (int)(itemDamage * 100 / (double)item.getMaxDamage());
            }
            if (!customItemProperties.damage.isInRange(itemDamage)) {
                return false;
            }
        }
        if (customItemProperties.stackSize != null && !customItemProperties.stackSize.isInRange(itemStack.stackSize)) {
            return false;
        }
        int[][] array2 = array;
        if (customItemProperties.enchantmentIds != null) {
            if (array == null) {
                array2 = getEnchantmentIdLevels(itemStack);
            }
            boolean b = false;
            for (int i = 0; i < array2.length; ++i) {
                if (customItemProperties.enchantmentIds.isInRange(array2[i][0])) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                return false;
            }
        }
        if (customItemProperties.enchantmentLevels != null) {
            if (array2 == null) {
                array2 = getEnchantmentIdLevels(itemStack);
            }
            boolean b2 = false;
            for (int j = 0; j < array2.length; ++j) {
                if (customItemProperties.enchantmentLevels.isInRange(array2[j][1])) {
                    b2 = true;
                    break;
                }
            }
            if (!b2) {
                return false;
            }
        }
        if (customItemProperties.nbtTagValues != null) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            for (int k = 0; k < customItemProperties.nbtTagValues.length; ++k) {
                if (!customItemProperties.nbtTagValues[k].matches(tagCompound)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static int[][] getEnchantmentIdLevels(final ItemStack itemStack) {
        final NBTTagList list = (itemStack.getItem() == Items.enchanted_book) ? Items.enchanted_book.func_92110_g(itemStack) : itemStack.getEnchantmentTagList();
        if (list != null && list.tagCount() > 0) {
            final int[][] array = new int[list.tagCount()][2];
            for (int i = 0; i < list.tagCount(); ++i) {
                final NBTTagCompound compoundTag = list.getCompoundTagAt(i);
                final short short1 = compoundTag.getShort(CustomItems.lIlIIIllIlIlIIlI[68]);
                final short short2 = compoundTag.getShort(CustomItems.lIlIIIllIlIlIIlI[69]);
                array[i][0] = short1;
                array[i][1] = short2;
            }
            return array;
        }
        return CustomItems.EMPTY_INT2_ARRAY;
    }
    
    public static boolean renderCustomEffect(final RenderItem renderItem, final ItemStack itemStack, final IBakedModel bakedModel) {
        if (CustomItems.enchantmentProperties == null) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        final int[][] enchantmentIdLevels = getEnchantmentIdLevels(itemStack);
        if (enchantmentIdLevels.length <= 0) {
            return false;
        }
        HashSet<Integer> set = null;
        boolean b = false;
        final TextureManager textureManager = Config.getTextureManager();
        for (int i = 0; i < enchantmentIdLevels.length; ++i) {
            final int n = enchantmentIdLevels[i][0];
            if (n >= 0 && n < CustomItems.enchantmentProperties.length) {
                final CustomItemProperties[] array = CustomItems.enchantmentProperties[n];
                if (array != null) {
                    for (int j = 0; j < array.length; ++j) {
                        final CustomItemProperties customItemProperties = array[j];
                        if (set == null) {
                            set = new HashSet<Integer>();
                        }
                        if (set.add(n) && matchesProperties(customItemProperties, itemStack, enchantmentIdLevels) && customItemProperties.textureLocation != null) {
                            textureManager.bindTexture(customItemProperties.textureLocation);
                            final float textureWidth = customItemProperties.getTextureWidth(textureManager);
                            if (!b) {
                                b = true;
                                GlStateManager.depthMask(false);
                                GlStateManager.depthFunc(514);
                                GlStateManager.disableLighting();
                                GlStateManager.matrixMode(5890);
                            }
                            Blender.setupBlend(customItemProperties.blend, 1.0f);
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(textureWidth / 2.0f, textureWidth / 2.0f, textureWidth / 2.0f);
                            GlStateManager.translate(customItemProperties.speed * (Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f, 0.0f, 0.0f);
                            GlStateManager.rotate(customItemProperties.rotation, 0.0f, 0.0f, 1.0f);
                            renderItem.func_175035_a(bakedModel, -1);
                            GlStateManager.popMatrix();
                        }
                    }
                }
            }
        }
        if (b) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            textureManager.bindTexture(TextureMap.locationBlocksTexture);
        }
        return b;
    }
    
    public static boolean renderCustomArmorEffect(final EntityLivingBase entityLivingBase, final ItemStack itemStack, final ModelBase modelBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (CustomItems.enchantmentProperties == null) {
            return false;
        }
        if (Config.isShaders() && Shaders.isShadowPass) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        final int[][] enchantmentIdLevels = getEnchantmentIdLevels(itemStack);
        if (enchantmentIdLevels.length <= 0) {
            return false;
        }
        HashSet<Integer> set = null;
        boolean b = false;
        final TextureManager textureManager = Config.getTextureManager();
        for (int i = 0; i < enchantmentIdLevels.length; ++i) {
            final int n8 = enchantmentIdLevels[i][0];
            if (n8 >= 0 && n8 < CustomItems.enchantmentProperties.length) {
                final CustomItemProperties[] array = CustomItems.enchantmentProperties[n8];
                if (array != null) {
                    for (int j = 0; j < array.length; ++j) {
                        final CustomItemProperties customItemProperties = array[j];
                        if (set == null) {
                            set = new HashSet<Integer>();
                        }
                        if (set.add(n8) && matchesProperties(customItemProperties, itemStack, enchantmentIdLevels) && customItemProperties.textureLocation != null) {
                            textureManager.bindTexture(customItemProperties.textureLocation);
                            final float textureWidth = customItemProperties.getTextureWidth(textureManager);
                            if (!b) {
                                b = true;
                                if (Config.isShaders()) {
                                    ShadersRender.layerArmorBaseDrawEnchantedGlintBegin();
                                }
                                GlStateManager.enableBlend();
                                GlStateManager.depthFunc(514);
                                GlStateManager.depthMask(false);
                            }
                            Blender.setupBlend(customItemProperties.blend, 1.0f);
                            GlStateManager.disableLighting();
                            GlStateManager.matrixMode(5890);
                            GlStateManager.loadIdentity();
                            GlStateManager.rotate(customItemProperties.rotation, 0.0f, 0.0f, 1.0f);
                            final float n9 = textureWidth / 8.0f;
                            GlStateManager.scale(n9, n9 / 2.0f, n9);
                            GlStateManager.translate(0.0f, customItemProperties.speed * (Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f, 0.0f);
                            GlStateManager.matrixMode(5888);
                            modelBase.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                        }
                    }
                }
            }
        }
        if (b) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            if (Config.isShaders()) {
                ShadersRender.layerArmorBaseDrawEnchantedGlintEnd();
            }
        }
        return b;
    }
    
    public static boolean isUseGlint() {
        return CustomItems.useGlint;
    }
    
    private static void llllIIIIlIIIIIII() {
        (lIlIIIllIlIlIIlI = new String[70])[0] = llllIIIIIllIllll(CustomItems.lIlIIIllIlIllllI[0], CustomItems.lIlIIIllIlIllllI[1]);
        CustomItems.lIlIIIllIlIlIIlI[1] = llllIIIIIlllIIII(CustomItems.lIlIIIllIlIllllI[2], CustomItems.lIlIIIllIlIllllI[3]);
        CustomItems.lIlIIIllIlIlIIlI[2] = llllIIIIIllIllll(CustomItems.lIlIIIllIlIllllI[4], CustomItems.lIlIIIllIlIllllI[5]);
        CustomItems.lIlIIIllIlIlIIlI[3] = llllIIIIIllIllll(CustomItems.lIlIIIllIlIllllI[6], CustomItems.lIlIIIllIlIllllI[7]);
        CustomItems.lIlIIIllIlIlIIlI[4] = llllIIIIIlllIIIl(CustomItems.lIlIIIllIlIllllI[8], CustomItems.lIlIIIllIlIllllI[9]);
        CustomItems.lIlIIIllIlIlIIlI[5] = llllIIIIIlllIIIl(CustomItems.lIlIIIllIlIllllI[10], CustomItems.lIlIIIllIlIllllI[11]);
        CustomItems.lIlIIIllIlIlIIlI[6] = llllIIIIIlllIIII(CustomItems.lIlIIIllIlIllllI[12], CustomItems.lIlIIIllIlIllllI[13]);
        CustomItems.lIlIIIllIlIlIIlI[7] = llllIIIIIlllIlIl(CustomItems.lIlIIIllIlIllllI[14], CustomItems.lIlIIIllIlIllllI[15]);
        CustomItems.lIlIIIllIlIlIIlI[8] = llllIIIIIllIllll(CustomItems.lIlIIIllIlIllllI[16], CustomItems.lIlIIIllIlIllllI[17]);
        CustomItems.lIlIIIllIlIlIIlI[9] = llllIIIIIlllIlIl(CustomItems.lIlIIIllIlIllllI[18], CustomItems.lIlIIIllIlIllllI[19]);
        CustomItems.lIlIIIllIlIlIIlI[10] = llllIIIIIlllIIIl(CustomItems.lIlIIIllIlIllllI[20], CustomItems.lIlIIIllIlIllllI[21]);
        CustomItems.lIlIIIllIlIlIIlI[11] = llllIIIIIlllIIII(CustomItems.lIlIIIllIlIllllI[22], CustomItems.lIlIIIllIlIllllI[23]);
        CustomItems.lIlIIIllIlIlIIlI[12] = llllIIIIIllIllll("AA/rXLd1EqGQlEayYPMTBVlDKXAZo3NK+r5jvgX0BxY=", "fRFqd");
        CustomItems.lIlIIIllIlIlIIlI[13] = llllIIIIIllIllll("/pfGGS1aqszzGs1BNR3cHQ9N8TH3WmbrHGsChfcKntk=", "TCpik");
        CustomItems.lIlIIIllIlIlIIlI[14] = llllIIIIIlllIlIl("+MhoEoVLoAg=", "TTqio");
        CustomItems.lIlIIIllIlIlIIlI[15] = llllIIIIIlllIlIl("lAoX5vtYoh8=", "rpjNU");
        CustomItems.lIlIIIllIlIlIIlI[16] = llllIIIIIlllIIII("IS8TFyYvJAYEfS8lF1kiIzgKGTxj", "LLcvR");
        CustomItems.lIlIIIllIlIlIIlI[17] = llllIIIIIlllIlIl("Ldi7whsbF2yAYBHA48BUonf3ZPMzlMRv", "eSqTT");
        CustomItems.lIlIIIllIlIlIIlI[18] = llllIIIIIlllIIII("TSolBg==", "cZKaq");
        CustomItems.lIlIIIllIlIlIIlI[19] = llllIIIIIlllIIIl("YP0mySNQK1cCZz+DYv+jkw==", "KCrJH");
        CustomItems.lIlIIIllIlIlIIlI[20] = llllIIIIIlllIlIl("/8+LsGWog1c=", "kKxva");
        CustomItems.lIlIIIllIlIlIIlI[21] = llllIIIIIllIllll("KXfsd62EzfJ9A5wy0S653A==", "hvNoI");
        CustomItems.lIlIIIllIlIlIIlI[22] = llllIIIIIlllIIIl("AWSCH3GjoCw=", "SeRil");
        CustomItems.lIlIIIllIlIlIIlI[23] = llllIIIIIlllIIIl("TAkR4t5JosI=", "tjITG");
        CustomItems.lIlIIIllIlIlIIlI[24] = llllIIIIIlllIIIl("bMNiOFBChN0=", "XLUZR");
        CustomItems.lIlIIIllIlIlIIlI[25] = llllIIIIIlllIIII("DBIxNyE=", "efTZR");
        CustomItems.lIlIIIllIlIlIIlI[26] = llllIIIIIlllIlIl("5qTYwp71qprOe+PC4Rj54H1f2mR+tLg7M8sTxIjKepU=", "YNkGq");
        CustomItems.lIlIIIllIlIlIIlI[27] = llllIIIIIlllIlIl("1jE+rHDOzs4=", "mSFQR");
        CustomItems.lIlIIIllIlIlIIlI[28] = llllIIIIIllIllll("IoMQ3lBzS80NEHyTP0OIIg==", "jHDMC");
        CustomItems.lIlIIIllIlIlIIlI[29] = llllIIIIIllIllll("+8JTBrEVk+r0c21Xeh2iNg==", "qDFen");
        CustomItems.lIlIIIllIlIlIIlI[30] = llllIIIIIlllIlIl("+fbHZGX/Emk=", "RvWJK");
        CustomItems.lIlIIIllIlIlIIlI[31] = llllIIIIIlllIIIl("1lRUHL+RQDY=", "dJXTJ");
        CustomItems.lIlIIIllIlIlIIlI[32] = llllIIIIIlllIIII("ETAvFwwQHCMFAA==", "uQBvk");
        CustomItems.lIlIIIllIlIlIIlI[33] = llllIIIIIlllIIIl("d0Mww8dG75JZFzojZIC3smATWYfFsM9ipldAVnlsLFI=", "ZgsKJ");
        CustomItems.lIlIIIllIlIlIIlI[34] = llllIIIIIlllIIII("ExEZMzAVEU83KhMdDikaBRsVMykCKwU1LAkfACUpAg==", "gtaGE");
        CustomItems.lIlIIIllIlIlIIlI[35] = llllIIIIIllIllll("OOSR4M7eLyvoCBQLLkh2Iw==", "zCSlU");
        CustomItems.lIlIIIllIlIlIIlI[36] = llllIIIIIlllIIIl("rzu/N8iNw1I=", "JGvpr");
        CustomItems.lIlIIIllIlIlIIlI[37] = llllIIIIIlllIIIl("U33U0beD8fk=", "DXrug");
        CustomItems.lIlIIIllIlIlIIlI[38] = llllIIIIIlllIIII("HAQ8BDYY", "lkHaX");
        CustomItems.lIlIIIllIlIlIIlI[39] = llllIIIIIlllIIIl("aQEwEO17bCvGimLoPFK2gQ==", "UkjTJ");
        CustomItems.lIlIIIllIlIlIIlI[40] = llllIIIIIllIllll("8cWByWuEdl3RzbRz/zDkdg==", "qXbtk");
        CustomItems.lIlIIIllIlIlIIlI[41] = llllIIIIIlllIlIl("9jWFA37hUYMkNUuWf2hQlQ==", "VLpRo");
        CustomItems.lIlIIIllIlIlIIlI[42] = llllIIIIIllIllll("lj4KkjY/h6CJ5BY3laReqQ==", "ZZHnH");
        CustomItems.lIlIIIllIlIlIIlI[43] = llllIIIIIlllIlIl("aciLy844UYI=", "rFuFH");
        CustomItems.lIlIIIllIlIlIIlI[44] = llllIIIIIllIllll("4Zonu5GTfxNjMjZMKsHqZw==", "BbDer");
        CustomItems.lIlIIIllIlIlIIlI[45] = llllIIIIIllIllll("CeATH9jA96zv+boYffkLkw==", "telcX");
        CustomItems.lIlIIIllIlIlIIlI[46] = llllIIIIIlllIIII("EhggJjkZAyk=", "pmNAU");
        CustomItems.lIlIIIllIlIlIIlI[47] = llllIIIIIlllIIII("ASsyPCULLTQ=", "bCSNH");
        CustomItems.lIlIIIllIlIlIIlI[48] = llllIIIIIlllIIII("FhELMQ==", "dpeZj");
        CustomItems.lIlIIIllIlIlIIlI[49] = llllIIIIIlllIIII("FS4rIxkHODk=", "bKJHw");
        CustomItems.lIlIIIllIlIlIIlI[50] = llllIIIIIlllIlIl("gD0YpVp1xAorQxRkx8RyoQ==", "SVocS");
        CustomItems.lIlIIIllIlIlIIlI[51] = llllIIIIIlllIIII("OzksEQQ6OS0QOCE4", "VVZtW");
        CustomItems.lIlIIIllIlIlIIlI[52] = llllIIIIIlllIIII("HDgsIjMLNA==", "xQJDF");
        CustomItems.lIlIIIllIlIlIIlI[53] = llllIIIIIlllIlIl("soq31OVZju4=", "pHVdq");
        CustomItems.lIlIIIllIlIlIIlI[54] = llllIIIIIlllIIIl("SfGILw/h9i8=", "RyrRw");
        CustomItems.lIlIIIllIlIlIIlI[55] = llllIIIIIlllIlIl("x2xTi+PKpOc=", "LrahI");
        CustomItems.lIlIIIllIlIlIIlI[56] = llllIIIIIlllIIII("HDM+JQ==", "tRLHy");
        CustomItems.lIlIIIllIlIlIIlI[57] = llllIIIIIlllIlIl("Szu/lZlavylwx5V8N+d38A==", "QXDwt");
        CustomItems.lIlIIIllIlIlIIlI[58] = llllIIIIIllIllll("dkT+TANfg6ukQVIJxLgLkQ==", "CmedZ");
        CustomItems.lIlIIIllIlIlIIlI[59] = llllIIIIIlllIlIl("rUApfqKDX0k=", "wVMlU");
        CustomItems.lIlIIIllIlIlIIlI[60] = llllIIIIIlllIIIl("RCFDgvS+Ht+UvZZ1lpJwgA==", "sHFxG");
        CustomItems.lIlIIIllIlIlIIlI[61] = llllIIIIIlllIlIl("uQLzgxFfa5WEK/V2ULdH3g==", "AWIVt");
        CustomItems.lIlIIIllIlIlIIlI[62] = llllIIIIIlllIIIl("JEQ2NDXnitY=", "RRksU");
        CustomItems.lIlIIIllIlIlIIlI[63] = llllIIIIIlllIIIl("u3aiRHrb3k8=", "JRUNM");
        CustomItems.lIlIIIllIlIlIIlI[64] = llllIIIIIllIllll("QZvEgs2hCm+Ru6t3zJnqfvuqfrjUaHDgLsiwqVyM7No=", "zbVXo");
        CustomItems.lIlIIIllIlIlIIlI[65] = llllIIIIIllIllll("vC6vaN4lvsUwZmVh8roX4A==", "relsP");
        CustomItems.lIlIIIllIlIlIIlI[66] = llllIIIIIlllIIIl("gs4tsRBmh+Y=", "eKQNd");
        CustomItems.lIlIIIllIlIlIIlI[67] = llllIIIIIlllIIII("KQ==", "vwcfY");
        CustomItems.lIlIIIllIlIlIIlI[68] = llllIIIIIlllIIII("GiA=", "sDxXL");
        CustomItems.lIlIIIllIlIlIIlI[69] = llllIIIIIlllIIII("NhUB", "ZcmVj");
        CustomItems.lIlIIIllIlIllllI = null;
    }
    
    private static void llllIIIIlIIIIIIl() {
        final String fileName = new Exception().getStackTrace()[0].getFileName();
        CustomItems.lIlIIIllIlIllllI = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
    }
    
    private static String llllIIIIIlllIlIl(final String s, final String s2) {
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
    
    private static String llllIIIIIlllIIIl(final String s, final String s2) {
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
    
    private static String llllIIIIIllIllll(final String s, final String s2) {
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
    
    private static String llllIIIIIlllIIII(String s, final String s2) {
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
}
