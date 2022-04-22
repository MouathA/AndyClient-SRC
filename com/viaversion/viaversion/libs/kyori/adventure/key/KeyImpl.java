package com.viaversion.viaversion.libs.kyori.adventure.key;

import java.util.function.*;
import java.util.*;
import org.jetbrains.annotations.*;
import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;

final class KeyImpl implements Key
{
    static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";
    static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";
    private static final IntPredicate NAMESPACE_PREDICATE;
    private static final IntPredicate VALUE_PREDICATE;
    private final String namespace;
    private final String value;
    
    KeyImpl(@NotNull final String namespace, @NotNull final String value) {
        if (!namespaceValid(namespace)) {
            throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9_.-] character in namespace of Key[%s]", asString(namespace, value)));
        }
        if (!valueValid(value)) {
            throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9/._-] character in value of Key[%s]", asString(namespace, value)));
        }
        this.namespace = Objects.requireNonNull(namespace, "namespace");
        this.value = Objects.requireNonNull(value, "value");
    }
    
    @VisibleForTesting
    static boolean namespaceValid(@NotNull final String namespace) {
        while (0 < namespace.length()) {
            if (!KeyImpl.NAMESPACE_PREDICATE.test(namespace.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @VisibleForTesting
    static boolean valueValid(@NotNull final String value) {
        while (0 < value.length()) {
            if (!KeyImpl.VALUE_PREDICATE.test(value.charAt(0))) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @NotNull
    @Override
    public String namespace() {
        return this.namespace;
    }
    
    @NotNull
    @Override
    public String value() {
        return this.value;
    }
    
    @NotNull
    @Override
    public String asString() {
        return asString(this.namespace, this.value);
    }
    
    @NotNull
    private static String asString(@NotNull final String namespace, @NotNull final String value) {
        return namespace + ':' + value;
    }
    
    @NotNull
    @Override
    public String toString() {
        return this.asString();
    }
    
    @NotNull
    @Override
    public Stream examinableProperties() {
        return Stream.of(new ExaminableProperty[] { ExaminableProperty.of("namespace", this.namespace), ExaminableProperty.of("value", this.value) });
    }
    
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Key)) {
            return false;
        }
        final Key key = (Key)other;
        return Objects.equals(this.namespace, key.namespace()) && Objects.equals(this.value, key.value());
    }
    
    @Override
    public int hashCode() {
        return 31 * this.namespace.hashCode() + this.value.hashCode();
    }
    
    @Override
    public int compareTo(@NotNull final Key that) {
        return super.compareTo(that);
    }
    
    static int clampCompare(final int value) {
        if (value < 0) {
            return -1;
        }
        if (value > 0) {
            return 1;
        }
        return value;
    }
    
    @Override
    public int compareTo(@NotNull final Object that) {
        return this.compareTo((Key)that);
    }
    
    private static boolean lambda$static$1(final int n) {
        return n == 95 || n == 45 || (n >= 97 && n <= 122) || (n >= 48 && n <= 57) || n == 47 || n == 46;
    }
    
    private static boolean lambda$static$0(final int n) {
        return n == 95 || n == 45 || (n >= 97 && n <= 122) || (n >= 48 && n <= 57) || n == 46;
    }
    
    static {
        NAMESPACE_PREDICATE = KeyImpl::lambda$static$0;
        VALUE_PREDICATE = KeyImpl::lambda$static$1;
    }
}
