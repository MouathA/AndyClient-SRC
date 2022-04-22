package Mood.Host;

import java.awt.*;
import java.net.*;
import java.io.*;

public enum WebUtils
{
    INSTANCE("INSTANCE", 0);
    
    private static final WebUtils[] ENUM$VALUES;
    
    static {
        ENUM$VALUES = new WebUtils[] { WebUtils.INSTANCE };
    }
    
    private WebUtils(final String s, final int n) {
    }
    
    public static boolean openLink(final String s) {
        Desktop.getDesktop().browse(new URI(s));
        return true;
    }
    
    public static String getWebsiteData(final String s) {
        final StringBuilder sb = new StringBuilder("");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(s).openStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }
    
    public String infosFromWebsite(final String s) {
        final StringBuilder sb = new StringBuilder("");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(s).openStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }
}
