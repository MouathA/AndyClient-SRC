package org.apache.logging.log4j.core.helpers;

import java.io.*;
import java.util.*;

public class Throwables
{
    public static List toStringList(final Throwable t) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        printWriter.flush();
        final LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(stringWriter.toString()));
        final ArrayList<String> list = new ArrayList<String>();
        for (String s = lineNumberReader.readLine(); s != null; s = lineNumberReader.readLine()) {
            list.add(s);
        }
        return list;
    }
}
