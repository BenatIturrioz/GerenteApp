package com.example.gerenteapp;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class ErabiltzaileaDBTest {

    private static Erabiltzailea testErabiltzailea;

    @BeforeAll
    static void setUpBeforeAll() {
        // Creamos el usuario de prueba
        testErabiltzailea = new Erabiltzailea(
                99,                         // ID de prueba
                "testuser",                 // Nombre de usuario
                "testpass",                 // Contraseña
                1,                          // langilea_id (asegúrate de que este id exista en tu base de datos)
                4,                          // langilea_mota
                true                        // txatBaimena
        );

        // Insertamos el usuario de prueba en la base de datos
        String insertQuery = "INSERT INTO erabiltzailea (id, erabiltzaileIzena, pasahitza, langilea_id, langilea_mota, txatBaimena) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionTest.connect(); // Establecemos la conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            // Establecemos los valores en el PreparedStatement
            stmt.setInt(1, testErabiltzailea.getId());
            stmt.setString(2, testErabiltzailea.getErabiltzaileIzena());
            stmt.setString(3, testErabiltzailea.getPasahitza());
            stmt.setInt(4, testErabiltzailea.getLangilea_id());
            stmt.setInt(5, testErabiltzailea.getLangilea_mota());
            stmt.setBoolean(6, testErabiltzailea.getTxatBaimena());

            // Ejecutamos la inserción
            stmt.executeUpdate();
            System.out.println("Usuario de prueba insertado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("No se pudo insertar el usuario de prueba en la base de datos.");
        }
    }

    @AfterAll
    static void tearDownAfterAll() {
        // Eliminamos el usuario de prueba de la base de datos
        String deleteQuery = "DELETE FROM erabiltzailea WHERE id = ?";

        try (Connection conn = ConnectionTest.connect(); // Establecemos la conexión
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            // Establecemos el parámetro para el id del usuario
            stmt.setInt(1, testErabiltzailea.getId());

            // Ejecutamos la eliminación
            stmt.executeUpdate();
            System.out.println("Usuario de prueba eliminado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
            fail("No se pudo eliminar el usuario de prueba de la base de datos.");
        }
    }

    @Test
    void getErabiltzailea() {
        // Verificamos que el usuario se ha insertado correctamente
        List<Erabiltzailea> lista = ErabiltzaileaDB.getErabiltzailea();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(e -> e.getId() == testErabiltzailea.getId()));
    }

    @Test
    void updateErabiltzailea() {
        testErabiltzailea.setPasahitza("nuevopass");
        ErabiltzaileaDB.updateErabiltzailea(testErabiltzailea);

        String checkQuery = "SELECT pasahitza FROM erronka1.erabiltzailea WHERE id = ?";
        try (Connection conn = ConnectionTest.connect();
             PreparedStatement stmt = conn.prepareStatement(checkQuery)) {

            stmt.setInt(1, testErabiltzailea.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String updatedPassword = rs.getString("pasahitza");
                assertEquals("nuevopass", updatedPassword);  // Verificamos si la contraseña es la correcta
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error al verificar la contraseña actualizada");
        }
    }

    @Test
    void deleteErabiltzailea() {
        // Creamos un nuevo usuario temporal
        Erabiltzailea temp = new Erabiltzailea(9998, "tempuser", "temppass", 1, 4, true);
        ErabiltzaileaDB.updateErabiltzailea(temp);

        // Eliminamos al usuario temporal
        ErabiltzaileaDB.deleteErabiltzailea(temp.getId());

        // Verificamos que el usuario ya no existe
        List<Erabiltzailea> lista = ErabiltzaileaDB.getErabiltzailea();
        assertFalse(lista.stream().anyMatch(e -> e.getId() == temp.getId()));
    }

    @Test
    void validarErabiltzailea() {
        // 1. Preparar datos de prueba específicos para este test
        Erabiltzailea userToTest = new Erabiltzailea(
                9999,                   // ID diferente para evitar conflictos
                "testvalidation",        // Nombre de usuario
                "testpass123",           // Contraseña
                1,                       // langilea_id
                4,                       // langilea_mota DEBE SER 4 (como int)
                true                     // txatBaimena
        );

        // 2. Insertar usuario de prueba directamente en la BD
        String insertSQL = "INSERT INTO erabiltzailea (id, erabiltzaileIzena, pasahitza, langilea_id, langilea_mota, txatBaimena) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionTest.connect();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setInt(1, userToTest.getId());
            stmt.setString(2, userToTest.getErabiltzaileIzena());
            stmt.setString(3, userToTest.getPasahitza());
            stmt.setInt(4, userToTest.getLangilea_id());
            stmt.setInt(5, userToTest.getLangilea_mota()); // Se inserta como INT 4
            stmt.setBoolean(6, userToTest.getTxatBaimena());

            stmt.executeUpdate();
            System.out.println("Usuario de validación insertado correctamente");
        } catch (SQLException e) {
            fail("Error al insertar usuario para test de validación: " + e.getMessage());
        }

        // 3. Ejecutar las pruebas
        // Caso 1: Credenciales correctas (debería pasar)
        boolean resultadoValido = ErabiltzaileaDB.validarErabiltzailea("testvalidation", "testpass123");
        assertTrue(resultadoValido, "Debería validar credenciales correctas");

        // Caso 2: Contraseña incorrecta (debería fallar)
        boolean resultadoInvalido = ErabiltzaileaDB.validarErabiltzailea("testvalidation", "contraseñaerrónea");
        assertFalse(resultadoInvalido, "No debería validar contraseña incorrecta");

        // Caso 3: Usuario no existe (debería fallar)
        boolean resultadoNoExiste = ErabiltzaileaDB.validarErabiltzailea("usuarioinexistente", "cualquiercontraseña");
        assertFalse(resultadoNoExiste, "No debería validar usuario inexistente");

        // 4. Limpieza: Eliminar el usuario de prueba
        try (Connection conn = ConnectionTest.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM erabiltzailea WHERE id = ?")) {

            stmt.setInt(1, userToTest.getId());
            stmt.executeUpdate();
            System.out.println("Usuario de validación eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al limpiar usuario de validación: " + e.getMessage());
        }
    }

    @Test
    void baimenaTxat() {
        // Verificamos si el usuario tiene permisos de chat
        boolean tieneBaimena = new ErabiltzaileaDB().baimenaTxat("testuser");
        assertTrue(tieneBaimena);

        boolean sinBaimena = new ErabiltzaileaDB().baimenaTxat("noexiste");
        assertFalse(sinBaimena);
    }
}
