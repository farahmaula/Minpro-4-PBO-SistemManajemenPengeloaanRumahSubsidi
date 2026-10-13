package controller;

import java.util.ArrayList;
import java.util.Scanner;
import model.Rumah;
import model.RumahTersedia;
import model.RumahTerjual;
import model.Pembeli;
import model.Pengajuan;
import model.Dokumen;
import model.DapatSurvey;
import dao.RumahDAO;
import dao.PembeliDAO;
import dao.DokumenDAO;
import dao.PengajuanDAO;

/**
 *
 * @author ACER
 */
public class ManajemenData {
    Scanner input = new Scanner(System.in);
    private ValidasiInput validasi = new ValidasiInput();
    private ArrayList<Rumah> daftarRumah = new ArrayList<>();
    private RumahDAO rumahDAO = new RumahDAO();
    private ArrayList<Pembeli> daftarPembeli = new ArrayList<>();
    private final PembeliDAO pembeliDAO = new PembeliDAO();
    private ArrayList<Pengajuan> daftarPengajuan = new ArrayList<>();
    private final PengajuanDAO pengajuanDAO = new PengajuanDAO();
    private ArrayList<Dokumen> daftarDokumen = new ArrayList<>();
    private final DokumenDAO dokumenDAO = new DokumenDAO();
    
    public ManajemenData() {
        // Mengambil data rumah dari database melalui DAO
        daftarRumah.addAll(rumahDAO.getSemuaRumah());
        daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());
        daftarDokumen.addAll(dokumenDAO.getSemuaDokumen());
        daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());

}

    // MENU rumah
    
    public void menuRumah() {
        int pilihan;

        do {
            System.out.println("\nMANAJEMEN DATA RUMAH");
            System.out.println("1. Tambah Rumah");
            System.out.println("2. Lihat Rumah");
            System.out.println("3. Ubah Rumah");
            System.out.println("4. Hapus Rumah");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = validasi.bacaPilihan(input,0,4);

            switch (pilihan) {
                case 1:
                    tambahRumah();
                    break;

                case 2:
                    tampilRumah();
                    break;

                case 3:
                    ubahRumah();
                    break;

                case 4:
                    hapusRumah();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");
            }
        } while (pilihan != 0);

    }

    public void tambahRumah() {
        System.out.println("Contoh ID Rumah : R000");
        System.out.println("=======================\n");
        System.out.print("ID Rumah: ");
        String id = input.nextLine();
        
        if (!validasi.isIdRumahValid(id)) {
            System.out.println("ID Rumah Tidak Valid");
            return;
        }

        for (Rumah rumah : daftarRumah) {
            if (rumah.getIdRumah().equalsIgnoreCase(id)) {
                System.out.println("ID Rumah sudah tersedia.");
                return;
            }
        }

        System.out.println("Contoh Unit : A1");
        System.out.println("==================\n");
        System.out.print("Unit: ");
        String unit = input.nextLine();
        
        if (!validasi.isTeksValid(unit)) {
            System.out.println("Unit tidak boleh kosong");
            return;
        }
        
        System.out.println("Contoh Tipe Rumah : 3x5");
        System.out.println("=======================\n");
        System.out.print("Tipe Rumah: ");
        String tipe = input.nextLine();
        
        if (!validasi.isTeksValid(tipe)) {
            System.out.println("Tipe rumah tidak boleh kosong.");
            return;
        }
        
        System.out.println("Contoh Harga : 1000000");
        System.out.println("=======================\n");
        System.out.print("Harga: ");
        double harga = validasi.bacaDoublePositif(input);
        
        System.out.println("Contoh Jumlah Unit : 10");
        System.out.println("========================\n");
        System.out.print("Jumlah Unit: ");
        int jumlahUnit = validasi.bacaIntPositif(input);
        
        System.out.println("Contoh Blok Rumah : Blok Mawar");
        System.out.println("=======================\n");
        System.out.print("Blok Rumah: ");
        String blokRumah = input.nextLine();

        if (blokRumah.trim().isEmpty()) {
            System.out.println("Blok rumah tidak boleh kosong.");
            return;
        }
        
        // Polymorphic Collection: ArrayList bertipe Superclass (Rumah)
        RumahTersedia rumahBaru = new RumahTersedia(id, unit, tipe, harga, jumlahUnit, blokRumah);
        if (rumahDAO.tambahRumah(rumahBaru)) {
            daftarRumah.add(rumahBaru);
            System.out.println("Rumah subsidi berhasil ditambahkan.");
        }else{
            System.out.println("Gagal menambahkan rumah.");
        }
    }

    public void tampilRumah() {
        daftarRumah.clear();
        daftarRumah.addAll(rumahDAO.getSemuaRumah());
        System.out.println("\nDAFTAR RUMAH");
        if (daftarRumah.isEmpty()) {
            System.out.println("Data rumah masih kosong.");
            return;
        }

        //Panggil Method Overide
        for (Rumah rumah : daftarRumah) {
            System.out.println("\n==== Rumah Subsidi ====");            
            rumah.tampilkanData();
             if (rumah instanceof DapatSurvey) {
                ((DapatSurvey) rumah).survey();
             }
            System.out.println("=========================");
        }
    }

    public void ubahRumah() {
        System.out.println("Contoh ID Rumah : R000");
        System.out.println("=======================\n");
        System.out.print("Masukkan ID Rumah: ");
        String id = input.nextLine();
        
        if (!validasi.isIdRumahValid(id)) {
            System.out.println("ID Rumah Tidak Valid");
            return;
        }

        for (Rumah rumah : daftarRumah) {
            if (rumah.getIdRumah().equalsIgnoreCase(id)) {
                System.out.println("Contoh Unit : A1");
                System.out.println("=======================\n");
                System.out.print("Jumlah Unit Baru: ");
                String unit = input.nextLine();
                if (!validasi.isTeksValid(unit)) {
                    System.out.println("Unit tidak boleh kosong");
                    return;
                }
                
                System.out.println("Contoh Tipe Rumah : 3x5");
                System.out.println("=======================\n");
                System.out.print("Tipe Rumah Baru: ");
                String tipe = input.nextLine();
                if (!validasi.isTeksValid(tipe)) {
                    System.out.println("Tipe rumah tidak boleh kosong.");
                    return;
                }
                
                System.out.println("Contoh Harga : 2000000");
                System.out.println("=======================\n");
                System.out.print("Harga Baru: ");
                double harga = validasi.bacaDoublePositif(input);

                if (!(rumah instanceof RumahTersedia)) {
                    System.out.println("Rumah tidak berstatus tersedia.");
                    return;
                }

                RumahTersedia rumahTersedia = (RumahTersedia) rumah;

                RumahTersedia rumahBaru = new RumahTersedia(id, unit, tipe, harga, rumahTersedia.getJumlahUnit(), rumahTersedia.getBlokRumah());

                if (rumahDAO.ubahRumah(rumahBaru)) {
                    daftarRumah.clear();
                    daftarRumah.addAll(rumahDAO.getSemuaRumah());
                     System.out.println("Data rumah berhasil diubah.");
                } else {
                    System.out.println("Gagal mengubah data rumah.");
                }
                return;
            }
        }
        System.out.println("ID Rumah tidak ditemukan.");
    }

    public void hapusRumah() {
        System.out.println("Contoh : R000");
        System.out.println("=======================\n");
        System.out.print("Masukkan ID Rumah: ");
        String id = input.nextLine();
        if (!validasi.isIdRumahValid(id)) {
            System.out.println("ID Rumah Tidak Valid");
            return;
        }

        if (rumahDAO.hapusRumah(id)) {
            daftarRumah.clear();
            daftarRumah.addAll(rumahDAO.getSemuaRumah());
            System.out.println("Data rumah berhasil dihapus.");
        } else {
        System.out.println(
            "Gagal menghapus rumah. Pastikan ID Rumah benar ");
        }
    }
    
    // MENU Pembeli

    public void menuPembeli() {
        int pilihan;

        do {
            System.out.println("\nMANAJEMEN DATA PEMBELI");
            System.out.println("1. Tambah Pembeli");
            System.out.println("2. Lihat Pembeli");
            System.out.println("3. Ubah Pembeli");
            System.out.println("4. Hapus Pembeli");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = validasi.bacaPilihan(input,0,4);

            switch (pilihan) {
                case 1:
                    tambahPembeli();
                    break;

                case 2:
                    tampilPembeli();
                    break;

                case 3:
                    ubahPembeli();
                    break;

                case 4:
                    hapusPembeli();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");
            }
        } while (pilihan != 0);
    }

    public void tambahPembeli() {
        System.out.println("Contoh NIK : 6472056767280002");
        System.out.println("==============================\n");
        System.out.print("NIK: ");
        String nik = input.nextLine();
        if (!validasi.isNikValid(nik)) {
            System.out.println("NIK harus 16 digit");
            return;
        }

        for (Pembeli pembeli : daftarPembeli) {
            if (pembeli.getNik().equals(nik)) {
                System.out.println("NIK sudah terdaftar.");
                return;
            }
        }

        System.out.println("Contoh Nama : Anggita Melastri");
        System.out.println("===============================\n");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        if (!validasi.isTeksValid(nama)) {
            System.out.println("Nama tidak boleh kosong");
            return;
        }
        
        System.out.println("Contoh Penghasilan: 2000000");
        System.out.println("=======================\n");
        System.out.print("Penghasilan: ");
        String inputPenghasilan = input.nextLine();
        double penghasilan = Double.parseDouble(inputPenghasilan);
        
        System.out.println("Contoh No HP: 085612789355");
        System.out.println("=======================\n");
        System.out.print("No HP: ");
        String noHP = input.nextLine();
        if (!validasi.isNoHPValid(noHP)){
            System.out.println("No HP tidak boleh kosong");
            return;
        }
        
        System.out.println("Sudah memiliki rumah?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        System.out.print("Pilih: ");
        int pilihan = validasi.bacaPilihan(input,1,2);

        String statusRumah;

        if (pilihan == 1) {
            statusRumah = "Ya";
        } else if (pilihan == 2) {
            statusRumah = "Tidak";
        } else {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Pembeli pembeliBaru = new Pembeli(nik, nama, penghasilan, noHP, statusRumah);
        if (pembeliDAO.tambahPembeli(pembeliBaru)) {
            daftarPembeli.clear();
            daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());
            System.out.println("Pembeli berhasil ditambahkan.");
        } else {
            System.out.println("Gagal menambahkan pembeli ke database.");
        }
    }

    public void tampilPembeli() {
        daftarPembeli.clear();
        daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());
        System.out.println("\nDAFTAR PEMBELI");
        
        if (daftarPembeli.isEmpty()) {
            System.out.println("Data pembeli masih kosong.");
            return;
        }

        for (Pembeli pembeli : daftarPembeli) {
            System.out.println("\n=========================");
            pembeli.tampilkanData();
            System.out.println("=========================");
        }
    }

    public void ubahPembeli() {
        System.out.println("Contoh NIK : 6472056767280002");
        System.out.println("=============================\n");
        System.out.print("Masukkan NIK: ");
        String nik = input.nextLine();
        if (!validasi.isNikValid(nik)) {
            System.out.println("NIK harus 16 digit");
            return;
        }

        for (Pembeli pembeli : daftarPembeli) {
            if (pembeli.getNik().equals(nik)) {
                System.out.println("Contoh Penghasilan : 2000000");
                System.out.println("=======================\n");
                System.out.print("Penghasilan Baru: ");
                double penghasilan = validasi.bacaDoublePositif(input);
                
                System.out.println("Contoh No HP : 085612789355");
                System.out.println("=======================\n");
                System.out.print("No HP Baru: ");
                String noHP = input.nextLine();
                if (!validasi.isNoHPValid(noHP)){
                    System.out.println("No HP tidak boleh kosong");
                    return;
                }
                
                Pembeli pembeliBaru = new Pembeli(nik, pembeli.getNama(), penghasilan, noHP, pembeli.getStatusPunyaRumah());

                if (pembeliDAO.ubahPembeli(pembeliBaru)) {
                    daftarPembeli.clear();
                    daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());
                    System.out.println("Data pembeli berhasil diubah.");
                } else {
                    System.out.println("Gagal mengubah data pembeli.");
                }
                return;
            }
        }
        System.out.println("NIK tidak ditemukan.");
    }

    public void hapusPembeli() {
        System.out.println("Contoh NIK : 6472056767280002");
        System.out.println("=======================\n");
        System.out.print("Masukkan NIK: ");
        String nik = input.nextLine();
        if (!validasi.isNikValid(nik)) {
            System.out.println("NIK harus 16 digit");
            return;
        }

        if (pembeliDAO.hapusPembeli(nik)) {
            daftarPembeli.clear();
            daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());
            System.out.println("Data pembeli berhasil dihapus.");
        } else {
            System.out.println("Gagal menghapus pembeli. NIK tidak ditemukan ");
        }
    }

    // MENU pengajuan

    public void menuPengajuan() {
        int pilihan;

        do {
            System.out.println("\nMANAJEMEN PENGAJUAN");
            System.out.println("1. Tambah Pengajuan");
            System.out.println("2. Lihat Pengajuan");
            System.out.println("3. Proses Pengajuan");
            System.out.println("4. Pembayaran");
            System.out.println("5. Hapus Pengajuan");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = validasi.bacaPilihan(input, 0, 5);

            switch (pilihan) {
                case 1:
                    tambahPengajuan();
                    break;

                case 2:
                    tampilPengajuan();
                    break;

                case 3:
                    prosesPengajuan();
                    break;

                case 4:
                    pembayaran();
                    break;
                    
                case 5:
                    hapusPengajuan();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");
            }
        } while (pilihan != 0);
    }

    public void tambahPengajuan() {
        daftarPembeli.clear();
        daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());
        
        daftarRumah.clear();
        daftarRumah.addAll(rumahDAO.getSemuaRumah());
        System.out.println("Contoh : P000");
        System.out.println("=======================\n");
        System.out.print("ID Pengajuan: ");
        String idPengajuan = input.nextLine();
        if (validasi.isIdDokumenValid(idPengajuan)){
            System.out.println("Sesuaikan Format ID Dokumen Seperti Contoh");
            return;
        }

        for (Pengajuan pengajuan : daftarPengajuan) {
            if (pengajuan.getIdPengajuan().equalsIgnoreCase(idPengajuan)) {
                System.out.println("ID Pengajuan sudah tersedia.");
                return;
            }
        }

        System.out.println("Contoh NIK : 6472056767280002");
        System.out.println("=======================\n");
        System.out.print("NIK Pembeli: ");
        String nik = input.nextLine();
        Pembeli pembeliDipilih = null;
        if (!validasi.isNikValid(nik)) {
            System.out.println("NIK harus 16 digit");
            return;
        }

        for (Pembeli pembeli : daftarPembeli) {
            if (pembeli.getNik().equals(nik)) {
                pembeliDipilih = pembeli;
                break;
            }
        }

        if (pembeliDipilih == null) {
            System.out.println("Pembeli tidak ditemukan.");
            return;
        }

        if (pembeliDipilih.getPenghasilan() < 2000000) {
            System.out.println("Pengajuan tidak dapat dibuat.");
            System.out.println("Penghasilan pembeli kurang dari Rp2.000.000.");
            System.out.println("Penghasilan belum memadai untuk mengajukan rumah subsidi.");
            return;
        }
        
        System.out.println("Contoh ID Rumah : R000");
        System.out.println("=======================\n");
        System.out.print("ID Rumah: ");
        String idRumah = input.nextLine();
        boolean rumahTersedia = false;
        if (!validasi.isIdRumahValid(idRumah)) {
            System.out.println("ID Rumah Tidak Valid");
            return;
        }

        for (Rumah rumah : daftarRumah) {
            if (rumah.getIdRumah().equalsIgnoreCase(idRumah)) {
                rumahTersedia = true;
                break;
            }
        }

        if (!rumahTersedia) {
            System.out.println("Rumah tidak ditemukan atau tidak tersedia.");
            return;
        }

        Pengajuan pengajuanBaru = new Pengajuan(idPengajuan, nik, idRumah, "Menunggu Verifikasi");
        if (pengajuanDAO.tambahPengajuan(pengajuanBaru)) {
            daftarPengajuan.clear();
            daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());
            System.out.println("Pengajuan berhasil ditambahkan.");
            System.out.println("Status: Menunggu Verifikasi");
        }
    }

    public void tampilPengajuan() {
        daftarPengajuan.clear();
        daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());
        if (daftarPengajuan.isEmpty()) {
            System.out.println("Data pembayaran masih kosong.");
            return;
        }

        for (Pengajuan pembayaran : daftarPengajuan) {
            System.out.println("\n=========================");
            pembayaran.tampilkanData();
            System.out.println("=========================");
        }
    }

    public void prosesPengajuan() {
        daftarPengajuan.clear();
        daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());

        daftarPembeli.clear();
        daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());

        daftarDokumen.clear();
        daftarDokumen.addAll(dokumenDAO.getSemuaDokumen());
        System.out.println("Contoh ID Pengajuan : P000");
        System.out.println("=======================\n");
        System.out.print("Masukkan ID Pengajuan: ");
        String id = input.nextLine();
        if (validasi.isIdDokumenValid(id)){
            System.out.println("Sesuaikan Format ID Dokumen Seperti Contoh");
            return;
        }

        for (Pengajuan pengajuan : daftarPengajuan) {
            if (pengajuan.getIdPengajuan().equalsIgnoreCase(id)) {
                System.out.println("\nDATA PENGAJUAN");
                pengajuan.tampilkanData();
                System.out.print("\nApakah data pengajuan sudah sesuai? (y/t): ");
                String konfirmasi = input.nextLine();

                if (!konfirmasi.equalsIgnoreCase("y")) {
                    System.out.println("Proses pengajuan dibatalkan.");
                    return;
                }

                Pembeli pembeliDipilih = null;

                for (Pembeli pembeli : daftarPembeli) {
                    if (pembeli.getNik().equals(
                            pengajuan.getNikPembeli())) {

                        pembeliDipilih = pembeli;
                        break;
                    }
                }

                if (pembeliDipilih == null) {
                    System.out.println("Data pembeli tidak ditemukan.");
                    return;
                }

                if (pembeliDipilih.getPenghasilan() < 2000000) {
                    pengajuan.setStatusPengajuan("Ditolak");
                    if (pengajuanDAO.ubahPengajuan(pengajuan)) {
                        daftarPengajuan.clear();
                        daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());

                        System.out.println("Pengajuan DITOLAK.");
                        System.out.println("Penghasilan pembeli kurang dari Rp2.000.000.");
                        System.out.println("Penghasilan belum memadai untuk pengajuan.");
                        return;
                    }
                }

                if (pembeliDipilih.getStatusPunyaRumah()
                        .equalsIgnoreCase("Ya")) {

                    pengajuan.setStatusPengajuan("Ditolak");
                    if (pengajuanDAO.ubahPengajuan(pengajuan)) {
                        daftarPengajuan.clear();
                        daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());
                        System.out.println("Pengajuan DITOLAK.");
                        System.out.println("Pembeli sudah memiliki rumah.");
                        return;
                    }      
                }

                boolean ktp = false;

                for (Dokumen dokumen : daftarDokumen) {

                    if (dokumen.getNikPembeli().equals(pengajuan.getNikPembeli()) && dokumen.getStatusVerifikasi().equalsIgnoreCase("Terverifikasi")) {
                        if (dokumen.getNamaDokumen().equalsIgnoreCase("KTP")) {
                            ktp = true;
                        }
                    }
                }

                if (!ktp) {
                    System.out.println("Pengajuan belum dapat disetujui.");
                    System.out.println("Dokumen pembeli belum lengkap atau belum terverifikasi.");
                    System.out.println("\nDokumen yang belum terpenuhi:");

                    if (!ktp) {
                        System.out.println("- KTP");
                    }
                    return;
                }

                pengajuan.setStatusPengajuan("Disetujui");
                if (pengajuanDAO.ubahPengajuan(pengajuan)) {
                    daftarPengajuan.clear();
                    daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());
                    }

                System.out.println("\nPengajuan DISETUJUI.");
                System.out.println(
                        "Seluruh persyaratan telah terpenuhi.");
                return;
            }
        }
        System.out.println("ID Pengajuan tidak ditemukan.");
    }
    
public void pembayaran() {
        daftarPengajuan.clear();
        daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());

        daftarPembeli.clear();
        daftarPembeli.addAll(pembeliDAO.getSemuaPembeli());

        daftarRumah.clear();
        daftarRumah.addAll(rumahDAO.getSemuaRumah());
        System.out.println("Contoh ID Pengajuan : P000");
        System.out.println("=======================\n");
        System.out.print("Masukkan ID Pengajuan: ");
        String id = input.nextLine();
        if (!validasi.isIdPengajuanValid(id)){
            System.out.println("Sesuaikan Format ID Pengajuan Seperti Contoh");
            return;
        }

        boolean ditemukanPengajuan = false;
        int pilihan = 0; // Dideklarasikan di sini agar hierarki bawah tetap bisa mengaksesnya
        
        for (Pengajuan pengajuan : daftarPengajuan) {
            if (pengajuan.getIdPengajuan().equalsIgnoreCase(id)) {
                ditemukanPengajuan = true;
                if (!pengajuan.getStatusPengajuan().equalsIgnoreCase("Disetujui")) {
                    System.out.println("Pembayaran tidak dapat dilakukan.");
                    System.out.println("Pengajuan belum disetujui.");
                    return;
                }
                
                if (pengajuan.getMetodePembayaran() != null
                    && !pengajuan.getMetodePembayaran()
                    .equalsIgnoreCase("Belum Ada")
                    && !pengajuan.getMetodePembayaran().isEmpty()
                    && pengajuan.getJumlahBayar() > 0) {


                System.out.println(
                "Pembayaran sudah dilakukan."
                );


                System.out.println(
                "Metode Pembayaran : "
                + pengajuan.getMetodePembayaran()
                );


                System.out.println(
                "Jumlah Bayar : Rp. "
                + pengajuan.getJumlahBayar()
                );


                return;

            }

                
                System.out.println("Metode Pembayaran:");
                System.out.println("1. Cash");
                System.out.println("2. Cicilan");
                System.out.print("Pilih: ");
                pilihan = validasi.bacaPilihan(input, 1, 2);


                if (pilihan == 1) {
                    System.out.println("Contoh : 1000000");
                    System.out.print("Jumlah Bayar: ");
                    double jumlah = validasi.bacaDoublePositif(input);

                    pengajuan.setMetodePembayaran("Cash");
                    pengajuan.setJumlahBayar(jumlah);
                    System.out.println("Pembayaran cash berhasil disimpan.");
                }
                
                else if (pilihan == 2) {
                    Pembeli pembeliDipilih = null;

                    for (Pembeli pembeli : daftarPembeli) {
                        if (pembeli.getNik().equals(pengajuan.getNikPembeli())) {
                            pembeliDipilih = pembeli;
                            break;
                        }
                    }

                    if (pembeliDipilih == null) {
                        System.out.println("Data pembeli tidak ditemukan.");
                        return;
                    }

                    double cicilan = pembeliDipilih.getPenghasilan() * 0.30;
                    pengajuan.setMetodePembayaran("Cicilan");
                    pengajuan.setJumlahBayar(cicilan);
                    System.out.println("\nPembayaran cicilan berhasil ditentukan.");
                    System.out.println("Penghasilan Pembeli : Rp. " + pembeliDipilih.getPenghasilan());
                    System.out.println("Persentase Cicilan  : 30%");
                    System.out.println("Cicilan Per Bulan   : Rp. " +  cicilan);
                }
                 // Simpan pembayaran ke database
                if (!pengajuanDAO.ubahPengajuan(pengajuan)) {
                    System.out.println("Gagal menyimpan pembayaran ke database.");
                    return;
                }

                daftarPengajuan.clear();
                daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());
                System.out.println("\nPembayaran berhasil disimpan ke database.");
            
                // Tanggal jual hanya diminta satu kali untuk pembayaran Cash (Hierarki asli dipertahankan)
                if (pilihan == 1) {
                    System.out.println("\n=== DATA PENJUALAN RUMAH ===");
                    System.out.println("Contoh tanggal: 21 Juni 2030");
                    System.out.print("Tanggal Jual: ");
                    String tanggalJual = input.nextLine();
                
                    if (tanggalJual.trim().isEmpty()) {
                        System.out.println("Tanggal jual tidak boleh kosong.");
                        return;
                    }
                
                    java.sql.Date tanggal;
                
                    try {
                        java.time.format.DateTimeFormatter formatter
                            = java.time.format.DateTimeFormatter.ofPattern(
                                    "d MMMM yyyy",
                                    new java.util.Locale("id", "ID"));

                        java.time.LocalDate localDate
                            = java.time.LocalDate.parse(tanggalJual, formatter);

                        tanggal = java.sql.Date.valueOf(localDate);

                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Format tanggal salah. Contoh: 21 Juni 2030");
                        return;
                    }
                
                    // Tandai rumah terjual
                    if (rumahDAO.tandaiRumahTerjual(pengajuan.getIdRumah(), tanggal.toString())) {
                        daftarRumah.clear();
                        daftarRumah.addAll(rumahDAO.getSemuaRumah());
                        System.out.println("Rumah telah TERJUAL.");
                    } else {
                        System.out.println("Pembayaran tersimpan, tetapi status rumah gagal diperbarui.");
                    }
                }
                return;
            }
        }
        
        if (!ditemukanPengajuan) {
            System.out.println("ID Pengajuan tidak ditemukan.");
        }
    }

    public void hapusPengajuan() {
        System.out.println("Contoh : P000");
        System.out.println("=======================\n");
        System.out.print("Masukkan ID Pengajuan: ");
        String id = input.nextLine();
        if (!validasi.isIdPengajuanValid(id)){
            System.out.println("Sesuaikan Format ID Dokumen Seperti Contoh");
            return;
        }
        if (pengajuanDAO.hapusPengajuan(id)) {
            daftarPengajuan.clear();
            daftarPengajuan.addAll(pengajuanDAO.getSemuaPengajuan());
            System.out.println("Pengajuan berhasil dihapus.");
        }
    }

    // MENU Dokumen

    public void menuDokumen() {
        int pilihan;

        do {
            System.out.println("\nMANAJEMEN DOKUMEN");
            System.out.println("1. Tambah Dokumen");
            System.out.println("2. Lihat Dokumen");
            System.out.println("3. Verifikasi Dokumen");
            System.out.println("4. Hapus Dokumen");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = validasi.bacaPilihan(input,0,4);

            switch (pilihan) {
                case 1:
                    tambahDokumen();
                    break;

                case 2:
                    tampilDokumen();
                    break;

                case 3:
                    verifikasiDokumen();
                    break;

                case 4:
                    hapusDokumen();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");
            }
        } while (pilihan != 0);
    }

    public void tambahDokumen() {
        System.out.println("Contoh ID Dokumen : D000");
        System.out.println("=======================\n");
        System.out.print("ID Dokumen: ");
        String id = input.nextLine();
        if (validasi.isIdDokumenValid(id)){
            System.out.println("Sesuaikan Format ID Dokumen Seperti Contoh");
            return;
        }

        for (Dokumen dokumen : daftarDokumen) {
            if (dokumen.getIdDokumen().equalsIgnoreCase(id)) {
                System.out.println("ID Dokumen sudah tersedia.");
                return;
            }
        }

        System.out.println("Contoh NIK : 6472056767280002");
        System.out.println("=======================\n");
        System.out.print("NIK Pembeli: ");
        String nik = input.nextLine();
        boolean pembeliAda = false;
        if (!validasi.isNikValid(nik)) {
            System.out.println("NIK harus 16 digit");
            return;
        }

        for (Pembeli pembeli : daftarPembeli) {
            if (pembeli.getNik().equals(nik)) {
                pembeliAda = true;
                break;
            }
        }

        if (!pembeliAda) {
            System.out.println("Pembeli tidak ditemukan.");
            return;
        }

        System.out.println("Jenis Dokumen:");
        System.out.println("1. KTP");
        System.out.println("2. Surat Keterangan Belum Memiliki Rumah");
        System.out.print("Pilih: ");
        int pilihan = validasi.bacaPilihan(input, 1, 2);

        String jenis;
        switch (pilihan) {
            case 1:
                jenis = "KTP";
                break;

            case 2:
                jenis = "Surat Keterangan Belum Memiliki Rumah";
                break;

            default:
                System.out.println("Jenis dokumen tidak valid.");
                return;
        }

        Dokumen dokumenBaru = new Dokumen(id, nik, jenis, "Belum Terverifikasi");
        if (dokumenDAO.tambahDokumen(dokumenBaru)) {
            daftarDokumen.clear();
            daftarDokumen.addAll(dokumenDAO.getSemuaDokumen());
            System.out.println("Dokumen berhasil ditambahkan.");
        } else {
            System.out.println("Gagal menambahkan dokumen. Pastikan NIK pembeli sudah terdaftar dan ID dokumen belum digunakan.");
        }
    }

    public void tampilDokumen() {
        daftarDokumen.clear();
        daftarDokumen.addAll(dokumenDAO.getSemuaDokumen());

        System.out.println("\n=== DAFTAR DOKUMEN ===");
        if (daftarDokumen.isEmpty()) {
            System.out.println("Data dokumen masih kosong.");
            return;
        }

        for (Dokumen dokumen : daftarDokumen) {
            System.out.println("\n=========================");
            dokumen.tampilkanData();
            System.out.println("=========================");
        }
    }

    public void verifikasiDokumen() {
        daftarDokumen.clear();
        daftarDokumen.addAll(dokumenDAO.getSemuaDokumen());

        System.out.println("Contoh ID Dokumen : D000");
        System.out.println("=======================\n");
        System.out.print("Masukkan ID Dokumen: ");
        String id = input.nextLine();
        if (validasi.isIdDokumenValid(id)){
            System.out.println("Sesuaikan Format ID Dokumen Seperti Contoh");
            return;
        }

        for (Dokumen dokumen : daftarDokumen) {
            if (dokumen.getIdDokumen().equalsIgnoreCase(id)) {
                dokumen.setStatusVerifikasi("Terverifikasi");
                if (dokumenDAO.ubahDokumen(dokumen)) {
                    daftarDokumen.clear();
                    daftarDokumen.addAll( dokumenDAO.getSemuaDokumen());
                    System.out.println("Dokumen berhasil diverifikasi.");
                    return;
                }
            }
        }
        System.out.println("ID Dokumen tidak ditemukan.");
    }

    public void hapusDokumen() {
        System.out.println("Contoh ID Dokumen : D000");
        System.out.println("=======================\n");
        System.out.print("Masukkan ID Dokumen: ");
        String id = input.nextLine();
        if (validasi.isIdDokumenValid(id)){
            System.out.println("Sesuaikan Format ID Dokumen Seperti Contoh");
            return;
        }

        for (int i = 0; i < daftarDokumen.size(); i++) {
            if (dokumenDAO.hapusDokumen(id)) {
                daftarDokumen.clear();
                daftarDokumen.addAll(dokumenDAO.getSemuaDokumen());
                System.out.println("Dokumen berhasil dihapus.");
            }
        }
    }
    
    public ArrayList<Rumah> getDaftarRumah(){
        return daftarRumah;
    }

    public ArrayList<Pembeli> getDaftarPembeli(){
        return daftarPembeli;
    }

    public ArrayList<Pengajuan> getDaftarPengajuan(){
        return daftarPengajuan;
    }

    public ArrayList<Dokumen> getDaftarDokumen(){
        return daftarDokumen;
    }
}
