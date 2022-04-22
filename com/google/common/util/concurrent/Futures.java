package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.lang.reflect.*;
import com.google.common.base.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import com.google.common.collect.*;
import java.util.logging.*;
import java.util.concurrent.*;

@Beta
public final class Futures
{
    private static final AsyncFunction DEREFERENCER;
    private static final Ordering WITH_STRING_PARAM_FIRST;
    
    private Futures() {
    }
    
    public static CheckedFuture makeChecked(final ListenableFuture listenableFuture, final Function function) {
        return new MappingCheckedFuture((ListenableFuture)Preconditions.checkNotNull(listenableFuture), function);
    }
    
    public static ListenableFuture immediateFuture(@Nullable final Object o) {
        return new ImmediateSuccessfulFuture(o);
    }
    
    public static CheckedFuture immediateCheckedFuture(@Nullable final Object o) {
        return new ImmediateSuccessfulCheckedFuture(o);
    }
    
    public static ListenableFuture immediateFailedFuture(final Throwable t) {
        Preconditions.checkNotNull(t);
        return new ImmediateFailedFuture(t);
    }
    
    public static ListenableFuture immediateCancelledFuture() {
        return new ImmediateCancelledFuture();
    }
    
    public static CheckedFuture immediateFailedCheckedFuture(final Exception ex) {
        Preconditions.checkNotNull(ex);
        return new ImmediateFailedCheckedFuture(ex);
    }
    
    public static ListenableFuture withFallback(final ListenableFuture listenableFuture, final FutureFallback futureFallback) {
        return withFallback(listenableFuture, futureFallback, MoreExecutors.sameThreadExecutor());
    }
    
    public static ListenableFuture withFallback(final ListenableFuture listenableFuture, final FutureFallback futureFallback, final Executor executor) {
        Preconditions.checkNotNull(futureFallback);
        return new FallbackFuture(listenableFuture, futureFallback, executor);
    }
    
    public static ListenableFuture transform(final ListenableFuture listenableFuture, final AsyncFunction asyncFunction) {
        return transform(listenableFuture, asyncFunction, MoreExecutors.sameThreadExecutor());
    }
    
    public static ListenableFuture transform(final ListenableFuture listenableFuture, final AsyncFunction asyncFunction, final Executor executor) {
        final ChainingListenableFuture chainingListenableFuture = new ChainingListenableFuture(asyncFunction, listenableFuture, null);
        listenableFuture.addListener(chainingListenableFuture, executor);
        return chainingListenableFuture;
    }
    
    public static ListenableFuture transform(final ListenableFuture listenableFuture, final Function function) {
        return transform(listenableFuture, function, MoreExecutors.sameThreadExecutor());
    }
    
    public static ListenableFuture transform(final ListenableFuture listenableFuture, final Function function, final Executor executor) {
        Preconditions.checkNotNull(function);
        return transform(listenableFuture, new AsyncFunction(function) {
            final Function val$function;
            
            @Override
            public ListenableFuture apply(final Object o) {
                return Futures.immediateFuture(this.val$function.apply(o));
            }
        }, executor);
    }
    
    public static Future lazyTransform(final Future future, final Function function) {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(function);
        return new Future(future, function) {
            final Future val$input;
            final Function val$function;
            
            @Override
            public boolean cancel(final boolean b) {
                return this.val$input.cancel(b);
            }
            
            @Override
            public boolean isCancelled() {
                return this.val$input.isCancelled();
            }
            
            @Override
            public boolean isDone() {
                return this.val$input.isDone();
            }
            
            @Override
            public Object get() throws InterruptedException, ExecutionException {
                return this.applyTransformation(this.val$input.get());
            }
            
            @Override
            public Object get(final long n, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return this.applyTransformation(this.val$input.get(n, timeUnit));
            }
            
            private Object applyTransformation(final Object o) throws ExecutionException {
                return this.val$function.apply(o);
            }
        };
    }
    
    public static ListenableFuture dereference(final ListenableFuture listenableFuture) {
        return transform(listenableFuture, Futures.DEREFERENCER);
    }
    
    @Beta
    public static ListenableFuture allAsList(final ListenableFuture... array) {
        return listFuture(ImmutableList.copyOf(array), true, MoreExecutors.sameThreadExecutor());
    }
    
    @Beta
    public static ListenableFuture allAsList(final Iterable iterable) {
        return listFuture(ImmutableList.copyOf(iterable), true, MoreExecutors.sameThreadExecutor());
    }
    
    public static ListenableFuture nonCancellationPropagating(final ListenableFuture listenableFuture) {
        return new NonCancellationPropagatingFuture(listenableFuture);
    }
    
    @Beta
    public static ListenableFuture successfulAsList(final ListenableFuture... array) {
        return listFuture(ImmutableList.copyOf(array), false, MoreExecutors.sameThreadExecutor());
    }
    
    @Beta
    public static ListenableFuture successfulAsList(final Iterable iterable) {
        return listFuture(ImmutableList.copyOf(iterable), false, MoreExecutors.sameThreadExecutor());
    }
    
    @Beta
    public static ImmutableList inCompletionOrder(final Iterable iterable) {
        final ConcurrentLinkedQueue concurrentLinkedQueue = Queues.newConcurrentLinkedQueue();
        final ImmutableList.Builder builder = ImmutableList.builder();
        final SerializingExecutor serializingExecutor = new SerializingExecutor(MoreExecutors.sameThreadExecutor());
        for (final ListenableFuture listenableFuture : iterable) {
            final AsyncSettableFuture create = AsyncSettableFuture.create();
            concurrentLinkedQueue.add(create);
            listenableFuture.addListener(new Runnable(concurrentLinkedQueue, listenableFuture) {
                final ConcurrentLinkedQueue val$delegates;
                final ListenableFuture val$future;
                
                @Override
                public void run() {
                    ((AsyncSettableFuture)this.val$delegates.remove()).setFuture(this.val$future);
                }
            }, serializingExecutor);
            builder.add((Object)create);
        }
        return builder.build();
    }
    
    public static void addCallback(final ListenableFuture listenableFuture, final FutureCallback futureCallback) {
        addCallback(listenableFuture, futureCallback, MoreExecutors.sameThreadExecutor());
    }
    
    public static void addCallback(final ListenableFuture listenableFuture, final FutureCallback futureCallback, final Executor executor) {
        Preconditions.checkNotNull(futureCallback);
        listenableFuture.addListener(new Runnable(listenableFuture, futureCallback) {
            final ListenableFuture val$future;
            final FutureCallback val$callback;
            
            @Override
            public void run() {
                this.val$callback.onSuccess(Uninterruptibles.getUninterruptibly(this.val$future));
            }
        }, executor);
    }
    
    public static Object get(final Future future, final Class clazz) throws Exception {
        Preconditions.checkNotNull(future);
        Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(clazz), "Futures.get exception type (%s) must not be a RuntimeException", clazz);
        return future.get();
    }
    
    public static Object get(final Future future, final long n, final TimeUnit timeUnit, final Class clazz) throws Exception {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(clazz), "Futures.get exception type (%s) must not be a RuntimeException", clazz);
        return future.get(n, timeUnit);
    }
    
    private static void wrapAndThrowExceptionOrError(final Throwable t, final Class clazz) throws Exception {
        if (t instanceof Error) {
            throw new ExecutionError((Error)t);
        }
        if (t instanceof RuntimeException) {
            throw new UncheckedExecutionException(t);
        }
        throw newWithCause(clazz, t);
    }
    
    public static Object getUnchecked(final Future future) {
        Preconditions.checkNotNull(future);
        return Uninterruptibles.getUninterruptibly(future);
    }
    
    private static void wrapAndThrowUnchecked(final Throwable t) {
        if (t instanceof Error) {
            throw new ExecutionError((Error)t);
        }
        throw new UncheckedExecutionException(t);
    }
    
    private static Exception newWithCause(final Class clazz, final Throwable t) {
        final Iterator<Constructor> iterator = preferringStrings(Arrays.asList(clazz.getConstructors())).iterator();
        while (iterator.hasNext()) {
            final Exception ex = (Exception)newFromConstructor(iterator.next(), t);
            if (ex != null) {
                if (ex.getCause() == null) {
                    ex.initCause(t);
                }
                return ex;
            }
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + clazz + " in response to chained exception", t);
    }
    
    private static List preferringStrings(final List list) {
        return Futures.WITH_STRING_PARAM_FIRST.sortedCopy(list);
    }
    
    @Nullable
    private static Object newFromConstructor(final Constructor constructor, final Throwable t) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Object[] array = new Object[parameterTypes.length];
        while (0 < parameterTypes.length) {
            final Class<?> clazz = parameterTypes[0];
            if (clazz.equals(String.class)) {
                array[0] = t.toString();
            }
            else {
                if (!clazz.equals(Throwable.class)) {
                    return null;
                }
                array[0] = t;
            }
            int n = 0;
            ++n;
        }
        return constructor.newInstance(array);
    }
    
    private static ListenableFuture listFuture(final ImmutableList list, final boolean b, final Executor executor) {
        return new CombinedFuture(list, b, executor, new FutureCombiner() {
            @Override
            public List combine(final List list) {
                final ArrayList arrayList = Lists.newArrayList();
                for (final Optional optional : list) {
                    arrayList.add((optional != null) ? optional.orNull() : null);
                }
                return Collections.unmodifiableList((List<?>)arrayList);
            }
            
            @Override
            public Object combine(final List list) {
                return this.combine(list);
            }
        });
    }
    
    static {
        DEREFERENCER = new AsyncFunction() {
            public ListenableFuture apply(final ListenableFuture listenableFuture) {
                return listenableFuture;
            }
            
            @Override
            public ListenableFuture apply(final Object o) throws Exception {
                return this.apply((ListenableFuture)o);
            }
        };
        WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function() {
            public Boolean apply(final Constructor constructor) {
                return Arrays.asList(constructor.getParameterTypes()).contains(String.class);
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((Constructor)o);
            }
        }).reverse();
    }
    
    private static class MappingCheckedFuture extends AbstractCheckedFuture
    {
        final Function mapper;
        
        MappingCheckedFuture(final ListenableFuture listenableFuture, final Function function) {
            super(listenableFuture);
            this.mapper = (Function)Preconditions.checkNotNull(function);
        }
        
        @Override
        protected Exception mapException(final Exception ex) {
            return (Exception)this.mapper.apply(ex);
        }
    }
    
    private static class CombinedFuture extends AbstractFuture
    {
        private static final Logger logger;
        ImmutableCollection futures;
        final boolean allMustSucceed;
        final AtomicInteger remaining;
        FutureCombiner combiner;
        List values;
        final Object seenExceptionsLock;
        Set seenExceptions;
        
        CombinedFuture(final ImmutableCollection futures, final boolean allMustSucceed, final Executor executor, final FutureCombiner combiner) {
            this.seenExceptionsLock = new Object();
            this.futures = futures;
            this.allMustSucceed = allMustSucceed;
            this.remaining = new AtomicInteger(futures.size());
            this.combiner = combiner;
            this.values = Lists.newArrayListWithCapacity(futures.size());
            this.init(executor);
        }
        
        protected void init(final Executor executor) {
            this.addListener(new Runnable() {
                final CombinedFuture this$0;
                
                @Override
                public void run() {
                    if (this.this$0.isCancelled()) {
                        final Iterator iterator = this.this$0.futures.iterator();
                        while (iterator.hasNext()) {
                            iterator.next().cancel(this.this$0.wasInterrupted());
                        }
                    }
                    this.this$0.futures = null;
                    this.this$0.values = null;
                    this.this$0.combiner = null;
                }
            }, MoreExecutors.sameThreadExecutor());
            if (this.futures.isEmpty()) {
                this.set(this.combiner.combine(ImmutableList.of()));
                return;
            }
            int n = 0;
            while (0 < this.futures.size()) {
                this.values.add(null);
                ++n;
            }
            for (final ListenableFuture listenableFuture : this.futures) {
                final int n2 = 0;
                ++n;
                listenableFuture.addListener(new Runnable(n2, listenableFuture) {
                    final int val$index;
                    final ListenableFuture val$listenable;
                    final CombinedFuture this$0;
                    
                    @Override
                    public void run() {
                        CombinedFuture.access$400(this.this$0, this.val$index, this.val$listenable);
                    }
                }, executor);
            }
        }
        
        private void setExceptionAndMaybeLog(final Throwable exception) {
            if (this.allMustSucceed) {
                super.setException(exception);
                // monitorenter(seenExceptionsLock = this.seenExceptionsLock)
                if (this.seenExceptions == null) {
                    this.seenExceptions = Sets.newHashSet();
                }
                this.seenExceptions.add(exception);
            }
            // monitorexit(seenExceptionsLock)
            if (exception instanceof Error || this.allMustSucceed) {
                CombinedFuture.logger.log(Level.SEVERE, "input future failed.", exception);
            }
        }
        
        private void setOneValue(final int n, final Future future) {
            final List values = this.values;
            if (this.isDone() || values == null) {
                Preconditions.checkState(this.allMustSucceed || this.isCancelled(), (Object)"Future was done before all dependencies completed");
            }
            Preconditions.checkState(future.isDone(), (Object)"Tried to set value from future which is not done");
            final Object uninterruptibly = Uninterruptibles.getUninterruptibly(future);
            if (values != null) {
                values.set(n, Optional.fromNullable(uninterruptibly));
            }
            final int decrementAndGet = this.remaining.decrementAndGet();
            Preconditions.checkState(decrementAndGet >= 0, (Object)"Less than 0 remaining futures");
            if (decrementAndGet == 0) {
                final FutureCombiner combiner = this.combiner;
                if (combiner != null && values != null) {
                    this.set(combiner.combine(values));
                }
                else {
                    Preconditions.checkState(this.isDone());
                }
            }
        }
        
        static void access$400(final CombinedFuture combinedFuture, final int n, final Future future) {
            combinedFuture.setOneValue(n, future);
        }
        
        static {
            logger = Logger.getLogger(CombinedFuture.class.getName());
        }
    }
    
    private interface FutureCombiner
    {
        Object combine(final List p0);
    }
    
    private static class NonCancellationPropagatingFuture extends AbstractFuture
    {
        NonCancellationPropagatingFuture(final ListenableFuture listenableFuture) {
            Preconditions.checkNotNull(listenableFuture);
            Futures.addCallback(listenableFuture, new FutureCallback(listenableFuture) {
                final ListenableFuture val$delegate;
                final NonCancellationPropagatingFuture this$0;
                
                @Override
                public void onSuccess(final Object o) {
                    this.this$0.set(o);
                }
                
                @Override
                public void onFailure(final Throwable exception) {
                    if (this.val$delegate.isCancelled()) {
                        this.this$0.cancel(false);
                    }
                    else {
                        this.this$0.setException(exception);
                    }
                }
            }, MoreExecutors.sameThreadExecutor());
        }
    }
    
    private static class ChainingListenableFuture extends AbstractFuture implements Runnable
    {
        private AsyncFunction function;
        private ListenableFuture inputFuture;
        private ListenableFuture outputFuture;
        private final CountDownLatch outputCreated;
        
        private ChainingListenableFuture(final AsyncFunction asyncFunction, final ListenableFuture listenableFuture) {
            this.outputCreated = new CountDownLatch(1);
            this.function = (AsyncFunction)Preconditions.checkNotNull(asyncFunction);
            this.inputFuture = (ListenableFuture)Preconditions.checkNotNull(listenableFuture);
        }
        
        @Override
        public boolean cancel(final boolean b) {
            if (super.cancel(b)) {
                this.cancel(this.inputFuture, b);
                this.cancel(this.outputFuture, b);
                return true;
            }
            return false;
        }
        
        private void cancel(@Nullable final Future future, final boolean b) {
            if (future != null) {
                future.cancel(b);
            }
        }
        
        @Override
        public void run() {
            final ListenableFuture outputFuture = (ListenableFuture)Preconditions.checkNotNull(this.function.apply(Uninterruptibles.getUninterruptibly(this.inputFuture)), (Object)"AsyncFunction may not return null.");
            this.outputFuture = outputFuture;
            final ListenableFuture listenableFuture = outputFuture;
            if (this.isCancelled()) {
                listenableFuture.cancel(this.wasInterrupted());
                this.outputFuture = null;
                this.function = null;
                this.inputFuture = null;
                this.outputCreated.countDown();
                return;
            }
            listenableFuture.addListener(new Runnable(listenableFuture) {
                final ListenableFuture val$outputFuture;
                final ChainingListenableFuture this$0;
                
                @Override
                public void run() {
                    this.this$0.set(Uninterruptibles.getUninterruptibly(this.val$outputFuture));
                    ChainingListenableFuture.access$302(this.this$0, null);
                }
            }, MoreExecutors.sameThreadExecutor());
            this.function = null;
            this.inputFuture = null;
            this.outputCreated.countDown();
        }
        
        ChainingListenableFuture(final AsyncFunction asyncFunction, final ListenableFuture listenableFuture, final Futures$1 asyncFunction2) {
            this(asyncFunction, listenableFuture);
        }
        
        static ListenableFuture access$302(final ChainingListenableFuture chainingListenableFuture, final ListenableFuture outputFuture) {
            return chainingListenableFuture.outputFuture = outputFuture;
        }
    }
    
    private static class FallbackFuture extends AbstractFuture
    {
        private ListenableFuture running;
        
        FallbackFuture(final ListenableFuture running, final FutureFallback futureFallback, final Executor executor) {
            Futures.addCallback(this.running = running, new FutureCallback(futureFallback) {
                final FutureFallback val$fallback;
                final FallbackFuture this$0;
                
                @Override
                public void onSuccess(final Object o) {
                    this.this$0.set(o);
                }
                
                @Override
                public void onFailure(final Throwable t) {
                    if (this.this$0.isCancelled()) {
                        return;
                    }
                    FallbackFuture.access$102(this.this$0, this.val$fallback.create(t));
                    if (this.this$0.isCancelled()) {
                        FallbackFuture.access$100(this.this$0).cancel(this.this$0.wasInterrupted());
                        return;
                    }
                    Futures.addCallback(FallbackFuture.access$100(this.this$0), new FutureCallback() {
                        final Futures$FallbackFuture$1 this$1;
                        
                        @Override
                        public void onSuccess(final Object o) {
                            this.this$1.this$0.set(o);
                        }
                        
                        @Override
                        public void onFailure(final Throwable exception) {
                            if (FallbackFuture.access$100(this.this$1.this$0).isCancelled()) {
                                this.this$1.this$0.cancel(false);
                            }
                            else {
                                this.this$1.this$0.setException(exception);
                            }
                        }
                    }, MoreExecutors.sameThreadExecutor());
                }
            }, executor);
        }
        
        @Override
        public boolean cancel(final boolean b) {
            if (super.cancel(b)) {
                this.running.cancel(b);
                return true;
            }
            return false;
        }
        
        static ListenableFuture access$102(final FallbackFuture fallbackFuture, final ListenableFuture running) {
            return fallbackFuture.running = running;
        }
        
        static ListenableFuture access$100(final FallbackFuture fallbackFuture) {
            return fallbackFuture.running;
        }
    }
    
    private static class ImmediateFailedCheckedFuture extends ImmediateFuture implements CheckedFuture
    {
        private final Exception thrown;
        
        ImmediateFailedCheckedFuture(final Exception thrown) {
            super(null);
            this.thrown = thrown;
        }
        
        @Override
        public Object get() throws ExecutionException {
            throw new ExecutionException(this.thrown);
        }
        
        @Override
        public Object checkedGet() throws Exception {
            throw this.thrown;
        }
        
        @Override
        public Object checkedGet(final long n, final TimeUnit timeUnit) throws Exception {
            Preconditions.checkNotNull(timeUnit);
            throw this.thrown;
        }
    }
    
    private abstract static class ImmediateFuture implements ListenableFuture
    {
        private static final Logger log;
        
        private ImmediateFuture() {
        }
        
        @Override
        public void addListener(final Runnable runnable, final Executor executor) {
            Preconditions.checkNotNull(runnable, (Object)"Runnable was null.");
            Preconditions.checkNotNull(executor, (Object)"Executor was null.");
            executor.execute(runnable);
        }
        
        @Override
        public boolean cancel(final boolean b) {
            return false;
        }
        
        @Override
        public abstract Object get() throws ExecutionException;
        
        @Override
        public Object get(final long n, final TimeUnit timeUnit) throws ExecutionException {
            Preconditions.checkNotNull(timeUnit);
            return this.get();
        }
        
        @Override
        public boolean isCancelled() {
            return false;
        }
        
        @Override
        public boolean isDone() {
            return true;
        }
        
        ImmediateFuture(final Futures$1 asyncFunction) {
            this();
        }
        
        static {
            log = Logger.getLogger(ImmediateFuture.class.getName());
        }
    }
    
    private static class ImmediateCancelledFuture extends ImmediateFuture
    {
        private final CancellationException thrown;
        
        ImmediateCancelledFuture() {
            super(null);
            this.thrown = new CancellationException("Immediate cancelled future.");
        }
        
        @Override
        public boolean isCancelled() {
            return true;
        }
        
        @Override
        public Object get() {
            throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.thrown);
        }
    }
    
    private static class ImmediateFailedFuture extends ImmediateFuture
    {
        private final Throwable thrown;
        
        ImmediateFailedFuture(final Throwable thrown) {
            super(null);
            this.thrown = thrown;
        }
        
        @Override
        public Object get() throws ExecutionException {
            throw new ExecutionException(this.thrown);
        }
    }
    
    private static class ImmediateSuccessfulCheckedFuture extends ImmediateFuture implements CheckedFuture
    {
        @Nullable
        private final Object value;
        
        ImmediateSuccessfulCheckedFuture(@Nullable final Object value) {
            super(null);
            this.value = value;
        }
        
        @Override
        public Object get() {
            return this.value;
        }
        
        @Override
        public Object checkedGet() {
            return this.value;
        }
        
        @Override
        public Object checkedGet(final long n, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            return this.value;
        }
    }
    
    private static class ImmediateSuccessfulFuture extends ImmediateFuture
    {
        @Nullable
        private final Object value;
        
        ImmediateSuccessfulFuture(@Nullable final Object value) {
            super(null);
            this.value = value;
        }
        
        @Override
        public Object get() {
            return this.value;
        }
    }
}
