package DTool.values;

import com.darkmagician6.eventapi.events.*;

public class EventChangeValue implements Event
{
    public String valKey;
    public String valName;
    public Object oldVal;
    public Object val;
    
    public EventChangeValue(final String valKey, final String valName, final Object oldVal, final Object val) {
        this.valKey = valKey;
        this.valName = valName;
        this.oldVal = oldVal;
        this.val = val;
    }
}
