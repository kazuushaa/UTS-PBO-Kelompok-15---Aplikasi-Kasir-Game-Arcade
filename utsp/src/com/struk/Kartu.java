package com.struk;
public abstract class Kartu  {
    private String namaItem;
    private int harga;
    private int qty;

    // Constructor
    public Kartu(String namaItem, int harga, int qty) {
        this.namaItem = namaItem;
        this.harga = harga;
        this.qty = qty;
    }

    // Default Constructor
    public Kartu() {
        this.namaItem = "";
        this.harga = 0;
        this.qty = 0;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }
    public String getNamaItem() {
        return namaItem;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
    public int getHarga() {
        return harga;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    public int getQty() {
        return qty;
    }

    public int getSubtotal() {
        return harga * qty;
    }
}