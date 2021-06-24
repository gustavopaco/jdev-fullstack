package com.curso.praticaweb.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
    private static Connection connection;
    private static String banco = "jdbc:postgresql://localhost:5432/mvn-ajax-jsp";
    private static String usuario = "postgres";
    private static String password = "admin";

    static {
        connectar();
    }

    public SingleConnection() {
        connectar();
    }

    private static void connectar(){
            if (connection == null) {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(banco, usuario, password);
                    connection.setAutoCommit(false);
                    System.out.println("Banco Conectado com sucesso.");
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }

    }

    public static Connection getConnection(){
        return connection;
    }
}
