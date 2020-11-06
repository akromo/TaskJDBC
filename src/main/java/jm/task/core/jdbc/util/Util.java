package jm.task.core.jdbc.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


public class Util {


    private static final EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
            archiverPersistenceUnitInfo(),
            new HashMap<String, Object>() {{
                put("JPA_JDBC_DRIVER", "org.gjt.mm.mysql.Driver");
                put("JPA_JDBC_URL", "jdbc:mysql://localhost:3306/jdbc_f?useSSL=false"); //jdbc_f?useSSL=false
                put("DIALECT", MySQLDialect.class);
                put("SHOW_SQL", true);
                put("QUERY_STARTUP_CHECKING", false);
                put("GENERATE_STATISTICS", false);
                put("USE_REFLECTION_OPTIMIZER", false);
                put("USE_SECOND_LEVEL_CACHE", false);
                put("USE_QUERY_CACHE", false);
                put("USE_STRUCTURED_CACHE", false);
                put("STATEMENT_BATCH_SIZE", 20);
            }}
    );


    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    //    public static final SessionFactory sessionFactory;

//    private static DataSource dataSource() {
//        final MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setDatabaseName("jdbc_f");
//        dataSource.setUser("root");
//        dataSource.setPassword("password");
//        return dataSource;
//    }

//    private static Properties hibernateProperties() {
//        final Properties properties = new Properties();
//
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.show_sql", true);
//        properties.put("hibernate.connection.driver_class", "org.gjt.mm.mysql.Driver");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        properties.put("hibernate.connection.datasource", dataSource());
//
//        return properties;
//    }


//    static {
//        try {
//            sessionFactory = new Configuration().addProperties(hibernateProperties()).configure().buildSessionFactory();
//        }catch (HibernateException e){
//            System.out.println("new Configuration().configure().buildSessionFactory() throws "+e);
//            throw e;
//        }
//
//    }
//
//    public static SessionFactory getSessionFactory(){
//        return  sessionFactory;
//    }

    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbc_f?useSSL=false";
        String user = "root";
        String passwd = "password";
        return DriverManager.getConnection(url, user, passwd);
    }

//    public static void build() {
//        emf = new org.hibernate.jpa.HibernatePersistenceProvider().createContainerEntityManagerFactory(
//                archiverPersistenceUnitInfo(),
//                new HashMap<String, Object>() {{
//                    put("JPA_JDBC_DRIVER", "org.gjt.mm.mysql.Driver");
//                    put("JPA_JDBC_URL", "jdbc:mysql://localhost:3306/jdbc_f?useSSL=false"); //?useSSL=false
//                    put("DIALECT", MySQLDialect.class);
//                    put("SHOW_SQL", true);
//                    put("QUERY_STARTUP_CHECKING", false);
//                    put("GENERATE_STATISTICS", false);
//                    put("USE_REFLECTION_OPTIMIZER", false);
//                    put("USE_SECOND_LEVEL_CACHE", false);
//                    put("USE_QUERY_CACHE", false);
//                    put("USE_STRUCTURED_CACHE", false);
//                    put("STATEMENT_BATCH_SIZE", 20);
//                }}
//        );
//    }

    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "MyPersistenceUnit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return entityClassNames();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return hibernateProperties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

    private static Properties hibernateProperties() {
        final Properties properties = new Properties();

        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.connection.driver_class", "org.gjt.mm.mysql.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.connection.datasource", dataSource());

        return properties;
    }

    private static DataSource dataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("jdbc_f");
        dataSource.setUser("root");
        dataSource.setPassword("password");
        return dataSource;
    }

    private static List<String> entityClassNames() {
        Class<?>[] entities = new Class<?>[]{
                User.class
        };
        return Arrays.stream(entities).map(Class::getName).collect(Collectors.toList());
    }
}
// реализуйте настройку соеденения с БД
