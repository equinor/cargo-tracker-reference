package com.equinor.cargotrackerreference.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotracker.common.domain.FileUpload;

@Repository
public interface FileUploadRepository extends CrudRepository<FileUpload, String> {
}
