package com.cor.dbunit.spring;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cor.dbunit.service.XmlExportService;
import com.cor.dbunit.service.XmlImportService;
import com.google.common.base.Preconditions;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:database.properties"})
public class ApplicationConfig {

	@Autowired
	private Environment env;

	@Bean
	public XmlExportService xmlExportService() {
		return new XmlExportService(databaseConnection());
	}

	@Bean
	public XmlImportService xmlImportService() {
		return new XmlImportService(databaseConnection());
	}

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
		dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
		dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
		dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));
		return dataSource;
	}

	@Bean
	public IDatabaseConnection databaseConnection() {
		try {
			IDatabaseConnection connection = new DatabaseConnection(dataSource().getConnection());
			connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to get connection to database - check your db.properties file is configured.");
		}
	}
}
