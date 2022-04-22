package joptsimple.internal;

import java.util.*;
import java.text.*;

class Columns
{
    private static final int INDENT_WIDTH = 2;
    private final int optionWidth;
    private final int descriptionWidth;
    
    Columns(final int optionWidth, final int descriptionWidth) {
        this.optionWidth = optionWidth;
        this.descriptionWidth = descriptionWidth;
    }
    
    List fit(final Row row) {
        final List pieces = this.piecesOf(row.option, this.optionWidth);
        final List pieces2 = this.piecesOf(row.description, this.descriptionWidth);
        final ArrayList<Row> list = new ArrayList<Row>();
        while (0 < Math.max(pieces.size(), pieces2.size())) {
            list.add(new Row(itemOrEmpty(pieces, 0), itemOrEmpty(pieces2, 0)));
            int n = 0;
            ++n;
        }
        return list;
    }
    
    private static String itemOrEmpty(final List list, final int n) {
        return (n >= list.size()) ? "" : list.get(n);
    }
    
    private List piecesOf(final String s, final int n) {
        final ArrayList list = new ArrayList();
        final String[] split = s.trim().split(Strings.LINE_SEPARATOR);
        while (0 < split.length) {
            list.addAll(this.piecesOfEmbeddedLine(split[0], n));
            int n2 = 0;
            ++n2;
        }
        return list;
    }
    
    private List piecesOfEmbeddedLine(final String text, final int n) {
        final ArrayList<String> list = new ArrayList<String>();
        final BreakIterator lineInstance = BreakIterator.getLineInstance(Locale.US);
        lineInstance.setText(text);
        StringBuilder processNextWord = new StringBuilder();
        int first = lineInstance.first();
        for (int i = lineInstance.next(); i != -1; i = lineInstance.next()) {
            processNextWord = this.processNextWord(text, processNextWord, first, i, n, list);
            first = i;
        }
        if (processNextWord.length() > 0) {
            list.add(processNextWord.toString());
        }
        return list;
    }
    
    private StringBuilder processNextWord(final String s, final StringBuilder sb, final int n, final int n2, final int n3, final List list) {
        StringBuilder append = sb;
        final String substring = s.substring(n, n2);
        if (append.length() + substring.length() > n3) {
            list.add(append.toString().replaceAll("\\s+$", ""));
            append = new StringBuilder(Strings.repeat(' ', 2)).append(substring);
        }
        else {
            append.append(substring);
        }
        return append;
    }
}
