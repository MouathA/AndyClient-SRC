package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.provider;

import com.viaversion.viaversion.api.platform.providers.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public abstract class TitleRenderProvider implements Provider
{
    protected Map fadeIn;
    protected Map stay;
    protected Map fadeOut;
    protected Map titles;
    protected Map subTitles;
    protected Map times;
    
    public TitleRenderProvider() {
        this.fadeIn = new HashMap();
        this.stay = new HashMap();
        this.fadeOut = new HashMap();
        this.titles = new HashMap();
        this.subTitles = new HashMap();
        this.times = new HashMap();
    }
    
    public void setTimings(final UUID uuid, final int n, final int n2, final int n3) {
        this.setFadeIn(uuid, n);
        this.setStay(uuid, n2);
        this.setFadeOut(uuid, n3);
        final AtomicInteger time = this.getTime(uuid);
        if (time.get() > 0) {
            time.set(this.getFadeIn(uuid) + this.getStay(uuid) + this.getFadeOut(uuid));
        }
    }
    
    public void reset(final UUID uuid) {
        this.titles.remove(uuid);
        this.subTitles.remove(uuid);
        this.getTime(uuid).set(0);
        this.fadeIn.remove(uuid);
        this.stay.remove(uuid);
        this.fadeOut.remove(uuid);
    }
    
    public void setTitle(final UUID uuid, final String s) {
        this.titles.put(uuid, s);
        this.getTime(uuid).set(this.getFadeIn(uuid) + this.getStay(uuid) + this.getFadeOut(uuid));
    }
    
    public void setSubTitle(final UUID uuid, final String s) {
        this.subTitles.put(uuid, s);
    }
    
    public void clear(final UUID uuid) {
        this.titles.remove(uuid);
        this.subTitles.remove(uuid);
        this.getTime(uuid).set(0);
    }
    
    public AtomicInteger getTime(final UUID uuid) {
        return this.times.computeIfAbsent(uuid, TitleRenderProvider::lambda$getTime$0);
    }
    
    public int getFadeIn(final UUID uuid) {
        return this.fadeIn.getOrDefault(uuid, 10);
    }
    
    public int getStay(final UUID uuid) {
        return this.stay.getOrDefault(uuid, 70);
    }
    
    public int getFadeOut(final UUID uuid) {
        return this.fadeOut.getOrDefault(uuid, 20);
    }
    
    public void setFadeIn(final UUID uuid, final int n) {
        if (n >= 0) {
            this.fadeIn.put(uuid, n);
        }
        else {
            this.fadeIn.remove(uuid);
        }
    }
    
    public void setStay(final UUID uuid, final int n) {
        if (n >= 0) {
            this.stay.put(uuid, n);
        }
        else {
            this.stay.remove(uuid);
        }
    }
    
    public void setFadeOut(final UUID uuid, final int n) {
        if (n >= 0) {
            this.fadeOut.put(uuid, n);
        }
        else {
            this.fadeOut.remove(uuid);
        }
    }
    
    private static AtomicInteger lambda$getTime$0(final UUID uuid) {
        return new AtomicInteger(0);
    }
}
