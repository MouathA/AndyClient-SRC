package paulscode.sound;

public class SoundSystemLogger
{
    public void message(final String s, final int n) {
        String string = "";
        while (0 < n) {
            string += "    ";
            int n2 = 0;
            ++n2;
        }
        System.out.println(string + s);
    }
    
    public void importantMessage(final String s, final int n) {
        String string = "";
        while (0 < n) {
            string += "    ";
            int n2 = 0;
            ++n2;
        }
        System.out.println(string + s);
    }
    
    public boolean errorCheck(final boolean b, final String s, final String s2, final int n) {
        if (b) {
            this.errorMessage(s, s2, n);
        }
        return b;
    }
    
    public void errorMessage(final String s, final String s2, final int n) {
        String string = "";
        while (0 < n) {
            string += "    ";
            int n2 = 0;
            ++n2;
        }
        final String string2 = string + "Error in class '" + s + "'";
        final String string3 = "    " + string + s2;
        System.out.println(string2);
        System.out.println(string3);
    }
    
    public void printStackTrace(final Exception ex, final int n) {
        this.printExceptionMessage(ex, n);
        this.importantMessage("STACK TRACE:", n);
        if (ex == null) {
            return;
        }
        final StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace == null) {
            return;
        }
        while (0 < stackTrace.length) {
            final StackTraceElement stackTraceElement = stackTrace[0];
            if (stackTraceElement != null) {
                this.message(stackTraceElement.toString(), n + 1);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public void printExceptionMessage(final Exception ex, final int n) {
        this.importantMessage("ERROR MESSAGE:", n);
        if (ex.getMessage() == null) {
            this.message("(none)", n + 1);
        }
        else {
            this.message(ex.getMessage(), n + 1);
        }
    }
}
