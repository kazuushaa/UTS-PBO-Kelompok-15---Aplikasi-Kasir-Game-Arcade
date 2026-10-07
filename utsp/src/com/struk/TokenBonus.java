package com.struk;

public class TokenBonus extends Kartu {
    private String jenisPromo;
    private int bonusToken;

    public TokenBonus(String namaItem, int harga, int qty, String jenisPromo, int bonusToken) {
        super(namaItem, harga, qty);
        this.jenisPromo = jenisPromo;
        this.bonusToken = bonusToken;
    }

    public TokenBonus() {
        super();
        this.jenisPromo = "";
        this.bonusToken = 0;
    }

    public void setJenisPromo(String jenisPromo) {
        this.jenisPromo = jenisPromo;
    }

    public String getJenisPromo() {
        return jenisPromo;
    }

    public void setBonusToken(int bonusToken) {
        this.bonusToken = bonusToken;
    }

    public int getBonusToken() {
        return bonusToken;
    }

    @Override
    public String getDetailItem() {
        return "Promo Diterapkan: " + this.jenisPromo;
    }
}