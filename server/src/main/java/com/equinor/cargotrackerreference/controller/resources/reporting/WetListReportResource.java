package com.equinor.cargotrackerreference.controller.resources.reporting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TitleTransferResource;
import com.equinor.cargotrackerreference.controller.resources.analyticscargoresource.AnalyticsCargoResource;
import com.google.common.collect.Iterables;

public class WetListReportResource {

	public String id;
	public Boolean available;
	public LocalDate blDate;
	public LocalDate date;
	public YearMonth month;
	public String titleTransfers;
	public String titleTransfersShortName;
	public String gradeName;
	public String bofet;
	public String grade;
	public String countryGroup;
	public String columnCategory;
	public String tradingArea;
	public String comments;
	public LocalDate laycanEnd;
	public LocalDate laycanStart;
	public BigDecimal volume;
	public BigDecimal price;
	public int section;
	
	public WetListReportResource(AnalyticsCargoResource analyticsCargoResource, WetListGradeMapperResource wetListGradeMapper) {
		this.id = analyticsCargoResource.id;
		this.blDate = analyticsCargoResource.blDate;
		this.date = analyticsCargoResource.laycanStart != null ? analyticsCargoResource.laycanStart.plusDays(1) : analyticsCargoResource.blDate;
		this.comments = analyticsCargoResource.comments;
		this.gradeName = getGradeName(analyticsCargoResource, wetListGradeMapper);
		this.grade = analyticsCargoResource.grade != null ? analyticsCargoResource.grade.name : null;
		this.bofet = wetListGradeMapper != null ? wetListGradeMapper.bofet : "";
		this.columnCategory = wetListGradeMapper != null ? wetListGradeMapper.category : "";
		this.countryGroup = wetListGradeMapper != null ? wetListGradeMapper.countryGroup : "";
		this.laycanEnd = analyticsCargoResource.laycanEnd;
		this.laycanStart = analyticsCargoResource.laycanStart;
		this.month = analyticsCargoResource.month;
		List<CompanyIdNameProperty> titleTransferCompanies = getFirstAndLastTitleTransferCompany(analyticsCargoResource.titleTransfers);
		this.titleTransfers = getCompaniesName(titleTransferCompanies);
		this.titleTransfersShortName = getCompaniesShortname(titleTransferCompanies);
		this.tradingArea = analyticsCargoResource.tradingArea.name;
		this.volume = analyticsCargoResource.volume;
		this.price = getPrice(analyticsCargoResource);
		this.section = getSection(this.date);
		this.available = analyticsCargoResource.available;
	}
	
	private List<CompanyIdNameProperty> getFirstAndLastTitleTransferCompany(List<TitleTransferResource> titleTransfers) {
		var companies = new ArrayList<CompanyIdNameProperty>();
		if (titleTransfers != null && !titleTransfers.isEmpty()) {
			if (titleTransfers.get(0).company != null) {
				companies.add(titleTransfers.get(0).company);
			}
			if (titleTransfers.size() > 1 && Iterables.getLast(titleTransfers).company != null) {
				companies.add(Iterables.getLast(titleTransfers).company);
			}
		}
		return companies;
	}
	
	private String getCompaniesName(List<CompanyIdNameProperty> companies) {
		return companies.stream()
				.map(company -> company.name)
				.collect(Collectors.joining( "-"));
	}
	
	private String getCompaniesShortname(List<CompanyIdNameProperty> companies) {
		return companies.stream()
				.map(company -> StringUtils.isEmpty(company.shortName) ? getCompanyNameShortened(company.name) : company.shortName)
				.collect(Collectors.joining( "-"));
	}
	
	private String getCompanyNameShortened(String companyName) {
		return companyName.length() > 8 ? companyName.substring(0,8) : companyName;
	}

	private int getSection(LocalDate date) {
		int sectionNumber;
		if (date == null) {
			sectionNumber = 2;
		} else if (date.getDayOfMonth() < 10) {
			sectionNumber = 0;
		} else if (date.getDayOfMonth() < 20) {
			sectionNumber = 1;
		} else {
			sectionNumber = 2;
		}
		
		return sectionNumber;
	}
	
	private BigDecimal getPrice(AnalyticsCargoResource analyticsCargoResource) {
		return analyticsCargoResource.titleTransfers != null  && !analyticsCargoResource.titleTransfers.isEmpty() ? analyticsCargoResource.titleTransfers.get(analyticsCargoResource.titleTransfers.size()-1).price : null;
	}
	
	private String getGradeName(AnalyticsCargoResource analyticsCargoResource, WetListGradeMapperResource wetListGradeMapper) {
		String gradeName = "";
		if (wetListGradeMapper != null) {
			gradeName = wetListGradeMapper.name;
		} else if (analyticsCargoResource.grade != null) {
			gradeName = analyticsCargoResource.grade.name;
		}
		return gradeName;
	}
}
