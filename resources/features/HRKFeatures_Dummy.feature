Feature: Title of your feature

 

  @Regression @HRKTest3
  Scenario Outline: Service Advisor : Verify Connector Search is working as expected for search type Vehicle Make / Model
    Given Login as service advisor of "<Dealer_Code>" dealer
    * Service Advisor clicks on "Connector Lookup" from left side menu
    * Service Advisor verifies connector lookup ui is displayed
    When On connector lookup ui Service Advisor selects "<Search_Type>" radio button
    * Service Advisor selects "<Make>" from make drop down
    * Service Advisor selects "<Model>" from model drop down
    * Service Advisor selects "<Year>" from year drop down
    Then Service Advisor verifies list of connectors are displayed
    * Service Advisor click on first record from the list of connectors
    * Service Advisor verifies Connector Info screen is displayed
    * Service Advisor clicks on back to search results button
    * Service Advisor verifies list of connectors are displayed
    * Service Advisor clicks on back to search results link
    * Service Advisor verifies connector lookup ui is displayed
    * service advisor logs out

    Examples: 
      | Dealer_Code | Search_Type          | Make   | Model | Year |
      |        3900 | Vehicle Make / Model | Nissan | Titan | 2022 |

  @Regression @HRKTest4
  Scenario Outline: External User : Verify Connector Search is working as expected for search type Vehicle Make / Model
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When On connector lookup ui external user selects "<Search_Type>" radio button
    * External User selects "<Make>" from make drop down
    * External User selects "<Model>" from model drop down
    * External User selects "<Year>" from year drop down
    Then External user verify list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External user click on back to search results link
    * External user verify list of connectors are displayed
    * External user clicks on back to search results link
    * External user Verifies connector lookup ui is displayed

    Examples: 
      | Search_Type          | Make   | Model | Year |
      | Vehicle Make / Model | Nissan | Titan | 2022 |

  @Regression @HRKTest5
  Scenario: Service Advisor : Verify Connector Search is working as expected
    Given Login as service advisor of "3900" dealer
    * Click on "Connector Lookup" from left side menu
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "Repair Order / Work Order" radio button
    * Enter "AT-208390" in input textbox
    When Click on go button
    Then Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * Click on back to search results button
    * Verify list of connectors are displayed
    * Click on back to search results link
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "VIN" radio button
    * Enter "KNMAT2MT2FP529932" in input textbox
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * Click on back to search results button
    * Verify list of connectors are displayed
    * Click on back to search results link
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "Connector Type / Part No" radio button
    * Enter "sensor" in input textbox
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * Click on back to search results button
    * Verify list of connectors are displayed
    * Click on back to search results link
    * Verify connector lookup ui is displayed
    * service advisor logs out

 
  @Regression @HRKTest6
  Scenario: External User : Verify Connector Search is working as expected
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    #* On connector lookup ui external user selects "Connector Type / Part No" radio button
    #* External user enters "sensor" in input textbox
    #When External user clicks on go button
    #Then External user verify list of connectors are displayed
    #* External user clicks on first record from the list of connectors
    #* External user verify Connector Info screen is displayed
    #* External user click on back to search results link
    #* External user verify list of connectors are displayed
    #* External user clicks on back to search results link
    #* External user Verifies connector lookup ui is displayed
    * On connector lookup ui external user selects "VIN" radio button
    * External user enters "KNMAT2MT2FP529932" in input textbox
    * External user clicks on go button
    * External user verify list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External user click on back to search results link
    * External user verify list of connectors are displayed
    * External user clicks on back to search results link
    * External user Verifies connector lookup ui is displayed
     * Connector lookup page is displayed to external user
     * On connector lookup ui external user selects "Vehicle Make / Model" radio button
    * External User selects "Nissan" from make drop down
    * External User selects "Titan" from model drop down
    * External User selects "2022" from year drop down
    Then External user verify list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External user click on back to search results link
    * External user verify list of connectors are displayed
    * External user clicks on back to search results link
    * External user Verifies connector lookup ui is displayed

    
  @Regression @HRKTest7
  Scenario: Service Advisor : Verify Connector Search is working as expected for RO/WO Number with different VINs
    Given Login as service advisor of "3900" dealer
    * Click on "Connector Lookup" from left side menu
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "Repair Order / Work Order" radio button
    * Enter "KPTest1" in input textbox
    When Click on go button
    Then Select first VIN from the dropdown
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * Click on back to search results button
    * Verify list of connectors are displayed
    * Click on back to search results link
    * Verify connector lookup ui is displayed
    
 # All sceanrios
  @Regression @HRKTest1
  Scenario: Service Advisor : Verify Connector Search is working as expected
    Given Login as service advisor of "3900" dealer
    * Service Advisor clicks on "Connector Lookup" from left side menu
    * Service Advisor verifies connector lookup ui is displayed
    When On connector lookup ui Service Advisor selects "Vehicle Make / Model" radio button
    * Service Advisor selects "Nissan" from make drop down
    * Service Advisor selects "Titan" from model drop down
    * Service Advisor selects "2022" from year drop down
    Then Service Advisor verifies list of connectors are displayed
    * Service Advisor click on first record from the list of connectors
    * Service Advisor verifies Connector Info screen is displayed
    * Service Advisor clicks on back to search results button
    * Service Advisor verifies list of connectors are displayed
    * Service Advisor clicks on back to search results link
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "Repair Order / Work Order" radio button
    * Enter "KL8521" in input textbox
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * Click on back to search results button
    * Verify list of connectors are displayed
    * Click on back to search results link
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "VIN" radio button
    * Enter "KNMAT2MT2FP529932" in input textbox
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * Click on back to search results button
    * Verify list of connectors are displayed
    * Click on back to search results link
    * Verify connector lookup ui is displayed
    * service advisor logs out
    
    #External All
     @Regression @HRKTest2 
  Scenario: External User : Verify Connector Search is working as expected
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    * On connector lookup ui external user selects "VIN" radio button
    * External user enters "KNMAT2MT2FP529932" in input textbox
    * External user clicks on go button
    * External user verify list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External user click on back to search results link
    * External user verify list of connectors are displayed
    * External user clicks on back to search results link
    * External user Verifies connector lookup ui is displayed
     * Connector lookup page is displayed to external user
     * On connector lookup ui external user selects "Vehicle Make / Model" radio button
    * External User selects "Nissan" from make drop down
    * External User selects "Titan" from model drop down
    * External User selects "2022" from year drop down
    Then External user verify list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External user click on back to search results link
    * External user verify list of connectors are displayed
    * External user clicks on back to search results link
    * External user Verifies connector lookup ui is displayed
 