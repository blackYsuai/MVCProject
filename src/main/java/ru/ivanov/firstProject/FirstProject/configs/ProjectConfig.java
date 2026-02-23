package ru.ivanov.firstProject.FirstProject.configs;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ProjectConfig {

    private final Environment environment;

    @Autowired
    public ProjectConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("datasource.url"));
        dataSource.setUsername(environment.getProperty("datasource.username"));
        dataSource.setPassword(environment.getProperty("datasource.password"));
        return dataSource;
    }

//    @Bean
//    public LocalSessionFactoryBean localSessionFactoryBean(){
//        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
//        localSessionFactoryBean.setDataSource(dataSource());
//        localSessionFactoryBean.setPackagesToScan("ru.ivanov.firstProject.FirstProject.models");
//        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
//        return localSessionFactoryBean;
//    }
//
//    private Properties hibernateProperties(){
//        Properties properties = new Properties();
//        properties.put("datasource.hibernate.dialect", environment.getRequiredProperty("datasource.hibernate.dialect"));
//        return properties;
//    }
//
//    @Bean
//    public HibernateTransactionManager hibernateTransactionManager(SessionFactory localSessionFactoryBean){
//        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
//        hibernateTransactionManager.setSessionFactory(localSessionFactoryBean);
//        return hibernateTransactionManager;
//    }
}
