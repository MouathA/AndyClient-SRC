package org.apache.http.conn;

import org.apache.http.conn.scheme.*;
import org.apache.http.annotation.*;
import java.net.*;
import org.apache.http.params.*;
import java.util.*;
import java.io.*;
import org.apache.http.util.*;

@Deprecated
@Immutable
public final class MultihomePlainSocketFactory implements SocketFactory
{
    private static final MultihomePlainSocketFactory DEFAULT_FACTORY;
    
    public static MultihomePlainSocketFactory getSocketFactory() {
        return MultihomePlainSocketFactory.DEFAULT_FACTORY;
    }
    
    private MultihomePlainSocketFactory() {
    }
    
    public Socket createSocket() {
        return new Socket();
    }
    
    public Socket connectSocket(final Socket socket, final String s, final int n, final InetAddress inetAddress, final int n2, final HttpParams httpParams) throws IOException {
        Args.notNull(s, "Target host");
        Args.notNull(httpParams, "HTTP parameters");
        Socket socket2 = socket;
        if (socket2 == null) {
            socket2 = this.createSocket();
        }
        if (inetAddress != null || n2 > 0) {
            socket2.bind(new InetSocketAddress(inetAddress, (n2 > 0) ? n2 : 0));
        }
        final int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        final InetAddress[] allByName = InetAddress.getAllByName(s);
        final ArrayList list = new ArrayList<InetAddress>(allByName.length);
        list.addAll((Collection<?>)Arrays.asList(allByName));
        Collections.shuffle(list);
        final Object o = null;
        final Iterator<InetAddress> iterator = (Iterator<InetAddress>)list.iterator();
        if (iterator.hasNext()) {
            socket2.connect(new InetSocketAddress(iterator.next(), n), connectionTimeout);
        }
        if (o != null) {
            throw o;
        }
        return socket2;
    }
    
    public final boolean isSecure(final Socket socket) throws IllegalArgumentException {
        Args.notNull(socket, "Socket");
        Asserts.check(!socket.isClosed(), "Socket is closed");
        return false;
    }
    
    static {
        DEFAULT_FACTORY = new MultihomePlainSocketFactory();
    }
}
