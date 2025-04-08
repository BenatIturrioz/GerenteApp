package com.example.gerenteapp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UploadToDrive {

    public static void uploadFile() {
        try {
            File file = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "output.xml");


            if (!file.exists()) {
                throw new FileNotFoundException("El archivo no se encuentra en la ubicación especificada: " + file.getAbsolutePath());
            }

            String scriptUrl = "https://script.google.com/macros/s/AKfycbx_veprvJF3dyfjCCPDvzTOuY88Cz-iCq_SVwOTzgS0W1AUkCglU6k6Rm-3KGHtvjcj/exec";


            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
            }


            String postData = "fileData=" + URLEncoder.encode(fileContent.toString(), "UTF-8");


            HttpURLConnection connection = (HttpURLConnection) new URL(scriptUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                writer.write(postData);
            }


            try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String responseLine;
                while ((responseLine = responseReader.readLine()) != null) {
                    System.out.println("Respuesta del servidor: " + responseLine);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
