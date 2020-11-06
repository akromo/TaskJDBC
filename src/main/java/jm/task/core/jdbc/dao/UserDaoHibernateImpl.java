package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import javax.persistence.EntityManager;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

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
        EntityManager entityManager = Util.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(SQLcreate).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void dropUsersTable() {
        EntityManager entityManager = Util.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        EntityManager entityManager = Util.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new User(name, lastName, age));
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.printf("User с именем – %s добавлен в базу данных%n", name);
    }

    @Override
    public void removeUserById(long id) {
        EntityManager entityManager = Util.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(User.class, id));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = Util.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        List<User> result = entityManager.createQuery("FROM User", User.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        EntityManager entityManager = Util.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DELETE FROM users").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
