package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.core.*;
import java.io.*;
import org.apache.logging.log4j.core.helpers.*;

@Plugin(name = "ThrowablePatternConverter", category = "Converter")
@ConverterKeys({ "ex", "throwable", "exception" })
public class ThrowablePatternConverter extends LogEventPatternConverter
{
    private String rawOption;
    protected final ThrowableFormatOptions options;
    
    protected ThrowablePatternConverter(final String s, final String s2, final String[] array) {
        super(s, s2);
        this.options = ThrowableFormatOptions.newInstance(array);
        if (array != null && array.length > 0) {
            this.rawOption = array[0];
        }
    }
    
    public static ThrowablePatternConverter newInstance(final String[] array) {
        return new ThrowablePatternConverter("Throwable", "throwable", array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final Throwable thrown = logEvent.getThrown();
        if (this.isSubShortOption()) {
            this.formatSubShortOption(thrown, sb);
        }
        else if (thrown != null && this.options.anyLines()) {
            this.formatOption(thrown, sb);
        }
    }
    
    private boolean isSubShortOption() {
        return "short.message".equalsIgnoreCase(this.rawOption) || "short.localizedMessage".equalsIgnoreCase(this.rawOption) || "short.fileName".equalsIgnoreCase(this.rawOption) || "short.lineNumber".equalsIgnoreCase(this.rawOption) || "short.methodName".equalsIgnoreCase(this.rawOption) || "short.className".equalsIgnoreCase(this.rawOption);
    }
    
    private void formatSubShortOption(final Throwable t, final StringBuilder sb) {
        StackTraceElement stackTraceElement = null;
        if (t != null) {
            final StackTraceElement[] stackTrace = t.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                stackTraceElement = stackTrace[0];
            }
        }
        if (t != null && stackTraceElement != null) {
            String s = "";
            if ("short.className".equalsIgnoreCase(this.rawOption)) {
                s = stackTraceElement.getClassName();
            }
            else if ("short.methodName".equalsIgnoreCase(this.rawOption)) {
                s = stackTraceElement.getMethodName();
            }
            else if ("short.lineNumber".equalsIgnoreCase(this.rawOption)) {
                s = String.valueOf(stackTraceElement.getLineNumber());
            }
            else if ("short.message".equalsIgnoreCase(this.rawOption)) {
                s = t.getMessage();
            }
            else if ("short.localizedMessage".equalsIgnoreCase(this.rawOption)) {
                s = t.getLocalizedMessage();
            }
            else if ("short.fileName".equalsIgnoreCase(this.rawOption)) {
                s = stackTraceElement.getFileName();
            }
            final int length = sb.length();
            if (length > 0 && !Character.isWhitespace(sb.charAt(length - 1))) {
                sb.append(" ");
            }
            sb.append(s);
        }
    }
    
    private void formatOption(final Throwable t, final StringBuilder sb) {
        final StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        final int length = sb.length();
        if (length > 0 && !Character.isWhitespace(sb.charAt(length - 1))) {
            sb.append(' ');
        }
        if (!this.options.allLines() || !Constants.LINE_SEP.equals(this.options.getSeparator())) {
            final StringBuilder sb2 = new StringBuilder();
            final String[] split = stringWriter.toString().split(Constants.LINE_SEP);
            final int n = this.options.minLines(split.length) - 1;
            while (0 <= n) {
                sb2.append(split[0]);
                if (0 < n) {
                    sb2.append(this.options.getSeparator());
                }
                int n2 = 0;
                ++n2;
            }
            sb.append(sb2.toString());
        }
        else {
            sb.append(stringWriter.toString());
        }
    }
    
    @Override
    public boolean handlesThrowable() {
        return true;
    }
}
