package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.error.*;

public class ScannerException extends MarkedYAMLException
{
    private static final long serialVersionUID = 4782293188600445954L;
    
    public ScannerException(final String s, final Mark mark, final String s2, final Mark mark2, final String s3) {
        super(s, mark, s2, mark2, s3);
    }
    
    public ScannerException(final String s, final Mark mark, final String s2, final Mark mark2) {
        this(s, mark, s2, mark2, (String)null);
    }
}
