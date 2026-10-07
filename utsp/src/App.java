import com.struk.Kartu;
import com.struk.Operator;
import com.struk.TokenBonus;
import com.struk.TokenReguler;
import com.struk.Transaksi;
import com.struk.TransaksiQR;
import com.struk.TransaksiTunai;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Kartu> katalogProduk = new ArrayList<>();
        List<Operator> daftarOperator = new ArrayList<>();

// 1. BACA DATA KATALOG PRODUK DAN DATA OPERATOR DARI FILE .TXT
        try (BufferedReader br = new BufferedReader(new FileReader("katalog_token.txt"))) {
            String baris;
            while ((baris = br.readLine()) != null) {
                String[] data = baris.split(",");
                
                String jenis = data[0];
                String namaItem = data[1];
                int harga = Integer.parseInt(data[2]);
                int qtyDasar = Integer.parseInt(data[3]);

                if (jenis.equals("Reguler")) {
                    int jumlahToken = Integer.parseInt(data[4]);
                    katalogProduk.add(new TokenReguler(namaItem, harga, qtyDasar, jumlahToken));
                } else if (jenis.equals("Bonus")) {
                    String jenisPromo = data[4];
                    int bonusToken = Integer.parseInt(data[5]);
                    katalogProduk.add(new TokenBonus(namaItem, harga, qtyDasar, jenisPromo, bonusToken));
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file katalog_token.txt: " + e.getMessage());
            System.out.println("Pastikan file .txt ada di direktori yang sama.");
            return; // Hentikan program jika gagal baca file
        }


        ////
        try (BufferedReader br = new BufferedReader(new FileReader("data_operator.txt"))) {
            String baris;
            while ((baris = br.readLine()) != null) {
                String[] data = baris.split(",");
                
                String namaOperator = data[0];
                String terminal = data[1];

                daftarOperator.add(new Operator(namaOperator, terminal));
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file data_operator.txt: " + e.getMessage());
            System.out.println("Pastikan file .txt ada di direktori yang sama.");
            return; // Hentikan program jika gagal baca file
        }


// 2. INISIALISASI TRANSAKSI OLEH KASIR
        //Operator op1 = new Operator();
        System.out.println("------ PILIH OPERATOR ------");
        for (int i = 0; i < daftarOperator.size(); i++) {
            Operator operator = daftarOperator.get(i);
            System.out.println((i + 1) + ". " + operator.getNamaOperator() + " - Terminal: " + operator.getTerminal());
        }
        System.out.print("Pilih Operator (1-" + daftarOperator.size() + "): ");
            int pilihanOperator = input.nextInt();

        System.out.println("\n------ METODE TRANSAKSI ------");
        System.out.println("1. Tunai");
        System.out.println("2. Cashless");
        System.out.print("Pilih Metode Transaksi : ");
        int pilihantransaksi = input.nextInt();

        Transaksi trx1;
        if (pilihantransaksi == 1) {
            trx1 = new TransaksiTunai(); // Mengisi objek anak Tunai
        } else {
            trx1 = new TransaksiQR(); // Mengisi objek anak QRIS
        }
        trx1.setObjOperator(daftarOperator.get(pilihanOperator - 1));

        System.out.println("\n=================================");
        System.out.println("  SISTEM KASIR PENJUALAN TOKEN   ");
        System.out.println("=================================");
        
        boolean lanjutBelanja = true;

// 3. LOOPING MENU UNTUK OPERATOR
        while (lanjutBelanja) {
            System.out.println("\n------ KATALOG PRODUK ------");
            for (int i = 0; i < katalogProduk.size(); i++) {
                Kartu item = katalogProduk.get(i);
                System.out.println((i + 1) + ". " + item.getNamaItem() + " - Rp" + item.getHarga());
                System.out.println("   Info: " + item.getDetailItem());
            }
            System.out.println("0. Selesai & Lanjut Pembayaran");
            System.out.println("-----------------------------");

            System.out.print("Pilih nomor produk (0-" + katalogProduk.size() + "): ");
            int pilihan = input.nextInt();

            if (pilihan == 0) {
                lanjutBelanja = false;
            } else if (pilihan > 0 && pilihan <= katalogProduk.size()) {
                Kartu produkPilihan = katalogProduk.get(pilihan - 1);
                
                System.out.print("Masukkan jumlah beli (qty): ");
                int qtyBeli = input.nextInt();

                // Salin dari katalog sebagai objek baru agar qty sesuai dengan pesanan pelanggan
                if (produkPilihan instanceof TokenReguler) {
                    TokenReguler tr = (TokenReguler) produkPilihan;
                    trx1.tambahBarang(new TokenReguler(tr.getNamaItem(), tr.getHarga(), qtyBeli, tr.getJumlahToken()));
                } 
                else if (produkPilihan instanceof TokenBonus) {
                    TokenBonus tb = (TokenBonus) produkPilihan;
                    trx1.tambahBarang(new TokenBonus(tb.getNamaItem(), tb.getHarga(), qtyBeli, tb.getJenisPromo(), tb.getBonusToken()));
                }
                System.out.println(">> " + produkPilihan.getNamaItem() + " sebanyak " + qtyBeli + " berhasil ditambahkan!");
                System.out.println("   Keterangan: " + produkPilihan.getDetailItem());
            } else {
                System.out.println(">> Pilihan tidak valid, silakan ulangi.");
            }
        }

// 4. PROSES PEMBAYARAN JIKA ADA BARANG DI KERANJANG
        if (trx1.getListBarang().isEmpty()) {
            System.out.println("\nTidak ada barang yang dibeli. Transaksi dibatalkan.");
        } else {
            int totalBelanja = trx1.hitungTotal(); 
            System.out.println("\n====================================");
            System.out.println("TOTAL         : Rp" + totalBelanja);
        if (trx1 instanceof TransaksiTunai) {
            TransaksiTunai tunai = (TransaksiTunai) trx1;
            System.out.print("Nominal Bayar : Rp");
            int bayar = input.nextInt(); 
            tunai.setNominalBayar(bayar);
        } else if (trx1 instanceof TransaksiQR) {
            TransaksiQR qr = (TransaksiQR) trx1;
            input.nextLine();
            System.out.print("Masukkan Provider " + qr.getProvider() + " : ");
            String prov = input.nextLine();
            qr.setProvider(prov);
        }
            
            // 5. CETAK STRUK MENGGUNAKAN INTERFACE DAN KELAS STRUK
            trx1.cetakStruk();
        }
        input.close();
    }
}
