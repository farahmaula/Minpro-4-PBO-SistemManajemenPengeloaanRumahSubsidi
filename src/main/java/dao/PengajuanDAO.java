package dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pengajuan;

/**
 *
 * @author ACER
 */
public class PengajuanDAO {
    public List<Pengajuan> getSemuaPengajuan() {
        List<Pengajuan> daftar = new ArrayList<>();
        String sql = "SELECT * FROM pengajuan";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pengajuan p = new Pengajuan(
                    rs.getString("id_pengajuan"),
                    rs.getString("nik_pembeli"),
                    rs.getString("id_rumah"),
                    rs.getString("status_pengajuan")
                );

                p.setMetodePembayaran(
                    rs.getString("metode_pembayaran")
                );
                p.setJumlahBayar(rs.getDouble("jumlah_bayar"));

                daftar.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Gagal membaca pengajuan: " + e.getMessage());
        }

        return daftar;
    }

    public boolean tambahPengajuan(Pengajuan p) {
        String sql = "INSERT INTO pengajuan "
            + "(id_pengajuan, nik_pembeli, id_rumah, "
            + "status_pengajuan, metode_pembayaran, jumlah_bayar) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getIdPengajuan());
            ps.setString(2, p.getNikPembeli());
            ps.setString(3, p.getIdRumah());
            ps.setString(4, p.getStatusPengajuan());
            ps.setString(5, p.getMetodePembayaran());
            ps.setDouble(6, p.getJumlahBayar());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambah pengajuan: " + e.getMessage());
            return false;
        }
    }

    public boolean ubahPengajuan(Pengajuan p) {
        String sql = "UPDATE pengajuan SET nik_pembeli=?, "
            + "id_rumah=?, status_pengajuan=?, "
            + "metode_pembayaran=?, jumlah_bayar=? "
            + "WHERE id_pengajuan=?";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNikPembeli());
            ps.setString(2, p.getIdRumah());
            ps.setString(3, p.getStatusPengajuan());
            ps.setString(4, p.getMetodePembayaran());
            ps.setDouble(5, p.getJumlahBayar());
            ps.setString(6, p.getIdPengajuan());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah pengajuan: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusPengajuan(String idPengajuan) {
        String sql = "DELETE FROM pengajuan WHERE id_pengajuan=?";

        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPengajuan);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus pengajuan: " + e.getMessage());
            return false;
        }
    }
}
