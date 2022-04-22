package com.viaversion.viaversion.libs.gson.stream;

import java.util.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import com.viaversion.viaversion.libs.gson.internal.bind.*;

public class JsonReader implements Closeable
{
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
    int peeked;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int[] stack;
    private int stackSize;
    private String[] pathNames;
    private int[] pathIndices;
    
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
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
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
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_ARRAY but was " + this.peek() + this.locationString());
    }
    
    public void endArray() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 4) {
            --this.stackSize;
            final int[] pathIndices = this.pathIndices;
            final int n2 = this.stackSize - 1;
            ++pathIndices[n2];
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + this.peek() + this.locationString());
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
        throw new IllegalStateException("Expected BEGIN_OBJECT but was " + this.peek() + this.locationString());
    }
    
    public void endObject() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 2) {
            --this.stackSize;
            this.pathNames[this.stackSize] = null;
            final int[] pathIndices = this.pathIndices;
            final int n2 = this.stackSize - 1;
            ++pathIndices[n2];
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + this.peek() + this.locationString());
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
    
    int doPeek() throws IOException {
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
                    if ((this.pos < this.limit || this.fillBuffer(1)) && this.buffer[this.pos] == '>') {
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
            if (this.pos + 1 >= this.limit && !this.fillBuffer(2)) {
                return 0;
            }
            final char c2 = this.buffer[this.pos + 1];
            if (c2 != s.charAt(1) && c2 != s2.charAt(1)) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        if ((this.pos + length < this.limit || this.fillBuffer(length + 1)) && this.isLiteral(this.buffer[this.pos + length])) {
            return 0;
        }
        this.pos += length;
        return this.peeked = 7;
    }
    
    private int peekNumber() throws IOException {
        final char[] buffer = this.buffer;
        int n = this.pos;
        int n2 = this.limit;
        long n3 = 0L;
    Label_0321:
        while (true) {
            if (n + 0 == n2) {
                if (0 == buffer.length) {
                    return 0;
                }
                if (!this.fillBuffer(1)) {
                    break;
                }
                n = this.pos;
                n2 = this.limit;
            }
            final char c = buffer[n + 0];
            switch (c) {
                case 45: {
                    if (7 == 0) {
                        break;
                    }
                    if (7 == 5) {
                        break;
                    }
                    return 0;
                }
                case 43: {
                    if (7 == 5) {
                        break;
                    }
                    return 0;
                }
                case 69:
                case 101: {
                    if (7 == 2 || 7 == 4) {
                        break;
                    }
                    return 0;
                }
                case 46: {
                    if (7 == 2) {
                        break;
                    }
                    return 0;
                }
                default: {
                    if (c < '0' || c > '9') {
                        if (!this.isLiteral(c)) {
                            break Label_0321;
                        }
                        return 0;
                    }
                    else {
                        if (7 == 1 || 7 == 0) {
                            n3 = -(c - '0');
                            break;
                        }
                        if (7 == 2) {
                            if (n3 == 0L) {
                                return 0;
                            }
                            final long n4 = n3 * 10L - (c - '0');
                            final boolean b = true & (n3 > -922337203685477580L || (n3 == -922337203685477580L && n4 < n3));
                            n3 = n4;
                            break;
                        }
                        else {
                            if (7 == 3) {
                                break;
                            }
                            if (7 == 5 || 7 == 6) {}
                        }
                    }
                    break;
                }
            }
            int n5 = 0;
            ++n5;
        }
        if (7 == 2 && true && (n3 != Long.MIN_VALUE || true) && (n3 != 0L || false == true)) {
            this.peekedLong = (true ? n3 : (-n3));
            this.pos += 0;
            return this.peeked = 15;
        }
        if (7 == 2 || 7 == 4 || 7 == 7) {
            this.peekedNumberLength = 0;
            return this.peeked = 16;
        }
        return 0;
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
                throw new IllegalStateException("Expected a name but was " + this.peek() + this.locationString());
            }
            s = this.nextQuotedValue('\"');
        }
        this.peeked = 0;
        return this.pathNames[this.stackSize - 1] = s;
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
                throw new IllegalStateException("Expected a string but was " + this.peek() + this.locationString());
            }
            s = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
        final int[] pathIndices = this.pathIndices;
        final int n2 = this.stackSize - 1;
        ++pathIndices[n2];
        return s;
    }
    
    public boolean nextBoolean() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 5) {
            this.peeked = 0;
            final int[] pathIndices = this.pathIndices;
            final int n2 = this.stackSize - 1;
            ++pathIndices[n2];
            return true;
        }
        if (n == 6) {
            this.peeked = 0;
            final int[] pathIndices2 = this.pathIndices;
            final int n3 = this.stackSize - 1;
            ++pathIndices2[n3];
            return false;
        }
        throw new IllegalStateException("Expected a boolean but was " + this.peek() + this.locationString());
    }
    
    public void nextNull() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 7) {
            this.peeked = 0;
            final int[] pathIndices = this.pathIndices;
            final int n2 = this.stackSize - 1;
            ++pathIndices[n2];
            return;
        }
        throw new IllegalStateException("Expected null but was " + this.peek() + this.locationString());
    }
    
    public double nextDouble() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 15) {
            this.peeked = 0;
            final int[] pathIndices = this.pathIndices;
            final int n2 = this.stackSize - 1;
            ++pathIndices[n2];
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
            throw new IllegalStateException("Expected a double but was " + this.peek() + this.locationString());
        }
        this.peeked = 11;
        final double double1 = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(double1) || Double.isInfinite(double1))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + double1 + this.locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        final int[] pathIndices2 = this.pathIndices;
        final int n3 = this.stackSize - 1;
        ++pathIndices2[n3];
        return double1;
    }
    
    public long nextLong() throws IOException {
        int n = this.peeked;
        if (n == 0) {
            n = this.doPeek();
        }
        if (n == 15) {
            this.peeked = 0;
            final int[] pathIndices = this.pathIndices;
            final int n2 = this.stackSize - 1;
            ++pathIndices[n2];
            return this.peekedLong;
        }
        if (n == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
            this.peeked = 11;
            final double double1 = Double.parseDouble(this.peekedString);
            final long n3 = (long)double1;
            if (n3 != double1) {
                throw new NumberFormatException("Expected a long but was " + this.peekedString + this.locationString());
            }
            this.peekedString = null;
            this.peeked = 0;
            final int[] pathIndices2 = this.pathIndices;
            final int n4 = this.stackSize - 1;
            ++pathIndices2[n4];
            return n3;
        }
        else {
            if (n == 8 || n == 9 || n == 10) {
                if (n == 10) {
                    this.peekedString = this.nextUnquotedValue();
                }
                else {
                    this.peekedString = this.nextQuotedValue((char)((n == 8) ? 39 : 34));
                }
                final long long1 = Long.parseLong(this.peekedString);
                this.peeked = 0;
                final int[] pathIndices3 = this.pathIndices;
                final int n5 = this.stackSize - 1;
                ++pathIndices3[n5];
                return long1;
            }
            throw new IllegalStateException("Expected a long but was " + this.peek() + this.locationString());
        }
    }
    
    private String nextQuotedValue(final char c) throws IOException {
        final char[] buffer = this.buffer;
        StringBuilder sb = null;
        while (true) {
            int i = this.pos;
            int n = this.limit;
            int n2 = i;
            while (i < n) {
                final char c2 = buffer[i++];
                if (c2 == c) {
                    this.pos = i;
                    final int n3 = i - n2 - 1;
                    if (sb == null) {
                        return new String(buffer, n2, n3);
                    }
                    sb.append(buffer, n2, n3);
                    return sb.toString();
                }
                else if (c2 == '\\') {
                    this.pos = i;
                    final int n4 = i - n2 - 1;
                    if (sb == null) {
                        sb = new StringBuilder(Math.max((n4 + 1) * 2, 16));
                    }
                    sb.append(buffer, n2, n4);
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
            if (sb == null) {
                sb = new StringBuilder(Math.max((i - n2) * 2, 16));
            }
            sb.append(buffer, n2, i - n2);
            this.pos = i;
            if (!this.fillBuffer(1)) {
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
                    if (this.fillBuffer(1)) {
                        continue;
                    }
                    break Label_0168;
                }
                else {
                    if (sb == null) {
                        sb = new StringBuilder(Math.max(0, 16));
                    }
                    sb.append(this.buffer, this.pos, 0);
                    this.pos += 0;
                    if (!this.fillBuffer(1)) {
                        break Label_0168;
                    }
                    continue;
                }
            }
            this.checkLenient();
        }
        final String s = (null == sb) ? new String(this.buffer, this.pos, 0) : sb.append(this.buffer, this.pos, 0).toString();
        this.pos += 0;
        return s;
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
        } while (this.fillBuffer(1));
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
                if (!this.fillBuffer(1)) {
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
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + this.locationString());
            }
            this.peeked = 0;
            final int[] pathIndices = this.pathIndices;
            final int n3 = this.stackSize - 1;
            ++pathIndices[n3];
            return n2;
        }
        else if (n == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
            this.peeked = 11;
            final double double1 = Double.parseDouble(this.peekedString);
            final int n4 = (int)double1;
            if (n4 != double1) {
                throw new NumberFormatException("Expected an int but was " + this.peekedString + this.locationString());
            }
            this.peekedString = null;
            this.peeked = 0;
            final int[] pathIndices2 = this.pathIndices;
            final int n5 = this.stackSize - 1;
            ++pathIndices2[n5];
            return n4;
        }
        else {
            if (n == 8 || n == 9 || n == 10) {
                if (n == 10) {
                    this.peekedString = this.nextUnquotedValue();
                }
                else {
                    this.peekedString = this.nextQuotedValue((char)((n == 8) ? 39 : 34));
                }
                final int int1 = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                final int[] pathIndices3 = this.pathIndices;
                final int n6 = this.stackSize - 1;
                ++pathIndices3[n6];
                return int1;
            }
            throw new IllegalStateException("Expected an int but was " + this.peek() + this.locationString());
        }
    }
    
    @Override
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }
    
    public void skipValue() throws IOException {
        do {
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
        } while (false);
        final int[] pathIndices = this.pathIndices;
        final int n3 = this.stackSize - 1;
        ++pathIndices[n3];
        this.pathNames[this.stackSize - 1] = "null";
    }
    
    private void push(final int n) {
        if (this.stackSize == this.stack.length) {
            final int n2 = this.stackSize * 2;
            this.stack = Arrays.copyOf(this.stack, n2);
            this.pathIndices = Arrays.copyOf(this.pathIndices, n2);
            this.pathNames = Arrays.copyOf(this.pathNames, n2);
        }
        this.stack[this.stackSize++] = n;
    }
    
    private boolean fillBuffer(int n) throws IOException {
        final char[] buffer = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
        }
        else {
            this.limit = 0;
        }
        this.pos = 0;
        int read;
        while ((read = this.in.read(buffer, this.limit, buffer.length - this.limit)) != -1) {
            this.limit += read;
            if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && buffer[0] == '\ufeff') {
                ++this.pos;
                ++this.lineStart;
                ++n;
            }
            if (this.limit >= n) {
                return true;
            }
        }
        return false;
    }
    
    private int nextNonWhitespace(final boolean b) throws IOException {
        final char[] buffer = this.buffer;
        int pos = this.pos;
        int n = this.limit;
        while (true) {
            if (pos == n) {
                this.pos = pos;
                if (!this.fillBuffer(1)) {
                    if (b) {
                        throw new EOFException("End of input" + this.locationString());
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
                            if (!this.skipTo("*/")) {
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
        while (this.pos < this.limit || this.fillBuffer(1)) {
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
    
    private boolean skipTo(final String s) throws IOException {
        final int length = s.length();
        while (this.pos + length <= this.limit || this.fillBuffer(length)) {
            Label_0098: {
                if (this.buffer[this.pos] != '\n') {
                    while (0 < length) {
                        if (this.buffer[this.pos + 0] != s.charAt(0)) {
                            break Label_0098;
                        }
                        int n = 0;
                        ++n;
                    }
                    return true;
                }
                ++this.lineNumber;
                this.lineStart = this.pos + 1;
            }
            ++this.pos;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.locationString();
    }
    
    String locationString() {
        return " at line " + (this.lineNumber + 1) + " column " + (this.pos - this.lineStart + 1) + " path " + this.getPath();
    }
    
    public String getPath() {
        final StringBuilder append = new StringBuilder().append('$');
        while (0 < this.stackSize) {
            switch (this.stack[0]) {
                case 1:
                case 2: {
                    append.append('[').append(this.pathIndices[0]).append(']');
                    break;
                }
                case 3:
                case 4:
                case 5: {
                    append.append('.');
                    if (this.pathNames[0] != null) {
                        append.append(this.pathNames[0]);
                        break;
                    }
                    break;
                }
            }
            int n = 0;
            ++n;
        }
        return append.toString();
    }
    
    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        final char c = this.buffer[this.pos++];
        switch (c) {
            case 117: {
                if (this.pos + 4 > this.limit && !this.fillBuffer(4)) {
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
            }
            case 34:
            case 39:
            case 47:
            case 92: {
                return c;
            }
            default: {
                throw this.syntaxError("Invalid escape sequence");
            }
        }
    }
    
    private IOException syntaxError(final String s) throws IOException {
        throw new MalformedJsonException(s + this.locationString());
    }
    
    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace(true);
        --this.pos;
        final int pos = this.pos;
        if (pos + 5 > this.limit && !this.fillBuffer(5)) {
            return;
        }
        final char[] buffer = this.buffer;
        if (buffer[pos] != ')' || buffer[pos + 1] != ']' || buffer[pos + 2] != '}' || buffer[pos + 3] != '\'' || buffer[pos + 4] != '\n') {
            return;
        }
        this.pos += 5;
    }
    
    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() {
            @Override
            public void promoteNameToValue(final JsonReader jsonReader) throws IOException {
                if (jsonReader instanceof JsonTreeReader) {
                    ((JsonTreeReader)jsonReader).promoteNameToValue();
                    return;
                }
                int n = jsonReader.peeked;
                if (n == 0) {
                    n = jsonReader.doPeek();
                }
                if (n == 13) {
                    jsonReader.peeked = 9;
                }
                else if (n == 12) {
                    jsonReader.peeked = 8;
                }
                else {
                    if (n != 14) {
                        throw new IllegalStateException("Expected a name but was " + jsonReader.peek() + jsonReader.locationString());
                    }
                    jsonReader.peeked = 10;
                }
            }
        };
    }
}
