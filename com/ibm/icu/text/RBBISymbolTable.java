package com.ibm.icu.text;

import java.util.*;
import java.text.*;
import com.ibm.icu.lang.*;

class RBBISymbolTable implements SymbolTable
{
    String fRules;
    HashMap fHashTable;
    RBBIRuleScanner fRuleScanner;
    String ffffString;
    UnicodeSet fCachedSetLookup;
    
    RBBISymbolTable(final RBBIRuleScanner fRuleScanner, final String fRules) {
        this.fRules = fRules;
        this.fRuleScanner = fRuleScanner;
        this.fHashTable = new HashMap();
        this.ffffString = "\uffff";
    }
    
    public char[] lookup(final String s) {
        final RBBISymbolTableEntry rbbiSymbolTableEntry = this.fHashTable.get(s);
        if (rbbiSymbolTableEntry == null) {
            return null;
        }
        RBBINode rbbiNode;
        for (rbbiNode = rbbiSymbolTableEntry.val; rbbiNode.fLeftChild.fType == 2; rbbiNode = rbbiNode.fLeftChild) {}
        final RBBINode fLeftChild = rbbiNode.fLeftChild;
        String s2;
        if (fLeftChild.fType == 0) {
            this.fCachedSetLookup = fLeftChild.fLeftChild.fInputSet;
            s2 = this.ffffString;
        }
        else {
            this.fRuleScanner.error(66063);
            s2 = fLeftChild.fText;
            this.fCachedSetLookup = null;
        }
        return s2.toCharArray();
    }
    
    public UnicodeMatcher lookupMatcher(final int n) {
        UnicodeMatcher fCachedSetLookup = null;
        if (n == 65535) {
            fCachedSetLookup = this.fCachedSetLookup;
            this.fCachedSetLookup = null;
        }
        return fCachedSetLookup;
    }
    
    public String parseReference(final String s, final ParsePosition parsePosition, final int n) {
        int i;
        final int n2 = i = parsePosition.getIndex();
        final String s2 = "";
        while (i < n) {
            final int char1 = UTF16.charAt(s, i);
            if (i == n2 && !UCharacter.isUnicodeIdentifierStart(char1)) {
                break;
            }
            if (!UCharacter.isUnicodeIdentifierPart(char1)) {
                break;
            }
            i += UTF16.getCharCount(char1);
        }
        if (i == n2) {
            return s2;
        }
        parsePosition.setIndex(i);
        return s.substring(n2, i);
    }
    
    RBBINode lookupNode(final String s) {
        RBBINode val = null;
        final RBBISymbolTableEntry rbbiSymbolTableEntry = this.fHashTable.get(s);
        if (rbbiSymbolTableEntry != null) {
            val = rbbiSymbolTableEntry.val;
        }
        return val;
    }
    
    void addEntry(final String key, final RBBINode val) {
        if (this.fHashTable.get(key) != null) {
            this.fRuleScanner.error(66055);
            return;
        }
        final RBBISymbolTableEntry rbbiSymbolTableEntry = new RBBISymbolTableEntry();
        rbbiSymbolTableEntry.key = key;
        rbbiSymbolTableEntry.val = val;
        this.fHashTable.put(rbbiSymbolTableEntry.key, rbbiSymbolTableEntry);
    }
    
    void rbbiSymtablePrint() {
        System.out.print("Variable Definitions\nName               Node Val     String Val\n----------------------------------------------------------------------\n");
        final RBBISymbolTableEntry[] array = (RBBISymbolTableEntry[])this.fHashTable.values().toArray(new RBBISymbolTableEntry[0]);
        int n = 0;
        while (0 < array.length) {
            final RBBISymbolTableEntry rbbiSymbolTableEntry = array[0];
            System.out.print("  " + rbbiSymbolTableEntry.key + "  ");
            System.out.print("  " + rbbiSymbolTableEntry.val + "  ");
            System.out.print(rbbiSymbolTableEntry.val.fLeftChild.fText);
            System.out.print("\n");
            ++n;
        }
        System.out.println("\nParsed Variable Definitions\n");
        while (0 < array.length) {
            final RBBISymbolTableEntry rbbiSymbolTableEntry2 = array[0];
            System.out.print(rbbiSymbolTableEntry2.key);
            rbbiSymbolTableEntry2.val.fLeftChild.printTree(true);
            System.out.print("\n");
            ++n;
        }
    }
    
    static class RBBISymbolTableEntry
    {
        String key;
        RBBINode val;
    }
}
