package Mood.AndyConnection;

import com.google.gson.annotations.*;

public class PlayerBuilder extends AndyPlayer
{
    String username;
    String content;
    @SerializedName("avatar_url")
    String avatarUrl;
    @SerializedName("tts")
    boolean textToSpeech;
    
    public PlayerBuilder() {
        this(null, "", null, false);
    }
    
    public PlayerBuilder(final String s) {
        this(null, s, null, false);
    }
    
    public PlayerBuilder(final String s, final String s2, final String s3) {
        this(s, s2, s3, false);
    }
    
    public PlayerBuilder(final String s, final String cape, final String s2, final boolean b) {
        this.capeUsername(s);
        this.setCape(cape);
        this.checkCapeUrl(s2);
        this.isDev(b);
    }
    
    public void capeUsername(final String s) {
        if (s != null) {
            this.username = s.substring(0, Math.min(31, s.length()));
        }
        else {
            this.username = null;
        }
    }
    
    public void setCape(final String content) {
        this.content = content;
    }
    
    public void checkCapeUrl(final String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public void isDev(final boolean textToSpeech) {
        this.textToSpeech = textToSpeech;
    }
    
    public static class Builder
    {
        private final PlayerBuilder message;
        
        public Builder() {
            this.message = new PlayerBuilder();
        }
        
        public Builder(final String s) {
            this.message = new PlayerBuilder(s);
        }
        
        public Builder withUsername(final String s) {
            this.message.capeUsername(s);
            return this;
        }
        
        public Builder withContent(final String cape) {
            this.message.setCape(cape);
            return this;
        }
        
        public Builder withAvatarURL(final String s) {
            this.message.checkCapeUrl(s);
            return this;
        }
        
        public Builder withDev(final boolean b) {
            this.message.isDev(b);
            return this;
        }
        
        public PlayerBuilder build() {
            return this.message;
        }
    }
}
