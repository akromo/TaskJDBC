package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static Connection getNewConnection() throws SQLException {
        //String url = "jdbc:mysql:localhost:3306:jdbc_f";
        String url = "jdbc:mysql://localhost:3306/jdbc_f?useSSL=false";
        String user = "root";
        String passwd = "password";
        return DriverManager.getConnection(url, user, passwd);
    }
    // реализуйте настройку соеденения с БД
}
