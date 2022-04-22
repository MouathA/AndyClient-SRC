package Mood.Host;

import java.io.*;
import java.net.*;
import com.google.gson.*;
import java.util.*;

public final class CheckHostAPI
{
    public static Result createPingRequest(final String s, final int n) throws IOException {
        final Map.Entry sendCheckHostRequest = sendCheckHostRequest(CheckHostType.PING, s, n);
        return new Result(CheckHostType.PING, sendCheckHostRequest.getKey(), (List)sendCheckHostRequest.getValue());
    }
    
    public static Result createTcpRequest(final String s, final int n) throws IOException {
        final Map.Entry sendCheckHostRequest = sendCheckHostRequest(CheckHostType.TCP, s, n);
        return new Result(CheckHostType.TCP, sendCheckHostRequest.getKey(), (List)sendCheckHostRequest.getValue());
    }
    
    public static Result createUdpRequest(final String s, final int n) throws IOException {
        final Map.Entry sendCheckHostRequest = sendCheckHostRequest(CheckHostType.UDP, s, n);
        return new Result(CheckHostType.UDP, sendCheckHostRequest.getKey(), (List)sendCheckHostRequest.getValue());
    }
    
    public static Result createHttpRequest(final String s, final int n) throws IOException {
        final Map.Entry sendCheckHostRequest = sendCheckHostRequest(CheckHostType.HTTP, s, n);
        return new Result(CheckHostType.HTTP, sendCheckHostRequest.getKey(), (List)sendCheckHostRequest.getValue());
    }
    
    public static Result createDnsRequest(final String s, final int n) throws IOException {
        final Map.Entry sendCheckHostRequest = sendCheckHostRequest(CheckHostType.DNS, s, n);
        return new Result(CheckHostType.DNS, sendCheckHostRequest.getKey(), (List)sendCheckHostRequest.getValue());
    }
    
    private static JsonObject performGetRequest(final String s) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s).openConnection();
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String string = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            string = String.valueOf(string) + line + System.lineSeparator();
        }
        bufferedReader.close();
        final JsonObject asJsonObject = new JsonParser().parse(string).getAsJsonObject();
        httpURLConnection.disconnect();
        return asJsonObject;
    }
    
    private static Map.Entry sendCheckHostRequest(final CheckHostType checkHostType, final String s, final int n) throws IOException {
        final JsonObject performGetRequest = performGetRequest("https://check-host.net/check-" + checkHostType.getValue() + "?host=" + URLEncoder.encode(s, "UTF-8") + "&max_nodes=" + n);
        if (!performGetRequest.has("nodes")) {
            throw new IOException("Invalid response!");
        }
        final ArrayList<CheckHostServer> list = new ArrayList<CheckHostServer>();
        for (final Map.Entry<K, JsonElement> entry : performGetRequest.get("nodes").getAsJsonObject().entrySet()) {
            final JsonArray asJsonArray = entry.getValue().getAsJsonArray();
            final ArrayList<String> list2 = new ArrayList<String>();
            if (asJsonArray.size() > 3) {
                while (3 < asJsonArray.size()) {
                    list2.add(asJsonArray.get(3).getAsString());
                    int n2 = 0;
                    ++n2;
                }
            }
            list.add(new CheckHostServer((String)entry.getKey(), asJsonArray.get(1).getAsString(), asJsonArray.get(0).getAsString(), asJsonArray.get(2).getAsString(), list2));
        }
        return new Map.Entry((List)list) {
            private final JsonObject val$main;
            private final List val$servers;
            
            @Override
            public String getKey() {
                return this.val$main.get("request_id").getAsString();
            }
            
            @Override
            public List getValue() {
                return this.val$servers;
            }
            
            public List setValue(final List list) {
                return this.val$servers;
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
    }
    
    static Map ping(final Map.Entry entry) throws IOException {
        final String s = entry.getKey();
        final List list = (List)entry.getValue();
        final JsonObject performGetRequest = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(s, "UTF-8"));
        final HashMap<CheckHostServer, PingResult> hashMap = new HashMap<CheckHostServer, PingResult>();
        while (0 < list.size()) {
            final CheckHostServer checkHostServer = list.get(0);
            if (performGetRequest.has(checkHostServer.getName()) && !performGetRequest.get(checkHostServer.getName()).isJsonNull()) {
                final JsonArray asJsonArray = performGetRequest.get(checkHostServer.getName()).getAsJsonArray();
                while (0 < asJsonArray.size()) {
                    final JsonElement value = asJsonArray.get(0);
                    if (value.isJsonArray()) {
                        final JsonArray asJsonArray2 = value.getAsJsonArray();
                        final ArrayList<PingResult.PingEntry> list2 = new ArrayList<PingResult.PingEntry>();
                        while (0 < asJsonArray2.size()) {
                            if (asJsonArray2.get(0).isJsonArray()) {
                                final JsonArray asJsonArray3 = asJsonArray2.get(0).getAsJsonArray();
                                if (asJsonArray3.size() != 2 && asJsonArray3.size() != 3) {
                                    list2.add(new PingResult.PingEntry("Unable to resolve domain name.", -1.0, null));
                                }
                                else {
                                    final String asString = asJsonArray3.get(0).getAsString();
                                    final double asDouble = asJsonArray3.get(1).getAsDouble();
                                    String asString2 = null;
                                    if (asJsonArray3.size() > 2) {
                                        asString2 = asJsonArray3.get(2).getAsString();
                                    }
                                    list2.add(new PingResult.PingEntry(asString, asDouble, asString2));
                                }
                            }
                            int n = 0;
                            ++n;
                        }
                        hashMap.put(checkHostServer, new PingResult(list2));
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
            int n3 = 0;
            ++n3;
        }
        return hashMap;
    }
    
    static Map tcp(final Map.Entry entry) throws IOException {
        final String s = entry.getKey();
        final List list = (List)entry.getValue();
        final JsonObject performGetRequest = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(s, "UTF-8"));
        final HashMap<CheckHostServer, TCPResult> hashMap = new HashMap<CheckHostServer, TCPResult>();
        while (0 < list.size()) {
            final CheckHostServer checkHostServer = list.get(0);
            if (performGetRequest.has(checkHostServer.getName()) && !performGetRequest.get(checkHostServer.getName()).isJsonNull()) {
                final JsonArray asJsonArray = performGetRequest.get(checkHostServer.getName()).getAsJsonArray();
                if (asJsonArray.size() == 1) {
                    final JsonObject asJsonObject = asJsonArray.get(0).getAsJsonObject();
                    String asString = null;
                    if (asJsonObject.has("error")) {
                        asString = asJsonObject.get("error").getAsString();
                    }
                    String asString2 = null;
                    if (asJsonObject.has("address")) {
                        asString2 = asJsonObject.get("address").getAsString();
                    }
                    double asDouble = 0.0;
                    if (asJsonObject.has("time")) {
                        asDouble = asJsonObject.get("time").getAsDouble();
                    }
                    hashMap.put(checkHostServer, new TCPResult(asDouble, asString2, asString));
                }
            }
            int n = 0;
            ++n;
        }
        return hashMap;
    }
    
    static Map udp(final Map.Entry entry) throws IOException {
        final String s = entry.getKey();
        final List list = (List)entry.getValue();
        final JsonObject performGetRequest = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(s, "UTF-8"));
        final HashMap<CheckHostServer, UDPResult> hashMap = new HashMap<CheckHostServer, UDPResult>();
        while (0 < list.size()) {
            final CheckHostServer checkHostServer = list.get(0);
            if (performGetRequest.has(checkHostServer.getName()) && !performGetRequest.get(checkHostServer.getName()).isJsonNull()) {
                final JsonArray asJsonArray = performGetRequest.get(checkHostServer.getName()).getAsJsonArray();
                if (asJsonArray.size() == 1) {
                    final JsonObject asJsonObject = asJsonArray.get(0).getAsJsonObject();
                    String asString = null;
                    if (asJsonObject.has("error")) {
                        asString = asJsonObject.get("error").getAsString();
                    }
                    String asString2 = null;
                    if (asJsonObject.has("address")) {
                        asString2 = asJsonObject.get("address").getAsString();
                    }
                    double asDouble = 0.0;
                    if (asJsonObject.has("time")) {
                        asDouble = asJsonObject.get("time").getAsDouble();
                    }
                    double asDouble2 = 0.0;
                    if (asJsonObject.has("timeout")) {
                        asDouble2 = asJsonObject.get("timeout").getAsDouble();
                    }
                    hashMap.put(checkHostServer, new UDPResult(asDouble2, asDouble, asString2, asString));
                }
            }
            int n = 0;
            ++n;
        }
        return hashMap;
    }
    
    static Map http(final Map.Entry entry) throws IOException {
        final String s = entry.getKey();
        final List list = (List)entry.getValue();
        final JsonObject performGetRequest = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(s, "UTF-8"));
        final HashMap<CheckHostServer, HttpResult> hashMap = new HashMap<CheckHostServer, HttpResult>();
        while (0 < list.size()) {
            final CheckHostServer checkHostServer = list.get(0);
            if (performGetRequest.has(checkHostServer.getName()) && !performGetRequest.get(checkHostServer.getName()).isJsonNull()) {
                final JsonArray asJsonArray = performGetRequest.get(checkHostServer.getName()).getAsJsonArray();
                if (asJsonArray.size() == 1) {
                    final JsonArray asJsonArray2 = asJsonArray.get(0).getAsJsonArray();
                    final double asDouble = asJsonArray2.get(1).getAsDouble();
                    final String asString = asJsonArray2.get(2).getAsString();
                    final int n = (asJsonArray2.size() > 3 && asJsonArray2.get(3).isJsonPrimitive()) ? asJsonArray2.get(3).getAsInt() : -1;
                    if (n != -1) {
                        hashMap.put(checkHostServer, new HttpResult(asString, asDouble, (asJsonArray2.size() > 4 && asJsonArray2.get(4).isJsonPrimitive()) ? asJsonArray2.get(4).getAsString() : null, n));
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
        return hashMap;
    }
    
    static Map dns(final Map.Entry entry) throws IOException {
        final String s = entry.getKey();
        final List list = (List)entry.getValue();
        final JsonObject performGetRequest = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(s, "UTF-8"));
        final HashMap<CheckHostServer, DNSResult> hashMap = new HashMap<CheckHostServer, DNSResult>();
        while (0 < list.size()) {
            final CheckHostServer checkHostServer = list.get(0);
            if (performGetRequest.has(checkHostServer.getName()) && !performGetRequest.get(checkHostServer.getName()).isJsonNull()) {
                final JsonArray asJsonArray = performGetRequest.get(checkHostServer.getName()).getAsJsonArray();
                if (asJsonArray.size() == 1) {
                    final JsonObject asJsonObject = asJsonArray.get(0).getAsJsonObject();
                    final HashMap<String, String[]> hashMap2 = new HashMap<String, String[]>();
                    for (final Map.Entry<String, V> entry2 : asJsonObject.entrySet()) {
                        if (!entry2.getKey().equals("TTL")) {
                            if (!((JsonElement)entry2.getValue()).isJsonArray()) {
                                continue;
                            }
                            final JsonArray asJsonArray2 = ((JsonElement)entry2.getValue()).getAsJsonArray();
                            final String[] array = new String[asJsonArray2.size()];
                            while (0 < asJsonArray2.size()) {
                                if (asJsonArray2.get(0).isJsonPrimitive()) {
                                    array[0] = asJsonArray2.get(0).getAsString();
                                }
                                int n = 0;
                                ++n;
                            }
                            hashMap2.put(entry2.getKey(), array);
                        }
                    }
                    hashMap.put(checkHostServer, new DNSResult((asJsonObject.has("TTL") && asJsonObject.get("TTL").isJsonPrimitive()) ? asJsonObject.get("TTL").getAsInt() : -1, hashMap2));
                }
            }
            int n2 = 0;
            ++n2;
        }
        return hashMap;
    }
}
