package com.qa.chroma.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qa.chroma.base.TestBase;

public class ExcelUtil extends TestBase {

	private static Workbook book;
	private static Sheet sheet;

	public static String TESTDATA_SHEET_PATH = "D:\\All Project\\ECommerceWebsite_Chroma_GSIT\\src\\test\\resources\\testdata\\ChromaSearch.xlsx";

	public static Object[][] getTestData(String sheetName) {

		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(TESTDATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);

			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					Cell cell = sheet.getRow(i + 1).getCell(j); // new Added line
					DataFormatter formatter = new DataFormatter(); // new Added line
					data[i][j] = formatter.formatCellValue(cell); // new Added line

					// data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;

	}

}
