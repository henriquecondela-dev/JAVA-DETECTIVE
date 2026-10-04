package db;

import java.sql.*;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/javaDetective";
    private static final String USUARIO = "root";
    private static final String SENHA = "Hcgj5656%^";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}