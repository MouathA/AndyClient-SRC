package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@Beta
@GwtCompatible(emulated = true)
public abstract class BinaryTreeTraverser extends TreeTraverser
{
    public abstract Optional leftChild(final Object p0);
    
    public abstract Optional rightChild(final Object p0);
    
    @Override
    public final Iterable children(final Object o) {
        Preconditions.checkNotNull(o);
        return new FluentIterable(o) {
            final Object val$root;
            final BinaryTreeTraverser this$0;
            
            @Override
            public Iterator iterator() {
                return new AbstractIterator() {
                    boolean doneLeft;
                    boolean doneRight;
                    final BinaryTreeTraverser$1 this$1;
                    
                    @Override
                    protected Object computeNext() {
                        if (!this.doneLeft) {
                            this.doneLeft = true;
                            final Optional leftChild = this.this$1.this$0.leftChild(this.this$1.val$root);
                            if (leftChild.isPresent()) {
                                return leftChild.get();
                            }
                        }
                        if (!this.doneRight) {
                            this.doneRight = true;
                            final Optional rightChild = this.this$1.this$0.rightChild(this.this$1.val$root);
                            if (rightChild.isPresent()) {
                                return rightChild.get();
                            }
                        }
                        return this.endOfData();
                    }
                };
            }
        };
    }
    
    @Override
    UnmodifiableIterator preOrderIterator(final Object o) {
        return new PreOrderIterator(o);
    }
    
    @Override
    UnmodifiableIterator postOrderIterator(final Object o) {
        return new PostOrderIterator(o);
    }
    
    public final FluentIterable inOrderTraversal(final Object o) {
        Preconditions.checkNotNull(o);
        return new FluentIterable(o) {
            final Object val$root;
            final BinaryTreeTraverser this$0;
            
            @Override
            public UnmodifiableIterator iterator() {
                return this.this$0.new InOrderIterator(this.val$root);
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    private static void pushIfPresent(final Deque deque, final Optional optional) {
        if (optional.isPresent()) {
            deque.addLast(optional.get());
        }
    }
    
    static void access$000(final Deque deque, final Optional optional) {
        pushIfPresent(deque, optional);
    }
    
    private final class InOrderIterator extends AbstractIterator
    {
        private final Deque stack;
        private final BitSet hasExpandedLeft;
        final BinaryTreeTraverser this$0;
        
        InOrderIterator(final BinaryTreeTraverser this$0, final Object o) {
            this.this$0 = this$0;
            this.stack = new ArrayDeque();
            this.hasExpandedLeft = new BitSet();
            this.stack.addLast(o);
        }
        
        @Override
        protected Object computeNext() {
            while (!this.stack.isEmpty()) {
                final Object last = this.stack.getLast();
                if (this.hasExpandedLeft.get(this.stack.size() - 1)) {
                    this.stack.removeLast();
                    this.hasExpandedLeft.clear(this.stack.size());
                    BinaryTreeTraverser.access$000(this.stack, this.this$0.rightChild(last));
                    return last;
                }
                this.hasExpandedLeft.set(this.stack.size() - 1);
                BinaryTreeTraverser.access$000(this.stack, this.this$0.leftChild(last));
            }
            return this.endOfData();
        }
    }
    
    private final class PostOrderIterator extends UnmodifiableIterator
    {
        private final Deque stack;
        private final BitSet hasExpanded;
        final BinaryTreeTraverser this$0;
        
        PostOrderIterator(final BinaryTreeTraverser this$0, final Object o) {
            this.this$0 = this$0;
            (this.stack = new ArrayDeque()).addLast(o);
            this.hasExpanded = new BitSet();
        }
        
        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }
        
        @Override
        public Object next() {
            Object last;
            while (true) {
                last = this.stack.getLast();
                if (this.hasExpanded.get(this.stack.size() - 1)) {
                    break;
                }
                this.hasExpanded.set(this.stack.size() - 1);
                BinaryTreeTraverser.access$000(this.stack, this.this$0.rightChild(last));
                BinaryTreeTraverser.access$000(this.stack, this.this$0.leftChild(last));
            }
            this.stack.removeLast();
            this.hasExpanded.clear(this.stack.size());
            return last;
        }
    }
    
    private final class PreOrderIterator extends UnmodifiableIterator implements PeekingIterator
    {
        private final Deque stack;
        final BinaryTreeTraverser this$0;
        
        PreOrderIterator(final BinaryTreeTraverser this$0, final Object o) {
            this.this$0 = this$0;
            (this.stack = new ArrayDeque()).addLast(o);
        }
        
        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }
        
        @Override
        public Object next() {
            final Object removeLast = this.stack.removeLast();
            BinaryTreeTraverser.access$000(this.stack, this.this$0.rightChild(removeLast));
            BinaryTreeTraverser.access$000(this.stack, this.this$0.leftChild(removeLast));
            return removeLast;
        }
        
        @Override
        public Object peek() {
            return this.stack.getLast();
        }
    }
}
