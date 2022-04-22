package net.minecraft.world;

import net.minecraft.server.*;
import net.minecraft.profiler.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.border.*;
import net.minecraft.village.*;

public class WorldServerMulti extends WorldServer
{
    private WorldServer delegate;
    private static final String __OBFID;
    
    public WorldServerMulti(final MinecraftServer minecraftServer, final ISaveHandler saveHandler, final int n, final WorldServer delegate, final Profiler profiler) {
        super(minecraftServer, saveHandler, new DerivedWorldInfo(delegate.getWorldInfo()), n, profiler);
        this.delegate = delegate;
        delegate.getWorldBorder().addListener(new IBorderListener() {
            private static final String __OBFID;
            final WorldServerMulti this$0;
            
            @Override
            public void onSizeChanged(final WorldBorder worldBorder, final double transition) {
                this.this$0.getWorldBorder().setTransition(transition);
            }
            
            @Override
            public void func_177692_a(final WorldBorder worldBorder, final double n, final double n2, final long n3) {
                this.this$0.getWorldBorder().setTransition(n, n2, n3);
            }
            
            @Override
            public void onCenterChanged(final WorldBorder worldBorder, final double n, final double n2) {
                this.this$0.getWorldBorder().setCenter(n, n2);
            }
            
            @Override
            public void onWarningTimeChanged(final WorldBorder worldBorder, final int warningTime) {
                this.this$0.getWorldBorder().setWarningTime(warningTime);
            }
            
            @Override
            public void onWarningDistanceChanged(final WorldBorder worldBorder, final int warningDistance) {
                this.this$0.getWorldBorder().setWarningDistance(warningDistance);
            }
            
            @Override
            public void func_177696_b(final WorldBorder worldBorder, final double n) {
                this.this$0.getWorldBorder().func_177744_c(n);
            }
            
            @Override
            public void func_177695_c(final WorldBorder worldBorder, final double damageBuffer) {
                this.this$0.getWorldBorder().setDamageBuffer(damageBuffer);
            }
            
            static {
                __OBFID = "CL_00002273";
            }
        });
    }
    
    @Override
    protected void saveLevel() throws MinecraftException {
    }
    
    @Override
    public World init() {
        this.mapStorage = this.delegate.func_175693_T();
        this.worldScoreboard = this.delegate.getScoreboard();
        final String func_176062_a = VillageCollection.func_176062_a(this.provider);
        final VillageCollection villageCollectionObj = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, func_176062_a);
        if (villageCollectionObj == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(func_176062_a, this.villageCollectionObj);
        }
        else {
            (this.villageCollectionObj = villageCollectionObj).func_82566_a(this);
        }
        return this;
    }
    
    static {
        __OBFID = "CL_00001430";
    }
}
