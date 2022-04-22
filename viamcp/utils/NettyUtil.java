package viamcp.utils;

import io.netty.channel.*;

public class NettyUtil
{
    public static ChannelPipeline decodeEncodePlacement(final ChannelPipeline channelPipeline, String s, final String s2, final ChannelHandler channelHandler) {
        final String s3;
        switch (s3 = s) {
            case "encoder": {
                if (channelPipeline.get("via-encoder") != null) {
                    s = "via-encoder";
                    break;
                }
                break;
            }
            case "decoder": {
                if (channelPipeline.get("via-decoder") != null) {
                    s = "via-decoder";
                    break;
                }
                break;
            }
            default:
                break;
        }
        return channelPipeline.addBefore(s, s2, channelHandler);
    }
}
