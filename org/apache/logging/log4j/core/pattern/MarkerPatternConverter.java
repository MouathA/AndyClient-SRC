package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;

@Plugin(name = "MarkerPatternConverter", category = "Converter")
@ConverterKeys({ "marker" })
public final class MarkerPatternConverter extends LogEventPatternConverter
{
    private MarkerPatternConverter(final String[] array) {
        super("Marker", "marker");
    }
    
    public static MarkerPatternConverter newInstance(final String[] array) {
        return new MarkerPatternConverter(array);
    }
    
    @Override
    public void format(final LogEvent logEvent, final StringBuilder sb) {
        final Marker marker = logEvent.getMarker();
        if (marker != null) {
            sb.append(marker.toString());
        }
    }
}
