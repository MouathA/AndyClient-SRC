package io.netty.handler.codec.http.cors;

import java.util.concurrent.*;
import io.netty.util.internal.*;
import java.util.*;
import io.netty.handler.codec.http.*;

public final class CorsConfig
{
    private final Set origins;
    private final boolean anyOrigin;
    private final boolean enabled;
    private final Set exposeHeaders;
    private final boolean allowCredentials;
    private final long maxAge;
    private final Set allowedRequestMethods;
    private final Set allowedRequestHeaders;
    private final boolean allowNullOrigin;
    private final Map preflightHeaders;
    private final boolean shortCurcuit;
    
    private CorsConfig(final Builder builder) {
        this.origins = new LinkedHashSet(Builder.access$000(builder));
        this.anyOrigin = Builder.access$100(builder);
        this.enabled = Builder.access$200(builder);
        this.exposeHeaders = Builder.access$300(builder);
        this.allowCredentials = Builder.access$400(builder);
        this.maxAge = Builder.access$500(builder);
        this.allowedRequestMethods = Builder.access$600(builder);
        this.allowedRequestHeaders = Builder.access$700(builder);
        this.allowNullOrigin = Builder.access$800(builder);
        this.preflightHeaders = Builder.access$900(builder);
        this.shortCurcuit = Builder.access$1000(builder);
    }
    
    public boolean isCorsSupportEnabled() {
        return this.enabled;
    }
    
    public boolean isAnyOriginSupported() {
        return this.anyOrigin;
    }
    
    public String origin() {
        return this.origins.isEmpty() ? "*" : this.origins.iterator().next();
    }
    
    public Set origins() {
        return this.origins;
    }
    
    public boolean isNullOriginAllowed() {
        return this.allowNullOrigin;
    }
    
    public Set exposedHeaders() {
        return Collections.unmodifiableSet((Set<?>)this.exposeHeaders);
    }
    
    public boolean isCredentialsAllowed() {
        return this.allowCredentials;
    }
    
    public long maxAge() {
        return this.maxAge;
    }
    
    public Set allowedRequestMethods() {
        return Collections.unmodifiableSet((Set<?>)this.allowedRequestMethods);
    }
    
    public Set allowedRequestHeaders() {
        return Collections.unmodifiableSet((Set<?>)this.allowedRequestHeaders);
    }
    
    public HttpHeaders preflightResponseHeaders() {
        if (this.preflightHeaders.isEmpty()) {
            return HttpHeaders.EMPTY_HEADERS;
        }
        final DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
        for (final Map.Entry<K, Callable> entry : this.preflightHeaders.entrySet()) {
            final Object value = getValue(entry.getValue());
            if (value instanceof Iterable) {
                defaultHttpHeaders.add((CharSequence)entry.getKey(), (Iterable)value);
            }
            else {
                defaultHttpHeaders.add((CharSequence)entry.getKey(), value);
            }
        }
        return defaultHttpHeaders;
    }
    
    public boolean isShortCurcuit() {
        return this.shortCurcuit;
    }
    
    private static Object getValue(final Callable callable) {
        return callable.call();
    }
    
    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "[enabled=" + this.enabled + ", origins=" + this.origins + ", anyOrigin=" + this.anyOrigin + ", exposedHeaders=" + this.exposeHeaders + ", isCredentialsAllowed=" + this.allowCredentials + ", maxAge=" + this.maxAge + ", allowedRequestMethods=" + this.allowedRequestMethods + ", allowedRequestHeaders=" + this.allowedRequestHeaders + ", preflightHeaders=" + this.preflightHeaders + ']';
    }
    
    public static Builder withAnyOrigin() {
        return new Builder();
    }
    
    public static Builder withOrigin(final String s) {
        if ("*".equals(s)) {
            return new Builder();
        }
        return new Builder(new String[] { s });
    }
    
    public static Builder withOrigins(final String... array) {
        return new Builder(array);
    }
    
    CorsConfig(final Builder builder, final CorsConfig$1 object) {
        this(builder);
    }
    
    public static final class DateValueGenerator implements Callable
    {
        @Override
        public Date call() throws Exception {
            return new Date();
        }
        
        @Override
        public Object call() throws Exception {
            return this.call();
        }
    }
    
    private static final class ConstantValueGenerator implements Callable
    {
        private final Object value;
        
        private ConstantValueGenerator(final Object value) {
            if (value == null) {
                throw new IllegalArgumentException("value must not be null");
            }
            this.value = value;
        }
        
        @Override
        public Object call() {
            return this.value;
        }
        
        ConstantValueGenerator(final Object o, final CorsConfig$1 object) {
            this(o);
        }
    }
    
    public static class Builder
    {
        private final Set origins;
        private final boolean anyOrigin;
        private boolean allowNullOrigin;
        private boolean enabled;
        private boolean allowCredentials;
        private final Set exposeHeaders;
        private long maxAge;
        private final Set requestMethods;
        private final Set requestHeaders;
        private final Map preflightHeaders;
        private boolean noPreflightHeaders;
        private boolean shortCurcuit;
        
        public Builder(final String... array) {
            this.enabled = true;
            this.exposeHeaders = new HashSet();
            this.requestMethods = new HashSet();
            this.requestHeaders = new HashSet();
            this.preflightHeaders = new HashMap();
            this.origins = new LinkedHashSet(Arrays.asList(array));
            this.anyOrigin = false;
        }
        
        public Builder() {
            this.enabled = true;
            this.exposeHeaders = new HashSet();
            this.requestMethods = new HashSet();
            this.requestHeaders = new HashSet();
            this.preflightHeaders = new HashMap();
            this.anyOrigin = true;
            this.origins = Collections.emptySet();
        }
        
        public Builder allowNullOrigin() {
            this.allowNullOrigin = true;
            return this;
        }
        
        public Builder disable() {
            this.enabled = false;
            return this;
        }
        
        public Builder exposeHeaders(final String... array) {
            this.exposeHeaders.addAll(Arrays.asList(array));
            return this;
        }
        
        public Builder allowCredentials() {
            this.allowCredentials = true;
            return this;
        }
        
        public Builder maxAge(final long maxAge) {
            this.maxAge = maxAge;
            return this;
        }
        
        public Builder allowedRequestMethods(final HttpMethod... array) {
            this.requestMethods.addAll(Arrays.asList(array));
            return this;
        }
        
        public Builder allowedRequestHeaders(final String... array) {
            this.requestHeaders.addAll(Arrays.asList(array));
            return this;
        }
        
        public Builder preflightResponseHeader(final CharSequence charSequence, final Object... array) {
            if (array.length == 1) {
                this.preflightHeaders.put(charSequence, new ConstantValueGenerator(array[0], null));
            }
            else {
                this.preflightResponseHeader(charSequence, Arrays.asList(array));
            }
            return this;
        }
        
        public Builder preflightResponseHeader(final CharSequence charSequence, final Iterable iterable) {
            this.preflightHeaders.put(charSequence, new ConstantValueGenerator(iterable, null));
            return this;
        }
        
        public Builder preflightResponseHeader(final String s, final Callable callable) {
            this.preflightHeaders.put(s, callable);
            return this;
        }
        
        public Builder noPreflightResponseHeaders() {
            this.noPreflightHeaders = true;
            return this;
        }
        
        public CorsConfig build() {
            if (this.preflightHeaders.isEmpty() && !this.noPreflightHeaders) {
                this.preflightHeaders.put("Date", new DateValueGenerator());
                this.preflightHeaders.put("Content-Length", new ConstantValueGenerator("0", null));
            }
            return new CorsConfig(this, null);
        }
        
        public Builder shortCurcuit() {
            this.shortCurcuit = true;
            return this;
        }
        
        static Set access$000(final Builder builder) {
            return builder.origins;
        }
        
        static boolean access$100(final Builder builder) {
            return builder.anyOrigin;
        }
        
        static boolean access$200(final Builder builder) {
            return builder.enabled;
        }
        
        static Set access$300(final Builder builder) {
            return builder.exposeHeaders;
        }
        
        static boolean access$400(final Builder builder) {
            return builder.allowCredentials;
        }
        
        static long access$500(final Builder builder) {
            return builder.maxAge;
        }
        
        static Set access$600(final Builder builder) {
            return builder.requestMethods;
        }
        
        static Set access$700(final Builder builder) {
            return builder.requestHeaders;
        }
        
        static boolean access$800(final Builder builder) {
            return builder.allowNullOrigin;
        }
        
        static Map access$900(final Builder builder) {
            return builder.preflightHeaders;
        }
        
        static boolean access$1000(final Builder builder) {
            return builder.shortCurcuit;
        }
    }
}
