package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingCart {

    private static final String URL = "jdbc:mysql://localhost/shopping_cart";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addItemToCart(int itemId, int quantity) throws SQLException {
        String selectItemQuery = "SELECT item_name, item_price, item_qty FROM items WHERE item_id = ?";
        String selectCartQuery = "SELECT item_qty FROM cart WHERE item_id = ?";
        String insertCartQuery = "INSERT INTO cart (item_id, item_name, item_qty, item_price) VALUES (?, ?, ?, ?)";
        String updateCartQuery = "UPDATE cart SET item_qty = item_qty + ? WHERE item_id = ?";
        String updateStockQuery = "UPDATE items SET item_qty = item_qty - ? WHERE item_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement selectItemStmt = conn.prepareStatement(selectItemQuery);
             PreparedStatement selectCartStmt = conn.prepareStatement(selectCartQuery);
             PreparedStatement insertCartStmt = conn.prepareStatement(insertCartQuery);
             PreparedStatement updateCartStmt = conn.prepareStatement(updateCartQuery);
             PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery)) {

            conn.setAutoCommit(false);

            selectItemStmt.setInt(1, itemId);
            ResultSet itemRs = selectItemStmt.executeQuery();
            if (itemRs.next()) {
                String itemName = itemRs.getString("item_name");
                double itemPrice = itemRs.getDouble("item_price");
                int itemStock = itemRs.getInt("item_qty");

                selectCartStmt.setInt(1, itemId);
                ResultSet cartRs = selectCartStmt.executeQuery();
                if (cartRs.next()) {
                    updateCartStmt.setInt(1, quantity);
                    updateCartStmt.setInt(2, itemId);
                    updateCartStmt.executeUpdate();
                } else {
                    insertCartStmt.setInt(1, itemId);
                    insertCartStmt.setString(2, itemName);
                    insertCartStmt.setInt(3, quantity);
                    insertCartStmt.setDouble(4, itemPrice);
                    insertCartStmt.executeUpdate();
                }

                updateStockStmt.setInt(1, quantity);
                updateStockStmt.setInt(2, itemId);
                updateStockStmt.executeUpdate();

                conn.commit();
                System.out.println("Item added to cart.");
            } else {
                System.out.println("Item not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewCart() throws SQLException {
        String query = "SELECT * FROM cart";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("item_qty");
                double itemPrice = rs.getDouble("item_price");
                System.out.println("Item ID: " + itemId + ", Name: " + itemName +
                        ", Price: " + itemPrice + ", Quantity: " + quantity);
            }
        }
    }

    public void removeItemFromCart(int itemId) throws SQLException {
        String selectCartQuery = "SELECT item_qty FROM cart WHERE item_id = ?";
        String deleteCartQuery = "DELETE FROM cart WHERE item_id = ?";
        String updateCartQuery = "UPDATE cart SET item_qty = item_qty - 1 WHERE item_id = ?";
        String updateStockQuery = "UPDATE items SET item_qty = item_qty + 1 WHERE item_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement selectCartStmt = conn.prepareStatement(selectCartQuery);
             PreparedStatement deleteCartStmt = conn.prepareStatement(deleteCartQuery);
             PreparedStatement updateCartStmt = conn.prepareStatement(updateCartQuery);
             PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery)) {

            conn.setAutoCommit(false);

            selectCartStmt.setInt(1, itemId);
            ResultSet cartRs = selectCartStmt.executeQuery();
            if (cartRs.next()) {
                int currentCartQuantity = cartRs.getInt("item_qty");

                if (currentCartQuantity > 1) {
                    updateCartStmt.setInt(1, itemId);
                    updateCartStmt.executeUpdate();
                } else {
                    deleteCartStmt.setInt(1, itemId);
                    deleteCartStmt.executeUpdate();
                }

                updateStockStmt.setInt(1, itemId);
                updateStockStmt.executeUpdate();

                conn.commit();
            } else {
                System.out.println("Item not found in cart.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTotalPrice() throws SQLException {
        String query = "SELECT SUM(item_price * item_qty) AS total_price FROM cart";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                double totalPrice = rs.getDouble("total_price");
                System.out.println("Total Price: " + totalPrice);
            }
        }
    }

    public void viewItems() throws SQLException {
        String query = "SELECT * FROM items";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("item_qty");
                double itemPrice = rs.getDouble("item_price");
                System.out.println("Item ID: " + itemId + ", Name: " + itemName +
                        ", Price: " + itemPrice + ", Quantity: " + quantity);
            }
        }
    }
}