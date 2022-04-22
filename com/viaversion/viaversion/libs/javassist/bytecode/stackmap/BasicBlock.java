package com.viaversion.viaversion.libs.javassist.bytecode.stackmap;

import com.viaversion.viaversion.libs.javassist.bytecode.*;
import java.util.*;

public class BasicBlock
{
    protected int position;
    protected int length;
    protected int incoming;
    protected BasicBlock[] exit;
    protected boolean stop;
    protected Catch toCatch;
    
    protected BasicBlock(final int position) {
        this.position = position;
        this.length = 0;
        this.incoming = 0;
    }
    
    public static BasicBlock find(final BasicBlock[] array, final int n) throws BadBytecode {
        while (0 < array.length) {
            final BasicBlock basicBlock = array[0];
            if (basicBlock.position <= n && n < basicBlock.position + basicBlock.length) {
                return basicBlock;
            }
            int n2 = 0;
            ++n2;
        }
        throw new BadBytecode("no basic block at " + n);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        final String name = this.getClass().getName();
        final int lastIndex = name.lastIndexOf(46);
        sb.append((lastIndex < 0) ? name : name.substring(lastIndex + 1));
        sb.append("[");
        this.toString2(sb);
        sb.append("]");
        return sb.toString();
    }
    
    protected void toString2(final StringBuffer sb) {
        sb.append("pos=").append(this.position).append(", len=").append(this.length).append(", in=").append(this.incoming).append(", exit{");
        if (this.exit != null) {
            final BasicBlock[] exit = this.exit;
            while (0 < exit.length) {
                sb.append(exit[0].position).append(",");
                int n = 0;
                ++n;
            }
        }
        sb.append("}, {");
        for (Catch catch1 = this.toCatch; catch1 != null; catch1 = catch1.next) {
            sb.append("(").append(catch1.body.position).append(", ").append(catch1.typeIndex).append("), ");
        }
        sb.append("}");
    }
    
    public static class Maker
    {
        protected BasicBlock makeBlock(final int n) {
            return new BasicBlock(n);
        }
        
        protected BasicBlock[] makeArray(final int n) {
            return new BasicBlock[n];
        }
        
        private BasicBlock[] makeArray(final BasicBlock basicBlock) {
            final BasicBlock[] array = this.makeArray(1);
            array[0] = basicBlock;
            return array;
        }
        
        private BasicBlock[] makeArray(final BasicBlock basicBlock, final BasicBlock basicBlock2) {
            final BasicBlock[] array = this.makeArray(2);
            array[0] = basicBlock;
            array[1] = basicBlock2;
            return array;
        }
        
        public BasicBlock[] make(final MethodInfo methodInfo) throws BadBytecode {
            final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            if (codeAttribute == null) {
                return null;
            }
            final CodeIterator iterator = codeAttribute.iterator();
            return this.make(iterator, 0, iterator.getCodeLength(), codeAttribute.getExceptionTable());
        }
        
        public BasicBlock[] make(final CodeIterator codeIterator, final int n, final int n2, final ExceptionTable exceptionTable) throws BadBytecode {
            final BasicBlock[] blocks = this.makeBlocks(this.makeMarks(codeIterator, n, n2, exceptionTable));
            this.addCatchers(blocks, exceptionTable);
            return blocks;
        }
        
        private Mark makeMark(final Map map, final int n) {
            return this.makeMark0(map, n, true, true);
        }
        
        private Mark makeMark(final Map map, final int n, final BasicBlock[] array, final int n2, final boolean b) {
            final Mark mark0 = this.makeMark0(map, n, false, false);
            mark0.setJump(array, n2, b);
            return mark0;
        }
        
        private Mark makeMark0(final Map map, final int n, final boolean b, final boolean b2) {
            final Integer value = n;
            Mark mark = map.get(value);
            if (mark == null) {
                mark = new Mark(n);
                map.put(value, mark);
            }
            if (b) {
                if (mark.block == null) {
                    mark.block = this.makeBlock(n);
                }
                if (b2) {
                    final BasicBlock block = mark.block;
                    ++block.incoming;
                }
            }
            return mark;
        }
        
        private Map makeMarks(final CodeIterator codeIterator, final int n, final int n2, final ExceptionTable exceptionTable) throws BadBytecode {
            codeIterator.begin();
            codeIterator.move(n);
            final HashMap hashMap = new HashMap();
            while (codeIterator.hasNext()) {
                final int next = codeIterator.next();
                if (next >= n2) {
                    break;
                }
                final int byte1 = codeIterator.byteAt(next);
                if ((153 <= byte1 && byte1 <= 166) || byte1 == 198 || byte1 == 199) {
                    this.makeMark(hashMap, next, this.makeArray(this.makeMark(hashMap, next + codeIterator.s16bitAt(next + 1)).block, this.makeMark(hashMap, next + 3).block), 3, false);
                }
                else if (167 <= byte1 && byte1 <= 171) {
                    switch (byte1) {
                        case 167: {
                            this.makeGoto(hashMap, next, next + codeIterator.s16bitAt(next + 1), 3);
                            continue;
                        }
                        case 168: {
                            this.makeJsr(hashMap, next, next + codeIterator.s16bitAt(next + 1), 3);
                            continue;
                        }
                        case 169: {
                            this.makeMark(hashMap, next, null, 2, true);
                            continue;
                        }
                        case 170: {
                            final int n3 = (next & 0xFFFFFFFC) + 4;
                            final int n4 = codeIterator.s32bitAt(n3 + 8) - codeIterator.s32bitAt(n3 + 4) + 1;
                            final BasicBlock[] array = this.makeArray(n4 + 1);
                            array[0] = this.makeMark(hashMap, next + codeIterator.s32bitAt(n3)).block;
                            int n5 = n3 + 12;
                            final int n6 = 1 + n4 * 4;
                            while (1 < n6) {
                                final BasicBlock[] array2 = array;
                                final int n7 = 1;
                                int n8 = 0;
                                ++n8;
                                array2[n7] = this.makeMark(hashMap, next + codeIterator.s32bitAt(1)).block;
                                n5 += 4;
                            }
                            this.makeMark(hashMap, next, array, n6 - next, true);
                            continue;
                        }
                        case 171: {
                            final int n9 = (next & 0xFFFFFFFC) + 4;
                            final int s32bit = codeIterator.s32bitAt(n9 + 4);
                            final BasicBlock[] array3 = this.makeArray(s32bit + 1);
                            array3[0] = this.makeMark(hashMap, next + codeIterator.s32bitAt(n9)).block;
                            int i;
                            int n10;
                            for (i = n9 + 8 + 4, n10 = i + s32bit * 8 - 4; i < n10; i += 8) {
                                final BasicBlock[] array4 = array3;
                                final int n11 = 1;
                                int n5 = 0;
                                ++n5;
                                array4[n11] = this.makeMark(hashMap, next + codeIterator.s32bitAt(i)).block;
                            }
                            this.makeMark(hashMap, next, array3, n10 - next, true);
                            continue;
                        }
                    }
                }
                else if ((172 <= byte1 && byte1 <= 177) || byte1 == 191) {
                    this.makeMark(hashMap, next, null, 1, true);
                }
                else if (byte1 == 200) {
                    this.makeGoto(hashMap, next, next + codeIterator.s32bitAt(next + 1), 5);
                }
                else if (byte1 == 201) {
                    this.makeJsr(hashMap, next, next + codeIterator.s32bitAt(next + 1), 5);
                }
                else {
                    if (byte1 != 196 || codeIterator.byteAt(next + 1) != 169) {
                        continue;
                    }
                    this.makeMark(hashMap, next, null, 4, true);
                }
            }
            if (exceptionTable != null) {
                int size = exceptionTable.size();
                while (--size >= 0) {
                    this.makeMark0(hashMap, exceptionTable.startPc(size), true, false);
                    this.makeMark(hashMap, exceptionTable.handlerPc(size));
                }
            }
            return hashMap;
        }
        
        private void makeGoto(final Map map, final int n, final int n2, final int n3) {
            this.makeMark(map, n, this.makeArray(this.makeMark(map, n2).block), n3, true);
        }
        
        protected void makeJsr(final Map map, final int n, final int n2, final int n3) throws BadBytecode {
            throw new JsrBytecode();
        }
        
        private BasicBlock[] makeBlocks(final Map map) {
            final Mark[] array = (Mark[])map.values().toArray(new Mark[map.size()]);
            Arrays.sort(array);
            final ArrayList<BasicBlock> list = new ArrayList<BasicBlock>();
            int n2 = 0;
            BasicBlock basicBlock;
            if (array.length > 0 && array[0].position == 0 && array[0].block != null) {
                final Mark[] array2 = array;
                final int n = 0;
                ++n2;
                basicBlock = getBBlock(array2[n]);
            }
            else {
                basicBlock = this.makeBlock(0);
            }
            list.add(basicBlock);
            while (0 < array.length) {
                final Mark[] array3 = array;
                final int n3 = 0;
                ++n2;
                final Mark mark = array3[n3];
                final BasicBlock bBlock = getBBlock(mark);
                if (bBlock == null) {
                    if (basicBlock.length > 0) {
                        basicBlock = this.makeBlock(basicBlock.position + basicBlock.length);
                        list.add(basicBlock);
                    }
                    basicBlock.length = mark.position + mark.size - basicBlock.position;
                    basicBlock.exit = mark.jump;
                    basicBlock.stop = mark.alwaysJmp;
                }
                else {
                    if (basicBlock.length == 0) {
                        basicBlock.length = mark.position - basicBlock.position;
                        final BasicBlock basicBlock2 = bBlock;
                        ++basicBlock2.incoming;
                        basicBlock.exit = this.makeArray(bBlock);
                    }
                    else if (basicBlock.position + basicBlock.length < mark.position) {
                        final BasicBlock block = this.makeBlock(basicBlock.position + basicBlock.length);
                        list.add(block);
                        block.length = mark.position - block.position;
                        block.stop = true;
                        block.exit = this.makeArray(bBlock);
                    }
                    list.add(bBlock);
                    basicBlock = bBlock;
                }
            }
            return list.toArray(this.makeArray(list.size()));
        }
        
        private static BasicBlock getBBlock(final Mark mark) {
            final BasicBlock block = mark.block;
            if (block != null && mark.size > 0) {
                block.exit = mark.jump;
                block.length = mark.size;
                block.stop = mark.alwaysJmp;
            }
            return block;
        }
        
        private void addCatchers(final BasicBlock[] array, final ExceptionTable exceptionTable) throws BadBytecode {
            if (exceptionTable == null) {
                return;
            }
            int size = exceptionTable.size();
            while (--size >= 0) {
                final BasicBlock find = BasicBlock.find(array, exceptionTable.handlerPc(size));
                final int startPc = exceptionTable.startPc(size);
                final int endPc = exceptionTable.endPc(size);
                final int catchType = exceptionTable.catchType(size);
                final BasicBlock basicBlock = find;
                --basicBlock.incoming;
                while (0 < array.length) {
                    final BasicBlock basicBlock2 = array[0];
                    final int position = basicBlock2.position;
                    if (startPc <= position && position < endPc) {
                        basicBlock2.toCatch = new Catch(find, catchType, basicBlock2.toCatch);
                        final BasicBlock basicBlock3 = find;
                        ++basicBlock3.incoming;
                    }
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    static class Mark implements Comparable
    {
        int position;
        BasicBlock block;
        BasicBlock[] jump;
        boolean alwaysJmp;
        int size;
        Catch catcher;
        
        Mark(final int position) {
            this.position = position;
            this.block = null;
            this.jump = null;
            this.alwaysJmp = false;
            this.size = 0;
            this.catcher = null;
        }
        
        public int compareTo(final Mark mark) {
            if (null == mark) {
                return -1;
            }
            return this.position - mark.position;
        }
        
        void setJump(final BasicBlock[] jump, final int size, final boolean alwaysJmp) {
            this.jump = jump;
            this.size = size;
            this.alwaysJmp = alwaysJmp;
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Mark)o);
        }
    }
    
    public static class Catch
    {
        public Catch next;
        public BasicBlock body;
        public int typeIndex;
        
        Catch(final BasicBlock body, final int typeIndex, final Catch next) {
            this.body = body;
            this.typeIndex = typeIndex;
            this.next = next;
        }
    }
    
    static class JsrBytecode extends BadBytecode
    {
        private static final long serialVersionUID = 1L;
        
        JsrBytecode() {
            super("JSR");
        }
    }
}
