package com.viaversion.viaversion.unsupported;

import com.viaversion.viaversion.api.platform.*;
import java.util.*;
import com.google.common.base.*;

public final class UnsupportedSoftwareImpl implements UnsupportedSoftware
{
    private final String name;
    private final List classNames;
    private final List methods;
    private final String reason;
    
    public UnsupportedSoftwareImpl(final String name, final List list, final List list2, final String reason) {
        this.name = name;
        this.classNames = Collections.unmodifiableList((List<?>)list);
        this.methods = Collections.unmodifiableList((List<?>)list2);
        this.reason = reason;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getReason() {
        return this.reason;
    }
    
    @Override
    public boolean findMatch() {
        final Iterator<String> iterator = this.classNames.iterator();
        if (iterator.hasNext()) {
            Class.forName(iterator.next());
            return true;
        }
        final Iterator<UnsupportedMethods> iterator2 = this.methods.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().findMatch()) {
                return true;
            }
        }
        return false;
    }
    
    public static final class Reason
    {
        public static final String DANGEROUS_SERVER_SOFTWARE = "You are using server software that - outside of possibly breaking ViaVersion - can also cause severe damage to your server's integrity as a whole.";
    }
    
    public static final class Builder
    {
        private final List classNames;
        private final List methods;
        private String name;
        private String reason;
        
        public Builder() {
            this.classNames = new ArrayList();
            this.methods = new ArrayList();
        }
        
        public Builder name(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder reason(final String reason) {
            this.reason = reason;
            return this;
        }
        
        public Builder addMethod(final String s, final String s2) {
            this.methods.add(new UnsupportedMethods(s, Collections.singleton(s2)));
            return this;
        }
        
        public Builder addMethods(final String s, final String... array) {
            this.methods.add(new UnsupportedMethods(s, new HashSet(Arrays.asList(array))));
            return this;
        }
        
        public Builder addClassName(final String s) {
            this.classNames.add(s);
            return this;
        }
        
        public UnsupportedSoftware build() {
            Preconditions.checkNotNull(this.name);
            Preconditions.checkNotNull(this.reason);
            return new UnsupportedSoftwareImpl(this.name, this.classNames, this.methods, this.reason);
        }
    }
}
