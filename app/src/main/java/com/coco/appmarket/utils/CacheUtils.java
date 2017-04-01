package com.coco.appmarket.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 缓存处理
 */

public class CacheUtils {
    public static void setCache(String name, String json) {

        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, name);
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            //设置缓存的有效时间
            long deadLine = System.currentTimeMillis() + 30 * 60 * 1000;
            writer.write(deadLine + "\n");

            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }

    public String getCahce(String name, String json) {
        File cacheDir = UIUtils.getContext().getCacheDir();
        File cacheFile = new File(cacheDir, name);

        if (cacheFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadLine = reader.readLine();
                long deadTime = Long.parseLong(deadLine);
                StringBuilder sb = new StringBuilder();
                String line;
                if (System.currentTimeMillis() < deadTime) {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

}
