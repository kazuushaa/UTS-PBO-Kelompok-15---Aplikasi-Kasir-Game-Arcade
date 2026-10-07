package com.struk;
import java.util.Scanner;

public class Operator {
    Scanner input = new Scanner(System.in);
    private String namaOperator;
    private String terminal;

    public Operator(String nama, String term) {
        this.namaOperator = nama;
        this.terminal = term;
    }

    public Operator() {
            System.out.print("Masukkan Nama Operator : ");
            setNamaOperator(input.nextLine());
            System.out.print("Masukkan Terminal : ");
            setTerminal(input.nextLine());
    }

    public String getNamaOperator() { 
        return namaOperator; }
    public void setNamaOperator(String nama) { 
        this.namaOperator = nama; }
    
    public String getTerminal() { 
        return terminal; }
    public void setTerminal(String term) { 
        this.terminal = term; }
}