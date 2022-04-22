package DTool.command.impl;

import DTool.command.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import Mood.*;

public class MultiHolo extends Command
{
    public static boolean enabled;
    public static String text;
    
    static {
        MultiHolo.enabled = false;
        MultiHolo.text = "";
    }
    
    public MultiHolo() {
        super("MultiHolo", "MultiHolo", "MultiHolo", new String[] { "MultiHolo" });
    }
    
    public static double between(final int n, final int n2) {
        return new Random().nextInt(n2 - n + 1) + n;
    }
    
    public static ItemStack createMultiholo(final String s) {
        final ItemStack itemStack = new ItemStack(Items.armor_stand);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("Invisible", 1);
        nbtTagCompound.setString("CustomName", s);
        nbtTagCompound.setInteger("CustomNameVisible", 1);
        nbtTagCompound.setInteger("NoGravity", 1);
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagDouble(0.0 + between(-80, 80)));
        list.appendTag(new NBTTagDouble(0.0 + between(-20, 20)));
        list.appendTag(new NBTTagDouble(0.0 + between(-80, 80)));
        nbtTagCompound.setTag("Pos", list);
        tagCompound.setTag("EntityTag", nbtTagCompound);
        itemStack.setTagCompound(tagCompound);
        itemStack.setStackDisplayName("§cSPAM§a THIS");
        Minecraft.getMinecraft();
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, itemStack));
        return itemStack;
    }
    
    public static void onTick() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/Random.<init>:()V
        //     7: astore_0       
        //     8: bipush          15
        //    10: newarray        C
        //    12: dup            
        //    13: iconst_0       
        //    14: bipush          49
        //    16: castore        
        //    17: dup            
        //    18: iconst_1       
        //    19: bipush          50
        //    21: castore        
        //    22: dup            
        //    23: iconst_2       
        //    24: bipush          51
        //    26: castore        
        //    27: dup            
        //    28: iconst_3       
        //    29: bipush          52
        //    31: castore        
        //    32: dup            
        //    33: iconst_4       
        //    34: bipush          53
        //    36: castore        
        //    37: dup            
        //    38: iconst_5       
        //    39: bipush          54
        //    41: castore        
        //    42: dup            
        //    43: bipush          6
        //    45: bipush          55
        //    47: castore        
        //    48: dup            
        //    49: bipush          7
        //    51: bipush          57
        //    53: castore        
        //    54: dup            
        //    55: bipush          8
        //    57: bipush          97
        //    59: castore        
        //    60: dup            
        //    61: bipush          9
        //    63: bipush          98
        //    65: castore        
        //    66: dup            
        //    67: bipush          10
        //    69: bipush          99
        //    71: castore        
        //    72: dup            
        //    73: bipush          11
        //    75: bipush          100
        //    77: castore        
        //    78: dup            
        //    79: bipush          12
        //    81: bipush          101
        //    83: castore        
        //    84: dup            
        //    85: bipush          13
        //    87: bipush          102
        //    89: castore        
        //    90: dup            
        //    91: bipush          14
        //    93: bipush          107
        //    95: castore        
        //    96: astore_1       
        //    97: aload_1        
        //    98: aload_0        
        //    99: aload_1        
        //   100: arraylength    
        //   101: invokevirtual   java/util/Random.nextInt:(I)I
        //   104: caload         
        //   105: istore_2       
        //   106: getstatic       DTool/command/impl/MultiHolo.enabled:Z
        //   109: ifeq            177
        //   112: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   115: pop            
        //   116: getstatic       net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   119: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.isInCreativeMode:()Z
        //   122: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   125: pop            
        //   126: iconst_0       
        //   127: putstatic       net/minecraft/client/Minecraft.rightClickDelayTimer:I
        //   130: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   133: pop            
        //   134: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   137: getfield        net/minecraft/client/entity/EntityPlayerSP.sendQueue:Lnet/minecraft/client/network/NetHandlerPlayClient;
        //   140: new             Lnet/minecraft/network/play/client/C10PacketCreativeInventoryAction;
        //   143: dup            
        //   144: bipush          36
        //   146: new             Ljava/lang/StringBuilder;
        //   149: dup            
        //   150: ldc             "§"
        //   152: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   155: iload_2        
        //   156: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   159: getstatic       DTool/command/impl/MultiHolo.text:Ljava/lang/String;
        //   162: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   165: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   168: invokestatic    DTool/command/impl/MultiHolo.createMultiholo:(Ljava/lang/String;)Lnet/minecraft/item/ItemStack;
        //   171: invokespecial   net/minecraft/network/play/client/C10PacketCreativeInventoryAction.<init>:(ILnet/minecraft/item/ItemStack;)V
        //   174: invokevirtual   net/minecraft/client/network/NetHandlerPlayClient.addToSendQueue:(Lnet/minecraft/network/Packet;)V
        //   177: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0177 (coming from #0174).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length < 1) {
            Segito.msg("§7Haszn\u00e1lat:§b -multiholo <beallit/szoveg/elindit/leallit>");
            return;
        }
        if (array[0].equalsIgnoreCase("beallit")) {
            final Minecraft mc = MultiHolo.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Minecraft.getMinecraft();
            MultiHolo.x = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            MultiHolo.y = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            MultiHolo.z = Minecraft.thePlayer.posZ;
            Segito.msg("§7A §eHologramok§7 enn\u00e9l a pontn\u00e1l fognak megjelenni!");
        }
        if (array[0].equalsIgnoreCase("elindit")) {
            final Minecraft mc2 = MultiHolo.mc;
            if (Minecraft.playerController.isNotCreative()) {
                Segito.msg("§7Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                final Minecraft mc3 = MultiHolo.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                return;
            }
            final Minecraft mc4 = MultiHolo.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            MultiHolo.enabled = true;
            Segito.msg("§7Elind\u00edtva!");
            Segito.msg("§7Mostant\u00f3l ezt a sz\u00f6veget");
            Segito.msg("§7fogod tudni spamelni:§e " + MultiHolo.text);
        }
        if (array[0].equalsIgnoreCase("szoveg")) {
            String string = "";
            while (1 < array.length) {
                string = String.valueOf(String.valueOf(string)) + array[1] + " ";
                int n = 0;
                ++n;
            }
            MultiHolo.text = string.replace("", "");
            final Minecraft mc5 = MultiHolo.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            Segito.msg("§7Sz\u00f6veg be\u00e1ll\u00edtva!");
        }
        if (array[0].equalsIgnoreCase("leallit")) {
            final Minecraft mc6 = MultiHolo.mc;
            Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
            MultiHolo.enabled = false;
            HackedItemUtils.addItem(null);
            Segito.msg("§7Le\u00e1ll\u00edtva!");
        }
    }
}
