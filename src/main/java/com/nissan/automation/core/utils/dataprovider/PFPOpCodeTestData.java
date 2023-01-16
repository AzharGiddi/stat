package com.nissan.automation.core.utils.dataprovider;

import com.nissan.exceptions.CustomRuntimeException;

import static com.nissan.databeans.TestDataBeanManager.getTestData;

import com.nissan.enums.UserData;

public class PFPOpCodeTestData {

	
		
	public PFPOpCodeTestData(String coverageType) {
		
		switch(coverageType.toUpperCase()) {
		case "CVT":
			getTestData().getUserData().put(UserData.PFP, "3121428X7A");
			getTestData().getUserData().put(UserData.OPCODE, "JX56AA");
			break;
		case "POWERTRAIN":
			getTestData().getUserData().put(UserData.PFP, "310206SV1A");
			getTestData().getUserData().put(UserData.OPCODE, "JC01AA");
			
			break;
		case "BASIC":
			getTestData().getUserData().put(UserData.PFP, "259155SP4B");
			getTestData().getUserData().put(UserData.OPCODE, "RR26AA");
			break;
				
				default: throw new CustomRuntimeException(coverageType+ " is invalid. Valid values are CVT, Basic, PowerTrain.");
		}
		
		
	}
	
	
	
	
}
