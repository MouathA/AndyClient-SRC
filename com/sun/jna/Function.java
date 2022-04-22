package com.sun.jna;

import java.util.*;
import java.lang.reflect.*;

public class Function extends Pointer
{
    public static final int MAX_NARGS = 256;
    public static final int C_CONVENTION = 0;
    public static final int ALT_CONVENTION = 1;
    private static final int MASK_CC = 3;
    public static final int THROW_LAST_ERROR = 4;
    static final Integer INTEGER_TRUE;
    static final Integer INTEGER_FALSE;
    private NativeLibrary library;
    private final String functionName;
    int callFlags;
    final Map options;
    static final String OPTION_INVOKING_METHOD = "invoking-method";
    static Class class$com$sun$jna$NativeMapped;
    static Class array$Lcom$sun$jna$Structure$ByReference;
    static Class array$Lcom$sun$jna$Structure;
    static Class class$java$lang$Void;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Byte;
    static Class class$java$lang$Short;
    static Class class$java$lang$Character;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Long;
    static Class class$java$lang$Float;
    static Class class$java$lang$Double;
    static Class class$java$lang$String;
    static Class class$com$sun$jna$WString;
    static Class class$com$sun$jna$Pointer;
    static Class class$com$sun$jna$Structure;
    static Class class$com$sun$jna$Structure$ByValue;
    static Class class$com$sun$jna$Callback;
    static Class array$Ljava$lang$String;
    static Class array$Lcom$sun$jna$WString;
    static Class array$Lcom$sun$jna$Pointer;
    static Class class$java$lang$Object;
    static Class array$Lcom$sun$jna$NativeMapped;
    static Class class$com$sun$jna$Structure$ByReference;
    
    public static Function getFunction(final String s, final String s2) {
        return NativeLibrary.getInstance(s).getFunction(s2);
    }
    
    public static Function getFunction(final String s, final String s2, final int n) {
        return NativeLibrary.getInstance(s).getFunction(s2, n);
    }
    
    public static Function getFunction(final Pointer pointer) {
        return getFunction(pointer, 0);
    }
    
    public static Function getFunction(final Pointer pointer, final int n) {
        return new Function(pointer, n);
    }
    
    Function(final NativeLibrary library, final String functionName, final int callFlags) {
        this.checkCallingConvention(callFlags & 0x3);
        if (functionName == null) {
            throw new NullPointerException("Function name must not be null");
        }
        this.library = library;
        this.functionName = functionName;
        this.callFlags = callFlags;
        this.options = library.options;
        this.peer = library.getSymbolAddress(functionName);
    }
    
    Function(final Pointer pointer, final int callFlags) {
        this.checkCallingConvention(callFlags & 0x3);
        if (pointer == null || pointer.peer == 0L) {
            throw new NullPointerException("Function address may not be null");
        }
        this.functionName = pointer.toString();
        this.callFlags = callFlags;
        this.peer = pointer.peer;
        this.options = Collections.EMPTY_MAP;
    }
    
    private void checkCallingConvention(final int n) throws IllegalArgumentException {
        switch (n) {
            case 0:
            case 1: {}
            default: {
                throw new IllegalArgumentException("Unrecognized calling convention: " + n);
            }
        }
    }
    
    public String getName() {
        return this.functionName;
    }
    
    public int getCallingConvention() {
        return this.callFlags & 0x3;
    }
    
    public Object invoke(final Class clazz, final Object[] array) {
        return this.invoke(clazz, array, this.options);
    }
    
    public Object invoke(final Class clazz, final Object[] array, final Map map) {
        Object[] array2 = new Object[0];
        if (array != null) {
            if (array.length > 256) {
                throw new UnsupportedOperationException("Maximum argument count is 256");
            }
            array2 = new Object[array.length];
            System.arraycopy(array, 0, array2, 0, array2.length);
        }
        final TypeMapper typeMapper = map.get("type-mapper");
        final Method method = (Method)map.get("invoking-method");
        final boolean equals = Boolean.TRUE.equals(map.get("allow-objects"));
        while (0 < array2.length) {
            array2[0] = this.convertArgument(array2, 0, method, typeMapper, equals);
            int n = 0;
            ++n;
        }
        Class clazz2 = clazz;
        FromNativeConverter fromNativeConverter = null;
        if (((Function.class$com$sun$jna$NativeMapped == null) ? (Function.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Function.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz)) {
            clazz2 = ((NativeMappedConverter)(fromNativeConverter = NativeMappedConverter.getInstance(clazz))).nativeType();
        }
        else if (typeMapper != null) {
            fromNativeConverter = typeMapper.getFromNativeConverter(clazz);
            if (fromNativeConverter != null) {
                clazz2 = fromNativeConverter.nativeType();
            }
        }
        Object o = this.invoke(array2, clazz2, equals);
        if (fromNativeConverter != null) {
            FunctionResultContext functionResultContext;
            if (method != null) {
                functionResultContext = new MethodResultContext(clazz, this, array, method);
            }
            else {
                functionResultContext = new FunctionResultContext(clazz, this, array);
            }
            o = fromNativeConverter.fromNative(o, functionResultContext);
        }
        if (array != null) {
            while (0 < array.length) {
                final Object o2 = array[0];
                if (o2 != null) {
                    if (o2 instanceof Structure) {
                        if (!(o2 instanceof Structure.ByValue)) {
                            ((Structure)o2).autoRead();
                        }
                    }
                    else if (array2[0] instanceof PostCallRead) {
                        ((PostCallRead)array2[0]).read();
                        if (array2[0] instanceof PointerArray) {
                            final PointerArray pointerArray = (PointerArray)array2[0];
                            if (((Function.array$Lcom$sun$jna$Structure$ByReference == null) ? (Function.array$Lcom$sun$jna$Structure$ByReference = class$("[Lcom.sun.jna.Structure$ByReference;")) : Function.array$Lcom$sun$jna$Structure$ByReference).isAssignableFrom(((Structure)o2).getClass())) {
                                final Class<?> componentType = ((Structure)o2).getClass().getComponentType();
                                final Structure[] array3 = (Structure[])o2;
                                while (0 < array3.length) {
                                    array3[0] = Structure.updateStructureByReference(componentType, array3[0], pointerArray.getPointer(Pointer.SIZE * 0));
                                    int n2 = 0;
                                    ++n2;
                                }
                            }
                        }
                    }
                    else if (((Function.array$Lcom$sun$jna$Structure == null) ? (Function.array$Lcom$sun$jna$Structure = class$("[Lcom.sun.jna.Structure;")) : Function.array$Lcom$sun$jna$Structure).isAssignableFrom(((Structure[])o2).getClass())) {
                        Structure.autoRead((Structure[])o2);
                    }
                }
                int n3 = 0;
                ++n3;
            }
        }
        return o;
    }
    
    Object invoke(final Object[] array, final Class clazz, final boolean b) {
        Object o = null;
        if (clazz == null || clazz == Void.TYPE || clazz == ((Function.class$java$lang$Void == null) ? (Function.class$java$lang$Void = class$("java.lang.Void")) : Function.class$java$lang$Void)) {
            Native.invokeVoid(this.peer, this.callFlags, array);
            o = null;
        }
        else if (clazz == Boolean.TYPE || clazz == ((Function.class$java$lang$Boolean == null) ? (Function.class$java$lang$Boolean = class$("java.lang.Boolean")) : Function.class$java$lang$Boolean)) {
            o = valueOf(Native.invokeInt(this.peer, this.callFlags, array) != 0);
        }
        else if (clazz == Byte.TYPE || clazz == ((Function.class$java$lang$Byte == null) ? (Function.class$java$lang$Byte = class$("java.lang.Byte")) : Function.class$java$lang$Byte)) {
            o = new Byte((byte)Native.invokeInt(this.peer, this.callFlags, array));
        }
        else if (clazz == Short.TYPE || clazz == ((Function.class$java$lang$Short == null) ? (Function.class$java$lang$Short = class$("java.lang.Short")) : Function.class$java$lang$Short)) {
            o = new Short((short)Native.invokeInt(this.peer, this.callFlags, array));
        }
        else if (clazz == Character.TYPE || clazz == ((Function.class$java$lang$Character == null) ? (Function.class$java$lang$Character = class$("java.lang.Character")) : Function.class$java$lang$Character)) {
            o = new Character((char)Native.invokeInt(this.peer, this.callFlags, array));
        }
        else if (clazz == Integer.TYPE || clazz == ((Function.class$java$lang$Integer == null) ? (Function.class$java$lang$Integer = class$("java.lang.Integer")) : Function.class$java$lang$Integer)) {
            o = new Integer(Native.invokeInt(this.peer, this.callFlags, array));
        }
        else if (clazz == Long.TYPE || clazz == ((Function.class$java$lang$Long == null) ? (Function.class$java$lang$Long = class$("java.lang.Long")) : Function.class$java$lang$Long)) {
            o = new Long(Native.invokeLong(this.peer, this.callFlags, array));
        }
        else if (clazz == Float.TYPE || clazz == ((Function.class$java$lang$Float == null) ? (Function.class$java$lang$Float = class$("java.lang.Float")) : Function.class$java$lang$Float)) {
            o = new Float(Native.invokeFloat(this.peer, this.callFlags, array));
        }
        else if (clazz == Double.TYPE || clazz == ((Function.class$java$lang$Double == null) ? (Function.class$java$lang$Double = class$("java.lang.Double")) : Function.class$java$lang$Double)) {
            o = new Double(Native.invokeDouble(this.peer, this.callFlags, array));
        }
        else if (clazz == ((Function.class$java$lang$String == null) ? (Function.class$java$lang$String = class$("java.lang.String")) : Function.class$java$lang$String)) {
            o = this.invokeString(this.callFlags, array, false);
        }
        else if (clazz == ((Function.class$com$sun$jna$WString == null) ? (Function.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Function.class$com$sun$jna$WString)) {
            final String invokeString = this.invokeString(this.callFlags, array, true);
            if (invokeString != null) {
                o = new WString(invokeString);
            }
        }
        else {
            if (((Function.class$com$sun$jna$Pointer == null) ? (Function.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Function.class$com$sun$jna$Pointer).isAssignableFrom(clazz)) {
                return this.invokePointer(this.callFlags, array);
            }
            if (((Function.class$com$sun$jna$Structure == null) ? (Function.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Function.class$com$sun$jna$Structure).isAssignableFrom(clazz)) {
                if (((Function.class$com$sun$jna$Structure$ByValue == null) ? (Function.class$com$sun$jna$Structure$ByValue = class$("com.sun.jna.Structure$ByValue")) : Function.class$com$sun$jna$Structure$ByValue).isAssignableFrom(clazz)) {
                    final Structure invokeStructure = Native.invokeStructure(this.peer, this.callFlags, array, Structure.newInstance(clazz));
                    invokeStructure.autoRead();
                    o = invokeStructure;
                }
                else {
                    o = this.invokePointer(this.callFlags, array);
                    if (o != null) {
                        final Structure instance = Structure.newInstance(clazz);
                        instance.useMemory((Pointer)o);
                        instance.autoRead();
                        o = instance;
                    }
                }
            }
            else if (((Function.class$com$sun$jna$Callback == null) ? (Function.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Function.class$com$sun$jna$Callback).isAssignableFrom(clazz)) {
                o = this.invokePointer(this.callFlags, array);
                if (o != null) {
                    o = CallbackReference.getCallback(clazz, (Pointer)o);
                }
            }
            else if (clazz == ((Function.array$Ljava$lang$String == null) ? (Function.array$Ljava$lang$String = class$("[Ljava.lang.String;")) : Function.array$Ljava$lang$String)) {
                final Pointer invokePointer = this.invokePointer(this.callFlags, array);
                if (invokePointer != null) {
                    o = invokePointer.getStringArray(0L);
                }
            }
            else if (clazz == ((Function.array$Lcom$sun$jna$WString == null) ? (Function.array$Lcom$sun$jna$WString = class$("[Lcom.sun.jna.WString;")) : Function.array$Lcom$sun$jna$WString)) {
                final Pointer invokePointer2 = this.invokePointer(this.callFlags, array);
                if (invokePointer2 != null) {
                    final String[] stringArray = invokePointer2.getStringArray(0L, true);
                    final WString[] array2 = new WString[stringArray.length];
                    while (0 < stringArray.length) {
                        array2[0] = new WString(stringArray[0]);
                        int n = 0;
                        ++n;
                    }
                    o = array2;
                }
            }
            else if (clazz == ((Function.array$Lcom$sun$jna$Pointer == null) ? (Function.array$Lcom$sun$jna$Pointer = class$("[Lcom.sun.jna.Pointer;")) : Function.array$Lcom$sun$jna$Pointer)) {
                final Pointer invokePointer3 = this.invokePointer(this.callFlags, array);
                if (invokePointer3 != null) {
                    o = invokePointer3.getPointerArray(0L);
                }
            }
            else {
                if (!b) {
                    throw new IllegalArgumentException("Unsupported return type " + clazz + " in function " + this.getName());
                }
                o = Native.invokeObject(this.peer, this.callFlags, array);
                if (o != null && !clazz.isAssignableFrom(((Pointer)o).getClass())) {
                    throw new ClassCastException("Return type " + clazz + " does not match result " + ((Pointer)o).getClass());
                }
            }
        }
        return o;
    }
    
    private Pointer invokePointer(final int n, final Object[] array) {
        final long invokePointer = Native.invokePointer(this.peer, n, array);
        return (invokePointer == 0L) ? null : new Pointer(invokePointer);
    }
    
    private Object convertArgument(final Object[] array, final int n, final Method method, final TypeMapper typeMapper, final boolean b) {
        Object native1 = array[n];
        if (native1 != null) {
            final Class<? extends Structure> class1 = ((Structure)native1).getClass();
            ToNativeConverter toNativeConverter = null;
            if (((Function.class$com$sun$jna$NativeMapped == null) ? (Function.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Function.class$com$sun$jna$NativeMapped).isAssignableFrom(class1)) {
                toNativeConverter = NativeMappedConverter.getInstance(class1);
            }
            else if (typeMapper != null) {
                toNativeConverter = typeMapper.getToNativeConverter(class1);
            }
            if (toNativeConverter != null) {
                FunctionParameterContext functionParameterContext;
                if (method != null) {
                    functionParameterContext = new MethodParameterContext(this, array, n, method);
                }
                else {
                    functionParameterContext = new FunctionParameterContext(this, array, n);
                }
                native1 = toNativeConverter.toNative(native1, functionParameterContext);
            }
        }
        if (native1 == null || this.isPrimitiveArray(((Structure)native1).getClass())) {
            return native1;
        }
        final Class<? extends Structure> class2 = ((Structure)native1).getClass();
        if (native1 instanceof Structure) {
            final Structure structure = (Structure)native1;
            structure.autoWrite();
            if (structure instanceof Structure.ByValue) {
                Class<?> class3 = structure.getClass();
                if (method != null) {
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    if (isVarArgs(method)) {
                        if (n < parameterTypes.length - 1) {
                            class3 = parameterTypes[n];
                        }
                        else {
                            final Class<?> componentType = parameterTypes[parameterTypes.length - 1].getComponentType();
                            if (componentType != ((Function.class$java$lang$Object == null) ? (Function.class$java$lang$Object = class$("java.lang.Object")) : Function.class$java$lang$Object)) {
                                class3 = componentType;
                            }
                        }
                    }
                    else {
                        class3 = parameterTypes[n];
                    }
                }
                if (((Function.class$com$sun$jna$Structure$ByValue == null) ? (Function.class$com$sun$jna$Structure$ByValue = class$("com.sun.jna.Structure$ByValue")) : Function.class$com$sun$jna$Structure$ByValue).isAssignableFrom(class3)) {
                    return structure;
                }
            }
            return structure.getPointer();
        }
        if (native1 instanceof Callback) {
            return CallbackReference.getFunctionPointer((Callback)native1);
        }
        if (native1 instanceof String) {
            return new NativeString((String)native1, false).getPointer();
        }
        if (native1 instanceof WString) {
            return new NativeString(native1.toString(), true).getPointer();
        }
        if (native1 instanceof Boolean) {
            return Boolean.TRUE.equals(native1) ? Function.INTEGER_TRUE : Function.INTEGER_FALSE;
        }
        if (((Function.array$Ljava$lang$String == null) ? (Function.array$Ljava$lang$String = class$("[Ljava.lang.String;")) : Function.array$Ljava$lang$String) == class2) {
            return new StringArray((String[])native1);
        }
        if (((Function.array$Lcom$sun$jna$WString == null) ? (Function.array$Lcom$sun$jna$WString = class$("[Lcom.sun.jna.WString;")) : Function.array$Lcom$sun$jna$WString) == class2) {
            return new StringArray((WString[])native1);
        }
        if (((Function.array$Lcom$sun$jna$Pointer == null) ? (Function.array$Lcom$sun$jna$Pointer = class$("[Lcom.sun.jna.Pointer;")) : Function.array$Lcom$sun$jna$Pointer) == class2) {
            return new PointerArray((Pointer[])native1);
        }
        if (((Function.array$Lcom$sun$jna$NativeMapped == null) ? (Function.array$Lcom$sun$jna$NativeMapped = class$("[Lcom.sun.jna.NativeMapped;")) : Function.array$Lcom$sun$jna$NativeMapped).isAssignableFrom(class2)) {
            return new NativeMappedArray((NativeMapped[])native1);
        }
        if (((Function.array$Lcom$sun$jna$Structure == null) ? (Function.array$Lcom$sun$jna$Structure = class$("[Lcom.sun.jna.Structure;")) : Function.array$Lcom$sun$jna$Structure).isAssignableFrom(class2)) {
            final Structure[] array2 = (Structure[])native1;
            final Class componentType2 = class2.getComponentType();
            if (((Function.class$com$sun$jna$Structure$ByReference == null) ? (Function.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Function.class$com$sun$jna$Structure$ByReference).isAssignableFrom(componentType2)) {
                final Pointer[] array3 = new Pointer[array2.length + 1];
                while (0 < array2.length) {
                    array3[0] = ((array2[0] != null) ? array2[0].getPointer() : null);
                    int n2 = 0;
                    ++n2;
                }
                return new PointerArray(array3);
            }
            if (array2.length == 0) {
                throw new IllegalArgumentException("Structure array must have non-zero length");
            }
            if (array2[0] == null) {
                Structure.newInstance(componentType2).toArray(array2);
                return array2[0].getPointer();
            }
            Structure.autoWrite(array2);
            return array2[0].getPointer();
        }
        else {
            if (class2.isArray()) {
                throw new IllegalArgumentException("Unsupported array argument type: " + class2.getComponentType());
            }
            if (b) {
                return native1;
            }
            if (!Native.isSupportedNativeType(((Structure[])native1).getClass())) {
                throw new IllegalArgumentException("Unsupported argument type " + ((Structure[])native1).getClass().getName() + " at parameter " + n + " of function " + this.getName());
            }
            return native1;
        }
    }
    
    private boolean isPrimitiveArray(final Class clazz) {
        return clazz.isArray() && clazz.getComponentType().isPrimitive();
    }
    
    public void invoke(final Object[] array) {
        this.invoke((Function.class$java$lang$Void == null) ? (Function.class$java$lang$Void = class$("java.lang.Void")) : Function.class$java$lang$Void, array);
    }
    
    private String invokeString(final int n, final Object[] array, final boolean b) {
        final Pointer invokePointer = this.invokePointer(n, array);
        String s = null;
        if (invokePointer != null) {
            if (b) {
                s = invokePointer.getString(0L, b);
            }
            else {
                s = invokePointer.getString(0L);
            }
        }
        return s;
    }
    
    public String toString() {
        if (this.library != null) {
            return "native function " + this.functionName + "(" + this.library.getName() + ")@0x" + Long.toHexString(this.peer);
        }
        return "native function@0x" + Long.toHexString(this.peer);
    }
    
    public Object invokeObject(final Object[] array) {
        return this.invoke((Function.class$java$lang$Object == null) ? (Function.class$java$lang$Object = class$("java.lang.Object")) : Function.class$java$lang$Object, array);
    }
    
    public Pointer invokePointer(final Object[] array) {
        return (Pointer)this.invoke((Function.class$com$sun$jna$Pointer == null) ? (Function.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Function.class$com$sun$jna$Pointer, array);
    }
    
    public String invokeString(final Object[] array, final boolean b) {
        final Object invoke = this.invoke(b ? ((Function.class$com$sun$jna$WString == null) ? (Function.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Function.class$com$sun$jna$WString) : ((Function.class$java$lang$String == null) ? (Function.class$java$lang$String = class$("java.lang.String")) : Function.class$java$lang$String), array);
        return (invoke != null) ? invoke.toString() : null;
    }
    
    public int invokeInt(final Object[] array) {
        return (int)this.invoke((Function.class$java$lang$Integer == null) ? (Function.class$java$lang$Integer = class$("java.lang.Integer")) : Function.class$java$lang$Integer, array);
    }
    
    public long invokeLong(final Object[] array) {
        return (long)this.invoke((Function.class$java$lang$Long == null) ? (Function.class$java$lang$Long = class$("java.lang.Long")) : Function.class$java$lang$Long, array);
    }
    
    public float invokeFloat(final Object[] array) {
        return (float)this.invoke((Function.class$java$lang$Float == null) ? (Function.class$java$lang$Float = class$("java.lang.Float")) : Function.class$java$lang$Float, array);
    }
    
    public double invokeDouble(final Object[] array) {
        return (double)this.invoke((Function.class$java$lang$Double == null) ? (Function.class$java$lang$Double = class$("java.lang.Double")) : Function.class$java$lang$Double, array);
    }
    
    public void invokeVoid(final Object[] array) {
        this.invoke((Function.class$java$lang$Void == null) ? (Function.class$java$lang$Void = class$("java.lang.Void")) : Function.class$java$lang$Void, array);
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() == this.getClass()) {
            final Function function = (Function)o;
            return function.callFlags == this.callFlags && function.options.equals(this.options) && function.peer == this.peer;
        }
        return false;
    }
    
    public int hashCode() {
        return this.callFlags + this.options.hashCode() + super.hashCode();
    }
    
    static Object[] concatenateVarArgs(Object[] array) {
        if (array != null && array.length > 0) {
            final Object o = array[array.length - 1];
            final Class<? extends Object[]> clazz = (o != null) ? ((Object[])o).getClass() : null;
            if (clazz != null && clazz.isArray()) {
                final Object[] array2 = (Object[])o;
                final Object[] array3 = new Object[array.length + array2.length];
                System.arraycopy(array, 0, array3, 0, array.length - 1);
                System.arraycopy(array2, 0, array3, array.length - 1, array2.length);
                array3[array3.length - 1] = null;
                array = array3;
            }
        }
        return array;
    }
    
    static boolean isVarArgs(final Method method) {
        return Boolean.TRUE.equals(method.getClass().getMethod("isVarArgs", (Class<?>[])new Class[0]).invoke(method, new Object[0]));
    }
    
    static Boolean valueOf(final boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        INTEGER_TRUE = new Integer(-1);
        INTEGER_FALSE = new Integer(0);
    }
    
    private static class PointerArray extends Memory implements PostCallRead
    {
        private final Pointer[] original;
        
        public PointerArray(final Pointer[] original) {
            super(Pointer.SIZE * (original.length + 1));
            this.original = original;
            while (0 < original.length) {
                this.setPointer(0 * Pointer.SIZE, original[0]);
                int n = 0;
                ++n;
            }
            this.setPointer(Pointer.SIZE * original.length, null);
        }
        
        public void read() {
            this.read(0L, this.original, 0, this.original.length);
        }
    }
    
    public interface PostCallRead
    {
        void read();
    }
    
    private static class NativeMappedArray extends Memory implements PostCallRead
    {
        private final NativeMapped[] original;
        
        public NativeMappedArray(final NativeMapped[] original) {
            super(Native.getNativeSize(original.getClass(), original));
            this.setValue(0L, this.original = original, this.original.getClass());
        }
        
        public void read() {
            this.getValue(0L, this.original.getClass(), this.original);
        }
    }
}
