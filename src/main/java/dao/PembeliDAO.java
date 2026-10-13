package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pembeli;
/**
 *
 * @author ACER
 */
public class PembeliDAO {
    public List<Pembeli> getSemuaPembeli() {
        List<Pembeli> daftar = new ArrayList<>();
        String sql = "SELECT * FROM pembeli";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftar.add(new Pembeli(
                    rs.getString("nik"),
                    rs.getString("nama"),
                    rs.getDouble("penghasilan"),
                    rs.getString("no_hp"),
                    rs.getString("status_punya_rumah")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca pembeli: " + e.getMessage());
        }

        return daftar;
    }

    public boolean tambahPembeli(Pembeli p) {
        String sql = "INSERT INTO pembeli "
            + "(nik, nama, penghasilan, no_hp, status_punya_rumah) "
            + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNik());
            ps.setString(2, p.getNama());
            ps.setDouble(3, p.getPenghasilan());
            ps.setString(4, p.getNoHP());
            ps.setString(5, p.getStatusPunyaRumah());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambah pembeli: " + e.getMessage());
            return false;
        }
    }

    public boolean ubahPembeli(Pembeli p) {
        String sql = "UPDATE pembeli SET nama=?, penghasilan=?, "
            + "no_hp=?, status_punya_rumah=? WHERE nik=?";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNama());
            ps.setDouble(2, p.getPenghasilan());
            ps.setString(3, p.getNoHP());
            ps.setString(4, p.getStatusPunyaRumah());
            ps.setString(5, p.getNik());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah pembeli: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusPembeli(String nik) {
        String sql = "DELETE FROM pembeli WHERE nik=?";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nik);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus pembeli: " + e.getMessage());
            return false;
        }
    }
}
