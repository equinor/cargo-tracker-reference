package com.equinor.cargotrackerreference.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.equinor.cargotracker.common.domain.Grade;
import com.equinor.cargotracker.common.exceptions.InvalidOperationException;
import com.equinor.cargotrackerreference.controller.exceptions.InternalServerError;
import com.equinor.cargotrackerreference.controller.exceptions.ResourceAlreadyExists;
import com.equinor.cargotrackerreference.controller.resources.GradeResource;
import com.equinor.cargotrackerreference.controller.resources.GradeResourceConverter;
import com.equinor.cargotrackerreference.controller.resources.GradeResourceExcel;
import com.equinor.cargotrackerreference.controller.resources.GradeResourceIterator;
import com.equinor.cargotrackerreference.domain.FileUpload;
import com.equinor.cargotrackerreference.service.FileUploadService;
import com.equinor.cargotrackerreference.service.GradeService;
import com.equinor.cargotrackerreference.service.JmsService;

@RestController
@RequestMapping(value = "/ctref/config")
@CrossOrigin(origins = "*")
public class GradeController {

	@Autowired
	private GradeService gradeService;

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private JmsService jmsService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/grade", method = RequestMethod.GET)
	public Iterable<GradeResource> getGrades() {
		logger.debug("Getting grades.");
		return new GradeResourceIterator(gradeService.getAllGrades().iterator());
	}

	@RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
	public GradeResource getGrade(@PathVariable(value = "id") UUID id) {
		logger.debug("Getting grade with id {}", id);
		return GradeResourceConverter.createResourceFromGrade(gradeService.getGrade(id).orElse(null));
	}

	@RequestMapping(value = "/grade", method = RequestMethod.POST)
	public GradeResource createGrade(@RequestBody GradeResource grade) {
		logger.debug("Creating grade {}", grade);
		if (grade == null) {
			return null;
		}
		try {
			Grade newGrade = gradeService.createGrade(GradeResourceConverter.createGradeFromResource(grade));			
			jmsService.sendJmsMessage(newGrade, "grade", "create");
			return GradeResourceConverter.createResourceFromGrade(newGrade);
		} catch (DataIntegrityViolationException e) {
			String errormessage = "Unable to create grade. Already exists a grade with name " + grade.name;
			logger.error(errormessage);
			throw new ResourceAlreadyExists(errormessage);
		} catch (InvalidOperationException ex) {
			logger.error("Unable to create grade {}. Error: {}", grade, ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Unable to create grade {}. Error: {}", grade, ex.getMessage());
			throw new InternalServerError(ex.getMessage());
		}
	}

	@RequestMapping(value = "/grade/{id}", method = RequestMethod.PATCH)
	public GradeResource patchRegionId(@PathVariable(value = "id") UUID id, @RequestBody GradeResource grade) {
		Grade patchedGrade = gradeService.patchRegionId(id, grade.tradingAreaId);		
		jmsService.sendJmsMessage(patchedGrade, "grade", "patch");
		return GradeResourceConverter.createResourceFromGrade(patchedGrade);
	}

	@RequestMapping(value = "/grade/{id}", method = RequestMethod.PUT)
	public GradeResource updateGrade(@PathVariable(value = "id") UUID id, @RequestBody GradeResource gradeResource) {
		logger.debug("Updating grade to {}", gradeResource);
		Grade updatedGrade = gradeService.updateGrade(GradeResourceConverter.createGradeFromResource(gradeResource, gradeService.getGrade(id).orElseThrow()));
		jmsService.sendJmsMessage(updatedGrade, "grade", "update");
		return GradeResourceConverter.createResourceFromGrade(updatedGrade);
	}

	@RequestMapping(value = "/grade/{id}/verify", method = RequestMethod.PATCH)
	public GradeResource verifyGrade(@PathVariable(value = "id") UUID id, @RequestBody GradeResource grade) {
		logger.debug("Verifying grade with id {} to status verified = {}", id, grade.verified);
		return GradeResourceConverter.createResourceFromGrade(gradeService.verifyGrade(id, grade.verified));
	}

	@RequestMapping(value = "/grade/{id}", method = RequestMethod.DELETE)
	public void cancelGrade(@PathVariable(value = "id") UUID id) {
		logger.debug("Cancelling grade with id: {}", id);
		gradeService.cancelGrade(id);
		Optional<Grade> cancelledGrade = gradeService.getGrade(id);
		jmsService.sendJmsMessage(cancelledGrade, "grade", "cancel");		
	}

	@RequestMapping(value = "/grade/upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(MultipartHttpServletRequest request) throws Exception {
		logger.debug("Uploading excel file");
		FileUpload fileUpload = null;
		String processingErrorMessage = "";
		Iterator<String> filenameIterator = request.getFileNames();
		while (filenameIterator.hasNext()) {
			fileUpload = getFileUpload(request, filenameIterator.next());
			try {
				List<GradeResourceExcel> moreVersionsOfExcelGradesFromFile = new ArrayList<>();
				List<GradeResourceExcel> excelGradesFromFile = splitOutMulitpleVersionOfGrades(fileUploadService.getExcelGradesFromFile(fileUpload), moreVersionsOfExcelGradesFromFile);
				gradeService.updateGrades(excelGradesFromFile);
				// To update a grade, the first version has to be saved first
				if (!moreVersionsOfExcelGradesFromFile.isEmpty()) {
					gradeService.updateGrades(moreVersionsOfExcelGradesFromFile);					
				}
			} catch (Exception exception) {
				processingErrorMessage = exception.getMessage();
				logger.error("Upload of excel file failed with the following error: {}", processingErrorMessage);
				throw exception;
			} finally {
				logger.debug("Saving uploaded excel file");
				fileUpload.setProcessingErrorMessage(processingErrorMessage);
				fileUploadService.createFile(fileUpload);
			}
		}
		// TODO HEH: Quickfix. Does not handle null message
		jmsService.sendJmsMessage("Does not handle null, so we add a String", "grade", "update");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private List<GradeResourceExcel> splitOutMulitpleVersionOfGrades(List<GradeResourceExcel> excelGradesFromFile, List<GradeResourceExcel> duplicateExcelGradesFromFile) {
		List<GradeResourceExcel> uniqueExcelGrades = new ArrayList<>();		
		for (GradeResourceExcel gradeResourceExcel : excelGradesFromFile) {
			if (uniqueExcelGrades.contains(gradeResourceExcel)) {
				duplicateExcelGradesFromFile.add(gradeResourceExcel);
			} else {
				uniqueExcelGrades.add(gradeResourceExcel);
			}
		}
		return uniqueExcelGrades;
	}

	private FileUpload getFileUpload(MultipartHttpServletRequest request, String uploadedFile) throws IOException {
		MultipartFile file = request.getFile(uploadedFile);
		String mimeType = file.getContentType();
		String filename = file.getOriginalFilename();
		if (!StringUtils.contains(filename, ".xlsx")) {
			logger.error("Upload of excel file failed. File is not Excel Workbook (*.xlsx)");
			throw new InvalidOperationException("Unable to process file. This is not of type Excel Workbook (*.xlsx)");
		}
		byte[] content = file.getBytes();
		return new FileUpload(filename, content, mimeType);
	}
	
}
