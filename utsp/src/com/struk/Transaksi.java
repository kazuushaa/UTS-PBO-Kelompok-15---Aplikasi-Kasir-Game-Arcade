package com.struk;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public abstract class Transaksi implements CetakStruk {
    private String noStruk;
    private int total;
    private Operator objOperator;
    private List<Kartu> listBarang = new ArrayList<>();

    public Transaksi(String no) {
        this.noStruk = no;
    }

    public Transaksi() {  
    }

    public Operator getObjOperator() { 
        return objOperator; }

    public void setObjOperator(Operator op) { 
        this.objOperator = op; }

    public String getNamaOperator() {
        if (objOperator != null) {
            return objOperator.getNamaOperator();
        }
        return "-";
    }
    
    public void tambahBarang(Kartu b) {
        listBarang.add(b);
    }

    public int hitungTotal() {
        this.total = 0;
        for (Kartu item : listBarang) {
            this.total += item.getSubtotal();
        }
        return this.total;
    }

    public String getNoStruk() { 
        return noStruk; }
    public void setNoStruk(String no) { 
        this.noStruk = no; }

    public int getTotal() { 
        return total; }

    public List<Kartu> getListBarang() { 
        return listBarang; }

    public abstract String getDetailPembayaran();

    @Override
    public void cetakStruk() {
        System.out.println("\n\n========= STRUK TRANSAKSI =========");
        System.out.println("No Struk      : " + this.getNoStruk());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Time          : " + LocalDateTime.now().format(format)); 

        System.out.println("-----------------------------------");
        
        for (Kartu item : this.getListBarang()) {
            System.out.println("- " + item.getNamaItem() + " x" + item.getQty() + " = Rp" + item.getSubtotal());
            if (item instanceof TokenBonus) {
                TokenBonus tb = (TokenBonus) item;
                System.out.println("[!] Promo: " + tb.getJenisPromo() + " | Ekstra Bonus: " + tb.getBonusToken() + " Token");
            }
        }
        
        System.out.println("-----------------------------------");
            System.out.println("TOTAL         : Rp" + this.hitungTotal());

        // Cek jenis pembayaran
        /*if (this instanceof TransaksiTunai) {
            TransaksiTunai tunai = (TransaksiTunai) this;
            System.out.println("BAYAR (CASH)  : Rp" + tunai.getNominalBayar());
            System.out.println("KEMBALIAN     : Rp" + tunai.hitungKembalian());
        } else if (this instanceof TransaksiQR) {
            TransaksiQR qr = (TransaksiQR) this;
            System.out.println("BAYAR (QRIS)  : BERHASIL");
            System.out.println("PROVIDER      : " + qr.getProvider());
        }*/
        System.out.print(this.getDetailPembayaran());

        
        System.out.println("-----------------------------------");
        System.out.println("Operator      : " + this.getObjOperator().getNamaOperator());
        System.out.println("Terminal      : " + this.getObjOperator().getTerminal());
        System.out.println("===================================");

        // Panggil method save
        this.simpanKeFile();
    }

    public void simpanKeFile() {
        String namaFile = "log_transaksi.txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(namaFile, true))) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String waktu = LocalDateTime.now().format(format);
            
            String metode = "";
            if (this instanceof TransaksiTunai) {
                metode = "Tunai";
            } else if (this instanceof TransaksiQR) {
                TransaksiQR qr = (TransaksiQR) this;
                metode = "QRIS-" + qr.getProvider();
            }
            
            String logData = String.format("%s,%s,%d,%s,%s,%s", 
                             this.getNoStruk(), 
                             waktu, 
                             this.hitungTotal(), 
                             metode, 
                             this.getObjOperator().getNamaOperator(),
                             this.getObjOperator().getTerminal());
            
            writer.println(logData);
            System.out.println("\n[INFO] Rekap data berhasil disimpan ke: " + namaFile);
            
        } catch (IOException e) {
            System.out.println("\n[ERROR] Gagal menyimpan log transaksi: " + e.getMessage());
        }
    }

}