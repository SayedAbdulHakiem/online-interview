package model;

import java.sql.*;

public class DbConnection {

    Connection con = null;
    String url = "jdbc:mysql://127.0.0.1:330/online_interview";
    String user = "root";
    String password = "root";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException cnfe) {
            System.err.println("Exception: " + cnfe);
        }

        return con;
    }

}
