package com.equinor.cargotrackerreference.service;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecreateService {

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void recreate() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		logger.debug("Deleting existing readmodel");
		//cargoRmRepository.deleteAll();

		logger.debug("Starting recreating readmodel");
		//cargoRepository.findAll().forEach(analyticsCargo -> analyticsCargoService.saveCargoRm(analyticsCargo));
		stopWatch.stop();
		logger.debug("Finished in " + stopWatch.toString());
	}
}
