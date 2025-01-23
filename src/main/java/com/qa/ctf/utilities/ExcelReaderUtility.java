package com.qa.ctf.utilities;

import java.io.File;
import java.io.FileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

/**
 * 
 * @author Jaga
 *
 */
public class ExcelReaderUtility {

	private static final Logger log = LogManager.getLogger(ExcelReaderUtility.class.getName());

	public Object[][] getDataFromExcel(String excelPath, String sheetName) {
		Object[][] dataSet = null;
		try {
			log.info("The extraction of data from excel sheet starts here");
			var file = new FileInputStream(new File(excelPath));
			var workbook = new XSSFWorkbook(file);
			var sheet = workbook.getSheet(sheetName);
			var totalRow = sheet.getLastRowNum() + 1;
			var totalColumn = sheet.getRow(0).getLastCellNum();
			dataSet = new Object[totalRow - 1][totalColumn];
			for (var i = 1; i < totalRow; i++) {
				var rows = sheet.getRow(i);
				for (int j = 0; j < totalColumn; j++) {
					var cell = rows.getCell(j);
					switch (cell.getCellType()) {
					case STRING:
						dataSet[i - 1][j] = cell.getStringCellValue();
						break;
					case NUMERIC:
						dataSet[i - 1][j] = cell.getNumericCellValue();
						break;
					default:
						break;
					}
				}
			}
			log.info("The extraction of data from excel sheet has end");
			workbook.close();
		} catch (Exception ex) {
			log.info("Error occured while read data from excel sheet" + "\n" + ex);
			Assert.fail();
		}
		return dataSet;
	}
}
