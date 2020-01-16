package com.equinor.cargotrackerreference.utils;

import java.time.YearMonth;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {

	@Override
	public String convertToDatabaseColumn(YearMonth yearmonth) {
		return yearmonth != null ? yearmonth.toString() : null;
	}

	@Override
	public YearMonth convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		return YearMonth.parse(dbData);
	}
}
