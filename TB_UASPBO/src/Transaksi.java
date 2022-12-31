import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Transaksi extends Minuman {

    static Connection conn;

    public Transaksi() {
    }

    @Override
    public void SubTotal() {
        SubTotal = HargaMinuman * Jumlah;
    }

    @Override
    public void Discount() {
        if (SubTotal >= 15000 & SubTotal < 50000) {
            Discount = SubTotal * 5 / 100;
        }

        else if (SubTotal >= 50000) {
            Discount = SubTotal * 10 / 100;
        }

        else {
            Discount = 0;
        }
    }

    @Override
    public void TotalHarga() {
        TotalHarga = SubTotal - Discount;
    }

    static void tanggal() {
        Date Date = new Date();
        SimpleDateFormat tgl = new SimpleDateFormat("dd MMM yyyy");
        System.out.println("Tanggal             = " + tgl.format(Date));
    }

    static void waktu() {
        Date Time = new Date();
        SimpleDateFormat tm = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Waktu               = " + tm.format(Time));
    }

    static void View() {
        System.out.println("_____________________________________________");
        System.out.println("              PEMBELIAN MINUMAN");
        System.out.println("---------------------------------------------");
        tanggal();
        waktu();
        System.out.println("No Faktur           = " + NoFaktur);
        System.out.println("---------------------------------------------");
        System.out.println("Nama Minuman        = " + NamaMinuman);
        System.out.println("Harga Minuman       = " + HargaMinuman);
        System.out.println("Jumlah Minuman      = " + Jumlah);
        System.out.println("Total Pembelian     = " + SubTotal);
        System.out.println("Discount            = " + Discount);
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("Total Pembayaran    = " + TotalHarga);
    }

    static void ViewData() throws SQLException {
        String text1 = "\nHasil Transaksi Milk Square";
        System.out.println(text1.toUpperCase());

        String url = "jdbc:mysql://localhost:3306/milk_square";
        conn = DriverManager.getConnection(url, "root", "");

        String sql = "SELECT * FROM penjualan";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {
            System.out.print("\nNomor Faktur\t: ");
            System.out.print(result.getString("Nomor_Faktur"));
            System.out.print("\nNama Minuman\t: ");
            System.out.print(result.getString("Nama_Minuman"));
            System.out.print("\nHarga Minuman\t: ");
            System.out.print(result.getString("Harga_Minuman"));
            System.out.print("\nJumlah Barang\t: ");
            System.out.print(result.getString("Jumlah"));
            System.out.print("\nSub Total\t: ");
            System.out.print(result.getString("Sub_Total"));
            System.out.print("\nDiskon\t\t: ");
            System.out.print(result.getString("Diskon"));
            System.out.print("\nTotal Harga\t: ");
            System.out.print(result.getString("Total_Biaya"));
            System.out.print("\n");
        }
    }

    static void AddData() throws SQLException {
        String text2 = "\n===Tambah Data Penjualan===";
        System.out.println(text2.toUpperCase());

        String url = "jdbc:mysql://localhost:3306/milk_square";
        conn = DriverManager.getConnection(url, "root", "");

        Scanner terimaInput = new Scanner(System.in);

        Transaksi transaksi = new Transaksi();
        try {

            transaksi.NoFaktur();
            transaksi.NamaMinuman();
            transaksi.HargaMinuman();
            transaksi.Jumlah();
            transaksi.SubTotal();
            transaksi.Discount();
            transaksi.TotalHarga();

            String sql = "INSERT INTO penjualan (Nomor_Faktur, Nama_Minuman, Harga_Minuman, Jumlah, Sub_Total, Diskon, Total_Biaya) VALUES ('"
                    + NoFaktur + "','" + NamaMinuman + "','" + HargaMinuman + "','" + Jumlah + "','" + SubTotal + "','"
                    + Discount + "','" + TotalHarga + "')";

            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Berhasil input data");
            View();

        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan input data");
        } catch (InputMismatchException e) {
            System.err.println("Inputlah dengan angka saja");
        }
    }

    static void ChangeData() throws SQLException {
        String text3 = "\n===Ubah Data Transaksi Milk Square===";
        System.out.println(text3.toUpperCase());

        String url = "jdbc:mysql://localhost:3306/milk_square";
        conn = DriverManager.getConnection(url, "root", "");

        Scanner terimaInput = new Scanner(System.in);
        Transaksi transaksi = new Transaksi();

        try {
            ViewData();
            System.out.print("Masukkan Nomor Faktur yang akan dirubah : ");
            transaksi.NoFaktur = (terimaInput.nextLine());

            String sql = "SELECT * FROM penjualan WHERE Nomor_Faktur = " + transaksi.NoFaktur;

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {

                System.out.print("Nama Barang [" + result.getString("Nama_Minuman") + "]\t: ");
                transaksi.NamaMinuman = terimaInput.nextLine();

                System.out.print("Harga Barang [" + result.getString("Harga_Minuman") + "]\t: ");
                transaksi.HargaMinuman = terimaInput.nextInt();

                System.out.print("Jumlah Barang [" + result.getString("Jumlah") + "]\t: ");
                transaksi.Jumlah = terimaInput.nextInt();

                transaksi.SubTotal();
                transaksi.Discount();
                transaksi.TotalHarga();

                sql = "UPDATE penjualan SET Nama_Minuman='" + transaksi.NamaMinuman + "',Harga_Minuman= '"
                        + transaksi.HargaMinuman + "',Jumlah= '" + transaksi.Jumlah + "',Sub_Total= '"
                        + transaksi.SubTotal + "',Diskon= '" + transaksi.Discount + "',Total_Biaya= '"
                        + transaksi.TotalHarga + "' WHERE Nomor_Faktur='" + transaksi.NoFaktur + "'";

                if (statement.executeUpdate(sql) > 0) {
                    System.out
                            .println("Berhasil memperbaharui data Penjualan (Nomor_Faktur " + transaksi.NoFaktur + ")");
                }
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
    }

    static void DeleteData() throws SQLException {
        String text4 = "\n===Hapus Data Penjualan===";
        System.out.println(text4.toUpperCase());

        String url = "jdbc:mysql://localhost:3306/milk_square";
        conn = DriverManager.getConnection(url, "root", "");

        Scanner terimaInput = new Scanner(System.in);
        try {
            ViewData();
            System.out.print("\nKetik Nomor Faktur yang akan Anda Hapus : ");
            Integer NoFaktur = Integer.parseInt(terimaInput.nextLine());

            String sql = "DELETE FROM penjualan WHERE Nomor_Faktur = " + NoFaktur;
            Statement statement = conn.createStatement();
            // ResultSet result = statement.executeQuery(sql);

            if (statement.executeUpdate(sql) > 0) {
                System.out.println("Berhasil menghapus data Transaksi (Nomor Faktur	 " + NoFaktur + ")");
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan dalam menghapus data transaksi");
        }

    }

}