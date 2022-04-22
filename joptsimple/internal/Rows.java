package joptsimple.internal;

import java.util.*;

public class Rows
{
    private final int overallWidth;
    private final int columnSeparatorWidth;
    private final Set rows;
    private int widthOfWidestOption;
    private int widthOfWidestDescription;
    
    public Rows(final int overallWidth, final int columnSeparatorWidth) {
        this.rows = new LinkedHashSet();
        this.overallWidth = overallWidth;
        this.columnSeparatorWidth = columnSeparatorWidth;
    }
    
    public void add(final String s, final String s2) {
        this.add(new Row(s, s2));
    }
    
    private void add(final Row row) {
        this.rows.add(row);
        this.widthOfWidestOption = Math.max(this.widthOfWidestOption, row.option.length());
        this.widthOfWidestDescription = Math.max(this.widthOfWidestDescription, row.description.length());
    }
    
    private void reset() {
        this.rows.clear();
        this.widthOfWidestOption = 0;
        this.widthOfWidestDescription = 0;
    }
    
    public void fitToWidth() {
        final Columns columns = new Columns(this.optionWidth(), this.descriptionWidth());
        final LinkedHashSet<Row> set = new LinkedHashSet<Row>();
        final Iterator<Row> iterator = this.rows.iterator();
        while (iterator.hasNext()) {
            set.addAll((Collection<?>)columns.fit(iterator.next()));
        }
        this.reset();
        final Iterator<Object> iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            this.add(iterator2.next());
        }
    }
    
    public String render() {
        final StringBuilder sb = new StringBuilder();
        for (final Row row : this.rows) {
            this.pad(sb, row.option, this.optionWidth()).append(Strings.repeat(' ', this.columnSeparatorWidth));
            this.pad(sb, row.description, this.descriptionWidth()).append(Strings.LINE_SEPARATOR);
        }
        return sb.toString();
    }
    
    private int optionWidth() {
        return Math.min((this.overallWidth - this.columnSeparatorWidth) / 2, this.widthOfWidestOption);
    }
    
    private int descriptionWidth() {
        return Math.min((this.overallWidth - this.columnSeparatorWidth) / 2, this.widthOfWidestDescription);
    }
    
    private StringBuilder pad(final StringBuilder sb, final String s, final int n) {
        sb.append(s).append(Strings.repeat(' ', n - s.length()));
        return sb;
    }
}
