package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;


import javax.persistence.EntityManager;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = Util.getSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String SQLcreate = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` TINYINT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        session.getTransaction().begin();
        session.createNativeQuery(SQLcreate).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        session.getTransaction().begin();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.getTransaction().begin();
        session.persist(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session.getTransaction().begin();
        session.remove(session.find(User.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        session.getTransaction().begin();
        List<User> result = session.createQuery("FROM User", User.class).getResultList();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        session.getTransaction().begin();
        session.createNativeQuery("DELETE FROM users").executeUpdate();
        session.getTransaction().commit();
    }
}
