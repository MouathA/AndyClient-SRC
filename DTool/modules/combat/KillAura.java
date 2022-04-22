package DTool.modules.combat;

import DTool.modules.*;
import java.util.*;
import DTool.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import DTool.events.*;
import DTool.events.listeners.*;

public class KillAura extends Module
{
    public static EntityLivingBase targetEntity;
    public ArrayList friends;
    private final TimeManager timer;
    public float range;
    public float rotationYaw;
    public float rotationPitch;
    private boolean lockview;
    public float aps;
    
    public KillAura() {
        super("KillAura", 19, Category.Combat);
        this.friends = new ArrayList();
        this.timer = new TimeManager();
        this.range = 6.85f;
        this.aps = 6.1f;
    }
    
    @Override
    public void onDisable() {
        KillAura.targetEntity = null;
    }
    
    @Override
    public boolean onEnable() {
        return this.toggled;
    }
    
    public final void attackEntity(final EntityLivingBase entityLivingBase) {
        Minecraft.thePlayer.swingItem();
        Minecraft.thePlayer.setSprinting(false);
        Minecraft.playerController.attackEntity(Minecraft.thePlayer, entityLivingBase);
        Minecraft.thePlayer.setSprinting(false);
    }
    
    private EntityLivingBase getEntity() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //     6: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    11: astore_2       
        //    12: goto            72
        //    15: aload_2        
        //    16: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    21: astore_1       
        //    22: aload_1        
        //    23: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //    26: ifne            32
        //    29: goto            72
        //    32: aload_1        
        //    33: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //    36: astore_3       
        //    37: aload_3        
        //    38: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    41: if_acmpne       47
        //    44: goto            72
        //    47: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    50: aload_3        
        //    51: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.canEntityBeSeen:(Lnet/minecraft/entity/Entity;)Z
        //    54: ifne            60
        //    57: goto            72
        //    60: aload_0        
        //    61: aload_3        
        //    62: ifnull          72
        //    65: aload_0        
        //    66: invokevirtual   DTool/modules/combat/KillAura.isEnable:()Z
        //    69: pop            
        //    70: aload_3        
        //    71: areturn        
        //    72: aload_2        
        //    73: invokeinterface java/util/Iterator.hasNext:()Z
        //    78: ifne            15
        //    81: aconst_null    
        //    82: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0072 (coming from #0062).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public synchronized void faceEntity(final Entity entity) {
        final double n = entity.posX - Minecraft.thePlayer.posX;
        final double n2 = entity.posZ - Minecraft.thePlayer.posZ;
        final double n3 = entity.posY - Minecraft.thePlayer.posY + entity.height / 1.5;
        final double n4 = MathHelper.sqrt_double(n * n + n2 * n2);
        final float n5 = (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f;
        Minecraft.thePlayer.rotationPitch += MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n3, n4) * 180.0 / 3.141592653589793)) - Minecraft.thePlayer.rotationPitch);
        Minecraft.thePlayer.rotationYaw += MathHelper.wrapAngleTo180_float(n5 - Minecraft.thePlayer.rotationYaw);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            this.rotationYaw = Minecraft.thePlayer.rotationYaw;
            this.rotationPitch = Minecraft.thePlayer.rotationPitch;
            KillAura.targetEntity = this.getEntity();
            if (KillAura.targetEntity == null) {
                return;
            }
            this.faceEntity(KillAura.targetEntity);
            if (KillAura.targetEntity == null) {
                return;
            }
            if (this.timer.sleep(this.timer.convertToMillis(this.aps))) {
                this.attackEntity(KillAura.targetEntity);
                this.timer.resetTime();
            }
            if (!this.lockview) {
                Minecraft.thePlayer.rotationYaw = this.rotationYaw;
                Minecraft.thePlayer.rotationPitch = this.rotationPitch;
            }
        }
    }
}
