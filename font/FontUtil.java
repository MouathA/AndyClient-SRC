package font;

import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.util.*;

public class FontUtil
{
    public static int completed;
    public static MinecraftFontRenderer normal;
    private static Font normal_;
    
    private static Font getFont(final Map map, final String s, final int n) {
        Font font;
        if (map.containsKey(s)) {
            font = map.get(s).deriveFont(0, (float)n);
        }
        else {
            final Font font2 = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("MooDTool/fonts/" + s)).getInputStream());
            map.put(s, font2);
            font = font2.deriveFont(0, (float)n);
        }
        return font;
    }
    
    public static void bootstrap() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokedynamic   BootstrapMethod #0, run:()Ljava/lang/Runnable;
        //     9: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    12: invokevirtual   java/lang/Thread.start:()V
        //    15: new             Ljava/lang/Thread;
        //    18: dup            
        //    19: invokedynamic   BootstrapMethod #1, run:()Ljava/lang/Runnable;
        //    24: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    27: invokevirtual   java/lang/Thread.start:()V
        //    30: new             Ljava/lang/Thread;
        //    33: dup            
        //    34: invokedynamic   BootstrapMethod #2, run:()Ljava/lang/Runnable;
        //    39: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    42: invokevirtual   java/lang/Thread.start:()V
        //    45: goto            62
        //    48: ldc2_w          5
        //    51: invokestatic    java/lang/Thread.sleep:(J)V
        //    54: goto            62
        //    57: astore_0       
        //    58: aload_0        
        //    59: invokevirtual   java/lang/InterruptedException.printStackTrace:()V
        //    62: if_icmplt       48
        //    65: new             Lfont/MinecraftFontRenderer;
        //    68: dup            
        //    69: getstatic       font/FontUtil.normal_:Ljava/awt/Font;
        //    72: iconst_1       
        //    73: iconst_1       
        //    74: invokespecial   font/MinecraftFontRenderer.<init>:(Ljava/awt/Font;ZZ)V
        //    77: putstatic       font/FontUtil.normal:Lfont/MinecraftFontRenderer;
        //    80: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static void lambda$0() {
        FontUtil.normal_ = getFont(new HashMap(), "arial.ttf", 19);
        ++FontUtil.completed;
    }
    
    private static void lambda$1() {
        final HashMap hashMap = new HashMap();
        ++FontUtil.completed;
    }
    
    private static void lambda$2() {
        final HashMap hashMap = new HashMap();
        ++FontUtil.completed;
    }
}
