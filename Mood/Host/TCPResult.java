package Mood.Host;

public class TCPResult
{
    private final double ping;
    private final String address;
    private final String error;
    
    public TCPResult(final double ping, final String address, final String error) {
        this.ping = ping;
        this.address = address;
        this.error = error;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public double getPing() {
        return this.ping;
    }
    
    public String getError() {
        return this.error;
    }
    
    public boolean isSuccessful() {
        return this.error == null;
    }
}
