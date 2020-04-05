package me.gmx.minebomb.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static void copy (InputStream input, File file){
        try{
            FileOutputStream output = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int i;
            while ((i = input.read(b)) > 0){
                output.write(b,0,i);
            }
            output.close();
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void mkdir(File file){
        try{
            file.mkdir();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
