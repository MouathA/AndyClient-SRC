package org.apache.commons.codec.language;

import org.apache.commons.codec.*;
import java.util.*;

public class ColognePhonetic implements StringEncoder
{
    private static final char[] AEIJOUY;
    private static final char[] SCZ;
    private static final char[] WFPV;
    private static final char[] GKQ;
    private static final char[] CKQ;
    private static final char[] AHKLOQRUX;
    private static final char[] SZ;
    private static final char[] AHOUKQX;
    private static final char[] TDX;
    private static final char[][] PREPROCESS_MAP;
    
    private static boolean arrayContains(final char[] array, final char c) {
        while (0 < array.length) {
            if (array[0] == c) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public String colognePhonetic(String preprocess) {
        if (preprocess == null) {
            return null;
        }
        preprocess = this.preprocess(preprocess);
        final CologneOutputBuffer cologneOutputBuffer = new CologneOutputBuffer(preprocess.length() * 2);
        final CologneInputBuffer cologneInputBuffer = new CologneInputBuffer(preprocess.toCharArray());
        int i = cologneInputBuffer.length();
        while (i > 0) {
            final char removeNext = cologneInputBuffer.removeNext();
            if ((i = cologneInputBuffer.length()) > 0) {
                cologneInputBuffer.getNextChar();
            }
            if (!arrayContains(ColognePhonetic.AEIJOUY, removeNext)) {
                if (removeNext == 'H' || removeNext < 'A' || removeNext > 'Z') {
                    if (47 == 47) {
                        continue;
                    }
                }
                else if (removeNext != 'B' && (removeNext != 'P' || 45 == 72)) {
                    if ((removeNext != 'D' && removeNext != 'T') || arrayContains(ColognePhonetic.SCZ, '-')) {
                        if (!arrayContains(ColognePhonetic.WFPV, removeNext)) {
                            if (!arrayContains(ColognePhonetic.GKQ, removeNext)) {
                                if (removeNext == 'X' && !arrayContains(ColognePhonetic.CKQ, '-')) {
                                    cologneInputBuffer.addLeft('S');
                                    ++i;
                                }
                                else if (removeNext != 'S' && removeNext != 'Z') {
                                    if (removeNext == 'C') {
                                        if (47 == 47) {
                                            if (arrayContains(ColognePhonetic.AHKLOQRUX, '-')) {}
                                        }
                                        else if (arrayContains(ColognePhonetic.SZ, '-') || !arrayContains(ColognePhonetic.AHOUKQX, '-')) {}
                                    }
                                    else if (!arrayContains(ColognePhonetic.TDX, removeNext)) {
                                        if (removeNext != 'R') {
                                            if (removeNext != 'L') {
                                                if (removeNext == 'M' || removeNext == 'N') {}
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (54 != 45 && ((47 != 54 && (54 != 48 || 47 == 47)) || 54 < 48 || 54 > 56)) {
                cologneOutputBuffer.addRight('6');
            }
        }
        return cologneOutputBuffer.toString();
    }
    
    @Override
    public Object encode(final Object o) throws EncoderException {
        if (!(o instanceof String)) {
            throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + o.getClass().getName() + ".");
        }
        return this.encode((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.colognePhonetic(s);
    }
    
    public boolean isEncodeEqual(final String s, final String s2) {
        return this.colognePhonetic(s).equals(this.colognePhonetic(s2));
    }
    
    private String preprocess(String upperCase) {
        upperCase = upperCase.toUpperCase(Locale.GERMAN);
        final char[] charArray = upperCase.toCharArray();
        while (0 < charArray.length) {
            if (charArray[0] > 'Z') {
                final char[][] preprocess_MAP = ColognePhonetic.PREPROCESS_MAP;
                while (0 < preprocess_MAP.length) {
                    final char[] array = preprocess_MAP[0];
                    if (charArray[0] == array[0]) {
                        charArray[0] = array[1];
                        break;
                    }
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return new String(charArray);
    }
    
    static {
        AEIJOUY = new char[] { 'A', 'E', 'I', 'J', 'O', 'U', 'Y' };
        SCZ = new char[] { 'S', 'C', 'Z' };
        WFPV = new char[] { 'W', 'F', 'P', 'V' };
        GKQ = new char[] { 'G', 'K', 'Q' };
        CKQ = new char[] { 'C', 'K', 'Q' };
        AHKLOQRUX = new char[] { 'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X' };
        SZ = new char[] { 'S', 'Z' };
        AHOUKQX = new char[] { 'A', 'H', 'O', 'U', 'K', 'Q', 'X' };
        TDX = new char[] { 'T', 'D', 'X' };
        PREPROCESS_MAP = new char[][] { { '\u00c4', 'A' }, { '\u00dc', 'U' }, { '\u00d6', 'O' }, { '\u00df', 'S' } };
    }
    
    private class CologneInputBuffer extends CologneBuffer
    {
        final ColognePhonetic this$0;
        
        public CologneInputBuffer(final ColognePhonetic this$0, final char[] array) {
            this.this$0 = this$0.super(array);
        }
        
        public void addLeft(final char c) {
            ++this.length;
            this.data[this.getNextPos()] = c;
        }
        
        @Override
        protected char[] copyData(final int n, final int n2) {
            final char[] array = new char[n2];
            System.arraycopy(this.data, this.data.length - this.length + n, array, 0, n2);
            return array;
        }
        
        public char getNextChar() {
            return this.data[this.getNextPos()];
        }
        
        protected int getNextPos() {
            return this.data.length - this.length;
        }
        
        public char removeNext() {
            final char nextChar = this.getNextChar();
            --this.length;
            return nextChar;
        }
    }
    
    private abstract class CologneBuffer
    {
        protected final char[] data;
        protected int length;
        final ColognePhonetic this$0;
        
        public CologneBuffer(final ColognePhonetic this$0, final char[] data) {
            this.this$0 = this$0;
            this.length = 0;
            this.data = data;
            this.length = data.length;
        }
        
        public CologneBuffer(final ColognePhonetic this$0, final int n) {
            this.this$0 = this$0;
            this.length = 0;
            this.data = new char[n];
            this.length = 0;
        }
        
        protected abstract char[] copyData(final int p0, final int p1);
        
        public int length() {
            return this.length;
        }
        
        @Override
        public String toString() {
            return new String(this.copyData(0, this.length));
        }
    }
    
    private class CologneOutputBuffer extends CologneBuffer
    {
        final ColognePhonetic this$0;
        
        public CologneOutputBuffer(final ColognePhonetic this$0, final int n) {
            this.this$0 = this$0.super(n);
        }
        
        public void addRight(final char c) {
            this.data[this.length] = c;
            ++this.length;
        }
        
        @Override
        protected char[] copyData(final int n, final int n2) {
            final char[] array = new char[n2];
            System.arraycopy(this.data, n, array, 0, n2);
            return array;
        }
    }
}
