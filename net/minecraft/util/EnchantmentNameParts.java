package net.minecraft.util;

import java.util.*;

public class EnchantmentNameParts
{
    private static final EnchantmentNameParts instance;
    private Random rand;
    private String[] namePartsArray;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000756";
        instance = new EnchantmentNameParts();
    }
    
    public EnchantmentNameParts() {
        this.rand = new Random();
        this.namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
    }
    
    public static EnchantmentNameParts func_178176_a() {
        return EnchantmentNameParts.instance;
    }
    
    public String generateNewRandomName() {
        final int n = this.rand.nextInt(2) + 3;
        String s = "";
        while (0 < n) {
            if (0 > 0) {
                s = String.valueOf(s) + " ";
            }
            s = String.valueOf(s) + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
            int n2 = 0;
            ++n2;
        }
        return s;
    }
    
    public void reseedRandomGenerator(final long seed) {
        this.rand.setSeed(seed);
    }
}
