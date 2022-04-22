package net.minecraft.command.server;

import net.minecraft.entity.effect.*;
import net.minecraft.command.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class CommandSummon extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "summon";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.summon.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
        final String s = array[0];
        BlockPos position = commandSender.getPosition();
        final Vec3 positionVector = commandSender.getPositionVector();
        double n = positionVector.xCoord;
        double n2 = positionVector.yCoord;
        double n3 = positionVector.zCoord;
        if (array.length >= 4) {
            n = CommandBase.func_175761_b(n, array[1], true);
            n2 = CommandBase.func_175761_b(n2, array[2], false);
            n3 = CommandBase.func_175761_b(n3, array[3], true);
            position = new BlockPos(n, n2, n3);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(position)) {
            throw new CommandException("commands.summon.outOfWorld", new Object[0]);
        }
        if ("LightningBolt".equals(s)) {
            entityWorld.addWeatherEffect(new EntityLightningBolt(entityWorld, n, n2, n3));
            CommandBase.notifyOperators(commandSender, this, "commands.summon.success", new Object[0]);
        }
        else {
            NBTTagCompound func_180713_a = new NBTTagCompound();
            if (array.length >= 5) {
                func_180713_a = JsonToNBT.func_180713_a(CommandBase.getChatComponentFromNthArg(commandSender, array, 4).getUnformattedText());
            }
            func_180713_a.setString("id", s);
            final Entity entityFromNBT = EntityList.createEntityFromNBT(func_180713_a, entityWorld);
            if (entityFromNBT == null) {
                throw new CommandException("commands.summon.failed", new Object[0]);
            }
            entityFromNBT.setLocationAndAngles(n, n2, n3, entityFromNBT.rotationYaw, entityFromNBT.rotationPitch);
            if (!true && entityFromNBT instanceof EntityLiving) {
                ((EntityLiving)entityFromNBT).func_180482_a(entityWorld.getDifficultyForLocation(new BlockPos(entityFromNBT)), null);
            }
            entityWorld.spawnEntityInWorld(entityFromNBT);
            Entity entity = entityFromNBT;
            Entity entityFromNBT2;
            for (NBTTagCompound compoundTag = func_180713_a; entity != null && compoundTag.hasKey("Riding", 10); entity = entityFromNBT2, compoundTag = compoundTag.getCompoundTag("Riding")) {
                entityFromNBT2 = EntityList.createEntityFromNBT(compoundTag.getCompoundTag("Riding"), entityWorld);
                if (entityFromNBT2 != null) {
                    entityFromNBT2.setLocationAndAngles(n, n2, n3, entityFromNBT2.rotationYaw, entityFromNBT2.rotationPitch);
                    entityWorld.spawnEntityInWorld(entityFromNBT2);
                    entity.mountEntity(entityFromNBT2);
                }
            }
            CommandBase.notifyOperators(commandSender, this, "commands.summon.success", new Object[0]);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.func_175762_a(array, EntityList.func_180124_b()) : ((array.length > 1 && array.length <= 4) ? CommandBase.func_175771_a(array, 1, blockPos) : null);
    }
    
    static {
        __OBFID = "CL_00001158";
    }
}
