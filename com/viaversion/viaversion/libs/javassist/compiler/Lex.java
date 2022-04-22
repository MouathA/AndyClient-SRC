package com.viaversion.viaversion.libs.javassist.compiler;

public class Lex implements TokenId
{
    private int lastChar;
    private StringBuffer textBuffer;
    private Token currentToken;
    private Token lookAheadTokens;
    private String input;
    private int position;
    private int maxlen;
    private int lineNumber;
    private static final int[] equalOps;
    private static final KeywordTable ktable;
    
    public Lex(final String input) {
        this.lastChar = -1;
        this.textBuffer = new StringBuffer();
        this.currentToken = new Token();
        this.lookAheadTokens = null;
        this.input = input;
        this.position = 0;
        this.maxlen = input.length();
        this.lineNumber = 0;
    }
    
    public int get() {
        if (this.lookAheadTokens == null) {
            return this.get(this.currentToken);
        }
        final Token token = this.currentToken = this.lookAheadTokens;
        this.lookAheadTokens = this.lookAheadTokens.next;
        return token.tokenId;
    }
    
    public int lookAhead() {
        return this.lookAhead(0);
    }
    
    public int lookAhead(int n) {
        Token currentToken = this.lookAheadTokens;
        if (currentToken == null) {
            currentToken = (this.lookAheadTokens = this.currentToken);
            currentToken.next = null;
            this.get(currentToken);
        }
        while (n-- > 0) {
            if (currentToken.next == null) {
                this.get(currentToken.next = new Token());
            }
            currentToken = currentToken.next;
        }
        this.currentToken = currentToken;
        return currentToken.tokenId;
    }
    
    public String getString() {
        return this.currentToken.textValue;
    }
    
    public long getLong() {
        return this.currentToken.longValue;
    }
    
    public double getDouble() {
        return this.currentToken.doubleValue;
    }
    
    private int get(final Token token) {
        int i;
        do {
            i = this.readLine(token);
        } while (i == 10);
        return token.tokenId = i;
    }
    
    private int readLine(final Token token) {
        final int nextNonWhiteChar = this.getNextNonWhiteChar();
        if (nextNonWhiteChar < 0) {
            return nextNonWhiteChar;
        }
        if (nextNonWhiteChar == 10) {
            ++this.lineNumber;
            return 10;
        }
        if (nextNonWhiteChar == 39) {
            return this.readCharConst(token);
        }
        if (nextNonWhiteChar == 34) {
            return this.readStringL(token);
        }
        if (48 <= nextNonWhiteChar && nextNonWhiteChar <= 57) {
            return this.readNumber(nextNonWhiteChar, token);
        }
        if (nextNonWhiteChar == 46) {
            final int getc = this.getc();
            if (48 <= getc && getc <= 57) {
                final StringBuffer textBuffer = this.textBuffer;
                textBuffer.setLength(0);
                textBuffer.append('.');
                return this.readDouble(textBuffer, getc, token);
            }
            this.ungetc(getc);
            return this.readSeparator(46);
        }
        else {
            if (Character.isJavaIdentifierStart((char)nextNonWhiteChar)) {
                return this.readIdentifier(nextNonWhiteChar, token);
            }
            return this.readSeparator(nextNonWhiteChar);
        }
    }
    
    private int getNextNonWhiteChar() {
        int i;
        do {
            i = this.getc();
            if (i == 47) {
                final int getc = this.getc();
                if (getc == 47) {
                    do {
                        i = this.getc();
                        if (i != 10 && i != 13) {
                            continue;
                        }
                        break;
                    } while (i != -1);
                }
                else if (getc == 42) {
                    while (true) {
                        i = this.getc();
                        if (i == -1) {
                            break;
                        }
                        if (i != 42) {
                            continue;
                        }
                        final int getc2;
                        if ((getc2 = this.getc()) == 47) {
                            i = 32;
                            break;
                        }
                        this.ungetc(getc2);
                    }
                }
                else {
                    this.ungetc(getc);
                    i = 47;
                }
            }
        } while (isBlank(i));
        return i;
    }
    
    private int readCharConst(final Token token) {
        int escapeChar = 0;
        int getc;
        while ((getc = this.getc()) != 39) {
            if (getc == 92) {
                escapeChar = this.readEscapeChar();
            }
            else {
                if (getc < 32) {
                    if (getc == 10) {
                        ++this.lineNumber;
                    }
                    return 500;
                }
                escapeChar = getc;
            }
        }
        token.longValue = escapeChar;
        return 401;
    }
    
    private int readEscapeChar() {
        int getc = this.getc();
        if (getc == 110) {
            getc = 10;
        }
        else if (getc == 116) {
            getc = 9;
        }
        else if (getc == 114) {
            getc = 13;
        }
        else if (getc == 102) {
            getc = 12;
        }
        else if (getc == 10) {
            ++this.lineNumber;
        }
        return getc;
    }
    
    private int readStringL(final Token token) {
        final StringBuffer textBuffer = this.textBuffer;
        textBuffer.setLength(0);
        while (true) {
            int n;
            if ((n = this.getc()) != 34) {
                if (n == 92) {
                    n = this.readEscapeChar();
                }
                else if (n == 10 || n < 0) {
                    ++this.lineNumber;
                    return 500;
                }
                textBuffer.append((char)n);
            }
            else {
                int getc;
                while (true) {
                    getc = this.getc();
                    if (getc == 10) {
                        ++this.lineNumber;
                    }
                    else {
                        if (!isBlank(getc)) {
                            break;
                        }
                        continue;
                    }
                }
                if (getc != 34) {
                    this.ungetc(getc);
                    token.textValue = textBuffer.toString();
                    return 406;
                }
                continue;
            }
        }
    }
    
    private int readNumber(int n, final Token token) {
        long longValue = 0L;
        int n2 = this.getc();
        if (n == 48) {
            if (n2 == 88 || n2 == 120) {
                while (true) {
                    n = this.getc();
                    if (48 <= n && n <= 57) {
                        longValue = longValue * 16L + (n - 48);
                    }
                    else if (65 <= n && n <= 70) {
                        longValue = longValue * 16L + (n - 65 + 10);
                    }
                    else {
                        if (97 > n || n > 102) {
                            break;
                        }
                        longValue = longValue * 16L + (n - 97 + 10);
                    }
                }
                token.longValue = longValue;
                if (n == 76 || n == 108) {
                    return 403;
                }
                this.ungetc(n);
                return 402;
            }
            else if (48 <= n2 && n2 <= 55) {
                long longValue2 = n2 - 48;
                while (true) {
                    n = this.getc();
                    if (48 > n || n > 55) {
                        break;
                    }
                    longValue2 = longValue2 * 8L + (n - 48);
                }
                token.longValue = longValue2;
                if (n == 76 || n == 108) {
                    return 403;
                }
                this.ungetc(n);
                return 402;
            }
        }
        long longValue3 = n - 48;
        while (48 <= n2 && n2 <= 57) {
            longValue3 = longValue3 * 10L + n2 - 48L;
            n2 = this.getc();
        }
        token.longValue = longValue3;
        if (n2 == 70 || n2 == 102) {
            token.doubleValue = (double)longValue3;
            return 404;
        }
        if (n2 == 69 || n2 == 101 || n2 == 68 || n2 == 100 || n2 == 46) {
            final StringBuffer textBuffer = this.textBuffer;
            textBuffer.setLength(0);
            textBuffer.append(longValue3);
            return this.readDouble(textBuffer, n2, token);
        }
        if (n2 == 76 || n2 == 108) {
            return 403;
        }
        this.ungetc(n2);
        return 402;
    }
    
    private int readDouble(final StringBuffer sb, int n, final Token token) {
        if (n != 69 && n != 101 && n != 68 && n != 100) {
            sb.append((char)n);
            while (true) {
                n = this.getc();
                if (48 > n || n > 57) {
                    break;
                }
                sb.append((char)n);
            }
        }
        if (n == 69 || n == 101) {
            sb.append((char)n);
            n = this.getc();
            if (n == 43 || n == 45) {
                sb.append((char)n);
                n = this.getc();
            }
            while (48 <= n && n <= 57) {
                sb.append((char)n);
                n = this.getc();
            }
        }
        try {
            token.doubleValue = Double.parseDouble(sb.toString());
        }
        catch (NumberFormatException ex) {
            return 500;
        }
        if (n == 70 || n == 102) {
            return 404;
        }
        if (n != 68 && n != 100) {
            this.ungetc(n);
        }
        return 405;
    }
    
    private int readSeparator(final int n) {
        int n3;
        if (33 <= n && n <= 63) {
            final int n2 = Lex.equalOps[n - 33];
            if (n2 == 0) {
                return n;
            }
            n3 = this.getc();
            if (n == n3) {
                switch (n) {
                    case 61: {
                        return 358;
                    }
                    case 43: {
                        return 362;
                    }
                    case 45: {
                        return 363;
                    }
                    case 38: {
                        return 369;
                    }
                    case 60: {
                        final int getc = this.getc();
                        if (getc == 61) {
                            return 365;
                        }
                        this.ungetc(getc);
                        return 364;
                    }
                    case 62: {
                        final int getc2 = this.getc();
                        if (getc2 == 61) {
                            return 367;
                        }
                        if (getc2 != 62) {
                            this.ungetc(getc2);
                            return 366;
                        }
                        final int getc3 = this.getc();
                        if (getc3 == 61) {
                            return 371;
                        }
                        this.ungetc(getc3);
                        return 370;
                    }
                }
            }
            else if (n3 == 61) {
                return n2;
            }
        }
        else if (n == 94) {
            n3 = this.getc();
            if (n3 == 61) {
                return 360;
            }
        }
        else {
            if (n != 124) {
                return n;
            }
            n3 = this.getc();
            if (n3 == 61) {
                return 361;
            }
            if (n3 == 124) {
                return 368;
            }
        }
        this.ungetc(n3);
        return n;
    }
    
    private int readIdentifier(int getc, final Token token) {
        final StringBuffer textBuffer = this.textBuffer;
        textBuffer.setLength(0);
        do {
            textBuffer.append((char)getc);
            getc = this.getc();
        } while (Character.isJavaIdentifierPart((char)getc));
        this.ungetc(getc);
        final String string = textBuffer.toString();
        final int lookup = Lex.ktable.lookup(string);
        if (lookup >= 0) {
            return lookup;
        }
        token.textValue = string;
        return 400;
    }
    
    private static boolean isBlank(final int n) {
        return n == 32 || n == 9 || n == 12 || n == 13 || n == 10;
    }
    
    private static boolean isDigit(final int n) {
        return 48 <= n && n <= 57;
    }
    
    private void ungetc(final int lastChar) {
        this.lastChar = lastChar;
    }
    
    public String getTextAround() {
        int n = this.position - 10;
        if (n < 0) {
            n = 0;
        }
        int maxlen = this.position + 10;
        if (maxlen > this.maxlen) {
            maxlen = this.maxlen;
        }
        return this.input.substring(n, maxlen);
    }
    
    private int getc() {
        if (this.lastChar >= 0) {
            final int lastChar = this.lastChar;
            this.lastChar = -1;
            return lastChar;
        }
        if (this.position < this.maxlen) {
            return this.input.charAt(this.position++);
        }
        return -1;
    }
    
    static {
        equalOps = new int[] { 350, 0, 0, 0, 351, 352, 0, 0, 0, 353, 354, 0, 355, 0, 356, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 357, 358, 359, 0 };
        (ktable = new KeywordTable()).append("abstract", 300);
        Lex.ktable.append("boolean", 301);
        Lex.ktable.append("break", 302);
        Lex.ktable.append("byte", 303);
        Lex.ktable.append("case", 304);
        Lex.ktable.append("catch", 305);
        Lex.ktable.append("char", 306);
        Lex.ktable.append("class", 307);
        Lex.ktable.append("const", 308);
        Lex.ktable.append("continue", 309);
        Lex.ktable.append("default", 310);
        Lex.ktable.append("do", 311);
        Lex.ktable.append("double", 312);
        Lex.ktable.append("else", 313);
        Lex.ktable.append("extends", 314);
        Lex.ktable.append("false", 411);
        Lex.ktable.append("final", 315);
        Lex.ktable.append("finally", 316);
        Lex.ktable.append("float", 317);
        Lex.ktable.append("for", 318);
        Lex.ktable.append("goto", 319);
        Lex.ktable.append("if", 320);
        Lex.ktable.append("implements", 321);
        Lex.ktable.append("import", 322);
        Lex.ktable.append("instanceof", 323);
        Lex.ktable.append("int", 324);
        Lex.ktable.append("interface", 325);
        Lex.ktable.append("long", 326);
        Lex.ktable.append("native", 327);
        Lex.ktable.append("new", 328);
        Lex.ktable.append("null", 412);
        Lex.ktable.append("package", 329);
        Lex.ktable.append("private", 330);
        Lex.ktable.append("protected", 331);
        Lex.ktable.append("public", 332);
        Lex.ktable.append("return", 333);
        Lex.ktable.append("short", 334);
        Lex.ktable.append("static", 335);
        Lex.ktable.append("strictfp", 347);
        Lex.ktable.append("super", 336);
        Lex.ktable.append("switch", 337);
        Lex.ktable.append("synchronized", 338);
        Lex.ktable.append("this", 339);
        Lex.ktable.append("throw", 340);
        Lex.ktable.append("throws", 341);
        Lex.ktable.append("transient", 342);
        Lex.ktable.append("true", 410);
        Lex.ktable.append("try", 343);
        Lex.ktable.append("void", 344);
        Lex.ktable.append("volatile", 345);
        Lex.ktable.append("while", 346);
    }
}
