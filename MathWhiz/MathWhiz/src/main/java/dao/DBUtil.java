package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Opens JDBC connections using the settings in src/main/resources/db.properties. */
public final class DBUtil {
    private static Properties props;

    private DBUtil() { }

    private static synchronized Properties load() throws SQLException {
        if (props == null) {
            Properties p = new Properties();
            try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in == null) throw new SQLException("db.properties not found");
                p.load(in);
            } catch (IOException e) {
                throw new SQLException("Cannot read db.properties", e);
            }
            props = p;
        }
        return props;
    }

    public static Connection getConnection() throws SQLException {
        Properties p = load();
        try {
            Class.forName(p.getProperty("db.driver", "com.mysql.cj.jdbc.Driver"));
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC driver not found", e);
        }
        return DriverManager.getConnection(
                p.getProperty("db.url"),
                p.getProperty("db.user"),
                "Jahid@ict21");
    }
}
