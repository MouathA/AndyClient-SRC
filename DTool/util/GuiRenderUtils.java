package DTool.util;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import Mood.Gui.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public class GuiRenderUtils
{
    public static Minecraft mc;
    private static float scissorX;
    private static float scissorY;
    private static float scissorWidth;
    private static float scissorHeight;
    private static float scissorSF;
    private static boolean isScissoring;
    
    static {
        GuiRenderUtils.mc = Minecraft.getMinecraft();
    }
    
    public static float[] getScissor() {
        if (GuiRenderUtils.isScissoring) {
            return new float[] { GuiRenderUtils.scissorX, GuiRenderUtils.scissorY, GuiRenderUtils.scissorWidth, GuiRenderUtils.scissorHeight, GuiRenderUtils.scissorSF };
        }
        return new float[] { -1.0f };
    }
    
    public static void beginCrop(final float scissorX, final float scissorY, final float scissorWidth, final float scissorHeight) {
        final float scaleFactor = getScaleFactor();
        GL11.glEnable(3089);
        GL11.glScissor((int)(scissorX * scaleFactor), (int)(Display.getHeight() - scissorY * scaleFactor), (int)(scissorWidth * scaleFactor), (int)(scissorHeight * scaleFactor));
        GuiRenderUtils.isScissoring = true;
        GuiRenderUtils.scissorX = scissorX;
        GuiRenderUtils.scissorY = scissorY;
        GuiRenderUtils.scissorWidth = scissorWidth;
        GuiRenderUtils.scissorHeight = scissorHeight;
        GuiRenderUtils.scissorSF = scaleFactor;
    }
    
    public static void beginCropFixed(final float scissorX, final float scissorY, final float scissorWidth, final float scissorHeight) {
        final float scaleFactor = getScaleFactor();
        GL11.glEnable(3089);
        GL11.glScissor((int)(scissorX * scaleFactor), (int)(Display.getHeight() - scissorY * scaleFactor), (int)(scissorWidth * scaleFactor), (int)(scissorHeight * scaleFactor));
        GuiRenderUtils.isScissoring = true;
        GuiRenderUtils.scissorX = scissorX;
        GuiRenderUtils.scissorY = scissorY;
        GuiRenderUtils.scissorWidth = scissorWidth;
        GuiRenderUtils.scissorHeight = scissorHeight;
        GuiRenderUtils.scissorSF = scaleFactor;
    }
    
    public static void beginCrop(final float scissorX, final float scissorY, final float scissorWidth, final float scissorHeight, final float scissorSF) {
        GL11.glEnable(3089);
        GL11.glScissor((int)(scissorX * scissorSF), (int)(Display.getHeight() - scissorY * scissorSF), (int)(scissorWidth * scissorSF), (int)(scissorHeight * scissorSF));
        GuiRenderUtils.isScissoring = true;
        GuiRenderUtils.scissorX = scissorX;
        GuiRenderUtils.scissorY = scissorY;
        GuiRenderUtils.scissorWidth = scissorWidth;
        GuiRenderUtils.scissorHeight = scissorHeight;
        GuiRenderUtils.scissorSF = scissorSF;
    }
    
    public static void endCrop() {
        GL11.glDisable(3089);
        GuiRenderUtils.isScissoring = false;
    }
    
    public static void drawImageSpread(final ResourceLocation resourceLocation, final float n, final float n2, final float n3, final float n4, final float n5) {
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, n5);
        GuiRenderUtils.mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, n3, n4, 25.0f, 25.0f);
        GL11.glDepthMask(true);
    }
    
    public static void doGlScissor(final int n, final int n2, final float n3, final float n4, final float n5) {
        while (1 < n5 && GuiRenderUtils.mc.displayWidth / 2 >= 320 && GuiRenderUtils.mc.displayHeight / 2 >= 240) {
            int n6 = 0;
            ++n6;
        }
        GL11.glScissor(n * 1, (int)(GuiRenderUtils.mc.displayHeight - (n2 + n4) * 1), (int)(n3 * 1), (int)(n4 * 1));
    }
    
    public static void drawLine3D(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int n7) {
        drawLine3D(n, n2, n3, n4, n5, n6, n7, true);
    }
    
    public static void drawLine3D(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int color, final boolean b) {
        enableRender3D(b);
        setColor(color);
        GL11.glBegin(1);
        GL11.glVertex3d(n, n2, n3);
        GL11.glVertex3d(n4, n5, n6);
        disableRender3D(b);
    }
    
    public static void drawLine2D(final double n, final double n2, final double n3, final double n4, final float n5, final int color) {
        setColor(color);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n4);
    }
    
    public static void drawPoint(final int n, final int n2, final float n3, final int color) {
        setColor(color);
        GL11.glPointSize(n3);
        GL11.glEnable(2832);
        GL11.glBegin(0);
        GL11.glVertex2d(n, n2);
        GL11.glDisable(2832);
    }
    
    public static float getScaleFactor() {
        return (float)new ScaledResolution(GuiRenderUtils.mc).getScaleFactor();
    }
    
    public static float getScaleFactorForAbstractGuiScreen() {
        return (GuiRenderUtils.mc.currentScreen instanceof AbstractGuiScreen) ? ((AbstractGuiScreen)GuiRenderUtils.mc.currentScreen).scale : 2.0f;
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB axisAlignedBB, final int n) {
        drawOutlinedBox(axisAlignedBB, n, true);
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB axisAlignedBB, final int color, final boolean b) {
        if (axisAlignedBB != null) {
            enableRender3D(b);
            setColor(color);
            GL11.glBegin(3);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glBegin(3);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glBegin(1);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            disableRender3D(b);
        }
    }
    
    public static void drawBox(final AxisAlignedBB axisAlignedBB, final int n) {
        drawBox(axisAlignedBB, n, true);
    }
    
    public static void drawBox(final AxisAlignedBB axisAlignedBB, final int color, final boolean b) {
        if (axisAlignedBB != null) {
            enableRender3D(b);
            setColor(color);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glBegin(7);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
            GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
            disableRender3D(b);
        }
    }
    
    public static void enableRender3D(final boolean b) {
        if (b) {
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
        }
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.0f);
    }
    
    public static void disableRender3D(final boolean b) {
        if (b) {
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
        }
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void enableRender2D() {
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
    }
    
    public static void disableRender2D() {
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.shadeModel(7424);
    }
    
    public static void setColor(final int n) {
        GL11.glColor4f((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    public static void drawBorderedRect(final float n, final float n2, final float n3, final float n4, final float n5, final Color color, final Color color2) {
        drawBorderedRect(n, n2, n3, n4, n5, color.getRGB(), color2.getRGB());
    }
    
    public static void drawBorderedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        drawRect(n + n5, n2 + n5, n3 - n5 * 2.0f, n4 - n5 * 2.0f, n6);
        drawRect(n, n2, n3, n5, n7);
        drawRect(n, n2 + n5, n5, n4 - n5, n7);
        drawRect(n + n3 - n5, n2 + n5, n5, n4 - n5, n7);
        drawRect(n + n5, n2 + n4 - n5, n3 - n5 * 2.0f, n5, n7);
    }
    
    public static void drawBorder(final float n, final float n2, final float n3, final float n4, final float n5, final int n6) {
        drawRect(n + n5, n2 + n5, n3 - n5 * 2.0f, n5, n6);
        drawRect(n, n2 + n5, n5, n4 - n5, n6);
        drawRect(n + n3 - n5, n2 + n5, n5, n4 - n5, n6);
        drawRect(n + n5, n2 + n4 - n5, n3 - n5 * 2.0f, n5, n6);
    }
    
    public static void drawRect(final float n, final float n2, final float n3, final float n4, final Color color) {
        drawRect(n, n2, n3, n4, color.getRGB());
    }
    
    public static void drawRect(final float n, final float n2, final float n3, final float n4, final int color) {
        setColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n + n3, n2);
        GL11.glVertex2d(n + n3, n2 + n4);
        GL11.glVertex2d(n, n2 + n4);
    }
    
    public static void drawRoundedRect(final float p0, final float p1, final float p2, final float p3, final float p4, final int p5, final float p6, final int p7) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: ldc             16777215
        //     4: if_icmpne       15
        //     7: getstatic       java/awt/Color.BLACK:Ljava/awt/Color;
        //    10: invokevirtual   java/awt/Color.getRGB:()I
        //    13: istore          5
        //    15: iload           7
        //    17: ldc             16777215
        //    19: if_icmpne       30
        //    22: getstatic       java/awt/Color.BLACK:Ljava/awt/Color;
        //    25: invokevirtual   java/awt/Color.getRGB:()I
        //    28: istore          7
        //    30: fload           4
        //    32: fconst_0       
        //    33: fcmpg          
        //    34: ifge            40
        //    37: fconst_0       
        //    38: fstore          4
        //    40: fload           4
        //    42: fload_2        
        //    43: fconst_2       
        //    44: fdiv           
        //    45: fcmpl          
        //    46: ifle            54
        //    49: fload_2        
        //    50: fconst_2       
        //    51: fdiv           
        //    52: fstore          4
        //    54: fload           4
        //    56: fload_3        
        //    57: fconst_2       
        //    58: fdiv           
        //    59: fcmpl          
        //    60: ifle            68
        //    63: fload_3        
        //    64: fconst_2       
        //    65: fdiv           
        //    66: fstore          4
        //    68: fload_0        
        //    69: fload           4
        //    71: fadd           
        //    72: fload_1        
        //    73: fload           4
        //    75: fadd           
        //    76: fload_2        
        //    77: fload           4
        //    79: fconst_2       
        //    80: fmul           
        //    81: fsub           
        //    82: fload_3        
        //    83: fload           4
        //    85: fconst_2       
        //    86: fmul           
        //    87: fsub           
        //    88: iload           5
        //    90: invokestatic    DTool/util/GuiRenderUtils.drawRect:(FFFFI)V
        //    93: fload_0        
        //    94: fload           4
        //    96: fadd           
        //    97: fload_1        
        //    98: fload_2        
        //    99: fload           4
        //   101: fconst_2       
        //   102: fmul           
        //   103: fsub           
        //   104: fload           4
        //   106: iload           5
        //   108: invokestatic    DTool/util/GuiRenderUtils.drawRect:(FFFFI)V
        //   111: fload_0        
        //   112: fload           4
        //   114: fadd           
        //   115: fload_1        
        //   116: fload_3        
        //   117: fadd           
        //   118: fload           4
        //   120: fsub           
        //   121: fload_2        
        //   122: fload           4
        //   124: fconst_2       
        //   125: fmul           
        //   126: fsub           
        //   127: fload           4
        //   129: iload           5
        //   131: invokestatic    DTool/util/GuiRenderUtils.drawRect:(FFFFI)V
        //   134: fload_0        
        //   135: fload_1        
        //   136: fload           4
        //   138: fadd           
        //   139: fload           4
        //   141: fload_3        
        //   142: fload           4
        //   144: fconst_2       
        //   145: fmul           
        //   146: fsub           
        //   147: iload           5
        //   149: invokestatic    DTool/util/GuiRenderUtils.drawRect:(FFFFI)V
        //   152: fload_0        
        //   153: fload_2        
        //   154: fadd           
        //   155: fload           4
        //   157: fsub           
        //   158: fload_1        
        //   159: fload           4
        //   161: fadd           
        //   162: fload           4
        //   164: fload_3        
        //   165: fload           4
        //   167: fconst_2       
        //   168: fmul           
        //   169: fsub           
        //   170: iload           5
        //   172: invokestatic    DTool/util/GuiRenderUtils.drawRect:(FFFFI)V
        //   175: iload           5
        //   177: invokestatic    DTool/util/RenderUtil.color:(I)V
        //   180: bipush          6
        //   182: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   185: fload_0        
        //   186: fload           4
        //   188: fadd           
        //   189: fstore          8
        //   191: fload_1        
        //   192: fload           4
        //   194: fadd           
        //   195: fstore          9
        //   197: fload           8
        //   199: f2d            
        //   200: fload           9
        //   202: f2d            
        //   203: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   206: fload           4
        //   208: ldc             10.0
        //   210: invokestatic    java/lang/Math.max:(FF)F
        //   213: ldc             90.0
        //   215: invokestatic    java/lang/Math.min:(FF)F
        //   218: f2i            
        //   219: istore          10
        //   221: goto            272
        //   224: ldc2_w          6.283185307179586
        //   227: sipush          180
        //   230: i2d            
        //   231: dmul           
        //   232: iload           10
        //   234: iconst_4       
        //   235: imul           
        //   236: i2d            
        //   237: ddiv           
        //   238: dstore          12
        //   240: fload           8
        //   242: f2d            
        //   243: dload           12
        //   245: invokestatic    java/lang/Math.sin:(D)D
        //   248: fload           4
        //   250: f2d            
        //   251: dmul           
        //   252: dadd           
        //   253: fload           9
        //   255: f2d            
        //   256: dload           12
        //   258: invokestatic    java/lang/Math.cos:(D)D
        //   261: fload           4
        //   263: f2d            
        //   264: dmul           
        //   265: dadd           
        //   266: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   269: iinc            11, 1
        //   272: iconst_0       
        //   273: iload           10
        //   275: iconst_1       
        //   276: iadd           
        //   277: if_icmplt       224
        //   280: bipush          6
        //   282: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   285: fload_0        
        //   286: fload_2        
        //   287: fadd           
        //   288: fload           4
        //   290: fsub           
        //   291: fstore          8
        //   293: fload_1        
        //   294: fload           4
        //   296: fadd           
        //   297: fstore          9
        //   299: fload           8
        //   301: f2d            
        //   302: fload           9
        //   304: f2d            
        //   305: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   308: fload           4
        //   310: ldc             10.0
        //   312: invokestatic    java/lang/Math.max:(FF)F
        //   315: ldc             90.0
        //   317: invokestatic    java/lang/Math.min:(FF)F
        //   320: f2i            
        //   321: istore          10
        //   323: goto            373
        //   326: ldc2_w          6.283185307179586
        //   329: bipush          90
        //   331: i2d            
        //   332: dmul           
        //   333: iload           10
        //   335: iconst_4       
        //   336: imul           
        //   337: i2d            
        //   338: ddiv           
        //   339: dstore          12
        //   341: fload           8
        //   343: f2d            
        //   344: dload           12
        //   346: invokestatic    java/lang/Math.sin:(D)D
        //   349: fload           4
        //   351: f2d            
        //   352: dmul           
        //   353: dadd           
        //   354: fload           9
        //   356: f2d            
        //   357: dload           12
        //   359: invokestatic    java/lang/Math.cos:(D)D
        //   362: fload           4
        //   364: f2d            
        //   365: dmul           
        //   366: dadd           
        //   367: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   370: iinc            11, 1
        //   373: iconst_0       
        //   374: iload           10
        //   376: iconst_1       
        //   377: iadd           
        //   378: if_icmplt       326
        //   381: bipush          6
        //   383: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   386: fload_0        
        //   387: fload           4
        //   389: fadd           
        //   390: fstore          8
        //   392: fload_1        
        //   393: fload_3        
        //   394: fadd           
        //   395: fload           4
        //   397: fsub           
        //   398: fstore          9
        //   400: fload           8
        //   402: f2d            
        //   403: fload           9
        //   405: f2d            
        //   406: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   409: fload           4
        //   411: ldc             10.0
        //   413: invokestatic    java/lang/Math.max:(FF)F
        //   416: ldc             90.0
        //   418: invokestatic    java/lang/Math.min:(FF)F
        //   421: f2i            
        //   422: istore          10
        //   424: goto            475
        //   427: ldc2_w          6.283185307179586
        //   430: sipush          270
        //   433: i2d            
        //   434: dmul           
        //   435: iload           10
        //   437: iconst_4       
        //   438: imul           
        //   439: i2d            
        //   440: ddiv           
        //   441: dstore          12
        //   443: fload           8
        //   445: f2d            
        //   446: dload           12
        //   448: invokestatic    java/lang/Math.sin:(D)D
        //   451: fload           4
        //   453: f2d            
        //   454: dmul           
        //   455: dadd           
        //   456: fload           9
        //   458: f2d            
        //   459: dload           12
        //   461: invokestatic    java/lang/Math.cos:(D)D
        //   464: fload           4
        //   466: f2d            
        //   467: dmul           
        //   468: dadd           
        //   469: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   472: iinc            11, 1
        //   475: iconst_0       
        //   476: iload           10
        //   478: iconst_1       
        //   479: iadd           
        //   480: if_icmplt       427
        //   483: bipush          6
        //   485: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   488: fload_0        
        //   489: fload_2        
        //   490: fadd           
        //   491: fload           4
        //   493: fsub           
        //   494: fstore          8
        //   496: fload_1        
        //   497: fload_3        
        //   498: fadd           
        //   499: fload           4
        //   501: fsub           
        //   502: fstore          9
        //   504: fload           8
        //   506: f2d            
        //   507: fload           9
        //   509: f2d            
        //   510: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   513: fload           4
        //   515: ldc             10.0
        //   517: invokestatic    java/lang/Math.max:(FF)F
        //   520: ldc             90.0
        //   522: invokestatic    java/lang/Math.min:(FF)F
        //   525: f2i            
        //   526: istore          10
        //   528: goto            577
        //   531: ldc2_w          6.283185307179586
        //   534: iconst_0       
        //   535: i2d            
        //   536: dmul           
        //   537: iload           10
        //   539: iconst_4       
        //   540: imul           
        //   541: i2d            
        //   542: ddiv           
        //   543: dstore          12
        //   545: fload           8
        //   547: f2d            
        //   548: dload           12
        //   550: invokestatic    java/lang/Math.sin:(D)D
        //   553: fload           4
        //   555: f2d            
        //   556: dmul           
        //   557: dadd           
        //   558: fload           9
        //   560: f2d            
        //   561: dload           12
        //   563: invokestatic    java/lang/Math.cos:(D)D
        //   566: fload           4
        //   568: f2d            
        //   569: dmul           
        //   570: dadd           
        //   571: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   574: iinc            11, 1
        //   577: iconst_0       
        //   578: iload           10
        //   580: iconst_1       
        //   581: iadd           
        //   582: if_icmplt       531
        //   585: iload           7
        //   587: invokestatic    DTool/util/RenderUtil.color:(I)V
        //   590: fload           6
        //   592: invokestatic    org/lwjgl/opengl/GL11.glLineWidth:(F)V
        //   595: iconst_3       
        //   596: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   599: fload_0        
        //   600: fload           4
        //   602: fadd           
        //   603: fstore          8
        //   605: fload_1        
        //   606: fload           4
        //   608: fadd           
        //   609: fstore          9
        //   611: fload           4
        //   613: ldc             10.0
        //   615: invokestatic    java/lang/Math.max:(FF)F
        //   618: ldc             90.0
        //   620: invokestatic    java/lang/Math.min:(FF)F
        //   623: f2i            
        //   624: istore          10
        //   626: iload           10
        //   628: istore          11
        //   630: goto            681
        //   633: ldc2_w          6.283185307179586
        //   636: sipush          180
        //   639: i2d            
        //   640: dmul           
        //   641: iload           10
        //   643: iconst_4       
        //   644: imul           
        //   645: i2d            
        //   646: ddiv           
        //   647: dstore          12
        //   649: fload           8
        //   651: f2d            
        //   652: dload           12
        //   654: invokestatic    java/lang/Math.sin:(D)D
        //   657: fload           4
        //   659: f2d            
        //   660: dmul           
        //   661: dadd           
        //   662: fload           9
        //   664: f2d            
        //   665: dload           12
        //   667: invokestatic    java/lang/Math.cos:(D)D
        //   670: fload           4
        //   672: f2d            
        //   673: dmul           
        //   674: dadd           
        //   675: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   678: iinc            11, -1
        //   681: iconst_0       
        //   682: ifge            633
        //   685: fload_0        
        //   686: fload           4
        //   688: fadd           
        //   689: f2d            
        //   690: fload_1        
        //   691: f2d            
        //   692: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   695: fload_0        
        //   696: fload_2        
        //   697: fadd           
        //   698: fload           4
        //   700: fsub           
        //   701: f2d            
        //   702: fload_1        
        //   703: f2d            
        //   704: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   707: fload_0        
        //   708: fload_2        
        //   709: fadd           
        //   710: fload           4
        //   712: fsub           
        //   713: fstore          8
        //   715: fload_1        
        //   716: fload           4
        //   718: fadd           
        //   719: fstore          9
        //   721: iload           10
        //   723: istore          11
        //   725: goto            775
        //   728: ldc2_w          6.283185307179586
        //   731: bipush          90
        //   733: i2d            
        //   734: dmul           
        //   735: iload           10
        //   737: iconst_4       
        //   738: imul           
        //   739: i2d            
        //   740: ddiv           
        //   741: dstore          12
        //   743: fload           8
        //   745: f2d            
        //   746: dload           12
        //   748: invokestatic    java/lang/Math.sin:(D)D
        //   751: fload           4
        //   753: f2d            
        //   754: dmul           
        //   755: dadd           
        //   756: fload           9
        //   758: f2d            
        //   759: dload           12
        //   761: invokestatic    java/lang/Math.cos:(D)D
        //   764: fload           4
        //   766: f2d            
        //   767: dmul           
        //   768: dadd           
        //   769: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   772: iinc            11, -1
        //   775: iconst_0       
        //   776: ifge            728
        //   779: fload_0        
        //   780: fload_2        
        //   781: fadd           
        //   782: f2d            
        //   783: fload_1        
        //   784: fload           4
        //   786: fadd           
        //   787: f2d            
        //   788: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   791: fload_0        
        //   792: fload_2        
        //   793: fadd           
        //   794: f2d            
        //   795: fload_1        
        //   796: fload_3        
        //   797: fadd           
        //   798: fload           4
        //   800: fsub           
        //   801: f2d            
        //   802: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   805: fload_0        
        //   806: fload_2        
        //   807: fadd           
        //   808: fload           4
        //   810: fsub           
        //   811: fstore          8
        //   813: fload_1        
        //   814: fload_3        
        //   815: fadd           
        //   816: fload           4
        //   818: fsub           
        //   819: fstore          9
        //   821: iload           10
        //   823: istore          11
        //   825: goto            874
        //   828: ldc2_w          6.283185307179586
        //   831: iconst_0       
        //   832: i2d            
        //   833: dmul           
        //   834: iload           10
        //   836: iconst_4       
        //   837: imul           
        //   838: i2d            
        //   839: ddiv           
        //   840: dstore          12
        //   842: fload           8
        //   844: f2d            
        //   845: dload           12
        //   847: invokestatic    java/lang/Math.sin:(D)D
        //   850: fload           4
        //   852: f2d            
        //   853: dmul           
        //   854: dadd           
        //   855: fload           9
        //   857: f2d            
        //   858: dload           12
        //   860: invokestatic    java/lang/Math.cos:(D)D
        //   863: fload           4
        //   865: f2d            
        //   866: dmul           
        //   867: dadd           
        //   868: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   871: iinc            11, -1
        //   874: iconst_0       
        //   875: ifge            828
        //   878: fload_0        
        //   879: fload_2        
        //   880: fadd           
        //   881: fload           4
        //   883: fsub           
        //   884: f2d            
        //   885: fload_1        
        //   886: fload_3        
        //   887: fadd           
        //   888: f2d            
        //   889: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   892: fload_0        
        //   893: fload           4
        //   895: fadd           
        //   896: f2d            
        //   897: fload_1        
        //   898: fload_3        
        //   899: fadd           
        //   900: f2d            
        //   901: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   904: fload_0        
        //   905: fload           4
        //   907: fadd           
        //   908: fstore          8
        //   910: fload_1        
        //   911: fload_3        
        //   912: fadd           
        //   913: fload           4
        //   915: fsub           
        //   916: fstore          9
        //   918: iload           10
        //   920: istore          11
        //   922: goto            973
        //   925: ldc2_w          6.283185307179586
        //   928: sipush          270
        //   931: i2d            
        //   932: dmul           
        //   933: iload           10
        //   935: iconst_4       
        //   936: imul           
        //   937: i2d            
        //   938: ddiv           
        //   939: dstore          12
        //   941: fload           8
        //   943: f2d            
        //   944: dload           12
        //   946: invokestatic    java/lang/Math.sin:(D)D
        //   949: fload           4
        //   951: f2d            
        //   952: dmul           
        //   953: dadd           
        //   954: fload           9
        //   956: f2d            
        //   957: dload           12
        //   959: invokestatic    java/lang/Math.cos:(D)D
        //   962: fload           4
        //   964: f2d            
        //   965: dmul           
        //   966: dadd           
        //   967: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   970: iinc            11, -1
        //   973: iconst_0       
        //   974: ifge            925
        //   977: fload_0        
        //   978: f2d            
        //   979: fload_1        
        //   980: fload_3        
        //   981: fadd           
        //   982: fload           4
        //   984: fsub           
        //   985: f2d            
        //   986: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   989: fload_0        
        //   990: f2d            
        //   991: fload_1        
        //   992: fload           4
        //   994: fadd           
        //   995: f2d            
        //   996: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   999: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
    
    public static int getDisplayWidth() {
        final ScaledResolution scaledResolution = new ScaledResolution(GuiRenderUtils.mc);
        return ScaledResolution.getScaledWidth();
    }
    
    public static int getDisplayHeight() {
        final ScaledResolution scaledResolution = new ScaledResolution(GuiRenderUtils.mc);
        return ScaledResolution.getScaledHeight();
    }
    
    public static void drawCircle(final float n, final float n2, final float n3, final float n4, final int color) {
        setColor(color);
        GL11.glLineWidth(n4);
        final int n5 = (int)Math.min(Math.max(n3, 45.0f), 360.0f);
        GL11.glBegin(2);
        while (0 < n5) {
            final double n6 = 6.283185307179586 * 0 / n5;
            GL11.glVertex2d(n + Math.sin(n6) * n3, n2 + Math.cos(n6) * n3);
            int n7 = 0;
            ++n7;
        }
    }
    
    public static void drawFilledCircle(final float n, final float n2, final float n3, final int color) {
        setColor(color);
        final int n4 = (int)Math.min(Math.max(n3, 45.0f), 360.0f);
        GL11.glBegin(9);
        while (0 < n4) {
            final double n5 = 6.283185307179586 * 0 / n4;
            GL11.glVertex2d(n + Math.sin(n5) * n3, n2 + Math.cos(n5) * n3);
            int n6 = 0;
            ++n6;
        }
        drawCircle(n, n2, n3, 1.5f, 16777215);
    }
    
    public static void drawFilledCircleNoBorder(final float n, final float n2, final float n3, final int color) {
        setColor(color);
        final int n4 = (int)Math.min(Math.max(n3, 45.0f), 360.0f);
        GL11.glBegin(9);
        while (0 < n4) {
            final double n5 = 6.283185307179586 * 0 / n4;
            GL11.glVertex2d(n + Math.sin(n5) * n3, n2 + Math.cos(n5) * n3);
            int n6 = 0;
            ++n6;
        }
    }
    
    public static int darker(final int n, final int n2) {
        return (int)(((int)(float)(n >> 24 & 0xFF) << 24) + ((int)Math.max((n >> 16 & 0xFF) - (n >> 16 & 0xFF) / (100.0f / n2), 0.0f) << 16) + ((int)Math.max((n >> 8 & 0xFF) - (n >> 8 & 0xFF) / (100.0f / n2), 0.0f) << 8) + Math.max((n & 0xFF) - (n & 0xFF) / (100.0f / n2), 0.0f));
    }
    
    public static int opacity(final int n, final int n2) {
        return (int)(((int)Math.max((n >> 24 & 0xFF) - (n >> 24 & 0xFF) / (100.0f / n2), 0.0f) << 24) + ((int)(float)(n >> 16 & 0xFF) << 16) + ((int)(float)(n >> 8 & 0xFF) << 8) + (float)(n & 0xFF));
    }
}
