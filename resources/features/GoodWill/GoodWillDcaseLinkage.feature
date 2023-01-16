Feature: Good Regression scenarios with Audio Dcase Linkage

 

 Rule: Good Regression scenarios with CVT Dcase Linkage created thru same VIN and different RO
   
  @Test27 @Regression  @NonRestrictedParts
  Scenario Outline: Test Data Creation test case CVT Dcase linking test case, dcase created thru other ros with same VIN on the gw case.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        3529 | FG            | 310206SV1A | JC01AA | Powertrain    | KNMAT2MT2FP529932 | false              | CVT               |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes | Customer Symptom DropDowns              | Customer OtherSymptoms   | Payment Assumption |
      |                             | Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Customer Pay       |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   |
      |                               | Vibration:Judder             | Technician Repair comments |
    * Technician clicks on diagnose
    * Technician responds to Yes/No question with "<Question Response>"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Technician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Technician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Technician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"

    Examples: 
    |Question Response|
    |Yes|
    |No|
    
    @Test27 @Regression  @NonRestrictedParts
  Scenario: Test27 CVT Dcase linking test case, dcase created thru other ros with same VIN on the gw case.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        3529 | FG            | 310206SV1A | JC01AA | Powertrain    | KNMAT2MT2FP529932 | false              | CVT               |
    * Verify VCAN creation
    * Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "true" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * ServiceManager verifies Diagnostic Cases table is displayed
    * ServiceManager verifies Diagnostic cases created within last 30 days are displayed
    * ServiceManager selects the case with matching repair
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    # * Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanager clicks on save button
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY
 
 
 
  
 # Rule: Good Regression scenarios with Audio Dcase Linkage created thru same VIN and same RO
     
  @Regression @Test31 @sheettc11  @NonRestrictedParts
  Scenario Outline: Test31 Test Data creation for D-case linkage for Audio, dcase created thru other ros with same VIN on the gw case.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        3529 | FG            | 259155SP4B | RR26AA | Basic         | 1N6AD0EV3FN755780 | false              | Audio             |
    * Login as service advisor
    When service advisor clicks on New Audio Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects Audio plays with ignition off check box
    * service advisor submits the dcase
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects Audio plays with ignition off check box
    * Technician checks Iconfirm checkbox
    * Technician clicks on diagnose button
    Then Technician responds to Yes/No question with "<Question Response>"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Technician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Technician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Repair Recommendation 					 |
      | Automation Test Repair Audio        | 
    * Technician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    
     Examples: 
    |Question Response|
    |Yes|
    |No|
   
 
  Scenario: Test31 D-case linkage for Audio, dcase created thru other ros with same VIN on the gw case.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        3529 | FG            | 259155SP4B | RR26AA | Basic         | 1N6AD0EV3FN755780 | false              | Audio             |
    * Admin fetches vehiclewarranty details
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * ServiceManager verifies Diagnostic Cases table is displayed
    * ServiceManager verifies Diagnostic cases created within last 30 days are displayed
    * ServiceManager selects the case with "Automation Test Repair Audio" repair
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY
    
    
  
 # Rule:Good Regression scenarios with USS Dcase Linkage created thru same VIN and same RO
    
      
    #Scenario: USS DCase Linkage
     #Given Admin creates RO for good will request with below set of details
      #| Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      #| CVT       |        3529 | FG            | 259155SP4B | RR26AA | Basic         | 1N6AD0EV3FN755780 | false              | CVT               |
    #Given Login as Service advisor "DDODGK26"
    #When Service advisor clicks on Symptom Survey Beta link
    #Then Service advisor verifies Customer Concerns component is displayed
    #* Enter VIN "KNMAT2MT2FP529932" on customer concerns component and click on go
    #* Service advisor enters VIN on customer concerns component and click on go
    #* Service advisor clicks on add concern button
    #* Service advisor clicks on I see link
    #* Service advisor selects "poor appearance" check box and clicks ok
    #* Service advisor clicks on add to summary
    #* Service advisor verifies "Vehicle Concerns Summary" is displayed
    #* Service advisor clicks on next button
    #* Service advisor verifies Coverages Component is displayed
    #* Service advisor clicks on next
    #* Service advisor selects paytype as Customer Pay
    #* Service advisor clicks on done button
    #* Service advisor verifies Congratulations message
    #* service advisor logs out
    #* login as Technician
    #* Technician clicks on Start Universal Symptom from left side menu
    #* Technician opens RO from the RO table
    #* Technician selects customer concerns and clicks on go
    #* Technician verifies Dcase is created
    #* Technician opens dcase
    
    @Regression @Test32  @NonRestrictedParts
   Scenario: Test32 D-case linkage for USS, dcase created thru other ros with same VIN on the gw case.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT     |        3529 | FG            | 259155SP4B | RR26AA | Basic         | 1N6AD0EV3FN755780 | false              | CVT             |
    * Admin fetches vehiclewarranty details
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * ServiceManager verifies Diagnostic Cases table is displayed
    * ServiceManager verifies Diagnostic cases created within last 30 days are displayed
    * ServiceManager selects the case with "Automation Test Repair USS" repair
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY 
    
    
  @Regression @Dummy  @NonRestrictedParts
    Scenario: Test31 D-case linkage for Audio, dcase created thru other ros with same VIN on the gw case.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        3529 | FG            | 259155SP4B | RR26AA | Basic         | 1N6AD0EV3FN755780 | false              | Audio             |
    * Admin fetches vehiclewarranty details
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * ServiceManager verifies Diagnostic Cases table is displayed
    * ServiceManager verifies Diagnostic cases created within last 30 days are displayed
    * ServiceManager selects the case with "Automation Test Repair Audio" repair
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY
     
    
