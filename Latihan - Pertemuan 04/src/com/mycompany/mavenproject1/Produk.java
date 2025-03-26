package com.mycompany.mavenproject1;

//PRIVATE UNTUK DATA TIDAK GAMPANG DIUBAH
public class Produk {  
    private String invoiceNo;
    private String kodeStok;
    private String deskripsi;
    private int kuantitas;
    private String tanggal;
    private static double hargaSatuan;
    private String idPelanggan;
    private String negara;

    public Produk(String invoiceNo, String kodeStok, String deskripsi, String tanggal, double hargaSatuan, int kuantitas, String idPelanggan, String negara) {
        this.invoiceNo = invoiceNo;
        this.kodeStok = kodeStok;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.hargaSatuan = hargaSatuan;
        this.kuantitas = kuantitas;
        this.idPelanggan = idPelanggan;
        this.negara = negara;
    }
    
    public String getStockCode() {
        return kodeStok;
    }

    public int getQuantity() {
        return kuantitas;
    }

    public static double getUnitPrice() {
        return hargaSatuan;
    }

    public String getCountry() {
        return negara;
    }
    
    public double getTotalPrice() {
        return hargaSatuan * kuantitas;
    }
    
    //SEBAGAI TAMPILAN OUTPUT YANG DI RETURN KAN
    public String toString() {
        return invoiceNo + " - " + kodeStok + " - " + deskripsi + " - " + tanggal + " - " + kuantitas + " pcs - $" + hargaSatuan + " - " + idPelanggan + " - "+ negara;
    }
}