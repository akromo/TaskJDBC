package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import javax.persistence.EntityManager;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private EntityManager entityManager = Util.getEntityManagerFactory().createEntityManager();

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
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(SQLcreate).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        entityManager.getTransaction().commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        entityManager.getTransaction().begin();
        entityManager.persist(new User(name, lastName, age));
        entityManager.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(User.class, id));
        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        entityManager.getTransaction().begin();
        List<User> result = entityManager.createQuery("FROM User", User.class).getResultList();
        entityManager.getTransaction().commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DELETE FROM users").executeUpdate();
        entityManager.getTransaction().commit();
    }
}
