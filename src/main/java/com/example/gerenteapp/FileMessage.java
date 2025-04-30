package com.example.gerenteapp;

import java.io.Serializable;

public class FileMessage implements Serializable {
    private String fileName;
    private byte[] fileBytes;

    public FileMessage(String fileName, byte[] fileBytes) {
        this.fileName = fileName;
        this.fileBytes = fileBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }
}
