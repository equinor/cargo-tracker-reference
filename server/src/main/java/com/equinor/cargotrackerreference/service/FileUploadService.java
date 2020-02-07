package com.equinor.cargotrackerreference.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.controller.resources.GradeResourceExcel;
import com.equinor.cargotrackerreference.domain.FileUpload;
import com.equinor.cargotrackerreference.repository.FileUploadRepository;


@Service
@Transactional
public class FileUploadService {

	@Autowired
	private FileUploadRepository fileUploadRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void createFile(FileUpload fileUpload) {
		fileUploadRepository.save(fileUpload);
	}

	public List<GradeResourceExcel> getExcelGradesFromFile(FileUpload fileUpload) {
		List<GradeResourceExcel> grades = new ArrayList<>();
		InputStream is = new ByteArrayInputStream(fileUpload.getContent());

		XSSFWorkbook wb;
		try {
			wb = new XSSFWorkbook(is);
		} catch (Exception e) {
			String errormessage = "Failed to open Excel workbook. Error: " + e.getMessage();
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}

		XSSFSheet gradeSheet = wb.getSheet(wb.getSheetName(0));
		Iterator<Row> rowIterator = gradeSheet.rowIterator();
		int rowCounter = 0;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (isHeaderRow(rowCounter)) {
				rowCounter++;
				continue;
			}
			
			GradeResourceExcel gradeResource = getGradeResource(row, rowCounter);
			if (gradeResource != null) {
				grades.add(gradeResource);
			}
			rowCounter++;
		}

		try {
			wb.close();
		} catch (Exception e) {
			String errormessage = "Failed to close Excel workbook after reading grades. Error: " + e.getMessage();
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
		return grades;
	}

	private boolean isHeaderRow(int rowCounter) {
		return rowCounter == 0;
	}

	private GradeResourceExcel getGradeResource(Row row, int rowNumber) {
		GradeResourceExcel gradeResourceExcel = new GradeResourceExcel();
		int cellNumber = 1;
		try {
			gradeResourceExcel.name = getStringCellValue(row, cellNumber);
			cellNumber++;
			gradeResourceExcel.reference = getStringCellValue(row, cellNumber);
			cellNumber+=2;
			gradeResourceExcel.validFrom = getValidFromDateValue(row, cellNumber);
			cellNumber++;
			gradeResourceExcel.api = getCellNumberValue(row, cellNumber);
			cellNumber++;
			gradeResourceExcel.sulphur = getCellNumberValue(row, cellNumber);
			cellNumber++;
			gradeResourceExcel.country = getStringCellValue(row, cellNumber);
		} catch (Exception e) {
			String errormessage = "Got an exception trying to read a cell. Please verify that the data has the correct format."
					+ "\nRow Number: " + rowNumber 
					+ "\nCell Number: " + cellNumber
					+ "\nDetailed error: " + e.getMessage();
			logger.error(errormessage);
			throw new InvalidOperationException(errormessage);
		}
		if (StringUtils.isEmpty(gradeResourceExcel.name) && StringUtils.isEmpty(gradeResourceExcel.reference)) {
			// With both name and reference empty, we interpret this as an empty row
			return null;
		}
		return gradeResourceExcel;
	}

	private LocalDate getValidFromDateValue(Row row, int cellNumber) {
		LocalDate date = null;
		if (row.getCell(cellNumber) != null && row.getCell(cellNumber).getDateCellValue() != null) {
			// Though publication date can be any day of the month, we use the new analysis for all cargoes this month
			// Confirmed by user Jennifer Jhesabel Hernandez (JEJH)
			date = row.getCell(cellNumber).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1);
		}
		return date;
	}

	private BigDecimal getCellNumberValue(Row row, int cellNumber) {
		return row.getCell(cellNumber) != null ? BigDecimal.valueOf(row.getCell(cellNumber).getNumericCellValue()).setScale(2, RoundingMode.HALF_EVEN) : null;
	}

	private String getStringCellValue(Row row, int cellNumber) {
		return row.getCell(cellNumber) != null ? row.getCell(cellNumber).getStringCellValue() : null;
	}

}
