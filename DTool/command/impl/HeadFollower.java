package DTool.command.impl;

import DTool.command.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import Mood.*;

public class HeadFollower extends Command
{
    public static boolean enabled;
    public static Random rnd;
    public static String Jatekosneve;
    
    static {
        HeadFollower.enabled = false;
        HeadFollower.rnd = new Random();
        HeadFollower.Jatekosneve = "";
    }
    
    public HeadFollower() {
        super("HeadFollower", "HeadFollower", "HeadFollower", new String[] { "HeadFollower" });
    }
    
    public static ItemStack createHeadFollower(final double n, final double n2, final double n3) {
        final ItemStack itemStack = new ItemStack(Items.armor_stand);
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("Invisible", 1);
        nbtTagCompound.setInteger("NoBasePlate", 1);
        nbtTagCompound.setInteger("CustomName", 1);
        nbtTagCompound.setInteger("CustomNameVisible", 1);
        nbtTagCompound.setInteger("NoGravity", 1);
        final NBTTagList list = new NBTTagList();
        final NBTTagList list2 = new NBTTagList();
        list.appendTag(new NBTTagDouble(n));
        list.appendTag(new NBTTagDouble(n2));
        list.appendTag(new NBTTagDouble(n3));
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound4 = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound5 = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound6 = new NBTTagCompound();
        nbtTagCompound6.setString("id", "skull");
        nbtTagCompound6.setByte("Damage", (byte)1);
        nbtTagCompound6.setByte("Count", (byte)1);
        final NBTTagCompound nbtTagCompound7 = new NBTTagCompound();
        nbtTagCompound7.setString("SkullOwner", HeadFollower.Jatekosneve);
        nbtTagCompound6.setTag("tag", nbtTagCompound7);
        list2.appendTag(nbtTagCompound2);
        list2.appendTag(nbtTagCompound3);
        list2.appendTag(nbtTagCompound4);
        list2.appendTag(nbtTagCompound5);
        list2.appendTag(nbtTagCompound6);
        nbtTagCompound.setTag("Equipment", list2);
        nbtTagCompound.setTag("Pos", list);
        tagCompound.setTag("EntityTag", nbtTagCompound);
        itemStack.setTagCompound(tagCompound);
        return itemStack;
    }
    
    public static void onTick() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            206
        //     6: getstatic       Mood/HackerItemsHelper.target:Ljava/lang/String;
        //     9: ifnull          206
        //    12: getstatic       net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    15: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //    18: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    23: astore_1       
        //    24: goto            197
        //    27: aload_1        
        //    28: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    33: astore_0       
        //    34: aload_0        
        //    35: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    38: ifeq            197
        //    41: aload_0        
        //    42: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    45: if_acmpeq       197
        //    48: aload_0        
        //    49: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    52: astore_2       
        //    53: getstatic       Mood/HackerItemsHelper.target:Ljava/lang/String;
        //    56: aload_2        
        //    57: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //    60: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //    63: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    66: pop            
        //    67: iconst_0       
        //    68: putstatic       net/minecraft/client/Minecraft.rightClickDelayTimer:I
        //    71: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    74: pop            
        //    75: getstatic       net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //    78: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.isInCreativeMode:()Z
        //    81: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    84: pop            
        //    85: iconst_0       
        //    86: putstatic       net/minecraft/client/Minecraft.rightClickDelayTimer:I
        //    89: getstatic       net/minecraft/init/Items.armor_stand:Lnet/minecraft/item/ItemArmorStand;
        //    92: iconst_1       
        //    93: iconst_0       
        //    94: new             Ljava/lang/StringBuilder;
        //    97: dup            
        //    98: ldc             "{display:{Name:\"§8§l[§6§l\u00c1ldozat§8§l]: §e§l"
        //   100: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   103: aload_2        
        //   104: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //   107: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   110: ldc             "\"},EntityTag:{CustomName:\""
        //   112: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   115: aload_2        
        //   116: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //   119: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   122: ldc             "\",CustomNameVisible:1,Pos:["
        //   124: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   127: aload_2        
        //   128: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   131: invokevirtual   java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        //   134: ldc             ","
        //   136: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   139: aload_2        
        //   140: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   143: invokevirtual   java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        //   146: ldc             ","
        //   148: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   151: aload_2        
        //   152: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   155: invokevirtual   java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
        //   158: ldc             "],NoBasePlate:1,Invisible:1,NoGravity:1b,Small:1,Equipment:[{},{},{},{},{id:\"skull\",Count:1b,Damage:3b,tag:{SkullOwner:\""
        //   160: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   163: aload_2        
        //   164: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //   167: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   170: ldc             "\"}}],DisabledSlots:2039583,Rotation:["
        //   172: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   175: aload_2        
        //   176: invokevirtual   net/minecraft/entity/player/EntityPlayer.getRotationYawHead:()F
        //   179: invokevirtual   java/lang/StringBuilder.append:(F)Ljava/lang/StringBuilder;
        //   182: ldc             "f,0.0f]}}"
        //   184: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   187: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   190: invokestatic    Mood/HackedItemUtils.createItem:(Lnet/minecraft/item/Item;IILjava/lang/String;)Lnet/minecraft/item/ItemStack;
        //   193: invokestatic    Mood/HackedItemUtils.addItem:(Lnet/minecraft/item/ItemStack;)Z
        //   196: pop            
        //   197: aload_1        
        //   198: invokeinterface java/util/Iterator.hasNext:()Z
        //   203: ifne            27
        //   206: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0197 (coming from #0196).
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
            Segito.msg("§7Haszn\u00e1lat:§b -headfollower <elindit/leallit>");
        }
        else {
            if (array[0].equalsIgnoreCase("elindit")) {
                final Minecraft mc = HeadFollower.mc;
                if (Minecraft.playerController.isNotCreative()) {
                    Segito.msg("Ezt csak§b kreat\u00edvm\u00f3dban§7 haszn\u00e1lhatod!");
                    final Minecraft mc2 = HeadFollower.mc;
                    Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                    return;
                }
                final Minecraft mc3 = HeadFollower.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                HeadFollower.enabled = true;
                Segito.msg("§dElind\u00edtva!");
            }
            if (array[0].equalsIgnoreCase("leallit")) {
                final Minecraft mc4 = HeadFollower.mc;
                Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 2.0f));
                HackedItemUtils.addItem(null);
                HeadFollower.enabled = false;
                Segito.msg("§7Le\u00e1ll\u00edtva!");
            }
        }
    }
}
