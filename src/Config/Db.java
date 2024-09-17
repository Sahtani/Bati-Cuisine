package Config;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class Db {

    private static Db instance;
    private Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Bati-Cuisine";
    private static final String USER = "postgres";
    private static final String PASS = "soumia";

    private Db() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  Db getInstance() {
        if (instance == null) {
            instance = new Db();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
