/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class DBHelper {
    private static final String USERNAME = "root";
    private static String PASSWORD = "";
    private static final String DB = "resto";
    private static String MYCONN = "jdbc:mysql://localhost/" +DB;

    public static Connection getConnection() {
        Connection conn = null;
        
        try {   
            conn = DriverManager.getConnection(MYCONN, USERNAME, PASSWORD);
            //System.out.println("KONEKSI BERHASIL");
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("koneksi gagal");
        }
        return conn;
    }
}
