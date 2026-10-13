package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Rumah;
import model.RumahTersedia;
import model.RumahTerjual;

public class RumahDAO {

    // READ: Membaca semua data rumah
    public List<Rumah> getSemuaRumah() {
        List<Rumah> daftarRumah = new ArrayList<>();
        String sql = "SELECT * FROM rumah";

        try (Connection koneksi = Koneksi.getKoneksi()) {
            if (koneksi == null) {
                return daftarRumah;
            }

            try (PreparedStatement ps = koneksi.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Rumah rumah = new RumahTersedia(
                        rs.getString("id_rumah"),
                        rs.getString("unit"),
                        rs.getString("tipe_rumah"),
                        rs.getDouble("harga"),
                        rs.getInt("jumlah_unit"),
                        rs.getString("blok_rumah")
                    );

                    daftarRumah.add(rumah);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal membaca data rumah: "
                    + e.getMessage());
        }

        return daftarRumah;
    }

    // CREATE: Menambahkan rumah ke database
    public boolean tambahRumah(RumahTersedia rumah) {
        String sql = "INSERT INTO rumah "
                + "(id_rumah, unit, tipe_rumah, harga, "
                + "jumlah_unit, blok_rumah, status_rumah) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'Tersedia')";

        try (Connection koneksi = Koneksi.getKoneksi()) {
            if (koneksi == null) {
                return false;
            }

            try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                ps.setString(1, rumah.getIdRumah());
                ps.setString(2, rumah.getUnit());
                ps.setString(3, rumah.getTipeRumah());
                ps.setDouble(4, rumah.getHarga());
                ps.setInt(5, rumah.getJumlahUnit());
                ps.setString(6, rumah.getBlokRumah());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal menambah rumah: "
                    + e.getMessage());
            return false;
        }
    }

    // UPDATE: Mengubah data rumah
    public boolean ubahRumah(RumahTersedia rumah) {
        String sql = "UPDATE rumah SET unit = ?, tipe_rumah = ?, "
                + "harga = ?, jumlah_unit = ?, blok_rumah = ? "
                + "WHERE id_rumah = ? AND status_rumah = 'Tersedia'";

        try (Connection koneksi = Koneksi.getKoneksi()) {
            if (koneksi == null) {
                return false;
            }

            try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                ps.setString(1, rumah.getUnit());
                ps.setString(2, rumah.getTipeRumah());
                ps.setDouble(3, rumah.getHarga());
                ps.setInt(4, rumah.getJumlahUnit());
                ps.setString(5, rumah.getBlokRumah());
                ps.setString(6, rumah.getIdRumah());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengubah rumah: "
                    + e.getMessage());
            return false;
        }
    }

    // DELETE: Menghapus data rumah
    public boolean hapusRumah(String idRumah) {
        String sql = "DELETE FROM rumah "
                + "WHERE id_rumah = ? AND status_rumah = 'Tersedia'";

        try (Connection koneksi = Koneksi.getKoneksi()) {
            if (koneksi == null) {
                return false;
            }

            try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                ps.setString(1, idRumah);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus rumah: "
                    + e.getMessage());
            return false;
        }
    }
    
    public boolean tandaiRumahTerjual(
        String idRumah, String tanggalJual) {

    String sql = "UPDATE rumah "
               + "SET status_rumah = ?, tanggal_jual = ? "
               + "WHERE id_rumah = ? "
               + "AND status_rumah = 'Tersedia'";

    try (Connection conn = Koneksi.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "Terjual");
        ps.setDate(2, java.sql.Date.valueOf(tanggalJual));
        ps.setString(3, idRumah);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println(
            "Gagal memperbarui status rumah: " + e.getMessage()
        );
        return false;
    }
}
}
