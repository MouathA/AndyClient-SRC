package org.apache.http.impl.client;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import org.apache.http.*;
import java.io.*;
import org.apache.http.client.*;

@Immutable
public class BasicResponseHandler implements ResponseHandler
{
    public String handleResponse(final HttpResponse httpResponse) throws HttpResponseException, IOException {
        final StatusLine statusLine = httpResponse.getStatusLine();
        final HttpEntity entity = httpResponse.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        return (entity == null) ? null : EntityUtils.toString(entity);
    }
    
    public Object handleResponse(final HttpResponse httpResponse) throws ClientProtocolException, IOException {
        return this.handleResponse(httpResponse);
    }
}
