package com.equinor.cargotrackerreference.domain;

public enum PriceBasis {
	DTD("DTD"),
	WTI("WTI"),
	ICE("ICE"),
	OSP("OSP"),
	PLATTS("PLATTS"),
	FIXED_PRICE("FIXED PRICE"),
	NP("NP");

	public String label;

	private PriceBasis() {
		this.label = this.name();
	}

	private PriceBasis(String label) {
		this.label = label;
	}

}
