
package com.equinor.cargotrackerreference.kpler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "forecasteddestination", "vesseltype", "vessel", "cargotons", "product", "tradestatus",
		"etadestination", "installationorigin", "installationdestination", "countrydestination", "sellerorigin",
		"buyerdestination" })
@JsonIgnoreProperties({ "grade", "group", "family", "dateorigin", "origin", "etasourceorigin", "reloadstspartialorigin",
		"datedestination", "destination", "etasourcedestination", "reloadstspartialdestination", "forecastedeta",
		"forecastedconfidence", "exportprice", "numberoftradesexport", "importprice", "numberoftradesimport",
		"intermediaries", "countryorigin", "subcontinentorigin", "continentorigin", "etaorigin", "startorigin",
		"endorigin", "subcontinentdestination", "continentdestination", "startdestination", "enddestination",
		"capacityvesselm3", "cargotypevessel", "idvessel", "link1delivery", "link1type", "link1sellername",
		"link1sellercountry", "link1buyername", "link1buyercountry", "link2delivery", "link2type", "link2sellername",
		"link2sellercountry", "link2buyername", "link2buyercountry", "link3delivery", "link3type", "link3sellername",
		"link3sellercountry", "link3buyername", "link3buyercountry", "link4delivery", "link4type", "link4sellername",
		"link4sellercountry", "link4buyername", "link4buyercountry", "link5delivery", "link5type", "link5sellername",
		"link5sellercountry", "link5buyername", "link5buyercountry", "idtrade", "zoneorigin", "zoneoriginid",
		"zonedestination", "zonedestinationid", "installationoriginid", "installationdestinationid", "mileage",
		"tonmiles", "forecastedorigin", "forecastedorigineta", "forecastedoriginconfidence", "idvoyage", "charterer" })
public class Trade {

	@JsonProperty("forecasteddestination")
	private String forecasteddestination;
	@JsonProperty("vesseltype")
	private String vesseltype;
	@JsonProperty("vessel")
	private String vessel;
	@JsonProperty("cargotons")
	private Integer cargotons;
	@JsonProperty("product")
	private String product;
	@JsonProperty("tradestatus")
	private String tradestatus;
	@JsonProperty("etadestination")
	private Object etadestination;
	@JsonProperty("installationorigin")
	private String installationorigin;
	@JsonProperty("installationdestination")
	private String installationdestination;
	@JsonProperty("countrydestination")
	private String countrydestination;
	@JsonProperty("sellerorigin")
	private String sellerorigin;
	@JsonProperty("buyerdestination")
	private String buyerdestination;

	/*@JsonProperty("grade")
	private String grade;
	@JsonProperty("group")
	private String group;
	@JsonProperty("family")
	private String family;
	@JsonProperty("dateorigin")
	private Integer dateorigin;
	@JsonProperty("origin")
	private String origin;
	@JsonProperty("etasourceorigin")
	private String etasourceorigin;
	@JsonProperty("reloadstspartialorigin")
	private String reloadstspartialorigin;
	@JsonProperty("datedestination")
	private Object datedestination;
	@JsonProperty("destination")
	private String destination;
	@JsonProperty("etasourcedestination")
	private String etasourcedestination;
	@JsonProperty("reloadstspartialdestination")
	private String reloadstspartialdestination;
	@JsonProperty("forecastedeta")
	private Integer forecastedeta;
	@JsonProperty("forecastedconfidence")
	private Double forecastedconfidence;
	@JsonProperty("exportprice")
	private String exportprice;
	@JsonProperty("numberoftradesexport")
	private String numberoftradesexport;
	@JsonProperty("importprice")
	private String importprice;
	@JsonProperty("numberoftradesimport")
	private String numberoftradesimport;
	@JsonProperty("intermediaries")
	private Object intermediaries;
	@JsonProperty("countryorigin")
	private String countryorigin;
	@JsonProperty("subcontinentorigin")
	private String subcontinentorigin;
	@JsonProperty("continentorigin")
	private String continentorigin;
	@JsonProperty("etaorigin")
	private Object etaorigin;
	@JsonProperty("startorigin")
	private Object startorigin;
	@JsonProperty("endorigin")
	private Object endorigin;
	@JsonProperty("subcontinentdestination")
	private String subcontinentdestination;
	@JsonProperty("continentdestination")
	private String continentdestination;
	@JsonProperty("startdestination")
	private Object startdestination;
	@JsonProperty("enddestination")
	private Object enddestination;
	@JsonProperty("capacityvesselm3")
	private Integer capacityvesselm3;
	@JsonProperty("cargotypevessel")
	private String cargotypevessel;
	@JsonProperty("idvessel")
	private Integer idvessel;
	@JsonProperty("link1delivery")
	private String link1delivery;
	@JsonProperty("link1type")
	private String link1type;
	@JsonProperty("link1sellername")
	private String link1sellername;
	@JsonProperty("link1sellercountry")
	private String link1sellercountry;
	@JsonProperty("link1buyername")
	private String link1buyername;
	@JsonProperty("link1buyercountry")
	private String link1buyercountry;
	@JsonProperty("link2delivery")
	private String link2delivery;
	@JsonProperty("link2type")
	private String link2type;
	@JsonProperty("link2sellername")
	private String link2sellername;
	@JsonProperty("link2sellercountry")
	private String link2sellercountry;
	@JsonProperty("link2buyername")
	private String link2buyername;
	@JsonProperty("link2buyercountry")
	private String link2buyercountry;
	@JsonProperty("link3delivery")
	private String link3delivery;
	@JsonProperty("link3type")
	private String link3type;
	@JsonProperty("link3sellername")
	private String link3sellername;
	@JsonProperty("link3sellercountry")
	private String link3sellercountry;
	@JsonProperty("link3buyername")
	private String link3buyername;
	@JsonProperty("link3buyercountry")
	private String link3buyercountry;
	@JsonProperty("link4delivery")
	private String link4delivery;
	@JsonProperty("link4type")
	private String link4type;
	@JsonProperty("link4sellername")
	private String link4sellername;
	@JsonProperty("link4sellercountry")
	private String link4sellercountry;
	@JsonProperty("link4buyername")
	private String link4buyername;
	@JsonProperty("link4buyercountry")
	private String link4buyercountry;
	@JsonProperty("link5delivery")
	private String link5delivery;
	@JsonProperty("link5type")
	private String link5type;
	@JsonProperty("link5sellername")
	private String link5sellername;
	@JsonProperty("link5sellercountry")
	private String link5sellercountry;
	@JsonProperty("link5buyername")
	private String link5buyername;
	@JsonProperty("link5buyercountry")
	private String link5buyercountry;
	@JsonProperty("idtrade")
	private Integer idtrade;
	@JsonProperty("zoneorigin")
	private String zoneorigin;
	@JsonProperty("zoneoriginid")
	private Object zoneoriginid;
	@JsonProperty("zonedestination")
	private String zonedestination;
	@JsonProperty("zonedestinationid")
	private Object zonedestinationid;
	@JsonProperty("installationoriginid")
	private Object installationoriginid;
	@JsonProperty("installationdestinationid")
	private Object installationdestinationid;
	@JsonProperty("mileage")
	private Object mileage;
	@JsonProperty("tonmiles")
	private Object tonmiles;
	@JsonProperty("forecastedorigin")
	private String forecastedorigin;
	@JsonProperty("forecastedorigineta")
	private Integer forecastedorigineta;
	@JsonProperty("forecastedoriginconfidence")
	private Double forecastedoriginconfidence;
	@JsonProperty("idvoyage")
	private Integer idvoyage;
	@JsonProperty("charterer")
	private String charterer;*/

	@JsonProperty("forecasteddestination")
	public String getForecasteddestination() {
		return forecasteddestination;
	}

	@JsonProperty("forecasteddestination")
	public void setForecasteddestination(String forecasteddestination) {
		this.forecasteddestination = forecasteddestination;
	}

	@JsonProperty("vesseltype")
	public String getVesseltype() {
		return vesseltype;
	}

	@JsonProperty("vesseltype")
	public void setVesseltype(String vesseltype) {
		this.vesseltype = vesseltype;
	}

	@JsonProperty("vessel")
	public String getVessel() {
		return vessel;
	}

	@JsonProperty("vessel")
	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	@JsonProperty("cargotons")
	public Integer getCargotons() {
		return cargotons;
	}

	@JsonProperty("cargotons")
	public void setCargotons(Integer cargotons) {
		this.cargotons = cargotons;
	}

	@JsonProperty("product")
	public String getProduct() {
		return product;
	}

	@JsonProperty("product")
	public void setProduct(String product) {
		this.product = product;
	}

	@JsonProperty("tradestatus")
	public String getTradestatus() {
		return tradestatus;
	}

	@JsonProperty("tradestatus")
	public void setTradestatus(String tradestatus) {
		this.tradestatus = tradestatus;
	}

	@JsonProperty("etadestination")
	public Object getEtadestination() {
		return etadestination;
	}

	@JsonProperty("etadestination")
	public void setEtadestination(Object etadestination) {
		this.etadestination = etadestination;
	}

	@JsonProperty("installationorigin")
	public String getInstallationorigin() {
		return installationorigin;
	}

	@JsonProperty("installationorigin")
	public void setInstallationorigin(String installationorigin) {
		this.installationorigin = installationorigin;
	}

	@JsonProperty("installationdestination")
	public String getInstallationdestination() {
		return installationdestination;
	}

	@JsonProperty("installationdestination")
	public void setInstallationdestination(String installationdestination) {
		this.installationdestination = installationdestination;
	}

	@JsonProperty("countrydestination")
	public String getCountrydestination() {
		return countrydestination;
	}

	@JsonProperty("countrydestination")
	public void setCountrydestination(String countrydestination) {
		this.countrydestination = countrydestination;
	}

	@JsonProperty("sellerorigin")
	public String getSellerorigin() {
		return sellerorigin;
	}

	@JsonProperty("sellerorigin")
	public void setSellerorigin(String sellerorigin) {
		this.sellerorigin = sellerorigin;
	}

	@JsonProperty("buyerdestination")
	public String getBuyerdestination() {
		return buyerdestination;
	}

	@JsonProperty("buyerdestination")
	public void setBuyerdestination(String buyerdestination) {
		this.buyerdestination = buyerdestination;
	}

	/*
	@JsonProperty("grade")
	public String getGrade() {
		return grade;
	}

	@JsonProperty("grade")
	public void setGrade(String grade) {
		this.grade = grade;
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

	@JsonProperty("dateorigin")
	public Integer getDateorigin() {
		return dateorigin;
	}

	@JsonProperty("dateorigin")
	public void setDateorigin(Integer dateorigin) {
		this.dateorigin = dateorigin;
	}

	@JsonProperty("origin")
	public String getOrigin() {
		return origin;
	}

	@JsonProperty("origin")
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@JsonProperty("etasourceorigin")
	public String getEtasourceorigin() {
		return etasourceorigin;
	}

	@JsonProperty("etasourceorigin")
	public void setEtasourceorigin(String etasourceorigin) {
		this.etasourceorigin = etasourceorigin;
	}

	@JsonProperty("reloadstspartialorigin")
	public String getReloadstspartialorigin() {
		return reloadstspartialorigin;
	}

	@JsonProperty("reloadstspartialorigin")
	public void setReloadstspartialorigin(String reloadstspartialorigin) {
		this.reloadstspartialorigin = reloadstspartialorigin;
	}

	@JsonProperty("datedestination")
	public Object getDatedestination() {
		return datedestination;
	}

	@JsonProperty("datedestination")
	public void setDatedestination(Object datedestination) {
		this.datedestination = datedestination;
	}

	@JsonProperty("destination")
	public String getDestination() {
		return destination;
	}

	@JsonProperty("destination")
	public void setDestination(String destination) {
		this.destination = destination;
	}

	@JsonProperty("etasourcedestination")
	public String getEtasourcedestination() {
		return etasourcedestination;
	}

	@JsonProperty("etasourcedestination")
	public void setEtasourcedestination(String etasourcedestination) {
		this.etasourcedestination = etasourcedestination;
	}

	@JsonProperty("reloadstspartialdestination")
	public String getReloadstspartialdestination() {
		return reloadstspartialdestination;
	}

	@JsonProperty("reloadstspartialdestination")
	public void setReloadstspartialdestination(String reloadstspartialdestination) {
		this.reloadstspartialdestination = reloadstspartialdestination;
	}

	@JsonProperty("forecastedeta")
	public Integer getForecastedeta() {
		return forecastedeta;
	}

	@JsonProperty("forecastedeta")
	public void setForecastedeta(Integer forecastedeta) {
		this.forecastedeta = forecastedeta;
	}

	@JsonProperty("forecastedconfidence")
	public Double getForecastedconfidence() {
		return forecastedconfidence;
	}

	@JsonProperty("forecastedconfidence")
	public void setForecastedconfidence(Double forecastedconfidence) {
		this.forecastedconfidence = forecastedconfidence;
	}

	@JsonProperty("exportprice")
	public String getExportprice() {
		return exportprice;
	}

	@JsonProperty("exportprice")
	public void setExportprice(String exportprice) {
		this.exportprice = exportprice;
	}

	@JsonProperty("numberoftradesexport")
	public String getNumberoftradesexport() {
		return numberoftradesexport;
	}

	@JsonProperty("numberoftradesexport")
	public void setNumberoftradesexport(String numberoftradesexport) {
		this.numberoftradesexport = numberoftradesexport;
	}

	@JsonProperty("importprice")
	public String getImportprice() {
		return importprice;
	}

	@JsonProperty("importprice")
	public void setImportprice(String importprice) {
		this.importprice = importprice;
	}

	@JsonProperty("numberoftradesimport")
	public String getNumberoftradesimport() {
		return numberoftradesimport;
	}

	@JsonProperty("numberoftradesimport")
	public void setNumberoftradesimport(String numberoftradesimport) {
		this.numberoftradesimport = numberoftradesimport;
	}

	@JsonProperty("intermediaries")
	public Object getIntermediaries() {
		return intermediaries;
	}

	@JsonProperty("intermediaries")
	public void setIntermediaries(Object intermediaries) {
		this.intermediaries = intermediaries;
	}

	@JsonProperty("countryorigin")
	public String getCountryorigin() {
		return countryorigin;
	}

	@JsonProperty("countryorigin")
	public void setCountryorigin(String countryorigin) {
		this.countryorigin = countryorigin;
	}

	@JsonProperty("subcontinentorigin")
	public String getSubcontinentorigin() {
		return subcontinentorigin;
	}

	@JsonProperty("subcontinentorigin")
	public void setSubcontinentorigin(String subcontinentorigin) {
		this.subcontinentorigin = subcontinentorigin;
	}

	@JsonProperty("continentorigin")
	public String getContinentorigin() {
		return continentorigin;
	}

	@JsonProperty("continentorigin")
	public void setContinentorigin(String continentorigin) {
		this.continentorigin = continentorigin;
	}

	@JsonProperty("etaorigin")
	public Object getEtaorigin() {
		return etaorigin;
	}

	@JsonProperty("etaorigin")
	public void setEtaorigin(Object etaorigin) {
		this.etaorigin = etaorigin;
	}

	@JsonProperty("startorigin")
	public Object getStartorigin() {
		return startorigin;
	}

	@JsonProperty("startorigin")
	public void setStartorigin(Object startorigin) {
		this.startorigin = startorigin;
	}

	@JsonProperty("endorigin")
	public Object getEndorigin() {
		return endorigin;
	}

	@JsonProperty("endorigin")
	public void setEndorigin(Object endorigin) {
		this.endorigin = endorigin;
	}

	@JsonProperty("subcontinentdestination")
	public String getSubcontinentdestination() {
		return subcontinentdestination;
	}

	@JsonProperty("subcontinentdestination")
	public void setSubcontinentdestination(String subcontinentdestination) {
		this.subcontinentdestination = subcontinentdestination;
	}

	@JsonProperty("continentdestination")
	public String getContinentdestination() {
		return continentdestination;
	}

	@JsonProperty("continentdestination")
	public void setContinentdestination(String continentdestination) {
		this.continentdestination = continentdestination;
	}

	@JsonProperty("startdestination")
	public Object getStartdestination() {
		return startdestination;
	}

	@JsonProperty("startdestination")
	public void setStartdestination(Object startdestination) {
		this.startdestination = startdestination;
	}

	@JsonProperty("enddestination")
	public Object getEnddestination() {
		return enddestination;
	}

	@JsonProperty("enddestination")
	public void setEnddestination(Object enddestination) {
		this.enddestination = enddestination;
	}

	@JsonProperty("capacityvesselm3")
	public Integer getCapacityvesselm3() {
		return capacityvesselm3;
	}

	@JsonProperty("capacityvesselm3")
	public void setCapacityvesselm3(Integer capacityvesselm3) {
		this.capacityvesselm3 = capacityvesselm3;
	}

	@JsonProperty("cargotypevessel")
	public String getCargotypevessel() {
		return cargotypevessel;
	}

	@JsonProperty("cargotypevessel")
	public void setCargotypevessel(String cargotypevessel) {
		this.cargotypevessel = cargotypevessel;
	}

	@JsonProperty("idvessel")
	public Integer getIdvessel() {
		return idvessel;
	}

	@JsonProperty("idvessel")
	public void setIdvessel(Integer idvessel) {
		this.idvessel = idvessel;
	}

	@JsonProperty("link1delivery")
	public String getLink1delivery() {
		return link1delivery;
	}

	@JsonProperty("link1delivery")
	public void setLink1delivery(String link1delivery) {
		this.link1delivery = link1delivery;
	}

	@JsonProperty("link1type")
	public String getLink1type() {
		return link1type;
	}

	@JsonProperty("link1type")
	public void setLink1type(String link1type) {
		this.link1type = link1type;
	}

	@JsonProperty("link1sellername")
	public String getLink1sellername() {
		return link1sellername;
	}

	@JsonProperty("link1sellername")
	public void setLink1sellername(String link1sellername) {
		this.link1sellername = link1sellername;
	}

	@JsonProperty("link1sellercountry")
	public String getLink1sellercountry() {
		return link1sellercountry;
	}

	@JsonProperty("link1sellercountry")
	public void setLink1sellercountry(String link1sellercountry) {
		this.link1sellercountry = link1sellercountry;
	}

	@JsonProperty("link1buyername")
	public String getLink1buyername() {
		return link1buyername;
	}

	@JsonProperty("link1buyername")
	public void setLink1buyername(String link1buyername) {
		this.link1buyername = link1buyername;
	}

	@JsonProperty("link1buyercountry")
	public String getLink1buyercountry() {
		return link1buyercountry;
	}

	@JsonProperty("link1buyercountry")
	public void setLink1buyercountry(String link1buyercountry) {
		this.link1buyercountry = link1buyercountry;
	}

	@JsonProperty("link2delivery")
	public String getLink2delivery() {
		return link2delivery;
	}

	@JsonProperty("link2delivery")
	public void setLink2delivery(String link2delivery) {
		this.link2delivery = link2delivery;
	}

	@JsonProperty("link2type")
	public String getLink2type() {
		return link2type;
	}

	@JsonProperty("link2type")
	public void setLink2type(String link2type) {
		this.link2type = link2type;
	}

	@JsonProperty("link2sellername")
	public String getLink2sellername() {
		return link2sellername;
	}

	@JsonProperty("link2sellername")
	public void setLink2sellername(String link2sellername) {
		this.link2sellername = link2sellername;
	}

	@JsonProperty("link2sellercountry")
	public String getLink2sellercountry() {
		return link2sellercountry;
	}

	@JsonProperty("link2sellercountry")
	public void setLink2sellercountry(String link2sellercountry) {
		this.link2sellercountry = link2sellercountry;
	}

	@JsonProperty("link2buyername")
	public String getLink2buyername() {
		return link2buyername;
	}

	@JsonProperty("link2buyername")
	public void setLink2buyername(String link2buyername) {
		this.link2buyername = link2buyername;
	}

	@JsonProperty("link2buyercountry")
	public String getLink2buyercountry() {
		return link2buyercountry;
	}

	@JsonProperty("link2buyercountry")
	public void setLink2buyercountry(String link2buyercountry) {
		this.link2buyercountry = link2buyercountry;
	}

	@JsonProperty("link3delivery")
	public String getLink3delivery() {
		return link3delivery;
	}

	@JsonProperty("link3delivery")
	public void setLink3delivery(String link3delivery) {
		this.link3delivery = link3delivery;
	}

	@JsonProperty("link3type")
	public String getLink3type() {
		return link3type;
	}

	@JsonProperty("link3type")
	public void setLink3type(String link3type) {
		this.link3type = link3type;
	}

	@JsonProperty("link3sellername")
	public String getLink3sellername() {
		return link3sellername;
	}

	@JsonProperty("link3sellername")
	public void setLink3sellername(String link3sellername) {
		this.link3sellername = link3sellername;
	}

	@JsonProperty("link3sellercountry")
	public String getLink3sellercountry() {
		return link3sellercountry;
	}

	@JsonProperty("link3sellercountry")
	public void setLink3sellercountry(String link3sellercountry) {
		this.link3sellercountry = link3sellercountry;
	}

	@JsonProperty("link3buyername")
	public String getLink3buyername() {
		return link3buyername;
	}

	@JsonProperty("link3buyername")
	public void setLink3buyername(String link3buyername) {
		this.link3buyername = link3buyername;
	}

	@JsonProperty("link3buyercountry")
	public String getLink3buyercountry() {
		return link3buyercountry;
	}

	@JsonProperty("link3buyercountry")
	public void setLink3buyercountry(String link3buyercountry) {
		this.link3buyercountry = link3buyercountry;
	}

	@JsonProperty("link4delivery")
	public String getLink4delivery() {
		return link4delivery;
	}

	@JsonProperty("link4delivery")
	public void setLink4delivery(String link4delivery) {
		this.link4delivery = link4delivery;
	}

	@JsonProperty("link4type")
	public String getLink4type() {
		return link4type;
	}

	@JsonProperty("link4type")
	public void setLink4type(String link4type) {
		this.link4type = link4type;
	}

	@JsonProperty("link4sellername")
	public String getLink4sellername() {
		return link4sellername;
	}

	@JsonProperty("link4sellername")
	public void setLink4sellername(String link4sellername) {
		this.link4sellername = link4sellername;
	}

	@JsonProperty("link4sellercountry")
	public String getLink4sellercountry() {
		return link4sellercountry;
	}

	@JsonProperty("link4sellercountry")
	public void setLink4sellercountry(String link4sellercountry) {
		this.link4sellercountry = link4sellercountry;
	}

	@JsonProperty("link4buyername")
	public String getLink4buyername() {
		return link4buyername;
	}

	@JsonProperty("link4buyername")
	public void setLink4buyername(String link4buyername) {
		this.link4buyername = link4buyername;
	}

	@JsonProperty("link4buyercountry")
	public String getLink4buyercountry() {
		return link4buyercountry;
	}

	@JsonProperty("link4buyercountry")
	public void setLink4buyercountry(String link4buyercountry) {
		this.link4buyercountry = link4buyercountry;
	}

	@JsonProperty("link5delivery")
	public String getLink5delivery() {
		return link5delivery;
	}

	@JsonProperty("link5delivery")
	public void setLink5delivery(String link5delivery) {
		this.link5delivery = link5delivery;
	}

	@JsonProperty("link5type")
	public String getLink5type() {
		return link5type;
	}

	@JsonProperty("link5type")
	public void setLink5type(String link5type) {
		this.link5type = link5type;
	}

	@JsonProperty("link5sellername")
	public String getLink5sellername() {
		return link5sellername;
	}

	@JsonProperty("link5sellername")
	public void setLink5sellername(String link5sellername) {
		this.link5sellername = link5sellername;
	}

	@JsonProperty("link5sellercountry")
	public String getLink5sellercountry() {
		return link5sellercountry;
	}

	@JsonProperty("link5sellercountry")
	public void setLink5sellercountry(String link5sellercountry) {
		this.link5sellercountry = link5sellercountry;
	}

	@JsonProperty("link5buyername")
	public String getLink5buyername() {
		return link5buyername;
	}

	@JsonProperty("link5buyername")
	public void setLink5buyername(String link5buyername) {
		this.link5buyername = link5buyername;
	}

	@JsonProperty("link5buyercountry")
	public String getLink5buyercountry() {
		return link5buyercountry;
	}

	@JsonProperty("link5buyercountry")
	public void setLink5buyercountry(String link5buyercountry) {
		this.link5buyercountry = link5buyercountry;
	}

	@JsonProperty("idtrade")
	public Integer getIdtrade() {
		return idtrade;
	}

	@JsonProperty("idtrade")
	public void setIdtrade(Integer idtrade) {
		this.idtrade = idtrade;
	}

	@JsonProperty("zoneorigin")
	public String getZoneorigin() {
		return zoneorigin;
	}

	@JsonProperty("zoneorigin")
	public void setZoneorigin(String zoneorigin) {
		this.zoneorigin = zoneorigin;
	}

	@JsonProperty("zoneoriginid")
	public Object getZoneoriginid() {
		return zoneoriginid;
	}

	@JsonProperty("zoneoriginid")
	public void setZoneoriginid(Object zoneoriginid) {
		this.zoneoriginid = zoneoriginid;
	}

	@JsonProperty("zonedestination")
	public String getZonedestination() {
		return zonedestination;
	}

	@JsonProperty("zonedestination")
	public void setZonedestination(String zonedestination) {
		this.zonedestination = zonedestination;
	}

	@JsonProperty("zonedestinationid")
	public Object getZonedestinationid() {
		return zonedestinationid;
	}

	@JsonProperty("zonedestinationid")
	public void setZonedestinationid(Object zonedestinationid) {
		this.zonedestinationid = zonedestinationid;
	}

	@JsonProperty("installationoriginid")
	public Object getInstallationoriginid() {
		return installationoriginid;
	}

	@JsonProperty("installationoriginid")
	public void setInstallationoriginid(Object installationoriginid) {
		this.installationoriginid = installationoriginid;
	}

	@JsonProperty("installationdestinationid")
	public Object getInstallationdestinationid() {
		return installationdestinationid;
	}

	@JsonProperty("installationdestinationid")
	public void setInstallationdestinationid(Object installationdestinationid) {
		this.installationdestinationid = installationdestinationid;
	}

	@JsonProperty("mileage")
	public Object getMileage() {
		return mileage;
	}

	@JsonProperty("mileage")
	public void setMileage(Object mileage) {
		this.mileage = mileage;
	}

	@JsonProperty("tonmiles")
	public Object getTonmiles() {
		return tonmiles;
	}

	@JsonProperty("tonmiles")
	public void setTonmiles(Object tonmiles) {
		this.tonmiles = tonmiles;
	}

	@JsonProperty("forecastedorigin")
	public String getForecastedorigin() {
		return forecastedorigin;
	}

	@JsonProperty("forecastedorigin")
	public void setForecastedorigin(String forecastedorigin) {
		this.forecastedorigin = forecastedorigin;
	}

	@JsonProperty("forecastedorigineta")
	public Integer getForecastedorigineta() {
		return forecastedorigineta;
	}

	@JsonProperty("forecastedorigineta")
	public void setForecastedorigineta(Integer forecastedorigineta) {
		this.forecastedorigineta = forecastedorigineta;
	}

	@JsonProperty("forecastedoriginconfidence")
	public Double getForecastedoriginconfidence() {
		return forecastedoriginconfidence;
	}

	@JsonProperty("forecastedoriginconfidence")
	public void setForecastedoriginconfidence(Double forecastedoriginconfidence) {
		this.forecastedoriginconfidence = forecastedoriginconfidence;
	}

	@JsonProperty("idvoyage")
	public Integer getIdvoyage() {
		return idvoyage;
	}

	@JsonProperty("idvoyage")
	public void setIdvoyage(Integer idvoyage) {
		this.idvoyage = idvoyage;
	}

	@JsonProperty("charterer")
	public String getCharterer() {
		return charterer;
	}

	@JsonProperty("charterer")
	public void setCharterer(String charterer) {
		this.charterer = charterer;
	}*/

}