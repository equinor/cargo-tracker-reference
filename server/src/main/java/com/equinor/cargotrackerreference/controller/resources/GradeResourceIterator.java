package com.equinor.cargotrackerreference.controller.resources;

import java.util.Iterator;

import com.equinor.cargotracker.common.domain.Grade;

public class GradeResourceIterator implements Iterator<GradeResource>, Iterable<GradeResource> {

	private Iterator<? extends Grade> gradeIterator;

	public GradeResourceIterator(Iterator<? extends Grade> gradeIterator) {
		this.gradeIterator = gradeIterator;
	}

	@Override
	public boolean hasNext() {
		return gradeIterator.hasNext();
	}
	
	

	@Override
	public GradeResource next() {
		return GradeResourceConverter.createResourceFromGrade(gradeIterator.next());
	}

	@Override
	public Iterator<GradeResource> iterator() {
		return this;
	}

}
