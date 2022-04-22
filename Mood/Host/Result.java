package Mood.Host;

import java.io.*;
import java.util.*;

public class Result
{
    private final CheckHostType type;
    private final List servers;
    private final String requestId;
    private Object result;
    private static int[] $SWITCH_TABLE$Mood$Host$CheckHostType;
    
    public Result(final CheckHostType type, final String requestId, final List servers) throws IOException {
        this.type = type;
        this.requestId = requestId;
        this.servers = servers;
        this.update();
    }
    
    public String getRequestId() {
        return this.requestId;
    }
    
    public Object getResult() {
        return this.result;
    }
    
    public CheckHostType getType() {
        return this.type;
    }
    
    public List getServers() {
        return this.servers;
    }
    
    public void update() throws IOException {
        final Map.Entry entry = new Map.Entry() {
            final Result this$0;
            
            @Override
            public String getKey() {
                return Result.access$0(this.this$0);
            }
            
            @Override
            public List getValue() {
                return Result.access$1(this.this$0);
            }
            
            public List setValue(final List list) {
                return Result.access$1(this.this$0);
            }
            
            @Override
            public Object getKey() {
                return this.getKey();
            }
            
            @Override
            public Object setValue(final Object o) {
                return this.setValue((List)o);
            }
            
            @Override
            public Object getValue() {
                return this.getValue();
            }
        };
        switch ($SWITCH_TABLE$Mood$Host$CheckHostType()[this.type.ordinal()]) {
            case 1: {
                this.result = CheckHostAPI.ping(entry);
                break;
            }
            case 5: {
                this.result = CheckHostAPI.dns(entry);
                break;
            }
            case 4: {
                this.result = CheckHostAPI.http(entry);
                break;
            }
            case 3: {
                this.result = CheckHostAPI.udp(entry);
                break;
            }
            case 2: {
                this.result = CheckHostAPI.tcp(entry);
                break;
            }
        }
    }
    
    static String access$0(final Result result) {
        return result.requestId;
    }
    
    static List access$1(final Result result) {
        return result.servers;
    }
    
    static int[] $SWITCH_TABLE$Mood$Host$CheckHostType() {
        final int[] $switch_TABLE$Mood$Host$CheckHostType = Result.$SWITCH_TABLE$Mood$Host$CheckHostType;
        if ($switch_TABLE$Mood$Host$CheckHostType != null) {
            return $switch_TABLE$Mood$Host$CheckHostType;
        }
        final int[] $switch_TABLE$Mood$Host$CheckHostType2 = new int[CheckHostType.values().length];
        $switch_TABLE$Mood$Host$CheckHostType2[CheckHostType.DNS.ordinal()] = 5;
        $switch_TABLE$Mood$Host$CheckHostType2[CheckHostType.HTTP.ordinal()] = 4;
        $switch_TABLE$Mood$Host$CheckHostType2[CheckHostType.PING.ordinal()] = 1;
        $switch_TABLE$Mood$Host$CheckHostType2[CheckHostType.TCP.ordinal()] = 2;
        $switch_TABLE$Mood$Host$CheckHostType2[CheckHostType.UDP.ordinal()] = 3;
        return Result.$SWITCH_TABLE$Mood$Host$CheckHostType = $switch_TABLE$Mood$Host$CheckHostType2;
    }
}
