package com.asu.seatr.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
	public static void SetConnection() {
		 
        Connection conn = null;
        try {
            String url = "jdbc:mysql://52.33.148.209/ope_global";
            String user = "user";
            String password = "user";
 
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database test1");
            }
 
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
    }
}