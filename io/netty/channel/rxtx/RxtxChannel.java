package io.netty.channel.rxtx;

import io.netty.channel.oio.*;
import java.net.*;
import gnu.io.*;
import io.netty.channel.*;
import java.util.concurrent.*;

public class RxtxChannel extends OioByteStreamChannel
{
    private static final RxtxDeviceAddress LOCAL_ADDRESS;
    private final RxtxChannelConfig config;
    private boolean open;
    private RxtxDeviceAddress deviceAddress;
    private SerialPort serialPort;
    
    public RxtxChannel() {
        super(null);
        this.open = true;
        this.config = new DefaultRxtxChannelConfig(this);
    }
    
    @Override
    public RxtxChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isOpen() {
        return this.open;
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new RxtxUnsafe(null);
    }
    
    @Override
    protected void doConnect(final SocketAddress socketAddress, final SocketAddress socketAddress2) throws Exception {
        final RxtxDeviceAddress deviceAddress = (RxtxDeviceAddress)socketAddress;
        final CommPort open = CommPortIdentifier.getPortIdentifier(deviceAddress.value()).open(this.getClass().getName(), 1000);
        open.enableReceiveTimeout((int)this.config().getOption(RxtxChannelOption.READ_TIMEOUT));
        this.deviceAddress = deviceAddress;
        this.serialPort = (SerialPort)open;
    }
    
    protected void doInit() throws Exception {
        this.serialPort.setSerialPortParams((int)this.config().getOption(RxtxChannelOption.BAUD_RATE), ((RxtxChannelConfig.Databits)this.config().getOption(RxtxChannelOption.DATA_BITS)).value(), ((RxtxChannelConfig.Stopbits)this.config().getOption(RxtxChannelOption.STOP_BITS)).value(), ((RxtxChannelConfig.Paritybit)this.config().getOption(RxtxChannelOption.PARITY_BIT)).value());
        this.serialPort.setDTR((boolean)this.config().getOption(RxtxChannelOption.DTR));
        this.serialPort.setRTS((boolean)this.config().getOption(RxtxChannelOption.RTS));
        this.activate(this.serialPort.getInputStream(), this.serialPort.getOutputStream());
    }
    
    @Override
    public RxtxDeviceAddress localAddress() {
        return (RxtxDeviceAddress)super.localAddress();
    }
    
    @Override
    public RxtxDeviceAddress remoteAddress() {
        return (RxtxDeviceAddress)super.remoteAddress();
    }
    
    @Override
    protected RxtxDeviceAddress localAddress0() {
        return RxtxChannel.LOCAL_ADDRESS;
    }
    
    @Override
    protected RxtxDeviceAddress remoteAddress0() {
        return this.deviceAddress;
    }
    
    @Override
    protected void doBind(final SocketAddress socketAddress) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.open = false;
        super.doClose();
        if (this.serialPort != null) {
            this.serialPort.removeEventListener();
            this.serialPort.close();
            this.serialPort = null;
        }
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress0();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress0();
    }
    
    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }
    
    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }
    
    @Override
    public ChannelConfig config() {
        return this.config();
    }
    
    static {
        LOCAL_ADDRESS = new RxtxDeviceAddress("localhost");
    }
    
    private final class RxtxUnsafe extends AbstractUnsafe
    {
        final RxtxChannel this$0;
        
        private RxtxUnsafe(final RxtxChannel this$0) {
            this.this$0 = this$0.super();
        }
        
        @Override
        public void connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            final boolean active = this.this$0.isActive();
            this.this$0.doConnect(socketAddress, socketAddress2);
            final int intValue = (int)this.this$0.config().getOption(RxtxChannelOption.WAIT_TIME);
            if (intValue > 0) {
                this.this$0.eventLoop().schedule((Runnable)new Runnable(channelPromise, active) {
                    final ChannelPromise val$promise;
                    final boolean val$wasActive;
                    final RxtxUnsafe this$1;
                    
                    @Override
                    public void run() {
                        this.this$1.this$0.doInit();
                        RxtxUnsafe.access$100(this.this$1, this.val$promise);
                        if (!this.val$wasActive && this.this$1.this$0.isActive()) {
                            this.this$1.this$0.pipeline().fireChannelActive();
                        }
                    }
                }, (long)intValue, TimeUnit.MILLISECONDS);
            }
            else {
                this.this$0.doInit();
                this.safeSetSuccess(channelPromise);
                if (!active && this.this$0.isActive()) {
                    this.this$0.pipeline().fireChannelActive();
                }
            }
        }
        
        RxtxUnsafe(final RxtxChannel rxtxChannel, final RxtxChannel$1 object) {
            this(rxtxChannel);
        }
        
        static void access$100(final RxtxUnsafe rxtxUnsafe, final ChannelPromise channelPromise) {
            rxtxUnsafe.safeSetSuccess(channelPromise);
        }
        
        static void access$200(final RxtxUnsafe rxtxUnsafe, final ChannelPromise channelPromise, final Throwable t) {
            rxtxUnsafe.safeSetFailure(channelPromise, t);
        }
        
        static void access$300(final RxtxUnsafe rxtxUnsafe) {
            rxtxUnsafe.closeIfClosed();
        }
    }
}
