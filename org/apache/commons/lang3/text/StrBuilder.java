package org.apache.commons.lang3.text;

import org.apache.commons.lang3.builder.*;
import org.apache.commons.lang3.*;
import java.io.*;
import java.util.*;

public class StrBuilder implements CharSequence, Appendable, Serializable, Builder
{
    static final int CAPACITY = 32;
    private static final long serialVersionUID = 7628716375283629643L;
    protected char[] buffer;
    protected int size;
    private String newLine;
    private String nullText;
    
    public StrBuilder() {
        this(32);
    }
    
    public StrBuilder(final int n) {
        if (32 <= 0) {}
        this.buffer = new char[32];
    }
    
    public StrBuilder(final String s) {
        if (s == null) {
            this.buffer = new char[32];
        }
        else {
            this.buffer = new char[s.length() + 32];
            this.append(s);
        }
    }
    
    public String getNewLineText() {
        return this.newLine;
    }
    
    public StrBuilder setNewLineText(final String newLine) {
        this.newLine = newLine;
        return this;
    }
    
    public String getNullText() {
        return this.nullText;
    }
    
    public StrBuilder setNullText(String nullText) {
        if (nullText != null && nullText.isEmpty()) {
            nullText = null;
        }
        this.nullText = nullText;
        return this;
    }
    
    @Override
    public int length() {
        return this.size;
    }
    
    public StrBuilder setLength(final int n) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n < this.size) {
            this.size = n;
        }
        else if (n > this.size) {
            this.ensureCapacity(n);
            final int size = this.size;
            this.size = n;
            for (int i = size; i < n; ++i) {
                this.buffer[i] = '\0';
            }
        }
        return this;
    }
    
    public int capacity() {
        return this.buffer.length;
    }
    
    public StrBuilder ensureCapacity(final int n) {
        if (n > this.buffer.length) {
            System.arraycopy(this.buffer, 0, this.buffer = new char[n * 2], 0, this.size);
        }
        return this;
    }
    
    public StrBuilder minimizeCapacity() {
        if (this.buffer.length > this.length()) {
            System.arraycopy(this.buffer, 0, this.buffer = new char[this.length()], 0, this.size);
        }
        return this;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public StrBuilder clear() {
        this.size = 0;
        return this;
    }
    
    @Override
    public char charAt(final int n) {
        if (n < 0 || n >= this.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return this.buffer[n];
    }
    
    public StrBuilder setCharAt(final int n, final char c) {
        if (n < 0 || n >= this.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        this.buffer[n] = c;
        return this;
    }
    
    public StrBuilder deleteCharAt(final int n) {
        if (n < 0 || n >= this.size) {
            throw new StringIndexOutOfBoundsException(n);
        }
        this.deleteImpl(n, n + 1, 1);
        return this;
    }
    
    public char[] toCharArray() {
        if (this.size == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] array = new char[this.size];
        System.arraycopy(this.buffer, 0, array, 0, this.size);
        return array;
    }
    
    public char[] toCharArray(final int n, int validateRange) {
        validateRange = this.validateRange(n, validateRange);
        final int n2 = validateRange - n;
        if (n2 == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] array = new char[n2];
        System.arraycopy(this.buffer, n, array, 0, n2);
        return array;
    }
    
    public char[] getChars(char[] array) {
        final int length = this.length();
        if (array == null || array.length < length) {
            array = new char[length];
        }
        System.arraycopy(this.buffer, 0, array, 0, length);
        return array;
    }
    
    public void getChars(final int n, final int n2, final char[] array, final int n3) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 < 0 || n2 > this.length()) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n > n2) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        System.arraycopy(this.buffer, n, array, n3, n2 - n);
    }
    
    public StrBuilder appendNewLine() {
        if (this.newLine == null) {
            this.append(SystemUtils.LINE_SEPARATOR);
            return this;
        }
        return this.append(this.newLine);
    }
    
    public StrBuilder appendNull() {
        if (this.nullText == null) {
            return this;
        }
        return this.append(this.nullText);
    }
    
    public StrBuilder append(final Object o) {
        if (o == null) {
            return this.appendNull();
        }
        return this.append(o.toString());
    }
    
    @Override
    public StrBuilder append(final CharSequence charSequence) {
        if (charSequence == null) {
            return this.appendNull();
        }
        return this.append(charSequence.toString());
    }
    
    @Override
    public StrBuilder append(final CharSequence charSequence, final int n, final int n2) {
        if (charSequence == null) {
            return this.appendNull();
        }
        return this.append(charSequence.toString(), n, n2);
    }
    
    public StrBuilder append(final String s) {
        if (s == null) {
            return this.appendNull();
        }
        final int length = s.length();
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            s.getChars(0, length, this.buffer, length2);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final String s, final int n, final int n2) {
        if (s == null) {
            return this.appendNull();
        }
        if (n < 0 || n > s.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if (n2 < 0 || n + n2 > s.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if (n2 > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n2);
            s.getChars(n, n + n2, this.buffer, length);
            this.size += n2;
        }
        return this;
    }
    
    public StrBuilder append(final String s, final Object... array) {
        return this.append(String.format(s, array));
    }
    
    public StrBuilder append(final StringBuffer sb) {
        if (sb == null) {
            return this.appendNull();
        }
        final int length = sb.length();
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            sb.getChars(0, length, this.buffer, length2);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final StringBuffer sb, final int n, final int n2) {
        if (sb == null) {
            return this.appendNull();
        }
        if (n < 0 || n > sb.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if (n2 < 0 || n + n2 > sb.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if (n2 > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n2);
            sb.getChars(n, n + n2, this.buffer, length);
            this.size += n2;
        }
        return this;
    }
    
    public StrBuilder append(final StringBuilder sb) {
        if (sb == null) {
            return this.appendNull();
        }
        final int length = sb.length();
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            sb.getChars(0, length, this.buffer, length2);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final StringBuilder sb, final int n, final int n2) {
        if (sb == null) {
            return this.appendNull();
        }
        if (n < 0 || n > sb.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if (n2 < 0 || n + n2 > sb.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if (n2 > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n2);
            sb.getChars(n, n + n2, this.buffer, length);
            this.size += n2;
        }
        return this;
    }
    
    public StrBuilder append(final StrBuilder strBuilder) {
        if (strBuilder == null) {
            return this.appendNull();
        }
        final int length = strBuilder.length();
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            System.arraycopy(strBuilder.buffer, 0, this.buffer, length2, length);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final StrBuilder strBuilder, final int n, final int n2) {
        if (strBuilder == null) {
            return this.appendNull();
        }
        if (n < 0 || n > strBuilder.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if (n2 < 0 || n + n2 > strBuilder.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if (n2 > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n2);
            strBuilder.getChars(n, n + n2, this.buffer, length);
            this.size += n2;
        }
        return this;
    }
    
    public StrBuilder append(final char[] array) {
        if (array == null) {
            return this.appendNull();
        }
        final int length = array.length;
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            System.arraycopy(array, 0, this.buffer, length2, length);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final char[] array, final int n, final int n2) {
        if (array == null) {
            return this.appendNull();
        }
        if (n < 0 || n > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid startIndex: " + n2);
        }
        if (n2 < 0 || n + n2 > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + n2);
        }
        if (n2 > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n2);
            System.arraycopy(array, n, this.buffer, length, n2);
            this.size += n2;
        }
        return this;
    }
    
    public StrBuilder append(final boolean b) {
        if (b) {
            this.ensureCapacity(this.size + 4);
            this.buffer[this.size++] = 't';
            this.buffer[this.size++] = 'r';
            this.buffer[this.size++] = 'u';
            this.buffer[this.size++] = 'e';
        }
        else {
            this.ensureCapacity(this.size + 5);
            this.buffer[this.size++] = 'f';
            this.buffer[this.size++] = 'a';
            this.buffer[this.size++] = 'l';
            this.buffer[this.size++] = 's';
            this.buffer[this.size++] = 'e';
        }
        return this;
    }
    
    @Override
    public StrBuilder append(final char c) {
        this.ensureCapacity(this.length() + 1);
        this.buffer[this.size++] = c;
        return this;
    }
    
    public StrBuilder append(final int n) {
        return this.append(String.valueOf(n));
    }
    
    public StrBuilder append(final long n) {
        return this.append(String.valueOf(n));
    }
    
    public StrBuilder append(final float n) {
        return this.append(String.valueOf(n));
    }
    
    public StrBuilder append(final double n) {
        return this.append(String.valueOf(n));
    }
    
    public StrBuilder appendln(final Object o) {
        return this.append(o).appendNewLine();
    }
    
    public StrBuilder appendln(final String s) {
        return this.append(s).appendNewLine();
    }
    
    public StrBuilder appendln(final String s, final int n, final int n2) {
        return this.append(s, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final String s, final Object... array) {
        return this.append(s, array).appendNewLine();
    }
    
    public StrBuilder appendln(final StringBuffer sb) {
        return this.append(sb).appendNewLine();
    }
    
    public StrBuilder appendln(final StringBuilder sb) {
        return this.append(sb).appendNewLine();
    }
    
    public StrBuilder appendln(final StringBuilder sb, final int n, final int n2) {
        return this.append(sb, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final StringBuffer sb, final int n, final int n2) {
        return this.append(sb, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final StrBuilder strBuilder) {
        return this.append(strBuilder).appendNewLine();
    }
    
    public StrBuilder appendln(final StrBuilder strBuilder, final int n, final int n2) {
        return this.append(strBuilder, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final char[] array) {
        return this.append(array).appendNewLine();
    }
    
    public StrBuilder appendln(final char[] array, final int n, final int n2) {
        return this.append(array, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final boolean b) {
        return this.append(b).appendNewLine();
    }
    
    public StrBuilder appendln(final char c) {
        return this.append(c).appendNewLine();
    }
    
    public StrBuilder appendln(final int n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendln(final long n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendln(final float n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendln(final double n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendAll(final Object... array) {
        if (array != null && array.length > 0) {
            while (0 < array.length) {
                this.append(array[0]);
                int n = 0;
                ++n;
            }
        }
        return this;
    }
    
    public StrBuilder appendAll(final Iterable iterable) {
        if (iterable != null) {
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                this.append(iterator.next());
            }
        }
        return this;
    }
    
    public StrBuilder appendAll(final Iterator iterator) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                this.append(iterator.next());
            }
        }
        return this;
    }
    
    public StrBuilder appendWithSeparators(final Object[] array, final String s) {
        if (array != null && array.length > 0) {
            final String string = ObjectUtils.toString(s);
            this.append(array[0]);
            while (1 < array.length) {
                this.append(string);
                this.append(array[1]);
                int n = 0;
                ++n;
            }
        }
        return this;
    }
    
    public StrBuilder appendWithSeparators(final Iterable iterable, final String s) {
        if (iterable != null) {
            final String string = ObjectUtils.toString(s);
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                this.append(iterator.next());
                if (iterator.hasNext()) {
                    this.append(string);
                }
            }
        }
        return this;
    }
    
    public StrBuilder appendWithSeparators(final Iterator iterator, final String s) {
        if (iterator != null) {
            final String string = ObjectUtils.toString(s);
            while (iterator.hasNext()) {
                this.append(iterator.next());
                if (iterator.hasNext()) {
                    this.append(string);
                }
            }
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final String s) {
        return this.appendSeparator(s, null);
    }
    
    public StrBuilder appendSeparator(final String s, final String s2) {
        final String s3 = this.isEmpty() ? s2 : s;
        if (s3 != null) {
            this.append(s3);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final char c) {
        if (this.size() > 0) {
            this.append(c);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final char c, final char c2) {
        if (this.size() > 0) {
            this.append(c);
        }
        else {
            this.append(c2);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final String s, final int n) {
        if (s != null && n > 0) {
            this.append(s);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final char c, final int n) {
        if (n > 0) {
            this.append(c);
        }
        return this;
    }
    
    public StrBuilder appendPadding(final int n, final char c) {
        if (n >= 0) {
            this.ensureCapacity(this.size + n);
            while (0 < n) {
                this.buffer[this.size++] = c;
                int n2 = 0;
                ++n2;
            }
        }
        return this;
    }
    
    public StrBuilder appendFixedWidthPadLeft(final Object o, final int n, final char c) {
        if (n > 0) {
            this.ensureCapacity(this.size + n);
            String s = (o == null) ? this.getNullText() : o.toString();
            if (s == null) {
                s = "";
            }
            final int length = s.length();
            if (length >= n) {
                s.getChars(length - n, length, this.buffer, this.size);
            }
            else {
                final int n2 = n - length;
                while (0 < n2) {
                    this.buffer[this.size + 0] = c;
                    int n3 = 0;
                    ++n3;
                }
                s.getChars(0, length, this.buffer, this.size + n2);
            }
            this.size += n;
        }
        return this;
    }
    
    public StrBuilder appendFixedWidthPadLeft(final int n, final int n2, final char c) {
        return this.appendFixedWidthPadLeft(String.valueOf(n), n2, c);
    }
    
    public StrBuilder appendFixedWidthPadRight(final Object o, final int n, final char c) {
        if (n > 0) {
            this.ensureCapacity(this.size + n);
            String s = (o == null) ? this.getNullText() : o.toString();
            if (s == null) {
                s = "";
            }
            final int length = s.length();
            if (length >= n) {
                s.getChars(0, n, this.buffer, this.size);
            }
            else {
                final int n2 = n - length;
                s.getChars(0, length, this.buffer, this.size);
                while (0 < n2) {
                    this.buffer[this.size + length + 0] = c;
                    int n3 = 0;
                    ++n3;
                }
            }
            this.size += n;
        }
        return this;
    }
    
    public StrBuilder appendFixedWidthPadRight(final int n, final int n2, final char c) {
        return this.appendFixedWidthPadRight(String.valueOf(n), n2, c);
    }
    
    public StrBuilder insert(final int n, final Object o) {
        if (o == null) {
            return this.insert(n, this.nullText);
        }
        return this.insert(n, o.toString());
    }
    
    public StrBuilder insert(final int n, String nullText) {
        this.validateIndex(n);
        if (nullText == null) {
            nullText = this.nullText;
        }
        if (nullText != null) {
            final int length = nullText.length();
            if (length > 0) {
                final int size = this.size + length;
                this.ensureCapacity(size);
                System.arraycopy(this.buffer, n, this.buffer, n + length, this.size - n);
                this.size = size;
                nullText.getChars(0, length, this.buffer, n);
            }
        }
        return this;
    }
    
    public StrBuilder insert(final int n, final char[] array) {
        this.validateIndex(n);
        if (array == null) {
            return this.insert(n, this.nullText);
        }
        final int length = array.length;
        if (length > 0) {
            this.ensureCapacity(this.size + length);
            System.arraycopy(this.buffer, n, this.buffer, n + length, this.size - n);
            System.arraycopy(array, 0, this.buffer, n, length);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder insert(final int n, final char[] array, final int n2, final int n3) {
        this.validateIndex(n);
        if (array == null) {
            return this.insert(n, this.nullText);
        }
        if (n2 < 0 || n2 > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid offset: " + n2);
        }
        if (n3 < 0 || n2 + n3 > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + n3);
        }
        if (n3 > 0) {
            this.ensureCapacity(this.size + n3);
            System.arraycopy(this.buffer, n, this.buffer, n + n3, this.size - n);
            System.arraycopy(array, n2, this.buffer, n, n3);
            this.size += n3;
        }
        return this;
    }
    
    public StrBuilder insert(int n, final boolean b) {
        this.validateIndex(n);
        if (b) {
            this.ensureCapacity(this.size + 4);
            System.arraycopy(this.buffer, n, this.buffer, n + 4, this.size - n);
            this.buffer[n++] = 't';
            this.buffer[n++] = 'r';
            this.buffer[n++] = 'u';
            this.buffer[n] = 'e';
            this.size += 4;
        }
        else {
            this.ensureCapacity(this.size + 5);
            System.arraycopy(this.buffer, n, this.buffer, n + 5, this.size - n);
            this.buffer[n++] = 'f';
            this.buffer[n++] = 'a';
            this.buffer[n++] = 'l';
            this.buffer[n++] = 's';
            this.buffer[n] = 'e';
            this.size += 5;
        }
        return this;
    }
    
    public StrBuilder insert(final int n, final char c) {
        this.validateIndex(n);
        this.ensureCapacity(this.size + 1);
        System.arraycopy(this.buffer, n, this.buffer, n + 1, this.size - n);
        this.buffer[n] = c;
        ++this.size;
        return this;
    }
    
    public StrBuilder insert(final int n, final int n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    public StrBuilder insert(final int n, final long n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    public StrBuilder insert(final int n, final float n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    public StrBuilder insert(final int n, final double n2) {
        return this.insert(n, String.valueOf(n2));
    }
    
    private void deleteImpl(final int n, final int n2, final int n3) {
        System.arraycopy(this.buffer, n2, this.buffer, n, this.size - n2);
        this.size -= n3;
    }
    
    public StrBuilder delete(final int n, int validateRange) {
        validateRange = this.validateRange(n, validateRange);
        final int n2 = validateRange - n;
        if (n2 > 0) {
            this.deleteImpl(n, validateRange, n2);
        }
        return this;
    }
    
    public StrBuilder deleteAll(final char c) {
        while (0 < this.size) {
            int n = 0;
            if (this.buffer[0] == c) {
                do {
                    ++n;
                } while (0 < this.size && this.buffer[0] == c);
                this.deleteImpl(0, 0, 0);
            }
            ++n;
        }
        return this;
    }
    
    public StrBuilder deleteFirst(final char c) {
        while (0 < this.size) {
            if (this.buffer[0] == c) {
                this.deleteImpl(0, 1, 1);
                break;
            }
            int n = 0;
            ++n;
        }
        return this;
    }
    
    public StrBuilder deleteAll(final String s) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            for (int i = this.indexOf(s, 0); i >= 0; i = this.indexOf(s, i)) {
                this.deleteImpl(i, i + n, n);
            }
        }
        return this;
    }
    
    public StrBuilder deleteFirst(final String s) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            final int index = this.indexOf(s, 0);
            if (index >= 0) {
                this.deleteImpl(index, index + n, n);
            }
        }
        return this;
    }
    
    public StrBuilder deleteAll(final StrMatcher strMatcher) {
        return this.replace(strMatcher, null, 0, this.size, -1);
    }
    
    public StrBuilder deleteFirst(final StrMatcher strMatcher) {
        return this.replace(strMatcher, null, 0, this.size, 1);
    }
    
    private void replaceImpl(final int n, final int n2, final int n3, final String s, final int n4) {
        final int size = this.size - n3 + n4;
        if (n4 != n3) {
            this.ensureCapacity(size);
            System.arraycopy(this.buffer, n2, this.buffer, n + n4, this.size - n2);
            this.size = size;
        }
        if (n4 > 0) {
            s.getChars(0, n4, this.buffer, n);
        }
    }
    
    public StrBuilder replace(final int n, int validateRange, final String s) {
        validateRange = this.validateRange(n, validateRange);
        this.replaceImpl(n, validateRange, validateRange - n, s, (s == null) ? 0 : s.length());
        return this;
    }
    
    public StrBuilder replaceAll(final char c, final char c2) {
        if (c != c2) {
            while (0 < this.size) {
                if (this.buffer[0] == c) {
                    this.buffer[0] = c2;
                }
                int n = 0;
                ++n;
            }
        }
        return this;
    }
    
    public StrBuilder replaceFirst(final char c, final char c2) {
        if (c != c2) {
            while (0 < this.size) {
                if (this.buffer[0] == c) {
                    this.buffer[0] = c2;
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        return this;
    }
    
    public StrBuilder replaceAll(final String s, final String s2) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            for (int n2 = (s2 == null) ? 0 : s2.length(), i = this.indexOf(s, 0); i >= 0; i = this.indexOf(s, i + n2)) {
                this.replaceImpl(i, i + n, n, s2, n2);
            }
        }
        return this;
    }
    
    public StrBuilder replaceFirst(final String s, final String s2) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            final int index = this.indexOf(s, 0);
            if (index >= 0) {
                this.replaceImpl(index, index + n, n, s2, (s2 == null) ? 0 : s2.length());
            }
        }
        return this;
    }
    
    public StrBuilder replaceAll(final StrMatcher strMatcher, final String s) {
        return this.replace(strMatcher, s, 0, this.size, -1);
    }
    
    public StrBuilder replaceFirst(final StrMatcher strMatcher, final String s) {
        return this.replace(strMatcher, s, 0, this.size, 1);
    }
    
    public StrBuilder replace(final StrMatcher strMatcher, final String s, final int n, int validateRange, final int n2) {
        validateRange = this.validateRange(n, validateRange);
        return this.replaceImpl(strMatcher, s, n, validateRange, n2);
    }
    
    private StrBuilder replaceImpl(final StrMatcher strMatcher, final String s, final int n, int n2, int n3) {
        if (strMatcher == null || this.size == 0) {
            return this;
        }
        final int n4 = (s == null) ? 0 : s.length();
        final char[] buffer = this.buffer;
        for (int n5 = n; n5 < n2 && n3 != 0; ++n5) {
            final int match = strMatcher.isMatch(buffer, n5, n, n2);
            if (match > 0) {
                this.replaceImpl(n5, n5 + match, match, s, n4);
                n2 = n2 - match + n4;
                n5 = n5 + n4 - 1;
                if (n3 > 0) {
                    --n3;
                }
            }
        }
        return this;
    }
    
    public StrBuilder reverse() {
        if (this.size == 0) {
            return this;
        }
        final int n = this.size / 2;
        final char[] buffer = this.buffer;
        int n2 = this.size - 1;
        while (0 < n) {
            final char c = buffer[0];
            buffer[0] = buffer[n2];
            buffer[n2] = c;
            int n3 = 0;
            ++n3;
            --n2;
        }
        return this;
    }
    
    public StrBuilder trim() {
        if (this.size == 0) {
            return this;
        }
        int size = this.size;
        final char[] buffer = this.buffer;
        while (0 < size && buffer[0] <= ' ') {
            int n = 0;
            ++n;
        }
        while (0 < size && buffer[size - 1] <= ' ') {
            --size;
        }
        if (size < this.size) {
            this.delete(size, this.size);
        }
        if (0 > 0) {
            this.delete(0, 0);
        }
        return this;
    }
    
    public boolean startsWith(final String s) {
        if (s == null) {
            return false;
        }
        final int length = s.length();
        if (length == 0) {
            return true;
        }
        if (length > this.size) {
            return false;
        }
        while (0 < length) {
            if (this.buffer[0] != s.charAt(0)) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public boolean endsWith(final String s) {
        if (s == null) {
            return false;
        }
        final int length = s.length();
        if (length == 0) {
            return true;
        }
        if (length > this.size) {
            return false;
        }
        int n = this.size - length;
        while (0 < length) {
            if (this.buffer[n] != s.charAt(0)) {
                return false;
            }
            int n2 = 0;
            ++n2;
            ++n;
        }
        return true;
    }
    
    @Override
    public CharSequence subSequence(final int n, final int n2) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > this.size) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n > n2) {
            throw new StringIndexOutOfBoundsException(n2 - n);
        }
        return this.substring(n, n2);
    }
    
    public String substring(final int n) {
        return this.substring(n, this.size);
    }
    
    public String substring(final int n, int validateRange) {
        validateRange = this.validateRange(n, validateRange);
        return new String(this.buffer, n, validateRange - n);
    }
    
    public String leftString(final int n) {
        if (n <= 0) {
            return "";
        }
        if (n >= this.size) {
            return new String(this.buffer, 0, this.size);
        }
        return new String(this.buffer, 0, n);
    }
    
    public String rightString(final int n) {
        if (n <= 0) {
            return "";
        }
        if (n >= this.size) {
            return new String(this.buffer, 0, this.size);
        }
        return new String(this.buffer, this.size - n, n);
    }
    
    public String midString(final int n, final int n2) {
        if (0 < 0) {}
        if (n2 <= 0 || 0 >= this.size) {
            return "";
        }
        if (this.size <= 0 + n2) {
            return new String(this.buffer, 0, this.size - 0);
        }
        return new String(this.buffer, 0, n2);
    }
    
    public boolean contains(final char c) {
        final char[] buffer = this.buffer;
        while (0 < this.size) {
            if (buffer[0] == c) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public boolean contains(final String s) {
        return this.indexOf(s, 0) >= 0;
    }
    
    public boolean contains(final StrMatcher strMatcher) {
        return this.indexOf(strMatcher, 0) >= 0;
    }
    
    public int indexOf(final char c) {
        return this.indexOf(c, 0);
    }
    
    public int indexOf(final char c, int n) {
        n = ((n < 0) ? 0 : n);
        if (n >= this.size) {
            return -1;
        }
        final char[] buffer = this.buffer;
        for (int i = n; i < this.size; ++i) {
            if (buffer[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public int indexOf(final String s) {
        return this.indexOf(s, 0);
    }
    
    public int indexOf(final String s, int n) {
        n = ((n < 0) ? 0 : n);
        if (s == null || n >= this.size) {
            return -1;
        }
        final int length = s.length();
        if (length == 1) {
            return this.indexOf(s.charAt(0), n);
        }
        if (length == 0) {
            return n;
        }
        if (length > this.size) {
            return -1;
        }
        final char[] buffer = this.buffer;
        final int n2 = this.size - length + 1;
        int i = n;
    Label_0080:
        while (i < n2) {
            while (0 < length) {
                if (s.charAt(0) != buffer[i + 0]) {
                    ++i;
                    continue Label_0080;
                }
                int n3 = 0;
                ++n3;
            }
            return i;
        }
        return -1;
    }
    
    public int indexOf(final StrMatcher strMatcher) {
        return this.indexOf(strMatcher, 0);
    }
    
    public int indexOf(final StrMatcher strMatcher, int n) {
        n = ((n < 0) ? 0 : n);
        if (strMatcher == null || n >= this.size) {
            return -1;
        }
        final int size = this.size;
        final char[] buffer = this.buffer;
        for (int i = n; i < size; ++i) {
            if (strMatcher.isMatch(buffer, i, n, size) > 0) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final char c) {
        return this.lastIndexOf(c, this.size - 1);
    }
    
    public int lastIndexOf(final char c, int n) {
        n = ((n >= this.size) ? (this.size - 1) : n);
        if (n < 0) {
            return -1;
        }
        for (int i = n; i >= 0; --i) {
            if (this.buffer[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final String s) {
        return this.lastIndexOf(s, this.size - 1);
    }
    
    public int lastIndexOf(final String s, int n) {
        n = ((n >= this.size) ? (this.size - 1) : n);
        if (s == null || n < 0) {
            return -1;
        }
        final int length = s.length();
        if (length > 0 && length <= this.size) {
            if (length == 1) {
                return this.lastIndexOf(s.charAt(0), n);
            }
            int i = n - length + 1;
        Label_0069:
            while (i >= 0) {
                while (0 < length) {
                    if (s.charAt(0) != this.buffer[i + 0]) {
                        --i;
                        continue Label_0069;
                    }
                    int n2 = 0;
                    ++n2;
                }
                return i;
            }
        }
        else if (length == 0) {
            return n;
        }
        return -1;
    }
    
    public int lastIndexOf(final StrMatcher strMatcher) {
        return this.lastIndexOf(strMatcher, this.size);
    }
    
    public int lastIndexOf(final StrMatcher strMatcher, int n) {
        n = ((n >= this.size) ? (this.size - 1) : n);
        if (strMatcher == null || n < 0) {
            return -1;
        }
        final char[] buffer = this.buffer;
        final int n2 = n + 1;
        for (int i = n; i >= 0; --i) {
            if (strMatcher.isMatch(buffer, i, 0, n2) > 0) {
                return i;
            }
        }
        return -1;
    }
    
    public StrTokenizer asTokenizer() {
        return new StrBuilderTokenizer();
    }
    
    public Reader asReader() {
        return new StrBuilderReader();
    }
    
    public Writer asWriter() {
        return new StrBuilderWriter();
    }
    
    public boolean equalsIgnoreCase(final StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        if (this.size != strBuilder.size) {
            return false;
        }
        final char[] buffer = this.buffer;
        final char[] buffer2 = strBuilder.buffer;
        for (int i = this.size - 1; i >= 0; --i) {
            final char c = buffer[i];
            final char c2 = buffer2[i];
            if (c != c2 && Character.toUpperCase(c) != Character.toUpperCase(c2)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean equals(final StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        if (this.size != strBuilder.size) {
            return false;
        }
        final char[] buffer = this.buffer;
        final char[] buffer2 = strBuilder.buffer;
        for (int i = this.size - 1; i >= 0; --i) {
            if (buffer[i] != buffer2[i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof StrBuilder && this.equals((StrBuilder)o);
    }
    
    @Override
    public int hashCode() {
        final char[] buffer = this.buffer;
        for (int i = this.size - 1; i >= 0; --i) {
            final int n = '\0' + buffer[i];
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return new String(this.buffer, 0, this.size);
    }
    
    public StringBuffer toStringBuffer() {
        return new StringBuffer(this.size).append(this.buffer, 0, this.size);
    }
    
    public StringBuilder toStringBuilder() {
        return new StringBuilder(this.size).append(this.buffer, 0, this.size);
    }
    
    @Override
    public String build() {
        return this.toString();
    }
    
    protected int validateRange(final int n, int size) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (size > this.size) {
            size = this.size;
        }
        if (n > size) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        return size;
    }
    
    protected void validateIndex(final int n) {
        if (n < 0 || n > this.size) {
            throw new StringIndexOutOfBoundsException(n);
        }
    }
    
    @Override
    public Appendable append(final char c) throws IOException {
        return this.append(c);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence, final int n, final int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }
    
    @Override
    public Appendable append(final CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
    
    @Override
    public Object build() {
        return this.build();
    }
    
    class StrBuilderWriter extends Writer
    {
        final StrBuilder this$0;
        
        StrBuilderWriter(final StrBuilder this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void close() {
        }
        
        @Override
        public void flush() {
        }
        
        @Override
        public void write(final int n) {
            this.this$0.append((char)n);
        }
        
        @Override
        public void write(final char[] array) {
            this.this$0.append(array);
        }
        
        @Override
        public void write(final char[] array, final int n, final int n2) {
            this.this$0.append(array, n, n2);
        }
        
        @Override
        public void write(final String s) {
            this.this$0.append(s);
        }
        
        @Override
        public void write(final String s, final int n, final int n2) {
            this.this$0.append(s, n, n2);
        }
    }
    
    class StrBuilderReader extends Reader
    {
        private int pos;
        private int mark;
        final StrBuilder this$0;
        
        StrBuilderReader(final StrBuilder this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public void close() {
        }
        
        @Override
        public int read() {
            if (!this.ready()) {
                return -1;
            }
            return this.this$0.charAt(this.pos++);
        }
        
        @Override
        public int read(final char[] array, final int n, int n2) {
            if (n < 0 || n2 < 0 || n > array.length || n + n2 > array.length || n + n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return 0;
            }
            if (this.pos >= this.this$0.size()) {
                return -1;
            }
            if (this.pos + n2 > this.this$0.size()) {
                n2 = this.this$0.size() - this.pos;
            }
            this.this$0.getChars(this.pos, this.pos + n2, array, n);
            this.pos += n2;
            return n2;
        }
        
        @Override
        public long skip(long n) {
            if (this.pos + n > this.this$0.size()) {
                n = this.this$0.size() - this.pos;
            }
            if (n < 0L) {
                return 0L;
            }
            this.pos += (int)n;
            return n;
        }
        
        @Override
        public boolean ready() {
            return this.pos < this.this$0.size();
        }
        
        @Override
        public boolean markSupported() {
            return true;
        }
        
        @Override
        public void mark(final int n) {
            this.mark = this.pos;
        }
        
        @Override
        public void reset() {
            this.pos = this.mark;
        }
    }
    
    class StrBuilderTokenizer extends StrTokenizer
    {
        final StrBuilder this$0;
        
        StrBuilderTokenizer(final StrBuilder this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        protected List tokenize(final char[] array, final int n, final int n2) {
            if (array == null) {
                return super.tokenize(this.this$0.buffer, 0, this.this$0.size());
            }
            return super.tokenize(array, n, n2);
        }
        
        @Override
        public String getContent() {
            final String content = super.getContent();
            if (content == null) {
                return this.this$0.toString();
            }
            return content;
        }
    }
}
