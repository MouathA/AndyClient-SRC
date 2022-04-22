package joptsimple.internal;

class Row
{
    final String option;
    final String description;
    
    Row(final String option, final String description) {
        this.option = option;
        this.description = description;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        final Row row = (Row)o;
        return this.option.equals(row.option) && this.description.equals(row.description);
    }
    
    @Override
    public int hashCode() {
        return this.option.hashCode() ^ this.description.hashCode();
    }
}
