package io.netty.util.internal.chmv8;

import java.util.concurrent.*;
import sun.misc.*;
import java.io.*;
import io.netty.util.internal.*;
import java.util.concurrent.atomic.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class ConcurrentHashMapV8 implements ConcurrentMap, Serializable
{
    private static final long serialVersionUID = 7249069246763182397L;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final int DEFAULT_CAPACITY = 16;
    static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final float LOAD_FACTOR = 0.75f;
    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final int MIN_TREEIFY_CAPACITY = 64;
    private static final int MIN_TRANSFER_STRIDE = 16;
    static final int MOVED = -1;
    static final int TREEBIN = -2;
    static final int RESERVED = -3;
    static final int HASH_BITS = Integer.MAX_VALUE;
    static final int NCPU;
    private static final ObjectStreamField[] serialPersistentFields;
    transient Node[] table;
    private transient Node[] nextTable;
    private transient long baseCount;
    private transient int sizeCtl;
    private transient int transferIndex;
    private transient int transferOrigin;
    private transient int cellsBusy;
    private transient CounterCell[] counterCells;
    private transient KeySetView keySet;
    private transient ValuesView values;
    private transient EntrySetView entrySet;
    static final AtomicInteger counterHashCodeGenerator;
    static final int SEED_INCREMENT = 1640531527;
    private static final Unsafe U;
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    private static final long TRANSFERORIGIN;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;
    private static final long CELLVALUE;
    private static final long ABASE;
    private static final int ASHIFT;
    
    static final int spread(final int n) {
        return (n ^ n >>> 16) & Integer.MAX_VALUE;
    }
    
    private static final int tableSizeFor(final int n) {
        final int n2 = n - 1;
        final int n3 = n2 | n2 >>> 1;
        final int n4 = n3 | n3 >>> 2;
        final int n5 = n4 | n4 >>> 4;
        final int n6 = n5 | n5 >>> 8;
        final int n7 = n6 | n6 >>> 16;
        return (n7 < 0) ? 1 : ((n7 >= 1073741824) ? 1073741824 : (n7 + 1));
    }
    
    static Class comparableClassFor(final Object o) {
        if (o instanceof Comparable) {
            final Class<?> class1;
            if ((class1 = o.getClass()) == String.class) {
                return class1;
            }
            final Type[] genericInterfaces;
            if ((genericInterfaces = class1.getGenericInterfaces()) != null) {
                while (0 < genericInterfaces.length) {
                    final Type type;
                    final ParameterizedType parameterizedType;
                    final Type[] actualTypeArguments;
                    if ((type = genericInterfaces[0]) instanceof ParameterizedType && (parameterizedType = (ParameterizedType)type).getRawType() == Comparable.class && (actualTypeArguments = parameterizedType.getActualTypeArguments()) != null && actualTypeArguments.length == 1 && actualTypeArguments[0] == class1) {
                        return class1;
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
        return null;
    }
    
    static int compareComparables(final Class clazz, final Object o, final Object o2) {
        return (o2 == null || o2.getClass() != clazz) ? 0 : ((Comparable)o).compareTo(o2);
    }
    
    static final Node tabAt(final Node[] array, final int n) {
        return (Node)ConcurrentHashMapV8.U.getObjectVolatile(array, ((long)n << ConcurrentHashMapV8.ASHIFT) + ConcurrentHashMapV8.ABASE);
    }
    
    static final boolean casTabAt(final Node[] array, final int n, final Node node, final Node node2) {
        return ConcurrentHashMapV8.U.compareAndSwapObject(array, ((long)n << ConcurrentHashMapV8.ASHIFT) + ConcurrentHashMapV8.ABASE, node, node2);
    }
    
    static final void setTabAt(final Node[] array, final int n, final Node node) {
        ConcurrentHashMapV8.U.putObjectVolatile(array, ((long)n << ConcurrentHashMapV8.ASHIFT) + ConcurrentHashMapV8.ABASE, node);
    }
    
    public ConcurrentHashMapV8() {
    }
    
    public ConcurrentHashMapV8(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.sizeCtl = ((n >= 536870912) ? 1073741824 : tableSizeFor(n + (n >>> 1) + 1));
    }
    
    public ConcurrentHashMapV8(final Map map) {
        this.sizeCtl = 16;
        this.putAll(map);
    }
    
    public ConcurrentHashMapV8(final int n, final float n2) {
        this(n, n2, 1);
    }
    
    public ConcurrentHashMapV8(int n, final float n2, final int n3) {
        if (n2 <= 0.0f || n < 0 || n3 <= 0) {
            throw new IllegalArgumentException();
        }
        if (n < n3) {
            n = n3;
        }
        final long n4 = (long)(1.0 + n / n2);
        this.sizeCtl = ((n4 >= 1073741824L) ? 1073741824 : tableSizeFor((int)n4));
    }
    
    @Override
    public int size() {
        final long sumCount = this.sumCount();
        return (sumCount < 0L) ? 0 : ((sumCount > 2147483647L) ? Integer.MAX_VALUE : ((int)sumCount));
    }
    
    @Override
    public boolean isEmpty() {
        return this.sumCount() <= 0L;
    }
    
    @Override
    public Object get(final Object o) {
        final int spread = spread(o.hashCode());
        final Node[] table;
        final int length;
        Node node;
        if ((table = this.table) != null && (length = table.length) > 0 && (node = tabAt(table, length - 1 & spread)) != null) {
            final int hash;
            if ((hash = node.hash) == spread) {
                final Object key;
                if ((key = node.key) == o || (key != null && o.equals(key))) {
                    return node.val;
                }
            }
            else if (hash < 0) {
                final Node find;
                return ((find = node.find(spread, o)) != null) ? find.val : null;
            }
            while ((node = node.next) != null) {
                final Object key2;
                if (node.hash == spread && ((key2 = node.key) == o || (key2 != null && o.equals(key2)))) {
                    return node.val;
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.get(o) != null;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        final Node[] table;
        if ((table = this.table) != null) {
            Node advance;
            while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                final Object val;
                if ((val = advance.val) == o || (val != null && o.equals(val))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.putVal(o, o2, false);
    }
    
    final Object putVal(final Object o, final Object o2, final boolean b) {
        if (o == null || o2 == null) {
            throw new NullPointerException();
        }
        final int spread = spread(o.hashCode());
        Node[] array = this.table;
        while (true) {
            final int length;
            if (array == null || (length = array.length) == 0) {
                array = this.initTable();
            }
            else {
                final int n;
                final Node tab;
                if ((tab = tabAt(array, n = (length - 1 & spread))) == null) {
                    if (casTabAt(array, n, null, new Node(spread, o, o2, null))) {
                        break;
                    }
                    continue;
                }
                else {
                    final int hash;
                    if ((hash = tab.hash) == -1) {
                        array = this.helpTransfer(array, tab);
                    }
                    else {
                        Object o3 = null;
                        // monitorenter(node = tab)
                        Label_0299: {
                            if (tabAt(array, n) == tab) {
                                if (hash >= 0) {
                                    Node next = tab;
                                    Object key;
                                    while (next.hash != spread || ((key = next.key) != o && (key == null || !o.equals(key)))) {
                                        final Node node2 = next;
                                        if ((next = next.next) == null) {
                                            node2.next = new Node(spread, o, o2, null);
                                            break Label_0299;
                                        }
                                        int n2 = 0;
                                        ++n2;
                                    }
                                    o3 = next.val;
                                    if (!b) {
                                        next.val = o2;
                                    }
                                }
                                else {
                                    final TreeNode putTreeVal;
                                    if (tab instanceof TreeBin && (putTreeVal = ((TreeBin)tab).putTreeVal(spread, o, o2)) != null) {
                                        o3 = putTreeVal.val;
                                        if (!b) {
                                            putTreeVal.val = o2;
                                        }
                                    }
                                }
                            }
                        }
                        // monitorexit(node)
                        if (2 == 0) {
                            continue;
                        }
                        if (2 >= 8) {
                            this.treeifyBin(array, n);
                        }
                        if (o3 != null) {
                            return o3;
                        }
                        break;
                    }
                }
            }
        }
        this.addCount(1L, 2);
        return null;
    }
    
    @Override
    public void putAll(final Map map) {
        this.tryPresize(map.size());
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.putVal(entry.getKey(), entry.getValue(), false);
        }
    }
    
    @Override
    public Object remove(final Object o) {
        return this.replaceNode(o, null, null);
    }
    
    final Object replaceNode(final Object o, final Object o2, final Object o3) {
        final int spread = spread(o.hashCode());
        Node[] array = this.table;
        int length;
        while (array != null && (length = array.length) != 0) {
            final int n;
            final Node tab;
            if ((tab = tabAt(array, n = (length - 1 & spread))) == null) {
                break;
            }
            final int hash;
            if ((hash = tab.hash) == -1) {
                array = this.helpTransfer(array, tab);
            }
            else {
                Object o4 = null;
                // monitorenter(node = tab)
                Label_0360: {
                    if (tabAt(array, n) == tab) {
                        if (hash >= 0) {
                            Node next = tab;
                            Node node2 = null;
                            Object key;
                            while (next.hash != spread || ((key = next.key) != o && (key == null || !o.equals(key)))) {
                                node2 = next;
                                if ((next = next.next) == null) {
                                    break Label_0360;
                                }
                            }
                            final Object val = next.val;
                            if (o3 == null || o3 == val || (val != null && o3.equals(val))) {
                                o4 = val;
                                if (o2 != null) {
                                    next.val = o2;
                                }
                                else if (node2 != null) {
                                    node2.next = next.next;
                                }
                                else {
                                    setTabAt(array, n, next.next);
                                }
                            }
                        }
                        else if (tab instanceof TreeBin) {
                            final TreeBin treeBin = (TreeBin)tab;
                            final TreeNode root;
                            final TreeNode treeNode;
                            if ((root = treeBin.root) != null && (treeNode = root.findTreeNode(spread, o, null)) != null) {
                                final Object val2 = treeNode.val;
                                if (o3 == null || o3 == val2 || (val2 != null && o3.equals(val2))) {
                                    o4 = val2;
                                    if (o2 != null) {
                                        treeNode.val = o2;
                                    }
                                    else if (treeBin.removeTreeNode(treeNode)) {
                                        setTabAt(array, n, untreeify(treeBin.first));
                                    }
                                }
                            }
                        }
                    }
                }
                // monitorexit(node)
                if (!true) {
                    continue;
                }
                if (o4 != null) {
                    if (o2 == null) {
                        this.addCount(-1L, -1);
                    }
                    return o4;
                }
                break;
            }
        }
        return null;
    }
    
    @Override
    public void clear() {
        long n = 0L;
        Node[] array = this.table;
        while (array != null && 0 < array.length) {
            final Node tab = tabAt(array, 0);
            if (tab == null) {
                int n2 = 0;
                ++n2;
            }
            else {
                final int hash;
                if ((hash = tab.hash) == -1) {
                    array = this.helpTransfer(array, tab);
                }
                else if (tabAt(array, 0) == tab) {
                    for (Node next = (hash >= 0) ? tab : ((tab instanceof TreeBin) ? ((TreeBin)tab).first : null); next != null; next = next.next) {
                        --n;
                    }
                    final Node[] array2 = array;
                    final int n3 = 0;
                    int n2 = 0;
                    ++n2;
                    setTabAt(array2, n3, null);
                }
            }
        }
        if (n != 0L) {
            this.addCount(n, -1);
        }
    }
    
    @Override
    public KeySetView keySet() {
        final KeySetView keySet;
        return ((keySet = this.keySet) != null) ? keySet : (this.keySet = new KeySetView(this, null));
    }
    
    @Override
    public Collection values() {
        final ValuesView values;
        return ((values = this.values) != null) ? values : (this.values = new ValuesView(this));
    }
    
    @Override
    public Set entrySet() {
        final EntrySetView entrySet;
        return ((entrySet = this.entrySet) != null) ? entrySet : (this.entrySet = new EntrySetView(this));
    }
    
    @Override
    public int hashCode() {
        final Node[] table;
        if ((table = this.table) != null) {
            Node advance;
            while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                final int n = 0 + (advance.key.hashCode() ^ advance.val.hashCode());
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        final Node[] table;
        final int n = ((table = this.table) == null) ? 0 : table.length;
        final Traverser traverser = new Traverser(table, n, 0, n);
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        Node node;
        if ((node = traverser.advance()) != null) {
            while (true) {
                final Object key = node.key;
                final Object val = node.val;
                sb.append((key == this) ? "(this Map)" : key);
                sb.append('=');
                sb.append((val == this) ? "(this Map)" : val);
                if ((node = traverser.advance()) == null) {
                    break;
                }
                sb.append(',').append(' ');
            }
        }
        return sb.append('}').toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != this) {
            if (!(o instanceof Map)) {
                return false;
            }
            final Map map = (Map)o;
            final Node[] table;
            final int n = ((table = this.table) == null) ? 0 : table.length;
            Node advance;
            while ((advance = new Traverser(table, n, 0, n).advance()) != null) {
                final Object val = advance.val;
                final Object value = map.get(advance.key);
                if (value == null || (value != val && !value.equals(val))) {
                    return false;
                }
            }
            for (final Map.Entry<K, Object> entry : map.entrySet()) {
                final K key;
                final Object value2;
                final Object value3;
                if ((key = entry.getKey()) == null || (value2 = entry.getValue()) == null || (value3 = this.get(key)) == null || (value2 != value3 && !value2.equals(value3))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        while (1 < 16) {
            int n = 0;
            ++n;
        }
        final Segment[] array = new Segment[16];
        while (0 < array.length) {
            array[0] = new Segment(0.75f);
            int n2 = 0;
            ++n2;
        }
        objectOutputStream.putFields().put("segments", array);
        objectOutputStream.putFields().put("segmentShift", 32);
        objectOutputStream.putFields().put("segmentMask", 0);
        objectOutputStream.writeFields();
        final Node[] table;
        if ((table = this.table) != null) {
            Node advance;
            while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                objectOutputStream.writeObject(advance.key);
                objectOutputStream.writeObject(advance.val);
            }
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.writeObject(null);
    }
    
    private void readObject(final ObjectInputStream p0) throws IOException, ClassNotFoundException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_m1      
        //     2: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.sizeCtl:I
        //     5: aload_1        
        //     6: invokevirtual   java/io/ObjectInputStream.defaultReadObject:()V
        //     9: lconst_0       
        //    10: lstore_2       
        //    11: aconst_null    
        //    12: astore          4
        //    14: aload_1        
        //    15: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //    18: astore          5
        //    20: aload_1        
        //    21: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //    24: astore          6
        //    26: aload           5
        //    28: ifnull          66
        //    31: aload           6
        //    33: ifnull          66
        //    36: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //    39: dup            
        //    40: aload           5
        //    42: invokevirtual   java/lang/Object.hashCode:()I
        //    45: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.spread:(I)I
        //    48: aload           5
        //    50: aload           6
        //    52: aload           4
        //    54: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.<init>:(ILjava/lang/Object;Ljava/lang/Object;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //    57: astore          4
        //    59: lload_2        
        //    60: lconst_1       
        //    61: ladd           
        //    62: lstore_2       
        //    63: goto            14
        //    66: lload_2        
        //    67: lconst_0       
        //    68: lcmp           
        //    69: ifne            80
        //    72: aload_0        
        //    73: iconst_0       
        //    74: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.sizeCtl:I
        //    77: goto            447
        //    80: lload_2        
        //    81: ldc2_w          536870912
        //    84: lcmp           
        //    85: iflt            91
        //    88: goto            109
        //    91: lload_2        
        //    92: l2i            
        //    93: istore          6
        //    95: iload           6
        //    97: iload           6
        //    99: iconst_1       
        //   100: iushr          
        //   101: iadd           
        //   102: iconst_1       
        //   103: iadd           
        //   104: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.tableSizeFor:(I)I
        //   107: istore          5
        //   109: ldc             1073741824
        //   111: anewarray       Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   114: checkcast       [Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   117: astore          6
        //   119: lconst_0       
        //   120: lstore          8
        //   122: aload           4
        //   124: ifnull          428
        //   127: aload           4
        //   129: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   132: astore          11
        //   134: aload           4
        //   136: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   139: istore          13
        //   141: iload           13
        //   143: ldc_w           1073741823
        //   146: iand           
        //   147: istore          14
        //   149: aload           6
        //   151: iload           14
        //   153: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.tabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;I)Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   156: dup            
        //   157: astore          12
        //   159: ifnonnull       165
        //   162: goto            395
        //   165: aload           4
        //   167: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.key:Ljava/lang/Object;
        //   170: astore          15
        //   172: aload           12
        //   174: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   177: ifge            213
        //   180: aload           12
        //   182: checkcast       Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin;
        //   185: astore          16
        //   187: aload           16
        //   189: iload           13
        //   191: aload           15
        //   193: aload           4
        //   195: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.val:Ljava/lang/Object;
        //   198: invokevirtual   io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin.putTreeVal:(ILjava/lang/Object;Ljava/lang/Object;)Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;
        //   201: ifnonnull       210
        //   204: lload           8
        //   206: lconst_1       
        //   207: ladd           
        //   208: lstore          8
        //   210: goto            395
        //   213: aload           12
        //   215: astore          17
        //   217: aload           17
        //   219: ifnull          276
        //   222: aload           17
        //   224: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   227: iload           13
        //   229: if_icmpne       263
        //   232: aload           17
        //   234: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.key:Ljava/lang/Object;
        //   237: dup            
        //   238: astore          18
        //   240: aload           15
        //   242: if_acmpeq       260
        //   245: aload           18
        //   247: ifnull          263
        //   250: aload           15
        //   252: aload           18
        //   254: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   257: ifeq            263
        //   260: goto            276
        //   263: iinc            16, 1
        //   266: aload           17
        //   268: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   271: astore          17
        //   273: goto            217
        //   276: iconst_0       
        //   277: ifeq            395
        //   280: iconst_0       
        //   281: bipush          8
        //   283: if_icmplt       395
        //   286: lload           8
        //   288: lconst_1       
        //   289: ladd           
        //   290: lstore          8
        //   292: aload           4
        //   294: aload           12
        //   296: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   299: aconst_null    
        //   300: astore          19
        //   302: aconst_null    
        //   303: astore          20
        //   305: aload           4
        //   307: astore          17
        //   309: aload           17
        //   311: ifnull          379
        //   314: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;
        //   317: dup            
        //   318: aload           17
        //   320: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   323: aload           17
        //   325: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.key:Ljava/lang/Object;
        //   328: aload           17
        //   330: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.val:Ljava/lang/Object;
        //   333: aconst_null    
        //   334: aconst_null    
        //   335: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.<init>:(ILjava/lang/Object;Ljava/lang/Object;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;)V
        //   338: astore          21
        //   340: aload           21
        //   342: aload           20
        //   344: dup_x1         
        //   345: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.prev:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;
        //   348: ifnonnull       358
        //   351: aload           21
        //   353: astore          19
        //   355: goto            365
        //   358: aload           20
        //   360: aload           21
        //   362: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   365: aload           21
        //   367: astore          20
        //   369: aload           17
        //   371: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   374: astore          17
        //   376: goto            309
        //   379: aload           6
        //   381: iload           14
        //   383: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin;
        //   386: dup            
        //   387: aload           19
        //   389: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin.<init>:(Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;)V
        //   392: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   395: iconst_0       
        //   396: ifeq            421
        //   399: lload           8
        //   401: lconst_1       
        //   402: ladd           
        //   403: lstore          8
        //   405: aload           4
        //   407: aload           12
        //   409: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   412: aload           6
        //   414: iload           14
        //   416: aload           4
        //   418: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   421: aload           11
        //   423: astore          4
        //   425: goto            122
        //   428: aload_0        
        //   429: aload           6
        //   431: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.table:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   434: aload_0        
        //   435: ldc_w           805306368
        //   438: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.sizeCtl:I
        //   441: aload_0        
        //   442: lload           8
        //   444: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.baseCount:J
        //   447: return         
        //    Exceptions:
        //  throws java.io.IOException
        //  throws java.lang.ClassNotFoundException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public Object putIfAbsent(final Object o, final Object o2) {
        return this.putVal(o, o2, true);
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        if (o == null) {
            throw new NullPointerException();
        }
        return o2 != null && this.replaceNode(o, null, o2) != null;
    }
    
    @Override
    public boolean replace(final Object o, final Object o2, final Object o3) {
        if (o == null || o2 == null || o3 == null) {
            throw new NullPointerException();
        }
        return this.replaceNode(o, o3, o2) != null;
    }
    
    @Override
    public Object replace(final Object o, final Object o2) {
        if (o == null || o2 == null) {
            throw new NullPointerException();
        }
        return this.replaceNode(o, o2, null);
    }
    
    @Override
    public Object getOrDefault(final Object o, final Object o2) {
        final Object value;
        return ((value = this.get(o)) == null) ? o2 : value;
    }
    
    public void forEach(final BiAction biAction) {
        if (biAction == null) {
            throw new NullPointerException();
        }
        final Node[] table;
        if ((table = this.table) != null) {
            Node advance;
            while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                biAction.apply(advance.key, advance.val);
            }
        }
    }
    
    public void replaceAll(final BiFun biFun) {
        if (biFun == null) {
            throw new NullPointerException();
        }
        final Node[] table;
        if ((table = this.table) != null) {
            Node advance;
            while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                Object o = advance.val;
                final Object key = advance.key;
                Object apply;
                do {
                    apply = biFun.apply(key, o);
                    if (apply == null) {
                        throw new NullPointerException();
                    }
                } while (this.replaceNode(key, apply, o) == null && (o = this.get(key)) != null);
            }
        }
    }
    
    public Object computeIfAbsent(final Object o, final Fun fun) {
        if (o == null || fun == null) {
            throw new NullPointerException();
        }
        final int spread = spread(o.hashCode());
        Object o2 = null;
        Node[] array = this.table;
        while (true) {
            final int length;
            if (array == null || (length = array.length) == 0) {
                array = this.initTable();
            }
            else {
                final int n;
                final Node tab;
                if ((tab = tabAt(array, n = (length - 1 & spread))) == null) {
                    final ReservationNode reservationNode = new ReservationNode();
                    // monitorenter(reservationNode2 = reservationNode)
                    if (casTabAt(array, n, null, reservationNode)) {
                        Node node = null;
                        if ((o2 = fun.apply(o)) != null) {
                            node = new Node(spread, o, o2, null);
                        }
                        setTabAt(array, n, node);
                    }
                    // monitorexit(reservationNode2)
                    if (2 != 0) {
                        break;
                    }
                    continue;
                }
                else {
                    final int hash;
                    if ((hash = tab.hash) == -1) {
                        array = this.helpTransfer(array, tab);
                    }
                    else {
                        // monitorenter(node2 = tab)
                        Label_0411: {
                            if (tabAt(array, n) == tab) {
                                if (hash >= 0) {
                                    Node next = tab;
                                    Object key;
                                    while (next.hash != spread || ((key = next.key) != o && (key == null || !o.equals(key)))) {
                                        final Node node3 = next;
                                        if ((next = next.next) == null) {
                                            if ((o2 = fun.apply(o)) != null) {
                                                node3.next = new Node(spread, o, o2, null);
                                            }
                                            break Label_0411;
                                        }
                                        int n2 = 0;
                                        ++n2;
                                    }
                                    o2 = next.val;
                                }
                                else if (tab instanceof TreeBin) {
                                    final TreeBin treeBin = (TreeBin)tab;
                                    final TreeNode root;
                                    final TreeNode treeNode;
                                    if ((root = treeBin.root) != null && (treeNode = root.findTreeNode(spread, o, null)) != null) {
                                        o2 = treeNode.val;
                                    }
                                    else if ((o2 = fun.apply(o)) != null) {
                                        treeBin.putTreeVal(spread, o, o2);
                                    }
                                }
                            }
                        }
                        // monitorexit(node2)
                        if (2 == 0) {
                            continue;
                        }
                        if (2 >= 8) {
                            this.treeifyBin(array, n);
                        }
                        if (!true) {
                            return o2;
                        }
                        break;
                    }
                }
            }
        }
        if (o2 != null) {
            this.addCount(1L, 2);
        }
        return o2;
    }
    
    public Object computeIfPresent(final Object o, final BiFun biFun) {
        if (o == null || biFun == null) {
            throw new NullPointerException();
        }
        final int spread = spread(o.hashCode());
        Object o2 = null;
        Node[] array = this.table;
        while (true) {
            final int length;
            if (array == null || (length = array.length) == 0) {
                array = this.initTable();
            }
            else {
                final int n;
                final Node tab;
                if ((tab = tabAt(array, n = (length - 1 & spread))) == null) {
                    break;
                }
                final int hash;
                if ((hash = tab.hash) == -1) {
                    array = this.helpTransfer(array, tab);
                }
                else {
                    // monitorenter(node = tab)
                    Label_0353: {
                        if (tabAt(array, n) == tab) {
                            if (hash >= 0) {
                                Node next = tab;
                                Node node2 = null;
                                Object key;
                                while (next.hash != spread || ((key = next.key) != o && (key == null || !o.equals(key)))) {
                                    node2 = next;
                                    if ((next = next.next) == null) {
                                        break Label_0353;
                                    }
                                    int n2 = 0;
                                    ++n2;
                                }
                                o2 = biFun.apply(o, next.val);
                                if (o2 != null) {
                                    next.val = o2;
                                }
                                else {
                                    final Node next2 = next.next;
                                    if (node2 != null) {
                                        node2.next = next2;
                                    }
                                    else {
                                        setTabAt(array, n, next2);
                                    }
                                }
                            }
                            else if (tab instanceof TreeBin) {
                                final TreeBin treeBin = (TreeBin)tab;
                                final TreeNode root;
                                final TreeNode treeNode;
                                if ((root = treeBin.root) != null && (treeNode = root.findTreeNode(spread, o, null)) != null) {
                                    o2 = biFun.apply(o, treeNode.val);
                                    if (o2 != null) {
                                        treeNode.val = o2;
                                    }
                                    else if (treeBin.removeTreeNode(treeNode)) {
                                        setTabAt(array, n, untreeify(treeBin.first));
                                    }
                                }
                            }
                        }
                    }
                    // monitorexit(node)
                    if (2 != 0) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (-1 != 0) {
            this.addCount(-1, 2);
        }
        return o2;
    }
    
    public Object compute(final Object o, final BiFun biFun) {
        if (o == null || biFun == null) {
            throw new NullPointerException();
        }
        final int spread = spread(o.hashCode());
        Object o2 = null;
        Node[] array = this.table;
        while (true) {
            final int length;
            if (array == null || (length = array.length) == 0) {
                array = this.initTable();
            }
            else {
                final int n;
                final Node tab;
                if ((tab = tabAt(array, n = (length - 1 & spread))) == null) {
                    final ReservationNode reservationNode = new ReservationNode();
                    // monitorenter(reservationNode2 = reservationNode)
                    if (casTabAt(array, n, null, reservationNode)) {
                        Node node = null;
                        if ((o2 = biFun.apply(o, null)) != null) {
                            node = new Node(spread, o, o2, null);
                        }
                        setTabAt(array, n, node);
                    }
                    // monitorexit(reservationNode2)
                    if (true) {
                        break;
                    }
                    continue;
                }
                else {
                    final int hash;
                    if ((hash = tab.hash) == -1) {
                        array = this.helpTransfer(array, tab);
                    }
                    else {
                        // monitorenter(node2 = tab)
                        Label_0529: {
                            if (tabAt(array, n) == tab) {
                                if (hash >= 0) {
                                    Node next = tab;
                                    Node node3 = null;
                                    Object key;
                                    while (next.hash != spread || ((key = next.key) != o && (key == null || !o.equals(key)))) {
                                        node3 = next;
                                        if ((next = next.next) == null) {
                                            o2 = biFun.apply(o, null);
                                            if (o2 != null) {
                                                node3.next = new Node(spread, o, o2, null);
                                            }
                                            break Label_0529;
                                        }
                                        int n2 = 0;
                                        ++n2;
                                    }
                                    o2 = biFun.apply(o, next.val);
                                    if (o2 != null) {
                                        next.val = o2;
                                    }
                                    else {
                                        final Node next2 = next.next;
                                        if (node3 != null) {
                                            node3.next = next2;
                                        }
                                        else {
                                            setTabAt(array, n, next2);
                                        }
                                    }
                                }
                                else if (tab instanceof TreeBin) {
                                    final TreeBin treeBin = (TreeBin)tab;
                                    final TreeNode root;
                                    TreeNode treeNode;
                                    if ((root = treeBin.root) != null) {
                                        treeNode = root.findTreeNode(spread, o, null);
                                    }
                                    else {
                                        treeNode = null;
                                    }
                                    o2 = biFun.apply(o, (treeNode == null) ? null : treeNode.val);
                                    if (o2 != null) {
                                        if (treeNode != null) {
                                            treeNode.val = o2;
                                        }
                                        else {
                                            treeBin.putTreeVal(spread, o, o2);
                                        }
                                    }
                                    else if (treeNode != null && treeBin.removeTreeNode(treeNode)) {
                                        setTabAt(array, n, untreeify(treeBin.first));
                                    }
                                }
                            }
                        }
                        // monitorexit(node2)
                        if (!true) {
                            continue;
                        }
                        if (1 >= 8) {
                            this.treeifyBin(array, n);
                            break;
                        }
                        break;
                    }
                }
            }
        }
        if (-1 != 0) {
            this.addCount(-1, 1);
        }
        return o2;
    }
    
    public Object merge(final Object o, final Object o2, final BiFun biFun) {
        if (o == null || o2 == null || biFun == null) {
            throw new NullPointerException();
        }
        final int spread = spread(o.hashCode());
        Object apply = null;
        Node[] array = this.table;
        while (true) {
            final int length;
            if (array == null || (length = array.length) == 0) {
                array = this.initTable();
            }
            else {
                final int n;
                final Node tab;
                if ((tab = tabAt(array, n = (length - 1 & spread))) == null) {
                    if (casTabAt(array, n, null, new Node(spread, o, o2, null))) {
                        apply = o2;
                        break;
                    }
                    continue;
                }
                else {
                    final int hash;
                    if ((hash = tab.hash) == -1) {
                        array = this.helpTransfer(array, tab);
                    }
                    else {
                        // monitorenter(node = tab)
                        Label_0442: {
                            if (tabAt(array, n) == tab) {
                                if (hash >= 0) {
                                    Node next = tab;
                                    Node node2 = null;
                                    Object key;
                                    while (next.hash != spread || ((key = next.key) != o && (key == null || !o.equals(key)))) {
                                        node2 = next;
                                        if ((next = next.next) == null) {
                                            apply = o2;
                                            node2.next = new Node(spread, o, apply, null);
                                            break Label_0442;
                                        }
                                        int n2 = 0;
                                        ++n2;
                                    }
                                    apply = biFun.apply(next.val, o2);
                                    if (apply != null) {
                                        next.val = apply;
                                    }
                                    else {
                                        final Node next2 = next.next;
                                        if (node2 != null) {
                                            node2.next = next2;
                                        }
                                        else {
                                            setTabAt(array, n, next2);
                                        }
                                    }
                                }
                                else if (tab instanceof TreeBin) {
                                    final TreeBin treeBin = (TreeBin)tab;
                                    final TreeNode root = treeBin.root;
                                    final TreeNode treeNode = (root == null) ? null : root.findTreeNode(spread, o, null);
                                    apply = ((treeNode == null) ? o2 : biFun.apply(treeNode.val, o2));
                                    if (apply != null) {
                                        if (treeNode != null) {
                                            treeNode.val = apply;
                                        }
                                        else {
                                            treeBin.putTreeVal(spread, o, apply);
                                        }
                                    }
                                    else if (treeNode != null && treeBin.removeTreeNode(treeNode)) {
                                        setTabAt(array, n, untreeify(treeBin.first));
                                    }
                                }
                            }
                        }
                        // monitorexit(node)
                        if (2 == 0) {
                            continue;
                        }
                        if (2 >= 8) {
                            this.treeifyBin(array, n);
                            break;
                        }
                        break;
                    }
                }
            }
        }
        if (-1 != 0) {
            this.addCount(-1, 2);
        }
        return apply;
    }
    
    @Deprecated
    public boolean contains(final Object o) {
        return this.containsValue(o);
    }
    
    public Enumeration keys() {
        final Node[] table;
        final int n = ((table = this.table) == null) ? 0 : table.length;
        return new KeyIterator(table, n, 0, n, this);
    }
    
    public Enumeration elements() {
        final Node[] table;
        final int n = ((table = this.table) == null) ? 0 : table.length;
        return new ValueIterator(table, n, 0, n, this);
    }
    
    public long mappingCount() {
        final long sumCount = this.sumCount();
        return (sumCount < 0L) ? 0L : sumCount;
    }
    
    public static KeySetView newKeySet() {
        return new KeySetView(new ConcurrentHashMapV8(), Boolean.TRUE);
    }
    
    public static KeySetView newKeySet(final int n) {
        return new KeySetView(new ConcurrentHashMapV8(n), Boolean.TRUE);
    }
    
    public KeySetView keySet(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        return new KeySetView(this, o);
    }
    
    private final Node[] initTable() {
        Node[] array;
        while ((array = this.table) == null || array.length == 0) {
            int sizeCtl;
            if ((sizeCtl = this.sizeCtl) < 0) {
                Thread.yield();
            }
            else {
                if (ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sizeCtl, -1)) {
                    if ((array = this.table) == null || array.length == 0) {
                        final int n = (sizeCtl > 0) ? sizeCtl : 16;
                        array = (this.table = new Node[n]);
                        sizeCtl = n - (n >>> 2);
                    }
                    this.sizeCtl = sizeCtl;
                    break;
                }
                continue;
            }
        }
        return array;
    }
    
    private final void addCount(final long n, final int n2) {
        long n3 = 0L;
        Label_0138: {
            final CounterCell[] counterCells;
            if ((counterCells = this.counterCells) == null) {
                final Unsafe u = ConcurrentHashMapV8.U;
                final long basecount = ConcurrentHashMapV8.BASECOUNT;
                final long baseCount = this.baseCount;
                if (u.compareAndSwapLong(this, basecount, baseCount, n3 = baseCount + n)) {
                    break Label_0138;
                }
            }
            final InternalThreadLocalMap value = InternalThreadLocalMap.get();
            final IntegerHolder counterHashCode;
            final int n4;
            final CounterCell counterCell;
            if ((counterHashCode = value.counterHashCode()) != null && counterCells != null && (n4 = counterCells.length - 1) >= 0 && (counterCell = counterCells[n4 & counterHashCode.value]) != null) {
                final Unsafe u2 = ConcurrentHashMapV8.U;
                final CounterCell counterCell2 = counterCell;
                final long cellvalue = ConcurrentHashMapV8.CELLVALUE;
                final long value2 = counterCell.value;
                final boolean compareAndSwapLong;
                if (compareAndSwapLong = u2.compareAndSwapLong(counterCell2, cellvalue, value2, value2 + n)) {
                    if (n2 <= 1) {
                        return;
                    }
                    n3 = this.sumCount();
                    break Label_0138;
                }
            }
            this.fullAddCount(value, n, counterHashCode, true);
            return;
        }
        if (n2 >= 0) {
            int sizeCtl;
            Node[] table;
            while (n3 >= (sizeCtl = this.sizeCtl) && (table = this.table) != null && table.length < 1073741824) {
                if (sizeCtl < 0) {
                    if (sizeCtl == -1 || this.transferIndex <= this.transferOrigin) {
                        break;
                    }
                    final Node[] nextTable;
                    if ((nextTable = this.nextTable) == null) {
                        break;
                    }
                    if (ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sizeCtl, sizeCtl - 1)) {
                        this.transfer(table, nextTable);
                    }
                }
                else if (ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sizeCtl, -2)) {
                    this.transfer(table, null);
                }
                n3 = this.sumCount();
            }
        }
    }
    
    final Node[] helpTransfer(final Node[] array, final Node node) {
        final Node[] nextTable;
        if (node instanceof ForwardingNode && (nextTable = ((ForwardingNode)node).nextTable) != null) {
            final int sizeCtl;
            if (nextTable == this.nextTable && array == this.table && this.transferIndex > this.transferOrigin && (sizeCtl = this.sizeCtl) < -1 && ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sizeCtl, sizeCtl - 1)) {
                this.transfer(array, nextTable);
            }
            return nextTable;
        }
        return this.table;
    }
    
    private final void tryPresize(final int n) {
        final int n2 = (n >= 536870912) ? 1073741824 : tableSizeFor(n + (n >>> 1) + 1);
        int sizeCtl;
        while ((sizeCtl = this.sizeCtl) >= 0) {
            final Node[] table = this.table;
            final int length;
            if (table == null || (length = table.length) == 0) {
                final int n3 = (sizeCtl > n2) ? sizeCtl : n2;
                if (!ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sizeCtl, -1)) {
                    continue;
                }
                if (this.table == table) {
                    this.table = new Node[n3];
                    sizeCtl = n3 - (n3 >>> 2);
                }
                this.sizeCtl = sizeCtl;
            }
            else {
                if (n2 <= sizeCtl) {
                    break;
                }
                if (length >= 1073741824) {
                    break;
                }
                if (table != this.table || !ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sizeCtl, -2)) {
                    continue;
                }
                this.transfer(table, null);
            }
        }
    }
    
    private final void transfer(final Node[] p0, final Node[] p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: arraylength    
        //     2: istore_3       
        //     3: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.NCPU:I
        //     6: iconst_1       
        //     7: if_icmple       20
        //    10: iload_3        
        //    11: iconst_3       
        //    12: iushr          
        //    13: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.NCPU:I
        //    16: idiv           
        //    17: goto            21
        //    20: iload_3        
        //    21: dup            
        //    22: istore          4
        //    24: bipush          16
        //    26: if_icmpge       29
        //    29: aload_2        
        //    30: ifnonnull       168
        //    33: iload_3        
        //    34: iconst_1       
        //    35: ishl           
        //    36: anewarray       Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //    39: checkcast       [Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //    42: astore          5
        //    44: aload           5
        //    46: astore_2       
        //    47: goto            59
        //    50: astore          5
        //    52: aload_0        
        //    53: ldc             2147483647
        //    55: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.sizeCtl:I
        //    58: return         
        //    59: aload_0        
        //    60: aload_2        
        //    61: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.nextTable:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //    64: aload_0        
        //    65: iload_3        
        //    66: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.transferOrigin:I
        //    69: aload_0        
        //    70: iload_3        
        //    71: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.transferIndex:I
        //    74: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$ForwardingNode;
        //    77: dup            
        //    78: aload_1        
        //    79: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$ForwardingNode.<init>:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //    82: astore          5
        //    84: iload_3        
        //    85: istore          6
        //    87: iload           6
        //    89: ifle            168
        //    92: iload           6
        //    94: bipush          16
        //    96: if_icmple       107
        //    99: iload           6
        //   101: bipush          16
        //   103: isub           
        //   104: goto            108
        //   107: iconst_0       
        //   108: istore          7
        //   110: iconst_0       
        //   111: iload           6
        //   113: if_icmpge       127
        //   116: aload_2        
        //   117: iconst_0       
        //   118: aload           5
        //   120: aastore        
        //   121: iinc            8, 1
        //   124: goto            110
        //   127: iload_3        
        //   128: iconst_1       
        //   129: iadd           
        //   130: istore          8
        //   132: iconst_0       
        //   133: iload_3        
        //   134: iload           6
        //   136: iadd           
        //   137: if_icmpge       151
        //   140: aload_2        
        //   141: iconst_0       
        //   142: aload           5
        //   144: aastore        
        //   145: iinc            8, 1
        //   148: goto            132
        //   151: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   154: aload_0        
        //   155: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.TRANSFERORIGIN:J
        //   158: iconst_1       
        //   159: iconst_1       
        //   160: istore          6
        //   162: invokevirtual   sun/misc/Unsafe.putOrderedInt:(Ljava/lang/Object;JI)V
        //   165: goto            87
        //   168: aload_2        
        //   169: arraylength    
        //   170: istore          5
        //   172: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$ForwardingNode;
        //   175: dup            
        //   176: aload_2        
        //   177: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$ForwardingNode.<init>:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   180: astore          6
        //   182: iconst_1       
        //   183: ifeq            265
        //   186: iinc            9, -1
        //   189: iconst_m1      
        //   190: iconst_0       
        //   191: if_icmpge       198
        //   194: iconst_0       
        //   195: ifeq            201
        //   198: goto            182
        //   201: aload_0        
        //   202: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.transferIndex:I
        //   205: dup            
        //   206: istore          11
        //   208: aload_0        
        //   209: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.transferOrigin:I
        //   212: if_icmpgt       218
        //   215: goto            182
        //   218: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   221: aload_0        
        //   222: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.TRANSFERINDEX:J
        //   225: iload           11
        //   227: iload           11
        //   229: bipush          16
        //   231: if_icmple       242
        //   234: iload           11
        //   236: bipush          16
        //   238: isub           
        //   239: goto            243
        //   242: iconst_0       
        //   243: dup            
        //   244: istore          12
        //   246: invokevirtual   sun/misc/Unsafe.compareAndSwapInt:(Ljava/lang/Object;JII)Z
        //   249: ifeq            182
        //   252: iload           12
        //   254: istore          10
        //   256: iload           11
        //   258: iconst_1       
        //   259: isub           
        //   260: istore          9
        //   262: goto            182
        //   265: iconst_m1      
        //   266: iflt            282
        //   269: iconst_m1      
        //   270: iload_3        
        //   271: if_icmpge       282
        //   274: iconst_m1      
        //   275: iload_3        
        //   276: iadd           
        //   277: iload           5
        //   279: if_icmplt       352
        //   282: iconst_0       
        //   283: ifeq            308
        //   286: aload_0        
        //   287: aconst_null    
        //   288: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.nextTable:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   291: aload_0        
        //   292: aload_2        
        //   293: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.table:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   296: aload_0        
        //   297: iload_3        
        //   298: iconst_1       
        //   299: ishl           
        //   300: iload_3        
        //   301: iconst_1       
        //   302: iushr          
        //   303: isub           
        //   304: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.sizeCtl:I
        //   307: return         
        //   308: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   311: aload_0        
        //   312: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.SIZECTL:J
        //   315: aload_0        
        //   316: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.sizeCtl:I
        //   319: dup            
        //   320: istore          15
        //   322: iinc            15, 1
        //   325: iload           15
        //   327: invokevirtual   sun/misc/Unsafe.compareAndSwapInt:(Ljava/lang/Object;JII)Z
        //   330: ifeq            308
        //   333: iload           15
        //   335: iconst_m1      
        //   336: if_icmpeq       340
        //   339: return         
        //   340: iconst_1       
        //   341: iconst_1       
        //   342: istore          7
        //   344: istore          8
        //   346: iload_3        
        //   347: istore          9
        //   349: goto            875
        //   352: aload_1        
        //   353: iconst_m1      
        //   354: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.tabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;I)Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   357: dup            
        //   358: astore          14
        //   360: ifnonnull       391
        //   363: aload_1        
        //   364: iconst_m1      
        //   365: aconst_null    
        //   366: aload           6
        //   368: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.casTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)Z
        //   371: ifeq            875
        //   374: aload_2        
        //   375: iconst_m1      
        //   376: aconst_null    
        //   377: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   380: aload_2        
        //   381: iconst_m1      
        //   382: iload_3        
        //   383: iadd           
        //   384: aconst_null    
        //   385: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   388: goto            875
        //   391: aload           14
        //   393: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   396: dup            
        //   397: istore          13
        //   399: iconst_m1      
        //   400: if_icmpne       406
        //   403: goto            875
        //   406: aload           14
        //   408: dup            
        //   409: astore          15
        //   411: monitorenter   
        //   412: aload_1        
        //   413: iconst_m1      
        //   414: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.tabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;I)Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   417: aload           14
        //   419: if_acmpne       861
        //   422: iload           13
        //   424: iflt            617
        //   427: iload           13
        //   429: iload_3        
        //   430: iand           
        //   431: istore          18
        //   433: aload           14
        //   435: astore          19
        //   437: aload           14
        //   439: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   442: astore          20
        //   444: aload           20
        //   446: ifnull          483
        //   449: aload           20
        //   451: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   454: iload_3        
        //   455: iand           
        //   456: istore          21
        //   458: iload           21
        //   460: iload           18
        //   462: if_icmpeq       473
        //   465: iload           21
        //   467: istore          18
        //   469: aload           20
        //   471: astore          19
        //   473: aload           20
        //   475: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   478: astore          20
        //   480: goto            444
        //   483: iload           18
        //   485: ifne            498
        //   488: aload           19
        //   490: astore          16
        //   492: aconst_null    
        //   493: astore          17
        //   495: goto            505
        //   498: aload           19
        //   500: astore          17
        //   502: aconst_null    
        //   503: astore          16
        //   505: aload           14
        //   507: astore          20
        //   509: aload           20
        //   511: aload           19
        //   513: if_acmpeq       591
        //   516: aload           20
        //   518: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   521: istore          21
        //   523: aload           20
        //   525: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.key:Ljava/lang/Object;
        //   528: astore          22
        //   530: aload           20
        //   532: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.val:Ljava/lang/Object;
        //   535: astore          23
        //   537: iload           21
        //   539: iload_3        
        //   540: iand           
        //   541: ifne            564
        //   544: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   547: dup            
        //   548: iload           21
        //   550: aload           22
        //   552: aload           23
        //   554: aload           16
        //   556: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.<init>:(ILjava/lang/Object;Ljava/lang/Object;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   559: astore          16
        //   561: goto            581
        //   564: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   567: dup            
        //   568: iload           21
        //   570: aload           22
        //   572: aload           23
        //   574: aload           17
        //   576: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.<init>:(ILjava/lang/Object;Ljava/lang/Object;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   579: astore          17
        //   581: aload           20
        //   583: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   586: astore          20
        //   588: goto            509
        //   591: aload_2        
        //   592: iconst_m1      
        //   593: aload           16
        //   595: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   598: aload_2        
        //   599: iconst_m1      
        //   600: iload_3        
        //   601: iadd           
        //   602: aload           17
        //   604: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   607: aload_1        
        //   608: iconst_m1      
        //   609: aload           6
        //   611: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   614: goto            861
        //   617: aload           14
        //   619: instanceof      Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin;
        //   622: ifeq            861
        //   625: aload           14
        //   627: checkcast       Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin;
        //   630: astore          18
        //   632: aconst_null    
        //   633: astore          19
        //   635: aconst_null    
        //   636: astore          20
        //   638: aconst_null    
        //   639: astore          21
        //   641: aconst_null    
        //   642: astore          22
        //   644: aload           18
        //   646: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin.first:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;
        //   649: astore          25
        //   651: aload           25
        //   653: ifnull          770
        //   656: aload           25
        //   658: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.hash:I
        //   661: istore          26
        //   663: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;
        //   666: dup            
        //   667: iload           26
        //   669: aload           25
        //   671: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.key:Ljava/lang/Object;
        //   674: aload           25
        //   676: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.val:Ljava/lang/Object;
        //   679: aconst_null    
        //   680: aconst_null    
        //   681: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.<init>:(ILjava/lang/Object;Ljava/lang/Object;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;)V
        //   684: astore          27
        //   686: iload           26
        //   688: iload_3        
        //   689: iand           
        //   690: ifne            728
        //   693: aload           27
        //   695: aload           20
        //   697: dup_x1         
        //   698: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.prev:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;
        //   701: ifnonnull       711
        //   704: aload           27
        //   706: astore          19
        //   708: goto            718
        //   711: aload           20
        //   713: aload           27
        //   715: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   718: aload           27
        //   720: astore          20
        //   722: iinc            23, 1
        //   725: goto            760
        //   728: aload           27
        //   730: aload           22
        //   732: dup_x1         
        //   733: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.prev:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;
        //   736: ifnonnull       746
        //   739: aload           27
        //   741: astore          21
        //   743: goto            753
        //   746: aload           22
        //   748: aload           27
        //   750: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   753: aload           27
        //   755: astore          22
        //   757: iinc            24, 1
        //   760: aload           25
        //   762: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$Node.next:Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   765: astore          25
        //   767: goto            651
        //   770: iconst_0       
        //   771: bipush          6
        //   773: if_icmpgt       784
        //   776: aload           19
        //   778: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.untreeify:(Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   781: goto            802
        //   784: iconst_0       
        //   785: ifeq            800
        //   788: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin;
        //   791: dup            
        //   792: aload           19
        //   794: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin.<init>:(Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;)V
        //   797: goto            802
        //   800: aload           18
        //   802: astore          16
        //   804: iconst_0       
        //   805: bipush          6
        //   807: if_icmpgt       818
        //   810: aload           21
        //   812: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.untreeify:(Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;
        //   815: goto            836
        //   818: iconst_0       
        //   819: ifeq            834
        //   822: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin;
        //   825: dup            
        //   826: aload           21
        //   828: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeBin.<init>:(Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$TreeNode;)V
        //   831: goto            836
        //   834: aload           18
        //   836: astore          17
        //   838: aload_2        
        //   839: iconst_m1      
        //   840: aload           16
        //   842: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   845: aload_2        
        //   846: iconst_m1      
        //   847: iload_3        
        //   848: iadd           
        //   849: aload           17
        //   851: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   854: aload_1        
        //   855: iconst_m1      
        //   856: aload           6
        //   858: invokestatic    io/netty/util/internal/chmv8/ConcurrentHashMapV8.setTabAt:([Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;ILio/netty/util/internal/chmv8/ConcurrentHashMapV8$Node;)V
        //   861: aload           15
        //   863: monitorexit    
        //   864: goto            875
        //   867: astore          28
        //   869: aload           15
        //   871: monitorexit    
        //   872: aload           28
        //   874: athrow         
        //   875: goto            182
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private final void treeifyBin(final Node[] array, final int n) {
        if (array != null) {
            if (array.length < 64) {
                final int sizeCtl;
                if (array == this.table && (sizeCtl = this.sizeCtl) >= 0 && ConcurrentHashMapV8.U.compareAndSwapInt(this, ConcurrentHashMapV8.SIZECTL, sizeCtl, -2)) {
                    this.transfer(array, null);
                }
            }
            else {
                final Node tab;
                if ((tab = tabAt(array, n)) != null && tab.hash >= 0) {
                    // monitorenter(node = tab)
                    if (tabAt(array, n) == tab) {
                        TreeNode treeNode = null;
                        TreeNode prev = null;
                        for (Node next = tab; next != null; next = next.next) {
                            final TreeNode next2 = new TreeNode(next.hash, next.key, next.val, null, null);
                            if ((next2.prev = prev) == null) {
                                treeNode = next2;
                            }
                            else {
                                prev.next = next2;
                            }
                            prev = next2;
                        }
                        setTabAt(array, n, new TreeBin(treeNode));
                    }
                }
                // monitorexit(node)
            }
        }
    }
    
    static Node untreeify(final Node node) {
        Node node2 = null;
        Node node3 = null;
        for (Node next = node; next != null; next = next.next) {
            final Node next2 = new Node(next.hash, next.key, next.val, null);
            if (node3 == null) {
                node2 = next2;
            }
            else {
                node3.next = next2;
            }
            node3 = next2;
        }
        return node2;
    }
    
    final int batchFor(final long n) {
        final long sumCount;
        if (n == Long.MAX_VALUE || (sumCount = this.sumCount()) <= 1L || sumCount < n) {
            return 0;
        }
        final int n2 = ForkJoinPool.getCommonPoolParallelism() << 2;
        final long n3;
        return (n <= 0L || (n3 = sumCount / n) >= n2) ? n2 : ((int)n3);
    }
    
    public void forEach(final long n, final BiAction biAction) {
        if (biAction == null) {
            throw new NullPointerException();
        }
        new ForEachMappingTask(null, this.batchFor(n), 0, 0, this.table, biAction).invoke();
    }
    
    public void forEach(final long n, final BiFun biFun, final Action action) {
        if (biFun == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedMappingTask(null, this.batchFor(n), 0, 0, this.table, biFun, action).invoke();
    }
    
    public Object search(final long n, final BiFun biFun) {
        if (biFun == null) {
            throw new NullPointerException();
        }
        return new SearchMappingsTask(null, this.batchFor(n), 0, 0, this.table, biFun, new AtomicReference()).invoke();
    }
    
    public Object reduce(final long n, final BiFun biFun, final BiFun biFun2) {
        if (biFun == null || biFun2 == null) {
            throw new NullPointerException();
        }
        return new MapReduceMappingsTask(null, this.batchFor(n), 0, 0, this.table, null, biFun, biFun2).invoke();
    }
    
    public double reduceToDouble(final long n, final ObjectByObjectToDouble objectByObjectToDouble, final double n2, final DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectByObjectToDouble == null || doubleByDoubleToDouble == null) {
            throw new NullPointerException();
        }
        return (double)new MapReduceMappingsToDoubleTask(null, this.batchFor(n), 0, 0, this.table, null, objectByObjectToDouble, n2, doubleByDoubleToDouble).invoke();
    }
    
    public long reduceToLong(final long n, final ObjectByObjectToLong objectByObjectToLong, final long n2, final LongByLongToLong longByLongToLong) {
        if (objectByObjectToLong == null || longByLongToLong == null) {
            throw new NullPointerException();
        }
        return (long)new MapReduceMappingsToLongTask(null, this.batchFor(n), 0, 0, this.table, null, objectByObjectToLong, n2, longByLongToLong).invoke();
    }
    
    public int reduceToInt(final long n, final ObjectByObjectToInt objectByObjectToInt, final int n2, final IntByIntToInt intByIntToInt) {
        if (objectByObjectToInt == null || intByIntToInt == null) {
            throw new NullPointerException();
        }
        return (int)new MapReduceMappingsToIntTask(null, this.batchFor(n), 0, 0, this.table, null, objectByObjectToInt, n2, intByIntToInt).invoke();
    }
    
    public void forEachKey(final long n, final Action action) {
        if (action == null) {
            throw new NullPointerException();
        }
        new ForEachKeyTask(null, this.batchFor(n), 0, 0, this.table, action).invoke();
    }
    
    public void forEachKey(final long n, final Fun fun, final Action action) {
        if (fun == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedKeyTask(null, this.batchFor(n), 0, 0, this.table, fun, action).invoke();
    }
    
    public Object searchKeys(final long n, final Fun fun) {
        if (fun == null) {
            throw new NullPointerException();
        }
        return new SearchKeysTask(null, this.batchFor(n), 0, 0, this.table, fun, new AtomicReference()).invoke();
    }
    
    public Object reduceKeys(final long n, final BiFun biFun) {
        if (biFun == null) {
            throw new NullPointerException();
        }
        return new ReduceKeysTask(null, this.batchFor(n), 0, 0, this.table, null, biFun).invoke();
    }
    
    public Object reduceKeys(final long n, final Fun fun, final BiFun biFun) {
        if (fun == null || biFun == null) {
            throw new NullPointerException();
        }
        return new MapReduceKeysTask(null, this.batchFor(n), 0, 0, this.table, null, fun, biFun).invoke();
    }
    
    public double reduceKeysToDouble(final long n, final ObjectToDouble objectToDouble, final double n2, final DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectToDouble == null || doubleByDoubleToDouble == null) {
            throw new NullPointerException();
        }
        return (double)new MapReduceKeysToDoubleTask(null, this.batchFor(n), 0, 0, this.table, null, objectToDouble, n2, doubleByDoubleToDouble).invoke();
    }
    
    public long reduceKeysToLong(final long n, final ObjectToLong objectToLong, final long n2, final LongByLongToLong longByLongToLong) {
        if (objectToLong == null || longByLongToLong == null) {
            throw new NullPointerException();
        }
        return (long)new MapReduceKeysToLongTask(null, this.batchFor(n), 0, 0, this.table, null, objectToLong, n2, longByLongToLong).invoke();
    }
    
    public int reduceKeysToInt(final long n, final ObjectToInt objectToInt, final int n2, final IntByIntToInt intByIntToInt) {
        if (objectToInt == null || intByIntToInt == null) {
            throw new NullPointerException();
        }
        return (int)new MapReduceKeysToIntTask(null, this.batchFor(n), 0, 0, this.table, null, objectToInt, n2, intByIntToInt).invoke();
    }
    
    public void forEachValue(final long n, final Action action) {
        if (action == null) {
            throw new NullPointerException();
        }
        new ForEachValueTask(null, this.batchFor(n), 0, 0, this.table, action).invoke();
    }
    
    public void forEachValue(final long n, final Fun fun, final Action action) {
        if (fun == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedValueTask(null, this.batchFor(n), 0, 0, this.table, fun, action).invoke();
    }
    
    public Object searchValues(final long n, final Fun fun) {
        if (fun == null) {
            throw new NullPointerException();
        }
        return new SearchValuesTask(null, this.batchFor(n), 0, 0, this.table, fun, new AtomicReference()).invoke();
    }
    
    public Object reduceValues(final long n, final BiFun biFun) {
        if (biFun == null) {
            throw new NullPointerException();
        }
        return new ReduceValuesTask(null, this.batchFor(n), 0, 0, this.table, null, biFun).invoke();
    }
    
    public Object reduceValues(final long n, final Fun fun, final BiFun biFun) {
        if (fun == null || biFun == null) {
            throw new NullPointerException();
        }
        return new MapReduceValuesTask(null, this.batchFor(n), 0, 0, this.table, null, fun, biFun).invoke();
    }
    
    public double reduceValuesToDouble(final long n, final ObjectToDouble objectToDouble, final double n2, final DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectToDouble == null || doubleByDoubleToDouble == null) {
            throw new NullPointerException();
        }
        return (double)new MapReduceValuesToDoubleTask(null, this.batchFor(n), 0, 0, this.table, null, objectToDouble, n2, doubleByDoubleToDouble).invoke();
    }
    
    public long reduceValuesToLong(final long n, final ObjectToLong objectToLong, final long n2, final LongByLongToLong longByLongToLong) {
        if (objectToLong == null || longByLongToLong == null) {
            throw new NullPointerException();
        }
        return (long)new MapReduceValuesToLongTask(null, this.batchFor(n), 0, 0, this.table, null, objectToLong, n2, longByLongToLong).invoke();
    }
    
    public int reduceValuesToInt(final long n, final ObjectToInt objectToInt, final int n2, final IntByIntToInt intByIntToInt) {
        if (objectToInt == null || intByIntToInt == null) {
            throw new NullPointerException();
        }
        return (int)new MapReduceValuesToIntTask(null, this.batchFor(n), 0, 0, this.table, null, objectToInt, n2, intByIntToInt).invoke();
    }
    
    public void forEachEntry(final long n, final Action action) {
        if (action == null) {
            throw new NullPointerException();
        }
        new ForEachEntryTask(null, this.batchFor(n), 0, 0, this.table, action).invoke();
    }
    
    public void forEachEntry(final long n, final Fun fun, final Action action) {
        if (fun == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedEntryTask(null, this.batchFor(n), 0, 0, this.table, fun, action).invoke();
    }
    
    public Object searchEntries(final long n, final Fun fun) {
        if (fun == null) {
            throw new NullPointerException();
        }
        return new SearchEntriesTask(null, this.batchFor(n), 0, 0, this.table, fun, new AtomicReference()).invoke();
    }
    
    public Map.Entry reduceEntries(final long n, final BiFun biFun) {
        if (biFun == null) {
            throw new NullPointerException();
        }
        return (Map.Entry)new ReduceEntriesTask(null, this.batchFor(n), 0, 0, this.table, null, biFun).invoke();
    }
    
    public Object reduceEntries(final long n, final Fun fun, final BiFun biFun) {
        if (fun == null || biFun == null) {
            throw new NullPointerException();
        }
        return new MapReduceEntriesTask(null, this.batchFor(n), 0, 0, this.table, null, fun, biFun).invoke();
    }
    
    public double reduceEntriesToDouble(final long n, final ObjectToDouble objectToDouble, final double n2, final DoubleByDoubleToDouble doubleByDoubleToDouble) {
        if (objectToDouble == null || doubleByDoubleToDouble == null) {
            throw new NullPointerException();
        }
        return (double)new MapReduceEntriesToDoubleTask(null, this.batchFor(n), 0, 0, this.table, null, objectToDouble, n2, doubleByDoubleToDouble).invoke();
    }
    
    public long reduceEntriesToLong(final long n, final ObjectToLong objectToLong, final long n2, final LongByLongToLong longByLongToLong) {
        if (objectToLong == null || longByLongToLong == null) {
            throw new NullPointerException();
        }
        return (long)new MapReduceEntriesToLongTask(null, this.batchFor(n), 0, 0, this.table, null, objectToLong, n2, longByLongToLong).invoke();
    }
    
    public int reduceEntriesToInt(final long n, final ObjectToInt objectToInt, final int n2, final IntByIntToInt intByIntToInt) {
        if (objectToInt == null || intByIntToInt == null) {
            throw new NullPointerException();
        }
        return (int)new MapReduceEntriesToIntTask(null, this.batchFor(n), 0, 0, this.table, null, objectToInt, n2, intByIntToInt).invoke();
    }
    
    final long sumCount() {
        final CounterCell[] counterCells = this.counterCells;
        long baseCount = this.baseCount;
        if (counterCells != null) {
            while (0 < counterCells.length) {
                final CounterCell counterCell;
                if ((counterCell = counterCells[0]) != null) {
                    baseCount += counterCell.value;
                }
                int n = 0;
                ++n;
            }
        }
        return baseCount;
    }
    
    private final void fullAddCount(final InternalThreadLocalMap p0, final long p1, final IntegerHolder p2, final boolean p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: ifnonnull       51
        //     5: new             Lio/netty/util/internal/IntegerHolder;
        //     8: dup            
        //     9: invokespecial   io/netty/util/internal/IntegerHolder.<init>:()V
        //    12: astore          4
        //    14: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterHashCodeGenerator:Ljava/util/concurrent/atomic/AtomicInteger;
        //    17: ldc_w           1640531527
        //    20: invokevirtual   java/util/concurrent/atomic/AtomicInteger.addAndGet:(I)I
        //    23: istore          7
        //    25: aload           4
        //    27: iconst_0       
        //    28: ifne            35
        //    31: iconst_1       
        //    32: goto            36
        //    35: iconst_0       
        //    36: dup_x1         
        //    37: putfield        io/netty/util/internal/IntegerHolder.value:I
        //    40: istore          6
        //    42: aload_1        
        //    43: aload           4
        //    45: invokevirtual   io/netty/util/internal/InternalThreadLocalMap.setCounterHashCode:(Lio/netty/util/internal/IntegerHolder;)V
        //    48: goto            58
        //    51: aload           4
        //    53: getfield        io/netty/util/internal/IntegerHolder.value:I
        //    56: istore          6
        //    58: aload_0        
        //    59: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //    62: dup            
        //    63: astore          8
        //    65: ifnull          384
        //    68: aload           8
        //    70: arraylength    
        //    71: dup            
        //    72: istore          10
        //    74: ifle            384
        //    77: aload           8
        //    79: iload           10
        //    81: iconst_1       
        //    82: isub           
        //    83: iload           6
        //    85: iand           
        //    86: aaload         
        //    87: dup            
        //    88: astore          9
        //    90: ifnonnull       202
        //    93: aload_0        
        //    94: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //    97: ifne            199
        //   100: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   103: dup            
        //   104: lload_2        
        //   105: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell.<init>:(J)V
        //   108: astore          13
        //   110: aload_0        
        //   111: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   114: ifne            199
        //   117: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   120: aload_0        
        //   121: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.CELLSBUSY:J
        //   124: iconst_0       
        //   125: iconst_1       
        //   126: invokevirtual   sun/misc/Unsafe.compareAndSwapInt:(Ljava/lang/Object;JII)Z
        //   129: ifeq            199
        //   132: aload_0        
        //   133: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   136: dup            
        //   137: astore          15
        //   139: ifnull          174
        //   142: aload           15
        //   144: arraylength    
        //   145: dup            
        //   146: istore          16
        //   148: ifle            174
        //   151: aload           15
        //   153: iload           16
        //   155: iconst_1       
        //   156: isub           
        //   157: iload           6
        //   159: iand           
        //   160: dup            
        //   161: istore          17
        //   163: aaload         
        //   164: ifnonnull       174
        //   167: aload           15
        //   169: iload           17
        //   171: aload           13
        //   173: aastore        
        //   174: aload_0        
        //   175: iconst_0       
        //   176: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   179: goto            192
        //   182: astore          18
        //   184: aload_0        
        //   185: iconst_0       
        //   186: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   189: aload           18
        //   191: athrow         
        //   192: iconst_0       
        //   193: ifeq            58
        //   196: goto            509
        //   199: goto            352
        //   202: iconst_1       
        //   203: ifne            209
        //   206: goto            352
        //   209: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   212: aload           9
        //   214: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.CELLVALUE:J
        //   217: aload           9
        //   219: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell.value:J
        //   222: dup2           
        //   223: lstore          11
        //   225: lload           11
        //   227: lload_2        
        //   228: ladd           
        //   229: invokevirtual   sun/misc/Unsafe.compareAndSwapLong:(Ljava/lang/Object;JJJ)Z
        //   232: ifeq            238
        //   235: goto            509
        //   238: aload_0        
        //   239: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   242: aload           8
        //   244: if_acmpne       255
        //   247: iload           10
        //   249: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.NCPU:I
        //   252: if_icmplt       258
        //   255: goto            352
        //   258: iconst_0       
        //   259: ifne            265
        //   262: goto            352
        //   265: aload_0        
        //   266: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   269: ifne            352
        //   272: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   275: aload_0        
        //   276: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.CELLSBUSY:J
        //   279: iconst_0       
        //   280: iconst_1       
        //   281: invokevirtual   sun/misc/Unsafe.compareAndSwapInt:(Ljava/lang/Object;JII)Z
        //   284: ifeq            352
        //   287: aload_0        
        //   288: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   291: aload           8
        //   293: if_acmpne       331
        //   296: iload           10
        //   298: iconst_1       
        //   299: ishl           
        //   300: anewarray       Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   303: astore          13
        //   305: iconst_0       
        //   306: iload           10
        //   308: if_icmpge       325
        //   311: aload           13
        //   313: iconst_0       
        //   314: aload           8
        //   316: iconst_0       
        //   317: aaload         
        //   318: aastore        
        //   319: iinc            14, 1
        //   322: goto            305
        //   325: aload_0        
        //   326: aload           13
        //   328: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   331: aload_0        
        //   332: iconst_0       
        //   333: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   336: goto            349
        //   339: astore          19
        //   341: aload_0        
        //   342: iconst_0       
        //   343: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   346: aload           19
        //   348: athrow         
        //   349: goto            58
        //   352: iload           6
        //   354: iload           6
        //   356: bipush          13
        //   358: ishl           
        //   359: ixor           
        //   360: istore          6
        //   362: iload           6
        //   364: iload           6
        //   366: bipush          17
        //   368: iushr          
        //   369: ixor           
        //   370: istore          6
        //   372: iload           6
        //   374: iload           6
        //   376: iconst_5       
        //   377: ishl           
        //   378: ixor           
        //   379: istore          6
        //   381: goto            506
        //   384: aload_0        
        //   385: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   388: ifne            479
        //   391: aload_0        
        //   392: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   395: aload           8
        //   397: if_acmpne       479
        //   400: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   403: aload_0        
        //   404: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.CELLSBUSY:J
        //   407: iconst_0       
        //   408: iconst_1       
        //   409: invokevirtual   sun/misc/Unsafe.compareAndSwapInt:(Ljava/lang/Object;JII)Z
        //   412: ifeq            479
        //   415: aload_0        
        //   416: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   419: aload           8
        //   421: if_acmpne       451
        //   424: iconst_2       
        //   425: anewarray       Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   428: astore          14
        //   430: aload           14
        //   432: iload           6
        //   434: iconst_1       
        //   435: iand           
        //   436: new             Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   439: dup            
        //   440: lload_2        
        //   441: invokespecial   io/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell.<init>:(J)V
        //   444: aastore        
        //   445: aload_0        
        //   446: aload           14
        //   448: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.counterCells:[Lio/netty/util/internal/chmv8/ConcurrentHashMapV8$CounterCell;
        //   451: aload_0        
        //   452: iconst_0       
        //   453: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   456: goto            469
        //   459: astore          20
        //   461: aload_0        
        //   462: iconst_0       
        //   463: putfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.cellsBusy:I
        //   466: aload           20
        //   468: athrow         
        //   469: iconst_1       
        //   470: ifeq            476
        //   473: goto            509
        //   476: goto            506
        //   479: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.U:Lsun/misc/Unsafe;
        //   482: aload_0        
        //   483: getstatic       io/netty/util/internal/chmv8/ConcurrentHashMapV8.BASECOUNT:J
        //   486: aload_0        
        //   487: getfield        io/netty/util/internal/chmv8/ConcurrentHashMapV8.baseCount:J
        //   490: dup2           
        //   491: lstore          11
        //   493: lload           11
        //   495: lload_2        
        //   496: ladd           
        //   497: invokevirtual   sun/misc/Unsafe.compareAndSwapLong:(Ljava/lang/Object;JJJ)Z
        //   500: ifeq            506
        //   503: goto            509
        //   506: goto            58
        //   509: aload           4
        //   511: iload           6
        //   513: putfield        io/netty/util/internal/IntegerHolder.value:I
        //   516: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static Unsafe getUnsafe() {
        return Unsafe.getUnsafe();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    static Unsafe access$000() {
        return getUnsafe();
    }
    
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE) };
        counterHashCodeGenerator = new AtomicInteger();
        U = getUnsafe();
        final Class<ConcurrentHashMapV8> clazz = ConcurrentHashMapV8.class;
        SIZECTL = ConcurrentHashMapV8.U.objectFieldOffset(clazz.getDeclaredField("sizeCtl"));
        TRANSFERINDEX = ConcurrentHashMapV8.U.objectFieldOffset(clazz.getDeclaredField("transferIndex"));
        TRANSFERORIGIN = ConcurrentHashMapV8.U.objectFieldOffset(clazz.getDeclaredField("transferOrigin"));
        BASECOUNT = ConcurrentHashMapV8.U.objectFieldOffset(clazz.getDeclaredField("baseCount"));
        CELLSBUSY = ConcurrentHashMapV8.U.objectFieldOffset(clazz.getDeclaredField("cellsBusy"));
        CELLVALUE = ConcurrentHashMapV8.U.objectFieldOffset(CounterCell.class.getDeclaredField("value"));
        final Class<Node[]> clazz2 = Node[].class;
        ABASE = ConcurrentHashMapV8.U.arrayBaseOffset(clazz2);
        final int arrayIndexScale = ConcurrentHashMapV8.U.arrayIndexScale(clazz2);
        if ((arrayIndexScale & arrayIndexScale - 1) != 0x0) {
            throw new Error("data type scale not a power of two");
        }
        ASHIFT = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
    }
    
    static final class CounterHashCode
    {
        int code;
    }
    
    static final class CounterCell
    {
        long p0;
        long p1;
        long p2;
        long p3;
        long p4;
        long p5;
        long p6;
        long value;
        long q0;
        long q1;
        long q2;
        long q3;
        long q4;
        long q5;
        long q6;
        
        CounterCell(final long value) {
            this.value = value;
        }
    }
    
    static final class MapReduceMappingsToIntTask extends BulkTask
    {
        final ObjectByObjectToInt transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceMappingsToIntTask rights;
        MapReduceMappingsToIntTask nextRight;
        
        MapReduceMappingsToIntTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceMappingsToIntTask nextRight, final ObjectByObjectToInt transformer, final int basis, final IntByIntToInt reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectByObjectToInt transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceMappingsToIntTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.key, advance.val));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceMappingsToIntTask mapReduceMappingsToIntTask = (MapReduceMappingsToIntTask)countedCompleter;
                    MapReduceMappingsToIntTask nextRight;
                    for (MapReduceMappingsToIntTask rights = mapReduceMappingsToIntTask.rights; rights != null; rights = nextRight) {
                        mapReduceMappingsToIntTask.result = reducer.apply(mapReduceMappingsToIntTask.result, rights.result);
                        final MapReduceMappingsToIntTask mapReduceMappingsToIntTask2 = mapReduceMappingsToIntTask;
                        nextRight = rights.nextRight;
                        mapReduceMappingsToIntTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    public interface ObjectByObjectToInt
    {
        int apply(final Object p0, final Object p1);
    }
    
    public interface IntByIntToInt
    {
        int apply(final int p0, final int p1);
    }
    
    abstract static class BulkTask extends CountedCompleter
    {
        Node[] tab;
        Node next;
        int index;
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int batch;
        
        BulkTask(final BulkTask bulkTask, final int batch, final int n, final int baseLimit, final Node[] tab) {
            super(bulkTask);
            this.batch = batch;
            this.baseIndex = n;
            this.index = n;
            this.tab = tab;
            if (tab == null) {
                final int n2 = 0;
                this.baseLimit = n2;
                this.baseSize = n2;
            }
            else if (bulkTask == null) {
                final int length = tab.length;
                this.baseLimit = length;
                this.baseSize = length;
            }
            else {
                this.baseLimit = baseLimit;
                this.baseSize = bulkTask.baseSize;
            }
        }
        
        final Node advance() {
            Node next;
            if ((next = this.next) != null) {
                next = next.next;
            }
            while (next == null) {
                final Node[] tab;
                final int length;
                final int index;
                if (this.baseIndex >= this.baseLimit || (tab = this.tab) == null || (length = tab.length) <= (index = this.index) || index < 0) {
                    return this.next = null;
                }
                if ((next = ConcurrentHashMapV8.tabAt(tab, this.index)) != null && next.hash < 0) {
                    if (next instanceof ForwardingNode) {
                        this.tab = ((ForwardingNode)next).nextTable;
                        next = null;
                        continue;
                    }
                    if (next instanceof TreeBin) {
                        next = ((TreeBin)next).first;
                    }
                    else {
                        next = null;
                    }
                }
                if ((this.index += this.baseSize) < length) {
                    continue;
                }
                this.index = ++this.baseIndex;
            }
            return this.next = next;
        }
    }
    
    static class Node implements Map.Entry
    {
        final int hash;
        final Object key;
        Object val;
        Node next;
        
        Node(final int hash, final Object key, final Object val, final Node next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }
        
        @Override
        public final Object getKey() {
            return this.key;
        }
        
        @Override
        public final Object getValue() {
            return this.val;
        }
        
        @Override
        public final int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }
        
        @Override
        public final String toString() {
            return this.key + "=" + this.val;
        }
        
        @Override
        public final Object setValue(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public final boolean equals(final Object o) {
            final Map.Entry entry;
            final Object key;
            final Object value;
            final Object val;
            return o instanceof Map.Entry && (key = (entry = (Map.Entry)o).getKey()) != null && (value = entry.getValue()) != null && (key == this.key || key.equals(this.key)) && (value == (val = this.val) || value.equals(val));
        }
        
        Node find(final int n, final Object o) {
            Node next = this;
            if (o != null) {
                Object key;
                while (next.hash != n || ((key = next.key) != o && (key == null || !o.equals(key)))) {
                    if ((next = next.next) == null) {
                        return null;
                    }
                }
                return next;
            }
            return null;
        }
    }
    
    static final class ForwardingNode extends Node
    {
        final Node[] nextTable;
        
        ForwardingNode(final Node[] nextTable) {
            super(-1, null, null, null);
            this.nextTable = nextTable;
        }
        
        @Override
        Node find(final int n, final Object o) {
            Node[] array = this.nextTable;
            int length;
            Node node;
        Label_0005:
            while (o != null && array != null && (length = array.length) != 0 && (node = ConcurrentHashMapV8.tabAt(array, length - 1 & n)) != null) {
                int hash;
                Object key;
                while ((hash = node.hash) != n || ((key = node.key) != o && (key == null || !o.equals(key)))) {
                    if (hash < 0) {
                        if (node instanceof ForwardingNode) {
                            array = ((ForwardingNode)node).nextTable;
                            continue Label_0005;
                        }
                        return node.find(n, o);
                    }
                    else {
                        if ((node = node.next) == null) {
                            return null;
                        }
                        continue;
                    }
                }
                return node;
            }
            return null;
        }
    }
    
    static final class TreeBin extends Node
    {
        TreeNode root;
        TreeNode first;
        Thread waiter;
        int lockState;
        static final int WRITER = 1;
        static final int WAITER = 2;
        static final int READER = 4;
        private static final Unsafe U;
        private static final long LOCKSTATE;
        static final boolean $assertionsDisabled;
        
        TreeBin(final TreeNode first) {
            super(-2, null, null, null);
            this.first = first;
            TreeNode balanceInsertion = null;
            TreeNode treeNode2;
            for (TreeNode treeNode = first; treeNode != null; treeNode = treeNode2) {
                treeNode2 = (TreeNode)treeNode.next;
                final TreeNode treeNode3 = treeNode;
                final TreeNode treeNode4 = treeNode;
                final TreeNode treeNode5 = null;
                treeNode4.right = treeNode5;
                treeNode3.left = treeNode5;
                if (balanceInsertion == null) {
                    treeNode.parent = null;
                    treeNode.red = false;
                    balanceInsertion = treeNode;
                }
                else {
                    final Object key = treeNode.key;
                    final int hash = treeNode.hash;
                    Class comparableClass = null;
                    TreeNode treeNode6 = balanceInsertion;
                    TreeNode parent;
                    do {
                        final int hash2;
                        if ((hash2 = treeNode6.hash) <= hash) {
                            if (hash2 >= hash) {
                                if (comparableClass != null || (comparableClass = ConcurrentHashMapV8.comparableClassFor(key)) != null) {
                                    ConcurrentHashMapV8.compareComparables(comparableClass, key, treeNode6.key);
                                }
                            }
                        }
                        parent = treeNode6;
                    } while ((treeNode6 = ((0 <= 0) ? treeNode6.left : treeNode6.right)) != null);
                    treeNode.parent = parent;
                    if (0 <= 0) {
                        parent.left = treeNode;
                    }
                    else {
                        parent.right = treeNode;
                    }
                    balanceInsertion = balanceInsertion(balanceInsertion, treeNode);
                }
            }
            this.root = balanceInsertion;
        }
        
        private final void lockRoot() {
            if (!TreeBin.U.compareAndSwapInt(this, TreeBin.LOCKSTATE, 0, 1)) {
                this.contendedLock();
            }
        }
        
        private final void unlockRoot() {
            this.lockState = 0;
        }
        
        private final void contendedLock() {
            while (true) {
                final int lockState;
                if (((lockState = this.lockState) & 0x1) == 0x0) {
                    if (TreeBin.U.compareAndSwapInt(this, TreeBin.LOCKSTATE, lockState, 1)) {
                        break;
                    }
                    continue;
                }
                else if ((lockState & 0x2) == 0x0) {
                    if (!TreeBin.U.compareAndSwapInt(this, TreeBin.LOCKSTATE, lockState, lockState | 0x2)) {
                        continue;
                    }
                    this.waiter = Thread.currentThread();
                }
                else {
                    if (!true) {
                        continue;
                    }
                    LockSupport.park(this);
                }
            }
            if (true) {
                this.waiter = null;
            }
        }
        
        @Override
        final Node find(final int n, final Object o) {
            if (o != null) {
                for (Node node = this.first; node != null; node = node.next) {
                    final int lockState;
                    if (((lockState = this.lockState) & 0x3) != 0x0) {
                        final Object key;
                        if (node.hash == n && ((key = node.key) == o || (key != null && o.equals(key)))) {
                            return node;
                        }
                    }
                    else if (TreeBin.U.compareAndSwapInt(this, TreeBin.LOCKSTATE, lockState, lockState + 4)) {
                        final TreeNode root;
                        final TreeNode treeNode = ((root = this.root) == null) ? null : root.findTreeNode(n, o, null);
                        int lockState2;
                        while (!TreeBin.U.compareAndSwapInt(this, TreeBin.LOCKSTATE, lockState2 = this.lockState, lockState2 - 4)) {}
                        final Thread waiter;
                        if (lockState2 == 6 && (waiter = this.waiter) != null) {
                            LockSupport.unpark(waiter);
                        }
                        return treeNode;
                    }
                }
            }
            return null;
        }
        
        final TreeNode putTreeVal(final int n, final Object o, final Object o2) {
            Class comparableClass = null;
            TreeNode root = this.root;
            while (true) {
                while (root != null) {
                    final int hash;
                    if ((hash = root.hash) <= n) {
                        if (hash >= n) {
                            final Object key;
                            if ((key = root.key) == o || (key != null && o.equals(key))) {
                                return root;
                            }
                            final int compareComparables;
                            if ((comparableClass == null && (comparableClass = ConcurrentHashMapV8.comparableClassFor(o)) == null) || (compareComparables = ConcurrentHashMapV8.compareComparables(comparableClass, o, key)) == 0) {
                                if (root.left != null) {
                                    final TreeNode right;
                                    final TreeNode treeNode;
                                    if ((right = root.right) != null && (treeNode = right.findTreeNode(n, o, comparableClass)) != null) {
                                        return treeNode;
                                    }
                                }
                            }
                        }
                    }
                    final TreeNode treeNode2 = root;
                    if ((root = ((-1 < 0) ? root.left : root.right)) == null) {
                        final TreeNode first = this.first;
                        final TreeNode right2 = this.first = new TreeNode(n, o, o2, first, treeNode2);
                        if (first != null) {
                            first.prev = right2;
                        }
                        if (-1 < 0) {
                            treeNode2.left = right2;
                        }
                        else {
                            treeNode2.right = right2;
                        }
                        if (!treeNode2.red) {
                            right2.red = true;
                        }
                        else {
                            this.lockRoot();
                            this.root = balanceInsertion(this.root, right2);
                            this.unlockRoot();
                        }
                        assert checkInvariants(this.root);
                        return null;
                    }
                }
                final TreeNode treeNode3 = new TreeNode(n, o, o2, null, null);
                this.root = treeNode3;
                this.first = treeNode3;
                continue;
            }
        }
        
        final boolean removeTreeNode(final TreeNode treeNode) {
            final TreeNode treeNode2 = (TreeNode)treeNode.next;
            final TreeNode prev = treeNode.prev;
            if (prev == null) {
                this.first = treeNode2;
            }
            else {
                prev.next = treeNode2;
            }
            if (treeNode2 != null) {
                treeNode2.prev = prev;
            }
            if (this.first == null) {
                this.root = null;
                return true;
            }
            TreeNode root;
            final TreeNode left;
            if ((root = this.root) == null || root.right == null || (left = root.left) == null || left.left == null) {
                return true;
            }
            this.lockRoot();
            final TreeNode left2 = treeNode.left;
            final TreeNode right = treeNode.right;
            TreeNode treeNode3;
            if (left2 != null && right != null) {
                TreeNode right2;
                TreeNode left3;
                for (right2 = right; (left3 = right2.left) != null; right2 = left3) {}
                final boolean red = right2.red;
                right2.red = treeNode.red;
                treeNode.red = red;
                final TreeNode right3 = right2.right;
                final TreeNode parent = treeNode.parent;
                if (right2 == right) {
                    treeNode.parent = right2;
                    right2.right = treeNode;
                }
                else {
                    final TreeNode parent2 = right2.parent;
                    if ((treeNode.parent = parent2) != null) {
                        if (right2 == parent2.left) {
                            parent2.left = treeNode;
                        }
                        else {
                            parent2.right = treeNode;
                        }
                    }
                    right2.right = right;
                    right.parent = right2;
                }
                treeNode.left = null;
                right2.left = left2;
                left2.parent = right2;
                final TreeNode right4 = right3;
                treeNode.right = right4;
                if (right4 != null) {
                    right3.parent = treeNode;
                }
                if ((right2.parent = parent) == null) {
                    root = right2;
                }
                else if (treeNode == parent.left) {
                    parent.left = right2;
                }
                else {
                    parent.right = right2;
                }
                if (right3 != null) {
                    treeNode3 = right3;
                }
                else {
                    treeNode3 = treeNode;
                }
            }
            else if (left2 != null) {
                treeNode3 = left2;
            }
            else if (right != null) {
                treeNode3 = right;
            }
            else {
                treeNode3 = treeNode;
            }
            if (treeNode3 != treeNode) {
                final TreeNode treeNode4 = treeNode3;
                final TreeNode parent3 = treeNode.parent;
                treeNode4.parent = parent3;
                final TreeNode treeNode5 = parent3;
                if (treeNode5 == null) {
                    root = treeNode3;
                }
                else if (treeNode == treeNode5.left) {
                    treeNode5.left = treeNode3;
                }
                else {
                    treeNode5.right = treeNode3;
                }
                final TreeNode left4 = null;
                treeNode.parent = left4;
                treeNode.right = left4;
                treeNode.left = left4;
            }
            this.root = (treeNode.red ? root : balanceDeletion(root, treeNode3));
            final TreeNode parent4;
            if (treeNode == treeNode3 && (parent4 = treeNode.parent) != null) {
                if (treeNode == parent4.left) {
                    parent4.left = null;
                }
                else if (treeNode == parent4.right) {
                    parent4.right = null;
                }
                treeNode.parent = null;
            }
            this.unlockRoot();
            assert checkInvariants(this.root);
            return false;
        }
        
        static TreeNode rotateLeft(TreeNode treeNode, final TreeNode treeNode2) {
            final TreeNode right;
            if (treeNode2 != null && (right = treeNode2.right) != null) {
                final TreeNode left = right.left;
                treeNode2.right = left;
                final TreeNode treeNode3;
                if ((treeNode3 = left) != null) {
                    treeNode3.parent = treeNode2;
                }
                final TreeNode treeNode4 = right;
                final TreeNode parent = treeNode2.parent;
                treeNode4.parent = parent;
                final TreeNode treeNode5;
                if ((treeNode5 = parent) == null) {
                    (treeNode = right).red = false;
                }
                else if (treeNode5.left == treeNode2) {
                    treeNode5.left = right;
                }
                else {
                    treeNode5.right = right;
                }
                right.left = treeNode2;
                treeNode2.parent = right;
            }
            return treeNode;
        }
        
        static TreeNode rotateRight(TreeNode treeNode, final TreeNode treeNode2) {
            final TreeNode left;
            if (treeNode2 != null && (left = treeNode2.left) != null) {
                final TreeNode right = left.right;
                treeNode2.left = right;
                final TreeNode treeNode3;
                if ((treeNode3 = right) != null) {
                    treeNode3.parent = treeNode2;
                }
                final TreeNode treeNode4 = left;
                final TreeNode parent = treeNode2.parent;
                treeNode4.parent = parent;
                final TreeNode treeNode5;
                if ((treeNode5 = parent) == null) {
                    (treeNode = left).red = false;
                }
                else if (treeNode5.right == treeNode2) {
                    treeNode5.right = left;
                }
                else {
                    treeNode5.left = left;
                }
                left.right = treeNode2;
                treeNode2.parent = left;
            }
            return treeNode;
        }
        
        static TreeNode balanceInsertion(TreeNode treeNode, TreeNode treeNode2) {
            treeNode2.red = true;
            TreeNode treeNode3;
            while ((treeNode3 = treeNode2.parent) != null) {
                TreeNode parent;
                if (!treeNode3.red || (parent = treeNode3.parent) == null) {
                    return treeNode;
                }
                final TreeNode left;
                if (treeNode3 == (left = parent.left)) {
                    final TreeNode right;
                    if ((right = parent.right) != null && right.red) {
                        right.red = false;
                        treeNode3.red = false;
                        parent.red = true;
                        treeNode2 = parent;
                    }
                    else {
                        if (treeNode2 == treeNode3.right) {
                            treeNode = rotateLeft(treeNode, treeNode2 = treeNode3);
                            parent = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.parent);
                        }
                        if (treeNode3 == null) {
                            continue;
                        }
                        treeNode3.red = false;
                        if (parent == null) {
                            continue;
                        }
                        parent.red = true;
                        treeNode = rotateRight(treeNode, parent);
                    }
                }
                else if (left != null && left.red) {
                    left.red = false;
                    treeNode3.red = false;
                    parent.red = true;
                    treeNode2 = parent;
                }
                else {
                    if (treeNode2 == treeNode3.left) {
                        treeNode = rotateRight(treeNode, treeNode2 = treeNode3);
                        parent = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.parent);
                    }
                    if (treeNode3 == null) {
                        continue;
                    }
                    treeNode3.red = false;
                    if (parent == null) {
                        continue;
                    }
                    parent.red = true;
                    treeNode = rotateLeft(treeNode, parent);
                }
            }
            treeNode2.red = false;
            return treeNode2;
        }
        
        static TreeNode balanceDeletion(TreeNode treeNode, TreeNode treeNode2) {
            while (treeNode2 != null && treeNode2 != treeNode) {
                TreeNode treeNode3;
                if ((treeNode3 = treeNode2.parent) == null) {
                    treeNode2.red = false;
                    return treeNode2;
                }
                if (treeNode2.red) {
                    treeNode2.red = false;
                    return treeNode;
                }
                TreeNode left;
                if ((left = treeNode3.left) == treeNode2) {
                    TreeNode right;
                    if ((right = treeNode3.right) != null && right.red) {
                        right.red = false;
                        treeNode3.red = true;
                        treeNode = rotateLeft(treeNode, treeNode3);
                        right = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.right);
                    }
                    if (right == null) {
                        treeNode2 = treeNode3;
                    }
                    else {
                        final TreeNode left2 = right.left;
                        final TreeNode right2 = right.right;
                        if ((right2 == null || !right2.red) && (left2 == null || !left2.red)) {
                            right.red = true;
                            treeNode2 = treeNode3;
                        }
                        else {
                            if (right2 == null || !right2.red) {
                                if (left2 != null) {
                                    left2.red = false;
                                }
                                right.red = true;
                                treeNode = rotateRight(treeNode, right);
                                right = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.right);
                            }
                            if (right != null) {
                                right.red = (treeNode3 != null && treeNode3.red);
                                final TreeNode right3;
                                if ((right3 = right.right) != null) {
                                    right3.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateLeft(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                }
                else {
                    if (left != null && left.red) {
                        left.red = false;
                        treeNode3.red = true;
                        treeNode = rotateRight(treeNode, treeNode3);
                        left = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.left);
                    }
                    if (left == null) {
                        treeNode2 = treeNode3;
                    }
                    else {
                        final TreeNode left3 = left.left;
                        final TreeNode right4 = left.right;
                        if ((left3 == null || !left3.red) && (right4 == null || !right4.red)) {
                            left.red = true;
                            treeNode2 = treeNode3;
                        }
                        else {
                            if (left3 == null || !left3.red) {
                                if (right4 != null) {
                                    right4.red = false;
                                }
                                left.red = true;
                                treeNode = rotateLeft(treeNode, left);
                                left = (((treeNode3 = treeNode2.parent) == null) ? null : treeNode3.left);
                            }
                            if (left != null) {
                                left.red = (treeNode3 != null && treeNode3.red);
                                final TreeNode left4;
                                if ((left4 = left.left) != null) {
                                    left4.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateRight(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                }
            }
            return treeNode;
        }
        
        static boolean checkInvariants(final TreeNode treeNode) {
            final TreeNode parent = treeNode.parent;
            final TreeNode left = treeNode.left;
            final TreeNode right = treeNode.right;
            final TreeNode prev = treeNode.prev;
            final TreeNode treeNode2 = (TreeNode)treeNode.next;
            return (prev == null || prev.next == treeNode) && (treeNode2 == null || treeNode2.prev == treeNode) && (parent == null || treeNode == parent.left || treeNode == parent.right) && (left == null || (left.parent == treeNode && left.hash <= treeNode.hash)) && (right == null || (right.parent == treeNode && right.hash >= treeNode.hash)) && (!treeNode.red || left == null || !left.red || right == null || !right.red) && (left == null || checkInvariants(left)) && (right == null || checkInvariants(right));
        }
        
        static {
            $assertionsDisabled = !ConcurrentHashMapV8.class.desiredAssertionStatus();
            U = ConcurrentHashMapV8.access$000();
            LOCKSTATE = TreeBin.U.objectFieldOffset(TreeBin.class.getDeclaredField("lockState"));
        }
    }
    
    static final class TreeNode extends Node
    {
        TreeNode parent;
        TreeNode left;
        TreeNode right;
        TreeNode prev;
        boolean red;
        
        TreeNode(final int n, final Object o, final Object o2, final Node node, final TreeNode parent) {
            super(n, o, o2, node);
            this.parent = parent;
        }
        
        @Override
        Node find(final int n, final Object o) {
            return this.findTreeNode(n, o, null);
        }
        
        final TreeNode findTreeNode(final int n, final Object o, Class comparableClass) {
            if (o != null) {
                TreeNode treeNode = this;
                do {
                    final TreeNode left = treeNode.left;
                    final TreeNode right = treeNode.right;
                    final int hash;
                    if ((hash = treeNode.hash) > n) {
                        treeNode = left;
                    }
                    else if (hash < n) {
                        treeNode = right;
                    }
                    else {
                        final Object key;
                        if ((key = treeNode.key) == o || (key != null && o.equals(key))) {
                            return treeNode;
                        }
                        if (left == null && right == null) {
                            break;
                        }
                        final int compareComparables;
                        if ((comparableClass != null || (comparableClass = ConcurrentHashMapV8.comparableClassFor(o)) != null) && (compareComparables = ConcurrentHashMapV8.compareComparables(comparableClass, o, key)) != 0) {
                            treeNode = ((compareComparables < 0) ? left : right);
                        }
                        else if (left == null) {
                            treeNode = right;
                        }
                        else {
                            final TreeNode treeNode2;
                            if (right != null && (treeNode2 = right.findTreeNode(n, o, comparableClass)) != null) {
                                return treeNode2;
                            }
                            treeNode = left;
                        }
                    }
                } while (treeNode != null);
            }
            return null;
        }
    }
    
    static final class MapReduceEntriesToIntTask extends BulkTask
    {
        final ObjectToInt transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceEntriesToIntTask rights;
        MapReduceEntriesToIntTask nextRight;
        
        MapReduceEntriesToIntTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceEntriesToIntTask nextRight, final ObjectToInt transformer, final int basis, final IntByIntToInt reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToInt transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceEntriesToIntTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceEntriesToIntTask mapReduceEntriesToIntTask = (MapReduceEntriesToIntTask)countedCompleter;
                    MapReduceEntriesToIntTask nextRight;
                    for (MapReduceEntriesToIntTask rights = mapReduceEntriesToIntTask.rights; rights != null; rights = nextRight) {
                        mapReduceEntriesToIntTask.result = reducer.apply(mapReduceEntriesToIntTask.result, rights.result);
                        final MapReduceEntriesToIntTask mapReduceEntriesToIntTask2 = mapReduceEntriesToIntTask;
                        nextRight = rights.nextRight;
                        mapReduceEntriesToIntTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    public interface ObjectToInt
    {
        int apply(final Object p0);
    }
    
    static final class MapReduceValuesToIntTask extends BulkTask
    {
        final ObjectToInt transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceValuesToIntTask rights;
        MapReduceValuesToIntTask nextRight;
        
        MapReduceValuesToIntTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceValuesToIntTask nextRight, final ObjectToInt transformer, final int basis, final IntByIntToInt reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToInt transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceValuesToIntTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.val));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceValuesToIntTask mapReduceValuesToIntTask = (MapReduceValuesToIntTask)countedCompleter;
                    MapReduceValuesToIntTask nextRight;
                    for (MapReduceValuesToIntTask rights = mapReduceValuesToIntTask.rights; rights != null; rights = nextRight) {
                        mapReduceValuesToIntTask.result = reducer.apply(mapReduceValuesToIntTask.result, rights.result);
                        final MapReduceValuesToIntTask mapReduceValuesToIntTask2 = mapReduceValuesToIntTask;
                        nextRight = rights.nextRight;
                        mapReduceValuesToIntTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class MapReduceKeysToIntTask extends BulkTask
    {
        final ObjectToInt transformer;
        final IntByIntToInt reducer;
        final int basis;
        int result;
        MapReduceKeysToIntTask rights;
        MapReduceKeysToIntTask nextRight;
        
        MapReduceKeysToIntTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceKeysToIntTask nextRight, final ObjectToInt transformer, final int basis, final IntByIntToInt reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Integer getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToInt transformer;
            final IntByIntToInt reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                int result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceKeysToIntTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.key));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceKeysToIntTask mapReduceKeysToIntTask = (MapReduceKeysToIntTask)countedCompleter;
                    MapReduceKeysToIntTask nextRight;
                    for (MapReduceKeysToIntTask rights = mapReduceKeysToIntTask.rights; rights != null; rights = nextRight) {
                        mapReduceKeysToIntTask.result = reducer.apply(mapReduceKeysToIntTask.result, rights.result);
                        final MapReduceKeysToIntTask mapReduceKeysToIntTask2 = mapReduceKeysToIntTask;
                        nextRight = rights.nextRight;
                        mapReduceKeysToIntTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class MapReduceMappingsToLongTask extends BulkTask
    {
        final ObjectByObjectToLong transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceMappingsToLongTask rights;
        MapReduceMappingsToLongTask nextRight;
        
        MapReduceMappingsToLongTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceMappingsToLongTask nextRight, final ObjectByObjectToLong transformer, final long basis, final LongByLongToLong reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectByObjectToLong transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceMappingsToLongTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.key, advance.val));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceMappingsToLongTask mapReduceMappingsToLongTask = (MapReduceMappingsToLongTask)countedCompleter;
                    MapReduceMappingsToLongTask nextRight;
                    for (MapReduceMappingsToLongTask rights = mapReduceMappingsToLongTask.rights; rights != null; rights = nextRight) {
                        mapReduceMappingsToLongTask.result = reducer.apply(mapReduceMappingsToLongTask.result, rights.result);
                        final MapReduceMappingsToLongTask mapReduceMappingsToLongTask2 = mapReduceMappingsToLongTask;
                        nextRight = rights.nextRight;
                        mapReduceMappingsToLongTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    public interface ObjectByObjectToLong
    {
        long apply(final Object p0, final Object p1);
    }
    
    public interface LongByLongToLong
    {
        long apply(final long p0, final long p1);
    }
    
    static final class MapReduceEntriesToLongTask extends BulkTask
    {
        final ObjectToLong transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceEntriesToLongTask rights;
        MapReduceEntriesToLongTask nextRight;
        
        MapReduceEntriesToLongTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceEntriesToLongTask nextRight, final ObjectToLong transformer, final long basis, final LongByLongToLong reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToLong transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceEntriesToLongTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceEntriesToLongTask mapReduceEntriesToLongTask = (MapReduceEntriesToLongTask)countedCompleter;
                    MapReduceEntriesToLongTask nextRight;
                    for (MapReduceEntriesToLongTask rights = mapReduceEntriesToLongTask.rights; rights != null; rights = nextRight) {
                        mapReduceEntriesToLongTask.result = reducer.apply(mapReduceEntriesToLongTask.result, rights.result);
                        final MapReduceEntriesToLongTask mapReduceEntriesToLongTask2 = mapReduceEntriesToLongTask;
                        nextRight = rights.nextRight;
                        mapReduceEntriesToLongTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    public interface ObjectToLong
    {
        long apply(final Object p0);
    }
    
    static final class MapReduceValuesToLongTask extends BulkTask
    {
        final ObjectToLong transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceValuesToLongTask rights;
        MapReduceValuesToLongTask nextRight;
        
        MapReduceValuesToLongTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceValuesToLongTask nextRight, final ObjectToLong transformer, final long basis, final LongByLongToLong reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToLong transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceValuesToLongTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.val));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceValuesToLongTask mapReduceValuesToLongTask = (MapReduceValuesToLongTask)countedCompleter;
                    MapReduceValuesToLongTask nextRight;
                    for (MapReduceValuesToLongTask rights = mapReduceValuesToLongTask.rights; rights != null; rights = nextRight) {
                        mapReduceValuesToLongTask.result = reducer.apply(mapReduceValuesToLongTask.result, rights.result);
                        final MapReduceValuesToLongTask mapReduceValuesToLongTask2 = mapReduceValuesToLongTask;
                        nextRight = rights.nextRight;
                        mapReduceValuesToLongTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class MapReduceKeysToLongTask extends BulkTask
    {
        final ObjectToLong transformer;
        final LongByLongToLong reducer;
        final long basis;
        long result;
        MapReduceKeysToLongTask rights;
        MapReduceKeysToLongTask nextRight;
        
        MapReduceKeysToLongTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceKeysToLongTask nextRight, final ObjectToLong transformer, final long basis, final LongByLongToLong reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Long getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToLong transformer;
            final LongByLongToLong reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                long result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceKeysToLongTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.key));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceKeysToLongTask mapReduceKeysToLongTask = (MapReduceKeysToLongTask)countedCompleter;
                    MapReduceKeysToLongTask nextRight;
                    for (MapReduceKeysToLongTask rights = mapReduceKeysToLongTask.rights; rights != null; rights = nextRight) {
                        mapReduceKeysToLongTask.result = reducer.apply(mapReduceKeysToLongTask.result, rights.result);
                        final MapReduceKeysToLongTask mapReduceKeysToLongTask2 = mapReduceKeysToLongTask;
                        nextRight = rights.nextRight;
                        mapReduceKeysToLongTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class MapReduceMappingsToDoubleTask extends BulkTask
    {
        final ObjectByObjectToDouble transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceMappingsToDoubleTask rights;
        MapReduceMappingsToDoubleTask nextRight;
        
        MapReduceMappingsToDoubleTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceMappingsToDoubleTask nextRight, final ObjectByObjectToDouble transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectByObjectToDouble transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceMappingsToDoubleTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.key, advance.val));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask = (MapReduceMappingsToDoubleTask)countedCompleter;
                    MapReduceMappingsToDoubleTask nextRight;
                    for (MapReduceMappingsToDoubleTask rights = mapReduceMappingsToDoubleTask.rights; rights != null; rights = nextRight) {
                        mapReduceMappingsToDoubleTask.result = reducer.apply(mapReduceMappingsToDoubleTask.result, rights.result);
                        final MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask2 = mapReduceMappingsToDoubleTask;
                        nextRight = rights.nextRight;
                        mapReduceMappingsToDoubleTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    public interface ObjectByObjectToDouble
    {
        double apply(final Object p0, final Object p1);
    }
    
    public interface DoubleByDoubleToDouble
    {
        double apply(final double p0, final double p1);
    }
    
    static final class MapReduceEntriesToDoubleTask extends BulkTask
    {
        final ObjectToDouble transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceEntriesToDoubleTask rights;
        MapReduceEntriesToDoubleTask nextRight;
        
        MapReduceEntriesToDoubleTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceEntriesToDoubleTask nextRight, final ObjectToDouble transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToDouble transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceEntriesToDoubleTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask = (MapReduceEntriesToDoubleTask)countedCompleter;
                    MapReduceEntriesToDoubleTask nextRight;
                    for (MapReduceEntriesToDoubleTask rights = mapReduceEntriesToDoubleTask.rights; rights != null; rights = nextRight) {
                        mapReduceEntriesToDoubleTask.result = reducer.apply(mapReduceEntriesToDoubleTask.result, rights.result);
                        final MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask2 = mapReduceEntriesToDoubleTask;
                        nextRight = rights.nextRight;
                        mapReduceEntriesToDoubleTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    public interface ObjectToDouble
    {
        double apply(final Object p0);
    }
    
    static final class MapReduceValuesToDoubleTask extends BulkTask
    {
        final ObjectToDouble transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceValuesToDoubleTask rights;
        MapReduceValuesToDoubleTask nextRight;
        
        MapReduceValuesToDoubleTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceValuesToDoubleTask nextRight, final ObjectToDouble transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToDouble transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceValuesToDoubleTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.val));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask = (MapReduceValuesToDoubleTask)countedCompleter;
                    MapReduceValuesToDoubleTask nextRight;
                    for (MapReduceValuesToDoubleTask rights = mapReduceValuesToDoubleTask.rights; rights != null; rights = nextRight) {
                        mapReduceValuesToDoubleTask.result = reducer.apply(mapReduceValuesToDoubleTask.result, rights.result);
                        final MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask2 = mapReduceValuesToDoubleTask;
                        nextRight = rights.nextRight;
                        mapReduceValuesToDoubleTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class MapReduceKeysToDoubleTask extends BulkTask
    {
        final ObjectToDouble transformer;
        final DoubleByDoubleToDouble reducer;
        final double basis;
        double result;
        MapReduceKeysToDoubleTask rights;
        MapReduceKeysToDoubleTask nextRight;
        
        MapReduceKeysToDoubleTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceKeysToDoubleTask nextRight, final ObjectToDouble transformer, final double basis, final DoubleByDoubleToDouble reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.basis = basis;
            this.reducer = reducer;
        }
        
        @Override
        public final Double getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final ObjectToDouble transformer;
            final DoubleByDoubleToDouble reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                double result = this.basis;
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceKeysToDoubleTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, result, reducer)).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = reducer.apply(result, transformer.apply(advance.key));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask = (MapReduceKeysToDoubleTask)countedCompleter;
                    MapReduceKeysToDoubleTask nextRight;
                    for (MapReduceKeysToDoubleTask rights = mapReduceKeysToDoubleTask.rights; rights != null; rights = nextRight) {
                        mapReduceKeysToDoubleTask.result = reducer.apply(mapReduceKeysToDoubleTask.result, rights.result);
                        final MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask2 = mapReduceKeysToDoubleTask;
                        nextRight = rights.nextRight;
                        mapReduceKeysToDoubleTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class MapReduceMappingsTask extends BulkTask
    {
        final BiFun transformer;
        final BiFun reducer;
        Object result;
        MapReduceMappingsTask rights;
        MapReduceMappingsTask nextRight;
        
        MapReduceMappingsTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceMappingsTask nextRight, final BiFun transformer, final BiFun reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun transformer;
            final BiFun reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceMappingsTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, reducer)).fork();
                }
                Object result = null;
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance.key, advance.val)) != null) {
                        result = ((result == null) ? apply : reducer.apply(result, apply));
                    }
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceMappingsTask mapReduceMappingsTask = (MapReduceMappingsTask)countedCompleter;
                    MapReduceMappingsTask nextRight;
                    for (MapReduceMappingsTask rights = mapReduceMappingsTask.rights; rights != null; rights = nextRight) {
                        final Object result2;
                        if ((result2 = rights.result) != null) {
                            final Object result3;
                            mapReduceMappingsTask.result = (((result3 = mapReduceMappingsTask.result) == null) ? result2 : reducer.apply(result3, result2));
                        }
                        final MapReduceMappingsTask mapReduceMappingsTask2 = mapReduceMappingsTask;
                        nextRight = rights.nextRight;
                        mapReduceMappingsTask2.rights = nextRight;
                    }
                }
            }
        }
    }
    
    public interface BiFun
    {
        Object apply(final Object p0, final Object p1);
    }
    
    static final class MapReduceEntriesTask extends BulkTask
    {
        final Fun transformer;
        final BiFun reducer;
        Object result;
        MapReduceEntriesTask rights;
        MapReduceEntriesTask nextRight;
        
        MapReduceEntriesTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceEntriesTask nextRight, final Fun transformer, final BiFun reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final Fun transformer;
            final BiFun reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceEntriesTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, reducer)).fork();
                }
                Object result = null;
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance)) != null) {
                        result = ((result == null) ? apply : reducer.apply(result, apply));
                    }
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceEntriesTask mapReduceEntriesTask = (MapReduceEntriesTask)countedCompleter;
                    MapReduceEntriesTask nextRight;
                    for (MapReduceEntriesTask rights = mapReduceEntriesTask.rights; rights != null; rights = nextRight) {
                        final Object result2;
                        if ((result2 = rights.result) != null) {
                            final Object result3;
                            mapReduceEntriesTask.result = (((result3 = mapReduceEntriesTask.result) == null) ? result2 : reducer.apply(result3, result2));
                        }
                        final MapReduceEntriesTask mapReduceEntriesTask2 = mapReduceEntriesTask;
                        nextRight = rights.nextRight;
                        mapReduceEntriesTask2.rights = nextRight;
                    }
                }
            }
        }
    }
    
    public interface Fun
    {
        Object apply(final Object p0);
    }
    
    static final class MapReduceValuesTask extends BulkTask
    {
        final Fun transformer;
        final BiFun reducer;
        Object result;
        MapReduceValuesTask rights;
        MapReduceValuesTask nextRight;
        
        MapReduceValuesTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceValuesTask nextRight, final Fun transformer, final BiFun reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final Fun transformer;
            final BiFun reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceValuesTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, reducer)).fork();
                }
                Object result = null;
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance.val)) != null) {
                        result = ((result == null) ? apply : reducer.apply(result, apply));
                    }
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceValuesTask mapReduceValuesTask = (MapReduceValuesTask)countedCompleter;
                    MapReduceValuesTask nextRight;
                    for (MapReduceValuesTask rights = mapReduceValuesTask.rights; rights != null; rights = nextRight) {
                        final Object result2;
                        if ((result2 = rights.result) != null) {
                            final Object result3;
                            mapReduceValuesTask.result = (((result3 = mapReduceValuesTask.result) == null) ? result2 : reducer.apply(result3, result2));
                        }
                        final MapReduceValuesTask mapReduceValuesTask2 = mapReduceValuesTask;
                        nextRight = rights.nextRight;
                        mapReduceValuesTask2.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class MapReduceKeysTask extends BulkTask
    {
        final Fun transformer;
        final BiFun reducer;
        Object result;
        MapReduceKeysTask rights;
        MapReduceKeysTask nextRight;
        
        MapReduceKeysTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final MapReduceKeysTask nextRight, final Fun transformer, final BiFun reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.transformer = transformer;
            this.reducer = reducer;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final Fun transformer;
            final BiFun reducer;
            if ((transformer = this.transformer) != null && (reducer = this.reducer) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new MapReduceKeysTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, transformer, reducer)).fork();
                }
                Object result = null;
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance.key)) != null) {
                        result = ((result == null) ? apply : reducer.apply(result, apply));
                    }
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final MapReduceKeysTask mapReduceKeysTask = (MapReduceKeysTask)countedCompleter;
                    MapReduceKeysTask nextRight;
                    for (MapReduceKeysTask rights = mapReduceKeysTask.rights; rights != null; rights = nextRight) {
                        final Object result2;
                        if ((result2 = rights.result) != null) {
                            final Object result3;
                            mapReduceKeysTask.result = (((result3 = mapReduceKeysTask.result) == null) ? result2 : reducer.apply(result3, result2));
                        }
                        final MapReduceKeysTask mapReduceKeysTask2 = mapReduceKeysTask;
                        nextRight = rights.nextRight;
                        mapReduceKeysTask2.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class ReduceEntriesTask extends BulkTask
    {
        final BiFun reducer;
        Map.Entry result;
        ReduceEntriesTask rights;
        ReduceEntriesTask nextRight;
        
        ReduceEntriesTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final ReduceEntriesTask nextRight, final BiFun reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }
        
        @Override
        public final Map.Entry getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun reducer;
            if ((reducer = this.reducer) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new ReduceEntriesTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, reducer)).fork();
                }
                Map.Entry result = null;
                Node advance;
                while ((advance = this.advance()) != null) {
                    result = ((result == null) ? advance : ((Map.Entry)reducer.apply(result, advance)));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final ReduceEntriesTask reduceEntriesTask = (ReduceEntriesTask)countedCompleter;
                    ReduceEntriesTask nextRight;
                    for (ReduceEntriesTask rights = reduceEntriesTask.rights; rights != null; rights = nextRight) {
                        final Map.Entry result2;
                        if ((result2 = rights.result) != null) {
                            final Map.Entry result3;
                            reduceEntriesTask.result = (Map.Entry)(((result3 = reduceEntriesTask.result) == null) ? result2 : reducer.apply(result3, result2));
                        }
                        final ReduceEntriesTask reduceEntriesTask2 = reduceEntriesTask;
                        nextRight = rights.nextRight;
                        reduceEntriesTask2.rights = nextRight;
                    }
                }
            }
        }
        
        @Override
        public Object getRawResult() {
            return this.getRawResult();
        }
    }
    
    static final class ReduceValuesTask extends BulkTask
    {
        final BiFun reducer;
        Object result;
        ReduceValuesTask rights;
        ReduceValuesTask nextRight;
        
        ReduceValuesTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final ReduceValuesTask nextRight, final BiFun reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun reducer;
            if ((reducer = this.reducer) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new ReduceValuesTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, reducer)).fork();
                }
                Object result = null;
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object val = advance.val;
                    result = ((result == null) ? val : reducer.apply(result, val));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final ReduceValuesTask reduceValuesTask = (ReduceValuesTask)countedCompleter;
                    ReduceValuesTask nextRight;
                    for (ReduceValuesTask rights = reduceValuesTask.rights; rights != null; rights = nextRight) {
                        final Object result2;
                        if ((result2 = rights.result) != null) {
                            final Object result3;
                            reduceValuesTask.result = (((result3 = reduceValuesTask.result) == null) ? result2 : reducer.apply(result3, result2));
                        }
                        final ReduceValuesTask reduceValuesTask2 = reduceValuesTask;
                        nextRight = rights.nextRight;
                        reduceValuesTask2.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class ReduceKeysTask extends BulkTask
    {
        final BiFun reducer;
        Object result;
        ReduceKeysTask rights;
        ReduceKeysTask nextRight;
        
        ReduceKeysTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final ReduceKeysTask nextRight, final BiFun reducer) {
            super(bulkTask, n, n2, n3, array);
            this.nextRight = nextRight;
            this.reducer = reducer;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result;
        }
        
        @Override
        public final void compute() {
            final BiFun reducer;
            if ((reducer = this.reducer) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    (this.rights = new ReduceKeysTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, this.rights, reducer)).fork();
                }
                Object result = null;
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object key = advance.key;
                    result = ((result == null) ? key : ((key == null) ? result : reducer.apply(result, key)));
                }
                this.result = result;
                for (CountedCompleter countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    final ReduceKeysTask reduceKeysTask = (ReduceKeysTask)countedCompleter;
                    ReduceKeysTask nextRight;
                    for (ReduceKeysTask rights = reduceKeysTask.rights; rights != null; rights = nextRight) {
                        final Object result2;
                        if ((result2 = rights.result) != null) {
                            final Object result3;
                            reduceKeysTask.result = (((result3 = reduceKeysTask.result) == null) ? result2 : reducer.apply(result3, result2));
                        }
                        final ReduceKeysTask reduceKeysTask2 = reduceKeysTask;
                        nextRight = rights.nextRight;
                        reduceKeysTask2.rights = nextRight;
                    }
                }
            }
        }
    }
    
    static final class SearchMappingsTask extends BulkTask
    {
        final BiFun searchFunction;
        final AtomicReference result;
        
        SearchMappingsTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final BiFun searchFunction, final AtomicReference result) {
            super(bulkTask, n, n2, n3, array);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final BiFun searchFunction;
            final AtomicReference result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    if (result.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new SearchMappingsTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null) {
                    final Node advance;
                    if ((advance = this.advance()) == null) {
                        this.propagateCompletion();
                        break;
                    }
                    final Object apply;
                    if ((apply = searchFunction.apply(advance.key, advance.val)) == null) {
                        continue;
                    }
                    if (result.compareAndSet(null, apply)) {
                        this.quietlyCompleteRoot();
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    static final class SearchEntriesTask extends BulkTask
    {
        final Fun searchFunction;
        final AtomicReference result;
        
        SearchEntriesTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Fun searchFunction, final AtomicReference result) {
            super(bulkTask, n, n2, n3, array);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final Fun searchFunction;
            final AtomicReference result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    if (result.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new SearchEntriesTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null) {
                    final Node advance;
                    if ((advance = this.advance()) == null) {
                        this.propagateCompletion();
                        break;
                    }
                    final Object apply;
                    if ((apply = searchFunction.apply(advance)) != null) {
                        if (result.compareAndSet(null, apply)) {
                            this.quietlyCompleteRoot();
                        }
                    }
                }
            }
        }
    }
    
    static final class SearchValuesTask extends BulkTask
    {
        final Fun searchFunction;
        final AtomicReference result;
        
        SearchValuesTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Fun searchFunction, final AtomicReference result) {
            super(bulkTask, n, n2, n3, array);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final Fun searchFunction;
            final AtomicReference result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    if (result.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new SearchValuesTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null) {
                    final Node advance;
                    if ((advance = this.advance()) == null) {
                        this.propagateCompletion();
                        break;
                    }
                    final Object apply;
                    if ((apply = searchFunction.apply(advance.val)) == null) {
                        continue;
                    }
                    if (result.compareAndSet(null, apply)) {
                        this.quietlyCompleteRoot();
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    static final class SearchKeysTask extends BulkTask
    {
        final Fun searchFunction;
        final AtomicReference result;
        
        SearchKeysTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Fun searchFunction, final AtomicReference result) {
            super(bulkTask, n, n2, n3, array);
            this.searchFunction = searchFunction;
            this.result = result;
        }
        
        @Override
        public final Object getRawResult() {
            return this.result.get();
        }
        
        @Override
        public final void compute() {
            final Fun searchFunction;
            final AtomicReference result;
            if ((searchFunction = this.searchFunction) != null && (result = this.result) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    if (result.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new SearchKeysTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, searchFunction, result).fork();
                }
                while (result.get() == null) {
                    final Node advance;
                    if ((advance = this.advance()) == null) {
                        this.propagateCompletion();
                        break;
                    }
                    final Object apply;
                    if ((apply = searchFunction.apply(advance.key)) == null) {
                        continue;
                    }
                    if (result.compareAndSet(null, apply)) {
                        this.quietlyCompleteRoot();
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    static final class ForEachTransformedMappingTask extends BulkTask
    {
        final BiFun transformer;
        final Action action;
        
        ForEachTransformedMappingTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final BiFun transformer, final Action action) {
            super(bulkTask, n, n2, n3, array);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final BiFun transformer;
            final Action action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachTransformedMappingTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, transformer, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance.key, advance.val)) != null) {
                        action.apply(apply);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    public interface Action
    {
        void apply(final Object p0);
    }
    
    static final class ForEachTransformedEntryTask extends BulkTask
    {
        final Fun transformer;
        final Action action;
        
        ForEachTransformedEntryTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Fun transformer, final Action action) {
            super(bulkTask, n, n2, n3, array);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Fun transformer;
            final Action action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachTransformedEntryTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, transformer, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance)) != null) {
                        action.apply(apply);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachTransformedValueTask extends BulkTask
    {
        final Fun transformer;
        final Action action;
        
        ForEachTransformedValueTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Fun transformer, final Action action) {
            super(bulkTask, n, n2, n3, array);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Fun transformer;
            final Action action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachTransformedValueTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, transformer, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance.val)) != null) {
                        action.apply(apply);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachTransformedKeyTask extends BulkTask
    {
        final Fun transformer;
        final Action action;
        
        ForEachTransformedKeyTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Fun transformer, final Action action) {
            super(bulkTask, n, n2, n3, array);
            this.transformer = transformer;
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Fun transformer;
            final Action action;
            if ((transformer = this.transformer) != null && (action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachTransformedKeyTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, transformer, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    final Object apply;
                    if ((apply = transformer.apply(advance.key)) != null) {
                        action.apply(apply);
                    }
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachMappingTask extends BulkTask
    {
        final BiAction action;
        
        ForEachMappingTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final BiAction action) {
            super(bulkTask, n, n2, n3, array);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final BiAction action;
            if ((action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachMappingTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    action.apply(advance.key, advance.val);
                }
                this.propagateCompletion();
            }
        }
    }
    
    public interface BiAction
    {
        void apply(final Object p0, final Object p1);
    }
    
    static final class ForEachEntryTask extends BulkTask
    {
        final Action action;
        
        ForEachEntryTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Action action) {
            super(bulkTask, n, n2, n3, array);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Action action;
            if ((action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachEntryTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    action.apply(advance);
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachValueTask extends BulkTask
    {
        final Action action;
        
        ForEachValueTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Action action) {
            super(bulkTask, n, n2, n3, array);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Action action;
            if ((action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachValueTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    action.apply(advance.val);
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class ForEachKeyTask extends BulkTask
    {
        final Action action;
        
        ForEachKeyTask(final BulkTask bulkTask, final int n, final int n2, final int n3, final Node[] array, final Action action) {
            super(bulkTask, n, n2, n3, array);
            this.action = action;
        }
        
        @Override
        public final void compute() {
            final Action action;
            if ((action = this.action) != null) {
                final int baseIndex = this.baseIndex;
                int baseLimit2;
                int baseLimit;
                while (this.batch > 0 && (baseLimit = (baseLimit2 = this.baseLimit) + baseIndex >>> 1) > baseIndex) {
                    this.addToPendingCount(1);
                    final int batch = this.batch >>> 1;
                    this.batch = batch;
                    new ForEachKeyTask(this, batch, this.baseLimit = baseLimit, baseLimit2, this.tab, action).fork();
                }
                Node advance;
                while ((advance = this.advance()) != null) {
                    action.apply(advance.key);
                }
                this.propagateCompletion();
            }
        }
    }
    
    static final class EntrySetView extends CollectionView implements Set, Serializable
    {
        private static final long serialVersionUID = 2249069246763182397L;
        
        EntrySetView(final ConcurrentHashMapV8 concurrentHashMapV8) {
            super(concurrentHashMapV8);
        }
        
        @Override
        public boolean contains(final Object o) {
            final Map.Entry entry;
            final Object key;
            final Object value;
            final Object value2;
            return o instanceof Map.Entry && (key = (entry = (Map.Entry)o).getKey()) != null && (value = this.map.get(key)) != null && (value2 = entry.getValue()) != null && (value2 == value || value2.equals(value));
        }
        
        @Override
        public boolean remove(final Object o) {
            final Map.Entry entry;
            final Object key;
            final Object value;
            return o instanceof Map.Entry && (key = (entry = (Map.Entry)o).getKey()) != null && (value = entry.getValue()) != null && this.map.remove(key, value);
        }
        
        @Override
        public Iterator iterator() {
            final ConcurrentHashMapV8 map = this.map;
            final Node[] table;
            final int n = ((table = map.table) == null) ? 0 : table.length;
            return new EntryIterator(table, n, 0, n, map);
        }
        
        @Override
        public boolean addAll(final Collection p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
            //     6: astore_3       
            //     7: aload_3        
            //     8: invokeinterface java/util/Iterator.hasNext:()Z
            //    13: ifeq            36
            //    16: aload_3        
            //    17: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //    22: checkcast       Ljava/util/Map$Entry;
            //    25: astore          4
            //    27: aload_0        
            //    28: aload           4
            //    30: ifnonnull       33
            //    33: goto            7
            //    36: iconst_1       
            //    37: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0007 (coming from #0033).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
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
        public final int hashCode() {
            final Node[] table;
            if ((table = this.map.table) != null) {
                Node advance;
                while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                    final int n = 0 + advance.hashCode();
                }
            }
            return 0;
        }
        
        @Override
        public final boolean equals(final Object o) {
            final Set set;
            return o instanceof Set && ((set = (Set)o) == this || (this.containsAll(set) && set.containsAll(this)));
        }
        
        public ConcurrentHashMapSpliterator spliterator166() {
            final ConcurrentHashMapV8 map = this.map;
            final long sumCount = map.sumCount();
            final Node[] table;
            final int n = ((table = map.table) == null) ? 0 : table.length;
            return new EntrySpliterator(table, n, 0, n, (sumCount < 0L) ? 0L : sumCount, map);
        }
        
        public void forEach(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            final Node[] table;
            if ((table = this.map.table) != null) {
                Node advance;
                while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                    action.apply(new MapEntry(advance.key, advance.val, this.map));
                }
            }
        }
        
        @Override
        public boolean add(final Object o) {
            return this.add((Map.Entry)o);
        }
    }
    
    static class Traverser
    {
        Node[] tab;
        Node next;
        int index;
        int baseIndex;
        int baseLimit;
        final int baseSize;
        
        Traverser(final Node[] tab, final int baseSize, final int n, final int baseLimit) {
            this.tab = tab;
            this.baseSize = baseSize;
            this.index = n;
            this.baseIndex = n;
            this.baseLimit = baseLimit;
            this.next = null;
        }
        
        final Node advance() {
            Node next;
            if ((next = this.next) != null) {
                next = next.next;
            }
            while (next == null) {
                final Node[] tab;
                final int length;
                final int index;
                if (this.baseIndex >= this.baseLimit || (tab = this.tab) == null || (length = tab.length) <= (index = this.index) || index < 0) {
                    return this.next = null;
                }
                if ((next = ConcurrentHashMapV8.tabAt(tab, this.index)) != null && next.hash < 0) {
                    if (next instanceof ForwardingNode) {
                        this.tab = ((ForwardingNode)next).nextTable;
                        next = null;
                        continue;
                    }
                    if (next instanceof TreeBin) {
                        next = ((TreeBin)next).first;
                    }
                    else {
                        next = null;
                    }
                }
                if ((this.index += this.baseSize) < length) {
                    continue;
                }
                this.index = ++this.baseIndex;
            }
            return this.next = next;
        }
    }
    
    public interface ConcurrentHashMapSpliterator
    {
        ConcurrentHashMapSpliterator trySplit();
        
        long estimateSize();
        
        void forEachRemaining(final Action p0);
        
        boolean tryAdvance(final Action p0);
    }
    
    abstract static class CollectionView implements Collection, Serializable
    {
        private static final long serialVersionUID = 7249069246763182397L;
        final ConcurrentHashMapV8 map;
        private static final String oomeMsg = "Required array size too large";
        
        CollectionView(final ConcurrentHashMapV8 map) {
            this.map = map;
        }
        
        public ConcurrentHashMapV8 getMap() {
            return this.map;
        }
        
        @Override
        public final void clear() {
            this.map.clear();
        }
        
        @Override
        public final int size() {
            return this.map.size();
        }
        
        @Override
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }
        
        @Override
        public abstract Iterator iterator();
        
        @Override
        public abstract boolean contains(final Object p0);
        
        @Override
        public abstract boolean remove(final Object p0);
        
        @Override
        public final Object[] toArray() {
            final long mappingCount = this.map.mappingCount();
            if (mappingCount > 2147483639L) {
                throw new OutOfMemoryError("Required array size too large");
            }
            final int n = (int)mappingCount;
            Object[] copy = new Object[2147483639];
            for (final Object next : this) {
                if (0 == 2147483639) {
                    if (2147483639 >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (2147483639 >= 1073741819) {}
                    copy = Arrays.copyOf(copy, 2147483639);
                }
                final Object[] array = copy;
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                array[n2] = next;
            }
            return (0 == 2147483639) ? copy : Arrays.copyOf(copy, 0);
        }
        
        @Override
        public final Object[] toArray(final Object[] array) {
            final long mappingCount = this.map.mappingCount();
            if (mappingCount > 2147483639L) {
                throw new OutOfMemoryError("Required array size too large");
            }
            final int n = (int)mappingCount;
            Object[] copy = (Object[])((array.length >= n) ? array : Array.newInstance(array.getClass().getComponentType(), n));
            final int length = copy.length;
            for (final Object next : this) {
                if (0 == 2147483639) {
                    if (2147483639 >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    if (2147483639 >= 1073741819) {}
                    copy = Arrays.copyOf(copy, 2147483639);
                }
                final Object[] array2 = copy;
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                array2[n2] = next;
            }
            if (array == copy && 0 < 2147483639) {
                copy[0] = null;
                return copy;
            }
            return (0 == 2147483639) ? copy : Arrays.copyOf(copy, 0);
        }
        
        @Override
        public final String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append('[');
            final Iterator iterator = this.iterator();
            if (iterator.hasNext()) {
                while (true) {
                    final CollectionView next = iterator.next();
                    sb.append((next == this) ? "(this Collection)" : next);
                    if (!iterator.hasNext()) {
                        break;
                    }
                    sb.append(',').append(' ');
                }
            }
            return sb.append(']').toString();
        }
        
        @Override
        public final boolean containsAll(final Collection collection) {
            if (collection != this) {
                for (final Object next : collection) {
                    if (next == null || !this.contains(next)) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        @Override
        public final boolean removeAll(final Collection collection) {
            final Iterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
        
        @Override
        public final boolean retainAll(final Collection collection) {
            final Iterator iterator = this.iterator();
            while (iterator.hasNext()) {
                if (!collection.contains(iterator.next())) {
                    iterator.remove();
                }
            }
            return true;
        }
    }
    
    static final class EntryIterator extends BaseIterator implements Iterator
    {
        EntryIterator(final Node[] array, final int n, final int n2, final int n3, final ConcurrentHashMapV8 concurrentHashMapV8) {
            super(array, n, n2, n3, concurrentHashMapV8);
        }
        
        @Override
        public final Map.Entry next() {
            final Node next;
            if ((next = this.next) == null) {
                throw new NoSuchElementException();
            }
            final Object key = next.key;
            final Object val = next.val;
            this.lastReturned = next;
            this.advance();
            return new MapEntry(key, val, this.map);
        }
        
        @Override
        public Object next() {
            return this.next();
        }
    }
    
    static class BaseIterator extends Traverser
    {
        final ConcurrentHashMapV8 map;
        Node lastReturned;
        
        BaseIterator(final Node[] array, final int n, final int n2, final int n3, final ConcurrentHashMapV8 map) {
            super(array, n, n2, n3);
            this.map = map;
            this.advance();
        }
        
        public final boolean hasNext() {
            return this.next != null;
        }
        
        public final boolean hasMoreElements() {
            return this.next != null;
        }
        
        public final void remove() {
            final Node lastReturned;
            if ((lastReturned = this.lastReturned) == null) {
                throw new IllegalStateException();
            }
            this.lastReturned = null;
            this.map.replaceNode(lastReturned.key, null, null);
        }
    }
    
    static final class MapEntry implements Map.Entry
    {
        final Object key;
        Object val;
        final ConcurrentHashMapV8 map;
        
        MapEntry(final Object key, final Object val, final ConcurrentHashMapV8 map) {
            this.key = key;
            this.val = val;
            this.map = map;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public Object getValue() {
            return this.val;
        }
        
        @Override
        public int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }
        
        @Override
        public String toString() {
            return this.key + "=" + this.val;
        }
        
        @Override
        public boolean equals(final Object o) {
            final Map.Entry entry;
            final Object key;
            final Object value;
            return o instanceof Map.Entry && (key = (entry = (Map.Entry)o).getKey()) != null && (value = entry.getValue()) != null && (key == this.key || key.equals(this.key)) && (value == this.val || value.equals(this.val));
        }
        
        @Override
        public Object setValue(final Object val) {
            if (val == null) {
                throw new NullPointerException();
            }
            final Object val2 = this.val;
            this.val = val;
            this.map.put(this.key, val);
            return val2;
        }
    }
    
    static final class EntrySpliterator extends Traverser implements ConcurrentHashMapSpliterator
    {
        final ConcurrentHashMapV8 map;
        long est;
        
        EntrySpliterator(final Node[] array, final int n, final int n2, final int n3, final long est, final ConcurrentHashMapV8 map) {
            super(array, n, n2, n3);
            this.map = map;
            this.est = est;
        }
        
        @Override
        public ConcurrentHashMapSpliterator trySplit() {
            final int baseIndex = this.baseIndex;
            final int baseLimit;
            final int n;
            ConcurrentHashMapSpliterator concurrentHashMapSpliterator;
            if ((n = baseIndex + (baseLimit = this.baseLimit) >>> 1) <= baseIndex) {
                concurrentHashMapSpliterator = null;
            }
            else {
                final Node[] tab;
                final int baseSize;
                final int baseLimit2;
                concurrentHashMapSpliterator = new EntrySpliterator(tab, baseSize, baseLimit2, baseLimit, this.est >>>= 1, this.map);
                tab = this.tab;
                baseSize = this.baseSize;
                baseLimit2 = n;
                this.baseLimit = baseLimit2;
            }
            return concurrentHashMapSpliterator;
        }
        
        @Override
        public void forEachRemaining(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            Node advance;
            while ((advance = this.advance()) != null) {
                action.apply(new MapEntry(advance.key, advance.val, this.map));
            }
        }
        
        @Override
        public boolean tryAdvance(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            final Node advance;
            if ((advance = this.advance()) == null) {
                return false;
            }
            action.apply(new MapEntry(advance.key, advance.val, this.map));
            return true;
        }
        
        @Override
        public long estimateSize() {
            return this.est;
        }
    }
    
    static final class ValuesView extends CollectionView implements Collection, Serializable
    {
        private static final long serialVersionUID = 2249069246763182397L;
        
        ValuesView(final ConcurrentHashMapV8 concurrentHashMapV8) {
            super(concurrentHashMapV8);
        }
        
        @Override
        public final boolean contains(final Object o) {
            return this.map.containsValue(o);
        }
        
        @Override
        public final boolean remove(final Object o) {
            if (o != null) {
                final Iterator iterator = this.iterator();
                while (iterator.hasNext()) {
                    if (o.equals(iterator.next())) {
                        iterator.remove();
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Override
        public final Iterator iterator() {
            final ConcurrentHashMapV8 map = this.map;
            final Node[] table;
            final int n = ((table = map.table) == null) ? 0 : table.length;
            return new ValueIterator(table, n, 0, n, map);
        }
        
        @Override
        public final boolean add(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public final boolean addAll(final Collection collection) {
            throw new UnsupportedOperationException();
        }
        
        public ConcurrentHashMapSpliterator spliterator166() {
            final ConcurrentHashMapV8 map = this.map;
            final long sumCount = map.sumCount();
            final Node[] table;
            final int n = ((table = map.table) == null) ? 0 : table.length;
            return new ValueSpliterator(table, n, 0, n, (sumCount < 0L) ? 0L : sumCount);
        }
        
        public void forEach(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            final Node[] table;
            if ((table = this.map.table) != null) {
                Node advance;
                while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                    action.apply(advance.val);
                }
            }
        }
    }
    
    static final class ValueIterator extends BaseIterator implements Iterator, Enumeration
    {
        ValueIterator(final Node[] array, final int n, final int n2, final int n3, final ConcurrentHashMapV8 concurrentHashMapV8) {
            super(array, n, n2, n3, concurrentHashMapV8);
        }
        
        @Override
        public final Object next() {
            final Node next;
            if ((next = this.next) == null) {
                throw new NoSuchElementException();
            }
            final Object val = next.val;
            this.lastReturned = next;
            this.advance();
            return val;
        }
        
        @Override
        public final Object nextElement() {
            return this.next();
        }
    }
    
    static final class ValueSpliterator extends Traverser implements ConcurrentHashMapSpliterator
    {
        long est;
        
        ValueSpliterator(final Node[] array, final int n, final int n2, final int n3, final long est) {
            super(array, n, n2, n3);
            this.est = est;
        }
        
        @Override
        public ConcurrentHashMapSpliterator trySplit() {
            final int baseIndex = this.baseIndex;
            final int baseLimit;
            final int n;
            ConcurrentHashMapSpliterator concurrentHashMapSpliterator;
            if ((n = baseIndex + (baseLimit = this.baseLimit) >>> 1) <= baseIndex) {
                concurrentHashMapSpliterator = null;
            }
            else {
                final Node[] tab;
                final int baseSize;
                final int baseLimit2;
                concurrentHashMapSpliterator = new ValueSpliterator(tab, baseSize, baseLimit2, baseLimit, this.est >>>= 1);
                tab = this.tab;
                baseSize = this.baseSize;
                baseLimit2 = n;
                this.baseLimit = baseLimit2;
            }
            return concurrentHashMapSpliterator;
        }
        
        @Override
        public void forEachRemaining(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            Node advance;
            while ((advance = this.advance()) != null) {
                action.apply(advance.val);
            }
        }
        
        @Override
        public boolean tryAdvance(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            final Node advance;
            if ((advance = this.advance()) == null) {
                return false;
            }
            action.apply(advance.val);
            return true;
        }
        
        @Override
        public long estimateSize() {
            return this.est;
        }
    }
    
    public static class KeySetView extends CollectionView implements Set, Serializable
    {
        private static final long serialVersionUID = 7249069246763182397L;
        private final Object value;
        
        KeySetView(final ConcurrentHashMapV8 concurrentHashMapV8, final Object value) {
            super(concurrentHashMapV8);
            this.value = value;
        }
        
        public Object getMappedValue() {
            return this.value;
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.map.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.map.remove(o) != null;
        }
        
        @Override
        public Iterator iterator() {
            final ConcurrentHashMapV8 map = this.map;
            final Node[] table;
            final int n = ((table = map.table) == null) ? 0 : table.length;
            return new KeyIterator(table, n, 0, n, map);
        }
        
        @Override
        public boolean add(final Object o) {
            final Object value;
            if ((value = this.value) == null) {
                throw new UnsupportedOperationException();
            }
            return this.map.putVal(o, value, true) == null;
        }
        
        @Override
        public boolean addAll(final Collection collection) {
            final Object value;
            if ((value = this.value) == null) {
                throw new UnsupportedOperationException();
            }
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (this.map.putVal(iterator.next(), value, true) == null) {}
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            final Iterator iterator = this.iterator();
            while (iterator.hasNext()) {
                final int n = 0 + iterator.next().hashCode();
            }
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            final Set set;
            return o instanceof Set && ((set = (Set)o) == this || (this.containsAll(set) && set.containsAll(this)));
        }
        
        public ConcurrentHashMapSpliterator spliterator166() {
            final ConcurrentHashMapV8 map = this.map;
            final long sumCount = map.sumCount();
            final Node[] table;
            final int n = ((table = map.table) == null) ? 0 : table.length;
            return new KeySpliterator(table, n, 0, n, (sumCount < 0L) ? 0L : sumCount);
        }
        
        public void forEach(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            final Node[] table;
            if ((table = this.map.table) != null) {
                Node advance;
                while ((advance = new Traverser(table, table.length, 0, table.length).advance()) != null) {
                    action.apply(advance.key);
                }
            }
        }
        
        @Override
        public ConcurrentHashMapV8 getMap() {
            return super.getMap();
        }
    }
    
    static final class KeyIterator extends BaseIterator implements Iterator, Enumeration
    {
        KeyIterator(final Node[] array, final int n, final int n2, final int n3, final ConcurrentHashMapV8 concurrentHashMapV8) {
            super(array, n, n2, n3, concurrentHashMapV8);
        }
        
        @Override
        public final Object next() {
            final Node next;
            if ((next = this.next) == null) {
                throw new NoSuchElementException();
            }
            final Object key = next.key;
            this.lastReturned = next;
            this.advance();
            return key;
        }
        
        @Override
        public final Object nextElement() {
            return this.next();
        }
    }
    
    static final class KeySpliterator extends Traverser implements ConcurrentHashMapSpliterator
    {
        long est;
        
        KeySpliterator(final Node[] array, final int n, final int n2, final int n3, final long est) {
            super(array, n, n2, n3);
            this.est = est;
        }
        
        @Override
        public ConcurrentHashMapSpliterator trySplit() {
            final int baseIndex = this.baseIndex;
            final int baseLimit;
            final int n;
            ConcurrentHashMapSpliterator concurrentHashMapSpliterator;
            if ((n = baseIndex + (baseLimit = this.baseLimit) >>> 1) <= baseIndex) {
                concurrentHashMapSpliterator = null;
            }
            else {
                final Node[] tab;
                final int baseSize;
                final int baseLimit2;
                concurrentHashMapSpliterator = new KeySpliterator(tab, baseSize, baseLimit2, baseLimit, this.est >>>= 1);
                tab = this.tab;
                baseSize = this.baseSize;
                baseLimit2 = n;
                this.baseLimit = baseLimit2;
            }
            return concurrentHashMapSpliterator;
        }
        
        @Override
        public void forEachRemaining(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            Node advance;
            while ((advance = this.advance()) != null) {
                action.apply(advance.key);
            }
        }
        
        @Override
        public boolean tryAdvance(final Action action) {
            if (action == null) {
                throw new NullPointerException();
            }
            final Node advance;
            if ((advance = this.advance()) == null) {
                return false;
            }
            action.apply(advance.key);
            return true;
        }
        
        @Override
        public long estimateSize() {
            return this.est;
        }
    }
    
    static final class ReservationNode extends Node
    {
        ReservationNode() {
            super(-3, null, null, null);
        }
        
        @Override
        Node find(final int n, final Object o) {
            return null;
        }
    }
    
    static class Segment extends ReentrantLock implements Serializable
    {
        private static final long serialVersionUID = 2249069246763182397L;
        final float loadFactor;
        
        Segment(final float loadFactor) {
            this.loadFactor = loadFactor;
        }
    }
}
