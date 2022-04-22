package org.apache.commons.codec.language;

import org.apache.commons.codec.*;
import java.util.*;

public class DoubleMetaphone implements StringEncoder
{
    private static final String VOWELS = "AEIOUY";
    private static final String[] L_R_N_M_B_H_F_V_W_SPACE;
    private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER;
    private static final String[] L_T_K_S_N_M_B_Z;
    private int maxCodeLen;
    
    public DoubleMetaphone() {
        this.maxCodeLen = 4;
    }
    
    public String doubleMetaphone(final String s) {
        return this.doubleMetaphone(s, false);
    }
    
    public String doubleMetaphone(String cleanInput, final boolean b) {
        cleanInput = this.cleanInput(cleanInput);
        if (cleanInput == null) {
            return null;
        }
        final boolean slavoGermanic = this.isSlavoGermanic(cleanInput);
        int n = this.isSilentStart(cleanInput) ? 1 : 0;
        final DoubleMetaphoneResult doubleMetaphoneResult = new DoubleMetaphoneResult(this.getMaxCodeLen());
        while (!doubleMetaphoneResult.isComplete() && n <= cleanInput.length() - 1) {
            switch (cleanInput.charAt(n)) {
                case 'A':
                case 'E':
                case 'I':
                case 'O':
                case 'U':
                case 'Y': {
                    n = this.handleAEIOUY(doubleMetaphoneResult, n);
                    continue;
                }
                case 'B': {
                    doubleMetaphoneResult.append('P');
                    n = ((this.charAt(cleanInput, n + 1) == 'B') ? (n + 2) : (n + 1));
                    continue;
                }
                case '\u00c7': {
                    doubleMetaphoneResult.append('S');
                    ++n;
                    continue;
                }
                case 'C': {
                    n = this.handleC(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'D': {
                    n = this.handleD(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'F': {
                    doubleMetaphoneResult.append('F');
                    n = ((this.charAt(cleanInput, n + 1) == 'F') ? (n + 2) : (n + 1));
                    continue;
                }
                case 'G': {
                    n = this.handleG(cleanInput, doubleMetaphoneResult, n, slavoGermanic);
                    continue;
                }
                case 'H': {
                    n = this.handleH(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'J': {
                    n = this.handleJ(cleanInput, doubleMetaphoneResult, n, slavoGermanic);
                    continue;
                }
                case 'K': {
                    doubleMetaphoneResult.append('K');
                    n = ((this.charAt(cleanInput, n + 1) == 'K') ? (n + 2) : (n + 1));
                    continue;
                }
                case 'L': {
                    n = this.handleL(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'M': {
                    doubleMetaphoneResult.append('M');
                    n = (this.conditionM0(cleanInput, n) ? (n + 2) : (n + 1));
                    continue;
                }
                case 'N': {
                    doubleMetaphoneResult.append('N');
                    n = ((this.charAt(cleanInput, n + 1) == 'N') ? (n + 2) : (n + 1));
                    continue;
                }
                case '\u00d1': {
                    doubleMetaphoneResult.append('N');
                    ++n;
                    continue;
                }
                case 'P': {
                    n = this.handleP(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'Q': {
                    doubleMetaphoneResult.append('K');
                    n = ((this.charAt(cleanInput, n + 1) == 'Q') ? (n + 2) : (n + 1));
                    continue;
                }
                case 'R': {
                    n = this.handleR(cleanInput, doubleMetaphoneResult, n, slavoGermanic);
                    continue;
                }
                case 'S': {
                    n = this.handleS(cleanInput, doubleMetaphoneResult, n, slavoGermanic);
                    continue;
                }
                case 'T': {
                    n = this.handleT(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'V': {
                    doubleMetaphoneResult.append('F');
                    n = ((this.charAt(cleanInput, n + 1) == 'V') ? (n + 2) : (n + 1));
                    continue;
                }
                case 'W': {
                    n = this.handleW(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'X': {
                    n = this.handleX(cleanInput, doubleMetaphoneResult, n);
                    continue;
                }
                case 'Z': {
                    n = this.handleZ(cleanInput, doubleMetaphoneResult, n, slavoGermanic);
                    continue;
                }
                default: {
                    ++n;
                    continue;
                }
            }
        }
        return b ? doubleMetaphoneResult.getAlternate() : doubleMetaphoneResult.getPrimary();
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
        }
        return this.doubleMetaphone((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.doubleMetaphone(s);
    }
    
    public boolean isDoubleMetaphoneEqual(final String s, final String s2) {
        return this.isDoubleMetaphoneEqual(s, s2, false);
    }
    
    public boolean isDoubleMetaphoneEqual(final String s, final String s2, final boolean b) {
        return this.doubleMetaphone(s, b).equals(this.doubleMetaphone(s2, b));
    }
    
    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }
    
    public void setMaxCodeLen(final int maxCodeLen) {
        this.maxCodeLen = maxCodeLen;
    }
    
    private int handleAEIOUY(final DoubleMetaphoneResult doubleMetaphoneResult, final int n) {
        if (n == 0) {
            doubleMetaphoneResult.append('A');
        }
        return n + 1;
    }
    
    private int handleC(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int handleCH) {
        if (this.conditionC0(s, handleCH)) {
            doubleMetaphoneResult.append('K');
            handleCH += 2;
        }
        else if (handleCH == 0 && contains(s, handleCH, 6, "CAESAR")) {
            doubleMetaphoneResult.append('S');
            handleCH += 2;
        }
        else if (contains(s, handleCH, 2, "CH")) {
            handleCH = this.handleCH(s, doubleMetaphoneResult, handleCH);
        }
        else if (contains(s, handleCH, 2, "CZ") && !contains(s, handleCH - 2, 4, "WICZ")) {
            doubleMetaphoneResult.append('S', 'X');
            handleCH += 2;
        }
        else if (contains(s, handleCH + 1, 3, "CIA")) {
            doubleMetaphoneResult.append('X');
            handleCH += 3;
        }
        else {
            if (contains(s, handleCH, 2, "CC") && (handleCH != 1 || this.charAt(s, 0) != 'M')) {
                return this.handleCC(s, doubleMetaphoneResult, handleCH);
            }
            if (contains(s, handleCH, 2, "CK", "CG", "CQ")) {
                doubleMetaphoneResult.append('K');
                handleCH += 2;
            }
            else if (contains(s, handleCH, 2, "CI", "CE", "CY")) {
                if (contains(s, handleCH, 3, "CIO", "CIE", "CIA")) {
                    doubleMetaphoneResult.append('S', 'X');
                }
                else {
                    doubleMetaphoneResult.append('S');
                }
                handleCH += 2;
            }
            else {
                doubleMetaphoneResult.append('K');
                if (contains(s, handleCH + 1, 2, " C", " Q", " G")) {
                    handleCH += 3;
                }
                else if (contains(s, handleCH + 1, 1, "C", "K", "Q") && !contains(s, handleCH + 1, 2, "CE", "CI")) {
                    handleCH += 2;
                }
                else {
                    ++handleCH;
                }
            }
        }
        return handleCH;
    }
    
    private int handleCC(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n + 2, 1, "I", "E", "H") && !contains(s, n + 2, 2, "HU")) {
            if ((n == 1 && this.charAt(s, n - 1) == 'A') || contains(s, n - 1, 5, "UCCEE", "UCCES")) {
                doubleMetaphoneResult.append("KS");
            }
            else {
                doubleMetaphoneResult.append('X');
            }
            n += 3;
        }
        else {
            doubleMetaphoneResult.append('K');
            n += 2;
        }
        return n;
    }
    
    private int handleCH(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, final int n) {
        if (n > 0 && contains(s, n, 4, "CHAE")) {
            doubleMetaphoneResult.append('K', 'X');
            return n + 2;
        }
        if (this.conditionCH0(s, n)) {
            doubleMetaphoneResult.append('K');
            return n + 2;
        }
        if (this.conditionCH1(s, n)) {
            doubleMetaphoneResult.append('K');
            return n + 2;
        }
        if (n > 0) {
            if (contains(s, 0, 2, "MC")) {
                doubleMetaphoneResult.append('K');
            }
            else {
                doubleMetaphoneResult.append('X', 'K');
            }
        }
        else {
            doubleMetaphoneResult.append('X');
        }
        return n + 2;
    }
    
    private int handleD(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n, 2, "DG")) {
            if (contains(s, n + 2, 1, "I", "E", "Y")) {
                doubleMetaphoneResult.append('J');
                n += 3;
            }
            else {
                doubleMetaphoneResult.append("TK");
                n += 2;
            }
        }
        else if (contains(s, n, 2, "DT", "DD")) {
            doubleMetaphoneResult.append('T');
            n += 2;
        }
        else {
            doubleMetaphoneResult.append('T');
            ++n;
        }
        return n;
    }
    
    private int handleG(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int handleGH, final boolean b) {
        if (this.charAt(s, handleGH + 1) == 'H') {
            handleGH = this.handleGH(s, doubleMetaphoneResult, handleGH);
        }
        else if (this.charAt(s, handleGH + 1) == 'N') {
            if (handleGH == 1 && this.isVowel(this.charAt(s, 0)) && !b) {
                doubleMetaphoneResult.append("KN", "N");
            }
            else if (!contains(s, handleGH + 2, 2, "EY") && this.charAt(s, handleGH + 1) != 'Y' && !b) {
                doubleMetaphoneResult.append("N", "KN");
            }
            else {
                doubleMetaphoneResult.append("KN");
            }
            handleGH += 2;
        }
        else if (contains(s, handleGH + 1, 2, "LI") && !b) {
            doubleMetaphoneResult.append("KL", "L");
            handleGH += 2;
        }
        else if (handleGH == 0 && (this.charAt(s, handleGH + 1) == 'Y' || contains(s, handleGH + 1, 2, DoubleMetaphone.ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))) {
            doubleMetaphoneResult.append('K', 'J');
            handleGH += 2;
        }
        else if ((contains(s, handleGH + 1, 2, "ER") || this.charAt(s, handleGH + 1) == 'Y') && !contains(s, 0, 6, "DANGER", "RANGER", "MANGER") && !contains(s, handleGH - 1, 1, "E", "I") && !contains(s, handleGH - 1, 3, "RGY", "OGY")) {
            doubleMetaphoneResult.append('K', 'J');
            handleGH += 2;
        }
        else if (contains(s, handleGH + 1, 1, "E", "I", "Y") || contains(s, handleGH - 1, 4, "AGGI", "OGGI")) {
            if (contains(s, 0, 4, "VAN ", "VON ") || contains(s, 0, 3, "SCH") || contains(s, handleGH + 1, 2, "ET")) {
                doubleMetaphoneResult.append('K');
            }
            else if (contains(s, handleGH + 1, 3, "IER")) {
                doubleMetaphoneResult.append('J');
            }
            else {
                doubleMetaphoneResult.append('J', 'K');
            }
            handleGH += 2;
        }
        else if (this.charAt(s, handleGH + 1) == 'G') {
            handleGH += 2;
            doubleMetaphoneResult.append('K');
        }
        else {
            ++handleGH;
            doubleMetaphoneResult.append('K');
        }
        return handleGH;
    }
    
    private int handleGH(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n > 0 && !this.isVowel(this.charAt(s, n - 1))) {
            doubleMetaphoneResult.append('K');
            n += 2;
        }
        else if (n == 0) {
            if (this.charAt(s, n + 2) == 'I') {
                doubleMetaphoneResult.append('J');
            }
            else {
                doubleMetaphoneResult.append('K');
            }
            n += 2;
        }
        else if ((n > 1 && contains(s, n - 2, 1, "B", "H", "D")) || (n > 2 && contains(s, n - 3, 1, "B", "H", "D")) || (n > 3 && contains(s, n - 4, 1, "B", "H"))) {
            n += 2;
        }
        else {
            if (n > 2 && this.charAt(s, n - 1) == 'U' && contains(s, n - 3, 1, "C", "G", "L", "R", "T")) {
                doubleMetaphoneResult.append('F');
            }
            else if (n > 0 && this.charAt(s, n - 1) != 'I') {
                doubleMetaphoneResult.append('K');
            }
            n += 2;
        }
        return n;
    }
    
    private int handleH(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if ((n == 0 || this.isVowel(this.charAt(s, n - 1))) && this.isVowel(this.charAt(s, n + 1))) {
            doubleMetaphoneResult.append('H');
            n += 2;
        }
        else {
            ++n;
        }
        return n;
    }
    
    private int handleJ(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n, final boolean b) {
        if (contains(s, n, 4, "JOSE") || contains(s, 0, 4, "SAN ")) {
            if ((n == 0 && this.charAt(s, n + 4) == ' ') || s.length() == 4 || contains(s, 0, 4, "SAN ")) {
                doubleMetaphoneResult.append('H');
            }
            else {
                doubleMetaphoneResult.append('J', 'H');
            }
            ++n;
        }
        else {
            if (n == 0 && !contains(s, n, 4, "JOSE")) {
                doubleMetaphoneResult.append('J', 'A');
            }
            else if (this.isVowel(this.charAt(s, n - 1)) && !b && (this.charAt(s, n + 1) == 'A' || this.charAt(s, n + 1) == 'O')) {
                doubleMetaphoneResult.append('J', 'H');
            }
            else if (n == s.length() - 1) {
                doubleMetaphoneResult.append('J', ' ');
            }
            else if (!contains(s, n + 1, 1, DoubleMetaphone.L_T_K_S_N_M_B_Z) && !contains(s, n - 1, 1, "S", "K", "L")) {
                doubleMetaphoneResult.append('J');
            }
            if (this.charAt(s, n + 1) == 'J') {
                n += 2;
            }
            else {
                ++n;
            }
        }
        return n;
    }
    
    private int handleL(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.charAt(s, n + 1) == 'L') {
            if (this.conditionL0(s, n)) {
                doubleMetaphoneResult.appendPrimary('L');
            }
            else {
                doubleMetaphoneResult.append('L');
            }
            n += 2;
        }
        else {
            ++n;
            doubleMetaphoneResult.append('L');
        }
        return n;
    }
    
    private int handleP(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.charAt(s, n + 1) == 'H') {
            doubleMetaphoneResult.append('F');
            n += 2;
        }
        else {
            doubleMetaphoneResult.append('P');
            n = (contains(s, n + 1, 1, "P", "B") ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private int handleR(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, final int n, final boolean b) {
        if (n == s.length() - 1 && !b && contains(s, n - 2, 2, "IE") && !contains(s, n - 4, 2, "ME", "MA")) {
            doubleMetaphoneResult.appendAlternate('R');
        }
        else {
            doubleMetaphoneResult.append('R');
        }
        return (this.charAt(s, n + 1) == 'R') ? (n + 2) : (n + 1);
    }
    
    private int handleS(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int handleSC, final boolean b) {
        if (contains(s, handleSC - 1, 3, "ISL", "YSL")) {
            ++handleSC;
        }
        else if (handleSC == 0 && contains(s, handleSC, 5, "SUGAR")) {
            doubleMetaphoneResult.append('X', 'S');
            ++handleSC;
        }
        else if (contains(s, handleSC, 2, "SH")) {
            if (contains(s, handleSC + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
                doubleMetaphoneResult.append('S');
            }
            else {
                doubleMetaphoneResult.append('X');
            }
            handleSC += 2;
        }
        else if (contains(s, handleSC, 3, "SIO", "SIA") || contains(s, handleSC, 4, "SIAN")) {
            if (b) {
                doubleMetaphoneResult.append('S');
            }
            else {
                doubleMetaphoneResult.append('S', 'X');
            }
            handleSC += 3;
        }
        else if ((handleSC == 0 && contains(s, handleSC + 1, 1, "M", "N", "L", "W")) || contains(s, handleSC + 1, 1, "Z")) {
            doubleMetaphoneResult.append('S', 'X');
            handleSC = (contains(s, handleSC + 1, 1, "Z") ? (handleSC + 2) : (handleSC + 1));
        }
        else if (contains(s, handleSC, 2, "SC")) {
            handleSC = this.handleSC(s, doubleMetaphoneResult, handleSC);
        }
        else {
            if (handleSC == s.length() - 1 && contains(s, handleSC - 2, 2, "AI", "OI")) {
                doubleMetaphoneResult.appendAlternate('S');
            }
            else {
                doubleMetaphoneResult.append('S');
            }
            handleSC = (contains(s, handleSC + 1, 1, "S", "Z") ? (handleSC + 2) : (handleSC + 1));
        }
        return handleSC;
    }
    
    private int handleSC(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, final int n) {
        if (this.charAt(s, n + 2) == 'H') {
            if (contains(s, n + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
                if (contains(s, n + 3, 2, "ER", "EN")) {
                    doubleMetaphoneResult.append("X", "SK");
                }
                else {
                    doubleMetaphoneResult.append("SK");
                }
            }
            else if (n == 0 && !this.isVowel(this.charAt(s, 3)) && this.charAt(s, 3) != 'W') {
                doubleMetaphoneResult.append('X', 'S');
            }
            else {
                doubleMetaphoneResult.append('X');
            }
        }
        else if (contains(s, n + 2, 1, "I", "E", "Y")) {
            doubleMetaphoneResult.append('S');
        }
        else {
            doubleMetaphoneResult.append("SK");
        }
        return n + 3;
    }
    
    private int handleT(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n, 4, "TION")) {
            doubleMetaphoneResult.append('X');
            n += 3;
        }
        else if (contains(s, n, 3, "TIA", "TCH")) {
            doubleMetaphoneResult.append('X');
            n += 3;
        }
        else if (contains(s, n, 2, "TH") || contains(s, n, 3, "TTH")) {
            if (contains(s, n + 2, 2, "OM", "AM") || contains(s, 0, 4, "VAN ", "VON ") || contains(s, 0, 3, "SCH")) {
                doubleMetaphoneResult.append('T');
            }
            else {
                doubleMetaphoneResult.append('0', 'T');
            }
            n += 2;
        }
        else {
            doubleMetaphoneResult.append('T');
            n = (contains(s, n + 1, 1, "T", "D") ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private int handleW(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n, 2, "WR")) {
            doubleMetaphoneResult.append('R');
            n += 2;
        }
        else if (n == 0 && (this.isVowel(this.charAt(s, n + 1)) || contains(s, n, 2, "WH"))) {
            if (this.isVowel(this.charAt(s, n + 1))) {
                doubleMetaphoneResult.append('A', 'F');
            }
            else {
                doubleMetaphoneResult.append('A');
            }
            ++n;
        }
        else if ((n == s.length() - 1 && this.isVowel(this.charAt(s, n - 1))) || contains(s, n - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY") || contains(s, 0, 3, "SCH")) {
            doubleMetaphoneResult.appendAlternate('F');
            ++n;
        }
        else if (contains(s, n, 4, "WICZ", "WITZ")) {
            doubleMetaphoneResult.append("TS", "FX");
            n += 4;
        }
        else {
            ++n;
        }
        return n;
    }
    
    private int handleX(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n == 0) {
            doubleMetaphoneResult.append('S');
            ++n;
        }
        else {
            if (n != s.length() - 1 || (!contains(s, n - 3, 3, "IAU", "EAU") && !contains(s, n - 2, 2, "AU", "OU"))) {
                doubleMetaphoneResult.append("KS");
            }
            n = (contains(s, n + 1, 1, "C", "X") ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private int handleZ(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n, final boolean b) {
        if (this.charAt(s, n + 1) == 'H') {
            doubleMetaphoneResult.append('J');
            n += 2;
        }
        else {
            if (contains(s, n + 1, 2, "ZO", "ZI", "ZA") || (b && n > 0 && this.charAt(s, n - 1) != 'T')) {
                doubleMetaphoneResult.append("S", "TS");
            }
            else {
                doubleMetaphoneResult.append('S');
            }
            n = ((this.charAt(s, n + 1) == 'Z') ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private boolean conditionC0(final String s, final int n) {
        if (contains(s, n, 4, "CHIA")) {
            return true;
        }
        if (n <= 1) {
            return false;
        }
        if (this.isVowel(this.charAt(s, n - 2))) {
            return false;
        }
        if (!contains(s, n - 1, 3, "ACH")) {
            return false;
        }
        final char char1 = this.charAt(s, n + 2);
        return (char1 != 'I' && char1 != 'E') || contains(s, n - 2, 6, "BACHER", "MACHER");
    }
    
    private boolean conditionCH0(final String s, final int n) {
        return n == 0 && (contains(s, n + 1, 5, "HARAC", "HARIS") || contains(s, n + 1, 3, "HOR", "HYM", "HIA", "HEM")) && !contains(s, 0, 5, "CHORE");
    }
    
    private boolean conditionCH1(final String s, final int n) {
        return contains(s, 0, 4, "VAN ", "VON ") || contains(s, 0, 3, "SCH") || contains(s, n - 2, 6, "ORCHES", "ARCHIT", "ORCHID") || contains(s, n + 2, 1, "T", "S") || ((contains(s, n - 1, 1, "A", "O", "U", "E") || n == 0) && (contains(s, n + 2, 1, DoubleMetaphone.L_R_N_M_B_H_F_V_W_SPACE) || n + 1 == s.length() - 1));
    }
    
    private boolean conditionL0(final String s, final int n) {
        return (n == s.length() - 3 && contains(s, n - 1, 4, "ILLO", "ILLA", "ALLE")) || ((contains(s, s.length() - 2, 2, "AS", "OS") || contains(s, s.length() - 1, 1, "A", "O")) && contains(s, n - 1, 4, "ALLE"));
    }
    
    private boolean conditionM0(final String s, final int n) {
        return this.charAt(s, n + 1) == 'M' || (contains(s, n - 1, 3, "UMB") && (n + 1 == s.length() - 1 || contains(s, n + 2, 2, "ER")));
    }
    
    private boolean isSlavoGermanic(final String s) {
        return s.indexOf(87) > -1 || s.indexOf(75) > -1 || s.indexOf("CZ") > -1 || s.indexOf("WITZ") > -1;
    }
    
    private boolean isVowel(final char c) {
        return "AEIOUY".indexOf(c) != -1;
    }
    
    private boolean isSilentStart(final String s) {
        final String[] silent_START = DoubleMetaphone.SILENT_START;
        while (0 < silent_START.length && !s.startsWith(silent_START[0])) {
            int n = 0;
            ++n;
        }
        return true;
    }
    
    private String cleanInput(String trim) {
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        if (trim.length() == 0) {
            return null;
        }
        return trim.toUpperCase(Locale.ENGLISH);
    }
    
    protected char charAt(final String s, final int n) {
        if (n < 0 || n >= s.length()) {
            return '\0';
        }
        return s.charAt(n);
    }
    
    protected static boolean contains(final String s, final int n, final int n2, final String... array) {
        if (n >= 0 && n + n2 <= s.length()) {
            final String substring = s.substring(n, n + n2);
            while (0 < array.length) {
                if (substring.equals(array[0])) {
                    break;
                }
                int n3 = 0;
                ++n3;
            }
        }
        return true;
    }
    
    static {
        DoubleMetaphone.SILENT_START = new String[] { "GN", "KN", "PN", "WR", "PS" };
        L_R_N_M_B_H_F_V_W_SPACE = new String[] { "L", "R", "N", "M", "B", "H", "F", "V", "W", " " };
        ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = new String[] { "ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER" };
        L_T_K_S_N_M_B_Z = new String[] { "L", "T", "K", "S", "N", "M", "B", "Z" };
    }
    
    public class DoubleMetaphoneResult
    {
        private final StringBuilder primary;
        private final StringBuilder alternate;
        private final int maxLength;
        final DoubleMetaphone this$0;
        
        public DoubleMetaphoneResult(final DoubleMetaphone this$0, final int maxLength) {
            this.this$0 = this$0;
            this.primary = new StringBuilder(this.this$0.getMaxCodeLen());
            this.alternate = new StringBuilder(this.this$0.getMaxCodeLen());
            this.maxLength = maxLength;
        }
        
        public void append(final char c) {
            this.appendPrimary(c);
            this.appendAlternate(c);
        }
        
        public void append(final char c, final char c2) {
            this.appendPrimary(c);
            this.appendAlternate(c2);
        }
        
        public void appendPrimary(final char c) {
            if (this.primary.length() < this.maxLength) {
                this.primary.append(c);
            }
        }
        
        public void appendAlternate(final char c) {
            if (this.alternate.length() < this.maxLength) {
                this.alternate.append(c);
            }
        }
        
        public void append(final String s) {
            this.appendPrimary(s);
            this.appendAlternate(s);
        }
        
        public void append(final String s, final String s2) {
            this.appendPrimary(s);
            this.appendAlternate(s2);
        }
        
        public void appendPrimary(final String s) {
            final int n = this.maxLength - this.primary.length();
            if (s.length() <= n) {
                this.primary.append(s);
            }
            else {
                this.primary.append(s.substring(0, n));
            }
        }
        
        public void appendAlternate(final String s) {
            final int n = this.maxLength - this.alternate.length();
            if (s.length() <= n) {
                this.alternate.append(s);
            }
            else {
                this.alternate.append(s.substring(0, n));
            }
        }
        
        public String getPrimary() {
            return this.primary.toString();
        }
        
        public String getAlternate() {
            return this.alternate.toString();
        }
        
        public boolean isComplete() {
            return this.primary.length() >= this.maxLength && this.alternate.length() >= this.maxLength;
        }
    }
}
