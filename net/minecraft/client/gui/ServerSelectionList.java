package net.minecraft.client.gui;

import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.network.*;
import java.util.*;

public class ServerSelectionList extends GuiListExtended
{
    private final GuiMultiplayer owner;
    private final List field_148198_l;
    private final List field_148199_m;
    private final IGuiListEntry lanScanEntry;
    private int field_148197_o;
    private static final String __OBFID;
    
    public ServerSelectionList(final GuiMultiplayer owner, final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5) {
        super(minecraft, n, n2, n3, n4, n5);
        this.field_148198_l = Lists.newArrayList();
        this.field_148199_m = Lists.newArrayList();
        this.lanScanEntry = new ServerListEntryLanScan();
        this.field_148197_o = -1;
        this.owner = owner;
    }
    
    @Override
    public IGuiListEntry getListEntry(int n) {
        if (n < this.field_148198_l.size()) {
            return this.field_148198_l.get(n);
        }
        n -= this.field_148198_l.size();
        if (n == 0) {
            return this.lanScanEntry;
        }
        --n;
        return this.field_148199_m.get(n);
    }
    
    @Override
    protected int getSize() {
        return this.field_148198_l.size() + 1 + this.field_148199_m.size();
    }
    
    public void func_148192_c(final int field_148197_o) {
        this.field_148197_o = field_148197_o;
    }
    
    @Override
    protected boolean isSelected(final int n) {
        return n == this.field_148197_o;
    }
    
    public int func_148193_k() {
        return this.field_148197_o;
    }
    
    public void func_148195_a(final ServerList list) {
        this.field_148198_l.clear();
        while (0 < list.countServers()) {
            this.field_148198_l.add(new ServerListEntryNormal(this.owner, list.getServerData(0)));
            int n = 0;
            ++n;
        }
    }
    
    public void func_148194_a(final List list) {
        this.field_148199_m.clear();
        final Iterator<LanServerDetector.LanServer> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.field_148199_m.add(new ServerListEntryLanDetected(this.owner, iterator.next()));
        }
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 30;
    }
    
    @Override
    public int getListWidth() {
        return super.getListWidth() + 85;
    }
    
    static {
        __OBFID = "CL_00000819";
    }
}
