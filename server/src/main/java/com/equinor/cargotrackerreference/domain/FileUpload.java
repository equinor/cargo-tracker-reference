package com.equinor.cargotrackerreference.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.equinor.cargotracker.common.domain.VersionedEntityName;

@Entity
@Table(name = "FILE_UPLOAD")
public class FileUpload extends VersionedEntityName {

	@Lob
	private byte[] content;
	
	@Column(name = "MIMETYPE")
	private String mimeType;
	
	@Column(name = "processing_error_msg")
	private String processingErrorMessage;

	public FileUpload(String filename, byte[] content, String mimeType) {
		super();
		this.setName(filename);
		this.content = content;
		this.mimeType = mimeType;
	}
	
	@SuppressWarnings("unused")
	private FileUpload() {
		super();
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getProcessingErrorMessage() {
		return processingErrorMessage;
	}

	public void setProcessingErrorMessage(String message) {
		this.processingErrorMessage = message;
	}
}
