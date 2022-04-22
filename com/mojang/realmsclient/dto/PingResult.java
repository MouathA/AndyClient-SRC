package com.mojang.realmsclient.dto;

import java.util.*;

public class PingResult
{
    public List pingResults;
    public List worldIds;
    
    public PingResult() {
        this.pingResults = new ArrayList();
        this.worldIds = new ArrayList();
    }
}
