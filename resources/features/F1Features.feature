@tag
Feature: F1
  I want to

 Background: User fetches TSB and NAA approval
 	Given Login page is displayed
 	And Log into application as Admin user
 	And Admin creates RO
 	|Component|Dealer Code|VIN|Mileage|RuleSet Version|Warranty|Manufacture Date|
 	|CVT|3900|1N4BL2AP6AN487528|8000|STATIntSvc:01-06-03|No||
 	And Admin fetches vehicle reference details
 	And Admin logs out
 	And Login to application as Engineering Manager
 	And Engineering Manager evaluates TSBs
 	|Customer Symptom Checkboxes|Customer Symptom DropDowns							|Customer OtherSymptoms		|Payment Assumption	|Technician Symptom Checkboxes|Technician Symptom DropDowns|Technician OtherSymptoms		|login required		|logout required|
	|														|Vibration:Shake;ENGINESTALLWHEN:P-to-R|Customer Repair comments	|Customer Pay				|															|Vibration:Shake						 |Technician Repair comments	|false						|false					| 
  

    