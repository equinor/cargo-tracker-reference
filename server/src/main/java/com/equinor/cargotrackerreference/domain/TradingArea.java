package com.equinor.cargotrackerreference.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A TradingArea is the owner of a Crude Market Cargo.  A TradingArea will typically correspond to a trading 
 * desk within Marketing & Trading. A TradingArea has a one to many relationship with Crude Market Cargoes.
 * 
 * @author NIAND
 *
 */
@Entity
@Table(name = "trading_area")
public class TradingArea extends VersionedEntityName {

	private boolean active;
	
	public TradingArea() {
		super();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return super.toString() + " TradingArea [active=" + active + "]";
	}

	
}
