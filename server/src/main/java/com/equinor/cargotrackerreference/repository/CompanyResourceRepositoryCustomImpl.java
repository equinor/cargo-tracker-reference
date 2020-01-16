package com.equinor.cargotrackerreference.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.equinor.cargotrackerreference.controller.resources.CompanyResource;

public class CompanyResourceRepositoryCustomImpl implements CompanyResourceRepositoryCustom {
	@Autowired
	private EntityManager em;
	
	public CompanyResource findByAlias(String alias) {
		TypedQuery<CompanyResource> query = em.createQuery("SELECT a FROM CompanyResource a JOIN a.aliases p WHERE p = :alias", CompanyResource.class);
		query.setParameter("alias", alias);
		List<CompanyResource> resultList = query.getResultList();
		if (resultList.isEmpty()) {
			return null;
		}
		return resultList.get(0);
	}
}
