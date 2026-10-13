package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Dokumen;

/**
 *
 * @author ACER
 */
public class DokumenDAO {
    public List<Dokumen> getSemuaDokumen() {
        List<Dokumen> daftar = new ArrayList<>();
        String sql = "SELECT * FROM dokumen";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(new Dokumen(
                    rs.getString("id_dokumen"),
                    rs.getString("nik_pembeli"),
                    rs.getString("nama_dokumen"),
                    rs.getString("status_verifikasi")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca dokumen: " + e.getMessage());
        }

        return daftar;
    }

    public boolean tambahDokumen(Dokumen d) {
        String sql = "INSERT INTO dokumen "
            + "(id_dokumen, nik_pembeli, nama_dokumen, status_verifikasi) "
            + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getIdDokumen());
            ps.setString(2, d.getNikPembeli());
            ps.setString(3, d.getNamaDokumen());
            ps.setString(4, d.getStatusVerifikasi());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambah dokumen: " + e.getMessage());
            return false;
        }
    }

    public boolean ubahDokumen(Dokumen d) {
        String sql = "UPDATE dokumen SET nik_pembeli=?, "
            + "nama_dokumen=?, status_verifikasi=? "
            + "WHERE id_dokumen=?";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNikPembeli());
            ps.setString(2, d.getNamaDokumen());
            ps.setString(3, d.getStatusVerifikasi());
            ps.setString(4, d.getIdDokumen());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah dokumen: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusDokumen(String idDokumen) {
        String sql = "DELETE FROM dokumen WHERE id_dokumen=?";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idDokumen);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus dokumen: " + e.getMessage());
            return false;
        }
    }
}
