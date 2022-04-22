package tv.twitch.broadcast;

public class IngestList
{
    protected IngestServer[] servers;
    protected IngestServer defaultServer;
    
    public IngestServer[] getServers() {
        return this.servers;
    }
    
    public IngestServer getDefaultServer() {
        return this.defaultServer;
    }
    
    public IngestList(final IngestServer[] array) {
        this.servers = null;
        this.defaultServer = null;
        if (array == null) {
            this.servers = new IngestServer[0];
        }
        else {
            this.servers = new IngestServer[array.length];
            while (0 < array.length) {
                this.servers[0] = array[0];
                if (this.servers[0].defaultServer) {
                    this.defaultServer = this.servers[0];
                }
                int n = 0;
                ++n;
            }
            if (this.defaultServer == null && this.servers.length > 0) {
                this.defaultServer = this.servers[0];
            }
        }
    }
    
    public IngestServer getBestServer() {
        if (this.servers == null || this.servers.length == 0) {
            return null;
        }
        IngestServer ingestServer = this.servers[0];
        while (1 < this.servers.length) {
            if (ingestServer.bitrateKbps < this.servers[1].bitrateKbps) {
                ingestServer = this.servers[1];
            }
            int n = 0;
            ++n;
        }
        return ingestServer;
    }
}
