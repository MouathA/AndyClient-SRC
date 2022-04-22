package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.*;
import java.util.regex.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import java.math.*;
import org.yaml.snakeyaml.reader.*;

class SafeRepresenter extends BaseRepresenter
{
    protected Map classTags;
    protected TimeZone timeZone;
    protected DumperOptions.NonPrintableStyle nonPrintableStyle;
    private static final Pattern MULTILINE_PATTERN;
    
    public SafeRepresenter() {
        this(new DumperOptions());
    }
    
    public SafeRepresenter(final DumperOptions dumperOptions) {
        this.timeZone = null;
        this.nullRepresenter = new RepresentNull();
        this.representers.put(String.class, new RepresentString());
        this.representers.put(Boolean.class, new RepresentBoolean());
        this.representers.put(Character.class, new RepresentString());
        this.representers.put(UUID.class, new RepresentUuid());
        this.representers.put(byte[].class, new RepresentByteArray());
        final RepresentPrimitiveArray representPrimitiveArray = new RepresentPrimitiveArray();
        this.representers.put(short[].class, representPrimitiveArray);
        this.representers.put(int[].class, representPrimitiveArray);
        this.representers.put(long[].class, representPrimitiveArray);
        this.representers.put(float[].class, representPrimitiveArray);
        this.representers.put(double[].class, representPrimitiveArray);
        this.representers.put(char[].class, representPrimitiveArray);
        this.representers.put(boolean[].class, representPrimitiveArray);
        this.multiRepresenters.put(Number.class, new RepresentNumber());
        this.multiRepresenters.put(List.class, new RepresentList());
        this.multiRepresenters.put(Map.class, new RepresentMap());
        this.multiRepresenters.put(Set.class, new RepresentSet());
        this.multiRepresenters.put(Iterator.class, new RepresentIterator());
        this.multiRepresenters.put(new Object[0].getClass(), new RepresentArray());
        this.multiRepresenters.put(Date.class, new RepresentDate());
        this.multiRepresenters.put(Enum.class, new RepresentEnum());
        this.multiRepresenters.put(Calendar.class, new RepresentDate());
        this.classTags = new HashMap();
        this.nonPrintableStyle = dumperOptions.getNonPrintableStyle();
    }
    
    protected Tag getTag(final Class clazz, final Tag tag) {
        if (this.classTags.containsKey(clazz)) {
            return this.classTags.get(clazz);
        }
        return tag;
    }
    
    public Tag addClassTag(final Class clazz, final Tag tag) {
        if (tag == null) {
            throw new NullPointerException("Tag must be provided.");
        }
        return this.classTags.put(clazz, tag);
    }
    
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    
    static Pattern access$000() {
        return SafeRepresenter.MULTILINE_PATTERN;
    }
    
    static {
        MULTILINE_PATTERN = Pattern.compile("\n|\u0085|\u2028|\u2029");
    }
    
    protected class RepresentUuid implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentUuid(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representScalar(this.this$0.getTag(o.getClass(), new Tag(UUID.class)), o.toString());
        }
    }
    
    protected class RepresentByteArray implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentByteArray(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representScalar(Tag.BINARY, String.valueOf(Base64Coder.encode((byte[])o)), DumperOptions.ScalarStyle.LITERAL);
        }
    }
    
    protected class RepresentEnum implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentEnum(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representScalar(this.this$0.getTag(o.getClass(), new Tag(o.getClass())), ((Enum)o).name());
        }
    }
    
    protected class RepresentDate implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentDate(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            Calendar instance;
            if (o instanceof Calendar) {
                instance = (Calendar)o;
            }
            else {
                instance = Calendar.getInstance((this.this$0.getTimeZone() == null) ? TimeZone.getTimeZone("UTC") : this.this$0.timeZone);
                instance.setTime((Date)o);
            }
            final int value = instance.get(1);
            final int n = instance.get(2) + 1;
            final int value2 = instance.get(5);
            final int value3 = instance.get(11);
            final int value4 = instance.get(12);
            final int value5 = instance.get(13);
            final int value6 = instance.get(14);
            final StringBuilder sb = new StringBuilder(String.valueOf(value));
            while (sb.length() < 4) {
                sb.insert(0, "0");
            }
            sb.append("-");
            if (n < 10) {
                sb.append("0");
            }
            sb.append(String.valueOf(n));
            sb.append("-");
            if (value2 < 10) {
                sb.append("0");
            }
            sb.append(String.valueOf(value2));
            sb.append("T");
            if (value3 < 10) {
                sb.append("0");
            }
            sb.append(String.valueOf(value3));
            sb.append(":");
            if (value4 < 10) {
                sb.append("0");
            }
            sb.append(String.valueOf(value4));
            sb.append(":");
            if (value5 < 10) {
                sb.append("0");
            }
            sb.append(String.valueOf(value5));
            if (value6 > 0) {
                if (value6 < 10) {
                    sb.append(".00");
                }
                else if (value6 < 100) {
                    sb.append(".0");
                }
                else {
                    sb.append(".");
                }
                sb.append(String.valueOf(value6));
            }
            int offset = instance.getTimeZone().getOffset(instance.getTime().getTime());
            if (offset == 0) {
                sb.append('Z');
            }
            else {
                if (offset < 0) {
                    sb.append('-');
                    offset = -offset;
                }
                else {
                    sb.append('+');
                }
                final int n2 = offset / 60000;
                final int n3 = n2 / 60;
                final int n4 = n2 % 60;
                if (n3 < 10) {
                    sb.append('0');
                }
                sb.append(n3);
                sb.append(':');
                if (n4 < 10) {
                    sb.append('0');
                }
                sb.append(n4);
            }
            return this.this$0.representScalar(this.this$0.getTag(o.getClass(), Tag.TIMESTAMP), sb.toString(), DumperOptions.ScalarStyle.PLAIN);
        }
    }
    
    protected class RepresentSet implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentSet(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            final LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
            final Iterator<Object> iterator = ((Set)o).iterator();
            while (iterator.hasNext()) {
                linkedHashMap.put(iterator.next(), null);
            }
            return this.this$0.representMapping(this.this$0.getTag(o.getClass(), Tag.SET), linkedHashMap, DumperOptions.FlowStyle.AUTO);
        }
    }
    
    protected class RepresentMap implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentMap(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representMapping(this.this$0.getTag(o.getClass(), Tag.MAP), (Map)o, DumperOptions.FlowStyle.AUTO);
        }
    }
    
    protected class RepresentPrimitiveArray implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentPrimitiveArray(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            final Class<?> componentType = o.getClass().getComponentType();
            if (Byte.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asByteList(o), DumperOptions.FlowStyle.AUTO);
            }
            if (Short.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asShortList(o), DumperOptions.FlowStyle.AUTO);
            }
            if (Integer.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asIntList(o), DumperOptions.FlowStyle.AUTO);
            }
            if (Long.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asLongList(o), DumperOptions.FlowStyle.AUTO);
            }
            if (Float.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asFloatList(o), DumperOptions.FlowStyle.AUTO);
            }
            if (Double.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asDoubleList(o), DumperOptions.FlowStyle.AUTO);
            }
            if (Character.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asCharList(o), DumperOptions.FlowStyle.AUTO);
            }
            if (Boolean.TYPE == componentType) {
                return this.this$0.representSequence(Tag.SEQ, this.asBooleanList(o), DumperOptions.FlowStyle.AUTO);
            }
            throw new YAMLException("Unexpected primitive '" + componentType.getCanonicalName() + "'");
        }
        
        private List asByteList(final Object o) {
            final byte[] array = (byte[])o;
            final ArrayList list = new ArrayList<Byte>(array.length);
            while (0 < array.length) {
                list.add(Byte.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private List asShortList(final Object o) {
            final short[] array = (short[])o;
            final ArrayList list = new ArrayList<Short>(array.length);
            while (0 < array.length) {
                list.add(Short.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private List asIntList(final Object o) {
            final int[] array = (int[])o;
            final ArrayList list = new ArrayList<Integer>(array.length);
            while (0 < array.length) {
                list.add(Integer.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private List asLongList(final Object o) {
            final long[] array = (long[])o;
            final ArrayList list = new ArrayList<Long>(array.length);
            while (0 < array.length) {
                list.add(Long.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private List asFloatList(final Object o) {
            final float[] array = (float[])o;
            final ArrayList list = new ArrayList<Float>(array.length);
            while (0 < array.length) {
                list.add(Float.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private List asDoubleList(final Object o) {
            final double[] array = (double[])o;
            final ArrayList list = new ArrayList<Double>(array.length);
            while (0 < array.length) {
                list.add(Double.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private List asCharList(final Object o) {
            final char[] array = (char[])o;
            final ArrayList list = new ArrayList<Character>(array.length);
            while (0 < array.length) {
                list.add(Character.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
        
        private List asBooleanList(final Object o) {
            final boolean[] array = (boolean[])o;
            final ArrayList list = new ArrayList<Boolean>(array.length);
            while (0 < array.length) {
                list.add(Boolean.valueOf(array[0]));
                int n = 0;
                ++n;
            }
            return list;
        }
    }
    
    protected class RepresentArray implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentArray(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representSequence(Tag.SEQ, Arrays.asList((Object[])o), DumperOptions.FlowStyle.AUTO);
        }
    }
    
    private static class IteratorWrapper implements Iterable
    {
        private Iterator iter;
        
        public IteratorWrapper(final Iterator iter) {
            this.iter = iter;
        }
        
        @Override
        public Iterator iterator() {
            return this.iter;
        }
    }
    
    protected class RepresentIterator implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentIterator(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representSequence(this.this$0.getTag(o.getClass(), Tag.SEQ), new IteratorWrapper((Iterator)o), DumperOptions.FlowStyle.AUTO);
        }
    }
    
    protected class RepresentList implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentList(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representSequence(this.this$0.getTag(o.getClass(), Tag.SEQ), (Iterable)o, DumperOptions.FlowStyle.AUTO);
        }
    }
    
    protected class RepresentNumber implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentNumber(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            Tag tag;
            String s;
            if (o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof BigInteger) {
                tag = Tag.INT;
                s = o.toString();
            }
            else {
                final Number n = (Number)o;
                tag = Tag.FLOAT;
                if (n.equals(Double.NaN)) {
                    s = ".NaN";
                }
                else if (n.equals(Double.POSITIVE_INFINITY)) {
                    s = ".inf";
                }
                else if (n.equals(Double.NEGATIVE_INFINITY)) {
                    s = "-.inf";
                }
                else {
                    s = n.toString();
                }
            }
            return this.this$0.representScalar(this.this$0.getTag(o.getClass(), tag), s);
        }
    }
    
    protected class RepresentBoolean implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentBoolean(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            String s;
            if (Boolean.TRUE.equals(o)) {
                s = "true";
            }
            else {
                s = "false";
            }
            return this.this$0.representScalar(Tag.BOOL, s);
        }
    }
    
    protected class RepresentString implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentString(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            Tag tag = Tag.STR;
            DumperOptions.ScalarStyle scalarStyle = null;
            String s = o.toString();
            if (this.this$0.nonPrintableStyle == DumperOptions.NonPrintableStyle.BINARY && !StreamReader.isPrintable(s)) {
                tag = Tag.BINARY;
                final byte[] bytes = s.getBytes("UTF-8");
                if (!new String(bytes, "UTF-8").equals(s)) {
                    throw new YAMLException("invalid string value has occurred");
                }
                s = String.valueOf(Base64Coder.encode(bytes));
                scalarStyle = DumperOptions.ScalarStyle.LITERAL;
            }
            if (this.this$0.defaultScalarStyle == DumperOptions.ScalarStyle.PLAIN && SafeRepresenter.access$000().matcher(s).find()) {
                scalarStyle = DumperOptions.ScalarStyle.LITERAL;
            }
            return this.this$0.representScalar(tag, s, scalarStyle);
        }
    }
    
    protected class RepresentNull implements Represent
    {
        final SafeRepresenter this$0;
        
        protected RepresentNull(final SafeRepresenter this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Node representData(final Object o) {
            return this.this$0.representScalar(Tag.NULL, "null");
        }
    }
}
