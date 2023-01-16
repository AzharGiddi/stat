Feature: HRK Connector Search

  # All scenarios with QA Data
  @Regression @HRK2 @HRK2TC01
  Scenario Outline: TC01 User : Verify Connector Search is working as expected
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    * User verifies Connector General Repair Instructions link is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User click on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on back to search results button
    * User verifies list of connectors are displayed
    * User clicks on back to search button
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "Repair Order / Work Order" radio button
    * Enter "<RO_Number_QA>" in input textbox
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * User clicks on back to search results button
    * Verify list of connectors are displayed
    * User clicks on back to search button
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "VIN" radio button
    * Enter "<VIN>" in input textbox
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * User clicks on back to search results button
    * Verify list of connectors are displayed
    * User clicks on back to search button
    * Verify connector lookup ui is displayed
    * User logs out

    Examples: 
      | Dealer_User     | Make   | Model | Year | VIN               | RO_Number_QA | RO_Number_SIT |
      | DFONTT07        | Nissan | Titan | 2022 | KNMAT2MT2FP529932 | AT-105711    | AT-142887     |
      | XD764222        | Nissan | Titan | 2022 | KNMAT2MT2FP529932 | AT-105711    | AT-142887     |
      | DWINKM31        | Nissan | Titan | 2022 | KNMAT2MT2FP529932 | AT-262385    | AT-142887     |
      | OtherDealerUser | Nissan | Titan | 2022 | KNMAT2MT2FP529932 | AT-262385    | AT-142887     |
      | DMEINJ06        | Nissan | Titan | 2022 | KNMAT2MT2FP529932 | AT-262385    | AT-142887     |

  #External All
  @Regression @HRK1TC01
  Scenario Outline: TC02 External User : Verify Connector Search is working as expected
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    Then On connector lookup ui external user selects "VIN" radio button
    * External user enters "<VIN>" in input textbox
    * External user clicks on go button
    * External user verify list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External user click on back to search results button
    * External user verify list of connectors are displayed
    * External user clicks on back to search button
    * External user Verifies connector lookup ui is displayed
    * On connector lookup ui external user selects "Vehicle Make / Model" radio button
    * External User selects "<Make>" from make drop down
    * External User selects "<Model>" from model drop down
    * External User selects "<Year>" from year drop down
    * External user verify list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External user click on back to search results button
    * External user verify list of connectors are displayed
    * External user clicks on back to search button
    * External user Verifies connector lookup ui is displayed

    Examples: 
      | Make   | Model | Year | VIN               |
      | Nissan | Titan | 2022 | KNMAT2MT2FP529932 |

  #RO with different VINs
  @Regression @HRKTest3
  Scenario Outline: TC03 Service Advisor : Verify Connector Search is working as expected for RO/WO Number with different VINs
    Given Create a repair order with "<Dealer_Code>" and "<RO_Number>" for following VINs
      | 1N4BL3AP0GC151044 |
      | 1N6AA0EC7FN513355 |
    * Login as service advisor of "<Dealer_Code>" dealer
    * Click on "Connector Lookup" from left side menu
    * Verify connector lookup ui is displayed
    * On connector lookup ui select "Repair Order / Work Order" radio button
    * Enter "<RO_Number>" in input textbox
    When Click on go button
    Then Select first VIN from the dropdown
    * Click on go button
    * Verify list of connectors are displayed
    * Click on first record from the list of connectors
    * Verify Connector Info screen is displayed
    * Click on back to search results button
    * Verify list of connectors are displayed
    * Click on back to search button
    * Verify connector lookup ui is displayed

    Examples: 
      | Dealer_Code | RO_Number |
      |        3529 | AT-107099 |

  @Regression @HRK2 @HRK5TC01
  Scenario Outline: TC04 User : Verify Connector Search is working as expected when user enters 4 characters
    Given Create a repair order with "3900" and "AT-12345" for following VINs
      | 1N4BL3AP0GC151044 |
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    Then On connector lookup ui select "Repair Order / Work Order" radio button
    # * User enters four characters "<Three_Digit_RO_Number>" in input textbox
    # * User verifies list of ROs is not displayed
    * User enters four characters "<Four_Digit_RO_Number>" in input textbox
    * User verifies list of ROs is displayed
    * User clicks on search icon
    * User verifies list of connectors are displayed
    * User clicks on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on back to search results button
    * User verifies list of connectors are displayed
    * User clicks on back to search button
    * User verifies connector lookup ui is displayed
    * User logs out

    Examples: 
      | Dealer_User     | Four_Digit_RO_Number | Three_Digit_RO_Number | Four_Digit_RO_Number_SIT |
      | DDODGK26        | AT-1                 | KL1                   | KL11                     |
      | DWATAC64        | AT-1                 | KL1                   | KL11                     |
      | DWINKM31        | AT-1                 | KL1                   | KL11                     |
      | OtherDealerUser | AT-1                 | KL1                   | KL11                     |
      | DMEINJ06        | AT-1                 | KL1                   | KL11                     |

  @Regression @HRK10TC01
  Scenario: TC05 Verify that the connector manager users have ability to login HRK application and validate the menu items and dashboard section
    Given Login to the application as HRK Manager
    Then HRK Manager verifies "Manage System Rules" and "Reports" are displayed under Navigation section
    * HRK Manager clicks on "Manage System Rules"
    * HRK Manager verifies Manage System Rules tab is displayed
    * HRK Manager clicks on "Reports"
    * HRK Manager verifies Reports tab is displayed
    * HRK Manager logs out

  @Regression1 @HRK12TC01
  Scenario: TC06 Verify that the connector manager users have ability to update or remove what's new message
    Given Login to the application as HRK Manager
    Then HRK Manager clicks on "Manage System Rules"
    * HRK Manager click on "HRK - Whats New Messaging" under Manage system rules
    * HRK Manager unchecks the Enable Message button
    * HRK Manager logs out
    * Launch Extenral User Connector search url
    * Extenral User Verfies Whats new message is not displayed

  @Regression @HRK13TC01, @HRK14TC01
  Scenario: TC07 Verify Connector info, additional info and applicable model years are displayed on Connector details page
    Given Login to the application as HRK Manager
    Then HRK Manager clicks on "Manage Connectors"
    * HRK Manager verifies Manage Connector tab is opened with heading "Manage Connectors"
    #* On Manage Connector tab, HRK Manager verifies table with connector data is displayed
    * HRK Manager verifies table header has "Service Dwg Rev#" as column header
    * HRK Manager verifies table header has "Unit Name(Installation Place)" as column header
    * HRK Manager verifies table header has "Unit Description" as column header
    * HRK Manager verifies table header has "Cavities" as column header
    * HRK Manager verifies table header has "Color" as column header
    * HRK Manager verifies table header has "Harness Name" as column header
    * HRK Manager verifies table header has "Solder Sleeve" as column header
    * HRK Manager verifies table header has "AWG Wire" as column header
    * HRK Manager verifies table header has "Connector Type" as column header
    * HRK Manager verifies 100 rows are displayed per page
    * HRK Manager clicks on record no. 1 from the list of connectors
    * HRK Manager verifies Connector Info page is displayed
    * HRK Manager verifies "Connector Information" section is displayed
    * HRK Manager verifies "ADDITIONAL INFORMATION" section is displayed
    * HRK Manager verifies "APPLICABLE MODEL/ YEARS" section is displayed

  @Regression @HRK14TC02
  Scenario: TC08 Verify HRK Manager verifies additional information fields are displayed as expected
    Given Login to the application as HRK Manager
    Then HRK Manager clicks on "Manage Connectors"
    * HRK Manager verifies Manage Connector tab is opened with heading "Manage Connectors"
    * HRK Manager clicks on record no. 1 from the list of connectors
    * HRK Manager verifies Connector Info page is displayed
    * HRK Manager verifies "ADDITIONAL INFORMATION" section is displayed
    #* HRK Manager verifies "ADDITIONAL INFORMATION" section is exapndable/collapsable
    * HRK Manager verifies "Unit Name (SCD Name)" is displayed under Additional Information section
    * HRK Manager verifies "Service Dwg Rev #" is displayed under Additional Information section
    * HRK Manager verifies "Keywords" is displayed under Additional Information section
    * HRK Manager verifies "Delist Connector" is displayed under Additional Information section
    * HRK Manager verifies "Harness Name" is displayed under Additional Information section
    * HRK Manager verifies "Supplier Part #" is displayed under Additional Information section
    * HRK Manager click on Edit button under Additional Information section
    * HRK Manager click on Delist Connector check box and verifies Delist Date is visible under Additional Information section
    * HRK Manager verifies "Delist Date" is displayed under Additional Information section

  @Regression @HRK14TC03
  Scenario: TC09 Verify Manage Connector Applicable model year section is displayed on connector info page
    Given Login to the application as HRK Manager
    Then HRK Manager clicks on "Manage Connectors"
    * HRK Manager verifies Manage Connector tab is opened with heading "Manage Connectors"
    * HRK Manager clicks on record no. 1 from the list of connectors
    * HRK Manager verifies Connector Info page is displayed
    * HRK Manager verifies "APPLICABLE MODEL/ YEARS" section is displayed
    * HRK Manager verifies Model/Year list is displayed

  @Regression @HRK15TC01
  Scenario: TC10 Verify Manage Connector is able to update and save the additional info fields on Connector Info Page
    Given Login to the application as HRK Manager
    Then HRK Manager clicks on "Manage Connectors"
    * HRK Manager verifies Manage Connector tab is opened with heading "Manage Connectors"
    * HRK Manager clicks on record no. 1 from the list of connectors
    * HRK Manager verifies Connector Info page is displayed
    * HRK Manager verifies "ADDITIONAL INFORMATION" section is displayed
    * HRK Manager verifies "Unit Name (SCD Name)" is displayed under Additional Information section
    * HRK Manager verifies "Service Dwg Rev #" is displayed under Additional Information section
    * HRK Manager verifies "Keywords" is displayed under Additional Information section
    * HRK Manager verifies "Delist Connector" is displayed under Additional Information section
    * HRK Manager verifies "Harness Name" is displayed under Additional Information section
    * HRK Manager verifies "Supplier Part #" is displayed under Additional Information section
    * HRK Manager click on Edit button under Additional Information section
    * HRK Manager enter "A" in Keywords field
    * HRK Manager clicks on Save button

  @Regression @HRK15TC02
  Scenario: TC11 Verify Cancel button functionality is working as expected on Additional info section
    Given Login to the application as HRK Manager
    Then HRK Manager clicks on "Manage Connectors"
    * HRK Manager verifies Manage Connector tab is opened with heading "Manage Connectors"
    * HRK Manager clicks on record no. 1 from the list of connectors
    * HRK Manager verifies Connector Info page is displayed
    * HRK Manager click on Edit button under Additional Information section
    * HRK Manager enter "A" in Keywords field
    * HRK Manager clicks on Cancel button
    * HRK Manager verifies keyword value is not saved.

  @Regression @HRK19TC01
  Scenario Outline: TC12 Verify that the User details field are auto populated with Dealer name and Dealer code along with name and email on feedback component on Connector Information page
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User click on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on feedback button
    * User verifies feedback comments modal window is displayed
    # * User enters "<Dealer_Name>" in the Dealer? field
    * User verifies Dealer field is autopopulated with "<Dealer_Name>"
    * User verifies Name field is autopopulated with "<Name>"
    * User verifies Email field is autopopulated with "<Email>"
    * User verifies make/model/year field is autopopulated with "<Make>" "<Model>" "<Year>"
    * User enter "Testing" in additional comments text box
    * User attaches a file
    * User clicks on submit
    * User verifies Thank you for your comments message is displayed

    Examples: 
      | Dealer_User | Dealer_Name              | Make   | Model | Year | Name            | Email                       |
      | DFONTT07    | MIKE SMITH NISSAN (3529) | Nissan | Titan | 2022 | Tailor Fontenot | azharali.baig@nissa-usa.com |

  @Regression @HRK19TC02
  Scenario Outline: TC13 Verify that the User details field are auto populated with Dealer name and Dealer code along with name and email on feedback component on Connector look up results page
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User clicks on feedback button
    * User verifies feedback comments modal window is displayed
    * User verifies Dealer field is autopopulated with "<Dealer_Name>"
    * User verifies Name field is autopopulated with "<Name>"
    * User verifies Email field is autopopulated with "<Email>"
    * User verifies make/model/year field is autopopulated with "<Make>" "<Model>" "<Year>"
    * User enter "Testing" in additional comments text box
    * User attaches a file
    * User clicks on submit
    * User verifies Thank you for your comments message is displayed

    Examples: 
      | Dealer_User | Dealer_Name              | Make   | Model | Year | Name            | Email                       |
      | DFONTT07    | MIKE SMITH NISSAN (3529) | Nissan | Titan | 2022 | Tailor Fontenot | azharali.baig@nissa-usa.com |

  @Regression @HRK19TC03
  Scenario: TC14 External User : Verify that the External user details field are auto populated with Dealer name and Dealer code along with name and email on feedback component on Connector Information page
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    Then On connector lookup ui external user selects "VIN" radio button
    * External user enters "KNMAT2MT2FP529932" in input textbox
    * External user clicks on go button
    * External User verifies list of connectors are displayed
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External User clicks on feedback button
    * External User verifies feedback comments modal window is displayed
    * External User enters "3900" in the External? field
    * External User enters "Ross Schewe" in Names field
    * External User enters "Ramya.Bapatla@nissan-usa.com" in Email id field
    * External User verifies make/model/year field is autopopulated with "Nissan - ROGUE - 2015"
    * External User enter "Testing" in additional comments text box
    * External User attaches a file
    * External User clicks on submit
    * External User verifies Thank you for your comments message is displayed

  @Regression @HRK19TC04
  Scenario: TC15 External User : Verify that the External user details field are auto populated with Dealer name and Dealer code along with name and email on feedback component on Connector lookup results page
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    Then On connector lookup ui external user selects "VIN" radio button
    * External user enters "KNMAT2MT2FP529932" in input textbox
    * External user clicks on go button
    * External User verifies list of connectors are displayed
    * External User clicks on feedback button
    * External User verifies feedback comments modal window is displayed
    * External User enters "3900" in the External? field
    * External User enters "Ross Schewe" in Names field
    * External User enters "Ramya.Bapatla@nissan-usa.com" in Email id field
    * External User verifies make/model/year field is autopopulated with "Nissan - ROGUE - 2015"
    * External User enter "Testing" in additional comments text box
    * External User attaches a file
    * External User clicks on submit
    * External User verifies Thank you for your comments message is displayed

  @Regression @HRK2 @HRK20TC01
  Scenario Outline: TC17 User : Verify General Repair Instructions link is visible on the Connector Lookup Page
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    * User verifies Connector General Repair Instructions link is displayed
    * User clicks on connector general repair instructions link
    * User verifies GENERAL REPAIR INSTRUCTIONS page is displayed in a new window

    Examples: 
      | Dealer_User |
      | DSCHER49    |
      | HRKMANAGER  |

  @Regression @HRK20TC02
  Scenario Outline: TC18 External User : Verify General Repair Instructions link is visible on the Connector Lookup Page
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    Then User clicks on connector general repair instructions link
    * User verifies GENERAL REPAIR INSTRUCTIONS page is displayed in a new window

  @Regression @HRK21TC01
  Scenario Outline: TC19 User: Verify that the buttons under Connector Info sections are working as expected
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User uses smart search and search with "<ConnectorId_SIT>"
    * User click on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on connector general repair instructions button
    * User verifies GENERAL REPAIR INSTRUCTIONS page is displayed in a new window
    * User closes GENERAL REPAIR INSTRUCTIONS page
    * User clicks on Connector Disassembly Instructions button
    * User verifies Connector Disassembly Instructions page is displayed with heading "<ConnectorId>" in a new window
    * User closes Connector Disassembly Instructions page
    * User clicks on Required Related Parts button
    * User verifies Suplemental parts page is displayed in a new window

    Examples: 
      | Dealer_User | Make   | Model | Year | Name        | Email                        | ConnectorId_QA | ConnectorId_SIT |
      | DSCHER49    | Nissan | Titan | 2022 | Ross Schewe | Ramya.Bapatla@nissan-usa.com | 24008 9DM5B    | 24008 9DM6A     |

  @Regression @HRK21TC02
  Scenario Outline: TC16 External User: Verify that the buttons under Connector Info sections are working as expected
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    Then On connector lookup ui external user selects "VIN" radio button
    * External user enters "KNMAT2MT2FP529932" in input textbox
    * External user clicks on go button
    * External User verifies list of connectors are displayed
    * External User uses smart search and search with "<ConnectorId_QA>"
    * External user clicks on first record from the list of connectors
    * External user verify Connector Info screen is displayed
    * External User clicks on connector general repair instructions button
    * External User verifies GENERAL REPAIR INSTRUCTIONS page is displayed in a new window
    * External User closes GENERAL REPAIR INSTRUCTIONS page
    * External User clicks on Connector Disassembly Instructions button
    * External User verifies Connector Disassembly Instructions page is displayed with heading "<ConnectorId>" in a new window
    * External User closes Connector Disassembly Instructions page
    * External User clicks on Required Related Parts button
    * External User verifies Suplemental parts page is displayed in a new window

    Examples: 
      | Dealer_User | Make   | Model | Year | Name        | Email                        | ConnectorId_QA | ConnectorId_SIT |
      | DSCHER49    | Nissan | Titan | 2022 | Ross Schewe | Ramya.Bapatla@nissan-usa.com | 24008 9DM5B    | 24008 9DM6A     |

  @Regression @HRK21TC04
  Scenario Outline: TC20 User: Verify that the User General repair instructions page is displayed in a new window, when user clicks on General Repair instruction
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User uses smart search and search with "<ConnectorId>"
    * User click on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on connector general repair instructions button
    * User verifies GENERAL REPAIR INSTRUCTIONS page is displayed in a new window
    * User clicks on "12345 9DN3C" file label
    * User verifies pdf file is opened in reading frame
    * User clicks on "24008 9DD1D" file label
    * User verifies video file is opened in reading frame
    
    Examples: 
      | Dealer_User | Make   | Model | Year | Name        | Email                        | ConnectorId | ConnectorId_SIT |
      | DSCHER49    | Nissan | Titan | 2022 | Ross Schewe | Ramya.Bapatla@nissan-usa.com | 24008 9DM    | 24008 9DM6A     |

  @Regression @HRK21TC03
  Scenario Outline: TC21 User: Verify that the User Supplemental parts page is displayed in a new window, when user clicks on Required Related Parts button
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User click on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on Required Related Parts button
    * User verifies Suplemental parts page is displayed in a new window

    Examples: 
      | Dealer_User | Make   | Model | Year |
      | DSCHER49    | Nissan | Titan | 2022 |

  @Regression @HRK22TC01
  Scenario Outline: TC22 User: Verify that the View Connector page is displayed in a new window, when user clicks on i icon on connector results page
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "VIN" radio button
    * User enters "<VIN>" in vin textbox
    * User clicks on search button
    Then User verifies list of connectors are displayed
    * User clicks on i icon in the Kit description of first record from the list of connectors
    * User verifies View Connector Page is displayed in a new window

    Examples: 
      | Dealer_User | VIN               |
      | DSCHER49    | KNMAT2MT2FP529932 |

  @Regression @HRK22TC02
  Scenario Outline: TC23 <User> : Verify info links are clickable and takes user to the corresponding landing page in a new tab
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "VIN" radio button
    * User enters "<VIN>" in vin textbox
    * User clicks on search button
    Then User verifies list of connectors are displayed
    * User click on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on i icon of the Kit description under connector info section
    * User verifies View Connector Page is displayed in a new window
    * User closes View Connector Page
    * User clicks on i icon of the AWG Wire under connector info section
    * User verifies View Solder Sleeve Page is displayed in a new window
    * User closes View Solder Sleeve Page
    * User clicks on i icon of the Solder Sleeve under connector info section
    * User verifies View Solder Sleeve Page is displayed in a new window

    Examples: 
      | User        | Dealer_User | VIN               |
      | User        | DSCHER49    | KNMAT2MT2FP529932 |
      | HRK Manager | HRKMANAGER  | KNMAT2MT2FP529932 |

  @Regression @HRKNoResult
  Scenario Outline: TC24 User: Verify No Results Found message when searched with RO
    Given Create a repair order with "<Dealer_Code>" and "<RO_Number>" for following VINs
      | 1N4BL2AP7AC111366 |
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    When On connector lookup ui select "Repair Order / Work Order" radio button
    * Enter "<RO_Number>" in input textbox
    * User verifies list of ROs is displayed
    * User clicks on search icon
    Then User verifies No Matching record text is displayed

    Examples: 
      | Dealer_Code | Dealer_User | RO_Number |
      |        3529 | DFONTT07    | KL9720    |

  @Regression @HRK88TC01
  Scenario Outline: TC25 External User: Verify the column header of connector data table on connectors results page when searched with VIN
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    Then On connector lookup ui external user selects "VIN" radio button
    * External user enters "<VIN>" in input textbox
    * External user clicks on go button
    * External User verifies list of connectors are displayed
    * External User verifies "Connector Name" column is displayed in 1 position
    * External User verifies "Connector Type" column is displayed in 2 position
    * External User verifies "Service Kit Part Number" column is displayed in 3 position
    * External User verifies "Kit Description" column is displayed in 4 position
    * External User verifies "Color" column is displayed in 5 position
    * External User verifies "Cavities" column is displayed in 6 position

    Examples: 
      | VIN               |
      | KNMAT2MT2FP529932 |

  @Regression @HRK88TC02
  Scenario Outline: TC26 External User: Verify the column header of connector data table on connectors results page when searched with Vehicle make and model
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    * On connector lookup ui external user selects "Vehicle Make / Model" radio button
    * External User selects "<Make>" from make drop down
    * External User selects "<Model>" from model drop down
    * External User selects "<Year>" from year drop down
    * External User verifies list of connectors are displayed
    * External User verifies "Connector Name" column is displayed in 1 position
    * External User verifies "Connector Type" column is displayed in 2 position
    * External User verifies "Service Kit Part Number" column is displayed in 3 position
    * External User verifies "Kit Description" column is displayed in 4 position
    * External User verifies "Color" column is displayed in 5 position
    * External User verifies "Cavities" column is displayed in 6 position

    Examples: 
      | Make   | Model | Year |
      | Nissan | Titan | 2022 |

  @Regression @HRK88TC03
  Scenario Outline: TC27 External User: Verify the column header of connector data table on connectors results page when searched with Connector Id
    Given Launch Extenral User Connector search url
    * Connector lookup page is displayed to external user
    When External user clicks on Connector Look up from left side menu
    * On connector lookup ui external user selects "Connector Type / Part No" radio button
    * External User enter "<Connector_ID>" in Connector Keyword Type Part textbox
    * External user clicks on go button
    * External User verifies list of connectors are displayed
    * External User verifies "Connector Name" column is displayed in 1 position
    * External User verifies "Connector Type" column is displayed in 2 position
    * External User verifies "Service Kit Part Number" column is displayed in 3 position
    * External User verifies "Kit Description" column is displayed in 4 position
    * External User verifies "Color" column is displayed in 5 position
    * External User verifies "Cavities" column is displayed in 6 position

    Examples: 
      | Connector_ID |
      | 24008 9DM5B  |
      
   @Regression @HRK88TC04
  Scenario Outline: TC28 Dealer User: Verify the column header of connector data table on connectors results page when searched with Vehicle make and model
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    * User verifies Connector General Repair Instructions link is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User verifies "Connector Name" column is displayed in 1 position
    * User verifies "Connector Type" column is displayed in 2 position
    * User verifies "Service Kit Part Number" column is displayed in 3 position
    * User verifies "Kit Description" column is displayed in 4 position
    * User verifies "Color" column is displayed in 5 position
    * User verifies "Cavities" column is displayed in 6 position
    
    Examples:
     | Dealer_User     | Make   | Model | Year | 
      | DFONTT07        | Nissan | Titan | 2022 |  
      
      @Regression @HRK88TC05
  Scenario Outline: TC29 Dealer User: Verify the column header of connector data table on connectors results page when searched with Connector Id
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    * User verifies Connector General Repair Instructions link is displayed
    When On connector lookup ui User selects "Connector Type / Part No" radio button
    * User enter "<Connector_ID>" in Connector Keyword Type Part textbox
    * User clicks on search button
    Then User verifies list of connectors are displayed
    * User verifies "Connector Name" column is displayed in 1 position
    * User verifies "Connector Type" column is displayed in 2 position
    * User verifies "Service Kit Part Number" column is displayed in 3 position
    * User verifies "Kit Description" column is displayed in 4 position
    * User verifies "Color" column is displayed in 5 position
    * User verifies "Cavities" column is displayed in 6 position
    
   Examples: 
     | Dealer_User  | Connector_ID |
      | DFONTT07 | 24008 9DM5B  |
      
       @Regression @HRK88TC06
  Scenario Outline: TC30 Dealer User: Verify the column header of connector data table on connectors results page when searched with VIN
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    * User verifies Connector General Repair Instructions link is displayed
   When On connector lookup ui User selects "VIN" radio button
    * User enters "<VIN>" in vin textbox
    * User clicks on search button
    Then User verifies list of connectors are displayed
    * User verifies "Connector Name" column is displayed in 1 position
    * User verifies "Connector Type" column is displayed in 2 position
    * User verifies "Service Kit Part Number" column is displayed in 3 position
    * User verifies "Kit Description" column is displayed in 4 position
    * User verifies "Color" column is displayed in 5 position
    * User verifies "Cavities" column is displayed in 6 position
    
   Examples: 
    | Dealer_User | VIN               |
      | DSCHER49    | KNMAT2MT2FP529932 |
      
  @Regression @HRK88TC07
  Scenario Outline: TC31 Dealer User: Verify the column header of connector data table on connectors results page when searched with Repair Order Number
  Given Create a repair order with "<Dealer_Code>" and "<RO_Number>" for following VINs
  |1N6AD0EV3FN755780|
    * Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    * User verifies Connector General Repair Instructions link is displayed
   * On connector lookup ui select "Repair Order / Work Order" radio button
    * Enter "<RO_Number>" in input textbox
    * User clicks on search button
    Then User verifies list of connectors are displayed
    * User verifies "Connector Name" column is displayed in 1 position
    * User verifies "Connector Type" column is displayed in 2 position
    * User verifies "Service Kit Part Number" column is displayed in 3 position
    * User verifies "Kit Description" column is displayed in 4 position
    * User verifies "Color" column is displayed in 5 position
    * User verifies "Cavities" column is displayed in 6 position
    
   Examples: 
    | Dealer_User |Dealer_Code| RO_Number |
      | DFONTT07    |3529|  AT-105711 |
      
        @Regression @HRK88TC08
  Scenario: TC32 HRKManager: Verify all fields under Connector Info section are displayed
    Given Login to the application as HRK Manager
    Then HRK Manager clicks on "Manage Connectors"
    * HRK Manager verifies Manage Connector tab is opened with heading "Manage Connectors"
    * HRK Manager clicks on record no. 1 from the list of connectors
    * HRK Manager verifies Connector Info page is displayed
    * HRK Manager verifies "Connector Information" section is displayed
    * HRK Manager verifies "Part Name" is displayed under Connector Information section
    * HRK Manager verifies "Nissan Service Kit Part #" is displayed under Connector Information section
    * HRK Manager verifies "Kit Description" is displayed under Connector Information section
    * HRK Manager verifies "Connector Color" is displayed under Connector Information section
    * HRK Manager verifies "No. of Cavities" is displayed under Connector Information section
    * HRK Manager verifies "Nissan Conn. Type" is displayed under Connector Information section
    * HRK Manager verifies "AWG Wire" is displayed under Connector Information section
    * HRK Manager verifies "Solder Sleeve" is displayed under Connector Information section
    * HRK Manager verifies "Retainer Pick Tool" is displayed under Connector Information section
    * HRK Manager verifies "Terminal Removal Tool" is displayed under Connector Information section
    * HRK Manager verifies "Terminal Tightness Tool" is displayed under Connector Information section  

  @Regression @HRK88TC09
  Scenario Outline: TC33 User: Verify that file name is displayed as link and when clicked, file is opened in reading frame on Tools
    Given Login as User "<Dealer_User>"
    * User clicks on "Connector Lookup" from left side menu
    * User verifies connector lookup ui is displayed
    When On connector lookup ui User selects "Vehicle Make / Model" radio button
    * User selects "<Make>" from make drop down
    * User selects "<Model>" from model drop down
    * User selects "<Year>" from year drop down
    Then User verifies list of connectors are displayed
    * User uses smart search and search with "<ConnectorId_QA>"
    * User click on first record from the list of connectors
    * User verifies Connector Info screen is displayed
    * User clicks on Connector Disassembly Instructions button
    * User verifies Connector Disassembly Instructions page is displayed with heading "<ConnectorId_QA>" in a new window
    * User clicks on "24008 4RJ3A" file label on TOOLS AND CONNECTOR DISASSEMBLY page
    * User verifies file is opened in reading frame on TOOLS AND CONNECTOR DISASSEMBLY page
    * User closes Connector Disassembly Instructions page
  
    Examples: 
      | Dealer_User | Make   | Model | Year | Name        | Email                        | ConnectorId_QA | ConnectorId_SIT |
      | DSCHER49    | Nissan | Titan | 2022 | Ross Schewe | Ramya.Bapatla@nissan-usa.com | 24008 4RJ3A    | 24008 9DM6A     |
    
    