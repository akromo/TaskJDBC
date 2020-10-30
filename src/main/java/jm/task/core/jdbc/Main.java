package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;


public class Main {
    public static void main(String[] args) {
        UserDao sh = new UserDaoJDBCImpl();
        sh.createUsersTable();
        sh.saveUser("Vasya", "Ivanov", (byte) 30);
        sh.saveUser("Ivan", "Vasilev", (byte) 40);
        sh.saveUser("Petr", "TheFirst", (byte) 127);
        sh.saveUser("Java", "Mentor", (byte) -1);
        sh.getAllUsers().stream().forEach(System.out::println);
        sh.cleanUsersTable();
        sh.dropUsersTable();
    }
}
