package Mood.Host;

public class UDPResult
{
    private final double timeout;
    private final double ping;
    private final String address;
    private final String error;
    
    public UDPResult(final double timeout, final double ping, final String address, final String error) {
        this.timeout = timeout;
        this.ping = ping;
        this.address = address;
        this.error = error;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public double getTimeout() {
        return this.timeout;
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
