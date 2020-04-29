package com.equinor.cargotrackerreference.kpler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equinor.cargotrackerreference.config.AzureServiceBusConfiguration;
import com.equinor.cargotrackerreference.controller.resources.CompanyIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.CountryIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.GradeIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.RegionIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TerminalIdNameProperty;
import com.equinor.cargotrackerreference.controller.resources.TradingAreaIdNameProperty;
import com.equinor.cargotrackerreference.service.JmsService;

@Component
public class KplerProcessor implements Processor {
	
	@Autowired
	JmsService jmsService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		StrippedTrade trade =  (StrippedTrade) exchange.getIn().getBody();
					
		TitleTransferResource seller = new TitleTransferResource();
		CompanyIdNameProperty sellerCompany = new CompanyIdNameProperty();
		sellerCompany.name = trade.getSellerorigin();
		sellerCompany.id = UUID.randomUUID().toString();	
		seller.company = sellerCompany;
		seller.updatedBy = "Kpler";
		
		TitleTransferResource buyer = new TitleTransferResource();
		CompanyIdNameProperty buyerCompany = new CompanyIdNameProperty();
		buyerCompany.name = trade.getBuyerdestination();
		buyerCompany.id = UUID.randomUUID().toString();		
		buyer.company = buyerCompany;
		buyer.updatedBy = "Kpler";
				
		List<TitleTransferResource> titleTransfers = new ArrayList<TitleTransferResource>();
		titleTransfers.add(seller);
		titleTransfers.add(buyer);
		
		AnalyticsCargoResource cargo = new AnalyticsCargoResource();
		RegionIdNameProperty destinationRegion = new RegionIdNameProperty();
		destinationRegion.id = UUID.randomUUID().toString();
		destinationRegion.name = trade.getForecasteddestination();
		cargo.destinationRegion = destinationRegion;
		cargo.vesselType = trade.getVesseltype();
		cargo.vesselName = trade.getVessel();
		cargo.volume = trade.getCargotons();
		GradeIdNameProperty grade = new GradeIdNameProperty();
		grade.id = UUID.randomUUID().toString();
		grade.name = trade.getProduct();
		cargo.grade = grade;
		cargo.tradeStatus = trade.getTradestatus();
		cargo.dateDestination = trade.getEtadestination();
		cargo.titleTransfers = titleTransfers;
		TerminalIdNameProperty destinationInstallation = new TerminalIdNameProperty();
		destinationInstallation.id = UUID.randomUUID().toString();
		destinationInstallation.name = trade.getInstallationdestination();
		cargo.destinationPort = destinationInstallation;
		TerminalIdNameProperty sourceInstallation = new TerminalIdNameProperty();
		sourceInstallation.name = trade.getInstallationorigin();
		sourceInstallation.id = UUID.randomUUID().toString();
		cargo.originPort = sourceInstallation;
		CountryIdNameProperty destinationCountry = new CountryIdNameProperty();
		destinationCountry.id = UUID.randomUUID().toString();
		destinationCountry.name = trade.getCountrydestination();
		cargo.destinationCountry =  destinationCountry;
		CountryIdNameProperty sourceCountry = new CountryIdNameProperty();
		sourceCountry.id = UUID.randomUUID().toString();
		sourceCountry.name = trade.getCountrydestination();
		cargo.sourceCountry = sourceCountry;
		
		TradingAreaIdNameProperty tradingArea = new TradingAreaIdNameProperty();
		tradingArea.id = UUID.randomUUID().toString();
		tradingArea.name = "n/a";
		cargo.tradingArea = tradingArea;
		
		
		
		jmsService.sendJmsMessage(cargo, AzureServiceBusConfiguration.CARGO_TYPE, "update", true);
	}
}
