package com.ipg.assessment.persistence;

import java.sql.SQLException;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class PersistenceConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier("dataSource") final DataSource dataSource) throws SQLException {
		
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[] { "com.ipg.assessment.entities" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		@SuppressWarnings("unchecked")
		Map<String,String> jpaProperties = (Map<String,String>)vendorAdapter.getJpaPropertyMap();
		jpaProperties.put("hibernate.current_session_context_class", "org.springframework.orm.hibernate4.SpringSessionContext");
		jpaProperties.put("hibernate.show-sql", "true");
		jpaProperties.put("hibernate.ddl-auto", "create-drop");
		jpaProperties.put("hibernate.connection.provider_disables_autocommit", "true");
		//jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		jpaProperties.put("hibernate.connection.autocommit", "false");
		em.setJpaPropertyMap(jpaProperties);

		return em;
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL)
								.addScript("db/hsql/schema.sql")
								.addScript("db/hsql/service_pool_insert.sql")
								.build();

		return db;
	}
	
	@Bean
	public org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean hibernateJpaSessionFactoryBean(@Qualifier("entityManagerFactory") final EntityManagerFactory entityManagerFactory) {
		org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean factoryBean = new org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean();
		factoryBean.setEntityManagerFactory(entityManagerFactory);
		
		return factoryBean;
	}
	
	@Bean
	public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") final HibernateEntityManagerFactory hemf){
	    return hemf.getSessionFactory();  
	}
}
