package com.example.demo.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.orders.repositories", entityManagerFactoryRef = "ordersEntityManagerFactory", transactionManagerRef = "ordersTransactionManager")
@ComponentScan("com.example.demo")
@EntityScan("com.example.demo")  
public class OrdersDBConfig {
	@Autowired
	private Environment env;

	@Bean
	@ConfigurationProperties(prefix = "datasource.orders")
	public DataSourceProperties ordersDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource ordersDataSource() {
		DataSourceProperties primaryDataSourceProperties = ordersDataSourceProperties();
		return DataSourceBuilder.create().driverClassName(primaryDataSourceProperties.getDriverClassName())
				.url(primaryDataSourceProperties.getUrl()).username(primaryDataSourceProperties.getUsername())
				.password(primaryDataSourceProperties.getPassword()).build();
	}

	@Bean
	public PlatformTransactionManager ordersTransactionManager() {
		EntityManagerFactory factory = ordersEntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean ordersEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(ordersDataSource());
		factory.setPackagesToScan("com.example.demo");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("hibernate.show-sql"));
		factory.setJpaProperties(jpaProperties);
		return factory;
	}

	@Bean

	public DataSourceInitializer ordersDataSourceInitializer() {
		DataSourceInitializer dsInitializer = new DataSourceInitializer();
		dsInitializer.setDataSource(ordersDataSource());
		ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator();
		dbPopulator.addScript(new ClassPathResource("orders-data.sql"));
		dsInitializer.setDatabasePopulator(dbPopulator);
		dsInitializer.setEnabled(env.getProperty("datasource.orders.initialize", Boolean.class, false));
		return dsInitializer;
	}
}