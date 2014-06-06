package com.cor.dbunit.service;

import javax.inject.Inject;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.stereotype.Service;

@Service
public class XmlImportService {

	final IDatabaseConnection databaseConnection;

	@Inject
	public XmlImportService(IDatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	/**
	 * Import data from the defined file.
	 * @param filePath Xml file to import
	 */
	public void importData(String filePath) {

		try {
			IDataSet fileDataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(getClass().getResource(filePath));
			DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, fileDataSet);
			databaseConnection.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
