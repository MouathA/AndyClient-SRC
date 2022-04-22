package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.*;
import com.viaversion.viaversion.libs.javassist.bytecode.*;
import com.viaversion.viaversion.libs.javassist.bytecode.stackmap.*;
import java.util.*;

public class ControlFlow
{
    private CtClass clazz;
    private MethodInfo methodInfo;
    private Block[] basicBlocks;
    private Frame[] frames;
    
    public ControlFlow(final CtMethod ctMethod) throws BadBytecode {
        this(ctMethod.getDeclaringClass(), ctMethod.getMethodInfo2());
    }
    
    public ControlFlow(final CtClass p0, final MethodInfo p1) throws BadBytecode {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/lang/Object.<init>:()V
        //     4: aload_0        
        //     5: aload_1        
        //     6: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.clazz:Lcom/viaversion/viaversion/libs/javassist/CtClass;
        //     9: aload_0        
        //    10: aload_2        
        //    11: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.methodInfo:Lcom/viaversion/viaversion/libs/javassist/bytecode/MethodInfo;
        //    14: aload_0        
        //    15: aconst_null    
        //    16: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.frames:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/Frame;
        //    19: aload_0        
        //    20: new             Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$1;
        //    23: dup            
        //    24: aload_0        
        //    25: invokespecial   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$1.<init>:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow;)V
        //    28: aload_2        
        //    29: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$1.make:(Lcom/viaversion/viaversion/libs/javassist/bytecode/MethodInfo;)[Lcom/viaversion/viaversion/libs/javassist/bytecode/stackmap/BasicBlock;
        //    32: checkcast       [Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    35: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.basicBlocks:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    38: aload_0        
        //    39: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.basicBlocks:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    42: ifnonnull       53
        //    45: aload_0        
        //    46: iconst_0       
        //    47: anewarray       Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    50: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.basicBlocks:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    53: aload_0        
        //    54: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.basicBlocks:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    57: arraylength    
        //    58: istore_3       
        //    59: iload_3        
        //    60: newarray        I
        //    62: astore          4
        //    64: iconst_0       
        //    65: iload_3        
        //    66: if_icmpge       107
        //    69: aload_0        
        //    70: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.basicBlocks:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    73: iconst_0       
        //    74: aaload         
        //    75: astore          6
        //    77: aload           6
        //    79: iconst_0       
        //    80: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.index:I
        //    83: aload           6
        //    85: aload           6
        //    87: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.incomings:()I
        //    90: anewarray       Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    93: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.entrances:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //    96: aload           4
        //    98: iconst_0       
        //    99: iconst_0       
        //   100: iastore        
        //   101: iinc            5, 1
        //   104: goto            64
        //   107: iconst_0       
        //   108: iload_3        
        //   109: if_icmpge       220
        //   112: aload_0        
        //   113: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow.basicBlocks:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //   116: iconst_0       
        //   117: aaload         
        //   118: astore          6
        //   120: iconst_0       
        //   121: aload           6
        //   123: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.exits:()I
        //   126: if_icmpge       164
        //   129: aload           6
        //   131: iconst_0       
        //   132: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.exit:(I)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //   135: astore          8
        //   137: aload           8
        //   139: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.entrances:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //   142: aload           4
        //   144: aload           8
        //   146: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.index:I
        //   149: dup2           
        //   150: iaload         
        //   151: dup_x2         
        //   152: iconst_1       
        //   153: iadd           
        //   154: iastore        
        //   155: aload           6
        //   157: aastore        
        //   158: iinc            7, 1
        //   161: goto            120
        //   164: aload           6
        //   166: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.catchers:()[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Catcher;
        //   169: astore          7
        //   171: iconst_0       
        //   172: aload           7
        //   174: arraylength    
        //   175: if_icmpge       214
        //   178: aload           7
        //   180: iconst_0       
        //   181: aaload         
        //   182: invokestatic    com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Catcher.access$100:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Catcher;)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //   185: astore          9
        //   187: aload           9
        //   189: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.entrances:[Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block;
        //   192: aload           4
        //   194: aload           9
        //   196: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.index:I
        //   199: dup2           
        //   200: iaload         
        //   201: dup_x2         
        //   202: iconst_1       
        //   203: iadd           
        //   204: iastore        
        //   205: aload           6
        //   207: aastore        
        //   208: iinc            8, 1
        //   211: goto            171
        //   214: iinc            5, 1
        //   217: goto            107
        //   220: return         
        //    Exceptions:
        //  throws com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Block[] basicBlocks() {
        return this.basicBlocks;
    }
    
    public Frame frameAt(final int n) throws BadBytecode {
        if (this.frames == null) {
            this.frames = new Analyzer().analyze(this.clazz, this.methodInfo);
        }
        return this.frames[n];
    }
    
    public Node[] dominatorTree() {
        final int length = this.basicBlocks.length;
        if (length == 0) {
            return null;
        }
        final Node[] array = new Node[length];
        final boolean[] array2 = new boolean[length];
        final int[] array3 = new int[length];
        while (0 < length) {
            array[0] = new Node(this.basicBlocks[0]);
            array2[0] = false;
            int n = 0;
            ++n;
        }
        final Access access = new Access(array) {
            final ControlFlow this$0;
            
            @Override
            BasicBlock[] exits(final Node node) {
                return Node.access$200(node).getExit();
            }
            
            @Override
            BasicBlock[] entrances(final Node node) {
                return Node.access$200(node).entrances;
            }
        };
        array[0].makeDepth1stTree(null, array2, 0, array3, access);
        while (true) {
            if (0 < length) {
                array2[0] = false;
                int n2 = 0;
                ++n2;
            }
            else {
                if (!array[0].makeDominatorTree(array2, array3, access)) {
                    break;
                }
                continue;
            }
        }
        Node.access$300(array);
        return array;
    }
    
    public Node[] postDominatorTree() {
        final int length = this.basicBlocks.length;
        if (length == 0) {
            return null;
        }
        final Node[] array = new Node[length];
        final boolean[] array2 = new boolean[length];
        final int[] array3 = new int[length];
        while (0 < length) {
            array[0] = new Node(this.basicBlocks[0]);
            array2[0] = false;
            int n = 0;
            ++n;
        }
        final Access access = new Access(array) {
            final ControlFlow this$0;
            
            @Override
            BasicBlock[] exits(final Node node) {
                return Node.access$200(node).entrances;
            }
            
            @Override
            BasicBlock[] entrances(final Node node) {
                return Node.access$200(node).getExit();
            }
        };
        while (1 < length) {
            if (Node.access$200(array[1]).exits() == 0) {
                array[1].makeDepth1stTree(null, array2, 0, array3, access);
            }
            int n2 = 0;
            ++n2;
        }
        int n3 = 0;
        while (0 < length) {
            array2[0] = false;
            ++n3;
        }
        while (0 < length) {
            if (Node.access$200(array[0]).exits() != 0 || array[0].makeDominatorTree(array2, array3, access)) {}
            ++n3;
        }
        Node.access$300(array);
        return array;
    }
    
    static MethodInfo access$000(final ControlFlow controlFlow) {
        return controlFlow.methodInfo;
    }
    
    public static class Catcher
    {
        private Block node;
        private int typeIndex;
        
        Catcher(final BasicBlock.Catch catch1) {
            this.node = (Block)catch1.body;
            this.typeIndex = catch1.typeIndex;
        }
        
        public Block block() {
            return this.node;
        }
        
        public String type() {
            if (this.typeIndex == 0) {
                return "java.lang.Throwable";
            }
            return this.node.method.getConstPool().getClassInfo(this.typeIndex);
        }
        
        static Block access$100(final Catcher catcher) {
            return catcher.node;
        }
    }
    
    public static class Block extends BasicBlock
    {
        public Object clientData;
        int index;
        MethodInfo method;
        Block[] entrances;
        
        Block(final int n, final MethodInfo method) {
            super(n);
            this.clientData = null;
            this.method = method;
        }
        
        @Override
        protected void toString2(final StringBuffer sb) {
            super.toString2(sb);
            sb.append(", incoming{");
            while (0 < this.entrances.length) {
                sb.append(this.entrances[0].position).append(", ");
                int n = 0;
                ++n;
            }
            sb.append("}");
        }
        
        BasicBlock[] getExit() {
            return this.exit;
        }
        
        public int index() {
            return this.index;
        }
        
        public int position() {
            return this.position;
        }
        
        public int length() {
            return this.length;
        }
        
        public int incomings() {
            return this.incoming;
        }
        
        public Block incoming(final int n) {
            return this.entrances[n];
        }
        
        public int exits() {
            return (this.exit == null) ? 0 : this.exit.length;
        }
        
        public Block exit(final int n) {
            return (Block)this.exit[n];
        }
        
        public Catcher[] catchers() {
            final ArrayList<Catcher> list = new ArrayList<Catcher>();
            for (Catch catch1 = this.toCatch; catch1 != null; catch1 = catch1.next) {
                list.add(new Catcher(catch1));
            }
            return list.toArray(new Catcher[list.size()]);
        }
    }
    
    public static class Node
    {
        private Block block;
        private Node parent;
        private Node[] children;
        
        Node(final Block block) {
            this.block = block;
            this.parent = null;
        }
        
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            sb.append("Node[pos=").append(this.block().position());
            sb.append(", parent=");
            sb.append((this.parent == null) ? "*" : Integer.toString(this.parent.block().position()));
            sb.append(", children{");
            while (0 < this.children.length) {
                sb.append(this.children[0].block().position()).append(", ");
                int n = 0;
                ++n;
            }
            sb.append("}]");
            return sb.toString();
        }
        
        public Block block() {
            return this.block;
        }
        
        public Node parent() {
            return this.parent;
        }
        
        public int children() {
            return this.children.length;
        }
        
        public Node child(final int n) {
            return this.children[n];
        }
        
        int makeDepth1stTree(final Node parent, final boolean[] array, int depth1stTree, final int[] array2, final Access access) {
            final int index = this.block.index;
            if (array[index]) {
                return depth1stTree;
            }
            array[index] = true;
            this.parent = parent;
            final BasicBlock[] exits = access.exits(this);
            if (exits != null) {
                while (0 < exits.length) {
                    depth1stTree = access.node(exits[0]).makeDepth1stTree(this, array, depth1stTree, array2, access);
                    int n = 0;
                    ++n;
                }
            }
            array2[index] = depth1stTree++;
            return depth1stTree;
        }
        
        boolean makeDominatorTree(final boolean[] p0, final int[] p1, final Access p2) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     4: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Block.index:I
            //     7: istore          4
            //     9: aload_1        
            //    10: iload           4
            //    12: baload         
            //    13: ifeq            18
            //    16: iconst_0       
            //    17: ireturn        
            //    18: aload_1        
            //    19: iload           4
            //    21: iconst_1       
            //    22: bastore        
            //    23: aload_3        
            //    24: aload_0        
            //    25: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Access.exits:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;)[Lcom/viaversion/viaversion/libs/javassist/bytecode/stackmap/BasicBlock;
            //    28: astore          6
            //    30: aload           6
            //    32: ifnull          69
            //    35: iconst_0       
            //    36: aload           6
            //    38: arraylength    
            //    39: if_icmpge       69
            //    42: aload_3        
            //    43: aload           6
            //    45: iconst_0       
            //    46: aaload         
            //    47: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Access.node:(Lcom/viaversion/viaversion/libs/javassist/bytecode/stackmap/BasicBlock;)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;
            //    50: astore          8
            //    52: aload           8
            //    54: aload_1        
            //    55: aload_2        
            //    56: aload_3        
            //    57: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node.makeDominatorTree:([Z[ILcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Access;)Z
            //    60: ifeq            63
            //    63: iinc            7, 1
            //    66: goto            35
            //    69: aload_3        
            //    70: aload_0        
            //    71: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Access.entrances:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;)[Lcom/viaversion/viaversion/libs/javassist/bytecode/stackmap/BasicBlock;
            //    74: astore          7
            //    76: aload           7
            //    78: ifnull          134
            //    81: iconst_0       
            //    82: aload           7
            //    84: arraylength    
            //    85: if_icmpge       134
            //    88: aload_0        
            //    89: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node.parent:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;
            //    92: ifnull          128
            //    95: aload_0        
            //    96: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node.parent:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;
            //    99: aload_3        
            //   100: aload           7
            //   102: iconst_0       
            //   103: aaload         
            //   104: invokevirtual   com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Access.node:(Lcom/viaversion/viaversion/libs/javassist/bytecode/stackmap/BasicBlock;)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;
            //   107: aload_2        
            //   108: invokestatic    com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node.getAncestor:(Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;[I)Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;
            //   111: astore          9
            //   113: aload           9
            //   115: aload_0        
            //   116: getfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node.parent:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;
            //   119: if_acmpeq       128
            //   122: aload_0        
            //   123: aload           9
            //   125: putfield        com/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node.parent:Lcom/viaversion/viaversion/libs/javassist/bytecode/analysis/ControlFlow$Node;
            //   128: iinc            8, 1
            //   131: goto            81
            //   134: iconst_1       
            //   135: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        private static Node getAncestor(Node parent, Node parent2, final int[] array) {
            while (parent != parent2) {
                if (array[parent.block.index] < array[parent2.block.index]) {
                    parent = parent.parent;
                }
                else {
                    parent2 = parent2.parent;
                }
                if (parent == null || parent2 == null) {
                    return null;
                }
            }
            return parent;
        }
        
        private static void setChildren(final Node[] array) {
            final int length = array.length;
            final int[] array2 = new int[length];
            int n = 0;
            while (0 < length) {
                array2[0] = 0;
                ++n;
            }
            while (0 < length) {
                final Node parent = array[0].parent;
                if (parent != null) {
                    final int[] array3 = array2;
                    final int index = parent.block.index;
                    ++array3[index];
                }
                ++n;
            }
            while (0 < length) {
                array[0].children = new Node[array2[0]];
                ++n;
            }
            while (0 < length) {
                array2[0] = 0;
                ++n;
            }
            while (0 < length) {
                final Node node = array[0];
                final Node parent2 = node.parent;
                if (parent2 != null) {
                    parent2.children[array2[parent2.block.index]++] = node;
                }
                ++n;
            }
        }
        
        static Block access$200(final Node node) {
            return node.block;
        }
        
        static void access$300(final Node[] children) {
            setChildren(children);
        }
    }
    
    abstract static class Access
    {
        Node[] all;
        
        Access(final Node[] all) {
            this.all = all;
        }
        
        Node node(final BasicBlock basicBlock) {
            return this.all[((Block)basicBlock).index];
        }
        
        abstract BasicBlock[] exits(final Node p0);
        
        abstract BasicBlock[] entrances(final Node p0);
    }
}
