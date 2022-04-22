package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@Beta
@GwtCompatible(emulated = true)
public abstract class TreeTraverser
{
    public abstract Iterable children(final Object p0);
    
    public final FluentIterable preOrderTraversal(final Object o) {
        Preconditions.checkNotNull(o);
        return new FluentIterable(o) {
            final Object val$root;
            final TreeTraverser this$0;
            
            @Override
            public UnmodifiableIterator iterator() {
                return this.this$0.preOrderIterator(this.val$root);
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    UnmodifiableIterator preOrderIterator(final Object o) {
        return new PreOrderIterator(o);
    }
    
    public final FluentIterable postOrderTraversal(final Object o) {
        Preconditions.checkNotNull(o);
        return new FluentIterable(o) {
            final Object val$root;
            final TreeTraverser this$0;
            
            @Override
            public UnmodifiableIterator iterator() {
                return this.this$0.postOrderIterator(this.val$root);
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    UnmodifiableIterator postOrderIterator(final Object o) {
        return new PostOrderIterator(o);
    }
    
    public final FluentIterable breadthFirstTraversal(final Object o) {
        Preconditions.checkNotNull(o);
        return new FluentIterable(o) {
            final Object val$root;
            final TreeTraverser this$0;
            
            @Override
            public UnmodifiableIterator iterator() {
                return this.this$0.new BreadthFirstIterator(this.val$root);
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    private final class BreadthFirstIterator extends UnmodifiableIterator implements PeekingIterator
    {
        private final Queue queue;
        final TreeTraverser this$0;
        
        BreadthFirstIterator(final TreeTraverser this$0, final Object o) {
            this.this$0 = this$0;
            (this.queue = new ArrayDeque()).add(o);
        }
        
        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }
        
        @Override
        public Object peek() {
            return this.queue.element();
        }
        
        @Override
        public Object next() {
            final Object remove = this.queue.remove();
            Iterables.addAll(this.queue, this.this$0.children(remove));
            return remove;
        }
    }
    
    private final class PostOrderIterator extends AbstractIterator
    {
        private final ArrayDeque stack;
        final TreeTraverser this$0;
        
        PostOrderIterator(final TreeTraverser this$0, final Object o) {
            this.this$0 = this$0;
            (this.stack = new ArrayDeque()).addLast(this.expand(o));
        }
        
        @Override
        protected Object computeNext() {
            while (!this.stack.isEmpty()) {
                final PostOrderNode postOrderNode = this.stack.getLast();
                if (!postOrderNode.childIterator.hasNext()) {
                    this.stack.removeLast();
                    return postOrderNode.root;
                }
                this.stack.addLast(this.expand(postOrderNode.childIterator.next()));
            }
            return this.endOfData();
        }
        
        private PostOrderNode expand(final Object o) {
            return new PostOrderNode(o, this.this$0.children(o).iterator());
        }
    }
    
    private static final class PostOrderNode
    {
        final Object root;
        final Iterator childIterator;
        
        PostOrderNode(final Object o, final Iterator iterator) {
            this.root = Preconditions.checkNotNull(o);
            this.childIterator = (Iterator)Preconditions.checkNotNull(iterator);
        }
    }
    
    private final class PreOrderIterator extends UnmodifiableIterator
    {
        private final Deque stack;
        final TreeTraverser this$0;
        
        PreOrderIterator(final TreeTraverser this$0, final Object o) {
            this.this$0 = this$0;
            (this.stack = new ArrayDeque()).addLast(Iterators.singletonIterator(Preconditions.checkNotNull(o)));
        }
        
        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }
        
        @Override
        public Object next() {
            final Iterator<Object> iterator = this.stack.getLast();
            final Object checkNotNull = Preconditions.checkNotNull(iterator.next());
            if (!iterator.hasNext()) {
                this.stack.removeLast();
            }
            final Iterator iterator2 = this.this$0.children(checkNotNull).iterator();
            if (iterator2.hasNext()) {
                this.stack.addLast(iterator2);
            }
            return checkNotNull;
        }
    }
}
