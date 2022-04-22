package net.minecraft.nbt;

import java.util.regex.*;
import org.apache.logging.log4j.*;
import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class JsonToNBT
{
    private static final Logger logger;
    private static final Pattern field_179273_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001232";
        logger = LogManager.getLogger();
        field_179273_b = Pattern.compile("\\[[-+\\d|,\\s]+\\]");
    }
    
    public static NBTTagCompound func_180713_a(String trim) throws NBTException {
        trim = trim.trim();
        if (!trim.startsWith("{")) {
            throw new NBTException("Invalid tag encountered, expected '{' as first char.");
        }
        if (func_150310_b(trim) != 1) {
            throw new NBTException("Encountered multiple top tags, only one expected");
        }
        return (NBTTagCompound)func_150316_a("tag", trim).func_150489_a();
    }
    
    static int func_150310_b(final String s) throws NBTException {
        final Stack<Character> stack = new Stack<Character>();
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == '\"') {
                if (func_179271_b(s, 0)) {
                    if (!false) {
                        throw new NBTException("Illegal use of \\\": " + s);
                    }
                }
                else {
                    final boolean b = !false;
                }
            }
            else if (!false) {
                if (char1 != '{' && char1 != '[') {
                    if (char1 == '}' && (stack.isEmpty() || stack.pop() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + s);
                    }
                    if (char1 == ']' && (stack.isEmpty() || stack.pop() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + s);
                    }
                }
                else {
                    if (stack.isEmpty()) {
                        int n = 0;
                        ++n;
                    }
                    stack.push(char1);
                }
            }
            int n2 = 0;
            ++n2;
        }
        if (false) {
            throw new NBTException("Unbalanced quotation: " + s);
        }
        if (!stack.isEmpty()) {
            throw new NBTException("Unbalanced brackets: " + s);
        }
        if (true || !s.isEmpty()) {}
        return 1;
    }
    
    static Any func_179272_a(final String... array) throws NBTException {
        return func_150316_a(array[0], array[1]);
    }
    
    static Any func_150316_a(final String s, String s2) throws NBTException {
        s2 = s2.trim();
        if (s2.startsWith("{")) {
            s2 = s2.substring(1, s2.length() - 1);
            final Compound compound = new Compound(s);
            while (s2.length() > 0) {
                final String func_150314_a = func_150314_a(s2, true);
                if (func_150314_a.length() > 0) {
                    compound.field_150491_b.add(func_179270_a(func_150314_a, true));
                }
                if (s2.length() < func_150314_a.length() + 1) {
                    break;
                }
                final char char1 = s2.charAt(func_150314_a.length());
                if (char1 != ',' && char1 != '{' && char1 != '}' && char1 != '[' && char1 != ']') {
                    throw new NBTException("Unexpected token '" + char1 + "' at: " + s2.substring(func_150314_a.length()));
                }
                s2 = s2.substring(func_150314_a.length() + 1);
            }
            return compound;
        }
        if (s2.startsWith("[") && !JsonToNBT.field_179273_b.matcher(s2).matches()) {
            s2 = s2.substring(1, s2.length() - 1);
            final List list = new List(s);
            while (s2.length() > 0) {
                final String func_150314_a2 = func_150314_a(s2, false);
                if (func_150314_a2.length() > 0) {
                    list.field_150492_b.add(func_179270_a(func_150314_a2, true));
                }
                if (s2.length() < func_150314_a2.length() + 1) {
                    break;
                }
                final char char2 = s2.charAt(func_150314_a2.length());
                if (char2 != ',' && char2 != '{' && char2 != '}' && char2 != '[' && char2 != ']') {
                    throw new NBTException("Unexpected token '" + char2 + "' at: " + s2.substring(func_150314_a2.length()));
                }
                s2 = s2.substring(func_150314_a2.length() + 1);
            }
            return list;
        }
        return new Primitive(s, s2);
    }
    
    private static Any func_179270_a(final String s, final boolean b) throws NBTException {
        return func_179272_a(func_150313_b(s, b), func_150311_c(s, b));
    }
    
    private static String func_150314_a(final String s, final boolean b) throws NBTException {
        func_150312_a(s, ':');
        final int func_150312_a = func_150312_a(s, ',');
        if (b) {
            if (-1 == -1) {
                throw new NBTException("Unable to locate name/value separator for string: " + s);
            }
            if (func_150312_a != -1 && func_150312_a < -1) {
                throw new NBTException("Name error at: " + s);
            }
        }
        else if (-1 == -1 || -1 > func_150312_a) {}
        return func_179269_a(s, -1);
    }
    
    private static String func_179269_a(final String s, final int n) throws NBTException {
        final Stack<Character> stack = new Stack<Character>();
        int i;
        for (i = n + 1; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\"') {
                if (func_179271_b(s, i)) {
                    if (!false) {
                        throw new NBTException("Illegal use of \\\": " + s);
                    }
                }
                else {
                    final boolean b = !false;
                    if (!false || !true) {}
                    if (!false) {}
                }
            }
            else if (!false) {
                if (char1 != '{' && char1 != '[') {
                    if (char1 == '}' && (stack.isEmpty() || stack.pop() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + s);
                    }
                    if (char1 == ']' && (stack.isEmpty() || stack.pop() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + s);
                    }
                    if (char1 == ',' && stack.isEmpty()) {
                        return s.substring(0, i);
                    }
                }
                else {
                    stack.push(char1);
                }
            }
            if (!Character.isWhitespace(char1) && !false && true && i) {
                return s.substring(0, 1);
            }
        }
        return s.substring(0, i);
    }
    
    private static String func_150313_b(String trim, final boolean b) throws NBTException {
        if (b) {
            trim = trim.trim();
            if (trim.startsWith("{") || trim.startsWith("[")) {
                return "";
            }
        }
        final int func_150312_a = func_150312_a(trim, ':');
        if (func_150312_a != -1) {
            return trim.substring(0, func_150312_a).trim();
        }
        if (b) {
            return "";
        }
        throw new NBTException("Unable to locate name/value separator for string: " + trim);
    }
    
    private static String func_150311_c(String trim, final boolean b) throws NBTException {
        if (b) {
            trim = trim.trim();
            if (trim.startsWith("{") || trim.startsWith("[")) {
                return trim;
            }
        }
        final int func_150312_a = func_150312_a(trim, ':');
        if (func_150312_a != -1) {
            return trim.substring(func_150312_a + 1).trim();
        }
        if (b) {
            return trim;
        }
        throw new NBTException("Unable to locate name/value separator for string: " + trim);
    }
    
    private static int func_150312_a(final String s, final char c) {
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 == '\"') {
                if (!func_179271_b(s, 0)) {
                    final boolean b = !true;
                }
            }
            else if (true) {
                if (char1 == c) {
                    return 0;
                }
                if (char1 == '{' || char1 == '[') {
                    return -1;
                }
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    private static boolean func_179271_b(final String s, final int n) {
        return n > 0 && s.charAt(n - 1) == '\\' && !func_179271_b(s, n - 1);
    }
    
    abstract static class Any
    {
        protected String field_150490_a;
        private static final String __OBFID;
        
        public abstract NBTBase func_150489_a();
        
        static {
            __OBFID = "CL_00001233";
        }
    }
    
    static class Compound extends Any
    {
        protected java.util.List field_150491_b;
        private static final String __OBFID;
        
        public Compound(final String field_150490_a) {
            this.field_150491_b = Lists.newArrayList();
            this.field_150490_a = field_150490_a;
        }
        
        @Override
        public NBTBase func_150489_a() {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            for (final Any any : this.field_150491_b) {
                nbtTagCompound.setTag(any.field_150490_a, any.func_150489_a());
            }
            return nbtTagCompound;
        }
        
        static {
            __OBFID = "CL_00001234";
        }
    }
    
    static class List extends Any
    {
        protected java.util.List field_150492_b;
        private static final String __OBFID;
        
        public List(final String field_150490_a) {
            this.field_150492_b = Lists.newArrayList();
            this.field_150490_a = field_150490_a;
        }
        
        @Override
        public NBTBase func_150489_a() {
            final NBTTagList list = new NBTTagList();
            final Iterator<Any> iterator = this.field_150492_b.iterator();
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().func_150489_a());
            }
            return list;
        }
        
        static {
            __OBFID = "CL_00001235";
        }
    }
    
    static class Primitive extends Any
    {
        private static final Pattern field_179265_c;
        private static final Pattern field_179263_d;
        private static final Pattern field_179264_e;
        private static final Pattern field_179261_f;
        private static final Pattern field_179262_g;
        private static final Pattern field_179267_h;
        private static final Pattern field_179268_i;
        private static final Splitter field_179266_j;
        protected String field_150493_b;
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00001236";
            field_179265_c = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
            field_179263_d = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
            field_179264_e = Pattern.compile("[-+]?[0-9]+[b|B]");
            field_179261_f = Pattern.compile("[-+]?[0-9]+[l|L]");
            field_179262_g = Pattern.compile("[-+]?[0-9]+[s|S]");
            field_179267_h = Pattern.compile("[-+]?[0-9]+");
            field_179268_i = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
            field_179266_j = Splitter.on(',').omitEmptyStrings();
        }
        
        public Primitive(final String field_150490_a, final String field_150493_b) {
            this.field_150490_a = field_150490_a;
            this.field_150493_b = field_150493_b;
        }
        
        @Override
        public NBTBase func_150489_a() {
            if (Primitive.field_179265_c.matcher(this.field_150493_b).matches()) {
                return new NBTTagDouble(Double.parseDouble(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179263_d.matcher(this.field_150493_b).matches()) {
                return new NBTTagFloat(Float.parseFloat(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179264_e.matcher(this.field_150493_b).matches()) {
                return new NBTTagByte(Byte.parseByte(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179261_f.matcher(this.field_150493_b).matches()) {
                return new NBTTagLong(Long.parseLong(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179262_g.matcher(this.field_150493_b).matches()) {
                return new NBTTagShort(Short.parseShort(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179267_h.matcher(this.field_150493_b).matches()) {
                return new NBTTagInt(Integer.parseInt(this.field_150493_b));
            }
            if (Primitive.field_179268_i.matcher(this.field_150493_b).matches()) {
                return new NBTTagDouble(Double.parseDouble(this.field_150493_b));
            }
            if (this.field_150493_b.equalsIgnoreCase("true") || this.field_150493_b.equalsIgnoreCase("false")) {
                return new NBTTagByte((byte)(Boolean.parseBoolean(this.field_150493_b) ? 1 : 0));
            }
            if (this.field_150493_b.startsWith("[") && this.field_150493_b.endsWith("]")) {
                final String[] array = (String[])Iterables.toArray(Primitive.field_179266_j.split(this.field_150493_b.substring(1, this.field_150493_b.length() - 1)), String.class);
                final int[] array2 = new int[array.length];
                while (0 < array.length) {
                    array2[0] = Integer.parseInt(array[0].trim());
                    int n = 0;
                    ++n;
                }
                return new NBTTagIntArray(array2);
            }
            if (this.field_150493_b.startsWith("\"") && this.field_150493_b.endsWith("\"")) {
                this.field_150493_b = this.field_150493_b.substring(1, this.field_150493_b.length() - 1);
            }
            this.field_150493_b = this.field_150493_b.replaceAll("\\\\\"", "\"");
            final StringBuilder sb = new StringBuilder();
            while (0 < this.field_150493_b.length()) {
                int n2 = 0;
                if (0 < this.field_150493_b.length() - 1 && this.field_150493_b.charAt(0) == '\\' && this.field_150493_b.charAt(1) == '\\') {
                    sb.append('\\');
                    ++n2;
                }
                else {
                    sb.append(this.field_150493_b.charAt(0));
                }
                ++n2;
            }
            return new NBTTagString(sb.toString());
        }
    }
}
