package Mood.Helpers;

import Mood.Host.Helper.*;
import com.google.gson.*;
import Mood.Host.*;
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
    
    public static class Hosting
    {
        private String server;
        private JsonObject object;
        private TimeHelper timeUtils;
        
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
            //    11: ifeq            40
            //    14: aload_1        
            //    15: new             Ljava/lang/StringBuilder;
            //    18: dup            
            //    19: ldc             "§6Query: §7"
            //    21: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //    24: aload_0        
            //    25: ldc             "query"
            //    27: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //    30: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    33: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //    36: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //    39: pop            
            //    40: aload_0        
            //    41: ldc             "isp"
            //    43: ifeq            72
            //    46: aload_1        
            //    47: new             Ljava/lang/StringBuilder;
            //    50: dup            
            //    51: ldc             "§6ISP: §7"
            //    53: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //    56: aload_0        
            //    57: ldc             "isp"
            //    59: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //    62: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    65: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //    68: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //    71: pop            
            //    72: ldc             "continent"
            //    74: ifnull          414
            //    77: aload_0        
            //    78: ldc             "continent"
            //    80: ifeq            109
            //    83: aload_1        
            //    84: new             Ljava/lang/StringBuilder;
            //    87: dup            
            //    88: ldc             "§6Continent: §7"
            //    90: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //    93: aload_0        
            //    94: ldc             "continent"
            //    96: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //    99: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   102: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   105: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   108: pop            
            //   109: aload_0        
            //   110: ldc             "city"
            //   112: ifeq            141
            //   115: aload_1        
            //   116: new             Ljava/lang/StringBuilder;
            //   119: dup            
            //   120: ldc             "§6City: §7"
            //   122: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   125: aload_0        
            //   126: ldc             "city"
            //   128: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   131: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   134: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   137: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   140: pop            
            //   141: aload_0        
            //   142: ldc             "regionName"
            //   144: ifeq            173
            //   147: aload_1        
            //   148: new             Ljava/lang/StringBuilder;
            //   151: dup            
            //   152: ldc             "§6Region: §7"
            //   154: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   157: aload_0        
            //   158: ldc             "regionName"
            //   160: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   163: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   166: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   169: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   172: pop            
            //   173: aload_0        
            //   174: ldc             "country"
            //   176: ifeq            230
            //   179: aload_0        
            //   180: ldc             "countryCode"
            //   182: ifeq            230
            //   185: aload_1        
            //   186: new             Ljava/lang/StringBuilder;
            //   189: dup            
            //   190: ldc             "§6Country: §7"
            //   192: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   195: aload_0        
            //   196: ldc             "country"
            //   198: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   204: ldc             " ("
            //   206: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   209: aload_0        
            //   210: ldc             "countryCode"
            //   212: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   215: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   218: ldc             ")"
            //   220: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   223: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   226: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   229: pop            
            //   230: aload_0        
            //   231: ldc             "currency"
            //   233: ifeq            262
            //   236: aload_1        
            //   237: new             Ljava/lang/StringBuilder;
            //   240: dup            
            //   241: ldc             "§6Currency: §7"
            //   243: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   246: aload_0        
            //   247: ldc             "currency"
            //   249: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   252: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   255: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   258: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   261: pop            
            //   262: aload_0        
            //   263: ldc             "timezone"
            //   265: ifeq            294
            //   268: aload_1        
            //   269: new             Ljava/lang/StringBuilder;
            //   272: dup            
            //   273: ldc             "§6Timezone: §7"
            //   275: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   278: aload_0        
            //   279: ldc             "timezone"
            //   281: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   284: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   287: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   290: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   293: pop            
            //   294: aload_0        
            //   295: ldc             "reverse"
            //   297: ifeq            338
            //   300: aload_0        
            //   301: ldc             "reverse"
            //   303: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   306: invokevirtual   java/lang/String.isEmpty:()Z
            //   309: ifne            338
            //   312: aload_1        
            //   313: new             Ljava/lang/StringBuilder;
            //   316: dup            
            //   317: ldc             "§6Hostname: §7"
            //   319: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   322: aload_0        
            //   323: ldc             "reverse"
            //   325: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   328: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   331: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   334: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   337: pop            
            //   338: aload_0        
            //   339: ldc             "org"
            //   341: ifeq            382
            //   344: aload_0        
            //   345: ldc             "org"
            //   347: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   350: invokevirtual   java/lang/String.isEmpty:()Z
            //   353: ifne            382
            //   356: aload_1        
            //   357: new             Ljava/lang/StringBuilder;
            //   360: dup            
            //   361: ldc             "§6Organization: §7"
            //   363: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   366: aload_0        
            //   367: ldc             "org"
            //   369: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   372: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   375: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   378: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   381: pop            
            //   382: aload_0        
            //   383: ldc             "as"
            //   385: ifeq            414
            //   388: aload_1        
            //   389: new             Ljava/lang/StringBuilder;
            //   392: dup            
            //   393: ldc             "§6AS: §7"
            //   395: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
            //   398: aload_0        
            //   399: ldc             "as"
            //   401: invokespecial   Mood/Helpers/ServerInfos$Hosting.getStringObject:(Ljava/lang/String;)Ljava/lang/String;
            //   404: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   407: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   410: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
            //   413: pop            
            //   414: aload_1        
            //   415: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Inconsistent stack size at #0414 (coming from #0385).
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
        
        public String getServer() {
            return this.server;
        }
        
        public JsonObject getObject() {
            return this.object;
        }
        
        public TimeHelper getTimeUtils() {
            return this.timeUtils;
        }
        
        public void setServer(final String server) {
            this.server = server;
        }
        
        public void setObject(final JsonObject object) {
            this.object = object;
        }
        
        public void setTimeUtils(final TimeHelper timeUtils) {
            this.timeUtils = timeUtils;
        }
    }
}
