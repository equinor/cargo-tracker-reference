package com.equinor.cargotrackerreference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equinor.cargotrackerreference.controller.resources.reporting.WetListGradeMapperResource;

@Repository
public interface WetListGradeMapRmRepository extends JpaRepository<WetListGradeMapperResource, String> {

}
