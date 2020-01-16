package com.equinor.cargotrackerreference.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinor.cargotrackerreference.domain.PriceBasis;

@RestController
@RequestMapping(value = "/ct/config")
@CrossOrigin(origins = "*")
public class PriceBasisController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/pricebasis", method = RequestMethod.GET)
	public List<PriceBasisDto> getPriceBasis() {
		logger.debug("Getting price basis.");
		List<PriceBasisDto> priceBasis = new ArrayList<>();
		for (PriceBasis p : PriceBasis.values()) {
			priceBasis.add(new PriceBasisDto(p.name(), p.label));
		}
		return priceBasis;

	}

	public class PriceBasisDto {
		public final String name;
		public final String label;

		public PriceBasisDto(String name, String label) {
			super();
			this.name = name;
			this.label = label;
		}
	}

}
