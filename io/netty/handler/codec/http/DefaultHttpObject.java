package io.netty.handler.codec.http;

import io.netty.handler.codec.*;

public class DefaultHttpObject implements HttpObject
{
    private DecoderResult decoderResult;
    
    protected DefaultHttpObject() {
        this.decoderResult = DecoderResult.SUCCESS;
    }
    
    @Override
    public DecoderResult getDecoderResult() {
        return this.decoderResult;
    }
    
    @Override
    public void setDecoderResult(final DecoderResult decoderResult) {
        if (decoderResult == null) {
            throw new NullPointerException("decoderResult");
        }
        this.decoderResult = decoderResult;
    }
}
