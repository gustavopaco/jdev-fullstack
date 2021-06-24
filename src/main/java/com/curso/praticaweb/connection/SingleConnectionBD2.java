package com.curso.praticaweb.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBD2 {
    private static Connection connection;
    private static String banco = "jdbc:postgresql://localhost:5432/conexaojdbc";
    private static String usuario = "postgres";
    private static String password = "admin";

    static {
        connectar();
    }

    public SingleConnectionBD2() {
        connectar();
    }

    private static void connectar(){
            if (connection == null) {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(banco, usuario, password);
                    connection.setAutoCommit(false);
                    System.out.println("Banco ConexaoJDBC Conectado com sucesso.");
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }

    }

    public static Connection getConnection(){
        return connection;
    }
}
