package DTool.command.impl;

import Mood.*;
import DTool.command.*;
import net.minecraft.client.*;

public class Help extends Command
{
    public Help() {
        super("help", "help", "help", new String[] { "help" });
    }
    
    @Override
    public void onCommand(final String[] array, final String s) {
        if (array.length == 0) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§carmorstand§7 K\u00e9sz\u00edthetsz egy\u00e9ni armorstandeket.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cpageexploit§7 Egy olyan k\u00f6nyv, ami \u00e1ltal,", true);
            Segito.msg("§7 parancsokat adhatsz meg, majd ha lapoznak vele");
            Segito.msg("§7 k\u00e9pesek lesznek lefuttatni az adott parancsot,");
            Segito.msg("§7 amit te adt\u00e1l meg.");
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cbugitem§7 Egy olyan Item, ami k\u00e9pes", true);
            Segito.msg("§7 buggoltatni a karakteredet, hogyha");
            Segito.msg("§7s\u00e9t\u00e1lsz vele. (Add \u00e1t m\u00e1s j\u00e1t\u00e9kosoknak)", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("2")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§ccopyip§7 Az adott szervernek az IP", true);
            Segito.msg("§7c\u00edm\u00e9t m\u00e1solja le.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§ccheckcmd§7 Ezzel a paranccsal", true);
            Segito.msg("§7meg tudod n\u00e9zni t\u00fal\u00e9l\u0151 m\u00f3dban azt,", true);
            Segito.msg("§7hogy a szerveren enged\u00e9lyezve vannak-e", true);
            Segito.msg("§7a commandblockok.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§ccc§7 Ez csak szimpl\u00e1n egy", true);
            Segito.msg("§7chat t\u00f6rl\u00e9s.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("3")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§cconnect§7 Ezzel a paranccsal,", true);
            Segito.msg("§7szerverekre tudsz felcsatlakozni.", true);
            Segito.msg("§7Fontos tudni, hogy ez a parancs nem eg\u00e9szen m\u0171k\u00f6dik", true);
            Segito.msg("§7valami j\u00f3l.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§crename§7 Ezzel a paranccsal k\u00e9pes leszel", true);
            Segito.msg("§7kreat\u00edvm\u00f3dban \u00e1tnevezni egy Itemet,", true);
            Segito.msg("§7\u00fcll\u0151 n\u00e9lk\u00fcl.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§ccopynbtdata§7 Ezzel a paranccsal k\u00e9pes leszel", true);
            Segito.msg("§7az adott Item-nek lem\u00e1solni az NBTTag-j\u00e9t.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("4")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§ccrash§7 Ez csak szimpl\u00e1n egy onepacketcrasher,", true);
            Segito.msg("§7k\u00e9pes meg\u00f6lni, avagy le\u00e1ll\u00edtani a szervereket.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§ccreaitmspam§7 L\u00e9nyeg\u00e9ben annyit csin\u00e1l,", true);
            Segito.msg("§7hogyha net\u00e1n esetleg kreat\u00edvm\u00f3dban lenn\u00e9l,", true);
            Segito.msg("§7k\u00e9pes legy\u00e9l le\u00e1ll\u00edtani a kreat\u00edv szervereket.", true);
            Segito.msg("§7Egyj\u00e1t\u00e9kosm\u00f3d-ban nem aj\u00e1nlatos!", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§ctitle§7 Ezzel a paranccsal k\u00e9pes leszel", true);
            Segito.msg("§7el\u0151h\u00edvni egy Wither Hotbar-t", true);
            Segito.msg("§7a saj\u00e1t sz\u00f6vegeddel ell\u00e1tva!", true);
            Segito.msg("§7-title <c\u00edm>", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("5")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7-§crainbowarmor§7 <§cbekapcs§7> / <§ckikapcs§7>", true);
            Segito.msg("§7Ezt a funkci\u00f3t csak", true);
            Segito.msg("§7kreat\u00edvm\u00f3dban haszn\u00e1lhatod!", true);
            Segito.msg("§7Amennyiben bekapcsoltad kreat\u00edvm\u00f3dban,", true);
            Segito.msg("§7eszm\u00e9letlen dolog fog megjelenni el\u0151tted!", true);
            Segito.msg("§7Egy k\u00fcl\u00f6nleges p\u00e1nc\u00e9lzat, ami folyamatosan", true);
            Segito.msg("§7v\u00e1ltoztatja a sz\u00edn\u00e9t!", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cskull§7 Ezzel a paranccsal OP-Jog", true);
            Segito.msg("§7n\u00e9lk\u00fcl is, k\u00e9pes leszel leh\u00edvni j\u00e1t\u00e9kosok", true);
            Segito.msg("§7fejeit! Viszont, kell hozz\u00e1 kreat\u00edvm\u00f3d hogy", true);
            Segito.msg("§7haszn\u00e1lhasd.", true);
            Segito.msg("§7-skull <j\u00e1t\u00e9kosneve>", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cbookhack§7 Ez csak egy egyszer\u0171 bookhack.", true);
            Segito.msg("§7-bookhack <parancs>", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("6")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§ccopyitem§7 Szimpl\u00e1n el tudod", true);
            Segito.msg("§7lopni m\u00e1sok itemeit, kreat\u00edvm\u00f3dban.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cgive§7 Itemeket b\u00edrsz leh\u00edvni", true);
            Segito.msg("§7Op-Jog n\u00e9lk\u00fcl, viszont kell hogy legyen", true);
            Segito.msg("§7Gamemode 1-ed.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cgivelagsign§7 <tag>", true);
            Segito.msg("§7Ez a parancs csak gamemode 1-ben m\u0171k\u00f6dik.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("7")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§cplayerinfo§7 Ezzel a paranccsal k\u00e9pes leszel", true);
            Segito.msg("§7lek\u00e9rni az adott j\u00e1t\u00e9kosnak az adatait/inform\u00e1ci\u00f3it", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§ctntpackager§7 Ezzel a paranccsal k\u00e9pes leszel", true);
            Segito.msg("§7egy adott helyet kifagyasztani.", true);
            Segito.msg("§7-tntpackager§c <§ax§c> <§ay§c> <§az§c>", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cmodules§7 Felsorolja neked a moduleokat.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("8")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§chologram§7 Leid\u00e9z egy hologramot.", true);
            Segito.msg("§7-hologram §c<sz\u00f6veg>", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cmultiholo§7 Hologramokat id\u00e9zle.", true);
            Segito.msg("§7Haszn\u00e1lat:", true);
            Segito.msg("§a1.§7 Keress egy helyet ahov\u00e1", true);
            Segito.msg("§7\u00f3hajtod leid\u00e9zni a hologramokat.", true);
            Segito.msg("§a2.§7 Majd ha meg tal\u00e1ltad azt az adott helyet,", true);
            Segito.msg("§7az al\u00e1bbit \u00edrd a chatbe: §c-multiholo beallit", true);
            Segito.msg("§7Majd adj meg egy neked tetsz\u0151leges", true);
            Segito.msg("§7sz\u00f6veget: §c-multiholo szoveg§7 <§asz\u00f6veg§7> ", true);
            Segito.msg("§a3.§7 Majd ha elszeretn\u00e9d ind\u00edtani a folyamatot,", true);
            Segito.msg("§7akkor csup\u00e1n csak annyi a teend\u0151,", true);
            Segito.msg("§7hogy be\u00edrd a k\u00f6vetkez\u0151t: §c-multiholo elindit", true);
            Segito.msg("§a4. §7Ha pedig le szeretn\u00e9d \u00e1ll\u00edtani", true);
            Segito.msg("§7folyamatot, csup\u00e1n csak annyi a dolgod", true);
            Segito.msg("§7hogy be\u00edrd a k\u00f6vetkez\u0151t: §c-multiholo leallit", true);
            Segito.msg("§7Horogkereszteket is \u00e1ll\u00edthatsz be (Xd)", true);
            Segito.msg("§7P\u00e9lda: §c-multiholo szoveg§7 <§asz\u00f6veg§7> <§akeresztxd§7> ", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cjumper§7 Iz\u00e9 igen. Ugr\u00e1l\u00f3 ArmorStand.", true);
            Segito.msg("§7Haszn\u00e1lat:", true);
            Segito.msg("§a1.§7 Keress egy helyet ahov\u00e1", true);
            Segito.msg("§7\u00f3hajtod leid\u00e9zni az iz\u00e9ket (xd).", true);
            Segito.msg("§a2.§7 Majd ha meg tal\u00e1ltad azt az adott helyet,", true);
            Segito.msg("§7az al\u00e1bbit \u00edrd a chatbe: §c-jumper beallit", true);
            Segito.msg("§a3.§7 Majd ha elszeretn\u00e9d ind\u00edtani a folyamatot,", true);
            Segito.msg("§7akkor csup\u00e1n csak annyi a teend\u0151,", true);
            Segito.msg("§7hogy be\u00edrd ezt a parancsot: §c-jumper elindit/emoji", true);
            Segito.msg("§a4. §7Ha pedig le szeretn\u00e9d \u00e1ll\u00edtani", true);
            Segito.msg("§7folyamatot, csup\u00e1n csak annyi a dolgod", true);
            Segito.msg("§7hogy be\u00edrd a k\u00f6vetkez\u0151t: §c-jumper leallit", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("9")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§cheadfollower§7 Fejeket hagy", true);
            Segito.msg("§7az adott j\u00e1t\u00e9kos ut\u00e1n.", true);
            Segito.msg("§7(Amely k\u00f6zelebb van hozz\u00e1d)", true);
            Segito.msg("§7Haszn\u00e1lat:", true);
            Segito.msg("§a1.§7 Menj egy k\u00f6zeli j\u00e1t\u00e9koshoz, vagy", true);
            Segito.msg("§7k\u00e9rd meg hogy tpz-en hozz\u00e1d.", true);
            Segito.msg("§a2.§7 Majd az al\u00e1bbit \u00edrd a chatbe:", true);
            Segito.msg("§c-headfollower elindit", true);
            Segito.msg("§a3.§7 Majd ha k\u00edv\u00e1nod abbahagyni a folyamatot", true);
            Segito.msg("§7csup\u00e1n csak annyi a dolgod, hogy az al\u00e1bbit", true);
            Segito.msg("§7be\u00edrd: §c-headfollower leallit", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cpicturehologram", true);
            Segito.msg("§7Haszn\u00e1lat:", true);
            Segito.msg("§a1.§7 Menj fel §cGoogle§7-ra.", true);
            Segito.msg("§7Majd ha ez meg volt,", true);
            Segito.msg("§7\u00edrd be azt amit csak szeretn\u00e9l.", true);
            Segito.msg("§a2.§7 Majd menj a §c'K\u00e9pek'§7 lehet\u0151s\u00e9gre.", true);
            Segito.msg("§a3.§c V\u00e1laszd ki§7 a neked tetsz\u0151 k\u00e9pet.", true);
            Segito.msg("§a4.§7 Majd §cjobb kattolj§7 r\u00e1,", true);
            Segito.msg("§a5. §7\u00e9s majd a §c'K\u00e9pc\u00edm m\u00e1sol\u00e1sa'§7 lehet\u0151s\u00e9gre", true);
            Segito.msg("§7kattolj.", true);
            Segito.msg("§7Ha ez meg volt,", true);
            Segito.msg("§7csup\u00e1n csak annyi a dolgod hogy:", true);
            Segito.msg("§a6.§7 -picturehologram §cbetolt <link> <magass\u00e1g> <hossz\u00fas\u00e1g>", true);
            Segito.msg("§7Ha esetleg nem t\u00f6rt\u00e9nik semmi, nem kapsz semmit,", true);
            Segito.msg("§7Akkor prob\u00e1ld meg ezt \u00fajra, csak m\u00e1sik k\u00e9ppel.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("10")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7" + CommandManager.prefix + "§cgm§7 Egyszer\u0171s\u00edtett gamemode", true);
            Segito.msg("§7v\u00e1lt\u00e1s.", true);
            Segito.msg("§7Haszn\u00e1lat:", true);
            Segito.msg("§c-gm§7 <§aa/2§7> / <§ac/1§7> / <§as/0>§7 / <§asp/3§7>", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cday§7 Egyszer\u0171s\u00edtett nappal.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cnight§7 Egyszer\u0171s\u00edtett \u00e9jszaka.", true);
            Segito.msg("", true);
            Segito.msg("§7" + CommandManager.prefix + "§cweather§7 Egyszer\u0171s\u00edtett id\u0151j\u00e1r\u00e1s", true);
            Segito.msg("§7Haszn\u00e1lat:", true);
            Segito.msg("§c-weather§7§o (bekapcsol\u00e1s)", true);
            Segito.msg("§c-weather off§7§o (kikapcsol\u00e1s)", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("11")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7-§cgrief§7 <§ax§7> <§ay§7> <§az§7>", true);
            Segito.msg("§7Ezzel k\u00e9pes leszel, rengeteg", true);
            Segito.msg("§7v\u00e9gzets\u00e1rk\u00e1nyt leid\u00e9zni egy helyre.", true);
            Segito.msg("", true);
            Segito.msg("§7-§centityfucker§7 <§aEntit\u00e1s§7> <Motion§a X§7> <Motion§a Y§7> <Motion§a Z§7>", true);
            Segito.msg("§7Ez a kisebb parancs, l\u00e9nyeg\u00e9ben annyit", true);
            Segito.msg("§7csin\u00e1l, hogy fogja az adott Entit\u00e1st", true);
            Segito.msg("§7amit megadt\u00e1l, \u00e9s oda \u00edr\u00e1ny\u00edtja, ahov\u00e1", true);
            Segito.msg("§7te szeretn\u00e9d (?)", true);
            Segito.msg("", true);
            Segito.msg("§7-§cforceopspawner§7 <§cparancs§7>", true);
            Segito.msg("§7 Ez a kisebb funkci\u00f3 lehet\u0151v\u00e9 teszi", true);
            Segito.msg("§7azt, hogy leid\u00e9zzen neked commandblockokat", true);
            Segito.msg("§7egy mobspawnerb\u0151l.", true);
            Segito.msg("§7M\u00e9g hozz\u00e1 nem is ak\u00e1rmilyen commandblockot!", true);
            Segito.msg("§7Commandblockokat id\u00e9z le, amikben megvan adva", true);
            Segito.msg("§7egy adott parancs, amit igaz\u00e1b\u00f3l te adt\u00e1l meg.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("12")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7-§cgivecrashstand§7 egy crasheltet\u0151s", true);
            Segito.msg("§7p\u00e1nc\u00e9l\u00e1llv\u00e1nyt kapsz.", true);
            Segito.msg("", true);
            Segito.msg("§7-§cexsign§7 <§cparancs§7>", true);
            Segito.msg("§7Kapsz egy paranccsal ell\u00e1tott t\u00e1bl\u00e1t.", true);
            Segito.msg("§7Hogyha esetleg OP-jogra szeretn\u00e9l szert tenni,", true);
            Segito.msg("§7Akkor nincs m\u00e1s v\u00e1laszt\u00e1sod, mint a k\u00f6vetkez\u0151t", true);
            Segito.msg("§7be \u00edrni: §c-exsign /op " + Minecraft.session.getUsername(), true);
            Segito.msg("", true);
            Segito.msg("§7-§cflyingblock§7 <§aBlock§7>", true);
            Segito.msg("§7Rep\u00fcl\u0151 Blockokat id\u00e9z le, amit te adt\u00e1l", true);
            Segito.msg("§7meg.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("13")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7-§cteleport§7 <§aJ\u00e1t\u00e9kosneve§7> <§ax§7> <§ay§7> <§az§7>", true);
            Segito.msg("", true);
            Segito.msg("§7-§cgrieftool§7 <§celokeszit§7> / <§cszoveg§7> / <§celindit§7> / <§cleallit§7>", true);
            Segito.msg("§a 1.§7 -grieftool elokeszit", true);
            Segito.msg("§7 Els\u0151 sorban k\u00e9sz\u00edtsd el\u0151 a fegyvert.", true);
            Segito.msg("§a 2.§7 -grieftool szoveg§c <adj meg saj\u00e1t sz\u00f6veget>", true);
            Segito.msg("§7 Amit majd spammelni fogsz. Pl: Server Hacked: " + Minecraft.session.getUsername(), true);
            Segito.msg("§a 3.§7 Azt\u00e1n pedig csak szimpl\u00e1n el kell ind\u00edtanod", true);
            Segito.msg("§7 a folyamatot: §c-grieftool elindit", true);
            Segito.msg("§a 4.§7 Ha pedig v\u00e9gezt\u00e9l a mocskos munk\u00e1val,", true);
            Segito.msg("§7 csup\u00e1n csak annyi lesz a dolgod, hogy le\u00e1ll\u00edtod", true);
            Segito.msg("§7 a folyamatot: §c-grieftool leallit", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("14")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7-§cobjectloader§7 <f\u00e1jl n\u00e9v>", true);
            Segito.msg("§a1.§7 El\u0151sz\u0151r is menj fel erre a weboldalra:", true);
            Segito.msg("§chttps://mrgarretto.com/armorstand-1-10/", true);
            Segito.msg("§a2.§7 Ha ez megvolt, menj a:§c 'popular project'§7-re.", true);
            Segito.msg("§a3.§7 V\u00e1laszd ki a neked tetsz\u0151leges Objektumot.", true);
            Segito.msg("§a4.§7 Ha ez megvolt, menj a:§c 'Generate'§7 gombra.", true);
            Segito.msg("§a5.§7 Ha ezzel is megvolt\u00e1l, menj a:§c 'Raw Command'§7 gombra.", true);
            Segito.msg("§a7.§7 Folytat\u00e1s: Youtubeon.", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("15")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7-§ceco§7 <give/reset>", true);
            Segito.msg("§7Ezzel a parancsal p\u00e9nzt tudsz elvenni,", true);
            Segito.msg("§7valamint p\u00e9nzt tudsz lek\u00e9rni, az adott", true);
            Segito.msg("§7szerveren. (Csak§c *§7 joggal m\u0171k\u00f6dik)", true);
            Segito.msg("§7ez a folyamat.", true);
            Segito.msg("", true);
            Segito.msg("§7-§cdeleteserverplugins§7 <beallit/elindit>", true);
            Segito.msg("§a1.§7 Fontos, hogy legyen a szerveren", true);
            Segito.msg("§7egy §c'Multiverse-Core'§7 nevezet\u0171 plugin.", true);
            Segito.msg("§7Hiszen an\u00e9lk\u00fcl nem fog m\u0171k\u00f6dni ez a folyamat.", true);
            Segito.msg("§7Ja \u00e9s kelleni fog neked hozz\u00e1 szint \u00fagy§c *§7 jog.", true);
            Segito.msg("§a2.§7 Step 1:§c -deleteserverplugins beallit", true);
            Segito.msg("§a3.§7 Step 2:§c -deleteserverplugins elindit", true);
            Segito.msg("§7Majd a folyamat elkezd\u0151dik!", true);
            Segito.msg("", true);
            Segito.msg("§7-§choloexploit§7 <elokeszit/lekerdezes/plconfig>", true);
            Segito.msg("§a1.§7 Fontos, hogy legyen a szerveren", true);
            Segito.msg("§7egy §c'HolographicDisplays'§7 nevezet\u0171 plugin.", true);
            Segito.msg("§7azonbel\u00fcl fontos, hogy a verzi\u00f3ja 2.26, vagy", true);
            Segito.msg("§7ann\u00e1l r\u00e9gebbi legyen!", true);
            Segito.msg("§a2.§7 Step 1:§c holoexploit elokeszit", true);
            Segito.msg("§a3.§7 Step 2:§c holoexploit <lekerdezes/plconfig>", true);
            Segito.msg("§clek\u00e9rdez\u00e9s:§7 megmutatja neked a server.properties", true);
            Segito.msg("§7nek az adatait!", true);
            Segito.msg("§cplconfig:§7 megmutatja neked a pluginoknak a", true);
            Segito.msg("§7configjait!", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else if (array[0].equalsIgnoreCase("16")) {
            Segito.msg("---------------------------------------------------", true);
            Segito.msg("§7-§cserverdata§7 Megmutatja a szerver", true);
            Segito.msg("§7adatait!§c§o v\u00e1ros, orsz\u00e1g, IP, etc.", true);
            Segito.msg("", true);
            Segito.msg("§7-§clitebansfucker§c -litebansfucker <database/givedatabases", true);
            Segito.msg("§c/giveipaddress>", true);
            Segito.msg("§7ezzel a parancsal k\u00e9pes leszel megn\u00e9zni", true);
            Segito.msg("§7az adatb\u00e1zisokat, valamint lek\u00e9rni azokat,", true);
            Segito.msg("§7valamint le n\u00e9zni MINDENKI IP c\u00edm\u00e9t!§c (illegal)", true);
            Segito.msg("§a1.§7 Fontos, hogy legyen a szerveren", true);
            Segito.msg("§7egy §c'LiteBans'§7 nevezet\u0171 plugin.", true);
            Segito.msg("§a2.§7 Step 1:§c -litebansfucker database", true);
            Segito.msg("§7csekkold le az adatb\u00e1zisokat!", true);
            Segito.msg("§a3.§7 Step 2:§c -litebansfucker givedatabases", true);
            Segito.msg("§7k\u00e9rd le az adatb\u00e1zisokat!", true);
            Segito.msg("§a4.§7 Step 3:§c -litebansfucker giveipaddress", true);
            Segito.msg("§7gonosz m\u00f3don n\u00e9zd le mindenkinek az IP c\u00edm\u00e9t!", true);
            Segito.msg("§8§oNEM k\u00f6telez\u0151!", true);
            Segito.msg("§7S\u0151t, biztons\u00e1gosabb is hogyha nem hajtod v\u00e9gre!", true);
            Segito.msg("---------------------------------------------------", true);
        }
        else {
            Segito.msg("Nincsen t\u00f6bb oldal!");
            Segito.msg("Vedd meg a teljes verzi\u00f3j\u00e1t a kliensnek");
            Segito.msg("hogy m\u00e9g t\u00f6bb hasznos dolgot tudj csin\u00e1lni vele!");
            Segito.msg("§4§lYou§r§lTube:§e Cl3sTerMan.");
        }
    }
    
    @Override
    public String getName() {
        return "help";
    }
}
