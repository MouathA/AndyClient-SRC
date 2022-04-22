package net.minecraft.creativetab;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.enchantment.*;

public abstract class CreativeTabs
{
    public static CreativeTabs[] creativeTabArray;
    public static final CreativeTabs tabBlock;
    public static final CreativeTabs tabDecorations;
    public static final CreativeTabs tabRedstone;
    public static final CreativeTabs tabTransport;
    public static final CreativeTabs tabMisc;
    public static final CreativeTabs tabAllSearch;
    public static final CreativeTabs tabFood;
    public static final CreativeTabs tabTools;
    public static final CreativeTabs tabCombat;
    public static final CreativeTabs tabBrewing;
    public static final CreativeTabs tabMaterials;
    public static final CreativeTabs tabInventory;
    private final int tabIndex;
    private final String tabLabel;
    private String theTexture;
    private boolean hasScrollbar;
    private boolean drawTitle;
    private EnumEnchantmentType[] enchantmentTypes;
    private ItemStack iconItemStack;
    
    static {
        CreativeTabs.creativeTabArray = new CreativeTabs[12];
        tabBlock = new CreativeTabs("buildingBlocks") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.brick_block);
            }
        };
        tabDecorations = new CreativeTabs("decorations") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.double_plant);
            }
            
            @Override
            public int getIconItemDamage() {
                return BlockDoublePlant.EnumPlantType.PAEONIA.func_176936_a();
            }
        };
        tabRedstone = new CreativeTabs("redstone") {
            @Override
            public Item getTabIconItem() {
                return Items.redstone;
            }
        };
        tabTransport = new CreativeTabs("transportation") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.golden_rail);
            }
        };
        tabMisc = new CreativeTabs("misc") {
            @Override
            public Item getTabIconItem() {
                return Items.lava_bucket;
            }
        }.setRelevantEnchantmentTypes(EnumEnchantmentType.ALL);
        tabAllSearch = new CreativeTabs("search") {
            @Override
            public Item getTabIconItem() {
                return Items.compass;
            }
        }.setBackgroundImageName("item_search.png");
        tabFood = new CreativeTabs("food") {
            @Override
            public Item getTabIconItem() {
                return Items.apple;
            }
        };
        tabTools = new CreativeTabs("tools") {
            @Override
            public Item getTabIconItem() {
                return Items.iron_axe;
            }
        }.setRelevantEnchantmentTypes(EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE);
        tabCombat = new CreativeTabs("combat") {
            @Override
            public Item getTabIconItem() {
                return Items.golden_sword;
            }
        }.setRelevantEnchantmentTypes(EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON);
        tabBrewing = new CreativeTabs("brewing") {
            @Override
            public Item getTabIconItem() {
                return Items.potionitem;
            }
        };
        tabMaterials = new CreativeTabs("materials") {
            @Override
            public Item getTabIconItem() {
                return Items.stick;
            }
        };
        tabInventory = new CreativeTabs("inventory") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.chest);
            }
        }.setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
    }
    
    public CreativeTabs(final String s) {
        this(getNextID(), s);
    }
    
    public CreativeTabs(final int tabIndex, final String tabLabel) {
        this.theTexture = "items.png";
        this.hasScrollbar = true;
        this.drawTitle = true;
        if (tabIndex >= CreativeTabs.creativeTabArray.length) {
            final CreativeTabs[] creativeTabArray = new CreativeTabs[tabIndex + 1];
            while (0 < CreativeTabs.creativeTabArray.length) {
                creativeTabArray[0] = CreativeTabs.creativeTabArray[0];
                int n = 0;
                ++n;
            }
            CreativeTabs.creativeTabArray = creativeTabArray;
        }
        this.tabIndex = tabIndex;
        this.tabLabel = tabLabel;
        CreativeTabs.creativeTabArray[tabIndex] = this;
    }
    
    public int getTabIndex() {
        return this.tabIndex;
    }
    
    public CreativeTabs setBackgroundImageName(final String theTexture) {
        this.theTexture = theTexture;
        return this;
    }
    
    public String getTabLabel() {
        return this.tabLabel;
    }
    
    public String getTranslatedTabLabel() {
        return "itemGroup." + this.getTabLabel();
    }
    
    public ItemStack getIconItemStack() {
        if (this.iconItemStack == null) {
            this.iconItemStack = new ItemStack(this.getTabIconItem(), 1, this.getIconItemDamage());
        }
        return this.iconItemStack;
    }
    
    public Item getTabIconItem() {
        throw new Exception("Override this or getIconItemStack");
    }
    
    public int getIconItemDamage() {
        return 0;
    }
    
    public String getBackgroundImageName() {
        return this.theTexture;
    }
    
    public boolean drawInForegroundOfTab() {
        return this.drawTitle;
    }
    
    public CreativeTabs setNoTitle() {
        this.drawTitle = false;
        return this;
    }
    
    public boolean shouldHidePlayerInventory() {
        return this.hasScrollbar;
    }
    
    public CreativeTabs setNoScrollbar() {
        this.hasScrollbar = false;
        return this;
    }
    
    public int getTabColumn() {
        if (this.tabIndex > 11) {
            return (this.tabIndex - 12) % 10 % 5;
        }
        return this.tabIndex % 6;
    }
    
    public boolean isTabInFirstRow() {
        if (this.tabIndex > 11) {
            return (this.tabIndex - 12) % 10 < 5;
        }
        return this.tabIndex < 6;
    }
    
    public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
        return this.enchantmentTypes;
    }
    
    public CreativeTabs setRelevantEnchantmentTypes(final EnumEnchantmentType... enchantmentTypes) {
        this.enchantmentTypes = enchantmentTypes;
        return this;
    }
    
    public boolean hasRelevantEnchantmentType(final EnumEnchantmentType enumEnchantmentType) {
        if (this.enchantmentTypes == null) {
            return false;
        }
        EnumEnchantmentType[] enchantmentTypes;
        while (0 < (enchantmentTypes = this.enchantmentTypes).length) {
            if (enchantmentTypes[0] == enumEnchantmentType) {
                return true;
            }
            final byte b = 1;
        }
        return false;
    }
    
    public void displayAllReleventItems(final List list) {
        for (final Item item : Item.itemRegistry) {
            if (item != null && item.getCreativeTab() == this) {
                item.getSubItems(item, this, list);
            }
        }
        if (this.getRelevantEnchantmentTypes() != null) {
            this.addEnchantmentBooksToList(list, this.getRelevantEnchantmentTypes());
        }
    }
    
    public void addEnchantmentBooksToList(final List list, final EnumEnchantmentType... array) {
        Enchantment[] enchantmentsList;
        while (0 < (enchantmentsList = Enchantment.enchantmentsList).length) {
            final Enchantment enchantment = enchantmentsList[0];
            if (enchantment != null && enchantment.type != null) {
                if (0 < array.length) {}
                list.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, enchantment.getMaxLevel())));
            }
            final byte b = 1;
        }
    }
    
    public int getTabPage() {
        if (this.tabIndex > 11) {
            return (this.tabIndex - 12) / 10 + 1;
        }
        return 0;
    }
    
    public static int getNextID() {
        return CreativeTabs.creativeTabArray.length;
    }
    
    public boolean hasSearchBar() {
        return this.tabIndex == CreativeTabs.tabAllSearch.tabIndex;
    }
    
    public int getSearchbarWidth() {
        return 89;
    }
}
