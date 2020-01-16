package com.equinor.cargotrackerreference.domain;

import java.util.Optional;

public enum SourceSystem {
	SPORT(),
	CARGO_TRACKING("Cargo Tracking"),
	RATS(),
	COE();

	public final String label;
	
	public static Optional<SourceSystem> of(String label) {
		Optional<SourceSystem> optionalSourceSystem = Optional.empty();
		for (SourceSystem sourceSystem : SourceSystem.values()) {
			if (sourceSystem.label.equals(label)) {
				optionalSourceSystem = Optional.of(sourceSystem);
			}
		}
		return optionalSourceSystem;
	}

	private SourceSystem() {
		this.label = this.name();
	}

	private SourceSystem(String label) {
		this.label = label;
	}
}
