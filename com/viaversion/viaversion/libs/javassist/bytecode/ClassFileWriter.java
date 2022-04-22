package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.*;

public class ClassFileWriter
{
    private ByteStream output;
    private ConstPoolWriter constPool;
    private FieldWriter fields;
    private MethodWriter methods;
    int thisClass;
    int superClass;
    
    public ClassFileWriter(final int n, final int n2) {
        (this.output = new ByteStream(512)).writeInt(-889275714);
        this.output.writeShort(n2);
        this.output.writeShort(n);
        this.constPool = new ConstPoolWriter(this.output);
        this.fields = new FieldWriter(this.constPool);
        this.methods = new MethodWriter(this.constPool);
    }
    
    public ConstPoolWriter getConstPool() {
        return this.constPool;
    }
    
    public FieldWriter getFieldWriter() {
        return this.fields;
    }
    
    public MethodWriter getMethodWriter() {
        return this.methods;
    }
    
    public byte[] end(final int n, final int n2, final int n3, final int[] array, final AttributeWriter attributeWriter) {
        this.constPool.end();
        this.output.writeShort(n);
        this.output.writeShort(n2);
        this.output.writeShort(n3);
        if (array == null) {
            this.output.writeShort(0);
        }
        else {
            final int length = array.length;
            this.output.writeShort(length);
            while (0 < length) {
                this.output.writeShort(array[0]);
                int n4 = 0;
                ++n4;
            }
        }
        this.output.enlarge(this.fields.dataSize() + this.methods.dataSize() + 6);
        this.output.writeShort(this.fields.size());
        this.fields.write(this.output);
        this.output.writeShort(this.methods.numOfMethods());
        this.methods.write(this.output);
        writeAttribute(this.output, attributeWriter, 0);
        return this.output.toByteArray();
    }
    
    public void end(final DataOutputStream dataOutputStream, final int n, final int n2, final int n3, final int[] array, final AttributeWriter attributeWriter) throws IOException {
        this.constPool.end();
        this.output.writeTo(dataOutputStream);
        dataOutputStream.writeShort(n);
        dataOutputStream.writeShort(n2);
        dataOutputStream.writeShort(n3);
        if (array == null) {
            dataOutputStream.writeShort(0);
        }
        else {
            final int length = array.length;
            dataOutputStream.writeShort(length);
            while (0 < length) {
                dataOutputStream.writeShort(array[0]);
                int n4 = 0;
                ++n4;
            }
        }
        dataOutputStream.writeShort(this.fields.size());
        this.fields.write(dataOutputStream);
        dataOutputStream.writeShort(this.methods.numOfMethods());
        this.methods.write(dataOutputStream);
        if (attributeWriter == null) {
            dataOutputStream.writeShort(0);
        }
        else {
            dataOutputStream.writeShort(attributeWriter.size());
            attributeWriter.write(dataOutputStream);
        }
    }
    
    static void writeAttribute(final ByteStream byteStream, final AttributeWriter attributeWriter, final int n) {
        if (attributeWriter == null) {
            byteStream.writeShort(n);
            return;
        }
        byteStream.writeShort(attributeWriter.size() + n);
        final DataOutputStream dataOutputStream = new DataOutputStream(byteStream);
        attributeWriter.write(dataOutputStream);
        dataOutputStream.flush();
    }
    
    public static final class ConstPoolWriter
    {
        ByteStream output;
        protected int startPos;
        protected int num;
        
        ConstPoolWriter(final ByteStream output) {
            this.output = output;
            this.startPos = output.getPos();
            this.num = 1;
            this.output.writeShort(1);
        }
        
        public int[] addClassInfo(final String[] array) {
            final int length = array.length;
            final int[] array2 = new int[length];
            while (0 < length) {
                array2[0] = this.addClassInfo(array[0]);
                int n = 0;
                ++n;
            }
            return array2;
        }
        
        public int addClassInfo(final String s) {
            final int addUtf8Info = this.addUtf8Info(s);
            this.output.write(7);
            this.output.writeShort(addUtf8Info);
            return this.num++;
        }
        
        public int addClassInfo(final int n) {
            this.output.write(7);
            this.output.writeShort(n);
            return this.num++;
        }
        
        public int addNameAndTypeInfo(final String s, final String s2) {
            return this.addNameAndTypeInfo(this.addUtf8Info(s), this.addUtf8Info(s2));
        }
        
        public int addNameAndTypeInfo(final int n, final int n2) {
            this.output.write(12);
            this.output.writeShort(n);
            this.output.writeShort(n2);
            return this.num++;
        }
        
        public int addFieldrefInfo(final int n, final int n2) {
            this.output.write(9);
            this.output.writeShort(n);
            this.output.writeShort(n2);
            return this.num++;
        }
        
        public int addMethodrefInfo(final int n, final int n2) {
            this.output.write(10);
            this.output.writeShort(n);
            this.output.writeShort(n2);
            return this.num++;
        }
        
        public int addInterfaceMethodrefInfo(final int n, final int n2) {
            this.output.write(11);
            this.output.writeShort(n);
            this.output.writeShort(n2);
            return this.num++;
        }
        
        public int addMethodHandleInfo(final int n, final int n2) {
            this.output.write(15);
            this.output.write(n);
            this.output.writeShort(n2);
            return this.num++;
        }
        
        public int addMethodTypeInfo(final int n) {
            this.output.write(16);
            this.output.writeShort(n);
            return this.num++;
        }
        
        public int addInvokeDynamicInfo(final int n, final int n2) {
            this.output.write(18);
            this.output.writeShort(n);
            this.output.writeShort(n2);
            return this.num++;
        }
        
        public int addDynamicInfo(final int n, final int n2) {
            this.output.write(17);
            this.output.writeShort(n);
            this.output.writeShort(n2);
            return this.num++;
        }
        
        public int addStringInfo(final String s) {
            final int addUtf8Info = this.addUtf8Info(s);
            this.output.write(8);
            this.output.writeShort(addUtf8Info);
            return this.num++;
        }
        
        public int addIntegerInfo(final int n) {
            this.output.write(3);
            this.output.writeInt(n);
            return this.num++;
        }
        
        public int addFloatInfo(final float n) {
            this.output.write(4);
            this.output.writeFloat(n);
            return this.num++;
        }
        
        public int addLongInfo(final long n) {
            this.output.write(5);
            this.output.writeLong(n);
            final int num = this.num;
            this.num += 2;
            return num;
        }
        
        public int addDoubleInfo(final double n) {
            this.output.write(6);
            this.output.writeDouble(n);
            final int num = this.num;
            this.num += 2;
            return num;
        }
        
        public int addUtf8Info(final String s) {
            this.output.write(1);
            this.output.writeUTF(s);
            return this.num++;
        }
        
        void end() {
            this.output.writeShort(this.startPos, this.num);
        }
    }
    
    public static final class MethodWriter
    {
        protected ByteStream output;
        protected ConstPoolWriter constPool;
        private int methodCount;
        protected int codeIndex;
        protected int throwsIndex;
        protected int stackIndex;
        private int startPos;
        private boolean isAbstract;
        private int catchPos;
        private int catchCount;
        
        MethodWriter(final ConstPoolWriter constPool) {
            this.output = new ByteStream(256);
            this.constPool = constPool;
            this.methodCount = 0;
            this.codeIndex = 0;
            this.throwsIndex = 0;
            this.stackIndex = 0;
        }
        
        public void begin(final int n, final String s, final String s2, final String[] array, final AttributeWriter attributeWriter) {
            final int addUtf8Info = this.constPool.addUtf8Info(s);
            final int addUtf8Info2 = this.constPool.addUtf8Info(s2);
            int[] addClassInfo;
            if (array == null) {
                addClassInfo = null;
            }
            else {
                addClassInfo = this.constPool.addClassInfo(array);
            }
            this.begin(n, addUtf8Info, addUtf8Info2, addClassInfo, attributeWriter);
        }
        
        public void begin(final int n, final int n2, final int n3, final int[] array, final AttributeWriter attributeWriter) {
            ++this.methodCount;
            this.output.writeShort(n);
            this.output.writeShort(n2);
            this.output.writeShort(n3);
            this.isAbstract = ((n & 0x400) != 0x0);
            int n4 = this.isAbstract ? 0 : 1;
            if (array != null) {
                ++n4;
            }
            ClassFileWriter.writeAttribute(this.output, attributeWriter, n4);
            if (array != null) {
                this.writeThrows(array);
            }
            if (!this.isAbstract) {
                if (this.codeIndex == 0) {
                    this.codeIndex = this.constPool.addUtf8Info("Code");
                }
                this.startPos = this.output.getPos();
                this.output.writeShort(this.codeIndex);
                this.output.writeBlank(12);
            }
            this.catchPos = -1;
            this.catchCount = 0;
        }
        
        private void writeThrows(final int[] array) {
            if (this.throwsIndex == 0) {
                this.throwsIndex = this.constPool.addUtf8Info("Exceptions");
            }
            this.output.writeShort(this.throwsIndex);
            this.output.writeInt(array.length * 2 + 2);
            this.output.writeShort(array.length);
            while (0 < array.length) {
                this.output.writeShort(array[0]);
                int n = 0;
                ++n;
            }
        }
        
        public void add(final int n) {
            this.output.write(n);
        }
        
        public void add16(final int n) {
            this.output.writeShort(n);
        }
        
        public void add32(final int n) {
            this.output.writeInt(n);
        }
        
        public void addInvoke(final int n, final String s, final String s2, final String s3) {
            final int addMethodrefInfo = this.constPool.addMethodrefInfo(this.constPool.addClassInfo(s), this.constPool.addNameAndTypeInfo(s2, s3));
            this.add(n);
            this.add16(addMethodrefInfo);
        }
        
        public void codeEnd(final int n, final int n2) {
            if (!this.isAbstract) {
                this.output.writeShort(this.startPos + 6, n);
                this.output.writeShort(this.startPos + 8, n2);
                this.output.writeInt(this.startPos + 10, this.output.getPos() - this.startPos - 14);
                this.catchPos = this.output.getPos();
                this.catchCount = 0;
                this.output.writeShort(0);
            }
        }
        
        public void addCatch(final int n, final int n2, final int n3, final int n4) {
            ++this.catchCount;
            this.output.writeShort(n);
            this.output.writeShort(n2);
            this.output.writeShort(n3);
            this.output.writeShort(n4);
        }
        
        public void end(final StackMapTable.Writer writer, final AttributeWriter attributeWriter) {
            if (this.isAbstract) {
                return;
            }
            this.output.writeShort(this.catchPos, this.catchCount);
            ClassFileWriter.writeAttribute(this.output, attributeWriter, (writer != null) ? 1 : 0);
            if (writer != null) {
                if (this.stackIndex == 0) {
                    this.stackIndex = this.constPool.addUtf8Info("StackMapTable");
                }
                this.output.writeShort(this.stackIndex);
                final byte[] byteArray = writer.toByteArray();
                this.output.writeInt(byteArray.length);
                this.output.write(byteArray);
            }
            this.output.writeInt(this.startPos + 2, this.output.getPos() - this.startPos - 6);
        }
        
        public int size() {
            return this.output.getPos() - this.startPos - 14;
        }
        
        int numOfMethods() {
            return this.methodCount;
        }
        
        int dataSize() {
            return this.output.size();
        }
        
        void write(final OutputStream outputStream) throws IOException {
            this.output.writeTo(outputStream);
        }
    }
    
    public interface AttributeWriter
    {
        int size();
        
        void write(final DataOutputStream p0) throws IOException;
    }
    
    public static final class FieldWriter
    {
        protected ByteStream output;
        protected ConstPoolWriter constPool;
        private int fieldCount;
        
        FieldWriter(final ConstPoolWriter constPool) {
            this.output = new ByteStream(128);
            this.constPool = constPool;
            this.fieldCount = 0;
        }
        
        public void add(final int n, final String s, final String s2, final AttributeWriter attributeWriter) {
            this.add(n, this.constPool.addUtf8Info(s), this.constPool.addUtf8Info(s2), attributeWriter);
        }
        
        public void add(final int n, final int n2, final int n3, final AttributeWriter attributeWriter) {
            ++this.fieldCount;
            this.output.writeShort(n);
            this.output.writeShort(n2);
            this.output.writeShort(n3);
            ClassFileWriter.writeAttribute(this.output, attributeWriter, 0);
        }
        
        int size() {
            return this.fieldCount;
        }
        
        int dataSize() {
            return this.output.size();
        }
        
        void write(final OutputStream outputStream) throws IOException {
            this.output.writeTo(outputStream);
        }
    }
}
