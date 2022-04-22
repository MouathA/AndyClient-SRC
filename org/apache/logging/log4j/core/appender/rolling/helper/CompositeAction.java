package org.apache.logging.log4j.core.appender.rolling.helper;

import java.util.*;
import java.io.*;

public class CompositeAction extends AbstractAction
{
    private final Action[] actions;
    private final boolean stopOnError;
    
    public CompositeAction(final List list, final boolean stopOnError) {
        list.toArray(this.actions = new Action[list.size()]);
        this.stopOnError = stopOnError;
    }
    
    @Override
    public void run() {
        this.execute();
    }
    
    @Override
    public boolean execute() throws IOException {
        if (this.stopOnError) {
            final Action[] actions = this.actions;
            while (0 < actions.length) {
                if (!actions[0].execute()) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        final Object o = null;
        final Action[] actions2 = this.actions;
        while (0 < actions2.length) {
            final boolean b = false & actions2[0].execute();
            int n2 = 0;
            ++n2;
        }
        if (o != null) {
            throw o;
        }
        return false;
    }
}
