package net.minecraft.client.gui;

public interface IProgressMeter
{
    default static {
        IProgressMeter.lanSearchStates = new String[] { "oooooo", "Oooooo", "oOoooo", "ooOooo", "oooOoo", "ooooOo", "oooooO" };
    }
    
    void doneLoading();
}
