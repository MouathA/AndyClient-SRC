package io.netty.channel.sctp;

import io.netty.channel.*;

public class SctpChannelOption extends ChannelOption
{
    public static final SctpChannelOption SCTP_DISABLE_FRAGMENTS;
    public static final SctpChannelOption SCTP_EXPLICIT_COMPLETE;
    public static final SctpChannelOption SCTP_FRAGMENT_INTERLEAVE;
    public static final SctpChannelOption SCTP_INIT_MAXSTREAMS;
    public static final SctpChannelOption SCTP_NODELAY;
    public static final SctpChannelOption SCTP_PRIMARY_ADDR;
    public static final SctpChannelOption SCTP_SET_PEER_PRIMARY_ADDR;
    
    @Deprecated
    protected SctpChannelOption(final String s) {
        super(s);
    }
    
    static {
        SCTP_DISABLE_FRAGMENTS = new SctpChannelOption("SCTP_DISABLE_FRAGMENTS");
        SCTP_EXPLICIT_COMPLETE = new SctpChannelOption("SCTP_EXPLICIT_COMPLETE");
        SCTP_FRAGMENT_INTERLEAVE = new SctpChannelOption("SCTP_FRAGMENT_INTERLEAVE");
        SCTP_INIT_MAXSTREAMS = new SctpChannelOption("SCTP_INIT_MAXSTREAMS");
        SCTP_NODELAY = new SctpChannelOption("SCTP_NODELAY");
        SCTP_PRIMARY_ADDR = new SctpChannelOption("SCTP_PRIMARY_ADDR");
        SCTP_SET_PEER_PRIMARY_ADDR = new SctpChannelOption("SCTP_SET_PEER_PRIMARY_ADDR");
    }
}
