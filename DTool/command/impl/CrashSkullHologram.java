package DTool.command.impl;

import DTool.command.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import Mood.*;

public class CrashSkullHologram extends Command
{
    public CrashSkullHologram() {
        super("CrashSkullHologram", "CrashSkullHologram", "CrashSkullHologram", new String[] { "CrashSkullHologram" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        Minecraft.getMinecraft();
        CrashSkullHologram.posX = (int)Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        CrashSkullHologram.posY = (int)Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        CrashSkullHologram.posZ = (int)Minecraft.thePlayer.posZ;
        HackedItemUtils.addItem(HackedItemUtils.createItem(Items.armor_stand, 1, 0, "{display:{Lore:[0:\"§6Poz\u00edci\u00f3§e X§7: §6" + 1 + "\",1:\"§6Poz\u00edci\u00f3§e Y§7: §6" + 1 + "\",2:\"§6Poz\u00edci\u00f3§e Z§7: §6" + 1 + "\"],Name:\"§cCrashSkull§7 Hologram\"},EntityTag:{NoGravity:1b,Equipment:[0:{},1:{},2:{},3:{},4:{id:\"minecraft:skull\",Count:1b,tag:{SkullOwner:{Id:\"890c8215-d49c-4a90-8bd5-6e2c2b3c664c\",Properties:{textures:[0:{Signature:\"TaTCfzX88SXppibQNMCnycgaVcySC05piL5OM1e8DPoMa0ptnof0hX/Wdd2rITpJQ4ZRqK/1UvHADIjimoWhl/14VMnoF8C3yCQQiy/ylLmgLFKWYoLlRHE7bXCPs/L2lCEjPdQ8sIuiHSQtcNrFNfBO76EcvmCfa89/qPtFcSrx+OOh3m4O7RABni9xoTtG8xSorLwad09r/AgKYyxLg6gx2iaT4UlFuIAQ3hp51e3oVvpm+l2UfvTdpPEjs8M5QJqGJ6Sq4aWp/0KIP9T1asotvWRTxsWOemuzImuSRC1Sz+Q5XbGKbBXPTKkCLVGoM9TtqtBtcul9JpgAMxy5NdpEQTxZ/moT4kn8VNjKVIaYb27Fg8RkilMtKNVR8j5JBirjY+fYoV5KpdswlqYgc0uXYGC16Q+UQB6DK2x4SuUK3D1eVSvu6mqR8MwymvYXMwvhVT2za3Lrfc/SrZPiQ8A8EbY33rmfzYcHZqvYAPKbY+ynJJOAW8c5U485tSku3iofFiBZoO1fQR/rOeQ/Vn8j7x7UR+93QvsivFOpcoTNqp9EqvMXIjP6I7vu8zbits6z6+Qp+88QEOzN6HttKP7x4j3KYOmrch5YzXPi/5m3N95hcOeQvgWdd8F5fNjtMcXniaZze2If/s3mc4BUBj+XJmtm+oiADuW3TDOlrTg=\",Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6IiJ9fX0=\"}]},Name:\"U\u00f6eciLqVPIfJNwZ\"}},Damage:3s}],Pos:[" + 1 + ".0," + 1 + ".0," + 1 + ".0],Invulnerable:1b,Invisible:1b}}"));
        Segito.msg("Az§e Item§7 sikeresen le lett k\u00e9rve!");
        Segito.msg("A Hologram elhelyezked\u00e9se enn\u00e9l a pontn\u00e1l lesz!");
    }
}
