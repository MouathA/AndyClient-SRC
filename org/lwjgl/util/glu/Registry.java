package org.lwjgl.util.glu;

public class Registry extends Util
{
    private static final String versionString = "1.3";
    private static final String extensionString = "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess ";
    
    public static String gluGetString(final int n) {
        if (n == 100800) {
            return "1.3";
        }
        if (n == 100801) {
            return "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess ";
        }
        return null;
    }
    
    public static boolean gluCheckExtension(final String s, final String s2) {
        return s2 != null && s != null && s2.indexOf(s) != -1;
    }
}
