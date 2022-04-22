package net.minecraft.command.server;

import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTeleport extends CommandBase
{
    private static final String __OBFID;
    
    @Override
    public String getCommandName() {
        return "tp";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.tp.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        Entity entity;
        if (array.length != 2 && array.length != 4 && array.length != 6) {
            entity = CommandBase.getCommandSenderAsPlayer(commandSender);
        }
        else {
            entity = CommandBase.func_175768_b(commandSender, array[0]);
        }
        if (array.length != 1 && array.length != 2) {
            if (array.length < 4) {
                throw new WrongUsageException("commands.tp.usage", new Object[0]);
            }
            if (((EntityPlayerMP)entity).worldObj != null) {
                final CoordinateArg func_175770_a = CommandBase.func_175770_a(((EntityPlayerMP)entity).posX, array[1], true);
                final double posY = ((EntityPlayerMP)entity).posY;
                final int n = 2;
                int n2 = 0;
                ++n2;
                final CoordinateArg func_175767_a = CommandBase.func_175767_a(posY, array[n], 0, 0, false);
                final double posZ = ((EntityPlayerMP)entity).posZ;
                final int n3 = 2;
                ++n2;
                final CoordinateArg func_175770_a2 = CommandBase.func_175770_a(posZ, array[n3], true);
                final double n4 = ((EntityPlayerMP)entity).rotationYaw;
                String s;
                if (array.length > 2) {
                    final int n5 = 2;
                    ++n2;
                    s = array[n5];
                }
                else {
                    s = "~";
                }
                final CoordinateArg func_175770_a3 = CommandBase.func_175770_a(n4, s, false);
                final CoordinateArg func_175770_a4 = CommandBase.func_175770_a(((EntityPlayerMP)entity).rotationPitch, (array.length > 2) ? array[2] : "~", false);
                if (entity instanceof EntityPlayerMP) {
                    final EnumSet<S08PacketPlayerPosLook.EnumFlags> none = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
                    if (func_175770_a.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.X);
                    }
                    if (func_175767_a.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Y);
                    }
                    if (func_175770_a2.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Z);
                    }
                    if (func_175770_a4.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
                    }
                    if (func_175770_a3.func_179630_c()) {
                        none.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
                    }
                    float rotationYawHead = (float)func_175770_a3.func_179629_b();
                    if (!func_175770_a3.func_179630_c()) {
                        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead);
                    }
                    float n6 = (float)func_175770_a4.func_179629_b();
                    if (!func_175770_a4.func_179630_c()) {
                        n6 = MathHelper.wrapAngleTo180_float(n6);
                    }
                    if (n6 > 90.0f || n6 < -90.0f) {
                        n6 = MathHelper.wrapAngleTo180_float(180.0f - n6);
                        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead + 180.0f);
                    }
                    ((EntityPlayerMP)entity).mountEntity(null);
                    ((EntityPlayerMP)entity).playerNetServerHandler.func_175089_a(func_175770_a.func_179629_b(), func_175767_a.func_179629_b(), func_175770_a2.func_179629_b(), rotationYawHead, n6, none);
                    ((EntityPlayerMP)entity).setRotationYawHead(rotationYawHead);
                }
                else {
                    float wrapAngleTo180_float = (float)MathHelper.wrapAngleTo180_double(func_175770_a3.func_179628_a());
                    float wrapAngleTo180_float2 = (float)MathHelper.wrapAngleTo180_double(func_175770_a4.func_179628_a());
                    if (wrapAngleTo180_float2 > 90.0f || wrapAngleTo180_float2 < -90.0f) {
                        wrapAngleTo180_float2 = MathHelper.wrapAngleTo180_float(180.0f - wrapAngleTo180_float2);
                        wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(wrapAngleTo180_float + 180.0f);
                    }
                    ((EntityPlayerMP)entity).setLocationAndAngles(func_175770_a.func_179628_a(), func_175767_a.func_179628_a(), func_175770_a2.func_179628_a(), wrapAngleTo180_float, wrapAngleTo180_float2);
                    ((EntityPlayerMP)entity).setRotationYawHead(wrapAngleTo180_float);
                }
                CommandBase.notifyOperators(commandSender, this, "commands.tp.success.coordinates", ((EntityPlayerMP)entity).getName(), func_175770_a.func_179628_a(), func_175767_a.func_179628_a(), func_175770_a2.func_179628_a());
            }
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[array.length - 1]);
            if (func_175768_b.worldObj != ((EntityPlayerMP)entity).worldObj) {
                throw new CommandException("commands.tp.notSameDimension", new Object[0]);
            }
            ((EntityPlayerMP)entity).mountEntity(null);
            if (entity instanceof EntityPlayerMP) {
                ((EntityPlayerMP)entity).playerNetServerHandler.setPlayerLocation(func_175768_b.posX, func_175768_b.posY, func_175768_b.posZ, func_175768_b.rotationYaw, func_175768_b.rotationPitch);
            }
            else {
                ((EntityPlayerMP)entity).setLocationAndAngles(func_175768_b.posX, func_175768_b.posY, func_175768_b.posZ, func_175768_b.rotationYaw, func_175768_b.rotationPitch);
            }
            CommandBase.notifyOperators(commandSender, this, "commands.tp.success", ((EntityPlayerMP)entity).getName(), func_175768_b.getName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length != 1 && array.length != 2) ? null : CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    static {
        __OBFID = "CL_00001180";
    }
}
