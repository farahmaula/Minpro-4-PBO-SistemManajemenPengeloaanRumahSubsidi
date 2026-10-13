/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/rumah_subsidi";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getKoneksi() {
        Connection koneksi = null;
        try {
            koneksi = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal: "
                    + e.getMessage());
        }
        return koneksi;
    }

    public static Connection getConnection() {
        return getKoneksi();
    }
}
