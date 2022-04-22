package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.*;
import org.apache.http.entity.*;
import java.io.*;

@Deprecated
@NotThreadSafe
public class EntityEnclosingRequestWrapper extends RequestWrapper implements HttpEntityEnclosingRequest
{
    private HttpEntity entity;
    private boolean consumed;
    
    public EntityEnclosingRequestWrapper(final HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws ProtocolException {
        super(httpEntityEnclosingRequest);
        this.setEntity(httpEntityEnclosingRequest.getEntity());
    }
    
    public HttpEntity getEntity() {
        return this.entity;
    }
    
    public void setEntity(final HttpEntity httpEntity) {
        this.entity = ((httpEntity != null) ? new EntityWrapper(httpEntity) : null);
        this.consumed = false;
    }
    
    public boolean expectContinue() {
        final Header firstHeader = this.getFirstHeader("Expect");
        return firstHeader != null && "100-continue".equalsIgnoreCase(firstHeader.getValue());
    }
    
    @Override
    public boolean isRepeatable() {
        return this.entity == null || this.entity.isRepeatable() || !this.consumed;
    }
    
    static boolean access$002(final EntityEnclosingRequestWrapper entityEnclosingRequestWrapper, final boolean consumed) {
        return entityEnclosingRequestWrapper.consumed = consumed;
    }
    
    class EntityWrapper extends HttpEntityWrapper
    {
        final EntityEnclosingRequestWrapper this$0;
        
        EntityWrapper(final EntityEnclosingRequestWrapper this$0, final HttpEntity httpEntity) {
            this.this$0 = this$0;
            super(httpEntity);
        }
        
        @Override
        public void consumeContent() throws IOException {
            EntityEnclosingRequestWrapper.access$002(this.this$0, true);
            super.consumeContent();
        }
        
        @Override
        public InputStream getContent() throws IOException {
            EntityEnclosingRequestWrapper.access$002(this.this$0, true);
            return super.getContent();
        }
        
        @Override
        public void writeTo(final OutputStream outputStream) throws IOException {
            EntityEnclosingRequestWrapper.access$002(this.this$0, true);
            super.writeTo(outputStream);
        }
    }
}
