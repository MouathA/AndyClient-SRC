package com.viaversion.viaversion.libs.javassist.tools.reflect;

public class Sample
{
    private Metaobject _metaobject;
    private static ClassMetaobject _classobject;
    
    public Object trap(final Object[] array, final int n) throws Throwable {
        final Metaobject metaobject = this._metaobject;
        if (metaobject == null) {
            return ClassMetaobject.invoke(this, n, array);
        }
        return metaobject.trapMethodcall(n, array);
    }
    
    public static Object trapStatic(final Object[] array, final int n) throws Throwable {
        return Sample._classobject.trapMethodcall(n, array);
    }
    
    public static Object trapRead(final Object[] array, final String s) {
        if (array[0] == null) {
            return Sample._classobject.trapFieldRead(s);
        }
        return ((Metalevel)array[0])._getMetaobject().trapFieldRead(s);
    }
    
    public static Object trapWrite(final Object[] array, final String s) {
        final Metalevel metalevel = (Metalevel)array[0];
        if (metalevel == null) {
            Sample._classobject.trapFieldWrite(s, array[1]);
        }
        else {
            metalevel._getMetaobject().trapFieldWrite(s, array[1]);
        }
        return null;
    }
}
