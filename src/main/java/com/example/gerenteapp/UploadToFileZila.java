package com.example.gerenteapp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class UploadToFileZila {

    public static void uploadFile() {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;

        try {
            client.connect("127.0.0.1");
            client.login("edit", "edit");

            //
            // Create an InputStream of the file to be uploaded
            //
            String filename = "./output.xml";
            fis = new FileInputStream(filename);


            client.storeFile(filename, fis);
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
