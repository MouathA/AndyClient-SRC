package net.java.games.input;

public class LinuxJoystickPOV extends LinuxJoystickAxis
{
    private LinuxJoystickAxis hatX;
    private LinuxJoystickAxis hatY;
    
    LinuxJoystickPOV(final Component.Identifier.Axis axis, final LinuxJoystickAxis hatX, final LinuxJoystickAxis hatY) {
        super(axis, false);
        this.hatX = hatX;
        this.hatY = hatY;
    }
    
    protected LinuxJoystickAxis getXAxis() {
        return this.hatX;
    }
    
    protected LinuxJoystickAxis getYAxis() {
        return this.hatY;
    }
    
    protected void updateValue() {
        final float pollData = this.hatX.getPollData();
        final float pollData2 = this.hatY.getPollData();
        this.resetHasPolled();
        if (pollData == -1.0f && pollData2 == -1.0f) {
            this.setValue(0.125f);
        }
        else if (pollData == -1.0f && pollData2 == 0.0f) {
            this.setValue(1.0f);
        }
        else if (pollData == -1.0f && pollData2 == 1.0f) {
            this.setValue(0.875f);
        }
        else if (pollData == 0.0f && pollData2 == -1.0f) {
            this.setValue(0.25f);
        }
        else if (pollData == 0.0f && pollData2 == 0.0f) {
            this.setValue(0.0f);
        }
        else if (pollData == 0.0f && pollData2 == 1.0f) {
            this.setValue(0.75f);
        }
        else if (pollData == 1.0f && pollData2 == -1.0f) {
            this.setValue(0.375f);
        }
        else if (pollData == 1.0f && pollData2 == 0.0f) {
            this.setValue(0.5f);
        }
        else if (pollData == 1.0f && pollData2 == 1.0f) {
            this.setValue(0.625f);
        }
        else {
            ControllerEnvironment.logln("Unknown values x = " + pollData + " | y = " + pollData2);
            this.setValue(0.0f);
        }
    }
}
