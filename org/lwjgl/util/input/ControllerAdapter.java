package org.lwjgl.util.input;

import org.lwjgl.input.*;

public class ControllerAdapter implements Controller
{
    public String getName() {
        return "Dummy Controller";
    }
    
    public int getIndex() {
        return 0;
    }
    
    public int getButtonCount() {
        return 0;
    }
    
    public String getButtonName(final int n) {
        return "button n/a";
    }
    
    public boolean isButtonPressed(final int n) {
        return false;
    }
    
    public void poll() {
    }
    
    public float getPovX() {
        return 0.0f;
    }
    
    public float getPovY() {
        return 0.0f;
    }
    
    public float getDeadZone(final int n) {
        return 0.0f;
    }
    
    public void setDeadZone(final int n, final float n2) {
    }
    
    public int getAxisCount() {
        return 0;
    }
    
    public String getAxisName(final int n) {
        return "axis n/a";
    }
    
    public float getAxisValue(final int n) {
        return 0.0f;
    }
    
    public float getXAxisValue() {
        return 0.0f;
    }
    
    public float getXAxisDeadZone() {
        return 0.0f;
    }
    
    public void setXAxisDeadZone(final float n) {
    }
    
    public float getYAxisValue() {
        return 0.0f;
    }
    
    public float getYAxisDeadZone() {
        return 0.0f;
    }
    
    public void setYAxisDeadZone(final float n) {
    }
    
    public float getZAxisValue() {
        return 0.0f;
    }
    
    public float getZAxisDeadZone() {
        return 0.0f;
    }
    
    public void setZAxisDeadZone(final float n) {
    }
    
    public float getRXAxisValue() {
        return 0.0f;
    }
    
    public float getRXAxisDeadZone() {
        return 0.0f;
    }
    
    public void setRXAxisDeadZone(final float n) {
    }
    
    public float getRYAxisValue() {
        return 0.0f;
    }
    
    public float getRYAxisDeadZone() {
        return 0.0f;
    }
    
    public void setRYAxisDeadZone(final float n) {
    }
    
    public float getRZAxisValue() {
        return 0.0f;
    }
    
    public float getRZAxisDeadZone() {
        return 0.0f;
    }
    
    public void setRZAxisDeadZone(final float n) {
    }
    
    public int getRumblerCount() {
        return 0;
    }
    
    public String getRumblerName(final int n) {
        return "rumber n/a";
    }
    
    public void setRumblerStrength(final int n, final float n2) {
    }
}
