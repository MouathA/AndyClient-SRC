package optifine;

import java.util.*;

public class CompactArrayList
{
    private ArrayList list;
    private int initialCapacity;
    private float loadFactor;
    private int countValid;
    
    public CompactArrayList() {
        this(10, 0.75f);
    }
    
    public CompactArrayList(final int n) {
        this(n, 0.75f);
    }
    
    public CompactArrayList(final int initialCapacity, final float loadFactor) {
        this.list = null;
        this.initialCapacity = 0;
        this.loadFactor = 1.0f;
        this.countValid = 0;
        this.list = new ArrayList(initialCapacity);
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }
    
    public void add(final int n, final Object o) {
        if (o != null) {
            ++this.countValid;
        }
        this.list.add(n, o);
    }
    
    public boolean add(final Object o) {
        if (o != null) {
            ++this.countValid;
        }
        return this.list.add(o);
    }
    
    public Object set(final int n, final Object o) {
        final Object set = this.list.set(n, o);
        if (o != set) {
            if (set == null) {
                ++this.countValid;
            }
            if (o == null) {
                --this.countValid;
            }
        }
        return set;
    }
    
    public Object remove(final int n) {
        final Object remove = this.list.remove(n);
        if (remove != null) {
            --this.countValid;
        }
        return remove;
    }
    
    public void clear() {
        this.list.clear();
        this.countValid = 0;
    }
    
    public void compact() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        optifine/CompactArrayList.countValid:I
        //     4: ifgt            24
        //     7: aload_0        
        //     8: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //    11: invokevirtual   java/util/ArrayList.size:()I
        //    14: ifgt            24
        //    17: aload_0        
        //    18: invokevirtual   optifine/CompactArrayList.clear:()V
        //    21: goto            145
        //    24: aload_0        
        //    25: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //    28: invokevirtual   java/util/ArrayList.size:()I
        //    31: aload_0        
        //    32: getfield        optifine/CompactArrayList.initialCapacity:I
        //    35: if_icmple       145
        //    38: aload_0        
        //    39: getfield        optifine/CompactArrayList.countValid:I
        //    42: i2f            
        //    43: fconst_1       
        //    44: fmul           
        //    45: aload_0        
        //    46: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //    49: invokevirtual   java/util/ArrayList.size:()I
        //    52: i2f            
        //    53: fdiv           
        //    54: fstore_1       
        //    55: fload_1        
        //    56: aload_0        
        //    57: getfield        optifine/CompactArrayList.loadFactor:F
        //    60: fcmpg          
        //    61: ifgt            145
        //    64: goto            104
        //    67: aload_0        
        //    68: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //    71: iconst_0       
        //    72: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    75: astore          4
        //    77: aload           4
        //    79: ifnull          101
        //    82: iconst_0       
        //    83: iconst_0       
        //    84: if_icmpeq       98
        //    87: aload_0        
        //    88: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //    91: iconst_0       
        //    92: aload           4
        //    94: invokevirtual   java/util/ArrayList.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //    97: pop            
        //    98: iinc            2, 1
        //   101: iinc            3, 1
        //   104: iconst_0       
        //   105: aload_0        
        //   106: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //   109: invokevirtual   java/util/ArrayList.size:()I
        //   112: if_icmplt       67
        //   115: aload_0        
        //   116: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //   119: invokevirtual   java/util/ArrayList.size:()I
        //   122: iconst_1       
        //   123: isub           
        //   124: istore_3       
        //   125: goto            140
        //   128: aload_0        
        //   129: getfield        optifine/CompactArrayList.list:Ljava/util/ArrayList;
        //   132: iconst_0       
        //   133: invokevirtual   java/util/ArrayList.remove:(I)Ljava/lang/Object;
        //   136: pop            
        //   137: iinc            3, -1
        //   140: iconst_0       
        //   141: iconst_0       
        //   142: if_icmpge       128
        //   145: return         
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
    
    public boolean contains(final Object o) {
        return this.list.contains(o);
    }
    
    public Object get(final int n) {
        return this.list.get(n);
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public int size() {
        return this.list.size();
    }
    
    public int getCountValid() {
        return this.countValid;
    }
}
