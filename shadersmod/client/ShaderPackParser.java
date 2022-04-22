package shadersmod.client;

import optifine.*;
import java.util.regex.*;
import java.io.*;
import java.util.*;

public class ShaderPackParser
{
    private static final Pattern PATTERN_INCLUDE;
    private static final Set setConstNames;
    
    static {
        PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
        setConstNames = makeSetConstNames();
    }
    
    public static ShaderOption[] parseShaderPackOptions(final IShaderPack shaderPack, final String[] array, final List list) {
        if (shaderPack == null) {
            return new ShaderOption[0];
        }
        final HashMap hashMap = new HashMap();
        collectShaderOptions(shaderPack, "/shaders", array, hashMap);
        final Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            collectShaderOptions(shaderPack, "/shaders/world" + (int)iterator.next(), array, hashMap);
        }
        final Collection values = hashMap.values();
        final ShaderOption[] array2 = (ShaderOption[])values.toArray(new ShaderOption[values.size()]);
        Arrays.sort(array2, new Comparator() {
            public int compare(final ShaderOption shaderOption, final ShaderOption shaderOption2) {
                return shaderOption.getName().compareToIgnoreCase(shaderOption2.getName());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((ShaderOption)o, (ShaderOption)o2);
            }
        });
        return array2;
    }
    
    private static void collectShaderOptions(final IShaderPack shaderPack, final String s, final String[] array, final Map map) {
        while (0 < array.length) {
            final String s2 = array[0];
            if (!s2.equals("")) {
                final String string = String.valueOf(s) + "/" + s2 + ".vsh";
                final String string2 = String.valueOf(s) + "/" + s2 + ".fsh";
                collectShaderOptions(shaderPack, string, map);
                collectShaderOptions(shaderPack, string2, map);
            }
            int n = 0;
            ++n;
        }
    }
    
    private static void collectShaderOptions(final IShaderPack p0, final String p1, final Map p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokestatic    shadersmod/client/ShaderPackParser.getLines:(Lshadersmod/client/IShaderPack;Ljava/lang/String;)[Ljava/lang/String;
        //     5: astore_3       
        //     6: goto            245
        //     9: aload_3        
        //    10: iconst_0       
        //    11: aaload         
        //    12: astore          5
        //    14: aload           5
        //    16: aload_1        
        //    17: invokestatic    shadersmod/client/ShaderPackParser.getShaderOption:(Ljava/lang/String;Ljava/lang/String;)Lshadersmod/client/ShaderOption;
        //    20: astore          6
        //    22: aload           6
        //    24: ifnull          242
        //    27: aload           6
        //    29: invokevirtual   shadersmod/client/ShaderOption.checkUsed:()Z
        //    32: ifeq            41
        //    35: aload           6
        //    37: aload_3        
        //    38: ifeq            242
        //    41: aload           6
        //    43: invokevirtual   shadersmod/client/ShaderOption.getName:()Ljava/lang/String;
        //    46: astore          7
        //    48: aload_2        
        //    49: aload           7
        //    51: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    56: checkcast       Lshadersmod/client/ShaderOption;
        //    59: astore          8
        //    61: aload           8
        //    63: ifnull          231
        //    66: aload           8
        //    68: invokevirtual   shadersmod/client/ShaderOption.getValueDefault:()Ljava/lang/String;
        //    71: aload           6
        //    73: invokevirtual   shadersmod/client/ShaderOption.getValueDefault:()Ljava/lang/String;
        //    76: invokestatic    optifine/Config.equals:(Ljava/lang/Object;Ljava/lang/Object;)Z
        //    79: ifne            189
        //    82: new             Ljava/lang/StringBuilder;
        //    85: dup            
        //    86: ldc             "Ambiguous shader option: "
        //    88: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    91: aload           6
        //    93: invokevirtual   shadersmod/client/ShaderOption.getName:()Ljava/lang/String;
        //    96: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    99: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   102: invokestatic    optifine/Config.warn:(Ljava/lang/String;)V
        //   105: new             Ljava/lang/StringBuilder;
        //   108: dup            
        //   109: ldc             " - in "
        //   111: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   114: aload           8
        //   116: invokevirtual   shadersmod/client/ShaderOption.getPaths:()[Ljava/lang/String;
        //   119: invokestatic    optifine/Config.arrayToString:([Ljava/lang/Object;)Ljava/lang/String;
        //   122: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   125: ldc             ": "
        //   127: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   130: aload           8
        //   132: invokevirtual   shadersmod/client/ShaderOption.getValueDefault:()Ljava/lang/String;
        //   135: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   138: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   141: invokestatic    optifine/Config.warn:(Ljava/lang/String;)V
        //   144: new             Ljava/lang/StringBuilder;
        //   147: dup            
        //   148: ldc             " - in "
        //   150: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   153: aload           6
        //   155: invokevirtual   shadersmod/client/ShaderOption.getPaths:()[Ljava/lang/String;
        //   158: invokestatic    optifine/Config.arrayToString:([Ljava/lang/Object;)Ljava/lang/String;
        //   161: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   164: ldc             ": "
        //   166: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   169: aload           6
        //   171: invokevirtual   shadersmod/client/ShaderOption.getValueDefault:()Ljava/lang/String;
        //   174: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   177: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   180: invokestatic    optifine/Config.warn:(Ljava/lang/String;)V
        //   183: aload           8
        //   185: iconst_0       
        //   186: invokevirtual   shadersmod/client/ShaderOption.setEnabled:(Z)V
        //   189: aload           8
        //   191: invokevirtual   shadersmod/client/ShaderOption.getDescription:()Ljava/lang/String;
        //   194: ifnull          208
        //   197: aload           8
        //   199: invokevirtual   shadersmod/client/ShaderOption.getDescription:()Ljava/lang/String;
        //   202: invokevirtual   java/lang/String.length:()I
        //   205: ifgt            218
        //   208: aload           8
        //   210: aload           6
        //   212: invokevirtual   shadersmod/client/ShaderOption.getDescription:()Ljava/lang/String;
        //   215: invokevirtual   shadersmod/client/ShaderOption.setDescription:(Ljava/lang/String;)V
        //   218: aload           8
        //   220: aload           6
        //   222: invokevirtual   shadersmod/client/ShaderOption.getPaths:()[Ljava/lang/String;
        //   225: invokevirtual   shadersmod/client/ShaderOption.addPaths:([Ljava/lang/String;)V
        //   228: goto            242
        //   231: aload_2        
        //   232: aload           7
        //   234: aload           6
        //   236: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   241: pop            
        //   242: iinc            4, 1
        //   245: iconst_0       
        //   246: aload_3        
        //   247: arraylength    
        //   248: if_icmplt       9
        //   251: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0041 (coming from #0038).
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
    
    private static String[] getLines(final IShaderPack shaderPack, final String s) {
        final String loadFile = loadFile(s, shaderPack, 0);
        if (loadFile == null) {
            return new String[0];
        }
        return Config.readLines(new ByteArrayInputStream(loadFile.getBytes()));
    }
    
    private static ShaderOption getShaderOption(final String s, final String s2) {
        ShaderOption shaderOption = null;
        if (shaderOption == null) {
            shaderOption = ShaderOptionSwitch.parseOption(s, s2);
        }
        if (shaderOption == null) {
            shaderOption = ShaderOptionVariable.parseOption(s, s2);
        }
        if (shaderOption != null) {
            return shaderOption;
        }
        if (shaderOption == null) {
            shaderOption = ShaderOptionSwitchConst.parseOption(s, s2);
        }
        if (shaderOption == null) {
            shaderOption = ShaderOptionVariableConst.parseOption(s, s2);
        }
        return (shaderOption != null && ShaderPackParser.setConstNames.contains(shaderOption.getName())) ? shaderOption : null;
    }
    
    private static Set makeSetConstNames() {
        final HashSet<String> set = new HashSet<String>();
        set.add("shadowMapResolution");
        set.add("shadowDistance");
        set.add("shadowIntervalSize");
        set.add("generateShadowMipmap");
        set.add("generateShadowColorMipmap");
        set.add("shadowHardwareFiltering");
        set.add("shadowHardwareFiltering0");
        set.add("shadowHardwareFiltering1");
        set.add("shadowtex0Mipmap");
        set.add("shadowtexMipmap");
        set.add("shadowtex1Mipmap");
        set.add("shadowcolor0Mipmap");
        set.add("shadowColor0Mipmap");
        set.add("shadowcolor1Mipmap");
        set.add("shadowColor1Mipmap");
        set.add("shadowtex0Nearest");
        set.add("shadowtexNearest");
        set.add("shadow0MinMagNearest");
        set.add("shadowtex1Nearest");
        set.add("shadow1MinMagNearest");
        set.add("shadowcolor0Nearest");
        set.add("shadowColor0Nearest");
        set.add("shadowColor0MinMagNearest");
        set.add("shadowcolor1Nearest");
        set.add("shadowColor1Nearest");
        set.add("shadowColor1MinMagNearest");
        set.add("wetnessHalflife");
        set.add("drynessHalflife");
        set.add("eyeBrightnessHalflife");
        set.add("centerDepthHalflife");
        set.add("sunPathRotation");
        set.add("ambientOcclusionLevel");
        set.add("superSamplingLevel");
        set.add("noiseTextureResolution");
        return set;
    }
    
    public static ShaderProfile[] parseProfiles(final Properties properties, final ShaderOption[] array) {
        final String s = "profile.";
        final ArrayList<ShaderProfile> list = new ArrayList<ShaderProfile>();
        for (final String s2 : ((Hashtable<String, V>)properties).keySet()) {
            if (s2.startsWith(s)) {
                final String substring = s2.substring(s.length());
                properties.getProperty(s2);
                final ShaderProfile profile = parseProfile(substring, properties, new HashSet(), array);
                if (profile == null) {
                    continue;
                }
                list.add(profile);
            }
        }
        if (list.size() <= 0) {
            return null;
        }
        return list.toArray(new ShaderProfile[list.size()]);
    }
    
    private static ShaderProfile parseProfile(final String s, final Properties properties, final Set set, final ShaderOption[] array) {
        final String s2 = "profile.";
        final String string = String.valueOf(s2) + s;
        if (set.contains(string)) {
            Config.warn("[Shaders] Profile already parsed: " + s);
            return null;
        }
        set.add(s);
        final ShaderProfile shaderProfile = new ShaderProfile(s);
        final String[] tokenize = Config.tokenize(properties.getProperty(string), " ");
        while (0 < tokenize.length) {
            final String s3 = tokenize[0];
            if (s3.startsWith(s2)) {
                final ShaderProfile profile = parseProfile(s3.substring(s2.length()), properties, set, array);
                if (shaderProfile != null) {
                    shaderProfile.addOptionValues(profile);
                    shaderProfile.addDisabledPrograms(profile.getDisabledPrograms());
                }
            }
            else {
                final String[] tokenize2 = Config.tokenize(s3, ":=");
                if (tokenize2.length == 1) {
                    String substring = tokenize2[0];
                    if (substring.startsWith("!")) {
                        substring = substring.substring(1);
                    }
                    final String s4 = "program.";
                    if (substring.startsWith("program.")) {
                        final String substring2 = substring.substring(s4.length());
                        if (!Shaders.isProgramPath(substring2)) {
                            Config.warn("Invalid program: " + substring2 + " in profile: " + shaderProfile.getName());
                        }
                        else {
                            shaderProfile.addDisabledProgram(substring2);
                        }
                    }
                    else {
                        final ShaderOption shaderOption = ShaderUtils.getShaderOption(substring, array);
                        if (!(shaderOption instanceof ShaderOptionSwitch)) {
                            Config.warn("[Shaders] Invalid option: " + substring);
                        }
                        else {
                            shaderProfile.addOptionValue(substring, String.valueOf(false));
                            shaderOption.setVisible(true);
                        }
                    }
                }
                else if (tokenize2.length != 2) {
                    Config.warn("[Shaders] Invalid option value: " + s3);
                }
                else {
                    final String s5 = tokenize2[0];
                    final String s6 = tokenize2[1];
                    final ShaderOption shaderOption2 = ShaderUtils.getShaderOption(s5, array);
                    if (shaderOption2 == null) {
                        Config.warn("[Shaders] Invalid option: " + s3);
                    }
                    else if (!shaderOption2.isValidValue(s6)) {
                        Config.warn("[Shaders] Invalid value: " + s3);
                    }
                    else {
                        shaderOption2.setVisible(true);
                        shaderProfile.addOptionValue(s5, s6);
                    }
                }
            }
            int n = 0;
            ++n;
        }
        return shaderProfile;
    }
    
    public static Map parseGuiScreens(final Properties properties, final ShaderProfile[] array, final ShaderOption[] array2) {
        final HashMap hashMap = new HashMap();
        parseGuiScreen("screen", properties, (Map)hashMap, array, array2);
        return hashMap.isEmpty() ? null : hashMap;
    }
    
    public static BufferedReader resolveIncludes(final BufferedReader bufferedReader, final String s, final IShaderPack shaderPack, final int n) throws IOException {
        String substring = "/";
        final int lastIndex = s.lastIndexOf("/");
        if (lastIndex >= 0) {
            substring = s.substring(0, lastIndex);
        }
        final CharArrayWriter charArrayWriter = new CharArrayWriter();
        while (true) {
            String s2 = bufferedReader.readLine();
            if (s2 == null) {
                return new BufferedReader(new CharArrayReader(charArrayWriter.toCharArray()));
            }
            final Matcher matcher = ShaderPackParser.PATTERN_INCLUDE.matcher(s2);
            if (matcher.matches()) {
                final String group = matcher.group(1);
                s2 = loadFile(group.startsWith("/") ? ("/shaders" + group) : (String.valueOf(substring) + "/" + group), shaderPack, n);
                if (s2 == null) {
                    throw new IOException("Included file not found: " + s);
                }
            }
            charArrayWriter.write(s2);
            charArrayWriter.write("\n");
        }
    }
    
    private static String loadFile(final String s, final IShaderPack shaderPack, int n) throws IOException {
        if (n >= 10) {
            throw new IOException("#include depth exceeded: " + n + ", file: " + s);
        }
        ++n;
        final InputStream resourceAsStream = shaderPack.getResourceAsStream(s);
        if (resourceAsStream == null) {
            return null;
        }
        final BufferedReader resolveIncludes = resolveIncludes(new BufferedReader(new InputStreamReader(resourceAsStream, "ASCII")), s, shaderPack, n);
        final CharArrayWriter charArrayWriter = new CharArrayWriter();
        while (true) {
            final String line = resolveIncludes.readLine();
            if (line == null) {
                break;
            }
            charArrayWriter.write(line);
            charArrayWriter.write("\n");
        }
        return charArrayWriter.toString();
    }
}
