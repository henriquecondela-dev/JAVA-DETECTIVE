package db;

import java.sql.*;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/javaDetective";
    private static final String USUARIO = "workbench";
    private static final String SENHA = "Nhampule1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}