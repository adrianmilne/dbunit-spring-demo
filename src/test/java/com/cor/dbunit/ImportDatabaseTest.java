package com.cor.dbunit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.cor.dbunit.service.XmlImportService;
import com.cor.dbunit.spring.ApplicationConfig;

@ContextConfiguration(classes = ApplicationConfig.class)
public class ImportDatabaseTest extends AbstractJUnit4SpringContextTests {

	private final static Logger LOGGER = LoggerFactory.getLogger(ImportDatabaseTest.class);

	@Autowired
    protected XmlImportService xmlImportService;
	
	@Test
	public void testExportDatabase() {

		LOGGER.info("Starting Import");
		
		xmlImportService.importData("/import.xml");

		LOGGER.info("Import Complete");
	}
}
