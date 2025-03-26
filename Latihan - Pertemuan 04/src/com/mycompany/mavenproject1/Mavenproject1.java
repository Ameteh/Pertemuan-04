package com.mycompany.mavenproject1;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Mavenproject1 {
    public static void main(String[] args){
        String url = "https://drive.google.com/uc?export=download&id=14DWF2kG0hGD3hYJjAcsexOCGedVfrv3r";
        
        //Tambah ARRAY
        List<Produk> listProduk = new ArrayList<>();
        Set<String> negaraMana = new HashSet<>();
        Map<String, Integer> totalPenjualanProduk = new HashMap<>();
        Map<String, Double> penghasilanPerNegara = new HashMap<>();
        Map<String, Produk> produkSesuaiKode = new HashMap<>();

        
        //Baca LINK
        try (BufferedReader br = new BufferedReader (new InputStreamReader(new URL(url).openStream()))){
            
            String line;
            boolean firstLine = true;
                        
            while((line = br.readLine())!= null){
                if (firstLine) 
                {
                    firstLine = false;
                    continue;
                }
                
                //BUAT PISAHKAN MASING-MASING BAGIAN DI ARRAY
                // (?: ... )* menyatakan bahwa pola sepasang tanda kutip ini bisa muncul berulang kali.
                // [^\\\"]* mencocokkan 0 atau lebih karakter yang bukan tanda kutip ganda.
                // , = Menandakan bahwa pemisahan dilakukan pada karakter koma.
                // (?= ... ) Ini adalah positive lookahead. Lookahead memastikan bahwa pola di dalamnya harus terpenuhi agar kecocokan (match) terhadap koma dilakukan, tanpa mengonsumsi karakter tersebut.
                
                String[] values = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                
                // TRIM = menghilangkan semua karakter spasi yang terletak di awal (leading) dan akhir (trailing) string. Namun, spasi di dalam string (di antara kata-kata) tidak akan dihapus.
                String invoiceNo = values[0].trim();
                String kodeStok = values[1].trim();
                String deskripsi = values[2].trim();
                int kuantitas = Integer.parseInt(values[3].trim());
                String tanggal = values[4].trim();
                double hargaSatuan = Double.parseDouble(values[5].trim());
                String idPelanggan = values[6].trim();
                String negara = values[7].trim();

                //BUAT MENAMBAHKAN ENTITAS BARU KE ARRAY
                Produk produk = new Produk(invoiceNo, kodeStok, deskripsi, tanggal, hargaSatuan, kuantitas, idPelanggan, negara);
                listProduk.add(produk);
                negaraMana.add(negara);
                    
                //BUAT DAPATKAN TOTAL PENJUALAN DAN PENGHASILAN PER NEGARA DAN PENYESUAIAN SEUSAI KODE
                totalPenjualanProduk.put(kodeStok, totalPenjualanProduk.getOrDefault(kodeStok, 0) + kuantitas);
                penghasilanPerNegara.put(negara, penghasilanPerNegara.getOrDefault(negara, 0.0) + produk.getTotalPrice());
                produkSesuaiKode.put(kodeStok, produk);
            }

            // BUAT SCAN ,BEBAS LETAK DIMANA (SEBELUM INPUT)
            Scanner scanner = new Scanner(System.in);
            
            // OUTPUT
            System.out.println("Dataset berhasil dibaca! Total Produk: " + listProduk.size());
            
            System.out.println("\n=== Contoh 5 Produk ===");
            //STREAM = UNTUK MENGOPERASIKAN LIMIT DAN FOREACH, DAN OTOMATIS PENYESUAIAN BARIS
            listProduk.stream().limit(5).forEach(System.out::println);

            
            System.out.println("\n=== Daftar Negara ===");
            System.out.println(negaraMana);

            System.out.println("\n=== Total Produk Terjual berdasarkan Kode Stok ===");
            
            //Dibatasi hingga 5 saja, produk disortir berdasarkan kode stok.
            // ENTRYSET = UNTUK MENDAPATKAN E.GETKEY DAN E.GETVALUE DI DATA
            // FOREACH = KONSEP LOOP
            totalPenjualanProduk.entrySet().stream().limit(5).forEach(e -> System.out.println("Kode Stok: " + e.getKey() + " -> " + e.getValue() + " pcs"));

            System.out.println("\n=== Total Penghasilan Penjualan per Negara ===");
            penghasilanPerNegara.forEach((negara, duit) -> System.out.println("Negara: " + negara + " -> $" + duit));

            //UNTUK TEMPAT USER INPUT DAN BISA DI LOOP 
            while (true) {
                System.out.print("\nCari kode stok produk: ");
                String kodeStok = scanner.nextLine();
                if (produkSesuaiKode.containsKey(kodeStok)) {
                    System.out.println("\nProduk yang dicari: " + produkSesuaiKode.get(kodeStok)); // JIKA DITEMUKAN
                } else {
                    System.out.println("\nProduk yang ingin dicari tidak berhasil ditemukan.");    // JIKA TIDAK DITEMUKAN    
                }
                System.out.print("\nKetik 'yes' untuk mencari lagi: ");                            // JIKA SALAH INPUT KODE PRODUK YANG SUDAH SALAH
                String ulangi = scanner.nextLine().trim().toLowerCase();
                if (!ulangi.equals("yes")) {                                                        // JIKA YES LANJUT , JIKA ISI KATA-KATA LAIN AKAN END
                    break;
                }
        }
            scanner.close();
        }
        //JIKA TERJADI ERROR
        catch (IOException e){ 
                    e.printStackTrace();
                    }
        
    }              
}