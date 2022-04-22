package com.sun.jna;

import java.lang.reflect.*;
import java.nio.*;
import java.util.*;

public class Pointer
{
    public static final int SIZE;
    public static final Pointer NULL;
    protected long peer;
    static Class class$com$sun$jna$Structure;
    static Class class$com$sun$jna$Structure$ByReference;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Byte;
    static Class class$java$lang$Short;
    static Class class$java$lang$Character;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Long;
    static Class class$java$lang$Float;
    static Class class$java$lang$Double;
    static Class class$com$sun$jna$Pointer;
    static Class class$java$lang$String;
    static Class class$com$sun$jna$WString;
    static Class class$com$sun$jna$Callback;
    static Class class$java$nio$Buffer;
    static Class class$com$sun$jna$NativeMapped;
    
    public static final Pointer createConstant(final long n) {
        return new Opaque(n, null);
    }
    
    public static final Pointer createConstant(final int n) {
        return new Opaque((long)n & -1L, null);
    }
    
    Pointer() {
    }
    
    public Pointer(final long peer) {
        this.peer = peer;
    }
    
    public Pointer share(final long n) {
        return this.share(n, 0L);
    }
    
    public Pointer share(final long n, final long n2) {
        if (n == 0L) {
            return this;
        }
        return new Pointer(this.peer + n);
    }
    
    public void clear(final long n) {
        this.setMemory(0L, n, (byte)0);
    }
    
    public boolean equals(final Object o) {
        return o == this || (o != null && o instanceof Pointer && ((Pointer)o).peer == this.peer);
    }
    
    public int hashCode() {
        return (int)((this.peer >>> 32) + (this.peer & -1L));
    }
    
    public long indexOf(final long n, final byte b) {
        return Native.indexOf(this.peer + n, b);
    }
    
    public void read(final long n, final byte[] array, final int n2, final int n3) {
        Native.read(this.peer + n, array, n2, n3);
    }
    
    public void read(final long n, final short[] array, final int n2, final int n3) {
        Native.read(this.peer + n, array, n2, n3);
    }
    
    public void read(final long n, final char[] array, final int n2, final int n3) {
        Native.read(this.peer + n, array, n2, n3);
    }
    
    public void read(final long n, final int[] array, final int n2, final int n3) {
        Native.read(this.peer + n, array, n2, n3);
    }
    
    public void read(final long n, final long[] array, final int n2, final int n3) {
        Native.read(this.peer + n, array, n2, n3);
    }
    
    public void read(final long n, final float[] array, final int n2, final int n3) {
        Native.read(this.peer + n, array, n2, n3);
    }
    
    public void read(final long n, final double[] array, final int n2, final int n3) {
        Native.read(this.peer + n, array, n2, n3);
    }
    
    public void read(final long n, final Pointer[] array, final int n2, final int n3) {
        while (0 < n3) {
            final Pointer pointer = this.getPointer(n + 0 * Pointer.SIZE);
            final Pointer pointer2 = array[0 + n2];
            if (pointer2 == null || pointer == null || pointer.peer != pointer2.peer) {
                array[0 + n2] = pointer;
            }
            int n4 = 0;
            ++n4;
        }
    }
    
    public void write(final long n, final byte[] array, final int n2, final int n3) {
        Native.write(this.peer + n, array, n2, n3);
    }
    
    public void write(final long n, final short[] array, final int n2, final int n3) {
        Native.write(this.peer + n, array, n2, n3);
    }
    
    public void write(final long n, final char[] array, final int n2, final int n3) {
        Native.write(this.peer + n, array, n2, n3);
    }
    
    public void write(final long n, final int[] array, final int n2, final int n3) {
        Native.write(this.peer + n, array, n2, n3);
    }
    
    public void write(final long n, final long[] array, final int n2, final int n3) {
        Native.write(this.peer + n, array, n2, n3);
    }
    
    public void write(final long n, final float[] array, final int n2, final int n3) {
        Native.write(this.peer + n, array, n2, n3);
    }
    
    public void write(final long n, final double[] array, final int n2, final int n3) {
        Native.write(this.peer + n, array, n2, n3);
    }
    
    public void write(final long n, final Pointer[] array, final int n2, final int n3) {
        while (0 < n3) {
            this.setPointer(n + 0 * Pointer.SIZE, array[n2 + 0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    Object getValue(final long n, final Class clazz, final Object o) {
        Object o2 = null;
        if (((Pointer.class$com$sun$jna$Structure == null) ? (Pointer.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Pointer.class$com$sun$jna$Structure).isAssignableFrom(clazz)) {
            Structure updateStructureByReference = (Structure)o;
            if (((Pointer.class$com$sun$jna$Structure$ByReference == null) ? (Pointer.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Pointer.class$com$sun$jna$Structure$ByReference).isAssignableFrom(clazz)) {
                updateStructureByReference = Structure.updateStructureByReference(clazz, updateStructureByReference, this.getPointer(n));
            }
            else {
                updateStructureByReference.useMemory(this, (int)n);
                updateStructureByReference.read();
            }
            o2 = updateStructureByReference;
        }
        else if (clazz == Boolean.TYPE || clazz == ((Pointer.class$java$lang$Boolean == null) ? (Pointer.class$java$lang$Boolean = class$("java.lang.Boolean")) : Pointer.class$java$lang$Boolean)) {
            o2 = Function.valueOf(this.getInt(n) != 0);
        }
        else if (clazz == Byte.TYPE || clazz == ((Pointer.class$java$lang$Byte == null) ? (Pointer.class$java$lang$Byte = class$("java.lang.Byte")) : Pointer.class$java$lang$Byte)) {
            o2 = new Byte(this.getByte(n));
        }
        else if (clazz == Short.TYPE || clazz == ((Pointer.class$java$lang$Short == null) ? (Pointer.class$java$lang$Short = class$("java.lang.Short")) : Pointer.class$java$lang$Short)) {
            o2 = new Short(this.getShort(n));
        }
        else if (clazz == Character.TYPE || clazz == ((Pointer.class$java$lang$Character == null) ? (Pointer.class$java$lang$Character = class$("java.lang.Character")) : Pointer.class$java$lang$Character)) {
            o2 = new Character(this.getChar(n));
        }
        else if (clazz == Integer.TYPE || clazz == ((Pointer.class$java$lang$Integer == null) ? (Pointer.class$java$lang$Integer = class$("java.lang.Integer")) : Pointer.class$java$lang$Integer)) {
            o2 = new Integer(this.getInt(n));
        }
        else if (clazz == Long.TYPE || clazz == ((Pointer.class$java$lang$Long == null) ? (Pointer.class$java$lang$Long = class$("java.lang.Long")) : Pointer.class$java$lang$Long)) {
            o2 = new Long(this.getLong(n));
        }
        else if (clazz == Float.TYPE || clazz == ((Pointer.class$java$lang$Float == null) ? (Pointer.class$java$lang$Float = class$("java.lang.Float")) : Pointer.class$java$lang$Float)) {
            o2 = new Float(this.getFloat(n));
        }
        else if (clazz == Double.TYPE || clazz == ((Pointer.class$java$lang$Double == null) ? (Pointer.class$java$lang$Double = class$("java.lang.Double")) : Pointer.class$java$lang$Double)) {
            o2 = new Double(this.getDouble(n));
        }
        else if (((Pointer.class$com$sun$jna$Pointer == null) ? (Pointer.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Pointer.class$com$sun$jna$Pointer).isAssignableFrom(clazz)) {
            final Pointer pointer = this.getPointer(n);
            if (pointer != null) {
                final Pointer pointer2 = (o instanceof Pointer) ? ((Pointer)o) : null;
                if (pointer2 == null || pointer.peer != pointer2.peer) {
                    o2 = pointer;
                }
                else {
                    o2 = pointer2;
                }
            }
        }
        else if (clazz == ((Pointer.class$java$lang$String == null) ? (Pointer.class$java$lang$String = class$("java.lang.String")) : Pointer.class$java$lang$String)) {
            final Pointer pointer3 = this.getPointer(n);
            o2 = ((pointer3 != null) ? pointer3.getString(0L) : null);
        }
        else if (clazz == ((Pointer.class$com$sun$jna$WString == null) ? (Pointer.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Pointer.class$com$sun$jna$WString)) {
            final Pointer pointer4 = this.getPointer(n);
            o2 = ((pointer4 != null) ? new WString(pointer4.getString(0L, true)) : null);
        }
        else if (((Pointer.class$com$sun$jna$Callback == null) ? (Pointer.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Pointer.class$com$sun$jna$Callback).isAssignableFrom(clazz)) {
            final Pointer pointer5 = this.getPointer(n);
            if (pointer5 == null) {
                o2 = null;
            }
            else {
                Callback callback = (Callback)o;
                if (!pointer5.equals(CallbackReference.getFunctionPointer(callback))) {
                    callback = CallbackReference.getCallback(clazz, pointer5);
                }
                o2 = callback;
            }
        }
        else if (Platform.HAS_BUFFERS && ((Pointer.class$java$nio$Buffer == null) ? (Pointer.class$java$nio$Buffer = class$("java.nio.Buffer")) : Pointer.class$java$nio$Buffer).isAssignableFrom(clazz)) {
            final Pointer pointer6 = this.getPointer(n);
            if (pointer6 == null) {
                o2 = null;
            }
            else {
                final Pointer pointer7 = (o == null) ? null : Native.getDirectBufferPointer((Buffer)o);
                if (pointer7 == null || !pointer7.equals(pointer6)) {
                    throw new IllegalStateException("Can't autogenerate a direct buffer on memory read");
                }
                o2 = o;
            }
        }
        else if (((Pointer.class$com$sun$jna$NativeMapped == null) ? (Pointer.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Pointer.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz)) {
            final NativeMapped nativeMapped = (NativeMapped)o;
            if (nativeMapped != null) {
                o2 = nativeMapped.fromNative(this.getValue(n, nativeMapped.nativeType(), null), new FromNativeContext(clazz));
            }
            else {
                final NativeMappedConverter instance = NativeMappedConverter.getInstance(clazz);
                o2 = instance.fromNative(this.getValue(n, instance.nativeType(), null), new FromNativeContext(clazz));
            }
        }
        else {
            if (!clazz.isArray()) {
                throw new IllegalArgumentException("Reading \"" + clazz + "\" from memory is not supported");
            }
            o2 = o;
            if (o2 == null) {
                throw new IllegalStateException("Need an initialized array");
            }
            this.getArrayValue(n, o2, clazz.getComponentType());
        }
        return o2;
    }
    
    private void getArrayValue(final long n, final Object o, final Class clazz) {
        Array.getLength(o);
        if (clazz == Byte.TYPE) {
            this.read(n, (byte[])o, 0, 0);
        }
        else if (clazz == Short.TYPE) {
            this.read(n, (short[])o, 0, 0);
        }
        else if (clazz == Character.TYPE) {
            this.read(n, (char[])o, 0, 0);
        }
        else if (clazz == Integer.TYPE) {
            this.read(n, (int[])o, 0, 0);
        }
        else if (clazz == Long.TYPE) {
            this.read(n, (long[])o, 0, 0);
        }
        else if (clazz == Float.TYPE) {
            this.read(n, (float[])o, 0, 0);
        }
        else if (clazz == Double.TYPE) {
            this.read(n, (double[])o, 0, 0);
        }
        else if (((Pointer.class$com$sun$jna$Pointer == null) ? (Pointer.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Pointer.class$com$sun$jna$Pointer).isAssignableFrom(clazz)) {
            this.read(n, (Pointer[])o, 0, 0);
        }
        else if (((Pointer.class$com$sun$jna$Structure == null) ? (Pointer.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Pointer.class$com$sun$jna$Structure).isAssignableFrom(clazz)) {
            final Structure[] array = (Structure[])o;
            if (((Pointer.class$com$sun$jna$Structure$ByReference == null) ? (Pointer.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Pointer.class$com$sun$jna$Structure$ByReference).isAssignableFrom(clazz)) {
                final Pointer[] pointerArray = this.getPointerArray(n, array.length);
                while (0 < array.length) {
                    array[0] = Structure.updateStructureByReference(clazz, array[0], pointerArray[0]);
                    int n2 = 0;
                    ++n2;
                }
            }
            else {
                while (0 < array.length) {
                    if (array[0] == null) {
                        array[0] = Structure.newInstance(clazz);
                    }
                    array[0].useMemory(this, (int)(n + 0 * array[0].size()));
                    array[0].read();
                    int n3 = 0;
                    ++n3;
                }
            }
        }
        else {
            if (!((Pointer.class$com$sun$jna$NativeMapped == null) ? (Pointer.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Pointer.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Reading array of " + clazz + " from memory not supported");
            }
            final NativeMapped[] array2 = (NativeMapped[])o;
            final NativeMappedConverter instance = NativeMappedConverter.getInstance(clazz);
            final int n2 = Native.getNativeSize(o.getClass(), o) / array2.length;
            while (0 < array2.length) {
                array2[0] = (NativeMapped)instance.fromNative(this.getValue(n + 0, instance.nativeType(), array2[0]), new FromNativeContext(clazz));
                int n4 = 0;
                ++n4;
            }
        }
    }
    
    public byte getByte(final long n) {
        return Native.getByte(this.peer + n);
    }
    
    public char getChar(final long n) {
        return Native.getChar(this.peer + n);
    }
    
    public short getShort(final long n) {
        return Native.getShort(this.peer + n);
    }
    
    public int getInt(final long n) {
        return Native.getInt(this.peer + n);
    }
    
    public long getLong(final long n) {
        return Native.getLong(this.peer + n);
    }
    
    public NativeLong getNativeLong(final long n) {
        return new NativeLong((NativeLong.SIZE == 8) ? this.getLong(n) : this.getInt(n));
    }
    
    public float getFloat(final long n) {
        return Native.getFloat(this.peer + n);
    }
    
    public double getDouble(final long n) {
        return Native.getDouble(this.peer + n);
    }
    
    public Pointer getPointer(final long n) {
        return Native.getPointer(this.peer + n);
    }
    
    public ByteBuffer getByteBuffer(final long n, final long n2) {
        return Native.getDirectByteBuffer(this.peer + n, n2).order(ByteOrder.nativeOrder());
    }
    
    public String getString(final long n, final boolean b) {
        return Native.getString(this.peer + n, b);
    }
    
    public String getString(final long n) {
        final String property = System.getProperty("jna.encoding");
        if (property != null) {
            final long index = this.indexOf(n, (byte)0);
            if (index != -1L) {
                if (index > 2147483647L) {
                    throw new OutOfMemoryError("String exceeds maximum length: " + index);
                }
                return new String(this.getByteArray(n, (int)index), property);
            }
        }
        return this.getString(n, false);
    }
    
    public byte[] getByteArray(final long n, final int n2) {
        final byte[] array = new byte[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public char[] getCharArray(final long n, final int n2) {
        final char[] array = new char[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public short[] getShortArray(final long n, final int n2) {
        final short[] array = new short[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public int[] getIntArray(final long n, final int n2) {
        final int[] array = new int[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public long[] getLongArray(final long n, final int n2) {
        final long[] array = new long[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public float[] getFloatArray(final long n, final int n2) {
        final float[] array = new float[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public double[] getDoubleArray(final long n, final int n2) {
        final double[] array = new double[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public Pointer[] getPointerArray(final long n) {
        final ArrayList list = new ArrayList<Pointer>();
        for (Pointer pointer = this.getPointer(n); pointer != null; pointer = this.getPointer(n + 0)) {
            list.add(pointer);
            final int n2 = 0 + Pointer.SIZE;
        }
        return (Pointer[])list.toArray(new Pointer[list.size()]);
    }
    
    public Pointer[] getPointerArray(final long n, final int n2) {
        final Pointer[] array = new Pointer[n2];
        this.read(n, array, 0, n2);
        return array;
    }
    
    public String[] getStringArray(final long n) {
        return this.getStringArray(n, -1, false);
    }
    
    public String[] getStringArray(final long n, final int n2) {
        return this.getStringArray(n, n2, false);
    }
    
    public String[] getStringArray(final long n, final boolean b) {
        return this.getStringArray(n, -1, b);
    }
    
    public String[] getStringArray(final long n, final int n2, final boolean b) {
        final ArrayList list = new ArrayList<String>();
        if (n2 != -1) {
            Pointer pointer = this.getPointer(n + 0);
            while (true) {
                final int n3 = 0;
                int n4 = 0;
                ++n4;
                if (n3 >= n2) {
                    break;
                }
                list.add((pointer == null) ? null : pointer.getString(0L, b));
                if (0 >= n2) {
                    continue;
                }
                final int n5 = 0 + Pointer.SIZE;
                pointer = this.getPointer(n + 0);
            }
        }
        else {
            Pointer pointer2;
            while ((pointer2 = this.getPointer(n + 0)) != null) {
                list.add((pointer2 == null) ? null : pointer2.getString(0L, b));
                final int n6 = 0 + Pointer.SIZE;
            }
        }
        return (String[])list.toArray(new String[list.size()]);
    }
    
    void setValue(final long n, final Object o, final Class clazz) {
        if (clazz == Boolean.TYPE || clazz == ((Pointer.class$java$lang$Boolean == null) ? (Pointer.class$java$lang$Boolean = class$("java.lang.Boolean")) : Pointer.class$java$lang$Boolean)) {
            this.setInt(n, Boolean.TRUE.equals(o) ? -1 : 0);
        }
        else if (clazz == Byte.TYPE || clazz == ((Pointer.class$java$lang$Byte == null) ? (Pointer.class$java$lang$Byte = class$("java.lang.Byte")) : Pointer.class$java$lang$Byte)) {
            this.setByte(n, (byte)((o == null) ? 0 : ((byte)o)));
        }
        else if (clazz == Short.TYPE || clazz == ((Pointer.class$java$lang$Short == null) ? (Pointer.class$java$lang$Short = class$("java.lang.Short")) : Pointer.class$java$lang$Short)) {
            this.setShort(n, (short)((o == null) ? 0 : ((short)o)));
        }
        else if (clazz == Character.TYPE || clazz == ((Pointer.class$java$lang$Character == null) ? (Pointer.class$java$lang$Character = class$("java.lang.Character")) : Pointer.class$java$lang$Character)) {
            this.setChar(n, (o == null) ? '\0' : ((char)o));
        }
        else if (clazz == Integer.TYPE || clazz == ((Pointer.class$java$lang$Integer == null) ? (Pointer.class$java$lang$Integer = class$("java.lang.Integer")) : Pointer.class$java$lang$Integer)) {
            this.setInt(n, (o == null) ? 0 : ((int)o));
        }
        else if (clazz == Long.TYPE || clazz == ((Pointer.class$java$lang$Long == null) ? (Pointer.class$java$lang$Long = class$("java.lang.Long")) : Pointer.class$java$lang$Long)) {
            this.setLong(n, (o == null) ? 0L : ((long)o));
        }
        else if (clazz == Float.TYPE || clazz == ((Pointer.class$java$lang$Float == null) ? (Pointer.class$java$lang$Float = class$("java.lang.Float")) : Pointer.class$java$lang$Float)) {
            this.setFloat(n, (o == null) ? 0.0f : ((float)o));
        }
        else if (clazz == Double.TYPE || clazz == ((Pointer.class$java$lang$Double == null) ? (Pointer.class$java$lang$Double = class$("java.lang.Double")) : Pointer.class$java$lang$Double)) {
            this.setDouble(n, (o == null) ? 0.0 : ((double)o));
        }
        else if (clazz == ((Pointer.class$com$sun$jna$Pointer == null) ? (Pointer.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Pointer.class$com$sun$jna$Pointer)) {
            this.setPointer(n, (Pointer)o);
        }
        else if (clazz == ((Pointer.class$java$lang$String == null) ? (Pointer.class$java$lang$String = class$("java.lang.String")) : Pointer.class$java$lang$String)) {
            this.setPointer(n, (Pointer)o);
        }
        else if (clazz == ((Pointer.class$com$sun$jna$WString == null) ? (Pointer.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Pointer.class$com$sun$jna$WString)) {
            this.setPointer(n, (Pointer)o);
        }
        else if (((Pointer.class$com$sun$jna$Structure == null) ? (Pointer.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Pointer.class$com$sun$jna$Structure).isAssignableFrom(clazz)) {
            final Structure structure = (Structure)o;
            if (((Pointer.class$com$sun$jna$Structure$ByReference == null) ? (Pointer.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Pointer.class$com$sun$jna$Structure$ByReference).isAssignableFrom(clazz)) {
                this.setPointer(n, (structure == null) ? null : structure.getPointer());
                if (structure != null) {
                    structure.autoWrite();
                }
            }
            else {
                structure.useMemory(this, (int)n);
                structure.write();
            }
        }
        else if (((Pointer.class$com$sun$jna$Callback == null) ? (Pointer.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Pointer.class$com$sun$jna$Callback).isAssignableFrom(clazz)) {
            this.setPointer(n, CallbackReference.getFunctionPointer((Callback)o));
        }
        else if (Platform.HAS_BUFFERS && ((Pointer.class$java$nio$Buffer == null) ? (Pointer.class$java$nio$Buffer = class$("java.nio.Buffer")) : Pointer.class$java$nio$Buffer).isAssignableFrom(clazz)) {
            this.setPointer(n, (o == null) ? null : Native.getDirectBufferPointer((Buffer)o));
        }
        else if (((Pointer.class$com$sun$jna$NativeMapped == null) ? (Pointer.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Pointer.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz)) {
            final NativeMappedConverter instance = NativeMappedConverter.getInstance(clazz);
            this.setValue(n, instance.toNative(o, new ToNativeContext()), instance.nativeType());
        }
        else {
            if (!clazz.isArray()) {
                throw new IllegalArgumentException("Writing " + clazz + " to memory is not supported");
            }
            this.setArrayValue(n, o, clazz.getComponentType());
        }
    }
    
    private void setArrayValue(final long n, final Object o, final Class clazz) {
        if (clazz == Byte.TYPE) {
            final byte[] array = (byte[])o;
            this.write(n, array, 0, array.length);
        }
        else if (clazz == Short.TYPE) {
            final short[] array2 = (short[])o;
            this.write(n, array2, 0, array2.length);
        }
        else if (clazz == Character.TYPE) {
            final char[] array3 = (char[])o;
            this.write(n, array3, 0, array3.length);
        }
        else if (clazz == Integer.TYPE) {
            final int[] array4 = (int[])o;
            this.write(n, array4, 0, array4.length);
        }
        else if (clazz == Long.TYPE) {
            final long[] array5 = (long[])o;
            this.write(n, array5, 0, array5.length);
        }
        else if (clazz == Float.TYPE) {
            final float[] array6 = (float[])o;
            this.write(n, array6, 0, array6.length);
        }
        else if (clazz == Double.TYPE) {
            final double[] array7 = (double[])o;
            this.write(n, array7, 0, array7.length);
        }
        else if (((Pointer.class$com$sun$jna$Pointer == null) ? (Pointer.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Pointer.class$com$sun$jna$Pointer).isAssignableFrom(clazz)) {
            final Pointer[] array8 = (Pointer[])o;
            this.write(n, array8, 0, array8.length);
        }
        else if (((Pointer.class$com$sun$jna$Structure == null) ? (Pointer.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Pointer.class$com$sun$jna$Structure).isAssignableFrom(clazz)) {
            final Structure[] array9 = (Structure[])o;
            if (((Pointer.class$com$sun$jna$Structure$ByReference == null) ? (Pointer.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Pointer.class$com$sun$jna$Structure$ByReference).isAssignableFrom(clazz)) {
                final Pointer[] array10 = new Pointer[array9.length];
                while (0 < array9.length) {
                    if (array9[0] == null) {
                        array10[0] = null;
                    }
                    else {
                        array10[0] = array9[0].getPointer();
                        array9[0].write();
                    }
                    int n2 = 0;
                    ++n2;
                }
                this.write(n, array10, 0, array10.length);
            }
            else {
                while (0 < array9.length) {
                    if (array9[0] == null) {
                        array9[0] = Structure.newInstance(clazz);
                    }
                    array9[0].useMemory(this, (int)(n + 0 * array9[0].size()));
                    array9[0].write();
                    int n3 = 0;
                    ++n3;
                }
            }
        }
        else {
            if (!((Pointer.class$com$sun$jna$NativeMapped == null) ? (Pointer.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Pointer.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Writing array of " + clazz + " to memory not supported");
            }
            final NativeMapped[] array11 = (NativeMapped[])o;
            final NativeMappedConverter instance = NativeMappedConverter.getInstance(clazz);
            final Class nativeType = instance.nativeType();
            final int n4 = Native.getNativeSize(o.getClass(), o) / array11.length;
            while (0 < array11.length) {
                this.setValue(n + 0 * n4, instance.toNative(array11[0], new ToNativeContext()), nativeType);
                int n5 = 0;
                ++n5;
            }
        }
    }
    
    public void setMemory(final long n, final long n2, final byte b) {
        Native.setMemory(this.peer + n, n2, b);
    }
    
    public void setByte(final long n, final byte b) {
        Native.setByte(this.peer + n, b);
    }
    
    public void setShort(final long n, final short n2) {
        Native.setShort(this.peer + n, n2);
    }
    
    public void setChar(final long n, final char c) {
        Native.setChar(this.peer + n, c);
    }
    
    public void setInt(final long n, final int n2) {
        Native.setInt(this.peer + n, n2);
    }
    
    public void setLong(final long n, final long n2) {
        Native.setLong(this.peer + n, n2);
    }
    
    public void setNativeLong(final long n, final NativeLong nativeLong) {
        if (NativeLong.SIZE == 8) {
            this.setLong(n, nativeLong.longValue());
        }
        else {
            this.setInt(n, nativeLong.intValue());
        }
    }
    
    public void setFloat(final long n, final float n2) {
        Native.setFloat(this.peer + n, n2);
    }
    
    public void setDouble(final long n, final double n2) {
        Native.setDouble(this.peer + n, n2);
    }
    
    public void setPointer(final long n, final Pointer pointer) {
        Native.setPointer(this.peer + n, (pointer != null) ? pointer.peer : 0L);
    }
    
    public void setString(final long n, final String s, final boolean b) {
        Native.setString(this.peer + n, s, b);
    }
    
    public void setString(final long n, final String s) {
        final byte[] bytes = Native.getBytes(s);
        this.write(n, bytes, 0, bytes.length);
        this.setByte(n + bytes.length, (byte)0);
    }
    
    public String toString() {
        return "native@0x" + Long.toHexString(this.peer);
    }
    
    public static long nativeValue(final Pointer pointer) {
        return pointer.peer;
    }
    
    public static void nativeValue(final Pointer pointer, final long peer) {
        pointer.peer = peer;
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        if ((SIZE = Native.POINTER_SIZE) == 0) {
            throw new Error("Native library not initialized");
        }
        NULL = null;
    }
    
    private static class Opaque extends Pointer
    {
        private final String MSG;
        
        private Opaque(final long n) {
            super(n);
            this.MSG = "This pointer is opaque: " + this;
        }
        
        public long indexOf(final long n, final byte b) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void read(final long n, final byte[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void read(final long n, final char[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void read(final long n, final short[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void read(final long n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void read(final long n, final long[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void read(final long n, final float[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void read(final long n, final double[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void write(final long n, final byte[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void write(final long n, final char[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void write(final long n, final short[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void write(final long n, final int[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void write(final long n, final long[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void write(final long n, final float[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void write(final long n, final double[] array, final int n2, final int n3) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public byte getByte(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public char getChar(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public short getShort(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public int getInt(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public long getLong(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public float getFloat(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public double getDouble(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public Pointer getPointer(final long n) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public String getString(final long n, final boolean b) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setByte(final long n, final byte b) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setChar(final long n, final char c) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setShort(final long n, final short n2) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setInt(final long n, final int n2) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setLong(final long n, final long n2) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setFloat(final long n, final float n2) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setDouble(final long n, final double n2) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setPointer(final long n, final Pointer pointer) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public void setString(final long n, final String s, final boolean b) {
            throw new UnsupportedOperationException(this.MSG);
        }
        
        public String toString() {
            return "opaque@0x" + Long.toHexString(this.peer);
        }
        
        Opaque(final long n, final Pointer$1 object) {
            this(n);
        }
    }
}
