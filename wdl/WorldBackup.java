package wdl;

import java.text.*;
import java.util.*;
import java.util.zip.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.client.resources.*;

public class WorldBackup
{
    private static final DateFormat DATE_FORMAT;
    private static int[] $SWITCH_TABLE$wdl$WorldBackup$WorldBackupType;
    
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    }
    
    public static void backupWorld(final File file, final String s, final WorldBackupType worldBackupType, final IBackupProgressMonitor backupProgressMonitor) throws IOException {
        final String string = String.valueOf(s) + "_" + WorldBackup.DATE_FORMAT.format(new Date());
        switch ($SWITCH_TABLE$wdl$WorldBackup$WorldBackupType()[worldBackupType.ordinal()]) {
            case 1: {}
            case 2: {
                final File file2 = new File(file.getParentFile(), string);
                if (file2.exists()) {
                    throw new IOException("Backup folder (" + file2 + ") already exists!");
                }
                copyDirectory(file, file2, backupProgressMonitor);
            }
            case 3: {
                final File file3 = new File(file.getParentFile(), String.valueOf(string) + ".zip");
                if (file3.exists()) {
                    throw new IOException("Backup file (" + file3 + ") already exists!");
                }
                zipDirectory(file, file3, backupProgressMonitor);
            }
            default: {}
        }
    }
    
    public static void copyDirectory(final File file, final File file2, final IBackupProgressMonitor backupProgressMonitor) throws IOException {
        backupProgressMonitor.setNumberOfFiles(countFilesInFolder(file));
        copy(file, file2, file.getPath().length() + 1, backupProgressMonitor);
    }
    
    public static void zipDirectory(final File file, final File file2, final IBackupProgressMonitor backupProgressMonitor) throws IOException {
        backupProgressMonitor.setNumberOfFiles(countFilesInFolder(file));
        final FileOutputStream fileOutputStream = new FileOutputStream(file2);
        final ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        zipFolder(file, zipOutputStream, file.getPath().length() + 1, backupProgressMonitor);
        zipOutputStream.close();
        fileOutputStream.close();
    }
    
    private static void zipFolder(final File file, final ZipOutputStream zipOutputStream, final int n, final IBackupProgressMonitor backupProgressMonitor) throws IOException {
        File[] listFiles;
        while (0 < (listFiles = file.listFiles()).length) {
            final File file2 = listFiles[0];
            if (file2.isFile()) {
                final String substring = file2.getPath().substring(n);
                backupProgressMonitor.onNextFile(substring);
                zipOutputStream.putNextEntry(new ZipEntry(substring));
                final FileInputStream fileInputStream = new FileInputStream(file2);
                IOUtils.copy(fileInputStream, zipOutputStream);
                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
            else if (file2.isDirectory()) {
                zipFolder(file2, zipOutputStream, n, backupProgressMonitor);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    private static void copy(final File file, final File file2, final int n, final IBackupProgressMonitor backupProgressMonitor) throws IOException {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdir();
            }
            String[] list;
            while (0 < (list = file.list()).length) {
                final String s = list[0];
                copy(new File(file, s), new File(file2, s), n, backupProgressMonitor);
                int n2 = 0;
                ++n2;
            }
        }
        else {
            backupProgressMonitor.onNextFile(file2.getPath().substring(n));
            FileUtils.copyFile(file, file2, true);
        }
    }
    
    private static int countFilesInFolder(final File file) {
        if (!file.isDirectory()) {
            return 0;
        }
        File[] listFiles;
        while (0 < (listFiles = file.listFiles()).length) {
            final File file2 = listFiles[0];
            if (file2.isDirectory()) {
                final int n = 0 + countFilesInFolder(file2);
            }
            else {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    private WorldBackup() {
    }
    
    static int[] $SWITCH_TABLE$wdl$WorldBackup$WorldBackupType() {
        final int[] $switch_TABLE$wdl$WorldBackup$WorldBackupType = WorldBackup.$SWITCH_TABLE$wdl$WorldBackup$WorldBackupType;
        if ($switch_TABLE$wdl$WorldBackup$WorldBackupType != null) {
            return $switch_TABLE$wdl$WorldBackup$WorldBackupType;
        }
        final int[] $switch_TABLE$wdl$WorldBackup$WorldBackupType2 = new int[WorldBackupType.values().length];
        $switch_TABLE$wdl$WorldBackup$WorldBackupType2[WorldBackupType.FOLDER.ordinal()] = 2;
        $switch_TABLE$wdl$WorldBackup$WorldBackupType2[WorldBackupType.NONE.ordinal()] = 1;
        $switch_TABLE$wdl$WorldBackup$WorldBackupType2[WorldBackupType.ZIP.ordinal()] = 3;
        return WorldBackup.$SWITCH_TABLE$wdl$WorldBackup$WorldBackupType = $switch_TABLE$wdl$WorldBackup$WorldBackupType2;
    }
    
    public interface IBackupProgressMonitor
    {
        void setNumberOfFiles(final int p0);
        
        void onNextFile(final String p0);
    }
    
    public enum WorldBackupType
    {
        NONE("NONE", 0, "wdl.backup.none", ""), 
        FOLDER("FOLDER", 1, "wdl.backup.folder", "wdl.saveProgress.backingUp.title.folder"), 
        ZIP("ZIP", 2, "wdl.backup.zip", "wdl.saveProgress.backingUp.title.zip");
        
        public final String descriptionKey;
        public final String titleKey;
        private static final WorldBackupType[] ENUM$VALUES;
        
        static {
            ENUM$VALUES = new WorldBackupType[] { WorldBackupType.NONE, WorldBackupType.FOLDER, WorldBackupType.ZIP };
        }
        
        private WorldBackupType(final String s, final int n, final String descriptionKey, final String titleKey) {
            this.descriptionKey = descriptionKey;
            this.titleKey = titleKey;
        }
        
        public String getDescription() {
            return I18n.format(this.descriptionKey, new Object[0]);
        }
        
        public String getTitle() {
            return I18n.format(this.titleKey, new Object[0]);
        }
        
        public static WorldBackupType match(final String s) {
            WorldBackupType[] values;
            while (0 < (values = values()).length) {
                final WorldBackupType worldBackupType = values[0];
                if (worldBackupType.name().equalsIgnoreCase(s)) {
                    return worldBackupType;
                }
                int n = 0;
                ++n;
            }
            return WorldBackupType.NONE;
        }
    }
}
