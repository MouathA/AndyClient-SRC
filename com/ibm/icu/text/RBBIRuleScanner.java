package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.impl.*;
import java.text.*;

class RBBIRuleScanner
{
    private static final int kStackSize = 100;
    RBBIRuleBuilder fRB;
    int fScanIndex;
    int fNextIndex;
    boolean fQuoteMode;
    int fLineNum;
    int fCharNum;
    int fLastChar;
    RBBIRuleChar fC;
    String fVarName;
    short[] fStack;
    int fStackPtr;
    RBBINode[] fNodeStack;
    int fNodeStackPtr;
    boolean fReverseRule;
    boolean fLookAheadRule;
    RBBISymbolTable fSymbolTable;
    HashMap fSetTable;
    UnicodeSet[] fRuleSets;
    int fRuleNum;
    int fOptionStart;
    private static String gRuleSet_rule_char_pattern;
    private static String gRuleSet_name_char_pattern;
    private static String gRuleSet_digit_char_pattern;
    private static String gRuleSet_name_start_char_pattern;
    private static String gRuleSet_white_space_pattern;
    private static String kAny;
    static final int chNEL = 133;
    static final int chLS = 8232;
    
    RBBIRuleScanner(final RBBIRuleBuilder frb) {
        this.fC = new RBBIRuleChar();
        this.fStack = new short[100];
        this.fNodeStack = new RBBINode[100];
        this.fSetTable = new HashMap();
        this.fRuleSets = new UnicodeSet[10];
        this.fRB = frb;
        this.fLineNum = 1;
        this.fRuleSets[3] = new UnicodeSet(RBBIRuleScanner.gRuleSet_rule_char_pattern);
        this.fRuleSets[4] = new UnicodeSet(RBBIRuleScanner.gRuleSet_white_space_pattern);
        this.fRuleSets[1] = new UnicodeSet(RBBIRuleScanner.gRuleSet_name_char_pattern);
        this.fRuleSets[2] = new UnicodeSet(RBBIRuleScanner.gRuleSet_name_start_char_pattern);
        this.fRuleSets[0] = new UnicodeSet(RBBIRuleScanner.gRuleSet_digit_char_pattern);
        this.fSymbolTable = new RBBISymbolTable(this, frb.fRules);
    }
    
    boolean doParseActions(final int n) {
        switch (n) {
            case 11: {
                this.pushNewNode(7);
                ++this.fRuleNum;
                break;
            }
            case 9: {
                this.fixOpStack(4);
                final RBBINode fLeftChild = this.fNodeStack[this.fNodeStackPtr--];
                final RBBINode pushNewNode = this.pushNewNode(9);
                pushNewNode.fLeftChild = fLeftChild;
                fLeftChild.fParent = pushNewNode;
                break;
            }
            case 7: {
                this.fixOpStack(4);
                final RBBINode fLeftChild2 = this.fNodeStack[this.fNodeStackPtr--];
                final RBBINode pushNewNode2 = this.pushNewNode(8);
                pushNewNode2.fLeftChild = fLeftChild2;
                fLeftChild2.fParent = pushNewNode2;
                break;
            }
            case 12: {
                this.pushNewNode(15);
                break;
            }
            case 10: {
                this.fixOpStack(2);
                break;
            }
            case 13: {
                break;
            }
            case 22: {
                this.fNodeStack[this.fNodeStackPtr - 1].fFirstPos = this.fNextIndex;
                this.pushNewNode(7);
                break;
            }
            case 3: {
                this.fixOpStack(1);
                final RBBINode rbbiNode = this.fNodeStack[this.fNodeStackPtr - 2];
                final RBBINode fParent = this.fNodeStack[this.fNodeStackPtr - 1];
                final RBBINode fLeftChild3 = this.fNodeStack[this.fNodeStackPtr];
                fLeftChild3.fFirstPos = rbbiNode.fFirstPos;
                fLeftChild3.fLastPos = this.fScanIndex;
                fLeftChild3.fText = this.fRB.fRules.substring(fLeftChild3.fFirstPos, fLeftChild3.fLastPos);
                fParent.fLeftChild = fLeftChild3;
                fLeftChild3.fParent = fParent;
                this.fSymbolTable.addEntry(fParent.fText, fParent);
                this.fNodeStackPtr -= 3;
                break;
            }
            case 4: {
                this.fixOpStack(1);
                if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rtree") >= 0) {
                    this.printNodeStack("end of rule");
                }
                Assert.assrt(this.fNodeStackPtr == 1);
                if (this.fLookAheadRule) {
                    final RBBINode fLeftChild4 = this.fNodeStack[this.fNodeStackPtr];
                    final RBBINode pushNewNode3 = this.pushNewNode(6);
                    final RBBINode pushNewNode4 = this.pushNewNode(8);
                    this.fNodeStackPtr -= 2;
                    pushNewNode4.fLeftChild = fLeftChild4;
                    pushNewNode4.fRightChild = pushNewNode3;
                    this.fNodeStack[this.fNodeStackPtr] = pushNewNode4;
                    pushNewNode3.fVal = this.fRuleNum;
                    pushNewNode3.fLookAheadEnd = true;
                }
                final int n2 = this.fReverseRule ? 1 : this.fRB.fDefaultTree;
                if (this.fRB.fTreeRoots[n2] != null) {
                    final RBBINode fRightChild = this.fNodeStack[this.fNodeStackPtr];
                    final RBBINode fLeftChild5 = this.fRB.fTreeRoots[n2];
                    final RBBINode pushNewNode5 = this.pushNewNode(9);
                    pushNewNode5.fLeftChild = fLeftChild5;
                    fLeftChild5.fParent = pushNewNode5;
                    pushNewNode5.fRightChild = fRightChild;
                    fRightChild.fParent = pushNewNode5;
                    this.fRB.fTreeRoots[n2] = pushNewNode5;
                }
                else {
                    this.fRB.fTreeRoots[n2] = this.fNodeStack[this.fNodeStackPtr];
                }
                this.fReverseRule = false;
                this.fLookAheadRule = false;
                this.fNodeStackPtr = 0;
                break;
            }
            case 18: {
                this.error(66052);
                break;
            }
            case 31: {
                this.error(66052);
                break;
            }
            case 28: {
                final RBBINode fLeftChild6 = this.fNodeStack[this.fNodeStackPtr--];
                final RBBINode pushNewNode6 = this.pushNewNode(11);
                pushNewNode6.fLeftChild = fLeftChild6;
                fLeftChild6.fParent = pushNewNode6;
                break;
            }
            case 29: {
                final RBBINode fLeftChild7 = this.fNodeStack[this.fNodeStackPtr--];
                final RBBINode pushNewNode7 = this.pushNewNode(12);
                pushNewNode7.fLeftChild = fLeftChild7;
                fLeftChild7.fParent = pushNewNode7;
                break;
            }
            case 30: {
                final RBBINode fLeftChild8 = this.fNodeStack[this.fNodeStackPtr--];
                final RBBINode pushNewNode8 = this.pushNewNode(10);
                pushNewNode8.fLeftChild = fLeftChild8;
                fLeftChild8.fParent = pushNewNode8;
                break;
            }
            case 17: {
                final RBBINode pushNewNode9 = this.pushNewNode(0);
                this.findSetFor(String.valueOf((char)this.fC.fChar), pushNewNode9, null);
                pushNewNode9.fFirstPos = this.fScanIndex;
                pushNewNode9.fLastPos = this.fNextIndex;
                pushNewNode9.fText = this.fRB.fRules.substring(pushNewNode9.fFirstPos, pushNewNode9.fLastPos);
                break;
            }
            case 2: {
                final RBBINode pushNewNode10 = this.pushNewNode(0);
                this.findSetFor(RBBIRuleScanner.kAny, pushNewNode10, null);
                pushNewNode10.fFirstPos = this.fScanIndex;
                pushNewNode10.fLastPos = this.fNextIndex;
                pushNewNode10.fText = this.fRB.fRules.substring(pushNewNode10.fFirstPos, pushNewNode10.fLastPos);
                break;
            }
            case 21: {
                final RBBINode pushNewNode11 = this.pushNewNode(4);
                pushNewNode11.fVal = this.fRuleNum;
                pushNewNode11.fFirstPos = this.fScanIndex;
                pushNewNode11.fLastPos = this.fNextIndex;
                pushNewNode11.fText = this.fRB.fRules.substring(pushNewNode11.fFirstPos, pushNewNode11.fLastPos);
                this.fLookAheadRule = true;
                break;
            }
            case 23: {
                final RBBINode pushNewNode12 = this.pushNewNode(5);
                pushNewNode12.fVal = 0;
                pushNewNode12.fFirstPos = this.fScanIndex;
                pushNewNode12.fLastPos = this.fNextIndex;
                break;
            }
            case 25: {
                final RBBINode rbbiNode2 = this.fNodeStack[this.fNodeStackPtr];
                rbbiNode2.fVal = rbbiNode2.fVal * 10 + UCharacter.digit((char)this.fC.fChar, 10);
                break;
            }
            case 27: {
                final RBBINode rbbiNode3 = this.fNodeStack[this.fNodeStackPtr];
                rbbiNode3.fLastPos = this.fNextIndex;
                rbbiNode3.fText = this.fRB.fRules.substring(rbbiNode3.fFirstPos, rbbiNode3.fLastPos);
                break;
            }
            case 26: {
                this.error(66062);
                break;
            }
            case 15: {
                this.fOptionStart = this.fScanIndex;
                break;
            }
            case 14: {
                final String substring = this.fRB.fRules.substring(this.fOptionStart, this.fScanIndex);
                if (substring.equals("chain")) {
                    this.fRB.fChainRules = true;
                    break;
                }
                if (substring.equals("LBCMNoChain")) {
                    this.fRB.fLBCMNoChain = true;
                    break;
                }
                if (substring.equals("forward")) {
                    this.fRB.fDefaultTree = 0;
                    break;
                }
                if (substring.equals("reverse")) {
                    this.fRB.fDefaultTree = 1;
                    break;
                }
                if (substring.equals("safe_forward")) {
                    this.fRB.fDefaultTree = 2;
                    break;
                }
                if (substring.equals("safe_reverse")) {
                    this.fRB.fDefaultTree = 3;
                    break;
                }
                if (substring.equals("lookAheadHardBreak")) {
                    this.fRB.fLookAheadHardBreak = true;
                    break;
                }
                this.error(66061);
                break;
            }
            case 16: {
                this.fReverseRule = true;
                break;
            }
            case 24: {
                this.pushNewNode(2).fFirstPos = this.fScanIndex;
                break;
            }
            case 5: {
                final RBBINode rbbiNode4 = this.fNodeStack[this.fNodeStackPtr];
                if (rbbiNode4 == null || rbbiNode4.fType != 2) {
                    this.error(66049);
                    break;
                }
                rbbiNode4.fLastPos = this.fScanIndex;
                rbbiNode4.fText = this.fRB.fRules.substring(rbbiNode4.fFirstPos + 1, rbbiNode4.fLastPos);
                rbbiNode4.fLeftChild = this.fSymbolTable.lookupNode(rbbiNode4.fText);
                break;
            }
            case 1: {
                if (this.fNodeStack[this.fNodeStackPtr].fLeftChild == null) {
                    this.error(66058);
                    break;
                }
                break;
            }
            case 8: {
                break;
            }
            case 19: {
                this.error(66054);
                break;
            }
            case 6: {
                break;
            }
            case 20: {
                this.scanSet();
                break;
            }
            default: {
                this.error(66049);
                break;
            }
        }
        return false;
    }
    
    void error(final int n) {
        throw new IllegalArgumentException("Error " + n + " at line " + this.fLineNum + " column " + this.fCharNum);
    }
    
    void fixOpStack(final int n) {
        while (true) {
            final RBBINode fParent = this.fNodeStack[this.fNodeStackPtr - 1];
            if (fParent.fPrecedence == 0) {
                System.out.print("RBBIRuleScanner.fixOpStack, bad operator node");
                this.error(66049);
                return;
            }
            if (fParent.fPrecedence < n || fParent.fPrecedence <= 2) {
                if (n <= 2) {
                    if (fParent.fPrecedence != n) {
                        this.error(66056);
                    }
                    this.fNodeStack[this.fNodeStackPtr - 1] = this.fNodeStack[this.fNodeStackPtr];
                    --this.fNodeStackPtr;
                }
                return;
            }
            fParent.fRightChild = this.fNodeStack[this.fNodeStackPtr];
            this.fNodeStack[this.fNodeStackPtr].fParent = fParent;
            --this.fNodeStackPtr;
        }
    }
    
    void findSetFor(final String s, final RBBINode fParent, UnicodeSet fInputSet) {
        final RBBISetTableEl rbbiSetTableEl = this.fSetTable.get(s);
        if (rbbiSetTableEl != null) {
            fParent.fLeftChild = rbbiSetTableEl.val;
            Assert.assrt(fParent.fLeftChild.fType == 1);
            return;
        }
        if (fInputSet == null) {
            if (s.equals(RBBIRuleScanner.kAny)) {
                fInputSet = new UnicodeSet(0, 1114111);
            }
            else {
                final int char1 = UTF16.charAt(s, 0);
                fInputSet = new UnicodeSet(char1, char1);
            }
        }
        final RBBINode rbbiNode = new RBBINode(1);
        rbbiNode.fInputSet = fInputSet;
        rbbiNode.fParent = fParent;
        fParent.fLeftChild = rbbiNode;
        rbbiNode.fText = s;
        this.fRB.fUSetNodes.add(rbbiNode);
        final RBBISetTableEl rbbiSetTableEl2 = new RBBISetTableEl();
        rbbiSetTableEl2.key = s;
        rbbiSetTableEl2.val = rbbiNode;
        this.fSetTable.put(rbbiSetTableEl2.key, rbbiSetTableEl2);
    }
    
    static String stripRules(final String s) {
        final StringBuilder sb = new StringBuilder();
        final int length = s.length();
        while (0 < length) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            char c = s.charAt(n);
            if (c == '#') {
                while (0 < length && c != '\r' && c != '\n' && c != '\u0085') {
                    final int n3 = 0;
                    ++n2;
                    c = s.charAt(n3);
                }
            }
            if (!UCharacter.isISOControl(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    int nextCharLL() {
        if (this.fNextIndex >= this.fRB.fRules.length()) {
            return -1;
        }
        final int char1 = UTF16.charAt(this.fRB.fRules, this.fNextIndex);
        this.fNextIndex = UTF16.moveCodePointOffset(this.fRB.fRules, this.fNextIndex, 1);
        if (char1 == 13 || char1 == 133 || char1 == 8232 || (char1 == 10 && this.fLastChar != 13)) {
            ++this.fLineNum;
            this.fCharNum = 0;
            if (this.fQuoteMode) {
                this.error(66057);
                this.fQuoteMode = false;
            }
        }
        else if (char1 != 10) {
            ++this.fCharNum;
        }
        return this.fLastChar = char1;
    }
    
    void nextChar(final RBBIRuleChar rbbiRuleChar) {
        this.fScanIndex = this.fNextIndex;
        rbbiRuleChar.fChar = this.nextCharLL();
        rbbiRuleChar.fEscaped = false;
        if (rbbiRuleChar.fChar == 39) {
            if (UTF16.charAt(this.fRB.fRules, this.fNextIndex) != 39) {
                this.fQuoteMode = !this.fQuoteMode;
                if (this.fQuoteMode) {
                    rbbiRuleChar.fChar = 40;
                }
                else {
                    rbbiRuleChar.fChar = 41;
                }
                rbbiRuleChar.fEscaped = false;
                return;
            }
            rbbiRuleChar.fChar = this.nextCharLL();
            rbbiRuleChar.fEscaped = true;
        }
        if (this.fQuoteMode) {
            rbbiRuleChar.fEscaped = true;
        }
        else {
            if (rbbiRuleChar.fChar == 35) {
                do {
                    rbbiRuleChar.fChar = this.nextCharLL();
                } while (rbbiRuleChar.fChar != -1 && rbbiRuleChar.fChar != 13 && rbbiRuleChar.fChar != 10 && rbbiRuleChar.fChar != 133 && rbbiRuleChar.fChar != 8232);
            }
            if (rbbiRuleChar.fChar == -1) {
                return;
            }
            if (rbbiRuleChar.fChar == 92) {
                rbbiRuleChar.fEscaped = true;
                final int[] array = { this.fNextIndex };
                rbbiRuleChar.fChar = Utility.unescapeAt(this.fRB.fRules, array);
                if (array[0] == this.fNextIndex) {
                    this.error(66050);
                }
                this.fCharNum += array[0] - this.fNextIndex;
                this.fNextIndex = array[0];
            }
        }
    }
    
    void parse() {
        this.nextChar(this.fC);
        while (true) {
            while (true) {
                final RBBIRuleParseTable.RBBIRuleTableElement rbbiRuleTableElement = RBBIRuleParseTable.gRuleParseStateTable[1];
                if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                    System.out.println("char, line, col = ('" + (char)this.fC.fChar + "', " + this.fLineNum + ", " + this.fCharNum + "    state = " + rbbiRuleTableElement.fStateName);
                }
                RBBIRuleParseTable.RBBIRuleTableElement rbbiRuleTableElement2;
                while (true) {
                    rbbiRuleTableElement2 = RBBIRuleParseTable.gRuleParseStateTable[1];
                    if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                        System.out.print(".");
                    }
                    if (rbbiRuleTableElement2.fCharClass < 127 && !this.fC.fEscaped && rbbiRuleTableElement2.fCharClass == this.fC.fChar) {
                        break;
                    }
                    if (rbbiRuleTableElement2.fCharClass == 255) {
                        break;
                    }
                    if (rbbiRuleTableElement2.fCharClass == 254 && this.fC.fEscaped) {
                        break;
                    }
                    if (rbbiRuleTableElement2.fCharClass == 253 && this.fC.fEscaped) {
                        if (this.fC.fChar == 80) {
                            break;
                        }
                        if (this.fC.fChar == 112) {
                            break;
                        }
                    }
                    if (rbbiRuleTableElement2.fCharClass == 252 && this.fC.fChar == -1) {
                        break;
                    }
                    if (rbbiRuleTableElement2.fCharClass >= 128 && rbbiRuleTableElement2.fCharClass < 240 && !this.fC.fEscaped && this.fC.fChar != -1 && this.fRuleSets[rbbiRuleTableElement2.fCharClass - 128].contains(this.fC.fChar)) {
                        break;
                    }
                    int n = 0;
                    ++n;
                }
                if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                    System.out.println("");
                }
                if (!this.doParseActions(rbbiRuleTableElement2.fAction)) {
                    if (this.fRB.fTreeRoots[1] == null) {
                        this.fRB.fTreeRoots[1] = this.pushNewNode(10);
                        final RBBINode pushNewNode = this.pushNewNode(0);
                        this.findSetFor(RBBIRuleScanner.kAny, pushNewNode, null);
                        this.fRB.fTreeRoots[1].fLeftChild = pushNewNode;
                        pushNewNode.fParent = this.fRB.fTreeRoots[1];
                        this.fNodeStackPtr -= 2;
                    }
                    if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("symbols") >= 0) {
                        this.fSymbolTable.rbbiSymtablePrint();
                    }
                    if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ptree") >= 0) {
                        System.out.println("Completed Forward Rules Parse Tree...");
                        this.fRB.fTreeRoots[0].printTree(true);
                        System.out.println("\nCompleted Reverse Rules Parse Tree...");
                        this.fRB.fTreeRoots[1].printTree(true);
                        System.out.println("\nCompleted Safe Point Forward Rules Parse Tree...");
                        if (this.fRB.fTreeRoots[2] == null) {
                            System.out.println("  -- null -- ");
                        }
                        else {
                            this.fRB.fTreeRoots[2].printTree(true);
                        }
                        System.out.println("\nCompleted Safe Point Reverse Rules Parse Tree...");
                        if (this.fRB.fTreeRoots[3] == null) {
                            System.out.println("  -- null -- ");
                        }
                        else {
                            this.fRB.fTreeRoots[3].printTree(true);
                        }
                    }
                    return;
                }
                if (rbbiRuleTableElement2.fPushState != 0) {
                    ++this.fStackPtr;
                    if (this.fStackPtr >= 100) {
                        System.out.println("RBBIRuleScanner.parse() - state stack overflow.");
                        this.error(66049);
                    }
                    this.fStack[this.fStackPtr] = rbbiRuleTableElement2.fPushState;
                }
                if (rbbiRuleTableElement2.fNextChar) {
                    this.nextChar(this.fC);
                }
                if (rbbiRuleTableElement2.fNextState != 255) {
                    final short fNextState = rbbiRuleTableElement2.fNextState;
                }
                else {
                    final short n2 = this.fStack[this.fStackPtr];
                    --this.fStackPtr;
                    if (this.fStackPtr >= 0) {
                        continue;
                    }
                    System.out.println("RBBIRuleScanner.parse() - state stack underflow.");
                    this.error(66049);
                }
            }
            continue;
        }
    }
    
    void printNodeStack(final String s) {
        System.out.println(s + ".  Dumping node stack...\n");
        for (int i = this.fNodeStackPtr; i > 0; --i) {
            this.fNodeStack[i].printTree(true);
        }
    }
    
    RBBINode pushNewNode(final int n) {
        ++this.fNodeStackPtr;
        if (this.fNodeStackPtr >= 100) {
            System.out.println("RBBIRuleScanner.pushNewNode - stack overflow.");
            this.error(66049);
        }
        return this.fNodeStack[this.fNodeStackPtr] = new RBBINode(n);
    }
    
    void scanSet() {
        final ParsePosition parsePosition = new ParsePosition(this.fScanIndex);
        final int fScanIndex = this.fScanIndex;
        final UnicodeSet set = new UnicodeSet(this.fRB.fRules, parsePosition, this.fSymbolTable, 1);
        if (set.isEmpty()) {
            this.error(66060);
        }
        while (this.fNextIndex < parsePosition.getIndex()) {
            this.nextCharLL();
        }
        final RBBINode pushNewNode = this.pushNewNode(0);
        pushNewNode.fFirstPos = fScanIndex;
        pushNewNode.fLastPos = this.fNextIndex;
        this.findSetFor(pushNewNode.fText = this.fRB.fRules.substring(pushNewNode.fFirstPos, pushNewNode.fLastPos), pushNewNode, set);
    }
    
    static {
        RBBIRuleScanner.gRuleSet_rule_char_pattern = "[^[\\p{Z}\\u0020-\\u007f]-[\\p{L}]-[\\p{N}]]";
        RBBIRuleScanner.gRuleSet_name_char_pattern = "[_\\p{L}\\p{N}]";
        RBBIRuleScanner.gRuleSet_digit_char_pattern = "[0-9]";
        RBBIRuleScanner.gRuleSet_name_start_char_pattern = "[_\\p{L}]";
        RBBIRuleScanner.gRuleSet_white_space_pattern = "[\\p{Pattern_White_Space}]";
        RBBIRuleScanner.kAny = "any";
    }
    
    static class RBBISetTableEl
    {
        String key;
        RBBINode val;
    }
    
    static class RBBIRuleChar
    {
        int fChar;
        boolean fEscaped;
    }
}
