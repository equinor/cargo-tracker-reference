package com.equinor.cargotrackerreference.controller.resources;

import java.util.Iterator;

import com.equinor.cargotrackerreference.domain.Terminal;

public class TerminalResourceIterator implements Iterator<TerminalResource>, Iterable<TerminalResource> {

	private Iterator<? extends Terminal> locationIterator;

	public TerminalResourceIterator(Iterator<? extends Terminal> analyticsCargoIterator) {
		this.locationIterator = analyticsCargoIterator;
	}

	@Override
	public boolean hasNext() {
		return locationIterator.hasNext();
	}

	@Override
	public TerminalResource next() {
		return TerminalResourceConverter.createResourceFromTerminal(locationIterator.next());
	}

	@Override
	public Iterator<TerminalResource> iterator() {
		return this;
	}

}
