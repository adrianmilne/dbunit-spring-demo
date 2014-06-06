package com.cor.dbunit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.cor.dbunit.service.XmlExportService;
import com.cor.dbunit.spring.ApplicationConfig;

@ContextConfiguration(classes = ApplicationConfig.class)
public class ExportDependentTableTest extends AbstractJUnit4SpringContextTests {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportDependentTableTest.class);

	private static final String TABLE = "SCHEMA.TABLE1";
	
	@Autowired
    protected XmlExportService xmlExportService;
	
	@Test
	public void testExportTable() {

		LOGGER.info("Starting Export");

		xmlExportService.exportTableWithDependencies(TABLE, "dependents.xml");

		LOGGER.info("Export Complete");
	}
}
