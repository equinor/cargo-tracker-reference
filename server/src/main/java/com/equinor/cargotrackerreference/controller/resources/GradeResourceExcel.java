package com.equinor.cargotrackerreference.controller.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class GradeResourceExcel {

	public GradeResourceExcel() {
	}

	public String name;
	public String reference;
	public String tradingArea;
	public String country;
	public BigDecimal api;
	public BigDecimal sulphur;
	public LocalDate validFrom;
	
	@Override
	public String toString() {
		return "GradeResourceExcel [name=" + name + ", reference=" + reference + ", api=" + api + ", sulphur=" + sulphur
				+ ", validFrom=" + validFrom + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GradeResourceExcel other = (GradeResourceExcel) obj;
		return Objects.equals(name, other.name);
	}
	
	
	
}
