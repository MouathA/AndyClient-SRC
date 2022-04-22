package org.apache.http.client.entity;

import org.apache.http.annotation.*;
import java.util.*;
import org.apache.http.protocol.*;
import org.apache.http.client.utils.*;
import org.apache.http.entity.*;
import java.io.*;
import java.nio.charset.*;

@NotThreadSafe
public class UrlEncodedFormEntity extends StringEntity
{
    public UrlEncodedFormEntity(final List list, final String s) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(list, (s != null) ? s : HTTP.DEF_CONTENT_CHARSET.name()), ContentType.create("application/x-www-form-urlencoded", s));
    }
    
    public UrlEncodedFormEntity(final Iterable iterable, final Charset charset) {
        super(URLEncodedUtils.format(iterable, (charset != null) ? charset : HTTP.DEF_CONTENT_CHARSET), ContentType.create("application/x-www-form-urlencoded", charset));
    }
    
    public UrlEncodedFormEntity(final List list) throws UnsupportedEncodingException {
        this(list, (Charset)null);
    }
    
    public UrlEncodedFormEntity(final Iterable iterable) {
        this(iterable, null);
    }
}
