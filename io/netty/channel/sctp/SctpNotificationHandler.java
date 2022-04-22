package io.netty.channel.sctp;

import com.sun.nio.sctp.*;

public final class SctpNotificationHandler extends AbstractNotificationHandler
{
    private final SctpChannel sctpChannel;
    
    public SctpNotificationHandler(final SctpChannel sctpChannel) {
        if (sctpChannel == null) {
            throw new NullPointerException("sctpChannel");
        }
        this.sctpChannel = sctpChannel;
    }
    
    @Override
    public HandlerResult handleNotification(final AssociationChangeNotification associationChangeNotification, final Object o) {
        this.fireEvent(associationChangeNotification);
        return HandlerResult.CONTINUE;
    }
    
    @Override
    public HandlerResult handleNotification(final PeerAddressChangeNotification peerAddressChangeNotification, final Object o) {
        this.fireEvent(peerAddressChangeNotification);
        return HandlerResult.CONTINUE;
    }
    
    @Override
    public HandlerResult handleNotification(final SendFailedNotification sendFailedNotification, final Object o) {
        this.fireEvent(sendFailedNotification);
        return HandlerResult.CONTINUE;
    }
    
    @Override
    public HandlerResult handleNotification(final ShutdownNotification shutdownNotification, final Object o) {
        this.fireEvent(shutdownNotification);
        this.sctpChannel.close();
        return HandlerResult.RETURN;
    }
    
    private void fireEvent(final Notification notification) {
        this.sctpChannel.pipeline().fireUserEventTriggered(notification);
    }
}
