
package com.equinor.cargotrackerreference.kpler;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "vessel", "cargo_tons", "grade", "product", "group", "family", "date_origin", "origin",
		"eta_source_origin", "reload_sts_partial_origin", "date_destination", "destination", "eta_source_destination",
		"reload_sts_partial_destination", "forecasted_destination", "forecasted_eta", "forecasted_confidence",
		"export_price", "number_of_trades_export", "import_price", "number_of_trades_import", "trade_status",
		"seller_origin", "buyer_destination", "intermediaries", "country_origin", "subcontinent_origin",
		"continent_origin", "eta_origin", "start_origin", "end_origin", "country_destination",
		"subcontinent_destination", "continent_destination", "eta_destination", "start_destination", "end_destination",
		"capacity_vessel_m3", "vessel_type", "cargo_type_vessel", "id_vessel", "link1_delivery", "link1_type",
		"link1_seller_name", "link1_seller_country", "link1_buyer_name", "link1_buyer_country", "link2_delivery",
		"link2_type", "link2_seller_name", "link2_seller_country", "link2_buyer_name", "link2_buyer_country",
		"link3_delivery", "link3_type", "link3_seller_name", "link3_seller_country", "link3_buyer_name",
		"link3_buyer_country", "link4_delivery", "link4_type", "link4_seller_name", "link4_seller_country",
		"link4_buyer_name", "link4_buyer_country", "link5_delivery", "link5_type", "link5_seller_name",
		"link5_seller_country", "link5_buyer_name", "link5_buyer_country", "id_trade", "zone_origin", "zone_origin_id",
		"zone_destination", "zone_destination_id", "installation_origin", "installation_origin_id",
		"installation_destination", "installation_destination_id", "mileage", "ton_miles", "forecasted_origin",
		"forecasted_origin_eta", "forecasted_origin_confidence", "id_voyage", "charterer" })
public class Trade {

	@JsonProperty("vessel")
	private String vessel;
	@JsonProperty("cargo_tons")
	private BigDecimal cargoTons;
	@JsonProperty("grade")
	private String grade;
	@JsonProperty("product")
	private String product;
	@JsonProperty("group")
	private String group;
	@JsonProperty("family")
	private String family;
	
	@JsonProperty("date_origin")
	private Long dateOrigin;	
	@JsonProperty("origin")
	private String origin;
	@JsonProperty("eta_source_origin")
	private String etaSourceOrigin;
	@JsonProperty("reload_sts_partial_origin")
	private String reloadStsPartialOrigin;
	@JsonProperty("date_destination")
	private Object dateDestination;
	@JsonProperty("destination")
	private String destination;
	@JsonProperty("eta_source_destination")
	private String etaSourceDestination;
	@JsonProperty("reload_sts_partial_destination")
	private String reloadStsPartialDestination;
	@JsonProperty("forecasted_destination")
	private String forecastedDestination;
	@JsonProperty("forecasted_eta")
	private Long forecastedEta;
	@JsonProperty("forecasted_confidence")
	private Double forecastedConfidence;
	@JsonProperty("export_price")
	private String exportPrice;
	@JsonProperty("number_of_trades_export")
	private String numberOfTradesExport;
	@JsonProperty("import_price")
	private String importPrice;
	@JsonProperty("number_of_trades_import")
	private String numberOfTradesImport;
	@JsonProperty("trade_status")
	private String tradeStatus;
	@JsonProperty("seller_origin")
	private String sellerOrigin;
	@JsonProperty("buyer_destination")
	private String buyerDestination;
	@JsonProperty("intermediaries")
	private Long intermediaries;
	@JsonProperty("country_origin")
	private String countryOrigin;
	@JsonProperty("subcontinent_origin")
	private String subcontinentOrigin;
	@JsonProperty("continent_origin")
	private String continentOrigin;
	@JsonProperty("eta_origin")
	private Long etaOrigin;
	@JsonProperty("start_origin")
	private Object startOrigin;
	@JsonProperty("end_origin")
	private Object endOrigin;
	@JsonProperty("country_destination")
	private String countryDestination;
	@JsonProperty("subcontinent_destination")
	private String subcontinentDestination;
	@JsonProperty("continent_destination")
	private String continentDestination;
	@JsonProperty("eta_destination")
	private Object etaDestination;
	@JsonProperty("start_destination")
	private Object startDestination;
	@JsonProperty("end_destination")
	private Object endDestination;
	@JsonProperty("capacity_vessel_m3")
	private Long capacityVesselM3;
	@JsonProperty("vessel_type")
	private String vesselType;
	@JsonProperty("cargo_type_vessel")
	private String cargoTypeVessel;
	@JsonProperty("id_vessel")
	private Long idVessel;
	@JsonProperty("link1_delivery")
	private String link1Delivery;
	@JsonProperty("link1_type")
	private String link1Type;
	@JsonProperty("link1_seller_name")
	private String link1SellerName;
	@JsonProperty("link1_seller_country")
	private String link1SellerCountry;
	@JsonProperty("link1_buyer_name")
	private String link1BuyerName;
	@JsonProperty("link1_buyer_country")
	private String link1BuyerCountry;
	@JsonProperty("link2_delivery")
	private String link2Delivery;
	@JsonProperty("link2_type")
	private String link2Type;
	@JsonProperty("link2_seller_name")
	private String link2SellerName;
	@JsonProperty("link2_seller_country")
	private String link2SellerCountry;
	@JsonProperty("link2_buyer_name")
	private String link2BuyerName;
	@JsonProperty("link2_buyer_country")
	private String link2BuyerCountry;
	@JsonProperty("link3_delivery")
	private String link3Delivery;
	@JsonProperty("link3_type")
	private String link3Type;
	@JsonProperty("link3_seller_name")
	private String link3SellerName;
	@JsonProperty("link3_seller_country")
	private String link3SellerCountry;
	@JsonProperty("link3_buyer_name")
	private String link3BuyerName;
	@JsonProperty("link3_buyer_country")
	private String link3BuyerCountry;
	@JsonProperty("link4_delivery")
	private String link4Delivery;
	@JsonProperty("link4_type")
	private String link4Type;
	@JsonProperty("link4_seller_name")
	private String link4SellerName;
	@JsonProperty("link4_seller_country")
	private String link4SellerCountry;
	@JsonProperty("link4_buyer_name")
	private String link4BuyerName;
	@JsonProperty("link4_buyer_country")
	private String link4BuyerCountry;
	@JsonProperty("link5_delivery")
	private String link5Delivery;
	@JsonProperty("link5_type")
	private String link5Type;
	@JsonProperty("link5_seller_name")
	private String link5SellerName;
	@JsonProperty("link5_seller_country")
	private String link5SellerCountry;
	@JsonProperty("link5_buyer_name")
	private String link5BuyerName;
	@JsonProperty("link5_buyer_country")
	private String link5BuyerCountry;
	@JsonProperty("id_trade")
	private Long idTrade;
	@JsonProperty("zone_origin")
	private String zoneOrigin;
	@JsonProperty("zone_origin_id")
	private Long zoneOriginId;
	@JsonProperty("zone_destination")
	private String zoneDestination;
	@JsonProperty("zone_destination_id")
	private Object zoneDestinationId;
	@JsonProperty("installation_origin")
	private String installationOrigin;
	@JsonProperty("installation_origin_id")
	private Object installationOriginId;
	@JsonProperty("installation_destination")
	private String installationDestination;
	@JsonProperty("installation_destination_id")
	private Object installationDestinationId;
	@JsonProperty("mileage")
	private Object mileage;
	@JsonProperty("ton_miles")
	private Object tonMiles;
	@JsonProperty("forecasted_origin")
	private String forecastedOrigin;
	@JsonProperty("forecasted_origin_eta")
	private Object forecastedOriginEta;
	@JsonProperty("forecasted_origin_confidence")
	private Object forecastedOriginConfidence;
	@JsonProperty("id_voyage")
	private Long idVoyage;
	@JsonProperty("charterer")
	private String charterer;

	@JsonProperty("vessel")
	public String getVessel() {
		return vessel;
	}

	@JsonProperty("vessel")
	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	@JsonProperty("cargo_tons")
	public BigDecimal getCargoTons() {
		return cargoTons;
	}

	@JsonProperty("cargo_tons")
	public void setCargoTons(BigDecimal cargoTons) {
		this.cargoTons = cargoTons;
	}

	@JsonProperty("grade")
	public String getGrade() {
		return grade;
	}

	@JsonProperty("grade")
	public void setGrade(String grade) {
		this.grade = grade;
	}

	@JsonProperty("product")
	public String getProduct() {
		return product;
	}

	@JsonProperty("product")
	public void setProduct(String product) {
		this.product = product;
	}

	@JsonProperty("group")
	public String getGroup() {
		return group;
	}

	@JsonProperty("group")
	public void setGroup(String group) {
		this.group = group;
	}

	@JsonProperty("family")
	public String getFamily() {
		return family;
	}

	@JsonProperty("family")
	public void setFamily(String family) {
		this.family = family;
	}

	@JsonProperty("date_origin")
	public Long getDateOrigin() {
		return dateOrigin;
	}

	@JsonProperty("date_origin")
	public void setDateOrigin(Long dateOrigin) {
		this.dateOrigin = dateOrigin;
	}

	@JsonProperty("origin")
	public String getOrigin() {
		return origin;
	}

	@JsonProperty("origin")
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@JsonProperty("eta_source_origin")
	public String getEtaSourceOrigin() {
		return etaSourceOrigin;
	}

	@JsonProperty("eta_source_origin")
	public void setEtaSourceOrigin(String etaSourceOrigin) {
		this.etaSourceOrigin = etaSourceOrigin;
	}

	@JsonProperty("reload_sts_partial_origin")
	public String getReloadStsPartialOrigin() {
		return reloadStsPartialOrigin;
	}

	@JsonProperty("reload_sts_partial_origin")
	public void setReloadStsPartialOrigin(String reloadStsPartialOrigin) {
		this.reloadStsPartialOrigin = reloadStsPartialOrigin;
	}

	@JsonProperty("date_destination")
	public Object getDateDestination() {
		return dateDestination;
	}

	@JsonProperty("date_destination")
	public void setDateDestination(Object dateDestination) {
		this.dateDestination = dateDestination;
	}

	@JsonProperty("destination")
	public String getDestination() {
		return destination;
	}

	@JsonProperty("destination")
	public void setDestination(String destination) {
		this.destination = destination;
	}

	@JsonProperty("eta_source_destination")
	public String getEtaSourceDestination() {
		return etaSourceDestination;
	}

	@JsonProperty("eta_source_destination")
	public void setEtaSourceDestination(String etaSourceDestination) {
		this.etaSourceDestination = etaSourceDestination;
	}

	@JsonProperty("reload_sts_partial_destination")
	public String getReloadStsPartialDestination() {
		return reloadStsPartialDestination;
	}

	@JsonProperty("reload_sts_partial_destination")
	public void setReloadStsPartialDestination(String reloadStsPartialDestination) {
		this.reloadStsPartialDestination = reloadStsPartialDestination;
	}

	@JsonProperty("forecasted_destination")
	public String getForecastedDestination() {
		return forecastedDestination;
	}

	@JsonProperty("forecasted_destination")
	public void setForecastedDestination(String forecastedDestination) {
		this.forecastedDestination = forecastedDestination;
	}

	@JsonProperty("forecasted_eta")
	public Long getForecastedEta() {
		return forecastedEta;
	}

	@JsonProperty("forecasted_eta")
	public void setForecastedEta(Long forecastedEta) {
		this.forecastedEta = forecastedEta;
	}

	@JsonProperty("forecasted_confidence")
	public Double getForecastedConfidence() {
		return forecastedConfidence;
	}

	@JsonProperty("forecasted_confidence")
	public void setForecastedConfidence(Double forecastedConfidence) {
		this.forecastedConfidence = forecastedConfidence;
	}

	@JsonProperty("export_price")
	public String getExportPrice() {
		return exportPrice;
	}

	@JsonProperty("export_price")
	public void setExportPrice(String exportPrice) {
		this.exportPrice = exportPrice;
	}

	@JsonProperty("number_of_trades_export")
	public String getNumberOfTradesExport() {
		return numberOfTradesExport;
	}

	@JsonProperty("number_of_trades_export")
	public void setNumberOfTradesExport(String numberOfTradesExport) {
		this.numberOfTradesExport = numberOfTradesExport;
	}

	@JsonProperty("import_price")
	public String getImportPrice() {
		return importPrice;
	}

	@JsonProperty("import_price")
	public void setImportPrice(String importPrice) {
		this.importPrice = importPrice;
	}

	@JsonProperty("number_of_trades_import")
	public String getNumberOfTradesImport() {
		return numberOfTradesImport;
	}

	@JsonProperty("number_of_trades_import")
	public void setNumberOfTradesImport(String numberOfTradesImport) {
		this.numberOfTradesImport = numberOfTradesImport;
	}

	@JsonProperty("trade_status")
	public String getTradeStatus() {
		return tradeStatus;
	}

	@JsonProperty("trade_status")
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	@JsonProperty("seller_origin")
	public String getSellerOrigin() {
		return sellerOrigin;
	}

	@JsonProperty("seller_origin")
	public void setSellerOrigin(String sellerOrigin) {
		this.sellerOrigin = sellerOrigin;
	}

	@JsonProperty("buyer_destination")
	public String getBuyerDestination() {
		return buyerDestination;
	}

	@JsonProperty("buyer_destination")
	public void setBuyerDestination(String buyerDestination) {
		this.buyerDestination = buyerDestination;
	}

	@JsonProperty("intermediaries")
	public Long getIntermediaries() {
		return intermediaries;
	}

	@JsonProperty("intermediaries")
	public void setIntermediaries(Long intermediaries) {
		this.intermediaries = intermediaries;
	}

	@JsonProperty("country_origin")
	public String getCountryOrigin() {
		return countryOrigin;
	}

	@JsonProperty("country_origin")
	public void setCountryOrigin(String countryOrigin) {
		this.countryOrigin = countryOrigin;
	}

	@JsonProperty("subcontinent_origin")
	public String getSubcontinentOrigin() {
		return subcontinentOrigin;
	}

	@JsonProperty("subcontinent_origin")
	public void setSubcontinentOrigin(String subcontinentOrigin) {
		this.subcontinentOrigin = subcontinentOrigin;
	}

	@JsonProperty("continent_origin")
	public String getContinentOrigin() {
		return continentOrigin;
	}

	@JsonProperty("continent_origin")
	public void setContinentOrigin(String continentOrigin) {
		this.continentOrigin = continentOrigin;
	}

	@JsonProperty("eta_origin")
	public Long getEtaOrigin() {
		return etaOrigin;
	}

	@JsonProperty("eta_origin")
	public void setEtaOrigin(Long etaOrigin) {
		this.etaOrigin = etaOrigin;
	}

	@JsonProperty("start_origin")
	public Object getStartOrigin() {
		return startOrigin;
	}

	@JsonProperty("start_origin")
	public void setStartOrigin(Object startOrigin) {
		this.startOrigin = startOrigin;
	}

	@JsonProperty("end_origin")
	public Object getEndOrigin() {
		return endOrigin;
	}

	@JsonProperty("end_origin")
	public void setEndOrigin(Object endOrigin) {
		this.endOrigin = endOrigin;
	}

	@JsonProperty("country_destination")
	public String getCountryDestination() {
		return countryDestination;
	}

	@JsonProperty("country_destination")
	public void setCountryDestination(String countryDestination) {
		this.countryDestination = countryDestination;
	}

	@JsonProperty("subcontinent_destination")
	public String getSubcontinentDestination() {
		return subcontinentDestination;
	}

	@JsonProperty("subcontinent_destination")
	public void setSubcontinentDestination(String subcontinentDestination) {
		this.subcontinentDestination = subcontinentDestination;
	}

	@JsonProperty("continent_destination")
	public String getContinentDestination() {
		return continentDestination;
	}

	@JsonProperty("continent_destination")
	public void setContinentDestination(String continentDestination) {
		this.continentDestination = continentDestination;
	}

	@JsonProperty("eta_destination")
	public Object getEtaDestination() {
		return etaDestination;
	}

	@JsonProperty("eta_destination")
	public void setEtaDestination(Object etaDestination) {
		this.etaDestination = etaDestination;
	}

	@JsonProperty("start_destination")
	public Object getStartDestination() {
		return startDestination;
	}

	@JsonProperty("start_destination")
	public void setStartDestination(Object startDestination) {
		this.startDestination = startDestination;
	}

	@JsonProperty("end_destination")
	public Object getEndDestination() {
		return endDestination;
	}

	@JsonProperty("end_destination")
	public void setEndDestination(Object endDestination) {
		this.endDestination = endDestination;
	}

	@JsonProperty("capacity_vessel_m3")
	public Long getCapacityVesselM3() {
		return capacityVesselM3;
	}

	@JsonProperty("capacity_vessel_m3")
	public void setCapacityVesselM3(Long capacityVesselM3) {
		this.capacityVesselM3 = capacityVesselM3;
	}

	@JsonProperty("vessel_type")
	public String getVesselType() {
		return vesselType;
	}

	@JsonProperty("vessel_type")
	public void setVesselType(String vesselType) {
		this.vesselType = vesselType;
	}

	@JsonProperty("cargo_type_vessel")
	public String getCargoTypeVessel() {
		return cargoTypeVessel;
	}

	@JsonProperty("cargo_type_vessel")
	public void setCargoTypeVessel(String cargoTypeVessel) {
		this.cargoTypeVessel = cargoTypeVessel;
	}

	@JsonProperty("id_vessel")
	public Long getIdVessel() {
		return idVessel;
	}

	@JsonProperty("id_vessel")
	public void setIdVessel(Long idVessel) {
		this.idVessel = idVessel;
	}

	@JsonProperty("link1_delivery")
	public String getLink1Delivery() {
		return link1Delivery;
	}

	@JsonProperty("link1_delivery")
	public void setLink1Delivery(String link1Delivery) {
		this.link1Delivery = link1Delivery;
	}

	@JsonProperty("link1_type")
	public String getLink1Type() {
		return link1Type;
	}

	@JsonProperty("link1_type")
	public void setLink1Type(String link1Type) {
		this.link1Type = link1Type;
	}

	@JsonProperty("link1_seller_name")
	public String getLink1SellerName() {
		return link1SellerName;
	}

	@JsonProperty("link1_seller_name")
	public void setLink1SellerName(String link1SellerName) {
		this.link1SellerName = link1SellerName;
	}

	@JsonProperty("link1_seller_country")
	public String getLink1SellerCountry() {
		return link1SellerCountry;
	}

	@JsonProperty("link1_seller_country")
	public void setLink1SellerCountry(String link1SellerCountry) {
		this.link1SellerCountry = link1SellerCountry;
	}

	@JsonProperty("link1_buyer_name")
	public String getLink1BuyerName() {
		return link1BuyerName;
	}

	@JsonProperty("link1_buyer_name")
	public void setLink1BuyerName(String link1BuyerName) {
		this.link1BuyerName = link1BuyerName;
	}

	@JsonProperty("link1_buyer_country")
	public String getLink1BuyerCountry() {
		return link1BuyerCountry;
	}

	@JsonProperty("link1_buyer_country")
	public void setLink1BuyerCountry(String link1BuyerCountry) {
		this.link1BuyerCountry = link1BuyerCountry;
	}

	@JsonProperty("link2_delivery")
	public String getLink2Delivery() {
		return link2Delivery;
	}

	@JsonProperty("link2_delivery")
	public void setLink2Delivery(String link2Delivery) {
		this.link2Delivery = link2Delivery;
	}

	@JsonProperty("link2_type")
	public String getLink2Type() {
		return link2Type;
	}

	@JsonProperty("link2_type")
	public void setLink2Type(String link2Type) {
		this.link2Type = link2Type;
	}

	@JsonProperty("link2_seller_name")
	public String getLink2SellerName() {
		return link2SellerName;
	}

	@JsonProperty("link2_seller_name")
	public void setLink2SellerName(String link2SellerName) {
		this.link2SellerName = link2SellerName;
	}

	@JsonProperty("link2_seller_country")
	public String getLink2SellerCountry() {
		return link2SellerCountry;
	}

	@JsonProperty("link2_seller_country")
	public void setLink2SellerCountry(String link2SellerCountry) {
		this.link2SellerCountry = link2SellerCountry;
	}

	@JsonProperty("link2_buyer_name")
	public String getLink2BuyerName() {
		return link2BuyerName;
	}

	@JsonProperty("link2_buyer_name")
	public void setLink2BuyerName(String link2BuyerName) {
		this.link2BuyerName = link2BuyerName;
	}

	@JsonProperty("link2_buyer_country")
	public String getLink2BuyerCountry() {
		return link2BuyerCountry;
	}

	@JsonProperty("link2_buyer_country")
	public void setLink2BuyerCountry(String link2BuyerCountry) {
		this.link2BuyerCountry = link2BuyerCountry;
	}

	@JsonProperty("link3_delivery")
	public String getLink3Delivery() {
		return link3Delivery;
	}

	@JsonProperty("link3_delivery")
	public void setLink3Delivery(String link3Delivery) {
		this.link3Delivery = link3Delivery;
	}

	@JsonProperty("link3_type")
	public String getLink3Type() {
		return link3Type;
	}

	@JsonProperty("link3_type")
	public void setLink3Type(String link3Type) {
		this.link3Type = link3Type;
	}

	@JsonProperty("link3_seller_name")
	public String getLink3SellerName() {
		return link3SellerName;
	}

	@JsonProperty("link3_seller_name")
	public void setLink3SellerName(String link3SellerName) {
		this.link3SellerName = link3SellerName;
	}

	@JsonProperty("link3_seller_country")
	public String getLink3SellerCountry() {
		return link3SellerCountry;
	}

	@JsonProperty("link3_seller_country")
	public void setLink3SellerCountry(String link3SellerCountry) {
		this.link3SellerCountry = link3SellerCountry;
	}

	@JsonProperty("link3_buyer_name")
	public String getLink3BuyerName() {
		return link3BuyerName;
	}

	@JsonProperty("link3_buyer_name")
	public void setLink3BuyerName(String link3BuyerName) {
		this.link3BuyerName = link3BuyerName;
	}

	@JsonProperty("link3_buyer_country")
	public String getLink3BuyerCountry() {
		return link3BuyerCountry;
	}

	@JsonProperty("link3_buyer_country")
	public void setLink3BuyerCountry(String link3BuyerCountry) {
		this.link3BuyerCountry = link3BuyerCountry;
	}

	@JsonProperty("link4_delivery")
	public String getLink4Delivery() {
		return link4Delivery;
	}

	@JsonProperty("link4_delivery")
	public void setLink4Delivery(String link4Delivery) {
		this.link4Delivery = link4Delivery;
	}

	@JsonProperty("link4_type")
	public String getLink4Type() {
		return link4Type;
	}

	@JsonProperty("link4_type")
	public void setLink4Type(String link4Type) {
		this.link4Type = link4Type;
	}

	@JsonProperty("link4_seller_name")
	public String getLink4SellerName() {
		return link4SellerName;
	}

	@JsonProperty("link4_seller_name")
	public void setLink4SellerName(String link4SellerName) {
		this.link4SellerName = link4SellerName;
	}

	@JsonProperty("link4_seller_country")
	public String getLink4SellerCountry() {
		return link4SellerCountry;
	}

	@JsonProperty("link4_seller_country")
	public void setLink4SellerCountry(String link4SellerCountry) {
		this.link4SellerCountry = link4SellerCountry;
	}

	@JsonProperty("link4_buyer_name")
	public String getLink4BuyerName() {
		return link4BuyerName;
	}

	@JsonProperty("link4_buyer_name")
	public void setLink4BuyerName(String link4BuyerName) {
		this.link4BuyerName = link4BuyerName;
	}

	@JsonProperty("link4_buyer_country")
	public String getLink4BuyerCountry() {
		return link4BuyerCountry;
	}

	@JsonProperty("link4_buyer_country")
	public void setLink4BuyerCountry(String link4BuyerCountry) {
		this.link4BuyerCountry = link4BuyerCountry;
	}

	@JsonProperty("link5_delivery")
	public String getLink5Delivery() {
		return link5Delivery;
	}

	@JsonProperty("link5_delivery")
	public void setLink5Delivery(String link5Delivery) {
		this.link5Delivery = link5Delivery;
	}

	@JsonProperty("link5_type")
	public String getLink5Type() {
		return link5Type;
	}

	@JsonProperty("link5_type")
	public void setLink5Type(String link5Type) {
		this.link5Type = link5Type;
	}

	@JsonProperty("link5_seller_name")
	public String getLink5SellerName() {
		return link5SellerName;
	}

	@JsonProperty("link5_seller_name")
	public void setLink5SellerName(String link5SellerName) {
		this.link5SellerName = link5SellerName;
	}

	@JsonProperty("link5_seller_country")
	public String getLink5SellerCountry() {
		return link5SellerCountry;
	}

	@JsonProperty("link5_seller_country")
	public void setLink5SellerCountry(String link5SellerCountry) {
		this.link5SellerCountry = link5SellerCountry;
	}

	@JsonProperty("link5_buyer_name")
	public String getLink5BuyerName() {
		return link5BuyerName;
	}

	@JsonProperty("link5_buyer_name")
	public void setLink5BuyerName(String link5BuyerName) {
		this.link5BuyerName = link5BuyerName;
	}

	@JsonProperty("link5_buyer_country")
	public String getLink5BuyerCountry() {
		return link5BuyerCountry;
	}

	@JsonProperty("link5_buyer_country")
	public void setLink5BuyerCountry(String link5BuyerCountry) {
		this.link5BuyerCountry = link5BuyerCountry;
	}

	@JsonProperty("id_trade")
	public Long getIdTrade() {
		return idTrade;
	}

	@JsonProperty("id_trade")
	public void setIdTrade(Long idTrade) {
		this.idTrade = idTrade;
	}

	@JsonProperty("zone_origin")
	public String getZoneOrigin() {
		return zoneOrigin;
	}

	@JsonProperty("zone_origin")
	public void setZoneOrigin(String zoneOrigin) {
		this.zoneOrigin = zoneOrigin;
	}

	@JsonProperty("zone_origin_id")
	public Long getZoneOriginId() {
		return zoneOriginId;
	}

	@JsonProperty("zone_origin_id")
	public void setZoneOriginId(Long zoneOriginId) {
		this.zoneOriginId = zoneOriginId;
	}

	@JsonProperty("zone_destination")
	public String getZoneDestination() {
		return zoneDestination;
	}

	@JsonProperty("zone_destination")
	public void setZoneDestination(String zoneDestination) {
		this.zoneDestination = zoneDestination;
	}

	@JsonProperty("zone_destination_id")
	public Object getZoneDestinationId() {
		return zoneDestinationId;
	}

	@JsonProperty("zone_destination_id")
	public void setZoneDestinationId(Object zoneDestinationId) {
		this.zoneDestinationId = zoneDestinationId;
	}

	@JsonProperty("installation_origin")
	public String getInstallationOrigin() {
		return installationOrigin;
	}

	@JsonProperty("installation_origin")
	public void setInstallationOrigin(String installationOrigin) {
		this.installationOrigin = installationOrigin;
	}

	@JsonProperty("installation_origin_id")
	public Object getInstallationOriginId() {
		return installationOriginId;
	}

	@JsonProperty("installation_origin_id")
	public void setInstallationOriginId(Object installationOriginId) {
		this.installationOriginId = installationOriginId;
	}

	@JsonProperty("installation_destination")
	public String getInstallationDestination() {
		return installationDestination;
	}

	@JsonProperty("installation_destination")
	public void setInstallationDestination(String installationDestination) {
		this.installationDestination = installationDestination;
	}

	@JsonProperty("installation_destination_id")
	public Object getInstallationDestinationId() {
		return installationDestinationId;
	}

	@JsonProperty("installation_destination_id")
	public void setInstallationDestinationId(Object installationDestinationId) {
		this.installationDestinationId = installationDestinationId;
	}

	@JsonProperty("mileage")
	public Object getMileage() {
		return mileage;
	}

	@JsonProperty("mileage")
	public void setMileage(Object mileage) {
		this.mileage = mileage;
	}

	@JsonProperty("ton_miles")
	public Object getTonMiles() {
		return tonMiles;
	}

	@JsonProperty("ton_miles")
	public void setTonMiles(Object tonMiles) {
		this.tonMiles = tonMiles;
	}

	@JsonProperty("forecasted_origin")
	public String getForecastedOrigin() {
		return forecastedOrigin;
	}

	@JsonProperty("forecasted_origin")
	public void setForecastedOrigin(String forecastedOrigin) {
		this.forecastedOrigin = forecastedOrigin;
	}

	@JsonProperty("forecasted_origin_eta")
	public Object getForecastedOriginEta() {
		return forecastedOriginEta;
	}

	@JsonProperty("forecasted_origin_eta")
	public void setForecastedOriginEta(Object forecastedOriginEta) {
		this.forecastedOriginEta = forecastedOriginEta;
	}

	@JsonProperty("forecasted_origin_confidence")
	public Object getForecastedOriginConfidence() {
		return forecastedOriginConfidence;
	}

	@JsonProperty("forecasted_origin_confidence")
	public void setForecastedOriginConfidence(Object forecastedOriginConfidence) {
		this.forecastedOriginConfidence = forecastedOriginConfidence;
	}

	@JsonProperty("id_voyage")
	public Long getIdVoyage() {
		return idVoyage;
	}

	@JsonProperty("id_voyage")
	public void setIdVoyage(Long idVoyage) {
		this.idVoyage = idVoyage;
	}

	@JsonProperty("charterer")
	public String getCharterer() {
		return charterer;
	}

	@JsonProperty("charterer")
	public void setCharterer(String charterer) {
		this.charterer = charterer;
	}

}