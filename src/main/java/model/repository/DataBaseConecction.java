package model.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConecction {

    private static volatile DataBaseConecction instance;
    private final Connection connection;

    private DataBaseConecction() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bolsa_laboral_app",
                    "root",
                    "Aponte123_");              // <-- tu password real
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar a la BD", e);
        }
    }

    public static DataBaseConecction getInstance() {
        if (instance == null) {
            synchronized (DataBaseConecction.class) {
                if (instance == null) instance = new DataBaseConecction();
            }
        }
        return instance;
    }

    public Connection getConnection() { return connection; }
}
