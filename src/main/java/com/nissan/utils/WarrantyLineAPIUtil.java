package com.nissan.utils;

import static com.nissan.driver.DriverManager.getDriver;

import java.text.DecimalFormat;
import java.util.Objects;

import org.testng.Assert;

import com.nissan.WebElement.ExtWebComponent;
import com.nissan.automation.core.utils.XMLUtil;
import com.nissan.configuration.ConfigurationManager;
import com.nissan.databeans.TestDataBean;
import com.nissan.databeans.TestDataBeanManager;
import com.nissan.enums.UserData;
import com.nissan.exceptions.CustomRuntimeException;
import com.nissan.reports.ExtentLogger;

public class WarrantyLineAPIUtil {

	private WarrantyLineAPIUtil() {

	}

	/*private static String wLString;
	private static double totalPaidAmount;
	private static double oldPaidAmount;*/

	public static double getTotalPaidAmount() {
		//return totalPaidAmount;
		return TestDataBeanManager.getTestData().getTotalWLAmount();
		
		
	}

	public static void setTotalPaidAmount(double totalPaidAmount) {
		//WarrantyLineAPIUtil.totalPaidAmount = totalPaidAmount;
		TestDataBeanManager.getTestData().setTotalWLAmount(totalPaidAmount);
	}
	
	public static double getOldPaidAmount() {
		//return oldPaidAmount;
		return TestDataBeanManager.getTestData().getOldPaidAmount();
	}

	public static void setOldPaidAmount(double oldPaidAmount) {
		//WarrantyLineAPIUtil.oldPaidAmount = oldPaidAmount;
		TestDataBeanManager.getTestData().setOldPaidAmount(oldPaidAmount);
	}

	public static void submitWarrantyLine(TestDataBean testData) {

		String statusCode = testData.getUserData().get(UserData.STATUSCODE);
		if (statusCode.equalsIgnoreCase("SU")) {
			String vin = testData.getUserData().get(UserData.VIN);
			String dealerCode = testData.getUserData().get(UserData.DEALERCODE);
			String pFP = testData.getUserData().get(UserData.PFP);
			String opCode = testData.getUserData().get(UserData.OPCODE);
			String timeString = ROAPIUtil.getDateTimeString();
			String coverageCode = "FG";
			String rol = testData.getUserData().get(UserData.ROL);
			boolean nissanGTDcal = Boolean.parseBoolean(testData.getUserData().get(UserData.NISSANAMOUNTGTDCAL));
			boolean wLGTNissanAmount = Boolean.parseBoolean(testData.getUserData().get(UserData.WLGTNISSANAMOUNT));
			boolean wLGTDCAL = Boolean.parseBoolean(testData.getUserData().get(UserData.WLGTDCAL));
			double totalWLAmount = 0;
			double nissanAmount = testData.isApprovedCoupon?testData.getNissanCouponAmt():testData.getNissanShare();
			totalWLAmount = getGreater(wLGTNissanAmount,totalWLAmount,nissanAmount);
			//only applicable  when nissan amount is less than dcal
			if(!nissanGTDcal) {
				double dCal = testData.getdCal();
				totalWLAmount = getGreater(wLGTDCAL,totalWLAmount,dCal);
			}
			//total paid amount, sum of Exp+labor+part amounts on warranty line
			setTotalPaidAmount(totalWLAmount);
			setOldPaidAmount(getTotalPaidAmount());
			double wLAmount =  getTotalPaidAmount()/ 3;
			String formattedWLAMount = new DecimalFormat("#.00").format(wLAmount);
			double perShareAmount = testData.getTotalAmount()/3;
			String formattedPerShareAmount = new DecimalFormat("#.00").format(perShareAmount);
			ExtentLogger.info(String.format("Amount Deatils before WL submission, TotalWLAmount : %f, WLSharePerAmountType : %s, NissanAmount : %f, DcalAmount : %f",getTotalPaidAmount(), formattedWLAMount,nissanAmount,testData.getdCal()), true);
			String expenseAmount = formattedPerShareAmount;
			String laborAmount = formattedPerShareAmount;
			String partAmount = formattedPerShareAmount;
			String expReqCoverageAmount = formattedWLAMount;
			String laborReqCoverageAmount = formattedWLAMount;
			String partReqCoverageAmount = formattedWLAMount;
			String rONumber = ROAPIUtil.getRONumber();
			String wLXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("warrantyxml.path");
			String wLString = XMLUtil.getXMLString(wLXmlPath).replace("%STATUS_CODE%", statusCode)
					.replace("%DEALER_CODE%", dealerCode).replace("%RONUMBER%", rONumber)
					.replace("%ROOPENDATE%", timeString).replace("%VIN%", vin).replace("%coverage_code%", coverageCode)
					.replace("%PFP%", pFP).replace("%OPCODE%", opCode).replace("%ROL%", rol)
					.replace("%TREXAMOUNT%", expenseAmount).replace("%TRLABORAMOUNT%", laborAmount)
					.replace("%TRPARTAMOUNT%", partAmount).replace("%Exp_Coverage_Code1_Amount%", expReqCoverageAmount)
					.replace("%Labor_Coverage_Code1_Amount%", laborReqCoverageAmount)
					.replace("%Part_Coverage_Code1_Amount%", partReqCoverageAmount);
			TestDataBeanManager.getTestData().setWLString(wLString);
		} else {
			String wLString =TestDataBeanManager.getTestData().getWLString().replace("SU", "A");
			if (Objects.nonNull(testData.getUpdatedAmount()) && !testData.getUpdatedAmount().equals("")) {
				double updatedPerShareAmount = getTotalPaidAmount() / 3;
				String formattedPerShareAmount = new DecimalFormat("#.00").format(updatedPerShareAmount);
				String oldAmount = new DecimalFormat("#.00").format(getOldPaidAmount() / 3);
				wLString = wLString.replace("<RequestedExpenseAmount1>" + oldAmount,
						"<RequestedExpenseAmount1>" + formattedPerShareAmount);
				double nissanAmount = testData.isApprovedCoupon?testData.getNissanCouponAmt():testData.getNissanShare();
				ExtentLogger.info(String.format("Amount Deatils before WL submission, TotalWLAmount : %f, WLSharePerAmountType : %s, NissanAmount : %f, DcalAmount : %f",getTotalPaidAmount(), formattedPerShareAmount,nissanAmount,testData.getdCal()), true);
			}
			
			TestDataBeanManager.getTestData().setWLString(wLString);
		}
		String url = ConfigurationManager.getBundle().getString("wlsubmission.url");
		getDriver().manage().deleteAllCookies();
		getDriver().navigate().to(url + TestDataBeanManager.getTestData().getWLString());
		String status = ExtWebComponent.getExtWebElement("//label[text()='Status:']/parent::div").getText();
		if (!status.contains("good")) {
			throw new CustomRuntimeException("Warranty line Submission Fails with status:"+statusCode);
		} else {
			ExtentLogger.pass("Warranty Line Submitted with status:"+statusCode);
		}

	}
	
	public static void submitWarrantyLineForDcase(TestDataBean testData) {

		String statusCode = testData.getUserData().get(UserData.STATUSCODE);
		if (statusCode.toUpperCase().equals("SU")) {
			String vin = testData.getUserData().get(UserData.VIN);
			String dealerCode = testData.getUserData().get(UserData.DEALERCODE);
			String pFP = testData.getUserData().get(UserData.PFP);
			String opCode = testData.getUserData().get(UserData.OPCODE);
			String timeString = ROAPIUtil.getDateTimeString();
			String coverageCode = "FG";
			String rol = testData.getUserData().get(UserData.ROL);
			double wlExpAmount = Double.parseDouble(testData.getUserData().get(UserData.WLEXPENSEAMOUNT));
			double wlLabourAmount = Double.parseDouble(testData.getUserData().get(UserData.WLLABORAMOUNT));
			double wlPartAmount = Double.parseDouble(testData.getUserData().get(UserData.WLPARTAMOUNT));
			double wLAmount =  wlExpAmount+wlLabourAmount+wlPartAmount;
			String formattedWLAMount = new DecimalFormat("#.00").format(wLAmount);
			double perShareAmount = wLAmount/3;
			String formattedPerShareAmount = new DecimalFormat("#.00").format(perShareAmount);
			String expenseAmount = formattedPerShareAmount;
			String laborAmount = formattedPerShareAmount;
			String partAmount = formattedPerShareAmount;
			String expReqCoverageAmount = formattedWLAMount;
			String laborReqCoverageAmount = formattedWLAMount;
			String partReqCoverageAmount = formattedWLAMount;
			String rONumber = ROAPIUtil.getRONumber();
			String wLXmlPath = System.getProperty("user.dir") + ConfigurationManager.getBundle().getString("warrantyxml.path");
			String wLString = XMLUtil.getXMLString(wLXmlPath).replace("%STATUS_CODE%", statusCode)
					.replace("%DEALER_CODE%", dealerCode).replace("%RONUMBER%", rONumber)
					.replace("%ROOPENDATE%", timeString).replace("%VIN%", vin).replace("%coverage_code%", coverageCode)
					.replace("%PFP%", pFP).replace("%OPCODE%", opCode).replace("%ROL%", rol)
					.replace("%TREXAMOUNT%", expenseAmount).replace("%TRLABORAMOUNT%", laborAmount)
					.replace("%TRPARTAMOUNT%", partAmount).replace("%Exp_Coverage_Code1_Amount%", expReqCoverageAmount)
					.replace("%Labor_Coverage_Code1_Amount%", laborReqCoverageAmount)
					.replace("%Part_Coverage_Code1_Amount%", partReqCoverageAmount);
			TestDataBeanManager.getTestData().setWLString(wLString);
		} else {
			String wLString =TestDataBeanManager.getTestData().getWLString().replace("SU", "A");
			if (Objects.nonNull(testData.getUpdatedAmount()) && !testData.getUpdatedAmount().equals("")) {
				double updatedPerShareAmount = getTotalPaidAmount() / 3;
				String formattedPerShareAmount = new DecimalFormat("#.00").format(updatedPerShareAmount);
				String oldAmount = new DecimalFormat("#.00").format(getOldPaidAmount() / 3);
				wLString = wLString.replace("<RequestedExpenseAmount1>" + oldAmount,
						"<RequestedExpenseAmount1>" + formattedPerShareAmount);
				double nissanAmount = testData.isApprovedCoupon?testData.getNissanCouponAmt():testData.getNissanShare();
				ExtentLogger.info(String.format("Amount Deatils before WL submission, TotalWLAmount : %f, WLSharePerAmountType : %s, NissanAmount : %f, DcalAmount : %f",getTotalPaidAmount(), formattedPerShareAmount,nissanAmount,testData.getdCal()), true);
			}
			
			TestDataBeanManager.getTestData().setWLString(wLString);
		}
		String url = ConfigurationManager.getBundle().getString("wlsubmission.url");
		getDriver().manage().deleteAllCookies();
		getDriver().navigate().to(url + TestDataBeanManager.getTestData().getWLString());
		String status = ExtWebComponent.getExtWebElement("//label[text()='Status:']/parent::div").getText();
		if (!status.contains("good")) {
			throw new CustomRuntimeException("Warranty line Submission Fails with status:"+statusCode);
		} else {
			ExtentLogger.pass("Warranty Line Submitted with status:"+statusCode);
		}

	}

	private static double getGreater(boolean wLGTNissanAmount, double nissanAmount) {

		return wLGTNissanAmount ? getNumDivisibleByThree(nissanAmount + 100, wLGTNissanAmount)
				: getNumDivisibleByThree(nissanAmount - 100, wLGTNissanAmount);

	}
	
	private static double getGreater(boolean wLGTNissanOrDcalAmount, double wLAmmount, double nissanOrDcalAmount) {
		double fifteenPercent = getPercenatgeShare(15, nissanOrDcalAmount);
		
		nissanOrDcalAmount = wLGTNissanOrDcalAmount ? nissanOrDcalAmount+getPercenatgeShare(15, nissanOrDcalAmount):nissanOrDcalAmount-getPercenatgeShare(15, nissanOrDcalAmount);
		
		if (wLGTNissanOrDcalAmount && wLAmmount > nissanOrDcalAmount) {

			return wLAmmount;

		} else if (wLGTNissanOrDcalAmount) {

			return getNumDivisibleByThree(nissanOrDcalAmount + 100, wLGTNissanOrDcalAmount);
		} else {

			return getNumDivisibleByThree(nissanOrDcalAmount, wLGTNissanOrDcalAmount);
		}
	}
	
private static double getPercenatgeShare(int percentage, double value) {
		
		return (value*percentage)/100;
	}
	
	
	
	private static double getNumDivisibleByThree(double amount,boolean wLGTNissanAmount) {
		int intNum = ((Double) amount).intValue();
		int newNum=intNum;
		int sumOfDigits =getSum(intNum); 
		
		while(sumOfDigits%3!=0) {
			if(wLGTNissanAmount) {
				newNum += 1;
			}else {
				newNum -= 1;
				if(newNum<3) {
					newNum = 3;
				}
				
			}
			sumOfDigits = getSum(newNum); 
			
		}
		
		return Double.valueOf(newNum);
	}
	
	private static int getSum(int number) {

		int digit, sum = 0;

		while (number > 0) {

			digit = number % 10;
			sum = sum + digit;
			number = number / 10;
		}
		
		return sum;

	}

	

}
