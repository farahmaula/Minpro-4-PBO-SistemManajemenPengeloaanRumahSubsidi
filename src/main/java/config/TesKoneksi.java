/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import dao.RumahDAO;
import java.sql.Connection;
import model.Rumah;

/**
 *
 * @author ACER
 */
public class TesKoneksi {
    public static void main(String[] args) {
        try (Connection koneksi = Koneksi.getKoneksi()) {
            if (koneksi != null) {
                System.out.println(
                        "Database rumah_subsidi siap digunakan."
                );
            }else {
                System.out.println("Koneksi database gagal.");
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // Tes pembacaan data menggunakan RumahDAO
        System.out.println("\n=== DATA RUMAH DARI DATABASE ===");

        RumahDAO dao = new RumahDAO();

        for (Rumah rumah : dao.getSemuaRumah()) {
            System.out.println("-------------------------");
            rumah.tampilkanData();
        }
    }
}
