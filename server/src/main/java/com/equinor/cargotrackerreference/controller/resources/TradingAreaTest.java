package com.equinor.cargotrackerreference.controller.resources;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TradingAreaTest {
	
	public TradingAreaTest() {
		
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tradingAreaId")
	public TradingAreaIdNameProperty tradingArea;
	public String comment;
	public boolean confidential;
}
