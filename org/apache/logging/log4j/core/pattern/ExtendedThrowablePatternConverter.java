package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.helpers.*;
import org.apache.logging.log4j.core.impl.*;

@Plugin(name = "ExtendedThrowablePatternConverter", category = "Converter")
@ConverterKeys({ "xEx", "xThrowable", "xException" })
public final class ExtendedThrowablePatternConverter extends ThrowablePatternConverter
{
    private ExtendedThrowablePatternConverter(final String[] array) {
        super("ExtendedThrowable", "throwable", array);
    }
    
    public static ExtendedThrowablePatternConverter newInstance(final String[] array) {
        return new ExtendedThrowablePatternConverter(array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        ThrowableProxy thrownProxy = null;
        if (logEvent instanceof Log4jLogEvent) {
            thrownProxy = ((Log4jLogEvent)logEvent).getThrownProxy();
        }
        if (logEvent.getThrown() != null && this.options.anyLines()) {
            if (thrownProxy == null) {
                super.format(logEvent, sb);
                return;
            }
            final String extendedStackTrace = thrownProxy.getExtendedStackTrace(this.options.getPackages());
            final int length = sb.length();
            if (length > 0 && !Character.isWhitespace(sb.charAt(length - 1))) {
                sb.append(" ");
            }
            if (!this.options.allLines() || !Constants.LINE_SEP.equals(this.options.getSeparator())) {
                final StringBuilder sb2 = new StringBuilder();
                final String[] split = extendedStackTrace.split(Constants.LINE_SEP);
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
                sb.append(extendedStackTrace);
            }
        }
    }
}
