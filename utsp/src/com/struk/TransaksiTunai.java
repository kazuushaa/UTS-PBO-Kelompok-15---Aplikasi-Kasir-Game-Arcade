package com.struk;
import java.util.Scanner;

public class TransaksiTunai extends Transaksi {
    Scanner input = new Scanner(System.in);
    private int nominalBayar;
    private int kembalian;

    public TransaksiTunai(String no, int bayar) {
        super(no);
        this.nominalBayar = bayar;
    }

    public TransaksiTunai() {
            System.out.print("Masukan Nomor Transaksi : ");
            super.setNoStruk(input.nextLine());
            this.nominalBayar = 0;
    }


    public int getNominalBayar() { 
        return nominalBayar; }
    public void setNominalBayar(int b) { 
        this.nominalBayar = b; }

    public int getKembalian() { 
        return kembalian; }
    public void setKembalian(int k) { 
        this.kembalian = k; }
        
    public int hitungKembalian() {
        this.kembalian = this.nominalBayar - hitungTotal();
        return this.kembalian;
    }
    
    @Override
    public String getDetailPembayaran() {
        return "BAYAR (CASH)  : Rp" + this.nominalBayar + "\n" +
               "KEMBALIAN     : Rp" + this.hitungKembalian() + "\n";
    }
}