import java.util.Scanner;

public class Minuman implements Penjualan {
    public static String NoFaktur;
    public static String NamaMinuman;
    public static Integer HargaMinuman;
    public static Integer Jumlah;
    public static Integer SubTotal;
    public static Integer Discount;
    public static Integer TotalHarga;

    Scanner input = new Scanner(System.in);

    public Minuman() {
    }

    @Override
    public void NoFaktur() {
        System.out.print("No Faktur\t= ");
        NoFaktur = input.nextLine();
    }

    @Override
    public void NamaMinuman() {
        System.out.print("Nama Minuman\t= ");
        NamaMinuman = input.nextLine();
    }

    @Override
    public void HargaMinuman() {
        System.out.print("Harga Minuman\t= ");
        HargaMinuman = input.nextInt();
    }

    @Override
    public void Jumlah() {
        System.out.print("Jumlah Minuman\t= ");
        Jumlah = input.nextInt();
    }

    public void SubTotal() {
        SubTotal = null;
    }

    public void Discount() {
        Discount = null;
    }

    public void TotalHarga() {
        TotalHarga = null;
    }
}