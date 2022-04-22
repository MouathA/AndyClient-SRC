package optifine;

import java.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.nbt.*;

public class NbtTagValue
{
    private String[] parents;
    private String name;
    private int type;
    private String value;
    private static final String PREFIX_PATTERN;
    private static final String PREFIX_IPATTERN;
    private static final String PREFIX_REGEX;
    private static final String PREFIX_IREGEX;
    
    public NbtTagValue(final String s, String value) {
        this.parents = null;
        this.name = null;
        this.type = 0;
        this.value = null;
        final String[] tokenize = Config.tokenize(s, ".");
        this.parents = Arrays.copyOfRange(tokenize, 0, tokenize.length - 1);
        this.name = tokenize[tokenize.length - 1];
        if (value.startsWith("pattern:")) {
            this.type = 1;
            value = value.substring(8);
        }
        else if (value.startsWith("ipattern:")) {
            this.type = 2;
            value = value.substring(9).toLowerCase();
        }
        else if (value.startsWith("regex:")) {
            this.type = 3;
            value = value.substring(6);
        }
        else if (value.startsWith("iregex:")) {
            this.type = 4;
            value = value.substring(7).toLowerCase();
        }
        else {
            this.type = 0;
        }
        value = StringEscapeUtils.unescapeJava(value);
        this.value = value;
    }
    
    public boolean matches(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound == null) {
            return false;
        }
        NBTBase childTag = nbtTagCompound;
        while (0 < this.parents.length) {
            childTag = getChildTag(childTag, this.parents[0]);
            if (childTag == null) {
                return false;
            }
            int n = 0;
            ++n;
        }
        if (this.name.equals("*")) {
            return this.matchesAnyChild(childTag);
        }
        final NBTBase childTag2 = getChildTag(childTag, this.name);
        return childTag2 != null && childTag2 == null;
    }
    
    private boolean matchesAnyChild(final NBTBase p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Lnet/minecraft/nbt/NBTTagCompound;
        //     4: ifeq            66
        //     7: aload_1        
        //     8: checkcast       Lnet/minecraft/nbt/NBTTagCompound;
        //    11: astore_2       
        //    12: aload_2        
        //    13: invokevirtual   net/minecraft/nbt/NBTTagCompound.getKeySet:()Ljava/util/Set;
        //    16: astore_3       
        //    17: aload_3        
        //    18: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    23: astore          4
        //    25: goto            56
        //    28: aload           4
        //    30: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    35: checkcast       Ljava/lang/String;
        //    38: astore          5
        //    40: aload_2        
        //    41: aload           5
        //    43: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTBase;
        //    46: astore          6
        //    48: aload_0        
        //    49: aload           6
        //    51: ifnonnull       56
        //    54: iconst_1       
        //    55: ireturn        
        //    56: aload           4
        //    58: invokeinterface java/util/Iterator.hasNext:()Z
        //    63: ifne            28
        //    66: aload_1        
        //    67: instanceof      Lnet/minecraft/nbt/NBTTagList;
        //    70: ifeq            109
        //    73: aload_1        
        //    74: checkcast       Lnet/minecraft/nbt/NBTTagList;
        //    77: astore_2       
        //    78: aload_2        
        //    79: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //    82: istore_3       
        //    83: goto            104
        //    86: aload_2        
        //    87: iconst_0       
        //    88: invokevirtual   net/minecraft/nbt/NBTTagList.get:(I)Lnet/minecraft/nbt/NBTBase;
        //    91: astore          5
        //    93: aload_0        
        //    94: aload           5
        //    96: ifnonnull       101
        //    99: iconst_1       
        //   100: ireturn        
        //   101: iinc            4, 1
        //   104: iconst_0       
        //   105: iload_3        
        //   106: if_icmplt       86
        //   109: iconst_0       
        //   110: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0104 (coming from #0101).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static NBTBase getChildTag(final NBTBase nbtBase, final String s) {
        if (nbtBase instanceof NBTTagCompound) {
            return ((NBTTagCompound)nbtBase).getTag(s);
        }
        if (nbtBase instanceof NBTTagList) {
            final NBTTagList list = (NBTTagList)nbtBase;
            final int int1 = Config.parseInt(s, -1);
            return (int1 < 0) ? null : list.get(int1);
        }
        return null;
    }
    
    private boolean matchesPattern(final String s, final String s2) {
        return StrUtils.equalsMask(s, s2, '*', '?');
    }
    
    private boolean matchesRegex(final String s, final String s2) {
        return s.matches(s2);
    }
    
    private static String getValue(final NBTBase nbtBase) {
        if (nbtBase == null) {
            return null;
        }
        if (nbtBase instanceof NBTTagString) {
            return ((NBTTagString)nbtBase).getString();
        }
        if (nbtBase instanceof NBTTagInt) {
            return Integer.toString(((NBTTagInt)nbtBase).getInt());
        }
        if (nbtBase instanceof NBTTagByte) {
            return Byte.toString(((NBTTagByte)nbtBase).getByte());
        }
        if (nbtBase instanceof NBTTagShort) {
            return Short.toString(((NBTTagShort)nbtBase).getShort());
        }
        if (nbtBase instanceof NBTTagLong) {
            return Long.toString(((NBTTagLong)nbtBase).getLong());
        }
        if (nbtBase instanceof NBTTagFloat) {
            return Float.toString(((NBTTagFloat)nbtBase).getFloat());
        }
        if (nbtBase instanceof NBTTagDouble) {
            return Double.toString(((NBTTagDouble)nbtBase).getDouble());
        }
        return nbtBase.toString();
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        while (0 < this.parents.length) {
            sb.append(this.parents[0]);
            int n = 0;
            ++n;
        }
        if (sb.length() > 0) {
            sb.append(".");
        }
        sb.append(this.name);
        sb.append(" = ");
        sb.append(this.value);
        return sb.toString();
    }
    
    static {
        PREFIX_IREGEX = "iregex:";
        PREFIX_REGEX = "regex:";
        PREFIX_PATTERN = "pattern:";
        PREFIX_IPATTERN = "ipattern:";
    }
}
