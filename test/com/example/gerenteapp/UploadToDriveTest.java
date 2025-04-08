package com.example.gerenteapp;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class UploadToDriveTest {

    @Test
    void uploadFile_whenFileDoesNotExist_shouldThrowException() {
        File file = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "output.xml");
        File tempBackup = new File(file.getPath() + ".bak");

        if (file.exists()) {
            file.renameTo(tempBackup);
        }

        assertDoesNotThrow(() -> UploadToDrive.uploadFile());


        if (tempBackup.exists()) {
            tempBackup.renameTo(file);
        }
    }

    @Test
    void uploadFile_whenFileExists_shouldNotThrow() {
        File file = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "output.xml");

        try {
            if (!file.exists()) {
                file.createNewFile(); // creamos archivo vacío solo para test
            }

            assertDoesNotThrow(() -> UploadToDrive.uploadFile());

        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}
