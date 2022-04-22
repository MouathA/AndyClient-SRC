package Mood.Cosmetics.Utils.File;

import java.io.*;

public class FileEntry
{
    private File file;
    
    public FileEntry(final File file, final String s) {
        this.setFile(new File(file, s));
    }
    
    public void initDir() {
        if (!this.getFile().exists()) {
            this.getFile().mkdir();
        }
    }
    
    public void initData(final String s) {
        if (!this.getFile().exists()) {
            this.getFile().createNewFile();
            this.set(s);
        }
    }
    
    public String get() {
        return new BufferedReader(new FileReader(this.getFile())).readLine();
    }
    
    public void add(final String s) {
        final FileWriter fileWriter = new FileWriter(this.getFile());
        final StringBuilder sb = new StringBuilder();
        sb.append(s);
        String value = this.get();
        if (value == null) {
            value = "";
        }
        fileWriter.write(String.valueOf(String.valueOf(value)) + sb.toString());
        fileWriter.flush();
        fileWriter.close();
    }
    
    public void set(final String s) {
        this.getFile().delete();
        final FileWriter fileWriter = new FileWriter(this.getFile());
        final StringBuilder sb = new StringBuilder();
        sb.append(s);
        fileWriter.write(sb.toString());
        fileWriter.flush();
        fileWriter.close();
    }
    
    public File getFile() {
        return this.file;
    }
    
    public void deleteOnExitGame() {
        this.file.deleteOnExit();
    }
    
    public void delete() {
        this.file.delete();
    }
    
    public void setFile(final File file) {
        this.file = file;
    }
}
