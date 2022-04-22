package optifine;

import java.net.*;
import net.minecraft.client.*;
import java.io.*;

public class VersionCheckThread extends Thread
{
    @Override
    public void run() {
        Config.dbg("Checking for new version");
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("http://optifine.net/version/1.8/HD_U.txt").openConnection();
        if (Config.getGameSettings().snooperEnabled) {
            httpURLConnection.setRequestProperty("OF-MC-Version", "1.8");
            httpURLConnection.setRequestProperty("OF-MC-Brand", new StringBuilder().append(ClientBrandRetriever.getClientModName()).toString());
            httpURLConnection.setRequestProperty("OF-Edition", "HD_U");
            httpURLConnection.setRequestProperty("OF-Release", "H6");
            httpURLConnection.setRequestProperty("OF-Java-Version", new StringBuilder().append(System.getProperty("java.version")).toString());
            httpURLConnection.setRequestProperty("OF-CpuCount", new StringBuilder().append(Config.getAvailableProcessors()).toString());
            httpURLConnection.setRequestProperty("OF-OpenGL-Version", new StringBuilder().append(Config.openGlVersion).toString());
            httpURLConnection.setRequestProperty("OF-OpenGL-Vendor", new StringBuilder().append(Config.openGlVendor).toString());
        }
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(false);
        httpURLConnection.connect();
        final InputStream inputStream = httpURLConnection.getInputStream();
        final String inputStream2 = Config.readInputStream(inputStream);
        inputStream.close();
        final String[] tokenize = Config.tokenize(inputStream2, "\n\r");
        if (tokenize.length < 1) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return;
        }
        final String trim = tokenize[0].trim();
        Config.dbg("Version found: " + trim);
        if (Config.compareRelease(trim, "H6") > 0) {
            Config.setNewRelease(trim);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return;
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
