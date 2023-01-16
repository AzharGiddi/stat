@tag
Feature: F1
  I want to

  Background: User fetches TSB and NAA approval
    Given Login page is displayed

  @Demo1
  Scenario: UI TestCase : No TSB, case gets submitted at technician and summary screen is displayed.
    Given Log into application as Admin user
    * Admin creates RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | Manufacture Date |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | No       |                  |
    * Admin fetches vehicle reference details
    * Admin logs out
    * Login to application as Engineering Manager
    * Engineering Manager evaluates TSBs
      | Customer Symptom Checkboxes | Customer Symptom DropDowns             | Customer OtherSymptoms   | Payment Assumption | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   | login required | logout required |
      |                             | Vibration:Shake;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Customer Pay       |                               | Vibration:Shake;             | Technician Repair comments | false          | false           |
    * Engineering Manager fetches NAA rules
    * Engineering Manager logs out
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects symptoms
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects symptoms
    * Tecnician clicks on diagnose
    Then CCC screen is displayed
    * Tecnician selects below values on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Technician Disagreed Repair | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | yes                             | Vibration-checked cvt     | Comments                          |
    * Tecnician clicks on submit and PCC questions screen is displayed
    * Tecnician selects below reponds to PCC Question with below responses
      | Any Oil Leaks Present | Has dye been installed to isolate leak | List all if any vehicle modifications | Attachment Path                                                 | techline contacted | when was tech line contacted |
      | No                    | No                                     | Modification                          | C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf | Yes                | 2021-12-15                   |
    * Technician clicks on submit on questions screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out

  @Demo2
  Scenario: API TestCase : No TSB, case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
    * Evaluates TSBs for "No TSB"
      | Customer Symptom Checkboxes | Customer Symptom DropDowns             | Customer OtherSymptoms   | Payment Assumption | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   | login required | logout required |
      |                             | Vibration:Shake;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Customer Pay       |                               | Vibration:Shake;             | Technician Repair comments | false          | false           |
    * Evaluate NAA Rules
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects symptoms
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects symptoms
    * Tecnician clicks on diagnose
    Then CCC screen is displayed
    * Tecnician selects below values on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Technician Disagreed Repair | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | yes                             | Vibration-checked cvt     | Comments                          |
    * Tecnician clicks on submit and PCC questions screen is displayed
    * Tecnician selects below reponds to PCC Question with below responses
      | Any Oil Leaks Present | Has dye been installed to isolate leak | List all if any vehicle modifications | Attachment Path                                                 | techline contacted | when was tech line contacted |
      | No                    | No                                     | Modification                          | C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf | Yes                | 2021-12-15                   |
    * Technician clicks on submit on questions screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out

  @Demo3
  Scenario: UI TestCase : 1 TSB ,No ESM with QA and No repair - Customer Pay, case gets submitted at technician and summary screen is displayed.
    Given Log into application as Admin user
    * Admin creates RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | Manufacture Date |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | No       |                  |
    * Admin fetches vehicle reference details
    * Admin logs out
    * Login to application as Engineering Manager
    * Engineering Manager evaluates TSBs
      | Customer Symptom Checkboxes | Customer Symptom DropDowns              | Customer OtherSymptoms   | Payment Assumption | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   | login required | logout required |
      |                             | Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Customer Pay       |                               | Vibration:Judder             | Technician Repair comments | false          | false           |
    * Engineering Manager fetches NAA rules
    * Engineering Manager logs out
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects symptoms
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects symptoms
    * Tecnician clicks on diagnose
    Then Technician responds to Yes/No question with "Yes"
    * CCC screen is displayed
    * Tecnician selects below values on ccc screen and clicks on submit button
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Technician Disagreed Repair | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | yes                             | Vibration-checked cvt     | Comments                          |
    * Dcase is resolved and summary screen is displayed
    * Technician logs out

  @Demo4
  Scenario: API TestCase : 1 TSB ,No ESM with QA and No repair - Customer Pay, case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
    * Evaluates TSBs for "1 TSB"
      | Customer Symptom Checkboxes | Customer Symptom DropDowns              | Customer OtherSymptoms   | Payment Assumption | Technician Symptom Checkboxes | Technician Symptom DropDowns            | Technician OtherSymptoms   |
      |                             | Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Customer Pay       |                               | Vibration:Judder;ENGINESTALLWHEN:P-to-R | Technician Repair comments |
    * Evaluate NAA Rules
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects symptoms
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects symptoms
    * Tecnician clicks on diagnose
    Then Technician responds to Yes/No question with "Yes"
    * CCC screen is displayed
    * Tecnician selects below values on ccc screen and clicks on submit button
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Technician Disagreed Repair | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | yes                             | Vibration-checked cvt     | Comments                          |
    * Dcase is resolved and summary screen is displayed
    * Technician logs out

  @Regression @test1
  Scenario: No TSB, case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
    * Evaluates TSBs for "No TSB"
      | Customer Symptom Checkboxes | Customer Symptom DropDowns             | Customer OtherSymptoms   | Payment Assumption | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   | login required | logout required |
      |                             | Vibration:Shake;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Customer Pay       |                               | Vibration:Shake;             | Technician Repair comments | false          | false           |
    * Evaluate NAA Rules
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects symptoms
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects symptoms
    * Tecnician clicks on diagnose
    Then CCC screen is displayed
    * Tecnician selects below values on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Technician Disagreed Repair | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | yes                             | Vibration-checked cvt     | Comments                          |
    * Tecnician clicks on submit and PCC questions screen is displayed
    * Tecnician selects below reponds to PCC Question with below responses
      | Any Oil Leaks Present | Has dye been installed to isolate leak | List all if any vehicle modifications | Attachment Path                                                 | techline contacted | when was tech line contacted |
      | No                    | No                                     | Modification                          | C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf | Yes                | 2021-12-15                   |
    * Technician clicks on submit on questions screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out

  @Regression @test2
  Scenario: NO TSB/ESM,Warranty, case gets submitted at VCAT and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes | Customer Symptom DropDowns             | Customer OtherSymptoms   | Payment Assumption |
      |                             | Vibration:Shake;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Warranty           |
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   |
      |                               | Vibration:Shake;             | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "No TSB" evaluated
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Verify PCC Questions screen is displayed
    * Tecnician selects below reponds to PCC Question with below responses
      | Any Oil Leaks Present | Has dye been installed to isolate leak | List all if any vehicle modifications | Attachment Path                                                 | techline contacted | when was tech line contacted |
      | No                    | No                                     | Modification                          | C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf | Yes                | 2021-12-15                   |
    * Technician clicks on submit on questions screen
    * Thank you screen is displayed to technician
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Login as PCCF1Analyst
    * Open dcase and verify evaluated NAA rules again populated fields on dcase
    * VCAT User enters below details and submit dcase
      | VCAT-System make the right recommendation | VCAT-Agree with the technician recommendation | VCAT-Want to override the recommendation | VCAT-Support Notes |
      | Yes                                       | Yes                                           | No                                       | VCAT support       |
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out

  @Regression @test3
  Scenario: Multiple TSB/ESM, Customer Pay,case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                      | Customer Symptom DropDowns                                         | Customer OtherSymptoms   | Payment Assumption |
      | Flare;Chirp / Creak / Squeak / Squeal;Engine Hot | VehicleDoesNotMove:Forward;Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Customer Pay       |
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                    | Technician Symptom DropDowns                                       | Technician OtherSymptoms   |
      | Flare;Chirp / Creak / Squeak / Squeal;Engine Hot | VehicleDoesNotMove:Forward;Vibration:Judder;ENGINESTALLWHEN:P-to-R | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "Multiple TSB" evaluated
    * CCC screen is displayed
    * Tecnician selects below values on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | Vibration-checked cvt     | Comments                          |
    * Tecnician clicks on submit and PCC questions screen is displayed
    * Tecnician selects below reponds to PCC Question with below responses
      | Any Oil Leaks Present | Has dye been installed to isolate leak | List all if any vehicle modifications | Attachment Path                                                 | techline contacted | when was tech line contacted |
      | No                    | No                                     | Modification                          | C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf | Yes                | 2021-12-15                   |
    * Technician clicks on submit on questions screen
    * Dcase is resolved and summary screen is displayed
    * Evaluate NAA Rules and verify routeToVcat is "false"
    * Technician logs out

  @Regression @test4
  Scenario: Multiple TSB/ESM, Warranty, case gets submitted at VCAT and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                      | Customer Symptom DropDowns                                         | Customer OtherSymptoms   | Payment Assumption |
      | Flare;Chirp / Creak / Squeak / Squeal;Engine Hot | VehicleDoesNotMove:Forward;Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Warranty           |
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                    | Technician Symptom DropDowns                                       | Technician OtherSymptoms   |
      | Flare;Chirp / Creak / Squeak / Squeal;Engine Hot | VehicleDoesNotMove:Forward;Vibration:Judder;ENGINESTALLWHEN:P-to-R | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "Multiple TSB" evaluated
    * CCC screen is displayed
    * Tecnician selects below values on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | Vibration-checked cvt     | Comments                          |
    * Tecnician clicks on submit and PCC questions screen is displayed
    * Tecnician selects below reponds to PCC Question with below responses
      | Any Oil Leaks Present | Has dye been installed to isolate leak | List all if any vehicle modifications | Attachment Path                                                 | techline contacted | when was tech line contacted |
      | No                    | No                                     | Modification                          | C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf | Yes                | 2021-12-15                   |
    * Technician clicks on submit on questions screen
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Login as PCCF1Analyst
    * Open dcase and verify evaluated NAA rules again populated fields on dcase
    * VCAT User enters below details and submit dcase
      | VCAT-System make the right recommendation | VCAT-Agree with the technician recommendation | VCAT-Want to override the recommendation | VCAT-Support Notes |
      | Yes                                       | Yes                                           | No                                       | VCAT support       |
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out

  @Regression @test5
  Scenario: 1 TSB ,No ESM with QA and No repair - Customer Pay, case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
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
    Then Technician responds to Yes/No question with "Yes"
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values on ccc screen and clicks on submit button
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Technician Disagreed Repair | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | yes                             | Vibration-checked cvt     | Comments                          |
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"

  @Regression @test6
  Scenario: 1 TSB ,No ESM with QA and No repair - Customer Pay, case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes | Customer Symptom DropDowns              | Customer OtherSymptoms   | Payment Assumption |
      |                             | Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Goodwill           |
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
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values on ccc screen and clicks on submit button
      | CCC-Part Failed First | CCC-Why Part Failed | CCC-Technician Disagreed Repair | CCC-Repair Recommendation | CCC-Repair Justification/Comments |
      | Engine                | Modification        | yes                             | Vibration-checked cvt     | Comments                          |
    * Thank you screen is displayed to technician
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Login as PCCF1Analyst
    * Open dcase and verify evaluated NAA rules again populated fields on dcase
    * VCAT User enters below details and submit dcase
      | VCAT-System make the right recommendation | VCAT-Agree with the technician recommendation | VCAT-Want to override the recommendation | VCAT-Support Notes |
      | Yes                                       | Yes                                           | No                                       | VCAT support       |
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out

  @Regression @test7
  Scenario: 1 TSB with QA Refer to PCC - Customer Pay, case gets submitted at vcat and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes  | Customer Symptom DropDowns | Customer OtherSymptoms   | Payment Assumption |
      | Going uphill, heavy throttle | ENGINESTALLWHEN:P-to-D     | Customer Repair comments | Customer Pay       |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | Going uphill, heavy throttle  | ENGINESTALLWHEN:P-to-D       | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * Technician responds to Yes/No question with "No"
    * Technician responds to input response question with "input"
    * Technician verifies Unable to diagnose message is displayed
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Vibration-checked cvt                | comments                          |
    * Tecnician clicks on submit and PCC questions screen is displayed
    * Tecnician selects below reponds to PCC Question with below responses
      | Any Oil Leaks Present | Has dye been installed to isolate leak | List all if any vehicle modifications | Attachment Path                                                 | techline contacted | when was tech line contacted |
      | No                    | No                                     | Modification                          | C:\\Users\\ab00789853\\Downloads\\DiagnosticOutput_D-180531.pdf | Yes                | 2021-12-15                   |
    * Technician clicks on submit on questions screen
    * Thank you screen is displayed to technician
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Login as PCCF1Analyst
    * Open dcase and verify evaluated NAA rules again populated fields on dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Agree with the technician recommendation as "Yes"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out

  @Regression @test8
  Scenario: 1 TSB ,No ESM with QA have repair - Warranty, case gets submitted at VCAT and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes | Customer Symptom DropDowns              | Customer OtherSymptoms   | Payment Assumption |
      |                             | Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Warranty           |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   |
      |                               | Vibration:Judder             | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * Technician responds to Yes/No question with "No"
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
    * Thank you screen is displayed to technician
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Login as PCCF1Analyst
    * Open dcase and verify evaluated NAA rules again populated fields on dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Want to override the system recommendation as "No"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out

  @Regression @test9
  Scenario: 1 TSB ,No ESM with QA have repair - Warranty, case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
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
    * Technician responds to Yes/No question with "No"
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

  @Regression @test10
  Scenario: 1 TSB with QA have repair - Warranty, case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes | Customer Symptom DropDowns              | Customer OtherSymptoms   | Payment Assumption |
      |                             | Vibration:Judder;ENGINESTALLWHEN:P-to-R | Customer Repair comments | Warranty           |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes | Technician Symptom DropDowns | Technician OtherSymptoms   |
      |                               | Vibration:Judder             | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * Technician responds to Yes/No question with "No"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed           |
      | Engine                | Defect in factory workmanship |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"

  @Regression @test11
  Scenario: 1TSB ,No ESM without QA - default repair - Warranty,case gets submitted at VCAT and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                       | Customer Symptom DropDowns          | Customer OtherSymptoms   | Payment Assumption |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) | Vibration:Single_or_Multiple_Bumps; | Customer Repair comments | Warranty           |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                     | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) |                              | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Thank you screen is displayed to technician
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Login as PCCF1Analyst
    * Open dcase and verify evaluated NAA rules again populated fields on dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Want to override the system recommendation as "No"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out

  @Regression @test12
  Scenario: 1TSB ,No ESM without QA - default repair- Customer Pay,case gets submitted at technician and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                       | Customer Symptom DropDowns          | Customer OtherSymptoms   | Payment Assumption |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) | Vibration:Single_or_Multiple_Bumps; | Customer Repair comments | Customer Pay       |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                     | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) |                              | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed           |
      | Engine                | Defect in factory workmanship |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"

  @Regression @test13
  Scenario: 1 TSB ,No ESM without QA - default repair - Warranty
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | true     |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                       | Customer Symptom DropDowns          | Customer OtherSymptoms   | Payment Assumption |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) | Vibration:Single_or_Multiple_Bumps; | Customer Repair comments | Warranty           |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                     | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) |                              | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    Then Technician responds to Yes/No question with "No"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed           |
      | Engine                | Defect in factory workmanship |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"

  @Regression @test14
  Scenario: 1TSB ,No ESM with QA have repair - Customer Pay, gets submitted at Technician.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
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
    Then Technician responds to Yes/No question with "No"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed           |
      | Engine                | Defect in factory workmanship |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"

  @Regression @test15
  Scenario: 1 TSB with repair, technician disagrees checkbox not checked, case gets submitted and summary screen is displayed.
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty |
      | CVT       |        5408 | 1N4BL2AP6AN487528 |    8000 | false    |
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
    Then Technician responds to Yes/No question with "No"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Modification        |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | true                            | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Thank you screen is displayed to technician
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Login as PCCF1Analyst
    * Open dcase and verify evaluated NAA rules again populated fields on dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Agree with the technician recommendation as "Yes"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out

  @Regression @test16
  Scenario: Test16: Warranty line submission for cvt D-case resolved at technician with WL amount lesser than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | CVT       |        5408 | 1N6AA0EC7FN513355 |    8000 | true     | 3121428X7C | JX56AA |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                       | Customer Symptom DropDowns          | Customer OtherSymptoms   | Payment Assumption |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) | Vibration:Single_or_Multiple_Bumps; | Customer Repair comments | Factory Warranty   |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                     | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) |                              | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    Then Technician responds to Yes/No question with "No"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed         |
      | Engine                | Defect in factory materials |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              1000 |            1000 |           1000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Approved by F1 msg is updated in VCAN approval comments

  @Regression @test17
  Scenario: Test17: Warranty line submission for cvt D-case resolved at technician with WL amount greater than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | CVT       |        5408 | 1N6AA0EC7FN513355 |    8000 | true     | 3121428X7C | JX56AA |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                       | Customer Symptom DropDowns          | Customer OtherSymptoms   | Payment Assumption |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) | Vibration:Single_or_Multiple_Bumps; | Customer Repair comments | Factory Warranty   |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                     | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) |                              | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Verify "1 TSB" evaluated
    Then Technician responds to Yes/No question with "No"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed           |
      | Engine                | Defect in factory workmanship |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              6000 |            6000 |           6000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Claimed amount in WL is over the approved repair is updated in VCAN approval comments

  @Regression @test18
  Scenario: Test18: Warranty line submission for cvt D-case resolved at technician and WL amount lesser than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | CVT       |        5408 | 1N6AA0EC7FN513355 |    8000 | true     | 3121428X7C | JX56AA |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                       | Customer Symptom DropDowns          | Customer OtherSymptoms   | Payment Assumption |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) | Vibration:Single_or_Multiple_Bumps; | Customer Repair comments | Factory Warranty   |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                     | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) |                              | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Technician responds to Yes/No question with "No"
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
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Dcase is assigned to "VCAT Support"
    * Login as PCCF1Analyst
    * PCCF1Analyst Opens dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Want to override the system recommendation as "No"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              1000 |            1000 |           1000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Approved by F1 msg is updated in VCAN approval comments

  @Regression @test19
  Scenario: Test19: Warranty line submission for CVT D-case resolved at technician and WL amount greater than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | CVT       |        5408 | 1N6AA0EC7FN513355 |    8000 | true     | 3121428X7C | JX56AA |
    * Login as service advisor
    When service advisor clicks on New CVT Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects below symptoms
      | Customer Symptom Checkboxes                       | Customer Symptom DropDowns          | Customer OtherSymptoms   | Payment Assumption |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) | Vibration:Single_or_Multiple_Bumps; | Customer Repair comments | Factory Warranty   |
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician
    * Technician opens dcase
    * Technician selects below symptoms
      | Technician Symptom Checkboxes                     | Technician Symptom DropDowns | Technician OtherSymptoms   |
      | HarshShift;Slips;Gurgle / Slosh (Liquid Movement) |                              | Technician Repair comments |
    * Tecnician clicks on diagnose
    Then Technician responds to Yes/No question with "No"
    * Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed |
      | Engine                | Vehicle Abuse       |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Disagreed Repair | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | false                           | Vibration-checked cvt                | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "true"
    * Dcase is assigned to "VCAT Support"
    * Login as PCCF1Analyst
    * PCCF1Analyst Opens dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Want to override the system recommendation as "No"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              6000 |            6000 |           6000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Claimed amount in WL is over the approved repair is updated in VCAN approval comments

  @Regression @test20
  Scenario: Test20: Warranty line submission for audio D-case resolved at vcat with WL amount lesser than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | Audio     |        5408 | 1N6AD0EV3FN755780 |    8000 | true     | 259156CA0E | RN74AA |
    * Login as service advisor
    When service advisor clicks on New Audio Symptom Form
    * service advisor creates Dcase for created RO
    * service advisor selects Audio plays with ignition off check box
    * service advisor submits the dcase
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician
    * Technician opens dcase
    * Technician selects Audio plays with ignition off check box
    * Technician checks Iconfirm checkbox
    * Tecnician clicks on diagnose button
    Then Technician responds to Yes/No question with "No"
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
    * Technician logs out
    * Dcase is assigned to "VCAT Support"
    * Login as PCCF1Analyst
    * PCCF1Analyst Opens dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Want to override the system recommendation as "No"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              1000 |            1000 |           1000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Approved by F1 msg is updated in VCAN approval comments

  @Regression @test21
  Scenario: Test21: Warranty line submission for audio D-case resolved at vcat with WL amount greater than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | Audio     |        5408 | 1N6AD0EV3FN755780 |    8000 | true     | 259156CA0E | RN74AA |
    * Login as service advisor
    When service advisor clicks on New Audio Symptom Form
    # * service advisor creates Dcase for "AT-318589"
    * service advisor creates Dcase for created RO
    * Service Advisor selects below symptoms under General Symptoms
      | General Symptoms                                |
      | Audio plays with ignition off;Button Appearance |
    * Service Advisor selects below symptoms under Systems and components
      | Systems Components                                       |
      | Navigation;Address not found;Satellite;No Signal message |
    * Service Advisor selects below symptoms under Occurs when
      | Occurs_when            |
      | Engine off;Smooth road |
    * Service Advisor selects below symptoms under Occurs where
      | Occurs_Where          |
      | Rural;Hill / Mountain |
    * Service Advisor selects below symptoms under Occurs condition
      | Occurs_Condition              |
      | Day;Hot Outside (insert Temp) |
    * Service Advisor selects below symptoms under when did the concern begin dropdown
      | when_did_the_concern_begin? |
      | Problem just started        |
    * Service Advisor selects below symptoms under frequency dropdown
      | Frequency  |
      | Continuous |
    * Service Advisor enters following comments in comments textbox "Comments"
    * Service Advisor selects "Factory Warranty" as payment type
    * service advisor submits the dcase
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician "DRODIJ99"
    #* Technician opens dcase with dcaseID "D-64427" and RO number "AT-469316"
    * Technician opens dcase
    * Technician selects below symptoms under General Symptoms
      | General Symptoms                                |
      | Audio plays with ignition off;Button Appearance |
    * Technician selects below symptoms under Systems and components
      | Systems Components                                       |
      | Navigation;Address not found;Satellite;No Signal message |
    * Technician selects below symptoms under Occurs when
      | Occurs_when            |
      | Engine off;Smooth road |
    * Technician selects below symptoms under Occurs where
      | Occurs_Where          |
      | Rural;Hill / Mountain |
    * Technician selects below symptoms under Occurs condition
      | Occurs_Condition              |
      | Day;Hot Outside (insert Temp) |
    * Technician selects below symptoms under when did the concern begin dropdown
      | when_did_the_concern_begin? |
      | Problem just started        |
    * Technician selects below symptoms under frequency dropdown
      | Frequency  |
      | Continuous |
    * Technician checks Iconfirm checkbox
    * Tecnician clicks on diagnose button
    * Verify "1 TSB" evaluated
    Then Technician responds to Yes/No question with "No"
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
    * Thank you screen is displayed to technician
    * Technician logs out
    * Dcase is assigned to "VCAT Support"
    * Login as PCCF1Analyst
    * PCCF1Analyst Opens dcase
    * VCAT User selects System make the right recommendation as "Yes"
    * VCAT User selects Want to override the system recommendation as "No"
    * VCAT user enters VCAT suport notes "vcat support"
    * VCAT user verifies payment type
    * VCATuser clicks on submit
    * Dcase is resolved and summary screen is displayed
    * VCAT user logs out
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              6000 |            6000 |           6000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Claimed amount in WL is over the approved repair is updated in VCAN approval comments

  @Regression @test22
  Scenario: Test22: Warranty line submission for audio D-case resolved at technician with WL amount greater than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | Audio     |        5408 | 1N6AD0EV3FN755780 |    8000 | true     | 259156CA0E | RN74AA |
    * Login as service advisor
    When service advisor clicks on New Audio Symptom Form
    # * service advisor creates Dcase for "AT-318589"
    * service advisor creates Dcase for created RO
    * Service Advisor selects below symptoms under General Symptoms
      | General Symptoms                                |
      | Audio plays with ignition off;Button Appearance |
    * Service Advisor selects below symptoms under Systems and components
      | Systems Components                                       |
      | Navigation;Address not found;Satellite;No Signal message |
    * Service Advisor selects below symptoms under Occurs when
      | Occurs_when            |
      | Engine off;Smooth road |
    * Service Advisor selects below symptoms under Occurs where
      | Occurs_Where          |
      | Rural;Hill / Mountain |
    * Service Advisor selects below symptoms under Occurs condition
      | Occurs_Condition              |
      | Day;Hot Outside (insert Temp) |
    * Service Advisor selects below symptoms under when did the concern begin dropdown
      | when_did_the_concern_begin? |
      | Problem just started        |
    * Service Advisor selects below symptoms under frequency dropdown
      | Frequency  |
      | Continuous |
    * Service Advisor enters following comments in comments textbox "Comments"
    * Service Advisor selects "Factory Warranty" as payment type
    * service advisor submits the dcase
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician "DRODIJ99"
    #* Technician opens dcase with dcaseID "D-64427" and RO number "AT-469316"
    * Technician opens dcase
    * Technician selects below symptoms under General Symptoms
      | General Symptoms                                |
      | Audio plays with ignition off;Button Appearance |
    * Technician selects below symptoms under Systems and components
      | Systems Components                                       |
      | Navigation;Address not found;Satellite;No Signal message |
    * Technician selects below symptoms under Occurs when
      | Occurs_when            |
      | Engine off;Smooth road |
    * Technician selects below symptoms under Occurs where
      | Occurs_Where          |
      | Rural;Hill / Mountain |
    * Technician selects below symptoms under Occurs condition
      | Occurs_Condition              |
      | Day;Hot Outside (insert Temp) |
    * Technician selects below symptoms under when did the concern begin dropdown
      | when_did_the_concern_begin? |
      | Problem just started        |
    * Technician selects below symptoms under frequency dropdown
      | Frequency  |
      | Continuous |
    * Technician checks Iconfirm checkbox
    * Tecnician clicks on diagnose button
    * Verify "1 TSB" evaluated
    Then Technician responds to Yes/No question with "No"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed         |
      | Engine                | Defect in factory materials |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              6000 |            6000 |           6000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Claimed amount in WL is over the approved repair is updated in VCAN approval comments

  @Regression @test23
  Scenario: Test23: Warranty line submission for audio D-case resolved at technician with WL amount lesser than repair amount
    Given Create RO with below set of details
      | Component | Dealer Code | VIN               | Mileage | Warranty | PFP        | OPCODE |
      | Audio     |        5408 | 1N6AD0EV3FN755780 |    8000 | true     | 259156CA0E | RN74AA |
    * Login as service advisor
    When service advisor clicks on New Audio Symptom Form
    # * service advisor creates Dcase for "AT-318589"
    * service advisor creates Dcase for created RO
    * Service Advisor selects below symptoms under General Symptoms
      | General Symptoms                                |
      | Audio plays with ignition off;Button Appearance |
    * Service Advisor selects below symptoms under Systems and components
      | Systems Components                                       |
      | Navigation;Address not found;Satellite;No Signal message |
    * Service Advisor selects below symptoms under Occurs when
      | Occurs_when            |
      | Engine off;Smooth road |
    * Service Advisor selects below symptoms under Occurs where
      | Occurs_Where          |
      | Rural;Hill / Mountain |
    * Service Advisor selects below symptoms under Occurs condition
      | Occurs_Condition              |
      | Day;Hot Outside (insert Temp) |
    * Service Advisor selects below symptoms under when did the concern begin dropdown
      | when_did_the_concern_begin? |
      | Problem just started        |
    * Service Advisor selects below symptoms under frequency dropdown
      | Frequency  |
      | Continuous |
    * Service Advisor enters following comments in comments textbox "Comments"
    * Service Advisor selects "Factory Warranty" as payment type
    * service advisor submits the dcase
    * Thank you screen is displayed to service advisor
    * service advisor logs out
    * Dcase is assigned to "Technician"
    * login as Technician "DRODIJ99"
    * Technician opens dcase
    * Technician selects below symptoms under General Symptoms
      | General Symptoms                                |
      | Audio plays with ignition off;Button Appearance |
    * Technician selects below symptoms under Systems and components
      | Systems Components                                       |
      | Navigation;Address not found;Satellite;No Signal message |
    * Technician selects below symptoms under Occurs when
      | Occurs_when            |
      | Engine off;Smooth road |
    * Technician selects below symptoms under Occurs where
      | Occurs_Where          |
      | Rural;Hill / Mountain |
    * Technician selects below symptoms under Occurs condition
      | Occurs_Condition              |
      | Day;Hot Outside (insert Temp) |
    * Technician selects below symptoms under when did the concern begin dropdown
      | when_did_the_concern_begin? |
      | Problem just started        |
    * Technician selects below symptoms under frequency dropdown
      | Frequency  |
      | Continuous |
    * Technician checks Iconfirm checkbox
    * Tecnician clicks on diagnose button
    * Technician responds to Yes/No question with "No"
    #* Technician verifies text No Further Questions
    * Technician clicks on submit on questions screen
    * CCC screen is displayed
    * Tecnician selects below values in Cause section on ccc screen
      | CCC-Part Failed First | CCC-Why Part Failed         |
      | Engine                | Defect in factory materials |
    * Tecnician selects below values in Correction section with below repair recmmendation on ccc screen
      | CCC-Technician Repair Recommendation | CCC-Repair Justification/Comments |
      | Break Issue                          | Comments                          |
    * Tecnician clicks on submit on ccc screen
    * Dcase is resolved and summary screen is displayed
    * Technician logs out
    * Evaluate NAA Rules and verify routeToVcat is "false"
    * Dcase is assigned to "Warranty Admin"
    * Submit WarrantyLine for dcase with below values
      | Status_Code | ROL | WL_Expense_Amount | WL_Labor_Amount | WL_Part_Amount |
      | SU          |   1 |              1000 |            1000 |           1000 |
    * Verify dcase case status is changed to "Resolved-AutomatedRecommendation" and latest claim status is updated to "Submitted"
    * Verify Approved by F1 msg is updated in VCAN approval comments
