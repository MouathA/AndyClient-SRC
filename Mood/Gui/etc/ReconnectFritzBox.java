package Mood.Gui.etc;

import java.net.*;
import java.nio.charset.*;
import java.io.*;

public class ReconnectFritzBox
{
    public static void reconnectFritzBox() {
        final Socket socket = new Socket(InetAddress.getByName("fritz.box"), 49000);
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        bufferedWriter.write("POST /igdupnp/control/WANIPConn1 HTTP/1.1");
        bufferedWriter.write("Host: fritz.box:49000\r\n");
        bufferedWriter.write("SOAPACTION: \"urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination\"\r\n");
        bufferedWriter.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
        bufferedWriter.write("Content-Length: " + 271 + "\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>");
        bufferedWriter.flush();
        socket.close();
        final Socket socket2 = new Socket(InetAddress.getByName("192.168.178.1"), 49000);
        final BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream(), StandardCharsets.UTF_8));
        bufferedWriter2.write("POST /igdupnp/control/WANIPConn1 HTTP/1.1");
        bufferedWriter2.write("Host: 192.168.178.1:49000\r\n");
        bufferedWriter2.write("SOAPACTION: \"urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination\"\r\n");
        bufferedWriter2.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
        bufferedWriter2.write("Content-Length: " + 271 + "\r\n");
        bufferedWriter2.write("\r\n");
        bufferedWriter2.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>");
        bufferedWriter2.flush();
        socket2.close();
    }
}
