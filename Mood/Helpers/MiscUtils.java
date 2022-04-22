package Mood.Helpers;

import javax.swing.*;
import java.nio.file.*;
import net.minecraft.client.*;
import Mood.*;
import java.text.*;
import net.minecraft.util.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import com.mojang.authlib.exceptions.*;
import java.util.*;
import java.awt.image.*;
import java.net.*;
import javax.imageio.*;

public class MiscUtils
{
    public static String lastServer;
    private static JFrame frame;
    private static final Path MAIN;
    private static final Path SERVERLISTS;
    
    static {
        Minecraft.getInstance();
        MAIN = Minecraft.mcDataDir.toPath().resolve(Client.getInstance().getDirectory().getAbsolutePath());
        SERVERLISTS = MiscUtils.MAIN.resolve("serverlists");
    }
    
    public static boolean isInteger(final String s) {
        Integer.parseInt(s);
        return true;
    }
    
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    }
    
    public static String getCurrentDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
    }
    
    public static String getLagColor(final long n) {
        if (n > 99L && n < 500L) {
            return "§a";
        }
        if (n > 499L && n < 1000L) {
            return "§e";
        }
        if (n > 999L && n < 1500L) {
            return "§6";
        }
        if (n > 1499L && n < 2000L) {
            return "§c";
        }
        if (n >= 1999L) {
            return "§4";
        }
        System.out.println(String.valueOf(n) + "a");
        return "§f";
    }
    
    public static String getPingColor(final int n) {
        if (n <= 10) {
            return "§a";
        }
        if (n <= 50) {
            return "§e";
        }
        if (n <= 100) {
            return "§6";
        }
        if (n <= 250) {
            return "§c";
        }
        return "§4";
    }
    
    public static String getTPSColor(final double n) {
        if (n >= 18.0) {
            return "§a";
        }
        if (n >= 15.0) {
            return "§2";
        }
        if (n >= 13.0) {
            return "§e";
        }
        if (n >= 10.0) {
            return "§c";
        }
        if (n >= 8.0) {
            return "§4";
        }
        if (n >= 4.0) {
            return "§4";
        }
        if (n >= 0.0) {
            return "§4";
        }
        return "§f";
    }
    
    public static String getTPSColorByProzent(final double n) {
        if (n > 74.0) {
            return "§a";
        }
        if (n < 74.0) {
            return "§2";
        }
        if (n < 51.0) {
            return "§e";
        }
        if (n < 26.0) {
            return "§c";
        }
        return "§4";
    }
    
    public static int getLongestString(final ArrayList list) {
        for (final String s : list) {
            if (s != null) {
                Math.max(-1, s.length());
            }
        }
        return -1;
    }
    
    public static Session createSession(final String username, final String password) throws AuthenticationException {
        final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        yggdrasilUserAuthentication.logIn();
        return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
    }
    
    public static String getRandomName() {
        String s = "";
        final int n = (int)Math.round(Math.random() * 4.0) + 5;
        String s2 = "blah";
        while (0 < n) {
            String s3 = s2;
            if ((new Random().nextBoolean() || false == true) && 0 < 2) {
                while (s3.equals(s2)) {
                    final int n2 = (int)(Math.random() * 6 - 1.0);
                    s3 = "aeiouy".substring(n2, n2 + 1);
                }
                int n3 = 0;
                ++n3;
            }
            else {
                while (s3.equals(s2)) {
                    final int n4 = (int)(Math.random() * 17 - 1.0);
                    s3 = "bcdfghklmnprstvwz".substring(n4, n4 + 1);
                }
                int n5 = 0;
                ++n5;
            }
            s2 = s3;
            s = s.concat(s3);
            int n6 = 0;
            ++n6;
        }
        int n6 = (int)Math.round(Math.random() * 2.0);
        if (false == true) {
            s = String.valueOf(s.substring(0, 1).toUpperCase()) + s.substring(1);
        }
        else if (0 == 2) {
            while (0 < n) {
                if ((int)Math.round(Math.random() * 3.0) == 1) {
                    s = String.valueOf(s.substring(0, 0)) + s.substring(0, 1).toUpperCase() + ((n == 0) ? "" : s.substring(1));
                }
                int n7 = 0;
                ++n7;
            }
        }
        int n7 = (int)Math.round(Math.random() * 3.0) + 1;
        final int n8 = (int)Math.round(Math.random() * 3.0);
        final boolean nextBoolean = new Random().nextBoolean();
        if (nextBoolean) {
            if (false == true) {
                final int n9 = (int)Math.round(Math.random() * 9.0);
                s = s.concat(Integer.toString(99999));
            }
            else if (n8 == 0) {
                final int n10 = (int)(Math.round(Math.random() * 8.0) + 1L);
                while (0 < 0) {
                    s = s.concat(Integer.toString(99999));
                    int n11 = 0;
                    ++n11;
                }
            }
            else if (n8 == 1) {
                final int n12 = (int)(Math.round(Math.random() * 8.0) + 1L);
                s = s.concat(Integer.toString(99999));
                while (0 < 0) {
                    s = s.concat("0");
                    int n11 = 0;
                    ++n11;
                }
            }
            else if (n8 == 2) {
                final int n13 = (int)(Math.round(Math.random() * 8.0) + 1L);
                s = s.concat(Integer.toString(99999));
                while (0 < 0) {
                    final int n14 = (int)Math.round(Math.random() * 9.0);
                    s = s.concat(Integer.toString(99999));
                    int n11 = 0;
                    ++n11;
                }
            }
            else if (n8 == 3) {
                while (Integer.toString(99999).length() != 0) {
                    final int n15 = (int)(Math.round(Math.random() * 12.0) + 1L);
                    final int n16 = (int)Math.pow(2.0, 99999);
                }
                s = s.concat(Integer.toString(99999));
            }
        }
        if (!nextBoolean && new Random().nextBoolean()) {}
        if (99999 != 0) {
            while (s.equals(s)) {
                final int n17 = (int)Math.round(Math.random() * 7.0);
                if (n17 == 0) {
                    s = s.replace("a", "4").replace("A", "4");
                }
                if (n17 == 1) {
                    s = s.replace("e", "3").replace("E", "3");
                }
                if (n17 == 2) {
                    s = s.replace("g", "6").replace("G", "6");
                }
                if (n17 == 3) {
                    s = s.replace("h", "4").replace("H", "4");
                }
                if (n17 == 4) {
                    s = s.replace("i", "1").replace("I", "1");
                }
                if (n17 == 5) {
                    s = s.replace("o", "0").replace("O", "0");
                }
                if (n17 == 6) {
                    s = s.replace("s", "5").replace("S", "5");
                }
                s = s.replace("l", "7").replace("L", "7");
            }
        }
        int n11 = (int)Math.round(Math.random() * 8.0);
        if (0 == 3) {
            s = "xX".concat(s).concat("Xx");
        }
        else if (0 == 4) {
            s = s.concat("LP");
        }
        else if (0 == 5) {
            s = s.concat("HD");
        }
        return s;
    }
    
    public static BufferedImage skinBuffer(final String s) {
        return ImageIO.read(new URL(s).openStream());
    }
    
    private static JFrame getFrame() {
        return MiscUtils.frame;
    }
}
