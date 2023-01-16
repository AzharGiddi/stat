@USS
Feature: Regression for USS scenarios

  @Regression1 @USS1
  Scenario: USS regression scenario
    Given Admin creates RO for good will request with below set of details
      | Component | Dealer Code | Coverage_Code | Coverage_Type | VIN               | Mileage | NissanAmountGTDcal | Warranty | Vehicle_Component |
      | CVT       |        3529 | FG            | Powertrain    | 1N6AA0EC7FN513355 |    8000 | false              | true     | Restricted Parts  |
    * Login as service advisor
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "I See" under Observation Malfunction section and selects below concerns
      | Check Engine Light / Warning Light / Warning Message / Malfunction Message |
      | Poor Appearance                                                            |
    * Service advisor clicks on "I Hear" under Observation Malfunction section and selects below concerns
      | Excessive Noise       |
      | Tick / Click / Rattle |
    * Service advisor clicks on "Time" under when It Occurs section and selects below concerns
      | Intermittent |
      | Since New    |
    * Service advisor clicks on "Touch Screen Area" hotspot	under Component Details and selects below values
      | Audio |

  @Regression @USS2
  Scenario: Verify all the options under I See menu are displayed as expected
    Given Login as service advisor "DFONTT07"
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters "1N6AA0EC7FN513355" as VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "I See" under Observation Malfunction section and verify below concerns are displayed
      | Check Engine Light / Warning Light / Warning Message / Malfunction Message |
      | Poor Appearance                                                            |
      | Dim / No Illumination                                                      |
      | Perforated / Rust Related / Foreign Material                               |
      | Fell Off / Loose / Poor Fit                                                |
      | No Operation / Indication                                                  |
      | No Operation / Body Related                                                |
      | Missing/ Built Incorrectly / Improperly Installed                          |
      | Broken / Cracked / Dented / Chipped / Dinged / Warped                      |
      | Kinked / Bent                                                              |
      | Oil Leak / Fluid Leak / Fuel Leak                                          |
      | Soiled / Worn / Torn / Separated                                           |
      | Blue Smoke / White Smoke / Black Smoke                                     |
      | Poor Visibility / Distorted Glass / Hazy Glass                             |
      | Water / Leaking / Condensation                                             |
    * service advisor logs out

  @Regression @USS3
  Scenario: Verify all the options under I See menu are displayed as expected
    Given Login as service advisor "DFONTT07"
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters "1N6AA0EC7FN513355" as VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "I Hear" under Observation Malfunction section and verify below concerns are displayed
      | Excessive Noise                        |
      | Tick / Click / Rattle                  |
      | Boom / Drone / Rumble / Roar / Throb   |
      | Hum / Whine / Static (Melodious Sound) |
      | Hiss / Whistle / Buzz (Air Movement)   |
      | Chirp / Creak / Squeak / Squeal        |
      | Grind / Growl / Groan                  |
      | Clunk / Knock / Pop / Thump            |
      | Gurgle / Slosh (Liquid Movement)       |
      | Flutter / Flapping                     |
      | Warning / Gong / Chime / Alert         |
    * service advisor logs out

  @Regression @USS4
  Scenario: Verify all the options under I See menu are displayed as expected
    Given Login as service advisor "DFONTT07"
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters "1N6AA0EC7FN513355" as VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "I Smell" under Observation Malfunction section and verify below concerns are displayed
      | Electrical            |
      | Fuel                  |
      | Exhaust               |
      | Amonia                |
      | Rotten                |
      | Moldy                 |
      | Burning               |
      | Other (i.e. A/C Odor) |
      | No "New Car Smell"    |
      | Plastic               |
      | Sweet                 |
    * service advisor logs out

  @Regression @USS5
  Scenario: Verify all the options under I See menu are displayed as expected
    Given Login as service advisor "DFONTT07"
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters "1N6AA0EC7FN513355" as VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "I Feel" under Observation Malfunction section and verify below concerns are displayed
      | Slow / Hesitates / Stops / Lack of Response |
      | Binding / Sticking / Bound                  |
      | Hard to Move / Stiff / Heavy                |
      | Vibration (Slow)                            |
      | Vibration (Fast)                            |
      | Judder / Rumble                             |
      | Shake / Shimmy (Road Speed Related)         |
      | Multiple Knock / Bang / Bump                |
      | Single Knock / Bang / Bump                  |
      | Rough Surface                               |
      | Sticky Surface                              |
      | Rough Idle                                  |
    * service advisor logs out

  @Regression @USS6
  Scenario: Verify all the options under I See menu are displayed as expected
    Given Login as service advisor "DFONTT07"
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters "1N6AA0EC7FN513355" as VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "I Taste" under Observation Malfunction section and verify below concerns are displayed
      | Bitter  |
      | Exhaust |
      | Moldy   |
      | Salty   |
      | Sweet   |
    * service advisor logs out

  @Regression @USS7
  Scenario: Verify all the options under I See menu are displayed as expected
    Given Login as service advisor "DFONTT07"
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters "1N6AA0EC7FN513355" as VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "Functionality" under Observation Malfunction section and verify below concerns are displayed
      | No Operation                                |
      | Unwanted Operation                          |
      | Improper Opening / Closing                  |
      | Improper Lock / Unlock                      |
      | Improper Illumination                       |
      | Improper Movement / Sliding / Pivoting      |
      | Wrong / Missing                             |
      | No Display / Frozen Display / No Response   |
      | Intermittent                                |
      | Slow / Hesitates / Stops / Lack of Response |
      | Low Power / Poor Performance                |
      | Pulls Left / Pulls Right                    |
      | Difficult To Fill                           |
      | Delayed                                     |
    * service advisor logs out
    
  

  @USS100
  Scenario: Verify all the options under I See menu are displayed as expected
    Given Login as service advisor "DFONTT07"
    * Service advisor clicks on Universal Symptom Survey
    * Service advisor verifies Customer Concerns component is displayed
    * Service advisor enters "1N6AA0EC7FN513355" as VIN on customer concerns component and click on go
    * Service advisor clicks on add concern button
    * Service advisor clicks on "Time" under Observation Malfunction section and get all concerns
