
package user;
import db.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel {

    public static String getNama(int id) {
    String nama = null;
    Connection conn = DBHelper.getConnection();
    String query = "SELECT username FROM users WHERE id = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            nama = rs.getString("username");
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    return nama;
}

    
    public static User getUser(String username, String password) {
        User user = null;
        Connection conn = DBHelper.getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");
                // Note: In a real-world scenario, you should hash passwords.
                user = new User(userId, dbUsername, dbPassword,role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return user;
    }
     public static boolean registerUser(String username, String password) {
        Connection conn = DBHelper.getConnection();
        String query = "INSERT INTO users (username, password,role) VALUES (?, ?,'user')";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
