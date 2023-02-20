package persistence;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJpaConfigure {
	
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/persistence");
		dataSource.setUsername("phu_admin");
		dataSource.setPassword("1234");
		return dataSource;
	}
	
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("persistence.model");
		em.setPersistenceUnitName("test-persistence");
		
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		
		
		/*Phai set up property tai day!!!! -> sau do moi gan JPA Adapter vao entity mana factory bean
		 * 
		 * */
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		
		return em;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		 
		return transactionManager; 
	}



	
	/* Khong phai setting o dau -> phai setting o JPA vendor
	 * 
	 * */
	Properties additionalProperties() {
		Properties properties = new Properties();
		
		properties.setProperty("generate-ddl", "true");
		properties.setProperty("show-sql", "true");
		properties.setProperty("hibernate.ddl-auto", "create");
		
		return properties;
	}

}
