package com.cor.dbunit;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.cor.dbunit.service.XmlExportService;
import com.cor.dbunit.spring.ApplicationConfig;

@ContextConfiguration(classes = ApplicationConfig.class)
public class ExportTableTest extends AbstractJUnit4SpringContextTests {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportTableTest.class);

	private static final List<String> TABLES = Arrays.asList("SCHEMA.TABLE1", "SCHEMA.TABLE2");

	@Autowired
    protected XmlExportService xmlExportService;
	
	@Test
	public void testExportTable() {

		LOGGER.info("Starting Export");

		xmlExportService.exportTables(TABLES, "partial.xml");

		LOGGER.info("Export Complete");
	}
}
