package com.viaversion.viaversion.libs.javassist.tools.web;

import java.net.*;

class ServiceThread extends Thread
{
    Webserver web;
    Socket sock;
    
    public ServiceThread(final Webserver web, final Socket sock) {
        this.web = web;
        this.sock = sock;
    }
    
    @Override
    public void run() {
        this.web.process(this.sock);
    }
}
