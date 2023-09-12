package com.curso.praticaweb.web_mvn_praticanova_jsp.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
    private static Connection connection;
    private static String banco = "jdbc:postgresql://localhost:5432/mvn-novo-jsp";
    private static String usuario = "postgres";
    private static String password = "admin";

    static {
        conectar();
    }

    public SingleConnection() {
        conectar();
    }

    private static void conectar(){
        if (connection == null){
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(banco,usuario,password);
                connection.setAutoCommit(false);
                System.out.println("Banco conectado com sucesso");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
