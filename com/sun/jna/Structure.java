package com.sun.jna;

import java.io.*;
import java.lang.reflect.*;
import java.util.zip.*;
import java.util.*;

public abstract class Structure
{
    private static final boolean REVERSE_FIELDS;
    private static final boolean REQUIRES_FIELD_ORDER;
    static final boolean isPPC;
    static final boolean isSPARC;
    static final boolean isARM;
    public static final int ALIGN_DEFAULT = 0;
    public static final int ALIGN_NONE = 1;
    public static final int ALIGN_GNUC = 2;
    public static final int ALIGN_MSVC = 3;
    protected static final int CALCULATE_SIZE = -1;
    static final Map layoutInfo;
    private Pointer memory;
    private int size;
    private int alignType;
    private int structAlignment;
    private Map structFields;
    private final Map nativeStrings;
    private TypeMapper typeMapper;
    private long typeInfo;
    private List fieldOrder;
    private boolean autoRead;
    private boolean autoWrite;
    private Structure[] array;
    private static final ThreadLocal reads;
    private static final ThreadLocal busy;
    static Class class$com$sun$jna$Structure$MemberOrder;
    static Class class$com$sun$jna$Structure;
    static Class class$com$sun$jna$Callback;
    static Class class$java$nio$Buffer;
    static Class class$com$sun$jna$Pointer;
    static Class class$com$sun$jna$NativeMapped;
    static Class class$java$lang$String;
    static Class class$com$sun$jna$WString;
    static Class class$com$sun$jna$Structure$ByReference;
    static Class class$java$lang$Long;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Short;
    static Class class$java$lang$Character;
    static Class class$java$lang$Byte;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Float;
    static Class class$java$lang$Double;
    static Class class$java$lang$Void;
    
    protected Structure() {
        this((Pointer)null);
    }
    
    protected Structure(final TypeMapper typeMapper) {
        this(null, 0, typeMapper);
    }
    
    protected Structure(final Pointer pointer) {
        this(pointer, 0);
    }
    
    protected Structure(final Pointer pointer, final int n) {
        this(pointer, n, null);
    }
    
    protected Structure(final Pointer pointer, final int alignType, final TypeMapper typeMapper) {
        this.size = -1;
        this.nativeStrings = new HashMap();
        this.autoRead = true;
        this.autoWrite = true;
        this.setAlignType(alignType);
        this.setTypeMapper(typeMapper);
        if (pointer != null) {
            this.useMemory(pointer);
        }
        else {
            this.allocateMemory(-1);
        }
    }
    
    Map fields() {
        return this.structFields;
    }
    
    TypeMapper getTypeMapper() {
        return this.typeMapper;
    }
    
    protected void setTypeMapper(TypeMapper typeMapper) {
        if (typeMapper == null) {
            final Class<?> declaringClass = this.getClass().getDeclaringClass();
            if (declaringClass != null) {
                typeMapper = Native.getTypeMapper(declaringClass);
            }
        }
        this.typeMapper = typeMapper;
        this.size = -1;
        if (this.memory instanceof AutoAllocated) {
            this.memory = null;
        }
    }
    
    protected void setAlignType(int structureAlignment) {
        if (2 == 0) {
            final Class<?> declaringClass = this.getClass().getDeclaringClass();
            if (declaringClass != null) {
                structureAlignment = Native.getStructureAlignment(declaringClass);
            }
            if (2 != 0 || Platform.isWindows()) {}
        }
        this.alignType = 2;
        this.size = -1;
        if (this.memory instanceof AutoAllocated) {
            this.memory = null;
        }
    }
    
    protected Memory autoAllocate(final int n) {
        return new AutoAllocated(n);
    }
    
    protected void useMemory(final Pointer pointer) {
        this.useMemory(pointer, 0);
    }
    
    protected void useMemory(final Pointer pointer, final int n) {
        this.memory = pointer.share(n);
        if (this.size == -1) {
            this.size = this.calculateSize(false);
        }
        if (this.size != -1) {
            this.memory = pointer.share(n, this.size);
        }
        this.array = null;
    }
    
    protected void ensureAllocated() {
        this.ensureAllocated(false);
    }
    
    private void ensureAllocated(final boolean b) {
        if (this.memory == null) {
            this.allocateMemory(b);
        }
        else if (this.size == -1) {
            this.size = this.calculateSize(true, b);
        }
    }
    
    protected void allocateMemory() {
        this.allocateMemory(false);
    }
    
    private void allocateMemory(final boolean b) {
        this.allocateMemory(this.calculateSize(true, b));
    }
    
    protected void allocateMemory(int calculateSize) {
        if (calculateSize == -1) {
            calculateSize = this.calculateSize(false);
        }
        else if (calculateSize <= 0) {
            throw new IllegalArgumentException("Structure size must be greater than zero: " + calculateSize);
        }
        if (calculateSize != -1) {
            if (this.memory == null || this.memory instanceof AutoAllocated) {
                this.memory = this.autoAllocate(calculateSize);
            }
            this.size = calculateSize;
        }
    }
    
    public int size() {
        this.ensureAllocated();
        if (this.size == -1) {
            this.size = this.calculateSize(true);
        }
        return this.size;
    }
    
    public void clear() {
        this.memory.clear(this.size());
    }
    
    public Pointer getPointer() {
        this.ensureAllocated();
        return this.memory;
    }
    
    static Set busy() {
        return Structure.busy.get();
    }
    
    static Map reading() {
        return Structure.reads.get();
    }
    
    public void read() {
        this.ensureAllocated();
        if (busy().contains(this)) {
            return;
        }
        busy().add(this);
        if (this instanceof ByReference) {
            reading().put(this.getPointer(), this);
        }
        final Iterator<StructField> iterator = this.fields().values().iterator();
        while (iterator.hasNext()) {
            this.readField(iterator.next());
        }
        busy().remove(this);
        if (reading().get(this.getPointer()) == this) {
            reading().remove(this.getPointer());
        }
    }
    
    protected int fieldOffset(final String s) {
        this.ensureAllocated();
        final StructField structField = this.fields().get(s);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + s);
        }
        return structField.offset;
    }
    
    public Object readField(final String s) {
        this.ensureAllocated();
        final StructField structField = this.fields().get(s);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + s);
        }
        return this.readField(structField);
    }
    
    Object getField(final StructField structField) {
        return structField.field.get(this);
    }
    
    void setField(final StructField structField, final Object o) {
        this.setField(structField, o, false);
    }
    
    void setField(final StructField structField, final Object o, final boolean b) {
        structField.field.set(this, o);
    }
    
    static Structure updateStructureByReference(final Class clazz, Structure instance, final Pointer pointer) {
        if (pointer == null) {
            instance = null;
        }
        else {
            if (instance == null || !pointer.equals(instance.getPointer())) {
                final Structure structure = reading().get(pointer);
                if (structure != null && clazz.equals(structure.getClass())) {
                    instance = structure;
                }
                else {
                    instance = newInstance(clazz);
                    instance.useMemory(pointer);
                }
            }
            instance.autoRead();
        }
        return instance;
    }
    
    Object readField(final StructField structField) {
        final int offset = structField.offset;
        Class clazz = structField.type;
        final FromNativeConverter readConverter = structField.readConverter;
        if (readConverter != null) {
            clazz = readConverter.nativeType();
        }
        Object o = this.memory.getValue(offset, clazz, (((Structure.class$com$sun$jna$Structure == null) ? (Structure.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Structure.class$com$sun$jna$Structure).isAssignableFrom(clazz) || ((Structure.class$com$sun$jna$Callback == null) ? (Structure.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Structure.class$com$sun$jna$Callback).isAssignableFrom(clazz) || (Platform.HAS_BUFFERS && ((Structure.class$java$nio$Buffer == null) ? (Structure.class$java$nio$Buffer = class$("java.nio.Buffer")) : Structure.class$java$nio$Buffer).isAssignableFrom(clazz)) || ((Structure.class$com$sun$jna$Pointer == null) ? (Structure.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Structure.class$com$sun$jna$Pointer).isAssignableFrom(clazz) || ((Structure.class$com$sun$jna$NativeMapped == null) ? (Structure.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Structure.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz) || clazz.isArray()) ? this.getField(structField) : null);
        if (readConverter != null) {
            o = readConverter.fromNative(o, structField.context);
        }
        this.setField(structField, o, true);
        return o;
    }
    
    public void write() {
        this.ensureAllocated();
        if (this instanceof ByValue) {
            this.getTypeInfo();
        }
        if (busy().contains(this)) {
            return;
        }
        busy().add(this);
        for (final StructField structField : this.fields().values()) {
            if (!structField.isVolatile) {
                this.writeField(structField);
            }
        }
        busy().remove(this);
    }
    
    public void writeField(final String s) {
        this.ensureAllocated();
        final StructField structField = this.fields().get(s);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + s);
        }
        this.writeField(structField);
    }
    
    public void writeField(final String s, final Object o) {
        this.ensureAllocated();
        final StructField structField = this.fields().get(s);
        if (structField == null) {
            throw new IllegalArgumentException("No such field: " + s);
        }
        this.setField(structField, o);
        this.writeField(structField);
    }
    
    void writeField(final StructField structField) {
        if (structField.isReadOnly) {
            return;
        }
        final int offset = structField.offset;
        Object o = this.getField(structField);
        Class clazz = structField.type;
        final ToNativeConverter writeConverter = structField.writeConverter;
        if (writeConverter != null) {
            o = writeConverter.toNative(o, new StructureWriteContext(this, structField.field));
            clazz = writeConverter.nativeType();
        }
        if (((Structure.class$java$lang$String == null) ? (Structure.class$java$lang$String = class$("java.lang.String")) : Structure.class$java$lang$String) == clazz || ((Structure.class$com$sun$jna$WString == null) ? (Structure.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Structure.class$com$sun$jna$WString) == clazz) {
            final boolean b = clazz == ((Structure.class$com$sun$jna$WString == null) ? (Structure.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Structure.class$com$sun$jna$WString);
            if (o != null) {
                final NativeString nativeString = new NativeString(o.toString(), b);
                this.nativeStrings.put(structField.name, nativeString);
                o = nativeString.getPointer();
            }
            else {
                o = null;
                this.nativeStrings.remove(structField.name);
            }
        }
        this.memory.setValue(offset, o, clazz);
    }
    
    private boolean hasFieldOrder() {
        // monitorenter(this)
        // monitorexit(this)
        return this.fieldOrder != null;
    }
    
    protected List getFieldOrder() {
        // monitorenter(this)
        if (this.fieldOrder == null) {
            this.fieldOrder = new ArrayList();
        }
        // monitorexit(this)
        return this.fieldOrder;
    }
    
    protected void setFieldOrder(final String[] array) {
        this.getFieldOrder().addAll(Arrays.asList(array));
        this.size = -1;
        if (this.memory instanceof AutoAllocated) {
            this.memory = null;
        }
    }
    
    protected void sortFields(final List list, final List list2) {
        while (0 < list2.size()) {
            final String s = list2.get(0);
            while (0 < list.size()) {
                if (s.equals(list.get(0).getName())) {
                    Collections.swap(list, 0, 0);
                    break;
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    protected List getFields(final boolean b) {
        final ArrayList list = new ArrayList();
        for (Serializable s = this.getClass(); !s.equals((Structure.class$com$sun$jna$Structure == null) ? (Structure.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Structure.class$com$sun$jna$Structure); s = ((Class<? extends Structure>)s).getSuperclass()) {
            final ArrayList<Field> list2 = new ArrayList<Field>();
            final Field[] declaredFields = ((Class)s).getDeclaredFields();
            while (0 < declaredFields.length) {
                final int modifiers = declaredFields[0].getModifiers();
                if (!Modifier.isStatic(modifiers)) {
                    if (Modifier.isPublic(modifiers)) {
                        list2.add(declaredFields[0]);
                    }
                }
                int n = 0;
                ++n;
            }
            if (Structure.REVERSE_FIELDS) {
                Collections.reverse(list2);
            }
            list.addAll(0, list2);
        }
        if (Structure.REQUIRES_FIELD_ORDER || this.hasFieldOrder()) {
            final List fieldOrder = this.getFieldOrder();
            if (fieldOrder.size() < list.size()) {
                if (b) {
                    throw new Error("This VM does not store fields in a predictable order; you must use Structure.setFieldOrder to explicitly indicate the field order: " + System.getProperty("java.vendor") + ", " + System.getProperty("java.version"));
                }
                return null;
            }
            else {
                this.sortFields(list, fieldOrder);
            }
        }
        return list;
    }
    
    private synchronized boolean fieldOrderMatch(final List list) {
        return this.fieldOrder == list || (this.fieldOrder != null && this.fieldOrder.equals(list));
    }
    
    private int calculateSize(final boolean b) {
        return this.calculateSize(b, false);
    }
    
    int calculateSize(final boolean b, final boolean b2) {
        // monitorenter(layoutInfo = Structure.layoutInfo)
        LayoutInfo deriveLayout = Structure.layoutInfo.get(this.getClass());
        // monitorexit(layoutInfo)
        if (deriveLayout == null || this.alignType != deriveLayout.alignType || this.typeMapper != deriveLayout.typeMapper || !this.fieldOrderMatch(deriveLayout.fieldOrder)) {
            deriveLayout = this.deriveLayout(b, b2);
        }
        if (deriveLayout != null) {
            this.structAlignment = deriveLayout.alignment;
            this.structFields = deriveLayout.fields;
            deriveLayout.alignType = this.alignType;
            deriveLayout.typeMapper = this.typeMapper;
            deriveLayout.fieldOrder = this.fieldOrder;
            if (!deriveLayout.variable) {
                // monitorenter(layoutInfo2 = Structure.layoutInfo)
                Structure.layoutInfo.put(this.getClass(), deriveLayout);
            }
            // monitorexit(layoutInfo2)
            if (false) {
                this.initializeFields();
            }
            return deriveLayout.size;
        }
        return -1;
    }
    
    private LayoutInfo deriveLayout(final boolean b, final boolean b2) {
        final LayoutInfo layoutInfo = new LayoutInfo(null);
        final List fields = this.getFields(b);
        if (fields == null) {
            return null;
        }
        for (final Field field : fields) {
            final int modifiers = field.getModifiers();
            final Class<?> type = field.getType();
            if (type.isArray()) {
                layoutInfo.variable = true;
            }
            final StructField structField = new StructField();
            structField.isVolatile = Modifier.isVolatile(modifiers);
            structField.isReadOnly = Modifier.isFinal(modifiers);
            if (structField.isReadOnly) {
                if (!Platform.RO_FIELDS) {
                    throw new IllegalArgumentException("This VM does not support read-only fields (field '" + field.getName() + "' within " + this.getClass() + ")");
                }
                field.setAccessible(true);
            }
            structField.field = field;
            structField.name = field.getName();
            structField.type = type;
            if (((Structure.class$com$sun$jna$Callback == null) ? (Structure.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Structure.class$com$sun$jna$Callback).isAssignableFrom(type) && !type.isInterface()) {
                throw new IllegalArgumentException("Structure Callback field '" + field.getName() + "' must be an interface");
            }
            if (type.isArray() && ((Structure.class$com$sun$jna$Structure == null) ? (Structure.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Structure.class$com$sun$jna$Structure).equals(type.getComponentType())) {
                throw new IllegalArgumentException("Nested Structure arrays must use a derived Structure type so that the size of the elements can be determined");
            }
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }
            Object o = this.getField(structField);
            if (o == null && type.isArray()) {
                if (b) {
                    throw new IllegalStateException("Array fields must be initialized");
                }
                return null;
            }
            else {
                Class<?> nativeType = type;
                if (((Structure.class$com$sun$jna$NativeMapped == null) ? (Structure.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Structure.class$com$sun$jna$NativeMapped).isAssignableFrom(type)) {
                    final NativeMappedConverter instance = NativeMappedConverter.getInstance(type);
                    nativeType = (Class<?>)instance.nativeType();
                    structField.writeConverter = instance;
                    structField.readConverter = instance;
                    structField.context = new StructureReadContext(this, field);
                }
                else if (this.typeMapper != null) {
                    final ToNativeConverter toNativeConverter = this.typeMapper.getToNativeConverter(type);
                    final FromNativeConverter fromNativeConverter = this.typeMapper.getFromNativeConverter(type);
                    if (toNativeConverter != null && fromNativeConverter != null) {
                        o = toNativeConverter.toNative(o, new StructureWriteContext(this, structField.field));
                        nativeType = ((o != null) ? o.getClass() : ((Structure.class$com$sun$jna$Pointer == null) ? (Structure.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Structure.class$com$sun$jna$Pointer));
                        structField.writeConverter = toNativeConverter;
                        structField.readConverter = fromNativeConverter;
                        structField.context = new StructureReadContext(this, field);
                    }
                    else if (toNativeConverter != null || fromNativeConverter != null) {
                        throw new IllegalArgumentException("Structures require bidirectional type conversion for " + type);
                    }
                }
                if (o == null) {
                    o = this.initializeField(structField, type);
                }
                structField.size = this.getNativeSize(nativeType, o);
                this.getNativeAlignment(nativeType, o, false);
                layoutInfo.alignment = Math.max(layoutInfo.alignment, 1);
                if (false) {}
                structField.offset = 0;
                final int n = 0 + structField.size;
                layoutInfo.fields.put(structField.name, structField);
            }
        }
        if (0 > 0) {
            final int calculateAlignedSize = this.calculateAlignedSize(0, layoutInfo.alignment);
            if (this instanceof ByValue && !b2) {
                this.getTypeInfo();
            }
            if (this.memory != null && !(this.memory instanceof AutoAllocated)) {
                this.memory = this.memory.share(0L, calculateAlignedSize);
            }
            layoutInfo.size = calculateAlignedSize;
            return layoutInfo;
        }
        throw new IllegalArgumentException("Structure " + this.getClass() + " has unknown size (ensure " + "all fields are public)");
    }
    
    private void initializeFields() {
        for (final StructField structField : this.fields().values()) {
            this.initializeField(structField, structField.type);
        }
    }
    
    private Object initializeField(final StructField structField, final Class clazz) {
        Object o = null;
        if (((Structure.class$com$sun$jna$Structure == null) ? (Structure.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Structure.class$com$sun$jna$Structure).isAssignableFrom(clazz) && !((Structure.class$com$sun$jna$Structure$ByReference == null) ? (Structure.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Structure.class$com$sun$jna$Structure$ByReference).isAssignableFrom(clazz)) {
            o = newInstance(clazz);
            this.setField(structField, o);
        }
        else if (((Structure.class$com$sun$jna$NativeMapped == null) ? (Structure.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Structure.class$com$sun$jna$NativeMapped).isAssignableFrom(clazz)) {
            o = NativeMappedConverter.getInstance(clazz).defaultValue();
            this.setField(structField, o);
        }
        return o;
    }
    
    int calculateAlignedSize(final int n) {
        return this.calculateAlignedSize(n, this.structAlignment);
    }
    
    private int calculateAlignedSize(int n, final int n2) {
        if (this.alignType != 1 && n % n2 != 0) {
            n += n2 - n % n2;
        }
        return n;
    }
    
    protected int getStructAlignment() {
        if (this.size == -1) {
            this.calculateSize(true);
        }
        return this.structAlignment;
    }
    
    protected int getNativeAlignment(Class nativeType, Object o, final boolean b) {
        if (((Structure.class$com$sun$jna$NativeMapped == null) ? (Structure.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : Structure.class$com$sun$jna$NativeMapped).isAssignableFrom(nativeType)) {
            final NativeMappedConverter instance = NativeMappedConverter.getInstance(nativeType);
            nativeType = instance.nativeType();
            o = instance.toNative(o, new ToNativeContext());
        }
        Native.getNativeSize(nativeType, o);
        if (nativeType.isPrimitive() && ((Structure.class$java$lang$Long == null) ? (Structure.class$java$lang$Long = class$("java.lang.Long")) : Structure.class$java$lang$Long) != nativeType && ((Structure.class$java$lang$Integer == null) ? (Structure.class$java$lang$Integer = class$("java.lang.Integer")) : Structure.class$java$lang$Integer) != nativeType && ((Structure.class$java$lang$Short == null) ? (Structure.class$java$lang$Short = class$("java.lang.Short")) : Structure.class$java$lang$Short) != nativeType && ((Structure.class$java$lang$Character == null) ? (Structure.class$java$lang$Character = class$("java.lang.Character")) : Structure.class$java$lang$Character) != nativeType && ((Structure.class$java$lang$Byte == null) ? (Structure.class$java$lang$Byte = class$("java.lang.Byte")) : Structure.class$java$lang$Byte) != nativeType && ((Structure.class$java$lang$Boolean == null) ? (Structure.class$java$lang$Boolean = class$("java.lang.Boolean")) : Structure.class$java$lang$Boolean) != nativeType && ((Structure.class$java$lang$Float == null) ? (Structure.class$java$lang$Float = class$("java.lang.Float")) : Structure.class$java$lang$Float) != nativeType && ((Structure.class$java$lang$Double == null) ? (Structure.class$java$lang$Double = class$("java.lang.Double")) : Structure.class$java$lang$Double) != nativeType) {
            if (((Structure.class$com$sun$jna$Pointer == null) ? (Structure.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : Structure.class$com$sun$jna$Pointer) == nativeType || (Platform.HAS_BUFFERS && ((Structure.class$java$nio$Buffer == null) ? (Structure.class$java$nio$Buffer = class$("java.nio.Buffer")) : Structure.class$java$nio$Buffer).isAssignableFrom(nativeType)) || ((Structure.class$com$sun$jna$Callback == null) ? (Structure.class$com$sun$jna$Callback = class$("com.sun.jna.Callback")) : Structure.class$com$sun$jna$Callback).isAssignableFrom(nativeType) || ((Structure.class$com$sun$jna$WString == null) ? (Structure.class$com$sun$jna$WString = class$("com.sun.jna.WString")) : Structure.class$com$sun$jna$WString) == nativeType || ((Structure.class$java$lang$String == null) ? (Structure.class$java$lang$String = class$("java.lang.String")) : Structure.class$java$lang$String) == nativeType) {
                final int size = Pointer.SIZE;
            }
            else if (((Structure.class$com$sun$jna$Structure == null) ? (Structure.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Structure.class$com$sun$jna$Structure).isAssignableFrom(nativeType)) {
                if (((Structure.class$com$sun$jna$Structure$ByReference == null) ? (Structure.class$com$sun$jna$Structure$ByReference = class$("com.sun.jna.Structure$ByReference")) : Structure.class$com$sun$jna$Structure$ByReference).isAssignableFrom(nativeType)) {
                    final int size2 = Pointer.SIZE;
                }
                else {
                    if (o == null) {
                        o = newInstance(nativeType);
                    }
                    ((Structure)o).getStructAlignment();
                }
            }
            else {
                if (!nativeType.isArray()) {
                    throw new IllegalArgumentException("Type " + nativeType + " has unknown " + "native alignment");
                }
                this.getNativeAlignment(nativeType.getComponentType(), null, b);
            }
        }
        if (this.alignType != 1) {
            if (this.alignType == 3) {
                Math.min(8, 1);
            }
            else if (this.alignType == 2 && (!b || !Platform.isMac() || !Structure.isPPC)) {
                Math.min(8, 1);
            }
        }
        return 1;
    }
    
    public String toString() {
        return this.toString(Boolean.getBoolean("jna.dump_memory"));
    }
    
    public String toString(final boolean b) {
        return this.toString(0, true, true);
    }
    
    private String format(final Class clazz) {
        final String name = clazz.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
    
    private String toString(final int n, final boolean b, final boolean b2) {
        this.ensureAllocated();
        final String property = System.getProperty("line.separator");
        String s = this.format(this.getClass()) + "(" + this.getPointer() + ")";
        if (!(this.getPointer() instanceof Memory)) {
            s = s + " (" + this.size() + " bytes)";
        }
        String string = "";
        while (0 < n) {
            string += "  ";
            int n2 = 0;
            ++n2;
        }
        String s2 = property;
        if (!b) {
            s2 = "...}";
        }
        else {
            final Iterator<StructField> iterator = this.fields().values().iterator();
            while (iterator.hasNext()) {
                final StructField structField = iterator.next();
                Object o = this.getField(structField);
                String s3 = this.format(structField.type);
                String string2 = "";
                final String string3 = s2 + string;
                if (structField.type.isArray() && o != null) {
                    s3 = this.format(structField.type.getComponentType());
                    string2 = "[" + Array.getLength(o) + "]";
                }
                final String string4 = string3 + "  " + s3 + " " + structField.name + string2 + "@" + Integer.toHexString(structField.offset);
                if (o instanceof Structure) {
                    o = ((Structure)o).toString(n + 1, !(o instanceof ByReference), b2);
                }
                final String string5 = string4 + "=";
                String s4;
                if (o instanceof Long) {
                    s4 = string5 + Long.toHexString((long)o);
                }
                else if (o instanceof Integer) {
                    s4 = string5 + Integer.toHexString((int)o);
                }
                else if (o instanceof Short) {
                    s4 = string5 + Integer.toHexString((short)o);
                }
                else if (o instanceof Byte) {
                    s4 = string5 + Integer.toHexString((byte)o);
                }
                else {
                    s4 = string5 + String.valueOf(o).trim();
                }
                s2 = s4 + property;
                if (!iterator.hasNext()) {
                    s2 = s2 + string + "}";
                }
            }
        }
        if (n == 0 && b2) {
            String s5 = s2 + property + "memory dump" + property;
            final byte[] byteArray = this.getPointer().getByteArray(0L, this.size());
            while (0 < byteArray.length) {
                if (!false) {
                    s5 += "[";
                }
                if (byteArray[0] >= 0 && byteArray[0] < 16) {
                    s5 += "0";
                }
                s5 += Integer.toHexString(byteArray[0] & 0xFF);
                if (0 == 3 && 0 < byteArray.length - 1) {
                    s5 = s5 + "]" + property;
                }
                int n3 = 0;
                ++n3;
            }
            s2 = s5 + "]";
        }
        return s + " {" + s2;
    }
    
    public Structure[] toArray(final Structure[] array) {
        this.ensureAllocated();
        int n = 0;
        if (this.memory instanceof AutoAllocated) {
            final Memory memory = (Memory)this.memory;
            n = array.length * this.size();
            if (memory.size() < 1) {
                this.useMemory(this.autoAllocate(1));
            }
        }
        array[0] = this;
        final int size = this.size();
        while (1 < array.length) {
            (array[1] = newInstance(this.getClass())).useMemory(this.memory.share(1 * size, size));
            array[1].read();
            ++n;
        }
        if (!(this instanceof ByValue)) {
            this.array = array;
        }
        return array;
    }
    
    public Structure[] toArray(final int n) {
        return this.toArray((Structure[])Array.newInstance(this.getClass(), n));
    }
    
    private Class baseClass() {
        if ((this instanceof ByReference || this instanceof ByValue) && ((Structure.class$com$sun$jna$Structure == null) ? (Structure.class$com$sun$jna$Structure = class$("com.sun.jna.Structure")) : Structure.class$com$sun$jna$Structure).isAssignableFrom(this.getClass().getSuperclass())) {
            return this.getClass().getSuperclass();
        }
        return this.getClass();
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Structure)) {
            return false;
        }
        if (o.getClass() != this.getClass() && ((Structure)o).baseClass() != this.baseClass()) {
            return false;
        }
        final Structure structure = (Structure)o;
        if (structure.getPointer().equals(this.getPointer())) {
            return true;
        }
        if (structure.size() == this.size()) {
            this.clear();
            this.write();
            final byte[] byteArray = this.getPointer().getByteArray(0L, this.size());
            structure.clear();
            structure.write();
            return Arrays.equals(byteArray, structure.getPointer().getByteArray(0L, structure.size()));
        }
        return false;
    }
    
    public int hashCode() {
        this.clear();
        this.write();
        final Adler32 adler32 = new Adler32();
        adler32.update(this.getPointer().getByteArray(0L, this.size()));
        return (int)adler32.getValue();
    }
    
    protected void cacheTypeInfo(final Pointer pointer) {
        this.typeInfo = pointer.peer;
    }
    
    protected Pointer getFieldTypeInfo(final StructField structField) {
        Class clazz = structField.type;
        Object o = this.getField(structField);
        if (this.typeMapper != null) {
            final ToNativeConverter toNativeConverter = this.typeMapper.getToNativeConverter(clazz);
            if (toNativeConverter != null) {
                clazz = toNativeConverter.nativeType();
                o = toNativeConverter.toNative(o, new ToNativeContext());
            }
        }
        return FFIType.access$200(o, clazz);
    }
    
    Pointer getTypeInfo() {
        final Pointer typeInfo = getTypeInfo(this);
        this.cacheTypeInfo(typeInfo);
        return typeInfo;
    }
    
    public void setAutoSynch(final boolean b) {
        this.setAutoRead(b);
        this.setAutoWrite(b);
    }
    
    public void setAutoRead(final boolean autoRead) {
        this.autoRead = autoRead;
    }
    
    public boolean getAutoRead() {
        return this.autoRead;
    }
    
    public void setAutoWrite(final boolean autoWrite) {
        this.autoWrite = autoWrite;
    }
    
    public boolean getAutoWrite() {
        return this.autoWrite;
    }
    
    static Pointer getTypeInfo(final Object o) {
        return FFIType.get(o);
    }
    
    public static Structure newInstance(final Class clazz) throws IllegalArgumentException {
        final Structure structure = clazz.newInstance();
        if (structure instanceof ByValue) {
            structure.allocateMemory();
        }
        return structure;
    }
    
    private static void structureArrayCheck(final Structure[] array) {
        final Pointer pointer = array[0].getPointer();
        final int size = array[0].size();
        while (1 < array.length) {
            if (array[1].getPointer().peer != pointer.peer + size * 1) {
                throw new IllegalArgumentException("Structure array elements must use contiguous memory (bad backing address at Structure array index " + 1 + ")");
            }
            int n = 0;
            ++n;
        }
    }
    
    public static void autoRead(final Structure[] array) {
        structureArrayCheck(array);
        if (array[0].array == array) {
            array[0].autoRead();
        }
        else {
            while (0 < array.length) {
                array[0].autoRead();
                int n = 0;
                ++n;
            }
        }
    }
    
    public void autoRead() {
        if (this.getAutoRead()) {
            this.read();
            if (this.array != null) {
                while (1 < this.array.length) {
                    this.array[1].autoRead();
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    public static void autoWrite(final Structure[] array) {
        structureArrayCheck(array);
        if (array[0].array == array) {
            array[0].autoWrite();
        }
        else {
            while (0 < array.length) {
                array[0].autoWrite();
                int n = 0;
                ++n;
            }
        }
    }
    
    public void autoWrite() {
        if (this.getAutoWrite()) {
            this.write();
            if (this.array != null) {
                while (1 < this.array.length) {
                    this.array[1].autoWrite();
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    protected int getNativeSize(final Class clazz, final Object o) {
        return Native.getNativeSize(clazz, o);
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static void access$1300(final Structure structure, final boolean b) {
        structure.ensureAllocated(b);
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifnonnull       19
        //     6: ldc_w           "com.sun.jna.Structure$MemberOrder"
        //     9: invokestatic    com/sun/jna/Structure.class$:(Ljava/lang/String;)Ljava/lang/Class;
        //    12: dup            
        //    13: putstatic       com/sun/jna/Structure.class$com$sun$jna$Structure$MemberOrder:Ljava/lang/Class;
        //    16: goto            22
        //    19: getstatic       com/sun/jna/Structure.class$com$sun$jna$Structure$MemberOrder:Ljava/lang/Class;
        //    22: invokevirtual   java/lang/Class.getFields:()[Ljava/lang/reflect/Field;
        //    25: astore_0       
        //    26: new             Ljava/util/ArrayList;
        //    29: dup            
        //    30: invokespecial   java/util/ArrayList.<init>:()V
        //    33: astore_1       
        //    34: iconst_0       
        //    35: aload_0        
        //    36: arraylength    
        //    37: if_icmpge       59
        //    40: aload_1        
        //    41: aload_0        
        //    42: iconst_0       
        //    43: aaload         
        //    44: invokevirtual   java/lang/reflect/Field.getName:()Ljava/lang/String;
        //    47: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    52: pop            
        //    53: iinc            2, 1
        //    56: goto            34
        //    59: invokestatic    com/sun/jna/Structure$MemberOrder.access$000:()[Ljava/lang/String;
        //    62: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    65: astore_2       
        //    66: new             Ljava/util/ArrayList;
        //    69: dup            
        //    70: aload_2        
        //    71: invokespecial   java/util/ArrayList.<init>:(Ljava/util/Collection;)V
        //    74: astore_3       
        //    75: aload_3        
        //    76: invokestatic    java/util/Collections.reverse:(Ljava/util/List;)V
        //    79: aload_1        
        //    80: aload_3        
        //    81: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //    84: putstatic       com/sun/jna/Structure.REVERSE_FIELDS:Z
        //    87: aload_1        
        //    88: aload_2        
        //    89: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //    92: ifne            105
        //    95: getstatic       com/sun/jna/Structure.REVERSE_FIELDS:Z
        //    98: ifne            105
        //   101: iconst_1       
        //   102: goto            106
        //   105: iconst_0       
        //   106: putstatic       com/sun/jna/Structure.REQUIRES_FIELD_ORDER:Z
        //   109: ldc_w           "os.arch"
        //   112: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //   115: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   118: astore          4
        //   120: ldc_w           "ppc"
        //   123: aload           4
        //   125: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   128: ifne            142
        //   131: ldc_w           "powerpc"
        //   134: aload           4
        //   136: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   139: ifeq            146
        //   142: iconst_1       
        //   143: goto            147
        //   146: iconst_0       
        //   147: putstatic       com/sun/jna/Structure.isPPC:Z
        //   150: ldc_w           "sparc"
        //   153: aload           4
        //   155: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   158: putstatic       com/sun/jna/Structure.isSPARC:Z
        //   161: ldc_w           "arm"
        //   164: aload           4
        //   166: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   169: putstatic       com/sun/jna/Structure.isARM:Z
        //   172: getstatic       com/sun/jna/Structure.isSPARC:Z
        //   175: ifne            196
        //   178: getstatic       com/sun/jna/Structure.isPPC:Z
        //   181: ifne            190
        //   184: getstatic       com/sun/jna/Structure.isARM:Z
        //   187: ifeq            199
        //   190: invokestatic    com/sun/jna/Platform.isLinux:()Z
        //   193: ifeq            199
        //   196: goto            202
        //   199: getstatic       com/sun/jna/Native.LONG_SIZE:I
        //   202: new             Ljava/util/WeakHashMap;
        //   205: dup            
        //   206: invokespecial   java/util/WeakHashMap.<init>:()V
        //   209: putstatic       com/sun/jna/Structure.layoutInfo:Ljava/util/Map;
        //   212: new             Lcom/sun/jna/Structure$1;
        //   215: dup            
        //   216: invokespecial   com/sun/jna/Structure$1.<init>:()V
        //   219: putstatic       com/sun/jna/Structure.reads:Ljava/lang/ThreadLocal;
        //   222: new             Lcom/sun/jna/Structure$2;
        //   225: dup            
        //   226: invokespecial   com/sun/jna/Structure$2.<init>:()V
        //   229: putstatic       com/sun/jna/Structure.busy:Ljava/lang/ThreadLocal;
        //   232: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0202 (coming from #0199).
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
    
    private class AutoAllocated extends Memory
    {
        private final Structure this$0;
        
        public AutoAllocated(final Structure this$0, final int n) {
            this.this$0 = this$0;
            super(n);
            super.clear();
        }
    }
    
    static class FFIType extends Structure
    {
        private static Map typeInfoMap;
        private static final int FFI_TYPE_STRUCT = 13;
        public size_t size;
        public short alignment;
        public short type;
        public Pointer elements;
        
        private FFIType(final Structure structure) {
            this.type = 13;
            Structure.access$1300(structure, true);
            Pointer[] array;
            if (structure instanceof Union) {
                final StructField biggestField = ((Union)structure).biggestField;
                array = new Pointer[] { get(structure.getField(biggestField), biggestField.type), null };
            }
            else {
                array = new Pointer[structure.fields().size() + 1];
                for (final StructField structField : structure.fields().values()) {
                    final Pointer[] array2 = array;
                    final int n = 0;
                    int n2 = 0;
                    ++n2;
                    array2[n] = structure.getFieldTypeInfo(structField);
                }
            }
            this.init(array);
        }
        
        private FFIType(final Object o, final Class clazz) {
            this.type = 13;
            final int length = Array.getLength(o);
            final Pointer[] array = new Pointer[length + 1];
            final Pointer value = get(null, clazz.getComponentType());
            while (0 < length) {
                array[0] = value;
                int n = 0;
                ++n;
            }
            this.init(array);
        }
        
        private void init(final Pointer[] array) {
            (this.elements = new Memory(Pointer.SIZE * array.length)).write(0L, array, 0, array.length);
            this.write();
        }
        
        static Pointer get(final Object o) {
            if (o == null) {
                return FFITypes.access$1200();
            }
            if (o instanceof Class) {
                return get(null, (Class)o);
            }
            return get(o, o.getClass());
        }
        
        private static Pointer get(Object instance, Class nativeType) {
            final TypeMapper typeMapper = Native.getTypeMapper(nativeType);
            if (typeMapper != null) {
                final ToNativeConverter toNativeConverter = typeMapper.getToNativeConverter(nativeType);
                if (toNativeConverter != null) {
                    nativeType = toNativeConverter.nativeType();
                }
            }
            // monitorenter(typeInfoMap = FFIType.typeInfoMap)
            final Object value = FFIType.typeInfoMap.get(nativeType);
            if (value instanceof Pointer) {
                // monitorexit(typeInfoMap)
                return (Pointer)value;
            }
            if (value instanceof FFIType) {
                // monitorexit(typeInfoMap)
                return ((FFIType)value).getPointer();
            }
            if ((Platform.HAS_BUFFERS && ((Structure.class$java$nio$Buffer == null) ? (Structure.class$java$nio$Buffer = Structure.class$("java.nio.Buffer")) : Structure.class$java$nio$Buffer).isAssignableFrom(nativeType)) || ((Structure.class$com$sun$jna$Callback == null) ? (Structure.class$com$sun$jna$Callback = Structure.class$("com.sun.jna.Callback")) : Structure.class$com$sun$jna$Callback).isAssignableFrom(nativeType)) {
                FFIType.typeInfoMap.put(nativeType, FFITypes.access$1200());
                // monitorexit(typeInfoMap)
                return FFITypes.access$1200();
            }
            if (((Structure.class$com$sun$jna$Structure == null) ? (Structure.class$com$sun$jna$Structure = Structure.class$("com.sun.jna.Structure")) : Structure.class$com$sun$jna$Structure).isAssignableFrom(nativeType)) {
                if (instance == null) {
                    instance = Structure.newInstance(nativeType);
                }
                if (((Structure.class$com$sun$jna$Structure$ByReference == null) ? (Structure.class$com$sun$jna$Structure$ByReference = Structure.class$("com.sun.jna.Structure$ByReference")) : Structure.class$com$sun$jna$Structure$ByReference).isAssignableFrom(nativeType)) {
                    FFIType.typeInfoMap.put(nativeType, FFITypes.access$1200());
                    // monitorexit(typeInfoMap)
                    return FFITypes.access$1200();
                }
                final FFIType ffiType = new FFIType((Structure)instance);
                FFIType.typeInfoMap.put(nativeType, ffiType);
                // monitorexit(typeInfoMap)
                return ffiType.getPointer();
            }
            else {
                if (((Structure.class$com$sun$jna$NativeMapped == null) ? (Structure.class$com$sun$jna$NativeMapped = Structure.class$("com.sun.jna.NativeMapped")) : Structure.class$com$sun$jna$NativeMapped).isAssignableFrom(nativeType)) {
                    final NativeMappedConverter instance2 = NativeMappedConverter.getInstance(nativeType);
                    // monitorexit(typeInfoMap)
                    return get(instance2.toNative(instance, new ToNativeContext()), instance2.nativeType());
                }
                if (nativeType.isArray()) {
                    final FFIType ffiType2 = new FFIType(instance, nativeType);
                    FFIType.typeInfoMap.put(instance, ffiType2);
                    // monitorexit(typeInfoMap)
                    return ffiType2.getPointer();
                }
                throw new IllegalArgumentException("Unsupported Structure field type " + nativeType);
            }
        }
        
        static Pointer access$200(final Object o, final Class clazz) {
            return get(o, clazz);
        }
        
        static {
            FFIType.typeInfoMap = new WeakHashMap();
            if (Native.POINTER_SIZE == 0) {
                throw new Error("Native library not initialized");
            }
            if (FFITypes.access$300() == null) {
                throw new Error("FFI types not initialized");
            }
            FFIType.typeInfoMap.put(Void.TYPE, FFITypes.access$300());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Void == null) ? (Structure.class$java$lang$Void = Structure.class$("java.lang.Void")) : Structure.class$java$lang$Void, FFITypes.access$300());
            FFIType.typeInfoMap.put(Float.TYPE, FFITypes.access$400());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Float == null) ? (Structure.class$java$lang$Float = Structure.class$("java.lang.Float")) : Structure.class$java$lang$Float, FFITypes.access$400());
            FFIType.typeInfoMap.put(Double.TYPE, FFITypes.access$500());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Double == null) ? (Structure.class$java$lang$Double = Structure.class$("java.lang.Double")) : Structure.class$java$lang$Double, FFITypes.access$500());
            FFIType.typeInfoMap.put(Long.TYPE, FFITypes.access$600());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Long == null) ? (Structure.class$java$lang$Long = Structure.class$("java.lang.Long")) : Structure.class$java$lang$Long, FFITypes.access$600());
            FFIType.typeInfoMap.put(Integer.TYPE, FFITypes.access$700());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Integer == null) ? (Structure.class$java$lang$Integer = Structure.class$("java.lang.Integer")) : Structure.class$java$lang$Integer, FFITypes.access$700());
            FFIType.typeInfoMap.put(Short.TYPE, FFITypes.access$800());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Short == null) ? (Structure.class$java$lang$Short = Structure.class$("java.lang.Short")) : Structure.class$java$lang$Short, FFITypes.access$800());
            final Pointer pointer = (Native.WCHAR_SIZE == 2) ? FFITypes.access$900() : FFITypes.access$1000();
            FFIType.typeInfoMap.put(Character.TYPE, pointer);
            FFIType.typeInfoMap.put((Structure.class$java$lang$Character == null) ? (Structure.class$java$lang$Character = Structure.class$("java.lang.Character")) : Structure.class$java$lang$Character, pointer);
            FFIType.typeInfoMap.put(Byte.TYPE, FFITypes.access$1100());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Byte == null) ? (Structure.class$java$lang$Byte = Structure.class$("java.lang.Byte")) : Structure.class$java$lang$Byte, FFITypes.access$1100());
            FFIType.typeInfoMap.put((Structure.class$com$sun$jna$Pointer == null) ? (Structure.class$com$sun$jna$Pointer = Structure.class$("com.sun.jna.Pointer")) : Structure.class$com$sun$jna$Pointer, FFITypes.access$1200());
            FFIType.typeInfoMap.put((Structure.class$java$lang$String == null) ? (Structure.class$java$lang$String = Structure.class$("java.lang.String")) : Structure.class$java$lang$String, FFITypes.access$1200());
            FFIType.typeInfoMap.put((Structure.class$com$sun$jna$WString == null) ? (Structure.class$com$sun$jna$WString = Structure.class$("com.sun.jna.WString")) : Structure.class$com$sun$jna$WString, FFITypes.access$1200());
            FFIType.typeInfoMap.put(Boolean.TYPE, FFITypes.access$1000());
            FFIType.typeInfoMap.put((Structure.class$java$lang$Boolean == null) ? (Structure.class$java$lang$Boolean = Structure.class$("java.lang.Boolean")) : Structure.class$java$lang$Boolean, FFITypes.access$1000());
        }
        
        private static class FFITypes
        {
            private static Pointer ffi_type_void;
            private static Pointer ffi_type_float;
            private static Pointer ffi_type_double;
            private static Pointer ffi_type_longdouble;
            private static Pointer ffi_type_uint8;
            private static Pointer ffi_type_sint8;
            private static Pointer ffi_type_uint16;
            private static Pointer ffi_type_sint16;
            private static Pointer ffi_type_uint32;
            private static Pointer ffi_type_sint32;
            private static Pointer ffi_type_uint64;
            private static Pointer ffi_type_sint64;
            private static Pointer ffi_type_pointer;
            
            static Pointer access$300() {
                return FFITypes.ffi_type_void;
            }
            
            static Pointer access$400() {
                return FFITypes.ffi_type_float;
            }
            
            static Pointer access$500() {
                return FFITypes.ffi_type_double;
            }
            
            static Pointer access$600() {
                return FFITypes.ffi_type_sint64;
            }
            
            static Pointer access$700() {
                return FFITypes.ffi_type_sint32;
            }
            
            static Pointer access$800() {
                return FFITypes.ffi_type_sint16;
            }
            
            static Pointer access$900() {
                return FFITypes.ffi_type_uint16;
            }
            
            static Pointer access$1000() {
                return FFITypes.ffi_type_uint32;
            }
            
            static Pointer access$1100() {
                return FFITypes.ffi_type_sint8;
            }
            
            static Pointer access$1200() {
                return FFITypes.ffi_type_pointer;
            }
        }
        
        public static class size_t extends IntegerType
        {
            public size_t() {
                this(0L);
            }
            
            public size_t(final long n) {
                super(Native.POINTER_SIZE, n);
            }
        }
    }
    
    class StructField
    {
        public String name;
        public Class type;
        public Field field;
        public int size;
        public int offset;
        public boolean isVolatile;
        public boolean isReadOnly;
        public FromNativeConverter readConverter;
        public ToNativeConverter writeConverter;
        public FromNativeContext context;
        private final Structure this$0;
        
        StructField(final Structure this$0) {
            this.this$0 = this$0;
            this.size = -1;
            this.offset = -1;
        }
        
        public String toString() {
            return this.name + "@" + this.offset + "[" + this.size + "] (" + this.type + ")";
        }
    }
    
    private class LayoutInfo
    {
        int size;
        int alignment;
        Map fields;
        int alignType;
        TypeMapper typeMapper;
        List fieldOrder;
        boolean variable;
        private final Structure this$0;
        
        private LayoutInfo(final Structure this$0) {
            this.this$0 = this$0;
            this.size = -1;
            this.alignment = 1;
            this.fields = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
            this.alignType = 0;
        }
        
        LayoutInfo(final Structure structure, final Structure$1 threadLocal) {
            this(structure);
        }
    }
    
    private static class MemberOrder
    {
        public int first;
        public int second;
        public int middle;
        public int penultimate;
        public int last;
        
        static String[] access$000() {
            return MemberOrder.FIELDS;
        }
        
        static {
            MemberOrder.FIELDS = new String[] { "first", "second", "middle", "penultimate", "last" };
        }
    }
    
    public interface ByReference
    {
    }
    
    public interface ByValue
    {
    }
}
