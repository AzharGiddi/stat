package com.nissan.databeans;

import java.util.Map;

import com.nissan.enums.VCATCheckpoint;

public class NAARulesDataBean {

	private boolean paymntAssmptnChngdNWtoW;

	public boolean getPaymntAssmptnChngdNWtoW() {
		return paymntAssmptnChngdNWtoW;
	}

	public boolean getTechDisagrdSysRecmndRepair() {
		return techDisagrdSysRecmndRepair;
	}

	public String getArcDealer() {
		return arcDealer;
	}

	public String getEngineeringReview() {
		return engineeringReview;
	}

	public String getManufacturingDate() {
		return manufacturingDate;
	}

	public String getMileage() {
		return String.valueOf(mileage);
	}

	public String getMonthsInService() {
		return String.valueOf(monthsInService);
	}

	public String getVin() {
		return String.valueOf(vin);
	}

	public String getDealerSpecific() {
		return String.valueOf(dealerSpecific);
	}

	public String getAdditionalRules() {
		return String.valueOf(additionalRules);
	}

	public String getInServiceDateUnavailable() {
		return String.valueOf(inServiceDateUnavailable);
	}

	public String getServiceContractCoverage() {
		return String.valueOf(serviceContractCoverage);
	}

	public String getPartsWarrantyCoverage() {
		return String.valueOf(partsWarrantyCoverage);
	}

	public boolean isRouteToVCAT() {
		return routeToVCAT;
	}

	public boolean isRouteToNESNA() {
		return routeToNESNA;
	}

	private void setPaymntAssmptnChngdNWtoW(boolean paymntAssmptnChngdNWtoW) {
		this.paymntAssmptnChngdNWtoW = paymntAssmptnChngdNWtoW ;
	}

	public void setTechDisagrdSysRecmndRepair(boolean techDisagrdSysRecmndRepair) {
		this.techDisagrdSysRecmndRepair = techDisagrdSysRecmndRepair;
	}

	private void setArcDealer(boolean arcDealer) {
		this.arcDealer = String.valueOf(arcDealer);
	}

	private void setEngineeringReview(boolean engineeringReview) {
		this.engineeringReview = String.valueOf(engineeringReview);
	}

	private void setManufacturingDate(boolean manufacturingDate) {
		this.manufacturingDate = String.valueOf(manufacturingDate);
	}

	private void setMileage(boolean mileage) {
		this.mileage = mileage;
	}

	private void setMonthsInService(boolean monthsInService) {
		this.monthsInService = monthsInService;
	}

	private void setVin(boolean vin) {
		this.vin = vin;
	}

	private void setDealerSpecific(boolean dealerSpecific) {
		this.dealerSpecific = dealerSpecific;
	}

	private void setAdditionalRules(boolean additionalRules) {
		this.additionalRules = additionalRules;
	}

	private void setInServiceDateUnavailable(boolean inServiceDateUnavailable) {
		this.inServiceDateUnavailable = inServiceDateUnavailable;
	}

	private void setServiceContractCoverage(boolean serviceContractCoverage) {
		this.serviceContractCoverage = serviceContractCoverage;
	}

	private void setPartsWarrantyCoverage(boolean partsWarrantyCoverage) {
		this.partsWarrantyCoverage = partsWarrantyCoverage;
	}

	public void setRouteToVCAT(boolean routeToVCAT) {
		this.routeToVCAT = routeToVCAT;
	}

	public void setRouteToNESNA(boolean routeToNESNA) {
		this.routeToNESNA = routeToNESNA;
	}

	private boolean techDisagrdSysRecmndRepair;

	private String arcDealer;

	private String engineeringReview;

	private String manufacturingDate;

	private boolean mileage;

	private boolean monthsInService;

	private boolean vin;

	private boolean dealerSpecific;

	private boolean additionalRules;

	private boolean inServiceDateUnavailable;

	private boolean serviceContractCoverage;

	private boolean partsWarrantyCoverage;

	private boolean routeToVCAT;

	private boolean routeToNESNA;

	//

	public NAARulesDataBean() {

	}

	public NAARulesDataBean(Map<VCATCheckpoint, Boolean> rulesMap) {

		setPaymntAssmptnChngdNWtoW(rulesMap.get(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW));
		setTechDisagrdSysRecmndRepair(rulesMap.get(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR));
		setArcDealer(rulesMap.get(VCATCheckpoint.ARCDEALER));
		setEngineeringReview(rulesMap.get(VCATCheckpoint.ENGINEERINGREVIEW));
		setManufacturingDate(rulesMap.get(VCATCheckpoint.MANUFACTURINGDATE));
		setMileage(rulesMap.get(VCATCheckpoint.MILEAGE));
		setMonthsInService(rulesMap.get(VCATCheckpoint.MONTHSINSERVICE));
		setVin(rulesMap.get(VCATCheckpoint.VIN));
		setDealerSpecific(rulesMap.get(VCATCheckpoint.DEALERSPECIFIC));
		setAdditionalRules(rulesMap.get(VCATCheckpoint.ADDITIONALRULES));
		setInServiceDateUnavailable(rulesMap.get(VCATCheckpoint.INSERVICEDATEUNAVAILABLE));
		setServiceContractCoverage(rulesMap.get(VCATCheckpoint.SERVICECONTRACTCOVERAGE));
		setPartsWarrantyCoverage(rulesMap.get(VCATCheckpoint.PARTSWARRANTYCOVERAGE));
		setRouteToVCAT(rulesMap.get(VCATCheckpoint.ROUTETOVCAT));
		setRouteToNESNA(rulesMap.get(VCATCheckpoint.ROUTETONESNA));

	}

}
