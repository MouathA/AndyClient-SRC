package org.yaml.snakeyaml.extensions.compactnotation;

public class PackageCompactConstructor extends CompactConstructor
{
    private String packageName;
    
    public PackageCompactConstructor(final String packageName) {
        this.packageName = packageName;
    }
    
    @Override
    protected Class getClassForName(final String s) throws ClassNotFoundException {
        if (s.indexOf(46) < 0) {
            return Class.forName(this.packageName + "." + s);
        }
        return super.getClassForName(s);
    }
}
