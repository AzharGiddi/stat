package com.automation.core.enums;

public enum CheckboxName {

	FLARE("Flare"), HARSHSHIFT("Harsh Shift"), HESITATION("Hesitation"), LACKOFPOWER("Lack of Power"), NODOWNSHIFT(
			"No Downshift"), NOUPSHIFT("No Upshift"), SHIFTPOINTTOOHIGH("Shift Point Too High"), SHIFTPOINTTOOLOW(
					"Shift Point Too Low"), SLIPS("Slips"), EXCESSIVENOISE("Excessive Noise"), TICKCLICKRATTLE(
							"Tick / Click / Rattle"), BOOMDRONERUMBLEROARTHROB(
									"Boom / Drone / Rumble / Roar / Throb"), HUMWHINESTATICMELODIOUSSOUND(
											"Hum / Whine / Static (Melodious Sound)"), HISSWHISTLEBUZZAIRMOVEMENT(
													"Hiss / Whistle / Buzz (Air Movement)"), CHIRPCREAKSQUEAKSQUEAL(
															"Chirp / Creak / Squeak / Squeal"), GRINDGROWLGROAN(
																	"Grind / Growl / Groan"), CLUNKKNOCKPOPTHUMP(
																			"Clunk / Knock / Pop / Thump"), GURGLESLOSHLIQUIDMOVEMENT(
																					"Gurgle / Slosh (Liquid Movement)"), ATABOUTMPH(
																							"At about MPH"), ENGINECOLD(
																									"Engine Cold"), ENGINEHOT(
																											"Engine Hot"), GOINGDOWNHILL(
																													"Going downhill"), GOINGUPHILLHEAVYTHROTTLE(
																															"Going uphill, heavy throttle"), GOINGUPHILLLIGHTTHROTTLE(
																																	"Going uphill, light throttle"), IDLE(
																																			"Idle"), OTHERS(
																																					"Others"), SLOWINGDOWN(
																																							"Slowing Down"), SPEEDINGUPHEAVYTHROTTLE(
																																									"Speeding up, heavy throttle"), SPEEDINGUPLIGHTTHROTTLE(
																																											"Speeding up, light throttle"), STARTINGUPENGINE(
																																													"Starting up engine"),NOMATCHFOUND("No Match Found");;

	private String name;

	public String getCheckboxName() {
		return name;
	}

	private CheckboxName(String checkboxName) {
		name = checkboxName;
	}

}
