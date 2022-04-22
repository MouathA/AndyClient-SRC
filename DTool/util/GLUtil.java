package DTool.util;

import org.lwjgl.opengl.*;
import java.util.*;

public class GLUtil
{
    private static Map glCapMap;
    
    static {
        GLUtil.glCapMap = new HashMap();
    }
    
    public static void setGLCap(final int n, final boolean b) {
        GLUtil.glCapMap.put(n, GL11.glGetBoolean(n));
        if (b) {
            GL11.glEnable(n);
        }
        else {
            GL11.glDisable(n);
        }
    }
    
    public static void revertGLCap(final int n) {
        final Boolean b = GLUtil.glCapMap.get(n);
        if (b != null) {
            if (b) {
                GL11.glEnable(n);
            }
            else {
                GL11.glDisable(n);
            }
        }
    }
    
    public static void glEnable(final int n) {
        setGLCap(n, true);
    }
    
    public static void glDisable(final int n) {
        setGLCap(n, false);
    }
    
    public static void revertAllCaps() {
        final Iterator<Integer> iterator = GLUtil.glCapMap.keySet().iterator();
        while (iterator.hasNext()) {
            revertGLCap(iterator.next());
        }
    }
}
