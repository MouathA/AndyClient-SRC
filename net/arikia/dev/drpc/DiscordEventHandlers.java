package net.arikia.dev.drpc;

import com.sun.jna.*;
import net.arikia.dev.drpc.callbacks.*;
import java.util.*;

public class DiscordEventHandlers extends Structure
{
    public ReadyCallback ready;
    public DisconnectedCallback disconnected;
    public ErroredCallback errored;
    public JoinGameCallback joinGame;
    public SpectateGameCallback spectateGame;
    public JoinRequestCallback joinRequest;
    
    public List getFieldOrder() {
        return Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest");
    }
    
    public static class Builder
    {
        DiscordEventHandlers h;
        
        public Builder() {
            this.h = new DiscordEventHandlers();
        }
        
        public Builder setReadyEventHandler(final ReadyCallback ready) {
            this.h.ready = ready;
            return this;
        }
        
        public Builder setDisconnectedEventHandler(final DisconnectedCallback disconnected) {
            this.h.disconnected = disconnected;
            return this;
        }
        
        public Builder setErroredEventHandler(final ErroredCallback errored) {
            this.h.errored = errored;
            return this;
        }
        
        public Builder setJoinGameEventHandler(final JoinGameCallback joinGame) {
            this.h.joinGame = joinGame;
            return this;
        }
        
        public Builder setSpectateGameEventHandler(final SpectateGameCallback spectateGame) {
            this.h.spectateGame = spectateGame;
            return this;
        }
        
        public Builder setJoinRequestEventHandler(final JoinRequestCallback joinRequest) {
            this.h.joinRequest = joinRequest;
            return this;
        }
        
        public DiscordEventHandlers build() {
            return this.h;
        }
    }
}
