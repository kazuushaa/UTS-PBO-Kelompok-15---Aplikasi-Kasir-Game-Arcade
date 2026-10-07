package com.struk;
import java.util.Scanner;

public class TransaksiQR extends Transaksi {
    Scanner input = new Scanner(System.in);
    private String provider;
    private String remarks;

    public TransaksiQR(String no, String prov, String rem) {
        super(no);
        this.provider = prov;
        this.remarks = rem;
    }

    public TransaksiQR() {
            System.out.print("Masukan Nomor Transaksi : ");
            super.setNoStruk(input.nextLine());
            System.out.print("Masukan Metode (Transfer/QRIS) : ");
            setProvider(input.nextLine());
            System.out.print("Masukan Remarks : ");
            setRemarks(input.nextLine());

    }

    public String getProvider() { 
        return provider; }
    public void setProvider(String prov) { 
        this.provider = prov; }

    public String getRemarks() { 
        return remarks; }
    public void setRemarks(String rem) { 
        this.remarks = rem; }

    @Override
    public String getDetailPembayaran() {
        return "BAYAR         : BERHASIL\n" +
               "PROVIDER      : " + this.provider + "\n";
    }
}