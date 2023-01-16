package com.nissan.databeans;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.nissan.automation.core.utils.BDDDataTableUtil;
import com.nissan.enums.UserData;
import com.nissan.enums.VCATCheckpoint;
import com.nissan.utils.DVehicleReferenceDetailsUtil;
import com.nissan.utils.NAARulesUtil;
import com.nissan.utils.ROAPIUtil;
import com.nissan.utils.ROUtil;
import com.nissan.utils.TSBUtil;

public class TestDataBean {

	private String roNumber;
	
	private String user;

	private String caseID;
	
	private String gwCaseID;

	private String paymentAssumption;
	
	private String updatedAmount;
	
	private String roCaseID;
	
	public boolean isApprovedCoupon=false;
	
	private String couponId;
	
	private double oldPaidAmount;
	
	private String wlString;
	
	private Map<String, String> gWCostGrid;
	
	public String getUpdatedAmount() {
		return updatedAmount;
	}

	public void setUpdatedAmount(String updatedAmount) {
		this.updatedAmount = updatedAmount;
	}

	private double dCal;
	
	private double nissanShare; 
	
	private double nissanCouponAmt; 
	
	private double totalAmount;
	
	private double totalWLAmount;
	
	private String vCANRefNum;
	
	private String vCANPFPPO;

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getNissanShare() {
		return nissanShare;
	}

	public void setNissanShare(double nissanShare) {
		this.nissanShare = nissanShare;
	}
	
	public double getNissanCouponAmt() {
		return nissanCouponAmt;
	}

	public void setNissanCouponAmt(double nissanCouponAmt) {
		this.nissanCouponAmt = nissanCouponAmt;
	}

	public double getdCal() {
		return dCal;
	}
	
	
	public void setdCal(double dCal) {
		this.dCal = dCal;
	}

	private boolean isQAReferToPCC = false;

	public boolean isQAReferToPCC() {
		return isQAReferToPCC;
	}

	public void setQAReferToPCC(boolean isQAReferToPCC) {
		this.isQAReferToPCC = isQAReferToPCC;
	}

	public String getMileage() {
		/*if (Objects.isNull(mileage))
			setMileage(ROAPIUtil.getMileage());*/

		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getrOOpenDate() {
		if (Objects.isNull(rOOpenDate))
			setrOOpenDate(ROAPIUtil.getDateTimeString());
		return rOOpenDate;
	}

	public void setrOOpenDate(String rOOpenDate) {
		this.rOOpenDate = rOOpenDate;
	}

	private String mileage;

	private String rOOpenDate;

	private Map<String, String> vehicleRefDetails;

	private Map<String, String> vehicleWarranty;

	private Map<String, String> applicableWarranty;

	private SymptomsDataBean customerSymptom;

	private SymptomsDataBean technicianSymptom;

	private TSBDataBean tsbDataBean;
	
	private ESMDataBean esmDataBean;

	private NAARulesDataBean nAARulesDataBean;

	private Map<UserData, String> userData;

	public TSBDataBean getTsbDataBean() {
		return tsbDataBean;
	}

	public void setTsbDataBean(TSBDataBean tsbData) {
		this.tsbDataBean = tsbData;
	}

	public ESMDataBean getEsmDataBean() {
		return esmDataBean;
	}

	public void setEsmDataBean(ESMDataBean esmDataBean) {
		this.esmDataBean = esmDataBean;
	}

	public void setTsbDataBean(List<String> customerSymptomsSet, List<String> technicianSymptomsSet,
			Map<String, String> vehicleRefMap, boolean loginRequired, boolean logoutRequired) {
		this.tsbDataBean = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet, vehicleRefMap, loginRequired,
				logoutRequired);
	}

	public void setTsbDataBean(List<String> customerSymptomsSet, List<String> technicianSymptomsSet,
			Map<String, String> vehicleRefMap, String component, boolean loginRequired, boolean logoutRequired) {
		this.tsbDataBean = TSBUtil.evaluateTSB(customerSymptomsSet, technicianSymptomsSet, vehicleRefMap, component,
				loginRequired, logoutRequired);
	}

	public String getRoNumber() {
		return roNumber;
	}

	public void setRoNumber(String roNumber) {
		this.roNumber = roNumber;
	}

	
	public String getDCaseID() {
		return caseID;
	}

	public void setDCaseId(String caseId) {
		this.caseID = caseId;
	}
	
	public String getGWCaseID() {
		return gwCaseID;
	}

	public void setGWCaseId(String gwCaseID) {
		this.gwCaseID = gwCaseID;
	}

	public Map<String, String> getVehicleRefDetails() {
		return vehicleRefDetails;
	}

	public Map<String, String> getVehicleRefDetails(String vIN) {
		if(Objects.isNull(vehicleRefDetails))
			setVehicleRefDetails(DVehicleReferenceDetailsUtil.getVehicleReferenceMap(vIN));
		
		return vehicleRefDetails;
	}

	public void setVehicleRefDetails(String vIN) {

		setVehicleRefDetails(DVehicleReferenceDetailsUtil.getVehicleReferenceMap(vIN));

	}

	public void setVehicleRefDetails(Map<String, String> vehicleRefDetails) {
		this.vehicleRefDetails = vehicleRefDetails;
	}

	public Map<String, String> getVehicleWarranty() {
		return vehicleWarranty;
	}

	public void setVehicleWarranty(Map<String, String> vehicleWarranty) {
		this.vehicleWarranty = vehicleWarranty;
	}

	public Map<String, String> getApplicableWarranty() {
		return applicableWarranty;
	}

	public void setApplicableWarranty(Map<String, String> applicableWarranty) {
		this.applicableWarranty = applicableWarranty;
	}

	public SymptomsDataBean getCustomerSymptom() {
		if(Objects.isNull(customerSymptom)) {
			setCustomerSymptom();
		}
		
		return customerSymptom;
	}
	
	public void setCustomerSymptom() {
		this.customerSymptom= new SymptomsDataBean();
	}

	public void setCustomerSymptom(String checkboxesString, String dropDownString, String comments,
			String paymentAssumption) {

		this.customerSymptom = new SymptomsDataBean(checkboxesString, dropDownString, comments, this.getPaymentAssumption());

	}

	public SymptomsDataBean getTechnicianSymptom() {
		if(Objects.isNull(technicianSymptom)) {
			settechnicianSymptom();
		}
		return technicianSymptom;
	}
	
	public void settechnicianSymptom() {

		this.technicianSymptom = new SymptomsDataBean();

	}

	public void settechnicianSymptom(String checkboxesString, String dropDownString, String comments,
			String paymentAssumption) {

		this.technicianSymptom = new SymptomsDataBean(checkboxesString, dropDownString, comments, "");

	}

	public NAARulesDataBean getnAARulesDataBean() {
		return nAARulesDataBean;
	}

	public String getPaymentAssumption() {
		return this.getUserData().get(UserData.PAYMENTASSUMPTION);
	}

	public void setPaymentAssumption(String paymentAssumption) {
		this.paymentAssumption = paymentAssumption;
	}

	public void setnAARulesDataBean(String dealerCode, String component, String paymentAssumption,
			Map<String, String> vehicleRefMap, boolean paymentAssumptionChangedNWtoW,
			boolean techDisagrdSysRecmndRepair, boolean loginRequired, boolean logoutRequired) {

		Map<VCATCheckpoint, Boolean> nAARulesMap = new LinkedHashMap<>();
		nAARulesMap = NAARulesUtil.evaluateNAARules(dealerCode, component, paymentAssumption, vehicleRefMap,
				loginRequired, logoutRequired);
		nAARulesMap.put(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW, paymentAssumptionChangedNWtoW);
		nAARulesMap.put(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR, techDisagrdSysRecmndRepair);
		this.nAARulesDataBean = new NAARulesDataBean(nAARulesMap);

	}

	public void setnAARulesDataBean(Map<VCATCheckpoint, Boolean> nAARulesMap, boolean paymentAssumptionChangedNWtoW,
			boolean techDisagrdSysRecmndRepair) {

		nAARulesMap.put(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW, paymentAssumptionChangedNWtoW);
		nAARulesMap.put(VCATCheckpoint.TECHDISAGRDSYSRECMNDREPAIR, techDisagrdSysRecmndRepair);
		this.nAARulesDataBean = new NAARulesDataBean(nAARulesMap);

	}

	public void setnAARulesDataBean(String dealerCode, String component, String paymentAssumption,
			Map<String, String> vehicleRefMap, boolean paymentAssumptionChangedNWtoW, boolean loginRequired,
			boolean logoutRequired) {

		Map<VCATCheckpoint, Boolean> nAARulesMap = new LinkedHashMap<>();
		nAARulesMap = NAARulesUtil.evaluateNAARules(dealerCode, component, paymentAssumption, vehicleRefMap,
				loginRequired, logoutRequired);
		nAARulesMap.put(VCATCheckpoint.PAYMNTASSMPTNCHNGDNWTOW, paymentAssumptionChangedNWtoW);
		this.nAARulesDataBean = new NAARulesDataBean(nAARulesMap);

	}

	public Map<UserData, String> getUserData() {
		return this.userData;
	}

	public void setUserData(Map<String, String> userData) {
		if (userData.isEmpty()) {
			return;
		}
		Map<UserData, String> enumUserDataMap = new LinkedHashMap<>();

		Iterator<UserData> itr = Arrays.asList(UserData.values()).iterator();

		while (itr.hasNext()) {

			UserData field = itr.next();
			enumUserDataMap.put(field, userData.get(field.getFieldName()));

		}

		this.userData = enumUserDataMap;
	}

	public void addUserData(Map<String, String> inputUserData) {
		if (inputUserData.isEmpty()) {
			return;
		}
		
		if (Objects.isNull(getUserData())) {
 			setUserData(inputUserData);
			return;
		}
		
		//Map<UserData, String> enumUserDataMap = new LinkedHashMap<>();

		Iterator<UserData> itr = Arrays.asList(UserData.values()).iterator();

		while (itr.hasNext()) {

			UserData field = itr.next();
			String value = inputUserData.get(field.getFieldName());
			if (Objects.isNull(value)) {
				continue;
			}
			//enumUserDataMap.put(field, value);
			addUserData(field, value);
		}

		//this.userData.putAll(enumUserDataMap);

	}
	
	public void addUserData(io.cucumber.datatable.DataTable dataTable) {
		
		addUserData(BDDDataTableUtil.getAsMap(dataTable));
	}
	
	public void addUserData(UserData userData, String value) {
		
		this.getUserData().put(userData, value);
		
	}

	public TestDataBean() {

	}

	public TestDataBean(Map<String, String> data) {

		setUserData(data);
		setRoNumber(ROUtil.createRO(getUserData(), true, false));
		setVehicleRefDetails(
				DVehicleReferenceDetailsUtil.getVehicleReferenceMap(getUserData().get(UserData.VIN), false, true));
		setPaymentAssumption(getUserData().get(UserData.PAYMENTASSUMPTION));
		setCustomerSymptom(getUserData().get(UserData.CUSTOMERSYMPTOMCHECKBOXES),
				getUserData().get(UserData.CUSTOMERSYMPTOMDROPDOWNS), getUserData().get(UserData.CUSTOMEROTHERSYMPTOMS),
				getUserData().get(UserData.PAYMENTASSUMPTION));
		settechnicianSymptom(getUserData().get(UserData.TECHNICIANSYMPTOMCHECKBOXES),
				getUserData().get(UserData.TECHNICIANSYMPTOMDROPDOWNS),
				getUserData().get(UserData.TECHNICIANOTHERSYMPTOMS), "");
		setTsbDataBean(getCustomerSymptom().getSymptomSet(), getTechnicianSymptom().getSymptomSet(),
				getVehicleRefDetails(), true, false);
		setnAARulesDataBean(getUserData().get(UserData.DEALERCODE), getUserData().get(UserData.COMPONENT),
				getPaymentAssumption(), getVehicleRefDetails(), false,
				getUserData().get(UserData.CCCTECHNICIANDISAGREEDREPAIR).equalsIgnoreCase("yes"), false, true);

	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getVCANRefNum() {
		return vCANRefNum;
	}

	public void setVCANRefNum(String vCANRefNum) {
		this.vCANRefNum = vCANRefNum;
	}

	public String getVCANPFPPO() {
		return vCANPFPPO;
	}

	public void setVCANPFPPO(String vCANPFPPO) {
		this.vCANPFPPO = vCANPFPPO;
	}

	public String getROCaseID() {
		return roCaseID;
	}

	public void setROCaseID(String roCaseID) {
		this.roCaseID = roCaseID;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Map<String, String> getgWCostGrid() {
		return gWCostGrid;
	}

	public void setgWCostGrid(Map<String, String> gWCostGrid) {
		this.gWCostGrid = gWCostGrid;
	}

	public double getOldPaidAmount() {
		return oldPaidAmount;
	}

	public void setOldPaidAmount(double oldPaidAmount) {
		this.oldPaidAmount = oldPaidAmount;
	}

	public String getWLString() {
		return wlString;
	}

	public void setWLString(String wlString) {
		this.wlString = wlString;
	}

	public double getTotalWLAmount() {
		return totalWLAmount;
	}

	public void setTotalWLAmount(double totalWLAmount) {
		this.totalWLAmount = totalWLAmount;
	}

}
