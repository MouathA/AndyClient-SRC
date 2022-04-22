package org.apache.commons.compress.compressors.snappy;

import java.util.zip.*;

class PureJavaCrc32C implements Checksum
{
    private int crc;
    private static final int T8_0_start = 0;
    private static final int T8_1_start = 256;
    private static final int T8_2_start = 512;
    private static final int T8_3_start = 768;
    private static final int T8_4_start = 1024;
    private static final int T8_5_start = 1280;
    private static final int T8_6_start = 1536;
    private static final int T8_7_start = 1792;
    private static final int[] T;
    
    public PureJavaCrc32C() {
        this.reset();
    }
    
    public long getValue() {
        return ~(long)this.crc & 0xFFFFFFFFL;
    }
    
    public void reset() {
        this.crc = -1;
    }
    
    public void update(final byte[] array, int n, int i) {
        int crc = this.crc;
        while (i > 7) {
            final int n2;
            final int n3;
            crc = (PureJavaCrc32C.T[1792 + ((array[n + 0] ^ crc) & 0xFF)] ^ PureJavaCrc32C.T[1536 + ((array[n + 1] ^ (n2 = crc >>> 8)) & 0xFF)] ^ (PureJavaCrc32C.T[1280 + ((array[n + 2] ^ (n3 = n2 >>> 8)) & 0xFF)] ^ PureJavaCrc32C.T[1024 + ((array[n + 3] ^ n3 >>> 8) & 0xFF)]) ^ (PureJavaCrc32C.T[768 + (array[n + 4] & 0xFF)] ^ PureJavaCrc32C.T[512 + (array[n + 5] & 0xFF)] ^ (PureJavaCrc32C.T[256 + (array[n + 6] & 0xFF)] ^ PureJavaCrc32C.T[0 + (array[n + 7] & 0xFF)])));
            n += 8;
            i -= 8;
        }
        switch (i) {
            case 7: {
                crc = (crc >>> 8 ^ PureJavaCrc32C.T[0 + ((crc ^ array[n++]) & 0xFF)]);
            }
            case 6: {
                crc = (crc >>> 8 ^ PureJavaCrc32C.T[0 + ((crc ^ array[n++]) & 0xFF)]);
            }
            case 5: {
                crc = (crc >>> 8 ^ PureJavaCrc32C.T[0 + ((crc ^ array[n++]) & 0xFF)]);
            }
            case 4: {
                crc = (crc >>> 8 ^ PureJavaCrc32C.T[0 + ((crc ^ array[n++]) & 0xFF)]);
            }
            case 3: {
                crc = (crc >>> 8 ^ PureJavaCrc32C.T[0 + ((crc ^ array[n++]) & 0xFF)]);
            }
            case 2: {
                crc = (crc >>> 8 ^ PureJavaCrc32C.T[0 + ((crc ^ array[n++]) & 0xFF)]);
            }
            case 1: {
                crc = (crc >>> 8 ^ PureJavaCrc32C.T[0 + ((crc ^ array[n++]) & 0xFF)]);
                break;
            }
        }
        this.crc = crc;
    }
    
    public final void update(final int n) {
        this.crc = (this.crc >>> 8 ^ PureJavaCrc32C.T[0 + ((this.crc ^ n) & 0xFF)]);
    }
    
    static {
        T = new int[] { 0, -227835133, -516198153, 324072436, -946170081, 904991772, 648144872, -724933397, -1965467441, 2024987596, 1809983544, -1719030981, 1296289744, -1087877933, -1401372889, 1578318884, 274646895, -499825556, -244992104, 51262619, -675000208, 632279923, 922689671, -996891772, -1702387808, 1760304291, 2075979607, -1982370732, 1562183871, -1351185476, -1138329528, 1313733451, 549293790, -757723683, -1048117719, 871202090, -416867903, 357341890, 102525238, -193467851, -1436232175, 1477399826, 1264559846, -1187764763, 1845379342, -1617575411, -1933233671, 2125378298, 820201905, -1031222606, -774358714, 598981189, -143008082, 85089709, 373468761, -467063462, -1170599554, 1213305469, 1526817161, -1452612982, 2107672161, -1882520222, -1667500394, 1861252501, 1098587580, -1290756417, -1606390453, 1378610760, -2032039261, 1955203488, 1742404180, -1783531177, -878557837, 969524848, 714683780, -655182201, 205050476, -28094097, -318528869, 526918040, 1361435347, -1555146288, -1340167644, 1114974503, -1765847604, 1691668175, 2005155131, -2047885768, -604208612, 697762079, 986182379, -928222744, 476452099, -301099520, -44210700, 255256311, 1640403810, -1817374623, -2130844779, 1922457750, -1503918979, 1412925310, 1197962378, -1257441399, -350237779, 427051182, 170179418, -129025959, 746937522, -554770511, -843174843, 1070968646, 1905808397, -2081171698, -1868356358, 1657317369, -1241332974, 1147748369, 1463399397, -1521340186, -79622974, 153784257, 444234805, -401473738, 1021025245, -827320098, -572462294, 797665321, -2097792136, 1889384571, 1674398607, -1851340660, 1164749927, -1224265884, -1537745776, 1446797203, 137323447, -96149324, -384560320, 461344835, -810158936, 1037989803, 781091935, -588970148, -1834419177, 1623424788, 1939049696, -2114449437, 1429367560, -1487280117, -1274471425, 1180866812, 410100952, -367384613, -112536529, 186734380, -538233913, 763408580, 1053836080, -860110797, -1572096602, 1344288421, 1131464017, -1323612590, 1708204729, -1749376582, -2065018290, 1988219213, 680717673, -621187478, -911630946, 1002577565, -284657034, 493091189, 238226049, -61306494, -1307217207, 1082061258, 1395524158, -1589280451, 1972364758, -2015074603, -1800104671, 1725896226, 952904198, -894981883, -638100751, 731699698, -11092711, 222117402, 510512622, -335130899, -1014159676, 837199303, 582374963, -790768336, 68661723, -159632680, -450051796, 390545967, 1230274059, -1153434360, -1469116676, 1510247935, -1899042540, 2091215383, 1878366691, -1650582816, -741088853, 565732008, 854102364, -1065151905, 340358836, -433916489, -177076669, 119113024, 1493875044, -1419691417, -1204696685, 1247431312, -1634718085, 1828433272, 2141937292, -1916740209, -483350502, 291187481, 34330861, -262120466, 615137029, -691946490, -980332558, 939183345, 1776939221, -1685949482, -1999470558, 2058945313, -1368168502, 1545135305, 1330124605, -1121741762, -210866315, 17165430, 307568514, -532767615, 888469610, -962626711, -707819363, 665062302, 2042050490, -1948470087, -1735637171, 1793573966, -1104306011, 1279665062, 1595330642, -1384295599, 0, 329422967, 658845934, 887597209, 1317691868, 1562966443, 1775194418, 2054015301, -1659583560, -1900651569, -1169034410, -1443665119, -744578460, -1069794797, -186936694, -411497731, 1069937025, 744974838, 411091311, 186800408, 1901039709, 1659701290, 1443537075, 1168652484, -1563348423, -1317819826, -2053897513, -1774806368, -329559067, -406638, -887201013, -658703492, 2139874050, 1814657909, 1489949676, 1265388443, 822182622, 581114537, 373600816, 98970183, -492887878, -247613235, -975564716, -696743901, -1407893146, -1078470383, -1957662328, -1728910849, 1078858371, 1408010996, 1728782957, 1957280282, 247755615, 493284136, 696337329, 975428550, -581250757, -822589108, -98573867, -373458526, -1815039769, -2140002160, -1265270775, -1489561474, -15219196, -323658125, -665651478, -873435491, -1315067944, -1572913233, -1764190410, -2055598271, 1644365244, 1906417099, 1162229074, 1457827109, 747201632, 1059847191, 197940366, 409914617, -1059965051, -747589646, -409532565, -197812452, -1906813351, -1644507602, -1457690953, -1161822528, 1573319741, 1315204170, 2055455955, 1763794084, 323786209, 15601046, 873047311, 665533816, -2137250554, -1824604815, -1478945304, -1266971233, -837401382, -575349587, -380406732, -84808637, 495511230, 237665993, 986568272, 695160359, 1392674658, 1084235541, 1950857100, 1743073275, -1084631929, -1392816912, -1742936983, -1950450658, -237783717, -495899348, -694778443, -986440254, 575477567, 837783368, 84420561, 380288934, 1825011427, 2137386644, 1266828813, 1478549114, -71042311, -396270962, -595146217, -819703200, -1253467355, -1494547630, -1844663349, -2119289924, 1725380929, 1970643254, 1100089775, 1378914776, 677206173, 1006616810, 253257843, 482013188, -1006236808, -677080305, -482133098, -253647903, -1970509148, -1724976429, -1379313078, -1100234179, 1494403264, 1253068983, 2119694382, 1844797529, 395880732, 70922603, 819829234, 595526021, -2075649541, -1746239092, -1559418603, -1330663070, -893224921, -647962544, -309901111, -31076162, 425515587, 184435252, 1041885869, 767259354, 1473690527, 1148462056, 1888717681, 1664160518, -1148327814, -1473286131, -1664558956, -1888861981, -184055386, -425389615, -767379128, -1042276033, 647572418, 893105077, 31202092, 310281051, 1746094622, 2075251305, 1331067632, 1559552647, 81018109, 393651338, 596708371, 808686692, 1247698209, 1509737814, 1830514127, 2126116280, -1715404987, -1973262542, -1098526805, -1389930532, -682975591, -991426834, -267407753, -475187712, 991022460, 682841355, 475331986, 267806181, 1973136544, 1715025111, 1390320718, 1098646585, -1509617980, -1247308109, -2126496214, -1830640035, -393253096, -80873617, -808820746, -597112959, 2069880831, 1761429384, 1545269009, 1337489254, 903200291, 645342804, 311463629, 20059834, -431285177, -169245648, -1056035671, -760433442, -1463714405, -1151080980, -1887154827, -1675176702, 1150955134, 1463334409, 1675566736, 1887274727, 168841122, 431151061, 760577868, 1056433979, -644944442, -903055951, -20194008, -311868065, -1761309670, -2069490579, -1337869068, -1545395069, 0, -1522429314, 1332695565, -366034829, -1629576166, 1000289892, -776866281, 1961911401, 944848581, -1659851589, 2000579784, -763363658, -1500538145, 63834273, -371144494, 1285642924, 1889697162, -706482188, 1070411655, -1702052359, -293807728, 1262308334, -1592554595, 72489443, 1223902031, -307048143, 127668546, -1562541252, -701634731, 1936487723, -1723681448, 1006839590, -515572972, 1141205354, -1372870375, 191511399, 2140823310, -623129232, 821366019, -1783324803, -652884527, 2085902255, -1770350628, 859506082, 1204511179, -494210123, 144978886, -1377459784, -1847163234, 883365088, -561392493, 2076722925, 255337092, -1434865414, 1079472265, -451484937, -1447577509, 217459237, -421991850, 1134131240, 929635393, -1842835905, 2013679180, -582493134, -949649191, 1646531239, -2012556588, 759906474, 1505436867, -50678083, 383022798, -1282022224, -13320676, 1517628514, -1336153071, 354057839, 1642732038, -995391368, 780486667, -1950033291, -1211630253, 310800173, -123162786, 1575566624, 689527113, -1940337865, 1719012164, -1019766470, -1885944938, 718754280, -1057386085, 1706558437, 289957772, -1274415630, 1579627905, -77158401, 639728589, -2090800717, 1766730176, -871384130, -1191190569, 499010985, -141521446, 1389436836, 510674184, -1154361482, 1360992005, -195132037, -2136022766, 636449644, -809389281, 1786782049, 1451427399, -205351879, 434918474, -1129462220, -933387683, 1830563875, -2026704816, 577987118, 1859270786, -879514884, 566061711, -2063795983, -267608936, 1431113446, -1083978091, 438459627, -1960347837, 778495293, -1001904818, 1628026672, 368694105, -1330101977, 1519812948, -2682070, -1284093562, 372759544, -65463413, 1498974709, 766045596, -1997963294, 1657257873, -947507729, -75167031, 1589942455, -1259710268, 296471226, 1700507347, -1072022355, 708115678, -1888129376, -1009503220, 1721083506, -1933875711, 704312447, 1560973334, -129301912, 308658715, -1222356891, 1784908887, -819847639, 621600346, -2142417884, -188929971, 1375517235, -1143834048, 513009726, 1379054226, -143449876, 492691615, -1206095135, -856942968, 1772979446, -2088549243, 650303227, 448917981, -1082104925, 1437508560, -252759634, -2078321209, 559859641, -881850422, 1848743348, 579915544, -2016322202, 1845468437, -927068309, -1135711486, 420477308, -215926513, 1449175921, 1279457178, -385653276, 53323159, -1502857239, -761506944, 2011021822, -1645018739, 951227379, 1947453791, -783131871, 998021970, -1640167124, -355635899, 1334640443, -1516093624, 14921014, 1021348368, -1717495698, 1938806813, -691123613, -1572983286, 125811828, -313426937, 1209069177, 78755029, -1578096981, 1272899288, -291539802, -1703997233, 1060012721, -721403198, 1883361468, -1392112498, 138911472, -496411005, 1193856253, 869836948, -1768342806, 2092432025, -638162713, -1789447605, 806789173, -633839546, 2138698296, 193566289, -1362623441, 1155974236, -509127134, -576425724, 2028331898, -1832180983, 931836279, 1132123422, -432322720, 202737427, -1454107283, -436908095, 1085595071, -1432740404, 266047410, 2066475995, -563447387, 876919254, -1861932120, 0, -582636872, -1083759743, 1646430521, 2065838579, -1503159477, -1002106254, 419477706, -163290138, 721537374, 1227047015, -1805194529, -1922674155, 1344534701, 838955412, -280700116, -379276995, 874584965, 1443074748, -1958332412, -1840873266, 1325607542, 757179215, -261879305, 522244827, -1033537437, -1605897894, 2097306594, 1677910824, -1186510448, -614088535, 102787601, -685435765, 174112307, 1749169930, -1257792078, -1408817800, 1900187584, 325060345, -836391871, 560035693, -64692779, -1643752212, 1128529492, 1514358430, -2029589466, -450599649, 945934247, 1044489654, -486273266, -2128181705, 1550003343, 1164153925, -1742323971, -100354108, 658578812, -939145648, 356543720, 2002970065, -1440264343, -1289226333, 1851940123, 205575202, -788169062, -1415159833, 1994650975, 348224614, -914041122, -796627436, 230802604, 1877167509, -1297684691, 1575107585, -2136500551, -494592128, 1069593912, 650120690, -75126966, -1717096845, 1155695819, 1120071386, -1618525086, -39465637, 551577571, 971038505, -458918511, -2037908312, 1539462672, -1266250436, 1774397316, 199339709, -693894139, -811287345, 316741239, 1891868494, -1383713290, 2088979308, -1580801580, -1008441107, 513917525, 128023199, -622538713, -1194960610, 1703146406, -1966659446, 1468170802, 899681035, -387604045, -236643975, 748729281, 1317157624, -1815637952, -1779959215, 1218597097, 713087440, -138054808, -289027166, 864051482, 1369630755, -1931001189, 1671666103, -1092209905, -591087050, 25235598, 411150404, -977009924, -1478063163, 2057511293, 1386268991, -1880792185, -305665346, 813842438, 696449228, -188263820, -1763321011, 1268806133, -1528517927, 2040594529, 461605208, -960093216, -540632278, 42152338, 1621211307, -1109126637, -1144752126, 1719784122, 77814659, -639176389, -1058649615, 497279817, 2139187824, -1564163896, 1300241380, -1866092196, -219727771, 799183581, 916597271, -337149777, -1983575658, 1417716526, -2054824524, 1489008396, 987954741, -408464243, -22549433, 602031871, 1103155142, -1668979330, 1942077010, -1367075606, -861495853, 300103531, 149131169, -710531815, -1216041952, 1791035032, 1826712713, -1314601423, -746172664, 247719344, 398679418, -897124414, -1465614597, 1977734211, -1700458641, 1205904855, 633482478, -125335978, -511230308, 1019384868, 1591745821, -2086291547, -117008680, 608386144, 1180808537, -1692131359, -2111527125, 1600195987, 1027835050, -536465902, 256046398, -771268730, -1339697473, 1835039751, 1952498893, -1457164683, -888674484, 373444084, 274868197, -853045923, -1358625692, 1916841692, 1799362070, -1241138002, -735627881, 157458223, -433699837, 996404923, 1497458562, -2080060102, -1660652048, 1078058824, 576935537, -14222135, 774079059, -211408661, -1857773102, 1275136874, 1426174880, -2008803048, -362377183, 925055641, -664280651, 86133517, 1728102964, -1169856372, -1555705786, 2113960702, 472052679, -1050191489, -951635090, 436378070, 2015367407, -1520059817, -1134230883, 1629530149, 50471196, -565736540, 822300808, -330892752, -1906019575, 1394727345, 1243701627, -1755001917, -179944710, 671344706, 0, 940666796, 1881333592, 1211347188, -532300112, -665530084, -1872272920, -1468658108, -983102575, -42572739, -1253714743, -1923826843, 623031585, 489937549, 1426090617, 1829832149, -1893572141, -1221390721, -16728437, -955133657, 1869078371, 1467396303, 524615739, 659845015, 1246063170, 1918109166, 979875098, 41343670, -1442786062, -1844331682, -635302998, -499948538, 464041303, 599382779, 1804233231, 1402668451, -68351001, -1006869429, -1949313857, -1277248749, -556810554, -421605014, -1360174690, -1761866190, 1049231478, 110850010, 1319690030, 1991880834, -1802840956, -1399080152, -458748964, -591829904, 1959750196, 1289618840, 82687340, 1023204032, 1374543637, 1778167993, 567214157, 434008033, -1314365019, -1984360951, -1047871747, -107228847, 928082606, 255853826, 1198765558, 2137184858, -686500834, -284836942, -1489630394, -1624808214, -231317185, -903410029, -2112569753, -1174023733, 309583759, 711110691, 1649475799, 1514172283, -1200419971, -2141035311, -934161371, -264193143, 1479980493, 1613224545, 672426645, 268764473, 2098462956, 1157984064, 221700020, 891793432, -1655587236, -1522478608, -311205628, -714994008, 754573305, 350793813, 1557856417, 1690988301, -860069559, -189985051, -1130447343, -2070949443, -375466904, -779109436, -1715729616, -1582472036, 165374680, 835323252, 2046408064, 1105779244, -1545880022, -1681206906, -738631310, -337113378, 1134428314, 2072997686, 868016066, 195932270, 1723643323, 1588451863, 379480803, 781124943, -2030499061, -1092066137, -153365421, -825574401, 1856165212, 1454619376, 511707652, 647062952, -1897436180, -1225391040, -20597580, -959128808, -1413095731, -1814777503, -605617771, -470389191, 1266704509, 1938886609, 1000521509, 62115977, -511658865, -644752605, -1851626537, -1447885701, 29690431, 970220947, 1911018855, 1240906443, 619167518, 485937330, 1422221382, 1825837034, -996015698, -55349758, -1266622730, -1936608934, 1963614219, 1293619111, 86556499, 1027199231, -1789927749, -1386303209, -445840925, -579047857, -1335006310, -2005138378, -1068518206, -128001170, 1344853290, 1748613766, 537528946, 404448734, -98041384, -1036423564, -1978999168, -1306808020, 443400040, 578605252, 1783586864, 1381896092, 1062144585, 123626981, 1332598033, 2004662973, -552946439, -417604779, -1356305503, -1757871091, 1509146610, 1642254430, 701587626, 297799430, -1179254462, -2119733522, -912990694, -242896458, -1667976093, -1534731313, -323589317, -727251817, 2094074579, 1153459583, 217306507, 887274023, -690889183, -289361523, -1494023815, -1629327659, 915693713, 243601213, 1186381769, 2124927077, 330749360, 732412444, 1670646504, 1535468868, -202151168, -874380116, -2083408808, -1144988684, 1113262757, 2051695881, 846845437, 174635601, -1575046123, -1710236743, -767792307, -366148383, -2026110668, -1087541608, -148971924, -821055040, 1736032132, 1600704552, 391864540, 793382768, -847680650, -177732390, -1118063570, -2058691710, 758961606, 355318378, 1562249886, 1695507762, 136208615, 806293323, 2017247167, 1076744211, -396632489, -800411141, -1736900337, -1603768669, 0, -282039527, -611540797, 884788186, -1292552329, 1573215342, 1769576372, -2041971539, 1611012127, -1892257018, -1148536612, 1421530053, -755814552, 1036207217, 159354795, -430971726, -1072943042, 792484647, 461410557, -189727772, 1928922953, -1647743920, -1451907190, 1178979475, -1609947103, 1329218360, 2072414434, -1799953413, 318709590, -36735921, -915161195, 641979532, -2047601011, 1791262100, 1584969294, -1320624809, 922821114, -667858205, -326270663, 62777888, -437121390, 180512139, 1048489553, -783366840, 1460091365, -1204333828, -1937008346, 1673261631, 1173890739, -1429713494, -1636530576, 1900342633, -150138428, 406682333, 746696967, -1011754466, 637419180, -892447307, -26042769, 289600886, -1760884261, 2017157826, 1283959064, -1548238335, 235166699, -516673294, -712443096, 985174065, -1125028708, 1405159301, 1736297567, -2008176826, 1845642228, -2127419155, -1248926921, 1522436142, -587762557, 868687770, 125555776, -397688999, -838308907, 557318348, 361024278, -88825841, 2096979106, -1815267397, -1485702047, 1212258168, -1374784566, 1094588627, 1971507977, -1699563504, 486229181, -204787804, -948444034, 675778407, -1947185818, 1690314367, 1350364581, -1085503812, 956660241, -701165304, -494282030, 230273483, -336177799, 80100960, 813364666, -548757853, 1493393934, -1238169321, -2104507699, 1841277396, 1274838360, -1530128831, -1871651429, 2134947458, -116831697, 372842806, 579201772, -843742731, 737830215, -993391010, -260651644, 524725917, -1727049168, 1983854889, 1115943667, -1380738582, 470333398, -214377265, -947644651, 682916876, -1359118175, 1104014264, 1970348130, -1706996869, 2081337289, -1824602928, -1484648694, 1219650579, -822372162, 567014311, 360134781, -95988892, -603682840, 859106545, 126363435, -390575054, 1861333151, -2118001786, -1250095012, 1515027269, -1140678665, 1395848430, 1737375540, -2000792531, 251111552, -507001959, -713357245, 978019162, -1711496869, 1993132610, 1114636696, -1388287359, 722048556, -1002832587, -259705105, 531979766, -101009084, 382390877, 578165127, -851021154, 1259310643, -1539316438, -1870450960, 2142455273, 1508938085, -1228866948, -2105790042, 1833720511, -351951342, 70634763, 814286545, -541495864, 972458362, -691608989, -495310407, 222970528, -1962689011, 1681118484, 1351556814, -1077971497, 302836797, -46364892, -914338562, 649078759, -1594238134, 1338617939, 2071296905, -1807413104, 1913320482, -1657102533, -1450814239, 1186349048, -1056980139, 802138188, 460546966, -196933489, -771695613, 1026602778, 160201920, -423880743, 1626729332, -1882881939, -1149678665, 1414078638, -1308179428, 1563864837, 1770677471, -2034626618, 15987563, -272394126, -612412504, 877607089, -1745290576, 2026410409, 1282693747, -1555811990, 621661639, -901929250, -25072380, 296814109, -134290769, 416188854, 745685612, -1019074187, 1158403544, -1438925119, -1635289829, 1907826178, 1475660430, -1195073129, -1938265523, 1665663316, -452854279, 171022048, 1049451834, -776128989, 938660497, -658327160, -327257518, 55449931, -2063079962, 1782025983, 1586185509, -1313132996, 0, 1745038536, -804890224, -1207601832, -1511995951, -840701671, 1978047553, 501592201, 1311636819, 640602523, -1641306941, -165115893, -338872190, -2083646390, 1003184402, 1405636058, -1671693658, -195504530, 1281205046, 610177022, 968572791, 1371018175, -373463321, -2118235601, -764016651, -1166726339, 40918629, 1785950893, 2006368804, 529919724, -1483695180, -812402820, 1029407677, 1431875445, -312616403, -2057373979, -1732557204, -256349532, 1220354044, 549335860, 1937145582, 460673574, -1552930946, -881652810, -694764737, -1097492489, 110158511, 1855180391, -1593804517, -922528301, 1896226955, 419761219, 81837258, 1826852866, -723065510, -1125791342, -282229688, -2026985344, 1059839448, 1462300944, 1254965657, 583953745, -1697966071, -221760319, 2058815354, 313797554, -1431216406, -1028492766, -547896661, -1219170717, 257006395, 1733474291, 882571817, 1553585889, -459496519, -1935700111, -1854259208, -109505744, 1098671720, 696208032, -420676132, -1896885996, 921347148, 1592363140, 1124874253, 722408645, -1828036195, -83276459, -1463746417, -1061016505, 2026330399, 281310679, 220317022, 1696786838, -584606514, -1255886842, 1206682823, 804235279, -1746215593, -1445473, -502513386, -1978700322, 839522438, 1510552654, 163674516, 1640125788, -641261564, -1312551732, -1407075259, -1004367731, 2082989525, 337955101, -608731551, -1280027991, 196159473, 1672612665, 2119678896, 374642552, -1370365408, -967651608, -1785035982, -40259590, 1167907490, 765458026, 813319907, 1484352043, -528736397, -2004929605, -177336588, -1653792196, 627595108, 1298889644, 1351535397, 948824045, -2138533195, -393494915, -1153174617, -750723217, 1799727671, 54953727, 514012790, 1990204094, -828018714, -1499053266, 1765143634, 20371610, -1187795518, -785350390, -858443389, -1529471669, 483616787, 1959806171, 655886593, 1327179209, -149008239, -1625457575, -2097623856, -352591848, 1392416064, 989706632, -936014519, -1607032447, 406050009, 1882257425, 1842694296, 97936464, -1110241016, -707772992, -2045218790, -300196654, 1444817290, 1042089282, 603502027, 1274779907, -201397157, -1677868909, 1416525807, 1013799719, -2073547137, -328531273, -242306498, -1718771978, 562621358, 1233897318, 440634044, 1916839540, -901393620, -1572405276, -1079816339, -677354587, 1873090301, 128334389, -1881601650, -405134010, 1608470558, 937196758, 708430943, 1111154839, -96496177, -1841514233, -1040911139, -1443375083, 301116749, 2045870469, 1679044876, 202841540, -1273861988, -602848172, 327349032, 2072109024, -1014715720, -1417181584, -1235077383, -564061647, 1717858153, 241648545, 1571753595, 900473523, -1918281749, -441812189, -128988246, -1874008222, 675910202, 1078640370, -1300067789, -629037317, 1652872099, 176684907, 392318946, 2137088810, -949741966, -1352189254, -55609504, -1800643672, 749285104, 1151992376, 1498395313, 827104889, -1991644383, -515192855, 786002069, 1188715613, -18929403, -1763965491, -1959152316, -482698868, 1530916052, 859619356, 1626639814, 150446350, -1326263210, -655230818, -988526569, -1390975777, 353505671, 2098281807, 0, 1228700967, -1837565362, -616265879, 555582061, 1747058506, -1285195741, -94829308, 1111164122, 185039357, -800850284, -1719696461, 1663469239, 706411408, -245465863, -1201536546, -2072639052, -850758509, 370078714, 1597148893, -1519678503, -329779458, 924021143, 2117012656, -968028818, -1888391095, 1412822816, 487164423, -414150909, -1368591836, 1965585741, 1007945834, 218129817, 1144789182, -1619484713, -700128528, 740157428, 1696701139, -1100669510, -145137507, 1329291587, 101129316, -582771955, -1803557334, 1848042286, 656055817, -60941984, -1251843001, -1988727763, -1068887798, 453940835, 1379068740, -1469321664, -514354329, 974328846, 1932486953, -884119305, -2106518064, 1496683193, 269086622, -363795814, -1553164355, 2015891668, 823422451, 436259634, 1396487701, -2005388932, -1052488613, 991775071, 1914778744, -1452952815, -530985418, 1480314856, 285717199, -901565018, -2088810367, 2032553349, 807022754, -346114101, -1570583828, -1636384122, -682967135, 202258632, 1160922607, -1083490069, -162054708, 756267685, 1680852866, -598882724, -1787708549, 1312111634, 118047029, -45071311, -1267975914, 1864941183, 638894936, 385920683, 1581044620, -2055711515, -867948094, 907881670, 2132890081, -1536829816, -312890449, 1429973617, 470275926, -951889857, -1904268008, 1948657692, 1025135931, -429993390, -1352487051, -1820940513, -632628680, 17718609, 1211244662, -1301600910, -78161835, 538173244, 1764729371, -783440955, -1737367838, 1127569803, 168371372, -263183960, -1184080753, 1646844902, 722773697, 872519268, 2101209923, -1501991894, -280686323, 354161673, 1545627950, -2023428537, -833055904, 1983550142, 1057419161, -465409808, -1384245801, 1462178003, 505114100, -983569763, -1939630150, -1334337584, -112467209, 571434398, 1798510777, -1855316547, -665427814, 51570675, 1244568276, -229860598, -1150228947, 1614045508, 688397411, -749659801, -1704106944, 1093264169, 135634446, 956429309, 1883082458, -1418131021, -498764652, 404517264, 1361054903, -1973122082, -1017579783, 2067461927, 839289344, -381547159, -1602326450, 1512535370, 320538733, -933261564, -2124156381, -1116210615, -196376722, 789512199, 1714650400, -1670744028, -715782909, 236094058, 1194262349, -11731309, -1234140236, 1832125661, 604535290, -565084930, -1754463783, 1277789872, 85326743, 771841366, 1732059249, -1132878056, -179971521, 253550395, 1176543772, -1654381195, -732407726, 1815763340, 621159595, -29187134, -1216422171, 1294457825, 68921030, -547413585, -1771873144, -1435020062, -481613371, 940551852, 1899221899, -1955932529, -1034507352, 420621505, 1345212902, -397651912, -1586483937, 2050271862, 856217425, -917384619, -2140295310, 1529423899, 303387964, 587282639, 1782400488, -1317420415, -129646682, 35437218, 1260439429, -1872477972, -648528437, 1631206421, 671498546, -213727653, -1166099588, 1076346488, 152814431, -765508554, -1687996143, -1485360773, -297055140, 890227509, 2083763730, -2039827690, -816394703, 336742744, 1563309183, -447990367, -1401927546, 1999949807, 1040757448, -1001277492, -1922184469, 1445547394, 521482405 };
    }
}
