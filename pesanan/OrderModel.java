package pesanan;

import db.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import produk.Produk;
import produk.ProdukModel;

public class OrderModel {

    public static boolean isOrder(int id) {
    boolean found = false;
    String sql = "SELECT * FROM pesanan WHERE user = ?";

    try (Connection connection = DBHelper.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
            found = resultSet.next();  // If there is at least one result, set found to true
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return found;
}

    
   public static void insertOrder(Order order, ObservableList<Produk> produkList) {
    String insertOrderSQL = "INSERT INTO pesanan (user, subtotal) VALUES (?, ?)";
    String insertItemsSQL = "INSERT INTO item (pesanan, produk) VALUES (?, ?)";

    try (Connection connection = DBHelper.getConnection();
         PreparedStatement orderStatement = connection.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS);
         PreparedStatement itemsStatement = connection.prepareStatement(insertItemsSQL)) {

        // Insert into pesanan table
        orderStatement.setInt(1, order.getUser());
        
        orderStatement.setInt(2, order.getSubtotal());

        int affectedRows = orderStatement.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    order.setId(orderId);

                    // Insert into items table for each produk in the list
                    for (Produk produk : produkList) {
                        itemsStatement.setInt(1, orderId);
                        itemsStatement.setInt(2, produk.getId());
                        itemsStatement.addBatch();
                    }

                    // Execute batch insert for items
                    itemsStatement.executeBatch();
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


     public static ObservableList<Produk> getAllProduk(Order pesanan) {
    ObservableList<Produk> items = FXCollections.observableArrayList();
    String sql = "SELECT * FROM item WHERE pesanan = ?";
    
    try (Connection connection = DBHelper.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, pesanan.getId()); // Assuming Order class has a getId method

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Produk produk = ProdukModel.getProduk(resultSet.getInt("produk"));
                items.add(produk);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return items;
}
     
     

    
    public static ObservableList<Order> getAllOrders() {
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM pesanan";

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("user"),
                        resultSet.getString("status"),
                        resultSet.getInt("subtotal")
                );
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    
    public static ObservableList<Order> getAllOrdersPaid() {
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM pesanan WHERE status != 'PAY'";

        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("user"),
                        resultSet.getString("status"),
                        resultSet.getInt("subtotal")
                );
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
    public static void updateOrder(Order order) {
        String sql = "UPDATE pesanan SET user = ?, status = ?, subtotal = ? WHERE id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, order.getUser());
            statement.setString(2, order.getStatus());
            statement.setInt(3, order.getSubtotal());
            statement.setInt(4, order.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrder(int orderId) {
        String sql = "DELETE FROM pesanan WHERE id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            statement.executeUpdate();
            String sql1 = "DELETE FROM item WHERE pesanan = ?";
            try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql1)) {
                stmt.setInt(1, orderId);
                stmt.executeUpdate();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Order getOrder(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM pesanan WHERE id = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order(
                            resultSet.getInt("id"),
                            resultSet.getInt("user"),
                            resultSet.getString("status"),
                            resultSet.getInt("subtotal")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
    public static Order getOrderByUser(int userId) {
        Order order = null;
        String sql = "SELECT * FROM pesanan WHERE user = ?";
        try (Connection connection = DBHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order(
                            resultSet.getInt("id"),
                            resultSet.getInt("user"),
                            resultSet.getString("status"),
                            resultSet.getInt("subtotal")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}

