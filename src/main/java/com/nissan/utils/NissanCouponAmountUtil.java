package com.nissan.utils;

public class NissanCouponAmountUtil {

	private NissanCouponAmountUtil() {
		
	}
	
	
	public static double getNissanCouponAmount(boolean nissanCpnAmtGTNissanAmt,  double nissanAmt) {
		double fifteenPercent = getPercenatgeShare(15, nissanAmt);
		
		double nissanCpnAmt = nissanCpnAmtGTNissanAmt ? nissanAmt+fifteenPercent:nissanAmt-fifteenPercent;
		
		if (nissanCpnAmtGTNissanAmt && nissanCpnAmt > nissanAmt) {

			return getNumDivisibleByThree(nissanCpnAmt,nissanCpnAmtGTNissanAmt);

		} else {

			return getNumDivisibleByThree(nissanCpnAmt, nissanCpnAmtGTNissanAmt);
			
		}
	}

	private static double getNumDivisibleByThree(double amount,boolean nissanCpnAmtGTNissanAmt) {
		int intNum = ((Double) amount).intValue();
		int newNum=intNum;
		int sumOfDigits =getSum(intNum); 
		
		while(sumOfDigits%3!=0) {
			if(nissanCpnAmtGTNissanAmt) {
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

	private static double getPercenatgeShare(int percentage, double value) {

		return (value * percentage) / 100;
	}
}
