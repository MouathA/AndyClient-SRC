package io.netty.buffer;

import java.nio.*;
import java.util.*;
import java.io.*;
import io.netty.util.internal.*;
import java.nio.channels.*;
import io.netty.util.*;

public class CompositeByteBuf extends AbstractReferenceCountedByteBuf
{
    private final ResourceLeak leak;
    private final ByteBufAllocator alloc;
    private final boolean direct;
    private final List components;
    private final int maxNumComponents;
    private static final ByteBuffer FULL_BYTEBUFFER;
    private boolean freed;
    
    public CompositeByteBuf(final ByteBufAllocator alloc, final boolean direct, final int maxNumComponents) {
        super(Integer.MAX_VALUE);
        this.components = new ArrayList();
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        this.alloc = alloc;
        this.direct = direct;
        this.maxNumComponents = maxNumComponents;
        this.leak = CompositeByteBuf.leakDetector.open(this);
    }
    
    public CompositeByteBuf(final ByteBufAllocator alloc, final boolean direct, final int maxNumComponents, final ByteBuf... array) {
        super(Integer.MAX_VALUE);
        this.components = new ArrayList();
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (maxNumComponents < 2) {
            throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
        }
        this.alloc = alloc;
        this.direct = direct;
        this.maxNumComponents = maxNumComponents;
        this.addComponents0(0, array);
        this.consolidateIfNeeded();
        this.setIndex(0, this.capacity());
        this.leak = CompositeByteBuf.leakDetector.open(this);
    }
    
    public CompositeByteBuf(final ByteBufAllocator alloc, final boolean direct, final int maxNumComponents, final Iterable iterable) {
        super(Integer.MAX_VALUE);
        this.components = new ArrayList();
        if (alloc == null) {
            throw new NullPointerException("alloc");
        }
        if (maxNumComponents < 2) {
            throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents + " (expected: >= 2)");
        }
        this.alloc = alloc;
        this.direct = direct;
        this.maxNumComponents = maxNumComponents;
        this.addComponents0(0, iterable);
        this.consolidateIfNeeded();
        this.setIndex(0, this.capacity());
        this.leak = CompositeByteBuf.leakDetector.open(this);
    }
    
    public CompositeByteBuf addComponent(final ByteBuf byteBuf) {
        this.addComponent0(this.components.size(), byteBuf);
        this.consolidateIfNeeded();
        return this;
    }
    
    public CompositeByteBuf addComponents(final ByteBuf... array) {
        this.addComponents0(this.components.size(), array);
        this.consolidateIfNeeded();
        return this;
    }
    
    public CompositeByteBuf addComponents(final Iterable iterable) {
        this.addComponents0(this.components.size(), iterable);
        this.consolidateIfNeeded();
        return this;
    }
    
    public CompositeByteBuf addComponent(final int n, final ByteBuf byteBuf) {
        this.addComponent0(n, byteBuf);
        this.consolidateIfNeeded();
        return this;
    }
    
    private int addComponent0(final int n, final ByteBuf byteBuf) {
        this.checkComponentIndex(n);
        if (byteBuf == null) {
            throw new NullPointerException("buffer");
        }
        final int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            return n;
        }
        final Component component = new Component(byteBuf.order(ByteOrder.BIG_ENDIAN).slice());
        if (n == this.components.size()) {
            this.components.add(component);
            if (n == 0) {
                component.endOffset = readableBytes;
            }
            else {
                component.offset = this.components.get(n - 1).endOffset;
                component.endOffset = component.offset + readableBytes;
            }
        }
        else {
            this.components.add(n, component);
            this.updateComponentOffsets(n);
        }
        return n;
    }
    
    public CompositeByteBuf addComponents(final int n, final ByteBuf... array) {
        this.addComponents0(n, array);
        this.consolidateIfNeeded();
        return this;
    }
    
    private int addComponents0(int n, final ByteBuf... array) {
        this.checkComponentIndex(n);
        if (array == null) {
            throw new NullPointerException("buffers");
        }
        int n3 = 0;
        while (0 < array.length) {
            final ByteBuf byteBuf = array[0];
            if (byteBuf == null) {
                break;
            }
            final int n2 = 0 + byteBuf.readableBytes();
            ++n3;
        }
        if (!false) {
            return n;
        }
        while (0 < array.length) {
            final ByteBuf byteBuf2 = array[0];
            if (byteBuf2 == null) {
                break;
            }
            if (byteBuf2.isReadable()) {
                n = this.addComponent0(n, byteBuf2) + 1;
                final int size = this.components.size();
                if (n > size) {
                    n = size;
                }
            }
            else {
                byteBuf2.release();
            }
            ++n3;
        }
        return n;
    }
    
    public CompositeByteBuf addComponents(final int n, final Iterable iterable) {
        this.addComponents0(n, iterable);
        this.consolidateIfNeeded();
        return this;
    }
    
    private int addComponents0(final int n, Iterable list) {
        if (list == null) {
            throw new NullPointerException("buffers");
        }
        if (list instanceof ByteBuf) {
            return this.addComponent0(n, (ByteBuf)list);
        }
        if (!(list instanceof Collection)) {
            final ArrayList<Object> list2 = new ArrayList<Object>();
            final Iterator<ByteBuf> iterator = list.iterator();
            while (iterator.hasNext()) {
                list2.add(iterator.next());
            }
            list = (List<ByteBuf>)list2;
        }
        final ArrayList<ByteBuf> list3 = (ArrayList<ByteBuf>)list;
        return this.addComponents0(n, (ByteBuf[])list3.toArray(new ByteBuf[list3.size()]));
    }
    
    private void consolidateIfNeeded() {
        final int size = this.components.size();
        if (size > this.maxNumComponents) {
            final ByteBuf allocBuffer = this.allocBuffer(this.components.get(size - 1).endOffset);
            while (0 < size) {
                final Component component = this.components.get(0);
                allocBuffer.writeBytes(component.buf);
                component.freeIfNecessary();
                int n = 0;
                ++n;
            }
            final Component component2 = new Component(allocBuffer);
            component2.endOffset = component2.length;
            this.components.clear();
            this.components.add(component2);
        }
    }
    
    private void checkComponentIndex(final int n) {
        this.ensureAccessible();
        if (n < 0 || n > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", n, this.components.size()));
        }
    }
    
    private void checkComponentIndex(final int n, final int n2) {
        this.ensureAccessible();
        if (n < 0 || n + n2 > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", n, n2, this.components.size()));
        }
    }
    
    private void updateComponentOffsets(int n) {
        final int size = this.components.size();
        if (size <= n) {
            return;
        }
        final Component component = this.components.get(n);
        if (n == 0) {
            component.offset = 0;
            component.endOffset = component.length;
            ++n;
        }
        for (int i = n; i < size; ++i) {
            final Component component2 = this.components.get(i - 1);
            final Component component3 = this.components.get(i);
            component3.offset = component2.endOffset;
            component3.endOffset = component3.offset + component3.length;
        }
    }
    
    public CompositeByteBuf removeComponent(final int n) {
        this.checkComponentIndex(n);
        this.components.remove(n).freeIfNecessary();
        this.updateComponentOffsets(n);
        return this;
    }
    
    public CompositeByteBuf removeComponents(final int n, final int n2) {
        this.checkComponentIndex(n, n2);
        final List<Component> subList = (List<Component>)this.components.subList(n, n + n2);
        final Iterator<Component> iterator = subList.iterator();
        while (iterator.hasNext()) {
            iterator.next().freeIfNecessary();
        }
        subList.clear();
        this.updateComponentOffsets(n);
        return this;
    }
    
    public Iterator iterator() {
        this.ensureAccessible();
        final ArrayList<Object> list = new ArrayList<Object>(this.components.size());
        final Iterator<Component> iterator = this.components.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().buf);
        }
        return list.iterator();
    }
    
    public List decompose(final int n, final int n2) {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return Collections.emptyList();
        }
        int componentIndex = this.toComponentIndex(n);
        final ArrayList<ByteBuf> list = new ArrayList<ByteBuf>(this.components.size());
        final Component component = this.components.get(componentIndex);
        final ByteBuf duplicate = component.buf.duplicate();
        duplicate.readerIndex(n - component.offset);
        ByteBuf duplicate2 = duplicate;
        int i = n2;
        int readableBytes;
        do {
            readableBytes = duplicate2.readableBytes();
            if (i <= 0) {
                duplicate2.writerIndex(duplicate2.readerIndex() + i);
                list.add(duplicate2);
                break;
            }
            list.add(duplicate2);
            i -= 0;
            ++componentIndex;
            duplicate2 = this.components.get(componentIndex).buf.duplicate();
        } while (i > 0);
        while (0 < list.size()) {
            list.set(0, ((ByteBuf)list.get(0)).slice());
            ++readableBytes;
        }
        return list;
    }
    
    @Override
    public boolean isDirect() {
        final int size = this.components.size();
        if (size == 0) {
            return false;
        }
        while (0 < size) {
            if (!this.components.get(0).buf.isDirect()) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public boolean hasArray() {
        return this.components.size() == 1 && this.components.get(0).buf.hasArray();
    }
    
    @Override
    public byte[] array() {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.array();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int arrayOffset() {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.arrayOffset();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return this.components.size() == 1 && this.components.get(0).buf.hasMemoryAddress();
    }
    
    @Override
    public long memoryAddress() {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.memoryAddress();
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int capacity() {
        if (this.components.isEmpty()) {
            return 0;
        }
        return this.components.get(this.components.size() - 1).endOffset;
    }
    
    @Override
    public CompositeByteBuf capacity(final int n) {
        this.ensureAccessible();
        if (n < 0 || n > this.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + n);
        }
        final int capacity = this.capacity();
        if (n > capacity) {
            final int n2 = n - capacity;
            if (this.components.size() < this.maxNumComponents) {
                final ByteBuf allocBuffer = this.allocBuffer(n2);
                allocBuffer.setIndex(0, n2);
                this.addComponent0(this.components.size(), allocBuffer);
            }
            else {
                final ByteBuf allocBuffer2 = this.allocBuffer(n2);
                allocBuffer2.setIndex(0, n2);
                this.addComponent0(this.components.size(), allocBuffer2);
                this.consolidateIfNeeded();
            }
        }
        else if (n < capacity) {
            int n3 = capacity - n;
            final ListIterator<Component> listIterator = (ListIterator<Component>)this.components.listIterator(this.components.size());
            while (listIterator.hasPrevious()) {
                final Component component = listIterator.previous();
                if (n3 < component.length) {
                    final Component component2 = new Component(component.buf.slice(0, component.length - n3));
                    component2.offset = component.offset;
                    component2.endOffset = component2.offset + component2.length;
                    listIterator.set(component2);
                    break;
                }
                n3 -= component.length;
                listIterator.remove();
            }
            if (this.readerIndex() > n) {
                this.setIndex(n, n);
            }
            else if (this.writerIndex() > n) {
                this.writerIndex(n);
            }
        }
        return this;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.alloc;
    }
    
    @Override
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }
    
    public int numComponents() {
        return this.components.size();
    }
    
    public int maxNumComponents() {
        return this.maxNumComponents;
    }
    
    public int toComponentIndex(final int n) {
        this.checkIndex(n);
        int size = this.components.size();
        while (0 <= size) {
            final int n2 = 0 + size >>> 1;
            final Component component = this.components.get(n2);
            if (n >= component.endOffset) {
                continue;
            }
            if (n >= component.offset) {
                return n2;
            }
            size = n2 - 1;
        }
        throw new Error("should not reach here");
    }
    
    public int toByteIndex(final int n) {
        this.checkComponentIndex(n);
        return this.components.get(n).offset;
    }
    
    @Override
    public byte getByte(final int n) {
        return this._getByte(n);
    }
    
    @Override
    protected byte _getByte(final int n) {
        final Component component = this.findComponent(n);
        return component.buf.getByte(n - component.offset);
    }
    
    @Override
    protected short _getShort(final int n) {
        final Component component = this.findComponent(n);
        if (n + 2 <= component.endOffset) {
            return component.buf.getShort(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (short)((this._getByte(n) & 0xFF) << 8 | (this._getByte(n + 1) & 0xFF));
        }
        return (short)((this._getByte(n) & 0xFF) | (this._getByte(n + 1) & 0xFF) << 8);
    }
    
    @Override
    protected int _getUnsignedMedium(final int n) {
        final Component component = this.findComponent(n);
        if (n + 3 <= component.endOffset) {
            return component.buf.getUnsignedMedium(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(n) & 0xFFFF) << 8 | (this._getByte(n + 2) & 0xFF);
        }
        return (this._getShort(n) & 0xFFFF) | (this._getByte(n + 2) & 0xFF) << 16;
    }
    
    @Override
    protected int _getInt(final int n) {
        final Component component = this.findComponent(n);
        if (n + 4 <= component.endOffset) {
            return component.buf.getInt(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(n) & 0xFFFF) << 16 | (this._getShort(n + 2) & 0xFFFF);
        }
        return (this._getShort(n) & 0xFFFF) | (this._getShort(n + 2) & 0xFFFF) << 16;
    }
    
    @Override
    protected long _getLong(final int n) {
        final Component component = this.findComponent(n);
        if (n + 8 <= component.endOffset) {
            return component.buf.getLong(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return ((long)this._getInt(n) & 0xFFFFFFFFL) << 32 | ((long)this._getInt(n + 4) & 0xFFFFFFFFL);
        }
        return ((long)this._getInt(n) & 0xFFFFFFFFL) | ((long)this._getInt(n + 4) & 0xFFFFFFFFL) << 32;
    }
    
    @Override
    public CompositeByteBuf getBytes(int n, final byte[] array, int n2, int i) {
        this.checkDstIndex(n, i, n2, array.length);
        if (i == 0) {
            return this;
        }
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            buf.getBytes(n - offset, array, n2, min);
            n += min;
            n2 += min;
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf getBytes(int n, final ByteBuffer byteBuffer) {
        final int limit = byteBuffer.limit();
        int i = byteBuffer.remaining();
        this.checkIndex(n, i);
        if (i == 0) {
            return this;
        }
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            byteBuffer.limit(byteBuffer.position() + min);
            buf.getBytes(n - offset, byteBuffer);
            n += min;
        }
        byteBuffer.limit(limit);
        return this;
    }
    
    @Override
    public CompositeByteBuf getBytes(int n, final ByteBuf byteBuf, int n2, int i) {
        this.checkDstIndex(n, i, n2, byteBuf.capacity());
        if (i == 0) {
            return this;
        }
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            buf.getBytes(n - offset, byteBuf, n2, min);
            n += min;
            n2 += min;
        }
        return this;
    }
    
    @Override
    public int getBytes(final int n, final GatheringByteChannel gatheringByteChannel, final int n2) throws IOException {
        if (this.nioBufferCount() == 1) {
            return gatheringByteChannel.write(this.internalNioBuffer(n, n2));
        }
        final long write = gatheringByteChannel.write(this.nioBuffers(n, n2));
        if (write > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int)write;
    }
    
    @Override
    public CompositeByteBuf getBytes(int n, final OutputStream outputStream, int i) throws IOException {
        this.checkIndex(n, i);
        if (i == 0) {
            return this;
        }
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            buf.getBytes(n - offset, outputStream, min);
            n += min;
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf setByte(final int n, final int n2) {
        final Component component = this.findComponent(n);
        component.buf.setByte(n - component.offset, n2);
        return this;
    }
    
    @Override
    protected void _setByte(final int n, final int n2) {
        this.setByte(n, n2);
    }
    
    @Override
    public CompositeByteBuf setShort(final int n, final int n2) {
        return (CompositeByteBuf)super.setShort(n, n2);
    }
    
    @Override
    protected void _setShort(final int n, final int n2) {
        final Component component = this.findComponent(n);
        if (n + 2 <= component.endOffset) {
            component.buf.setShort(n - component.offset, n2);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setByte(n, (byte)(n2 >>> 8));
            this._setByte(n + 1, (byte)n2);
        }
        else {
            this._setByte(n, (byte)n2);
            this._setByte(n + 1, (byte)(n2 >>> 8));
        }
    }
    
    @Override
    public CompositeByteBuf setMedium(final int n, final int n2) {
        return (CompositeByteBuf)super.setMedium(n, n2);
    }
    
    @Override
    protected void _setMedium(final int n, final int n2) {
        final Component component = this.findComponent(n);
        if (n + 3 <= component.endOffset) {
            component.buf.setMedium(n - component.offset, n2);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShort(n, (short)(n2 >> 8));
            this._setByte(n + 2, (byte)n2);
        }
        else {
            this._setShort(n, (short)n2);
            this._setByte(n + 2, (byte)(n2 >>> 16));
        }
    }
    
    @Override
    public CompositeByteBuf setInt(final int n, final int n2) {
        return (CompositeByteBuf)super.setInt(n, n2);
    }
    
    @Override
    protected void _setInt(final int n, final int n2) {
        final Component component = this.findComponent(n);
        if (n + 4 <= component.endOffset) {
            component.buf.setInt(n - component.offset, n2);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShort(n, (short)(n2 >>> 16));
            this._setShort(n + 2, (short)n2);
        }
        else {
            this._setShort(n, (short)n2);
            this._setShort(n + 2, (short)(n2 >>> 16));
        }
    }
    
    @Override
    public CompositeByteBuf setLong(final int n, final long n2) {
        return (CompositeByteBuf)super.setLong(n, n2);
    }
    
    @Override
    protected void _setLong(final int n, final long n2) {
        final Component component = this.findComponent(n);
        if (n + 8 <= component.endOffset) {
            component.buf.setLong(n - component.offset, n2);
        }
        else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setInt(n, (int)(n2 >>> 32));
            this._setInt(n + 4, (int)n2);
        }
        else {
            this._setInt(n, (int)n2);
            this._setInt(n + 4, (int)(n2 >>> 32));
        }
    }
    
    @Override
    public CompositeByteBuf setBytes(int n, final byte[] array, int n2, int i) {
        this.checkSrcIndex(n, i, n2, array.length);
        if (i == 0) {
            return this;
        }
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            buf.setBytes(n - offset, array, n2, min);
            n += min;
            n2 += min;
        }
        return this;
    }
    
    @Override
    public CompositeByteBuf setBytes(int n, final ByteBuffer byteBuffer) {
        final int limit = byteBuffer.limit();
        int i = byteBuffer.remaining();
        this.checkIndex(n, i);
        if (i == 0) {
            return this;
        }
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            byteBuffer.limit(byteBuffer.position() + min);
            buf.setBytes(n - offset, byteBuffer);
            n += min;
        }
        byteBuffer.limit(limit);
        return this;
    }
    
    @Override
    public CompositeByteBuf setBytes(int n, final ByteBuf byteBuf, int n2, int i) {
        this.checkSrcIndex(n, i, n2, byteBuf.capacity());
        if (i == 0) {
            return this;
        }
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            buf.setBytes(n - offset, byteBuf, n2, min);
            n += min;
            n2 += min;
        }
        return this;
    }
    
    @Override
    public int setBytes(int n, final InputStream inputStream, int i) throws IOException {
        this.checkIndex(n, i);
        if (i == 0) {
            return inputStream.read(EmptyArrays.EMPTY_BYTES);
        }
        int componentIndex = this.toComponentIndex(n);
        do {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            final int min = Math.min(i, buf.capacity() - (n - offset));
            final int setBytes = buf.setBytes(n - offset, inputStream, min);
            if (setBytes < 0) {
                if (!false) {
                    return -1;
                }
                break;
            }
            else if (setBytes == min) {
                n += min;
                i -= min;
                ++componentIndex;
            }
            else {
                n += setBytes;
                i -= setBytes;
            }
        } while (i > 0);
        return 0;
    }
    
    @Override
    public int setBytes(int n, final ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        this.checkIndex(n, i);
        if (i == 0) {
            return scatteringByteChannel.read(CompositeByteBuf.FULL_BYTEBUFFER);
        }
        int componentIndex = this.toComponentIndex(n);
        do {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            final int min = Math.min(i, buf.capacity() - (n - offset));
            final int setBytes = buf.setBytes(n - offset, scatteringByteChannel, min);
            if (setBytes == 0) {
                break;
            }
            if (setBytes < 0) {
                if (!false) {
                    return -1;
                }
                break;
            }
            else if (setBytes == min) {
                n += min;
                i -= min;
                ++componentIndex;
            }
            else {
                n += setBytes;
                i -= setBytes;
            }
        } while (i > 0);
        return 0;
    }
    
    @Override
    public ByteBuf copy(final int n, final int n2) {
        this.checkIndex(n, n2);
        final ByteBuf buffer = Unpooled.buffer(n2);
        if (n2 != 0) {
            this.copyTo(n, n2, this.toComponentIndex(n), buffer);
        }
        return buffer;
    }
    
    private void copyTo(int n, int i, final int n2, final ByteBuf byteBuf) {
        int min;
        for (int n3 = n2; i > 0; i -= min, ++n3) {
            final Component component = this.components.get(n3);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            buf.getBytes(n - offset, byteBuf, 0, min);
            n += min;
        }
        byteBuf.writerIndex(byteBuf.capacity());
    }
    
    public ByteBuf component(final int n) {
        return this.internalComponent(n).duplicate();
    }
    
    public ByteBuf componentAtOffset(final int n) {
        return this.internalComponentAtOffset(n).duplicate();
    }
    
    public ByteBuf internalComponent(final int n) {
        this.checkComponentIndex(n);
        return this.components.get(n).buf;
    }
    
    public ByteBuf internalComponentAtOffset(final int n) {
        return this.findComponent(n).buf;
    }
    
    private Component findComponent(final int n) {
        this.checkIndex(n);
        int size = this.components.size();
        while (0 <= size) {
            final int n2 = 0 + size >>> 1;
            final Component component = this.components.get(n2);
            if (n >= component.endOffset) {
                continue;
            }
            if (n >= component.offset) {
                return component;
            }
            size = n2 - 1;
        }
        throw new Error("should not reach here");
    }
    
    @Override
    public int nioBufferCount() {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.nioBufferCount();
        }
        while (0 < this.components.size()) {
            final int n = 0 + this.components.get(0).buf.nioBufferCount();
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int n, final int n2) {
        if (this.components.size() == 1) {
            return this.components.get(0).buf.internalNioBuffer(n, n2);
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ByteBuffer nioBuffer(final int n, final int n2) {
        if (this.components.size() == 1 && this.components.get(0).buf.nioBufferCount() == 1) {
            return this.components.get(0).buf.nioBuffer(n, n2);
        }
        final ByteBuffer order = ByteBuffer.allocate(n2).order(this.order());
        final ByteBuffer[] nioBuffers = this.nioBuffers(n, n2);
        while (0 < nioBuffers.length) {
            order.put(nioBuffers[0]);
            int n3 = 0;
            ++n3;
        }
        order.flip();
        return order;
    }
    
    @Override
    public ByteBuffer[] nioBuffers(int n, int i) {
        this.checkIndex(n, i);
        if (i == 0) {
            return EmptyArrays.EMPTY_BYTE_BUFFERS;
        }
        final ArrayList<Object> list = new ArrayList<Object>(this.components.size());
        int min;
        for (int componentIndex = this.toComponentIndex(n); i > 0; i -= min, ++componentIndex) {
            final Component component = this.components.get(componentIndex);
            final ByteBuf buf = component.buf;
            final int offset = component.offset;
            min = Math.min(i, buf.capacity() - (n - offset));
            switch (buf.nioBufferCount()) {
                case 0: {
                    throw new UnsupportedOperationException();
                }
                case 1: {
                    list.add(buf.nioBuffer(n - offset, min));
                    break;
                }
                default: {
                    Collections.addAll(list, buf.nioBuffers(n - offset, min));
                    break;
                }
            }
            n += min;
        }
        return list.toArray(new ByteBuffer[list.size()]);
    }
    
    public CompositeByteBuf consolidate() {
        this.ensureAccessible();
        final int numComponents = this.numComponents();
        if (numComponents <= 1) {
            return this;
        }
        final ByteBuf allocBuffer = this.allocBuffer(this.components.get(numComponents - 1).endOffset);
        while (0 < numComponents) {
            final Component component = this.components.get(0);
            allocBuffer.writeBytes(component.buf);
            component.freeIfNecessary();
            int n = 0;
            ++n;
        }
        this.components.clear();
        this.components.add(new Component(allocBuffer));
        this.updateComponentOffsets(0);
        return this;
    }
    
    public CompositeByteBuf consolidate(final int n, final int n2) {
        this.checkComponentIndex(n, n2);
        if (n2 <= 1) {
            return this;
        }
        final int n3 = n + n2;
        final ByteBuf allocBuffer = this.allocBuffer(this.components.get(n3 - 1).endOffset - this.components.get(n).offset);
        for (int i = n; i < n3; ++i) {
            final Component component = this.components.get(i);
            allocBuffer.writeBytes(component.buf);
            component.freeIfNecessary();
        }
        this.components.subList(n + 1, n3).clear();
        this.components.set(n, new Component(allocBuffer));
        this.updateComponentOffsets(n);
        return this;
    }
    
    public CompositeByteBuf discardReadComponents() {
        this.ensureAccessible();
        final int readerIndex = this.readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        final int writerIndex = this.writerIndex();
        if (readerIndex == writerIndex && writerIndex == this.capacity()) {
            final Iterator<Component> iterator = this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().freeIfNecessary();
            }
            this.components.clear();
            this.setIndex(0, 0);
            this.adjustMarkers(readerIndex);
            return this;
        }
        final int componentIndex = this.toComponentIndex(readerIndex);
        while (0 < componentIndex) {
            this.components.get(0).freeIfNecessary();
            int n = 0;
            ++n;
        }
        this.components.subList(0, componentIndex).clear();
        final int offset = this.components.get(0).offset;
        this.updateComponentOffsets(0);
        this.setIndex(readerIndex - offset, writerIndex - offset);
        this.adjustMarkers(offset);
        return this;
    }
    
    @Override
    public CompositeByteBuf discardReadBytes() {
        this.ensureAccessible();
        final int readerIndex = this.readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        final int writerIndex = this.writerIndex();
        if (readerIndex == writerIndex && writerIndex == this.capacity()) {
            final Iterator<Component> iterator = this.components.iterator();
            while (iterator.hasNext()) {
                iterator.next().freeIfNecessary();
            }
            this.components.clear();
            this.setIndex(0, 0);
            this.adjustMarkers(readerIndex);
            return this;
        }
        final int componentIndex = this.toComponentIndex(readerIndex);
        while (0 < componentIndex) {
            this.components.get(0).freeIfNecessary();
            int n = 0;
            ++n;
        }
        this.components.subList(0, componentIndex).clear();
        final Component component = this.components.get(0);
        final int n2 = readerIndex - component.offset;
        if (n2 == component.length) {
            this.components.remove(0);
        }
        else {
            this.components.set(0, new Component(component.buf.slice(n2, component.length - n2)));
        }
        this.updateComponentOffsets(0);
        this.setIndex(0, writerIndex - readerIndex);
        this.adjustMarkers(readerIndex);
        return this;
    }
    
    private ByteBuf allocBuffer(final int n) {
        if (this.direct) {
            return this.alloc().directBuffer(n);
        }
        return this.alloc().heapBuffer(n);
    }
    
    @Override
    public String toString() {
        final String string = super.toString();
        return string.substring(0, string.length() - 1) + ", components=" + this.components.size() + ')';
    }
    
    @Override
    public CompositeByteBuf readerIndex(final int n) {
        return (CompositeByteBuf)super.readerIndex(n);
    }
    
    @Override
    public CompositeByteBuf writerIndex(final int n) {
        return (CompositeByteBuf)super.writerIndex(n);
    }
    
    @Override
    public CompositeByteBuf setIndex(final int n, final int n2) {
        return (CompositeByteBuf)super.setIndex(n, n2);
    }
    
    @Override
    public CompositeByteBuf clear() {
        return (CompositeByteBuf)super.clear();
    }
    
    @Override
    public CompositeByteBuf markReaderIndex() {
        return (CompositeByteBuf)super.markReaderIndex();
    }
    
    @Override
    public CompositeByteBuf resetReaderIndex() {
        return (CompositeByteBuf)super.resetReaderIndex();
    }
    
    @Override
    public CompositeByteBuf markWriterIndex() {
        return (CompositeByteBuf)super.markWriterIndex();
    }
    
    @Override
    public CompositeByteBuf resetWriterIndex() {
        return (CompositeByteBuf)super.resetWriterIndex();
    }
    
    @Override
    public CompositeByteBuf ensureWritable(final int n) {
        return (CompositeByteBuf)super.ensureWritable(n);
    }
    
    @Override
    public CompositeByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        return (CompositeByteBuf)super.getBytes(n, byteBuf);
    }
    
    @Override
    public CompositeByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return (CompositeByteBuf)super.getBytes(n, byteBuf, n2);
    }
    
    @Override
    public CompositeByteBuf getBytes(final int n, final byte[] array) {
        return (CompositeByteBuf)super.getBytes(n, array);
    }
    
    @Override
    public CompositeByteBuf setBoolean(final int n, final boolean b) {
        return (CompositeByteBuf)super.setBoolean(n, b);
    }
    
    @Override
    public CompositeByteBuf setChar(final int n, final int n2) {
        return (CompositeByteBuf)super.setChar(n, n2);
    }
    
    @Override
    public CompositeByteBuf setFloat(final int n, final float n2) {
        return (CompositeByteBuf)super.setFloat(n, n2);
    }
    
    @Override
    public CompositeByteBuf setDouble(final int n, final double n2) {
        return (CompositeByteBuf)super.setDouble(n, n2);
    }
    
    @Override
    public CompositeByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        return (CompositeByteBuf)super.setBytes(n, byteBuf);
    }
    
    @Override
    public CompositeByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return (CompositeByteBuf)super.setBytes(n, byteBuf, n2);
    }
    
    @Override
    public CompositeByteBuf setBytes(final int n, final byte[] array) {
        return (CompositeByteBuf)super.setBytes(n, array);
    }
    
    @Override
    public CompositeByteBuf setZero(final int n, final int n2) {
        return (CompositeByteBuf)super.setZero(n, n2);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuf byteBuf) {
        return (CompositeByteBuf)super.readBytes(byteBuf);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        return (CompositeByteBuf)super.readBytes(byteBuf, n);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return (CompositeByteBuf)super.readBytes(byteBuf, n, n2);
    }
    
    @Override
    public CompositeByteBuf readBytes(final byte[] array) {
        return (CompositeByteBuf)super.readBytes(array);
    }
    
    @Override
    public CompositeByteBuf readBytes(final byte[] array, final int n, final int n2) {
        return (CompositeByteBuf)super.readBytes(array, n, n2);
    }
    
    @Override
    public CompositeByteBuf readBytes(final ByteBuffer byteBuffer) {
        return (CompositeByteBuf)super.readBytes(byteBuffer);
    }
    
    @Override
    public CompositeByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        return (CompositeByteBuf)super.readBytes(outputStream, n);
    }
    
    @Override
    public CompositeByteBuf skipBytes(final int n) {
        return (CompositeByteBuf)super.skipBytes(n);
    }
    
    @Override
    public CompositeByteBuf writeBoolean(final boolean b) {
        return (CompositeByteBuf)super.writeBoolean(b);
    }
    
    @Override
    public CompositeByteBuf writeByte(final int n) {
        return (CompositeByteBuf)super.writeByte(n);
    }
    
    @Override
    public CompositeByteBuf writeShort(final int n) {
        return (CompositeByteBuf)super.writeShort(n);
    }
    
    @Override
    public CompositeByteBuf writeMedium(final int n) {
        return (CompositeByteBuf)super.writeMedium(n);
    }
    
    @Override
    public CompositeByteBuf writeInt(final int n) {
        return (CompositeByteBuf)super.writeInt(n);
    }
    
    @Override
    public CompositeByteBuf writeLong(final long n) {
        return (CompositeByteBuf)super.writeLong(n);
    }
    
    @Override
    public CompositeByteBuf writeChar(final int n) {
        return (CompositeByteBuf)super.writeChar(n);
    }
    
    @Override
    public CompositeByteBuf writeFloat(final float n) {
        return (CompositeByteBuf)super.writeFloat(n);
    }
    
    @Override
    public CompositeByteBuf writeDouble(final double n) {
        return (CompositeByteBuf)super.writeDouble(n);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuf byteBuf) {
        return (CompositeByteBuf)super.writeBytes(byteBuf);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        return (CompositeByteBuf)super.writeBytes(byteBuf, n);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return (CompositeByteBuf)super.writeBytes(byteBuf, n, n2);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final byte[] array) {
        return (CompositeByteBuf)super.writeBytes(array);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        return (CompositeByteBuf)super.writeBytes(array, n, n2);
    }
    
    @Override
    public CompositeByteBuf writeBytes(final ByteBuffer byteBuffer) {
        return (CompositeByteBuf)super.writeBytes(byteBuffer);
    }
    
    @Override
    public CompositeByteBuf writeZero(final int n) {
        return (CompositeByteBuf)super.writeZero(n);
    }
    
    @Override
    public CompositeByteBuf retain(final int n) {
        return (CompositeByteBuf)super.retain(n);
    }
    
    @Override
    public CompositeByteBuf retain() {
        return (CompositeByteBuf)super.retain();
    }
    
    @Override
    public ByteBuffer[] nioBuffers() {
        return this.nioBuffers(this.readerIndex(), this.readableBytes());
    }
    
    @Override
    public CompositeByteBuf discardSomeReadBytes() {
        return this.discardReadComponents();
    }
    
    @Override
    protected void deallocate() {
        if (this.freed) {
            return;
        }
        this.freed = true;
        while (0 < this.components.size()) {
            this.components.get(0).freeIfNecessary();
            int n = 0;
            ++n;
        }
        if (this.leak != null) {
            this.leak.close();
        }
    }
    
    @Override
    public ByteBuf unwrap() {
        return null;
    }
    
    @Override
    public ByteBuf retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ByteBuf retain() {
        return this.retain();
    }
    
    @Override
    public ByteBuf writeZero(final int n) {
        return this.writeZero(n);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuffer byteBuffer) {
        return this.writeBytes(byteBuffer);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.writeBytes(byteBuf, n, n2);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf, final int n) {
        return this.writeBytes(byteBuf, n);
    }
    
    @Override
    public ByteBuf writeBytes(final ByteBuf byteBuf) {
        return this.writeBytes(byteBuf);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array) {
        return this.writeBytes(array);
    }
    
    @Override
    public ByteBuf writeBytes(final byte[] array, final int n, final int n2) {
        return this.writeBytes(array, n, n2);
    }
    
    @Override
    public ByteBuf writeDouble(final double n) {
        return this.writeDouble(n);
    }
    
    @Override
    public ByteBuf writeFloat(final float n) {
        return this.writeFloat(n);
    }
    
    @Override
    public ByteBuf writeChar(final int n) {
        return this.writeChar(n);
    }
    
    @Override
    public ByteBuf writeLong(final long n) {
        return this.writeLong(n);
    }
    
    @Override
    public ByteBuf writeInt(final int n) {
        return this.writeInt(n);
    }
    
    @Override
    public ByteBuf writeMedium(final int n) {
        return this.writeMedium(n);
    }
    
    @Override
    public ByteBuf writeShort(final int n) {
        return this.writeShort(n);
    }
    
    @Override
    public ByteBuf writeByte(final int n) {
        return this.writeByte(n);
    }
    
    @Override
    public ByteBuf writeBoolean(final boolean b) {
        return this.writeBoolean(b);
    }
    
    @Override
    public ByteBuf skipBytes(final int n) {
        return this.skipBytes(n);
    }
    
    @Override
    public ByteBuf readBytes(final OutputStream outputStream, final int n) throws IOException {
        return this.readBytes(outputStream, n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuffer byteBuffer) {
        return this.readBytes(byteBuffer);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n, final int n2) {
        return this.readBytes(byteBuf, n, n2);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf, final int n) {
        return this.readBytes(byteBuf, n);
    }
    
    @Override
    public ByteBuf readBytes(final ByteBuf byteBuf) {
        return this.readBytes(byteBuf);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array) {
        return this.readBytes(array);
    }
    
    @Override
    public ByteBuf readBytes(final byte[] array, final int n, final int n2) {
        return this.readBytes(array, n, n2);
    }
    
    @Override
    public ByteBuf setZero(final int n, final int n2) {
        return this.setZero(n, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.setBytes(n, byteBuf, n2);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf) {
        return this.setBytes(n, byteBuf);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array) {
        return this.setBytes(n, array);
    }
    
    @Override
    public ByteBuf setDouble(final int n, final double n2) {
        return this.setDouble(n, n2);
    }
    
    @Override
    public ByteBuf setLong(final int n, final long n2) {
        return this.setLong(n, n2);
    }
    
    @Override
    public ByteBuf setFloat(final int n, final float n2) {
        return this.setFloat(n, n2);
    }
    
    @Override
    public ByteBuf setInt(final int n, final int n2) {
        return this.setInt(n, n2);
    }
    
    @Override
    public ByteBuf setMedium(final int n, final int n2) {
        return this.setMedium(n, n2);
    }
    
    @Override
    public ByteBuf setChar(final int n, final int n2) {
        return this.setChar(n, n2);
    }
    
    @Override
    public ByteBuf setShort(final int n, final int n2) {
        return this.setShort(n, n2);
    }
    
    @Override
    public ByteBuf setBoolean(final int n, final boolean b) {
        return this.setBoolean(n, b);
    }
    
    @Override
    public ByteBuf setByte(final int n, final int n2) {
        return this.setByte(n, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2) {
        return this.getBytes(n, byteBuf, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf) {
        return this.getBytes(n, byteBuf);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array) {
        return this.getBytes(n, array);
    }
    
    @Override
    public ByteBuf ensureWritable(final int n) {
        return this.ensureWritable(n);
    }
    
    @Override
    public ByteBuf discardSomeReadBytes() {
        return this.discardSomeReadBytes();
    }
    
    @Override
    public ByteBuf discardReadBytes() {
        return this.discardReadBytes();
    }
    
    @Override
    public ByteBuf resetWriterIndex() {
        return this.resetWriterIndex();
    }
    
    @Override
    public ByteBuf markWriterIndex() {
        return this.markWriterIndex();
    }
    
    @Override
    public ByteBuf resetReaderIndex() {
        return this.resetReaderIndex();
    }
    
    @Override
    public ByteBuf markReaderIndex() {
        return this.markReaderIndex();
    }
    
    @Override
    public ByteBuf clear() {
        return this.clear();
    }
    
    @Override
    public ByteBuf setIndex(final int n, final int n2) {
        return this.setIndex(n, n2);
    }
    
    @Override
    public ByteBuf writerIndex(final int n) {
        return this.writerIndex(n);
    }
    
    @Override
    public ByteBuf readerIndex(final int n) {
        return this.readerIndex(n);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuffer byteBuffer) {
        return this.setBytes(n, byteBuffer);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.setBytes(n, array, n2, n3);
    }
    
    @Override
    public ByteBuf setBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.setBytes(n, byteBuf, n2, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final OutputStream outputStream, final int n2) throws IOException {
        return this.getBytes(n, outputStream, n2);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuffer byteBuffer) {
        return this.getBytes(n, byteBuffer);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final byte[] array, final int n2, final int n3) {
        return this.getBytes(n, array, n2, n3);
    }
    
    @Override
    public ByteBuf getBytes(final int n, final ByteBuf byteBuf, final int n2, final int n3) {
        return this.getBytes(n, byteBuf, n2, n3);
    }
    
    @Override
    public ByteBuf capacity(final int n) {
        return this.capacity(n);
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
    
    static {
        FULL_BYTEBUFFER = (ByteBuffer)ByteBuffer.allocate(1).position(1);
    }
    
    private static final class Component
    {
        final ByteBuf buf;
        final int length;
        int offset;
        int endOffset;
        
        Component(final ByteBuf buf) {
            this.buf = buf;
            this.length = buf.readableBytes();
        }
        
        void freeIfNecessary() {
            this.buf.release();
        }
    }
}
