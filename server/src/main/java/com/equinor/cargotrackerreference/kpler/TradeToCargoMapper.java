package com.equinor.cargotrackerreference.kpler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;



@Mapper
public interface TradeToCargoMapper {
	
	TradeToCargoMapper INSTANCE = Mappers.getMapper(TradeToCargoMapper.class);
	
	@Mappings({
	@Mapping(target = "id", ignore=true),
	@Mapping(source = "vessel", target = "vesselName")
	
	
	})
	AnalyticsCargoResource tradeToCargo(StrippedTrade trade);
	
	
}
