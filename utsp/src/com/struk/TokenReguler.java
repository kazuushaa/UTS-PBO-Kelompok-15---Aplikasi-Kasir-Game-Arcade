package com.struk;
public class TokenReguler extends Kartu {
    private int jumlahToken;

    public TokenReguler(String namaItem, int harga, int qty, int jumlahToken) {
        super(namaItem, harga, qty);
        this.jumlahToken = jumlahToken;
    }

    public TokenReguler() {
        super();
        this.jumlahToken = 0;
    }

    public void setJumlahToken(int jumlahToken) {
        this.jumlahToken = jumlahToken;
    }

    public int getJumlahToken() {
        return jumlahToken;
    }
    
    @Override
    public String getDetailItem() {
        return "Item Reguler (Tidak ada promo)";
    }
}