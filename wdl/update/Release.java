package wdl.update;

import java.util.regex.*;
import java.util.*;
import com.google.gson.*;

public class Release
{
    private static final Pattern HIDDEN_JSON_REGEX;
    private static final JsonParser PARSER;
    public final JsonObject object;
    public final String URL;
    public final String tag;
    public final String title;
    public final String date;
    public final boolean prerelease;
    public final String markdownBody;
    public final String textOnlyBody;
    public final HiddenInfo hiddenInfo;
    
    static {
        HIDDEN_JSON_REGEX = Pattern.compile("^\\[\\]\\(# '(.+?)'\\)");
        PARSER = new JsonParser();
    }
    
    public Release(final JsonObject object) {
        this.object = object;
        this.markdownBody = object.get("body").getAsString();
        final Matcher matcher = Release.HIDDEN_JSON_REGEX.matcher(this.markdownBody);
        if (matcher.find()) {
            this.hiddenInfo = new HiddenInfo(Release.PARSER.parse(this.markdownBody.substring(matcher.start(1), matcher.end(1))).getAsJsonObject(), null);
        }
        else {
            this.hiddenInfo = null;
        }
        this.URL = object.get("html_url").getAsString();
        this.textOnlyBody = object.get("body_text").getAsString();
        this.tag = object.get("tag_name").getAsString();
        this.title = object.get("name").getAsString();
        this.date = object.get("published_at").getAsString();
        this.prerelease = object.get("prerelease").getAsBoolean();
    }
    
    @Override
    public String toString() {
        return "Release [URL=" + this.URL + ", tag=" + this.tag + ", title=" + this.title + ", date=" + this.date + ", prerelease=" + this.prerelease + ", markdownBody=" + this.markdownBody + ", textOnlyBody=" + this.textOnlyBody + ", hiddenInfo=" + this.hiddenInfo + "]";
    }
    
    public class HashData
    {
        public final String relativeTo;
        public final String file;
        public final String[] validHashes;
        final Release this$0;
        
        public HashData(final Release this$0, final JsonObject jsonObject) {
            this.this$0 = this$0;
            this.relativeTo = jsonObject.get("RelativeTo").getAsString();
            this.file = jsonObject.get("File").getAsString();
            final JsonArray asJsonArray = jsonObject.get("Hash").getAsJsonArray();
            this.validHashes = new String[asJsonArray.size()];
            while (0 < this.validHashes.length) {
                this.validHashes[0] = asJsonArray.get(0).getAsString();
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public String toString() {
            return "HashData [relativeTo=" + this.relativeTo + ", file=" + this.file + ", validHashes=" + Arrays.toString(this.validHashes) + "]";
        }
        
        @Override
        public int hashCode() {
            final int n = 31 + this.getOuterType().hashCode();
            final int n2 = 31 + ((this.file == null) ? 0 : this.file.hashCode());
            final int n3 = 31 + ((this.relativeTo == null) ? 0 : this.relativeTo.hashCode());
            return 1;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (!(o instanceof HashData)) {
                return false;
            }
            final HashData hashData = (HashData)o;
            if (!this.getOuterType().equals(hashData.getOuterType())) {
                return false;
            }
            if (this.file == null) {
                if (hashData.file != null) {
                    return false;
                }
            }
            else if (!this.file.equals(hashData.file)) {
                return false;
            }
            if (this.relativeTo == null) {
                if (hashData.relativeTo != null) {
                    return false;
                }
            }
            else if (!this.relativeTo.equals(hashData.relativeTo)) {
                return false;
            }
            return true;
        }
        
        private Release getOuterType() {
            return this.this$0;
        }
    }
    
    public class HiddenInfo
    {
        public final String mainMinecraftVersion;
        public final String[] supportedMinecraftVersions;
        public final String loader;
        public final String post;
        public final HashData[] hashes;
        final Release this$0;
        
        private HiddenInfo(final Release this$0, final JsonObject jsonObject) {
            this.this$0 = this$0;
            this.mainMinecraftVersion = jsonObject.get("Minecraft").getAsString();
            final JsonArray asJsonArray = jsonObject.get("MinecraftCompatible").getAsJsonArray();
            this.supportedMinecraftVersions = new String[asJsonArray.size()];
            while (0 < asJsonArray.size()) {
                this.supportedMinecraftVersions[0] = asJsonArray.get(0).getAsString();
                int n = 0;
                ++n;
            }
            this.loader = jsonObject.get("Loader").getAsString();
            final JsonElement value = jsonObject.get("Post");
            if (value.isJsonNull()) {
                this.post = null;
            }
            else {
                this.post = value.getAsString();
            }
            final JsonArray asJsonArray2 = jsonObject.get("Hashes").getAsJsonArray();
            this.hashes = new HashData[asJsonArray2.size()];
            while (0 < asJsonArray2.size()) {
                this.hashes[0] = this$0.new HashData(asJsonArray2.get(0).getAsJsonObject());
                int n2 = 0;
                ++n2;
            }
        }
        
        @Override
        public String toString() {
            return "HiddenInfo [mainMinecraftVersion=" + this.mainMinecraftVersion + ", supportedMinecraftVersions=" + Arrays.toString(this.supportedMinecraftVersions) + ", loader=" + this.loader + ", post=" + this.post + ", hashes=" + Arrays.toString(this.hashes) + "]";
        }
        
        HiddenInfo(final Release release, final JsonObject jsonObject, final HiddenInfo hiddenInfo) {
            this(release, jsonObject);
        }
    }
}
