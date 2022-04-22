package Mood.Helpers;

public class Server
{
    private String ip;
    private String version;
    private int port;
    
    public Server(final String ip, final int port) {
        this.ip = ip;
        this.version = "";
        this.port = port;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    public void setVersion(final String version) {
        this.version = version;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
}
