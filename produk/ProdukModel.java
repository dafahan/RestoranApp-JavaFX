package produk;

import db.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdukModel {

    public static void insertProduk(Produk produk) {
        String sql = "INSERT INTO produk (nama, harga, image) VALUES (?, ?, ?)";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, produk.getNama());
            statement.setDouble(2, produk.getHarga());
            statement.setString(3, produk.getImage());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idProduk = generatedKeys.getInt(1);
                        produk.setId(idProduk);
                    } else {
                        throw new SQLException("Creating product failed, no ID obtained.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Produk> getAllProduk() {
        ObservableList<Produk> produkList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM produk";

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Produk produk = new Produk(
                        resultSet.getInt("id"),
                        resultSet.getString("nama"),
                        resultSet.getInt("harga"),
                        resultSet.getString("image")
                );
                produkList.add(produk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produkList;
    }

    public static void updateProduk(Produk produk) {
        String sql = "UPDATE produk SET nama = ?, harga = ?, image = ? WHERE id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produk.getNama());
            statement.setInt(2, produk.getHarga());
            statement.setString(3, produk.getImage());
            statement.setInt(4, produk.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduk(int produkId) {
        String sql = "DELETE FROM produk WHERE id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, produkId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Produk getProduk(int produkId) {
        Produk produk = null;
        String sql = "SELECT * FROM produk WHERE id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, produkId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                     produk = new Produk(
                        resultSet.getInt("id"),
                        resultSet.getString("nama"),
                        resultSet.getInt("harga"),
                        resultSet.getString("image")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produk;
    }

    public static Produk getProdukByName(String produkName) {
        Produk produk = null;
        String sql = "SELECT * FROM produk WHERE nama = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produkName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                     produk = new Produk(
                        resultSet.getInt("id"),
                        resultSet.getString("nama"),
                        resultSet.getInt("harga"),
                        resultSet.getString("image")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produk;
    }
}

