package com.ibm.icu.text;

import java.io.*;
import java.util.*;
import com.ibm.icu.impl.*;

class RBBISetBuilder
{
    RBBIRuleBuilder fRB;
    RangeDescriptor fRangeList;
    IntTrieBuilder fTrie;
    int fTrieSize;
    int fGroupCount;
    boolean fSawBOF;
    RBBIDataManipulate dm;
    
    RBBISetBuilder(final RBBIRuleBuilder frb) {
        this.dm = new RBBIDataManipulate();
        this.fRB = frb;
    }
    
    void build() {
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("usets") >= 0) {
            this.printSets();
        }
        this.fRangeList = new RangeDescriptor();
        this.fRangeList.fStartChar = 0;
        this.fRangeList.fEndChar = 1114111;
        for (final RBBINode rbbiNode : this.fRB.fUSetNodes) {
            final UnicodeSet fInputSet = rbbiNode.fInputSet;
            final int rangeCount = fInputSet.getRangeCount();
            RangeDescriptor rangeDescriptor = this.fRangeList;
            while (0 < rangeCount) {
                final int rangeStart = fInputSet.getRangeStart(0);
                final int rangeEnd = fInputSet.getRangeEnd(0);
                while (rangeDescriptor.fEndChar < rangeStart) {
                    rangeDescriptor = rangeDescriptor.fNext;
                }
                if (rangeDescriptor.fStartChar < rangeStart) {
                    rangeDescriptor.split(rangeStart);
                }
                else {
                    if (rangeDescriptor.fEndChar > rangeEnd) {
                        rangeDescriptor.split(rangeEnd + 1);
                    }
                    if (rangeDescriptor.fIncludesSets.indexOf(rbbiNode) == -1) {
                        rangeDescriptor.fIncludesSets.add(rbbiNode);
                    }
                    if (rangeEnd == rangeDescriptor.fEndChar) {
                        int n = 0;
                        ++n;
                    }
                    rangeDescriptor = rangeDescriptor.fNext;
                }
            }
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("range") >= 0) {
            this.printRanges();
        }
        for (RangeDescriptor rangeDescriptor2 = this.fRangeList; rangeDescriptor2 != null; rangeDescriptor2 = rangeDescriptor2.fNext) {
            for (RangeDescriptor rangeDescriptor3 = this.fRangeList; rangeDescriptor3 != rangeDescriptor2; rangeDescriptor3 = rangeDescriptor3.fNext) {
                if (rangeDescriptor2.fIncludesSets.equals(rangeDescriptor3.fIncludesSets)) {
                    rangeDescriptor2.fNum = rangeDescriptor3.fNum;
                    break;
                }
            }
            if (rangeDescriptor2.fNum == 0) {
                ++this.fGroupCount;
                rangeDescriptor2.fNum = this.fGroupCount + 2;
                rangeDescriptor2.setDictionaryFlag();
                this.addValToSets(rangeDescriptor2.fIncludesSets, this.fGroupCount + 2);
            }
        }
        final String s = "eof";
        final String s2 = "bof";
        for (final RBBINode rbbiNode2 : this.fRB.fUSetNodes) {
            final UnicodeSet fInputSet2 = rbbiNode2.fInputSet;
            if (fInputSet2.contains(s)) {
                this.addValToSet(rbbiNode2, 1);
            }
            if (fInputSet2.contains(s2)) {
                this.addValToSet(rbbiNode2, 2);
                this.fSawBOF = true;
            }
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rgroup") >= 0) {
            this.printRangeGroups();
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("esets") >= 0) {
            this.printSets();
        }
        this.fTrie = new IntTrieBuilder(null, 100000, 0, 0, true);
        for (RangeDescriptor rangeDescriptor4 = this.fRangeList; rangeDescriptor4 != null; rangeDescriptor4 = rangeDescriptor4.fNext) {
            this.fTrie.setRange(rangeDescriptor4.fStartChar, rangeDescriptor4.fEndChar + 1, rangeDescriptor4.fNum, true);
        }
    }
    
    int getTrieSize() {
        this.fTrie.serialize(null, true, this.dm);
        return 0;
    }
    
    void serializeTrie(final OutputStream outputStream) throws IOException {
        this.fTrie.serialize(outputStream, true, this.dm);
    }
    
    void addValToSets(final List list, final int n) {
        final Iterator<RBBINode> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.addValToSet(iterator.next(), n);
        }
    }
    
    void addValToSet(final RBBINode rbbiNode, final int fVal) {
        final RBBINode rbbiNode2 = new RBBINode(3);
        rbbiNode2.fVal = fVal;
        if (rbbiNode.fLeftChild == null) {
            rbbiNode.fLeftChild = rbbiNode2;
            rbbiNode2.fParent = rbbiNode;
        }
        else {
            final RBBINode fLeftChild = new RBBINode(9);
            fLeftChild.fLeftChild = rbbiNode.fLeftChild;
            fLeftChild.fRightChild = rbbiNode2;
            fLeftChild.fLeftChild.fParent = fLeftChild;
            fLeftChild.fRightChild.fParent = fLeftChild;
            rbbiNode.fLeftChild = fLeftChild;
            fLeftChild.fParent = rbbiNode;
        }
    }
    
    int getNumCharCategories() {
        return this.fGroupCount + 3;
    }
    
    boolean sawBOF() {
        return this.fSawBOF;
    }
    
    int getFirstChar(final int n) {
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            if (rangeDescriptor.fNum == n) {
                final int fStartChar = rangeDescriptor.fStartChar;
                break;
            }
        }
        return -1;
    }
    
    void printRanges() {
        System.out.print("\n\n Nonoverlapping Ranges ...\n");
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            System.out.print(" " + rangeDescriptor.fNum + "   " + rangeDescriptor.fStartChar + "-" + rangeDescriptor.fEndChar);
            while (0 < rangeDescriptor.fIncludesSets.size()) {
                final RBBINode rbbiNode = rangeDescriptor.fIncludesSets.get(0);
                String fText = "anon";
                final RBBINode fParent = rbbiNode.fParent;
                if (fParent != null) {
                    final RBBINode fParent2 = fParent.fParent;
                    if (fParent2 != null && fParent2.fType == 2) {
                        fText = fParent2.fText;
                    }
                }
                System.out.print(fText);
                System.out.print("  ");
                int n = 0;
                ++n;
            }
            System.out.println("");
        }
    }
    
    void printRangeGroups() {
        System.out.print("\nRanges grouped by Unicode Set Membership...\n");
        for (RangeDescriptor rangeDescriptor = this.fRangeList; rangeDescriptor != null; rangeDescriptor = rangeDescriptor.fNext) {
            final int n = rangeDescriptor.fNum & 0xBFFF;
            if (n > 0) {
                if (n < 10) {
                    System.out.print(" ");
                }
                System.out.print(n + " ");
                if ((rangeDescriptor.fNum & 0x4000) != 0x0) {
                    System.out.print(" <DICT> ");
                }
                int n2 = 0;
                while (0 < rangeDescriptor.fIncludesSets.size()) {
                    final RBBINode rbbiNode = rangeDescriptor.fIncludesSets.get(0);
                    String fText = "anon";
                    final RBBINode fParent = rbbiNode.fParent;
                    if (fParent != null) {
                        final RBBINode fParent2 = fParent.fParent;
                        if (fParent2 != null && fParent2.fType == 2) {
                            fText = fParent2.fText;
                        }
                    }
                    System.out.print(fText);
                    System.out.print(" ");
                    ++n2;
                }
                for (RangeDescriptor fNext = rangeDescriptor; fNext != null; fNext = fNext.fNext) {
                    if (fNext.fNum == rangeDescriptor.fNum) {
                        final int n3 = 0;
                        ++n2;
                        if (n3 % 5 == 0) {
                            System.out.print("\n    ");
                        }
                        RBBINode.printHex(fNext.fStartChar, -1);
                        System.out.print("-");
                        RBBINode.printHex(fNext.fEndChar, 0);
                    }
                }
                System.out.print("\n");
            }
        }
        System.out.print("\n");
    }
    
    void printSets() {
        System.out.print("\n\nUnicode Sets List\n------------------\n");
        while (0 < this.fRB.fUSetNodes.size()) {
            final RBBINode rbbiNode = this.fRB.fUSetNodes.get(0);
            RBBINode.printInt(2, 0);
            String fText = "anonymous";
            final RBBINode fParent = rbbiNode.fParent;
            if (fParent != null) {
                final RBBINode fParent2 = fParent.fParent;
                if (fParent2 != null && fParent2.fType == 2) {
                    fText = fParent2.fText;
                }
            }
            System.out.print("  " + fText);
            System.out.print("   ");
            System.out.print(rbbiNode.fText);
            System.out.print("\n");
            if (rbbiNode.fLeftChild != null) {
                rbbiNode.fLeftChild.printTree(true);
            }
            int n = 0;
            ++n;
        }
        System.out.print("\n");
    }
    
    class RBBIDataManipulate implements TrieBuilder.DataManipulate
    {
        final RBBISetBuilder this$0;
        
        RBBIDataManipulate(final RBBISetBuilder this$0) {
            this.this$0 = this$0;
        }
        
        public int getFoldedValue(int i, final int n) {
            final boolean[] array = { false };
            while (i < i + 1024) {
                final int value = this.this$0.fTrie.getValue(i, array);
                if (array[0]) {
                    i += 32;
                }
                else {
                    if (value != 0) {
                        return n | 0x8000;
                    }
                    ++i;
                }
            }
            return 0;
        }
    }
    
    static class RangeDescriptor
    {
        int fStartChar;
        int fEndChar;
        int fNum;
        List fIncludesSets;
        RangeDescriptor fNext;
        
        RangeDescriptor() {
            this.fIncludesSets = new ArrayList();
        }
        
        RangeDescriptor(final RangeDescriptor rangeDescriptor) {
            this.fStartChar = rangeDescriptor.fStartChar;
            this.fEndChar = rangeDescriptor.fEndChar;
            this.fNum = rangeDescriptor.fNum;
            this.fIncludesSets = new ArrayList(rangeDescriptor.fIncludesSets);
        }
        
        void split(final int fStartChar) {
            Assert.assrt(fStartChar > this.fStartChar && fStartChar <= this.fEndChar);
            final RangeDescriptor fNext = new RangeDescriptor(this);
            fNext.fStartChar = fStartChar;
            this.fEndChar = fStartChar - 1;
            fNext.fNext = this.fNext;
            this.fNext = fNext;
        }
        
        void setDictionaryFlag() {
            while (0 < this.fIncludesSets.size()) {
                final RBBINode rbbiNode = this.fIncludesSets.get(0);
                String fText = "";
                final RBBINode fParent = rbbiNode.fParent;
                if (fParent != null) {
                    final RBBINode fParent2 = fParent.fParent;
                    if (fParent2 != null && fParent2.fType == 2) {
                        fText = fParent2.fText;
                    }
                }
                if (fText.equals("dictionary")) {
                    this.fNum |= 0x4000;
                    break;
                }
                int n = 0;
                ++n;
            }
        }
    }
}
