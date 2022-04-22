package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.util.*;

class RBBINode
{
    static final int setRef = 0;
    static final int uset = 1;
    static final int varRef = 2;
    static final int leafChar = 3;
    static final int lookAhead = 4;
    static final int tag = 5;
    static final int endMark = 6;
    static final int opStart = 7;
    static final int opCat = 8;
    static final int opOr = 9;
    static final int opStar = 10;
    static final int opPlus = 11;
    static final int opQuestion = 12;
    static final int opBreak = 13;
    static final int opReverse = 14;
    static final int opLParen = 15;
    static final int nodeTypeLimit = 16;
    static final int precZero = 0;
    static final int precStart = 1;
    static final int precLParen = 2;
    static final int precOpOr = 3;
    static final int precOpCat = 4;
    int fType;
    RBBINode fParent;
    RBBINode fLeftChild;
    RBBINode fRightChild;
    UnicodeSet fInputSet;
    int fPrecedence;
    String fText;
    int fFirstPos;
    int fLastPos;
    boolean fNullable;
    int fVal;
    boolean fLookAheadEnd;
    Set fFirstPosSet;
    Set fLastPosSet;
    Set fFollowPos;
    int fSerialNum;
    static int gLastSerial;
    
    RBBINode(final int fType) {
        this.fPrecedence = 0;
        Assert.assrt(fType < 16);
        this.fSerialNum = ++RBBINode.gLastSerial;
        this.fType = fType;
        this.fFirstPosSet = new HashSet();
        this.fLastPosSet = new HashSet();
        this.fFollowPos = new HashSet();
        if (fType == 8) {
            this.fPrecedence = 4;
        }
        else if (fType == 9) {
            this.fPrecedence = 3;
        }
        else if (fType == 7) {
            this.fPrecedence = 1;
        }
        else if (fType == 15) {
            this.fPrecedence = 2;
        }
        else {
            this.fPrecedence = 0;
        }
    }
    
    RBBINode(final RBBINode rbbiNode) {
        this.fPrecedence = 0;
        this.fSerialNum = ++RBBINode.gLastSerial;
        this.fType = rbbiNode.fType;
        this.fInputSet = rbbiNode.fInputSet;
        this.fPrecedence = rbbiNode.fPrecedence;
        this.fText = rbbiNode.fText;
        this.fFirstPos = rbbiNode.fFirstPos;
        this.fLastPos = rbbiNode.fLastPos;
        this.fNullable = rbbiNode.fNullable;
        this.fVal = rbbiNode.fVal;
        this.fFirstPosSet = new HashSet(rbbiNode.fFirstPosSet);
        this.fLastPosSet = new HashSet(rbbiNode.fLastPosSet);
        this.fFollowPos = new HashSet(rbbiNode.fFollowPos);
    }
    
    RBBINode cloneTree() {
        RBBINode cloneTree;
        if (this.fType == 2) {
            cloneTree = this.fLeftChild.cloneTree();
        }
        else if (this.fType == 1) {
            cloneTree = this;
        }
        else {
            cloneTree = new RBBINode(this);
            if (this.fLeftChild != null) {
                cloneTree.fLeftChild = this.fLeftChild.cloneTree();
                cloneTree.fLeftChild.fParent = cloneTree;
            }
            if (this.fRightChild != null) {
                cloneTree.fRightChild = this.fRightChild.cloneTree();
                cloneTree.fRightChild.fParent = cloneTree;
            }
        }
        return cloneTree;
    }
    
    RBBINode flattenVariables() {
        if (this.fType == 2) {
            return this.fLeftChild.cloneTree();
        }
        if (this.fLeftChild != null) {
            this.fLeftChild = this.fLeftChild.flattenVariables();
            this.fLeftChild.fParent = this;
        }
        if (this.fRightChild != null) {
            this.fRightChild = this.fRightChild.flattenVariables();
            this.fRightChild.fParent = this;
        }
        return this;
    }
    
    void flattenSets() {
        Assert.assrt(this.fType != 0);
        if (this.fLeftChild != null) {
            if (this.fLeftChild.fType == 0) {
                this.fLeftChild = this.fLeftChild.fLeftChild.fLeftChild.cloneTree();
                this.fLeftChild.fParent = this;
            }
            else {
                this.fLeftChild.flattenSets();
            }
        }
        if (this.fRightChild != null) {
            if (this.fRightChild.fType == 0) {
                this.fRightChild = this.fRightChild.fLeftChild.fLeftChild.cloneTree();
                this.fRightChild.fParent = this;
            }
            else {
                this.fRightChild.flattenSets();
            }
        }
    }
    
    void findNodes(final List list, final int n) {
        if (this.fType == n) {
            list.add(this);
        }
        if (this.fLeftChild != null) {
            this.fLeftChild.findNodes(list, n);
        }
        if (this.fRightChild != null) {
            this.fRightChild.findNodes(list, n);
        }
    }
    
    static void printNode(final RBBINode rbbiNode) {
        if (rbbiNode == null) {
            System.out.print(" -- null --\n");
        }
        else {
            printInt(rbbiNode.fSerialNum, 10);
            printString(RBBINode.nodeTypeNames[rbbiNode.fType], 11);
            printInt((rbbiNode.fParent == null) ? 0 : rbbiNode.fParent.fSerialNum, 11);
            printInt((rbbiNode.fLeftChild == null) ? 0 : rbbiNode.fLeftChild.fSerialNum, 11);
            printInt((rbbiNode.fRightChild == null) ? 0 : rbbiNode.fRightChild.fSerialNum, 12);
            printInt(rbbiNode.fFirstPos, 12);
            printInt(rbbiNode.fVal, 7);
            if (rbbiNode.fType == 2) {
                System.out.print(" " + rbbiNode.fText);
            }
        }
        System.out.println("");
    }
    
    static void printString(final String s, final int n) {
        for (int i = n; i < 0; ++i) {
            System.out.print(' ');
        }
        for (int j = s.length(); j < n; ++j) {
            System.out.print(' ');
        }
        System.out.print(s);
    }
    
    static void printInt(final int n, final int n2) {
        final String string = Integer.toString(n);
        printString(string, Math.max(n2, string.length() + 1));
    }
    
    static void printHex(final int n, final int n2) {
        final String string = Integer.toString(n, 16);
        printString("00000".substring(0, Math.max(0, 5 - string.length())) + string, n2);
    }
    
    void printTree(final boolean b) {
        if (b) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("    Serial       type     Parent  LeftChild  RightChild    position  value");
        }
        printNode(this);
        if (this.fType != 2) {
            if (this.fLeftChild != null) {
                this.fLeftChild.printTree(false);
            }
            if (this.fRightChild != null) {
                this.fRightChild.printTree(false);
            }
        }
    }
    
    static {
        RBBINode.nodeTypeNames = new String[] { "setRef", "uset", "varRef", "leafChar", "lookAhead", "tag", "endMark", "opStart", "opCat", "opOr", "opStar", "opPlus", "opQuestion", "opBreak", "opReverse", "opLParen" };
    }
}
