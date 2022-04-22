package Mood;

import net.minecraft.item.*;
import java.util.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class HackerItemsHelper
{
    public static ItemStack Give_Block;
    public static String Give_lock;
    public static String target;
    public static ArrayList Client_rdmText;
    public static ArrayList RandomNames;
    public static ArrayList RandomProfilPictures;
    public static ArrayList ThrowItemsData;
    public static boolean Udvozles;
    
    static {
        HackerItemsHelper.Give_Block = null;
        HackerItemsHelper.Give_lock = "";
        HackerItemsHelper.target = "";
        HackerItemsHelper.Client_rdmText = new ArrayList();
        HackerItemsHelper.RandomNames = new ArrayList();
        HackerItemsHelper.RandomProfilPictures = new ArrayList();
        HackerItemsHelper.ThrowItemsData = new ArrayList();
        HackerItemsHelper.Udvozles = true;
    }
    
    public static void copyString(final String s) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
    }
}
