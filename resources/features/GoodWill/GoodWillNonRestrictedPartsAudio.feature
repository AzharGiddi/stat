@Regression @Audio
Feature: GoodWill Regression NonRestricted Parts Audio scenarios

  Background: Pre-Conditions: Set up Repair Option for Audio
    Given EngManager adds below PFPs to the Audio repair options
      | PFP        |
      | 310206SV1A |
      | 259155SP4B |
      | 3102050X5  |
      | 3121428X7A |

   
  @Regression @Test6 @sheettc11 @NonRestrictedParts @Audio
  Scenario: Test9d D-case linkage of Audio dcase and coverage type Basic
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
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Technician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Technician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Technician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
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
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
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

  @Regression @Test6 @sheettc11 @NonRestrictedParts @Audio
  Scenario: Test11b D-case linkage of audio dcase and coverage type CVT
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        3529 | FG            | 3102050X5 | JE99AA | CVT           | 3N1AB7AP3GL665428 | false              | Audio             |
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
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Technician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Technician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Technician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
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
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
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

  @Regression @Test6 @sheettc11 @NonRestrictedParts @Audio
  Scenario: Test11c D-case linkage of Audio dcase and coverage type Powertrain
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        3529 | FG            | 310206SV1A | JC01AA | Powertrain    | KNMAT2MT2FP529932 | false              | Audio             |
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
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Technician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Technician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Technician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
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
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
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

  @Test10 @sheettc6
  Scenario: Test5 Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Then Login as FOM "3529"
    * Open goodwill case "GW-91015" from All Dealer Cases
    * Click on Update VCAN on goodwill case
    * On Update VCAN ui, part amount, labor amount and Expense amount with below values
    * Enter "Update VCAN" in comments field
    * Click on submit button

  
  
   @Test25 @Regression @NonRestrictedParts @Audio @sheettc12 
  Scenario: Test25 Linking Approved goodwill coupon to goodwill case from other actions,
    			Good will case with component Audio,coverage type Basic with non restricted parts and 
    			Nissan amount is lesser than DCAl, Nissan Coupn amount is lesser than Nissan Amount and WL amount is greater than DCAL.

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
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Technician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Technician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Technician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    #* Evaluate NAA Rules and verify routeToVcat is "false"
    * Admin fetches vehiclewarranty details
    * All the active gw cases are resolved
    * Verify VCAN creation
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER verifies GoodWill Cases section is displayed
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | false                   |
    * CAMANAGER enter "concern" in Concern text box
    * CAMANAGER enter "comments" in Internal Comments text box
    * CAMANAGER click on Submit button
    * CAMANAGER verifies Thank You text is displayed
    * CAMANAGER logs out
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "true" and select repair order line
    * GoodWill request ui is displayed
    * ServiceManager clicks on Add Coupon button
    * ServiceManager verifies add coupon pop up is displayed
    * ServiceManager select first coupon from the list of coupons and click on submit button
    * ServiceManager verifies coupon is linked to goodwill case.
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * ServiceManager verifies Diagnostic Cases table is displayed
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
    * GoodWill Case is assigned to "FOM"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Verify temp VCAN deletion and approved VCAN creation
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  