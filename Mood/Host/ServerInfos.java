package Mood.Host;

import Mood.Host.Helper.*;
import com.google.gson.*;
import java.util.*;

public class ServerInfos
{
    private String serverIP;
    private String serverMotd;
    private String populationInfo;
    private String gameVersion;
    private String playerList;
    private String serverIcon;
    private boolean field_78841_f;
    private int version;
    private long pingToServer;
    private Hosting hosting;
    
    public void setServerIP(final String serverIP) {
        this.serverIP = serverIP;
    }
    
    public void setServerMotd(final String serverMotd) {
        this.serverMotd = serverMotd;
    }
    
    public void setPopulationInfo(final String populationInfo) {
        this.populationInfo = populationInfo;
    }
    
    public void setGameVersion(final String gameVersion) {
        this.gameVersion = gameVersion;
    }
    
    public void setPlayerList(final String playerList) {
        this.playerList = playerList;
    }
    
    public void setServerIcon(final String serverIcon) {
        this.serverIcon = serverIcon;
    }
    
    public void setField_78841_f(final boolean field_78841_f) {
        this.field_78841_f = field_78841_f;
    }
    
    public void setVersion(final int version) {
        this.version = version;
    }
    
    public void setPingToServer(final long pingToServer) {
        this.pingToServer = pingToServer;
    }
    
    public void setHosting(final Hosting hosting) {
        this.hosting = hosting;
    }
    
    public String getServerIP() {
        return this.serverIP;
    }
    
    public String getServerMotd() {
        return this.serverMotd;
    }
    
    public String getPopulationInfo() {
        return this.populationInfo;
    }
    
    public String getGameVersion() {
        return this.gameVersion;
    }
    
    public String getPlayerList() {
        return this.playerList;
    }
    
    public String getServerIcon() {
        return this.serverIcon;
    }
    
    public boolean isField_78841_f() {
        return this.field_78841_f;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public long getPingToServer() {
        return this.pingToServer;
    }
    
    public Hosting getHosting() {
        return this.hosting;
    }
    
    public ServerInfos(final String serverIP, final String serverMotd, final String populationInfo, final String gameVersion, final String playerList, final String serverIcon, final boolean field_78841_f, final int version, final long pingToServer) {
        this.setServerIP(serverIP);
        this.setServerMotd(serverMotd);
        this.setPopulationInfo(populationInfo);
        this.setGameVersion(gameVersion);
        this.setPlayerList(playerList);
        this.setServerIcon(serverIcon);
        this.setField_78841_f(field_78841_f);
        this.setVersion(version);
        this.setPingToServer(pingToServer);
        this.setHosting(new Hosting(this.getServerIP()));
    }
    
    public static class Hosting
    {
        private String server;
        private JsonObject object;
        private TimeHelper timeUtils;
        
        public void setServer(final String server) {
            this.server = server;
        }
        
        public void setObject(final JsonObject object) {
            this.object = object;
        }
        
        public void setTimeUtils(final TimeHelper timeUtils) {
            this.timeUtils = timeUtils;
        }
        
        public String getServer() {
            return this.server;
        }
        
        public JsonObject getObject() {
            return this.object;
        }
        
        public TimeHelper getTimeUtils() {
            return this.timeUtils;
        }
        
        public Hosting(final String s) {
            this.setServer("http://ip-api.com/json/" + s + "?fields=status,message,continent,continentCode,country,countryCode,region,regionName,city,district,zip,lat,lon,timezone,currency,isp,org,as,asname,reverse,mobile,proxy,query");
            this.object = new JsonParser().parse(WebUtils.getWebsiteData(this.getServer())).getAsJsonObject();
            (this.timeUtils = new TimeHelper()).reset();
        }
        
        public ArrayList getResults() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: dup            
            //     4: invokespecial   java/util/ArrayList.<init>:()V
            //     7: astore_1       
            //     8: aload_0        
            //     9: ldc             "query"
            //    11: ifeq            38
            //    14: aload_1        
            //    15: new             Ljava/lang/StringBuilder;
            //    18: dup            
            //    19: invokespecial   java/lang/StringBuilder.<init>:()V
            //    22: aload_0        
            //    23: ldc             "query"
            //    25: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //    28: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    31: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //    34: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //    37: pop            
            //    38: aload_0        
            //    39: ldc             "isp"
            //    41: ifeq            68
            //    44: aload_1        
            //    45: new             Ljava/lang/StringBuilder;
            //    48: dup            
            //    49: invokespecial   java/lang/StringBuilder.<init>:()V
            //    52: aload_0        
            //    53: ldc             "isp"
            //    55: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //    58: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    61: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //    64: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //    67: pop            
            //    68: aload_0        
            //    69: ldc             "continent"
            //    71: ifeq            98
            //    74: aload_1        
            //    75: new             Ljava/lang/StringBuilder;
            //    78: dup            
            //    79: invokespecial   java/lang/StringBuilder.<init>:()V
            //    82: aload_0        
            //    83: ldc             "continent"
            //    85: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //    88: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    91: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //    94: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //    97: pop            
            //    98: aload_0        
            //    99: ldc             "city"
            //   101: ifeq            128
            //   104: aload_1        
            //   105: new             Ljava/lang/StringBuilder;
            //   108: dup            
            //   109: invokespecial   java/lang/StringBuilder.<init>:()V
            //   112: aload_0        
            //   113: ldc             "city"
            //   115: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   118: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   121: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   124: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   127: pop            
            //   128: aload_0        
            //   129: ldc             "regionName"
            //   131: ifeq            158
            //   134: aload_1        
            //   135: new             Ljava/lang/StringBuilder;
            //   138: dup            
            //   139: invokespecial   java/lang/StringBuilder.<init>:()V
            //   142: aload_0        
            //   143: ldc             "regionName"
            //   145: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   148: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   151: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   154: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   157: pop            
            //   158: aload_0        
            //   159: ldc             "country"
            //   161: ifeq            213
            //   164: aload_0        
            //   165: ldc             "countryCode"
            //   167: ifeq            213
            //   170: aload_1        
            //   171: new             Ljava/lang/StringBuilder;
            //   174: dup            
            //   175: invokespecial   java/lang/StringBuilder.<init>:()V
            //   178: aload_0        
            //   179: ldc             "country"
            //   181: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   184: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   187: ldc             " ("
            //   189: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   192: aload_0        
            //   193: ldc             "countryCode"
            //   195: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   198: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   201: ldc             ")"
            //   203: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   206: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   209: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   212: pop            
            //   213: aload_0        
            //   214: ldc             "currency"
            //   216: ifeq            243
            //   219: aload_1        
            //   220: new             Ljava/lang/StringBuilder;
            //   223: dup            
            //   224: invokespecial   java/lang/StringBuilder.<init>:()V
            //   227: aload_0        
            //   228: ldc             "currency"
            //   230: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   233: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   236: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   239: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   242: pop            
            //   243: aload_0        
            //   244: ldc             "timezone"
            //   246: ifeq            273
            //   249: aload_1        
            //   250: new             Ljava/lang/StringBuilder;
            //   253: dup            
            //   254: invokespecial   java/lang/StringBuilder.<init>:()V
            //   257: aload_0        
            //   258: ldc             "timezone"
            //   260: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   263: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   266: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   269: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   272: pop            
            //   273: aload_0        
            //   274: ldc             "reverse"
            //   276: ifeq            315
            //   279: aload_0        
            //   280: ldc             "reverse"
            //   282: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   285: invokevirtual   java/lang/String.isEmpty:()Z
            //   288: ifne            315
            //   291: aload_1        
            //   292: new             Ljava/lang/StringBuilder;
            //   295: dup            
            //   296: invokespecial   java/lang/StringBuilder.<init>:()V
            //   299: aload_0        
            //   300: ldc             "reverse"
            //   302: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   305: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   308: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   311: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   314: pop            
            //   315: aload_0        
            //   316: ldc             "org"
            //   318: ifeq            357
            //   321: aload_0        
            //   322: ldc             "org"
            //   324: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   327: invokevirtual   java/lang/String.isEmpty:()Z
            //   330: ifne            357
            //   333: aload_1        
            //   334: new             Ljava/lang/StringBuilder;
            //   337: dup            
            //   338: invokespecial   java/lang/StringBuilder.<init>:()V
            //   341: aload_0        
            //   342: ldc             "org"
            //   344: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   347: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   350: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   353: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   356: pop            
            //   357: aload_0        
            //   358: ldc             "as"
            //   360: ifeq            387
            //   363: aload_1        
            //   364: new             Ljava/lang/StringBuilder;
            //   367: dup            
            //   368: invokespecial   java/lang/StringBuilder.<init>:()V
            //   371: aload_0        
            //   372: ldc             "as"
            //   374: invokespecial   Mood/Host/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   377: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   380: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   383: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   386: pop            
            //   387: aload_1        
            //   388: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0213 (coming from #0167).
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
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
        
        private String getStringObject(final String s) {
            if (!s.isEmpty() && !s.contains("Unknown")) {
                return this.object.get(s).getAsString();
            }
            return "Can't load";
        }
    }
}
