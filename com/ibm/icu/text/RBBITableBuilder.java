package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import com.ibm.icu.lang.*;
import java.util.*;

class RBBITableBuilder
{
    private RBBIRuleBuilder fRB;
    private int fRootIx;
    private List fDStates;
    
    RBBITableBuilder(final RBBIRuleBuilder frb, final int fRootIx) {
        this.fRootIx = fRootIx;
        this.fRB = frb;
        this.fDStates = new ArrayList();
    }
    
    void build() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return;
        }
        this.fRB.fTreeRoots[this.fRootIx] = this.fRB.fTreeRoots[this.fRootIx].flattenVariables();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ftree") >= 0) {
            System.out.println("Parse tree after flattening variable references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            final RBBINode fParent = new RBBINode(8);
            final RBBINode fLeftChild = new RBBINode(3);
            fParent.fLeftChild = fLeftChild;
            fParent.fRightChild = this.fRB.fTreeRoots[this.fRootIx];
            fLeftChild.fParent = fParent;
            fLeftChild.fVal = 2;
            this.fRB.fTreeRoots[this.fRootIx] = fParent;
        }
        final RBBINode rbbiNode = new RBBINode(8);
        rbbiNode.fLeftChild = this.fRB.fTreeRoots[this.fRootIx];
        this.fRB.fTreeRoots[this.fRootIx].fParent = rbbiNode;
        rbbiNode.fRightChild = new RBBINode(6);
        rbbiNode.fRightChild.fParent = rbbiNode;
        (this.fRB.fTreeRoots[this.fRootIx] = rbbiNode).flattenSets();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("stree") >= 0) {
            System.out.println("Parse tree after flattening Unicode Set references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        this.calcNullable(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcFirstPos(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcLastPos(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("pos") >= 0) {
            System.out.print("\n");
            this.printPosSets(this.fRB.fTreeRoots[this.fRootIx]);
        }
        if (this.fRB.fChainRules) {
            this.calcChainedFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            this.bofFixup();
        }
        this.buildStateTable();
        this.flagAcceptingStates();
        this.flagLookAheadStates();
        this.flagTaggedStates();
        this.mergeRuleStatusVals();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("states") >= 0) {
            this.printStates();
        }
    }
    
    void calcNullable(final RBBINode rbbiNode) {
        if (rbbiNode == null) {
            return;
        }
        if (rbbiNode.fType == 0 || rbbiNode.fType == 6) {
            rbbiNode.fNullable = false;
            return;
        }
        if (rbbiNode.fType == 4 || rbbiNode.fType == 5) {
            rbbiNode.fNullable = true;
            return;
        }
        this.calcNullable(rbbiNode.fLeftChild);
        this.calcNullable(rbbiNode.fRightChild);
        if (rbbiNode.fType == 9) {
            rbbiNode.fNullable = (rbbiNode.fLeftChild.fNullable || rbbiNode.fRightChild.fNullable);
        }
        else if (rbbiNode.fType == 8) {
            rbbiNode.fNullable = (rbbiNode.fLeftChild.fNullable && rbbiNode.fRightChild.fNullable);
        }
        else if (rbbiNode.fType == 10 || rbbiNode.fType == 12) {
            rbbiNode.fNullable = true;
        }
        else {
            rbbiNode.fNullable = false;
        }
    }
    
    void calcFirstPos(final RBBINode rbbiNode) {
        if (rbbiNode == null) {
            return;
        }
        if (rbbiNode.fType == 3 || rbbiNode.fType == 6 || rbbiNode.fType == 4 || rbbiNode.fType == 5) {
            rbbiNode.fFirstPosSet.add(rbbiNode);
            return;
        }
        this.calcFirstPos(rbbiNode.fLeftChild);
        this.calcFirstPos(rbbiNode.fRightChild);
        if (rbbiNode.fType == 9) {
            rbbiNode.fFirstPosSet.addAll(rbbiNode.fLeftChild.fFirstPosSet);
            rbbiNode.fFirstPosSet.addAll(rbbiNode.fRightChild.fFirstPosSet);
        }
        else if (rbbiNode.fType == 8) {
            rbbiNode.fFirstPosSet.addAll(rbbiNode.fLeftChild.fFirstPosSet);
            if (rbbiNode.fLeftChild.fNullable) {
                rbbiNode.fFirstPosSet.addAll(rbbiNode.fRightChild.fFirstPosSet);
            }
        }
        else if (rbbiNode.fType == 10 || rbbiNode.fType == 12 || rbbiNode.fType == 11) {
            rbbiNode.fFirstPosSet.addAll(rbbiNode.fLeftChild.fFirstPosSet);
        }
    }
    
    void calcLastPos(final RBBINode rbbiNode) {
        if (rbbiNode == null) {
            return;
        }
        if (rbbiNode.fType == 3 || rbbiNode.fType == 6 || rbbiNode.fType == 4 || rbbiNode.fType == 5) {
            rbbiNode.fLastPosSet.add(rbbiNode);
            return;
        }
        this.calcLastPos(rbbiNode.fLeftChild);
        this.calcLastPos(rbbiNode.fRightChild);
        if (rbbiNode.fType == 9) {
            rbbiNode.fLastPosSet.addAll(rbbiNode.fLeftChild.fLastPosSet);
            rbbiNode.fLastPosSet.addAll(rbbiNode.fRightChild.fLastPosSet);
        }
        else if (rbbiNode.fType == 8) {
            rbbiNode.fLastPosSet.addAll(rbbiNode.fRightChild.fLastPosSet);
            if (rbbiNode.fRightChild.fNullable) {
                rbbiNode.fLastPosSet.addAll(rbbiNode.fLeftChild.fLastPosSet);
            }
        }
        else if (rbbiNode.fType == 10 || rbbiNode.fType == 12 || rbbiNode.fType == 11) {
            rbbiNode.fLastPosSet.addAll(rbbiNode.fLeftChild.fLastPosSet);
        }
    }
    
    void calcFollowPos(final RBBINode rbbiNode) {
        if (rbbiNode == null || rbbiNode.fType == 3 || rbbiNode.fType == 6) {
            return;
        }
        this.calcFollowPos(rbbiNode.fLeftChild);
        this.calcFollowPos(rbbiNode.fRightChild);
        if (rbbiNode.fType == 8) {
            final Iterator<RBBINode> iterator = rbbiNode.fLeftChild.fLastPosSet.iterator();
            while (iterator.hasNext()) {
                iterator.next().fFollowPos.addAll(rbbiNode.fRightChild.fFirstPosSet);
            }
        }
        if (rbbiNode.fType == 10 || rbbiNode.fType == 11) {
            final Iterator<RBBINode> iterator2 = rbbiNode.fLastPosSet.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().fFollowPos.addAll(rbbiNode.fFirstPosSet);
            }
        }
    }
    
    void calcChainedFollowPos(final RBBINode rbbiNode) {
        final ArrayList<RBBINode> list = (ArrayList<RBBINode>)new ArrayList<Object>();
        final ArrayList<RBBINode> list2 = (ArrayList<RBBINode>)new ArrayList<Object>();
        rbbiNode.findNodes(list, 6);
        rbbiNode.findNodes(list2, 3);
        RBBINode fRightChild = rbbiNode;
        if (this.fRB.fSetBuilder.sawBOF()) {
            fRightChild = rbbiNode.fLeftChild.fRightChild;
        }
        Assert.assrt(fRightChild != null);
        final Set fFirstPosSet = fRightChild.fFirstPosSet;
        for (final RBBINode rbbiNode2 : list2) {
            RBBINode rbbiNode3 = null;
            final Iterator<Object> iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                if (rbbiNode2.fFollowPos.contains(iterator2.next())) {
                    rbbiNode3 = rbbiNode2;
                    break;
                }
            }
            if (rbbiNode3 == null) {
                continue;
            }
            if (this.fRB.fLBCMNoChain) {
                final int firstChar = this.fRB.fSetBuilder.getFirstChar(rbbiNode3.fVal);
                if (firstChar != -1 && UCharacter.getIntPropertyValue(firstChar, 4104) == 9) {
                    continue;
                }
            }
            for (final RBBINode rbbiNode4 : fFirstPosSet) {
                if (rbbiNode4.fType != 3) {
                    continue;
                }
                if (rbbiNode3.fVal != rbbiNode4.fVal) {
                    continue;
                }
                rbbiNode3.fFollowPos.addAll(rbbiNode4.fFollowPos);
            }
        }
    }
    
    void bofFixup() {
        final RBBINode fLeftChild = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fLeftChild;
        Assert.assrt(fLeftChild.fType == 3);
        Assert.assrt(fLeftChild.fVal == 2);
        for (final RBBINode rbbiNode : this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fRightChild.fFirstPosSet) {
            if (rbbiNode.fType != 3) {
                continue;
            }
            if (rbbiNode.fVal != fLeftChild.fVal) {
                continue;
            }
            fLeftChild.fFollowPos.addAll(rbbiNode.fFollowPos);
        }
    }
    
    void buildStateTable() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: getfield        com/ibm/icu/text/RBBIRuleBuilder.fSetBuilder:Lcom/ibm/icu/text/RBBISetBuilder;
        //     7: invokevirtual   com/ibm/icu/text/RBBISetBuilder.getNumCharCategories:()I
        //    10: iconst_1       
        //    11: isub           
        //    12: istore_1       
        //    13: new             Lcom/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor;
        //    16: dup            
        //    17: iload_1        
        //    18: invokespecial   com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.<init>:(I)V
        //    21: astore_2       
        //    22: aload_0        
        //    23: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //    26: aload_2        
        //    27: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    32: pop            
        //    33: new             Lcom/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor;
        //    36: dup            
        //    37: iload_1        
        //    38: invokespecial   com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.<init>:(I)V
        //    41: astore_3       
        //    42: aload_3        
        //    43: getfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fPositions:Ljava/util/Set;
        //    46: aload_0        
        //    47: getfield        com/ibm/icu/text/RBBITableBuilder.fRB:Lcom/ibm/icu/text/RBBIRuleBuilder;
        //    50: getfield        com/ibm/icu/text/RBBIRuleBuilder.fTreeRoots:[Lcom/ibm/icu/text/RBBINode;
        //    53: aload_0        
        //    54: getfield        com/ibm/icu/text/RBBITableBuilder.fRootIx:I
        //    57: aaload         
        //    58: getfield        com/ibm/icu/text/RBBINode.fFirstPosSet:Ljava/util/Set;
        //    61: invokeinterface java/util/Set.addAll:(Ljava/util/Collection;)Z
        //    66: pop            
        //    67: aload_0        
        //    68: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //    71: aload_3        
        //    72: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    77: pop            
        //    78: aconst_null    
        //    79: astore          4
        //    81: iconst_1       
        //    82: aload_0        
        //    83: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //    86: invokeinterface java/util/List.size:()I
        //    91: if_icmpge       130
        //    94: aload_0        
        //    95: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //    98: iconst_1       
        //    99: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   104: checkcast       Lcom/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor;
        //   107: astore          6
        //   109: aload           6
        //   111: getfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fMarked:Z
        //   114: ifne            124
        //   117: aload           6
        //   119: astore          4
        //   121: goto            130
        //   124: iinc            5, 1
        //   127: goto            81
        //   130: aload           4
        //   132: ifnonnull       138
        //   135: goto            376
        //   138: aload           4
        //   140: iconst_1       
        //   141: putfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fMarked:Z
        //   144: iconst_1       
        //   145: iload_1        
        //   146: if_icmpgt       373
        //   149: aconst_null    
        //   150: astore          7
        //   152: aload           4
        //   154: getfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fPositions:Ljava/util/Set;
        //   157: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   162: astore          8
        //   164: aload           8
        //   166: invokeinterface java/util/Iterator.hasNext:()Z
        //   171: ifeq            234
        //   174: aload           8
        //   176: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   181: checkcast       Lcom/ibm/icu/text/RBBINode;
        //   184: astore          9
        //   186: aload           9
        //   188: getfield        com/ibm/icu/text/RBBINode.fType:I
        //   191: iconst_3       
        //   192: if_icmpne       231
        //   195: aload           9
        //   197: getfield        com/ibm/icu/text/RBBINode.fVal:I
        //   200: iconst_1       
        //   201: if_icmpne       231
        //   204: aload           7
        //   206: ifnonnull       218
        //   209: new             Ljava/util/HashSet;
        //   212: dup            
        //   213: invokespecial   java/util/HashSet.<init>:()V
        //   216: astore          7
        //   218: aload           7
        //   220: aload           9
        //   222: getfield        com/ibm/icu/text/RBBINode.fFollowPos:Ljava/util/Set;
        //   225: invokeinterface java/util/Set.addAll:(Ljava/util/Collection;)Z
        //   230: pop            
        //   231: goto            164
        //   234: aload           7
        //   236: ifnull          367
        //   239: aload           7
        //   241: invokeinterface java/util/Set.size:()I
        //   246: ifle            253
        //   249: iconst_1       
        //   250: goto            254
        //   253: iconst_0       
        //   254: invokestatic    com/ibm/icu/impl/Assert.assrt:(Z)V
        //   257: iconst_0       
        //   258: aload_0        
        //   259: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //   262: invokeinterface java/util/List.size:()I
        //   267: if_icmpge       314
        //   270: aload_0        
        //   271: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //   274: iconst_0       
        //   275: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   280: checkcast       Lcom/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor;
        //   283: astore          11
        //   285: aload           7
        //   287: aload           11
        //   289: getfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fPositions:Ljava/util/Set;
        //   292: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   295: ifeq            308
        //   298: aload           11
        //   300: getfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fPositions:Ljava/util/Set;
        //   303: astore          7
        //   305: goto            314
        //   308: iinc            10, 1
        //   311: goto            257
        //   314: goto            359
        //   317: new             Lcom/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor;
        //   320: dup            
        //   321: iload_1        
        //   322: invokespecial   com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.<init>:(I)V
        //   325: astore          11
        //   327: aload           11
        //   329: aload           7
        //   331: putfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fPositions:Ljava/util/Set;
        //   334: aload_0        
        //   335: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //   338: aload           11
        //   340: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   345: pop            
        //   346: aload_0        
        //   347: getfield        com/ibm/icu/text/RBBITableBuilder.fDStates:Ljava/util/List;
        //   350: invokeinterface java/util/List.size:()I
        //   355: iconst_1       
        //   356: isub           
        //   357: istore          8
        //   359: aload           4
        //   361: getfield        com/ibm/icu/text/RBBITableBuilder$RBBIStateDescriptor.fDtran:[I
        //   364: iconst_1       
        //   365: iconst_0       
        //   366: iastore        
        //   367: iinc            6, 1
        //   370: goto            144
        //   373: goto            78
        //   376: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void flagAcceptingStates() {
        final ArrayList<RBBINode> list = (ArrayList<RBBINode>)new ArrayList<Object>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(list, 6);
        while (0 < list.size()) {
            final RBBINode rbbiNode = list.get(0);
            while (0 < this.fDStates.size()) {
                final RBBIStateDescriptor rbbiStateDescriptor = this.fDStates.get(0);
                if (rbbiStateDescriptor.fPositions.contains(rbbiNode)) {
                    if (rbbiStateDescriptor.fAccepting == 0) {
                        rbbiStateDescriptor.fAccepting = rbbiNode.fVal;
                        if (rbbiStateDescriptor.fAccepting == 0) {
                            rbbiStateDescriptor.fAccepting = -1;
                        }
                    }
                    if (rbbiStateDescriptor.fAccepting == -1 && rbbiNode.fVal != 0) {
                        rbbiStateDescriptor.fAccepting = rbbiNode.fVal;
                    }
                    if (rbbiNode.fLookAheadEnd) {
                        rbbiStateDescriptor.fLookAhead = rbbiStateDescriptor.fAccepting;
                    }
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    void flagLookAheadStates() {
        final ArrayList<RBBINode> list = (ArrayList<RBBINode>)new ArrayList<Object>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(list, 4);
        while (0 < list.size()) {
            final RBBINode rbbiNode = list.get(0);
            while (0 < this.fDStates.size()) {
                final RBBIStateDescriptor rbbiStateDescriptor = this.fDStates.get(0);
                if (rbbiStateDescriptor.fPositions.contains(rbbiNode)) {
                    rbbiStateDescriptor.fLookAhead = rbbiNode.fVal;
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    void flagTaggedStates() {
        final ArrayList<RBBINode> list = (ArrayList<RBBINode>)new ArrayList<Object>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(list, 5);
        while (0 < list.size()) {
            final RBBINode rbbiNode = list.get(0);
            while (0 < this.fDStates.size()) {
                final RBBIStateDescriptor rbbiStateDescriptor = this.fDStates.get(0);
                if (rbbiStateDescriptor.fPositions.contains(rbbiNode)) {
                    rbbiStateDescriptor.fTagVals.add(rbbiNode.fVal);
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    void mergeRuleStatusVals() {
        if (this.fRB.fRuleStatusVals.size() == 0) {
            this.fRB.fRuleStatusVals.add(1);
            this.fRB.fRuleStatusVals.add(0);
            final TreeSet set = new TreeSet();
            final Integer value = 0;
            this.fRB.fStatusSets.put(set, value);
            new TreeSet<Integer>().add(value);
            this.fRB.fStatusSets.put(set, value);
        }
        while (0 < this.fDStates.size()) {
            final RBBIStateDescriptor rbbiStateDescriptor = this.fDStates.get(0);
            final SortedSet fTagVals = rbbiStateDescriptor.fTagVals;
            Integer value2 = this.fRB.fStatusSets.get(fTagVals);
            if (value2 == null) {
                value2 = this.fRB.fRuleStatusVals.size();
                this.fRB.fStatusSets.put(fTagVals, value2);
                this.fRB.fRuleStatusVals.add(fTagVals.size());
                this.fRB.fRuleStatusVals.addAll(fTagVals);
            }
            rbbiStateDescriptor.fTagsIdx = value2;
            int n = 0;
            ++n;
        }
    }
    
    void printPosSets(final RBBINode rbbiNode) {
        if (rbbiNode == null) {
            return;
        }
        RBBINode.printNode(rbbiNode);
        System.out.print("         Nullable:  " + rbbiNode.fNullable);
        System.out.print("         firstpos:  ");
        this.printSet(rbbiNode.fFirstPosSet);
        System.out.print("         lastpos:   ");
        this.printSet(rbbiNode.fLastPosSet);
        System.out.print("         followpos: ");
        this.printSet(rbbiNode.fFollowPos);
        this.printPosSets(rbbiNode.fLeftChild);
        this.printPosSets(rbbiNode.fRightChild);
    }
    
    int getTableSize() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return 0;
        }
        final int n = 16 + this.fDStates.size() * (8 + 2 * this.fRB.fSetBuilder.getNumCharCategories());
        return 16;
    }
    
    short[] exportTable() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return new short[0];
        }
        Assert.assrt(this.fRB.fSetBuilder.getNumCharCategories() < 32767 && this.fDStates.size() < 32767);
        final int size = this.fDStates.size();
        final int n = 4 + this.fRB.fSetBuilder.getNumCharCategories();
        final short[] array = new short[this.getTableSize() / 2];
        array[0] = (short)(size >>> 16);
        array[1] = (short)(size & 0xFFFF);
        array[2] = (short)(n >>> 16);
        array[3] = (short)(n & 0xFFFF);
        if (this.fRB.fLookAheadHardBreak) {}
        if (this.fRB.fSetBuilder.sawBOF()) {}
        array[4] = 0;
        array[5] = 0;
        final int numCharCategories = this.fRB.fSetBuilder.getNumCharCategories();
        while (0 < size) {
            final RBBIStateDescriptor rbbiStateDescriptor = this.fDStates.get(0);
            final int n2 = 8 + 0 * n;
            Assert.assrt(-32768 < rbbiStateDescriptor.fAccepting && rbbiStateDescriptor.fAccepting <= 32767);
            Assert.assrt(-32768 < rbbiStateDescriptor.fLookAhead && rbbiStateDescriptor.fLookAhead <= 32767);
            array[n2 + 0] = (short)rbbiStateDescriptor.fAccepting;
            array[n2 + 1] = (short)rbbiStateDescriptor.fLookAhead;
            array[n2 + 2] = (short)rbbiStateDescriptor.fTagsIdx;
            while (0 < numCharCategories) {
                array[n2 + 4 + 0] = (short)rbbiStateDescriptor.fDtran[0];
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        return array;
    }
    
    void printSet(final Collection collection) {
        final Iterator<RBBINode> iterator = collection.iterator();
        while (iterator.hasNext()) {
            RBBINode.printInt(iterator.next().fSerialNum, 8);
        }
        System.out.println();
    }
    
    void printStates() {
        System.out.print("state |           i n p u t     s y m b o l s \n");
        System.out.print("      | Acc  LA    Tag");
        int n = 0;
        while (0 < this.fRB.fSetBuilder.getNumCharCategories()) {
            RBBINode.printInt(0, 3);
            ++n;
        }
        System.out.print("\n");
        System.out.print("      |---------------");
        while (0 < this.fRB.fSetBuilder.getNumCharCategories()) {
            System.out.print("---");
            ++n;
        }
        System.out.print("\n");
        while (0 < this.fDStates.size()) {
            final RBBIStateDescriptor rbbiStateDescriptor = this.fDStates.get(0);
            RBBINode.printInt(0, 5);
            System.out.print(" | ");
            RBBINode.printInt(rbbiStateDescriptor.fAccepting, 3);
            RBBINode.printInt(rbbiStateDescriptor.fLookAhead, 4);
            RBBINode.printInt(rbbiStateDescriptor.fTagsIdx, 6);
            System.out.print(" ");
            while (0 < this.fRB.fSetBuilder.getNumCharCategories()) {
                RBBINode.printInt(rbbiStateDescriptor.fDtran[0], 3);
                ++n;
            }
            System.out.print("\n");
            int n2 = 0;
            ++n2;
        }
        System.out.print("\n\n");
    }
    
    void printRuleStatusTable() {
        final List fRuleStatusVals = this.fRB.fRuleStatusVals;
        System.out.print("index |  tags \n");
        System.out.print("-------------------\n");
        while (0 < fRuleStatusVals.size()) {
            final int n = 0 + fRuleStatusVals.get(0) + 1;
            RBBINode.printInt(0, 7);
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }
    
    static class RBBIStateDescriptor
    {
        boolean fMarked;
        int fAccepting;
        int fLookAhead;
        SortedSet fTagVals;
        int fTagsIdx;
        Set fPositions;
        int[] fDtran;
        
        RBBIStateDescriptor(final int n) {
            this.fTagVals = new TreeSet();
            this.fPositions = new HashSet();
            this.fDtran = new int[n + 1];
        }
    }
}
