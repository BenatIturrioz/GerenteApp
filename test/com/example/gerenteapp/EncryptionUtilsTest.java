package com.example.gerenteapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EncryptionUtilsTest {
    @Test
    void encrypt() {
        String textoOriginal = "Prueba123";
        String textoEncriptado = EncryptionUtils.encrypt(textoOriginal);

        assertNotNull(textoEncriptado);
        assertNotEquals(textoOriginal, textoEncriptado);
        assertTrue(textoEncriptado.contains("=")); // Verifica formato Base64
    }

    @Test
    void decrypt() {
        String textoOriginal = "Prueba123";
        String textoEncriptado = EncryptionUtils.encrypt(textoOriginal);
        String textoDesencriptado = EncryptionUtils.decrypt(textoEncriptado);

        assertNotNull(textoDesencriptado);
        assertEquals(textoOriginal, textoDesencriptado);
    }
}