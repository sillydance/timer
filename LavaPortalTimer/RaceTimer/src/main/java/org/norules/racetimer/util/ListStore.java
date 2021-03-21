package org.norules.racetimer.util;

import java.io.*;
import java.util.*;

public class ListStore
{
    private File storageFile;
    public Hashtable<String, Long> records;

    public ListStore(final File file) {
        this.storageFile = file;
        this.records = new Hashtable<String, Long>();
        if (!this.storageFile.exists()) {
            try {
                this.storageFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        try {
            final InputStream input = new DataInputStream(new FileInputStream(this.storageFile));
            final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null) {
                final Object[] both = line.split(":");
                this.records.put((String)both[0], new Long((String)both[1]));
            }
            reader.close();
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            final FileWriter stream = new FileWriter(this.storageFile);
            final BufferedWriter out = new BufferedWriter(stream);
            for (final Map.Entry<String, Long> entry : this.records.entrySet()) {
                out.write(String.valueOf(entry.getKey()) + ":" + entry.getValue());
                out.newLine();
            }
            out.close();
            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTime(final String name) {
        return this.records.get(name);
    }

    public void add(final String name, final long num) {
        this.records.put(name, num);
    }

    public int getSize() {
        return this.records.size();
    }
}
