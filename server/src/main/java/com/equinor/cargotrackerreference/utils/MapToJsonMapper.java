package com.equinor.cargotrackerreference.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jadira.usertype.spi.shared.AbstractStringColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapToJsonMapper extends AbstractStringColumnMapper<Map<String, String>> {

	private static final long serialVersionUID = -4985834656096627011L;
	private static final Logger LOG = LoggerFactory.getLogger(MapToJsonMapper.class);

	private static ObjectMapper mapper = new ObjectMapper();

	
	public static Map<String, String> toMap(String json) {
		MapToJsonMapper mapper = new MapToJsonMapper();
		return mapper.fromNonNullValue(json);
	}

	public static String toJson(Map<String, String> map) {
		MapToJsonMapper mapper = new MapToJsonMapper();
		return mapper.toNonNullValue(map);
	}

	public static String toJson(Object object) {
		String json = "";
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LOG.error("Exception occured mapping from object to json.\nObject: {}", object, e);
		}
		return json;
	}

	public static <T> T fromJson(String json, TypeReference<T> typeReference) {
		T object = null;
		try {
			object = mapper.readValue(json, typeReference);
		} catch (IOException e) {
			LOG.error("Exception occured mapping from json-string to object.\nJson: {}", json, e);
		}
		return object;
	}

	@Override
	public Map<String, String> fromNonNullValue(String arg0) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (arg0 == null) {
			map = new HashMap<String, String>();
		} else {
			try {
				map = mapper.readValue(arg0, new TypeReference<HashMap<String, String>>() {
				});
			} catch (IOException e) {
				LOG.error("Exception occured mapping from json-string to map.\nJson: {}", arg0, e);
			}
		}
		return map;
	}

	@Override
	public String toNonNullValue(Map<String, String> map) {
		String value = null;
		try {
			value = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			LOG.error("Exception occured creating json-string based on input map", e);
		}
		return value;
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}

}