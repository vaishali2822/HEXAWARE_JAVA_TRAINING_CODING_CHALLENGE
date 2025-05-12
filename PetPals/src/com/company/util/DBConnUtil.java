package com.company.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                DBPropertyUtil.get("db.url"),
                DBPropertyUtil.get("db.username"),
                DBPropertyUtil.get("db.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
