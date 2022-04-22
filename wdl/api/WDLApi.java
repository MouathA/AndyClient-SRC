package wdl.api;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import wdl.chan.*;
import wdl.*;
import java.util.*;
import com.google.common.collect.*;

public class WDLApi
{
    private static Logger logger;
    private static Map wdlMods;
    
    static {
        WDLApi.logger = LogManager.getLogger();
        WDLApi.wdlMods = new HashMap();
        WDLApi.logger.info("Loading default WDL extensions");
        addWDLMod("Hologram", "1.0", new HologramHandler());
        addWDLMod("EntityRealigner", "1.0", new EntityRealigner());
    }
    
    public static void saveTileEntity(final BlockPos blockPos, final TileEntity tileEntity) {
        if (!WDLPluginChannels.canSaveTileEntities(blockPos.getX() << 16, blockPos.getZ() << 16)) {
            WDLApi.logger.warn("API attempted to call saveTileEntity when saving TileEntities is not allowed!  Pos: " + blockPos + ", te: " + tileEntity + ".  StackTrace: ");
            return;
        }
        WDL.saveTileEntity(blockPos, tileEntity);
    }
    
    public static void addWDLMod(final String s, final String s2, final IWDLMod iwdlMod) {
        if (s == null) {
            throw new IllegalArgumentException("id must not be null!  (mod=" + iwdlMod + ", version=" + s2 + ")");
        }
        if (s2 == null) {
            throw new IllegalArgumentException("version must not be null!  (mod=" + iwdlMod + ", id=" + s2 + ")");
        }
        if (iwdlMod == null) {
            throw new IllegalArgumentException("mod must not be null!  (id=" + s + ", version=" + s2 + ")");
        }
        final ModInfo modInfo = new ModInfo(s, s2, iwdlMod, null);
        if (WDLApi.wdlMods.containsKey(s)) {
            throw new IllegalArgumentException("A mod by the name of '" + s + "' is already registered by " + WDLApi.wdlMods.get(s) + " (tried to register " + modInfo + " over it)");
        }
        if (iwdlMod.isValidEnvironment("1.8.9a-beta2")) {
            WDLApi.wdlMods.put(s, modInfo);
            if (iwdlMod instanceof IMessageTypeAdder) {
                final Map messageTypes = ((IMessageTypeAdder)iwdlMod).getMessageTypes();
                final ModMessageTypeCategory modMessageTypeCategory = new ModMessageTypeCategory(modInfo);
                for (final Map.Entry<String, V> entry : messageTypes.entrySet()) {
                    WDLMessages.registerMessage(entry.getKey(), (IWDLMessageType)entry.getValue(), modMessageTypeCategory);
                }
            }
            return;
        }
        final String environmentErrorMessage = iwdlMod.getEnvironmentErrorMessage("1.8.9a-beta2");
        if (environmentErrorMessage != null) {
            throw new IllegalArgumentException(environmentErrorMessage);
        }
        throw new IllegalArgumentException("Environment for " + modInfo + " is incorrect!  Perhaps it is for a different" + " version of WDL?  You are running " + "1.8.9a-beta2" + ".");
    }
    
    public static List getImplementingExtensions(final Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz must not be null!");
        }
        final ArrayList<ModInfo> list = new ArrayList<ModInfo>();
        for (final ModInfo modInfo : WDLApi.wdlMods.values()) {
            if (!modInfo.isEnabled()) {
                continue;
            }
            if (!clazz.isAssignableFrom(modInfo.mod.getClass())) {
                continue;
            }
            list.add(modInfo);
        }
        return list;
    }
    
    public static List getAllImplementingExtensions(final Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz must not be null!");
        }
        final ArrayList<ModInfo> list = new ArrayList<ModInfo>();
        for (final ModInfo modInfo : WDLApi.wdlMods.values()) {
            if (clazz.isAssignableFrom(modInfo.mod.getClass())) {
                list.add(modInfo);
            }
        }
        return list;
    }
    
    public static Map getWDLMods() {
        return ImmutableMap.copyOf(WDLApi.wdlMods);
    }
    
    public static String getModInfo(final String s) {
        if (!WDLApi.wdlMods.containsKey(s)) {
            return null;
        }
        return WDLApi.wdlMods.get(s).getInfo();
    }
    
    private static void logStackTrace() {
        StackTraceElement[] stackTrace;
        while (0 < (stackTrace = Thread.currentThread().getStackTrace()).length) {
            WDLApi.logger.warn(stackTrace[0].toString());
            int n = 0;
            ++n;
        }
    }
    
    public static class ModInfo
    {
        public final String id;
        public final String version;
        public final IWDLMod mod;
        
        private ModInfo(final String id, final String version, final IWDLMod mod) {
            this.id = id;
            this.version = version;
            this.mod = mod;
        }
        
        @Override
        public String toString() {
            return String.valueOf(this.id) + "v" + this.version + " (" + this.mod.toString() + "/" + this.mod.getClass().getName() + ")";
        }
        
        public String getDisplayName() {
            if (this.mod instanceof IWDLModDescripted) {
                final String displayName = ((IWDLModDescripted)this.mod).getDisplayName();
                if (displayName != null && !displayName.isEmpty()) {
                    return displayName;
                }
            }
            return this.id;
        }
        
        public String getInfo() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: dup            
            //     4: invokespecial   java/lang/StringBuilder.<init>:()V
            //     7: astore_1       
            //     8: aload_1        
            //     9: ldc             "Id: "
            //    11: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    14: aload_0        
            //    15: getfield        wdl/api/WDLApi$ModInfo.id:Ljava/lang/String;
            //    18: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    21: bipush          10
            //    23: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //    26: pop            
            //    27: aload_1        
            //    28: ldc             "Version: "
            //    30: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    33: aload_0        
            //    34: getfield        wdl/api/WDLApi$ModInfo.version:Ljava/lang/String;
            //    37: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    40: bipush          10
            //    42: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //    45: pop            
            //    46: aload_0        
            //    47: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //    50: instanceof      Lwdl/api/IWDLModDescripted;
            //    53: ifeq            311
            //    56: aload_0        
            //    57: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //    60: checkcast       Lwdl/api/IWDLModDescripted;
            //    63: astore_2       
            //    64: aload_2        
            //    65: invokeinterface wdl/api/IWDLModDescripted.getDisplayName:()Ljava/lang/String;
            //    70: astore_3       
            //    71: aload_2        
            //    72: invokeinterface wdl/api/IWDLModDescripted.getMainAuthor:()Ljava/lang/String;
            //    77: astore          4
            //    79: aload_2        
            //    80: invokeinterface wdl/api/IWDLModDescripted.getAuthors:()[Ljava/lang/String;
            //    85: astore          5
            //    87: aload_2        
            //    88: invokeinterface wdl/api/IWDLModDescripted.getURL:()Ljava/lang/String;
            //    93: astore          6
            //    95: aload_2        
            //    96: invokeinterface wdl/api/IWDLModDescripted.getDescription:()Ljava/lang/String;
            //   101: astore          7
            //   103: aload_3        
            //   104: ifnull          130
            //   107: aload_3        
            //   108: invokevirtual   java/lang/String.isEmpty:()Z
            //   111: ifne            130
            //   114: aload_1        
            //   115: ldc             "Display name: "
            //   117: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   120: aload_3        
            //   121: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   124: bipush          10
            //   126: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   129: pop            
            //   130: aload           4
            //   132: ifnull          160
            //   135: aload           4
            //   137: invokevirtual   java/lang/String.isEmpty:()Z
            //   140: ifne            160
            //   143: aload_1        
            //   144: ldc             "Main author: "
            //   146: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   149: aload           4
            //   151: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   154: bipush          10
            //   156: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   159: pop            
            //   160: aload           5
            //   162: ifnull          251
            //   165: aload           5
            //   167: arraylength    
            //   168: ifle            251
            //   171: aload_1        
            //   172: ldc             "Authors: "
            //   174: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   177: pop            
            //   178: goto            244
            //   181: aload           5
            //   183: iconst_0       
            //   184: aaload         
            //   185: aload           4
            //   187: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
            //   190: ifeq            196
            //   193: goto            241
            //   196: iconst_0       
            //   197: aload           5
            //   199: arraylength    
            //   200: iconst_2       
            //   201: isub           
            //   202: if_icmpgt       215
            //   205: aload_1        
            //   206: ldc             ", "
            //   208: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   211: pop            
            //   212: goto            241
            //   215: iconst_0       
            //   216: aload           5
            //   218: arraylength    
            //   219: iconst_1       
            //   220: isub           
            //   221: if_icmpne       234
            //   224: aload_1        
            //   225: ldc             ", and "
            //   227: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   230: pop            
            //   231: goto            241
            //   234: aload_1        
            //   235: bipush          10
            //   237: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   240: pop            
            //   241: iinc            8, 1
            //   244: iconst_0       
            //   245: aload           5
            //   247: arraylength    
            //   248: if_icmplt       181
            //   251: aload           6
            //   253: ifnull          281
            //   256: aload           6
            //   258: invokevirtual   java/lang/String.isEmpty:()Z
            //   261: ifne            281
            //   264: aload_1        
            //   265: ldc             "URL: "
            //   267: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   270: aload           6
            //   272: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   275: bipush          10
            //   277: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   280: pop            
            //   281: aload           7
            //   283: ifnull          311
            //   286: aload           7
            //   288: invokevirtual   java/lang/String.isEmpty:()Z
            //   291: ifne            311
            //   294: aload_1        
            //   295: ldc             "Description: \n"
            //   297: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   300: aload           7
            //   302: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   305: bipush          10
            //   307: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   310: pop            
            //   311: aload_1        
            //   312: ldc             "Main class: "
            //   314: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   317: aload_0        
            //   318: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //   321: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //   324: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
            //   327: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   330: bipush          10
            //   332: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   335: pop            
            //   336: aload_1        
            //   337: ldc             "Containing file: "
            //   339: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   342: pop            
            //   343: new             Ljava/io/File;
            //   346: dup            
            //   347: aload_0        
            //   348: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //   351: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //   354: invokevirtual   java/lang/Class.getProtectionDomain:()Ljava/security/ProtectionDomain;
            //   357: invokevirtual   java/security/ProtectionDomain.getCodeSource:()Ljava/security/CodeSource;
            //   360: invokevirtual   java/security/CodeSource.getLocation:()Ljava/net/URL;
            //   363: invokevirtual   java/net/URL.toURI:()Ljava/net/URI;
            //   366: invokespecial   java/io/File.<init>:(Ljava/net/URI;)V
            //   369: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
            //   372: astore_2       
            //   373: ldc             "user.name"
            //   375: invokestatic    java/lang/System.getProperty:(Ljava/lang/String;)Ljava/lang/String;
            //   378: astore_3       
            //   379: aload_2        
            //   380: aload_3        
            //   381: ldc             "<USERNAME>"
            //   383: invokevirtual   java/lang/String.replace:(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
            //   386: astore_2       
            //   387: aload_1        
            //   388: aload_2        
            //   389: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   392: pop            
            //   393: goto            416
            //   396: astore_2       
            //   397: aload_1        
            //   398: ldc             "Unknown ("
            //   400: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   403: aload_2        
            //   404: invokevirtual   java/lang/Exception.toString:()Ljava/lang/String;
            //   407: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   410: bipush          41
            //   412: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   415: pop            
            //   416: aload_1        
            //   417: bipush          10
            //   419: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   422: pop            
            //   423: aload_0        
            //   424: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //   427: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //   430: invokevirtual   java/lang/Class.getInterfaces:()[Ljava/lang/Class;
            //   433: astore_2       
            //   434: aload_1        
            //   435: ldc             "Implemented interfaces ("
            //   437: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   440: aload_2        
            //   441: arraylength    
            //   442: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
            //   445: ldc             ")\n"
            //   447: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   450: pop            
            //   451: goto            482
            //   454: aload_1        
            //   455: iconst_0       
            //   456: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
            //   459: ldc             ": "
            //   461: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   464: aload_2        
            //   465: iconst_0       
            //   466: aaload         
            //   467: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
            //   470: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   473: bipush          10
            //   475: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   478: pop            
            //   479: iinc            3, 1
            //   482: iconst_0       
            //   483: aload_2        
            //   484: arraylength    
            //   485: if_icmplt       454
            //   488: aload_1        
            //   489: ldc             "Superclass: "
            //   491: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   494: aload_0        
            //   495: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //   498: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //   501: invokevirtual   java/lang/Class.getSuperclass:()Ljava/lang/Class;
            //   504: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
            //   507: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   510: bipush          10
            //   512: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   515: pop            
            //   516: aload_0        
            //   517: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //   520: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //   523: invokevirtual   java/lang/Class.getClassLoader:()Ljava/lang/ClassLoader;
            //   526: astore_3       
            //   527: aload_1        
            //   528: ldc             "Classloader: "
            //   530: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   533: aload_3        
            //   534: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
            //   537: pop            
            //   538: aload_3        
            //   539: ifnull          564
            //   542: aload_1        
            //   543: ldc             " ("
            //   545: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   548: aload_3        
            //   549: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //   552: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
            //   555: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   558: bipush          41
            //   560: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   563: pop            
            //   564: aload_1        
            //   565: bipush          10
            //   567: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
            //   570: pop            
            //   571: aload_0        
            //   572: getfield        wdl/api/WDLApi$ModInfo.mod:Lwdl/api/IWDLMod;
            //   575: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //   578: invokevirtual   java/lang/Class.getAnnotations:()[Ljava/lang/annotation/Annotation;
            //   581: astore          4
            //   583: aload_1        
            //   584: ldc             "Annotations ("
            //   586: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   589: aload           4
            //   591: arraylength    
            //   592: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
            //   595: ldc             ")\n"
            //   597: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   600: pop            
            //   601: goto            655
            //   604: aload_1        
            //   605: iconst_0       
            //   606: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
            //   609: ldc             ": "
            //   611: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   614: aload           4
            //   616: iconst_0       
            //   617: aaload         
            //   618: invokeinterface java/lang/annotation/Annotation.toString:()Ljava/lang/String;
            //   623: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   626: ldc             " ("
            //   628: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   631: aload           4
            //   633: iconst_0       
            //   634: aaload         
            //   635: invokeinterface java/lang/annotation/Annotation.annotationType:()Ljava/lang/Class;
            //   640: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
            //   643: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   646: ldc             ")\n"
            //   648: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   651: pop            
            //   652: iinc            5, 1
            //   655: iconst_0       
            //   656: aload           4
            //   658: arraylength    
            //   659: if_icmplt       604
            //   662: aload_1        
            //   663: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   666: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public boolean isEnabled() {
            return WDL.globalProps.getProperty("Extensions." + this.id + ".enabled", "true").equals("true");
        }
        
        public void setEnabled(final boolean b) {
            WDL.globalProps.setProperty("Extensions." + this.id + ".enabled", Boolean.toString(b));
        }
        
        public void toggleEnabled() {
            this.setEnabled(!this.isEnabled());
        }
        
        ModInfo(final String s, final String s2, final IWDLMod iwdlMod, final ModInfo modInfo) {
            this(s, s2, iwdlMod);
        }
    }
    
    private static class ModMessageTypeCategory extends MessageTypeCategory
    {
        private ModInfo mod;
        
        public ModMessageTypeCategory(final ModInfo modInfo) {
            super(modInfo.id);
        }
        
        @Override
        public String getDisplayName() {
            return this.mod.getDisplayName();
        }
    }
}
