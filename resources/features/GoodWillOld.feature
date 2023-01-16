Feature: Good Regression scenarios

  #Scenario: Restricted Part Sceanrio where NAM<DCAL and WL<DCAL and WL <NAM, Case should auto approved.
  #Given Admin creates RO for good will request with below set of details
  #| Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               |
  #| CVT       |        3900 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 |
  #* Admin fetches vehiclewarranty details
  #When Login as ServiceManager
  #* ServiceManager clicks on New GoodWill request
  #* selects the ro from the SELECT THE REPAIR ORDER table
  #* Select repair order screen it displayed and select coupon "false" and select repair order line
  #* GoodWill request ui is displayed
  #* enters symptom code and diagnosis code
  #| Symptom_Code | Diagnosis_Code |
  #|         1234 |           1234 |
  #* ServiceManager clicks on Next
  #Then Cost grid is displayed
  #* Servicemanager verifies split percentages
  #* Servicemanager enters goodwill reason
  #* Servicemanager selects purchase type as "New"
  #* Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
  #* Servicemanger clicks on Review and Submit button
  #* Service manager verifies Your Goodwill Request has been submitted to Nissan message
  #* Servicemanager Logs out
  #* Login as PCCF1Analyst
  #* Open GoodWill case from VCAT support WL
  #* Enter vcat comments "test"
  #* Click on Approve
  #* Verify Thank You text is dislayed.
  #* VCAT user logs out
  #* GoodWill Case is assigned to "Warranty Admin"
  #* Submit WarrantyLine with below values
  #| Status_Code | ROL | Expense_Amount | Labor_Amount | Part_Amount |
  #| SU          |   1 |             10 |           10 |          10 |
  #* Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
  #* Submit WarrantyLine with below values
  #| Status_Code |
  #| A           |
  #* Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
  @Regression1 @Test2 @Debug @Restricted
  Scenario: Test2 Restricted Part Sceanrio where NAM is less than DCAL and WLis less thanDCAL and WL is less than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        3900 | FG            | 3121428X7C | JX56AA | CVT           | 3N1AB51D16L526889 | false              | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount |
      | SU          |   1 | true             |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"

  @Regression1 @Test2 @Debug @Restricted
  Scenario: Test2b Coverage Type: Basic, Restricted Part Sceanrio where NAM is less than DCAL and WLis less thanDCAL and WL is less than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        3900 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount |
      | SU          |   1 | true             |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"

  @Parallel @Regression @Test3 @sheettc1 @RestrictedParts @Passed
  Scenario: Test1 Restricted Part Sceanrio where NAM is less than DCAL and WL is less than DCAL and WL is greater than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        3900 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | false              | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Parallel @Regression @Test3 @sheettc1 @RestrictedParts
  Scenario: Test1b Restricted Part Sceanrio where NAM is less than DCAL and WL is less than DCAL and WL is greater than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | false              | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Parallel @Regression @Test3 @sheettc1 @RestrictedParts
  Scenario: Test1c Restricted Part Sceanrio where NAM is less than DCAL and WL is less than DCAL and WL is greater than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Parallel @Regression @Test4ab @sheettc2 @RestrictedParts
  Scenario: Test2 Restricted Part Sceanrio where NAM is greater than DCAL and WL is less than DCAL and WL is greater than NAM, Case routes to FOM for approval.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | true               | Restricted Parts  |
    * Verify VCAN creation
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * FOM verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Enter FOM comments "FOM comments"
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Parallel @Regression @Test4 @sheettc2 @RestrictedParts
  Scenario: Test2b Restricted Part Sceanrio where NAM is greater than DCAL and WL is less than DCAL and WL is greater than NAM, Case routes to FOM for approval.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | true               | Restricted Parts  |
    * Verify VCAN creation
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments "FOM comments"
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Parallel @Regression @Test4 @sheettc2 @RestrictedParts
  Scenario: Test2c Restricted Part Sceanrio where NAM is greater than DCAL and WL is less than DCAL and WL is greater than NAM, Case routes to FOM for approval.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | true               | Restricted Parts  |
    * Verify VCAN creation
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments "FOM comments"
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Parallel @Regression @Test5 @Debug @sheettc3
  Scenario: Test3 Restricted Part Sceanrio where NAM is less than DCAL and WL is greater than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | false              | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test5 @Debug @sheettc3
  Scenario: Test3b Restricted Part Sceanrio where NAM is less than DCAL and WL is greater than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * FOM verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Parallel @Regression @Test5c @Debug @sheettc3
  Scenario: Test3c Restricted Part Sceanrio where NAM is less than DCAL and WL is greater than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | KNMAT2MT2FP529932 | false              | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    # * Verify temp VCAN deletion and approved VCAN creation
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * FOM verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test15a @Debug @sheettc3
  Scenario: Test3 Restricted Part Sceanrio where NAM is less than DCAL and WL is greater than NAM, Case should auto approved.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | false              | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    #* Verify temp VCAN deletion and approved VCAN creation
    * Verify temp VCAN deletion and approved VCAN creation
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test7 @sheettc4 @Debug
  Scenario: Test4a Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | true               | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test7 @sheettc4 @Debug
  Scenario: Test4b Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | true               | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test7 @sheettc4 @Debug
  Scenario: Test4c Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | true               | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test8 @sheettc5
  Scenario: Test5a Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | true               | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * FOM verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
    * Enter FOM comments
    * Click on Reject button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out

  @Regression @Test8 @sheettc5
  Scenario: Test5b Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | true               | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Reject button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out

  @Regression @Test8 @sheettc5
  Scenario: Test5c Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | true               | Restricted Parts  |
    * Admin fetches vehiclewarranty details
    * Verify VCAN creation
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Reject button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out

  @Regression @Test9 @sheettc6
  Scenario: Test6a Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FW            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | true               | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | true     |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open goodwill case from All Dealer Cases
    * Click on Update VCAN on goodwill case
    * On Update VCAN ui, part amount, labor amount and Expense amount with below values
    * Enter "Update VCAN" in comments field
    * Click on submit button
    * FOm user logs out
    * Update Warranty line xml with new part, labor and expense amounts and submit with below status
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test9 @sheettc6
  Scenario: Test6b Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FW            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | true               | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | true     |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open goodwill case from All Dealer Cases
    * Click on Update VCAN on goodwill case
    * On Update VCAN ui, part amount, labor amount and Expense amount with below values
    * Enter "Update VCAN" in comments field
    * Click on submit button
    * FOm user logs out
    * Update Warranty line xml with new part, labor and expense amounts and submit with below status
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test9 @sheettc6
  Scenario: Test6c Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FW            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | true               | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | true     |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open goodwill case from All Dealer Cases
    * Click on Update VCAN on goodwill case
    * On Update VCAN ui, part amount, labor amount and Expense amount with below values
    * Enter "Update VCAN" in comments field
    * Click on submit button
    * FOm user logs out
    * Update Warranty line xml with new part, labor and expense amounts and submit with below status
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test11 @sheettc7
  Scenario: Test7 Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FW            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | true               | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Click on Request Grid Exception Checkbox
    * Enter Requested Nissan Contribution
    * Enter Exception reason as "Exception Comments"
    * Click on Update
    * Verify Updated Cost Split Grid Successfully is displayed
    * Enter FOM comments "Route to RAM"
    * Click on Route to RAM button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * Login as RAM
    * Open GW case from Pending RAM Actions
    * Enter RAM Comments as "Deny Case"
    * Click on Deny Exception button
    * Verify Thank You text is dislayed.
    * RAM logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments "Route to Dealer"
    * Click on Modify button
    * Click on Visible to dealer checkbox
    * Click on Route to dealer button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * Login as ServiceManager
    * Open GW case from Pending SM Actions
    * Click on accept and continue
    * Verify Thank You text is dislayed.
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    #  * Verify temp VCAN deletion and approved VCAN creation
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test11 @sheettc7
  Scenario: Test7b Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FW            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | true               | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Click on Request Grid Exception Checkbox
    * Enter Requested Nissan Contribution
    * Enter Exception reason as "Exception Comments"
    * Click on Update
    * Verify Updated Cost Split Grid Successfully is displayed
    * Enter FOM comments "Route to RAM"
    * Click on Route to RAM button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * Login as RAM
    * Open GW case from Pending RAM Actions
    * Enter RAM Comments as "Deny Case"
    * Click on Deny Exception button
    * Verify Thank You text is dislayed.
    * RAM logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments "Route to Dealer"
    * Click on Modify button
    * Click on Visible to dealer checkbox
    * Click on Route to dealer button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * Login as ServiceManager
    * Open GW case from Pending SM Actions
    * Click on accept and continue
    * Verify Thank You text is dislayed.
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    #  * Verify temp VCAN deletion and approved VCAN creation
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test11 @sheettc7
  Scenario: Test7c Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FW            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | true               | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Click on Request Grid Exception Checkbox
    * Enter Requested Nissan Contribution
    * Enter Exception reason as "Exception Comments"
    * Click on Update
    * Verify Updated Cost Split Grid Successfully is displayed
    * Enter FOM comments "Route to RAM"
    * Click on Route to RAM button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * Login as RAM
    * Open GW case from Pending RAM Actions
    * Enter RAM Comments as "Deny Case"
    * Click on Deny Exception button
    * Verify Thank You text is dislayed.
    * RAM logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments "Route to Dealer"
    * Click on Modify button
    * Click on Visible to dealer checkbox
    * Click on Route to dealer button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * Login as ServiceManager
    * Open GW case from Pending SM Actions
    * Click on accept and continue
    * Verify Thank You text is dislayed.
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    #  * Verify temp VCAN deletion and approved VCAN creation
    * Verify PaidAmount, PaidDate and PaidFY

  # test data issue
  @Regression @Test12a @sheettc8
  Scenario: Test8 Restricted Part Sceanrio where NAM is greater than DCAL and WL is greater than NAM, case routed to FOM, FOM approves.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FW            | 3121428X7A | JX56AA | OOW           | 1N4BL2AP5AN545239 | true               | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    * Servicemanager verifies following text message "Please provide as much information as possible on why the specific part needs replacement so that VCAT can review and take action."
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Click on Request Grid Exception Checkbox
    * Enter Requested Nissan Contribution
    * Enter Exception reason as "Exception Comments"
    * Click on Update
    * Verify Updated Cost Split Grid Successfully is displayed
    * Enter FOM comments "Route to RAM"
    * Click on Route to RAM button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * Login as RAM
    * Open GW case from Pending RAM Actions
    * Enter RAM Comments as "Conditionally Approve Case"
    * Click on Conditionally Approve button
    * Verify Thank You text is dislayed.
    * RAM logs out
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | true     |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    #* Login as FOM
    #* Open GW case from Pending FOM Actions
    #* Enter FOM comments
    #* Click on Approve button
    #* FOM verifies Thank You text is dislayed
    #* FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Regression @Test6 @sheettc9
  Scenario: Test9 D-case linkage
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | CVT               |
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
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * Technician responds to Yes/No question with "Yes"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"
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

  @Regression @Test6 @sheettc9
  Scenario: Test9b D-case linkage
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | false              | CVT               |
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
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * Technician responds to Yes/No question with "Yes"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"
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
    * Servicemanager verifies Is the customer likely to be come back for service? field is displayed with Yes or No as autopopulated value
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

  @Regression @Test6 @sheettc9
  Scenario: Test9c D-case linkage
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | false              | CVT               |
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
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * Technician responds to Yes/No question with "Yes"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"
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

  @Regression @Test6 @sheettc11
  Scenario: Test9d D-case linkage for Audio
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Audio             |
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
    * Tecnician clicks on diagnose button
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Tecnician clicks on submit on ccc screen
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

  @Regression @Test6 @sheettc11
  Scenario: Test11b D-case linkage for Audio
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | false              | Audio             |
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
    * Tecnician clicks on diagnose button
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Tecnician clicks on submit on ccc screen
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

  @Regression @Test6 @sheettc11
  Scenario: Test11c D-case linkage for Audio
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | KNMAT2MT2FP529932 | false              | Audio             |
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
    * Tecnician clicks on diagnose button
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Tecnician clicks on submit on ccc screen
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
    Then Login as FOM "3900"
    * Open goodwill case "GW-91015" from All Dealer Cases
    * Click on Update VCAN on goodwill case
    * On Update VCAN ui, part amount, labor amount and Expense amount with below values
    * Enter "Update VCAN" in comments field
    * Click on submit button

  @Test12 @Regression @RestrictedParts @sheettc12 @Rerun
  Scenario: Test12 Good will case with recommended coupon.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
    * All the active gw cases are resolved
    * Verify VCAN creation
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER enters Nissan Contribution as "500"
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
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
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
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Test13 @Regression @NonRestrictedParts @sheettc12 @Rerun
  Scenario: Test13 Good will case with approved coupon.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | CVT               |
    * All the active gw cases are resolved
    * Verify VCAN creation
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
    * CAMANAGER enter "concern" in Concern text box
    * CAMANAGER enter "comments" in Internal Comments text box
    * CAMANAGER click on Submit button
    * CAMANAGER verifies Thank You text is displayed
    * CAMANAGER logs out
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
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * Technician responds to Yes/No question with "Yes"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Automation Test Data CVT             | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"
    * Admin fetches vehiclewarranty details
    * Login as ServiceManager
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
    * Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    #* Login as PCCF1Analyst
    #* Open GoodWill case from VCAT support WL
    #* Enter vcat comments "test"
    #* Click on Approve
    #* Verify Thank You text is dislayed.
    #* VCAT user logs out
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

  @Test14 @Regression @NonRestrictedParts @sheettc12 @Rerun
  Scenario: Test14 Good will case with approved coupon.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
    * All the active gw cases are resolved
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
    * CAMANAGER enter "concern" in Concern text box
    * CAMANAGER enter "comments" in Internal Comments text box
    * CAMANAGER click on Submit button
    * CAMANAGER verifies Thank You text is displayed
    * CAMANAGER logs out
    * Admin fetches vehiclewarranty details
    * Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Test16 @Regression @NonRestrictedParts @sheettc12 @Rerun
  Scenario: Test16 Good will case with approved coupon. Case should route to FOM for approval
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | true               | Restricted Parts  |
    * All the active gw cases are resolved
    * Verify VCAN creation
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
    * CAMANAGER enter "concern" in Concern text box
    * CAMANAGER enter "comments" in Internal Comments text box
    * CAMANAGER click on Submit button
    * CAMANAGER verifies Thank You text is displayed
    * CAMANAGER logs out
    * Admin fetches vehiclewarranty details
    * Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * GoodWill Case is assigned to "VCAT"
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "FOM"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | true             | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Test17 @Regression @RestrictedParts @sheettc12 @Rerun
  Scenario: Test17 Good will case with recommended coupon.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
    * All the active gw cases are resolved
    * Verify VCAN creation
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
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
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    ##* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | false    |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Submitted"
    * Verify temp VCAN deletion and approved VCAN creation
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Test17b @Regression @RestrictedParts @sheettc12 @Rerun
  Scenario: Test17b Good will case with recommended coupon, Nissan amount greater than dcal and wl less than dcal
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | true               | Restricted Parts  |
    * All the active gw cases are resolved
    * Verify VCAN creation
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
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
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    ##* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "FOM"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * Verify Thank You text is dislayed.
    * FOm user logs out
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

  @Test18 @Regression @RestrictedParts @sheettc12
  Scenario: Test18 Good will case with approved coupon, linking goodwill coupon with goodwill case at CAManager
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
    * Verify VCAN creation
    * All the active gw cases are resolved
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    ##* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER verifies GoodWill Cases section is displayed
    * CAMANAGER selects the Goodwill Case created earlier
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
    * CAMANAGER enter "concern" in Concern text box
    * CAMANAGER enter "comments" in Internal Comments text box
    * CAMANAGER click on Submit button
    * CAMANAGER verifies Thank You text is displayed
    * CAMANAGER logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * VCAT user verifies coupon is linked to GW Case
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
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

  @Test19 @Regression @RestrictedParts @sheettc12
  Scenario: Test19 Good will case with approved coupon, linking goodwill coupon with goodwill case at CAManager
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7A | JX56AA | CVT           | 3N1AB7AP3GL665428 | false              | Restricted Parts  |
    * Verify VCAN creation
    * All the active gw cases are resolved
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER verifies GoodWill Cases section is displayed
    * CAMANAGER selects the Goodwill Case created earlier
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
    * CAMANAGER enter "concern" in Concern text box
    * CAMANAGER enter "comments" in Internal Comments text box
    * CAMANAGER click on Submit button
    * CAMANAGER verifies Thank You text is displayed
    * CAMANAGER logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    #* VCAT user verifies coupon is linked to GW Case
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
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

  @Test20 @Regression @RestrictedParts @sheettc12
  Scenario: Test20 Good will case with approved coupon, linking goodwill coupon with goodwill case at CAManager
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | false              | Restricted Parts  |
    * Verify VCAN creation
    * All the active gw cases are resolved
    When Login as ServiceManager
    * ServiceManager clicks on New GoodWill request
    * selects the ro from the SELECT THE REPAIR ORDER table
    * Select repair order screen it displayed and select coupon "false" and select repair order line
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    # #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER verifies GoodWill Cases section is displayed
    * CAMANAGER selects the Goodwill Case created earlier
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER selects Approved radio button under status
    * CAMANAGER enters Nissan Contribution
      | NissanCpnAmtGTNissanAmt |
      | true                    |
    * CAMANAGER enter "concern" in Concern text box
    * CAMANAGER enter "comments" in Internal Comments text box
    * CAMANAGER click on Submit button
    * CAMANAGER verifies Thank You text is displayed
    * CAMANAGER logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    #* VCAT user verifies coupon is linked to GW Case
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "Warranty Admin"
    * Submit WarrantyLine with below values
      | Status_Code | ROL | WLGTNissanAmount | WLGTDcal |
      | SU          |   1 | false            | true     |
    * Verify case status is changed to "Pending-ClaimAmountMismatch" and latest claim status is updated to "Submitted"
    * GoodWill Case is assigned to "COMPLETE"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * FOM user verifies coupon is linked to GW Case
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

  @DummyTest
  Scenario: Test13 Good will case with approved coupon.
    Given Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters "1N6AD0EV3FN755780" in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER verifies GoodWill Cases section is displayed
    * CAMANAGER selects the Goodwill Case created earlier

  @Test21 @Regression @DelegatedUsers @sheettc12
  Scenario Outline: Test21 Verify Delegated User <DelegatedUser> is able to see New Good Will case option on left side Menu
    Given Login as ServiceManager "<ServiceManager>"
    * ServiceManager clicks on "System Settings" from left side menu
    * ServiceManager verifies System Setting Component is displayed
    * ServiceManager clicks on Manage GoodWill Delegation link
    * ServiceManager verifies Manage GoodWill Delegation Component is displayed
    * ServiceManager select "<DelegatedUser>" and click on Save
    * Servicemanager Logs out
    * Login as "<DelegatedUser>"
    * Verify New Good Will Request option is displayed

    Examples: 
      | DelegatedUser | ServiceManager |
      | DCONED92      | DMARTC55       |
      | DRODIJ99      | DMARTC55       |

  @Test22 @Regression @RestrictedParts @sheettc12 @Rerun
  Scenario: Test22 Good will case with recommended coupon, linking coupon from other actions
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Restricted Parts  |
    * All the active gw cases are resolved
    * Verify VCAN creation
    * Login into the application as "CAMANAGER"
    * CAMANAGER clicks on "New Goodwill Coupon" from left side menu
    * CAMANAGER verifies Search by VIN Customer Information component is displayed
    * CAMANAGER enters VIN in VIN textbox
    * CAMANAGER clicks on Search by VIN button
    * CAMANAGER clicks on Select button on first record
    * CAMANAGER clicks on Continue Without Goodwill Case button
    * CAMANAGER verifies GoodWill Coupon Details is displayed
    * CAMANAGER enters Nissan Contribution as "500"
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
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
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
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @DummyTest1
  Scenario: DeleteAll ROs
    Given DeleteALL ROs

  @Test23 @Regression @RestrictedParts @sheettc12 @Rerun
  Scenario: Test23 Good will case with approved coupon, linking coupon from other actions
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | false              | Restricted Parts  |
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
      | true                    |
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
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
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
    * GoodWill Case is assigned to "COMPLETE"
    * Verify temp VCAN deletion and approved VCAN creation
    * Submit WarrantyLine with below values
      | Status_Code |
      | A           |
    * Verify case status is changed to "Resolved-Completed" and latest claim status is updated to "Paid"
    * Verify PaidAmount, PaidDate and PaidFY

  @Test24 @Regression @RestrictedParts @sheettc12 @Rerun
  Scenario: Test24 Good will case with approved coupon, linking coupon from other actions, coupon amount less than nissan amount
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | false              | Restricted Parts  |
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
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "FOM"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
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

  @Test25 @Regression @NonRestrictedParts @sheettc12 @Rerun
  Scenario: Test25 Non Restricted parts Good will case with approved coupon , linking coupon from other actions, coupon amount less than nissan amount
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 259156CA0E | RN74AA | Basic         | 1N6AD0EV3FN755780 | false              | Audio             |
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
    * Tecnician clicks on diagnose button
    Then Technician responds to Yes/No question with "Yes"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Tecnician clicks on submit on ccc screen
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

  @Test26 @Regression @RestrictedParts @sheettc12
  Scenario: Test26 Good will case with approved coupon, linking coupon from other actions, coupon amount less than nissan amount
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | Audio     |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | 1N6AA0EC7FN513355 | false              | Restricted Parts  |
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
    Then Cost grid is displayed
    * Servicemanager verifies split percentages
    * Servicemanager enters goodwill reason
    #* Servicemanager adds attachments
    * Servicemanager selects purchase type as "New"
    * Servicemanager enters "2000" in customer pay amount textbox
    * Servicemanger checks We certify that this car is not covered by Nissan or 3rd party extended service contract
    * Servicemanger checks We also certify that this vehicle does not have a branded title
    #   * Servicemanager clicks on save button
    * Servicemanger clicks on Review and Submit button
    * Service manager verifies Your Goodwill Request has been submitted to Nissan message
    * Servicemanager Logs out
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "FOM"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
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

  @Test28
  Scenario: dummyTest
    Given login as Technician "DRODIJ99"
    * Technician opens dcase "D-62401" with RONumber "AT-585951"

  @Test29
  Scenario: USS
    Given Login as Service advisor "DCONED92"
    When Click on Symptom Survey Beta link
    Then Customer Concerns component is displayed
    * Enter VIN "5N1AT2MV5FC841117" on customer concerns component and click on go
    * Click on add concern button
    * Click on I see link
    * Select "poor appearance" check box and click ok
    * Click on add to summary
    * "Vehicle Concerns Summary" is displayed
    * CLick on next button
    * Coverages Component is displayed
    * Click on next
    * Select paytype as Factory Warranty

  @Test30
  Scenario: dummy test
    * Login as ServiceManager "DMARTC55"
    * ServiceManager clicks on pending SM actions and opend GW case "GW-37022"
    * GoodWill request ui is displayed
    * enters symptom code and diagnosis code
      | Symptom_Code | Diagnosis_Code |
      |         1234 |           1234 |
    * ServiceManager clicks on Next
    * ServiceManager verifies Diagnostic Cases table is displayed
    * ServiceManager verifies Diagnostic cases created within last 30 days are displayed
    * ServiceManager selects the case with matching repair "CVT"
    * ServiceManager clicks on confirm and proceed button
    Then Cost grid is displayed
    #* Servicemanager verifies split percentages
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
    * Login as PCCF1Analyst
    * Open GoodWill case from VCAT support WL
    * Enter vcat comments "test"
    * Click on Approve
    * Verify Thank You text is dislayed.
    * VCAT user logs out
    * GoodWill Case is assigned to "FOM"
    * Login as FOM
    * Open GW case from Pending FOM Actions
    * Enter FOM comments
    * Click on Approve button
    * FOM verifies Thank You text is dislayed
    * FOm user logs out
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

  @Test27
  Scenario Outline: Create RO with Same VIN
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | KNMAT2MT2FP529932 | false              | CVT               |
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
    * Tecnician clicks on diagnose
    * Technician responds to Yes/No question with "<Question Response>"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"

    Examples: 
    |Question Response|
    |Yes|
    |No|
    
    @Test27
  Scenario: Test27 CVT Dcase linking test case, dcase created thru other ros with same VIN on the gw case.
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | PFP        | OPCODE | Coverage_Type | VIN               | NissanAmountGTDcal | Vehicle_Component |
      | CVT       |        5408 | FG            | 3121428X7C | JX56AA | Powertrain    | KNMAT2MT2FP529932 | false              | CVT               |
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