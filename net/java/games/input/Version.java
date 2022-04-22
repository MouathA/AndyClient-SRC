package net.java.games.input;

public final class Version
{
    private static final String apiVersion;
    private static final String buildNumber;
    private static final String antBuildNumberToken;
    private static final String antAPIVersionToken;
    
    private Version() {
    }
    
    public static String getVersion() {
        String string = "Unversioned";
        if (!"@API_VERSION@".equals("2.0.5")) {
            string = "2.0.5";
        }
        if (!"@BUILD_NUMBER@".equals("1088")) {
            string += "-b1088";
        }
        return string;
    }
    
    static {
        antAPIVersionToken = "@API_VERSION@";
        buildNumber = "1088";
        antBuildNumberToken = "@BUILD_NUMBER@";
        apiVersion = "2.0.5";
    }
}
