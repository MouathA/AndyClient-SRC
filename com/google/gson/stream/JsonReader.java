package com.google.gson.stream;

import java.io.*;
import com.google.gson.internal.*;
import com.google.gson.internal.bind.*;

public class JsonReader implements Closeable
{
    private static final char[] NON_EXECUTE_PREFIX;
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private static final int PEEKED_LONG = 15;
    private static final int PEEKED_NUMBER = 16;
    private static final int PEEKED_EOF = 17;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private final Reader in;
    private boolean lenient;
    private final char[] buffer;
    private int pos;
    private int limit;
    private int lineNumber;
    private int lineStart;
    private int peeked;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int[] stack;
    private int stackSize;
    
    public JsonReader(final Reader in) {
        this.lenient = false;
        this.buffer = new char[1024];
        this.pos = 0;
        this.limit = 0;
        this.lineNumber = 0;
        this.lineStart = 0;
        this.peeked = 0;
        this.stack = new int[32];
        this.stackSize = 0;
        this.stack[this.stackSize++] = 6;
        if (in == null) {
            throw new NullPointerException("in == null");
        }
        this.in = in;
    }
    
    public final void setLenient(final boolean lenient) {
        this.lenient = lenient;
    }
    
    public final boolean isLenient() {
        return this.lenient;
    }
    
    public void beginArray() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 3) {
            this.push(1);
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_ARRAY but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    public void endArray() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 4) {
            --this.stackSize;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    public void beginObject() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 1) {
            this.push(3);
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_OBJECT but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    public void endObject() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 2) {
            --this.stackSize;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    public boolean hasNext() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        return n != 2 && n != 4;
    }
    
    public JsonToken peek() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        switch (n) {
            case 1: {
                return JsonToken.BEGIN_OBJECT;
            }
            case 2: {
                return JsonToken.END_OBJECT;
            }
            case 3: {
                return JsonToken.BEGIN_ARRAY;
            }
            case 4: {
                return JsonToken.END_ARRAY;
            }
            case 12:
            case 13:
            case 14: {
                return JsonToken.NAME;
            }
            case 5:
            case 6: {
                return JsonToken.BOOLEAN;
            }
            case 7: {
                return JsonToken.NULL;
            }
            case 8:
            case 9:
            case 10:
            case 11: {
                return JsonToken.STRING;
            }
            case 15:
            case 16: {
                return JsonToken.NUMBER;
            }
            case 17: {
                return JsonToken.END_DOCUMENT;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    private int doPeek() throws IOException {
        final int n = this.stack[this.stackSize - 1];
        if (n == 1) {
            this.stack[this.stackSize - 1] = 2;
        }
        else if (n == 2) {
            switch (this.nextNonWhitespace(true)) {
                case 93: {
                    return this.peeked = 4;
                }
                case 59: {
                    this.checkLenient();
                }
                case 44: {
                    break;
                }
                default: {
                    throw this.syntaxError("Unterminated array");
                }
            }
        }
        else if (n == 3 || n == 5) {
            this.stack[this.stackSize - 1] = 4;
            if (n == 5) {
                switch (this.nextNonWhitespace(true)) {
                    case 125: {
                        return this.peeked = 2;
                    }
                    case 59: {
                        this.checkLenient();
                    }
                    case 44: {
                        break;
                    }
                    default: {
                        throw this.syntaxError("Unterminated object");
                    }
                }
            }
            final int nextNonWhitespace = this.nextNonWhitespace(true);
            switch (nextNonWhitespace) {
                case 34: {
                    return this.peeked = 13;
                }
                case 39: {
                    this.checkLenient();
                    return this.peeked = 12;
                }
                case 125: {
                    if (n != 5) {
                        return this.peeked = 2;
                    }
                    throw this.syntaxError("Expected name");
                }
                default: {
                    this.checkLenient();
                    --this.pos;
                    if (this.isLiteral((char)nextNonWhitespace)) {
                        return this.peeked = 14;
                    }
                    throw this.syntaxError("Expected name");
                }
            }
        }
        else if (n == 4) {
            this.stack[this.stackSize - 1] = 5;
            switch (this.nextNonWhitespace(true)) {
                case 58: {
                    break;
                }
                case 61: {
                    this.checkLenient();
                    if ((this.pos < this.limit || this != 1) && this.buffer[this.pos] == '>') {
                        ++this.pos;
                        break;
                    }
                    break;
                }
                default: {
                    throw this.syntaxError("Expected ':'");
                }
            }
        }
        else if (n == 6) {
            if (this.lenient) {
                this.consumeNonExecutePrefix();
            }
            this.stack[this.stackSize - 1] = 7;
        }
        else if (n == 7) {
            if (this.nextNonWhitespace(false) == -1) {
                return this.peeked = 17;
            }
            this.checkLenient();
            --this.pos;
        }
        else if (n == 8) {
            throw new IllegalStateException("JsonReader is closed");
        }
        switch (this.nextNonWhitespace(true)) {
            case 93: {
                if (n == 1) {
                    return this.peeked = 4;
                }
            }
            case 44:
            case 59: {
                if (n == 1 || n == 2) {
                    this.checkLenient();
                    --this.pos;
                    return this.peeked = 7;
                }
                throw this.syntaxError("Unexpected value");
            }
            case 39: {
                this.checkLenient();
                return this.peeked = 8;
            }
            case 34: {
                if (this.stackSize == 1) {
                    this.checkLenient();
                }
                return this.peeked = 9;
            }
            case 91: {
                return this.peeked = 3;
            }
            case 123: {
                return this.peeked = 1;
            }
            default: {
                --this.pos;
                if (this.stackSize == 1) {
                    this.checkLenient();
                }
                final int peekKeyword = this.peekKeyword();
                if (peekKeyword != 0) {
                    return peekKeyword;
                }
                final int peekNumber = this.peekNumber();
                if (peekNumber != 0) {
                    return peekNumber;
                }
                if (!this.isLiteral(this.buffer[this.pos])) {
                    throw this.syntaxError("Expected value");
                }
                this.checkLenient();
                return this.peeked = 10;
            }
        }
    }
    
    private int peekKeyword() throws IOException {
        final char c = this.buffer[this.pos];
        String s;
        String s2;
        if (c == 't' || c == 'T') {
            s = "true";
            s2 = "TRUE";
        }
        else if (c == 'f' || c == 'F') {
            s = "false";
            s2 = "FALSE";
        }
        else {
            if (c != 'n' && c != 'N') {
                return 0;
            }
            s = "null";
            s2 = "NULL";
        }
        final int length = s.length();
        while (1 < length) {
            if (this.pos + 1 >= this.limit && this != 2) {
                return 0;
            }
            final char c2 = this.buffer[this.pos + 1];
            if (c2 != s.charAt(1) && c2 != s2.charAt(1)) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        if ((this.pos + length < this.limit || this != length + 1) && this.isLiteral(this.buffer[this.pos + length])) {
            return 0;
        }
        this.pos += length;
        return this.peeked = 7;
    }
    
    private int peekNumber() throws IOException {
        final char[] buffer = this.buffer;
        int n = this.pos;
        int n2 = this.limit;
    Label_0277:
        while (true) {
            if (n + 0 == n2) {
                if (0 == buffer.length) {
                    return 0;
                }
                if (this != 1) {
                    break;
                }
                n = this.pos;
                n2 = this.limit;
            }
            final char c = buffer[n + 0];
            switch (c) {
                case 45: {
                    return 0;
                }
                case 43: {
                    return 0;
                }
                case 69:
                case 101: {
                    return 0;
                }
                case 46: {
                    return 0;
                }
                default: {
                    if (c >= '0' && c <= '9') {
                        int n3 = 0;
                        ++n3;
                        continue;
                    }
                    if (!this.isLiteral(c)) {
                        break Label_0277;
                    }
                    return 0;
                }
            }
        }
        this.peekedNumberLength = 0;
        return this.peeked = 16;
    }
    
    private boolean isLiteral(final char c) throws IOException {
        switch (c) {
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\': {
                this.checkLenient();
            }
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}': {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public String nextName() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        String s;
        if (n == 14) {
            s = this.nextUnquotedValue();
        }
        else if (n == 12) {
            s = this.nextQuotedValue('\'');
        }
        else {
            if (n != 13) {
                throw new IllegalStateException("Expected a name but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            s = this.nextQuotedValue('\"');
        }
        this.peeked = 0;
        return s;
    }
    
    public String nextString() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        String s;
        if (n == 10) {
            s = this.nextUnquotedValue();
        }
        else if (n == 8) {
            s = this.nextQuotedValue('\'');
        }
        else if (n == 9) {
            s = this.nextQuotedValue('\"');
        }
        else if (n == 11) {
            s = this.peekedString;
            this.peekedString = null;
        }
        else if (n == 15) {
            s = Long.toString(this.peekedLong);
        }
        else {
            if (n != 16) {
                throw new IllegalStateException("Expected a string but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            s = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
        return s;
    }
    
    public boolean nextBoolean() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 5) {
            this.peeked = 0;
            return true;
        }
        if (n == 6) {
            this.peeked = 0;
            return false;
        }
        throw new IllegalStateException("Expected a boolean but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    public void nextNull() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 7) {
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected null but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    public double nextDouble() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 15) {
            this.peeked = 0;
            return (double)this.peekedLong;
        }
        if (n == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        else if (n == 8 || n == 9) {
            this.peekedString = this.nextQuotedValue((char)((n == 8) ? 39 : 34));
        }
        else if (n == 10) {
            this.peekedString = this.nextUnquotedValue();
        }
        else if (n != 11) {
            throw new IllegalStateException("Expected a double but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.peeked = 11;
        final double double1 = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(double1) || Double.isInfinite(double1))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + double1 + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.peekedString = null;
        this.peeked = 0;
        return double1;
    }
    
    public long nextLong() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 15) {
            this.peeked = 0;
            return this.peekedLong;
        }
        if (n == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
            this.peeked = 11;
            final double double1 = Double.parseDouble(this.peekedString);
            final long n2 = (long)double1;
            if (n2 != double1) {
                throw new NumberFormatException("Expected a long but was " + this.peekedString + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            this.peekedString = null;
            this.peeked = 0;
            return n2;
        }
        else {
            if (n == 8 || n == 9) {
                this.peekedString = this.nextQuotedValue((char)((n == 8) ? 39 : 34));
                final long long1 = Long.parseLong(this.peekedString);
                this.peeked = 0;
                return long1;
            }
            throw new IllegalStateException("Expected a long but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
    }
    
    private String nextQuotedValue(final char c) throws IOException {
        final char[] buffer = this.buffer;
        final StringBuilder sb = new StringBuilder();
        while (true) {
            int i = this.pos;
            int n = this.limit;
            int n2 = i;
            while (i < n) {
                final char c2 = buffer[i++];
                if (c2 == c) {
                    sb.append(buffer, n2, (this.pos = i) - n2 - 1);
                    return sb.toString();
                }
                if (c2 == '\\') {
                    sb.append(buffer, n2, (this.pos = i) - n2 - 1);
                    sb.append(this.readEscapeCharacter());
                    i = this.pos;
                    n = this.limit;
                    n2 = i;
                }
                else {
                    if (c2 != '\n') {
                        continue;
                    }
                    ++this.lineNumber;
                    this.lineStart = i;
                }
            }
            sb.append(buffer, n2, i - n2);
            this.pos = i;
            if (this != 1) {
                throw this.syntaxError("Unterminated string");
            }
        }
    }
    
    private String nextUnquotedValue() throws IOException {
        StringBuilder sb = null;
        Label_0168: {
        Label_0164:
            while (true) {
                if (this.pos + 0 < this.limit) {
                    switch (this.buffer[this.pos + 0]) {
                        case '#':
                        case '/':
                        case ';':
                        case '=':
                        case '\\': {
                            break Label_0164;
                        }
                        case '\t':
                        case '\n':
                        case '\f':
                        case '\r':
                        case ' ':
                        case ',':
                        case ':':
                        case '[':
                        case ']':
                        case '{':
                        case '}': {
                            break Label_0168;
                        }
                        default: {
                            int n = 0;
                            ++n;
                            continue;
                        }
                    }
                }
                else if (0 < this.buffer.length) {
                    if (this != 1) {
                        continue;
                    }
                    break Label_0168;
                }
                else {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(this.buffer, this.pos, 0);
                    this.pos += 0;
                    if (this != 1) {
                        break Label_0168;
                    }
                    continue;
                }
            }
            this.checkLenient();
        }
        String string;
        if (sb == null) {
            string = new String(this.buffer, this.pos, 0);
        }
        else {
            sb.append(this.buffer, this.pos, 0);
            string = sb.toString();
        }
        this.pos += 0;
        return string;
    }
    
    private void skipQuotedValue(final char c) throws IOException {
        final char[] buffer = this.buffer;
        do {
            int i = this.pos;
            int n = this.limit;
            while (i < n) {
                final char c2 = buffer[i++];
                if (c2 == c) {
                    this.pos = i;
                    return;
                }
                if (c2 == '\\') {
                    this.pos = i;
                    this.readEscapeCharacter();
                    i = this.pos;
                    n = this.limit;
                }
                else {
                    if (c2 != '\n') {
                        continue;
                    }
                    ++this.lineNumber;
                    this.lineStart = i;
                }
            }
            this.pos = i;
        } while (this == 1);
        throw this.syntaxError("Unterminated string");
    }
    
    private void skipUnquotedValue() throws IOException {
    Label_0168:
        while (true) {
            if (this.pos + 0 < this.limit) {
                switch (this.buffer[this.pos + 0]) {
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\': {
                        this.checkLenient();
                    }
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}': {
                        break Label_0168;
                    }
                    default: {
                        int n = 0;
                        ++n;
                        continue;
                    }
                }
            }
            else {
                this.pos += 0;
                if (this != 1) {
                    return;
                }
                continue;
            }
        }
        this.pos += 0;
    }
    
    public int nextInt() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 15) {
            final int n2 = (int)this.peekedLong;
            if (this.peekedLong != n2) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            this.peeked = 0;
            return n2;
        }
        else if (n == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
            this.peeked = 11;
            final double double1 = Double.parseDouble(this.peekedString);
            final int n3 = (int)double1;
            if (n3 != double1) {
                throw new NumberFormatException("Expected an int but was " + this.peekedString + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
            this.peekedString = null;
            this.peeked = 0;
            return n3;
        }
        else {
            if (n == 8 || n == 9) {
                this.peekedString = this.nextQuotedValue((char)((n == 8) ? 39 : 34));
                final int int1 = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                return int1;
            }
            throw new IllegalStateException("Expected an int but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
    }
    
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }
    
    public void skipValue() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 3) {
            this.push(1);
            int n2 = 0;
            ++n2;
        }
        else if (n == 1) {
            this.push(3);
            int n2 = 0;
            ++n2;
        }
        else if (n == 4) {
            --this.stackSize;
            int n2 = 0;
            --n2;
        }
        else if (n == 2) {
            --this.stackSize;
            int n2 = 0;
            --n2;
        }
        else if (n == 14 || n == 10) {
            this.skipUnquotedValue();
        }
        else if (n == 8 || n == 12) {
            this.skipQuotedValue('\'');
        }
        else if (n == 9 || n == 13) {
            this.skipQuotedValue('\"');
        }
        else if (n == 16) {
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
    }
    
    private void push(final int n) {
        if (this.stackSize == this.stack.length) {
            final int[] stack = new int[this.stackSize * 2];
            System.arraycopy(this.stack, 0, stack, 0, this.stackSize);
            this.stack = stack;
        }
        this.stack[this.stackSize++] = n;
    }
    
    private int getLineNumber() {
        return this.lineNumber + 1;
    }
    
    private int getColumnNumber() {
        return this.pos - this.lineStart + 1;
    }
    
    private int nextNonWhitespace(final boolean b) throws IOException {
        final char[] buffer = this.buffer;
        int pos = this.pos;
        int n = this.limit;
        while (true) {
            if (pos == n) {
                this.pos = pos;
                if (this != 1) {
                    if (b) {
                        throw new EOFException("End of input at line " + this.getLineNumber() + " column " + this.getColumnNumber());
                    }
                    return -1;
                }
                else {
                    pos = this.pos;
                    n = this.limit;
                }
            }
            final char c = buffer[pos++];
            if (c == '\n') {
                ++this.lineNumber;
                this.lineStart = pos;
            }
            else {
                if (c == ' ' || c == '\r') {
                    continue;
                }
                if (c == '\t') {
                    continue;
                }
                if (c == '/') {
                    if ((this.pos = pos) == n) {
                        --this.pos;
                        final boolean fillBuffer = this.fillBuffer(2);
                        ++this.pos;
                        if (!fillBuffer) {
                            return c;
                        }
                    }
                    this.checkLenient();
                    switch (buffer[this.pos]) {
                        case '*': {
                            ++this.pos;
                            if (this > "*/") {
                                throw this.syntaxError("Unterminated comment");
                            }
                            pos = this.pos + 2;
                            n = this.limit;
                            continue;
                        }
                        case '/': {
                            ++this.pos;
                            this.skipToEndOfLine();
                            pos = this.pos;
                            n = this.limit;
                            continue;
                        }
                        default: {
                            return c;
                        }
                    }
                }
                else {
                    if (c != '#') {
                        this.pos = pos;
                        return c;
                    }
                    this.pos = pos;
                    this.checkLenient();
                    this.skipToEndOfLine();
                    pos = this.pos;
                    n = this.limit;
                }
            }
        }
    }
    
    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }
    
    private void skipToEndOfLine() throws IOException {
        while (this.pos < this.limit || this != 1) {
            final char c = this.buffer[this.pos++];
            if (c == '\n') {
                ++this.lineNumber;
                this.lineStart = this.pos;
                break;
            }
            if (c == '\r') {
                break;
            }
        }
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber();
    }
    
    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && this != 1) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        final char c = this.buffer[this.pos++];
        switch (c) {
            case 117: {
                if (this.pos + 4 > this.limit && this != 4) {
                    throw this.syntaxError("Unterminated escape sequence");
                }
                for (int i = this.pos; i < i + 4; ++i) {
                    final char c2 = this.buffer[i];
                    final char c3 = 0;
                    if (c2 >= '0' && c2 <= '9') {
                        final char c4 = (char)(0 + (c2 - '0'));
                    }
                    else if (c2 >= 'a' && c2 <= 'f') {
                        final char c5 = (char)(0 + (c2 - 'a' + 10));
                    }
                    else {
                        if (c2 < 'A' || c2 > 'F') {
                            throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                        }
                        final char c6 = (char)(0 + (c2 - 'A' + 10));
                    }
                }
                this.pos += 4;
                return '\0';
            }
            case 116: {
                return '\t';
            }
            case 98: {
                return '\b';
            }
            case 110: {
                return '\n';
            }
            case 114: {
                return '\r';
            }
            case 102: {
                return '\f';
            }
            case 10: {
                ++this.lineNumber;
                this.lineStart = this.pos;
                break;
            }
        }
        return c;
    }
    
    private IOException syntaxError(final String s) throws IOException {
        throw new MalformedJsonException(s + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace(true);
        --this.pos;
        if (this.pos + JsonReader.NON_EXECUTE_PREFIX.length > this.limit && this != JsonReader.NON_EXECUTE_PREFIX.length) {
            return;
        }
        while (0 < JsonReader.NON_EXECUTE_PREFIX.length) {
            if (this.buffer[this.pos + 0] != JsonReader.NON_EXECUTE_PREFIX[0]) {
                return;
            }
            int n = 0;
            ++n;
        }
        this.pos += JsonReader.NON_EXECUTE_PREFIX.length;
    }
    
    static int access$000(final JsonReader jsonReader) {
        return jsonReader.peeked;
    }
    
    static int access$100(final JsonReader jsonReader) throws IOException {
        return jsonReader.doPeek();
    }
    
    static int access$002(final JsonReader jsonReader, final int peeked) {
        return jsonReader.peeked = peeked;
    }
    
    static int access$200(final JsonReader jsonReader) {
        return jsonReader.getLineNumber();
    }
    
    static int access$300(final JsonReader jsonReader) {
        return jsonReader.getColumnNumber();
    }
    
    static {
        NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() {
            @Override
            public void promoteNameToValue(final JsonReader jsonReader) throws IOException {
                if (jsonReader instanceof JsonTreeReader) {
                    ((JsonTreeReader)jsonReader).promoteNameToValue();
                    return;
                }
                int n = JsonReader.access$000(jsonReader);
                if (n == 0) {
                    n = JsonReader.access$100(jsonReader);
                }
                if (n == 13) {
                    JsonReader.access$002(jsonReader, 9);
                }
                else if (n == 12) {
                    JsonReader.access$002(jsonReader, 8);
                }
                else {
                    if (n != 14) {
                        throw new IllegalStateException("Expected a name but was " + jsonReader.peek() + " " + " at line " + JsonReader.access$200(jsonReader) + " column " + JsonReader.access$300(jsonReader));
                    }
                    JsonReader.access$002(jsonReader, 10);
                }
            }
        };
    }
}
