package com.sun.jna.win32;

import com.sun.jna.*;

public class W32APITypeMapper extends DefaultTypeMapper
{
    public static final TypeMapper UNICODE;
    public static final TypeMapper ASCII;
    static Class class$com$sun$jna$WString;
    static Class class$java$lang$String;
    static Class array$Ljava$lang$String;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Boolean;
    
    protected W32APITypeMapper(final boolean b) {
        if (b) {
            final TypeConverter typeConverter = new TypeConverter() {
                private final W32APITypeMapper this$0;
                
                public Object toNative(final Object o, final ToNativeContext toNativeContext) {
                    if (o == null) {
                        return null;
                    }
                    if (o instanceof String[]) {
                        return new StringArray((String[])o, true);
                    }
                    return new WString(o.toString());
                }
                
                public Object fromNative(final Object o, final FromNativeContext fromNativeContext) {
                    if (o == null) {
                        return null;
                    }
                    return o.toString();
                }
                
                public Class nativeType() {
                    return (W32APITypeMapper.class$com$sun$jna$WString == null) ? (W32APITypeMapper.class$com$sun$jna$WString = W32APITypeMapper.class$("com.sun.jna.WString")) : W32APITypeMapper.class$com$sun$jna$WString;
                }
            };
            this.addTypeConverter((W32APITypeMapper.class$java$lang$String == null) ? (W32APITypeMapper.class$java$lang$String = class$("java.lang.String")) : W32APITypeMapper.class$java$lang$String, typeConverter);
            this.addToNativeConverter((W32APITypeMapper.array$Ljava$lang$String == null) ? (W32APITypeMapper.array$Ljava$lang$String = class$("[Ljava.lang.String;")) : W32APITypeMapper.array$Ljava$lang$String, typeConverter);
        }
        this.addTypeConverter((W32APITypeMapper.class$java$lang$Boolean == null) ? (W32APITypeMapper.class$java$lang$Boolean = class$("java.lang.Boolean")) : W32APITypeMapper.class$java$lang$Boolean, new TypeConverter() {
            private final W32APITypeMapper this$0;
            
            public Object toNative(final Object o, final ToNativeContext toNativeContext) {
                return new Integer(Boolean.TRUE.equals(o) ? 1 : 0);
            }
            
            public Object fromNative(final Object o, final FromNativeContext fromNativeContext) {
                return ((int)o != 0) ? Boolean.TRUE : Boolean.FALSE;
            }
            
            public Class nativeType() {
                return (W32APITypeMapper.class$java$lang$Integer == null) ? (W32APITypeMapper.class$java$lang$Integer = W32APITypeMapper.class$("java.lang.Integer")) : W32APITypeMapper.class$java$lang$Integer;
            }
        });
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        UNICODE = new W32APITypeMapper(true);
        ASCII = new W32APITypeMapper(false);
    }
}
