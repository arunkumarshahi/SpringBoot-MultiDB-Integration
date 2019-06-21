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
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(basePackages = "com.example.demo", entityManagerFactoryRef = "securityEntityManagerFactory", transactionManagerRef = "securityTransactionManager")
@ComponentScan("com.example.demo")
@EntityScan("com.example.demo")  
public class SecurityDBConfig {
	@Autowired
	private Environment env;

	@Bean
	//@Primary
	@ConfigurationProperties(prefix = "datasource.security")
	public DataSourceProperties securityDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	//@Primary
	public DataSource securityDataSource() {
		DataSourceProperties securityDataSourceProperties = securityDataSourceProperties();
		return DataSourceBuilder.create().driverClassName(securityDataSourceProperties.getDriverClassName())
				.url(securityDataSourceProperties.getUrl()).username(securityDataSourceProperties.getUsername())
				.password(securityDataSourceProperties.getPassword()).build();
	}

	@Bean
	public PlatformTransactionManager securityTransactionManager() {
		EntityManagerFactory factory = securityEntityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean
	
	public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(securityDataSource());
		factory.setPackagesToScan("com.example.demo");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("hibernate.show-sql"));
		factory.setJpaProperties(jpaProperties);
		return factory;
	}

	@Bean
	public DataSourceInitializer securityDataSourceInitializer() {
		DataSourceInitializer dsInitializer = new DataSourceInitializer();
		dsInitializer.setDataSource(securityDataSource());
		ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator();
		dbPopulator.addScript(new ClassPathResource("security-data.sql"));
		dsInitializer.setDatabasePopulator(dbPopulator);
		dsInitializer.setEnabled(env.getProperty("datasource.security.initialize", Boolean.class, false));
		return dsInitializer;
	}
}