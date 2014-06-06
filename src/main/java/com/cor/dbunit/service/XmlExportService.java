package com.cor.dbunit.service;

import java.io.FileOutputStream;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.stereotype.Service;

@Service
public class XmlExportService {

	/** Injected by DataSourceConfig */
	final IDatabaseConnection databaseConnection;

	@Inject
	public XmlExportService(IDatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	/**
	 * Export All Tables.
	 * @param fileName Name of XML export file
	 */
	public void exportAllTables(String fileName) {
		try {
			// full database export
			IDataSet fullDataSet = databaseConnection.createDataSet();
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Export only names tables. 
	 * @param tableNames Name of tables to export
	 * @param fileName Name of XML export file
	 */
	public void exportTables(List<String> tableNames, String fileName) {

		try {
			QueryDataSet partialDataSet = new QueryDataSet(databaseConnection);
			for (String tableName : tableNames) {
				// partialDataSet.addTable("FOO", "SELECT * FROM TABLE WHERE COL='VALUE'");
				partialDataSet.addTable(tableName);
			}
			FlatXmlDataSet.write(partialDataSet, new FileOutputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Export the specified table with all its dependencies.
	 * @param tableName Name of table (and dependencies) to export
	 * @param fileName Name of XML export file
	 */
	public void exportTableWithDependencies(String tableName, String fileName) {
		try {
			String[] depTableNames = TablesDependencyHelper.getAllDependentTables(databaseConnection, tableName);
			IDataSet depDataSet = databaseConnection.createDataSet(depTableNames);
			FlatXmlDataSet.write(depDataSet, new FileOutputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
