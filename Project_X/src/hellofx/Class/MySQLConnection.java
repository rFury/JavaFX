package hellofx.Class;

import java.sql.*;

public class MySQLConnection {
    public static void main(String[] args) {
        

    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
            return null;
        }
        // Connection parameters
        String url = "jdbc:mysql://localhost:3306/FX";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

}
